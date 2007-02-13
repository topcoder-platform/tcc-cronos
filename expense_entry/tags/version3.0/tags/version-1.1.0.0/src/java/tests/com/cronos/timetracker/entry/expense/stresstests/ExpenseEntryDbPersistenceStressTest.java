/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.stresstests;

import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryStatus;
import com.cronos.timetracker.entry.expense.ExpenseEntryType;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryStatusDbPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypeDbPersistence;

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
import java.util.List;


/**
 * Stress test for class ExpenseEntryDbPersistence, ExpenseEntryTypeDbPersistence, ExpenseEntryStatusDbPersistence
 *
 * @author mgmg
 * @version 1.0
 */
public class ExpenseEntryDbPersistenceStressTest extends TestCase {
    /** Represents the namespace to load manager configuration. */
    private static final String NAMESPACE = "com.cronos.timetracker.entry.expense";

    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.cronos.timetracker.entry.expense.connection";

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a DB connection factory to create connections. */
    private DBConnectionFactory factory;

    /** The test count to run. */
    private final int testCount = 20;

    /**
     * Aggragates all tests in this class.
     *
     * @return all test cases
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryDbPersistenceStressTest.class);
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
            statement.executeUpdate("DELETE FROM ExpenseEntries;");
            statement.executeUpdate("DELETE FROM ExpenseTypes;");
            statement.executeUpdate("DELETE FROM ExpenseStatuses;");
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
        DeleteAllTables();
    }

    /**
     * Clean the test environment
     *
     * @throws Exception
     */
    public void tearDown() throws Exception {
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
     * Stress test for class ExpenseEntryDbPersistence
     *
     * @throws Exception to JUnit
     */
    public void testExpenseEntryDbPersistenceStress() throws Exception {
        ExpenseEntryStatus status = null;
        ExpenseEntryType type = null;
        ExpenseEntry entry = null;

        ExpenseEntryDbPersistence persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);
        ExpenseEntryTypeDbPersistence typePersistence = new ExpenseEntryTypeDbPersistence("Connection", DB_NAMESPACE);
        ExpenseEntryStatusDbPersistence statusPersistence =
            new ExpenseEntryStatusDbPersistence("Connection", DB_NAMESPACE);

        status = new ExpenseEntryStatus(23);
        status.setDescription("Pending Approval");
        status.setCreationDate(createDate(2005, 1, 1));
        status.setModificationDate(createDate(2005, 2, 1));
        status.setCreationUser("TangentZ");
        status.setModificationUser("Ivern");

        type = new ExpenseEntryType(45);
        type.setDescription("Travel Expense");
        type.setCreationDate(createDate(2005, 1, 1));
        type.setModificationDate(createDate(2005, 2, 1));
        type.setCreationUser("TangentZ");
        type.setModificationUser("Ivern");

        try {
            typePersistence.addType(type);
            statusPersistence.addStatus(status);

            long start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                entry = new ExpenseEntry(i);
                entry.setCreationDate(new Date());
                entry.setModificationDate(new Date());
                entry.setDescription("Description" + i);
                entry.setCreationUser("Create" + i);
                entry.setModificationUser("Modify" + i);
                entry.setAmount(new BigDecimal(100.12 + (i * 0.45)));
                entry.setBillable(true);
                entry.setDate(createDate(2005, 2, 5));
                entry.setExpenseType(type);
                entry.setStatus(status);

                assertTrue("The entry should be added.", persistence.addEntry(entry));
            }

            System.out.println("addEntry for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                ExpenseEntry returnEntry = persistence.retrieveEntry(i);
                assertNotNull("The entry returned should not be null.", returnEntry);
                assertTrue("The entry returned is wrong", returnEntry.getId() == i);
            }

            System.out.println("retrieveEntry for " + testCount + " times takes " + (new Date().getTime() - start)
                + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                entry = new ExpenseEntry(i);
                entry.setCreationDate(new Date());
                entry.setModificationDate(new Date());
                entry.setDescription("Description1" + i);
                entry.setCreationUser("Create2" + i);
                entry.setModificationUser("Modify3" + i);
                entry.setAmount(new BigDecimal(1004.12 + (i * 0.65)));
                entry.setBillable(true);
                entry.setDate(createDate(2005, 5, 6));
                entry.setExpenseType(type);
                entry.setStatus(status);

                assertTrue("The entry should be updated", persistence.updateEntry(entry));
            }

            System.out.println("updateEntry for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            List entryList = persistence.retrieveAllEntries();
            assertTrue("The list should contains " + testCount + " items", testCount == entryList.size());

            System.out.println("retrieveAllEntries for 1 time takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                assertTrue("The entry returned is wrong", persistence.deleteEntry(i));
            }

            System.out.println("deleteEntry for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");
        } finally {
            persistence.closeConnection();
            typePersistence.closeConnection();
            statusPersistence.closeConnection();
        }
    }

    /**
     * Stress test for class ExpenseEntryTypeDbPersistence.
     *
     * @throws Exception to JUnit
     */
    public void testExpenseEntryTypeDbPersistenceStress()
        throws Exception {
        ExpenseEntryType type = null;
        ExpenseEntryTypeDbPersistence persistence = new ExpenseEntryTypeDbPersistence("Connection", DB_NAMESPACE);

        try {
            long start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                type = new ExpenseEntryType(i);
                type.setCreationDate(new Date());
                type.setModificationDate(new Date());
                type.setDescription("Description" + i);
                type.setCreationUser("Create" + i);
                type.setModificationUser("Modify" + i);

                assertTrue("The type should be added.", persistence.addType(type));
            }

            System.out.println("addType for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                ExpenseEntryType returnType = persistence.retrieveType(i);
                assertNotNull("The type returned should not be null.", returnType);
                assertTrue("The type returned is wrong", returnType.getId() == i);
            }

            System.out.println("retrieveType for " + testCount + " times takes " + (new Date().getTime() - start)
                + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                type = new ExpenseEntryType(i);
                type.setCreationDate(new Date());
                type.setModificationDate(new Date());
                type.setDescription("Description1" + i);
                type.setCreationUser("Create2" + i);
                type.setModificationUser("Modify3" + i);

                assertTrue("The type should be updated", persistence.updateType(type));
            }

            System.out.println("updateType for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            List entryList = persistence.retrieveAllTypes();
            assertTrue("The list should contains " + testCount + " items", testCount == entryList.size());

            System.out.println("retrieveAllTypes for 1 time takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                assertTrue("The type returned is wrong", persistence.deleteType(i));
            }

            System.out.println("deleteType for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");
        } finally {
            persistence.closeConnection();
        }
    }

    /**
     * Stress test for class ExpenseEntryStatusDbPersistence
     *
     * @throws Exception to JUnit
     */
    public void testExpenseEntryStatusDbPersistenceStress()
        throws Exception {
        ExpenseEntryStatus status = null;
        ExpenseEntryStatusDbPersistence persistence = new ExpenseEntryStatusDbPersistence("Connection", DB_NAMESPACE);

        try {
            long start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                status = new ExpenseEntryStatus(i);
                status.setCreationDate(new Date());
                status.setModificationDate(new Date());
                status.setDescription("Description" + i);
                status.setCreationUser("Create" + i);
                status.setModificationUser("Modify" + i);

                assertTrue("The status should be added.", persistence.addStatus(status));
            }

            System.out.println("addStatus for " + testCount + " times takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                ExpenseEntryStatus returnStatus = persistence.retrieveStatus(i);
                assertNotNull("The status returned should not be null.", returnStatus);
                assertTrue("The status returned is wrong", returnStatus.getId() == i);
            }

            System.out.println("retrieveStatus for " + testCount + " times takes " + (new Date().getTime() - start)
                + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                status = new ExpenseEntryStatus(i);
                status.setCreationDate(new Date());
                status.setModificationDate(new Date());
                status.setDescription("Description1" + i);
                status.setCreationUser("Create2" + i);
                status.setModificationUser("Modify3" + i);

                assertTrue("The status should be updated", persistence.updateStatus(status));
            }

            System.out.println("updateStatus for " + testCount + " times takes " + (new Date().getTime() - start)
                + "ms");

            start = new Date().getTime();

            List entryList = persistence.retrieveAllStatuses();
            assertTrue("The list should contains " + testCount + " items", testCount == entryList.size());

            System.out.println("retrieveAllStatuses for 1 time takes " + (new Date().getTime() - start) + "ms");

            start = new Date().getTime();

            for (int i = 1; i <= testCount; i++) {
                assertTrue("The status returned is wrong", persistence.deleteStatus(i));
            }

            System.out.println("deleteStatus for " + testCount + " times takes " + (new Date().getTime() - start)
                + "ms");
        } finally {
            persistence.closeConnection();
        }
    }
}
