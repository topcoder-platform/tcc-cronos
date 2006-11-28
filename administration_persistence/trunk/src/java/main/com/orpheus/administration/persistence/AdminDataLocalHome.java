/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * <p>This is the local home interface for managing administration data. The local client will obtain it to get the
 * local interface.</p>
 *
 * <p><strong>Thread Safety</strong>: The container assumes all responsibility for thread-safety of these
 * implementations.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface AdminDataLocalHome extends EJBLocalHome {
    /**
     * <p>Creates the EJB and nitializes any required resources.</p>
     *
     * @return the local interface
     * @throws CreateException if the local interface cannot be instantiated
     */
    public AdminDataLocal create() throws CreateException;
}


