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
import com.topcoder.security.authorization.Principal;

/**
 * <p>
 * This unit test addresses the functionality provided by the <code>{@link AdminLoginAction}</code> class.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 * @see AdminLoginAction
 */
public class AdminLoginActionTest extends BasicActionTestCaseAdapter {
    /**
     * <p>
     * The instance of <code>AdminLoginAction</code> for test.
     * </p>
     */
    private AdminLoginAction adminLoginAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests of <code>AdminLoginActionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(AdminLoginActionTest.class);
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
        factory.getMockRequest().setupAddParameter("user", "admin");
        factory.getMockRequest().setupAddParameter("pwd", "admin");
        factory.getMockRequest().setupAddParameter("cat", "1");

        adminLoginAction = new AdminLoginAction();
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
        super.tearDown();
        adminLoginAction = null;
    }

    /**
     * <p>
     * Detail test of <code>{@link AdminLoginAction#AdminLoginAction()}</code> constructor.
     * </p>
     *
     * <p>
     * Expects non null instance. Also check inheritance.
     * </p>
     *
     */
    public void testAdminLoginActionAccuracy() {
        assertNotNull("failed to create AdminLoginAction", adminLoginAction);

        assertTrue("AdminLoginAction should inherit from LoginAction", adminLoginAction instanceof LoginAction);
    }

    /**
     * <p>
     * Detail test of <code>{@link AdminLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Expects the exact forward name and principal in session attribute.
     * </p>
     *
     */
    public void testExecuteAccuracy() {
        actionPerform(AdminLoginAction.class);

        // verify the results of the action processing
        verifyNoActionErrors();
        verifyNoActionMessages();
        verifyForward(TestHelper.LOGIN_SUCCESS_FWD);

        Object object = getSessionAttribute(adminLoginAction.getUserProfileKey());

        assertTrue("Principal expected in the session attribute", object instanceof Principal);

        Principal principal = (Principal) object;

        assertEquals("principal id mismatch", 1, principal.getId());
        assertEquals("principal name mismatch", "admin", principal.getName());
    }

    /**
     * <p>
     * Failure test of <code>{@link AdminLoginAction#execute(ActionMapping mapping, ActionForm form,
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
        actionPerform(AdminLoginAction.class);
        verifyForward(TestHelper.LOGIN_FAIL_FWD);
    }

    /**
     * <p>
     * Failure test of <code>{@link AdminLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>
     * method.
     * </p>
     *
     * <p>
     * Authorization failure.
     * </p>
     */
    public void testExecuteFailure2() {
        getActionMockObjectFactory().getMockRequest().setupAddParameter("user", "dummy_admin");
        actionPerform(AdminLoginAction.class);
        verifyForward(TestHelper.LOGIN_FAIL_FWD);
    }
}
