/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 *  This exception is thrown if the involved <code>PaymentTerm</code> was not found in the persistence.
 *  This is thrown during update/delete methods.
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
public class PaymentTermNotFoundException extends PaymentTermDAOException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 8313867367875352566L;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  The ID of the PaymentTerm that the DAO was working with when a problem occurred.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Should be positive. Initialized in the constructor.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *  Accessed through <code>getProblemPaymentTermId()</code> method.
     * </p>
     *
     * <p>
     *  <strong>Immutability:</strong>
     *  Immutable.
     * </p>
     */
    private final long problemPaymentTermId;

    /**
     * <p>
     *  Constructor with error message and id of <code>PaymentTerm</code> which was not found in the persistence.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  To follow the tc code convention, the passed in <em>problemPaymentTermId</em> is not checked.
     *  But it should be positive.
     * </p>
     *
     * @param message the error message.
     * @param problemPaymentTermId the id of the <code>PaymentTerm</code> that was not found in the persistence.
     */
    public PaymentTermNotFoundException(String message, long problemPaymentTermId) {
        super(message);
        this.problemPaymentTermId = problemPaymentTermId;
    }

    /**
     * <p>
     *  Retrieves the Id of the <code>PaymentTerm</code> that was not found in the persistence.
     * </p>
     *
     * @return the Id of the <code>PaymentTerm</code> that was not found in the persistence.
     */
    public long getProblemPaymentTermId() {
        return this.problemPaymentTermId;
    }
}
