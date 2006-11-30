/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * <p>
 * The remote home interface for the {@link UserProfileBean} session bean. It
 * allows remote clients to obtain a reference to the remote
 * {@link UserProfileRemote} interface. A remote client is a client which runs
 * in a different execution environment (JVM) as the session bean.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> The application server container assumes all the
 * responsibility for providing thread-safe access to the interface.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileRemote
 * @see UserProfileLocal
 * @see UserProfileHomeLocal
 * @see UserProfileBean
 */
public interface UserProfileHomeRemote extends EJBHome {

    /**
     * <p>
     * Creates and returns a reference to the remote {@link UserProfileRemote}
     * interface.
     * </p>
     *
     * @return a reference to the remote <code>UserProfileRemote</code>
     *         interface
     * @throws CreateException if creating a reference to the remote
     *         <code>UserProfileRemote</code> interface fails
     * @throws RemoteException if a network error occurs
     */
    public UserProfileRemote create() throws CreateException, RemoteException;

}
