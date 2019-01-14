//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.12 at 12:22:50 PM GMT 
//
package eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ImageMimeType.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * <
 * pre>
 * &lt;simpleType name="ImageMimeType"> &lt;restriction
 * base="{urn:crue:academic:xsd:language:diplomasupplement}MimeType">
 * &lt;enumeration value="image/gif"/> &lt;enumeration value="image/jpeg"/>
 * &lt;enumeration value="image/pjpeg"/> &lt;enumeration value="image/png"/>
 * &lt;enumeration value="image/tiff"/> &lt;/restriction> &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "ImageMimeType")
@XmlEnum(MimeType.class)
public enum ImageMimeType {

    /**
     * GIF image
     *
     */
    @XmlEnumValue("image/gif")
    IMAGE_GIF(MimeType.IMAGE_GIF),
    /**
     * JPEG JFIF image
     *
     */
    @XmlEnumValue("image/jpeg")
    IMAGE_JPEG(MimeType.IMAGE_JPEG),
    /**
     * JPEG JFIF image
     *
     */
    @XmlEnumValue("image/pjpeg")
    IMAGE_PJPEG(MimeType.IMAGE_PJPEG),
    /**
     * Portable Network Graphics
     *
     */
    @XmlEnumValue("image/png")
    IMAGE_PNG(MimeType.IMAGE_PNG),
    /**
     * Tag Image File Format
     *
     */
    @XmlEnumValue("image/tiff")
    IMAGE_TIFF(MimeType.IMAGE_TIFF);
    private final MimeType value;

    ImageMimeType(MimeType v) {
        value = v;
    }

    public MimeType value() {
        return value;
    }

    public static ImageMimeType fromValue(MimeType v) {
        for (ImageMimeType c : ImageMimeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}