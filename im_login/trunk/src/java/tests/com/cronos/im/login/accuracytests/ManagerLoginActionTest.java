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
import com.cronos.im.login.ManagerLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * <p>
 * This Junit tests ManagerLoginAction class. It contains both the accuracy test cases. This test suite covers all
 * the possible functionalities/behavior of this class for accurate inputs.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class ManagerLoginActionTest extends BasicActionTestCaseAdapter {

    /** Represents the ManagerLoginAction for test. */
    private ManagerLoginAction managerLoginAction = null;

    /** Represents the mock action factory for testing ManagerLoginAction class. */
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
        managerLoginAction = new ManagerLoginAction();

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
        managerLoginAction = null;
        mockObjectFactory = null;
    }

    /**
     * <p>
     * Accuracy test for <code>ManagerLoginAction()</code>.
     * </p>
     * <p>
     * Test non null instance and inheritance.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testManagerLoginActionAccuracy() throws Exception {
        assertNotNull("ManagerLoginAction creation failed", new ManagerLoginAction());
        assertEquals("ManagerLoginAction doesn't inherit from LoginAction", "com.cronos.im.login.LoginAction",
                managerLoginAction.getClass().getSuperclass().getName());
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
        Method getUserProfile = null;
        Method getRoleKey = null;
        Method getCategoryKey = null;
        try {
            // sets the valid user and password in request
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.USER_PARAM, "manager");
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.PWD_PARAM, "managerpwd");

            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.CAT_PARAM, "2");

            actionPerform(ManagerLoginAction.class);

            // checks for any action messages
            verifyNoActionMessages();

            // check for any errors
            verifyNoActionErrors();

            // checks for successful forward
            verifyForward("loginSucceed");

            // retrieve the user profile via reflection
            getUserProfile = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getUserProfileKey",
                    new Class[] {});

            getUserProfile.setAccessible(true);

            // retrieves the profile key
            String profileKey = (String) getUserProfile.invoke(managerLoginAction, new Object[] {});

            // checks for the profile in the session
            Object chatProfile = getSessionAttribute(profileKey);

            // retrieves the role key
            getRoleKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getRoleKey",
                    new Class[] {});

            getRoleKey.setAccessible(true);

            // retrieves the profile key
            String roleKey = (String) getRoleKey.invoke(managerLoginAction, new Object[] {});

            assertTrue("Corresponding profile of manager is not set is session attribute",
                    chatProfile instanceof ChatUserProfile);

            ChatUserProfile chatUserProfile = (ChatUserProfile) chatProfile;

            assertEquals("failed to set the role properly", "manager",
                    chatUserProfile.getPropertyValue(roleKey)[0]);


            assertEquals("failed to set the user profile properly", "manager", chatUserProfile.getUsername());

            assertEquals("failed to set the user profile properly", "Registered", chatUserProfile.getType());

        } finally {
            if (getUserProfile != null) {
                getUserProfile.setAccessible(false);
            }
            if (getRoleKey != null) {
                getRoleKey.setAccessible(false);
            }
            if (getCategoryKey != null) {
                getCategoryKey.setAccessible(false);
            }
        }
    }
}
