/*
 * Copyright (C) 2007 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * The EJB home used for EJB Demo.
 * @author fuyun, TCSDEVELOPER
 * @version 1.2.1
 * @since 1.1
 */
public interface ProjectHome extends EJBHome {

    /**
     * Create the <code>ProjectObject</code>.
     * @return the created <code>ProjectObject</code> object.
     * @throws RemoteException if there is anything remote problem.
     * @throws CreateException if fails to create the object.
     */
    ProjectObject create() throws RemoteException,
            CreateException;
}
