/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.standalone.ui.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/** Nombre de las preferencias de configuraci&oacute;n del programa.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class PreferencesManager {

	/** Objecto general de preferencias donde se guarda la configuraci&oacute;n de la
	 * aplicaci&oacute;n. */
	private static final Preferences preferences;
	static {
		preferences = Preferences.userNodeForPackage(PreferencesManager.class);
	}

	private PreferencesManager() {
		/** No permitimos la instanciacion */
	}

	//**************************************************************************************************************************
	//**************** PREFERENCIAS GENERALES **********************************************************************************

	/** Configuraci&oacute;n de <i>proxy</i> seleccionada.
	 * Un valor de <code>true</code> en esta preferencia indica que debe usarse el <i>proxy</i> configurado,
	 * y un valor de <code>false</code> que no usar&aacute;a <i>proxy</i> en las conexiones de red. */
	public static final String PREFERENCE_GENERAL_PROXY_SELECTED = "proxySelected"; //$NON-NLS-1$

	/** Host del servidor <i>proxy</i> configurado. */
	public static final String PREFERENCE_GENERAL_PROXY_HOST = "proxyHost"; //$NON-NLS-1$

	/** Puerto del servidor <i>proxy</i> configurado. */
	public static final String PREFERENCE_GENERAL_PROXY_PORT = "proxyPort"; //$NON-NLS-1$

	/** Nombre de usuario del servidor <i>proxy</i> configurado. */
	public static final String PREFERENCE_GENERAL_PROXY_USERNAME = "proxyUsername"; //$NON-NLS-1$

	/** Contraseña del servidor <i>proxy</i> configurado. */
	public static final String PREFERENCE_GENERAL_PROXY_PASSWORD = "proxyPassword"; //$NON-NLS-1$

	/** Proteger cambios en preferencias.
	 * Un valor de <code>true</code> en esta preferencia indica que deben limitarse las opciones de configuraci&oacute;n
	 * mediante interfaz gr&aacute;fico, apareciendo de forma deshabilitada (solo para consulta).
	 * Un valor de <code>false</code> habilitar&aacute; que cualquier opci&oacute;n de configuraci&oacute;n pueda ser
	 * alterada por parte del usuario mediante el interfaz gr&aacute;fico. */
	public static final String PREFERENCE_GENERAL_UNPROTECTED = "preferencesUnprotected"; //$NON-NLS-1$

	/** Evitar la confirmaci&oacute;n al cerrar la aplicaci&oacute;n o no.
	 * Un valor de <code>true</code> en esta preferencia permitir&aacute; cerrar la aplicaci&oacute;n sin ning&uacute;n di&aacute;logo
	 * de advertencia. Un valor de <code>false</code> har&aacute; que se muestre un di&aacute;logo para que el usuario confirme que
	 * realmente desea cerrar la aplicaci&oacute;n. */
	public static final String PREFERENCE_GENERAL_OMIT_ASKONCLOSE = "omitAskOnClose"; //$NON-NLS-1$

	/** No mostrar la pantalla inicial de uso de DNIe.
	 * Un valor de <code>true</code> en esta preferencia hace que nunca se muestre la pantalla inicial que sugiere al usuario
	 * el uso directo del DNIe como almac&eacute;n de claves. Un valor de <code>false</code> har&aacute; que se muestre esta pantalla
	 * al inicio siempre que se detecte un lector de tarjetas en el sistema. */
	public static final String PREFERENCE_GENERAL_HIDE_DNIE_START_SCREEN = "hideDnieStartScreen"; //$NON-NLS-1$

	/** Buscar actualizaciones al arrancar.
	 * Un valor de <code>true</code> en esta preferencia hace que, al arrancar, la aplicaci&oacute;n compruebe autom&aacute;ticamente
	 * si hay publicadas versiones m&aacute;s actuales del aplicativo. Un valor de <code>false</code> har&aacute; que no se haga
	 * esta comprobaci&oacute;n. */
	public static final String PREFERENCE_GENERAL_UPDATECHECK = "checkForUpdates"; //$NON-NLS-1$

	/** Env&iacute;a estad&iacute;sticas de uso.
	 * Un valor de <code>true</code> en esta preferencia hace que, al arrancar, la aplicaci&oacute;n env&iacute;e
	 * de forma an&oacute;nima estad&iacute;sticas de uso a <i>Google Analytics</i>. Un valor de <code>false</code>
	 * har&aacute; que no se env&iacute;e ning&uacute;n dato. */
	public static final String PREFERENCE_GENERAL_USEANALYTICS = "useAnalytics"; //$NON-NLS-1$

	/** Algoritmo de firma.
	 * Esta preferencia debe tener uno de estos valores:
	 * <ul>
	 *  <li>SHA1withRSA</li>
	 *  <li>SHA256withRSA</li>
	 *  <li>SHA384withRSA</li>
	 *  <li>SHA512withRSA</li>
	 * </ul> */
	public static final String PREFERENCE_GENERAL_SIGNATURE_ALGORITHM = "signatureAlgorithm"; //$NON-NLS-1$

	/** Formato de firma por defecto para documentos PDF. */
	public static final String PREFERENCE_GENERAL_DEFAULT_FORMAT_PDF = "defaultSignatureFormatPdf"; //$NON-NLS-1$

	/** Formato de firma por defecto para documentos OOXML de Microsoft Office. */
	public static final String PREFERENCE_GENERAL_DEFAULT_FORMAT_OOXML = "defaultSignatureFormatOoxml"; //$NON-NLS-1$

	/** Formato de firma por defecto para Facturas Electr&oacute;nicas. */
	public static final String PREFERENCE_GENERAL_DEFAULT_FORMAT_FACTURAE = "defaultSignatureFormatFacturae"; //$NON-NLS-1$

	/** Formato de firma por defecto para documentos ODF de LibreOffice / OpenOffice. */
	public static final String PREFERENCE_GENERAL_DEFAULT_FORMAT_ODF = "defaultSignatureFormatOdf"; //$NON-NLS-1$

	/** Formato de firma por defecto para documentos XML. */
	public static final String PREFERENCE_GENERAL_DEFAULT_FORMAT_XML = "defaultSignatureFormatXml"; //$NON-NLS-1$

	/** Formato de firma por defecto para ficheros binarios que no se adec&uacute;en a ninguna otra categor&iacute;a. */
	public static final String PREFERENCE_GENERAL_DEFAULT_FORMAT_BIN = "defaultSignatureFormatBin"; //$NON-NLS-1$

	/** Mostrar icono. */
	public static final String PREFERENCE_GENERAL_SHOW_ICON_INIT = "showIcon"; //$NON-NLS-1$

	//**************** FIN PREFERENCIAS GENERALES ******************************************************************************
	//**************************************************************************************************************************

	//**************************************************************************************************************************
	//**************** PREFERENCIAS DE ALMACENES DE CLAVES *********************************************************************

	/** En firma, restringir que &uacute;nicamente se puedan usar certificados de firma. */
	public static final String PREFERENCE_KEYSTORE_SIGN_ONLY_CERTS = "useOnlySignatureCertificates"; //$NON-NLS-1$

	/** En el cifrado de sobres digitales, restringir que &uacute;nicamente se puedan usar certificados de cifrado. */
	public static final String PREFERENCE_KEYSTORE_CYPH_ONLY_CERTS = "useOnlyEnciphermentCertificates"; //$NON-NLS-1$

	/** En firma, restringir que &uacute;nicamente se puedan usar certificados de seud&oacute;nimo cuando estos est&eacute;n disponibles. */
	public static final String PREFERENCE_KEYSTORE_ALIAS_ONLY_CERTS = "useOnlyAliasCertificates"; //$NON-NLS-1$

	/** En firma, restringir que &uacute;nicamente se puedan usar certificados con ciertas pol&iacute;ticas. */
	public static final String PREFERENCE_KEYSTORE_ACCEPTED_POLICIES_ONLY_CERTS = "useOnlyAcceptedPoliciesCertificate"; //$NON-NLS-1$

	/** Lista de OID de las pol&iacute;ticas de certificados aceptados para firma. */
	public static final String PREFERENCE_KEYSTORE_ACCEPTED_POLICIES_LIST = "acceptedCertPoliciesList"; //$NON-NLS-1$

	/** Almacen de claves por defecto. */
	public static final String PREFERENCE_KEYSTORE_DEFAULT_STORE = "defaultStore"; //$NON-NLS-1$

	/** Almacen de claves prioritario. */
	public static final String PREFERENCE_KEYSTORE_PRIORITARY_STORE = "prioritaryStore"; //$NON-NLS-1$

	//**************** FIN PREFERENCIAS DE ALMACENES DE CLAVES *****************************************************************
	//**************************************************************************************************************************

	//**************************************************************************************************************************
	//************************* PREFERENCIAS DE FIRMAS XAdES *******************************************************************

	/** Identificador de la pol&iacute;tica de firma para XAdES. */
	public static final String PREFERENCE_XADES_POLICY_IDENTIFIER = "xadesPolicyIdentifier"; //$NON-NLS-1$

	/** Huella digital del identificador de la pol&iacute;tica de firma para XAdES. */
	public static final String PREFERENCE_XADES_POLICY_IDENTIFIER_HASH = "xadesPolicyIdentifierHash"; //$NON-NLS-1$

	/** Algoritmo de la huella digital del identificador de la pol&iacute;tica de firma para XAdES. */
	public static final String PREFERENCE_XADES_POLICY_IDENTIFIER_HASH_ALGORITHM = "xadesPolicyIdentifierHashAlgorithm"; //$NON-NLS-1$

	/** Calificador de la pol&iacute;tica de firma para XAdES. */
	public static final String PREFERENCE_XADES_POLICY_QUALIFIER = "xadesPolicyQualifier"; //$NON-NLS-1$

	/** Ciudad de firma para firmas XAdES. */
	public static final String PREFERENCE_XADES_SIGNATURE_PRODUCTION_CITY = "xadesSignatureProductionCity"; //$NON-NLS-1$

	/** Provincia de firma para firmas XAdES. */
	public static final String PREFERENCE_XADES_SIGNATURE_PRODUCTION_PROVINCE = "xadesSignatureProductionProvince"; //$NON-NLS-1$

	/** C&oacute;digo de firma para firmas XAdES. */
	public static final String PREFERENCE_XADES_SIGNATURE_PRODUCTION_POSTAL_CODE = "xadesSignatureProductionPostalCode"; //$NON-NLS-1$

	/** Pa&iacute;s de firma para firmas XAdES. */
	public static final String PREFERENCE_XADES_SIGNATURE_PRODUCTION_COUNTRY = "xadesSignatureProductionCountry"; //$NON-NLS-1$

	/** Cargo supuesto para el firmante en firmas XAdES. */
	public static final String PREFERENCE_XADES_SIGNER_CLAIMED_ROLE = "xadesSignerClaimedRole"; //$NON-NLS-1$

	/** Formato de las firmas XAdES. */
	public static final String PREFERENCE_XADES_SIGN_FORMAT = "xadesSignFormat"; //$NON-NLS-1$

	//************************* FIN PREFERENCIAS DE FIRMAS XAdES ***************************************************************
	//**************************************************************************************************************************

	//**************************************************************************************************************************
	//************************* PREFERENCIAS DE FIRMAS PAdES *******************************************************************

	/** Motivo de la firma en firmas PAdES. */
	public static final String PREFERENCE_PADES_SIGN_REASON = "padesSignReason"; //$NON-NLS-1$

	/** Ciudad de firma para firmas PAdES. */
	public static final String PREFERENCE_PADES_SIGN_PRODUCTION_CITY = "padesSignProductionCity"; //$NON-NLS-1$

	/** Contacto del firmante en firmas PAdES. */
	public static final String PREFERENCE_PADES_SIGNER_CONTACT = "padesSignerContact"; //$NON-NLS-1$

	/** Formato de firma PAdES (PAdES B&aacute;sico o PAdES-BES). */
	public static final String PREFERENCE_PADES_FORMAT = "padesBasicFormat"; //$NON-NLS-1$

	/** Identificador de la pol&iacute;tica de firma para PAdES. */
	public static final String PREFERENCE_PADES_POLICY_IDENTIFIER = "padesPolicyIdentifier"; //$NON-NLS-1$

	/** Huella digital, en Base64, del identificador de la pol&iacute;tica de firma para PAdES. */
	public static final String PREFERENCE_PADES_POLICY_IDENTIFIER_HASH = "padesPolicyIdentifierHash"; //$NON-NLS-1$

	/** Algoritmo de la huella digital del identificador de la pol&iacute;tica de firma para PAdES. */
	public static final String PREFERENCE_PADES_POLICY_IDENTIFIER_HASH_ALGORITHM = "padesPolicyIdentifierHashAlgorithm"; //$NON-NLS-1$

	/** Calificador de la pol&iacute;tica de firma para PAdES. */
	public static final String PREFERENCE_PADES_POLICY_QUALIFIER = "padesPolicyQualifier"; //$NON-NLS-1$

	/** Nivel de certificaci&oacute;n de firma para PAdES. */
	public static final String PREFERENCE_PADES_CERTIFICATION_LEVEL = "padesCertificationLevel"; //$NON-NLS-1$

	//**************** PREFERENCIAS DE SELLOS DE TIEMPO PADES*****************************************************************

	/** Indica si la configuraci&oacute;n de sello de tiempo est&aacute; activada. */
	public static final String PREFERENCE_PADES_TIMESTAMP_CONFIGURE = "isTimeStampConfiguredPades"; //$NON-NLS-1$

	/** Tipo de sello de tiempo a aplicar en las firmas PAdES.
	 * Debe contener un valor nu&eacute;rico entre uno y tres:
	 * <ul>
	 *  <li>1 = Solo sello a nivel de firma.</li>
     *  <li>2 = Solo sello a nivel de documento.</li>
     *  <li>3 = Dos sellos, uno a nivel de firma y otro a nivel de documento.</li>
     * </ul> */
	public static final String PREFERENCE_PADES_TIMESTAMP_STAMP_TYPE = "tsType"; //$NON-NLS-1$

	/** Algoritmo de huella digital a utilizar en sellos de tiempo para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_HASHALGORITHM = "tsaHashAlgorithmsPades"; //$NON-NLS-1$

	/** URL de la autoridad de sello de tiempo para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_TSA_URL = "tsaUrlPades"; //$NON-NLS-1$

	/** Pol&iacute;tica de sellado de tiempo para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_STAMP_POLICY = "tsaPolicyPades"; //$NON-NLS-1$

	/** Nombre de usuario de la TSA para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_TSA_USR = "tsaUsrPades"; //$NON-NLS-1$

	/** Contrase&ntilde;a del usuario de la TSA para las firmas PAdES. */
	public static final String PREFERENCE_PADES_TIMESTAMP_TSA_PWD = "tsaPwdPades"; //$NON-NLS-1$

	/** OID de la extensi&oacute;n a a&ntilde;adir a la petici&oacute;n al servidor de sello de tiempo (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_EXTENSION_OID = "tsaExtensionOidPades"; //$NON-NLS-1$

	/** Valor, en binario convertido a Base64, de la extensi&oacute;n a a&ntilde;adir a la petici&oacute;n al
	 * servidor de sello de tiempo (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_EXTENSION_VALUE = "tsaExtensionValueBase64Pades"; //$NON-NLS-1$

	/** Indica si se requiere el certificado de la TSA (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_CERT_REQUIRED = "isCertRequiredPades"; //$NON-NLS-1$

	/** Indica si la extensi&oacute;n indicada en <code>tsaExtensionOid</code> es cr&iacute;tica (para las firmas PAdES). */
	public static final String PREFERENCE_PADES_TIMESTAMP_OID_CRITICAL = "isExtensionCriticalPades"; //$NON-NLS-1$

	//**************** FIN PREFERENCIAS DE SELLOS DE TIEMPO PADES*****************************************************************


	//************************* FIN PREFERENCIAS DE FIRMAS PAdES ***************************************************************
	//**************************************************************************************************************************

	//**************************************************************************************************************************
	//************************* PREFERENCIAS DE FIRMAS CAdES *******************************************************************

	/** Identificador de la pol&iacute;tica de firma para CAdES. */
	public static final String PREFERENCE_CADES_POLICY_IDENTIFIER = "cadesPolicyIdentifier"; //$NON-NLS-1$

	/** Huella digital del identificador de la pol&iacute;tica de firma para CAdES. */
	public static final String PREFERENCE_CADES_POLICY_HASH = "cadesPolicyIdentifierHash"; //$NON-NLS-1$

	/** Algoritmo de la huella digital del identificador de la pol&iacute;tica de firma para CAdES. */
	public static final String PREFERENCE_CADES_POLICY_HASH_ALGORITHM = "cadesPolicyIdentifierHashAlgorithm"; //$NON-NLS-1$

	/** Calificador de la pol&iacute;tica de firma para CAdES. */
	public static final String PREFERENCE_CADES_POLICY_QUALIFIER = "cadesPolicyQualifier"; //$NON-NLS-1$

	/** Indica si la firma CAdES debe realizarse en modo impl&iacute;cito (<i>attached</i>) o no (<i>detached</i>). */
	public static final String PREFERENCE_CADES_IMPLICIT = "cadesImplicitMode"; //$NON-NLS-1$

	//**************** PREFERENCIAS DE SELLOS DE TIEMPO CADES*****************************************************************

	/** Indica si la configuraci&oacute;n de sello de tiempo est&aacute; activada. */
	public static final String PREFERENCE_CADES_TIMESTAMP_CONFIGURE = "isTimeStampConfiguredCades"; //$NON-NLS-1$

	/** Algoritmo de huella digital a utilizar en sellos de tiempo. */
	public static final String PREFERENCE_CADES_TIMESTAMP_HASHALGORITHM = "tsaHashAlgorithmsCades"; //$NON-NLS-1$

	/** URL de la autoridad de sello de tiempo. */
	public static final String PREFERENCE_CADES_TIMESTAMP_TSA_URL = "tsaUrlCades"; //$NON-NLS-1$

	/** Pol&iacute;tica de sellado de tiempo. */
	public static final String PREFERENCE_CADES_TIMESTAMP_STAMP_POLICY = "tsaPolicyCades"; //$NON-NLS-1$

	/** Nombre de usuario de la TSA. */
	public static final String PREFERENCE_CADES_TIMESTAMP_TSA_USR = "tsaUsrCades"; //$NON-NLS-1$

	/** Contrase&ntilde;a del usuario de la TSA. */
	public static final String PREFERENCE_CADES_TIMESTAMP_TSA_PWD = "tsaPwdCades"; //$NON-NLS-1$

	/** OID de la extensi&oacute;n a a&ntilde;adir a la petici&oacute;n al servidor de sello de tiempo. */
	public static final String PREFERENCE_CADES_TIMESTAMP_EXTENSION_OID = "tsaExtensionOidCades"; //$NON-NLS-1$

	/** Valor, en binario convertido a Base64, de la extensi&oacute;n a a&ntilde;adir a la petici&oacute;n al servidor de sello de tiempo. */
	public static final String PREFERENCE_CADES_TIMESTAMP_EXTENSION_VALUE = "tsaExtensionValueBase64Cades"; //$NON-NLS-1$

	/** Indica si se requiere el certificado de la TSA. */
	public static final String PREFERENCE_CADES_TIMESTAMP_CERT_REQUIRED = "isCertRequiredCades"; //$NON-NLS-1$

	/** Indica si la extensi&oacute;n indicada en tsaExtensionOid es cr&iacute;tica. */
	public static final String PREFERENCE_CADES_TIMESTAMP_OID_CRITICAL = "isExtensionCriticalCades"; //$NON-NLS-1$

	//**************** FIN PREFERENCIAS DE SELLOS DE TIEMPO CADES*****************************************************************

	//************************* FIN PREFERENCIAS DE FIRMAS CAdES ***************************************************************
	//**************************************************************************************************************************

	//**************************************************************************************************************************
	//**************** PREFERENCIAS DE FACTURAS ELECTRONICAS *******************************************************************

	/** Ciudad de firma para firmas FacturaE. */
	public static final String PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_CITY = "facturaeSignatureProductionCity"; //$NON-NLS-1$

	/** Provincia de firma para firmas FacturaE. */
	public static final String PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_PROVINCE = "facturaeSignatureProductionProvince"; //$NON-NLS-1$

	/** C&oacute;digo de firma para firmas FacturaE. */
	public static final String PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_POSTAL_CODE = "facturaeSignatureProductionPostalCode"; //$NON-NLS-1$

	/** Pa&iacute;s de firma para firmas FacturaE. */
	public static final String PREFERENCE_FACTURAE_SIGNATURE_PRODUCTION_COUNTRY = "facturaeSignatureProductionCountry"; //$NON-NLS-1$

	/** Papel del firmante de las facturas. */
	public static final String PREFERENCE_FACTURAE_SIGNER_ROLE = "facturaeSignerRole"; //$NON-NLS-1$

	/** Identificador de la pol&iacute;tica de firma para FacturaE. */
	public static final String PREFERENCE_FACTURAE_POLICY_IDENTIFIER = "facturaePolicyIdentifier"; //$NON-NLS-1$

	/** Huella digital del identificador de la pol&iacute;tica de firma para FacturaE. */
	public static final String PREFERENCE_FACTURAE_POLICY_IDENTIFIER_HASH = "facturaePolicyIdentifierHash"; //$NON-NLS-1$

	/** Algoritmo de la huella digital del identificador de la pol&iacute;tica de firma para FacturaE. */
	public static final String PREFERENCE_FACTURAE_POLICY_IDENTIFIER_HASH_ALGORITHM = "facturaePolicyIdentifierHashAlgorithm"; //$NON-NLS-1$

	/** Calificador de la pol&iacute;tica de firma para FacturaE. */
	public static final String PREFERENCE_FACTURAE_POLICY_QUALIFIER = "facturaePolicyQualifier"; //$NON-NLS-1$

	//**************** FIN PREFERENCIAS DE FACTURAS ELECTRONICAS ***************************************************************
	//**************************************************************************************************************************

	/** Agoritmo de cifrado. */
	public static final String PREFERENCE_CIPHER_ALGORITHM = "cipherAlgorithms"; //$NON-NLS-1$

	/** Agoritmo de descifrado. */
	public static final String PREFERENCE_DECIPHER_ALGORITHM = "decipherAlgorithms"; //$NON-NLS-1$

	/** Si esta establecido a <code>true</code> se pide al usuario que determine mediante di&aacute;logos
	 * gr&aacute;ficos los par&aacute;metros de una firma visible PDF y se inserta como tal en el
	 * documento. */
	public static final String PREFERENCE_PADES_VISIBLE = "padesVisibleSignature"; //$NON-NLS-1$

	/** Recupera el valor de una cadena de texto almacenada entre las preferencias de la
	 * aplicaci&oacute;n.
	 * @param key Clave del valor que queremos recuperar.
	 * @param def Valor que se devolver&aacute;a si la preferencia no se encontraba almacenada.
	 * @return La preferencia almacenada o {@code def} si no se encontr&oacute;. */
	public static String get(final String key, final String def) {
		return preferences.get(key, def);
	}

	/** Recupera el valor {@code true} o {@code false} almacenado entre las preferencias de la
	 * aplicaci&oacute;n.
	 * @param key Clave del valor que queremos recuperar.
	 * @param def Valor que se devolver&aacute;a si la preferencia no se encontraba almacenada.
	 * @return La preferencia almacenada o {@code def} si no se encontr&oacute;. */
	public static boolean getBoolean(final String key, final boolean def) {
		return preferences.getBoolean(key, def);
	}

	/** Establece una cadena de texto en la configuraci&oacute;n de la aplicaci&oacute;n
	 * identific&aacute;ndola con una clave. Para realizar el guardado completo, es
	 * necesario ejecutar el m&eacute;todo {@code flush()}.
	 * @param key Clave con la que identificaremos el valor.
	 * @param value Valor que se desea almacenar. */
	public static void put(final String key, final String value) {
		preferences.put(key, value);
	}

	/** Establece un {@code true} o {@code false} en la configuraci&oacute;n de la aplicaci&oacute;n
	 * identific&aacute;ndolo con una clave. Para realizar el guardado completo, es
	 * necesario ejecutar el m&eacute;todo {@code flush()}.
	 * @param key Clave con la que identificaremos el valor.
	 * @param value Valor que se desea almacenar. */
	public static void putBoolean(final String key, final boolean value) {
		preferences.putBoolean(key, value);
	}

	/** Elimina una clave de entre la configuraci&oacute;n de la aplicaci&oacute;n.
	 * @param key Clave que eliminar. */
	public static void remove(final String key) {
		preferences.remove(key);
	}

	/** Almacena en las preferencias de la aplicaci&oacute;n todos los valores
	 * establecidos hasta el momento.
	 * @throws BackingStoreException Cuando ocurre un error durante el guardado. */
	public static void flush() throws BackingStoreException {
		preferences.flush();
	}
}
