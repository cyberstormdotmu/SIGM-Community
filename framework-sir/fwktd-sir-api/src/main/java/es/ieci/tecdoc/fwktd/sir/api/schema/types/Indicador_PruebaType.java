/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.0.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.ieci.tecdoc.fwktd.sir.api.schema.types;

/**
 * Enumeration Indicador_PruebaType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public enum Indicador_PruebaType implements java.io.Serializable {


      //------------------/
     //- Enum Constants -/
    //------------------/

    /**
     * Constant VALUE_0
     */
    VALUE_0("0"),
    /**
     * Constant VALUE_1
     */
    VALUE_1("1");

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field value.
     */
    private final java.lang.String value;


      //----------------/
     //- Constructors -/
    //----------------/

    private Indicador_PruebaType(final java.lang.String value) {
        this.value = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method fromValue.
     * 
     * @param value
     * @return the constant for this value
     */
    public static es.ieci.tecdoc.fwktd.sir.api.schema.types.Indicador_PruebaType fromValue(
            final java.lang.String value) {
        for (Indicador_PruebaType c: Indicador_PruebaType.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    /**
     * 
     * 
     * @param value
     */
    public void setValue(
            final java.lang.String value) {
    }

    /**
     * Method toString.
     * 
     * @return the value of this constant
     */
    public java.lang.String toString(
    ) {
        return this.value;
    }

    /**
     * Method value.
     * 
     * @return the value of this constant
     */
    public java.lang.String value(
    ) {
        return this.value;
    }

}
