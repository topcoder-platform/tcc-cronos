/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This is an exception that signals that there was an issue with a search operation.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class SearchException extends BaseException {
    /**
     * <p>
     * creates a new instance of exception initialized with input values..
     * </p>
     *
     * @param message exception message
     */
    public SearchException(String message) {
        super(message);
    }

    /**
     * <p>
     * creates a new instance of exception initialized with input values..
     * </p>
     *
     * @param message exception message
     * @param cause wrapped cause exception
     */
    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
