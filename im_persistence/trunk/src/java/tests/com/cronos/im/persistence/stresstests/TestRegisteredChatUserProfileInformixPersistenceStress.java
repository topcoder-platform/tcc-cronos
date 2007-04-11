/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.stresstests;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cronos.im.persistence.RegisteredChatUserProfileInformixPersistence;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>RegisteredChatUserProfileInformixPersistence </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestRegisteredChatUserProfileInformixPersistenceStress extends TestCase {

    /**
     * Represents the times for calling setup.
     */
    private static int countSetup = 0;

    /**
     * Represents the times for calling teardown.
     */
    private static int countTeardown = 0;

    /**
     * Represents the RegisteredChatUserProfileInformixPersistence instance for testing.
     */
    private RegisteredChatUserProfileInformixPersistence persistence = null;

    /**
     * Set up.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        persistence = new RegisteredChatUserProfileInformixPersistence(DBUtil.getDBConnectionFactory(), "sysuser");

        countSetup++;

        if (countSetup == 1) {
            // setup the database.
            Connection connection = DBUtil.getConnection();
            for (int i = 1; i <= 50; i++) {
                DBUtil.insertRecordIntoUser(connection, i);
            }

            for (int i = 1; i <= 50; i++) {
                DBUtil.insertRecordIntoEmail(connection, i);
            }

            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Clear all the namespace in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        countTeardown++;

        if (countTeardown == 4) {
            Connection connection = DBUtil.getConnection();

            connection.createStatement().executeUpdate("delete from user");
            connection.createStatement().executeUpdate("delete from email");
            connection.createStatement().executeUpdate("delete from company");
            connection.createStatement().executeUpdate("delete from contact");

            DBUtil.closeConnection(connection);

        }
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test method <code>ChatUserProfile getProfile(String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfile() throws Exception {
        long start = System.currentTimeMillis();
        ChatUserProfile profile = null;
        for (int i = 1; i <= 50; i++) {
            profile = persistence.getProfile("handle" + i);
        }

        long end = System.currentTimeMillis();

        System.out.println("call 50 times for getProfile(username) cost " + (end - start) / 1000.0);

        assertNotNull("should not be null.", profile);
    }

    /**
     * Test method <code>ChatUserProfile[] getProfiles(String[] usernames) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfiles() throws Exception {
        String[] usernames = new String[1000];

        for (int i = 0; i < usernames.length; i++) {
            usernames[i] = "handle" + i;
        }

        long start = System.currentTimeMillis();

        ChatUserProfile[] profiles = null;

        profiles = persistence.getProfiles(usernames);

        long end = System.currentTimeMillis();

        System.out.println("calling getProfiles method with 1000 username array, cost " + (end - start) / 1000.0);

        assertEquals("Equal is expected.", 1000, profiles.length);
    }

    /**
     * Test method <code>ChatUserProfile[] searchProfiles(Map criteria, String[] registeredUsers) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchProfiles_1() throws Exception {
        Map criteria = new HashMap();

        String[] registeredUsers = new String[50];

        for (int i = 0; i < registeredUsers.length; i++) {
            registeredUsers[i] = "handle" + (i + 1);
        }

        long start = System.currentTimeMillis();

        ChatUserProfile[] profiles = null;

        profiles = persistence.searchProfiles(criteria, registeredUsers);

        long end = System.currentTimeMillis();

        System.out.println("calling searchProfiles with 50 registeredUsers, cost " + (end - start) / 1000.0);

        assertEquals("Equal is expected.", 50, profiles.length);
    }

    /**
     * Test method <code>ChatUserProfile[] searchProfiles(Map criteria, String[] registeredUsers) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchProfiles_2() throws Exception {
        Map criteria = new HashMap();

        String[] registeredUsers = new String[50];

        for (int i = 0; i < registeredUsers.length; i++) {
            registeredUsers[i] = "handle" + (i + 1);
        }

        long start = System.currentTimeMillis();

        ChatUserProfile[] profiles = null;

        for (int i = 0; i < 20; i++) {
            profiles = persistence.searchProfiles(criteria, registeredUsers);
        }
        long end = System.currentTimeMillis();

        System.out.println("calling searchProfiles with 50 registeredUsers for 20 times, cost " + (end - start)
                / 1000.0);

        assertEquals("Equal is expected.", 50, profiles.length);
    }

}