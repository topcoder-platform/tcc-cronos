/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This abstract class represents object model for session instance in data store.
 * </p>
 *
 * <p>
 * It is a data class and contains session attributes like id, create user, create date, requested users
 * inactive users and active users.
 * </p>
 *
 * <p>
 * This class has three static attributes which is given to define mode id of session, that are,
 * {@link ChatSession#ONE_ONE_SESSION}, {@link ChatSession#SALES_SESSION} and
 * {@link ChatSession#GROUP_SESSION}.
 * </p>
 *
 * <p>
 * Note, this class contains methods which may change the states of this class.
 * These methods should be used only in this component.
 * </p>
 *
 * <p>
 * Thread Safety : Class has several mutable attributes. But it is thread safe, because the mutable
 * attributes are synchronized when accessing.
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public abstract class ChatSession {
    /**
     * <p>
     * This attribute is used to identify that child of ChatSession is OneOneSession.
     * </p>
     *
     * <p>
     * This is immutable, static, default value is 1.
     * </p>
     */
    public static final int ONE_ONE_SESSION = 1;

    /**
     * <p>
     * This attribute is used to identify that child of ChatSession is GroupSession.
     * </p>
     *
     * <p>
     * This is immutable, static, default value is 2.
     * </p>
     */
    public static final int GROUP_SESSION = 2;

    /**
     * <p>
     * This attribute is used to identify that child of ChatSession is SalesSession.
     * </p>
     *
     * <p>
     * This is immutable, static, default value is 3.
     * </p>
     */
    public static final int SALES_SESSION = 3;

    /**
     * <p>
     * Represents the lock object for the id attribute.
     * </p>
     */
    private final Object idLock = new Object();

    /**
     * <p>
     * Represents the lock object for the createdDate attribute.
     * </p>
     */
    private final Object createdDateLock = new Object();

    /**
     * <p>
     * This attribute represents unique id of session instance.
     * </p>
     *
     * <p>
     * This is mutable, it is synchronized when accessing.
     * </p>
     */
    private long id;

    /**
     * <p>
     * This attribute represents unique id of user who create current session.
     * </p>
     *
     * <p>
     * This is immutable.
     * </p>
     */
    private final long createdUser;

    /**
     * <p>
     * This attribute represents date when session was created.
     * </p>
     *
     * <p>
     * This is mutable, it is synchronized when accessing.
     * </p>
     */
    private Date createdDate;

    /**
     * <p>
     * This attribute represents user ids which were requested to current session.
     * </p>
     *
     * <p>
     * The type of this attribute is <code>Set</code>, the elements of it are all of <code>Long</code> type.
     * </p>
     *
     * <p>
     * The reference is immutable but its content can be changed. It is synchronized when accessing.
     * </p>
     *
     * <p>
     * It will never be null but may be empty.
     * </p>
     */
    private final Set requestedUsers = new HashSet();

    /**
     * <p>
     * This attribute represents user ids which were added to current session as inactive users.
     * </p>
     *
     * <p>
     * The type of this attribute is <code>Set</code>, the elements of it are all of <code>Long</code> type.
     * </p>
     *
     * <p>
     * The reference is immutable but its content can be changed. It is synchronized when accessing.
     * </p>
     *
     * <p>
     * It will never be null but may be empty.
     * </p>
     */
    private final Set users = new HashSet();

    /**
     * <p>
     * This attribute represents user ids which were added to current session as active users.
     * </p>
     *
     * <p>
     * The type of this attribute is <code>Set</code>, the elements of it are all of <code>Long</code> type.
     * </p>
     *
     * <p>
     * The reference is immutable but its content can be changed. It is synchronized when accessing.
     * </p>
     *
     * <p>
     * It will never be null but may be empty.
     * </p>
     */
    private final Set activeUsers = new HashSet();

    /**
     * <p>
     * Constructs a <code>ChatSession</code> with the create user id given.
     * </p>
     *
     * <p>
     * Note, in this constructor, the session id will be set to <code>0</code>, the created
     * date will set to the current date, the requested users, inactive users and active users
     * for this session will be empty.
     * </p>
     *
     * @param createdUser createUser id of created user, any long value
     */
    protected ChatSession(long createdUser) {
        this.createdUser = createdUser;
        this.id = 0;
        this.createdDate = new Date();
    }

    /**
     * <p>
     * Constructs a <code>ChatSession</code> with the session id, created user id, created date,
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
    protected ChatSession(long id, long createdUser, Date createdDate, long[] requestedUsers, long[] users,
        long[] activeUsers) {
        Util.checkNull(createdDate, "createdDate");
        Util.checkNull(requestedUsers, "requestedUsers");
        Util.checkNull(users, "users");
        Util.checkNull(activeUsers, "activeUsers");

        this.id = id;
        this.createdUser = createdUser;
        this.createdDate = createdDate;
        this.requestedUsers.addAll(convertToSet(requestedUsers));
        this.users.addAll(convertToSet(users));
        this.activeUsers.addAll(convertToSet(activeUsers));
    }

    /**
     * <p>
     * Returns the session id.
     * </p>
     *
     * @return the session id
     */
    public long getId() {
        synchronized (idLock) {
            return this.id;
        }
    }

    /**
     * <p>
     * Sets the id of the session.
     * </p>
     *
     * @param id id of session, any possible long value
     */
    public void setId(long id) {
        synchronized (idLock) {
            this.id = id;
        }
    }

    /**
     * <p>
     * Returns the create user id of the session.
     * </p>
     *
     * @return the create user id of the session.
     */
    public long getCreatedUser() {
        return createdUser;
    }

    /**
     * <p>
     * Returns the create date of the session.
     * </p>
     *
     * @return the create date of the session, null impossible
     */
    public Date getCreatedDate() {
        synchronized (createdDateLock) {
            return createdDate;
        }
    }

    /**
     * <p>
     * Sets the create date of the session.
     * </p>
     *
     * @param createdDate the new create date of the session, null impossible
     *
     * @throws IllegalArgumentException if createdDate is null
     */
    public void setCreatedDate(Date createdDate) {
        Util.checkNull(createdDate, "createdDate");

        synchronized (createdDateLock) {
            this.createdDate = createdDate;
        }
    }

    /**
     * <p>
     * Returns an array of requested user ids of this session.
     * </p>
     *
     * <p>
     * Note, the return array will not contain duplicate ids.
     * </p>
     *
     * @return an array of requested user ids of this session.
     */
    public long[] getRequestedUsers() {
        synchronized (requestedUsers) {
            return Util.toLongArray(requestedUsers);
        }
    }

    /**
     * <p>
     * Adds a new requested user to session.
     * </p>
     *
     * <p>
     * Note, if the given user id is present in this session as a requested user, then it will ignored.
     * </p>
     *
     * @param user user id, any long value
     */
    public void addRequestedUser(long user) {
        synchronized (requestedUsers) {
            requestedUsers.add(new Long(user));
        }
    }

    /**
     * <p>
     * Returns an array of inactive and active user ids of this session.
     * </p>
     *
     * @return an array of inactive and active user ids of this session, null impossible
     * but may be empty array
     */
    public long[] getUsers() {
        synchronized (users) {
            synchronized (activeUsers) {
                Set allUsers = new HashSet();

                // the inactive and active users should be returned
                allUsers.addAll(users);
                allUsers.addAll(activeUsers);

                return Util.toLongArray(allUsers);
            }
        }
    }

    /**
     * <p>
     * Adds a new inactive user to session.
     * </p>
     *
     * <p>
     * Note, if the given user id is present in this session as an inactive user, then it will ignored.
     * </p>
     *
     * @param user user id, any long value
     */
    public void addUser(long user) {
        synchronized (users) {
            users.add(new Long(user));
        }
    }

    /**
     * <p>
     * Returns an array of active user ids of this session.
     * </p>
     *
     * <p>
     * Note, the return array will not contain duplicate ids.
     * </p>
     *
     * @return an array of active user ids of this session.
     */
    public long[] getActiveUsers() {
        synchronized (activeUsers) {
            return Util.toLongArray(activeUsers);
        }
    }

    /**
     * <p>
     * Adds a new active user to session.
     * </p>
     *
     * <p>
     * Note, if the given user id is present in this session as an active user, then it will ignored.
     * </p>
     *
     * @param user user id, any long value
     */
    public void addActiveUser(long user) {
        synchronized (activeUsers) {
            activeUsers.add(new Long(user));
        }
    }

    /**
     * <p>
     * Removes the given active user from this session.
     * </p>
     *
     * <p>
     * If the given user is not present as an active user, then false will be returned, otherwise
     * true will be returned.
     * </p>
     *
     * @param user user id, any long value
     *
     * @return true if the given user is present as an active user, false otherwise.
     */
    public boolean removeActiveUser(long user) {
        synchronized (activeUsers) {
            return activeUsers.remove(new Long(user));
        }
    }

    /**
     * <p>
     * This is an abstract method which should be overridden by concrete implementations.
     * </p>
     *
     * <p>
     * This method should return the mode of current session.
     * </p>
     *
     * @return the mode of current session.
     */
    public abstract int getMode();

    /**
     * <p>
     * This method converts a long array to its <code>Set</code> representation.
     * </p>
     *
     * @param users the long array for converting
     *
     * @return the <code>Set</code> representation of the given long array
     */
    private Set convertToSet(long[] users) {
        Set values = new HashSet();
        for (int i = 0; i < users.length; i++) {
            values.add(new Long(users[i]));
        }

        return values;
    }
}
