/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.team.service.OperationResult;

/**
 * <p>
 * Simple implementation of the <code>OperationResult</code> interface.
 * Implements all methods in that interface, and adds the required setters. It
 * provides the required default constructor if the user wants to set all values
 * via the setters, and one full constructor to set these values in one go.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class OperationResultImpl implements OperationResult {

    /**
     * <p>
     * Represents a flag as to whether the operation was successful.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed
     * with the getter.
     * </p>
     *
     */
    private boolean successful;

    /**
     * <p>
     * Represents the error messages if the operation was not successful.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed
     * with the getter. The array may be empty, which would mean no errors if
     * the successful flag is true, or not explicit error messages given even if
     * the successful flag is false. Null/empty elements in the array will also
     * not be acceptable, not will this be anything but empty if successful is
     * true.
     * </p>
     * <p>
     * When set in the constructor or setter, it will be set to a shallow copy
     * of any passed message array. When retrieved, it will pass a shallow copy
     * of the array to the user.
     * </p>
     *
     */
    private String[] errors;

    /**
     * <p>
     * Default constructor. Sets errors to an empty.
     * </p>
     *
     */
    public OperationResultImpl() {
        this.successful = true;
        this.errors = new String[0];
    }

    /**
     * <p>
     * Constructor: Creates a new OperationResultImpl from the passed values.
     * </p>
     * <p>
     * It is possible that errors array may not contain any error messages even
     * if the operation fails. But the reverse is not allowed. Only a null or
     * empty array will be allowed if operation succeeds.
     * </p>
     * <p>
     * As noted, a null errors parameter is interpreted as an empty message
     * array, and will be set as such to this.errors field. Otherwise, a shallow
     * copy is made of the incoming array to put in that field. Null/empty
     * elements in the array are illegal.
     * </p>
     *
     *
     * @param successful
     *            a flag as to whether the operation was successful
     * @param errors
     *            the error messages, potentially, if the operation was not
     *            successful
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true
     * @throws IllegalArgumentException
     *             if errors contains null/empty elements in the array
     */
    public OperationResultImpl(boolean successful, String[] errors) {
        this.successful = successful;
        setErrors(errors);
    }

    /**
     * Gets the result flag for the operation. It will be true if successful,
     * and false otherwise.
     *
     *
     * @return true If operation was successful, and false otherwise.
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Sets the result flag for the operation. Since a successful result must
     * not have error messages, the errors field must be empty for this field to
     * be true.
     *
     *
     * @param successful
     *            a flag as to whether the operation was successful
     * @throws IllegalArgumentException
     *             if successful is true, but errors is not empty
     */
    public void setSuccessful(boolean successful) {
        if (successful && errors.length > 0) {
            throw new IllegalArgumentException(
                    "successful is true, but errors is not empty");
        }
        this.successful = successful;
    }

    /**
     * Gets the array of error messages associated with a unsuccessful
     * operation. The array will not contain any null/empty elements, and will
     * return an empty array if there are no error messages. This array is a
     * shallow copy of the internal array.
     *
     *
     * @return String array of error messages.
     */
    public String[] getErrors() {
        return (String[]) errors.clone();
    }

    /**
     * Sets the array of error messages associated with a unsuccessful
     * operation. A null errors parameter is interpreted as an empty message
     * array, and will be set as such to this.errors field. Otherwise, a shallow
     * copy is made of the incoming array to put in that field. Null/empty
     * elements in the array are illegal.
     *
     *
     * @param errors
     *            the error messages, potentially, if the operation was not
     *            successful
     * @throws IllegalArgumentException
     *             if errors is a non-empty array but successful is true
     * @throws IllegalArgumentException
     *             if errors contains null/empty elements in the array
     */
    public void setErrors(String[] errors) {
        RegistrationValidationHelper.validateErrorMessages(successful, errors);
        if (successful) {
            this.errors = new String[0];
        } else {
            this.errors = (errors == null) ? (new String[0])
                    : (String[]) errors.clone();
        }
    }
}
