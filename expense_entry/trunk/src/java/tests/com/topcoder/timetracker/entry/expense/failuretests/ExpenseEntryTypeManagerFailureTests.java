/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.util.Date;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.ExpenseEntryTypeManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;

/**
 * Failure tests for <c>ExpenseEntryTypeManager</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryTypeManagerFailureTests extends FailureTestBase {

    /** Failure config namespace. */
    private static final String NAMESPACE = ExpenseEntryTypeManagerFailureTests.class.getName();

    /** Failre config file. */
    private static final String CONFIG_FILE = "failuretests/type_manager_failure_conf.xml";
    
    /** ExpenseEntryTypeManager instance to test on. */
    private ExpenseEntryTypeManager manager = null;
    
    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        config.add(CONFIG_FILE);
        manager = new ExpenseEntryTypeManager(NAMESPACE);
    }

    
    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testExpenseEntryTypeManager() throws Exception {
        try {
            new ExpenseEntryTypeManager(null);
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
    public void testExpenseEntryTypeManagerEmpty() throws Exception {
        try {
            new ExpenseEntryTypeManager(" ");
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
    /**
     * Tests constructor config failure.
     * 
     * missing entry_persistence_class property
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig1() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".1");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }
    
    /**
     * Tests constructor config failure.
     * 
     * property entry_persistence_class has empty value
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig2() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".2");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests constructor config failure.
     * 
     * entry_persistence_class value in not ExpenseEntryDbPersistence class
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig3() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".3");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests constructor config failure.
     * 
     * entry_persistence_class missing (String, String) ctor
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig4() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".4");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests constructor config failure.
     * 
     * missing connection_factory_namespace property
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig5() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".5");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests constructor config failure.
     * 
     * invalid DBConnectionFactory namespace
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig6() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".6");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests constructor config failure.
     * 
     * connection_factory_namespace empty value
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig7() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".7");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests constructor config failure.
     * 
     * connection_producer_name: non existing producer
     * 
     */
    public void testExpenseEntryTypeManagerFailureConfig8() {
        try {
            new ExpenseEntryTypeManager(NAMESPACE + ".8");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeNull() throws Exception {
        try {
            manager.addType(null);
            fail("Null object, NPE expected");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeNullCreationUser() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        // type.setCreationUser("kr00tki");
        type.setDescription("description");
        type.setModificationUser("kr00tki");
        
        try {
            manager.addType(type);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeNullDescription() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        type.setCreationUser("kr00tki");
        //type.setDescription("description");
        type.setModificationUser("kr00tki");
        
        try {
            manager.addType(type);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeNullModificationUser() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        type.setCreationUser("kr00tki");
        type.setDescription("description");
        //type.setModificationUser("kr00tki");
        
        try {
            manager.addType(type);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeNotNullModificationDate() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        type.setCreationUser("kr00tki");
        type.setDescription("description");
        type.setModificationUser("kr00tki");
        type.setModificationDate(new Date());
        
        try {
            manager.addType(type);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddTypeNotNullCreationDate() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        type.setCreationUser("kr00tki");
        type.setDescription("description");
        type.setModificationUser("kr00tki");
        type.setCreationDate(new Date());
        
        try {
            manager.addType(type);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
    

    /**
     * Tests <c>updateType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateTypeNull() throws Exception {
        try {
            manager.updateType(null);
            fail("Null object, NPE expected");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateTypeNullCreationUser() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        // type.setCreationUser("kr00tki");
        type.setDescription("description");
        type.setModificationUser("kr00tki");
        
        try {
            manager.updateType(type);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateTypeNullDescription() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        type.setCreationUser("kr00tki");
        //type.setDescription("description");
        type.setModificationUser("kr00tki");
        
        try {
            manager.updateType(type);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateType</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateTypeNullModificationUser() throws Exception {
        ExpenseEntryType type = new ExpenseEntryType(1);
        type.setCreationUser("kr00tki");
        type.setDescription("description");
        //type.setModificationUser("kr00tki");
        
        try {
            manager.updateType(type);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
}
