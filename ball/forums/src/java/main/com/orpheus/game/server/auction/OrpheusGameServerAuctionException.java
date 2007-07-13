/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.auction;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>A custom unchecked exception to be used for reporting on unrecoverable errors occurring in context of <code>
 * Orpheus Game Server</code> application. In most case this exception will wrap the original exceptions which have
 * caused the error to occur.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusGameServerAuctionException extends BaseRuntimeException {

    /**
     * <p>Constructs new <code>OrpheusGameServerAuctionException</code> instance. This implementation does nothing.</p>
     */
    public OrpheusGameServerAuctionException() {
    }

    /**
     * <p>Constructs new <code>OrpheusGameServerAuctionException</code> instance with specified message.</p>
     *
     * @param message a <code>String</code> providing the details for the error.
     */
    public OrpheusGameServerAuctionException(String message) {
        super(message);
    }

    /**
     * <p>Constructs new <code>OrpheusGameServerAuctionException</code> instance with specified message and original
     * cause of error.</p>
     *
     * @param message a <code>String</code> providing the details for the error.
     * @param throwable a <code>Throwable</code> providing the original cause of error.
     */
    public OrpheusGameServerAuctionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * <p>Constructs new <code>OrpheusGameServerAuctionException</code> instance with specified cause of error.</p>
     *
     * @param throwable a <code>Throwable</code> providing the original cause of error.
     */
    public OrpheusGameServerAuctionException(Throwable throwable) {
        super(throwable);
    }
}
