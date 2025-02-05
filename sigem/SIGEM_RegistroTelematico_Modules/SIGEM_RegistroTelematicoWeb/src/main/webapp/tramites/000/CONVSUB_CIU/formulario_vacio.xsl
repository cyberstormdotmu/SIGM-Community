<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">




<xsl:include href="../templates_comunes.xsl" />


<xsl:output encoding="ISO-8859-1" method="html"/>


	<xsl:variable name="lang.titulo" select="'CONVOCATORIA DE SUBVENCIONES Y ACTIVIDADES PARA PERSONAS F�SICAS'"/>
	
	<xsl:variable name="lang.id_nif" select="'Documento de identidad'"/>
	<xsl:variable name="lang.id_nombre" select="'Nombre'"/>
	<xsl:variable name="lang.cargo" select="'Cargo*'"/>


	<xsl:variable name="lang.datosRepresentante" select="'Datos del responsable del proyecto/actividad'"/>
	<xsl:variable name="lang.datosInteresado" select="'Datos de la Asociaci�n solicitante'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.datosDeclarativos" select="'Declaraciones Preceptivas que formula el solicitante'"/>
	
	<xsl:variable name="lang.nif_repre" select="'NIF/CIF'"/>
	<xsl:variable name="lang.nombre_repre" select="'Apellidos y Nombre o Denominaci�n Social'"/>
	<xsl:variable name="lang.domicilio_repre" select="'Domicilio'"/>
	<xsl:variable name="lang.ciudad_repre" select="'Localidad'"/>
	<xsl:variable name="lang.c_postal_repre" select="'C�digo Postal'"/>
	<xsl:variable name="lang.region_repre" select="'Regi�n / Pa�s'"/>
	<xsl:variable name="lang.movil_repre" select="'Tel�fono'"/>
	<xsl:variable name="lang.d_email_repre" select="'Correo electr�nico'"/>	


	<xsl:variable name="lang.convocatoria" select="'Convocatorias abiertas:* (seleccionar)'"/>
	<xsl:variable name="lang.convocatoriaObligatoria" select="'Convocatorias'"/>
	<xsl:variable name="lang.avisoConvocatoria" select="'Por favor, revise que ha seleccionado correctamente la convocatoria deseada.'"/>


	<xsl:variable name="lang.ayuntamiento" select="'Nombre de la asociaci�n'"/>	
	<xsl:variable name="lang.nif" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.representante" select="'Asociaci�n'"/>
	<xsl:variable name="lang.cif" select="'CIF'"/>
	<xsl:variable name="lang.direccion" select="'Domicilio a efectos de notificaci�n'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.cp" select="'C�digo postal'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.telefono" select="'Tel�fono'"/>
	<xsl:variable name="lang.email" select="'Correo electr�nico'"/>


	<xsl:variable name="lang.finalidad" select="'Finalidad (Obra, Suministro o Actividad)'"/>
	<xsl:variable name="lang.presupuesto" select="'Presupuesto Total Estimado (xxxx.xx, 0 en caso de que no proceda)'"/>
	<xsl:variable name="lang.subvencion" select="'Subvenci�n que se solicita (xxxx.xx, 0 en caso de que no proceda)'"/>
	<xsl:variable name="lang.otrasSubvenciones" select="'Otras subvenciones solicitadas y/o concedidas para la actividad, servicio y/o inversi�n'"/>
	<xsl:variable name="lang.fecha" select="'Fecha prevista de realizaci�n actividad / suministro / terminaci�n de las obras'"/>
	
	
	<xsl:variable name="lang.expone" select="'Expone'"/>
	<xsl:variable name="lang.solicita" select="'Solicita'"/>
	<xsl:variable name="lang.declaro" select="'Asimismo, declaro bajo mi Responsabilidad (obligatorio marcar con una x cada cuadro para poder seguir el tr�mite)'"/>
	
	<xsl:variable name="lang.declaro1" select="'Que al d�a de la fecha, esta entidad se encuentra al corriente en el cumplimiento de sus obligaciones tributarias y frente a la Seguidad Social, as� como de sus obligaciones fiscales con la Excma. Diputaci�n Provincial de Ciudad Real, no es deudora por resoluci�n de procedencia de reintegro de subvenciones, y autorizo a la Diputaci�n Provincial para la obtenci�n de los certificados de la Agencia Estatal de la Administraci�n Tributaria y de la Tesorer�a Territorial de la Seguridad de estar al corriente en el cumplimiento de dichas obligaciones.'"/>
	<xsl:variable name="lang.declaro2" select="'Que no me encuentro/esta entidad no se encuentra incursa en ninguna de las circunstancias de prohibici�n para la obtenci�n de la condici�n de beneficiario de ayuda o subvenci�n, previstas en el art�culo 13 de la Ley General de Subvenciones, de 17 de noviembre de 2003.'"/>
	<xsl:variable name="lang.declaro3" select="'Que la persona o entidad solicitante autoriza expresamente a la Diputaci�n de Ciudad Real para consultar las expresadas circunstancias ante las entidades se�aladas.'"/>	


	<xsl:variable name="lang.anexar" select="'DOCUMENTACI�N QUE SE ACOMPA�A'"/>
	<!--<xsl:variable name="lang.anexarInfo1" select="'1.- Memoria descriptiva y valorada econ�micamente del proyecto, programa, servicio o actividad a subvencionar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Certificaci�n de aprobaci�n de la Memoria por el �rgano competente de la entidad.'"/>
	<xsl:variable name="lang.anexarInfo3" select="'3.- Para adjuntar los ficheros, pulse el bot�n examinar. Seleccione el fichero que desee anexar a la solicitud. Recuerde que debe anexar copia de la Memoria o proyecto al que se refiere la presente solicitud y de cualquier otro que considere conveniente. Si el documento est� en soporte papel, debe escanearlo con car�cter previo. Los originales quedan a disposici�n de la Diputaci�n Provincial durante toda la tramitaci�n del expediente.'"/>-->


	<xsl:variable name="lang.anexarInfo1" select="'1.- Debe anexarse la documentaci�n establecida en la convocatoria.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Para adjuntar los ficheros, pulse el bot�n examinar. Seleccione el fichero que desee anexar a la solicitud. Recuerde que debe anexar copia de la Memoria o proyecto al que se refiere la presente solicitud y de cualquier otro que considere conveniente. Si el documento est� en soporte papel, debe escanearlo con car�cter previo. Los originales quedan a disposici�n de la Diputaci�n Provincial durante toda la tramitaci�n del expediente.'"/>


	<xsl:variable name="lang.documentoTipo" select="'Archivo ZIP'"/>
	<xsl:variable name="lang.documentoTipoXSIG" select="'Archivo XSIG'"/>
	<xsl:variable name="lang.documentoTipo_DOC_ODT" select="'Archivo ODT/DOC'"/>
	<xsl:variable name="lang.documentoTipoPDF" select="'Archivo PDF'"/>
	<xsl:variable name="lang.documentoTipoJPEG" select="'Archivo JPEG'"/>
	
		
	<xsl:template match="/"  xmlns:java="http://xml.apache.org/xslt/java">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			//----------------------------
			var validar = new Array(8);
			
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.id_nif"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.id_nombre"/>');		


			validar[2] = new Array('domicilio_repre','<xsl:value-of select="$lang.direccion"/>');
			validar[3] = new Array('ciudad_repre','<xsl:value-of select="$lang.localidad"/>');
			validar[4] = new Array('region_repre','<xsl:value-of select="$lang.provincia"/>');
			validar[5] = new Array('c_postal_repre','<xsl:value-of select="$lang.cp"/>');


			validar[6] = new Array('convocatoria','<xsl:value-of select="$lang.convocatoriaObligatoria"/>');
			validar[7] = new Array('finalidad', '<xsl:value-of select="$lang.finalidad"/>');




			//Array con los datos especificos del formilario -> -> ('id_campo','tag_xml')
			//----------------------------------------------
			var especificos = new Array(29);
			
			especificos[0] = new Array('ayuntamiento','ayuntamiento');
			especificos[1] = new Array('domicilioNotificacion','domicilioNotificacion');
			especificos[2] = new Array('localidad','localidad');
			especificos[3] = new Array('provincia','provincia');
			especificos[4] = new Array('codigoPostal','codigoPostal');
			especificos[5] = new Array('telefono','telefono');
			
			especificos[6] = new Array('convocatoria','convocatoria');


			especificos[7] = new Array('finalidad','finalidad');
			especificos[8] = new Array('expone','expone');
			especificos[9] = new Array('solicita','solicita');
			
			
			especificos[10] = new Array('documentoIdentidad','documentoIdentidad');
			especificos[11] = new Array('nombreSolicitante','nombreSolicitante');
			especificos[12] = new Array('emailSolicitante','emailSolicitante');
			
			especificos[13] = new Array('cif','cif');
			
			especificos[14] = new Array('declaro1','declaro1');
			especificos[15] = new Array('declaro2','declaro2');
			especificos[16] = new Array('declaro3','declaro3');
			
			especificos[17] = new Array('nif_repre','nif_repre');
			especificos[18] = new Array('nombre_repre','nombre_repre');
			especificos[19] = new Array('domicilio_repre','domicilio_repre');
			especificos[20] = new Array('ciudad_repre','ciudad_repre');
			especificos[21] = new Array('c_postal_repre','c_postal_repre');
			especificos[22] = new Array('region_repre','region_repre');
			especificos[23] = new Array('movil_repre','movil_repre');
			especificos[24] = new Array('d_email_repre','d_email_repre');


			especificos[25] = new Array('cargo','cargo');
			especificos[26] = new Array('Descripcion_ayuntamiento','Descripcion_ayuntamiento');
			
			especificos[27] = new Array('texto_legal_comun','texto_legal_comun');
			especificos[28] = new Array('texto_datos_personales_comun','texto_datos_personales_comun');
			
			//Array de validaciones
			//----------------------------------------------
			var validarNumero;
			function verificacionesEspecificas() {
				document.getElementById('nombreSolicitante').value = document.getElementById('nombreSolicitante').value;
				document.getElementById('documentoIdentidad').value = document.getElementById('documentoIdentidad').value;
//				document.getElementById('domicilioNotificacion').value = document.getElementById('domicilio_repre').value;
				document.getElementById('localidad').value = document.getElementById('ciudad_repre').value;


				document.getElementById('Descripcion_ayuntamiento').value = document.getElementById('nombreSolicitante').value;
				
				document.getElementById('cargo').value = '';
				document.getElementById('nif_repre').value = document.getElementById('documentoIdentidad').value;
				document.getElementById('nombre_repre').value = document.getElementById('nombreSolicitante').value;
				
				document.getElementById('cif').value = document.getElementById('documentoIdentidad').value;
				document.getElementById('ayuntamiento').value = document.getElementById('nombreSolicitante').value;
				
				document.getElementById('domicilioNotificacion').value = document.getElementById('domicilio_repre').value;
				document.getElementById('localidad').value = document.getElementById('ciudad_repre').value;
				document.getElementById('provincia').value = document.getElementById('region_repre').value;
				document.getElementById('codigoPostal').value = document.getElementById('c_postal_repre').value;
				document.getElementById('telefono').value = document.getElementById('movil_repre').value;
				document.getElementById('emailSolicitante').value = document.getElementById('d_email_repre').value;
				
				if(document.getElementById('convocatoria').value=='-------'){
					alert('Debe seleccionar una convocatoria');
					document.getElementById('convocatoria').focus();
					return false;
				}
				
				if(document.getElementById('finalidad').value==''){
					alert('Debe indicar la finalidad');
					document.getElementById('finalidad').focus();
					return false;
				}


				return true;
			}
						
			
			function abrirDocumento(){ 
				v=window.open("documentos/MODELO.odt"); 
			} 
						
		</script>
		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
   		
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.id_nif"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">width:490px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Documento_Identificacion/Numero"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.id_nombre"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">width:490px; </xsl:attribute>
					<xsl:attribute name="name">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="id">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Nombre"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio_repre"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">width:490px; </xsl:attribute>
					<xsl:attribute name="name">domicilio_repre</xsl:attribute>
					<xsl:attribute name="id">domicilio_repre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/domicilio_repre"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.ciudad_repre"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">width:300px; </xsl:attribute>
					<xsl:attribute name="name">ciudad_repre</xsl:attribute>
					<xsl:attribute name="id">ciudad_repre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/ciudad_repre"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.c_postal_repre"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">width:60px; </xsl:attribute>
					<xsl:attribute name="name">c_postal_repre</xsl:attribute>
					<xsl:attribute name="id">c_postal_repre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/c_postal_repre"/></xsl:attribute>
				</input>
			</div>	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.region_repre"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">width:300px; </xsl:attribute>
					<xsl:attribute name="name">region_repre</xsl:attribute>
					<xsl:attribute name="id">region_repre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/region_repre"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.movil_repre"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">width:70px; </xsl:attribute>
					<xsl:attribute name="name">movil_repre</xsl:attribute>
					<xsl:attribute name="id">movil_repre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/movil_repre"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.d_email_repre"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">width:300px; </xsl:attribute>
					<xsl:attribute name="name">d_email_repre</xsl:attribute>
					<xsl:attribute name="id">d_email_repre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/d_email_repre"/></xsl:attribute>
				</input>
			</div>
   		</div>


   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro" style="">
	   		<b><xsl:value-of select="$lang.convocatoria"/></b>
			<xsl:variable name="convocatoria" select="java:es.dipucr.sigem.registroTelematicoWeb.formulario.common.XmlCargaDatos.getDatosConsulta('Convocatoria', 'select numexp, asunto from spac_expedientes where codprocedimiento IN ( select valor from DPCR_CONV_EXP_CONV_CIU) and fcierre is null and estadoadm=#PR#','000')"/>
			<xsl:variable name="convocatoria" select="document($convocatoria)"/>
			<tb/>
			<select class="gr">
				<xsl:attribute name="style">border:none; width:650px;color:#006699;</xsl:attribute>
				<xsl:attribute name="name">convocatoria</xsl:attribute>
				<xsl:attribute name="id">convocatoria</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/convocatoria"/></xsl:attribute>
				<option>
					<xsl:attribute name="value">-------</xsl:attribute>----------------------------------------------------</option>
				<xsl:for-each select="$convocatoria/listado/dato">
					<option>
						<xsl:attribute name="value"><xsl:value-of select="valor"/></xsl:attribute>
						<xsl:value-of select="sustituto"/>
					</option>
				</xsl:for-each>				
			</select>
			<br/>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:640px; color=RED</xsl:attribute>
				<br/>*<xsl:value-of select="$lang.avisoConvocatoria"/>
			</label>
		</div>  		


   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.finalidad"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:490px; </xsl:attribute>
					<xsl:attribute name="name">finalidad</xsl:attribute>
					<xsl:attribute name="id">finalidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/finalidad"/></xsl:attribute>
				</input>
			</div>			
			<br/>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.expone"/>:
				</label>
				<textarea type="gr">
					<xsl:attribute name="style">position: relative; width:490px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">expone</xsl:attribute>
					<xsl:attribute name="id">expone</xsl:attribute>
					<xsl:attribute name="rows">7</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/expone"/>
				</textarea>
			</div>
			<br/>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.solicita"/>:	
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:490px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">solicita</xsl:attribute>
					<xsl:attribute name="id">solicita</xsl:attribute>
					<xsl:attribute name="rows">7</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/solicita"/>
				</textarea>
			</div>


<script>
document.getElementById('expone').value = 'Que la persona solicitante tiene inter�s en participar en la convocatoria seleccionada.';
document.getElementById('solicita').value = 'Que sea concedida.';
</script>
			
			</div>
			<div class="submenu">
   				<h1><xsl:value-of select="$lang.datosDeclarativos"/></h1>
   			</div>
   			<div class="cuadro" style="">	
				<input type="checkbox" id="declaro1" checked="Yes" style="border:none;color:#006699;position: relative; width:20px;" DISABLED="Yes"/>
				<xsl:value-of select="$lang.declaro1"/>
				
				<br/>
				
				<input type="checkbox" id="declaro2" checked="Yes" style="border:none;color:#006699;position: relative; width:20px;" DISABLED="Yes"/>
				<xsl:value-of select="$lang.declaro2"/>
				<br/>
				
				<input type="checkbox" id="declaro3" checked="Yes" style="border:none;color:#006699;position: relative; width:20px;" DISABLED="Yes"/>
				<xsl:value-of select="$lang.declaro3"/>
				<br/>
			</div>
		
   			<br/>
			<xsl:call-template name="TEXTO_LEGAL_COMUN" />
	   		<br/>
			<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   			</div>
   			<div class="cuadro" style="">
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/>
		   			<!-- <a style="text-decoration:underline; color:red" href="javascript:abrirDocumento();">Modelo orientativo de acuerdo</a><br/>  -->
		   			<br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
					<!--<xsl:value-of select="$lang.anexarInfo3"/><br/>-->
				</label>
			<br/><br/>
			
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoTipo"/>:<font color="#950000"></font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">CR_CONVSUB_D1</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">CR_CONVSUB_D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">zip</xsl:attribute>
				</input>
				
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoTipo_DOC_ODT"/>:<font color="#950000"></font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">CR_CONVSUB_D2</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">CR_CONVSUB_D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">odt,doc</xsl:attribute>
				</input>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoTipoPDF"/>:<font color="#950000"></font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">CR_CONVSUB_D3</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">CR_CONVSUB_D3_Tipo</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D3_Tipo</xsl:attribute>
					<xsl:attribute name="value">PDF</xsl:attribute>
				</input>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoTipoJPEG"/>:<font color="#950000"></font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">CR_CONVSUB_D4</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D4</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">CR_CONVSUB_D4_Tipo</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D4_Tipo</xsl:attribute>
					<xsl:attribute name="value">JPG</xsl:attribute>
				</input>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px; text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoTipoXSIG"/>:<font color="#950000"></font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; size:400px;</xsl:attribute>
					<xsl:attribute name="name">CR_CONVSUB_D5</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D5</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">CR_CONVSUB_D5_Tipo</xsl:attribute>
					<xsl:attribute name="id">CR_CONVSUB_D5_Tipo</xsl:attribute>
					<xsl:attribute name="value">XSIG</xsl:attribute>
				</input>
			</div>
   		</div>
		<xsl:call-template name="TEXTO_DATOS_PERSONALES_COMUN" />
   		<br/>
		<xsl:call-template name="TEXTO_COMPARECE_COMUN" />
   		<br/>
   		<input type="hidden">
			<xsl:attribute name="name">datosEspecificos</xsl:attribute>
			<xsl:attribute name="id">datosEspecificos</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">cif</xsl:attribute>
			<xsl:attribute name="id">cif</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">emailSolicitante</xsl:attribute>
			<xsl:attribute name="id">emailSolicitante</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">Decripcion_ayuntamiento</xsl:attribute>
			<xsl:attribute name="id">Decripcion_ayuntamiento</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">cargo</xsl:attribute>
			<xsl:attribute name="id">cargo</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">nif_repre</xsl:attribute>
			<xsl:attribute name="id">nif_repre</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">nombre_repre</xsl:attribute>
			<xsl:attribute name="id">nombre_repre</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">cif</xsl:attribute>
			<xsl:attribute name="id">cif</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">ayuntamiento</xsl:attribute>
			<xsl:attribute name="id">ayuntamiento</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">Descripcion_ayuntamiento</xsl:attribute>
			<xsl:attribute name="id">Descripcion_ayuntamiento</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
			<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">localidad</xsl:attribute>
			<xsl:attribute name="id">localidad</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">provincia</xsl:attribute>
			<xsl:attribute name="id">provincia</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">codigoPostal</xsl:attribute>
			<xsl:attribute name="id">codigoPostal</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">telefono</xsl:attribute>
			<xsl:attribute name="id">telefono</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>


	</xsl:template>
</xsl:stylesheet>
