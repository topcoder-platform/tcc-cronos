/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * <p>
 * This unit test addresses the functionality provided by the <code>{@link ManagerLoginAction}</code> class.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 * @see ManagerLoginAction
 */
public class ManagerLoginActionTest extends BasicActionTestCaseAdapter {
    /**
     * <p>
     * The instance of <code>ManagerLoginAction</code> for test.
     * </p>
     */
    private ManagerLoginAction managerLoginAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests of <code>ManagerLoginActionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ManagerLoginActionTest.class);
    }

    /**
     * <p>
     * Sets up the environment for each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfigs(true, "config.xml");
        TestHelper.deployEJB();
        ActionMockObjectFactory factory = getActionMockObjectFactory();
        // add the necessary parameters for testing.
        factory.getMockRequest().setupAddParameter("user", "manager");
        factory.getMockRequest().setupAddParameter("pwd", "manager");
        factory.getMockRequest().setupAddParameter("cat", "3");

        managerLoginAction = new ManagerLoginAction();
    }

    /**
     * <p>
     * Tears down the environment after execution of each TestCase.
     * </p>
     *
     * @throws Exception
     *             throws exception if any to JUnit.
     */
    protected void tearDown() throws Exception {
        managerLoginAction = null;
    }

    /**
     * <p>
     * Detail test of <code>{@link ManagerLoginAction#ManagerLoginAction()}</code> constructor.
     * </p>
     *
     * <p>
     * Expects non null instance. Also check inheritance.
     * </p>
     *
     */
    public void testManagerLoginActionAccuracy() {
        assertNotNull("failed to create ManagerLoginAction", managerLoginAction);

        assertTrue("ManagerLoginAction should inherit from LoginAction", managerLoginAction instanceof LoginAction);
    }

    /**
     * <p>
     * Detail test of <code>{@link ManagerLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the exact forward name and profile in session attribute.
     * </p>
     *
     */
    public void testExecuteAccuracy() {
        actionPerform(ManagerLoginAction.class);
        // verify the results of the action processing
        verifyNoActionErrors();
        verifyNoActionMessages();
        verifyForward(TestHelper.LOGIN_SUCCESS_FWD);

        Object object = getSessionAttribute(managerLoginAction.getUserProfileKey());

        assertTrue("Chat profile expected in the session attribute", object instanceof ChatUserProfile);

        ChatUserProfile profile = (ChatUserProfile) object;

        assertEquals("Chat profile user name mismatch", profile.getUsername(), "manager");
        assertEquals("Chat profile role property mismatch", profile.getPropertyValue(managerLoginAction
                .getRoleKey())[0], "manager");
    }

    /**
     * <p>
     * Failure test of <code>{@link ManagerLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Authentication failure.
     * </p>
     */
    public void testExecuteFailure1() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("pwd", "wrong");
        actionPerform(ManagerLoginAction.class);
        verifyForward(TestHelper.LOGIN_FAIL_FWD);
    }

    /**
     * <p>
     * Failure test of <code>{@link ManagerLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Authorization failure.
     * </p>
     */
    public void testExecuteFailure2() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("user", "dummy_manager");
        actionPerform(ManagerLoginAction.class);
        verifyForward(TestHelper.LOGIN_FAIL_FWD);
    }
}