/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Demostrates the usage of this component. The persistence classes should not be used directly by user, and thus are
 * not demostrated.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class DemoTestCase extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. Data tables are truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        TestHelper.clearConfiguration();

        configManager.add("Valid.xml");
        configManager.add("Database.xml");
        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        TestHelper.clearDatabase(connection);
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. Data tables are truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearDatabase(connection);

        connection.close();
        factory = null;
        connection = null;
    }

    /**
     * <p>
     * Demostrates manipulations on expense entry statuses and persistence.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoStatus() throws Exception {
        // Create a manager for expense entry status from configuration
        ExpenseEntryStatusManager manager = new ExpenseEntryStatusManager(NAMESPACE);

        // Get the underlying persistence
        ExpenseEntryStatusPersistence persistence = manager.getStatusPersistence();

        // Set a database connection explicitly.
        persistence.setConnection(factory.createConnection());

        // Initialize a database connection from DB connection factory.
        persistence.initConnection("Connection");

        // Get the connection
        Connection conn = persistence.getConnection();

        // Create an expense status with ID
        ExpenseEntryStatus status = new ExpenseEntryStatus(5);

        // Create an expense status without ID. The ID will be generated when adding it to the persistence.
        status = new ExpenseEntryStatus();

        // Set fields
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Create");

        // Add the expense entry status to persistence.
        boolean success = manager.addStatus(status);

        assertTrue("The instance should be added to the persistence.", success);

        // Change fields
        status.setModificationUser("Modify");
        status.setDescription("Changed");

        // Update the expense entry status to persistence
        success = manager.updateStatus(status);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry status from persistence
        ExpenseEntryStatus retrieved = manager.retrieveStatus(status.getId());

        // Get properties
        int id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();

        TestHelper.assertEquals(status, retrieved);

        // Retrieve a list of all expense entry statuses from persistence
        List list = manager.retrieveAllStatuses();

        assertEquals("One expense entry status should be retrieved.", 1, list.size());

        // Delete one expense entry status from persistence
        success = manager.deleteStatus(status.getId());

        assertTrue("The expense entry status should be deleted.", success);

        // Delete all expense entry statuses
        manager.deleteAllStatuses();

        // Close the connection in persistence
        persistence.closeConnection();
    }

    /**
     * <p>
     * Demostrates manipulations on expense entry types and persistence.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoType() throws Exception {
        // Create a manager for expense entry type from configuration
        ExpenseEntryTypeManager manager = new ExpenseEntryTypeManager(NAMESPACE);

        // Get the underlying persistence
        ExpenseEntryTypePersistence persistence = manager.getTypePersistence();

        // Set a database connection explicitly.
        persistence.setConnection(factory.createConnection());

        // Initialize a database connection from DB connection factory.
        persistence.initConnection("Connection");

        // Get the connection
        Connection conn = persistence.getConnection();

        // Create an expense type with ID
        ExpenseEntryType type = new ExpenseEntryType(4);

        // Create an expense type without ID. The ID will be generated when adding it to the persistence.
        type = new ExpenseEntryType();

        // Set fields
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Create");

        // Add the expense entry entry to persistence.
        boolean success = manager.addType(type);

        assertTrue("The instance should be added to the persistence.", success);

        // Change fields
        type.setModificationUser("Modify");
        type.setDescription("Changed");

        // Update the expense entry type to persistence
        success = manager.updateType(type);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry type from persistence
        ExpenseEntryType retrieved = manager.retrieveType(type.getId());

        // Get properties
        int id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();

        TestHelper.assertEquals(type, retrieved);

        // Retrieve a list of all expense entry types from persistence
        List list = manager.retrieveAllTypes();

        assertEquals("One expense entry type should be retrieved.", 1, list.size());

        // Delete one expense entry type from persistence
        success = manager.deleteType(type.getId());

        assertTrue("The expense entry type should be deleted.", success);

        // Delete all expense entry types
        manager.deleteAllTypes();

        // Close the connection in persistence
        persistence.closeConnection();
    }

    /**
     * <p>
     * Demostrates manipulations on expense entries and persistence.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDemoEntry() throws Exception {
        // Add one status and one type into database
        ExpenseEntryStatusManager statusManager = new ExpenseEntryStatusManager(NAMESPACE);
        ExpenseEntryTypeManager typeManager = new ExpenseEntryTypeManager(NAMESPACE);
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        ExpenseEntryType type = new ExpenseEntryType(2);

        status.setCreationUser("Create");
        status.setDescription("Status");
        status.setModificationUser("Create");
        type.setCreationUser("Create");
        type.setDescription("Type");
        type.setModificationUser("Create");

        statusManager.addStatus(status);
        typeManager.addType(type);

        // Close the connection for status manager and type manager
        statusManager.getStatusPersistence().closeConnection();
        typeManager.getTypePersistence().closeConnection();

        // Create a manager for expense entry from configuration
        ExpenseEntryManager manager = new ExpenseEntryManager(NAMESPACE);

        // Get the underlying persistence
        ExpenseEntryPersistence persistence = manager.getEntryPersistence();

        // Set a database connection explicitly.
        persistence.setConnection(factory.createConnection());

        // Initialize a database connection from DB connection factory.
        persistence.initConnection("Connection");

        // Get the connection
        Connection conn = persistence.getConnection();

        // Create an expense entry with ID
        ExpenseEntry entry = new ExpenseEntry(5);

        // Create an expense entry without ID. The ID will be generated when adding it to the persistence.
        entry = new ExpenseEntry();

        // Set fields
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Create");
        entry.setAmount(new BigDecimal(100.00));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2000, 1, 2));
        entry.setExpenseType(type);
        entry.setStatus(status);

        // Add the expense entry entry to persistence.
        boolean success = manager.addEntry(entry);

        assertTrue("The instance should be added to the persistence.", success);

        // Change fields
        entry.setModificationUser("Modify");
        entry.setDescription("Changed");
        entry.setAmount(new BigDecimal(10000.00));
        entry.setBillable(false);
        entry.setDate(TestHelper.createDate(2005, 2, 1));

        // Update the expense entry to persistence
        success = manager.updateEntry(entry);

        assertTrue("The instance should be updated.", success);

        // Get the expense entry from persistence. The
        ExpenseEntry retrieved = manager.retrieveEntry(entry.getId());

        // Get properties
        int id = retrieved.getId();
        String description = retrieved.getDescription();
        String creationUser = retrieved.getCreationUser();
        String modificationUser = retrieved.getModificationUser();
        Date creationDate = retrieved.getCreationDate();
        Date modificationDate = retrieved.getModificationDate();
        Date date = retrieved.getDate();
        BigDecimal amount = retrieved.getAmount();
        boolean billable = retrieved.isBillable();

        TestHelper.assertEquals(entry, retrieved);

        // Retrieve a list of all expense entries from persistence
        List list = manager.retrieveAllEntries();

        assertEquals("One expense entry should be retrieved.", 1, list.size());

        // Delete one expense entry from persistence
        success = manager.deleteEntry(entry.getId());

        assertTrue("The expense entry should be deleted.", success);

        // Delete all expense entries
        manager.deleteAllEntries();

        // Close the connection in persistence
        persistence.closeConnection();
    }

    /**
     * <p>
     * Aggragates all tests in this class.
     * </p>
     *
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTestCase.class);
    }
}






