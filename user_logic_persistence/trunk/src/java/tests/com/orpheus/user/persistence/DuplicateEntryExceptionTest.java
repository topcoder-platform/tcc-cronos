/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the DuplicateEntryException class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class DuplicateEntryExceptionTest extends TestCase {

    /**
     * <p>
     * A sample exception message for testing the DuplicateEntryException class.
     * </p>
     */
    private static final String MESSAGE = "The entry already exists in the persistent store";

    /**
     * <p>
     * A sample duplicate identifier for testing the DuplicateEntryException
     * class.
     * </p>
     */
    private static final Object IDENTIFIER = new Long(1234567890);

    /**
     * <p>
     * Tests the DuplicateEntryException(String message, Object identifier)
     * constructor with a valid non-null arguments. The newly created instance
     * should not be null. The getMessage() and getIdentifier() method should
     * return values equal to the corresponding constructor arguments.
     * </p>
     */
    public void testCtorWithValidNonNullArgs() {
        DuplicateEntryException e = new DuplicateEntryException(MESSAGE, IDENTIFIER);
        assertNotNull("The DuplicateEntryException instance should not be null", e);
        assertEquals("The message is incorrect", MESSAGE, e.getMessage());
        assertEquals("The identifier is incorrect", IDENTIFIER, e.getIdentifier());
    }

    /**
     * <p>
     * Tests the DuplicateEntryException(String message, Object identifier)
     * constructor with a valid null "message" argument. The newly created
     * instance should not be null. The getMessage() method should return null,
     * while the return value of the getIdentifier() method should be equal to
     * the "identifier" argument.
     * </p>
     */
    public void testCtorWithValidNullMessageArg() {
        DuplicateEntryException e = new DuplicateEntryException(null, IDENTIFIER);
        assertNotNull("The DuplicateEntryException instance should not be null", e);
        assertNull("The message should be null", e.getMessage());
        assertEquals("The identifier is incorrect", IDENTIFIER, e.getIdentifier());
    }

    /**
     * <p>
     * Tests the DuplicateEntryException(String message, Throwable cause)
     * constructor with valid non-null arguments. The newly created instance
     * should not be null. The getMessage() method should return a string that
     * starts with the "message" argument. The getCause() method should return a
     * value equal to the "cause" argument.
     * </p>
     */
    public void testCtorWithValidNullIdentifierArg() {
        DuplicateEntryException e = new DuplicateEntryException(MESSAGE, null);
        assertNotNull("The ObjectInstantiationException instance should not be null", e);
        assertTrue("The message is incorrect", e.getMessage().startsWith(MESSAGE));
        assertNull("The identifier should be null", e.getIdentifier());
    }

    /**
     * <p>
     * Tests that DuplicateEntryException is a subclass of PersistenceException.
     * </p>
     */
    public void testInheritance() {
        assertTrue("DuplicateEntryException should be a subclass of PersistenceException", new DuplicateEntryException(
                MESSAGE, IDENTIFIER) instanceof PersistenceException);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(DuplicateEntryExceptionTest.class);
    }

}
