/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;


/**
 * <p>
 * This is the remote home interface for managing auctions. The remote client will obtain it to get the remote
 * interface.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionRemoteHome extends EJBHome {
    /**
     * <p>
     * Creates the ejb. Initializes any required resources.
     * </p>
     *
     * @return remote interface.
     *
     * @throws RemoteException If there is a network issue.
     * @throws CreateException If any error occurs while instantiating.
     */
    AuctionRemote create() throws RemoteException, CreateException;
}
