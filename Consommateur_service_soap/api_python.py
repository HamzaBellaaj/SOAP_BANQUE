import json
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer

from requests import Session
from zeep import Client
from zeep.helpers import serialize_object
from zeep.transports import Transport

OPERATIONS = {
    "conversion", "getCompte", "listComptes", "creerCompte",
    "verser", "retirer", "virement", "supprimerCompte"
}


def creer_client():
    return Client("http://127.0.0.1:8005/BanqueWS?wsdl",
                  transport=Transport(session=Session(), timeout=5, operation_timeout=8))


class BanqueAPI(BaseHTTPRequestHandler):
    def repondre(self, statut, data):
        contenu = json.dumps(data, ensure_ascii=False).encode("utf-8")
        self.send_response(statut)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(contenu)))
        self.end_headers()
        self.wfile.write(contenu)

    def do_GET(self):
        if self.path != "/api/python/health":
            self.repondre(404, {"success": False, "message": "Route introuvable."})
            return
        try:
            creer_client().service.conversion(0)
            self.repondre(200, {"success": True, "consumer": "Python"})
        except Exception as e:
            self.repondre(502, {"success": False, "message": str(e)})

    def do_POST(self):
        if self.path != "/api/python/execute":
            self.repondre(404, {"success": False, "message": "Route introuvable."})
            return
        try:
            longueur = int(self.headers.get("Content-Length", "0"))
            if longueur <= 0 or longueur > 65536:
                raise ValueError("Requete invalide.")
            requete = json.loads(self.rfile.read(longueur))
            operation = requete.get("operation")
            params = requete.get("params", {})
            if not isinstance(operation, str) or operation not in OPERATIONS or not isinstance(params, dict):
                raise ValueError("Operation invalide.")
        except (ValueError, AttributeError) as e:
            self.repondre(400, {"success": False, "message": str(e)})
            return

        try:
            resultat = getattr(creer_client().service, operation)(**params)
            if operation == "listComptes" and resultat is None:
                resultat = []
            self.repondre(200, {"success": True, "consumer": "Python",
                               "data": serialize_object(resultat)})
        except Exception as e:
            self.repondre(502, {"success": False, "message": str(e)})


if __name__ == "__main__":
    print("API Python : http://127.0.0.1:8102", flush=True)
    ThreadingHTTPServer(("127.0.0.1", 8102), BanqueAPI).serve_forever()
