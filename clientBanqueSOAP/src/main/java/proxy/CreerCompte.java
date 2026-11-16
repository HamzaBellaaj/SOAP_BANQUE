
package proxy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour creerCompte complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="creerCompte">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="soldeInitial" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creerCompte", propOrder = {
    "soldeInitial"
})
public class CreerCompte {

    protected double soldeInitial;

    /**
     * Obtient la valeur de la propriété soldeInitial.
     * 
     */
    public double getSoldeInitial() {
        return soldeInitial;
    }

    /**
     * Définit la valeur de la propriété soldeInitial.
     * 
     */
    public void setSoldeInitial(double value) {
        this.soldeInitial = value;
    }

}
