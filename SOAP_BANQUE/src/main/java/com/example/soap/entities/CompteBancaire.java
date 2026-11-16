package com.example.soap.entities;

public class CompteBancaire {
    private int numCompte;
    private double solde;
    private String dateCreation;
    
    public CompteBancaire() {
    }

    public CompteBancaire(int numCompte, double solde, String dateCreation) {
        this.numCompte = numCompte;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }

    public int getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    
}
