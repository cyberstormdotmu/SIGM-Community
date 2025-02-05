/**
 * TiposConectores.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatalogoTramitesWebService;

public class TiposConectores  extends es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio  implements java.io.Serializable {
    private es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTipoConector tiposConectores;

    public TiposConectores() {
    }

    public TiposConectores(
           java.lang.String errorCode,
           java.lang.String returnCode,
           es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTipoConector tiposConectores) {
        super(
            errorCode,
            returnCode);
        this.tiposConectores = tiposConectores;
    }


    /**
     * Gets the tiposConectores value for this TiposConectores.
     * 
     * @return tiposConectores
     */
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTipoConector getTiposConectores() {
        return tiposConectores;
    }


    /**
     * Sets the tiposConectores value for this TiposConectores.
     * 
     * @param tiposConectores
     */
    public void setTiposConectores(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ArrayOfTipoConector tiposConectores) {
        this.tiposConectores = tiposConectores;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TiposConectores)) return false;
        TiposConectores other = (TiposConectores) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.tiposConectores==null && other.getTiposConectores()==null) || 
             (this.tiposConectores!=null &&
              this.tiposConectores.equals(other.getTiposConectores())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getTiposConectores() != null) {
            _hashCode += getTiposConectores().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TiposConectores.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "TiposConectores"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tiposConectores");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "tiposConectores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.catalogo.sgm.tecdoc.ieci", "ArrayOfTipoConector"));
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
