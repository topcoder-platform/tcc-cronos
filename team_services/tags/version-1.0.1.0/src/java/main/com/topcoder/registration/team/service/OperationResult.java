/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import java.io.Serializable;

/**
 * <p>
 * The interface that defines the result of a team service operation. It defines a flag for success,
 * and potential error messages if the team service operation was not successful. Extends
 * <code>java.io.Serializable</code> so it can be used in a remote environment.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining getters for these properties. The
 * implementations are to provide the setters and at least an empty constructor.
 * </p>
 * <p>
 * It has one implementation: <code>OperationResultImpl</code>.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface OperationResult extends Serializable {

    /**
     * <p>
     * Gets the result flag for the operation.
     * </p>
     * <p>
     * It will be true if successful, and false otherwise.
     * </p>
     * @return true If operation was successful, and false otherwise.
     */
    public boolean isSuccessful();

    /**
     * <p>
     * Gets the array of error messages associated with a unsuccessful operation.
     * </p>
     * <p>
     * The array will not contain any null elements, and will return an empty array if there are no
     * error messages
     * </p>
     * @return String array of error messages.
     */
    public String[] getErrors();
}
