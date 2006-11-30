/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the PersistenceException class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {

    /**
     * <p>
     * A sample exception message for testing the PersistenceException class.
     * </p>
     */
    private static final String MESSAGE = "A persistence error has occurred";

    /**
     * <p>
     * A sample cause exception for testing the PersistenceException class.
     * </p>
     */
    private Exception cause = null;

    /**
     * <p>
     * Creates the sample cause exception for testing the PersistenceException
     * class.
     * </p>
     */
    protected void setUp() {
        cause = new Exception("Something went wrong");
    }

    /**
     * <p>
     * Tests the PersistenceException(String message) constructor with a valid
     * non-null argument. The newly created instance should not be null. The
     * getMessage() method should return a value equal to the constructor
     * argument.
     * </p>
     */
    public void testCtor1WithValidNonNullArg() {
        PersistenceException e = new PersistenceException(MESSAGE);
        assertNotNull("The PersistenceException instance should not be null", e);
        assertEquals("The message is incorrect", MESSAGE, e.getMessage());
    }

    /**
     * <p>
     * Tests the PersistenceException(String message) constructor with a valid
     * null argument. The newly created instance should not be null. The
     * getMessage() method should return null.
     * </p>
     */
    public void testCtor1WithValidNullArg() {
        PersistenceException e = new PersistenceException(null);
        assertNotNull("The PersistenceException instance should not be null", e);
        assertNull("The message should be null", e.getMessage());
    }

    /**
     * <p>
     * Tests the PersistenceException(String message, Throwable cause)
     * constructor with valid non-null arguments. The newly created instance
     * should not be null. The getMessage() method should return a string that
     * starts with the "message" argument. The getCause() method should return a
     * value equal to the "cause" argument.
     * </p>
     */
    public void testCtor2WithValidNonNullArgs() {
        PersistenceException e = new PersistenceException(MESSAGE, cause);
        assertNotNull("The PersistenceException instance should not be null", e);
        assertTrue("The message is incorrect", e.getMessage().startsWith(MESSAGE));
        assertEquals("The cause is incorrect", cause, e.getCause());
    }

    /**
     * <p>
     * Tests the PersistenceException(String message, Throwable cause)
     * constructor with a null "message" argument. The newly created instance
     * should not be null. The getMessage() method should return a string that
     * ends with the exception message of the "cause" argument. The return value
     * of the getCause() method should be equal to the "cause" argument.
     * </p>
     */
    public void testCtor2ValidNullMessageArg() {
        PersistenceException e = new PersistenceException(null, cause);
        assertNotNull("The PersistenceException instance should not be null", e);
        assertTrue("The message is incorrect", e.getMessage().endsWith(cause.getMessage()));
        assertEquals("The cause is incorrect", cause, e.getCause());
    }

    /**
     * <p>
     * Tests the PersistenceException(String message, Throwable cause)
     * constructor with a null "cause" argument. The newly created instance
     * should not be null. The getMessage() method should return a value equal
     * to the "message" argument. The getCause() method should return null.
     * </p>
     */
    public void testCtor2WithValidNullCauseArg() {
        PersistenceException e = new PersistenceException(MESSAGE, null);
        assertNotNull("The PersistenceException instance should not be null", e);
        assertEquals("The message is incorrect", MESSAGE, e.getMessage());
        assertNull("The cause should be null", e.getCause());
    }

    /**
     * <p>
     * Tests that PersistenceException is a subclass of
     * UserPersistenceException.
     * </p>
     */
    public void testInheritance() {
        assertTrue("PersistenceException should be a subclass of UserPersistenceException", new PersistenceException(
                MESSAGE) instanceof UserPersistenceException);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionTest.class);
    }

}
