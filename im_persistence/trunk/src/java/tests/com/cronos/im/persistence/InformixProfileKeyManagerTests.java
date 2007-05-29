/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.DuplicateProfileKeyException;
import com.topcoder.chat.user.profile.ProfileKey;
import com.topcoder.chat.user.profile.ProfileKeyNotFoundException;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.idgenerator.IDGeneratorFactory;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>InformixProfileKeyManager</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class InformixProfileKeyManagerTests extends TestCase {
    /**
     * The configuration manager.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * An database connection to use for the test. This is initialized in {@link #setUp setUp} to be a new instance
     * for each test.
     */
    private Connection connection;

    /**
     * An indication of whether this is the first test that is run.
     */
    private boolean firstTime = true;

    /**
     * An <code>InformixProfileKeyManager</code> instance to use for the test. This is
     * initialized in {@link #setUp setUp} to be a new instance for each test.
     */
    private InformixProfileKeyManager ipkm;

    /**
     * Pre-test setup: loads the configuration, populates the tables, and creates the per-test variables.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        if (firstTime) {
            // clean up in case a previous test left things in a bad state
            removeAllNamespaces();
        }

        MANAGER.add("test.xml");

        ipkm = new InformixProfileKeyManager();

        connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").
            createConnection();

        if (firstTime) {
            firstTime = false;
            clearAllTables();
        }
    }

    /**
     * Per-test cleanup: clears the tables, closes the connection, and resets the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        ipkm = null;

        removeAllNamespaces();
        clearAllTables();

        connection.close();
        connection = null;
    }

    /**
     * Removes all namespaces from the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void removeAllNamespaces() throws Exception {
        for (Iterator it = MANAGER.getAllNamespaces(); it.hasNext();) {
            MANAGER.removeNamespace((String) it.next());
        }
    }

    /**
     * Clears all tables used by this test suite.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void clearAllTables() throws Exception {
        connection.prepareStatement("DELETE FROM all_user").executeUpdate();
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_null_arg1() throws Exception {
        try {
            new InformixProfileKeyManager(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed an empty namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_empty_arg1() throws Exception {
        try {
            new InformixProfileKeyManager("  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a bad namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_namespace() throws Exception {
        try {
            new InformixProfileKeyManager("bad.namespace");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing specification namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_spec_namespace() throws Exception {
        try {
            new InformixProfileKeyManager("missing.spec.namespace");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_connection_factory() throws Exception {
        try {
            new InformixProfileKeyManager("missing.connection.factory");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing an invalid connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_connection_factory() throws Exception {
        try {
            new InformixProfileKeyManager("bad.connection.factory");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_connection_name() throws Exception {
        try {
            new InformixProfileKeyManager("missing.connection.name");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a invalid object validator.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_validator() throws Exception {
        try {
            new InformixProfileKeyManager("bad.validator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests the first constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1() throws Exception {
        new InformixProfileKeyManager(InformixProfileKeyManager.DEFAULT_NAMESPACE);
        // should succeed
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_null_arg1() throws Exception {
        try {
            new InformixProfileKeyManager(null, "name", IDGeneratorFactory.getIDGenerator("generator"), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_null_arg2() throws Exception {
        try {
            new InformixProfileKeyManager(new DBConnectionFactoryImpl(), null,
                                          IDGeneratorFactory.getIDGenerator("generator"), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed an empty
     * connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_empty_arg2() throws Exception {
        try {
            new InformixProfileKeyManager(new DBConnectionFactoryImpl(), "  ",
                                          IDGeneratorFactory.getIDGenerator("generator"), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the second constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2() throws Exception {
        new InformixProfileKeyManager(new DBConnectionFactoryImpl(), "name",
                                      IDGeneratorFactory.getIDGenerator("generator"), null);
        // should succeed
    }

    // the default constructor is used by many of the other tests, so there is no need for a special test case

    /**
     * Tests that the <code>createProfileKey</code> method throws <code>IllegalArgumentException</code> when passed
     * a <code>null</code> key.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfileKey_null_arg1() throws Exception {
        try {
            ipkm.createProfileKey(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>createProfileKey</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfileKey() throws Exception {
        ProfileKey key = ipkm.createProfileKey(new ProfileKey("username", "Registered"));

        assertEquals("username should be username", "username", key.getUsername());
        assertEquals("type should be Registered", "Registered", key.getType());
    }

    /**
     * Tests that <code>createProfileKey</code> throws <code>DuplicateProfileKeyException</code> when the key
     * already exists.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfileKey_duplicate() throws Exception {
        ipkm.createProfileKey(new ProfileKey("username", "Registered"));
        try {
            ipkm.createProfileKey(new ProfileKey("username", "Registered"));
            fail("should have thrown DuplicateProfileKeyException");
        } catch (DuplicateProfileKeyException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>deleteProfileKey</code> throws <code>IllegalArgumentException</code> when passed a negative
     * ID.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteProfileKey_negative() throws Exception {
        try {
            ipkm.deleteProfileKey(-1);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>deleteProfileKey</code> throws <code>ProfileKeyNotFoundException</code> when the key does
     * not exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteProfileKey_not_found() throws Exception {
        try {
            ipkm.deleteProfileKey(5);
            fail("should have thrown ProfileKeyNotFoundException");
        } catch (ProfileKeyNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of <code>deleteProfileKey</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_deleteProfileKey() throws Exception {
        long id = ipkm.createProfileKey(new ProfileKey("username", "Registered")).getId();

        assertNotNull("the profile should exist", ipkm.getProfileKey(id));

        ipkm.deleteProfileKey(id);

        assertNull("the profile should no longer exist", ipkm.getProfileKey(id));
    }

    /**
     * Tests that <code>getProfileKey(long)</code> throws <code>IllegalArgumentException</code> when passed a
     * negative ID.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey_negative() throws Exception {
        try {
            ipkm.getProfileKey(-1);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    // the normal operaiton is getProfileKey(long) is tested above, so no need for a special test

    /**
     * Tests that <code>getProfileKeys(long[])</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys_null() throws Exception {
        try {
            ipkm.getProfileKeys((long[]) null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(long[])</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> array element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys_negative_element() throws Exception {
        try {
            ipkm.getProfileKeys(new long[] {1, -1});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of <code>getProfileKeys(long[])</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys() throws Exception {
        long id = ipkm.createProfileKey(new ProfileKey("user", "Registered")).getId();
        long id2 = ipkm.createProfileKey(new ProfileKey("user2", "Registered")).getId();

        ProfileKey[] keys = ipkm.getProfileKeys(new long[] {id, id2, id + id2 + 1});
        assertEquals("three keys should be returned", 3, keys.length);
        assertEquals("the first key should be user", "user", keys[0].getUsername());
        assertEquals("the second key should be user2", "user2", keys[1].getUsername());
        assertNull("the third key should be null", keys[2]);
    }

    /**
     * Tests that <code>getProfileKey(String, String)</code> throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> username.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey2_null_username() throws Exception {
        try {
            ipkm.getProfileKey(null, "Registered");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKey(String, String)</code> throws <code>IllegalArgumentException</code> when
     * passed an empty username.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey2_empty_username() throws Exception {
        try {
            ipkm.getProfileKey("  ", "Registered");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKey(String, String)</code> throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey2_null_type() throws Exception {
        try {
            ipkm.getProfileKey("username", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKey(String, String)</code> throws <code>IllegalArgumentException</code> when
     * passed an empty type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey2_empty_type() throws Exception {
        try {
            ipkm.getProfileKey("username", "   ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKey(String, String)</code> returns <code>null</code> when the profile key does
     * not exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey2_not_found() throws Exception {
        assertNull("getProfileKey should return null", ipkm.getProfileKey("ivern", "Registered"));

    }

    /**
     * Tests the normal operation of <code>getProfileKey(String, String)</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKey2() throws Exception {
        ipkm.createProfileKey(new ProfileKey("ivern", "Registered"));

        ProfileKey key = ipkm.getProfileKey("ivern", "Registered");
        assertNotNull("the profile key should not be null", key);
    }

    /**
     * Tests that <code>getProfileKeys(String[], String)</code> throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> usernames array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2_null_usernames() throws Exception {
        try {
            ipkm.getProfileKeys(null, "Registered");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[], String)</code> throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> username.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2_null_username() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {null}, "Registered");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[], String)</code> throws <code>IllegalArgumentException</code> when
     * passed an empty username.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2_empty_username() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {"  "}, "Registered");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[], String)</code> throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2_null_type() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {"username"}, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[], String)</code> throws <code>IllegalArgumentException</code> when
     * passed an empty type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2_empty_type() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {"username"}, "  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[], String)</code> returns <code>null</code> when none of the keys
     * exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2_none_found() throws Exception {
        assertNull("getProfileKeys should return null", ipkm.getProfileKeys(new String[] {"username"}, "Registered"));
    }

    /**
     * Tests the normal operation of <code>getProfileKeys(String[], String)</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys2() throws Exception {
        ipkm.createProfileKey(new ProfileKey("ivern", "Registered"));
        ipkm.createProfileKey(new ProfileKey("mess", "Registered"));
        ipkm.createProfileKey(new ProfileKey("pops", "Registered"));
        ipkm.createProfileKey(new ProfileKey("ivern", "Unregistered"));

        ProfileKey[] keys = ipkm.getProfileKeys(new String[] {"ivern", "mess"}, "Registered");
        assertEquals("two keys should be returned", 2, keys.length);
        String name1 = keys[0].getUsername();
        String name2 = keys[1].getUsername();

        assertTrue("the names should be ivern and mess",
                   (name1.equals("ivern") && name2.equals("mess"))
                   || (name1.equals("mess") && name2.equals("ivern")));
    }

    /**
     * Tests that <code>getProfileKeys(String)</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys3_null_type() throws Exception {
        try {
            ipkm.getProfileKeys((String) null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String)</code> throws <code>IllegalArgumentException</code> when passed an
     * empty type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys3_empty_type() throws Exception {
        try {
            ipkm.getProfileKeys("   ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String)</code> throws <code>ProfileKeyNotFoundException</code> when no keys
     * exist for the type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys3_none_found() throws Exception {
        try {
            ipkm.getProfileKeys("Registered");
            fail("should have thrown ProfileKeyNotFoundException");
        } catch (ProfileKeyNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of <code>getProfileKeys(String)</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys3() throws Exception {
        ipkm.createProfileKey(new ProfileKey("ivern", "Registered"));
        ipkm.createProfileKey(new ProfileKey("mess", "Registered"));
        ipkm.createProfileKey(new ProfileKey("Pops", "Unregistered"));

        ProfileKey[] keys = ipkm.getProfileKeys("Registered");

        assertEquals("keys should return 2 items", 2, keys.length);
        String name1 = keys[0].getUsername();
        String name2 = keys[1].getUsername();

        assertTrue("the names should be ivern and mess",
                   (name1.equals("ivern") && name2.equals("mess"))
                   || (name1.equals("mess") && name2.equals("ivern")));
    }

    /**
     * Tests that <code>getProfileKeys(String[])</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> type array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys4_null_types() throws Exception {
        try {
            ipkm.getProfileKeys((String[]) null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[])</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys4_null_type() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {null});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[])</code> throws <code>IllegalArgumentException</code> when passed an
     * empty type.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys4_empty_type() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {"  "});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfileKeys(String[])</code> throws <code>ProfileKeyNotFoundException</code> when no
     * profiles exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys4_none_found() throws Exception {
        try {
            ipkm.getProfileKeys(new String[] {"Registered"});
            fail("should have thrown ProfileKeyNotFoundException");
        } catch (ProfileKeyNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of <code>getProfileKeys(String[])</code>.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfileKeys4() throws Exception {
        ipkm.createProfileKey(new ProfileKey("ivern", "Registered"));
        ipkm.createProfileKey(new ProfileKey("mess", "Registered"));
        ProfileKey key = ipkm.createProfileKey(new ProfileKey("Pops", "Unregistered"));

        ProfileKey[][] keys = ipkm.getProfileKeys(new String[] {"Registered", "Unregistered"});
        assertEquals("there should be two arrays", 2, keys.length);
        assertEquals("the first array should have 2 keys", 2, keys[0].length);
        String name1 = keys[0][0].getUsername();
        String name2 = keys[0][1].getUsername();

        assertTrue("the names should be ivern and mess",
                   (name1.equals("ivern") && name2.equals("mess"))
                   || (name1.equals("mess") && name2.equals("ivern")));

        assertEquals("the second array should have 1 key", 1, keys[1].length);
        assertEquals("the key should be " + key.getUsername(), key.getUsername(), keys[1][0].getUsername());
    }
}
