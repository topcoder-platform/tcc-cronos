/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.math.BigDecimal;
import java.util.Date;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;

/**
 * Failure tests for <c>ExpenseEntryManager</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryManagerFailureTests extends FailureTestBase {

    /** Failure config namespace. */
    private static final String NAMESPACE = ExpenseEntryManagerFailureTests.class.getName();
    /** Failre config file. */
    private static final String CONFIG_FILE = "failuretests/entry_manager_failure_conf.xml";
    
    /** ExpenseEntryManager instance to test on. */
    private ExpenseEntryManager manager = null;
    
    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        config.add(CONFIG_FILE);
        manager = new ExpenseEntryManager(NAMESPACE);
    }

    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testExpenseEntryManagerNull() throws Exception {
        try {
            new ExpenseEntryManager(null);
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
    public void testExpenseEntryManagerEmpty() throws Exception {
        try {
            new ExpenseEntryManager(" ");
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
    /**
     * Tests constructor config failure.
     * 
     * Missing entry_persistence_class property.
     *
     */
    public void testExpenseEntryManagerFailureConfig1() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".1");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    /**
     * Tests constructor config failure.
     * 
     * Property entry_persistence_class has empty value.
     *
     */
    public void testExpenseEntryManagerFailureConfig2() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".2");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    /**
     * Tests constructor config failure.
     * 
     * entry_persistence_class value in not ExpenseEntryDbPersistence class.
     *
     */
    public void testExpenseEntryManagerFailureConfig3() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".3");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    
    /**
     * Tests constructor config failure.
     * 
     * entry_persistence_class missing (String, String) ctor.
     */
    public void testExpenseEntryManagerFailureConfig4() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".4");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    
    /**
     * Tests constructor config failure.
     * 
     * Missing connection_factory_namespace property.
     *
     */
    public void testExpenseEntryManagerFailureConfig5() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".5");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    
    /**
     * Tests constructor config failure.
     * 
     * Invalid DBConnectionFactory namespace.
     *
     */
    public void testExpenseEntryManagerFailureConfig6() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".6");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    
    /**
     * Tests constructor config failure.
     * 
     * connection_factory_namespace empty value.
     *
     */
    public void testExpenseEntryManagerFailureConfig7() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".7");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    /**
     * Tests constructor config failure.
     * 
     * connection_producer_name: non existing producer.
     */
    public void testExpenseEntryManagerFailureConfig8() {
        try {
            new ExpenseEntryManager(NAMESPACE + ".8");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
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
            manager.addEntry(null);
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
    public void testAddEntryNullStatus() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        
        
        try {
            manager.addEntry(entry);
            fail("Null status, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullType() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        
        try {
            manager.addEntry(entry);
            fail("Null type, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullDate() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        
        try {
            manager.addEntry(entry);
            fail("Null date, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullAmount() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        
        try {
            manager.addEntry(entry);
            fail("Null amount, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
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
        entry.setModificationUser("kr00tki");
        
        try {
            manager.addEntry(entry);
            fail("Null creation user, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullModificationUser() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        
        try {
            manager.addEntry(entry);
            fail("Null modification user, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryNullDescritption() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setModificationUser("kr00tki");
        
        try {
            manager.addEntry(entry);
            fail("Null description, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryModificationDateNotNull() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        
        try {
            manager.addEntry(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>addEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddEntryCreationDateNotNull() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setCreationDate(new Date());
        
        try {
            manager.addEntry(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntry() throws Exception {
        try {
            manager.updateEntry(null);
            fail("Null value, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullStatus() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("Null status, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullType() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("Null type, InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullDate() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullAmount() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullCreationUser() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setDescription("description");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullModificationUser() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setDescription("description");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateEntry</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryNullDescritption() throws Exception {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        entry.setCreationUser("kr00tki");
        entry.setModificationUser("kr00tki");
        entry.setModificationDate(new Date());
        entry.setCreationDate(new Date());
        
        try {
            manager.updateEntry(entry);
            fail("InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
}
