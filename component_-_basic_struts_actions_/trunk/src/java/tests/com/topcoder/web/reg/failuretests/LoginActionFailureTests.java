/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import org.easymock.EasyMock;

import com.topcoder.security.TCSubject;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BasicActionException;
import com.topcoder.web.reg.actions.basic.LoginAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for LoginAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class LoginActionFailureTests extends TestCase {
    /**
     * <p>
     * The LoginAction instance for testing.
     * </p>
     */
    private LoginAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new LoginAction();
        LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
        instance.setLoginRemote(loginRemote);
        instance.setLastRequestedURIParameterName("lastRequestedURIParameterName");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(LoginActionFailureTests.class);
    }

    /**
     * <p>
     * Tests LoginAction#checkConfiguration() for failure.
     * It tests the case that when loginRemote is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullLoginRemote() {
        instance.setLoginRemote(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests LoginAction#checkConfiguration() for failure.
     * It tests the case that when lastRequestedURIParameterName is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullLastRequestedURIParameterName() {
        instance.setLastRequestedURIParameterName(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests LoginAction#checkConfiguration() for failure.
     * It tests the case that when lastRequestedURIParameterName is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyLastRequestedURIParameterName() {
        instance.setLastRequestedURIParameterName(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests LoginAction#execute() for failure.
     * Expects BasicActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_BasicActionException() throws Exception {
        TCSubject tcSubject = new TCSubject(1);
        LoginRemote loginRemote = EasyMock.createNiceMock(LoginRemote.class);
        EasyMock.expect(
            loginRemote.login(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                EasyMock.anyObject(String.class))).andReturn(tcSubject);
        instance.setLoginRemote(loginRemote);

        UserDAO userDAO = EasyMock.createNiceMock(UserDAO.class);
        instance.setUserDAO(userDAO);
        EasyMock.replay(loginRemote);

        try {
            instance.execute();
            fail("BasicActionException expected.");
        } catch (BasicActionException e) {
            //good
        }
    }

}