# banq. — Interface Angular

Voir le [README du projet](../README.md) pour le demarrage complet avec les
quatre consommateurs SOAP.

```powershell
npm install
npm start
```

L'interface est disponible sur http://127.0.0.1:4200.
Les API Java, Python, PHP et JavaScript doivent etre lancees sur les ports
8101 a 8104. Le fichier `proxy.conf.json` dirige les requetes vers chaque API.

Pour compiler l'application : `npm run build`.
La sortie se trouve dans `dist/banque-angular/browser`.

Les composants restent simples :

- `src/app/app.ts` : donnees affichees et actions utilisateur ;
- `src/app/app.html` : ecran, tableaux et formulaires ;
- `src/app/app.css` : presentation et adaptations mobiles ;
- `src/app/banque.service.ts` : appels HTTP aux consommateurs ;
- `src/app/icon.ts` : icones SVG ;
- `src/styles.css` : styles communs et typographie.

Les donnees des comptes viennent du service SOAP. Les comptes et le formulaire
d'operation sont affiches sur la meme page.
