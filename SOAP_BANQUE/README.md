# Service SOAP Banque

Le service utilise MySQL sur `localhost:3306`, la base `banque_db`,
l'utilisateur `root` et un mot de passe vide, comme dans le code existant.

## Nouvelles methodes

| Methode | Resultat |
| --- | --- |
| `creerCompte(soldeInitial)` | Le compte cree, avec son numero et la date du jour |
| `verser(code, montant)` | Un message de confirmation |
| `retirer(code, montant)` | Un message de confirmation |
| `virement(codeSource, codeDestination, montant)` | Un message de confirmation |
| `supprimerCompte(code)` | Un message de confirmation |

Le solde initial peut etre nul. Les montants des operations doivent etre
strictement positifs. Un retrait ou un virement ne peut pas rendre le solde
negatif. Le virement utilise une transaction : si le compte destination
n'existe pas, le debit est annule. La suppression exige un solde nul.
Une operation refusee renvoie une erreur SOAP au client.

## Table MySQL

La creation d'un compte suppose que `numCompte` est une cle primaire
`AUTO_INCREMENT`. Les virements supposent une table utilisant le moteur
`InnoDB`, qui permet les transactions.

Pour verifier la table existante dans MySQL :

```sql
SHOW CREATE TABLE banque_db.compte_bancaire;
```

Si `numCompte` n'est pas en `AUTO_INCREMENT`, executer le fichier
`sql/activer_numero_automatique.sql` dans MySQL. Cette modification conserve
les numeros des comptes existants.

Pour une nouvelle base, voici une table compatible :

```sql
CREATE DATABASE IF NOT EXISTS banque_db;
USE banque_db;

CREATE TABLE IF NOT EXISTS compte_bancaire (
    numCompte INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    solde DOUBLE NOT NULL,
    dateCreation DATE NOT NULL
) ENGINE=InnoDB;
```

`CREATE TABLE IF NOT EXISTS` ne modifie pas une table deja presente.

## Utilisation

Demarrer MySQL, puis lancer la classe `com.example.soap.App` dans l'IDE.
Le WSDL est disponible a `http://localhost:8005/BanqueWS?wsdl`.
Il faut redemarrer le serveur apres les changements pour publier les nouvelles methodes.

Les clients Python, PHP et JavaScript peuvent appeler les nouvelles methodes
apres rechargement du WSDL. Exemple Python, avec un client Zeep deja cree :

```python
compte = client.service.creerCompte(100)
print(client.service.verser(compte.numCompte, 50))
print(client.service.retirer(compte.numCompte, 20))
```

Pour le client Java, il faut regenerer les classes `proxy` avec le serveur
demarre : executer `mvn generate-sources` dans `clientBanqueSOAP`.
Les quatre consommateurs proposent maintenant les huit operations dans un menu
textuel. Voir `../Consommateur_service_soap/README.md` pour les lancer.

Pour lancer la compilation et les tests de validation, executer `mvn test`
dans `SOAP_BANQUE`. Ces tests ne necessitent pas de connexion a MySQL.
