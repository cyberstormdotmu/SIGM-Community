/*
* Copyright 2016 Ministerio de Sanidad, Servicios Sociales e Igualdad 
* Licencia con arreglo a la EUPL, Versi�n 1.1 o �en cuanto sean aprobadas por laComisi�n Europea� versiones posteriores de la EUPL (la �Licencia�); 
* Solo podr� usarse esta obra si se respeta la Licencia. 
* Puede obtenerse una copia de la Licencia en: 
* http://joinup.ec.europa.eu/software/page/eupl/licence-eupl 
* Salvo cuando lo exija la legislaci�n aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye �TAL CUAL�, SIN GARANT�AS NI CONDICIONES DE NING�N TIPO, ni expresas ni impl�citas. 
* V�ase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia. 
*/

 
package es.msssi.sigm.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "peticionRegistro", propOrder = {
    "ficheros",
    "contenidoAcuse"
    
})
public class PeticionRegistro  extends SigmRequest{

	
    @XmlElement(required = true)
    protected Ficheros ficheros;
    @XmlElement(required = false)
    protected String contenidoAcuse;
    
    public Ficheros getFicheros() {
        return ficheros;
    }

    public void setFicheros(Ficheros value) {
        this.ficheros = value;
    }

    public String getContenidoAcuse() {
		return contenidoAcuse;
	}
    public void setContenidoAcuse(String contenidoAcuse) {
		this.contenidoAcuse = contenidoAcuse;
	}
    
}
