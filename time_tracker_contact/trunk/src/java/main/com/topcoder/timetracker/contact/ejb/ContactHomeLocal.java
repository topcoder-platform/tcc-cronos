/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * <p>
 * The local interface of the SLSB <code>ContactBean</code>.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety</strong>
 *  The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public interface ContactHomeLocal extends EJBLocalHome {

    /**
     * <p>
     * Creates the EJB. Initialize any required resources.
     * </p>
     *
     * @return The local interface for SLSB <code>ContactBean</code>.
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    public ContactLocal create() throws CreateException;
}


