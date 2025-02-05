/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.cert.signvalidation;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import es.gob.afirma.cert.signvalidation.SignValidity.SIGN_DETAIL_TYPE;
import es.gob.afirma.cert.signvalidation.SignValidity.VALIDITY_ERROR;
import es.gob.afirma.signers.xml.CustomUriDereferencer;
import es.gob.afirma.signers.xml.Utils;

/** Validador de firmas XML. Basado en la documentaci&oacute;n y los ejemplo de la JSR 105. */
public final class ValidateXMLSignature {

	static final Logger LOGGER = Logger.getLogger("es.gob.afirma"); //$NON-NLS-1$

	private ValidateXMLSignature() {
		// No permitimos la instanciacion
	}

	private static final SignValidity KO = new SignValidity(SIGN_DETAIL_TYPE.KO, null);

    /** Valida una firma XML.
     * @param sign Firma a validar
     * @return Validez de la firma. */
    public static SignValidity validate(final byte[] sign) {

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final Document doc;
        try {
            doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(sign));
        }
        catch (final Exception e) {
            return new SignValidity(SIGN_DETAIL_TYPE.KO, VALIDITY_ERROR.CORRUPTED_SIGN);
        }

        // Obtenemos el elemento Signature
        final NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature"); //$NON-NLS-1$
        if (nl.getLength() == 0) {
            return new SignValidity(SIGN_DETAIL_TYPE.KO, VALIDITY_ERROR.NO_SIGN);
        }

        try {

        	final DOMValidateContext valContext = new DOMValidateContext(
    			new KeyValueKeySelector(),
    			nl.item(0)
			);

        	final XMLSignature signature = Utils.getDOMFactory().unmarshalXMLSignature(valContext);
            if (!signature.validate(valContext)) {
            	LOGGER.info("La firma es invalida"); //$NON-NLS-1$
            	return KO;
            }
            if (!signature.getSignatureValue().validate(valContext)) {
            	LOGGER.info("El valor de la firma es invalido"); //$NON-NLS-1$
            	return KO;
            }

            // Ahora miramos las referencias una a una
		    final Iterator<?> i = signature.getSignedInfo().getReferences().iterator();
		    for (int j=0; i.hasNext(); j++) {
		    	if (!((Reference) i.next()).validate(valContext)) {
		    		LOGGER.info("La referencia " + j + " de la firma es invalida"); //$NON-NLS-1$ //$NON-NLS-2$
		    		return KO;
		    	}
		    }

    		return new SignValidity(SIGN_DETAIL_TYPE.OK, null);
        }
        catch (final Exception e) {
        	LOGGER.warning("No se ha podido validar la firma: " + e); //$NON-NLS-1$
            return new SignValidity(SIGN_DETAIL_TYPE.UNKNOWN, null);
        }
    }

    static final class KeyValueKeySelector extends KeySelector {
        @Override
		public KeySelectorResult select(final KeyInfo keyInfo,
                                        final KeySelector.Purpose purpose,
                                        final AlgorithmMethod method,
                                        final XMLCryptoContext context) throws KeySelectorException {
            if (keyInfo == null) {
                throw new KeySelectorException("Objeto KeyInfo nulo"); //$NON-NLS-1$
            }
            final List<?> list = keyInfo.getContent();

            try {
            	// Obtenemos el dereferenciador por defecto
            	// e instalamos uno nuevo que solo actua cuando falla el por defecto
            	context.setURIDereferencer(
        			new CustomUriDereferencer(CustomUriDereferencer.getDefaultDereferencer())
    			);
            }
            catch (final Exception e) {
            	LOGGER.warning(
        			"No se ha podido instalar un dereferenciador a medida: " + e //$NON-NLS-1$
    			);
            }

            for (int i = 0; i < list.size(); i++) {
                final XMLStructure xmlStructure = (XMLStructure) list.get(i);
                if (xmlStructure instanceof KeyValue) {
                    final PublicKey publicKey;
                    try {
                        publicKey = ((KeyValue)xmlStructure).getPublicKey();
                    }
                    catch (final KeyException ke) {
                        throw new KeySelectorException(ke);
                    }
                    return new SimpleKeySelectorResult(publicKey);
                }

                // Si no hay KeyValue intentamos sacar la clave publica del primer Certificado
                // que encontramos en X509Data
                else if (xmlStructure instanceof X509Data) {
                	final List<Object> x509DataObjects = ((X509Data)xmlStructure).getContent();
                	for (final Object o : x509DataObjects) {
                		if (o instanceof Certificate) {
                            return new SimpleKeySelectorResult(((Certificate)o).getPublicKey());
                		}
                	}
                }
            }
            throw new KeySelectorException("No se ha encontrado la clave publica dentro del XML firmado"); //$NON-NLS-1$
        }

    }

    private static final class SimpleKeySelectorResult implements KeySelectorResult {
        private final PublicKey pk;
        SimpleKeySelectorResult(final PublicKey pk) {
            this.pk = pk;
        }

		@Override
		public Key getKey() { return this.pk; }
    }
}
