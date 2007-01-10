/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryManager</code> class. The test uses
 * <code>ExpenseEntryDbPersistence</code> as the underlying persistence.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class ExpenseEntryManagerTestCase extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the configuration manager instance used to load and delete configuration. */
    private ConfigManager configManager;

    /** Represents the manager instance used in tests. */
    private ExpenseEntryManager manager;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents a valid expense entry entry instance. */
    private ExpenseEntry entry;

    /** Represents a valid expense entry type instance. */
    private ExpenseEntryType type;

    /** Represents a valid expense entry status instance. */
    private ExpenseEntryStatus status;

    /**
     * <p>
     * Sets up the test environment. The configurations are removed. A valid manager is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status are added into the
     * database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        // Create the manager
        manager = new ExpenseEntryManager(NAMESPACE);
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        TestHelper.clearDatabase(connection);
        V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a')", connection);

        // Insert an expense type
        PreparedStatement ps = connection.prepareStatement("INSERT INTO expense_type(expense_type_id, description, "
                + "creation_user, creation_date, modification_user, modification_date, active) VALUES (?,?,?,?,?,?,0)");

        try {
            ps.setInt(1, 1);
            ps.setString(2, "Travel Expense");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }
        V1Dot1TestHelper.executeSQL("insert into comp_exp_type values(1, 1, current, 'a', current, 'a')", connection);

        // Insert an expense status
        ps = connection.prepareStatement("INSERT INTO expense_status(expense_status_id, description, creation_user, "
                + "creation_date, modification_user, modification_date) VALUES (?,?,?,?,?,?)");

        try {
            ps.setInt(1, 2);
            ps.setString(2, "Pending Approval");
            ps.setString(3, "TangentZ");
            ps.setDate(4, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(5, "Ivern");
            ps.setDate(6, new java.sql.Date(TestHelper.createDate(2005, 2, 1).getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
        }

        // Create the expense status
        status = new ExpenseEntryStatus(2);

        status.setDescription("Pending Approval");
        status.setCreationDate(TestHelper.createDate(2005, 1, 1));
        status.setModificationDate(TestHelper.createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        // Create the expense type
        type = new ExpenseEntryType(1);

        type.setCompanyId(1);
        type.setDescription("Travel Expense");
        type.setCreationDate(TestHelper.createDate(2005, 1, 1));
        type.setModificationDate(TestHelper.createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");

        // Create the expense entry
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The manager instance is disposed. The data
     * table is truncated. The expense entry entry instance is disposed. The expense type and expense status are
     * deleted.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearDatabase(connection);

        connection.close();

        configManager = null;

        try {
            manager.getEntryPersistence().closeConnection();
        } catch (Exception e) {
            // ignore
        }

        manager = null;

        connection = null;
        entry = null;
        factory = null;
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the given namespace is <code>null</code>. Expect
     * NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerNull() throws Exception {
        try {
            new ExpenseEntryManager(null);
            fail("The namespace is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerEmpty() throws Exception {
        try {
            new ExpenseEntryManager("  ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the given namespace does not exist. Expect
     * ConfigurationException.
     * </p>
     */
    public void testExpenseEntryManagerNamespaceNotExist() {
        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The namespace does not exist, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the persistence class name is missing in
     * configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerClassMissing() throws Exception {
        configManager.add("classmissing.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The persistence class name is missing, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the persistence class name is empty string in
     * configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerClassEmpty() throws Exception {
        configManager.add("classempty.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The persistence class name is empty string, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the persistence class cannot be found. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerClassUnknown() throws Exception {
        configManager.add("classunknown.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The persistence class name cannot be found, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the persistence class cannot be instantiated. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerNotClass() throws Exception {
        configManager.add("notclass.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The persistence class name cannot be instantiated, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the persistence class does not have a proper
     * constructor. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerNoProperConstructor()
        throws Exception {
        configManager.add("noconstructor.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail(
                "The persistence class name does not have a proper constructor, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the class is not a persistence class. Expect
     * ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerClassNotPersistence()
        throws Exception {
        configManager.add("notpersistence.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The class is not a persistence class, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the namespace used to create DB connection factory is
     * missing. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerFactoryNamespaceMissing()
        throws Exception {
        configManager.add("DBmissing.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail(
                "The namespace used to create DB connection factory is missing, should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the namespace used to create DB connection factory is
     * empty string. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerFactoryNamespaceEmpty()
        throws Exception {
        configManager.add("DBempty.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The namespace used to create DB connection factory is empty string, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the namespace used to create DB connection factory
     * cannot be found in configuration. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerFactoryNamespaceUnknown()
        throws Exception {
        configManager.add("DBunknown.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The namespace used to create DB connection factory cannot be found, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the configuration for DB connection factory contains
     * error. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerFactoryConfigurationError()
        throws Exception {
        configManager.add("DBerror.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The configuration for DB connection factory contains error, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>ExpenseEntryManager</code> when the producer name cannot be found in DB connection
     * factory. Expect ConfigurationException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerProducerNameUnknown()
        throws Exception {
        configManager.add("producerunknown.xml");

        try {
            new ExpenseEntryManager(NAMESPACE);
            fail("The producer name cannot be found in DB connection factory, "
                + "should throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the given expense entry is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryNull() throws Exception {
        try {
            manager.addEntry(null);
            fail("The expense entry is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
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
    public void testAddEntryCreationDateNotNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setCreationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryModificationDateNotNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryCreationUserNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryModificationUserNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when the description is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryDescriptionNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
            fail("The description is null, should throw InsufficientDataException.");
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
    public void testAddEntryAmountNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryDateNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryTypeNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryTypeInvalid() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Ivern");
        type.setModificationUser("Ivern");

        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
            fail("The ID of expense type is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
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
    public void testAddEntryStatusNull() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);

        try {
            manager.addEntry(entry);
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
    public void testAddEntryStatusInvalid() throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("Description");
        status.setCreationUser("Ivern");
        status.setModificationUser("Ivern");

        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
            fail("The ID of expense status is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.addEntry(entry);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.deleteEntry(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>deleteAllEntryes</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllEntryesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.deleteAllEntries();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the given expense entry is <code>null</code>. Expect NullPointerException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryNull() throws Exception {
        try {
            manager.updateEntry(null);
            fail("The expense entry is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
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
    public void testUpdateEntryCreationUserNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
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
    public void testUpdateEntryModificationUserNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when the description is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryDescriptionNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
            fail("The description is null, should throw InsufficientDataException.");
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
    public void testUpdateEntryAmountNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
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
    public void testUpdateEntryDateNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
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
    public void testUpdateEntryTypeNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
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
    public void testUpdateEntryTypeInvalid() throws Exception {
        type = new ExpenseEntryType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Ivern");
        type.setModificationUser("Ivern");

        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
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
    public void testUpdateEntryStatusNull() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);

        try {
            manager.updateEntry(entry);
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
    public void testUpdateEntryStatusInvalid() throws Exception {
        status = new ExpenseEntryStatus();
        status.setDescription("Description");
        status.setCreationUser("Ivern");
        status.setModificationUser("Ivern");

        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
            fail("The ID of expense status is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        try {
            manager.updateEntry(entry);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveEntry</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryPersistenceError() throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.retrieveEntry(0);
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>retrieveAllEntryes</code> when persistence error occurs. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntryesPersistenceError()
        throws Exception {
        Connection conn = factory.createConnection();

        conn.close();
        manager.getEntryPersistence().setConnection(conn);

        try {
            manager.retrieveAllEntries();
            fail("The persistence error occurs, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryManager</code> when the connection producer name is specified.
     * The constructor of persistence class with two string arguments should be called. The connection producer name
     * and namespace in persistence class should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerAccuracy() throws Exception {
        configManager.add("Valid.xml");
        manager = new ExpenseEntryManager(NAMESPACE);

        ExpenseEntryPersistence persistence = manager.getEntryPersistence();

        assertTrue("The persistence should be ExpenseEntryDbPersistence instance.",
            persistence instanceof ExpenseEntryDbPersistence);

        Field field = persistence.getClass().getDeclaredField("connectionProducerName");

        try {
            field.setAccessible(true);
            assertEquals("The connection producer name should be correct.", "Connection", field.get(persistence));
        } finally {
            field.setAccessible(false);
        }

        field = persistence.getClass().getDeclaredField("namespace");

        try {
            field.setAccessible(true);
            assertEquals("The namespace to create DB connection should be correct.", DB_NAMESPACE,
                field.get(persistence));
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>ExpenseEntryManager</code> when the connection producer name is not
     * specified. The constructor of persistence class with one string arguments should be called. The connection
     * producer name should be <code>null</code>. The namespace in persistence class should be set correctly.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryManagerNoConnectionProducerAccuracy()
        throws Exception {
        configManager.add("ValidProducerMissing.xml");
        manager = new ExpenseEntryManager(NAMESPACE);

        ExpenseEntryPersistence persistence = manager.getEntryPersistence();

        assertTrue("The persistence should be ExpenseEntryDbPersistence instance.",
            persistence instanceof ExpenseEntryDbPersistence);

        Field field = persistence.getClass().getDeclaredField("connectionProducerName");

        try {
            field.setAccessible(true);
            assertNull("The connection producer name should be null.", field.get(persistence));
        } finally {
            field.setAccessible(false);
        }

        field = persistence.getClass().getDeclaredField("namespace");

        try {
            field.setAccessible(true);
            assertEquals("The namespace to create DB connection should be correct.", DB_NAMESPACE,
                field.get(persistence));
        } finally {
            field.setAccessible(false);
        }
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
    public void testAddEntryAccuracy() throws Exception {
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        assertTrue("The entry should be added.", manager.addEntry(entry));

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
            TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.",
                entry.getAmount().doubleValue(), resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            TestHelper.assertEquals("The date should be correct.", TestHelper.createDate(2005, 2, 5),
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
    public void testAddEntryIDSetAccuracy() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        assertTrue("The entry should be added.", manager.addEntry(entry));

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
            TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.",
                entry.getAmount().doubleValue(), resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 1, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            TestHelper.assertEquals("The date should be correct.", TestHelper.createDate(2005, 2, 5),
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
     * Tests accuracy of <code>addEntry</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddEntryExistAccuracy() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        // Add again, should return false.
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        assertFalse("The ID exists in database.", manager.addEntry(entry));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteEntry</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryAccuracy() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", manager.deleteEntry(entry.getId()));

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
    public void testDeleteAllEntryesAccuracy() throws Exception {
        // Add two records
        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        entry = new ExpenseEntry();
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        // Delete
        manager.deleteAllEntries();

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
     * Tests accuracy of <code>deleteEntry</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteEntryIDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", manager.deleteEntry(5));
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
    public void testUpdateEntryAccuracy() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        ExpenseEntry update = new ExpenseEntry(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(200));
        update.setBillable(false);
        update.setDate(TestHelper.createDate(2005, 3, 5));
        update.setExpenseType(type);
        update.setStatus(status);

        assertTrue("The record should be updated.", manager.updateEntry(update));

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
            TestHelper.assertEquals("The creation date should not be modified.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.",
                    update.getAmount().doubleValue(), resultSet.getDouble("amount"), 1E-9);
            assertEquals("The billable flag should be correct.", 0, resultSet.getShort("billable"));
            assertEquals("The expense type ID should be correct.", 1, resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", 2, resultSet.getInt("expense_status_id"));
            TestHelper.assertEquals("The date should be correct.", TestHelper.createDate(2005, 3, 5),
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
     * Tests accuracy of <code>updateEntry</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateEntryIDNotExistAccuracy() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100.12));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        assertFalse("The record should not be updated.", manager.updateEntry(entry));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntry</code> when ID does not exist in database. <code>null</code> should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryIDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", manager.retrieveEntry(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveEntry</code> when ID exists in database. The correct instance should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryAccuracy() throws Exception {
        entry = new ExpenseEntry(5);
        entry.setCompanyId(1);
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
        entry.setAmount(new BigDecimal(100));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 2, 5));
        entry.setExpenseType(type);
        entry.setStatus(status);

        manager.addEntry(entry);

        ExpenseEntry retrieved = manager.retrieveEntry(5);

        TestHelper.assertEquals(entry, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllEntryes</code> when no record exists in database. An empty list should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntryesNoRecordAccuracy()
        throws Exception {
        assertTrue("The retrieved list should be empty.", manager.retrieveAllEntries().isEmpty());
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllEntryes</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntryesAccuracy() throws Exception {
        List expected = new ArrayList();

        // Add 5 instances
        for (int i = 0; i < 5; ++i) {
            entry = new ExpenseEntry(i);
            entry.setCompanyId(1);
            entry.setDescription("Description" + i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Modify" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(TestHelper.createDate(2005, 2, i));
            entry.setExpenseType(type);
            entry.setStatus(status);

            manager.addEntry(entry);
            expected.add(entry);
        }

        List actual = manager.retrieveAllEntries();

        // Verify number
        assertEquals("The number of items in list should be correct.", expected.size(), actual.size());

        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < expected.size(); ++i) {
            TestHelper.assertEquals((ExpenseEntry) expected.get(i), (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getEntryPersistence</code>. The persistence instance should be correct.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetEntryPersistenceAccuracy() throws Exception {
        Field field = manager.getClass().getDeclaredField("entryPersistence");

        try {
            field.setAccessible(true);
            assertSame("The persistence instance should be correct.", field.get(manager),
                manager.getEntryPersistence());
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryManagerTestCase.class);
    }
}






