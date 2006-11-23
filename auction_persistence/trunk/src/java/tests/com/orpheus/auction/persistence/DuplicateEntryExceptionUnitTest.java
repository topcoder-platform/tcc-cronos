/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>DuplicateEntryException</code>.
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
public class DuplicateEntryExceptionUnitTest extends TestCase {
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
    public void testDuplicateEntryException() {
        DuplicateEntryException ce = new DuplicateEntryException(ERROR_MESSAGE, IDENTIFIER);

        assertNotNull("Unable to instantiate DuplicateEntryException.", ce);
        assertEquals("Message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
        assertEquals("Identifier is not properly set.", IDENTIFIER, ce.getIdentifier());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DuplicateEntryException subclasses PersistenceException.
     * </p>
     */
    public void testDuplicateEntryExceptionInheritance() {
        assertTrue("DuplicateEntryException does not subclass PersistenceException.",
            new DuplicateEntryException(ERROR_MESSAGE, IDENTIFIER) instanceof PersistenceException);
    }
}
