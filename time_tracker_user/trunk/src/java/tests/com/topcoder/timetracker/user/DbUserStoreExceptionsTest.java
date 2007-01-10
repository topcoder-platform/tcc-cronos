/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authenticationfactory.AuthenticateException;

/**
 * <p>
 * Tests that the methods in the DbUserStore class throw Exceptions when appropriate.
 * This class uses a "bad" database connection to force PersistenceExceptions to occur.
 * </p>
 *
 * @see DbUserStore
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DbUserStoreExceptionsTest extends ConfigTestCase {

    /** A password with which to test. */
    private static final String PASSWORD = "password";

    /** A good connection name with which to test. */
    private static final String CONNECTION_NAME = "InformixConnection";

    /** The UserStore that we're testing. */
    private DbUserStore store;


    /**
     * Configure the environment to point at a valid server but invalid database, and then
     * instantiate a DbUserStore to test.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // this config references a valid server and database, but the tables we need don't
        // exist in the specified DB.
        addNamespace(UserManager.CONFIG_NAMESPACE, "TimeTrackerUserWrongDatabase.xml");

        /** Always have to set the connection name. */
        store = new DbUserStore();
        store.setConnectionString(CONNECTION_NAME);
    }


    /**
     * Test that the default constructor throws ConfigurationException if any exception occurs during
     * initialization.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorThrowsConfigurationException() throws Exception {

        // with no namespace, it should throw an exception
        removeNamespace(UserManager.CONFIG_NAMESPACE);
        try {
            new DbUserStore();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }

        // this namespace has a bad sql connection
        addNamespace(UserManager.CONFIG_NAMESPACE, "TimeTrackerUserBadClass.xml");
        try {
            new DbUserStore();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 2-arg constructor throws NullPointerException if connectionName or dbFactory is
     * null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test2ArgCtorThrowsNPE() throws Exception {

        try {
            new DbUserStore(CONNECTION_NAME, null);
            fail("2-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new DbUserStore(null, new DBConnectionFactoryImpl());
            fail("2-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new DbUserStore(null, null);
            fail("2-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 2-arg constructor throws IllegalArgumentException if connectionName is the
     * empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test2ArgCtorThrowsIAE() throws Exception {

        try {
            new DbUserStore("", new DBConnectionFactoryImpl());
            fail("2-arg ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getNames</code> throws PersistenceException if any errors occur while
     * retrieving the names.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetNamesThrowsPersistenceException() throws Exception {

        try {
            // The store we're using is configured with a DBConnectionFactory that
            // doesn't have the right tables; will always throw a SQLException
            store.getNames();
            fail("getNames didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getNames</code> throws IllegalStateException if you call it
     * before setting the connectionString.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetNamesThrowsIllegalStateException() throws Exception {
        store = new DbUserStore();
        try {
            store.getNames();
            fail("getNames before setConnectionString doesn't throw IllegalStateException");
        } catch (IllegalStateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>search</code> throws NullPointerException if pattern is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSearchThrowsNPE() throws Exception {

        try {
            store.search(null);
            fail("search didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>search</code> throws IllegalArgumentException if pattern is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSearchThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            store.search("");
            fail("search didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>search</code> throws PersistenceException if any errors occur while
     * searching for the pattern.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSearchThrowsPersistenceException() throws Exception {

        // The store we're using is configured with a DBConnectionFactory that
        // doesn't have the right table; will always throw a SQLException, regardless of the pattern,
        // but try a few patterns anyway.
        try {
            store.search("pattern");
            fail("search didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }

        try {
            store.search("%");
            fail("search didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }

        try {
            store.search("_");
            fail("search didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }

        try {
            store.search("'");
            fail("search didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }

    }


    /**
     * Test that <code>search</code> throws IllegalStateException if you call it
     * before setting the connectionString.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSearchThrowsIllegalStateException() throws Exception {
        store = new DbUserStore();
        try {
            store.search(USERNAME);
            fail("search before setConnectionString doesn't throw IllegalStateException");
        } catch (IllegalStateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>contains</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsThrowsNPE() throws Exception {

        try {
            store.contains(null);
            fail("contains didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>contains</code> throws IllegalArgumentException if name is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            store.contains("");
            fail("contains didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>contains</code> throws PersistenceException if any errors occur while trying
     * to find the name.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsThrowsPersistenceException() throws Exception {

        try {
            // The store we're using is configured with a DBConnectionFactory that
            // doesn't have the right table; will always throw a SQLException
            store.contains("name");
            fail("contains didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>contains</code> throws IllegalStateException if you call it
     * before setting the connectionString.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsThrowsIllegalStateException() throws Exception {
        store = new DbUserStore();
        try {
            store.contains(USERNAME);
            fail("contains before setConnectionString doesn't throw IllegalStateException");
        } catch (IllegalStateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws NullPointerException if name or password is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsNPE() throws Exception {

        try {
            store.authenticate(null, null);
            fail("authenticate didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            store.authenticate(USERNAME, null);
            fail("authenticate didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            store.authenticate(null, PASSWORD);
            fail("authenticate didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws AuthenticateException if any errors occur while
     * trying to authenticate the user.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsAuthenticateException() throws Exception {

        try {
            // The store we're using is configured with a DBConnectionFactory that
            // doesn't have the right table; will always throw a SQLException, which
            // will be wrapped in an AuthenticateException.
            store.authenticate(USERNAME, PASSWORD);
            fail("authenticate didn't throw AuthenticateException");
        } catch (AuthenticateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws IllegalStateException if you call it
     * before setting the connectionString.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsIllegalStateException() throws Exception {
        store = new DbUserStore();
        try {
            store.authenticate(USERNAME, PASSWORD);
            fail("authenticate before setConnectionString doesn't throw IllegalStateException");
        } catch (IllegalStateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>setConnectionString</code> throws NullPointerException if connection is
     * null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetConnectionStringThrowsNPE() throws Exception {

        try {
            store.setConnectionString(null);
            fail("setConnectionString didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>setConnectionString</code> throws IllegalArgumentException if connection is
     * the empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetConnectionStringThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            store.setConnectionString("");
            fail("setConnectionString didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }
}
