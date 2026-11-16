$ErrorActionPreference = 'Stop'
$projectRoot = $PSScriptRoot
$runtime = Join-Path $projectRoot '.runtime'
New-Item -ItemType Directory -Path $runtime -Force | Out-Null
$processFile = Join-Path $runtime 'processes.json'
$ownedProcesses = @()
if (Test-Path -LiteralPath $processFile) {
    $ownedProcesses = Get-Content -LiteralPath $processFile -Raw | ConvertFrom-Json
}

function Port-Ouvert($port) {
    return [bool](Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue)
}

function Lancer($nom, $port, $executable, $arguments, $dossier) {
    if (Port-Ouvert $port) {
        Write-Host "$nom deja demarre sur le port $port."
        return
    }
    $processus = Start-Process -FilePath $executable -ArgumentList $arguments `
        -WorkingDirectory $dossier -WindowStyle Hidden -PassThru `
        -RedirectStandardOutput (Join-Path $runtime "$nom.log") `
        -RedirectStandardError (Join-Path $runtime "$nom.err.log")
    $script:ownedProcesses += [pscustomobject]@{
        name = $nom; id = $processus.Id; started = $processus.StartTime.ToUniversalTime().ToString('o')
    }
    ConvertTo-Json -InputObject @($script:ownedProcesses) | Set-Content -LiteralPath $processFile
    for ($attempt = 0; $attempt -lt 60; $attempt++) {
        if (Port-Ouvert $port) {
            Write-Host "$nom disponible sur le port $port."
            return
        }
        if ($processus.HasExited) {
            throw "$nom n'a pas demarre. Consultez .runtime/$nom.err.log."
        }
        Start-Sleep -Milliseconds 500
        $processus.Refresh()
    }
    throw "$nom met trop de temps a demarrer. Consultez .runtime/$nom.log."
}

if (!(Port-Ouvert 3306)) {
    throw 'Demarrez MySQL dans Laragon avant de lancer cette commande.'
}

$java = (Get-Command java -ErrorAction Stop).Source
$node = (Get-Command node -ErrorAction Stop).Source
$php = (Get-Command php -ErrorAction Stop).Source
$python = (& py -c 'import sys; print(sys.executable)').Trim()
if ($LASTEXITCODE -ne 0) { throw 'Python est introuvable. Installez Python et le lanceur py.' }
& $python -c 'import zeep'
if ($LASTEXITCODE -ne 0) { throw 'Installez Zeep : py -m pip install zeep' }

$soapDir = Join-Path $projectRoot 'SOAP_BANQUE'
if (!(Port-Ouvert 8005)) {
    & mvn -B -ntp -f (Join-Path $soapDir 'pom.xml') compile dependency:build-classpath '-Dmdep.outputFile=target/soap-classpath.txt'
    if ($LASTEXITCODE -ne 0) { throw 'Compilation du serveur SOAP echouee.' }
    $soapClasspath = (Join-Path $soapDir 'target/classes') + ';' + (Get-Content (Join-Path $soapDir 'target/soap-classpath.txt') -Raw).Trim()
    Lancer 'soap' 8005 $java @('-cp', "`"$soapClasspath`"", 'com.example.soap.App') $soapDir
}

$clientDir = Join-Path $projectRoot 'clientBanqueSOAP'
& mvn -B -ntp -f (Join-Path $clientDir 'pom.xml') compile dependency:build-classpath '-Dmdep.outputFile=target/client-classpath.txt'
if ($LASTEXITCODE -ne 0) { throw 'Compilation du consommateur Java echouee.' }
$clientClasspath = (Join-Path $clientDir 'target/classes') + ';' + (Get-Content (Join-Path $clientDir 'target/client-classpath.txt') -Raw).Trim()
$consumersDir = Join-Path $projectRoot 'Consommateur_service_soap'

if (!(Test-Path (Join-Path $consumersDir 'node_modules/soap'))) {
    Push-Location $consumersDir
    try { & npm ci; if ($LASTEXITCODE -ne 0) { throw 'Installation du client JavaScript echouee.' } }
    finally { Pop-Location }
}

Lancer 'api-java' 8101 $java @('-cp', "`"$clientClasspath`"", 'com.example.jaxws.BanqueApi') $clientDir
Lancer 'api-python' 8102 $python @('-u', 'api_python.py') $consumersDir
Lancer 'api-php' 8103 $php @('-S', '127.0.0.1:8103', 'api_php.php') $consumersDir
Lancer 'api-js' 8104 $node @('api_js.js') $consumersDir

$angularDir = Join-Path $projectRoot 'banque-angular'
if (!(Test-Path (Join-Path $angularDir 'node_modules/@angular/cli'))) {
    Push-Location $angularDir
    try { & npm ci; if ($LASTEXITCODE -ne 0) { throw 'Installation Angular echouee.' } }
    finally { Pop-Location }
}
$env:NG_CLI_ANALYTICS = 'false'
Lancer 'angular' 4200 $node @('node_modules/@angular/cli/bin/ng.js', 'serve') $angularDir
Write-Host ''
Write-Host 'Application disponible : http://127.0.0.1:4200' -ForegroundColor Green
Write-Host 'Pour arreter les processus lances par ce script : .\arreter.ps1'
