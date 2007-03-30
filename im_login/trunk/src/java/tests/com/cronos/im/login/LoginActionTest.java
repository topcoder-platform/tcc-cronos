/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import javax.naming.NamingException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts.action.Action;

import com.topcoder.security.authorization.ConfigurationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This unit test addresses the functionality provided by the <code>{@link LoginAction}</code> class.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 * @see LoginAction
 */
public class LoginActionTest extends TestCase {
    /**
     * <p>
     * The instance of <code>LoginAction</code> for test.
     * </p>
     */
    private LoginAction loginAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests of <code>LoginActionTest</code>.
     */
    public static Test suite() {
        return new TestSuite(LoginActionTest.class);
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
        TestHelper.loadConfigs(true, "config.xml");
        TestHelper.deployEJB();
        loginAction = new LoginAction() {
        };
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
        TestHelper.releaseConfigs();
        loginAction = null;
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#LoginAction()}</code> constructor.
     * </p>
     *
     * <p>
     * Expects non null instance. Also check inheritance.
     * </p>
     *
     */
    public void testLoginActionAccuracy() {
        assertNotNull("failed to create LoginAction", loginAction);

        assertTrue("LoginAction should inherit from Action", loginAction instanceof Action);
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getLog()}</code> method.
     * </p>
     *
     * <p>
     * Expects non-null log.
     * </p>
     *
     */
    public void testGetLogAccuracy() {
        assertNotNull("failed to get log", loginAction.getLog());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getUserProfileKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetUserProfileKeyAccuracy() {
        assertEquals("failed to get user profile key", TestHelper.USER_PROFILE_KEY, loginAction
                .getUserProfileKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getLoginSucceedForwardName()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetLoginSucceedForwardNameAccuracy() {
        assertEquals("failed to get login success forward name", TestHelper.LOGIN_SUCCESS_FWD, loginAction
                .getLoginSucceedForwardName());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getLoginFailForwardName()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetLoginFailForwardNameAccuracy() {
        assertEquals("failed to get login fail forward name", TestHelper.LOGIN_FAIL_FWD, loginAction
                .getLoginFailForwardName());
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link LoginAction#doLog(String user, String action, String entityID, String message, Level level)}</code>
     * method.
     * </p>
     *
     * <p>
     * action is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testDoLogFailure3() {
        try {
            loginAction.doLog("user", null, "entityID", "message", Level.ERROR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link LoginAction#doLog(String user, String action, String entityID, String message, Level level)}</code>
     * method.
     * </p>
     *
     * <p>
     * action is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testDoLogFailure4() {
        try {
            loginAction.doLog("user", " ", "entityID", "message", Level.ERROR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link LoginAction#doLog(String user, String action, String entityID, String message, Level level)}</code>
     * method.
     * </p>
     *
     * <p>
     * message is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testDoLogFailure5() {
        try {
            loginAction.doLog("user", "action", "entityID", null, Level.ERROR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link LoginAction#doLog(String user, String action, String entityID, String message, Level level)}</code>
     * method.
     * </p>
     *
     * <p>
     * message is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testDoLogFailure6() {
        try {
            loginAction.doLog("user", "action", "entityID", " ", Level.ERROR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>{@link LoginAction#doLog(String user, String action, String entityID, String message, Level level)}</code>
     * method.
     * </p>
     *
     * <p>
     * level is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testDoLogFailure7() {
        try {
            loginAction.doLog("user", "action", "entityID", "message", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getCategoryKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetCategoryKeyAccuracy() {
        assertEquals("failed to get category key", TestHelper.CATEGORY_KEY, loginAction.getCategoryKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getRoleKey()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetRoleKeyAccuracy() {
        assertEquals("failed to get role key", TestHelper.ROLE_KEY, loginAction.getRoleKey());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getAuthProviderURL()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetAuthProviderURLAccuracy() {
        assertEquals("failed to get authentication provider url", TestHelper.AUTH_URL, loginAction
                .getAuthProviderURL());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getAuthInitialContextFactory()}</code> method.
     * </p>
     *
     * <p>
     * Expects the same which is loaded from the config.
     * </p>
     *
     */
    public void testGetAuthInitialContextFactoryAccuracy() {
        assertEquals("failed to get authentication initial context factory", TestHelper.AUTH_CTX_FACTORY,
                loginAction.getAuthInitialContextFactory());
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getLoginRemote()}</code> method.
     * </p>
     *
     * <p>
     * Expects non-null instance.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetLoginRemoteAccuracy() throws Exception {
        assertNotNull("failed to get LoginRemote", loginAction.getLoginRemote());
    }

    /**
     * <p>
     * Failure test of <code>{@link LoginAction#getLoginRemote()}</code> method.
     * </p>
     *
     * <p>
     * Undeploy the ejb.
     * </p>
     *
     * <p>
     * Expects <code>NamingException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetLoginRemoteFailure1() throws Exception {
        try {
            TestHelper.undeployEJB();
            loginAction.getLoginRemote();
            fail("NamingException should be thrown.");
        } catch (NamingException e) {
            // expect
        }
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getChatUserProfileManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetChatUserProfileManagerAccuracy() throws Exception {
        assertNotNull("failed to get ChatUserProfileManager", loginAction.getChatUserProfileManager());
    }

    /**
     * <p>
     * Failure test of <code>{@link LoginAction#getChatUserProfileManager()}</code> method.
     * </p>
     *
     * <p>
     * Wrong Config.
     * </p>
     *
     * <p>
     * Expects <code>SpecificationConfigurationException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetChatUserProfileManagerFailure1() throws Exception {
        try {
            TestHelper.releaseConfigs();
            loginAction.getChatUserProfileManager();
            fail("SpecificationConfigurationException should be thrown.");
        } catch (SpecificationConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link LoginAction#getChatUserProfileManager()}</code> method.
     * </p>
     *
     * <p>
     * Wrong Config.
     * </p>
     *
     * <p>
     * Expects <code>IllegalReferenceException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetChatUserProfileManagerFailure2() throws Exception {
        try {
            TestHelper.loadConfigs(false, "IllegalRef_Config.xml");
            loginAction.getChatUserProfileManager();
            fail("IllegalReferenceException should be thrown.");
        } catch (IllegalReferenceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link LoginAction#getChatUserProfileManager()}</code> method.
     * </p>
     *
     * <p>
     * Wrong Config.
     * </p>
     *
     * <p>
     * Expects <code>InvalidClassSpecificationException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetChatUserProfileManagerFailure3() throws Exception {
        try {
            TestHelper.loadConfigs(false, "Invalid_Class_Config.xml");
            loginAction.getChatUserProfileManager();
            fail("InvalidClassSpecificationException should be thrown.");
        } catch (InvalidClassSpecificationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link LoginAction#getChatUserProfileManager()}</code> method.
     * </p>
     *
     * <p>
     * Different Class Type
     * </p>
     *
     * <p>
     * Expects <code>ClassCastException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetChatUserProfileManagerFailure4() throws Exception {
        try {
            TestHelper.loadConfigs(false, "Class_Cast_Config.xml");
            loginAction.getChatUserProfileManager();
            fail("ClassCastException should be thrown.");
        } catch (ClassCastException e) {
            // expect
        }
    }

    /**
     * <p>
     * Detail test of <code>{@link LoginAction#getAuthorizationManager()}</code> method.
     * </p>
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetAuthorizationManagerAccuracy() throws Exception {
        assertNotNull("failed to get Authorization Manager", loginAction.getAuthorizationManager());
    }

    /**
     * <p>
     * Failure test of <code>{@link LoginAction#getAuthorizationManager()}</code> method.
     * </p>
     *
     * <p>
     * Config problem.
     * </p>
     *
     * <p>
     * Expects <code>ConfigurationException</code> to be thrown.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetAuthorizationManagerFailure() throws Exception {
        try {
            TestHelper.releaseConfigs();
            loginAction.getAuthorizationManager();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expect
        }
    }
}
