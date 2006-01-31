/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.persistence.failuretests;

import com.topcoder.timetracker.entry.expense.failuretests.FailureTestBase;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * Failure tests for <c>ExpenseEntryDbPersistence</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryDbPersistenceFailureTests extends FailureTestBase {

    /** ExpenseEntryDbPersistence instance to test on. */
    private ExpenseEntryDbPersistence persistence = null;
    
    /** ExpenseEntryDbPersistence instance to test on. */
    private ExpenseEntryDbPersistence emptyPersistence = null;
    
    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new ExpenseEntryDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyPersistence = new ExpenseEntryDbPersistence(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryDbPersistence(null);
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
            new ExpenseEntryDbPersistence(" ");
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
            new ExpenseEntryDbPersistence(null, DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryDbPersistence(PRODUCER_NAME, null);
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
            new ExpenseEntryDbPersistence(" ", DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryDbPersistence(PRODUCER_NAME, " ");
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
            new ExpenseEntryDbPersistence("x", DB_FACTORY_NAMESPACE);
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
            new ExpenseEntryDbPersistence(PRODUCER_NAME, "x");
            fail("Empty string, PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

     /**
      * Tests <c>addEntry</c> method failure.
      * 
      * @throws Exception to JUnit.
      */
     public void testAddEntryNull() throws Exception {
        try {
            persistence.addEntry(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
     /**
      * Tests <c>addEntry</c> method failure.
      * 
      * @throws Exception to JUnit.
      */
     public void testAddEntryEmptyDB() throws Exception {
        try {
            emptyPersistence.addEntry(getExpenseEntry());
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

     /**
      * Tests <c>deleteEntry</c> method failure.
      * 
      */
     public void testDeleteEntryEmptyDB() {
        try {
            emptyPersistence.deleteEntry(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

     /**
      * Tests <c>deleteAllEntries</c> method failure.
      * 
      */
     public void testDeleteAllEntriesEmptyDB() {
        try {
            emptyPersistence.deleteAllEntries();
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

     /**
      * Tests <c>updateEntry</c> method failure.
      * 
      * @throws Exception to JUnit
      */
     public void testUpdateEntryNull() throws Exception {
        try {
            persistence.updateEntry(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
     /**
      * Tests <c>updateEntry</c> method failure.
      * 
      * @throws Exception to JUnit
      */
     public void testUpdateEntryEmptyDB() throws Exception {
        try {
            emptyPersistence.updateEntry(getExpenseEntry());
            fail("Null value, NPE expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

     /**
      * Tests <c>retrieveEntry</c> method failure.
      * 
      */
     public void testRetrieveEntryEmptyDB() {
        try {
            emptyPersistence.retrieveEntry(1);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

     /**
      * Tests <c>retrieveAllEntries</c> method failure.
      * 
      */
     public void testRetrieveAllEntries() {
        try {
            emptyPersistence.retrieveAllEntries();
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
