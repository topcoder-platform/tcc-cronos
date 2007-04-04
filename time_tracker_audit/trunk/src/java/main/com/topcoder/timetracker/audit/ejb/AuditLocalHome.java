/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;


/**
 * <p>
 * This is the local home interface for managing audits. The local client will obtain it to get the local interface.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface AuditLocalHome extends EJBLocalHome {
    /**
     * <p>
     * Creates the ejb, which is a handle to the AuditSessionBean. Initializes any required resources.
     * </p>
     *
     * @return local interface.
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    AuditLocalObject create() throws CreateException;
}
