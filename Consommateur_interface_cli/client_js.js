const soap = require('soap');
const readline = require('node:readline/promises');

const url = 'http://localhost:8005/BanqueWS?wsdl';

function afficherCompte(compte) {
    if (!compte) {
        console.log('Compte introuvable.');
    } else {
        console.log('Numero :', compte.numCompte,
            '| Solde :', compte.solde, '| Date creation :', compte.dateCreation);
    }
}

async function lireNombre(rl, message, entier = false) {
    const saisie = (await rl.question(message)).trim();
    const nombre = Number(saisie);
    if (saisie === '' || !Number.isFinite(nombre) || (entier && !Number.isInteger(nombre))) {
        throw new Error(entier ? 'Le code doit etre un entier.' : 'Veuillez entrer un nombre.');
    }
    return nombre;
}

async function main() {
    let rl;
    try {
        const client = await soap.createClientAsync(url);
        rl = readline.createInterface({ input: process.stdin, output: process.stdout });
        let choix;

        do {
            console.log('\n===== MENU BANQUE - JAVASCRIPT =====');
            console.log('1. Consulter un compte');
            console.log('2. Faire une conversion');
            console.log('3. Lister les comptes');
            console.log('4. Creer un compte');
            console.log("5. Verser de l'argent");
            console.log("6. Retirer de l'argent");
            console.log('7. Faire un virement');
            console.log('8. Supprimer un compte');
            console.log('0. Quitter');
            choix = (await rl.question('Veuillez choisir une option : ')).trim();

            try {
                switch (choix) {
                    case '1': {
                        const code = await lireNombre(rl, 'Code du compte : ', true);
                        const [res] = await client.getCompteAsync({ code });
                        afficherCompte(res ? res.return : null);
                        break;
                    }
                    case '2': {
                        const montant = await lireNombre(rl, 'Montant a convertir : ');
                        const [res] = await client.conversionAsync({ montant });
                        console.log('Conversion :', res.return);
                        break;
                    }
                    case '3': {
                        const [res] = await client.listComptesAsync({});
                        const comptes = res ? (res.return || []) : [];
                        if (comptes.length === 0) console.log('Aucun compte.');
                        for (const compte of comptes) afficherCompte(compte);
                        break;
                    }
                    case '4': {
                        const soldeInitial = await lireNombre(rl, 'Solde initial : ');
                        const [res] = await client.creerCompteAsync({ soldeInitial });
                        afficherCompte(res.return);
                        break;
                    }
                    case '5': {
                        const code = await lireNombre(rl, 'Code du compte : ', true);
                        const montant = await lireNombre(rl, 'Montant a verser : ');
                        const [res] = await client.verserAsync({ code, montant });
                        console.log(res.return);
                        break;
                    }
                    case '6': {
                        const code = await lireNombre(rl, 'Code du compte : ', true);
                        const montant = await lireNombre(rl, 'Montant a retirer : ');
                        const [res] = await client.retirerAsync({ code, montant });
                        console.log(res.return);
                        break;
                    }
                    case '7': {
                        const codeSource = await lireNombre(rl, 'Code du compte source : ', true);
                        const codeDestination = await lireNombre(rl, 'Code du compte destination : ', true);
                        const montant = await lireNombre(rl, 'Montant du virement : ');
                        const [res] = await client.virementAsync({ codeSource, codeDestination, montant });
                        console.log(res.return);
                        break;
                    }
                    case '8': {
                        const code = await lireNombre(rl, 'Code du compte a supprimer : ', true);
                        const [res] = await client.supprimerCompteAsync({ code });
                        console.log(res.return);
                        break;
                    }
                    case '0':
                        console.log('Au revoir !');
                        break;
                    default:
                        console.log('Choix invalide.');
                }
            } catch (err) {
                if (rl.closed) break;
                console.error('Erreur :', err.message);
            }
        } while (choix !== '0' && !rl.closed);
    } catch (err) {
        if (!rl || !rl.closed) console.error('Erreur :', err.message);
    } finally {
        if (rl) rl.close();
    }
}

main();
