<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/xtreeUnidades.css">
	<link href="css/estilos.css" rel="stylesheet" type="text/css" />
	<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
	<link href="css/directorioComun.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript">
		function returnToTop(idOficina, nombre){
			window.parent.opener.document.forms[0].codEntidadReg.value = idOficina;
			window.parent.opener.document.forms[0].nameEntidadReg.value = nombre;
			window.parent.close();
		}

		function buscarOficinas(){
			var codigo = trim(document.getElementById("codigo").value);
			var nombre = trim(document.getElementById("nombre").value);
			if(codigo=="" && nombre=="")
			{
				alert('<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.required.search.criterio"/>');
			}else{
				document.forms[0].submit();
			}
		}

		function trim (myString)
		{
			return myString.replace(/^\s+/g,'').replace(/\s+$/g,'')
		}
	</script>
</head>
<body>


<jsp:include page="includes/errores.jsp"/>

<html:form action="/busquedaOficinasDirectorioComun.do" method="post">
	<div id="search">
		<p>
			<label for="codigo" class="labelBusquedaDC"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.listado.codigo"/></label>
			<html:text styleId="codigo" styleClass="textinput" property="codigo" title="C�digo"/>
		</p>
		<p>
			<label for="nombre" class="labelBusquedaDC"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.listado.nombre"/></label>
			<html:text styleId="nombre" styleClass="textinput" property="nombre" title="Nombre"/>
		</p>
		<p>
			<table align="center">
				<tr>
					<td align="right" class="col_buscar" onclick="buscarOficinas();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.buscar"/></td>
					<td align="right" class="col_cancelar" onclick="cancelarBusqueda();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
		</p>
	</div>
</html:form>

<div id="results"  style="overflow:auto; height:230px; width:100% ">
	<logic:present name="listaOficinas">
		<display:table id="oficina" name="listaOficinas" class="displaytable"
			pagesize="10" requestURI="/busquedaOficinasDirectorioComun.do"
			defaultsort="1" defaultorder="ascending">
			<display:column title="Codigo" sortable="true">
				<a href="#"
					onclick="javascript:returnToTop('<bean:write name="oficina" property="id" />','<bean:write name="oficina" property="nombre" />');return false;"><bean:write
					name="oficina" property="id" /></a>
			</display:column>
			<display:column title="Nombre" sortable="true">
				<a href="#"
					onclick="javascript:returnToTop('<bean:write name="oficina" property="id" />', '<bean:write name="oficina" property="nombre" />');return false;"><bean:write
					name="oficina" property="nombre" /></a>
			</display:column>
			<display:column title="Responsable" sortable="true">
				<a href="#"
					onclick="javascript:returnToTop('<bean:write name="oficina" property="id" />', '<bean:write name="oficina" property="nombre" />');return false;"><bean:write
					name="oficina" property="nombreUnidadResponsable" /></a>
			</display:column>
		</display:table>
	</logic:present>
</div>
</body>
</html>
