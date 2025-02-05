// Copyright (C) 2012-13 MINHAP, Gobierno de España
// This program is licensed and may be used, modified and redistributed under the terms
// of the European Public License (EUPL), either version 1.1 or (at your
// option) any later version as soon as they are approved by the European Commission.
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// or implied. See the License for the specific language governing permissions and
// more details.
// You should have received a copy of the EUPL1.1 license
// along with this program; if not, you may find it at
// http://joinup.ec.europa.eu/software/page/eupl/licence-eupl

/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2009-,2011 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.afirma5ServiceInvoker.Afirma5ServiceInvokerException.java.</p>
 * <b>Description:</b><p>Class used for all possible errors that can lead to invoke a @Firma plataform service.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>16/03/2011.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/03/2011.
 */
package es.gob.afirma.afirma5ServiceInvoker;


/**
 * <p>Class used for all possible errors that can lead to invoke a @Firma plataform service.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.0, 16/03/2011.
 */
public class Afirma5ServiceInvokerException extends Exception {

	/**
	 * Attribute that represents serial version UID.
	 */
	private static final long serialVersionUID = 3562488179175920708L;

	/**
	 * Constructor method for the class Afirma5ServiceInvokerException.java.
	 */
	public Afirma5ServiceInvokerException() {
		super();
	}

	/**
	 * Constructor method for the class Afirma5ServiceInvokerException.java.
	 * @param arg0 error message.
	 */
	public Afirma5ServiceInvokerException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor method for the class Afirma5ServiceInvokerException.java.
	 * @param arg0 error cause.
	 */
	public Afirma5ServiceInvokerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Constructor method for the class Afirma5ServiceInvokerException.java.
	 * @param arg0 error message.
	 * @param arg1 error cause.
	 */
	public Afirma5ServiceInvokerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
