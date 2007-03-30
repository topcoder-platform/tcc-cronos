/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 *
 * UserIDRetriever.java
 */

package com.cronos.im.messenger;

/**
 * <p>
 * This interface defines the contract for retrieving user id (of long type) from the user object.
 * The sender of the <c>Message</c> is of <c>Object</c> type, this interface is used to retrieve
 * the long user id from the <c>Object</c> argument.
 * </p>
 * <p>
 * <b>Thread safety</b>: The implementation of this interface is required to be thread safe.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public interface UserIDRetriever {
    /**
     * Retrieve long user id from the argument, which is wrapped by <c>Object</c> type..
     *
     * @param userId The user ID object.
     * @return The user id.
     * @throws IllegalArgumentException If argument is null.
     * @throws UserIDRetrieverException Wraps any other error that may occurre.
     */
    public long retrieve(Object userId) throws UserIDRetrieverException;
}


