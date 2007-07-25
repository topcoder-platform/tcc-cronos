/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

/**
 * <p>
 * A simple listing of the names of properties to be used as custom properties in a
 * <code>Resource</code>. These include an external reference ID, i.e. user ID, a user handle,
 * user email, and the date of the registration. These are added when the user registers in the
 * <code>RegistrationServices.registerForProject</code> method.
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class CustomResourceProperties {

    /**
     * <p>
     * Represents a custom resource property key for an external reference ID, generally the user
     * ID.
     * </p>
     */
    public static final String EXTERNAL_REFERENCE_ID = "External Reference ID";

    /**
     * <p>
     * Represents a custom resource property key for the user handle.
     * </p>
     */
    public static final String HANDLE = "Handle";

    /**
     * <p>
     * Represents a custom resource property key for the user email.
     * </p>
     */
    public static final String EMAIL = "Email";

    /**
     * <p>
     * Represents a custom resource property key for registration date.
     * </p>
     */
    public static final String REGISTRATION_DATE = "Registration Date";

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private CustomResourceProperties() {
    }
}
