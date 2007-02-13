/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.common.Utils;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs when performing operations with the UserDAO.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class UserDAOException extends BaseException {

    /**
     * <p>
     * The Time Tracker User that the DAO was working with when a problem occurred.
     * </p>
     * <p>
     * Initialized In: Constructor (may be null).
     * </p>
     * <p>
     * Accessed In: getProblemUser
     * </p>
     *
     */
    private final User problemUser;

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
    public UserDAOException(String message, Throwable cause, User problemUser) {
        super(message, cause);
        Utils.checkString(message, "message", false);
        this.problemUser = problemUser;
    }

    /**
     * Retrieves the Time Tracker User that the DAO was working with when a problem occurred.
     *
     *
     * @return the Time Tracker User that the DAO was working with when a problem occurred.
     */
    public User getProblemUser() {
        return problemUser;
    }
}
