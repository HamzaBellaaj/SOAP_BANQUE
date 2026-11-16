USE banque_db;

-- Conserver les numeros existants et generer ceux des nouveaux comptes.
ALTER TABLE compte_bancaire
    MODIFY numCompte INT NOT NULL AUTO_INCREMENT;
