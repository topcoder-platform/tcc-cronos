/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.Response;

import java.util.Collection;
import java.util.HashSet;

/**
 * <p>
 * This abstract base class is used to test the DbUserStore object, for "normal" cases.  For exception
 * ("failure") tests, see DbUserStoreExceptionsTest.  Subclasses of this class must provide the
 * "createDbStore" method, which instantiates a DbUserStore using one of the constructors.
 * </p>
 *
 * <p>
 * This class extends DbTestCase, which will clean up the database after *each test*.
 * </p>
 *
 * @see DbUserStore
 * @see DbUserStoreExceptionsTest
 * @see DbUserStoreDefaultCtorTest
 * @see DbUserStoreTwoArgCtorTest
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public abstract class DbUserStoreTestCase extends DbTestCase {


    /** The DbUserStore that we're testing. */
    private DbUserStore store;


    /**
     * Sets up a good configuration file and instantiates a DbUserStore to test.  This DbUserStore
     * will be "fully-functional".
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        addNamespace(UserManager.CONFIG_NAMESPACE, "TimeTrackerUserConfig.xml");
        cleanupDatabase();

        // instantiate the store, and set up its connection
        store = createDbStore();
        store.setConnectionString(CONNECTION_NAME);
    }


    /**
     * Instantiate a new DbUserStore object for testing purposes.  The setConnectionString
     * method should NOT be called on this object before being returned.
     * @return a new instance of the DbUserStore class
     * @throws Exception Never under normal conditions.
     */
    protected abstract DbUserStore createDbStore() throws Exception;


    /**
     * Test the <code>getNames</code> method, when there is nothing in the database.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetNamesEmpty() throws Exception {

        Collection names = store.getNames();
        assertNotNull("getNames returned null!", names);
        assertEquals("getNames returns collection of wrong size", 0, names.size());
    }


    /**
     * Test the <code>getNames</code> method, after inserting some users into the database.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetNames() throws Exception {

        insertDefaultUsers();

        // Compare the actual names to an expected set of names; use a Set
        // so that order does not matter.
        Collection expected = new HashSet();
        expected.add("username");
        expected.add("username1");
        expected.add("username2");
        expected.add("username3");
        Collection names = new HashSet(store.getNames());
        assertEquals("getNames is wrong", expected, names);
    }


    /**
     * Test the <code>search</code> method when the database is empty; should always
     * return an emtpy list.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSearchEmpty() throws Exception {

        // even finding everything should fail.
        Collection names = store.search("%");
        assertEquals("search is wrong size", 0, names.size());

        // find everything again
        names = store.search("username%");
        assertEquals("search is wrong size", 0, names.size());

        // show that wildcard works at the end too
        names = store.search("%1");
        assertEquals("search is wrong size", 0, names.size());

        // find just one (exact match; similar to 'contains')
        names = store.search("username1");
        assertEquals("search is wrong size", 0, names.size());

        names = store.search("XYZ");
        assertEquals("search is wrong size", 0, names.size());
    }


    /**
     * Test the <code>search</code> method after inserting some records; the
     * wildcard should be applied appropriately.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSearch() throws Exception {

        insertDefaultUsers();

        // find something that isn't there -> should be empty set.
        Collection names = store.search("XYZ");
        Collection expected = new HashSet();
        assertEquals("search on something not there returns the wrong records",
                     expected, new HashSet(names));

        // find everything
        names = store.search("%");
        expected.add("username");
        expected.add("username1");
        expected.add("username2");
        expected.add("username3");
        assertEquals("search on everything finds the wrong records", expected, new HashSet(names));

        // find everything again
        names = store.search("username%");
        assertEquals("search using wildcards finds the wrong records", expected, new HashSet(names));

        // show that wildcard works at the end too
        names = store.search("%1");
        expected.clear();
        expected.add("username1");
        assertEquals("search using wildcards finds the wrong records", expected, new HashSet(names));

        // find just one (exact match; similar to 'contains')
        names = store.search("username1");
        expected.clear();
        expected.add("username1");
        assertEquals("search using exact match is wrong", expected, new HashSet(names));
    }


    /**
     * Test the <code>contains</code> method when the database is empty; should
     * always return false.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsEmpty() throws Exception {

        // sanity check: no records in the store
        assertEquals("Empty store has wrong # of records", 0, store.getNames().size());

        // we never ever insert this user
        assertFalse("contains returns true for nonexistent name", store.contains("XYZ"));

        // always returns false
        assertFalse("contains returns true for nonexistent name", store.contains("username1"));
        assertFalse("contains returns true for nonexistent name", store.contains("username2"));
        assertFalse("contains returns true for wildcard", store.contains("%"));
    }


    /**
     * Test the <code>contains</code> method returns true when really finding the
     * records in the database.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContains() throws Exception {

        insertDefaultUsers();

        // find something that isn't there -> should be empty set.
        assertFalse("contains() returns true for nonexistent name", store.contains("XYZ"));
        assertTrue("contains() returns false for existent name", store.contains("username1"));
        assertTrue("contains() returns false true for existent name", store.contains("username2"));
        assertTrue("contains() returns false for existent name", store.contains("username3"));

        // it's case-sensitive
        assertFalse("contains() is case sensitive when it shouldn't be", store.contains("USERNAME1"));
    }


    /**
     * Test that the <code>authenticate</code> method returns a 'not successful' Response for all
     * requests when the database is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateEmpty() throws Exception {

        // Loop through "all" the usernames and passwords; they should all not be authenticated
        for (int i = 1; i <= 3; ++i) {

            // sanity check: this record doesn't exist in the db:
            assertFalse("contains returns true for nonexistent record", store.contains("username" + i));

            // this normally would be the correct password, but since the database
            // doesn't include these records, it will return false
            Response response = store.authenticate("username" + i, "password" + i);
            assertFalse("authenticate on an empty database succeeded", response.isSuccessful());
        }

        // Correct username, bad password
        Response response = store.authenticate("username1", "badpasswrd");
        assertFalse("authenticate with the wrong password was successful", response.isSuccessful());

        // case sensitive
        response = store.authenticate("username1", "PASSWORD1");
        assertFalse("authenticate with the wrong password was successful", response.isSuccessful());

        // this user doesn't exist, should return false
        response = store.authenticate("DOESNOTEXIST", "password1");
        assertFalse("authenticate with a nonexistent username was successful", response.isSuccessful());
    }


    /**
     * Test that the <code>authenticate</code> method returns a 'successful' Response when we
     * populate the DefaultUsers table and authenticate against this data.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticatePass() throws Exception {

        insertDefaultUsers();
        // Loop through "all" the usernames and passwords; they should all be authenticated
        for (int i = 1; i <= 3; ++i) {

            // sanity check: this record should exist in the db:
            assertTrue("contains returns false for existent record", store.contains("username" + i));

            Response response = store.authenticate("username" + i, "password" + i);
            assertTrue("authenticate with the correct passord failed", response.isSuccessful());
        }
    }


    /**
     * Test the <code>authenticate</code> method returns a 'not successful' Response when
     * given the wrong password, or wrong username.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateFail() throws Exception {

        insertDefaultUsers();

        // Correct username, bad password
        Response response = store.authenticate("username1", "badpasswrd");
        assertFalse("authenticate with the wrong password was successful", response.isSuccessful());

        // case sensitive
        response = store.authenticate("username1", "PASSWORD1");
        assertFalse("authenticate with the wrong password was successful", response.isSuccessful());

        // this user doesn't exist, should return false
        response = store.authenticate("DOESNOTEXIST", "password1");
        assertFalse("authenticate with a nonexistent username was successful", response.isSuccessful());
    }


    /**
     * Test the <code>setConnectionString</code> method, by first setting it to something
     * invalid, showing that database access failes, then setting it to something valid, and
     * showing that database access then psases.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetConnectionString() throws Exception {

        insertDefaultUsers();

        // set the string to something invalid, and it will fail when we try to use it
        store.setConnectionString("invalid");
        try {
            store.contains("username1");
            fail("Should have thrown PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }

        // set the connection string back to something correct, and it shouldn't throw an exception
        store.setConnectionString(CONNECTION_NAME);
        assertTrue("Store contained nonexistent name", store.contains("username1"));
    }
}
