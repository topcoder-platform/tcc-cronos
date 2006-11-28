/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import junit.framework.TestCase;

/**
 * Unit test cases for <code>EntryNotFoundException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class EntryNotFoundExceptionTests extends TestCase {
    /**
     * Tests that <code>EntryNotFoundException</code> inherits from <code>PersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("EntryNotFoundExceptionTests should inherit from PersistenceException",
                   PersistenceException.class.isAssignableFrom(EntryNotFoundException.class));
    }

    /**
     * Tests that the two-argument constructor sets the message correctly.
     */
    public void test_ctor2_message() {
        String message = "hello";
        assertTrue("getMessage() should contain the message passed to the constructor",
                   new EntryNotFoundException(message, new RuntimeException()).
                   getMessage().indexOf(message) != -1);
    }

    /**
     * Tests that the two-argument constructor sets the identifier correctly.
     */
    public void test_ctor2_identifier() {
        Object identifier = "identifier";
        assertEquals("getIdentifier() should return the identifier passed to the constructor", identifier,
                     new EntryNotFoundException("hi", identifier).getIdentifier());
    }
}
