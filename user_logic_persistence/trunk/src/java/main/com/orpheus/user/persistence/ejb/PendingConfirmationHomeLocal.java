/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * <p>
 * The local home interface for the {@link PendingConfirmationBean} session
 * bean. It allows local clients to obtain a reference to the local
 * {@link PendingConfirmationLocal} interface. A local client is a client which
 * runs in the same execution environment (JVM) as the session bean.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> The application server container assumes all the
 * responsibility for providing thread-safe access to the interface.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see PendingConfirmationLocal
 * @see PendingConfirmationRemote
 * @see PendingConfirmationHomeRemote
 * @see PendingConfirmationBean
 */
public interface PendingConfirmationHomeLocal extends EJBLocalHome {

    /**
     * <p>
     * Creates and returns a reference to the local
     * {@link PendingConfirmationLocal} interface.
     * </p>
     *
     * @return a reference to the local <code>PendingConfirmationLocal</code>
     *         interface
     * @throws CreateException if creating a reference to the local
     *         <code>PendingConfirmationLocal</code> interface fails
     */
    public PendingConfirmationLocal create() throws CreateException;

}
