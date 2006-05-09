/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import java.lang.reflect.Field;
import java.util.Collection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.user.DbUserStore;
import com.topcoder.timetracker.user.UserStore;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Accuracy test for class <code>DbUserStore</code>.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class DbUserStoreAccuracyTest extends TestCase {

    /**
     * Represents the <code>DbUserStore</code> instance used for accuray test.
     */
    private UserStore userStore;

    /**
     * <p>
     * Represents the namespace used for create <code>UserStore</code> object.
     * </p>
     */
    private final String namespace = "com.topcoder.timetracker.user";

    /**
     * <p>
     * Represents the DB Connection factory's config namespace.
     * </p>
     */
    private final String dbFactoryNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the Logging Wrapper's config namespace.
     * </p>
     */
    private final String logNamespace = "com.topcoder.util.log";

    /**
     * <p>
     * The setUp method used for initializing testing environment.
     * </p>
     * <p>
     * The environment includes the namespace configuration, tested instance creating and database
     * data preparing.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void setUp() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        // remove the namespace if it exists.
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }

        // add the configuration file with namespace
        configManager.add("Accuracy/SampleConfig.xml");
        configManager.add("Accuracy/DBFactory.xml");
        configManager.add(logNamespace, "Accuracy/Logging.xml", ConfigManager.CONFIG_XML_FORMAT);

        // create DbUserStore instance used for test and set the connection.
        userStore = new DbUserStore();
        userStore.setConnectionString("informix");
        AccuracyTestHelper.cleanDefaultUsersTable();
        AccuracyTestHelper.prepareDefaultUsersTable();
    }

    /**
     * <p>
     * The tearDown method used for cleaning the testing environment.
     * </p>
     * <p>
     * The task includes cleaning the namespace, set tested instance <code>null</code> and clean
     * database data.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.cleanDefaultUsersTable();
        ConfigManager configManager = ConfigManager.getInstance();

        // remove the namespace
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }
        // set the DbUserStore instance null.
        userStore = null;
    }

    /**
     * <p>
     * Test constructor DbUserStore().
     * </p>
     * <p>
     * Assert that the <code>UserStore</code> instance can be created and the field
     * <code>dbFactory</code> is initialized properly.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testDbUserStore() throws Exception {
        // get the private field dbFactory
        Field dbFactoryField = DbUserStore.class.getDeclaredField("dbFactory");
        dbFactoryField.setAccessible(true);
        // assert that the dbFactory can be properly set.
        assertNotNull("the dbFactory field is not properly set", dbFactoryField.get(userStore));
    }

    /**
     * <p>
     * Test method DbUserStore(String connectionName, DBConnectionFactory dbFactory).
     * </p>
     * <p>
     * Assert that the <code>UserStore</code> instance can be created and the field
     * <code>dbFactory</code> and <code>connectionName</code> are initialized properly.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testDbUserStoreStringDBConnectionFactory() throws Exception {
        // get the private dbFactory and connectionName field.
        Field dbFactoryField = DbUserStore.class.getDeclaredField("dbFactory");
        Field connectionNameField = DbUserStore.class.getDeclaredField("connectionName");
        dbFactoryField.setAccessible(true);
        connectionNameField.setAccessible(true);

        // a new DBConnectionFactory.
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        String connectionName = "test";
        // create the DbUserStore instance.
        UserStore dbUserStore = new DbUserStore(connectionName, factory);

        // assert the instance can be created.
        assertNotNull("fail to create DbUserStore instance.", dbUserStore);

        // assert the dbFactory and connectionName can be set properly
        assertEquals("the factory field is not properly set.", factory, dbFactoryField
                .get(dbUserStore));
        assertEquals("the connectionName field is not properly set.", connectionName,
                connectionNameField.get(dbUserStore));
    }

    /**
     * <p>
     * Test method getNames().
     * </p>
     * <p>
     * Assert that the method can get all the names of data in table DefaultUsers.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetNames() throws Exception {
        Collection names = userStore.getNames();
        // the return collection should include three names: user1, user2 and user3.
        assertEquals("Three names should be returned.", 3, names.size());
        assertTrue("missing name 'user3'.", names.contains("user3"));
        assertTrue("missing name 'user4'.", names.contains("user4"));
        assertTrue("missing name 'user5'.", names.contains("user5"));
    }

    /**
     * <p>
     * Test method search(String pattern).
     * </p>
     * <p>
     * Assert that the method can search the names of data sucessfully according some pattern.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testSearch1() throws Exception {
        Collection names = userStore.search("%user%");
        // use the pattern %user% so three names should be returned: user1, user2 and user3
        assertEquals("Three names should be returned.", 3, names.size());
        assertTrue("missing name 'user3'.", names.contains("user3"));
        assertTrue("missing name 'user4'.", names.contains("user4"));
        assertTrue("missing name 'user5'.", names.contains("user5"));
    }

    /**
     * <p>
     * Test method search2(String pattern).
     * </p>
     * <p>
     * Assert that the method can return the properly names according some pattern.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testSearch2() throws Exception {
        Collection names = userStore.search("%3%");
        // use the pattern %1% so one name should be returned: user1
        assertEquals("One name should be returned.", 1, names.size());
        assertTrue("missing name 'user3'.", names.contains("user3"));
    }

    /**
     * <p>
     * Test method contains(String name).
     * </p>
     * <p>
     * Assert that the method can judge whether the record(s) exists according the provided name.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testContains() throws Exception {
        // the record with name user1 should be in database
        assertTrue("the database should contain the user3", userStore.contains("user3"));
        // the record with name user4 should be in database
        assertFalse("the database should not contain the user6", userStore.contains("user6"));
    }

    /**
     * <p>
     * Test method authenticate(String username, Object password).
     * </p>
     * <p>
     * Assert that the method can authenticate the user successfully using the password.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testAuthenticate() throws Exception {
        // the user1 has the password user1, so authenticate successfully.
        assertTrue("fail to authenticate user.", userStore.authenticate("user3", "user3")
                .isSuccessful());
        // fail to use passwore user for user1
        assertFalse("fail to authenticate user.", userStore.authenticate("user3", "user")
                .isSuccessful());
    }

    /**
     * <p>
     * Test method setConnectionString(String).
     * </p>
     * <p>
     * Assert that the <code>connection</code> field can be properly set.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testSetConnectionString() throws Exception {
        Field connectionNameField = DbUserStore.class.getDeclaredField("connectionName");
        connectionNameField.setAccessible(true);
        userStore.setConnectionString("connection");
        assertEquals("the field connectionName is not properly set.", "connection",
                connectionNameField.get(userStore));
    }

}
