//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.12 at 12:22:50 PM GMT 
//
package eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Name and status of awarding institution
 *
 * <p>
 * Java class for NameAndStatusOfAwardingInstitutionType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="NameAndStatusOfAwardingInstitutionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AwardingInstitution" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{urn:crue:academic:xsd:language:diplomasupplement}InstitutionType">
 *                 &lt;attribute name="awardingInstitutionID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameAndStatusOfAwardingInstitutionType", propOrder = {
    "awardingInstitution"
})
public class NameAndStatusOfAwardingInstitutionType {

    @XmlElement(name = "AwardingInstitution", required = true)
    protected List<NameAndStatusOfAwardingInstitutionType.AwardingInstitution> awardingInstitution;

    /**
     * Gets the value of the awardingInstitution property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the awardingInstitution property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAwardingInstitution().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameAndStatusOfAwardingInstitutionType.AwardingInstitution }
     *
     *
     */
    public List<NameAndStatusOfAwardingInstitutionType.AwardingInstitution> getAwardingInstitution() {
        if (awardingInstitution == null) {
            awardingInstitution = new ArrayList<NameAndStatusOfAwardingInstitutionType.AwardingInstitution>();
        }
        return this.awardingInstitution;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{urn:crue:academic:xsd:language:diplomasupplement}InstitutionType">
     *       &lt;attribute name="awardingInstitutionID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AwardingInstitution
            extends InstitutionType {

        @XmlAttribute(name = "awardingInstitutionID", required = true)
        protected String awardingInstitutionID;

        /**
         * Gets the value of the awardingInstitutionID property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getAwardingInstitutionID() {
            return awardingInstitutionID;
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

}
