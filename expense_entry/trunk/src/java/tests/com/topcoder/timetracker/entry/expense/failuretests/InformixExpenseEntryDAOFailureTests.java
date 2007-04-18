/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.File;
import java.sql.Connection;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseEntryDAO;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test cases of <code>InformixExpenseEntryDAO</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class InformixExpenseEntryDAOFailureTests extends TestCase {
	/**
	 * The path of the configuration files.
	 */
	private static final String PATH = "FailureTests" + File.separatorChar;

    /**
     * Represents the <code>InformixExpenseEntryDAO</code> instance used for testing.
     */
    private InformixExpenseEntryDAO dao = null;

    /**
     * Represents the connection instance for testing.
     */
    private Connection connection = null;

    /**
     * Represents a valid expense entry entry instance.
     */
    private ExpenseEntry entry;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	TestHelper.clearConfig();
        TestHelper.addConfig("DBConnectionFactory_Config.xml");
        TestHelper.addConfig("ObjectFactory_Config.xml");
        TestHelper.addConfig("InformixExpenseEntryDAO_Config.xml");

        TestHelper.addConfig("InformixExpenseStatusDAO_Config.xml");
        TestHelper.addConfig("ExpenseStatusBean_Config.xml");
        TestHelper.addConfig("ExpenseStatusManagerLocalDelegate_Config.xml");
        TestHelper.addConfig("InformixExpenseTypeDAO_Config.xml");
        TestHelper.addConfig("ExpenseTypeBean_Config.xml");
        TestHelper.addConfig("ExpenseTypeManagerLocalDelegate_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", PATH + "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);

        connection = TestHelper.getConnection();
        TestHelper.clearDatabase(connection);
        TestHelper.prepareData(connection);

        // create the testing instance
        TestHelper.deployEJB();
        dao = new InformixExpenseEntryDAO(InformixExpenseEntryDAO.class.getName());

        entry = TestHelper.BuildExpenseEntry();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
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
    public void testInformixExpenseEntryDAO_StringEmptyNamespace1() throws Exception {
        try {
            new InformixExpenseEntryDAO("");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
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
    public void testInformixExpenseEntryDAO_StringEmptyNamespace2() throws Exception {
        try {
            new InformixExpenseEntryDAO("   \n \t  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace1() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid1");
            fail("should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace2() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid2");
            fail("should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace3() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid3");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace4() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid4");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace5() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid5");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace6() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid6");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace7() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid7");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace8() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid8");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace9() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid9");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace10() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid10");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace11() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid11");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace12() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid12");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace13() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid13");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace14() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid14");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace15() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid15");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace16() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid16");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace17() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid17");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace18() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid18");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace19() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid19");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace20() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid20");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseEntryDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseEntryDAO_InvalidNamespace21() throws Exception {
        try {
            new InformixExpenseEntryDAO("invalid21");
            fail("Should throw ConfigurationException.");
        } catch (ConfigurationException e) {
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
     * Tests <code>addEntry</code> when the amount of money is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntry_AmountNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "amount", null);

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
        TestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "date", null);

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
        TestHelper.setPrivateField(entry.getClass(), entry, "expenseType", null);

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
        TestHelper.setPrivateField(entry.getExpenseType().getClass(), entry.getExpenseType(), "companyId",
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
        TestHelper.setPrivateField(entry.getClass(), entry, "status", null);

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
        TestHelper.setPrivateField(entry.getInvoice().getClass(), entry.getInvoice(), "companyId",
        		new Long(-1));

        try {
            dao.addEntry(entry, false);
            fail("The company ID of invoice is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
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
        TestHelper.setPrivateField(reason.getClass(), reason, "companyId", new Long(-1));

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
     * Tests <code>updateEntry</code> when the amount of money is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntry_AmountNull() throws Exception {
        TestHelper.setPrivateField(entry.getClass(), entry, "amount", null);

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
        TestHelper.setPrivateField(entry.getClass().getSuperclass(), entry, "date", null);

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
        TestHelper.setPrivateField(entry.getClass(), entry, "expenseType", null);

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
        TestHelper.setPrivateField(entry.getClass(), entry, "status", null);

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
}
