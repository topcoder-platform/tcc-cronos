/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>InvalidEntryException</code>.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and identifier properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidEntryExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The identifier used for testing.
     * </p>
     */
    private static final Object IDENTIFIER = "identifier";

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the identifier are properly propagated.
     * </p>
     */
    public void testInvalidEntryException() {
        InvalidEntryException ce = new InvalidEntryException(ERROR_MESSAGE, IDENTIFIER);

        assertNotNull("Unable to instantiate InvalidEntryException.", ce);
        assertEquals("Message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
        assertEquals("Identifier is not properly set.", IDENTIFIER, ce.getIdentifier());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidEntryException subclasses PersistenceException.
     * </p>
     */
    public void testInvalidEntryExceptionInheritance() {
        assertTrue("InvalidEntryException does not subclass PersistenceException.",
            new InvalidEntryException(ERROR_MESSAGE, IDENTIFIER) instanceof PersistenceException);
    }
}
