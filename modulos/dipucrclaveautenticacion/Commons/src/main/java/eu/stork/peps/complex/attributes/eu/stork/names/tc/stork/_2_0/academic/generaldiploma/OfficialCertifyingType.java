//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.12 at 12:22:50 PM GMT 
//
package eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for OfficialCertifyingType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="OfficialCertifyingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FamilyName" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}FamilyNameType"/>
 *         &lt;element name="GivenName" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}GivenNameType"/>
 *         &lt;element name="Capacity" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="awardingInstitutionID" type="{http://www.w3.org/2001/XMLSchema}string" default="DEF-IAW" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfficialCertifyingType", propOrder = {
    "familyName",
    "givenName",
    "capacity"
})
public class OfficialCertifyingType {

    @XmlElement(name = "FamilyName", required = true)
    protected FamilyNameType familyName;
    @XmlElement(name = "GivenName", required = true)
    protected GivenNameType givenName;
    @XmlElement(name = "Capacity")
    protected String capacity;
    @XmlAttribute(name = "awardingInstitutionID")
    protected String awardingInstitutionID;

    /**
     * Gets the value of the familyName property.
     *
     * @return possible object is {@link FamilyNameType }
     *
     */
    public FamilyNameType getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     *
     * @param value allowed object is {@link FamilyNameType }
     *
     */
    public void setFamilyName(FamilyNameType value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the givenName property.
     *
     * @return possible object is {@link GivenNameType }
     *
     */
    public GivenNameType getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     *
     * @param value allowed object is {@link GivenNameType }
     *
     */
    public void setGivenName(GivenNameType value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the capacity property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCapacity(String value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the awardingInstitutionID property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAwardingInstitutionID() {
        if (awardingInstitutionID == null) {
            return "DEF-IAW";
        } else {
            return awardingInstitutionID;
        }
    }

    /**
     * Sets the value of the awardingInstitutionID property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAwardingInstitutionID(String value) {
        this.awardingInstitutionID = value;
    }

}
