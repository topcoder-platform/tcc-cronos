/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.topcoder.util.auction.AuctionPersistenceException;


/**
 * <p>
 * This exception is thrown by the constructors of most custom classes in this design that require configuration. The
 * classes that use this exception include: CustomAuctionPersistence, LocalCustomAuctionPersistence,
 * RemoteCustomAuctionPersistence, and SQLServerAuctionDAO. It is thrown if there is an error during the construction
 * of these objects.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ObjectInstantiationException extends AuctionPersistenceException {
    /**
     * <p>
     * Creates a new instance of <code>ObjectInstantiationException</code> class with a detail error message.
     * </p>
     *
     * @param msg a detail error message describing the error.
     */
    public ObjectInstantiationException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new instance of <code>ObjectInstantiationException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param msg a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public ObjectInstantiationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
