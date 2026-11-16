package com.example.soap.webservices;

import com.example.soap.entities.CompteBancaire;
import com.example.soap.metier.CompteBancaireMetier;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(serviceName = "BanqueWS")
public class CompteBancaireWS {

    private CompteBancaireMetier metier = new CompteBancaireMetier();

    @WebMethod
    public double conversion(@WebParam(name = "montant") double mt) {
        return metier.conversion(mt);
    }

    @WebMethod
    public CompteBancaire getCompte(@WebParam(name = "code") int code) {
        return metier.getCompte(code);
    }

    @WebMethod
    public List<CompteBancaire> listComptes() {
        return metier.getCompteBancaires();
    }

    @WebMethod
    public CompteBancaire creerCompte(@WebParam(name = "soldeInitial") double soldeInitial) {
        return metier.creerCompte(soldeInitial);
    }

    @WebMethod
    public String verser(@WebParam(name = "code") int code,@WebParam(name = "montant") double montant) {
        return metier.verser(code, montant);
    }

    @WebMethod
    public String retirer(@WebParam(name = "code") int code,@WebParam(name = "montant") double montant) {
        return metier.retirer(code, montant);
    }

    @WebMethod
    public String virement(@WebParam(name = "codeSource") int codeSource,@WebParam(name = "codeDestination") int codeDestination,@WebParam(name = "montant") double montant) {
        return metier.virement(codeSource, codeDestination, montant);
    }

    @WebMethod
    public String supprimerCompte(@WebParam(name = "code") int code) {
        return metier.supprimerCompte(code);
    }
}
