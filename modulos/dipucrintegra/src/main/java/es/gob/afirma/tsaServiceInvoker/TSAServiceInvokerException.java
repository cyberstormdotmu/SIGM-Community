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

/**
 * <b>File:</b><p>es.gob.afirma.tsaServiceInvoker.TSAServiceInvokerException.java.</p>
 * <b>Description:</b><p>Class that manages the exceptions related to the invocation of TS@ services.</p>
 * <b>Project:</b><p>@Firma and TS@ Web Services Integration Platform.</p>
 * <b>Date:</b><p>09/01/2014.</p>
 * @author Gobierno de España.
 * @version 1.0, 09/01/2014.
 */
package es.gob.afirma.tsaServiceInvoker;

/**
 * <p>Class that manages the exceptions related to the invocation of TS@ services.</p>
 * <b>Project:</b><p>@Firma and TS@ Web Services Integration Platform.</p>
 * @version 1.0, 09/01/2014.
 */
public class TSAServiceInvokerException extends Exception {

    /**
     * Class serial version.
     */
    private static final long serialVersionUID = -5170309917005079456L;

    /**
     * Constructor method for the class TSAServiceInvokerException.java.
     */
    public TSAServiceInvokerException() {
	super();
    }

    /**
     * Constructor method for the class TSAServiceInvokerException.java.
     * @param message Error message.
     */
    public TSAServiceInvokerException(String message) {
	super(message);
    }

    /**
     * Constructor method for the class TSAServiceInvokerException.java.
     * @param cause Error cause.
     */
    public TSAServiceInvokerException(Throwable cause) {
	super(cause);

    }

    /**
     * Constructor method for the class TSAServiceInvokerException.java.
     * @param message Error message.
     * @param cause Error cause.
     */
    public TSAServiceInvokerException(String message, Throwable cause) {
	super(message, cause);
    }

}
