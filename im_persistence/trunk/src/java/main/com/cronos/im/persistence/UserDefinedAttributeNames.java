/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

/**
 * <p>Defines constants to be used as custom property names in the <code>ChatUserProfile</code> class.
 *
 * <p><strong>THread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class UserDefinedAttributeNames {
    /**
     * The property name representing the user's first name.
     */
    public static final String FIRST_NAME = "First Name";

    /**
     * The property name representing the user's last name.
     */
    public static final String LAST_NAME = "Last Name";

    /**
     * The property name representing the user's company name.
     */
    public static final String COMPANY = "Company";

    /**
     * The property name representing the user's title.
     */
    public static final String TITLE = "Title";

    /**
     * The property name representing the user's e-mail address.
     */
    public static final String EMAIL = "Email";

    /**
     * The property name representing the user's name. This does not correspond to any database column, but will be
     * populated based on the first and last name columns.
     */
    public static final String NAME = "Name";

    /**
     * Private constructor to prevent instantiation.
     */
    private UserDefinedAttributeNames() {
    }
}
