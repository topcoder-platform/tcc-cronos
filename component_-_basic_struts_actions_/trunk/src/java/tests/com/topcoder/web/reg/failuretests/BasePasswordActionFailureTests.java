/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BasePasswordAction;
import com.topcoder.web.reg.actions.basic.ResetPasswordAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BasePasswordAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BasePasswordActionFailureTests extends TestCase {
    /**
     * <p>
     * The BasePasswordAction instance for testing.
     * </p>
     */
    private BasePasswordAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ResetPasswordAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BasePasswordActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BasePasswordAction#checkConfiguration() for failure.
     * It tests the case that when passwordRecoveryDAO is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testSetPasswordRecoveryDAO_NullPasswordRecoveryDAO() {
        instance.setPasswordRecoveryDAO(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

}