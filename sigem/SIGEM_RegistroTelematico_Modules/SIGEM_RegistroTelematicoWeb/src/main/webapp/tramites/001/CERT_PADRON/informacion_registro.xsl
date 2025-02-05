<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificaci�n'"/>
	<xsl:variable name="lang.email" select="'Correo electr�nico'"/>
	<xsl:variable name="lang.tipoCert" select="'Tipo de Certificado'"/>
	<xsl:variable name="lang.telefono" select="'Tel�fono m�vil'"/>
  <xsl:variable name="lang.texto" select="'Texto'"/>
  <xsl:variable name="lang.observaciones" select="'Observaciones'"/>
  <xsl:variable name="lang.numRegistro" select="'N�mero de registro'"/>
	<xsl:variable name="lang.fechaPresentacion" select="'Fecha de presentaci�n'"/>
	<xsl:variable name="lang.fechaEfectiva" select="'Fecha efectiva'"/>
	<xsl:variable name="lang.info_legal" select="'Los datos personales, identificativos y de contacto, aportados mediante esta comunicaci�n se entienden facilitados voluntariamente, y ser�n incorporados a un fichero cuya finalidad es la de mantener con Vd. relaciones dentro del �mbito de las competencias de esta Administraci�n P�blica as� como informarle de nuestros servicios presentes y futuros ya sea por correo ordinario o por medios telem�ticos y enviarle invitaciones para eventos y felicitaciones en fechas se�aladas. Entenderemos que presta su consentimiento t�cito para este tratamiento de datos si en el plazo de un mes no expresa su voluntad en contra. Podr� ejercer sus derechos de acceso, rectificaci�n, cancelaci�n y oposici�n ante el Responsable del Fichero, la Diputaci�n Provincial de Ciudad Real en C/ Toledo, 17, 13071 Ciudad Real - Espa�a, siempre acreditando conforme a Derecho su identidad en la comunicaci�n. En cumplimiento de la L.O. 34/2002 le informamos de que puede revocar en cualquier momento el consentimiento que nos otorga dirigi�ndose a la direcci�n citada ut supra o bien al correo electr�nico lopd@dipucr.es o bien por telefono al numero gratuito 900 714 080.'"/>
	
	<xsl:template match="/">
		
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/NIF">
			<div class="col" xml:space="preserve">
				<label class="gr" style="position: relative; width:170px;">
					<xsl:value-of select="$lang.docIdentidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:460px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/NIF"/>
				</label>
				<br/>
			</div>
			<div class="col">
				<label class="gr" style="position: relative; width:170px;">
					<xsl:value-of select="$lang.nombre"/>:	
				</label>
				<label class="gr" style="position: relative; width:460px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="not(Solicitud_Registro/Datos_Firmados/Datos_Especificos/NIF)">
			<div class="col" xml:space="preserve">
				<label class="gr" style="position: relative; width:170px;">
					<xsl:value-of select="$lang.docIdentidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:460px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Documento_Identificacion/Numero"/>
				</label>
				<br/>
			</div>
			<div class="col">
				<label class="gr" style="position: relative; width:170px;">
					<xsl:value-of select="$lang.nombre"/>:	
				</label>
				<label class="gr" style="position: relative; width:460px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
				</label>
				<br/>
			</div>
		</xsl:if>
			
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.tipoCert"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Descripcion_Tipo_Cert"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.email"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Correo_Electronico"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.observaciones"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Observaciones"/>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.numRegistro"/>:	
			</label>
			<label class="gr" style="position: relative; width:460px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Registro/Numero_Registro"/>
			</label>
			<input type="hidden" id="numeroRegistro" name="numeroRegistro">
				<xsl:attribute name="value"><xsl:value-of select="Solicitud_Registro/Datos_Registro/Numero_Registro"/></xsl:attribute>
			</input>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.fechaPresentacion"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:460px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Registro/Hora_Presentacion"/>, 
				<xsl:call-template name="transformaFecha">
				   <xsl:with-param name="node" select="Solicitud_Registro/Datos_Registro/Fecha_Presentacion"/>
				</xsl:call-template>
			</label>
			<br/>
		</div>
		<div class="col">
			<label class="gr" style="position: relative; width:170px;">
				<xsl:value-of select="$lang.fechaEfectiva"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative;  width:460px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Registro/Hora_Efectiva"/>, 
				<xsl:call-template name="transformaFecha">
				   <xsl:with-param name="node" select="Solicitud_Registro/Datos_Registro/Fecha_Efectiva"/>
				</xsl:call-template>
			</label>
			<br/>
		</div>
		<br/>
		<div style="color: grey; text-align:justify">
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
				<xsl:value-of select="$lang.info_legal"/>
			</label>
		</div>
	</xsl:template>
	<xsl:template name="transformaFecha">
		<xsl:param name="node"/>
		<xsl:variable name="date" select="concat(substring(string($node),9,2),'-',substring(string($node),6,2),'-',substring(string($node),1,4))"/>
		<xsl:value-of select="$date"/>
	</xsl:template>
</xsl:stylesheet>