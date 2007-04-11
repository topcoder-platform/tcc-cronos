/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ProfileNotFoundException;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>RegisteredChatUserProfileInformixPersistence</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RegisteredChatUserProfileInformixPersistenceTests extends TestCase {
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
     * An <code>RegisteredChatUserProfileInformixPersistence</code> instance to use for the test. This is
     * initialized in {@link #setUp setUp} to be a new instance for each test.
     */
    private RegisteredChatUserProfileInformixPersistence rcupip;

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

        rcupip = new RegisteredChatUserProfileInformixPersistence();

        connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").
            createConnection();

        if (firstTime) {
            firstTime = false;
            clearAllTables();
        }

        populateTables();
    }

    /**
     * Populates the relevant tables with some test data.
     *
     * @throws Exception if an error occurs
     */
    private void populateTables() throws Exception {
        // create some users for the test
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO user (user_id, first_name, last_name, handle) "
                                        + "VALUES (?, ?, ?, ?)");
        statement.setLong(1, 1);
        statement.setString(2, "George");
        statement.setString(3, "Bush");
        statement.setString(4, "gwbush");
        statement.executeUpdate();

        statement.setLong(1, 2);
        statement.setString(2, "Laura");
        statement.setString(4, "lbush");
        statement.executeUpdate();

        statement.setLong(1, 3);
        statement.setString(2, "Britney");
        statement.setString(3, "Spears");
        statement.setString(4, "bspears");
        statement.executeUpdate();
        statement.close();

        // add some email addresses
        statement = connection.prepareStatement("INSERT INTO email (user_id, address) VALUES (?, ?)");
        statement.setLong(1, 1);
        statement.setString(2, "gwbush@whitehouse.gov");
        statement.executeUpdate();

        statement.setLong(1, 3);
        statement.setString(2, "bspears@hotmail.com");
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Per-test cleanup: clears the tables, closes the connection, and resets the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        rcupip = null;

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
        connection.prepareStatement("DELETE FROM user").executeUpdate();
        connection.prepareStatement("DELETE FROM email").executeUpdate();
        connection.prepareStatement("DELETE FROM company").executeUpdate();
        connection.prepareStatement("DELETE FROM contact").executeUpdate();
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_null_arg1() throws Exception {
        try {
            new RegisteredChatUserProfileInformixPersistence(null);
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
            new RegisteredChatUserProfileInformixPersistence("  ");
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
            new RegisteredChatUserProfileInformixPersistence("bad.namespace");
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
            new RegisteredChatUserProfileInformixPersistence("missing.spec.namespace");
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
            new RegisteredChatUserProfileInformixPersistence("missing.connection.factory");
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
            new RegisteredChatUserProfileInformixPersistence("bad.connection.factory");
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
            new RegisteredChatUserProfileInformixPersistence("missing.connection.name");
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
        new RegisteredChatUserProfileInformixPersistence(RegisteredChatUserProfileInformixPersistence.
                                                         DEFAULT_NAMESPACE);
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
            new RegisteredChatUserProfileInformixPersistence(null, "name");
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
            new RegisteredChatUserProfileInformixPersistence(new DBConnectionFactoryImpl(), null);
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
            new RegisteredChatUserProfileInformixPersistence(new DBConnectionFactoryImpl(), "  ");
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
        new RegisteredChatUserProfileInformixPersistence(new DBConnectionFactoryImpl(), "name");
        // should succeed
    }

    // the default constructor is used by many of the other tests, so there is no need for a special test case

    /**
     * Tests the <code>createProfile</code> method.
     */
    public void test_createProfile() {
        try {
            rcupip.createProfile(new ChatUserProfile("1", "type"));
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>deleteProfile</code> method.
     */
    public void test_deleteProfile() {
        try {
            rcupip.deleteProfile("username");
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>updateProfile</code> method.
     */
    public void test_updateProfile() {
        try {
            rcupip.updateProfile(new ChatUserProfile("1", "type"));
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getProfile</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> username.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfile_null_arg1() throws Exception {
        try {
            rcupip.getProfile(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getProfile</code> method throws <code>IllegalArgumentException</code> when passed an
     * empty username.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfile_empty_arg1() throws Exception {
        try {
            rcupip.getProfile("  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getProfile</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfile() throws Exception {
        ChatUserProfile profile = rcupip.getProfile("gwbush");

        assertEquals("first name should be George", "George",
                     profile.getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("last name should be Bush", "Bush",
                     profile.getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("email should be gwbush@whitehouse.gov", "gwbush@whitehouse.gov",
                     profile.getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);
    }

    /**
     * Tests that <code>getProfile</code> throws <code>ProfileNotFoundException</code> when the profile is not found.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfile_not_found() throws Exception {
        try {
            rcupip.getProfile("no such user");
            fail("should have thrown ProfileNotFoundException");
        } catch (ProfileNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getProfiles</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> username array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfiles_null_arg1() throws Exception {
        try {
            rcupip.getProfiles(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfiles</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfiles_null_element() throws Exception {
        try {
            rcupip.getProfiles(new String[] {null});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProfiles</code> throws <code>IllegalArgumentException</code> when passed an empty element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfiles_empty_element() throws Exception {
        try {
            rcupip.getProfiles(new String[] {"  "});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getProfiles</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfiles() throws Exception {
        ChatUserProfile[] profile = rcupip.getProfiles(new String[] {"gwbush", "ivern", "bspears"});

        assertEquals("the first profile should be gwbush", "gwbush", profile[0].getUsername());
        assertNull("the second profile should be null", profile[1]);
        assertEquals("the third profile should be bspears", "bspears", profile[2].getUsername());
    }

    /**
     * Tests that the <code>getProfiles</code> method throws <code>ProfileNotFoundException</code> when none of the
     * profiles can be found.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfiles_none_found() throws Exception {
        try {
            rcupip.getProfiles(new String[] {"no such user"});
            fail("should have thrown ProfileNotFoundException");
        } catch (ProfileNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>searchProfiles</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> criteria map.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_null_arg1() throws Exception {
        try {
            rcupip.searchProfiles(null, new String[] {"1"});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>searchProfiles</code> throws <code>IllegalArgumentException</code> when passed a map
     * containing a <code>null</code> value.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_arg1_null_value() throws Exception {
        Map m = new HashMap();
        m.put("1", null);
        try {
            rcupip.searchProfiles(m, new String[] {"1"});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>searchProfiles</code> throws <code>IllegalArgumentException</code> when passed a map
     * containing an empty value.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_arg1_empty() throws Exception {
        Map m = new HashMap();
        m.put("1", "  ");
        try {
            rcupip.searchProfiles(m, new String[] {"1"});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>searchProfiles</code> throws <code>IllegalArgumentException</code> when passed a map
     * containing a <code>null</code> value.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_arg1_list_contains_null() throws Exception {
        Map m = new HashMap();
        List l = new ArrayList();
        l.add(null);
        m.put("1", l);
        try {
            rcupip.searchProfiles(m, new String[] {"1"});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>searchProfiles</code> throws <code>IllegalArgumentException</code> when passed a map
     * containing an empty value.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_arg1_list_contains_empty() throws Exception {
        Map m = new HashMap();
        List l = new ArrayList();
        l.add("  ");
        m.put("1", l);
        try {
            rcupip.searchProfiles(m, new String[] {"1"});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>searchProfiles</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> username array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_null_arg2() throws Exception {
        try {
            rcupip.searchProfiles(new HashMap(), null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>searchProfiles</code> throws <code>IllegalArgumentException</code> when passed a null array
     * element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_arg2_null_element() throws Exception {
        try {
            rcupip.searchProfiles(new HashMap(), new String[] {null});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>searchProfiles</code> throws <code>IllegalArgumentException</code> when passed an empty
     * array element.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_arg2_empty_element() throws Exception {
        try {
            rcupip.searchProfiles(new HashMap(), new String[] {"  "});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>searchProfiles</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles() throws Exception {
        Map criteria = new HashMap();
        List first = new ArrayList();
        first.add("George");
        first.add("Britney");
        criteria.put(UserDefinedAttributeNames.FIRST_NAME, first);
        criteria.put(UserDefinedAttributeNames.LAST_NAME, "Bush");

        ChatUserProfile[] profiles = rcupip.searchProfiles(criteria,
                                                           new String[] {"gwbush", "lbush", "bspears", "ivern"});
        assertEquals("one profile should be returned", 1, profiles.length);
        assertEquals("the profile should be gwbush", "gwbush", profiles[0].getUsername());
    }

    /**
     * Tests that <code>searchProfiles</code> returns an empty array when the username array is empty.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_empty_array() throws Exception {
        assertEquals("zero profiles should be returned", 0,
                     rcupip.searchProfiles(new HashMap(), new String[0]).length);
    }
}
