package com.example.soap.metier;

import com.example.soap.entities.CompteBancaire;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompteBancaireMetier {
    
    private static final String URL = "jdbc:mysql://localhost:3306/banque_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public CompteBancaireMetier(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public double conversion(double mt){
        return mt * 3.5;
    } 
    
    public CompteBancaire getCompte(int numCompte){
        CompteBancaire compte = null;
        String query = "SELECT numCompte, solde, dateCreation FROM compte_bancaire WHERE numCompte = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, numCompte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    compte = new CompteBancaire(rs.getInt("numCompte"),rs.getDouble("solde"),rs.getString("dateCreation")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compte;
    }
    
    public List<CompteBancaire> getCompteBancaires(){
        List<CompteBancaire> comptes = new ArrayList<>();
        String query = "SELECT numCompte, solde, dateCreation FROM compte_bancaire";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                comptes.add(new CompteBancaire(rs.getInt("numCompte"),rs.getDouble("solde"),rs.getString("dateCreation")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }

    public CompteBancaire creerCompte(double soldeInitial) {
        if (!Double.isFinite(soldeInitial) || soldeInitial < 0) {
            throw new IllegalArgumentException("Le solde initial doit etre positif ou nul.");
        }

        String query = "INSERT INTO compte_bancaire (solde, dateCreation) VALUES (?, ?)";
        String dateCreation = LocalDate.now().toString();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, soldeInitial);
            stmt.setString(2, dateCreation);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new CompteBancaire(rs.getInt(1), soldeInitial, dateCreation);
                }
            }
            throw new IllegalStateException("Le numero du compte n'a pas ete genere.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Impossible de creer le compte.", e);
        }
    }
        public String verser(int code, double montant) {
        if (!Double.isFinite(montant) || montant <= 0) {
            throw new IllegalArgumentException("Le montant doit etre strictement positif.");
        }

        String query = "UPDATE compte_bancaire SET solde = solde + ? WHERE numCompte = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, montant);
            stmt.setInt(2, code);
            if (stmt.executeUpdate() == 0) {
                throw new IllegalArgumentException("Compte introuvable.");
            }
            return "Versement effectue avec succes.";
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible d'effectuer le versement.", e);
        }
    }

    public String retirer(int code, double montant) {
        if (!Double.isFinite(montant) || montant <= 0) {
            throw new IllegalArgumentException("Le montant doit etre strictement positif.");
        }

        String query = "UPDATE compte_bancaire SET solde = solde - ? WHERE numCompte = ? AND solde >= ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, montant);
            stmt.setInt(2, code);
            stmt.setDouble(3, montant);
            if (stmt.executeUpdate() == 0) {
                throw new IllegalArgumentException("Compte introuvable ou solde insuffisant.");
            }
            return "Retrait effectue avec succes.";
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible d'effectuer le retrait.", e);
        }
    }

    public String virement(int codeSource, int codeDestination, double montant) {
        if (!Double.isFinite(montant) || montant <= 0) {
            throw new IllegalArgumentException("Le montant doit etre strictement positif.");
        }
        if (codeSource == codeDestination) {
            throw new IllegalArgumentException("Les deux comptes doivent etre differents.");
        }

        String debit = "UPDATE compte_bancaire SET solde = solde - ? WHERE numCompte = ? AND solde >= ?";
        String credit = "UPDATE compte_bancaire SET solde = solde + ? WHERE numCompte = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtDebit = conn.prepareStatement(debit);
                 PreparedStatement stmtCredit = conn.prepareStatement(credit)) {

                stmtDebit.setDouble(1, montant);
                stmtDebit.setInt(2, codeSource);
                stmtDebit.setDouble(3, montant);
                if (stmtDebit.executeUpdate() == 0) {
                    throw new IllegalArgumentException("Compte source introuvable ou solde insuffisant.");
                }

                stmtCredit.setDouble(1, montant);
                stmtCredit.setInt(2, codeDestination);
                if (stmtCredit.executeUpdate() == 0) {
                    throw new IllegalArgumentException("Compte destination introuvable.");
                }

                conn.commit();
                return "Virement effectue avec succes.";
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible d'effectuer le virement.", e);
        }
    }

    public String supprimerCompte(int code) {
        String query = "DELETE FROM compte_bancaire WHERE numCompte = ? AND solde = 0";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, code);
            if (stmt.executeUpdate() == 0) {
                throw new IllegalArgumentException("Compte introuvable ou solde non nul.");
            }
            return "Compte supprime avec succes.";
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible de supprimer le compte.", e);
        }
    }
}
