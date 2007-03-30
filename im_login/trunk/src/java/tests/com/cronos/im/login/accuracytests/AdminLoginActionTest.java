/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cronos.im.login.AdminLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;
import com.topcoder.security.authorization.Principal;

/**
 * <p>
 * This Junit tests AdminLoginAction class. It contains both the accuracy and failure test cases. This test suite
 * covers all the possible functionalities/behavior of this class for both valid and invalid inputs.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class AdminLoginActionTest extends BasicActionTestCaseAdapter {

    /** Represents the AminLoginAction for testing.*/
    private AdminLoginAction adminLoginAction = null;

    /** Represents the mock action factory for testing AdminLoginAction class. */
    private ActionMockObjectFactory mockObjectFactory;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        AccuracyTestHelper.loadConfig();
        AccuracyTestHelper.initializeMockEjbContainer();
        mockObjectFactory = getActionMockObjectFactory();
        adminLoginAction = new AdminLoginAction();
    }

    /**
     * <p>
     * Tears down the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        AccuracyTestHelper.releaseConfig();
        adminLoginAction = null;
        mockObjectFactory = null;
    }

    /**
     * <p>
     * Accuracy test for <code>AdminLoginAction()</code>.
     * </p>
     * <p>
     * Test non null instance and inheritance.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testAdminLoginActionAccuracy() throws Exception {
        assertNotNull("AdminLoginAction creation failed", new AdminLoginAction());
        assertEquals("AdminLoginAction doesn't inherit from LoginAction", "com.cronos.im.login.LoginAction",
                adminLoginAction.getClass().getSuperclass().getName());
    }

    /**
     * <p>
     * Accuracy test of <code>{@link AdminLoginAction#execute(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response)}</code>.
     * </p>
     * <p>
     * Calling execute method with valid user and password of an administrator.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     *
     */
    public void testExecuteAccuracy1() throws Exception {
        Method method = null;
        try {
            // sets the valid user and password in request
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.USER_PARAM, "administrator");
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.PWD_PARAM, "adminpwd");

            actionPerform(AdminLoginAction.class);

            // checks for any action messages
            verifyNoActionMessages();

            // check for any errors
            verifyNoActionErrors();

            // checks for successful forward
            verifyForward("loginSucceed");

            // retrieve the user profile via reflection
            method = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getUserProfileKey",
                    new Class[] {});

            method.setAccessible(true);

            // retrieves the profile key
            String profileKey = (String) method.invoke(adminLoginAction, new Object[] {});

            // checks for the profile in the session
            Object object = getSessionAttribute(profileKey);

            assertTrue("Corresponding principal of administrator is not set is session attribute",
                    object instanceof Principal);
            assertEquals("failed to set the name of the principal", "administrator", ((Principal) object)
                    .getName());
            assertEquals("failed to set the id of the principal", 1, ((Principal) object).getId());

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }
}
