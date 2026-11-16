# Consommateurs SOAP Banque

Demarrer MySQL et le serveur SOAP (`com.example.soap.App` dans `SOAP_BANQUE`)
avant de lancer un consommateur. Le WSDL utilise est
`http://localhost:8005/BanqueWS?wsdl`.

Les quatre programmes sont des clients en console avec le meme menu :

```text
1. Consulter un compte
2. Faire une conversion
3. Lister les comptes
4. Creer un compte
5. Verser de l'argent
6. Retirer de l'argent
7. Faire un virement
8. Supprimer un compte
0. Quitter
```

Saisir les montants avec un point decimal, par exemple `25.50`.
Les erreurs SOAP sont affichees et le menu permet de faire une autre operation.
Un compte ne peut etre supprime que si son solde est nul.

## Interface Angular et API HTTP

Le projet contient egalement une interface Angular, avec un choix reel du
consommateur. Voir le [README principal](../README.md) pour lancer toute
l'application avec `demarrer.ps1`.

Les fichiers `api_python.py`, `api_php.php` et `api_js.js` exposent les appels
SOAP en HTTP/JSON. Le consommateur Java fournit la classe `BanqueApi`.
Ces passerelles utilisent les memes bibliotheques SOAP que les menus ci-dessous.

## Python

Dans ce dossier, sous Windows :

```powershell
py -m pip install zeep
py client_python.py
```

Si Python est disponible avec la commande `python`, elle peut remplacer `py`.

## PHP

Activer l'extension `soap` dans le `php.ini` utilise par PHP en ligne de commande.
Puis, dans ce dossier :

```powershell
php client_php.php
```

## JavaScript (Node.js)

Utiliser Node.js 18 ou plus recent. Dans ce dossier :

```powershell
npm install
node client_js.js
```

## Java

Les classes `proxy` ont ete regenerees avec les nouvelles methodes SOAP.
Dans `../clientBanqueSOAP`, executer :

```powershell
mvn compile
```

Puis lancer la classe `com.example.jaxws.App` dans l'IDE.
Le serveur SOAP doit etre demarre pendant la generation des classes et
l'execution du consommateur.
