/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserPersistence;
import com.topcoder.timetracker.user.UserPersistenceImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy test for class <code>UserPersistenceImpl</code>.
 * @author fuyun
 * @version 1.0
 */
public class UserPersistenceImplAccuracyTest extends TestCase {

    /**
     * <p>
     * The <code>UserPersistenceImpl</code> instance used for test.
     * </p>
     */
    private UserPersistence userPersistence;

    /**
     * <p>
     * the namespace used for create <code>UserPersistenceImpl</code> instance.
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
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }

        configManager.add("Accuracy/SampleConfig.xml");
        configManager.add("Accuracy/DBFactory.xml");
        configManager.add(logNamespace, "Accuracy/Logging.xml", ConfigManager.CONFIG_XML_FORMAT);

        userPersistence = new UserPersistenceImpl(namespace);
        AccuracyTestHelper.cleanUsersTable();
        AccuracyTestHelper.prepareUsersTable();
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
        userPersistence = null;
        AccuracyTestHelper.cleanUsersTable();
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }
    }

    /**
     * <p>
     * Test method UserPersistenceImpl(String namespace).
     * </p>
     * <p>
     * Assert that the <code>UserPersistenceImpl</code> instance can be created and the fields can
     * be properly set.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testUserPersistenceImplString() throws Exception {
        // assert the UserPersistenceImpl can be created
        assertNotNull("fail to create UserPersistenceImpl instance.", userPersistence);
        // get the private dbFactory and connectionName using reflection
        Field dbFactoryField = UserPersistenceImpl.class.getDeclaredField("dbFactory");
        Field connectionNameField = UserPersistenceImpl.class.getDeclaredField("connectionName");
        dbFactoryField.setAccessible(true);
        connectionNameField.setAccessible(true);
        // assert the field dbFactory and connectionName can be properly set
        assertNotNull("the field dbFactory is not properly set.", dbFactoryField
                .get(userPersistence));
        assertEquals("the field dbFactory is not properly set.", "informix", connectionNameField
                .get(userPersistence));
    }

    /**
     * <p>
     * Test method UserPersistenceImpl(String connectionName, DBConnectionFactory dbFactory).
     * </p>
     * <p>
     * Assert that the <code>UserPersistenceImpl</code> instance can be created using this
     * constructor and the fields can be set properly.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testUserPersistenceImplStringDBConnectionFactory() throws Exception {

        // get the private fields using reflection
        Field dbFactoryField = UserPersistenceImpl.class.getDeclaredField("dbFactory");
        Field connectionNameField = UserPersistenceImpl.class.getDeclaredField("connectionName");
        dbFactoryField.setAccessible(true);
        connectionNameField.setAccessible(true);
        // create a DBConnectionFactory used for arg
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        String connectionName = "test";
        UserPersistence persistence = new UserPersistenceImpl(connectionName, factory);
        // assert the instance can be created.
        assertNotNull("fail to create DbUserStore instance.", persistence);
        // assert the fields
        assertEquals("the factory field is not properly set.", factory, dbFactoryField
                .get(persistence));
        assertEquals("the connectionName field is not properly set.", connectionName,
                connectionNameField.get(persistence));

    }

    /**
     * <p>
     * Test method addUser(User user).
     * </p>
     * <p>
     * Assert that the user can be insert into database successfully.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testAddUser() throws Exception {
        // create a new User with id '3', name 'user3', store name 'store3'
        User user = new User(3, "user3", "store3");
        // add the user into db
        userPersistence.addUser(user);
        Connection conn = AccuracyTestHelper.getConnection();
        Statement stmt = conn.createStatement();
        try {
            // assert the user has been inserted into db
            ResultSet result = stmt.executeQuery("select * from Users where Name='user3';");
            assertTrue("fail to add user.", result.next());
        } finally {
            stmt.close();
            conn.close();
        }
    }

    /**
     * <p>
     * Test method removeUser(User user)
     * </P>.
     * <p>
     * Assert that the record representing the appointed user can be removed from db.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testRemoveUser1() throws Exception {
        User user = new User(2, "user2", "DbUserStore1");
        // remove the user.
        userPersistence.removeUser(user);
        Connection conn = AccuracyTestHelper.getConnection();
        Statement stmt = conn.createStatement();
        try {
            // assert the record does not exist now
            ResultSet result = stmt.executeQuery("select * from Users where Name='user2';");
            assertFalse("fail to remove user.", result.next());
        } finally {
            stmt.close();
            conn.close();
        }
    }

    /**
     * <p>
     * Test method removeUser(User user)
     * </P>.
     * <p>
     * The user does not exist, but no exception should be thrown.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testRemoveUser2() throws Exception {
        User user = new User(100, "unknown", "unknown");
        // remove the user.
        userPersistence.removeUser(user);
    }

    /**
     * <p>
     * Test method getUsers().
     * </p>
     * <p>
     * Assert that the users can be fetched.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetUsers() throws Exception {
        Collection users = userPersistence.getUsers();
        assertEquals("fail to get users.", 2, users.size());
    }

}
