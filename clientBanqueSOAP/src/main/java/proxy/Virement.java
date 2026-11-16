
package proxy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour virement complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>{@code
 * <complexType name="virement">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codeSource" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="codeDestination" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="montant" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "virement", propOrder = {
    "codeSource",
    "codeDestination",
    "montant"
})
public class Virement {

    protected int codeSource;
    protected int codeDestination;
    protected double montant;

    /**
     * Obtient la valeur de la propriété codeSource.
     * 
     */
    public int getCodeSource() {
        return codeSource;
    }

    /**
     * Définit la valeur de la propriété codeSource.
     * 
     */
    public void setCodeSource(int value) {
        this.codeSource = value;
    }

    /**
     * Obtient la valeur de la propriété codeDestination.
     * 
     */
    public int getCodeDestination() {
        return codeDestination;
    }

    /**
     * Définit la valeur de la propriété codeDestination.
     * 
     */
    public void setCodeDestination(int value) {
        this.codeDestination = value;
    }

    /**
     * Obtient la valeur de la propriété montant.
     * 
     */
    public double getMontant() {
        return montant;
    }

    /**
     * Définit la valeur de la propriété montant.
     * 
     */
    public void setMontant(double value) {
        this.montant = value;
    }

}
