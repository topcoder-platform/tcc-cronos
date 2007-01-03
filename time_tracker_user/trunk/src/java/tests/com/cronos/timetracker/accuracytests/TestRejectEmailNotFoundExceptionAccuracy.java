/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.RejectEmail;
import com.cronos.timetracker.common.RejectEmailNotFoundException;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>RejectEmailNotFoundException </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestRejectEmailNotFoundExceptionAccuracy extends TestCase {

    /**
     * Represents the RejectEmailNotFoundException instance for test.
     */
    private RejectEmailNotFoundException exception = null;

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

        exception = new RejectEmailNotFoundException("error", new NullPointerException(), email);
    }

    /**
     * Test constructor.
     *
     */
    public void testRejectEmailNotFoundException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * test method getProblemRejectEmail
     *
     */
    public void testGetProblemRejectEmail() {
        assertEquals("Equal is expected.", email, exception.getProblemRejectEmail());
    }

}
