/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>This is a general-purpose exception created for the component to signal that something went wrong during
 * configuration processing.</p>
 *<p>Thread Safety: This class is thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
  */
public class ConfigurationException extends BaseException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = 6954636023603139743L;

	/**
     * Creates a new exception with passed error message.
     *
     *
     * @param msg error message
     */
    public ConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception with this error message and cause of error.
     *
     *
     * @param msg error message
     * @param cause the cause of the error
     */
    public ConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
