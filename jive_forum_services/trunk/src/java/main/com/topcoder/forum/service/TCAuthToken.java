/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.jivesoftware.base.AuthToken;

import java.io.Serializable;


/**
 * <p>
 * This class implements the <code>AuthToken</code> and <code>Serializable</code> interfaces.
 * It represents the authorization token used to plug into the Jive Forum APIs, it is used in
 * <code>JiveForumManager</code> to obtain the <code>ForumFactory</code>.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * It's immutable and thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
class TCAuthToken implements Serializable, AuthToken {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = 5937906927457197288L;

    /**
     * <p>
     * This field represents the user id of the auth token. It's initialized in the constructor,
     * and never changed afterwards. It must be above 0.
     * </p>
     */
    private final long userId;

    /**
     * <p>
     * Creates an instance with specified userId.
     * </p>
     *
     * @param userId the user id.
     * @throws IllegalArgumentException if the userId is not above 0
     */
    public TCAuthToken(long userId) {
        Helper.checkId(userId, "userId");

        this.userId = userId;
    }

    /**
     * <p>
     * Gets the user id.
     * </p>
     *
     * @return the user id.
     */
    public long getUserID() {
        return userId;
    }

    /**
     * <p>
     * Indicates whether the user is anonymous. It will always return false as user needs to
     * be non-anonymous in this class.
     * </p>
     *
     * @return always returns false
     */
    public boolean isAnonymous() {
        return false;
    }
}
