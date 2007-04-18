/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseTypeManagerLocalDelegate;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Failure test cases of <code>ExpenseTypeManagerLocalDelegate</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class ExpenseTypeManagerLocalDelegateFailureTests extends TestCase {
	/**
	 * The path of the configuration files.
	 */
	private static final String PATH = "FailureTests" + File.separatorChar;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig(PATH + "DBConnectionFactory_Config.xml");
        TestHelper.addConfig(PATH + "ObjectFactory_Config.xml");
        TestHelper.addConfig(PATH + "InformixExpenseTypeDAO_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseTypeBean_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseTypeManagerLocalDelegate_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", PATH + "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);

        // create delegate
        TestHelper.deployEJB();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();

        TestHelper.undeployEJB();
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the namespace is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_NullNamespace() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the namespace is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_EmptyNamespace1() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the namespace is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_EmptyNamespace2() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the namespace does not
     * exist, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_NotExistNamespace() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate("NotExist");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the property value for
     * jndi_context is invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_InvalidJndiContext() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate(ExpenseTypeManagerLocalDelegate.class.getName()
                + ".InvalidJndiContext");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the property value for
     * JndiReference is missing, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_MissingJndiReference() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate(ExpenseTypeManagerLocalDelegate.class.getName()
                + ".MissingJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the property value for
     * JndiReference is empty, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_EmptyJndiReference() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate(ExpenseTypeManagerLocalDelegate.class.getName()
                + ".EmptyJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseTypeManagerLocalDelegate(String namespace)</code> when the property value for
     * JndiReference is invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_InvalidJndiReference() throws Exception {
        try {
            new ExpenseTypeManagerLocalDelegate(ExpenseTypeManagerLocalDelegate.class.getName()
                + ".InvalidJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }
}
