//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.12 at 12:22:50 PM GMT 
//
package eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Programme requirements
 *
 * <p>
 * Java class for ProgrammeRequirementsType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="ProgrammeRequirementsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Requirements" type="{urn:crue:academic:xsd:language:diplomasupplement}RichTextTagType"/>
 *         &lt;element name="KeyLearningOutcomes" type="{urn:crue:academic:xsd:language:diplomasupplement}RichTextTagType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgrammeRequirementsType", propOrder = {
    "requirements",
    "keyLearningOutcomes"
})
public class ProgrammeRequirementsType {

    @XmlElement(name = "Requirements", required = true)
    protected RichTextTagType requirements;
    @XmlElement(name = "KeyLearningOutcomes")
    protected RichTextTagType keyLearningOutcomes;

    /**
     * Gets the value of the requirements property.
     *
     * @return possible object is {@link RichTextTagType }
     *
     */
    public RichTextTagType getRequirements() {
        return requirements;
    }

    /**
     * Sets the value of the requirements property.
     *
     * @param value allowed object is {@link RichTextTagType }
     *
     */
    public void setRequirements(RichTextTagType value) {
        this.requirements = value;
    }

    /**
     * Gets the value of the keyLearningOutcomes property.
     *
     * @return possible object is {@link RichTextTagType }
     *
     */
    public RichTextTagType getKeyLearningOutcomes() {
        return keyLearningOutcomes;
    }

    /**
     * Sets the value of the keyLearningOutcomes property.
     *
     * @param value allowed object is {@link RichTextTagType }
     *
     */
    public void setKeyLearningOutcomes(RichTextTagType value) {
        this.keyLearningOutcomes = value;
    }

}
