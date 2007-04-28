/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.persistence;

/**
 * <p>
 * This exception indicates an error in the batch operation. It is thrown by the implementations of
 * TimeTrackerProjectPersistence interface. It stores the array of cause exceptions, each corresponding to the
 * particular operation. If a particular operation was successful the array contains null in that place.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public class BatchOperationException extends PersistenceException {
    /**
     * <p>
     * The array of cause exceptions, each corresponding to the particular operation. If a particular operation was
     * successful the array contains null in that place. It is initialized in constructor and then never changed.
     * </p>
     */
    private final Throwable[] causes;

    /**
     * <p>
     * Creates an exception with the specified error message and the array of cause exceptions, each corresponding to
     * the particular operation. If a particular operation was successful the array should contain null in that place.
     * A shallow copy of the causes is made.
     * </p>
     *
     * @param message the error message describing the exception.
     * @param causes the array of cause exceptions.
     */
    public BatchOperationException(String message, Throwable[] causes) {
        super(message);
        this.causes = (causes == null) ? null : (Throwable[]) causes.clone();
    }

    /**
     * <p>
     * Returns a copy of the array of cause exceptions, each corresponding to the particular operation. If a particular
     * operation was successful the array contains null in that place.
     * </p>
     *
     * @return the array of cause exceptions.
     */
    public Throwable[] getCauses() {
        return (causes == null) ? null : (Throwable[]) causes.clone();
    }
}
