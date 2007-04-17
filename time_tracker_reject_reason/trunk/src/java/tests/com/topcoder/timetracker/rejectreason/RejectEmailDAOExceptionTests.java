/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import junit.framework.TestCase;


/**
 * Unit test for <code>RejectEmailDAOException</code> class. Instantiation tests and basic functionalities tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailDAOExceptionTests extends TestCase {
    /** The exception message to create the exception. */
    private final String message = "an exception message";

    /** The cause to create the exception. */
    private final Throwable cause = new Exception();

    /** The reject email to create the exception. */
    private final RejectEmail email = new RejectEmail();

    /**
     * <p>
     * Tests constructor(String). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessage() {
        RejectEmailDAOException exception = new RejectEmailDAOException(message);

        assertNotNull("Unable to instantiate RejectEmailDAOException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", null, exception.getCause());
        assertEquals("ProblemRejectEmail is not initialized correctly", null, exception.getProblemRejectEmail());
    }

    /**
     * <p>
     * Tests constructor(String, Throwable). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessageAndCause() {
        RejectEmailDAOException exception = new RejectEmailDAOException(message, cause);

        assertNotNull("Unable to instantiate RejectEmailDAOException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", cause, exception.getCause());
        assertEquals("ProblemRejectEmail is not initialized correctly", null, exception.getProblemRejectEmail());
    }

    /**
     * <p>
     * Tests constructor(String, Throwable, RejectEmail). Verifies it can be instantiated properly.
     * </p>
     */
    public void testConstructorWithMessageAndCauseAndRejectEmail() {
        RejectEmailDAOException exception = new RejectEmailDAOException(message, cause, email);

        assertNotNull("Unable to instantiate RejectEmailDAOException", exception);
        assertTrue("Message is not initialized correctly", exception.getMessage().matches(message + ".*"));
        assertEquals("Cause is not initialized correctly", cause, exception.getCause());
        assertEquals("ProblemRejectEmail is not initialized correctly", email, exception.getProblemRejectEmail());
    }

    /**
     * <p>
     * Tests getProblemRejectEmail method accuracy.
     * </p>
     */
    public void testGetProblemRejectEmail_accuracy() {
        RejectEmailDAOException exception = new RejectEmailDAOException(null);
        assertEquals("The method getProblemRejectEmail does not function correctly.", null,
            exception.getProblemRejectEmail());
        exception = new RejectEmailDAOException(null, null, email);
        assertEquals("The method getProblemRejectEmail does not function correctly.", email,
            exception.getProblemRejectEmail());
    }
}
