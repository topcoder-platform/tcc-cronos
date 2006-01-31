/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;

import java.util.Date;


/**
 * <p>
 * This class contains the accuracy unit tests for ExpenseEntryDbPersistence.java
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ExpenseEntryDbPersistenceTest extends TestCase {
    /** Represents ExpenseEntryPersistence instance for testing. */
    private ExpenseEntryPersistence persistence = null;

    /** Represents ExpenseEntry instance for testing. */
    private ExpenseEntry entry = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryDbPersistenceTest.class);
    }

    /**
     * <p>
     * Sets up the fixture.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.addConfig();

        persistence = new ExpenseEntryManager(TestHelper.NAMESPACE).getEntryPersistence();
        entry = new ExpenseEntry(1);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("Description");
        entry.setCreationUser("Create");
        entry.setModificationUser("Modify");
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Connection connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
        TestHelper.clearConfig();
        TestHelper.clearRecords(connection);
        TestHelper.closeResources(null, null, connection);
    }

    /**
     * <p>
     * Tests accuracy of the constructor.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseEntryDbPersistenceAccuracy()
        throws Exception {
        persistence = new ExpenseEntryDbPersistence(TestHelper.DB_NAMESPACE);
        assertNotNull(persistence);
    }

    /**
     * <p>
     * Tests accuracy of the getConnection() method.
     * </p>
     */
    public void testGetConnectionAccuracy() {
        assertNull("By default the connection should be null.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of the setConnection() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetConnectionAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            persistence.setConnection(connection);
            assertSame("The connection should be properly set.", connection, persistence.getConnection());
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the initConnection() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInitConnectionAccuracy() throws Exception {
        persistence.initConnection("Connection");
        assertNotNull("The connection should be initialized.", persistence.getConnection());
    }

    /**
     * <p>
     * Tests accuracy of the initConnection() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCloseConnectionAccuracy() throws Exception {
        persistence.initConnection("Connection");
        persistence.closeConnection();
        assertNull("The connection should be properly closed.", persistence.getConnection());
    }
}
