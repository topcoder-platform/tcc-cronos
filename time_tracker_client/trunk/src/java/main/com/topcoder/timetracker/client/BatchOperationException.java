/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

/**
 * <p>
 * This exception will be thrown by the ClientUtility and ClientDAO if the batch operation can't be completed
 * successfully.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class BatchOperationException extends ClientPersistenceException {
    /**
     * <p>
     * Represents whether the operations in batch are successful. It is set in the constructor, possible null, possible
     * empty. It will be referenced by the getResult method.
     * </p>
     */
    private final boolean[] result;

    /**
     * <p>
     * Constructs the exception with given message.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param result possible null, possible empty boolean array representing whether the operation is successful
     */
    public BatchOperationException(String message, boolean[] result) {
        super(message);
        if (result != null) {
            this.result = (boolean[]) result.clone();
        } else {
            this.result = null;
        }
    }

    /**
     * <p>
     * Constructs the exception with given message and cause.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     * @param result possible null, possible empty boolean array representing whether the operation is successful
     */
    public BatchOperationException(String message, Exception cause, boolean[] result) {
        super(message, cause);

        if (result != null) {
            this.result = (boolean[]) result.clone();
        } else {
            this.result = null;
        }
    }

    /**
     * <p>
     * Return the operation result.
     * </p>
     *
     * @return possible null, possible empty boolean array representing whether the operation is successful
     */
    public boolean[] getResult() {
        if (result != null) {
            return (boolean[]) result.clone();
        } else {
            return null;
        }
    }
}
