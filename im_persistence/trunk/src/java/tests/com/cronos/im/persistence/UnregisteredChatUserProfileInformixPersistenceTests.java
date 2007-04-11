/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.DuplicateProfileException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>UnregisteredChatUserProfileInformixPersistence</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class UnregisteredChatUserProfileInformixPersistenceTests extends TestCase {
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
     * An <code>UnregisteredChatUserProfileInformixPersistence</code> instance to use for the test. This is
     * initialized in {@link #setUp setUp} to be a new instance for each test.
     */
    private UnregisteredChatUserProfileInformixPersistence ucupip;

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

        ucupip = new UnregisteredChatUserProfileInformixPersistence();

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
        ucupip = null;

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
        connection.prepareStatement("DELETE FROM client").executeUpdate();
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_null_arg1() throws Exception {
        try {
            new UnregisteredChatUserProfileInformixPersistence(null);
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
            new UnregisteredChatUserProfileInformixPersistence("  ");
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
            new UnregisteredChatUserProfileInformixPersistence("bad.namespace");
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
            new UnregisteredChatUserProfileInformixPersistence("missing.spec.namespace");
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
            new UnregisteredChatUserProfileInformixPersistence("missing.connection.factory");
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
            new UnregisteredChatUserProfileInformixPersistence("bad.connection.factory");
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
            new UnregisteredChatUserProfileInformixPersistence("missing.connection.name");
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
            new UnregisteredChatUserProfileInformixPersistence("bad.validator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a invalid object validator (wrong type).
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_validator_wrong_type() throws Exception {
        try {
            new UnregisteredChatUserProfileInformixPersistence("invalid.validator");
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
        new UnregisteredChatUserProfileInformixPersistence(UnregisteredChatUserProfileInformixPersistence.
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
            new UnregisteredChatUserProfileInformixPersistence(null, "name", null);
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
            new UnregisteredChatUserProfileInformixPersistence(new DBConnectionFactoryImpl(), null, null);
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
            new UnregisteredChatUserProfileInformixPersistence(new DBConnectionFactoryImpl(), "  ", null);
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
        new UnregisteredChatUserProfileInformixPersistence(new DBConnectionFactoryImpl(), "name", null);
        // should succeed
    }

    // the default constructor is used by many of the other tests, so there is no need for a special test case

    /**
     * Tests that the <code>createProfile</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> profile.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfile_null_arg1() throws Exception {
        try {
            ucupip.createProfile(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>createProfile</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfile() throws Exception {
        ChatUserProfile profile = new ChatUserProfile("1", "type");
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "George");
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "Bush");
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "george@whitehouse.gov");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "President");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "United States");

        ucupip.createProfile(profile);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM client");
        ResultSet results = statement.executeQuery();

        assertTrue("there should be a row in the table", results.next());
        assertEquals("first_name should be George", "George", results.getString("first_name"));
        assertEquals("last_name should be George", "Bush", results.getString("last_name"));
        assertEquals("company should be United States", "United States", results.getString("company"));
        assertEquals("title should be President", "President", results.getString("title"));
        assertEquals("email should be george@whitehouse.gov", "george@whitehouse.gov", results.getString("email"));

        results.close();
        statement.close();
    }

    /**
     * Tests that <code>createProfile</code> throws <code>DuplicateProfileException</code> when the profile already
     * exists.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfile_duplicate() throws Exception {
        ChatUserProfile profile = new ChatUserProfile("1", "type");
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "George");
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "Bush");
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "george@whitehouse.gov");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "President");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "United States");

        ucupip.createProfile(profile);
        // first one should succeed

        try {
            ucupip.createProfile(profile);
            fail("should have thrown DuplicateProfileException");
        } catch (DuplicateProfileException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>createProfile</code> throws <code>IllegalArgumentException</code> when the username is not
     * a long.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfile_bad_username() throws Exception {
        ChatUserProfile profile = new ChatUserProfile("oh no", "type");

        try {
            ucupip.createProfile(profile);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>createProfile</code> throws <code>IllegalArgumentException</code> when the username is not
     * a non-negative long.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_createProfile_negative_username() throws Exception {
        ChatUserProfile profile = new ChatUserProfile("-1", "type");

        try {
            ucupip.createProfile(profile);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>deleteProfile</code> method.
     */
    public void test_deleteProfile() {
        try {
            ucupip.deleteProfile("user");
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>updateProfile</code> method.
     */
    public void test_updateProfile() {
        try {
            ucupip.updateProfile(new ChatUserProfile("1", "type"));
        } catch (UnsupportedOperationException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getProfile</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> usnermae.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfile_null_arg1() throws Exception {
        try {
            ucupip.getProfile(null);
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
            ucupip.getProfile("  ");
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
        ChatUserProfile profile = new ChatUserProfile("1", "type");
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "George");
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "Bush");
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "george@whitehouse.gov");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "President");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "United States");

        ucupip.createProfile(profile);

        profile = ucupip.getProfile("1");
        assertEquals("profile name should be 1", "1", profile.getUsername());
        assertEquals("profile type should be unregistered", "Unregistered", profile.getType());
    }

    /**
     * Tests that <code>getProfile</code> throws <code>ProfileNotFoundException</code> when the profile is not found.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfile_not_found() throws Exception {
        try {
            ucupip.getProfile("2");
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
            ucupip.getProfiles(null);
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
            ucupip.getProfiles(new String[] {null});
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
            ucupip.getProfiles(new String[] {"  "});
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
        ChatUserProfile profile = new ChatUserProfile("1", "type");
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "George");
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "Bush");
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "george@whitehouse.gov");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "President");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "United States");

        ucupip.createProfile(profile);

        ChatUserProfile[] profiles = ucupip.getProfiles(new String[] {"2", "1", "3"});
        assertEquals("there should be three profiles", 3, profiles.length);
        assertNull("the first profile should be null", profiles[0]);
        assertEquals("the second profile should be 1", "1", profiles[1].getUsername());
        assertNull("the third profile should be null", profiles[2]);
    }

    /**
     * Tests that <code>getProfiles</code> throws <code>ProfileNotFoundException</code> when none of the profiles
     * are found.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getProfiles_none_found() throws Exception {
        try {
            ucupip.getProfiles(new String[] {"1", "2", "3"});
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
            ucupip.searchProfiles(null, new String[] {"1"});
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
            ucupip.searchProfiles(m, new String[] {"1"});
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
            ucupip.searchProfiles(m, new String[] {"1"});
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
            ucupip.searchProfiles(m, new String[] {"1"});
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
            ucupip.searchProfiles(m, new String[] {"1"});
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
            ucupip.searchProfiles(new HashMap(), null);
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
            ucupip.searchProfiles(new HashMap(), new String[] {null});
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
            ucupip.searchProfiles(new HashMap(), new String[] {"  "});
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
        ChatUserProfile profile1 = createProfile("1", "George", "Bush");
        ChatUserProfile profile2 = createProfile("2", "Laura", "Bush");
        ChatUserProfile profile3 = createProfile("3", "Jeb", "Bush");
        ChatUserProfile profile4 = createProfile("4", "Dick", "Cheney");
        ucupip.createProfile(profile1);
        ucupip.createProfile(profile2);
        ucupip.createProfile(profile3);
        ucupip.createProfile(profile4);

        Map criteria = new HashMap();
        List first = new ArrayList();
        first.add("George");
        first.add("Laura");
        criteria.put(UserDefinedAttributeNames.FIRST_NAME, first);
        criteria.put(UserDefinedAttributeNames.LAST_NAME, "Bush");

        ChatUserProfile[] profiles = ucupip.searchProfiles(criteria, new String[] {"1", "2", "3", "4", "5"});
        assertEquals("there should be 2 profiles returned", 2, profiles.length);
        String name1 = profiles[0].getUsername();
        String name2 = profiles[1].getUsername();
        assertTrue("the names should be 1 and 2", (name1.equals("1") && name2.equals("2"))
                   || (name1.equals("2") && name2.equals("1")));
    }

    /**
     * Tests that <code>searchProfiles</code> returns an empty array when the username array is empty.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_searchProfiles_empty_array() throws Exception {
        assertEquals("zero profiles should be returned", 0,
                     ucupip.searchProfiles(new HashMap(), new String[0]).length);
    }

    /**
     * Returns a <code>ChatUserProfile</code> with the specified username, first name, and last name.
     *
     * @param username the username
     * @param first the first name
     * @param last the last name
     * @return a <code>ChatUserProfile</code> with the specified username, first name, and last name
     */
    private static ChatUserProfile createProfile(String username, String first, String last) {
        ChatUserProfile profile = new ChatUserProfile(username, "type");
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, first);
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, last);
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "email");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "title");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "company");

        return profile;
    }
}
