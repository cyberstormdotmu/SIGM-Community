//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.12 at 12:22:50 PM GMT 
//
package eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for MobilityProgrammeType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="MobilityProgrammeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" minOccurs="0"/>
 *         &lt;element name="FieldOfStudy" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType" minOccurs="0"/>
 *         &lt;element name="Country" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}CountryTextCodeType"/>
 *         &lt;element name="ReceivingInstitutionName" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}PlainTextType"/>
 *         &lt;element name="AcademicYear" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}AcademicYearType" minOccurs="0"/>
 *         &lt;element name="DateFrom" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}DateType" minOccurs="0"/>
 *         &lt;element name="DateTo" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}DateType" minOccurs="0"/>
 *         &lt;element name="CoursesUnits" type="{urn:eu:stork:names:tc:STORK:2.0:academic:generaldiploma}MobilityProgrammeCoursesUnitsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MobilityProgrammeType", propOrder = {
    "type",
    "fieldOfStudy",
    "country",
    "receivingInstitutionName",
    "academicYear",
    "dateFrom",
    "dateTo",
    "coursesUnits"
})
public class MobilityProgrammeType {

    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "FieldOfStudy")
    protected String fieldOfStudy;
    @XmlElement(name = "Country", required = true)
    protected CountryTextCodeType country;
    @XmlElement(name = "ReceivingInstitutionName", required = true)
    protected String receivingInstitutionName;
    @XmlElement(name = "AcademicYear")
    protected String academicYear;
    @XmlElement(name = "DateFrom")
    protected XMLGregorianCalendar dateFrom;
    @XmlElement(name = "DateTo")
    protected XMLGregorianCalendar dateTo;
    @XmlElement(name = "CoursesUnits", required = true)
    protected MobilityProgrammeCoursesUnitsType coursesUnits;

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the fieldOfStudy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * Sets the value of the fieldOfStudy property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFieldOfStudy(String value) {
        this.fieldOfStudy = value;
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

    /**
     * Gets the value of the receivingInstitutionName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getReceivingInstitutionName() {
        return receivingInstitutionName;
    }

    /**
     * Sets the value of the receivingInstitutionName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setReceivingInstitutionName(String value) {
        this.receivingInstitutionName = value;
    }

    /**
     * Gets the value of the academicYear property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * Sets the value of the academicYear property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAcademicYear(String value) {
        this.academicYear = value;
    }

    /**
     * Gets the value of the dateFrom property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDateFrom(XMLGregorianCalendar value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDateTo(XMLGregorianCalendar value) {
        this.dateTo = value;
    }

    /**
     * Gets the value of the coursesUnits property.
     *
     * @return possible object is {@link MobilityProgrammeCoursesUnitsType }
     *
     */
    public MobilityProgrammeCoursesUnitsType getCoursesUnits() {
        return coursesUnits;
    }

    /**
     * Sets the value of the coursesUnits property.
     *
     * @param value allowed object is {@link MobilityProgrammeCoursesUnitsType }
     *
     */
    public void setCoursesUnits(MobilityProgrammeCoursesUnitsType value) {
        this.coursesUnits = value;
    }

}
