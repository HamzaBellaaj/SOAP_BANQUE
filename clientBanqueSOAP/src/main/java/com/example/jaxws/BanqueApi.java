package com.example.jaxws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import jakarta.xml.ws.BindingProvider;
import proxy.BanqueWS;
import proxy.CompteBancaireWS;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class BanqueApi {
    private static final ObjectMapper json = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        HttpServer serveur = HttpServer.create(new InetSocketAddress("127.0.0.1", 8101), 0);
        serveur.createContext("/api/java", BanqueApi::traiter);
        serveur.setExecutor(Executors.newFixedThreadPool(4));
        serveur.start();
        System.out.println("API Java : http://127.0.0.1:8101");
    }

    private static void traiter(HttpExchange demande) throws IOException {
        String route = demande.getRequestURI().getPath();
        boolean health = demande.getRequestMethod().equals("GET") && route.equals("/api/java/health");
        boolean execute = demande.getRequestMethod().equals("POST") && route.equals("/api/java/execute");
        if (!health && !execute) {
            repondre(demande, 404, false, "Route introuvable.");
            return;
        }

        try {
            CompteBancaireWS client = new BanqueWS(new java.net.URL("http://127.0.0.1:8005/BanqueWS?wsdl")).getCompteBancaireWSPort();
            BindingProvider configuration = (BindingProvider) client;
            configuration.getRequestContext().put("com.sun.xml.ws.connect.timeout", 5000);
            configuration.getRequestContext().put("com.sun.xml.ws.request.timeout", 8000);
            if (health) {
                client.conversion(0);
                repondre(demande, 200, true, null);
                return;
            }

            JsonNode requete = json.readTree(demande.getRequestBody());
            if (requete == null) throw new IllegalArgumentException("Requete invalide.");
            String operation = requete.path("operation").asText();
            JsonNode params = requete.path("params");
            Object resultat;
            switch (operation) {
                case "conversion":
                    resultat = client.conversion(montant(params, "montant"));
                    break;
                case "getCompte":
                    resultat = client.getCompte(code(params, "code"));
                    break;
                case "listComptes":
                    resultat = client.listComptes();
                    break;
                case "creerCompte":
                    resultat = client.creerCompte(montant(params, "soldeInitial"));
                    break;
                case "verser":
                    resultat = client.verser(code(params, "code"), montant(params, "montant"));
                    break;
                case "retirer":
                    resultat = client.retirer(code(params, "code"), montant(params, "montant"));
                    break;
                case "virement":
                    resultat = client.virement(code(params, "codeSource"), code(params, "codeDestination"), montant(params, "montant"));
                    break;
                case "supprimerCompte":
                    resultat = client.supprimerCompte(code(params, "code"));
                    break;
                default:
                    throw new IllegalArgumentException("Operation invalide.");
            }
            repondre(demande, 200, true, resultat);
        } catch (jakarta.xml.ws.soap.SOAPFaultException e) {
            repondre(demande, 502, false, e.getFault().getFaultString());
        } catch (IllegalArgumentException | com.fasterxml.jackson.core.JsonProcessingException e) {
            repondre(demande, 400, false, e.getMessage());
        } catch (Exception e) {
            repondre(demande, 502, false, e.getMessage());
        }
    }

    private static int code(JsonNode params, String nom) {
        JsonNode valeur = params.path(nom);
        if (!valeur.isIntegralNumber() || !valeur.canConvertToInt()) {
            throw new IllegalArgumentException("Le code doit etre un entier.");
        }
        return valeur.asInt();
    }

    private static double montant(JsonNode params, String nom) {
        JsonNode valeur = params.path(nom);
        if (!valeur.isNumber() || !Double.isFinite(valeur.asDouble())) {
            throw new IllegalArgumentException("Le montant doit etre un nombre.");
        }
        return valeur.asDouble();
    }

    private static void repondre(HttpExchange demande, int statut, boolean succes, Object data) throws IOException {
        Map<String, Object> reponse = new LinkedHashMap<>();
        reponse.put("success", succes);
        reponse.put("consumer", "Java");
        reponse.put(succes ? "data" : "message", data);
        byte[] contenu = json.writeValueAsBytes(reponse);
        demande.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        demande.sendResponseHeaders(statut, contenu.length);
        demande.getResponseBody().write(contenu);
        demande.close();
    }
}
