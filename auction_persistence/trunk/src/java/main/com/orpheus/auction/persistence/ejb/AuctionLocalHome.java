/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;


/**
 * <p>
 * This is the local home interface for managing auctions. The local client will obtain it to get the local interface.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionLocalHome extends EJBLocalHome {
    /**
     * <p>
     * Creates the ejb. Initializes any required resources.
     * </p>
     *
     * @return local interface
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    AuctionLocal create() throws CreateException;
}
