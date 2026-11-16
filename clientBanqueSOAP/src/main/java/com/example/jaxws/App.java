package com.example.jaxws;

import proxy.BanqueWS;
import proxy.CompteBancaireWS;
import proxy.CompteBancaire;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Locale;
import jakarta.xml.ws.WebServiceException;

public class App {
    public static void afficherCompte(CompteBancaire compte) {
        if (compte == null) {
            System.out.println("Compte introuvable.");
        } else {
            System.out.println("Numero : " + compte.getNumCompte()
                + " | Solde : " + compte.getSolde()
                + " | Date creation : " + compte.getDateCreation());
        }
    }

    public static void main(String[] args) {
        CompteBancaireWS proxy;
        try {
            proxy = new BanqueWS().getCompteBancaireWSPort();
        } catch (WebServiceException e) {
            System.out.println("Connexion au service SOAP impossible : " + e.getMessage());
            return;
        }
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        int choix;

        do {
            System.out.println("\n===== MENU BANQUE =====");
            System.out.println("1. Consulter un compte");
            System.out.println("2. Faire une conversion");
            System.out.println("3. Lister les comptes");
            System.out.println("4. Creer un compte");
            System.out.println("5. Verser de l'argent");
            System.out.println("6. Retirer de l'argent");
            System.out.println("7. Faire un virement");
            System.out.println("8. Supprimer un compte");
            System.out.println("0. Quitter");
            System.out.print("Veuillez choisir une option : ");

            if (!sc.hasNext()) break;
            if (!sc.hasNextInt()) {
                System.out.println("Choix invalide.");
                sc.nextLine();
                choix = -1;
                continue;
            }
            choix = sc.nextInt();

            try {
                switch (choix) {
                    case 1:
                        System.out.println("-> Option 1 sélectionnée (Consulter un compte)");
                        System.out.print("Tapez le code du compte : ");
                        int code = sc.nextInt();
                        CompteBancaire compte = proxy.getCompte(code);
                        afficherCompte(compte);
                        break;
                    case 2:
                        System.out.println("-> Option 2 sélectionnée (Conversion)");
                        System.out.print("Tapez le montant a convertir : ");
                        double mt = sc.nextDouble();
                        System.out.println("Conv : " + proxy.conversion(mt));
                        break;
                    case 3:
                        System.out.println("-> Option 3 sélectionnée (Lister les comptes)");
                        java.util.List<CompteBancaire> liste = proxy.listComptes();
                        if (liste.isEmpty()) System.out.println("Aucun compte.");
                        for (CompteBancaire c : liste) {
                            afficherCompte(c);
                        }
                        break;
                    case 4: {
                        System.out.print("Solde initial : ");
                        double solde = sc.nextDouble();
                        afficherCompte(proxy.creerCompte(solde));
                        break;
                    }
                    case 5: {
                        System.out.print("Code du compte : ");
                        int numero = sc.nextInt();
                        System.out.print("Montant a verser : ");
                        double montant = sc.nextDouble();
                        System.out.println(proxy.verser(numero, montant));
                        break;
                    }
                    case 6: {
                        System.out.print("Code du compte : ");
                        int numero = sc.nextInt();
                        System.out.print("Montant a retirer : ");
                        double montant = sc.nextDouble();
                        System.out.println(proxy.retirer(numero, montant));
                        break;
                    }
                    case 7: {
                        System.out.print("Code du compte source : ");
                        int source = sc.nextInt();
                        System.out.print("Code du compte destination : ");
                        int destination = sc.nextInt();
                        System.out.print("Montant du virement : ");
                        double montant = sc.nextDouble();
                        System.out.println(proxy.virement(source, destination, montant));
                        break;
                    }
                    case 8: {
                        System.out.print("Code du compte a supprimer : ");
                        int numero = sc.nextInt();
                        System.out.println(proxy.supprimerCompte(numero));
                        break;
                    }
                    case 0:
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Saisie invalide : veuillez entrer un nombre.");
                sc.nextLine();
            } catch (WebServiceException e) {
                System.out.println("Erreur SOAP : " + e.getMessage());
            } catch (java.util.NoSuchElementException e) {
                break;
            }
        } while (choix != 0);

        sc.close();
    }
}
