/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;


/**
 * <p>
 * Enum class for entity types. It is used in <code>JiveForumManager</code> class' watch methods
 * to identify the type of the entityId. It contains values: FORUM_CATEGORY, FORUM, FORUM_THREAD.
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
public enum EntityType {
    /**
     * <p>
     * Represents the forum category entity type.
     * </p>
     */
    FORUM_CATEGORY,
    /**
     * <p>
     * Represents the forum entity type.
     * </p>
     */
    FORUM,
    /**
     * <p>
     * Represents the forum thread entity type.
     * </p>
     */
    FORUM_THREAD;
}
