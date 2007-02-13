/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.util.Date;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatusManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;

/**
 * Failure tests for <c>ExpenseEntryStatusManager</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class ExpenseEntryStatusManagerFailureTests extends FailureTestBase {
    /** Failure config namespace. */
    private static final String NAMESPACE = ExpenseEntryStatusManagerFailureTests.class.getName();
    
    /** Failre config file. */
    private static final String CONFIG_FILE = "failuretests/status_manager_failure_conf.xml";
    
    /** ExpenseEntryStatusManager instance to test on. */
    private ExpenseEntryStatusManager manager = null;

    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        config.add(CONFIG_FILE);
        manager = new ExpenseEntryStatusManager(NAMESPACE);
    }

    
    /**
     * Tests constructor failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testExpenseEntryStatusManager() throws Exception {
        try {
            new ExpenseEntryStatusManager(null);
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
    public void testExpenseEntryStatusManagerEmpty() throws Exception {
        try {
            new ExpenseEntryStatusManager(" ");
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
    public void testExpenseEntryStatusManagerFailureConfig1() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".1");
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
    public void testExpenseEntryStatusManagerFailureConfig2() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".2");
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
    public void testExpenseEntryStatusManagerFailureConfig3() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".3");
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
    public void testExpenseEntryStatusManagerFailureConfig4() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".4");
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
    public void testExpenseEntryStatusManagerFailureConfig5() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".5");
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
    public void testExpenseEntryStatusManagerFailureConfig6() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".6");
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
    public void testExpenseEntryStatusManagerFailureConfig7() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".7");
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
    public void testExpenseEntryStatusManagerFailureConfig8() {
        try {
            new ExpenseEntryStatusManager(NAMESPACE + ".8");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusNull() throws Exception {
        try {
            manager.addStatus(null);
            fail("Null object, NPE expected");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusNullCreationUser() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        //status.setCreationUser("kr00tki");
        status.setDescription("description");
        status.setModificationUser("kr00tki");
        
        try {
            manager.addStatus(status);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusNullDescription() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        status.setCreationUser("kr00tki");
        //status.setDescription("description");
        status.setModificationUser("kr00tki");
        
        try {
            manager.addStatus(status);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusNullModificationUser() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        status.setCreationUser("kr00tki");
        status.setDescription("description");
        //status.setModificationUser("kr00tki");
        
        try {
            manager.addStatus(status);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusCreationDateNotNull() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        status.setCreationUser("kr00tki");
        status.setDescription("description");
        status.setModificationUser("kr00tki");
        status.setCreationDate(new Date());
        
        try {
            manager.addStatus(status);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>addStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testAddStatusModificationDateNotNull() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        status.setCreationUser("kr00tki");
        status.setDescription("description");
        status.setModificationUser("kr00tki");
        status.setModificationDate(new Date());
        
        try {
            manager.addStatus(status);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateStatusNull() throws Exception {
        try {
            manager.updateStatus(null);
            fail("Null object, NPE expected");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateStatusNullCreationUser() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        //status.setCreationUser("kr00tki");
        status.setDescription("description");
        status.setModificationUser("kr00tki");
        
        try {
            manager.updateStatus(status);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateStatusNullDescription() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        status.setCreationUser("kr00tki");
        //status.setDescription("description");
        status.setModificationUser("kr00tki");
        
        try {
            manager.updateStatus(status);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>updateStatus</c> method failure.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateStatusNullModificationUser() throws Exception {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        status.setCreationUser("kr00tki");
        status.setDescription("description");
        //status.setModificationUser("kr00tki");
        
        try {
            manager.updateStatus(status);
            fail("InsufficientDataException expected");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
}
