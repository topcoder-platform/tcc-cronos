/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import java.util.Date;

/**
 * <p>
 * This class extends from <code>ChatSession</code> and it represents
 * concrete session type - <b>one one session</b>.
 * </p>
 *
 * <p>
 * This session type is used for communicating between two users.
 * </p>
 *
 * <p>
 * Thread Safety : This class is thread safety, because this class is immutable
 * and its super class is thread safe.
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public class OneOneSession extends ChatSession {
    /**
     * <p>
     * Constructs a <code>OneOneSession</code> with the create user id given.
     * </p>
     *
     * @param createdUser createUser id of created user, any long value
     */
    public OneOneSession(long createdUser) {
        super(createdUser);
    }

    /**
     * <p>
     * Constructs a <code>OneOneSession</code> instance with session id, create user id, create date,
     * requested users, inactive users and active users given.
     * </p>
     *
     * @param id the session id, any long value
     * @param createdUser the id of created user, any long value
     * @param createdDate created date of the session, null impossible
     * @param requestedUsers the array of requested user ids, null impossible but may be empty array
     * @param users the array of inactive users, null impossible but may be empty array
     * @param activeUsers the array of active users, null impossible but may be empty array
     *
     * @throws IllegalArgumentException if createdDate, requestedUsers, users or activeUsers is null
     */
    public OneOneSession(long id, long createdUser, Date createdDate, long[] requestedUsers, long[] users,
        long[] activeUsers) {
        super(id, createdUser, createdDate, requestedUsers, users, activeUsers);
    }

    /**
     * <p>
     * Returns the mode of current session.
     * </p>
     *
     * <p>
     * This method will return {@link ChatSession#ONE_ONE_SESSION}.
     * </p>
     *
     * @return the mode of current session.
     */
    public int getMode() {
        return ONE_ONE_SESSION;
    }
}
