/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import javax.ejb.EJBLocalObject;

import com.topcoder.timetracker.contact.ContactManager;

/**
 * <p>
 * The local component interface of the SLSB <code>ContactBean</code>, which provides access
 * to the persistent store for contacts managed by the application. It also derives from
 * <code>ContactManager</code> and contains exactly the same methods which will be supported
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
public interface ContactLocal extends EJBLocalObject, ContactManager {
}


