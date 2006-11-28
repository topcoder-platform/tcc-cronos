/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.AdministrationException;

import junit.framework.TestCase;

/**
 * Unit test cases for <code>InstantiationException</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class InstantiationExceptionTests extends TestCase {
    /**
     * Tests that <code>InstantiationException</code> inherits from <code>AdministrationException</code>.
     */
    public void test_inheritance() {
        assertTrue("InstantiationExceptionTests should inherit from AdministrationException",
                   AdministrationException.class.isAssignableFrom(InstantiationException.class));
    }

    /**
     * Tests that the one-argument constructor sets the message corretly.
     */
    public void test_ctor1_message() {
        String message = "hi";
        assertEquals("getMessage() should return the message passed to the constructor",
                     message, new InstantiationException(message).getMessage());
    }

    /**
     * Tests that the two-argument constructor sets the message correctly.
     */
    public void test_ctor2_message() {
        String message = "hello";
        assertTrue("getMessage() should contain the message passed to the constructor",
                   new InstantiationException(message, new RuntimeException()).
                   getMessage().indexOf(message) != -1);
    }

    /**
     * Tests that the two-argument constructor sets the cause correctly.
     */
    public void test_ctor2_cause() {
        Throwable cause = new RuntimeException();
        assertEquals("getCause() should return the cause passed to the constructor", cause,
                     new InstantiationException("hi", cause).getCause());
    }
}
