from zeep import Client
from zeep.exceptions import Fault
from requests.exceptions import RequestException


def afficher_compte(compte):
    if compte is None:
        print("Compte introuvable.")
    else:
        print("Numero :", compte.numCompte,
              "| Solde :", compte.solde,
              "| Date creation :", compte.dateCreation)


def main():
    try:
        client = Client("http://localhost:8005/BanqueWS?wsdl")
    except (Fault, RequestException) as e:
        print("Connexion au service SOAP impossible :", e)
        return

    while True:
        print("\n===== MENU BANQUE - PYTHON =====")
        print("1. Consulter un compte")
        print("2. Faire une conversion")
        print("3. Lister les comptes")
        print("4. Creer un compte")
        print("5. Verser de l'argent")
        print("6. Retirer de l'argent")
        print("7. Faire un virement")
        print("8. Supprimer un compte")
        print("0. Quitter")

        try:
            choix = input("Veuillez choisir une option : ").strip()
            if choix == "0":
                print("Au revoir !")
                break
            elif choix == "1":
                code = int(input("Code du compte : "))
                afficher_compte(client.service.getCompte(code))
            elif choix == "2":
                montant = float(input("Montant a convertir : "))
                print("Conversion :", client.service.conversion(montant))
            elif choix == "3":
                comptes = client.service.listComptes()
                if not comptes:
                    print("Aucun compte.")
                else:
                    for compte in comptes:
                        afficher_compte(compte)
            elif choix == "4":
                solde = float(input("Solde initial : "))
                afficher_compte(client.service.creerCompte(solde))
            elif choix == "5":
                code = int(input("Code du compte : "))
                montant = float(input("Montant a verser : "))
                print(client.service.verser(code, montant))
            elif choix == "6":
                code = int(input("Code du compte : "))
                montant = float(input("Montant a retirer : "))
                print(client.service.retirer(code, montant))
            elif choix == "7":
                source = int(input("Code du compte source : "))
                destination = int(input("Code du compte destination : "))
                montant = float(input("Montant du virement : "))
                print(client.service.virement(source, destination, montant))
            elif choix == "8":
                code = int(input("Code du compte a supprimer : "))
                print(client.service.supprimerCompte(code))
            else:
                print("Choix invalide.")
        except ValueError:
            print("Saisie invalide : veuillez entrer un nombre.")
        except Fault as e:
            print("Erreur SOAP :", e)
        except RequestException as e:
            print("Erreur de connexion :", e)
        except (EOFError, KeyboardInterrupt):
            print("\nAu revoir !")
            break


if __name__ == "__main__":
    main()
