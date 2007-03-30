/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import com.cronos.im.login.AdminLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;

/**
 * Tests the {@link AdminLoginAction} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class AdminLoginActionFailureTest extends BasicActionTestCaseAdapter {

    /**
     * Represents the mock action factory for testing AdminLoginAction class.
     */
    private ActionMockObjectFactory actionMockObjectFactory;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfigFile("config.xml");
        FailureTestHelper.deployMockEJB();
        actionMockObjectFactory = getActionMockObjectFactory();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Tests the scenario of RemoteException.
     * </p>
     */
    public void testExecuteFailure1() {

        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "RemoteExceptionUser");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "SomePassword");
            // executes the action class
            actionPerform(AdminLoginAction.class);
            fail("Should throw RemoteException");

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Administrator with different password is given as input. Action should redirect to failure page.
     * </p>
     */
    public void testExecuteFailure2() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Administrator");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "some_password");
        // executes the action class
        actionPerform(AdminLoginAction.class);

        // unable to authenticate the administrator.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Valid user and password but unauthorized login.
     * </p>
     */
    public void testExecuteFailure3() {

        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "UnAuthorized_Administrator");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "unauth_admin");

        actionPerform(AdminLoginAction.class);

        // unable to authorize the administrator.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);

    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Set user as null.
     * </p>
     */
    public void testExecuteFailure4() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "unauth_admin");
            actionPerform(AdminLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Set password as null.
     * </p>
     */
    public void testExecuteFailure5() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "UnAuthorized_Administrator");
            actionPerform(AdminLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Set user as empty.
     * </p>
     */
    public void testExecuteFailure6() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "unauth_admin");
            actionPerform(AdminLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>AdminLoginAction</code>.
     * </p>
     * <p>
     * Set password as empty.
     * </p>
     */
    public void testExecuteFailure7() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "UnAuthorized_Administrator");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "");
            actionPerform(AdminLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }
}
