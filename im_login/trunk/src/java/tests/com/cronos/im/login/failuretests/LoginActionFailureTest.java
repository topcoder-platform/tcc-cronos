/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import java.lang.reflect.InvocationTargetException;

import com.cronos.im.login.AdminLoginAction;
import com.topcoder.util.log.Level;

/**
 * Tests the <code>LoginAction</code> for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class LoginActionFailureTest extends ReflectionTestCase {

    /**
     * Represents the <code>LoginAction</code> for test.
     */
    private AdminLoginAction loginAction;

    /**
     * <p>
     * Tests the doLog method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Sets the action as null. Expects {@link IllegalArgumentException}
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoLog1() throws Exception {
        try {
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "doLog", new Class[] {String.class,
                String.class, String.class, String.class, Level.class }, new Object[] {"failure reviewer",
                    null, "testCase", "from junit", Level.ERROR });
            fail("Should throw illegal argument exception");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalArgumentException", e.getCause().toString().startsWith(
                    "java.lang.IllegalArgumentException"));
        }
    }

    /**
     * <p>
     * Tests the doLog method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Sets the user as empty string. Expects {@link IllegalArgumentException}
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoLog2() throws Exception {
        try {
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "doLog", new Class[] {String.class,
                String.class, String.class, String.class, Level.class }, new Object[] {"failure reviewer",
                    "", "testCase", "from junit", Level.ERROR });
            fail("Should throw illegal argument exception");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalArgumentException", e.getCause().toString().startsWith(
                    "java.lang.IllegalArgumentException"));
        }
    }

    /**
     * <p>
     * Tests the doLog method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Sets the message as null. Expects {@link IllegalArgumentException}
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoLog3() throws Exception {
        try {
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "doLog", new Class[] {String.class,
                String.class, String.class, String.class, Level.class }, new Object[] {"failure reviewer",
                    "failure action", "testCase", null, Level.ERROR });
            fail("Should throw illegal argument exception");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalArgumentException", e.getCause().toString().startsWith(
                    "java.lang.IllegalArgumentException"));
        }
    }

    /**
     * <p>
     * Tests the doLog method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Sets the message as empty string. Expects {@link IllegalArgumentException}
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoLog4() throws Exception {
        try {
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "doLog", new Class[] {String.class,
                String.class, String.class, String.class, Level.class }, new Object[] {"failure reviewer",
                    "failure action", "testCase", "", Level.ERROR });
            fail("Should throw illegal argument exception");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalArgumentException", e.getCause().toString().startsWith(
                    "java.lang.IllegalArgumentException"));
        }
    }

    /**
     * <p>
     * Tests the getAuthorizationManager method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Retrieves the authorization manager without loading any config files. Expects
     * <code>ConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetAuthorizationManager() throws Exception {
        try {
            FailureTestHelper.releaseConfigFiles();
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "getAuthorizationManager", new Class[] {},
                    new Object[] {});
            fail("Should throw ConfigurationException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw ConfigurationException", e.getCause().toString().startsWith(
                    "com.topcoder.security.authorization.ConfigurationException:"));
        }
    }

    /**
     * <p>
     * Tests the getChatUserProfileManager method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Retrieves the chat user profile manager without loading any config files. Expects
     * <code>SpecificationConfigurationException</code>.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetChatUserProfileManager1() throws Exception {
        try {
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "getChatUserProfileManager", new Class[] {},
                    new Object[] {});
            fail("Should throw SpecificationConfigurationException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw SpecificationConfigurationException", e.getCause().toString().startsWith(
                    "com.topcoder.util.objectfactory.impl.SpecificationConfigurationException"));
        }
    }

    /**
     * <p>
     * Tests the getChatUserProfileManager method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Retrieves the chat user profile manager with wrong config files. Expects
     * <code>InvalidClassSpecificationException</code>
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetChatUserProfileManager2() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("Wrong_Config1.xml");
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "getChatUserProfileManager", new Class[] {},
                    new Object[] {});
            fail("Should throw InvalidClassSpecificationException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw InvalidClassSpecificationException", e.getCause().toString().startsWith(
                    "com.topcoder.util.objectfactory.InvalidClassSpecificationException"));
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getChatUserProfileManager method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Retrieves the chat user profile manager with wrong config files. Expects
     * <code>InvalidClassSpecificationException</code>
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetChatUserProfileManager3() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("Wrong_Config2.xml");
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "getChatUserProfileManager", new Class[] {},
                    new Object[] {});
            fail("Should throw ClassCastException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw ClassCastException", e.getCause().toString().startsWith(
                    "java.lang.ClassCastException"));
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getChatUserProfileManager method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Retrieves the chat user profile manager with wrong config files. Expects
     * <code>InvalidClassSpecificationException</code>
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetChatUserProfileManager4() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("Wrong_Config3.xml");
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "getChatUserProfileManager", new Class[] {},
                    new Object[] {});
            fail("Should throw IllegalReferenceException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalReferenceException", e.getCause().toString().startsWith(
                    "com.topcoder.util.objectfactory.impl.IllegalReferenceException"));
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getLoginRemote method of <code>LoginAction</code>.
     * </p>
     * <p>
     * Invokes the remote login without deploying the ejb. Expects <code>NamingException</code>.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetLoginRemoteFailure() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("config.xml");
            FailureTestHelper.undeployMockEJB();
            loginAction = new AdminLoginAction();
            // invokes the doLog method
            invokeSuperMethod(loginAction, AdminLoginAction.class, "getLoginRemote", new Class[] {},
                    new Object[] {});
            fail("Should throw NamingException.");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw NamingException", e.getCause().toString().startsWith(
                    "javax.naming.NameNotFoundException"));
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }
}