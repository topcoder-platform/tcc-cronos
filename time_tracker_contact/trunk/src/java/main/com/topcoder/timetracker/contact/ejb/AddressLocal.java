/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import javax.ejb.EJBLocalObject;

import com.topcoder.timetracker.contact.AddressManager;


/**
 * <p>
 * The local component interface of the SLSB <code>AddressBean</code>, which provides access
 * to the persistent store for addresses managed by the application. It also derives from
 * <code>AddressManager</code> and contains exactly the same methods which will be supported
 * for clients.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety</strong>
 * The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public interface AddressLocal extends EJBLocalObject, AddressManager {
}
