/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger.impl;

import com.cronos.im.messenger.Helper;
import com.cronos.im.messenger.UserIDRetriever;
import com.cronos.im.messenger.UserIDRetrieverException;

/**
 * <p>
 * This class assumes that the user object is of Long type, otherwise an
 * exception of type <c>UserIDRetrieverException</c> will be thrown.
 * This class will be used by MessengerImpl to retrieve long user id from sender of the Message.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is thread safe since it does not contain any mutable inner status.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class UserIDRetrieverImpl implements UserIDRetriever {

    /**
     * Creates a new instance of this class.
     */
    public UserIDRetrieverImpl() {
    }

    /**
     * Retrieve the user id from the <c>Object</c> specified as parameter.
     * This method always assume that the user object is of Long type
     * , otherwise, an exception of type <c>UserIDretrieverException</c> will be thrown.
     *
     * @param userID The userID object.
     * @return The id of the object as a long value.
     * @throws IllegalArgumentException If argument is null.
     * @throws UserIDRetrieverException Wraps any other exceptions that may occur during this
     *                                  method call.
     */
    public long retrieve(Object userID) throws UserIDRetrieverException {
        Helper.validateNotNull(userID, "userID");

        Long longUserID;
        if (userID instanceof Long) {
            longUserID = (Long) userID;
        } else {
            throw new UserIDRetrieverException("userID is not of Long type");
        }

        return longUserID.longValue();
    }
}
