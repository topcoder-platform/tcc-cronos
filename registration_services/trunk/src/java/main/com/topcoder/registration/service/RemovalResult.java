/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

/**
 * <p>
 * The interface that defines the result of the registration removal attempt. It defines a flag for
 * success, potential error messages if the registration removal was not successful.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining getters for these properties.
 * </p>
 * <p>
 * It has one implementation: RemovalResultImpl.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public interface RemovalResult {

    /**
     * <p>
     * Gets the result flag for the removal. It will be <code>true</code> if successful, and
     * <code>false</code> otherwise.
     * </p>
     * @return true if removal was successful, and false otherwise
     */
    public boolean isSuccessful();

    /**
     * <p>
     * Gets the array of error messages associated with a unsuccessful removal. The array will not
     * contain any null elements, and will return an empty array if there are no error messages.
     * </p>
     * @return String array of error messages
     */
    public String[] getErrors();
}
