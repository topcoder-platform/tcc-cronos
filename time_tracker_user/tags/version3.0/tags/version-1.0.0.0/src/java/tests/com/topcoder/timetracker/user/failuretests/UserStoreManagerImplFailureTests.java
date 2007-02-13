/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;
import com.topcoder.timetracker.user.*;

/**
 * Failure test cases for UserStoreManagerImpl.
 *
 * @author WishingBone
 * @version 1.0
 */
public class UserStoreManagerImplFailureTests extends TestCase {

    /**
     * Create with null namespace.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate_NullNamespace() throws Exception {
        try {
            new UserStoreManagerImpl(null);
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
            new UserStoreManagerImpl("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create with invalid namespace.
     */
    public void testCreate_InvalidNamespace() {
        try {
            new UserStoreManagerImpl("invalid");
            fail("Should have thrown ConfigurationException.");
        } catch (ConfigurationException ce) {
        }
    }

    /**
     * contains() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testContains_NullName() throws Exception {
        try {
            TestHelper.getUserStoreManager().contains(null);
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
            TestHelper.getUserStoreManager().contains("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * add() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testAdd_NullName() throws Exception {
        try {
            TestHelper.getUserStoreManager().add(null, TestHelper.getUserStore());
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * add() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testAdd_EmptyName() throws Exception {
        try {
            TestHelper.getUserStoreManager().add("", TestHelper.getUserStore());
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * add() with null user store.
     *
     * @throws Exception to JUnit.
     */
    public void testAdd_NullUserStore() throws Exception {
        try {
            TestHelper.getUserStoreManager().add("name", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * remove() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testRemove_NullName() throws Exception {
        try {
            TestHelper.getUserStoreManager().remove(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * remove() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testRemove_EmptyName() throws Exception {
        try {
            TestHelper.getUserStoreManager().remove("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * getUserStore() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserStore_NullName() throws Exception {
        try {
            TestHelper.getUserStoreManager().getUserStore(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * getUserStore() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserStore_EmptyName() throws Exception {
        try {
            TestHelper.getUserStoreManager().getUserStore("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * getUserStore() with invalid name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserStore_InvalidName() throws Exception {
        try {
            TestHelper.getUserStoreManager().getUserStore("invalid");
            fail("Should have thrown UnknownUserStoreException.");
        } catch (UnknownUserStoreException uuse) {
        }
    }

}
