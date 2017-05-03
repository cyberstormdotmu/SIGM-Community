/**
 * SolicitudTransmision.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package conv.es.redsara.intermediacion.scsp.esquemas.V3.peticion;

public class SolicitudTransmision  implements java.io.Serializable {
    private conv.es.redsara.intermediacion.scsp.esquemas.V3.peticion.SolicitudTransmisionDatosGenericos datosGenericos;

    private es.dipucr.bdns.common.DatosEspecificos datosEspecificos;

    public SolicitudTransmision() {
    }

    public SolicitudTransmision(
           conv.es.redsara.intermediacion.scsp.esquemas.V3.peticion.SolicitudTransmisionDatosGenericos datosGenericos,
           es.dipucr.bdns.common.DatosEspecificos datosEspecificos) {
           this.datosGenericos = datosGenericos;
           this.datosEspecificos = datosEspecificos;
    }


    /**
     * Gets the datosGenericos value for this SolicitudTransmision.
     * 
     * @return datosGenericos
     */
    public conv.es.redsara.intermediacion.scsp.esquemas.V3.peticion.SolicitudTransmisionDatosGenericos getDatosGenericos() {
        return datosGenericos;
    }


    /**
     * Sets the datosGenericos value for this SolicitudTransmision.
     * 
     * @param datosGenericos
     */
    public void setDatosGenericos(conv.es.redsara.intermediacion.scsp.esquemas.V3.peticion.SolicitudTransmisionDatosGenericos datosGenericos) {
        this.datosGenericos = datosGenericos;
    }


    /**
     * Gets the datosEspecificos value for this SolicitudTransmision.
     * 
     * @return datosEspecificos
     */
    public es.dipucr.bdns.common.DatosEspecificos getDatosEspecificos() {
        return datosEspecificos;
    }


    /**
     * Sets the datosEspecificos value for this SolicitudTransmision.
     * 
     * @param datosEspecificos
     */
    public void setDatosEspecificos(es.dipucr.bdns.common.DatosEspecificos datosEspecificos) {
        this.datosEspecificos = datosEspecificos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudTransmision)) return false;
        SolicitudTransmision other = (SolicitudTransmision) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datosGenericos==null && other.getDatosGenericos()==null) || 
             (this.datosGenericos!=null &&
              this.datosGenericos.equals(other.getDatosGenericos()))) &&
            ((this.datosEspecificos==null && other.getDatosEspecificos()==null) || 
             (this.datosEspecificos!=null &&
              this.datosEspecificos.equals(other.getDatosEspecificos())));
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
        if (getDatosGenericos() != null) {
            _hashCode += getDatosGenericos().hashCode();
        }
        if (getDatosEspecificos() != null) {
            _hashCode += getDatosEspecificos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudTransmision.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", ">SolicitudTransmision"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosGenericos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", "DatosGenericos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", ">>SolicitudTransmision>DatosGenericos"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosEspecificos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/datosEspecificos", "DatosEspecificos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/datosEspecificos", "DatosEspecificos"));
        elemField.setNillable(true);
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