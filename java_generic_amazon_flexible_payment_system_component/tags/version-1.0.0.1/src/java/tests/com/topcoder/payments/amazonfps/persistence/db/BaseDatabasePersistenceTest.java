/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.sql.Connection;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.NoDefaultConnectionException;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.TestHelper;
import com.topcoder.util.log.Log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <p>
 * Unit tests for {@link com.topcoder.payments.amazonfps.persistence.db.BaseDatabasePersistence} abstract class.
 * The {@code MockBaseDatabasePersistence} class is used as a reference implementation.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class BaseDatabasePersistenceTest {
    /**
     * Constant for connectionName property's name.
     */
    private static final String CONNECTION_NAME_PROPERTY = "connectionName";

    /**
     * Constant for configuration namespace for accuracy tests.
     */
    private static final String ACCURACY_CONFIGURATION_1 = "BaseDatabasePersistenceTest1";
    /**
     * Constant for configuration namespace for accuracy tests.
     */
    private static final String ACCURACY_CONFIGURATION_2 = "BaseDatabasePersistenceTest2";

    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_1 = "BaseDatabasePersistenceFailure1";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_2 = "BaseDatabasePersistenceFailure2";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_3 = "BaseDatabasePersistenceFailure3";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_4 = "BaseDatabasePersistenceFailure4";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_5 = "BaseDatabasePersistenceFailure5";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_6 = "BaseDatabasePersistenceFailure6";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_7 = "BaseDatabasePersistenceFailure7";

    /**
     * The {@code MockBaseDatabasePersistence} instance used for testing.
     */
    private MockBaseDatabasePersistence instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseDatabasePersistenceTest.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new MockBaseDatabasePersistence();
    }

    //-------------------------------------------------------------------------
    // constructor test
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code BaseDatabasePersistence} constructor. <br/>
     * Check that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructorBaseDatabasePersistence() {
        assertNull("The connection factory should be null",
                TestHelper.getSuperField(instance, "dbConnectionFactory"));
        assertNull("The connection name should be null",
                TestHelper.getSuperField(instance, "connectionName"));
        assertNull("The log should be null",
                TestHelper.getSuperField(instance, "log"));
    }

    //-------------------------------------------------------------------------
    // configure() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code configure} method. <br/>
     * Check that fields are initialized according to configuration.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_configure_1() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration(ACCURACY_CONFIGURATION_1);
        instance.configure(configuration);

        DBConnectionFactory factory = (DBConnectionFactory) TestHelper.getSuperField(instance, "dbConnectionFactory");
        String connectionName = (String) TestHelper.getSuperField(instance, "connectionName");
        Log log = (Log) TestHelper.getSuperField(instance, "log");

        assertNotNull("The db connection factory should not be null", factory);
        assertNotNull("The connection name should not be null", connectionName);
        assertNotNull("The log should not be null", log);

        String expected = configuration.getPropertyValue(CONNECTION_NAME_PROPERTY, String.class);
        assertEquals("Connection name should be correct", expected, connectionName);
    }

    /**
     * Accuracy test for {@code configure} method. <br/>
     * Do not specify optional fields.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_configure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(ACCURACY_CONFIGURATION_2));

        DBConnectionFactory factory = (DBConnectionFactory) TestHelper.getSuperField(instance, "dbConnectionFactory");
        String connectionName = (String) TestHelper.getSuperField(instance, "connectionName");
        Log log = (Log) TestHelper.getSuperField(instance, "log");

        assertNotNull("The db connection factory should not be null", factory);
        assertNull("The connection name should be null", connectionName);
        assertNull("The log should be null", log);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Set null configuration.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configureFailure_1() throws Exception {
        instance.configure(null);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * DB connection factory configuration is missing.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_1));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Specify empty logger name.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_2));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Specify empty connection name.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
    }

    //-------------------------------------------------------------------------
    // createConnection() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code createConnection} method. <br/>
     * Create connection using not empty connectionName configuration property.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createConnection_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(ACCURACY_CONFIGURATION_1));
        Connection connection = instance.createConnection();
        assertNotNull("Connection should exist", connection);
        connection.close();
    }

    /**
     * Accuracy test for {@code createConnection} method. <br/>
     * Create connection using default connection.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createConnection_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(ACCURACY_CONFIGURATION_2));
        Connection connection = instance.createConnection();
        assertNotNull("Connection should exist", connection);
        connection.close();
    }

    /**
     * Failure test for {@code createConnection} method. <br/>
     * Start creating connection without persistence object configuration
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_createConnectionFailure_1() throws Exception {
        instance.createConnection();
    }

    /**
     * Failure test for {@code createConnection} method. <br/>
     * connectionName is not specified and default connection is not provided
     *
     * @throws Exception to JUnit
     */
    @Test (expected = NoDefaultConnectionException.class)
    public void test_createConnectionFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_4));
        instance.createConnection();
    }

    /**
     * Failure test for {@code createConnection} method. <br/>
     * Provide not existed connection name
     *
     * @throws Exception to JUnit
     */
    @Test (expected = UnknownConnectionException.class)
    public void test_createConnectionFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        instance.createConnection();
    }

    /**
     * Failure test for {@code createConnection} method. <br/>
     * Incorrect configuration for db connection factory.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_createConnectionFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_6));
    }

    /**
     * Failure test for {@code createConnection} method. <br/>
     * Unknown default connection.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_createConnectionFailure_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_7));
    }

    //-------------------------------------------------------------------------
    // getLog() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code getLog} method. <br/>
     * Logger name is specified in the configuration
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getLog_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(ACCURACY_CONFIGURATION_1));

        Log log = instance.getLog();
        assertNotNull("Log should not be null", log);
    }

    /**
     * Accuracy test for {@code getLog} method. <br/>
     * Logger name is not specified in the configuration
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getLog_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(ACCURACY_CONFIGURATION_2));

        Log log = instance.getLog();
        assertNull("Log should be null", log);
    }
}
