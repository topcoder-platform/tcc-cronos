/*
 * FeedRetrievalException.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception can be thrown from the getFeed method of FeedDataStore if the data store fails to create the feed.
 * This exception is designed to encapsulate all possible underlying data store specific exceptions, or the data in
 * the store is not sufficient to create the Feed instance.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public class FeedRetrievalException extends BaseException {
    /**
     * <p>
     * Create a new instance of FeedRetrievalException with the given error message.
     * </p>
     *
     * @param msg the error message of the exception.
     */
    public FeedRetrievalException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Create a new instance of FeedRetrievalException with the given error message and inner exception cause.
     * </p>
     *
     * @param msg the error message of the exception.
     * @param cause the cause of the exception.
     */
    public FeedRetrievalException(String msg, Exception cause) {
        super(msg, cause);
    }
}
