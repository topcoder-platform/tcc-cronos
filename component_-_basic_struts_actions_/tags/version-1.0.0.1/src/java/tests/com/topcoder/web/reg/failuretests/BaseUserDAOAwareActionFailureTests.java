/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BaseUserDAOAwareAction;
import com.topcoder.web.reg.actions.basic.LoginAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseUserDAOAwareAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BaseUserDAOAwareActionFailureTests extends TestCase {
    /**
     * <p>
     * The BaseUserDAOAwareAction instance for testing.
     * </p>
     */
    private BaseUserDAOAwareAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new LoginAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseUserDAOAwareActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseUserDAOAwareAction#checkConfiguration() for failure.
     * It tests the case that when userDAO is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullUserDAO() {
        instance.setUserDAO(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

}