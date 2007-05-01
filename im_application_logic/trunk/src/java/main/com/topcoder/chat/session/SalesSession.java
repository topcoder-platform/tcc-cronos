/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import java.util.Date;

/**
 * <p>
 * This class extends from <code>ChatSession</code> and it represents concrete
 * session type - <b>sales session</b>.
 * </p>
 *
 * <p>
 * For a sales session, it owns additional category id and name compared to other chat session.
 * </p>
 *
 * <p>
 * This session type is used for communicating between two users.
 * </p>
 *
 * <p>
 * Thread Safety : This class is thread safety, because the mutable attribute of this class is synchronized
 * when accessing and its super class is thread safe.
 * </p>
 *
 * @author tushak, TCSDEVELOPER
 * @version 1.0
 */
public class SalesSession extends OneOneSession {
    /**
     * <p>
     * This attribute represents id of category of current session.
     * </p>
     *
     * <p>
     * This is immutable.
     * </p>
     */
    private final long categoryId;

    /**
     * <p>
     * This attribute represents name of category of current session.
     * </p>
     *
     * <p>
     * This is mutable, it is empty string by default.
     * </p>
     */
    private String categoryName;

    /**
     * <p>
     * Constructs a <code>SalesSession</code> with create user id and category id given.
     * </p>
     *
     * @param createdUser id of create user, any long value
     * @param categoryId id of category, any long value
     */
    public SalesSession(long createdUser, long categoryId) {
        super(createdUser);

        this.categoryId = categoryId;
        categoryName = "";
    }

    /**
     * <p>
     * Constructs a <code>SalesSession</code> instance with session id, create user id, create date,
     * requested users, inactive users, active users, category id and category name given.
     * </p>
     *
     * @param id the session id, any long value
     * @param createdUser the id of created user, any long value
     * @param createdDate created date of the session, null impossible
     * @param requestedUsers the array of requested user ids, null impossible but may be empty array
     * @param users the array of inactive users, null impossible but may be empty array
     * @param activeUsers the array of active users, null impossible but may be empty array
     * @param categoryId id of category, any long value
     * @param categoryName name of category, not null, non-empty String
     *
     * @throws IllegalArgumentException if createdDate, requestedUsers, users or activeUsers is null,
     * or categoryName is empty
     */
    public SalesSession(long id, long createdUser, Date createdDate, long[] requestedUsers, long[] users,
        long[] activeUsers, long categoryId, String categoryName) {
        super(id, createdUser, createdDate, requestedUsers, users, activeUsers);

        this.setCategoryName(categoryName);
        this.categoryId = categoryId;
    }

    /**
     * <p>
     * Returns the category id for this sales session.
     * </p>
     *
     * @return the category id for this sales session.
     */
    public long getCategoryId() {
        return this.categoryId;
    }

    /**
     * <p>
     * Returns the category name for this sales session.
     * </p>
     *
     * @return the category name for this sales session.
     */
    public synchronized String getCategoryName() {
        return this.categoryName;
    }

    /**
     * <p>
     * Sets the category name for this sales session.
     * </p>
     *
     * @param categoryName name of category, not null, non-empty String
     *
     * @throws IllegalArgumentException if categoryName is null or empty String
     */
    public synchronized void setCategoryName(String categoryName) {
        Util.checkString(categoryName, "categoryName");
        this.categoryName = categoryName;
    }

    /**
     * <p>
     * Returns the mode of current session.
     * </p>
     *
     * <p>
     * This method will return {@link ChatSession#SALES_SESSION}.
     * </p>
     *
     * @return the mode of current session.
     */
    public int getMode() {
        return SALES_SESSION;
    }
}
