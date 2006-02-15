/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DAO;
import com.topcoder.timetracker.entry.time.DAOFactory;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * Test the <code>DAOFactory</code> class.
 * @author fuyun
 * @version 1.0
 */
public class DAOFactoryAccuracyTest extends TestCase {

    /**
     * the namespace used by <code>DAOFactory</code> class.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time";

    /**
     * the configuration manager used in tests.
     */
    private ConfigManager configManager;

    /**
     * <p>
     * Sets up the test environment. The namespace is removed from the configuration.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }
    }

    /**
     * <p>
     * Test method <code>getDAO</code>. A <code>TimeEntryDAO</code> instance should be created.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetDAO1() throws Exception {
        configManager.add("SampleConfig.xml");

        DAO dao = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);

        assertTrue("The DAO instance should be TimeEntryDAO.", dao instanceof TimeEntryDAO);
    }

    /**
     * <p>
     * Tests method <code>getDAO</code>. A <code>TimeStatusDAO</code> instance should be
     * created.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetDAO2() throws Exception {
        configManager.add("SampleConfig.xml");

        DAO dao = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);

        assertTrue("The DAO instance should be TimeEntryDAO.", dao instanceof TimeStatusDAO);
    }

    /**
     * <p>
     * Test method <code>getDAO</code>. A <code>TaskTypeDAO</code> instance should be created.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetDAO3() throws Exception {
        configManager.add("SampleConfig.xml");

        DAO dao = DAOFactory.getDAO(TaskType.class, NAMESPACE);

        assertTrue("The DAO instance should be TimeEntryDAO.", dao instanceof TaskTypeDAO);
    }

}
