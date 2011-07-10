/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BasicActionException;
import com.topcoder.web.reg.actions.basic.RecoverPasswordAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for RecoverPasswordAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class RecoverPasswordActionFailureTests extends TestCase {
    /**
     * <p>
     * The RecoverPasswordAction instance for testing.
     * </p>
     */
    private RecoverPasswordAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new RecoverPasswordAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RecoverPasswordActionFailureTests.class);
    }

    /**
     * <p>
     * Tests RecoverPasswordAction#checkConfiguration() for failure.
     * It tests the case that when passwordRecoveryExpiration is negative and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativePasswordRecoveryExpiration() {
        instance.setPasswordRecoveryExpiration(-1);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests RecoverPasswordAction#execute() for failure.
     * Expects BasicActionException.
     * </p>
     */
    public void testExecute_BasicActionException() {
        try {
            instance.execute();
            fail("BasicActionException expected.");
        } catch (BasicActionException e) {
            //good
        }
    }

}