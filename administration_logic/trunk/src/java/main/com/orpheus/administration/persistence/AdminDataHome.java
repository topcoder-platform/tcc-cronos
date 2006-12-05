package com.orpheus.administration.persistence;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * <p>This is the remote home interface for managing administration data. The remote client will obtain it to get the remote interface.</p>
 * <p><strong>Thread Safety</strong></p>
 * <p>The container assumes all responsibility for thread-safety of these implementations.</p>
 * 
 */
public interface AdminDataHome extends javax.ejb.EJBHome {
    /**
     * <p>Creates the ejb. Initializes any required resources.</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>CreateException If any error occurs while instantiating</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @return remote interface
     * @throws CreateException If any error occurs while instantiating
     * @throws RemoteException If there is a network issue
     */
    public com.orpheus.administration.persistence.AdminData create()
        throws CreateException, RemoteException;
}
