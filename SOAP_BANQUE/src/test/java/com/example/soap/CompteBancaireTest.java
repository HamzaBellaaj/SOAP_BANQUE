package com.example.soap;

import com.example.soap.entities.CompteBancaire;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompteBancaireTest {

    @Test
    public void inclureNumeroCompteDansLeXml() throws Exception {
        CompteBancaire compte = new CompteBancaire(12, 100, "2026-11-14");
        JAXBContext context = JAXBContext.newInstance(CompteBancaire.class);
        StringWriter xml = new StringWriter();

        context.createMarshaller().marshal(
            new JAXBElement<>(new QName("compte"), CompteBancaire.class, compte), xml);

        assertTrue(xml.toString().contains("<numCompte>12</numCompte>"));
    }
}
