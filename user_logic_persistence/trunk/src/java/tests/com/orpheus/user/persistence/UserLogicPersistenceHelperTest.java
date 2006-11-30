/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Tests the UserLogicPersistenceHelper class. This test case is executed within
 * the application server container, because the DBConnectionFactory given to
 * the UserLogicPersistenceHelper.createDBConnection(DBConnectionFactory,
 * String) method needs access to the DataSource provided by the container in
 * order to create the database connections.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class UserLogicPersistenceHelperTest extends ServletTestCase {

    /**
     * <p>
     * A sample argument description.
     * </p>
     */
    private static final String ARGUMENT_DESCRIPTION = "Description of argument";

    /**
     * <p>
     * The namespace containing DB Connection Factory configuration.
     * </p>
     */
    private static final String DB_CONNECTIONFACTORY_NAMESPACE = "com.topcoder.db.connectionfactory";

    /**
     * <p>
     * The name of the DB connection to test the
     * createDBConnection(DBConnectionFactory, String) method with.
     * </p>
     */
    private static final String DB_CONNECTION_NAME = "topcoderDB";

    /**
     * <p>
     * A test DAO configuration file containing valid configuration.
     * </p>
     */
    private static final String DAO_CONFIG_FILE = "test_conf/valid_dao_config.xml";

    /**
     * <p>
     * The test DAO configuration namespace.
     * </p>
     */
    private static final String DAO_NAMESPACE = "com.orpheus.user.persistence.impl.DAO.validWithConnName";

    /**
     * <p>
     * The test Object Factory configuration namespace for DAO instances.
     * </p>
     */
    private static final String DAO_OBJFACTORY_NAMESPACE = "com.orpheus.user.persistence.impl.DAO.objFactory";

    /**
     * <p>
     * Loads the test configuration namespaces into the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadConfig(DAO_CONFIG_FILE, DAO_NAMESPACE);
        ConfigHelper.loadConfig(DAO_CONFIG_FILE, DAO_OBJFACTORY_NAMESPACE);
        ConfigHelper.loadDBConnectionFactoryConfig();
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadConfig(DAO_NAMESPACE);
        ConfigHelper.unloadConfig(DAO_OBJFACTORY_NAMESPACE);
        ConfigHelper.unloadDBConnectionFactoryConfig();
    }

    /**
     * <p>
     * Tests that the assertArgumentNotNull(Object arg, String description)
     * method does NOT throw an IllegalArgumentException when the arg argument
     * is non-null.
     * </p>
     */
    public void testAssertArgumentNotNullWithValidArg() {
        try {
            UserLogicPersistenceHelper.assertArgumentNotNull("argument", ARGUMENT_DESCRIPTION);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown");
        }
    }

    /**
     * <p>
     * Tests that the assertArgumentNotNull(Object arg, String description)
     * method throws an IllegalArgumentException when the arg argument is null.
     * </p>
     */
    public void testAssertArgumentNotNullWithNullArg() {
        try {
            UserLogicPersistenceHelper.assertArgumentNotNull(null, ARGUMENT_DESCRIPTION);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * <p>
     * Tests that the assertArgumentNotNullOrBlank(String arg, String
     * description) method does NOT throw an IllegalArgumentException when the
     * arg argument is non-null and a non-empty string.
     * </p>
     */
    public void testAssertArgumentNotNullOrBlankWithValidArg() {
        try {
            UserLogicPersistenceHelper.assertArgumentNotNullOrBlank("argument", ARGUMENT_DESCRIPTION);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown");
        }
    }

    /**
     * <p>
     * Tests that the assertArgumentNotNullOrBlank(String arg, String
     * description) method throws an IllegalArgumentException when the arg
     * argument is null.
     * </p>
     */
    public void testAssertArgumentNotNullOrBlankWithNullArg() {
        try {
            UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(null, ARGUMENT_DESCRIPTION);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * <p>
     * Tests that the assertArgumentNotNullOrBlank(String arg, String
     * description) method throws an IllegalArgumentException when the arg
     * argument is an empty string.
     * </p>
     */
    public void testAssertArgumentNotNullOrBlankWithEmptyArg() {
        try {
            UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(" ", ARGUMENT_DESCRIPTION);
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * <p>
     * Tests the createObject(String namespace, String objectKeyConfigProperty,
     * Class expectedType) method with valid arguments. In this test case, a
     * DBConnectionFactory instance is created by using the "factoryKey"
     * property specified in a DAO namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateObjectWithValidArgs() throws Exception {
        Object obj = UserLogicPersistenceHelper.createObject(DAO_NAMESPACE, "factoryKey", DBConnectionFactory.class);
        assertNotNull("The object was not created properly", obj);
    }

    /**
     * <p>
     * Tests that the createObject(String namespace, String
     * objectKeyConfigProperty, Class expectedType) method throws an
     * ObjectInstantiationException when the given namespace is unknown.
     * </p>
     */
    public void testCreateObjectWithUnknownNamespaceArg() {
        try {
            UserLogicPersistenceHelper.createObject("unknownNamespace", "objectKey", String.class);
            fail("ObjectInstantiationException should be thrown: unknown namespace");
        } catch (ObjectInstantiationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the createObject(String namespace, String
     * objectKeyConfigProperty, Class expectedType) method throws an
     * ObjectInstantiationException when the "objectKeyConfigProperty" property
     * does not exist in the given namespace.
     * </p>
     */
    public void testCreateObjectWithMissingObjectKeyArg() {
        try {
            UserLogicPersistenceHelper.createObject(DAO_NAMESPACE, "missingKey", String.class);
            fail("ObjectInstantiationException should be thrown: missing object key");
        } catch (ObjectInstantiationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the createObject(String namespace, String
     * objectKeyConfigProperty, Class expectedType) method throws an
     * ObjectInstantiationException when the created object is not of the
     * expected type. In this test case, a DBConnectionFactory object is
     * created, but the expectedType argument is equal to String.class.
     * </p>
     */
    public void testCreateObjectWithWrongExpectedTypeArg() {
        try {
            UserLogicPersistenceHelper.createObject(DAO_NAMESPACE, "factoryKey", String.class);
            fail("ObjectInstantiationException should be thrown: object type is wrong");
        } catch (ObjectInstantiationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the createObject(String namespace, String
     * objectKeyConfigProperty, Class expectedType) method throws an
     * ObjectInstantiationException when there is no "specNamespace" property in
     * the specified namespace. This test case uses the DB Connection Factory
     * namespace.
     * </p>
     */
    public void testCreateObjectWithMissingSpecNamespaceProperty() {
        try {
            UserLogicPersistenceHelper.createObject(DB_CONNECTIONFACTORY_NAMESPACE, "objectKey", String.class);
            fail("ObjectInstantiationException should be thrown: missing specNamespace property");
        } catch (ObjectInstantiationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the getConfigProperty(String namespace, String property,
     * boolean required) method returns the specified property value from the
     * given namespace. This test case uses the DAO namespace, and looks for the
     * "name" property, which should have the value, "topcoderDB".
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigPropertyWithValidArgs() throws Exception {
        String value = UserLogicPersistenceHelper.getConfigProperty(DAO_NAMESPACE, "name", true);
        assertEquals("The property value is incorrect", DB_CONNECTION_NAME, value);
    }

    /**
     * <p>
     * Tests that the getConfigProperty(String namespace, String property,
     * boolean required) method returns null when the specified optional
     * property does not appear in the given namespace. This test case uses the
     * DB Connection Factory namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigPropertyWithMissiongOptionalPropertyArg() throws Exception {
        String value = UserLogicPersistenceHelper.getConfigProperty(DB_CONNECTIONFACTORY_NAMESPACE,
                                                                    "myProperty", false);
        assertNull("Null should be returned: missing optional property", value);
    }

    /**
     * <p>
     * Tests that the getConfigProperty(String namespace, String property,
     * boolean required) method throws an ObjectInstantiationException when the
     * specified namespace is unknown.
     * </p>
     */
    public void testGetConfigPropertyWithUnknownNamespaceArg() {
        try {
            UserLogicPersistenceHelper.getConfigProperty("unknownNamespace", "myProperty", false);
            fail("ObjectInstantiationException should be thrown: unknown namespace");
        } catch (ObjectInstantiationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the getConfigProperty(String namespace, String property,
     * boolean required) method throws an ObjectInstantiationException when the
     * specified required property does not appear in the given namespace. This
     * test case looks for the optional property in the DB Connection Factory
     * namespace.
     * </p>
     */
    public void testGetConfigPropertyWithMissingRequiredPropertyArg() {
        try {
            UserLogicPersistenceHelper.getConfigProperty(DB_CONNECTIONFACTORY_NAMESPACE, "myProperty", true);
            fail("ObjectInstantiationException should be thrown: required property missing");
        } catch (ObjectInstantiationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the createDBConnection(DBConnectionFactory connFactory, String
     * connName) method creates the database connection whose name is given by
     * the connName argument. This is accomplished by checking that a
     * PersistenceException is not thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateDBConnectionWithValidConnNameArg() throws Exception {
        try {
            UserLogicPersistenceHelper.createDBConnection(new DBConnectionFactoryImpl(DB_CONNECTIONFACTORY_NAMESPACE),
                                                          DB_CONNECTION_NAME);
        } catch (PersistenceException e) {
            fail("The DB connection was not created");
        }
    }

    /**
     * <p>
     * Tests that the createDBConnection(DBConnectionFactory connFactory, String
     * connName) method creates the default database connection when the
     * connName argument is null. This is accomplished by checking that a
     * PersistenceException is not thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateDBConnectionWithValidNullConnNameArg() throws Exception {
        try {
            UserLogicPersistenceHelper.createDBConnection(new DBConnectionFactoryImpl(DB_CONNECTIONFACTORY_NAMESPACE),
                                                          DB_CONNECTION_NAME);
        } catch (PersistenceException e) {
            fail("The DB connection was not created");
        }
    }

    /**
     * <p>
     * Tests that the createDBConnection(DBConnectionFactory connFactory, String
     * connName) method throws a PersistenceException when it cannot create the
     * database connection. In this test case, the connection name does not
     * appear in the DB Connection Factory namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateDBConnectionWithUnknownConnNameArg() throws Exception {
        try {
            UserLogicPersistenceHelper.createDBConnection(new DBConnectionFactoryImpl(DB_CONNECTIONFACTORY_NAMESPACE),
                                                          "unknownConnection");
            fail("PersistenceException should be thrown: could not create the DB connection");
        } catch (PersistenceException e) {
            // Success.
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
        return new TestSuite(UserLogicPersistenceHelperTest.class);
    }

}
