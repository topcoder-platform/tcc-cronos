/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatusManager;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;

import java.util.Date;


/**
 * <p>
 * This class contains the accuracy unit tests for ExpenseEntryStatusDbPersistence.java
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ExpenseEntryStatusDbPersistenceTest extends TestCase {
    /** Represents ExpenseEntryStatusPersistence instance for testing. */
    private ExpenseEntryStatusPersistence persistence = null;

    /** Represents ExpenseEntryStatus instance for testing. */
    private ExpenseEntryStatus status = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryStatusDbPersistenceTest.class);
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
        persistence = new ExpenseEntryStatusManager(TestHelper.NAMESPACE).getStatusPersistence();
        status = new ExpenseEntryStatus(1);
        status.setCreationDate(new Date());
        status.setModificationDate(new Date());
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
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
    public void testExpenseEntryStatusDbPersistenceAccuracy()
        throws Exception {
        persistence = new ExpenseEntryStatusDbPersistence(TestHelper.DB_NAMESPACE);
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
