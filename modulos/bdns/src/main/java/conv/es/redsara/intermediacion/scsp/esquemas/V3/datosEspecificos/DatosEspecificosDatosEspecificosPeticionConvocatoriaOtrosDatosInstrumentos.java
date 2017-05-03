/**
 * DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package conv.es.redsara.intermediacion.scsp.esquemas.V3.datosEspecificos;

public class DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos  implements java.io.Serializable {
    private java.lang.String[] instrumento;

    public DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos() {
    }

    public DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos(
           java.lang.String[] instrumento) {
           this.instrumento = instrumento;
    }


    /**
     * Gets the instrumento value for this DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos.
     * 
     * @return instrumento
     */
    public java.lang.String[] getInstrumento() {
        return instrumento;
    }


    /**
     * Sets the instrumento value for this DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos.
     * 
     * @param instrumento
     */
    public void setInstrumento(java.lang.String[] instrumento) {
        this.instrumento = instrumento;
    }

    public java.lang.String getInstrumento(int i) {
        return this.instrumento[i];
    }

    public void setInstrumento(int i, java.lang.String _value) {
        this.instrumento[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos)) return false;
        DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos other = (DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.instrumento==null && other.getInstrumento()==null) || 
             (this.instrumento!=null &&
              java.util.Arrays.equals(this.instrumento, other.getInstrumento())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getInstrumento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInstrumento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInstrumento(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosEspecificosDatosEspecificosPeticionConvocatoriaOtrosDatosInstrumentos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/datosEspecificos", ">>>>DatosEspecificos>DatosEspecificosPeticion>Convocatoria>OtrosDatos>Instrumentos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instrumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/datosEspecificos", "Instrumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/datosEspecificos", ">>>>>DatosEspecificos>DatosEspecificosPeticion>Convocatoria>OtrosDatos>Instrumentos>Instrumento"));
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}