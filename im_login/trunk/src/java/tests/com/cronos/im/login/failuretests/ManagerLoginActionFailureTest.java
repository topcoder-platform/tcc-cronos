/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import com.cronos.im.login.ManagerLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;

/**
 * Tests the {@link ManagerLoginAction} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class ManagerLoginActionFailureTest extends BasicActionTestCaseAdapter {
    /**
     * Represents the mock action factory for testing <code>ManagerLoginAction</code> class.
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
     * Tests the execute method of <code>ManagerLoginAction</code>.
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
            actionPerform(ManagerLoginAction.class);
            fail("Should throw RemoteException");
        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Manager with different password is given as input. Action should redirect to failure page.
     * </p>
     */
    public void testExecuteFailure2() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Manager");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "SomePassword");
        // executes the action class
        actionPerform(ManagerLoginAction.class);

        // unable to authenticate the manager.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Valid user and password but unauthorized login.
     * </p>
     */
    public void testExecuteFailure3() {

        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "UnAuthorized_Manager");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "unauth_manager");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "2");
        actionPerform(ManagerLoginAction.class);

        // unable to authorize the administrator.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);

    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Set user as null.
     * </p>
     */
    public void testExecuteFailure4() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "unauth_manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "2");
            actionPerform(ManagerLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Set password as null.
     * </p>
     */
    public void testExecuteFailure5() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "UnAuthorized_Manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "2");
            actionPerform(ManagerLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Set category as null.
     * </p>
     */
    public void testExecuteFailure6() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "unauth_manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "UnAuthorized_Manager");
            actionPerform(ManagerLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Set user as empty.
     * </p>
     */
    public void testExecuteFailure7() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "unauth_manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "2");
            actionPerform(ManagerLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Set password as empty.
     * </p>
     */
    public void testExecuteFailure8() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "UnAuthorized_Manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "2");
            actionPerform(ManagerLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ManagerLoginAction</code>.
     * </p>
     * <p>
     * Set category as empty.
     * </p>
     */
    public void testExecuteFailure9() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "UnAuthorized_Manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "unauth_manager");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "");
            actionPerform(ManagerLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

}
