
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.timetracker.contact.ejb.ContactLocal;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
/**
 * The local interface of the Contact EJB
 * <p><strong>Thread Safety</strong></p>
 * The container assumes all responsibility for thread-safety of these implementations.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd96]
 */
public interface ContactHomeLocal extends javax.ejb.EJBLocalHome {
/**
 * Creates the ejb. Initializes any required resources.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma28]
 * @return local interface
 * @throws CreateException If any error occurs while instantiating
 */
    public ContactLocal create();
}


