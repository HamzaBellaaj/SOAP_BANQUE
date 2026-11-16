package com.example.soap;

import com.example.soap.metier.CompteBancaireMetier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompteBancaireMetierTest {

    private CompteBancaireMetier metier = new CompteBancaireMetier();

    @Test
    public void refuserSoldeInitialInvalide() {
        assertThrows(IllegalArgumentException.class, () -> metier.creerCompte(-10));
        assertThrows(IllegalArgumentException.class, () -> metier.creerCompte(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> metier.creerCompte(Double.POSITIVE_INFINITY));
    }

    @Test
    public void refuserMontantsInvalides() {
        double[] montants = {0, -10, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        for (double montant : montants) {
            assertThrows(IllegalArgumentException.class, () -> metier.verser(1, montant));
            assertThrows(IllegalArgumentException.class, () -> metier.retirer(1, montant));
            assertThrows(IllegalArgumentException.class, () -> metier.virement(1, 2, montant));
        }
    }

    @Test
    public void refuserVirementVersLeMemeCompte() {
        assertThrows(IllegalArgumentException.class, () -> metier.virement(1, 1, 50));
    }
}
