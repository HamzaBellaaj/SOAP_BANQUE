<?php

function lire($message) {
    echo $message;
    $ligne = fgets(STDIN);
    if ($ligne === false) {
        throw new RuntimeException("Fin de la saisie.");
    }
    return trim($ligne);
}

function lireCode($message) {
    $code = filter_var(lire($message), FILTER_VALIDATE_INT);
    if ($code === false) {
        throw new InvalidArgumentException("Le code doit etre un entier.");
    }
    return $code;
}

function lireMontant($message) {
    $valeur = lire($message);
    if (!is_numeric($valeur) || !is_finite((float) $valeur)) {
        throw new InvalidArgumentException("Le montant doit etre un nombre.");
    }
    return (float) $valeur;
}

function afficherCompte($compte) {
    if ($compte === null) {
        echo "Compte introuvable.\n";
    } else {
        echo "Numero : $compte->numCompte | Solde : $compte->solde | Date creation : $compte->dateCreation\n";
    }
}

try {
    $client = new SoapClient("http://localhost:8005/BanqueWS?wsdl", [
        'trace' => 1,
        'exceptions' => true,
        'cache_wsdl' => WSDL_CACHE_NONE
    ]);

    do {
        echo "\n===== MENU BANQUE - PHP =====\n";
        echo "1. Consulter un compte\n";
        echo "2. Faire une conversion\n";
        echo "3. Lister les comptes\n";
        echo "4. Creer un compte\n";
        echo "5. Verser de l'argent\n";
        echo "6. Retirer de l'argent\n";
        echo "7. Faire un virement\n";
        echo "8. Supprimer un compte\n";
        echo "0. Quitter\n";
        $choix = lire("Veuillez choisir une option : ");

        try {
            switch ($choix) {
                case '1':
                    $code = lireCode("Code du compte : ");
                    $res = $client->getCompte(['code' => $code]);
                    afficherCompte($res->return ?? null);
                    break;
                case '2':
                    $montant = lireMontant("Montant a convertir : ");
                    $res = $client->conversion(['montant' => $montant]);
                    echo "Conversion : $res->return\n";
                    break;
                case '3':
                    $res = $client->listComptes();
                    $comptes = $res->return ?? [];
                    // PHP renvoie un objet s'il n'y a qu'un seul compte.
                    if (!is_array($comptes)) {
                        $comptes = [$comptes];
                    }
                    if (count($comptes) === 0) {
                        echo "Aucun compte.\n";
                    }
                    foreach ($comptes as $compte) {
                        afficherCompte($compte);
                    }
                    break;
                case '4':
                    $solde = lireMontant("Solde initial : ");
                    $res = $client->creerCompte(['soldeInitial' => $solde]);
                    afficherCompte($res->return);
                    break;
                case '5':
                    $code = lireCode("Code du compte : ");
                    $montant = lireMontant("Montant a verser : ");
                    $res = $client->verser(['code' => $code, 'montant' => $montant]);
                    echo $res->return . "\n";
                    break;
                case '6':
                    $code = lireCode("Code du compte : ");
                    $montant = lireMontant("Montant a retirer : ");
                    $res = $client->retirer(['code' => $code, 'montant' => $montant]);
                    echo $res->return . "\n";
                    break;
                case '7':
                    $source = lireCode("Code du compte source : ");
                    $destination = lireCode("Code du compte destination : ");
                    $montant = lireMontant("Montant du virement : ");
                    $res = $client->virement([
                        'codeSource' => $source,
                        'codeDestination' => $destination,
                        'montant' => $montant
                    ]);
                    echo $res->return . "\n";
                    break;
                case '8':
                    $code = lireCode("Code du compte a supprimer : ");
                    $res = $client->supprimerCompte(['code' => $code]);
                    echo $res->return . "\n";
                    break;
                case '0':
                    echo "Au revoir !\n";
                    break;
                default:
                    echo "Choix invalide.\n";
            }
        } catch (SoapFault $e) {
            echo "Erreur SOAP : " . $e->getMessage() . "\n";
        } catch (InvalidArgumentException $e) {
            echo "Saisie invalide : " . $e->getMessage() . "\n";
        }
    } while ($choix !== '0');
} catch (SoapFault $e) {
    echo "Connexion au service SOAP impossible : " . $e->getMessage() . "\n";
} catch (RuntimeException $e) {
    echo $e->getMessage() . "\n";
}
