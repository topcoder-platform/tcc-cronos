/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;
import com.topcoder.timetracker.user.*;

/**
 * Failure test cases for UserPersistenceImpl.
 *
 * @author WishingBone
 * @version 1.0
 */
public class UserPersistenceImplFailureTests extends TestCase {

    /**
     * Create with null namespace.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate_NullNamespace() throws Exception {
        try {
            new UserPersistenceImpl(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with empty namespace.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate_EmptyNamespace() throws Exception {
        try {
            new UserPersistenceImpl("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create with invalid namespace.
     */
    public void testCreate_InvalidNamespace() {
        try {
            new UserPersistenceImpl("invalid");
            fail("Should have thrown ConfigurationException.");
        } catch (ConfigurationException ce) {
        }
    }

    /**
     * Create with null connection name.
     */
    public void testCreate_NullConnectionName() throws Exception {
        try {
            new UserPersistenceImpl(null, TestHelper.getConnectionFactory());
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with empty connection name.
     */
    public void testCreate_EmptyConnectionName() throws Exception {
        try {
            new UserPersistenceImpl("", TestHelper.getConnectionFactory());
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create with null connection factory.
     */
    public void testCreate_NullConnectionFactory() throws Exception {
        try {
            new UserPersistenceImpl("conn", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * addUser() with null user.
     *
     * @throws Exception to JUnit.
     */
    public void testAddUser_NullUser() throws Exception {
        try {
            TestHelper.getUserPersistence().addUser(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * addUser() with invalid persistence.
     */
    public void testAddUser_InvalidPersistence() {
        try {
            new UserPersistenceImpl("invalid", TestHelper.getConnectionFactory()).addUser(new User(1, "name", "store"));
            fail("Should have thrown PersistenceException.");
        } catch (PersistenceException pe) {
        }
    }

    /**
     * removeUser() with null user.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveUser_NullUser() throws Exception {
        try {
            TestHelper.getUserPersistence().removeUser(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * removeUser() with invalid persistence.
     */
    public void testRemoveUser_InvalidPersistence() {
        try {
            new UserPersistenceImpl("invalid", TestHelper.getConnectionFactory()).removeUser(new User(1, "name", "store"));
            fail("Should have thrown PersistenceException.");
        } catch (PersistenceException pe) {
        }
    }

    /**
     * getUsers() with invalid persistence.
     */
    public void testGetUsers_InvalidPersistence() {
        try {
            new UserPersistenceImpl("invalid", TestHelper.getConnectionFactory()).getUsers();
            fail("Should have thrown PersistenceException.");
        } catch (PersistenceException pe) {
        }
    }

}
