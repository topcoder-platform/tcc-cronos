/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.client;

/**
 * <p>
 * Component's base exception. The service will throw this exception and all other specific exceptions should inherit
 * from it.
 * </p>
 * 
 * @author romanoTC
 * @version 1.0
 */
public class JiraClientServiceException extends Exception {
    /**
     * <p>
     * Constructs a <code>JiraClientServiceException</code> with the given <code>message</code>.
     * </p>
     * 
     * @param message Useful message containing a description of why the exception was thrown - may be null.
     */
    public JiraClientServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a <code>JiraClientServiceException</code> taking both a <code>message</code> and a
     * <code>cause</code> detailing why the critical exception occurs.
     * </p>
     * 
     * @param message Useful message containing a description of why the exception was thrown - may be null.
     * @param cause The initial throwable reason which triggered this exception to be thrown - may be null.
     */
    public JiraClientServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
