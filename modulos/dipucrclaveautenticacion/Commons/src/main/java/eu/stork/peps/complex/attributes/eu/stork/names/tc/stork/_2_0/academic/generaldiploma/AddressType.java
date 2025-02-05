//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.12 at 12:22:50 PM GMT 
//
package eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for AddressType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="AddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Line" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" maxOccurs="unbounded"/>
 *         &lt;element name="City" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" minOccurs="0"/>
 *         &lt;element name="StateOrRegion" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" minOccurs="0"/>
 *         &lt;element name="Country" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}CountryTextCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", propOrder = {
    "line",
    "city",
    "stateOrRegion",
    "postalCode",
    "country"
})
public class AddressType {

    @XmlElement(name = "Line", required = true)
    protected List<String> line;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "StateOrRegion")
    protected String stateOrRegion;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "Country")
    protected CountryTextCodeType country;

    /**
     * Gets the value of the line property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the line property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLine().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     */
    public List<String> getLine() {
        if (line == null) {
            line = new ArrayList<String>();
        }
        return this.line;
    }

    /**
     * Gets the value of the city property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the stateOrRegion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getStateOrRegion() {
        return stateOrRegion;
    }

    /**
     * Sets the value of the stateOrRegion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setStateOrRegion(String value) {
        this.stateOrRegion = value;
    }

    /**
     * Gets the value of the postalCode property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the country property.
     *
     * @return possible object is {@link CountryTextCodeType }
     *
     */
    public CountryTextCodeType getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value allowed object is {@link CountryTextCodeType }
     *
     */
    public void setCountry(CountryTextCodeType value) {
        this.country = value;
    }

}
