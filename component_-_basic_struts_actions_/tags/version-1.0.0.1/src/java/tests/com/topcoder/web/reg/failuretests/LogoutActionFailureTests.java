/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import java.util.HashMap;

import com.topcoder.web.reg.actions.basic.BasicActionException;
import com.topcoder.web.reg.actions.basic.LogoutAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for LogoutAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class LogoutActionFailureTests extends TestCase {
    /**
     * <p>
     * The LogoutAction instance for testing.
     * </p>
     */
    private LogoutAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new LogoutAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(LogoutActionFailureTests.class);
    }

    /**
     * <p>
     * Tests LogoutAction#execute() for failure.
     * Expects BasicActionException.
     * </p>
     */
    public void testExecute_BasicActionException() {
        instance.setSession(new HashMap<String, Object>());
        try {
            instance.execute();
            fail("BasicActionException expected.");
        } catch (BasicActionException e) {
            //good
        }
    }
}