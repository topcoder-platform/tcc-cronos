/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

/**
 * <p>
 * SecurityGroupsActionException is thrown by struts actions if any error is caught during the action operation.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong>
 * This class is not thread safe because its base class is not thread safe.
 * </p>
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SecurityGroupsActionException extends Exception {
    /**
     * <p>
     * Create the exception, call corresponding parent constructor.
     * </p>
     * @param message The error message to set.
     */
    public SecurityGroupsActionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create the exception, call corresponding parent constructor.
     * </p>
     * @param message The error message to set.
     * @param cause The cause error to set.
     */
    public SecurityGroupsActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
