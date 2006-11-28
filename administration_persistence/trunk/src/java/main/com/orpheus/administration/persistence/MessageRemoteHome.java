/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * <p>This is the remote home interface for managing messages. The remote client will obtain it to get the remote
 * interface.</p>
 *
 * <p><strong>Thread Safety</strong>: The container assumes all responsibility for thread-safety of these
 * implementations.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface MessageRemoteHome extends EJBHome {
    /**
     * <p>Creates the EJB and initializes any required resources.</p>
     *
     * @return the remote interface
     * @throws CreateException if the remote interface cannot be instantiated
     * @throws RemoteException if there is a network issue
     */
    public MessageRemote create() throws CreateException, RemoteException;
}
