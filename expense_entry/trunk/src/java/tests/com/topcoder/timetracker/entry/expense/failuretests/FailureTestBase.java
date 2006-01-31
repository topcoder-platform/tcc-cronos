/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.expense.CommonInfo;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests base class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class FailureTestBase extends TestCase {

    /** DBConnectionFactory config namespace. */
    protected static final String DB_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /** DBConnectionFactory config file. */
    private static final String DB_FACTORY_FILE = "Database.xml";

    /** Producer name. Points to valid database. */
    protected static final String PRODUCER_NAME = "Connection";

    /** Producer name. Points to empty database. */
    protected static final String EMPTY_DATABASE = "empty";

    /** Configuration Manager instance. */
    protected ConfigManager config = null;

    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        clearConfigManager();
        config = ConfigManager.getInstance();
        config.add(DB_FACTORY_NAMESPACE, DB_FACTORY_FILE, ConfigManager.CONFIG_XML_FORMAT);
    }

    /**
     * Clears afer test.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearConfigManager();
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
    protected ExpenseEntryType getExpenseEntryType() {
        ExpenseEntryType type = new ExpenseEntryType(2);
        setCommonInfo(type);
        return type;
    }
    
    /**
     * Helper method. Fills <c>CommonInfo</c> with default data.
     * 
     * @param type object to fill.
     */
    protected void setCommonInfo(CommonInfo type) {
        type.setCreationDate(new Date());
        type.setCreationUser("kr00tki");
        type.setDescription("description");
        type.setModificationDate(new Date());
        type.setModificationUser("kr00tki");
    }
    
    /**
     * Helper method. Creates <c>ExpenseEntryStatus</c> filled with data.
     * 
     * @return <c>ExpenseEntryStatus</c> instance.
     */
    protected ExpenseEntryStatus getExpenseEntryStatus() {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        setCommonInfo(status);
        return status;
    }
    
    /**
     * Helper method. Creates <c>ExpenseEntry</c> filled with data.
     * 
     * @return <c>ExpenseEntry</c> instance.
     */
    protected ExpenseEntry getExpenseEntry() {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        setCommonInfo(entry);
        
        return entry;
    }
    
}
