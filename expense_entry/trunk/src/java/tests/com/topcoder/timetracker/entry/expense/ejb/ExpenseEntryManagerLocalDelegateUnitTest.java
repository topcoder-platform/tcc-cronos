/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.UnitTestHelper;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseEntryManagerLocalDelegate;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryManagerLocalDelegate</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseEntryManagerLocalDelegateUnitTest extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>ExpenseEntryManagerLocalDelegate</code> instance used for testing. */
    private ExpenseEntryManagerLocalDelegate delegate = null;

    /** Represents a valid expense entry entry instance. */
    private ExpenseEntry entry;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.addConfig("DBConnectionFactory_Config.xml");
        UnitTestHelper.addConfig("ObjectFactory_Config.xml");
        UnitTestHelper.addConfig("InformixExpenseEntryDAO_Config.xml");
        UnitTestHelper.addConfig("BasicExpenseEntryBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseEntryBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseEntryManagerLocalDelegate_Config.xml");

        UnitTestHelper.addConfig("InformixExpenseStatusDAO_Config.xml");
        UnitTestHelper.addConfig("ExpenseStatusBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseStatusManagerLocalDelegate_Config.xml");
        UnitTestHelper.addConfig("InformixExpenseTypeDAO_Config.xml");
        UnitTestHelper.addConfig("ExpenseTypeBean_Config.xml");
        UnitTestHelper.addConfig("ExpenseTypeManagerLocalDelegate_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        // create delegate
        UnitTestHelper.deployEJB();
        delegate = new ExpenseEntryManagerLocalDelegate();

        entry = UnitTestHelper.BuildExpenseEntry();
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
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        UnitTestHelper.undeployEJB();
    }

    /**
     * Accuracy test for the constructor <code>ExpenseEntryManagerLocalDelegate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_NoArgAccuracy() throws Exception {
        assertNotNull("The ExpenseEntryManagerLocalDelegate instance should be created.", delegate);
        assertNotNull("The localEJB should be created.",
            UnitTestHelper.getPrivateField(delegate.getClass(), delegate, "localEJB"));
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
    public void testExpenseTypeManagerLocalDelegate_EmptyNamespace() throws Exception {
        try {
            new ExpenseEntryManagerLocalDelegate(" ");
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
     * Accuracy test for the constructor <code>ExpenseEntryManagerLocalDelegate(String namespace)</code>. No exception
     * is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseTypeManagerLocalDelegate_Accuracy() throws Exception {
        assertNotNull("The ExpenseEntryManagerLocalDelegate instance should be created.", delegate);
        assertNotNull("The localEJB should be created.",
            UnitTestHelper.getPrivateField(delegate.getClass(), delegate, "localEJB"));
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
     * Tests <code>addEntry</code> when the description is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_DescriptionNull() throws Exception {
        UnitTestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "description", null);

        delegate.addEntry(entry, false);

        ExpenseEntry retrieved = delegate.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the amount of money is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_AmountNull() throws Exception {
        UnitTestHelper.setPrivateField(entry.getClass(), entry, "amount", null);

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
        UnitTestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "date", null);

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
        UnitTestHelper.setPrivateField(entry.getClass(), entry, "expenseType", null);

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
        UnitTestHelper.setPrivateField(entry.getClass(), entry, "status", null);

        try {
            delegate.addEntry(entry, false);
            fail("The status of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the invoice is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_InvoiceNull() throws Exception {
        entry.setInvoice(null);

        delegate.addEntry(entry, false);

        ExpenseEntry retrieved = delegate.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the rejectReasons is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_RejectReasonsNull() throws Exception {
        UnitTestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "rejectReasons", null);

        delegate.addEntry(entry, false);

        ExpenseEntry retrieved = delegate.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
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
     * Tests accuracy of <code>addEntry</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_ExistAccuracy() throws Exception {
        delegate.addEntry(entry, false);

        // Add again, should return false.
        entry.setCreationDate(null);
        entry.setModificationDate(null);

        assertFalse("The ID exists in database.", delegate.addEntry(entry, false));
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntry</code> when ID is -1. A new ID is generated. A new record should be inserted
     * into the database. The creation date and modification date should be set the same. The method should return
     * <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_Accuracy() throws Exception {
        assertTrue("The entry should be added.", delegate.addEntry(entry, false));

        // Verify instance
        assertTrue("A ID should be generated.", entry.getId() != -1);
        assertNotNull("The creation date should be set.", entry.getCreationDate());
        assertNotNull("The modification date should be set.", entry.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", entry.getCreationDate(),
            entry.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.",
                    "Modify", resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.", entry.getAmount().doubleValue(),
                resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            UnitTestHelper.assertEquals("The date should be correct.", UnitTestHelper.createDate(2005, 2, 5),
                resultSet.getDate("entry_date"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntry</code> when ID is not -1. The ID should be used. A new record should be
     * inserted into the database. The creation date and modification date should be set the same. The method should
     * return <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_IDSetAccuracy() throws Exception {
        entry.setId(5);
        assertTrue("The entry should be added.", delegate.addEntry(entry, false));

        // Verify instance
        assertEquals("The ID should be used.", 5, entry.getId());
        assertNotNull("The creation date should be set.", entry.getCreationDate());
        assertNotNull("The modification date should be set.", entry.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", entry.getCreationDate(),
            entry.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.",
                    "Modify", resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.", entry.getAmount().doubleValue(),
                resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            UnitTestHelper.assertEquals("The date should be correct.", UnitTestHelper.createDate(2005, 2, 5),
                resultSet.getDate("entry_date"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
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
     * Tests <code>updateEntry</code> when the description is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_DescriptionNull() throws Exception {
        entry.setId(5);
        delegate.addEntry(entry, false);
        UnitTestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "description", null);

        delegate.updateEntry(entry, false);

        ExpenseEntry retrieved = delegate.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the amount of money is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_AmountNull() throws Exception {
        UnitTestHelper.setPrivateField(entry.getClass(), entry, "amount", null);

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
        UnitTestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "date", null);

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
        UnitTestHelper.setPrivateField(entry.getClass(), entry, "expenseType", null);

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
        UnitTestHelper.setPrivateField(entry.getClass(), entry, "status", null);

        try {
            delegate.updateEntry(entry, false);
            fail("The status of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntry</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryIDNotExistAccuracy() throws Exception {
        assertFalse("The record should not be updated.", delegate.updateEntry(entry, false));
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the invoice is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_InvoiceNull() throws Exception {
        delegate.addEntry(entry, false);

        ExpenseEntry update = UnitTestHelper.BuildExpenseEntry();
        update.setId(entry.getId());
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(200));
        update.setBillable(false);
        update.setDate(UnitTestHelper.createDate(2005, 3, 5));
        update.setInvoice(null);

        assertTrue("The record should be updated.", delegate.updateEntry(update, true));

        ExpenseEntry retrieved = delegate.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntry</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_Accuracy() throws Exception {
        delegate.addEntry(entry, false);

        ExpenseEntry update = UnitTestHelper.BuildExpenseEntry();
        update.setId(entry.getId());
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(200));
        update.setBillable(false);
        update.setDate(UnitTestHelper.createDate(2005, 3, 5));

        assertTrue("The record should be updated.", delegate.updateEntry(update, false));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.",
                    "Modify2", resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.", update.getAmount().doubleValue(),
                resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 0, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            UnitTestHelper.assertEquals("The date should be correct.", UnitTestHelper.createDate(2005, 3, 5),
                resultSet.getDate("entry_date"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntry</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntry_IDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", delegate.deleteEntry(5, false));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntry</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntry_Accuracy() throws Exception {
        delegate.addEntry(entry, false);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", delegate.deleteEntry(entry.getId(), false));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertFalse("No record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteAllEntryes</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntryes_Accuracy() throws Exception {
        // Add two records
        delegate.addEntry(UnitTestHelper.BuildExpenseEntry(), false);
        delegate.addEntry(UnitTestHelper.BuildExpenseEntry(), false);

        // Delete
        delegate.deleteAllEntries(false);

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_entry");

            assertFalse("No record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntry</code> when ID does not exist in database. <code>null</code> should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntry_IDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", delegate.retrieveEntry(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntry</code> when ID exists in database. The correct instance should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntry_Accuracy() throws Exception {
        delegate.addEntry(entry, false);

        ExpenseEntry retrieved = delegate.retrieveEntry(entry.getId());

        UnitTestHelper.assertEquals(entry, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllEntryes</code> when no record exists in database. An empty list should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntryes_NoRecordAccuracy() throws Exception {
        assertTrue("The retrieved list should be empty.", delegate.retrieveAllEntries().length == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllEntryes</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntryes_Accuracy() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < expected.length; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            entry.setDescription("Description" + i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(UnitTestHelper.createDate(2005, 2, i));

            delegate.addEntry(entry, false);
            expected[i] = entry;
        }

        ExpenseEntry[] actual = delegate.retrieveAllEntries();

        // Verify instances
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>searchEntries(Criteria)</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_Accuracy() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = delegate.searchEntries(new FieldMatchCriteria("expense_type.expense_type_id",
                    new Long(expected[0].getExpenseType().getId())));

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        delegate.addEntries(expected, false);

        ExpenseEntry[] actual = delegate.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            delegate.addEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the entries
     * has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setId(1);

        try {
            delegate.addEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. All the
     * entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        assertNull("Should return null", delegate.addEntries(expected, true, false));

        ExpenseEntry[] actual = delegate.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy5() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            delegate.addEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAccuracy6() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setId(1);

        try {
            delegate.addEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode. All
     * the entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        delegate.addEntries(expected, false, false);

        ExpenseEntry[] actual = delegate.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode. One
     * of the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].getStatus().setId(10);

        ExpenseEntry[] errors = delegate.addEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[4]}, errors);
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode. One
     * of the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        expected[4].setId(1);

        ExpenseEntry[] errors = delegate.addEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[4]}, errors);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean)</code> when it is in Atomic mode. All the entryIds should
     * be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            delegate.addEntry(entry, false);
        }

        // delete
        delegate.deleteEntries(new long[] {1, 2, 3, 4, 5}, true);

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, delegate.retrieveAllEntries().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean)</code> when it is in Atomic mode. One of the entryIds has
     * not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            delegate.addEntry(entry, false);
        }

        delegate.deleteEntry(entry.getId(), false);

        try {
            delegate.deleteEntries(new long[] {1, 2, 3, 4, 5}, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in Atomic mode. All the
     * entryIds should be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy3() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            delegate.addEntry(entry, false);
        }

        // delete
        assertNull("Should return null.", delegate.deleteEntries(new long[] {1, 2, 3, 4, 5}, true, false));

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, delegate.retrieveAllEntries().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAccuracy4() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            delegate.addEntry(entry, false);
        }

        delegate.deleteEntry(entry.getId(), false);

        try {
            delegate.deleteEntries(new long[] {1, 2, 3, 4, 5}, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in non-Atomic mode. All the
     * entryIds should be deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAccuracy1() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            delegate.addEntry(entry, false);
        }

        // delete
        delegate.deleteEntries(new long[] {1, 2, 3, 4, 5}, false, true);

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, delegate.retrieveAllEntries().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in non-Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAccuracy2() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            delegate.addEntry(entry, false);
        }

        delegate.deleteEntry(entry.getId(), false);

        long[] errors = delegate.deleteEntries(new long[] {1, 2, 3, 4, 5}, false, false);
        assertEquals("The error id should be correct.", 1, errors.length);
        assertEquals("The error id should be correct.", entry.getId(), errors[0]);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. All the entries
     * should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        delegate.updateEntries(expected, true);

        // check
        ExpenseEntry[] actual = delegate.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            delegate.updateEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        delegate.deleteEntry(1, false);

        try {
            delegate.updateEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. All
     * the entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        delegate.updateEntries(expected, true, true);

        // check
        ExpenseEntry[] actual = delegate.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy5() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            delegate.updateEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in Atomic mode. One of
     * the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAccuracy6() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        delegate.deleteEntry(1, false);

        try {
            delegate.updateEntries(expected, true, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode.
     * All the entries should be added. These entries have their own id already (NOT -1).
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        delegate.updateEntries(expected, false, true);

        // check
        ExpenseEntry[] actual = delegate.retrieveAllEntries();
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in non-Atomic mode.
     * One of the entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        expected[4].getStatus().setId(10);

        ExpenseEntry[] errors = delegate.updateEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[4]}, errors);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in non-Atomic mode. One of the
     * entries has errors to insert into the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        delegate.deleteEntry(1, false);

        ExpenseEntry[] errors = delegate.updateEntries(expected, false, false);
        UnitTestHelper.assertEquals(new ExpenseEntry[] {expected[0]}, errors);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. All the entries should
     * be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = delegate.retrieveEntries(new long[] {1, 2, 3, 4, 5});

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. One of the entryIds
     * has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        try {
            delegate.retrieveEntries(new long[] {0, 1, 2, 3, 4});
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. All the entries should
     * be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy3() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = delegate.retrieveEntries(new long[] {1, 2, 3, 4, 5}, true);

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Atomic mode. One of the entryIds
     * has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_AtomicModeAccuracy4() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        try {
            delegate.retrieveEntries(new long[] {0, 1, 2, 3, 4}, true);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Non-Atomic mode. All the entries
     * should be retrieved.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = delegate.retrieveEntries(new long[] {1, 2, 3, 4, 5}, false);

        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(int[], boolean)</code> when it is in Non-Atomic mode. One of the
     * entryIds has not exist in the database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAccuracy2() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[3];

        // Add 3 instances
        for (int i = 0; i < 3; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            delegate.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = delegate.retrieveEntries(new long[] {0, 1, 2, 3, 4}, false);
        UnitTestHelper.assertEquals(expected, actual);
    }
}
