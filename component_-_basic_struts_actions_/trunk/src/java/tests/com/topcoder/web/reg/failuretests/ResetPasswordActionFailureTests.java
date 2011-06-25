/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import org.easymock.EasyMock;

import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BasicActionException;
import com.topcoder.web.reg.actions.basic.ResetPasswordAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ResetPasswordAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ResetPasswordActionFailureTests extends TestCase {
    /**
     * <p>
     * The ResetPasswordAction instance for testing.
     * </p>
     */
    private ResetPasswordAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ResetPasswordAction();
        PrincipalMgrRemote principalMgrRemote = EasyMock.createNiceMock(PrincipalMgrRemote.class);
        instance.setPrincipalMgrRemote(principalMgrRemote);
        instance.setMaximalPasswordLength(8);
        instance.setMinimalPasswordLength(2);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ResetPasswordActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ResetPasswordAction#checkConfiguration() for failure.
     * It tests the case that when principalMgrRemote is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullPrincipalMgrRemote() {
        instance.setPrincipalMgrRemote(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ResetPasswordAction#checkConfiguration() for failure.
     * It tests the case that when minimalPasswordLength is negative and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativeMinimalPasswordLength() {
        instance.setMinimalPasswordLength(-5);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ResetPasswordAction#checkConfiguration() for failure.
     * It tests the case that when maximalPasswordLength is negative and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativeMaximalPasswordLength() {
        instance.setMaximalPasswordLength(-5);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ResetPasswordAction#execute() for failure.
     * Expects BasicActionException.
     * </p>
     */
    public void testExecute_BasicActionException() {
        PasswordRecoveryDAO passwordRecoveryDAO = EasyMock.createNiceMock(PasswordRecoveryDAO.class);
        instance.setPasswordRecoveryDAO(passwordRecoveryDAO);
        try {
            instance.execute();
            fail("BasicActionException expected.");
        } catch (BasicActionException e) {
            //good
        }
    }

}