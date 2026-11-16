
package proxy;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the proxy package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Conversion_QNAME = new QName("http://webservices.soap.example.com/", "conversion");
    private static final QName _ConversionResponse_QNAME = new QName("http://webservices.soap.example.com/", "conversionResponse");
    private static final QName _CreerCompte_QNAME = new QName("http://webservices.soap.example.com/", "creerCompte");
    private static final QName _CreerCompteResponse_QNAME = new QName("http://webservices.soap.example.com/", "creerCompteResponse");
    private static final QName _GetCompte_QNAME = new QName("http://webservices.soap.example.com/", "getCompte");
    private static final QName _GetCompteResponse_QNAME = new QName("http://webservices.soap.example.com/", "getCompteResponse");
    private static final QName _ListComptes_QNAME = new QName("http://webservices.soap.example.com/", "listComptes");
    private static final QName _ListComptesResponse_QNAME = new QName("http://webservices.soap.example.com/", "listComptesResponse");
    private static final QName _Retirer_QNAME = new QName("http://webservices.soap.example.com/", "retirer");
    private static final QName _RetirerResponse_QNAME = new QName("http://webservices.soap.example.com/", "retirerResponse");
    private static final QName _SupprimerCompte_QNAME = new QName("http://webservices.soap.example.com/", "supprimerCompte");
    private static final QName _SupprimerCompteResponse_QNAME = new QName("http://webservices.soap.example.com/", "supprimerCompteResponse");
    private static final QName _Verser_QNAME = new QName("http://webservices.soap.example.com/", "verser");
    private static final QName _VerserResponse_QNAME = new QName("http://webservices.soap.example.com/", "verserResponse");
    private static final QName _Virement_QNAME = new QName("http://webservices.soap.example.com/", "virement");
    private static final QName _VirementResponse_QNAME = new QName("http://webservices.soap.example.com/", "virementResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: proxy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Conversion }
     * 
     * @return
     *     the new instance of {@link Conversion }
     */
    public Conversion createConversion() {
        return new Conversion();
    }

    /**
     * Create an instance of {@link ConversionResponse }
     * 
     * @return
     *     the new instance of {@link ConversionResponse }
     */
    public ConversionResponse createConversionResponse() {
        return new ConversionResponse();
    }

    /**
     * Create an instance of {@link CreerCompte }
     * 
     * @return
     *     the new instance of {@link CreerCompte }
     */
    public CreerCompte createCreerCompte() {
        return new CreerCompte();
    }

    /**
     * Create an instance of {@link CreerCompteResponse }
     * 
     * @return
     *     the new instance of {@link CreerCompteResponse }
     */
    public CreerCompteResponse createCreerCompteResponse() {
        return new CreerCompteResponse();
    }

    /**
     * Create an instance of {@link GetCompte }
     * 
     * @return
     *     the new instance of {@link GetCompte }
     */
    public GetCompte createGetCompte() {
        return new GetCompte();
    }

    /**
     * Create an instance of {@link GetCompteResponse }
     * 
     * @return
     *     the new instance of {@link GetCompteResponse }
     */
    public GetCompteResponse createGetCompteResponse() {
        return new GetCompteResponse();
    }

    /**
     * Create an instance of {@link ListComptes }
     * 
     * @return
     *     the new instance of {@link ListComptes }
     */
    public ListComptes createListComptes() {
        return new ListComptes();
    }

    /**
     * Create an instance of {@link ListComptesResponse }
     * 
     * @return
     *     the new instance of {@link ListComptesResponse }
     */
    public ListComptesResponse createListComptesResponse() {
        return new ListComptesResponse();
    }

    /**
     * Create an instance of {@link Retirer }
     * 
     * @return
     *     the new instance of {@link Retirer }
     */
    public Retirer createRetirer() {
        return new Retirer();
    }

    /**
     * Create an instance of {@link RetirerResponse }
     * 
     * @return
     *     the new instance of {@link RetirerResponse }
     */
    public RetirerResponse createRetirerResponse() {
        return new RetirerResponse();
    }

    /**
     * Create an instance of {@link SupprimerCompte }
     * 
     * @return
     *     the new instance of {@link SupprimerCompte }
     */
    public SupprimerCompte createSupprimerCompte() {
        return new SupprimerCompte();
    }

    /**
     * Create an instance of {@link SupprimerCompteResponse }
     * 
     * @return
     *     the new instance of {@link SupprimerCompteResponse }
     */
    public SupprimerCompteResponse createSupprimerCompteResponse() {
        return new SupprimerCompteResponse();
    }

    /**
     * Create an instance of {@link Verser }
     * 
     * @return
     *     the new instance of {@link Verser }
     */
    public Verser createVerser() {
        return new Verser();
    }

    /**
     * Create an instance of {@link VerserResponse }
     * 
     * @return
     *     the new instance of {@link VerserResponse }
     */
    public VerserResponse createVerserResponse() {
        return new VerserResponse();
    }

    /**
     * Create an instance of {@link Virement }
     * 
     * @return
     *     the new instance of {@link Virement }
     */
    public Virement createVirement() {
        return new Virement();
    }

    /**
     * Create an instance of {@link VirementResponse }
     * 
     * @return
     *     the new instance of {@link VirementResponse }
     */
    public VirementResponse createVirementResponse() {
        return new VirementResponse();
    }

    /**
     * Create an instance of {@link CompteBancaire }
     * 
     * @return
     *     the new instance of {@link CompteBancaire }
     */
    public CompteBancaire createCompteBancaire() {
        return new CompteBancaire();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Conversion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Conversion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "conversion")
    public JAXBElement<Conversion> createConversion(Conversion value) {
        return new JAXBElement<>(_Conversion_QNAME, Conversion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConversionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "conversionResponse")
    public JAXBElement<ConversionResponse> createConversionResponse(ConversionResponse value) {
        return new JAXBElement<>(_ConversionResponse_QNAME, ConversionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreerCompte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreerCompte }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "creerCompte")
    public JAXBElement<CreerCompte> createCreerCompte(CreerCompte value) {
        return new JAXBElement<>(_CreerCompte_QNAME, CreerCompte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreerCompteResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreerCompteResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "creerCompteResponse")
    public JAXBElement<CreerCompteResponse> createCreerCompteResponse(CreerCompteResponse value) {
        return new JAXBElement<>(_CreerCompteResponse_QNAME, CreerCompteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCompte }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "getCompte")
    public JAXBElement<GetCompte> createGetCompte(GetCompte value) {
        return new JAXBElement<>(_GetCompte_QNAME, GetCompte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompteResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCompteResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "getCompteResponse")
    public JAXBElement<GetCompteResponse> createGetCompteResponse(GetCompteResponse value) {
        return new JAXBElement<>(_GetCompteResponse_QNAME, GetCompteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListComptes }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListComptes }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "listComptes")
    public JAXBElement<ListComptes> createListComptes(ListComptes value) {
        return new JAXBElement<>(_ListComptes_QNAME, ListComptes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListComptesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListComptesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "listComptesResponse")
    public JAXBElement<ListComptesResponse> createListComptesResponse(ListComptesResponse value) {
        return new JAXBElement<>(_ListComptesResponse_QNAME, ListComptesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Retirer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Retirer }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "retirer")
    public JAXBElement<Retirer> createRetirer(Retirer value) {
        return new JAXBElement<>(_Retirer_QNAME, Retirer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetirerResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetirerResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "retirerResponse")
    public JAXBElement<RetirerResponse> createRetirerResponse(RetirerResponse value) {
        return new JAXBElement<>(_RetirerResponse_QNAME, RetirerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SupprimerCompte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SupprimerCompte }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "supprimerCompte")
    public JAXBElement<SupprimerCompte> createSupprimerCompte(SupprimerCompte value) {
        return new JAXBElement<>(_SupprimerCompte_QNAME, SupprimerCompte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SupprimerCompteResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SupprimerCompteResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "supprimerCompteResponse")
    public JAXBElement<SupprimerCompteResponse> createSupprimerCompteResponse(SupprimerCompteResponse value) {
        return new JAXBElement<>(_SupprimerCompteResponse_QNAME, SupprimerCompteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Verser }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Verser }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "verser")
    public JAXBElement<Verser> createVerser(Verser value) {
        return new JAXBElement<>(_Verser_QNAME, Verser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerserResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VerserResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "verserResponse")
    public JAXBElement<VerserResponse> createVerserResponse(VerserResponse value) {
        return new JAXBElement<>(_VerserResponse_QNAME, VerserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Virement }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Virement }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "virement")
    public JAXBElement<Virement> createVirement(Virement value) {
        return new JAXBElement<>(_Virement_QNAME, Virement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VirementResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VirementResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices.soap.example.com/", name = "virementResponse")
    public JAXBElement<VirementResponse> createVirementResponse(VirementResponse value) {
        return new JAXBElement<>(_VirementResponse_QNAME, VirementResponse.class, null, value);
    }

}
