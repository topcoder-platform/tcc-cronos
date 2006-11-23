/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.auction;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This class is referenced from the Auction Logic component but the Auction Framework component
 * where it is defined is still under development, so default implementation is used here. After the
 * Auction Framework will be released this class can be removed. The documentation is left as it was
 * in the development distribution of the Auction Framework component.
 * </p>
 * <p>
 * Purpose: This is the general exception of this component and all exceptions extend this one. It
 * is thrown by the AuctionManager to indicate the failure of any process - such as bid process,
 * auction persistence and so on. Sub-classes may indicate a finer cause of error.
 * </p>
 * <p>
 * Implementation: Extends the BaseException class and does not add any variables.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe as it has no state.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionException extends BaseException {

    /**
     * <p>
     * Purpose: Constructs this exception with a null message and cause.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Implementation: Simply call super()
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     */
    public AuctionException() {
        // your code here
    }

    /**
     * <p>
     * Purpose: Constructs this exception with an error message and null cause.
     * </p>
     * <p>
     * Args: message - The error message. Possibly null or empty.
     * </p>
     * <p>
     * Implementation: Simply call super(message)
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * <p>
     * </p>
     * @param message
     */
    public AuctionException(String message) {
        // your code here
    }

    /**
     * <p>
     * Purpose: Constructs this exception with an error message and cause.
     * </p>
     * <p>
     * Args: message - The error message. Possibly null or empty.
     * </p>
     * <p>
     * cause - The cause of this exception. Possibly null.
     * </p>
     * <p>
     * Implementation: Simply call super(message,cause)
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * <p>
     * </p>
     * @param message
     * @param cause
     */
    public AuctionException(String message, Throwable cause) {
        // your code here
    }
}
