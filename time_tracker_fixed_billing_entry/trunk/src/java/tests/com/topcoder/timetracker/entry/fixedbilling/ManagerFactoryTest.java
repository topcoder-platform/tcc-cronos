/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link ManagerFactory}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class ManagerFactoryTest extends TestCase {
    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.OBJECT_FACTORY_CONFIGE_FILE_NAME);
    }

    /**
     * Clear up the config.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        TestHelper.removeNamespaces();
    }

    /**
     * Test the <code>{@link ManagerFactory}</code> with invalid config. Should throw a ConfigurationException here.
     *
     * @throws Exception to junit.
     */
    public void testGetFixedBillingEntryManager_InvalidConfig()
        throws Exception {
        TestHelper.removeNamespaces();

        try {
            ManagerFactory.getFixedBillingEntryManager();

            //If already call getFixedBillingEntryManager(), it'll not throw exception.
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link ManagerFactory}</code> with success process.
     *
     * @throws Exception to junit.
     */
    public void testGetFixedBillingEntryManager_Success()
        throws Exception {
        FixedBillingEntryManager entryManager = ManagerFactory.getFixedBillingEntryManager();
        assertNotNull("Unable to create the instance.", entryManager);
    }

    /**
     * Test the <code>{@link ManagerFactory}</code> with invalid config. Should throw a ConfigurationException here.
     *
     * @throws Exception to junit.
     */
    public void testGetFixedBililngStatusManager_InvalidConfig()
        throws Exception {
        TestHelper.removeNamespaces();

        try {
            ManagerFactory.getFixedBililngStatusManager();

            //If already call getFixedBililngStatusManager(), it'll not throw exception.
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link ManagerFactory}</code> with success process.
     *
     * @throws Exception to junit.
     */
    public void testGetFixedBililngStatusManager_Success()
        throws Exception {
        FixedBillingStatusManager statusManager = ManagerFactory.getFixedBililngStatusManager();
        assertNotNull("Unable to create the instance.", statusManager);
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(ManagerFactoryTest.class);
    }
}
