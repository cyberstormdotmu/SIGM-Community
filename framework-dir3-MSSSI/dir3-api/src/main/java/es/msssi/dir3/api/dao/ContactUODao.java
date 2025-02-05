/*
* Copyright 2016 Ministerio de Sanidad, Servicios Sociales e Igualdad 
* Licencia con arreglo a la EUPL, Versi�n 1.1 o �en cuanto sean aprobadas por laComisi�n Europea� versiones posteriores de la EUPL (la �Licencia�); 
* Solo podr� usarse esta obra si se respeta la Licencia. 
* Puede obtenerse una copia de la Licencia en: 
* http://joinup.ec.europa.eu/software/page/eupl/licence-eupl 
* Salvo cuando lo exija la legislaci�n aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye �TAL CUAL�, SIN GARANT�AS NI CONDICIONES DE NING�N TIPO, ni expresas ni impl�citas. 
* V�ase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia. 
*/

package es.msssi.dir3.api.dao;

import java.sql.SQLException;
import java.util.List;

import es.msssi.dir3.api.type.CriterionEnum;
import es.msssi.dir3.api.vo.ContactUOVO;

/**
 * Interfaz de los DAOs de datos b�sicos de contacto de unidades org�nicas.
 * 
 * @author cmorenog
 * 
 */
public interface ContactUODao extends BaseDao<ContactUOVO, Integer, CriterionEnum> {

    /**
     * Se eliminan los contactos de la unidad que pasa como par�metro.
     * @param id
     * 	c�digo de la unidad.
     * @throws SQLException .
     */
    void deleteContactsOrg(
	String id)
	throws SQLException;

    /**
     * Se devuelven los contactos de la oficina que pasa como par�metro.
     * @param codigo
     * 	c�digo de la oficina.
     * @throws SQLException .
     */
    public List<ContactUOVO> getContacts(
	String codigo)
	throws SQLException;
}
