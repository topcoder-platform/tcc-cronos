/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.topcoder.util.auction.AuctionPersistenceException;


/**
 * <p>
 * This exception is thrown by AuctionTranslator and its subclasses if there is an error while doing the translations.
 * At this point, the DefaultAuctionTranslator does not use it because the translations are very simple and do not
 * involve any checked exceptions.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TranslationException extends AuctionPersistenceException {
    /**
     * <p>
     * Creates a new instance of <code>TranslationException</code> class with a detail error message.
     * </p>
     *
     * @param msg a detail error message describing the error.
     */
    public TranslationException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new instance of <code>TranslationException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param msg a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public TranslationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
