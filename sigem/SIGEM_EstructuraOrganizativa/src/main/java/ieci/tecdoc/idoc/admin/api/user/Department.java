package ieci.tecdoc.idoc.admin.api.user;

import ieci.tecdoc.idoc.admin.internal.BasicUsersImpl;
import ieci.tecdoc.idoc.admin.internal.GenericPermsImpl;

import java.util.Date;

/**
 * Proporciona toda la funcionalidad necesaria para manejar departamentos
 * de usuarios de invesDoc.
 */
public interface Department
{
	/**
    * Carga un departamento invesDoc.
    * 
    * @param deptId Identificador del departamento.
    * @throws Exception Si se produce algún error al leer la información del 
    * departamento.
    */

   public void load(int deptId, String entidad) throws Exception;

	/**
    * Carga un departamento invesDoc.
    * 
    * @param deptId Identificador del departamento.
    * @throws Exception Si se produce algún error al leer la información del 
    * departamento.
    */

   public void loadLite(int deptId, String entidad) throws Exception;
   
   /**
    * Carga un departamento invesDoc.
    * 
    * @param deptName Nombre del departamento.
    * @throws Exception Si se produce algún error al leer la información del 
    * departamento.
    */

   public void load(String deptName, String entidad) throws Exception;

   /**
    * Guarda el departamento. Se utiliza tanto para inserciones como para 
    * actualizaciones.
    * 
    * @throws Exception Si se produce algún error al guardar. Por ejemplo, 
    * el departamento ya existe.
    */

   public void store(String entidad) throws Exception;

   /**
    * Elimina el departamento.
    * 
    * @throws Exception Si se produce algún error al eliminar.
    */

   public void delete(String entidad) throws Exception;
   
   /**
    * Obtiene el identificador de departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getId();
   
   /**
    * Obtiene el nombre del departamento.
    *  
    * @return El nombre mencionado.
    */
   public String getName();
   
   /**
    * Establece el nombre del departamento.
    * 
    * @param name Nombre del departamento.
    */
   public void setName(String name);
   
   /**
    * Obtiene el identificador del padre del departamento.
    *  
    * @return El nombre mencionado.
    */
   public int getParentId();
   
   /**
    * Obtiene la lista de usuarios (identificadores y nombres) que pueden ser
    * administradores de este departamento.
    * 
    * @return La lista mencionada. 
    */    
   public BasicUsers getAdminUsers(String entidad) throws Exception;
   
   /**
    * Establece el identificador del padre del departamento.
    * 
    * @param parentId El identificador del padre del departamento.
    */
   public void setParentId(int parentId);
   
   /**
    * Obtiene el identificador del administrador del departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getManagerId();
   
   /**
    * Establece el identificador del administrador del departamento.
    * 
    * @param managerId El identificador del administrador.
    */
   public void setManagerId(int managerId);
   
   /**
    * Obtiene el identificador del tipo de departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getType();
   
   /**
    * Obtiene la descripción del departamento.
    * 
    * @return El nombre mencionado.
    */
   public String getDescription();
   
   /**
    * Establece la descripción del departamento.
    * 
    * @param description La descripción del grupo.
    */
   public void setDescription(String description);
   
   /**
   * Obtiene la email del departamento.
    * 
    * @return El nombre mencionado.
    */
   public String getEmail();
   
   /**
    * Establece la email del departamento.
    * 
    * @param email La descripción del grupo.
    */
   public void setEmail(String email);
   
   /**
    * Obtiene la descripción del idorg.
    * 
    * @return El idorg mencionado.
    */
   public Integer getIdorg();
   
   /**
    * Establece la descripción del idorg.
    * 
    * @param idorg La descripción del grupo.
    */
   public void setIdorg(Integer idorg);
   
   /**	 * Obtiene el identificador del usuario que ha creado el departamento. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId();
	
	/**
    * Obtiene la fecha de creación del departamento.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate();
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el departamento.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId();
   
   /**
    * Obtiene la fecha de actualización del departamento.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate();
   
   /**
    * Devuelve la lista de permisos del departamento.
    * 
    * @return La lista mencionada.
    */
   
   public Permissions getPermissions();
   
   public int get_userConnected();

   public GenericPermsImpl get_permsImpl();

   public Users get_users();

   public BasicUsersImpl get_adminUsers();
   
	public void set_creationDate(Date date);
	
	public void set_creatorId(int id);
	
	public void set_id(int _id);
	
	public void set_type(int _type);
	
	public void set_updateDate(Date date);
	
	public void set_updaterId(int id);
	
	public void set_adminUsers(BasicUsersImpl users);
	
	public void set_permsImpl(GenericPermsImpl impl);
	
	public void set_userConnected(int connected);
	
	public void set_users(Users _users);   
   
   
   /**
    * Devuelve la lista de usuarios del departamento.
    * 
    * @param id Identificador del departamento.
    * @return Los usuarios del departamento.
    */
   public Users getUsersByDept(int id, String entidad) throws Exception;
   
   /**
    * Obtiene la información del departamento en formato XML.
    *
    * @return La información mencionada.
    */
   
   public String toXML(); 

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString();
}
