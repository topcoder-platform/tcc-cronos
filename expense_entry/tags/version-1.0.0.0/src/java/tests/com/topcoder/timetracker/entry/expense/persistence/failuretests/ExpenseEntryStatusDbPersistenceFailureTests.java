/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.persistence.failuretests;

import com.topcoder.timetracker.entry.expense.failuretests.FailureTestBase;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * Failure tests for <c>ExpenseEntryStatusDbPersistence</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryStatusDbPersistenceFailureTests extends FailureTestBase {
    /** ExpenseEntryStatusDbPersistence instance to test on. */
    private ExpenseEntryStatusDbPersistence persistence = null;
    /** ExpenseEntryStatusDbPersistence instance to test on. */
    private ExpenseEntryStatusDbPersistence emptyPersistence = null;

    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new ExpenseEntryStatusDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyPersistence = new ExpenseEntryStatusDbPersistence(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
    }

    /**
     * Clears after test.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        persistence.closeConnection();
        emptyPersistence.closeConnection();
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringNull() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringEmpty() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(" ");
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringStringNullProducer() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(null, DB_FACTORY_NAMESPACE);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringStringNullNs() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(PRODUCER_NAME, null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringStringEmptyProducer() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(" ", DB_FACTORY_NAMESPACE);
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringStringEmptyNamespace() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(PRODUCER_NAME, " ");
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringStringEmptyInvalidProducerName() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence("x", DB_FACTORY_NAMESPACE);
            fail("Empty string, PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testConstructorStringStringInvalidDbNamespace() throws Exception {
        try {
            new ExpenseEntryStatusDbPersistence(PRODUCER_NAME, "x");
            fail("Empty string, PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusNull() throws Exception {
        try {
            persistence.addStatus(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusEmptyDB() throws Exception {
        try {
            emptyPersistence.addStatus(getExpenseEntryStatus());
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>deleteStatus</c> method failure.
     * 
     */
    public void testDeleteStatusEmptyDB() {
        try {
            emptyPersistence.deleteStatus(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>deleteAllStatuses</c> method failure.
     * 
     */
    public void testDeleteAllStatuses() {
        try {
            emptyPersistence.deleteAllStatuses();
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception
     * 
     */
    public void testUpdateStatusEmptyDB() throws Exception {
        try {
            emptyPersistence.updateStatus(getExpenseEntryStatus());
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception
     * 
     */
    public void testUpdateStatusNull() throws Exception {
        try {
            persistence.updateStatus(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception
     * 
     */
    public void testRetrieveStatusEmptyDB() throws Exception {
        try {
            emptyPersistence.updateStatus(getExpenseEntryStatus());
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>retrieveAllStatuses</c> method failure.
     * 
     */
    public void testRetrieveAllStatuses() {
        try {
            emptyPersistence.retrieveAllStatuses();
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>setConnection</c> method failure.
     * 
     * @throws Exception to JUnit
     * 
     */
    public void testSetConnection() throws Exception {
        try {
            persistence.setConnection(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
}
