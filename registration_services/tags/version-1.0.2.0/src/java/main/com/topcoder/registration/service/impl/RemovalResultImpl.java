/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.registration.service.RemovalResult;

/**
 * <p>
 * Simple implementation of the <code>RemovalResult</code> interface. Implements all methods in
 * that interface, and provides a default constructor if the user wants to set all values via the
 * setters, and one full constructor to set these values in one go.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread-safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RemovalResultImpl implements RemovalResult {

    /**
     * <p>
     * Represents a flag as to whether the removal was successful.
     * </p>
     * <p>
     * This value can be set in the full constructor or in the bean setter method, and retrieved in
     * the bean getter method.
     * </p>
     */
    private boolean successful;

    /**
     * <p>
     * Represents the error messages if the removal was not successful.
     * </p>
     * <p>
     * It is set in the constructor or setter to a non-null array. The array may be empty, which
     * would mean no errors if the successful flag is true, or not explicit error messages given
     * even if the successful flag is false. Null/empty elements in the array will also not be
     * acceptable. When set in the constructor or setter, it will be set to a shallow copy of any
     * passed message array. When retrieved, it will pass a shallow copy of the array to the user.
     * It is accessed via the getter method.
     * </p>
     */
    private String[] errors;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public RemovalResultImpl() {
        this.successful = true;
        this.errors = new String[0];
    }

    /**
     * <p>
     * Creates a new RemovalResultImpl from the passed values.
     * </p>
     * @param successful
     *            a flag as to whether the removal was successful
     * @param errors
     *            the error messages, potentially, if the removal was not successful.
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true or errors contains
     *             null/empty elements in the array
     */
    public RemovalResultImpl(boolean successful, String[] errors) {
        if (successful && errors != null && errors.length > 0) {
            throw new IllegalArgumentException(
                "[errors] array should be null or empty when [successful] flag is true.");
        }
        Util.checkErrorsArray(errors);

        this.successful = successful;
        if (errors == null) {
            this.errors = new String[0];
        } else {
            this.errors = (String[]) errors.clone();
        }
    }

    /**
     * <p>
     * Gets the result flag for the removal.
     * </p>
     * <p>
     * It will be <code>true</code> if successful, and <code>false</code> otherwise.
     * </p>
     * @return true if removal was successful, and false otherwise.
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * <p>
     * Sets the success of the removal attempt.
     * </p>
     * @param successful
     *            a flag as to whether the registration was successful
     * @throws IllegalArgumentException
     *             if successful is true when errors array not empty
     */
    public void setSuccessful(boolean successful) {
        if (successful && errors.length > 0) {
            throw new IllegalArgumentException(
                "Can't set 'successful' to true when errors messages exist.");
        }
        this.successful = successful;
    }

    /**
     * <p>
     * Gets the array of error messages associated with a unsuccessful removal.
     * </p>
     * @return String array of error messages
     */
    public String[] getErrors() {
        return (String[]) errors.clone();
    }

    /**
     * <p>
     * Sets the array of error messages associated with an unsuccessful removal.
     * </p>
     * @param errors
     *            the error messages, potentially, if the registration was not successful.
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true, or if errors array
     *             contains null/empty elements in the array
     */
    public void setErrors(String[] errors) {
        if (successful && errors != null && errors.length > 0) {
            throw new IllegalArgumentException(
                "[errors] array should be null or empty when [successful] flag is true.");
        }
        Util.checkErrorsArray(errors);

        if (errors == null) {
            this.errors = new String[0];
        } else {
            this.errors = (String[]) errors.clone();
        }
    }
}
