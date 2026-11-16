const http = require('node:http');
const soap = require('soap');

const operations = ['conversion', 'getCompte', 'listComptes', 'creerCompte',
    'verser', 'retirer', 'virement', 'supprimerCompte'];

function repondre(res, statut, data) {
    res.writeHead(statut, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify(data));
}

http.createServer(async (req, res) => {
    const health = req.method === 'GET' && req.url === '/api/js/health';
    const execute = req.method === 'POST' && req.url === '/api/js/execute';
    if (!health && !execute) {
        repondre(res, 404, { success: false, message: 'Route introuvable.' });
        return;
    }

    let operation;
    let params;
    if (execute) {
        try {
            let body = '';
            for await (const morceau of req) {
                body += morceau;
                if (body.length > 65536) throw new Error('Requete trop grande.');
            }
            const requete = JSON.parse(body);
            operation = requete.operation;
            params = requete.params || {};
            if (!operations.includes(operation) || typeof params !== 'object' || Array.isArray(params)) {
                throw new Error('Operation invalide.');
            }
        } catch (err) {
            repondre(res, 400, { success: false, message: err.message });
            return;
        }
    }

    try {
        const client = await soap.createClientAsync('http://127.0.0.1:8005/BanqueWS?wsdl', {
            wsdl_options: { timeout: 5000 }
        });
        if (health) {
            await client.conversionAsync({ montant: 0 }, { timeout: 8000 });
            repondre(res, 200, { success: true, consumer: 'JavaScript' });
            return;
        }
        const [resultat] = await client[operation + 'Async'](params, { timeout: 8000 });
        let data = resultat ? resultat.return : null;
        if (operation === 'listComptes') data = data || [];
        repondre(res, 200, { success: true, consumer: 'JavaScript', data });
    } catch (err) {
        repondre(res, 502, { success: false, message: err.message });
    }
}).listen(8104, '127.0.0.1', () => {
    console.log('API JavaScript : http://127.0.0.1:8104');
});
