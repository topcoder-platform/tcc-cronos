/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.user;

import com.cronos.timetracker.common.Utils;

/**
 * <p>
 * This exception is thrown when a problem occurs when performing operations
 * with the User DAO in non-atomic Batch Mode.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class BatchUserDAOException extends UserDAOException {

    /**
     * <p>
     * The set of causes for the problems that occurred during processing. The
     * number of elements in this array and the problemUsers array should be
     * equal, and the element in each array corresponds to the element in the
     * other array with the same index. (ie, problemUsers[1] corresponds to
     * causes[1])
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: getCauses
     * </p>
     */
    private final Throwable[] causes;

    /**
     * <p>
     * The set of Time Tracker Users where a problem occurred during processing.
     * The number of elements in this array and the causes array should be
     * equal, and the element in each array corresponds to the element in the
     * other array with the same index. (ie, problemUsers[1] corresponds to
     * causes[1])
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: getProblemUsers
     * </p>
     */
    private final User[] problemUsers;

    /**
     * <p>
     * Constructor accepting a message, causes and Users.
     * </p>
     * <p>
     * The first element of each array is provided as the argument to the
     * respective super constructor.
     * </p>
     *
     * @param message The message of the exception.
     * @param causes A set of causes for the problems that occurred during
     *        processing.
     * @param users A set of Time Tracker Users that the DAO was working on when
     *        the problem occurred.
     * @throws IllegalArgumentException if the message is null or an empty
     *         String, or if any parameter is null, or if users contains null
     *         elements, or the size of both arrays are not equal.
     */
    public BatchUserDAOException(String message, Throwable[] causes, User[] users) {
        super(message, Utils.getFirstThrowable(causes), getUser(users));
        if (causes.length != users.length) {
            throw new IllegalArgumentException("Different arrays sizes.");
        }
        this.problemUsers = users;
        this.causes = causes;
    }

    /**
     * <p>
     * Returns the first element from the array, if any exists.
     * </p>
     *
     * @param users the users array.
     * @return the first user element, or <code>null</code> if array is empty.
     */
    private static User getUser(User[] users) {
        Utils.checkNull(users, "users");
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                throw new IllegalArgumentException("The users array contains null element.");
            }
        }

        if (users.length > 0) {
            return users[0];
        }

        return null;
    }

    /**
     * <p>
     * Retrieves the set of causes for the problems that occurred during
     * processing. The number of elements in this array and the problemUsers
     * array should be equal, and the element in each array corresponds to the
     * element in the other array with the same index. (ie, problemUsers[1]
     * corresponds to causes[1])
     * </p>
     *
     * @return the set of causes for the problems that occurred during
     *         processing.
     */
    public Throwable[] getCauses() {
        return causes;
    }

    /**
     * <p>
     * Retrieves the set of Time Tracker Users where a problem occurred during
     * processing.
     * </p>
     *
     * @return the set of Time Tracker Users where a problem occurred during
     *         processing.
     */
    public User[] getProblemUsers() {
        return problemUsers;
    }
}
