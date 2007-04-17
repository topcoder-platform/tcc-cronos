/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import junit.framework.TestCase;


/**
 * Unit test for <code>RejectReasonDAOException</code> class. Instantiation tests and basic functionalities tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonDAOExceptionTests extends TestCase {
    /** The exception message to create the exception. */
    private final String message = "an exception message";

    /** The cause to create the exception. */
    private final Throwable cause = new Exception();

    /** The reject reason to create the exception. */
    private final RejectReason reason = new RejectReason();

    /**
     * <p>
     * Tests constructor(String). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessage() {
        RejectReasonDAOException exception = new RejectReasonDAOException(message);

        assertNotNull("Unable to instantiate RejectReasonDAOException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", null, exception.getCause());
        assertEquals("ProblemRejectReason is not initialized correctly", null, exception.getProblemRejectReason());
    }

    /**
     * <p>
     * Tests constructor(String, Throwable). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessageAndCause() {
        RejectReasonDAOException exception = new RejectReasonDAOException(message, cause);

        assertNotNull("Unable to instantiate RejectReasonDAOException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", cause, exception.getCause());
        assertEquals("ProblemRejectReason is not initialized correctly", null, exception.getProblemRejectReason());
    }

    /**
     * <p>
     * Tests constructor(String, Throwable, RejectReason). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessageAndCauseAndRejectReason() {
        RejectReasonDAOException exception = new RejectReasonDAOException(message, cause, reason);

        assertNotNull("Unable to instantiate RejectReasonDAOException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", cause, exception.getCause());
        assertEquals("ProblemRejectReason is not initialized correctly", reason, exception.getProblemRejectReason());
    }

    /**
     * <p>
     * Tests getProblemRejectReason method accuracy.
     * </p>
     */
    public void testGetProblemRejectReason_accuracy() {
        RejectReasonDAOException exception = new RejectReasonDAOException(null);
        assertEquals("The method getProblemRejectReason does not function correctly.", null,
            exception.getProblemRejectReason());
        exception = new RejectReasonDAOException(null, null, reason);
        assertEquals("The method getProblemRejectReason does not function correctly.", reason,
            exception.getProblemRejectReason());
    }
}
