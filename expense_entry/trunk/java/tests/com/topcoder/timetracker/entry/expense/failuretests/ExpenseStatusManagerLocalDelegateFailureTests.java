/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseStatusManagerLocalDelegate;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Failure test cases of <code>ExpenseStatusManagerLocalDelegate</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class ExpenseStatusManagerLocalDelegateFailureTests extends TestCase {
	/**
	 * The path of the configuration files.
	 */
	private static final String PATH = "FailureTests" + File.separatorChar;

    /**
     * Represents a valid expense entry status instance.
     */
    private ExpenseStatus status;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig(PATH + "DBConnectionFactory_Config.xml");
        TestHelper.addConfig(PATH + "ObjectFactory_Config.xml");
        TestHelper.addConfig(PATH + "InformixExpenseStatusDAO_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseStatusBean_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseStatusManagerLocalDelegate_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", PATH + "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);

        // create delegate
        TestHelper.deployEJB();

        status = new ExpenseStatus();
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
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
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the namespace is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_NullNamespace() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the namespace is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_EmptyNamespace1() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the namespace is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_EmptyNamespace2() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate("   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the namespace does
     * not exist, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_NotExistNamespace() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate("NotExist");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the property value
     * for jndi_context is invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_InvalidJndiContext() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate(ExpenseStatusManagerLocalDelegate.class.getName()
                + ".InvalidJndiContext");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the property value
     * for JndiReference is missing, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_MissingJndiReference() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate(ExpenseStatusManagerLocalDelegate.class.getName()
                + ".MissingJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the property value
     * for JndiReference is empty, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_EmptyJndiReference() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate(ExpenseStatusManagerLocalDelegate.class.getName()
                + ".EmptyJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseStatusManagerLocalDelegate(String namespace)</code> when the property value
     * for JndiReference is invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseStatusManagerLocalDelegate_InvalidJndiReference() throws Exception {
        try {
            new ExpenseStatusManagerLocalDelegate(ExpenseStatusManagerLocalDelegate.class.getName()
                + ".InvalidJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }
}
