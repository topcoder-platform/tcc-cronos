/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>
 * This exception will be thrown by the Manager classes, EJB entities, and DAO classes when any operation
 * in the batch is failed. This exception will be exposed to the caller of batch operation methods.
 * </p>
 *
 * <p>
 * Since the batch operation exception is some kind of database access error, so this class extends
 * <code>PersistenceException</code>.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class BatchOperationException extends PersistenceException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 6814287671643198756L;

    /**
     * <p>
     * Represents whether the operations in the batch are successfully completed.
     * It will be set in the constructor. It is possible null, possible empty.
     * It will be referenced by the <code>getResult()</code> method.
     * </p>
     */
    private final boolean[] result;

    /**
     * <p>Constructs the exception with given message and result of batch operation.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param result possible null, possible empty array represents whether the
     *        operations in the batch are successfully completed
     */
    public BatchOperationException(String message, boolean[] result) {
        super(message);
        this.result = result == null ? null : (boolean[]) result.clone();
    }

    /**
     * <p>Constructs the exception with given message, cause and result of batch operation.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     * @param result possible null, possible empty array represents whether the
     *        operations in the batch are successfully completed
     */
    public BatchOperationException(String message, Exception cause, boolean[] result) {
        super(message, cause);
        this.result = result == null ? null : (boolean[]) result.clone();
    }

    /**
     * <p>Get the result of the batch operation.</p>
     *
     * @return possible null, possible empty array represents whether the operations in the batch are
     *         successfully completed
     */
    public boolean[] getResult() {
        return this.result == null ? null : (boolean[]) this.result.clone();
    }
}
