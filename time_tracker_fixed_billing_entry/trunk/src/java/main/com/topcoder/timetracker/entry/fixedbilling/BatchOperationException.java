/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

/**
 * <p>
 * This exception is thrown during batch operations where one or more of the elements in the batch failed to be
 * processed properly. It will contain an array of causes: each element that managed to be processed successfully
 * will have a null for the corresponding element in the causes array, and each element that failed to be processed
 * would have an Exception detailing the reason for failure in the causes array.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class BatchOperationException extends DataAccessException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = -7951750247159660429L;

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
     * @return the array of causes for the failure of some of the elements within the batch.
     */
    public Throwable[] getCauses() {
        return causes;
    }
}
