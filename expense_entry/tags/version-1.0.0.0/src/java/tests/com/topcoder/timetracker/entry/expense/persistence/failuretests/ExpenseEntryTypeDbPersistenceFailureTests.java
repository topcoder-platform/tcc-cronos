/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.persistence.failuretests;

import com.topcoder.timetracker.entry.expense.failuretests.FailureTestBase;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypeDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * Failure tests for <c>ExpenseEntryTypeDbPersistence</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryTypeDbPersistenceFailureTests extends FailureTestBase {
    /** ExpenseEntryTypeDbPersistence instance to test on. */
    private ExpenseEntryTypeDbPersistence persistence = null;
    
    /** ExpenseEntryTypeDbPersistence instance to test on. */
    private ExpenseEntryTypeDbPersistence emptyPersistence = null;

    
    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new ExpenseEntryTypeDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyPersistence = new ExpenseEntryTypeDbPersistence(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryTypeDbPersistence(null);
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
            new ExpenseEntryTypeDbPersistence(" ");
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
            new ExpenseEntryTypeDbPersistence(null, DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryTypeDbPersistence(PRODUCER_NAME, null);
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
            new ExpenseEntryTypeDbPersistence(" ", DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryTypeDbPersistence(PRODUCER_NAME, " ");
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
            new ExpenseEntryTypeDbPersistence("x", DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryTypeDbPersistence(PRODUCER_NAME, "x");
            fail("Empty string, PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddType() throws Exception {
        try {
            persistence.addType(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeEmptyDB() throws Exception {
        try {
            emptyPersistence.addType(getExpenseEntryType());
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>deleteType</c> method failure.
     * 
     */
    public void testDeleteTypeEmptyDB() {
        try {
            emptyPersistence.deleteType(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>deleteAllTypes</c> method failure.
     * 
     */
    public void testDeleteAllTypes() {
        try {
            emptyPersistence.deleteAllTypes();
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateType</c> method failure.
     * 
     * @throws Exception
     * 
     */
    public void testUpdateTypeNull() throws Exception {
        try {
            persistence.updateType(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateType</c> method failure.
     * 
     * @throws Exception
     * 
     */
    public void testUpdateStatusEmptyDB() throws Exception {
        try {
            emptyPersistence.updateType(getExpenseEntryType());
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>retrieveType</c> method failure.
     * 
     * 
     */
    public void testRetrieveTypeEmptyDB() {
        try {
            emptyPersistence.retrieveType(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests <c>retrieveAllTypes</c> method failure.
     * 
     * 
     */
    public void testRetrieveAllTypesEmptyDB() {
        try {
            emptyPersistence.retrieveAllTypes();
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
