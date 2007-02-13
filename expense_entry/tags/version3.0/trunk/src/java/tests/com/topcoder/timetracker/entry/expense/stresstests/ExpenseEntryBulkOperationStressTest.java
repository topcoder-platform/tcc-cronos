/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.topcoder.timetracker.entry.expense.search.FieldLikeCriteria;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Date;


/**
 * Stress testing for bulk operation and search functionality of ExpenseEntry component.
 *
 * @author brain_cn
 * @author kr00tki
 * @version 2.0
 * @since 1.0
 */
public class ExpenseEntryBulkOperationStressTest extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents stress test number. */
    private static final int NUMBER = 50;

    /** Represents current time. */
    private static long currentTime = -1;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** Represents the ExpenseEntryDbPersistence instance. */
    private ExpenseEntryDbPersistence persistence = null;

    /** Represents the test ExpenseEntry type. */
    private ExpenseEntryType TEST_ETNRY_TYPE = new ExpenseEntryType(1);

    /** Represents the test ExpenseEntry status. */
    private ExpenseEntryStatus TEST_ETNRY_STATUS = new ExpenseEntryStatus(1);

    /**
     * Aggragates all tests in this class.
     *
     * @return all test cases
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryBulkOperationStressTest.class);
    }

    /**
     * <p>
     * Creates a <code>Date</code> instance representing the given year, month and date. The time would be 0:0:0.
     * </p>
     *
     * @param year the year in the instance.
     * @param month the month in the instance.
     * @param date the date in the instance.
     *
     * @return a <code>Date</code> instance representing the year, month and date.
     */
    public static Date createDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, 0, 0, 0);

        return calendar.getTime();
    }

    /**
     * Clean the databse.
     *
     * @throws Exception to JUnit
     */
    private void DeleteAllTables() throws Exception {
        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM comp_exp_type;");
            statement.executeUpdate("DELETE FROM comp_rej_reason;");
            statement.executeUpdate("DELETE FROM exp_reject_reason;");
            statement.executeUpdate("DELETE FROM expense_entry;");
            statement.executeUpdate("DELETE FROM expense_status;");
            statement.executeUpdate("DELETE FROM expense_type;");
            statement.executeUpdate("DELETE FROM reject_reason;");
            statement.executeUpdate("DELETE FROM company;");
        } finally {
            statement.close();
        }
    }

    /**
     * Create the test environment.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
        }

        // Create the manager
        configManager.add("stresstests/stress.xml");

        factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        connection = factory.createConnection();

        // Prepare type/status data
        prepareData();
        persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);
    }

    /**
     * Prepare type, status test data.
     *
     * @throws Exception to JUnir
     */
    private void prepareData() throws Exception {
        DeleteAllTables();

        String typeSql =
            "insert into expense_type (expense_type_id, Description, creation_date, creation_user, " +
            "modification_date, modification_user, active)"
            + " values (1, 'test', today, 'user', today, 'user', 1);";
        Statement stmt = connection.createStatement();
        stmt.execute(typeSql);

        String statusSql =
            "insert into expense_status (expense_status_id, Description, creation_date, creation_user, " +
            "modification_date, modification_user)"
            + " values (1, 'test', today, 'user', today, 'user');";
        stmt.execute(statusSql);

        // add in 2.0
        stmt.execute("insert into company values(10, 'a', 'a', current, 'a', current, 'a')");
        String typeClientSQL = "INSERT INTO comp_exp_type(company_id, expense_type_id, creation_date, " +
                "creation_user, modification_date, modification_user) VALUES " +
                "(10, 1, CURRENT, 'system', CURRENT, 'system')";

        stmt.execute(typeClientSQL);
        stmt.close();
    }

    /**
     * Clean the test environment
     *
     * @throws Exception
     */
    public void tearDown() throws Exception {
        persistence.closeConnection();
        ConfigManager configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
        }

        DeleteAllTables();
        connection.close();
    }

    /**
     * Test of addEntries method.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries() throws Exception {
        start();

        persistence.addEntries(createEntries(NUMBER), false);

        assertEquals("Failed to add all entries", NUMBER, persistence.retrieveAllEntries().size());

        printResult("testAddEntries");
    }

    /**
     * Test of updateEntries method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntries() throws Exception {
        persistence.addEntries(createEntries(NUMBER), false);

        start();
        persistence.updateEntries(createEntries(NUMBER), false);

        assertEquals("Failed to updateEntries", NUMBER, persistence.retrieveAllEntries().size());
        printResult("testUpdateEntries");
    }

    /**
     * Test of deleteEntries method.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntries() throws Exception {
        persistence.addEntries(createEntries(NUMBER), false);

        int[] ids = new int[NUMBER];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = i + 1;
        }

        start();
        persistence.deleteEntries(ids, false);

        assertEquals("Failed to deleteEntries", 0, persistence.retrieveAllEntries().size());
        printResult("testDeleteEntries");
    }

    /**
     * Test of retrieveEntries method.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntries() throws Exception {
        persistence.addEntries(createEntries(NUMBER), false);

        int[] ids = new int[NUMBER];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = i + 1;
        }

        start();

        ExpenseEntry[] entries = persistence.retrieveEntries(ids, false);

        assertEquals("Failed to retrieveEntries", NUMBER, entries.length);
        printResult("testRetrieveEntries");
    }

    /**
     * Test of the fully bulk operations.
     *
     * @throws Exception to JUnit
     */
    public void testFullBulKOperation() throws Exception {
        int count = 10;
        ExpenseEntry[] entries = createEntries(count);
        int[] ids = new int[count];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = i + 1;
        }

        start();

        for (int i = 0; i < NUMBER; i++) {
            persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);

            persistence.addEntries(entries, false);
            persistence.updateEntries(entries, false);
            assertEquals("Failed to search", count,
                persistence.searchEntries(FieldLikeCriteria.getDescriptionContainsCriteria("Description")).length);
            persistence.retrieveEntries(ids, false);
            persistence.deleteEntries(ids, false);
            persistence.closeConnection();
        }

        assertEquals("Failed to execute the fully process", 0, persistence.retrieveAllEntries().size());
        printResult("testFullBulKOperation");
    }

    /**
     * Start to time the test.
     */
    private static void start() {
        currentTime = System.currentTimeMillis();
    }

    /**
     * Create test entry.
     *
     * @param count the number of entry
     *
     * @return the created entry
     */
    private ExpenseEntry[] createEntries(int count) {
        ExpenseEntry[] entries = new ExpenseEntry[count];

        for (int i = 0; i < count; i++) {
            entries[i] = new ExpenseEntry(i + 1);
            entries[i].setCreationDate(new Date());
            entries[i].setModificationDate(new Date());
            entries[i].setDescription("Description" + i);
            entries[i].setCreationUser("Create" + i);
            entries[i].setModificationUser("Modify" + i);
            entries[i].setAmount(new BigDecimal(100.12 + (i * 0.45)));
            entries[i].setBillable(true);
            entries[i].setDate(createDate(2005, 2, 5));
            entries[i].setExpenseType(TEST_ETNRY_TYPE);
            entries[i].setStatus(TEST_ETNRY_STATUS);
            entries[i].setCompanyId(10);
        }

        return entries;
    }

    /**
     * Print the test information.
     *
     * @param name the test name
     */
    private static void printResult(String name) {
        System.out.println("Test " + name + " for " + NUMBER + " times cost: "
            + (System.currentTimeMillis() - currentTime) + "ms");
    }
}
