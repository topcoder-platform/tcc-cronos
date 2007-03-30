/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.cronos.im.login.ClientLoginAction;

/**
 * <p>
 * This Junit tests ClientLoginAction class. It contains the accuracy test cases for all methods without loading
 * the config file. This test suite covers all the possible functionalities/behavior of this class for accurate
 * inputs.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class ClientLoginActionTest extends TestCase {

    /** ClientLoginAction instance for testing.*/
    private ClientLoginAction clientLoginAction = null;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        clientLoginAction = new ClientLoginAction();
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
        clientLoginAction = null;
    }

    /**
     * <p>
     * Accuracy test for <code>ClientLoginAction()</code>.
     * </p>
     * <p>
     * Expects non null instance. Also check inheritance.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testClientLoginActionAccuracy() throws Exception {
        assertNotNull("ClientLoginAction creation failed", new ClientLoginAction());
        assertEquals("ClientLoginAction doesn't inherit from LoginAction", "com.cronos.im.login.LoginAction",
                clientLoginAction.getClass().getSuperclass().getName());
    }

    /**
     * <p>
     * Accuracy test for <code>getUnchattableForwardName()</code>.
     * </p>
     * <p>
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetUnchattableForwardNameAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getUnchattableForwardName",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the unchattable forward name
            String unchatFwdName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default unchattable forward name properly", "unchattable",
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
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetConnectionNameAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getConnectionName",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the connection name
            String connName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default connection name properly", "connectionName", connName);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getFamilyNameKey()</code>.
     * </p>
     * <p>
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetFamilyNameKeyAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getFamilyNameKey",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the family name key
            String familyName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default family name properly", "familyName", familyName);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getFamilyNameKey()</code>.
     * </p>
     * <p>
     * Load the config files. Expects the value in config file.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetFamilyNameKeyAccuracy1() throws Exception {
        Method method = null;
        try {
            AccuracyTestHelper.reloadConfig("ClientLogin_accuracy_config.xml");
            clientLoginAction = new ClientLoginAction();
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getFamilyNameKey",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the family name key
            String familyName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default family name properly", "config familyName", familyName);

        } finally {
            AccuracyTestHelper.releaseConfig();
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
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetLastNameKeyAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getLastNameKey",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the last name key
            String lastName = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default last name properly", "lastName", lastName);

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
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetTitleKeyAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper
                    .getMethod(ClientLoginAction.class.getName(), "getTitleKey", new Class[] {});
            method.setAccessible(true);

            // retrieves the title
            String title = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default title properly", "title", title);

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
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetEmailKeyAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper
                    .getMethod(ClientLoginAction.class.getName(), "getEmailKey", new Class[] {});
            method.setAccessible(true);

            // retrieves the email
            String email = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default email properly", "email", email);

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
     * Doesn't load the config files. Expects the default value set.
     * </p>
     *
     * @throws Exception
     *             throws exception to JUnit.
     */
    public void testgetCompanyKeyAccuracy() throws Exception {
        Method method = null;
        try {
            method = AccuracyTestHelper.getMethod(ClientLoginAction.class.getName(), "getCompanyKey",
                    new Class[] {});
            method.setAccessible(true);

            // retrieves the company
            String company = (String) method.invoke(clientLoginAction, new Object[] {});

            assertEquals("failed to retrieve the default email properly", "company", company);

        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

}
