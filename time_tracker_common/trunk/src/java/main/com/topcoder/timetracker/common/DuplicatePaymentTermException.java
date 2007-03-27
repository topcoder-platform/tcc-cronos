/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 *  This exception is thrown by <code>PaymentTermDAO</code> to indicate that a <code>PaymenTerm</code>
 *  already exists when one was not expected. This only really is thrown from a call to <code>addPaymentTerm()</code>.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is immutable since its super class <code>PaymentTermDAOException</code> is immutable,
 *  so this class is thread-safe.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class DuplicatePaymentTermException extends PaymentTermDAOException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 62322203574596518L;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  The <code>PaymentTerm</code> that the DAO was trying to add but this already exist in the persistence.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Should not be null and should hold the unique identifier which is found to be duplicate in the data store.
     *  Initialized in the constructor.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Accessed through <code>getDupplicatePaymentTerm()</code> method.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final PaymentTerm duplicatePaymentTerm;

    /**
     * <p>
     *  Constructor with error message and duplicate <code>PaymentTerm</code>.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  To follow the tc code convention, the passed in <code>PaymentTerm</code> is not checked. But it should not
     *  be null and should hold the unique identifier which is found to be duplicate in the data store.
     * </p>
     *
     * @param message the error message.
     * @param duplicatePaymentTerm the <code>PaymentTerm</code> that the DAO was trying to add but this already exist
     *        in the persistence.
     */
    public DuplicatePaymentTermException(String message, PaymentTerm duplicatePaymentTerm) {
        super(message);
        this.duplicatePaymentTerm = duplicatePaymentTerm;
    }

    /**
     * <p>
     *  Retrieves the <code>PaymentTerm</code> that the DAO was trying to add but this already exist in the persistence.
     * </p>
     *
     * <p>
     *  The returned <code>PaymentTerm</code> should hold the unique identifier which is found to be duplicate in the
     *  data store.
     * </p>
     *
     * @return the <code>PaymentTerm</code> that the DAO was trying to add but this already exist in the persistence.
     *         May be null.
     */
    public PaymentTerm getDuplicatePaymentTerm() {
        return this.duplicatePaymentTerm;
    }
}
