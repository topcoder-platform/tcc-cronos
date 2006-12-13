package com.orpheus.game.persistence;

import javax.ejb.CreateException;

/**
 * The local home interface of the GameData EJB.
 * <p><strong>Thread Safety</strong></p>
 * <p>The container assumes all responsibility for thread-safety of these implementations.</p>
 * 
 */
public interface GameDataLocalHome extends javax.ejb.EJBLocalHome {
    /**
     * Obtains an instance of the GameDataLocal bean
     * 
     * 
     * @return the local GameData interface
     * @throws CreateException if the bean container is unable to
     * allocate a bean instance to service the request
     */
    public GameDataLocal create() throws CreateException;
}
