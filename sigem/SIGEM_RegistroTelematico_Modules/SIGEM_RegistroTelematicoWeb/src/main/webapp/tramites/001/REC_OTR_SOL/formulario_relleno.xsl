<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:include href="../templates_comunes.xsl" />

<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:include href="../REC_COMUNES/templates_comunes.xsl" />
	<xsl:include href="../REC_COMUNES/template_IBAN.xsl" />

	<xsl:variable name="lang.datosIdentificativos" select="'Datos identificativos'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>

	<xsl:variable name="lang.pres_nif" select="'NIF Presentador'"/>
	<xsl:variable name="lang.pres_nombre" select="'Nombre Presentador'"/>
	<xsl:variable name="lang.repres_nif" select="'NIF/CIF Representante'"/>
	<xsl:variable name="lang.repres_nombre" select="'Nombre Representante'"/>
	<xsl:variable name="lang.nif" select="'NIF/CIF Contribuyente'"/>
	<xsl:variable name="lang.nombre" select="'Nombre Contribuyente'"/>
	<xsl:variable name="lang.direccion" select="'Direcci�n'"/>
	<xsl:variable name="lang.calle" select="'Calle'"/>
	<xsl:variable name="lang.numero" select="'Numero'"/>
	<xsl:variable name="lang.escalera" select="'Escalera'"/>
	<xsl:variable name="lang.planta_puerta" select="'Planta/Puerta'"/>
	<xsl:variable name="lang.c_postal" select="'C�digo Postal'"/>

	<xsl:variable name="lang.solicita" select="'Solicita'"/>
	<xsl:variable name="lang.expone" select="'Expone'"/>

	<xsl:variable name="lang.cuenta" select="'Cuenta'"/>

	<xsl:variable name="lang.datosBancarios" select="'Datos Bancarios'"/>
	<xsl:variable name="lang.titular" select="'Titular de la cuenta:'"/>

	<xsl:variable name="lang.entidad" select="'Entidad:'"/>
	<xsl:variable name="lang.sucursal" select="'Sucursal:'"/>
	<xsl:variable name="lang.dc" select="'D.C.:'"/>
	<xsl:variable name="lang.ncc" select="'N�C.C.:'"/>

	<xsl:variable name="lang.telefono" select="'Tel�fono'"/>

	<xsl:variable name="lang.anexos" select="'Anexos'"/>

	
	<xsl:template match="/">
		<xsl:call-template name="DATOS_SOLICITUD_RELLENOS" />
		<br/>
		<div class="col">
			<label class="gr" style="position: relative; width:350px;">
				<b><xsl:value-of select="$lang.datosSolicitud"/></b>	
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/expone">
			<div class="col" style="height: 35px;">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.expone"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/expone"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<br/>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/solicita">
			<div class="col" style="height: 35px;">
				<label class="gr" style="position: relative; width:150px;">
					<xsl:value-of select="$lang.solicita"/>:	
				</label>
				<label class="gr" style="position: relative; width:500px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/solicita"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<br/>
		<div class="col">
			<b>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="$lang.datosBancarios"/>:	
				</label>
			</b>
		</div>
		<div class="col"> 
			<xsl:call-template name="CUENTA_CORRIENTE_IBAN_RELLENOS" />
		</div>
		<br/>
		<xsl:call-template name="TEXTO_LEGAL_COMUN_RELLENO" />

		<xsl:call-template name="TEXTO_DATOS_PERSONALES_COMUN_RELLENO" />

		<xsl:call-template name="TEXTO_AUTOFIRMA_COMUN_RELLENO" />

	</xsl:template>
</xsl:stylesheet>
