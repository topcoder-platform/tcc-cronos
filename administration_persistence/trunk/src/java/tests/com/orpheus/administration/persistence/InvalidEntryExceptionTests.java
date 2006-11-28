/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import junit.framework.TestCase;

/**
 * Unit test cases for <code>InvalidEntryException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class InvalidEntryExceptionTests extends TestCase {
    /**
     * Tests that <code>InvalidEntryException</code> inherits from <code>PersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("InvalidEntryExceptionTests should inherit from PersistenceException",
                   PersistenceException.class.isAssignableFrom(InvalidEntryException.class));
    }

    /**
     * Tests that the two-argument constructor sets the message correctly.
     */
    public void test_ctor2_message() {
        String message = "hello";
        assertTrue("getMessage() should contain the message passed to the constructor",
                   new InvalidEntryException(message, new RuntimeException()).
                   getMessage().indexOf(message) != -1);
    }

    /**
     * Tests that the two-argument constructor sets the identifier correctly.
     */
    public void test_ctor2_identifier() {
        Object identifier = "identifier";
        assertEquals("getIdentifier() should return the identifier passed to the constructor", identifier,
                     new InvalidEntryException("hi", identifier).getIdentifier());
    }
}
