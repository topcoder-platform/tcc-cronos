/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import junit.framework.TestCase;

import com.cronos.im.persistence.rolecategories.User;

/**
 * <p>
 * Accuracy test for <code>{@link User}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the User instance used in tests.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        user = new User(1, "TopCoder");
    }

    /**
     * <p>
     * Accuracy test for <code>{@link User#User(long, String, String, boolean)}</code> constructor.
     * </p>
     */
    public void testUserAccuracy() {
        user = new User(2, "TC");

        assertNotNull("instance not created.", user);

        assertEquals("id field not set correctly.", 2, user.getId());
        assertEquals("name field not set correctly.", "TC", user.getName());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link User#getId()}</code> method.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertEquals("id field not set correctly.", 1, user.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link User#getName()}</code> method.
     * </p>
     */
    public void testGetNameAccuracy() {
        assertEquals("name field not set correctly.", "TopCoder", user.getName());
    }
}
