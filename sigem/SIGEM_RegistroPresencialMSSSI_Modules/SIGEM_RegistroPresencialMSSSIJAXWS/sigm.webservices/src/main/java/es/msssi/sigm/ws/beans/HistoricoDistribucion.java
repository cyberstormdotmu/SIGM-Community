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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "historicoDistribucion", propOrder = {
    "distribucionRegistro"
})
public class HistoricoDistribucion {

    @XmlElement(required = true)
    protected List<DistribucionRegistro> distribucionRegistro;

    public HistoricoDistribucion() {
	}
    
	public HistoricoDistribucion(List<DistribucionRegistro> distribucionRegistro) {
		super();
		this.distribucionRegistro = distribucionRegistro;
	}
	public void setMovimientoRegistro(List<DistribucionRegistro> distribucionRegistro) {
		this.distribucionRegistro = distribucionRegistro;
	}
	public List<DistribucionRegistro> getMovimientoRegistro() {
        if (distribucionRegistro == null) {
        	distribucionRegistro = new ArrayList<DistribucionRegistro>();
        }		
		return distribucionRegistro;
	}
}
