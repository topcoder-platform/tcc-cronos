/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

/**
 * <p>
 * Tests that the UserPersistenceImpl class throws exceptions when appropriate. For "normal" tests
 * please see UserPersistenceImplOneArgCtorTest and UserPersistenceImplTwoArgCtorTest.
 * </p>
 *
 * @see UserPersistenceImpl
 * @see UserPersistenceImplOneArgCtorTest
 * @see UserPersistenceImplTwoArgCtorTest
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserPersistenceImplExceptionsTest extends ConfigTestCase {

    /** The UserPersistence implementation that we're testing. */
    private UserPersistenceImpl persistence;


    /**
     * Set up a known, invalid configuration for a UserPersistenceImpl object and instantiate a
     * UserPersistenceImpl to test.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // This is a known "invalid" configuration whereby the database it points
        // to doesn't have the needed tables defined.
        addNamespace(NAMESPACE, "TimeTrackerUserWrongDatabase.xml");

        persistence = new UserPersistenceImpl(NAMESPACE);
    }


    /**
     * Test that <code>addUser</code> throws NullPointerException if the given User object is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAddUserThrowsNPE() throws Exception {

        try {
            persistence.addUser(null);
            fail("addUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>addUser</code> throws PersistenceException if any error occurs during
     * retrieving.  Note, there are other ways to cause persistence exceptions, with a good
     * database connection; see UserPersistenceImplTestCase.
     *
     * @throws Exception Never under normal conditions.
     * @see UserPersistenceImplTestCase#testAddUserThrowsPersistenceException()
     */
    public void testAddUserThrowsPersistenceException() throws Exception {

        try {
            // The configuration doesn't point at the right database
            persistence.addUser(new User(1, "user1", "store", null));
            fail("addUsers didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>removeUser</code> throws NullPointerException if the given User object
     * is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserThrowsNPE() throws Exception {

        try {
            persistence.removeUser(null);
            fail("removeUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>removeUser</code> throws PersistenceException if any error occurs while
     * removing the user.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserThrowsPersistenceException() throws Exception {

        try {
            // The configuration doesn't point at the right database
            persistence.removeUser(new User(1, "name", "storeName", null));
            fail("removeUser didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUsers</code> throws PersistenceException if any error occurs during
     * retrieving the users from the table.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUsersThrowsPersistenceException() throws Exception {

        try {
            // The configuration doesn't point at the right database
            persistence.getUsers();
            fail("getUsers didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }
}
