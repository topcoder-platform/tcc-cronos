/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import com.cronos.im.login.ClientLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;

/**
 * Tests the {@link ClientLoginAction} for failure. This class tests the execute method.
 * 
 * @author mittu
 * @version 1.0
 */
public class ClientLoginActionExecuteFailureTest extends BasicActionTestCaseAdapter {

    /**
     * Represents the mock action factory for testing ClientLoginAction class.
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
     * Tests the execute method of <code>ClientLoginAction</code>.
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
            actionPerform(ClientLoginAction.class);
            fail("Should throw RemoteException");

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category which is unchattable.
     * </p>
     */
    public void testExecuteFailure2() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "failure_client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "2");
        // executes the action class
        actionPerform(ClientLoginAction.class);

        // unable to chat the particular category.
        verifyForward("unchattable");
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, but no type is given as input.
     * </p>
     */
    public void testExecuteFailure3() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "failure_client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "1");
        // executes the action class
        actionPerform(ClientLoginAction.class);

        // unable to chat the particular category.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, and type as reg. But the password is not correct.
     * </p>
     */
    public void testExecuteFailure4() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "SomePassword");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "1");
        actionMockObjectFactory.getMockRequest()
                .setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER, "reg");

        // executes the action class
        actionPerform(ClientLoginAction.class);

        // unable to authenticate the client.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, and type as reg. But the password is null.
     * </p>
     */
    public void testExecuteFailure5() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "1");
        actionMockObjectFactory.getMockRequest()
                .setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER, "reg");

        // executes the action class
        actionPerform(ClientLoginAction.class);

        // unable to authenticate the client.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, and type as reg. But the category is null.
     * </p>
     */
    public void testExecuteFailure6() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "1");
        actionMockObjectFactory.getMockRequest()
                .setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER, "reg");

        // executes the action class
        actionPerform(ClientLoginAction.class);

        // unable to authenticate the client.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as null, and type as reg.
     * </p>
     */
    public void testExecuteFailure7() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "failure_client");
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "Client");
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER,
                    "reg");

            // executes the action class
            actionPerform(ClientLoginAction.class);
        } catch (Exception exception) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, and type as empty.
     * </p>
     */
    public void testExecuteFailure8() {
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                "Client");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.PASSWORD_REQUEST_PARAMETER,
                "SomePassword");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.CATEGORY_REQUEST_PARAMETER,
                "1");
        actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER, " ");

        // executes the action class
        actionPerform(ClientLoginAction.class);

        // unable to authenticate the client.
        verifyForward(FailureTestHelper.FAILURE_ACTION_FORWARD);
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, and type as unreg. But the family name is not set in request.
     * </p>
     */
    public void testExecuteFailure9() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "Client");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "SomePassword");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "1");
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER,
                    "unreg");
            actionMockObjectFactory.getMockRequest().setupAddParameter("lname", "reviewer");

            // executes the action class
            actionPerform(ClientLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the execute method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Tests with category as chattable, and type as unreg. But the last name is not set in request.
     * </p>
     */
    public void testExecuteFailure10() {
        try {
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.USER_REQUEST_PARAMETER,
                    "Client");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.PASSWORD_REQUEST_PARAMETER, "SomePassword");
            actionMockObjectFactory.getMockRequest().setupAddParameter(
                    FailureTestHelper.CATEGORY_REQUEST_PARAMETER, "1");
            actionMockObjectFactory.getMockRequest().setupAddParameter(FailureTestHelper.TYPE_REQUEST_PARAMETER,
                    "unreg");
            actionMockObjectFactory.getMockRequest().setupAddParameter("fname", "failure");

            // executes the action class
            actionPerform(ClientLoginAction.class);

        } catch (Exception e) {
            // expect
        }
    }

}
