/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * This abstract base class is used to test the UserPersistenceImpl object, for "normal" cases.  For exception
 * ("failure") tests, see UserPersistenceImplExceptionsTest.  Subclasses of this class must provide the
 * "createUserPersistenceImpl" method, which instantiates a UserPersistenceImpl using one of the constructors.
 * </p>
 *
 * @see UserPersistenceImpl
 * @see UserPersistenceImplExceptionsTest
 * @see UserPersistenceImplOneArgCtorTest
 * @see UserPersistenceImplTwoArgCtorTest
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class UserPersistenceImplTestCase extends DbTestCase {


    /** This is the UserPersistenceImpl object we're testing. */
    private UserPersistenceImpl persistence;


    /**
     * Set up a good, known configuration for a UserPersistenceImpl object and instantiate a
     * UserPersistenceImpl to test.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // set up a known good config
        addNamespace(NAMESPACE, "TimeTrackerUserConfig.xml");
        cleanupDatabase();

        persistence = createUserPersistenceImpl();
    }


    /**
     * Instantiate a new UserPersistenceImpl object for testing purposes.
     * @return a new instance of the UserPersistenceImpl class
     * @throws Exception Never under normal conditions.
     */
    protected abstract UserPersistenceImpl createUserPersistenceImpl() throws Exception;


    /**
     * Test the <code>addUser</code> method, and validate using JDBC.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAddUser() throws Exception {

        // indicate that we've inserted something into the database
        inserted();

        // add a user using the persistence...
        User user = new User(10, "user10", "DefaultUserStore", "user@topcoder.com");
        persistence.addUser(user);

        // ...and now validate using JDBC.
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        // trick to not to have to parse a result set
        int count = stmt.executeUpdate("update users set name = name "
                        + "where usersid = " + user.getId()
                        + " and name = '" + user.getName() + "'"
                        + " and userstore='" + user.getUserStoreName() + "'");
        assertEquals("Didn't insert correct # of records",
                     1,
                     count);
        stmt.close();
        // note: don't close the connection, as it is re-used by other methods in
        // DbTestCase.
    }


    /**
     * Test that <code>addUser</code> throws PersistenceException if any error occurs while
     * adding the user to the table, for example, if a primary key exception is thrown.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAddUserThrowsPersistenceException() throws Exception {

        // insert the same id twice
        try {
            User user = new User(10, "user10", "DefaultUserStore", null);
            persistence.addUser(user);
            // Force a PersistenceException
            persistence.addUser(user);
            fail("addUser didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>removeUser</code> method on an empty database.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserEmpty() throws Exception {

        // before, there are zero records...
        assertEquals("before remove, getUsers doesn't return correct # of users",
                     0, persistence.getUsers().size());

        User user = new User(1, "user1", "store1", null);
        // should do nothing.
        persistence.removeUser(user);

        // and after there are zero records.
        assertEquals("after remove, getUsers doesn't return correct # of users",
                     0, persistence.getUsers().size());
    }


    /**
     * Test the <code>removeUser</code> method by adding a user (which we have already
     * proven works), then removing it, then calling getUsers (which we have also already
     * proven works).
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUser() throws Exception {

        User user = new User(1, "user1", "store1", null);
        persistence.addUser(user);
        User user2 = new User(2, "user2", "store2", null);
        persistence.addUser(user2);

        // remove it
        persistence.removeUser(user);

        Collection users = persistence.getUsers();
        assertEquals("after remove, getUsers doesn't return correct # of users",
                     1, users.size());

        // show that we removed the right one
        User remainingUser = (User) users.iterator().next();
        assertEquals("remove didn't remove the right one; id is wrong",
                user2.getId(), remainingUser.getId());
        assertEquals("remove didn't remove the right one; name is wrong",
                user2.getName(), remainingUser.getName());
        assertEquals("remove didn't remove the right one; store is wrong",
                user2.getUserStoreName(), remainingUser.getUserStoreName());
    }


    /**
     * Test the <code>getUsers</code> method when the database is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUsersEmpty() throws Exception {

        Collection users = persistence.getUsers();
        assertEquals("getUsers on an empty database doesn't return correct # of users",
                     0, users.size());
    }


    /**
     * Test the <code>getUsers</code> method when there are supposedly 3 users in the
     * database.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUsers() throws Exception {

        // insert 3 users manually
        insertUsers();

        Collection users = persistence.getUsers();
        assertEquals("getUsers doesn't return correct # of users",
                     3, users.size());

        // make sure we find each one - ids 1, 2, 3
        boolean found[] = new boolean[3];
        for (Iterator i = users.iterator(); i.hasNext();) {
            User user = (User) i.next();
            assertFalse("Already found id " + user.getId(),
                        found[(int) (user.getId() - 1)]);
            found[(int) (user.getId() - 1)] = true;
        }
    }
}
