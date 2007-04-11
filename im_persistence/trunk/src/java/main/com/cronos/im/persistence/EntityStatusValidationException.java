/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.database.statustracker.persistence.StatusTrackerPersistenceException;

/**
 * Exception thrown when an <code>EntityKey</code> fails validation in the {@link
 * InformixEntityStatusTracker#setStatus setStatus} method.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class EntityStatusValidationException extends StatusTrackerPersistenceException {
    /**
     * Creates a new <code>EntityStatusValidationException</code> with the specified message.
     *
     * @param message a description of the reason for the exception
     */
    public EntityStatusValidationException(String message) {
        super(message);
    }
}
