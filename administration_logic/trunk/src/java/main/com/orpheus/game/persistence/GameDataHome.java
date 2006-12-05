package com.orpheus.game.persistence;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * The remote home interface of the GameData EJB
 * <p><strong>Thread Safety</strong></p>
 * <p>The container assumes all responsibility for thread-safety of these implementations.</p>
 * 
 */
public interface GameDataHome extends javax.ejb.EJBHome {

    /**
     * Obtains an instance of the GameData bean
     * 
     * 
     * @return the remote GameData interface
     * @throws CreateException if the bean container is unable to allocate a bean instance to service the request
     * @throws RemoteException if a communication error occurs between client and EJB container
     */
    public GameData create() throws CreateException, RemoteException;
}
