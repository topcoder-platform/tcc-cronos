
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.timetracker.contact.ejb.AddressLocal;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
/**
 * The local interface of the Address EJB
 * <p><strong>Thread Safety</strong></p>
 * The container assumes all responsibility for thread-safety of these implementations.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd9a]
 */
public interface AddressLocalHome extends javax.ejb.EJBLocalHome {
/**
 * Creates the ejb. Initializes any required resources.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmbcf]
 * @return local interface
 * @throws CreateException If any error occurs while instantiating
 */
    public AddressLocal create();
}


