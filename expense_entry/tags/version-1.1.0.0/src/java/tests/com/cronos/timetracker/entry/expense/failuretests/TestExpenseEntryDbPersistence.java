/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.expense.CommonInfo;
import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryStatus;
import com.cronos.timetracker.entry.expense.ExpenseEntryType;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.cronos.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * FailureTests for ExpenseEntryDbPersistence class.
 * @author qiucx0161
 * @version 1.0
 */
public class TestExpenseEntryDbPersistence extends TestCase {
    
    /** DBConnectionFactory config namespace. */
    protected static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
    
    /** Producer name. Points to valid database. */
    protected static final String PRODUCER_NAME = "Connection";

    /** Producer name. Points to empty database. */
    protected static final String EMPTY_DATABASE = "empty";
    
    /** DBConnectionFactory config file. */
    private static final String DB_FACTORY_FILE = "test_files/failuretests/Database.xml";
    
    /** ExpenseEntryDbPersistence instance to test on. */
    private ExpenseEntryDbPersistence persistence = null;
    
    /** ExpenseEntryDbPersistence instance to test on. */
    private ExpenseEntryDbPersistence emptyPersistence = null;
    
    /** Configuration Manager instance. */
    protected ConfigManager config = null;
    
    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        clearConfigManager();
        config = ConfigManager.getInstance();
        config.add(new File(DB_FACTORY_FILE).getAbsolutePath());
        
        persistence = new ExpenseEntryDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyPersistence = new ExpenseEntryDbPersistence(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
    }

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearConfigManager();
        persistence.closeConnection();
        emptyPersistence.closeConnection();
    }

    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with null Entries
     * IllegalArgumentException should be thrown.
     */
    public void testAddEntriesNullEntries() {
        try {
            persistence.addEntries(null, true);
            fail("testAddEntriesNullEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesNullEntries is failure.");
        }
    }
    
    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with empty Entries
     * IllegalArgumentException should be thrown.
     */
    public void testAddEntriesEmptyEntries() {
        try {
            persistence.addEntries(new ExpenseEntry[]{}, true);
            fail("testAddEntriesEmptyEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesEmptyEntries is failure.");
        }
    }
    
    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with null element in Entries
     * IllegalArgumentException should be thrown.
     */
    public void testAddEntriesNullElementInEntries() {
        try {
            persistence.addEntries(new ExpenseEntry[]{null}, true);
            fail("testAddEntriesNullElementInEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesNullElementInEntries is failure.");
        }
    }
    
    /**
     * Tests addEntries method failure. PersistenceException should be thrown.
     */
    public void testAddEntrysEmptyDB() {
       try {
           emptyPersistence.addEntries(new ExpenseEntry[]{getExpenseEntry()}, true);
           fail("PersistenceException expected.");
       } catch (PersistenceException ex) {
           // ok
       } catch (Exception e) {
           fail("testAddEntrysEmptyDB is failure.");
       }
   }

    /**
     * Tests deleteEntries(int[] entryIds, boolean isAtomic) method with null EntryIds
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteEntriesNullEntryIds() {
        try {
            persistence.deleteEntries(null, true);
            fail("testDeleteEntriesNullEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testDeleteEntriesNullEntryIds is failure.");
        }
    }
    
    /**
     * Tests deleteEntries(int[] entryIds, boolean isAtomic) method with empty EntryIds
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteEntriesEmptyEntryIds() {
        try {
            persistence.deleteEntries(new int[]{}, true);
            fail("testDeleteEntriesEmptyEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testDeleteEntriesEmptyEntryIds is failure.");
        }
    }

    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) method with null Entries
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesNullEntries() {
        try {
            persistence.updateEntries(null, true);
            fail("testUpdateEntriesNullEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesNullEntries is failure.");
        }
    }
    
    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) method with empty Entries
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesEmptyEntries() {
        try {
            persistence.updateEntries(new ExpenseEntry[]{}, true);
            fail("testUpdateEntriesEmptyEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesEmptyEntries is failure.");
        }
    }
    
    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) method with null element in Entries
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesNullElementInEntries() {
        try {
            persistence.updateEntries(new ExpenseEntry[]{null}, true);
            fail("testUpdateEntriesNullElementInEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesNullElementInEntries is failure.");
        }
    }


    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) with failure state.
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesInvalid() {
        try {
            persistence.updateEntries(new ExpenseEntry[]{}, true);
            fail("testUpdateEntriesInvalid is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesInvalid is failure.");
        }
    }

    /**
     * Tests retrieveEntries(int[] entryIds, boolean isAtomic) method with null EntryIds
     * IllegalArgumentException should be thrown.
     */
    public void testRetrieveEntriesNullEntryIds() {
        try {
            persistence.retrieveEntries(null, true);
            fail("testRetrieveEntriesNullEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testRetrieveEntriesNullEntryIds is failure.");
        }
    }
    
    /**
     * Tests retrieveEntries(int[] entryIds, boolean isAtomic) method with empty EntryIds
     * IllegalArgumentException should be thrown.
     */
    public void testRetrieveEntriesEmptyEntryIds() {
        try {
            persistence.retrieveEntries(new int[]{}, true);
            fail("testRetrieveEntriesEmptyEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testRetrieveEntriesEmptyEntryIds is failure.");
        }
    }

    /**
     * Tests searchEntries(Criteria criteria) method with null Criteria
     * IllegalArgumentException should be thrown.
     */
    public void testSearchEntriesNullCriteria() {
        try {
            persistence.searchEntries(null);
            fail("testSearchEntriesNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testSearchEntriesNullCriteria is failure.");
        }
    }
    
    /**
     * Removes all namespaces from Configuration Manager.
     * 
     * @throws Exception to JUnit.
     */
    private void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }
    
    /**
     * Helper method. Creates <c>ExpenseEntryType</c> filled with data.
     * 
     * @return <c>ExpenseEntryType</c> instance.
     */
    private ExpenseEntryType getExpenseEntryType() {
        ExpenseEntryType type = new ExpenseEntryType(2);
        setCommonInfo(type);
        return type;
    }
    
    /**
     * Helper method. Fills <c>CommonInfo</c> with default data.
     * 
     * @param type object to fill.
     */
    private void setCommonInfo(CommonInfo type) {
        type.setCreationDate(new Date());
        type.setCreationUser("tcs");
        type.setModificationDate(new Date());
        type.setModificationUser("tcs");
    }
    
    /**
     * Helper method. Creates <c>ExpenseEntryStatus</c> filled with data.
     * 
     * @return <c>ExpenseEntryStatus</c> instance.
     */
    private ExpenseEntryStatus getExpenseEntryStatus() {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        setCommonInfo(status);
        return status;
    }
    
    
    /**
     * Helper method. Creates <c>ExpenseEntry</c> filled with data.
     * 
     * @return <c>ExpenseEntry</c> instance.
     */
    private ExpenseEntry getExpenseEntry() {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        setCommonInfo(entry);
        
        return entry;
    }

}
