/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * Tests the UserStoreManagerImpl class by configuring it with a known good configuration file,
 * and validating that it initializes itself with the proper UserStore objects.  Also, performs
 * various exception tests to make sure that it properly throws an exception.
 * </p>
 *
 * @see UserStoreManagerImpl
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class UserStoreManagerImplTest extends ConfigTestCase {

    /** The name of a known store (in the "UserStoreManagerGood.xml" config file). */
    private static final String THROWING_STORE_NAME = "throwingStore";

    /** The name of a known store (in the "UserStoreManagerGood.xml" config file). */
    private static final String DUMMY_STORE_NAME = "dummyStore";

    /** The UserStoreManager that we're testing. */
    private UserStoreManagerImpl manager;

    /** A UserStore that we use for testing, not created by the UserStoreManager constructor. */
    private UserStore store;


    /**
     * Sets up the Config Manager namespace for the UserStoreManager with a good config file.
     * (UserStoreManagerGood.xml). Then, instantiates a UserStoreManagerImpl to test, and an empty
     * UserStore to put into it.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        addNamespace(NAMESPACE, "UserStoreManagerGood.xml");
        manager = new UserStoreManagerImpl(NAMESPACE);
        store = new DummyUserStore();
    }


    /**
     * Tests that the constructor loads and instantiates the default set of stores by using
     * the getUserStoreNames method.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorSetsInitialValues() throws Exception {

        // these are the expected initial names
        Set expectedNames = new HashSet();
        expectedNames.add(DUMMY_STORE_NAME);
        expectedNames.add(THROWING_STORE_NAME);

        // make a set so we can compare without regards to order.
        Set nameSet = new HashSet(manager.getUserStoreNames());
        assertEquals("Constructor didn't load correct names", expectedNames, nameSet);

        // make sure the constructor passes the connect string to the UserStore;
        // note that we cast here - if it's the wrong class, we'll throw a ClassCastException
        // up to JUnit
        DummyUserStore dummyStore = (DummyUserStore) manager.getUserStore(DUMMY_STORE_NAME);
        assertEquals("Connection string not set", "connect", dummyStore.getConnectionString());
    }


    /**
     * Test that the constructor throws NullPointerException if namespace is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorThrowsNPE() throws Exception {

        try {
            new UserStoreManagerImpl(null);
            fail("ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the constructor throws IllegalArgumentException if namespace is the empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorThrowsIAE() throws Exception {

        try {
            new UserStoreManagerImpl("");
            fail("ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the constructor throws ConfigurationException if any errors occur during initialization.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorThrowsConfigurationException() throws Exception {
        // this namespace doesn't exist.
        try {
            new UserStoreManagerImpl("BAD");
            fail("ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }

        // this namespace doesn't exist any more
        removeNamespace(NAMESPACE);
        try {
            new UserStoreManagerImpl(NAMESPACE);
            fail("ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }

        // this namespace has a bad class name
        addNamespace(NAMESPACE, "UserStoreManagerBadClass.xml");
        try {
            new UserStoreManagerImpl(NAMESPACE);
            fail("ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }

        // this namespace specifies a user store, but no class
        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "UserStoreManagerMissingClass.xml");
        try {
            new UserStoreManagerImpl(NAMESPACE);
            fail("ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }

        // this namespace specifies a good class name, but not
        // a connect string (required change for final fixes)
        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "UserStoreManagerMissingConnectString.xml");
        try {
            new UserStoreManagerImpl(NAMESPACE);
            fail("ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the constructor allows the configured namedspace to be empty. This is a feature
     * so that the UserStoreManager can be configured without any UserStores.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorAllowsEmptyNamespace() throws Exception {
        // this namespace doesn't have any names; should be OK.
        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "Empty.xml");
        new UserStoreManagerImpl(NAMESPACE);
    }


    /**
     * Test the <code>contains</code> method, when the user store is present.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsTrue() throws Exception {

        // easiest way is to test it with the list of names we know are there.
        Collection names = manager.getUserStoreNames();
        for (Iterator i = names.iterator(); i.hasNext();) {
            String name = (String) i.next();
            assertTrue("contains() doesn't return true for entry returned by getUserStoreNames",
                       manager.contains(name));
        }

        // add our own store
        manager.add(STORE_NAME, store);
        assertTrue("contains() doesn't return true for added entry",
                        manager.contains(STORE_NAME));
    }


    /**
     * Test the <code>contains</code> method, when the user store is not present.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsFalse() throws Exception {

        // should return false (case sensitive)
        assertFalse("contains is not case-sensitive", manager.contains(DUMMY_STORE_NAME.toUpperCase()));

        assertFalse("contains returns true for nonexistent store", manager.contains("DNE"));

        // note, no trimming
        assertFalse("contains returns true for blank store", manager.contains(" "));
    }


    /**
     * Test that <code>contains</code> throws NullPointerException if the argument is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsThrowsNPE() throws Exception {

        try {
            manager.contains(null);
            fail("contains didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>contains</code> throws IllegalArgumentException if the argument is the
     * empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testContainsThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.contains("");
            fail("contains didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>add</code> method, verified by <code>contains</code> and <code>getUserStore</code>.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAdd() throws Exception {

        // should not be there.
        assertFalse("The store is already there", manager.contains(STORE_NAME));

        // adding it the first time it should return true
        assertTrue("Add didn't return true the first time", manager.add(STORE_NAME, store));

        // verify that it's there; should return the same object as what was added
        assertTrue("The store is not there", manager.contains(STORE_NAME));
        assertSame("getUserStore returned wrong store", store, manager.getUserStore(STORE_NAME));

        // after adding, it should return false the 2nd time.
        assertFalse("Add didn't return false the second time", manager.add(STORE_NAME, store));
    }


    /**
     * Test that <code>add</code> throws NullPointerException if name or userStore is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAddThrowsNPE() throws Exception {

        try {
            manager.add(null, null);
            fail("add didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.add(STORE_NAME, null);
            fail("add didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.add(null, store);
            fail("add didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>add</code> throws IllegalArgumentException if name is the empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAddThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.add("", store);
            fail("add didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>remove</code> method, verified by the getUserStore method.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemove() throws Exception {

        // should be there.
        assertTrue("The store is not there", manager.contains(DUMMY_STORE_NAME));

        // removing it the first time it should return true
        assertTrue("Remove didn't return true the first time", manager.remove(DUMMY_STORE_NAME));

        // verify that it's gone there; should return the same object as added
        Collection names = manager.getUserStoreNames();
        assertFalse("Names still contains the store we removed", names.contains(DUMMY_STORE_NAME));

        // after removing, it should return false.
        assertFalse("Remove didn't return false the second time", manager.remove(DUMMY_STORE_NAME));
        assertFalse("Remove of nonexistent store didn't return false",
                        manager.remove("" + System.currentTimeMillis()));
    }


    /**
     * Test that <code>remove</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveThrowsNPE() throws Exception {

        try {
            manager.remove(null);
            fail("remove didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>remove</code> throws IllegalArgumentException if name is the empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.remove("");
            fail("remove didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>getUserStoreNames</code> method returns all the stores in the manager.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserStoreNames() throws Exception {

        // these are the expected initial names
        Set expectedNames = new HashSet();
        expectedNames.add(DUMMY_STORE_NAME);
        expectedNames.add(THROWING_STORE_NAME);

        // make a set so we can compare without regards to order.
        Set nameSet = new HashSet(manager.getUserStoreNames());
        assertEquals("Constructor didn't load correct names", expectedNames, nameSet);
    }


    /**
     * Test that the <code>getUserStore</code> method returns the proper store based on its name.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserStore() throws Exception {

        // this is a store that was added from the config file
        UserStore configuredStore = manager.getUserStore(DUMMY_STORE_NAME);
        assertNotNull("getUserStore returned null", configuredStore);
        // make sure it's the right type.
        assertTrue("getUserStore returned wrong store", configuredStore instanceof DummyUserStore);

        // add one manually too
        manager.add(STORE_NAME, store);
        UserStore gottenStore = manager.getUserStore(STORE_NAME);
        assertNotNull("getUserStore returned null", gottenStore);
        // should return the same object as added
        assertSame("getUserStore returned wrong store", store, gottenStore);
    }


    /**
     * Test that <code>getUserStore</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserStoreThrowsNPE() throws Exception {

        try {
            manager.getUserStore(null);
            fail("getUserStore didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUserStore</code> throws IllegalArgumentException if name is the empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserStoreThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.getUserStore("");
            fail("getUserStore didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUserStore</code> throws UnknownUserStoreException if no user store exists
     * for the given name.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserStoreThrowsUnknownUserStoreException() throws Exception {

        try {
            // Force an UnknownUserStoreException; this store could not possibly be present
            manager.getUserStore("" + System.currentTimeMillis());
            fail("getUserStore didn't throw UnknownUserStoreException");
        } catch (UnknownUserStoreException expected) {
            assertNotNull(expected);
        }

        // we know this store isn't present
        try {
            // Force an UnknownUserStoreException
            manager.getUserStore(STORE_NAME);
            fail("getUserStore didn't throw UnknownUserStoreException");
        } catch (UnknownUserStoreException expected) {
            assertNotNull(expected);
        }

        // add it; it shouldn't fail
        manager.add(STORE_NAME, store);
        assertNotNull("getUserStore returned null for newly added store", manager.getUserStore(STORE_NAME));
    }
}
