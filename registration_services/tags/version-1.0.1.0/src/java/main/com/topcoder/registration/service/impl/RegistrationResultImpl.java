/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.RegistrationResult;

/**
 * <p>
 * This is a simple implementation of the <code>RegistrationResult</code> interface. It implements
 * all methods in that interface, and provides a default constructor if the user wants to set all
 * values via the setters, and one full constructor to set these values in one go.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread-safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationResultImpl implements RegistrationResult {

    /**
     * <p>
     * Represents a flag as to whether the registration was successful.
     * </p>
     * <p>
     * This value can be set in the full constructor or in the bean setter method, and retrieved in
     * the bean getter method.
     * </p>
     */
    private boolean successful;

    /**
     * <p>
     * Represents the error messages if the registration was not successful.
     * </p>
     * <p>
     * It is set in the constructor or setter to a non-null array. The array may be empty, which
     * would mean no errors if the successful flag is true, or not explicit error messages given
     * even if the successful flag is false. Null/empty elements in the array will also not be
     * acceptable. When set in the constructor or setter , it will be set to a shallow copy of any
     * passed message array. When retrieved, it will pass a shallow copy of the array to the user.
     * It is accessed via the <code>getErrors</code> method.
     * </p>
     */
    private String[] errors;

    /**
     * <p>
     * Represents the previous registration for this member.
     * </p>
     * <p>
     * This value may be null if the registration was successful but there was not previous
     * registration to replace. This value would be null if the registration process was not
     * successful regardless if there was a previous registration or not. This value is set in the
     * constructor or setter and will not change. It is accessed via the getter.
     * </p>
     */
    private Resource previousRegistration;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public RegistrationResultImpl() {
        this.successful = true;
        this.errors = new String[0];
        this.previousRegistration = null;
    }

    /**
     * <p>
     * Creates a new <code>RegistrationResultImpl</code> from the passed values.
     * </p>
     * @param successful
     *            a flag as to whether the registration was successful
     * @param errors
     *            the error messages, potentially, if the registration was not successful.
     * @param previousRegistration
     *            the previous registration for this member, if available
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true or errors contains
     *             null/empty elements in the array or previousRegistration is not null but
     *             successful is false
     */
    public RegistrationResultImpl(boolean successful, String[] errors, Resource previousRegistration) {
        if (successful && errors != null && errors.length > 0) {
            throw new IllegalArgumentException(
                "[errors] array should be null or empty when [successful] flag is true.");
        }
        if (!successful && previousRegistration != null) {
            throw new IllegalArgumentException(
                "Previous registration should be null when registration failed.");
        }
        Util.checkErrorsArray(errors);

        this.successful = successful;
        this.previousRegistration = previousRegistration;
        if (errors == null) {
            this.errors = new String[0];
        } else {
            this.errors = (String[]) errors.clone();
        }
    }

    /**
     * <p>
     * Gets the result flag for the registration.
     * </p>
     * <p>
     * It will be <code>true</code> if successful, and <code>false</code> otherwise.
     * </p>
     * @return true if registration was successful, and false otherwise.
     */
    public boolean isSuccessful() {
        return this.successful;
    }

    /**
     * <p>
     * Sets the success of the registration attempt.
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
     * Gets the array of error messages associated with a unsuccessful registration.
     * </p>
     * @return String array of error messages.
     */
    public String[] getErrors() {
        return (String[]) errors.clone();
    }

    /**
     * <p>
     * Sets the array of error messages associated with an unsuccessful registration.
     * </p>
     * @param errors
     *            the error messages, potentially, if the registration was not successful
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true, or if errors contains
     *             null/empty elements in the array
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

    /**
     * <p>
     * Gets the previous registration resource.
     * </p>
     * <p>
     * It would be null if either there was no previous registration resource, or if the
     * registration failed.
     * </p>
     * @return the previous registration resource
     */
    public Resource getPreviousRegistration() {
        return this.previousRegistration;
    }

    /**
     * <p>
     * Sets the previous registration, if available.
     * </p>
     * <p>
     * Note that if successful is false, this must be null.
     * </p>
     * @param resource
     *            the previous registration resource
     * @throws IllegalArgumentException
     *             if resource is not null but successful is false
     */
    public void setPreviousRegistration(Resource resource) {
        if (resource != null && !successful) {
            throw new IllegalArgumentException(
                "The parameter [resource] should be null when [successful] flag is false.");
        }

        this.previousRegistration = resource;
    }
}
