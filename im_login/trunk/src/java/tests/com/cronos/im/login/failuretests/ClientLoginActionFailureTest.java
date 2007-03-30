/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import java.lang.reflect.InvocationTargetException;

import com.cronos.im.login.ClientLoginAction;

/**
 * Tests the {@link ClientLoginAction} for failure. This class tests other than execute methods.
 *
 * @author mittu
 * @version 1.0
 */
public class ClientLoginActionFailureTest extends ReflectionTestCase {

    /**
     * Represents ClientLoginAction for testing.
     */
    private ClientLoginAction clientLoginAction;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfigFile("config.xml");
        clientLoginAction = new ClientLoginAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clientLoginAction = null;
    }

    /**
     * <p>
     * Tests the checkChattable method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Inputs category as null. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattable1() throws Exception {
        try {
            // invokes the doLog method
            invokeMethod(clientLoginAction, ClientLoginAction.class, "checkChattable",
                    new Class[] {String.class, }, new Object[] {null });
            fail("Should throw illegal argument exception");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalArgumentException", e.getCause().toString().startsWith(
                    "java.lang.IllegalArgumentException"));
        }
    }

    /**
     * <p>
     * Tests the checkChattable method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Inputs category as empty string. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattable2() throws Exception {
        try {
            // invokes the doLog method
            invokeMethod(clientLoginAction, ClientLoginAction.class, "checkChattable",
                    new Class[] {String.class, }, new Object[] {"  " });
            fail("Should throw illegal argument exception");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw IllegalArgumentException", e.getCause().toString().startsWith(
                    "java.lang.IllegalArgumentException"));
        }
    }

    /**
     * <p>
     * Tests the checkChattable method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Without loading config file tries to check whether the category is chattable or not.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattable3() throws Exception {
        try {
            FailureTestHelper.releaseConfigFiles();
            // invokes the doLog method
            invokeMethod(clientLoginAction, ClientLoginAction.class, "checkChattable",
                    new Class[] {String.class, }, new Object[] {"1" });
            fail("Should throw ConfigurationException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw ConfigurationException", e.getCause().toString().startsWith(
                    "com.topcoder.db.connectionfactory.ConfigurationException"));
        }
    }

    /**
     * <p>
     * Tests the checkChattable method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Loads the wrong config files.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattable4() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("Wrong_Db_config.xml");
            // invokes the doLog method
            invokeMethod(clientLoginAction, ClientLoginAction.class, "checkChattable",
                    new Class[] {String.class, }, new Object[] {"1" });
            fail("Should throw DBConnectionException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw DBConnectionException", e.getCause().toString().startsWith(
                    "com.topcoder.db.connectionfactory.DBConnectionException"));
        }
    }

    /**
     * <p>
     * Tests the checkChattable method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Inputs category id as a string variable
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattable5() throws Exception {
        try {
            // invokes the doLog method
            invokeMethod(clientLoginAction, ClientLoginAction.class, "checkChattable",
                    new Class[] {String.class, }, new Object[] {"string" });
            fail("Should throw NumberFormatException");
        } catch (InvocationTargetException e) {
            assertTrue("failed to throw NumberFormatException", e.getCause().toString().startsWith(
                    "java.lang.NumberFormatException"));
        }
    }

    /**
     * <p>
     * Tests the checkChattable method of <code>ClientLoginAction</code>.
     * </p>
     * <p>
     * Inputs category id as that of unchattable one
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testCheckChattable6() throws Exception {
        try {
            // invokes the doLog method
            Boolean result = (Boolean) invokeMethod(clientLoginAction, ClientLoginAction.class, "checkChattable",
                    new Class[] {String.class, }, new Object[] {"2" });
            assertFalse("should recieve the category as unchattable", result.booleanValue());
        } catch (InvocationTargetException e) {
            fail("Recieved Exception");
        }
    }
}
