/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.RejectEmail;
import com.cronos.timetracker.common.RejectEmailDAOException;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>RejectEmailDAOException </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestRejectEmailDAOExceptionAccuracy extends TestCase {

    /**
     * Represents the RejectEmailDAOException instance for test.
     */
    private RejectEmailDAOException exception = null;

    /**
     * Represents RejectEmail instance for test.
     */
    private RejectEmail email = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        email = new RejectEmail();
        email.setBody("email rejected.");

        exception = new RejectEmailDAOException("error", new NullPointerException(), email);
    }

    /**
     * Test constructor.
     *
     */
    public void testRejectEmailDAOException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * test method getProblemRejectEmail.
     *
     */
    public void testGetProblemRejectEmail() {
        assertEquals("Equal is expected.", email, exception.getProblemRejectEmail());
    }

}
