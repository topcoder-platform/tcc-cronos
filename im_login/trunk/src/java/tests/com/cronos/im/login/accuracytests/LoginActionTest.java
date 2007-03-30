/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.apache.struts.action.Action;

import com.cronos.im.login.AdminLoginAction;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This Junit tests LoginAction class. It contains both the accuracy and failure test cases. This test suite covers
 * all the possible functionalities/behavior of this class for both valid and invalid inputs.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class LoginActionTest extends TestCase {

    /**
     * <p>
     * LoginAction instance for testing. As <code>LoginAction</code> is of default access and abstract class
     * </p>
     */
    private AdminLoginAction loginAction = null;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        loginAction = new AdminLoginAction();
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
        loginAction = null;
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
     * Accuracy test for <code>getLog()</code>.
     * </p>
     * <p>
     * Expects log created for this class.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetLogAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "getLog", new Class[] {});
            method.setAccessible(true);

            // retrieves the unchattable forward name
            Log log = (Log) method.invoke(loginAction, new Object[] {});

            assertNotNull("failed to retrieve the log created properly", log);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
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
     * Accuracy test for
     * <code>doLog(String user, String action, String entityID, String message, Level level)</code>.
     * </p>
     * <p>
     * Tests whether the logging is done properly even if no entity id is given as input.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testdoLogAccuracy() throws Exception {
        Method doLog = null;
        try {
            doLog = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "doLog", new Class[] {
                String.class, String.class, String.class, String.class, Level.class });

            doLog.setAccessible(true);

            // invokes the do log method
            doLog.invoke(loginAction, new Object[] {"user", "action", null, "message", Level.ERROR });

        } catch (Exception exception) {
            fail("Recieved exception");
        } finally {
            if (doLog != null) {
                doLog.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for
     * <code>doLog(String user, String action, String entityID, String message, Level level)</code>.
     * </p>
     * <p>
     * Tests whether the logging is done properly even if entity id is given as empty.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testdoLogAccuracy1() throws Exception {
        Method doLog = null;
        try {
            doLog = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "doLog", new Class[] {
                String.class, String.class, String.class, String.class, Level.class });

            doLog.setAccessible(true);

            // invokes the do log method
            doLog.invoke(loginAction, new Object[] { "user", "action", " ", "message", Level.ERROR });

        } catch (Exception exception) {
            fail("Recieved exception");
        } finally {
            if (doLog != null) {
                doLog.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for
     * <code>doLog(String user, String action, String entityID, String message, Level level)</code>.
     * </p>
     * <p>
     * Tests whether the logging is done properly even if user id is given as empty.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testdoLogAccuracy2() throws Exception {
        Method doLog = null;
        try {
            doLog = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "doLog", new Class[] {
                String.class, String.class, String.class, String.class, Level.class });

            doLog.setAccessible(true);

            // invokes the do log method
            doLog.invoke(loginAction, new Object[] {" ", "action", "entityid", "message", Level.ERROR });

        } catch (Exception exception) {
            fail("Recieved exception");
        } finally {
            if (doLog != null) {
                doLog.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for
     * <code>doLog(String user, String action, String entityID, String message, Level level)</code>.
     * </p>
     * <p>
     * Tests whether the logging is done properly even if user id is given as null.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testdoLogAccuracy3() throws Exception {
        Method doLog = null;
        try {
            // retrieve the user profile via reflection
            doLog = AccuracyTestHelper.getMethod("com.cronos.im.login.LoginAction", "doLog", new Class[] {
                String.class, String.class, String.class, String.class, Level.class });

            doLog.setAccessible(true);

            // invokes the do log method
            doLog.invoke(loginAction, new Object[] {null, "action", "entityid", "message", Level.ERROR });

        } catch (Exception exception) {
            fail("Recieved exception");
        } finally {
            if (doLog != null) {
                doLog.setAccessible(false);
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
     * Expects the null auth provider url.
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

            // retrieves the null authentication provider url
            assertNull("failed to retrieve the auth url", getAuthProviderURL.invoke(loginAction, new Object[] {}));

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
     * Expects the null AuthInitialContextFactory.
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
            assertNull("failed to retrieve the auth url", getAuthInitialContextFactory.invoke(loginAction,
                    new Object[] {}));

        } finally {
            if (getAuthInitialContextFactory != null) {
                getAuthInitialContextFactory.setAccessible(false);
            }
        }
    }
}
