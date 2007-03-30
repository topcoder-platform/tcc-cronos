/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.lang.reflect.Method;

import org.apache.struts.action.Action;

import com.cronos.im.login.AdminLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;

/**
 * <p>
 * This Junit tests LoginAction class. It contains both the accuracy and failure test cases. This test suite covers
 * all the possible functionalities/behavior of this class for accurate configured inputs and also tests the
 * execute method.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class LoginActionConfigTest extends BasicActionTestCaseAdapter {

    /** LoginAction instance for testing. */
    private AdminLoginAction loginAction = null;

    /** Represents the mock action factory for testing ClientLoginAction class. */
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
        AccuracyTestHelper.reloadConfig("config.xml");
        AccuracyTestHelper.initializeMockEjbContainer();
        loginAction = new AdminLoginAction();
        mockObjectFactory = getActionMockObjectFactory();
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
        loginAction = null;
        mockObjectFactory = null;
    }

    /**
     * <p>
     * Accuracy test for <code>LoginAction()</code>.
     * </p>
     * <p>
     * Test non null instance and inheritance.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testLoginActionAccuracy() throws Exception {
        assertNotNull("LoginAction creation failed", loginAction);
        assertTrue("LoginAction doesn't inherit from Action", loginAction instanceof Action);
    }

    /**
     * <p>
     * Accuracy test for <code>getUserProfileKey()</code>.
     * </p>
     * <p>
     * Expects the user profile key created for this class.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetUserProfileKeyAccuracy() throws Exception {
        Method getUserProfile = null;
        try {
            // retrieve the user profile via reflection
            getUserProfile = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getUserProfileKey",
                    new Class[] {});

            getUserProfile.setAccessible(true);

            // retrieves the profile key
            String profileKey = (String) getUserProfile.invoke(loginAction, new Object[] {});

            assertEquals("failed to retrieve the profile key", "userProfile", profileKey);

        } finally {
            if (getUserProfile != null) {
                getUserProfile.setAccessible(false);
            }
        }

    }

    /**
     * <p>
     * Accuracy test for <code>getLoginSucceedForwardName()</code>.
     * </p>
     * <p>
     * Expects the login succeed forward name.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetLoginSucceedForwardNameAccuracy() throws Exception {
        Method getLoginSucceedForwardName = null;
        try {
            // retrieve the user profile via reflection
            getLoginSucceedForwardName = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction",
                    "getLoginSucceedForwardName", new Class[] {});

            getLoginSucceedForwardName.setAccessible(true);

            // retrieves the login succeed
            String loginSucceed = (String) getLoginSucceedForwardName.invoke(loginAction, new Object[] {});

            assertEquals("failed to retrieve the profile key", "loginSucceed", loginSucceed);

        } finally {
            if (getLoginSucceedForwardName != null) {
                getLoginSucceedForwardName.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getLoginFailForwardName()</code>.
     * </p>
     * <p>
     * Expects the login failure forward name.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetLoginFailForwardNameAccuracy() throws Exception {
        Method getLoginFailForwardName = null;
        try {
            // retrieve the user profile via reflection
            getLoginFailForwardName = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction",
                    "getLoginFailForwardName", new Class[] {});

            getLoginFailForwardName.setAccessible(true);

            // retrieves the login failure
            String loginFail = (String) getLoginFailForwardName.invoke(loginAction, new Object[] {});

            assertEquals("failed to retrieve the profile key", "loginFail", loginFail);

        } finally {
            if (getLoginFailForwardName != null) {
                getLoginFailForwardName.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getCategoryKey()</code>.
     * </p>
     * <p>
     * Expects the category created.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetCategoryKeyAccuracy() throws Exception {
        Method getCategoryKey = null;
        try {
            // retrieve the user profile via reflection
            getCategoryKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getCategoryKey",
                    new Class[] {});

            getCategoryKey.setAccessible(true);

            // retrieves the category
            String category = (String) getCategoryKey.invoke(loginAction, new Object[] {});

            assertEquals("failed to retrieve the category", "category", category);

        } finally {
            if (getCategoryKey != null) {
                getCategoryKey.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getRoleKey()</code>.
     * </p>
     * <p>
     * Expects the role created.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetRoleKeyAccuracy() throws Exception {
        Method getRoleKey = null;
        try {
            // retrieve the user profile via reflection
            getRoleKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getRoleKey",
                    new Class[] {});

            getRoleKey.setAccessible(true);

            // retrieves the role
            String role = (String) getRoleKey.invoke(loginAction, new Object[] {});

            assertEquals("failed to retrieve the role key", "role", role);

        } finally {
            if (getRoleKey != null) {
                getRoleKey.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAuthProviderURL()</code>.
     * </p>
     * <p>
     * Expects the non null auth provider url.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetAuthProviderURLAccuracy() throws Exception {
        Method getAuthProviderURL = null;
        try {
            // retrieve the user profile via reflection
            getAuthProviderURL = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction",
                    "getAuthProviderURL", new Class[] {});

            getAuthProviderURL.setAccessible(true);

            // retrieves the non null authentication provider url
            assertEquals("failed to retrieve the auth url", "http://locahost", (String) getAuthProviderURL.invoke(
                    loginAction, new Object[] {}));

        } finally {
            if (getAuthProviderURL != null) {
                getAuthProviderURL.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAuthInitialContextFactory()</code>.
     * </p>
     * <p>
     * Expects the non null AuthInitialContextFactory.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetAuthInitialContextFactoryAccuracy() throws Exception {
        Method getAuthInitialContextFactory = null;
        try {
            // retrieve the user profile via reflection
            getAuthInitialContextFactory = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction",
                    "getAuthInitialContextFactory", new Class[] {});

            getAuthInitialContextFactory.setAccessible(true);

            // retrieves the null initial context factory
            assertEquals("failed to retrieve the auth url", "org.mockejb.jndi.MockContextFactory",
                    (String) getAuthInitialContextFactory.invoke(loginAction, new Object[] {}));

        } finally {
            if (getAuthInitialContextFactory != null) {
                getAuthInitialContextFactory.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getLoginRemote()</code>.
     * </p>
     * <p>
     * Expects the non-null bean object.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetLoginRemoteAccuracy() throws Exception {
        Method getLoginRemote = null;
        try {
            // retrieve the user profile via reflection
            getLoginRemote = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getLoginRemote",
                    new Class[] {});

            getLoginRemote.setAccessible(true);

            // retrieves the null initial context factory
            assertNotNull("failed to retrieve the auth url", getLoginRemote.invoke(loginAction, new Object[] {}));

        } finally {
            if (getLoginRemote != null) {
                getLoginRemote.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getChatUserProfileManager()</code>.
     * </p>
     * <p>
     * Expects the non-null Chat user profile manager.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetChatUserProfileManagerAccuracy() throws Exception {
        Method getChatUserProfileManager = null;
        try {
            // retrieve the user profile via reflection
            getChatUserProfileManager = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction",
                    "getChatUserProfileManager", new Class[] {});

            getChatUserProfileManager.setAccessible(true);

            // retrieves the null initial context factory
            assertNotNull("failed to retrieve the auth url", getChatUserProfileManager.invoke(loginAction,
                    new Object[] {}));

        } finally {
            if (getChatUserProfileManager != null) {
                getChatUserProfileManager.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getActionContext()</code>.
     * </p>
     * <p>
     * Expects the non-null ActionContext.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetActionContextAccuracy() throws Exception {
        Method getActionContext = null;
        try {
            // retrieve the user profile via reflection
            getActionContext = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getActionContext",
                    new Class[] {});

            getActionContext.setAccessible(true);

            // retrieves the null initial context factory
            assertNotNull("failed to retrieve the auth url", getActionContext.invoke(loginAction, new Object[] {}));

        } finally {
            if (getActionContext != null) {
                getActionContext.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAuthorizationManager()</code>.
     * </p>
     * <p>
     * Expects the non-null AuthorizationManager.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetAuthorizationManagerAccuracy() throws Exception {
        Method getAuthorizationManager = null;
        try {
            // retrieve the user profile via reflection
            getAuthorizationManager = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction",
                    "getAuthorizationManager", new Class[] {});

            getAuthorizationManager.setAccessible(true);

            // retrieves the null initial context factory
            assertNotNull("failed to retrieve the auth url", getAuthorizationManager.invoke(loginAction,
                    new Object[] {}));

        } finally {
            if (getAuthorizationManager != null) {
                getAuthorizationManager.setAccessible(false);
            }
        }
    }

}
