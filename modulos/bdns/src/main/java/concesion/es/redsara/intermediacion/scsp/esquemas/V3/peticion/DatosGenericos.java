/**
 * DatosGenericos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion;

public class DatosGenericos  implements java.io.Serializable {
    private concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Emisor emisor;

    private concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Solicitante solicitante;

    private concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Transmision transmision;

    public DatosGenericos() {
    }

    public DatosGenericos(
           concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Emisor emisor,
           concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Solicitante solicitante,
           concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Transmision transmision) {
           this.emisor = emisor;
           this.solicitante = solicitante;
           this.transmision = transmision;
    }


    /**
     * Gets the emisor value for this DatosGenericos.
     * 
     * @return emisor
     */
    public concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Emisor getEmisor() {
        return emisor;
    }


    /**
     * Sets the emisor value for this DatosGenericos.
     * 
     * @param emisor
     */
    public void setEmisor(concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Emisor emisor) {
        this.emisor = emisor;
    }


    /**
     * Gets the solicitante value for this DatosGenericos.
     * 
     * @return solicitante
     */
    public concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Solicitante getSolicitante() {
        return solicitante;
    }


    /**
     * Sets the solicitante value for this DatosGenericos.
     * 
     * @param solicitante
     */
    public void setSolicitante(concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Solicitante solicitante) {
        this.solicitante = solicitante;
    }


    /**
     * Gets the transmision value for this DatosGenericos.
     * 
     * @return transmision
     */
    public concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Transmision getTransmision() {
        return transmision;
    }


    /**
     * Sets the transmision value for this DatosGenericos.
     * 
     * @param transmision
     */
    public void setTransmision(concesion.es.redsara.intermediacion.scsp.esquemas.V3.peticion.Transmision transmision) {
        this.transmision = transmision;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosGenericos)) return false;
        DatosGenericos other = (DatosGenericos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.emisor==null && other.getEmisor()==null) || 
             (this.emisor!=null &&
              this.emisor.equals(other.getEmisor()))) &&
            ((this.solicitante==null && other.getSolicitante()==null) || 
             (this.solicitante!=null &&
              this.solicitante.equals(other.getSolicitante()))) &&
            ((this.transmision==null && other.getTransmision()==null) || 
             (this.transmision!=null &&
              this.transmision.equals(other.getTransmision())));
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
        if (getEmisor() != null) {
            _hashCode += getEmisor().hashCode();
        }
        if (getSolicitante() != null) {
            _hashCode += getSolicitante().hashCode();
        }
        if (getTransmision() != null) {
            _hashCode += getTransmision().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosGenericos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", ">DatosGenericos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", "Emisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", ">Emisor"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", "Solicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", ">Solicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transmision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", "Transmision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://intermediacion.redsara.es/scsp/esquemas/V3/peticion", ">Transmision"));
        elemField.setNillable(false);
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
