$ErrorActionPreference = 'Stop'
$processFile = Join-Path $PSScriptRoot '.runtime/processes.json'
if (!(Test-Path -LiteralPath $processFile)) {
    Write-Host 'Aucun processus enregistre.'
    return
}

$processes = Get-Content -LiteralPath $processFile -Raw | ConvertFrom-Json
foreach ($item in $processes) {
    $processus = Get-Process -Id $item.id -ErrorAction SilentlyContinue
    # La date de lancement evite d'arreter un autre processus qui aurait repris le meme PID.
    if ($processus -and $processus.StartTime.ToUniversalTime().ToString('o') -eq $item.started) {
        Stop-Process -Id $item.id
        Write-Host "$($item.name) arrete."
    }
}
Set-Content -LiteralPath $processFile -Value '[]'
