<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:include href="templates_comunes.xsl" />

<xsl:include href="../templates_comunes.xsl" /><xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.titulo" select="'Solicitud de ayudas al Plan de Emergencia'"/>

	<xsl:variable name="lang.datosBeneficiario" select="'Datos del beneficiario'"/>
	<xsl:variable name="lang.nif" select="'NIF del Trabajador/a Social'"/>
	<xsl:variable name="lang.nombre" select="'Apellidos y Nombre del Trabajador/a Social'"/>
	<xsl:variable name="lang.ciudad" select="'Ciudad'"/>

	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>
	<xsl:variable name="lang.convocatoriaObligatoria" select="'Convocatorias'"/>

	<xsl:variable name="lang.id_nif" select="'NIF del Trabajador/a Social'"/>
	<xsl:variable name="lang.id_nombre" select="'Nombre del Trabajador/a Social'"/>

	<xsl:variable name="lang.nif" select="'NIF/NIE'"/>
	<xsl:variable name="lang.nombre" select="'Apellidos y Nombre'"/>

	<xsl:variable name="lang.ciudad" select="'Localidad'"/>

	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero, pulse el bot�n examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.anexarInfo3" select="'3.- En caso de no poder escanear el documento enviarlo al FAX: 926 29 56 20'"/>
	<xsl:variable name="lang.anexarInfo4" select="'4.- Indicar los documentos que se anexan.'"/>
	<xsl:variable name="lang.documento" select="'Conjunto de anexos de la solicitud'"/>
	<xsl:variable name="lang.documentoTipo1" select="'Archivo DOC/ODT'"/>
	<xsl:variable name="lang.documentoTipo2" select="'Archivo PDF'"/>
	<xsl:variable name="lang.documentoTipo3" select="'Archivo ZIP'"/>
		
	<xsl:template match="/" xmlns:java="http://xml.apache.org/xslt/java">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			//----------------------------
			var validar = new Array(9);

			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.id_nif"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.id_nombre"/>');

			validar[2] = new Array('nif', '<xsl:value-of select="$lang.nif"/>');
			validar[3] = new Array('nombre','<xsl:value-of select="$lang.nombre"/>');
			validar[4] = new Array('ciudad','<xsl:value-of select="$lang.ciudad"/>');
			validar[5] = new Array('convocatoria','<xsl:value-of select="$lang.convocatoriaObligatoria"/>');
			validar[6] = new Array('nfamiliar','N�mero de miembros de la unidad familiar');
			validar[7] = new Array('menores3anios','Menores de 3 a�os inclusive');
			validar[8] = new Array('propuesta1_importe','Importe');
			

			//Array con los datos especificos del formulario -> -> ('id_campo','tag_xml')
			//----------------------------------------------
			var especificos = new Array(133);
			
			especificos[0] = new Array('nif','nif');
			especificos[1] = new Array('nombre','nombre');
			especificos[2] = new Array('ciudad','ciudad');
			especificos[3] = new Array('nfamiliar','nfamiliar');
			especificos[4] = new Array('menores3anios','menores3anios');
			especificos[5] = new Array('total','total');
			especificos[6] = new Array('totalFamilia1','totalFamilia1');
			especificos[7] = new Array('totalFamilia2','totalFamilia2');
			especificos[8] = new Array('totalFamilia3','totalFamilia3');
			especificos[9] = new Array('colectivo1','colectivo1');
			especificos[10] = new Array('colectivo2','colectivo2');
			especificos[11] = new Array('colectivo3','colectivo3');
			especificos[12] = new Array('colectivo4','colectivo4');

			especificos[13] = new Array('vivienda1','vivienda1');
			especificos[14] = new Array('vivienda2','vivienda2');
			especificos[15] = new Array('vivienda3','vivienda3');
			especificos[16] = new Array('viviendaEuros2','viviendaEuros2');
			especificos[17] = new Array('vivienda3','vivienda3');
			especificos[18] = new Array('viviendaEuros3','viviendaEuros3');
			especificos[19] = new Array('vivienda4','vivienda4');
			especificos[20] = new Array('diagnostico','diagnostico');

			especificos[21] = new Array('par_1','par_1');
			especificos[22] = new Array('par_2','par_2');
			especificos[23] = new Array('par_3','par_3');
			especificos[24] = new Array('par_4','par_4');
			especificos[25] = new Array('par_5','par_5');
			especificos[26] = new Array('par_6','par_6');
			especificos[27] = new Array('par_7','par_7');
			especificos[28] = new Array('par_8','par_8');
			especificos[29] = new Array('par_9','par_9');
			especificos[30] = new Array('par_10','par_10');
			especificos[31] = new Array('par_11','par_11');
			especificos[32] = new Array('par_12','par_12');
			especificos[33] = new Array('par_13','par_13');
			especificos[34] = new Array('par_14','par_14');
			especificos[35] = new Array('par_15','par_15');
			especificos[36] = new Array('par_16','par_16');
			especificos[37] = new Array('par_17','par_17');
			especificos[38] = new Array('par_18','par_18');
			especificos[39] = new Array('par_19','par_19');
			especificos[40] = new Array('par_20','par_20');

			especificos[41] = new Array('edad_1','edad_1');
			especificos[42] = new Array('edad_2','edad_2');
			especificos[43] = new Array('edad_3','edad_3');
			especificos[44] = new Array('edad_4','edad_4');
			especificos[45] = new Array('edad_5','edad_5');
			especificos[46] = new Array('edad_6','edad_6');
			especificos[47] = new Array('edad_7','edad_7');
			especificos[48] = new Array('edad_8','edad_8');
			especificos[49] = new Array('edad_9','edad_9');
			especificos[50] = new Array('edad_10','edad_10');
			especificos[51] = new Array('edad_11','edad_11');
			especificos[52] = new Array('edad_12','edad_12');
			especificos[53] = new Array('edad_13','edad_13');
			especificos[54] = new Array('edad_14','edad_14');
			especificos[55] = new Array('edad_15','edad_15');
			especificos[56] = new Array('edad_16','edad_16');
			especificos[57] = new Array('edad_17','edad_17');
			especificos[58] = new Array('edad_18','edad_18');
			especificos[59] = new Array('edad_19','edad_19');
			especificos[60] = new Array('edad_20','edad_20');

			especificos[61] = new Array('prof_1','prof_1');
			especificos[62] = new Array('prof_2','prof_2');
			especificos[63] = new Array('prof_3','prof_3');
			especificos[64] = new Array('prof_4','prof_4');
			especificos[65] = new Array('prof_5','prof_5');
			especificos[66] = new Array('prof_6','prof_6');
			especificos[67] = new Array('prof_7','prof_7');
			especificos[68] = new Array('prof_8','prof_8');
			especificos[69] = new Array('prof_9','prof_9');
			especificos[70] = new Array('prof_10','prof_10');
			especificos[71] = new Array('prof_11','prof_11');
			especificos[72] = new Array('prof_12','prof_12');
			especificos[73] = new Array('prof_13','prof_13');
			especificos[74] = new Array('prof_14','prof_14');
			especificos[75] = new Array('prof_15','prof_15');
			especificos[76] = new Array('prof_16','prof_16');
			especificos[77] = new Array('prof_17','prof_17');
			especificos[78] = new Array('prof_18','prof_18');
			especificos[79] = new Array('prof_19','prof_19');
			especificos[80] = new Array('prof_20','prof_20');
		
			especificos[81] = new Array('sit_1','sit_1');
			especificos[82] = new Array('sit_2','sit_2');
			especificos[83] = new Array('sit_3','sit_3');
			especificos[84] = new Array('sit_4','sit_4');
			especificos[85] = new Array('sit_5','sit_5');
			especificos[86] = new Array('sit_6','sit_6');
			especificos[87] = new Array('sit_7','sit_7');
			especificos[88] = new Array('sit_8','sit_8');
			especificos[89] = new Array('sit_9','sit_9');
			especificos[90] = new Array('sit_10','sit_10');
			especificos[91] = new Array('sit_11','sit_11');
			especificos[92] = new Array('sit_12','sit_12');
			especificos[93] = new Array('sit_13','sit_13');
			especificos[94] = new Array('sit_14','sit_14');
			especificos[95] = new Array('sit_15','sit_15');
			especificos[96] = new Array('sit_16','sit_16');
			especificos[97] = new Array('sit_17','sit_17');
			especificos[98] = new Array('sit_18','sit_18');
			especificos[99] = new Array('sit_19','sit_19');
			especificos[100] = new Array('sit_20','sit_20');

			especificos[101] = new Array('ing_1','ing_1');
			especificos[102] = new Array('ing_2','ing_2');
			especificos[103] = new Array('ing_3','ing_3');
			especificos[104] = new Array('ing_4','ing_4');
			especificos[105] = new Array('ing_5','ing_5');
			especificos[106] = new Array('ing_6','ing_6');
			especificos[107] = new Array('ing_7','ing_7');
			especificos[108] = new Array('ing_8','ing_8');
			especificos[109] = new Array('ing_9','ing_9');
			especificos[110] = new Array('ing_10','ing_10');
			especificos[111] = new Array('ing_11','ing_11');
			especificos[112] = new Array('ing_12','ing_12');
			especificos[113] = new Array('ing_13','ing_13');
			especificos[114] = new Array('ing_14','ing_14');
			especificos[115] = new Array('ing_15','ing_15');
			especificos[116] = new Array('ing_16','ing_16');
			especificos[117] = new Array('ing_17','ing_17');
			especificos[118] = new Array('ing_18','ing_18');
			especificos[119] = new Array('ing_19','ing_19');
			especificos[120] = new Array('ing_20','ing_20');

			especificos[121] = new Array('propuesta1_fecha','propuesta1_fecha');
			especificos[122] = new Array('propuesta1_meses','propuesta1_meses');
			especificos[123] = new Array('propuesta1_importe','propuesta1_importe');

			especificos[124] = new Array('propuesta2_fecha','propuesta2_fecha');
			especificos[125] = new Array('propuesta2_meses','propuesta2_meses');
			especificos[126] = new Array('propuesta2_importe','propuesta2_importe');
			
			especificos[127] = new Array('convocatoria','convocatoria');

			especificos[128] = new Array('documentoIdentidad','documentoIdentidad');
			especificos[129] = new Array('nombreSolicitante','nombreSolicitante');

			especificos[130] = new Array('trimestre','trimestre');
			especificos[131] = new Array('tipoAyuda','tipoAyuda');
			especificos[132] = new Array('documentos','documentos');


			//especificos[127] = new Array('propuesta3_fecha','propuesta3_fecha');
			//especificos[128] = new Array('propuesta3_meses','propuesta3_meses');
			//especificos[129] = new Array('propuesta3_importe','propuesta3_importe');
			
			
			//especificos[130] = new Array('nfamiliar2','nfamiliar2');
			//especificos[131] = new Array('menores3anios2','menores3anios2');
			//especificos[132] = new Array('total2','total2');			

			//especificos[133] = new Array('diagnostico2','diagnostico2');

			//especificos[134] = new Array('par2_1','par2_1');
			//especificos[135] = new Array('par2_2','par2_2');
			//especificos[136] = new Array('par2_3','par2_3');
			//especificos[137] = new Array('par2_4','par2_4');
			//especificos[138] = new Array('par2_5','par2_5');
			//especificos[139] = new Array('par2_6','par2_6');
			//especificos[140] = new Array('par2_7','par2_7');
			//especificos[141] = new Array('par2_8','par2_8');
			//especificos[142] = new Array('par2_9','par2_9');
			//especificos[143] = new Array('par2_10','par2_10');
			//especificos[144] = new Array('par2_11','par2_11');
			//especificos[145] = new Array('par2_12','par2_12');
			//especificos[146] = new Array('par2_13','par2_13');
			//especificos[147] = new Array('par2_14','par2_14');
			//especificos[148] = new Array('par2_15','par2_15');
			//especificos[149] = new Array('par2_16','par2_16');
			//especificos[150] = new Array('par2_17','par2_17');
			//especificos[151] = new Array('par2_18','par2_18');
			//especificos[152] = new Array('par2_19','par2_19');
			//especificos[153] = new Array('par2_20','par2_20');

			//especificos[154] = new Array('edad2_1','edad2_1');
			//especificos[155] = new Array('edad2_2','edad2_2');
			//especificos[156] = new Array('edad2_3','edad2_3');
			//especificos[157] = new Array('edad2_4','edad2_4');
			//especificos[158] = new Array('edad2_5','edad2_5');
			//especificos[159] = new Array('edad2_6','edad2_6');
			//especificos[160] = new Array('edad2_7','edad2_7');
			//especificos[161] = new Array('edad2_8','edad2_8');
			//especificos[162] = new Array('edad2_9','edad2_9');
			//especificos[163] = new Array('edad2_10','edad2_10');
			//especificos[164] = new Array('edad2_11','edad2_11');
			//especificos[165] = new Array('edad2_12','edad2_12');
			//especificos[166] = new Array('edad2_13','edad2_13');
			//especificos[167] = new Array('edad2_14','edad2_14');
			//especificos[168] = new Array('edad2_15','edad2_15');
			//especificos[169] = new Array('edad2_16','edad2_16');
			//especificos[170] = new Array('edad2_17','edad2_17');
			//especificos[171] = new Array('edad2_18','edad2_18');
			//especificos[172] = new Array('edad2_19','edad2_19');
			//especificos[173] = new Array('edad2_20','edad2_20');

			//especificos[174] = new Array('prof2_1','prof2_1');
			//especificos[175] = new Array('prof2_2','prof2_2');
			//especificos[176] = new Array('prof2_3','prof2_3');
			//especificos[177] = new Array('prof2_4','prof2_4');
			//especificos[178] = new Array('prof2_5','prof2_5');
			//especificos[179] = new Array('prof2_6','prof2_6');
			//especificos[180] = new Array('prof2_7','prof2_7');
			//especificos[181] = new Array('prof2_8','prof2_8');
			//especificos[182] = new Array('prof2_9','prof2_9');
			//especificos[183] = new Array('prof2_10','prof2_10');
			//especificos[184] = new Array('prof2_11','prof2_11');
			//especificos[185] = new Array('prof2_12','prof2_12');
			//especificos[186] = new Array('prof2_13','prof2_13');
			//especificos[187] = new Array('prof2_14','prof2_14');
			//especificos[188] = new Array('prof2_15','prof2_15');
			//especificos[189] = new Array('prof2_16','prof2_16');
			//especificos[190] = new Array('prof2_17','prof2_17');
			//especificos[191] = new Array('prof2_18','prof2_18');
			//especificos[192] = new Array('prof2_19','prof2_19');
			//especificos[193] = new Array('prof2_20','prof2_20');

			//especificos[194] = new Array('sit2_1','sit2_1');
			//especificos[195] = new Array('sit2_2','sit2_2');
			//especificos[196] = new Array('sit2_3','sit2_3');
			//especificos[197] = new Array('sit2_4','sit2_4');
			//especificos[198] = new Array('sit2_5','sit2_5');
			//especificos[199] = new Array('sit2_6','sit2_6');
			//especificos[200] = new Array('sit2_7','sit2_7');
			//especificos[201] = new Array('sit2_8','sit2_8');
			//especificos[202] = new Array('sit2_9','sit2_9');
			//especificos[203] = new Array('sit2_10','sit2_10');
			//especificos[204] = new Array('sit2_11','sit2_11');
			//especificos[205] = new Array('sit2_12','sit2_12');
			//especificos[206] = new Array('sit2_13','sit2_13');
			//especificos[207] = new Array('sit2_14','sit2_14');
			//especificos[208] = new Array('sit2_15','sit2_15');
			//especificos[209] = new Array('sit2_16','sit2_16');
			//especificos[210] = new Array('sit2_17','sit2_17');
			//especificos[211] = new Array('sit2_18','sit2_18');
			//especificos[212] = new Array('sit2_19','sit2_19');
			//especificos[213] = new Array('sit2_20','sit2_20');

			//especificos[214] = new Array('ing2_1','ing2_1');
			//especificos[215] = new Array('ing2_2','ing2_2');
			//especificos[216] = new Array('ing2_3','ing2_3');
			//especificos[217] = new Array('ing2_4','ing2_4');
			//especificos[218] = new Array('ing2_5','ing2_5');
			//especificos[219] = new Array('ing2_6','ing2_6');
			//especificos[220] = new Array('ing2_7','ing2_7');
			//especificos[221] = new Array('ing2_8','ing2_8');
			//especificos[222] = new Array('ing2_9','ing2_9');
			//especificos[223] = new Array('ing2_10','ing2_10');
			//especificos[224] = new Array('ing2_11','ing2_11');
			//especificos[225] = new Array('ing2_12','ing2_12');
			//especificos[226] = new Array('ing2_13','ing2_13');
			//especificos[227] = new Array('ing2_14','ing2_14');
			//especificos[228] = new Array('ing2_15','ing2_15');
			//especificos[229] = new Array('ing2_16','ing2_16');
			//especificos[230] = new Array('ing2_17','ing2_17');
			//especificos[231] = new Array('ing2_18','ing2_18');
			//especificos[232] = new Array('ing2_19','ing2_19');
			//especificos[233] = new Array('ing2_20','ing2_20');

			//especificos[234] = new Array('propuesta1_fecha2','propuesta1_fecha2');
			//especificos[235] = new Array('propuesta1_meses2','propuesta1_meses2');
			//especificos[236] = new Array('propuesta1_importe2','propuesta1_importe2');

			//especificos[237] = new Array('propuesta2_fecha2','propuesta2_fecha2');
			//especificos[238] = new Array('propuesta2_meses2','propuesta2_meses2');
			//especificos[239] = new Array('propuesta2_importe2','propuesta2_importe2');

			//especificos[240] = new Array('propuesta3_fecha2','propuesta3_fecha2');
			//especificos[241] = new Array('propuesta3_meses2','propuesta3_meses2');
			//especificos[242] = new Array('propuesta3_importe2','propuesta3_importe2');


			<xsl:call-template name="ADD_ROW" />
			
			<xsl:call-template name="VALIDACIONES" />
			
			//Array de validaciones
			//----------------------------------------------
			var validarNumero;
			function verificacionesEspecificas() {
				document.getElementById('nombresolihidden').value = document.getElementById('nombreSolicitante').value;
				document.getElementById('nifsolihidden').value = document.getElementById('documentoIdentidad').value;

				var d = new Date();
				var n = d.getMonth();
				
				if ( n == 0 || n==1 || n==2){
					document.getElementById('trimestre').value = 'PRIMER TRIMESTRE';
				}
				else if ( n == 3 || n== 4 || n== 5){
					document.getElementById('trimestre').value = 'SEGUNDO TRIMESTRE';
				}
				else if ( n == 6 || n==7 || n==8){
					document.getElementById('trimestre').value = 'TERCER TRIMESTRE';
				}
				else{
					document.getElementById('trimestre').value = 'CUARTO TRIMESTRE';
				}

				if(!validaNIFBeneficiario()) return false;

				if(document.getElementById('ciudad').value == '000' || document.getElementById('ciudad').value == ''){
					alert('Debe seleccionar una localidad');
					document.getElementById('ciudad').focus();
					return false;
				}

				if(document.getElementById('convocatoria').value=='-------'){
					alert('Debe seleccionar una convocatoria');
					document.getElementById('convocatoria').focus();
					return false;
				}

				if(document.getElementById('nfamiliar').value != ''){
					if(!numerico(document.getElementById('nfamiliar').value)){
						alert('El campo N�mero de miembros de la unidad familiar debe ser num�rico');
						document.getElementById('nfamiliar').focus();
						return false;
					}
				}

				if(document.getElementById('total').value != ''){
					if(!numerico(document.getElementById('total').value)){
						alert('El campo Total de Ingresos/Mes debe ser num�rico');
						document.getElementById('total').focus();
						return false;
					}
				}

				if(document.getElementById('viviendaEuros2').value != ''){
					if(!numerico(document.getElementById('viviendaEuros2').value)){
						alert('El campo del Importe de la Hipoteca debe ser num�rico');
						document.getElementById('viviendaEuros2').focus();
						return false;
					}
				}

				if(document.getElementById('viviendaEuros3').value != ''){
					if(!numerico(document.getElementById('viviendaEuros3').value)){
						alert('El campo del Importe del Alquiler debe ser num�rico');
						document.getElementById('viviendaEuros3').focus();
						return false;
					}
				}

				if(document.getElementById('propuesta1_importe').value != ''){
					if(!numerico(document.getElementById('propuesta1_importe').value)){
						alert('El campo Importe de la propuesta debe ser num�rico');
						document.getElementById('propuesta1_importe').focus();
						return false;
					}
				}

				if(document.getElementById('propuesta2_importe').value != ''){
					if(!numerico(document.getElementById('propuesta2_importe').value)){
						alert('El campo Importe de la propuesta debe ser num�rico');
						document.getElementById('propuesta2_importe').focus();
						return false;
					}
				}				

				//if(document.getElementById('menores3anios').value != 'SI'){
				//	if(document.getElementById('menores3anios').value != 'NO'){
				//		document.getElementById('menores3anios').value='NO';
				//	}
				//}

				var informe = document.getElementById('diagnostico').value;			
				var resultado = informe.replace(/[^a-zA-Z 0-9. , / - ; �� �� �� �� �� �� �� \n � $ \t]+/g,' ');
				document.getElementById('diagnostico').value= resultado;

				return true;
			}

			function validaNIFBeneficiario(){
				var valorNif = document.getElementById('nif');
				var validaNif = valida_nif_cif_nie(valorNif);
				if(validaNif != 1)
					if(validaNif != 2)
						if(validaNif != 3){
							alert('El NIF del beneficiario es incorrecto');
							valorNif.focus();			
							return false;
						}
				return true;
			}

			function valida_nif_cif_nie(campoNif){
				var a = escape(campoNif.value.toUpperCase());
				var temp=a.toUpperCase();
				campoNif.value = temp;
				var cadenadni="TRWAGMYFPDXBNJZSQVHLCKE";
 
				if (temp!=''){
				//si no tiene un formato valido devuelve error
					if ((!/^[A-Z]{1}[0-9]{7}[A-Z0-9]{1}$/.test(temp)))
						if( !/^[T]{1}[A-Z0-9]{8}$/.test(temp))
							if(!/^[0-9]{8}[A-Z]{1}$/.test(temp)){
								return 0;
							}
 
					//comprobacion de NIFs estandar
					if (/^[0-9]{8}[A-Z]{1}$/.test(temp)){
						posicion = a.substring(8,0) % 23;
						letra = cadenadni.charAt(posicion);
						var letradni=temp.charAt(8);
						if (letra == letradni){
						   	return 1;
						}
						else{
							return -1;
						}
					}
 
					//algoritmo para comprobacion de codigos tipo CIF
					suma = parseInt(a.charAt(2))+parseInt(a.charAt(4))+parseInt(a.charAt(6));
					var i = 1;
					var fin = false;
					while (!fin){
						temp1 = 2 * parseInt(a.charAt(i));
						temp1 += '';
						temp1 = temp1.substring(0,1);
						temp2 = 2 * parseInt(a.charAt(i));
						temp2 += '';
						temp2 = temp2.substring(1,2);
						if (temp2 == ''){
							temp2 = '0';
						}
						suma += (parseInt(temp1) + parseInt(temp2));
						i = i+2;
						if(i!=8 || i!=9 || i==100){
							fin = true;
						}
					}
					suma += '';
					n = 10 - parseInt(suma.substring(suma.length-1, suma.length));
					//comprobacion de NIFs especiales (se calculan como CIFs)
					if (/^[KLM]{1}/.test(temp)){
						if (a.charAt(8) == String.fromCharCode(64 + n)){
							return 1;
						}
						else{
							return -1;
						}
					}
	 
					//comprobacion de CIFs
					if (/^[ABCDEFGHJNPQRSUVW]{1}/.test(temp)){
						return validaCIF(a);
					}
 
					//comprobacion de NIEs
					//T
					if (/^[T]{1}/.test(temp)){
						if (a.charAt(8) == /^[T]{1}[A-Z0-9]{8}$/.test(temp)){
							return 3;
						}
						else{
							return -3;
						}
					}
			 
					//XYZ
					if (/^[XYZ]{1}/.test(temp)){
 						temp = temp.replace('X','0')
						temp = temp.replace('Y','1')
 						temp = temp.replace('Z','2')
 						pos = temp.substring(0, 8)% 23;
						if (a.charAt(8) == cadenadni.substring(pos, pos + 1)){
 							return 3;
 						}
 						else{
 							return -3;
						}
					}
				}
				return 0;
			}
		</script>

		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
		<xsl:call-template name="BOTON_LIMPIAR_FORMULARIO" />
   		<br/>
		<xsl:call-template name="DATOS_SOLICITANTE" />
		
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosBeneficiario"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.nif"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">width:100px; </xsl:attribute>
					<xsl:attribute name="name">nif</xsl:attribute>
					<xsl:attribute name="id">nif</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/nif"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">width:490px; </xsl:attribute>
					<xsl:attribute name="name">nombre</xsl:attribute>
					<xsl:attribute name="id">nombre</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/nombre"/></xsl:attribute>
				</input>
			</div>

			<xsl:variable name="fileAyuntam" select="java:es.dipucr.sigem.registroTelematicoWeb.formulario.common.XmlCargaDatos.getDatosTablaValidacionConSustituto('REC_VLDTBL_MUNICIPIOS','001')"/>
			<xsl:variable name="b" select="document($fileAyuntam)"/>

   			<div class="col">	
				<label class="gr">
					<xsl:attribute name="style">width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.ciudad"/>:*
				</label>
				<select class="gr">
					<xsl:attribute name="style">border:none; width:400px;color:#006699;</xsl:attribute>
					<xsl:attribute name="name">ciudad</xsl:attribute>
					<xsl:attribute name="id">ciudad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/ciudad"/></xsl:attribute>
					<xsl:for-each select="$b/listado/dato">
						<option>
							<xsl:attribute name="value"><xsl:value-of select="valor"/></xsl:attribute>
							<xsl:value-of select="sustituto"/>
						</option>
					</xsl:for-each>
				</select>	
			</div>
		</div>

		<xsl:call-template name="DATOS_FAMILIA" />
		<xsl:call-template name="DATOS_PROPUESTA_1" />
		<!--<xsl:call-template name="DATOS_FAMILIA2" />-->

		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
			<div class="col">
				<label class="gr">
			   		<xsl:attribute name="style">width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
					<xsl:value-of select="$lang.anexarInfo3"/><br/>
					<xsl:value-of select="$lang.anexarInfo4"/><br/><br/>
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:620px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">documentos</xsl:attribute>
					<xsl:attribute name="id">documentos</xsl:attribute>
					<xsl:attribute name="rows">5</xsl:attribute>
					<xsl:attribute name="cols">50</xsl:attribute>
					<xsl:attribute name="onkeypress">if(this.value.length&gt;25500){ alert('Tama�o m�ximo de campo alcanzado'); this.value = this.value.substr(0,25500);}</xsl:attribute>
					<xsl:attribute name="onkeyup">if(this.value.length&gt;25500){ alert('Tama�o m�ximo de campo alcanzado'); this.value = this.value.substr(0,25500);}</xsl:attribute>
					<xsl:value-of select="Datos_Registro/datos_especificos/documentos"/>
				</textarea>
				<br/>
				<label class="gr">
					<xsl:attribute name="style">width:250px;</xsl:attribute>
					<xsl:value-of select="$lang.documento"/>:
				</label>
			</div>   			
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">width:250px;</xsl:attribute>
					<xsl:value-of select="$lang.documentoTipo2"/>:
				</label>
				<input type="file">
					<xsl:attribute name="style">width:400px; </xsl:attribute>
					<xsl:attribute name="name">SERSO_02</xsl:attribute>
					<xsl:attribute name="id">SERSO_02</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">SERSO_02_Tipo</xsl:attribute>
					<xsl:attribute name="id">SERSO_02_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf</xsl:attribute>
				</input>
			</div>
			<div class="col">
   				<label class="gr">
					<xsl:attribute name="style">width:250px;</xsl:attribute>
					<xsl:value-of select="$lang.documentoTipo3"/>:
				</label>
				<input type="file">
					<xsl:attribute name="style">width:400px; </xsl:attribute>
					<xsl:attribute name="name">SERSO_03</xsl:attribute>
					<xsl:attribute name="id">SERSO_03</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">SERSO_03_Tipo</xsl:attribute>
					<xsl:attribute name="id">SERSO_03_Tipo</xsl:attribute>
					<xsl:attribute name="value">zip</xsl:attribute>
				</input>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">width:250px;</xsl:attribute>
					<xsl:value-of select="$lang.documentoTipo1"/>:
				</label>
				<input type="file">
					<xsl:attribute name="style">width:400px; </xsl:attribute>
					<xsl:attribute name="name">SERSO_01</xsl:attribute>
					<xsl:attribute name="id">SERSO_01</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">SERSO_01_Tipo</xsl:attribute>
					<xsl:attribute name="id">SERSO_01_Tipo</xsl:attribute>
					<xsl:attribute name="value">odt,doc</xsl:attribute>
				</input>
			</div>
			</div>
			<div style="color: grey; text-align:justify">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
					Los datos personales, identificativos y de contacto, aportados mediante esta comunicaci�n se entienden facilitados voluntariamente, y ser�n incorporados a un fichero cuya finalidad es la de mantener con Vd. relaciones dentro del �mbito de las competencias de esta Administraci�n P�blica as� como informarle de nuestros servicios presentes y futuros ya sea por correo ordinario o por medios telem�ticos y enviarle invitaciones para eventos y felicitaciones en fechas se�aladas. Entenderemos que presta su consentimiento t�cito para este tratamiento de datos si en el plazo de un mes no expresa su voluntad en contra. Podr� ejercer sus derechos de acceso, rectificaci�n, cancelaci�n y oposici�n ante el Responsable del Fichero, la Diputaci�n Provincial de Ciudad Real en C/ Toledo, 17, 13071 Ciudad Real - Espa�a, siempre acreditando conforme a Derecho su identidad en la comunicaci�n. En cumplimiento de la L.O. 34/2002 le informamos de que puede revocar en cualquier momento el consentimiento que nos otorga dirigi�ndose a la direcci�n citada ut supra o bien al correo electr�nico lopd@dipucr.es o bien por telefono al numero gratuito 900 714 080.	
				</label>
			</div>

   		</div>
   		<br/>
		<input type="hidden"><xsl:attribute name="id">emailSolicitante</xsl:attribute></input>
   		<input type="hidden">
			<xsl:attribute name="name">datosEspecificos</xsl:attribute>
			<xsl:attribute name="id">datosEspecificos</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">nombresolihidden</xsl:attribute>
			<xsl:attribute name="id">nombresolihidden</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/nombresolihidden"/></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">nifsolihidden</xsl:attribute>
			<xsl:attribute name="id">nifsolihidden</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/nifsolihidden"/></xsl:attribute>
		</input>
		<input type="hidden">
			<xsl:attribute name="name">trimestre</xsl:attribute>
			<xsl:attribute name="id">trimestre</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/trimestre"/></xsl:attribute>
		</input>

<script>
document.getElementById('par_1').value='SOLICITANTE';
document.getElementById('par_2').value='C�NYUGE';
</script>
	</xsl:template>
</xsl:stylesheet>
