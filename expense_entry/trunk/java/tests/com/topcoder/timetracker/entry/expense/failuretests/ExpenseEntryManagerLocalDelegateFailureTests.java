/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.File;
import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseEntryManagerLocalDelegate;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Failure test cases of <code>ExpenseEntryManagerLocalDelegate</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class ExpenseEntryManagerLocalDelegateFailureTests extends TestCase {
	/**
	 * The path of the configuration files.
	 */
	private static final String PATH = "FailureTests" + File.separatorChar;

    /**
     * Represents the connection instance for testing.
     */
    private Connection connection = null;

    /**
     * Represents the <code>ExpenseEntryManagerLocalDelegate</code> instance used for testing.
     */
    private ExpenseEntryManagerLocalDelegate delegate = null;

    /**
     * Represents a valid expense entry entry instance.
     */
    private ExpenseEntry entry;

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
        TestHelper.addConfig(PATH + "InformixExpenseEntryDAO_Config.xml");
        TestHelper.addConfig(PATH + "BasicExpenseEntryBean_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseEntryBean_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseEntryManagerLocalDelegate_Config.xml");

        TestHelper.addConfig(PATH + "InformixExpenseStatusDAO_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseStatusBean_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseStatusManagerLocalDelegate_Config.xml");
        TestHelper.addConfig(PATH + "InformixExpenseTypeDAO_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseTypeBean_Config.xml");
        TestHelper.addConfig(PATH + "ExpenseTypeManagerLocalDelegate_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", PATH + "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        connection = TestHelper.getConnection();
        TestHelper.prepareData(connection);

        // create delegate
        TestHelper.deployEJB();
        delegate = new ExpenseEntryManagerLocalDelegate();

        entry = TestHelper.BuildExpenseEntry();
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
        try {
            TestHelper.clearConfig();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        TestHelper.undeployEJB();
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the namespace is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_NullNamespace() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the namespace is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_EmptyNamespace1() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the namespace is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_EmptyNamespace2() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the namespace does not
     * exist, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_NotExistNamespace() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate("NotExist");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the property value for
     * jndi_context is invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_InvalidJndiContext() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate(ExpenseEntryManagerLocalDelegate.class.getName()
                + ".InvalidJndiContext");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the property value for
     * JndiReference is missing, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_MissingJndiReference() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate(ExpenseEntryManagerLocalDelegate.class.getName()
                + ".MissingJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the property value for
     * JndiReference is empty, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_EmptyJndiReference() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate(ExpenseEntryManagerLocalDelegate.class.getName()
                + ".EmptyJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code> when the property value for
     * JndiReference is invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_InvalidJndiReference() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate(ExpenseEntryManagerLocalDelegate.class.getName()
                + ".InvalidJndiReference");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_CreationUserNull() throws Exception {
        entry.setCreationUser(null);

        try {
            delegate.addEntry(entry, false);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_ModificationUserNull() throws Exception {
        entry.setModificationUser(null);

        try {
            delegate.addEntry(entry, false);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the amount of money is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_AmountNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "amount", null);

        try {
            delegate.addEntry(entry, false);
            fail("The amount of money is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the date of expense is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_DateNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "date", null);

        try {
            delegate.addEntry(entry, false);
            fail("The date of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the type of expense is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_TypeNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "expenseType", null);

        try {
            delegate.addEntry(entry, false);
            fail("The type of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the company ID of expense type is not matched. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_TypeCompanyIdNotMatched() throws Exception {
        entry.setCompanyId(2);
        entry.getInvoice().setCompanyId(2);
        entry.getExpenseType().setCompanyId(2);

        RejectReason reason = ((RejectReason) entry.getRejectReasons().values().toArray()[0]);
        reason.setCompanyId(2);

        try {
            delegate.addEntry(entry, false);
            fail("The company ID of expense type is not matched, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the status of expense is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_StatusNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "status", null);

        try {
            delegate.addEntry(entry, false);
            fail("The status of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the company ID of reject reason is not matched. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_RejectReasonCompanyIdNotMatched() throws Exception {
        entry.setCompanyId(3);
        entry.getInvoice().setCompanyId(3);
        entry.getExpenseType().setCompanyId(3);

        RejectReason reason = ((RejectReason) entry.getRejectReasons().values().toArray()[0]);
        reason.setCompanyId(3);

        try {
            delegate.addEntry(entry, false);
            fail("The company ID of reject reason is not matched, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_CreationUserNull() throws Exception {
        entry.setCreationUser(null);

        try {
            delegate.updateEntry(entry, false);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_ModificationUserNull() throws Exception {
        entry.setModificationUser(null);

        try {
            delegate.updateEntry(entry, false);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the amount of money is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_AmountNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "amount", null);

        try {
            delegate.updateEntry(entry, false);
            fail("The amount of money is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the date of expense is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_DateNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "date", null);

        try {
            delegate.updateEntry(entry, false);
            fail("The date of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the type of expense is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_TypeNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "expenseType", null);

        try {
            delegate.updateEntry(entry, false);
            fail("The type of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the status of expense is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_StatusNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "status", null);

        try {
            delegate.updateEntry(entry, false);
            fail("The status of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }
}
