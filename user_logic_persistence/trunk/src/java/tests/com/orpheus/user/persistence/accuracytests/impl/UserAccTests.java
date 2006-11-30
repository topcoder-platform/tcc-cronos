/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.impl;

import junit.framework.TestCase;

import java.io.Serializable;


/**
 * Accuracy test cases for <code>User</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class UserAccTests extends TestCase {

    /**
     * The user instance to test.
     */
    private MockUser user = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     */
    protected void setUp() {
        user = new MockUser(1);
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     */
    public void testConstructor() {

        assertEquals("Not the expected ID.", 1, user.getId());
    }

    /**
     * <p>
     * Test if User implements the right interface.
     * </p>
     */
    public void testImplementation() {

        assertTrue("Should implements Serializable interface.", user instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test of the <code>setActive()</code> method
     * </p>
     */
    public void testSetActive() {
        user.setActive("true");
        assertEquals("Not the expected value.", "true", user.getActive());
    }

    /**
     * <p>
     * Accuracy test of the <code>setEmail()</code> method
     * </p>
     */
    public void testSetEmail() {
        user.setEmail("test@topcoder.com");
        assertEquals("Not the expected value.", "test@topcoder.com", user.getEmail());
    }

    /**
     * <p>
     * Accuracy test of the <code>setHandle()</code> method
     * </p>
     */
    public void testSetHandle() {
        user.setHandle("handle");
        assertEquals("Not the expected value.", "handle", user.getHandle());
    }

    /**
     * <p>
     * Accuracy test of the <code>setPassword()</code> method
     * </p>
     */
    public void testSetPassword() {
        user.setPassword("1234");
        assertEquals("Not the expected value.", "1234", user.getPassword());
    }
}
