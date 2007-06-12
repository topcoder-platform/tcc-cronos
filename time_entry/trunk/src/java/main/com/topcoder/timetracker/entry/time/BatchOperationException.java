/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * This exception is thrown during batch operations where one or more of the elements in the batch failed to
 * be processed properly.
 * </p>
 *
 * <p>
 * It will contain an array of causes: each element that managed to be processed successfully will have a
 * null for the corresponding element in the causes array, and each element that failed to be processed
 * would have an Exception detailing the reason for failure in the causes array.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class BatchOperationException extends DataAccessException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 3368081700843921022L;

    /**
     * <p>
     * This is the array of causes for the failure of some of the elements within the batch.
     * </p>
     */
    private Throwable[] causes;

    /**
     * <p>
     * Constructor accepting message and an array of causes.
     * </p>
     *
     * <p>
     * Note, no clone is done to the given causes array.
     * </p>
     *
     * @param message The message of the exception.
     * @param causes An array of causes for the exception.
     */
    public BatchOperationException(String message, Throwable[] causes) {
        super(message);
        this.causes = causes;
    }

    /**
     * <p>
     * Retrieves the array of causes for the failure of some of the elements within the batch.
     * </p>
     *
     * <p>
     * Note, no clone is done to the causes array.
     * </p>
     *
     * @return the array of causes for the failure of some of the elements within the batch.
     */
    public Throwable[] getCauses() {
        return causes;
    }
}
