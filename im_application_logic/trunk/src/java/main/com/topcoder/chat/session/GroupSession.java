/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import java.util.Date;

/**
 * <p>
 * This class extends from <code>ChatSession</code> and it represents concrete
 * session type - <b>group session</b>.
 * </p>
 *
 * <p>
 * It has one additional attribute - <b>topic</b>.
 * </p>
 *
 * <p>
 * This session type is used for communicating among many users.
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
public class GroupSession extends ChatSession {
    /**
     * <p>
     * Represents the maximum string length of the <b>topic</b> attribute.
     * </p>
     */
    private static final int MAX_LENGTH = 255;

    /**
     * <p>
     * This attribute represents the topic of current session.
     * </p>
     *
     * <p>
     * This is String instance with length no more than <code>255</code>.
     * </p>
     *
     * <p>
     * This is immutable. It will never be null or empty.
     * </p>
     */
    private final String topic;

    /**
     * <p>
     * Constructs a <code>GroupSession</code> instance with create user id and topic given.
     * </p>
     *
     * @param createdUser id of create user, any long value
     * @param topic String instance with length no more than <code>255</code>, cannot be null or empty
     *
     * @throws IllegalArgumentException if topic is null, empty or its length is more than 255
     */
    public GroupSession(long createdUser, String topic) {
        super(createdUser);

        checkTopic(topic);

        this.topic = topic;
    }

    /**
     * <p>
     * Constructs a <code>GroupSession</code> instance with session id, create user id, create date,
     * requested users, inactive users, active users and topic given.
     * </p>
     *
     * @param id session id, any long value
     * @param createdUser create user id, any long value
     * @param createdDate create date, null impossible
     * @param requestedUsers the array of requested user ids, null impossible but may be empty array
     * @param users the array of inactive users, null impossible but may be empty array
     * @param activeUsers the array of active users, null impossible but may be empty array
     * @param topic String instance with length no more than <code>255</code>, cannot be null or empty
     *
     * @throws IllegalArgumentException if topic is null, empty or its length is more than 255, or
     * createdDate, requestedUsers, users or activeUsers is null
     */
    public GroupSession(long id, long createdUser, Date createdDate, long[] requestedUsers, long[] users,
        long[] activeUsers, String topic) {
        super(id, createdUser, createdDate, requestedUsers, users, activeUsers);

        checkTopic(topic);

        this.topic = topic;
    }

    /**
     * <p>
     * Gets the topic of the group session.
     * </p>
     *
     * @return the topic of the group session, it won't be null or empty, its length will less than 255
     */
    public String getTopic() {
        return this.topic;
    }

    /**
     * <p>
     * Returns the mode of current session.
     * </p>
     *
     * <p>
     * This method will return {@link ChatSession#GROUP_SESSION}.
     * </p>
     *
     * @return the mode of current session.
     */
    public int getMode() {
        return GROUP_SESSION;
    }

    /**
     * <p>
     * This method checks the topic string.
     * </p>
     *
     * @param topic the topic string for the group session
     *
     * @throws IllegalArgumentException if topic is null, empty or its length is more than 255
     */
    private void checkTopic(String topic) {
        Util.checkString(topic, "topic");

        // check the length for the maximum length constraint
        if (topic.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("The length of topic is more than " + MAX_LENGTH + ".");
        }
    }
}
