/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;
import com.topcoder.timetracker.user.*;
import com.topcoder.security.authenticationfactory.AuthenticateException;

/**
 * Failure test cases for DbUserStore.
 *
 * @author WishingBone
 * @version 1.0
 */
public class DbUserStoreFailureTests extends TestCase {

    /**
     * Create with invalid config.
     */
    public void testCreate_InvalidConfig() {
        try {
            new DbUserStore();
            fail("Should have thrown ConfigurationException.");
        } catch (ConfigurationException ce) {
        }
    }

    /**
     * Create with null connection name.
     */
    public void testCreate_NullConnectionName() {
        try {
            new DbUserStore(null, TestHelper.getConnectionFactory());
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with empty connection name.
     */
    public void testCreate_EmptyConnectionName() {
        try {
            new DbUserStore("", TestHelper.getConnectionFactory());
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create with null connection factory.
     */
    public void testCreate_NullConnectionFactory() {
        try {
            new DbUserStore("conn", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * setConnectionString() with null connection name.
     */
    public void testSetConnectionString_NullConnectionName() {
        try {
            TestHelper.getUserStore().setConnectionString(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * setConnectionString() with empty connection name.
     */
    public void testSetConnectionString_EmptyConnectionName() {
        try {
            TestHelper.getUserStore().setConnectionString("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * getNames() with invalid persistence.
     */
    public void testGetNames_InvalidPersistence() {
        try {
            new DbUserStore("invalid", TestHelper.getConnectionFactory()).getNames();
            fail("Should have thrown PersistenceException.");
        } catch (PersistenceException pe) {
        }
    }

    /**
     * search() with null pattern.
     *
     * @throws Exception to JUnit.
     */
    public void testSearch_NullPattern() throws Exception {
        try {
            TestHelper.getUserStore().search(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * search() with empty pattern.
     *
     * @throws Exception to JUnit.
     */
    public void testSearch_EmptyPattern() throws Exception {
        try {
            TestHelper.getUserStore().search("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * search() with invalid persistence.
     */
    public void testSearch_InvalidPersistence() {
        try {
            new DbUserStore("invalid", TestHelper.getConnectionFactory()).search("pattern");
            fail("Should have thrown PersistenceException.");
        } catch (PersistenceException pe) {
        }
    }

    /**
     * contains() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testContains_NullName() throws Exception {
        try {
            TestHelper.getUserStore().contains(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * contains() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testContains_EmptyName() throws Exception {
        try {
            TestHelper.getUserStore().contains("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * contains() with invalid persistence.
     */
    public void testContains_InvalidPersistence() {
        try {
            new DbUserStore("invalid", TestHelper.getConnectionFactory()).contains("name");
            fail("Should have thrown PersistenceException.");
        } catch (PersistenceException pe) {
        }
    }

    /**
     * authenticate() with null username.
     *
     * @throws Exception to JUnit.
     */
    public void testAuthenticate_NullUsername() throws Exception {
        try {
            TestHelper.getUserStore().authenticate(null, "password");
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * authenticate() with null password.
     *
     * @throws Exception to JUnit.
     */
    public void testAuthenticate_NullPassword() throws Exception {
        try {
            TestHelper.getUserStore().authenticate("username", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * authenticate() with empty username.
     *
     * @throws Exception to JUnit.
     */
    public void testAuthenticate_EmptyUsername() throws Exception {
        try {
            TestHelper.getUserStore().authenticate("", "password");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * authenticate() with invalid persistence.
     */
    public void testAuthenticate_InvalidPersistence() {
        try {
            new DbUserStore("invalid", TestHelper.getConnectionFactory()).authenticate("username", "password");
            fail("Should have thrown AuthenticateException.");
        } catch (AuthenticateException ae) {
        }
    }

}
