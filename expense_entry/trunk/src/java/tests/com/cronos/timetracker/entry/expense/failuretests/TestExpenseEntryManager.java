/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import com.cronos.timetracker.entry.expense.CommonInfo;
import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryManager;
import com.cronos.timetracker.entry.expense.ExpenseEntryStatus;
import com.cronos.timetracker.entry.expense.ExpenseEntryType;
import com.cronos.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;
/**
 * FailureTests for ExpenseEntryManager class.
 * @author qiucx0161
 * @version 1.0
 */
public class TestExpenseEntryManager extends TestCase {
    /** Failure config namespace. */
    private static final String NAMESPACE = "com.cronos.timetracker.entry.expense.failuretests";
    /** Failre config file. */
    private static final String CONFIG_FILE = "test_files/failuretests/configfile.xml";
    
    /** DBConnectionFactory config namespace. */
    protected static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** DBConnectionFactory config file. */
    private static final String DB_FACTORY_FILE = "test_files/failuretests/Database.xml";

    /** Producer name. Points to valid database. */
    protected static final String PRODUCER_NAME = "Connection";

    /** Producer name. Points to empty database. */
    protected static final String EMPTY_DATABASE = "empty";
    
    /** ExpenseEntryManager instance to test on. */
    private ExpenseEntryManager manager = null;
    
    /** Configuration Manager instance. */
    private ConfigManager config = null;
    
    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        clearConfigManager();
        config = ConfigManager.getInstance();
        config.add(new File(DB_FACTORY_FILE).getAbsolutePath());
        config.add(new File(CONFIG_FILE).getAbsolutePath());
        manager = new ExpenseEntryManager(NAMESPACE);
    }

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearConfigManager();
    }

    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with null Entries
     * IllegalArgumentException should be thrown.
     */
    public void testAddEntriesNullEntries() {
        try {
            manager.addEntries(null, true);
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
            manager.addEntries(new ExpenseEntry[]{}, true);
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
            manager.addEntries(new ExpenseEntry[]{null}, true);
            fail("testAddEntriesNullElementInEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesNullElementInEntries is failure.");
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullStatus() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null status, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullType() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null type, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullDate() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null date, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullAmount() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null amount, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullCreationUser() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null creation user, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullModificationUser() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null modification user, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullDescritption() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setModificationUser("tcs");
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("Null description, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryModificationDateNotNull() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        entry.setModificationDate(new Date());
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>addEntries</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryCreationDateNotNull() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("tcs");
        entry.setDescription("description");
        entry.setModificationUser("tcs");
        entry.setCreationDate(new Date());
        
        try {
            manager.addEntries(new ExpenseEntry[]{entry}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }


    /**
     * Tests deleteEntries(int[] entryIds, boolean isAtomic) method with null EntryIds
     * IllegalArgumentException should be thrown.
     */
    public void testDeleteEntriesNullEntryIds() {
        try {
            manager.deleteEntries(null, true);
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
            manager.deleteEntries(new int[]{}, true);
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
            manager.updateEntries(null, true);
            fail("testUpdateEntriesNullEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesNullEntries is failure.");
        }
    }

    /**
     * Tests retrieveEntries(int[] entryIds, boolean isAtomic) method with null EntryIds
     * IllegalArgumentException should be thrown.
     */
    public void testRetrieveEntriesNullEntryIds() {
        try {
            manager.retrieveEntries(null, true);
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
            manager.retrieveEntries(new int[]{}, true);
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
            manager.searchEntries(null);
            fail("testSearchEntriesNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testSearchEntriesNullCriteria is failure.");
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
}
