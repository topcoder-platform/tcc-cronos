/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import junit.framework.TestCase;

/**
 * Unit test cases for <code>DuplicateEntryException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class DuplicateEntryExceptionTests extends TestCase {
    /**
     * Tests that <code>DuplicateEntryException</code> inherits from <code>PersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("DuplicateEntryExceptionTests should inherit from PersistenceException",
                   PersistenceException.class.isAssignableFrom(DuplicateEntryException.class));
    }

    /**
     * Tests that the two-argument constructor sets the message correctly.
     */
    public void test_ctor2_message() {
        String message = "hello";
        assertTrue("getMessage() should contain the message passed to the constructor",
                   new DuplicateEntryException(message, new RuntimeException()).
                   getMessage().indexOf(message) != -1);
    }

    /**
     * Tests that the two-argument constructor sets the identifier correctly.
     */
    public void test_ctor2_identifier() {
        Object identifier = "identifier";
        assertEquals("getIdentifier() should return the identifier passed to the constructor", identifier,
                     new DuplicateEntryException("hi", identifier).getIdentifier());
    }
}
