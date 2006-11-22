/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;


/**
 * <p>
 * The remote home interface of the GameData EJB.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>The container assumes all responsibility for thread-safety of these
 * implementations.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface GameDataHome extends EJBHome {
    /**
     * Obtains an instance of the GameData bean.
     *
     * @return the remote GameData interface
     *
     * @throws CreateException if the bean container is unable to allocate a bean instance to service the request
     * @throws RemoteException if a communication error occurs between client and EJB container
     */
    GameData create() throws CreateException, RemoteException;
}
