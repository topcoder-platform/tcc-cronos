/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import com.topcoder.timetracker.user.BatchUserDAOException;
import com.topcoder.timetracker.user.User;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>BatchUserDAOException</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestBatchUserDAOExceptionAccuracy extends TestCase {

    /**
     * Represents the BatchUserDAOException instance for test.
     */
    private BatchUserDAOException exception = null;

    /**
     * Represents the causes.
     */
    private Throwable[] causes = null;

    /**
     * Represents the users.
     */
    private User[] users = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        causes = new Throwable[1];
        causes[0] = new NullPointerException();

        users = new User[1];
        users[0] = new User();

        exception = new BatchUserDAOException("error", causes, users);
    }

    /**
     * test constructor.
     *
     */
    public void testBatchUserDAOException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * Test method getCauses.
     *
     */
    public void testGetCauses() {
        assertEquals("Equal is expected.", causes, exception.getCauses());
    }

    /**
     * test method getProblemUsers.
     *
     */
    public void testGetProblemUsers() {
        assertEquals("Equal is expected.", users, exception.getProblemUsers());
    }

}
