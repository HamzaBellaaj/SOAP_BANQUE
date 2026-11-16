<?php
header('Content-Type: application/json; charset=utf-8');
ini_set('default_socket_timeout', '8');

function repondre($statut, $contenu) {
    http_response_code($statut);
    echo json_encode($contenu, JSON_UNESCAPED_UNICODE | JSON_THROW_ON_ERROR);
}

$route = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$health = $_SERVER['REQUEST_METHOD'] === 'GET' && $route === '/api/php/health';
$execute = $_SERVER['REQUEST_METHOD'] === 'POST' && $route === '/api/php/execute';
if (!$health && !$execute) {
    repondre(404, ['success' => false, 'message' => 'Route introuvable.']);
    return;
}

$operations = ['conversion', 'getCompte', 'listComptes', 'creerCompte',
               'verser', 'retirer', 'virement', 'supprimerCompte'];

try {
    if ($execute) {
        $requete = json_decode(file_get_contents('php://input'), true, 32, JSON_THROW_ON_ERROR);
        $operation = $requete['operation'] ?? '';
        $params = $requete['params'] ?? [];
        if (!in_array($operation, $operations, true) || !is_array($params)) {
            throw new InvalidArgumentException('Operation invalide.');
        }
    }

    $client = new SoapClient('http://127.0.0.1:8005/BanqueWS?wsdl', [
        'exceptions' => true,
        'cache_wsdl' => WSDL_CACHE_NONE,
        'connection_timeout' => 5
    ]);

    if ($health) {
        $client->conversion(['montant' => 0]);
        repondre(200, ['success' => true, 'consumer' => 'PHP']);
        return;
    }

    $res = $client->__soapCall($operation, [$params]);
    $data = $res->return ?? null;
    if ($operation === 'listComptes') {
        $data = $data === null ? [] : (is_array($data) ? $data : [$data]);
    }
    repondre(200, ['success' => true, 'consumer' => 'PHP', 'data' => $data]);
} catch (JsonException | InvalidArgumentException $e) {
    repondre(400, ['success' => false, 'message' => $e->getMessage()]);
} catch (SoapFault $e) {
    repondre(502, ['success' => false, 'message' => $e->getMessage()]);
}
