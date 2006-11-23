/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.auction;

/**
 * <p>
 * This class is referenced from the Auction Logic component but the Auction Framework component
 * where it is defined is still under development, so default implementation is used here. After the
 * Auction Framework will be released this class can be removed. The documentation is left as it was
 * in the development distribution of the Auction Framework component.
 * </p>
 * <p>
 * Purpose: This exception is intended to be thrown by implementations to indicate unsuccessful
 * configuration at start-up.
 * </p>
 * <p>
 * Implementation: Extends the AuctionException class and does not add any variables.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe as it has no state.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationException extends AuctionException {

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
     * <p>
     * </p>
     */
    public ConfigurationException() {
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
    public ConfigurationException(String message) {
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
    public ConfigurationException(String message, Throwable cause) {
        // your code here
    }
}
