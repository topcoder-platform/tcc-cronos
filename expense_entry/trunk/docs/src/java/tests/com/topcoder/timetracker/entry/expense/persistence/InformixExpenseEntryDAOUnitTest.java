/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.MockAuditManager;
import com.topcoder.timetracker.entry.expense.UnitTestHelper;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.rejectreason.RejectReason;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>InformixExpenseEntryDAO</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixExpenseEntryDAOUnitTest extends TestCase {
    /** Represents the <code>InformixExpenseEntryDAO</code> instance used for testing. */
    private InformixExpenseEntryDAO dao = null;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>AuditManager</code> instance used for testing. */
    private MockAuditManager auditManager = null;

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

        // create the testing instance
        UnitTestHelper.deployEJB();
        dao = new InformixExpenseEntryDAO(InformixExpenseEntryDAO.class.getName());

        auditManager = (MockAuditManager) UnitTestHelper.getPrivateField(dao.getClass(), dao, "auditManager");

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
     * <p>
     * Accuracy test for the constructor <code>InformixExpenseEntryDAO()</code>. No exception is expected, all the
     * fields should be set.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_NoArgAccuracy() throws Exception {
        dao = new InformixExpenseEntryDAO();
        assertNotNull("The dao instance should be created.", dao);

        assertNotNull("The connectionFactory should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionFactory"));
        assertNotNull("The invoiceManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "invoiceManager"));
        assertNotNull("The expenseTypeManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "expenseTypeManager"));
        assertNotNull("The expenseStatusManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "expenseStatusManager"));
        assertNotNull("The rejectReasonDAO should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "rejectReasonDAO"));
        assertNotNull("The auditManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "auditManager"));
        assertEquals("The connectionName should be loaded properly.", UnitTestHelper.CONN_NAME,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionName"));
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_StringNullNamespace() throws Exception {
        try {
            new InformixExpenseEntryDAO(null);
            fail("The namespace is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_StringEmptyNamespace() throws Exception {
        try {
            new InformixExpenseEntryDAO("  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace does not exist. Expect
     * ConfigurationException.
     * </p>
     */
    public void testInformixExpenseEntryDAO_StringNotExistNamespace() {
        try {
            new InformixExpenseEntryDAO("Unknown");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>InformixExpenseEntryDAO(String)</code>. No exception is expected, all
     * the fields should be set.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_StringAccuracy() throws Exception {
        assertNotNull("The dao instance should be created.", dao);

        assertNotNull("The connectionFactory should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionFactory"));
        assertNotNull("The invoiceManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "invoiceManager"));
        assertNotNull("The expenseTypeManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "expenseTypeManager"));
        assertNotNull("The expenseStatusManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "expenseStatusManager"));
        assertNotNull("The rejectReasonDAO should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "rejectReasonDAO"));
        assertNotNull("The auditManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "auditManager"));
        assertEquals("The connectionName should be loaded properly.", UnitTestHelper.CONN_NAME,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionName"));
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the given expense entry is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_NullEntry() throws Exception {
        try {
            dao.addEntry(null, false);
            fail("The expense entry is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the creation date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_CreationDateNotNull() throws Exception {
        entry.setCreationDate(new Date());

        try {
            dao.addEntry(entry, false);
            fail("The creation date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the modification date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_ModificationDateNotNull() throws Exception {
        entry.setModificationDate(new Date());

        try {
            dao.addEntry(entry, false);
            fail("The modification date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
            dao.addEntry(entry, false);
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
            dao.addEntry(entry, false);
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

        dao.addEntry(entry, false);

        ExpenseEntry retrieved = dao.retrieveEntry(entry.getId());
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
            dao.addEntry(entry, false);
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
            dao.addEntry(entry, false);
            fail("The date of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the ID of company is -1. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_CompanyIdInvalid() throws Exception {
        entry.setCompanyId(-1);

        try {
            dao.addEntry(entry, false);
            fail("The ID of company is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
            dao.addEntry(entry, false);
            fail("The type of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the ID of expense type is -1. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_TypeInvalid() throws Exception {
        entry.getExpenseType().setId(-1);

        try {
            dao.addEntry(entry, false);
            fail("The ID of expense type is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the company ID of expense type is invalid. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_TypeCompanyIdInvalid() throws Exception {
        UnitTestHelper.setPrivateField(entry.getExpenseType().getClass(), entry.getExpenseType(), "companyId",
            new Long(-1));

        try {
            dao.addEntry(entry, false);
            fail("The company ID of expense type is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
            dao.addEntry(entry, false);
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
            dao.addEntry(entry, false);
            fail("The status of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the ID of expense status is -1. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_StatusInvalid() throws Exception {
        entry.getStatus().setId(-1);

        try {
            dao.addEntry(entry, false);
            fail("The ID of expense status is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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

        dao.addEntry(entry, false);

        ExpenseEntry retrieved = dao.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the ID of invoice is invalid. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_InvoiceIdInvalid() throws Exception {
        entry.getInvoice().setId(-1);

        try {
            dao.addEntry(entry, false);
            fail("The ID of invoice is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the company ID of invoice is invalid. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_InvoiceCompanyIdInvalid() throws Exception {
        UnitTestHelper.setPrivateField(entry.getInvoice().getClass(), entry.getInvoice(), "companyId", new Long(-1));

        try {
            dao.addEntry(entry, false);
            fail("The company ID of invoice is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
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

        dao.addEntry(entry, false);

        ExpenseEntry retrieved = dao.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the ID of reject reason is invalid. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_RejectReasonIdInvalid() throws Exception {
        RejectReason reason = ((RejectReason) entry.getRejectReasons().values().toArray()[0]);
        reason.setId(-1);

        try {
            dao.addEntry(entry, false);
            fail("The ID of reject reason is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the company ID of reject reason is invalid. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_RejectReasonCompanyIdInvalid() throws Exception {
        RejectReason reason = ((RejectReason) entry.getRejectReasons().values().toArray()[0]);
        UnitTestHelper.setPrivateField(reason.getClass(), reason, "companyId", new Long(-1));

        try {
            dao.addEntry(entry, false);
            fail("The company ID of reject reason is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
            dao.addEntry(entry, false);
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
        dao.addEntry(entry, false);

        // Add again, should return false.
        entry.setCreationDate(null);
        entry.setModificationDate(null);

        assertFalse("The ID exists in database.", dao.addEntry(entry, false));
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntry</code>. The audit flag is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_AuditAccuracy() throws Exception {
        assertTrue("The entry should be added.", dao.addEntry(entry, true));

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 14,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
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
        assertTrue("The entry should be added.", dao.addEntry(entry, false));

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
            assertEquals("The modification user should be correct.", "Modify",
                    resultSet.getString("modification_user"));
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
        assertTrue("The entry should be added.", dao.addEntry(entry, false));

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
            assertEquals("The modification user should be correct.", "Modify",
                    resultSet.getString("modification_user"));
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
     * Tests <code>updateEntry</code> when the given expense entry is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_NullEntry() throws Exception {
        try {
            dao.updateEntry(null, false);
            fail("The expense entry is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
            dao.updateEntry(entry, false);
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
            dao.updateEntry(entry, false);
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
        dao.addEntry(entry, false);
        UnitTestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "description", null);

        dao.updateEntry(entry, false);

        ExpenseEntry retrieved = dao.retrieveEntry(entry.getId());
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
            dao.updateEntry(entry, false);
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
            dao.updateEntry(entry, false);
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
            dao.updateEntry(entry, false);
            fail("The type of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the ID of expense type is -1. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_TypeInvalid() throws Exception {
        entry.getExpenseType().setId(-1);

        try {
            dao.updateEntry(entry, false);
            fail("The ID of expense type is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
            dao.updateEntry(entry, false);
            fail("The status of expense is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the ID of expense status is -1. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_StatusInvalid() throws Exception {
        entry.getStatus().setId(-1);

        try {
            dao.updateEntry(entry, false);
            fail("The ID of expense status is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
        assertFalse("The record should not be updated.", dao.updateEntry(entry, false));
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the invoice is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_InvoiceNull() throws Exception {
        dao.addEntry(entry, false);

        ExpenseEntry update = UnitTestHelper.BuildExpenseEntry();
        update.setId(entry.getId());
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(200));
        update.setBillable(false);
        update.setDate(UnitTestHelper.createDate(2005, 3, 5));
        update.setInvoice(null);

        assertTrue("The record should be updated.", dao.updateEntry(update, true));

        ExpenseEntry retrieved = dao.retrieveEntry(entry.getId());
        UnitTestHelper.assertEquals(retrieved, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntry</code>. The audit flag is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_AuditAccuracy() throws Exception {
        dao.addEntry(entry, false);

        ExpenseEntry update = UnitTestHelper.BuildExpenseEntry();
        update.setId(entry.getId());
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(200));
        update.setBillable(false);
        update.setDate(UnitTestHelper.createDate(2005, 3, 5));

        Thread.sleep(1000);
        assertTrue("The record should be updated.", dao.updateEntry(update, true));

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 6,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
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
        dao.addEntry(entry, false);

        ExpenseEntry update = UnitTestHelper.BuildExpenseEntry();
        update.setId(entry.getId());
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(200));
        update.setBillable(false);
        update.setDate(UnitTestHelper.createDate(2005, 3, 5));

        assertTrue("The record should be updated.", dao.updateEntry(update, false));

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
            assertEquals("The creation user should not be modified.", "Create",
                    resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                    resultSet.getString("modification_user"));
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
        assertFalse("The ID does not exist in database.", dao.deleteEntry(5, false));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntry</code>. The audit flag is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntry_AuditAccuracy() throws Exception {
        dao.addEntry(entry, false);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", dao.deleteEntry(entry.getId(), true));

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 14,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
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
        dao.addEntry(entry, false);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", dao.deleteEntry(entry.getId(), false));

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
     * Tests accuracy of <code>deleteAllEntryes</code>. The audit flag is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntryes_AuditAccuracy() throws Exception {
        // Add two records
        dao.addEntry(UnitTestHelper.BuildExpenseEntry(), false);
        dao.addEntry(UnitTestHelper.BuildExpenseEntry(), false);

        // Delete
        dao.deleteAllEntries(true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 2, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 14,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
        assertEquals("AuditManager should be properly invoked.", 14,
            this.auditManager.getAuditHeaders()[1].getDetails().length);
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
        dao.addEntry(UnitTestHelper.BuildExpenseEntry(), false);
        dao.addEntry(UnitTestHelper.BuildExpenseEntry(), false);

        // Delete
        dao.deleteAllEntries(false);

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
        assertNull("The ID does not exist in database, null should return.", dao.retrieveEntry(5));
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
        dao.addEntry(entry, false);

        ExpenseEntry retrieved = dao.retrieveEntry(entry.getId());

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
        assertTrue("The retrieved list should be empty.", dao.retrieveAllEntries().length == 0);
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

            dao.addEntry(entry, false);
            expected[i] = entry;
        }

        ExpenseEntry[] actual = dao.retrieveAllEntries();

        // Verify instances
        UnitTestHelper.assertEquals(expected, actual);
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NullEntries() throws Exception {
        try {
            dao.addEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_EmptyEntries() throws Exception {
        try {
            dao.addEntries(new ExpenseEntry[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntries(ExpenseEntry[], boolean)</code> when entries contain <code>null</code> element. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_EntriesContainsNullElement() throws Exception {
        try {
            dao.addEntries(new ExpenseEntry[] {null}, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. The audit flag is
     * set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_AtomicModeAuditAccuracy() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
        }

        dao.addEntries(expected, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("AuditManager should be properly invoked.", 14,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
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

        dao.addEntries(expected, false);

        ExpenseEntry[] actual = dao.retrieveAllEntries();
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
            dao.addEntries(expected, false);
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
            dao.addEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean)</code> when entryIds is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NullEntries() throws Exception {
        try {
            dao.deleteEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntries(long[], boolean)</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_EmptyEntries() throws Exception {
        try {
            dao.deleteEntries(new long[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean)</code> when it is in Atomic mode. The audit flag is set
     * to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_AtomicModeAuditAccuracy() throws Exception {
        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = UnitTestHelper.BuildExpenseEntry();
            entry.setId(i + 1);
            dao.addEntry(entry, false);
        }

        // delete
        dao.deleteEntries(new long[] {1, 2, 3, 4, 5}, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("AuditManager should be properly invoked.", 14,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
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
            dao.addEntry(entry, false);
        }

        // delete
        dao.deleteEntries(new long[] {1, 2, 3, 4, 5}, true);

        // Verify number
        assertEquals("The number of items in list should be correct.", 0, dao.retrieveAllEntries().length);
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
            dao.addEntry(entry, false);
        }

        dao.deleteEntry(entry.getId(), false);

        try {
            dao.deleteEntries(new long[] {1, 2, 3, 4, 5}, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries is null. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NullEntries() throws Exception {
        try {
            dao.updateEntries(null, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries is empty. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_EmptyEntries() throws Exception {
        try {
            dao.updateEntries(new ExpenseEntry[0], false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntries(ExpenseEntry[], boolean)</code> when entries contain <code>null</code> element. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_EntriesContainsNullElement() throws Exception {
        try {
            dao.updateEntries(new ExpenseEntry[] {null}, false);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean)</code> when it is in Atomic mode. The audit flag
     * is set to true.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_AtomicModeAuditAccuracy() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            dao.addEntry(expected[i], false);
        }

        // update it
        Thread.sleep(1000);

        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        dao.updateEntries(expected, true);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 5, this.auditManager.getAuditHeaders().length);

        for (int i = 0; i < this.auditManager.getAuditHeaders().length; i++) {
            assertEquals("The " + i + "th AuditManager should be properly invoked.", 3,
                this.auditManager.getAuditHeaders()[i].getDetails().length);
        }
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
            dao.addEntry(expected[i], false);
        }

        // update it
        for (int i = 0; i < 5; ++i) {
            expected[i].setAmount(new BigDecimal("1000"));
            expected[i].setInvoice(null);
        }

        dao.updateEntries(expected, true);

        // check
        ExpenseEntry[] actual = dao.retrieveAllEntries();
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
            dao.addEntry(expected[i], false);
        }

        expected[4].setStatus(new ExpenseStatus(10));

        try {
            dao.updateEntries(expected, false);
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
            dao.addEntry(expected[i], false);
        }

        dao.deleteEntry(1, false);

        try {
            dao.updateEntries(expected, false);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[])</code> when entryIds is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NullEntries() throws Exception {
        try {
            dao.retrieveEntries(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntries(long[])</code> when entryIds is empty. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_EmptyEntries() throws Exception {
        try {
            dao.retrieveEntries(new long[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
    public void testRetrieveEntries_AtomicModeAccuracy1() throws Exception {
        ExpenseEntry[] expected = new ExpenseEntry[5];

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            expected[i] = UnitTestHelper.BuildExpenseEntry();
            expected[i].setId(i + 1);
            dao.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = dao.retrieveEntries(new long[] {1, 2, 3, 4, 5});

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
            dao.addEntry(expected[i], false);
        }

        try {
            dao.retrieveEntries(new long[] {0, 1, 2, 3, 4});
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addEntries(ExpenseEntry[], boolean, boolean)</code> when it is in NonAtomic mode,
     * UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntries_NonAtomicModeAuditAccuracy() throws Exception {
        try {
            dao.addEntries(new ExpenseEntry[0], false, true);
            fail("UnsupportedOperationException is expected.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntries(long[], boolean, boolean)</code> when it is in NonAtomic mode,
     * UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntries_NonAtomicModeAuditAccuracy() throws Exception {
        try {
            dao.deleteEntries(new long[0], false, true);
            fail("UnsupportedOperationException is expected.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateEntries(ExpenseEntry[], boolean, boolean)</code> when it is in NonAtomic mode,
     * UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntries_NonAtomicModeAuditAccuracy() throws Exception {
        try {
            dao.updateEntries(new ExpenseEntry[0], false, true);
            fail("UnsupportedOperationException is expected.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntries(long[], boolean, boolean)</code> when it is in NonAtomic mode,
     * UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntries_NonAtomicModeAuditAccuracy() throws Exception {
        try {
            dao.retrieveEntries(new long[0], false);
            fail("UnsupportedOperationException is expected.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>searchEntries(Criteria)</code> when criteria is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_NullCriteria() throws Exception {
        try {
            dao.searchEntries(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
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
            dao.addEntry(expected[i], false);
        }

        ExpenseEntry[] actual = dao.searchEntries(new FieldMatchCriteria("expense_type.expense_type_id",
                    new Long(expected[0].getExpenseType().getId())));

        UnitTestHelper.assertEquals(expected, actual);
    }
}
