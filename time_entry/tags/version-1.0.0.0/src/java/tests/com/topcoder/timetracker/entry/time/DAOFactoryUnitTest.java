/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit test cases for DAOFactory.
 * Use different config file(both correct and incorrect) to test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DAOFactoryUnitTest extends TestCase {
    /**
     * <p>
     * An invalid config file for testing. No com.topcoder.timetracker.entry.time.TaskType property.
     * </p>
     */
    private static final String INVALID_CONFIG_1 = "invalid1.xml";

    /**
     * <p>
     * An invalid config file for testing. No class propery in com.topcoder.timetracker.entry.time.TaskType property.
     * </p>
     */
    private static final String INVALID_CONFIG_2 = "invalid2.xml";

    /**
     * <p>
     * An invalid config file for testing. Incorrect class propery in com.topcoder.timetracker.entry.time.TaskType
     * property.
     * </p>
     */
    private static final String INVALID_CONFIG_3 = "invalid3.xml";

    /**
     * <p>
     * An invalid config file for testing. No connectionName propery in com.topcoder.timetracker.entry.time.TaskType
     * property.
     * </p>
     */
    private static final String INVALID_CONFIG_4 = "invalid4.xml";

    /**
     * <p>
     * The ConfigManager instance to load the config file.
     * </p>
     */
    private static ConfigManager cm = ConfigManager.getInstance();

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.myconfig";

    /**
     * <p>
     * The config file to load configuration.
     * </p>
     */
    private static final String CONFIG_FILE = "SampleXML.xml";

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(DAOFactoryUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        // load the configuration
        if (cm.existsNamespace(NAMESPACE)) {
            cm.removeNamespace(NAMESPACE);
        }

        cm.add(NAMESPACE, CONFIG_FILE, ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        if (cm.existsNamespace(NAMESPACE)) {
            cm.existsNamespace(NAMESPACE);
        }
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. With null dataObject, NullPointerException should
     * be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDAO1() throws Exception {
        try {
            DAOFactory.getDAO(null, NAMESPACE);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. With not DataObject class, DAOFactoryException
     * should be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDAO2() throws Exception {
        try {
            DAOFactory.getDAO(DAOFactory.class, NAMESPACE);
            fail("DAOFactoryException should be thrown");
        } catch (DAOFactoryException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. With null namespace, NullPointerException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDAO3() throws Exception {
        try {
            DAOFactory.getDAO(TaskType.class, null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. With empty namespace, IllegalArgumentException
     * should be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDAO4() throws Exception {
        try {
            DAOFactory.getDAO(TaskType.class, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. With not existed namespace, DAOFactoryException
     * should be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDAO5() throws Exception {
        try {
            DAOFactory.getDAO(TaskType.class, "test");
            fail("DAOFactoryException should be thrown");
        } catch (DAOFactoryException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. No com.topcoder.timetracker.entry.time.TaskType
     * property, DAOFactoryException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO6() throws Exception {
        invalidConfigurationTest(INVALID_CONFIG_1);
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. No class propery in
     * com.topcoder.timetracker.entry.time.TaskType property, DAOFactoryException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO7() throws Exception {
        invalidConfigurationTest(INVALID_CONFIG_2);
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. Incorrect class propery in
     * com.topcoder.timetracker.entry.time.TaskType property, DAOFactoryException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO8() throws Exception {
        invalidConfigurationTest(INVALID_CONFIG_3);
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method. No connectionName propery in
     * com.topcoder.timetracker.entry.time.TaskType property, DAOFactoryException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO9() throws Exception {
        invalidConfigurationTest(INVALID_CONFIG_4);
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method.  With TaskType class, no exception would be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO10() throws Exception {
        Object object = DAOFactory.getDAO(TaskType.class, NAMESPACE);
        assertNotNull("DAO Object was not properly got from DAOFactory", object);
        assertTrue("DAO Object was not properly got from DAOFactory", object.getClass().equals(TaskTypeDAO.class));
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method.  With TimeStatus class, no exception would be
     * thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO11() throws Exception {
        Object object = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);
        assertNotNull("DAO Object was not properly got from DAOFactory", object);
        assertTrue("DAO Object was not properly got from DAOFactory", object.getClass().equals(TimeStatusDAO.class));
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method.  With TimeEntry class, no exception would be
     * thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO12() throws Exception {
        Object object = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);
        assertNotNull("DAO Object was not properly got from DAOFactory", object);
        assertTrue("DAO Object was not properly got from DAOFactory", object.getClass().equals(TimeEntryDAO.class));
    }

    /**
     * <p>
     * Tests the getDAO(Class dataObject, String namespace) method with given invalid config file. DAOFactoryException
     * should be thrown.
     * </p>
     *
     * @param configFile the config file to load
     *
     * @throws Exception to JUnit
     */
    private void invalidConfigurationTest(String configFile)
        throws Exception {
        loadConfiguration(configFile);

        try {
            DAOFactory.getDAO(TaskType.class, NAMESPACE);
            fail("DAOFactoryException should be thrown");
        } catch (DAOFactoryException ce) {
            // good
        }
    }

    /**
     * Load the specified config file.
     *
     * @param configFile the config file to be loaded
     *
     * @throws Exception to JUnit
     */
    private void loadConfiguration(String configFile) throws Exception {
        if (cm.existsNamespace(NAMESPACE)) {
            cm.removeNamespace(NAMESPACE);
        }

        cm.add(NAMESPACE, configFile, ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }
}
