/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

/**
 * <p>
 * This class is a simple data structure used to represent an application-wide unique identifier key.
 * </p>
 *
 * <p>
 * A <code>ProfileKey</code> is composed of either an id, username and type or simply a (username, type) pair.
 * </p>
 *
 * <p>
 * The id of a <code>ProfileKey</code> is simply a mapping to a (username, type) pair.
 * The (username, type) pair is enough to ensure uniqueness.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread-safe as it is immutable.
 * </p>
 *
 * @author duner, TCSDEVELOPER
 * @version 2.0
 */
public class ProfileKey {
    /**
     * <p>
     * This value represents a unique id assigned to a given user profile.
     * </p>
     *
     * <p>
     * This id is considered to be applicable application-wide (as opposed to on a per data source
     * basis as a database table PK generally would be) and should not be confused with any per
     * data source unique identifiers.
     * </p>
     *
     * <p>
     * There is no guarantee that this value persists long term outside the ProfileKeyManager and
     * so should not be used by the data source for long term identification.
     * </p>
     *
     * <p>
     * This value is guaranteed to be non-negative for all valid id values or -1 in the case
     * that no id is currently set.
     * </p>
     *
     * <p>
     * The value is set in the constructor (to a non-negative value in the constructor taking an id
     * or to -1 in the constructor that doesn't).
     * </p>
     *
     * <p>
     * This value cannot be changed once set in the constructor. It can be accessed from the
     * {@link ProfileKey#getId()} method.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * This value is a context value that serves to identify a given user within a given
     * application or service.
     * </p>
     *
     * <p>
     * As a type usually maps to a single source of data, this means that a type paired
     * with a username identifies a unique user context (a user instance belonging to a
     * certain service or application).
     * </p>
     *
     * <p>
     * This is used by ChatUserProfileManager to direct any persistence operations directly
     * to the applicable data source.
     * </p>
     *
     * <p>
     * This value is set in the constructor to a non-null value. There is no restriction
     * on this value (empty, empty trimmed, etc.).
     * </p>
     *
     * <p>
     * Once set in the constructor, this value cannot be changed.
     * </p>
     *
     * <p>
     * It can be accessed in the {@link ProfileKey#getType()} method.
     * </p>
     */
    private final String type;

    /**
     * <p>
     * This value represents a unique username.
     * </p>
     *
     * <p>
     * A username does not uniquely identify a user within this system since a user may
     * have different user names throughout each service or application.
     * </p>
     *
     * <p>
     * For instance, this application might manage several chat services using different
     * data sources. Each user is required to sign up for each service individually.
     * In such a case, it's possible a user with a username in one service will not
     * have the same username in another service.
     * </p>
     *
     * <p>
     * A user is uniquely identified either by a (username, type) pair since a user can be
     * uniquely identified by username in the datasource or by an id assigned by
     * ProfileKeyManager as that interface is responsible for handling all such mappings.
     * </p>
     *
     * <p>
     * This value is set in the constructor to a non-null, non-empty (after trimmed) value.
     * It can otherwise be any valid string.
     * </p>
     *
     * <p>
     * It cannot be changed once set in the constructor.
     * </p>
     *
     * <p>
     * It can be accessed via the {@link ProfileKey#getUsername()} method.
     * </p>
     */
    private final String username;

    /**
     * <p>
     * Initializes this instance of <code>ProfileKey</code> with user name and type given.
     * </p>
     *
     * <p>
     * In this constructor, the id is set to -1 to indicate that no id has been set.
     * </p>
     *
     * @param username The name of the user.  This value must be non-null and
     * non-empty (trimmed).
     * @param type The type of application/service the user specified by username
     * belongs to.  This value must be non-null, but may be empty.
     * @throws IllegalArgumentException if username is null or empty (trimmed) or
     * if type is null.
     */
    public ProfileKey(String username, String type) {
        Util.checkString(username, "username");
        Util.checkNull(type, "type");

        this.username = username;
        this.type = type;
        this.id = -1;
    }

    /**
     * <p>
     * Initializes this instance of <code>ProfileKey</code> with id, user name and type given.
     * </p>
     *
     * @param id The unique application-wide identifier. This must be a non-negative value.
     * @param username The username of the user. This value must be non-null and non-empty (trimmed).
     * @param type The type of application/service the user specified by username
     * belongs to. This value must be non-null, but may be empty.
     *
     * @throws IllegalArgumentException if username is null or empty (trimmed) or
     * if type is null or id is negative.
     */
    public ProfileKey(long id, String username, String type) {
        if (id < 0) {
            throw new IllegalArgumentException("The give id [" + id + "] is negative.");
        }
        Util.checkString(username, "username");
        Util.checkNull(type, "type");

        this.id = id;
        this.username = username;
        this.type = type;
    }

    /**
     * <p>
     * Gets the type of this <code>ProfileKey</code> instance.
     * </p>
     *
     * <p>
     * This will be non-null.
     * </p>
     *
     * @return the type of this <code>ProfileKey</code> instance.
     */
    public String getType() {
        return this.type;
    }

    /**
     * <p>
     * Gets the user name of this <code>ProfileKey</code> instance.
     * </p>
     *
     * <p>
     * This value will be non-null and non-empty (trimmed).
     * </p>
     *
     * @return the user name of this <code>ProfileKey</code> instance.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * <p>
     * Gets the id of this <code>ProfileKey</code> instance.
     * </p>
     *
     * <p>
     * This value will be non-negative if set, otherwise it will return -1
     * to indicate no value has been set and that the id is not valid.
     * </p>
     *
     * @return the id of this <code>ProfileKey</code> instance.
     */
    public long getId() {
        return this.id;
    }
}
