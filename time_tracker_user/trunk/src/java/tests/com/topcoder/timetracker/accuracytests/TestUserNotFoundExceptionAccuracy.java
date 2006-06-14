/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.user.UserNotFoundException;
import com.cronos.timetracker.user.User;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>UserNotFoundException</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestUserNotFoundExceptionAccuracy extends TestCase {

    /**
     * Represents the UserNotFoundException instance for test.
     */
    private UserNotFoundException exception = null;

    /**
     * Represents the cause.
     */
    private Throwable cause = null;

    /**
     * Represents the user.
     */
    private User user = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        cause = new NullPointerException();

        user = new User();

        exception = new UserNotFoundException("error", cause, user);
    }

    /**
     * test constructor.
     *
     */
    public void testUserNotFoundException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * Test method getCause.
     *
     */
    public void testGetCause() {
        assertEquals("Equal is expected.", cause, exception.getCause());
    }

    /**
     * test method getProblemUsers.
     *
     */
    public void testGetProblemUser() {
        assertEquals("Equal is expected.", user, exception.getProblemUser());
    }

}
