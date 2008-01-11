/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;


/**
 * <p>
 * Enum class for category types. It is used in <code>JiveForumManager</code> class' createCategory
 * method to identify the type of the template category. It contains values: APPLICATION, COMPONENT,
 * ASSEMBLY_COMPETITION, TESTING_COMPETITION.
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
public enum CategoryType {
    /**
     * <p>
     * Represents the application category type.
     * </p>
     */
    APPLICATION,
    /**
     * <p>
     * Represents the component category type.
     * </p>
     */
    COMPONENT,
    /**
     * <p>
     * Represents the assembly competition category type.
     * </p>
     */
    ASSEMBLY_COMPETITION,
    /**
     * <p>
     * Represents the testing competition category type.
     * </p>
     */
    TESTING_COMPETITION;
}
