/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.client;

/**
 * <p>
 * This exception is thrown if the specified group does not exist in Confluence.
 * </p>
 * 
 * @author romanoTC
 * @since 1.0
 */
public class UnknownGroupException extends ConfluenceClientServiceException {
    /**
     * <p>
     * Constructs a <code>UnknownGroupException</code> with the given <code>message</code>.
     * </p>
     * 
     * @param message Useful message containing a description of why the exception was thrown - may be null.
     */
    public UnknownGroupException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a <code>UnknownGroupException</code> taking both a <code>message</code> and a <code>cause</code>
     * detailing why the critical exception occurs.
     * </p>
     * 
     * @param message Useful message containing a description of why the exception was thrown - may be null.
     * @param cause The initial throwable reason which triggered this exception to be thrown - may be null.
     */
    public UnknownGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
