/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

/**
 * <p>
 * <p>
 * This exception is thrown if the involved Time Tracker User was not found in the data store. This is thrown
 * during update/delete methods, and their batch correspondents.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class UserNotFoundException extends UserDAOException {

    /**
     * <p>
     * Constructor accepting a message, cause and problem Time Tracker User.
     * </p>
     *
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemUser The Time Tracker User that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public UserNotFoundException(String message, Throwable cause, User problemUser) {
        super(message, cause, problemUser);
    }
}
