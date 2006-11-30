/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.impl.SQLServerPendingConfirmationDAO;
import com.orpheus.user.persistence.impl.SQLServerUserProfileDAO;

/**
 * <p>
 * Tests the DAOFactory class. The testing is performed within an application
 * server container, because the DAO classes which the DAOFactory creates
 * need a bound JNDI reference to the DataStore.
 * </p>
 * <p>
 * <b>Note:</b> Since this class caches its objects that it creates in private
 * static fields, there is no way to unset those fields from client code once
 * they have been set. Therefore, the DAOFactory methods can only be tested with
 * one instance of input configuration once. This test case only tests valid
 * configuration. Invalid configuration cannot be tested.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class DAOFactoryTest extends ServletTestCase {

    /**
     * <p>
     * The number of times to invoke the getPendingConfirmationDAO() and
     * getUserProfileDAO() methods during the unit tests.
     * </p>
     */
    private static final int NUM_ITERATIONS = 10;

    /**
     * <p>
     * A test DAOFactory configuration file containing valid configuration.
     * </p>
     */
    private static final String DAOFACTORY_CONFIG_FILE = "test_conf/valid_daofactory_config.xml";

    /**
     * <p>
     * The prefix for the test DAOFactory configuration namespaces.
     * </p>
     */
    private static final String DAOFACTORY_NAMESPACE_PREFIX = "com.orpheus.user.persistence.DAOFactory";

    /**
     * <p>
     * A test DAO configuration file containing valid configuration.
     * </p>
     */
    private static final String DAO_CONFIG_FILE = "test_conf/valid_dao_config.xml";

    /**
     * <p>
     * The prefix for the test DAO configuration namespaces.
     * </p>
     */
    private static final String DAO_NAMESPACE_PREFIX = "com.orpheus.user.persistence.impl.DAO";

    /**
     * <p>
     * Loads the test configuration namespaces into the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadConfig(DAOFACTORY_CONFIG_FILE, DAOFACTORY_NAMESPACE_PREFIX);
        ConfigHelper.loadConfig(DAOFACTORY_CONFIG_FILE, DAOFACTORY_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.loadConfig(DAO_CONFIG_FILE, DAO_NAMESPACE_PREFIX + ".validWithConnName");
        ConfigHelper.loadConfig(DAO_CONFIG_FILE, DAO_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.loadCacheConfig();
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadConfig(DAOFACTORY_NAMESPACE_PREFIX);
        ConfigHelper.unloadConfig(DAOFACTORY_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.unloadConfig(DAO_NAMESPACE_PREFIX + ".validWithConnName");
        ConfigHelper.unloadConfig(DAO_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.unloadCacheConfig();
    }

    /**
     * <p>
     * Tests that the getPendingConfirmationDAO() method returns a non-null
     * PendingConfirmationDAO instance when valid configuration is used. The
     * method is called 10 times to ensure that it always returns the same type
     * of class on each call.
     * </p>
     * <p>
     * In this test, the returned instance is expected to be of type
     * SQLServerPendingConfirmationDAO. This instance is specified in the test
     * configuration file, "test_files/test_conf/daofactory/valid.xml".
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPendingConfirmationDAO() throws Exception {
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            assertTrue("A non-null SQLServerPendingConfirmationDAO should have been returned",
                       DAOFactory.getPendingConfirmationDAO() instanceof SQLServerPendingConfirmationDAO);
        }
    }

    /**
     * <p>
     * Tests that the getUserProfileDAO() method returns a non-null
     * UserProfileDAO instance when valid configuration is used. The method is
     * called 10 times to ensure that it always returns the same type of class
     * each call.
     * </p>
     * <p>
     * In this test, the returned instance is expected to be of type
     * SQLServerUserProfileDAO. This instance is specified in the test
     * configuration file, "test_files/test_conf/daofactory/valid.xml"
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetUserProfileDAO() throws Exception {
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            assertTrue("A non-null SQLServerUserProfileDAO should have been returned",
                       DAOFactory.getUserProfileDAO() instanceof SQLServerUserProfileDAO);
        }
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(DAOFactoryTest.class);
    }

}
