/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import com.topcoder.management.resource.Resource;

/**
 * <p>
 * The interface that defines the result of the registration attempt, generally referring to the
 * validation of the registration information. It defines a flag for success, potential error
 * messages if the registration was not successful, and the previous registration information, if
 * available, if the registration was successful.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining getters for these properties.
 * </p>
 * <p>
 * It has one implementation: RegistrationResultImpl.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public interface RegistrationResult {

    /**
     * <p>
     * Gets the result flag for the registration. It will be true if successful, and false
     * otherwise.
     * </p>
     * @return true If registration was successful, and false otherwise
     */
    public boolean isSuccessful();

    /**
     * <p>
     * Gets the array of error messages associated with a unsuccessful registration. The array will
     * not contain any null elements, and will return an empty array if there are no error messages
     * </p>
     * @return String array of error messages
     */
    public String[] getErrors();

    /**
     * <p>
     * Gets the previous registration resource. It would be null if either there was no previous
     * registration resource, or if the registration failed.
     * </p>
     * @return the previous registration resource
     */
    public Resource getPreviousRegistration();
}
