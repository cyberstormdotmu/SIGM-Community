/* 
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 * 
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 */

package eu.stork.peps.auth.engine.core.impl;

import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;

import eu.stork.peps.auth.engine.core.SPID;
import eu.stork.peps.auth.engine.core.SPInformation;

/**
 * The Class SPInformationUnmarshaller.
 * 
 * @author iinigo
 */
public class SPInformationUnmarshaller extends
	AbstractSAMLObjectUnmarshaller {

    /**
     * Process child element.
     * 
     * @param parentObject the parent object
     * @param childObject the child object
     * 
     * @throws UnmarshallingException the unmarshalling exception
     *
     */
    protected final void processChildElement(final XMLObject parentObject,
	    final XMLObject childObject) throws UnmarshallingException {
	final SPInformation spInformation = (SPInformation) parentObject;

	if (childObject instanceof SPID) {
        spInformation.setSPID((SPID) childObject);
	} else {
	    super.processChildElement(parentObject, childObject);
	}	
    }
}
