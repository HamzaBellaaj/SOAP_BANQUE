package com.example.soap;
import javax.xml.ws.Endpoint;
import com.example.soap.webservices.CompteBancaireWS;

public class App {
    public static void main(String[] args) {
        String url = "http://localhost:8005/BanqueWS";
        Endpoint.publish(url, new CompteBancaireWS());
        System.out.println("Service Web SOAP déployé sur " + url);
    }
}
