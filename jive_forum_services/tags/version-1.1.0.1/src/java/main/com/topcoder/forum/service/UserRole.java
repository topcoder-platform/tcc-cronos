/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;


/**
 * <p>
 * Enum class for the roles to be assigned to the user. It's used in <code>JiveForumManager</code>
 * to identify the role of the user. It contains values: NO_ACCESS, CONTRIBUTOR, MODERATOR.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * Enum class is thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public enum UserRole {
    /**
     * <p>
     * Represents the role that the user must not belong to moderators or users groups.
     * </p>
     */
    NO_ACCESS,
    /**
     * <p>
     * Represents the role that the user must just belong to users group.
     * </p>
     */
    CONTRIBUTOR,
    /**
     * <p>
     * Represents the role that the user must just belong to moderators group.
     * </p>
     */
    MODERATOR;
}
