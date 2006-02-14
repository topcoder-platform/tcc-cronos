/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.failuretests;

import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.time.BaseDataObject;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.util.config.ConfigManager;

/**
 * 
 * 
 * @author kr00tki
 * @version 1.0
 */
public class FailureTestBase extends TestCase {

    /** DBConnectionFactory config namespace. */
    protected static final String DB_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /** DBConnectionFactory config file. */
    private static final String DB_FACTORY_FILE = "failuretests/db_factory.xml";

    /** Producer name. Points to valid database. */
    protected static final String PRODUCER_NAME = "informix";

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
     * Helper method. Creates <c>TaskType</c> filled with data.
     * 
     * @return <c>TaskType</c> instance.
     */
    protected TaskType getTaskType() {
        TaskType type = new TaskType();
        setBaseDataObject(type);
        return type;
    }

    /**
     * Helper method. Fills <c>BaseDataObject</c> with default data.
     * 
     * @param base object to fill.
     */
    protected void setBaseDataObject(BaseDataObject base) {
        base.setCreationDate(new Date());
        base.setCreationUser("kr00tki");
        base.setDescription("description");
        base.setModificationDate(new Date());
        base.setModificationUser("kr00tki");

    }

    /**
     * Helper method. Creates <c>TimeStatus</c> filled with data.
     * 
     * @return <c>TimeStatus</c> instance.
     */
    protected TimeStatus getTimeStatus() {
        TimeStatus status = new TimeStatus();
        setBaseDataObject(status);
        return status;
    }

    /**
     * Helper method. Creates <c>TimeEntry</c> filled with data.
     * 
     * @return <c>TimeEntry</c> instance.
     */
    protected TimeEntry getTimeEntry() {
        TimeEntry entry = new TimeEntry();
        entry.setTimeStatusId(1);
        entry.setTaskTypeId(1);
        entry.setDate(new Date());
        entry.setHours(1.0f);
        setBaseDataObject(entry);

        return entry;
    }
}
