/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>This is a general manager exception which will be used to wrap all other exceptions when internal processing
 * fails for some reason (like persistence for example).</p>
 *  <p>Thread Safety: This class is thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class EntryManagerException extends BaseException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = 3136881029059756912L;

	/**
     * Creates a new exception with passed error message.
     * @param msg error message
     */
    public EntryManagerException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception with this error message and cause of error.
     *
     * @param msg error message
     * @param cause the cause of the error
     */
    public EntryManagerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
