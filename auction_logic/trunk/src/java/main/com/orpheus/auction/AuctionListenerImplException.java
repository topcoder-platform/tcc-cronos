/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This running time exception is thrown by AuctionListenerImpl if any error occurred during the
 * event processing.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread-safe.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class AuctionListenerImplException extends BaseRuntimeException {

    /**
     * <p>
     * Creates a new AuctionListenerImplException instance with the given error message.
     * </p>
     * @param msg the error message of the exception.
     */
    public AuctionListenerImplException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new AuctionListenerImplException instance with the given error message and inner
     * cause.
     * </p>
     * @param msg the error message of the exception.
     * @param cause the inner cause of the exception.
     */
    public AuctionListenerImplException(String msg, Throwable cause) {
        super(msg, cause);
    }
}