//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.25 at 10:59:25 AM CET 
//


package es.dipucr.jaxb.tribunalcuentas.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoContrato.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoContrato">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="J"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="Z"/>
 *     &lt;enumeration value="K"/>
 *     &lt;enumeration value="L"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoContrato")
@XmlEnum
public enum TipoContrato {

    A,
    E,
    C,
    F,
    H,
    B,
    I,
    J,
    D,
    G,
    Z,
    K,
    L;

    public String value() {
        return name();
    }

    public static TipoContrato fromValue(String v) {
        return valueOf(v);
    }

}
