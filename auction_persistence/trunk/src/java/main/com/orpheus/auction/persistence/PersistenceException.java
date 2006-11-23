/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.topcoder.util.auction.AuctionPersistenceException;


/**
 * <p>
 * This exception is the base exception for persistence operations in this component. As such, it and its three
 * subclasses are thrown by the ejbXXX method in the clients, the business methods in the EJBs, and the DAOs. In
 * effect, the client helper method and EJB business methods act as a pass-though for these exceptions.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceException extends AuctionPersistenceException {
    /**
     * <p>
     * Creates a new instance of <code>PersistenceException</code> class with a detail error message.
     * </p>
     *
     * @param msg a detail error message describing the error.
     */
    public PersistenceException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new instance of <code>PersistenceException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param msg a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
