/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker.persistence;

/**
 * <p>
 * This exception is thrown by the various findBy methods of the Manager implementations in the
 * Status Tracker component, and by some of the other persistence methods.
 * </p>
 *
 * <p>
 * It indicates to the user that the requested object could not be found, or a related object that it
 * depended on could not be found in persistent storage.
 * </p>
 *
 * <p>
 * Note, Only a single one-arg constructor is included because this exception should only be
 * constructed as a result of a <b>logic</b> error, not a database, or other resource exception.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is immutable and thread safe.
 * </p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class RecordNotFoundException extends StatusTrackerPersistenceException {
    /**
     * <p>
     * Construct this exception with the given message.
     * </p>
     *
     * <p>
     * Note, the message can be null, but it should describe the record that was not found (e.g., its type and
     * primary key) in the message.
     * </p>
     *
     * @param message the message to include.
     * @throws IllegalArgumentException if message is empty after trim
     */
    public RecordNotFoundException(String message) {
        super(message);
    }
}
