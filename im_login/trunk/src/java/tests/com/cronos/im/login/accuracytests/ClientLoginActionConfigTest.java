/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.lang.reflect.Method;

import com.cronos.im.login.ClientLoginAction;
import com.mockrunner.mock.web.ActionMockObjectFactory;
import com.mockrunner.struts.BasicActionTestCaseAdapter;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * <p>
 * This Junit tests ClientLoginAction class. It contains the accuracy test cases. This test suite covers all the
 * possible functionalities/behavior of this class for accurate inputs given via config file.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class ClientLoginActionConfigTest extends BasicActionTestCaseAdapter {

    /** ClientLoginAction instance for testing.*/
    private ClientLoginAction clientLoginAction = null;

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
        AccuracyTestHelper.reloadConfig("ClientLogin_accuracy_config.xml");
        AccuracyTestHelper.initializeMockEjbContainer();
        clientLoginAction = new ClientLoginAction();
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
        clientLoginAction = null;
        mockObjectFactory = null;
    }

    /**
     * <p>
     * Accuracy test of <code>execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}</code>
     * method.
     * </p>
     * <p>
     * Tests for the unregistered client login.
     * </p>
     *
     * @throws Exception
     *             throws Exception to junit.
     *
     */
    public void testExecuteAccuracyUnRegisteredClient() throws Exception {
        Method getUserProfile = null;
        Method getRoleKey = null;
        Method getCategoryKey = null;
        try {
            // sets the chattable category
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.CAT_PARAM, "1");
            mockObjectFactory.getMockRequest().setupAddParameter("type", "unreg");
            mockObjectFactory.getMockRequest().setupAddParameter("fname", "unknown");
            mockObjectFactory.getMockRequest().setupAddParameter("lname", "user");
            mockObjectFactory.getMockRequest().setupAddParameter("email", "accuracy@topcoder.com");
            actionPerform(ClientLoginAction.class);

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
            String profileKey = (String) getUserProfile.invoke(clientLoginAction, new Object[] {});

            // checks for the profile in the session
            Object chatProfile = getSessionAttribute(profileKey);

            // retrieves the role key
            getRoleKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getRoleKey",
                    new Class[] {});

            getRoleKey.setAccessible(true);

            // retrieves the profile key
            String roleKey = (String) getRoleKey.invoke(clientLoginAction, new Object[] {});

            // retrieves the category key
            getCategoryKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getCategoryKey",
                    new Class[] {});

            getCategoryKey.setAccessible(true);

            // retrieves the profile key
            String catKey = (String) getCategoryKey.invoke(clientLoginAction, new Object[] {});

            assertTrue("Corresponding profile of manager is not set is session attribute",
                    chatProfile instanceof ChatUserProfile);

            ChatUserProfile chatUserProfile = (ChatUserProfile) chatProfile;

            assertEquals("failed to set the role properly", "client", chatUserProfile.getPropertyValue(roleKey)[0]);

            assertEquals("failed to set category properly", "1", chatUserProfile.getPropertyValue(catKey)[0]);

            assertEquals("failed to set the user profile properly", "unknown user", chatUserProfile.getUsername());

            assertEquals("failed to set the user profile properly", "Unregistered", chatUserProfile.getType());
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

    /**
     * <p>
     * Accuracy test of <code>execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}</code>
     * method.
     * </p>
     * <p>
     * Tests for the registered client login.
     * </p>
     *
     * @throws Exception
     *             throws Exception to junit.
     *
     */
    public void testExecuteAccuracyRegisteredClient() throws Exception {
        Method getUserProfile = null;
        Method getRoleKey = null;
        Method getCategoryKey = null;
        try {
            // sets the chattable category
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.CAT_PARAM, "1");
            mockObjectFactory.getMockRequest().setupAddParameter("type", "reg");
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.USER_PARAM, "user");
            mockObjectFactory.getMockRequest().setupAddParameter(AccuracyTestHelper.PWD_PARAM, "userpwd");
            actionPerform(ClientLoginAction.class);

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
            String profileKey = (String) getUserProfile.invoke(clientLoginAction, new Object[] {});

            // checks for the profile in the session
            Object chatProfile = getSessionAttribute(profileKey);

            // retrieves the role key
            getRoleKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getRoleKey",
                    new Class[] {});

            getRoleKey.setAccessible(true);

            // retrieves the profile key
            String roleKey = (String) getRoleKey.invoke(clientLoginAction, new Object[] {});

            // retrieves the category key
            getCategoryKey = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getCategoryKey",
                    new Class[] {});

            getCategoryKey.setAccessible(true);

            // retrieves the profile key
            String catKey = (String) getCategoryKey.invoke(clientLoginAction, new Object[] {});

            assertTrue("Corresponding profile of manager is not set is session attribute",
                    chatProfile instanceof ChatUserProfile);

            ChatUserProfile chatUserProfile = (ChatUserProfile) chatProfile;

            assertEquals("failed to set the role properly", "client", chatUserProfile.getPropertyValue(roleKey)[0]);

            assertEquals("failed to set category properly", "1", chatUserProfile.getPropertyValue(catKey)[0]);

            assertEquals("failed to set the user profile properly", "user", chatUserProfile.getUsername());

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

    /**
     * <p>
     * Accuracy test for <code>getUnchattableForwardName()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetUnchattableForwardNameAccuracy1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getUnchattableForwardName",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the unchattable forward name
            String unchatFwdName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the unchattable forward name properly", "config unchattable",
                    unchatFwdName);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getConnectionName()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetConnectionNameAccuracy1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getConnectionName",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the connection name
            String connName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default connection name properly", "config connection", connName);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getCompanyKey()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetCompanyKeyAccuracy1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getCompanyKey",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the company
            String company = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default email properly", "config company", company);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getLastNameKey()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetLastNameKeyAccuracy1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getLastNameKey",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the last name key
            String lastName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default last name properly", "config lastName", lastName);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getTitleKey()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetTitleKeyAccuracy1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper
                    .getMethod(ClientLoginAction.class.getName(), "getTitleKey", new Class[] {});
            method.setAccessible(true);

            // retrieves the title
            String title = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default title properly", "config title", title);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getEmailKey()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetEmailKeyAccuracy1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper
                    .getMethod(ClientLoginAction.class.getName(), "getEmailKey", new Class[] {});
            method.setAccessible(true);

            // retrieves the email
            String email = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default email properly", "config email", email);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkChattable(String category)</code>.
     * </p>
     * <p>
     * Checks whether the category is chattable or not.
     * </p>
     *
     * @throws Exception
     *             throws exception to Junit.
     */
    public void testCheckChattable() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "checkChattable",
                    new Class[] {String.class});
            method.setAccessible(true);

            // retrieves the boolean flag
            Boolean chattable = (Boolean) method.invoke(clientLoginAction, new Object[] {"1" });            

            assertTrue("failed to implement the check chattable properly", chattable.booleanValue());

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkChattable(String category)</code>.
     * </p>
     * <p>
     * Checks whether the category is chattable or not.
     * </p>
     *
     * @throws Exception
     *             throws exception to Junit.
     */
    public void testCheckChattable1() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "checkChattable",
                    new Class[] {String.class });
            method.setAccessible(true);

            // retrieves the boolean flag
            Boolean chattable = (Boolean) method.invoke(clientLoginAction, new Object[] {"9" });

            assertFalse("failed to implement the check chattable properly", chattable.booleanValue());

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }
}