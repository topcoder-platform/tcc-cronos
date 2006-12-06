/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;


/**
 * <p>
 * The local home interface of the GameData EJB.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author argolite, waits
 * @version 1.0
 */
public interface GameDataLocalHome extends EJBLocalHome {
    /**
     * Obtains an instance of the GameDataLocal bean
     *
     * @return the local GameData interface
     *
     * @throws CreateException if the bean container is unable to allocate a bean instance to service the request
     */
    GameDataLocal create() throws CreateException;
}
