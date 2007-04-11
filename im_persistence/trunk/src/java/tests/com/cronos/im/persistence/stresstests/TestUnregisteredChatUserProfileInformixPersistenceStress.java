/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.stresstests;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cronos.im.persistence.UnregisteredChatUserProfileInformixPersistence;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>UnregisteredChatUserProfileInformixPersistence </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestUnregisteredChatUserProfileInformixPersistenceStress extends TestCase {

    /**
     * Represents the times for setup.
     */
    private static int countSetup = 0;

    /**
     * Represents the times for teardown.
     */
    private static int countTeardown = 0;

    /**
     * Represents the UnregisteredChatUserProfileInformixPersistence instance for testing.
     */
    private UnregisteredChatUserProfileInformixPersistence persistence = null;

    /**
     * Set up.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(DBUtil.getDBConnectionFactory(), "sysuser",
                null);

        countSetup++;

        if (countSetup == 1) {
            Connection connection = DBUtil.getConnection();
            for (int i = 1; i <= 50; i++) {
                DBUtil.insertRecordIntoClient(connection, i);
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

            connection.createStatement().executeUpdate("delete from client");

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
        ChatUserProfile profile = null;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 50; i++) {
            profile = persistence.getProfile("1");
        }

        long end = System.currentTimeMillis();

        System.out.println("Call 50 time for getProfile(username) cost " + (end - start) / 1000.0);

        assertNotNull("should not be null.", profile);

    }

    /**
     * Test method <code>ChatUserProfile[] getProfiles(String[] usernames) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfiles() throws Exception {
        String[] usernames = new String[50];
        for (int i = 0; i < usernames.length; i++) {
            usernames[i] = String.valueOf(i + 1);
        }
        long start = System.currentTimeMillis();
        ChatUserProfile[] profiles = persistence.getProfiles(usernames);

        long end = System.currentTimeMillis();

        System.out.println("Call getProfiles(usernames) with 50 user name cost" + (end - start) / 1000.0);

        assertEquals("Equal is expected.", 50, profiles.length);
    }

    /**
     * Test method <code>ChatUserProfile[] searchProfiles(Map criteria, String[] unregisteredUsers) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchProfiles_1() throws Exception {
        Map criteria = new HashMap();

        String[] unregisteredUsers = new String[50];

        for (int i = 0; i < unregisteredUsers.length; i++) {
            unregisteredUsers[i] = String.valueOf(i + 1);
        }

        long start = System.currentTimeMillis();
        ChatUserProfile[] profiles = persistence.searchProfiles(criteria, unregisteredUsers);

        long end = System.currentTimeMillis();

        System.out.println("call searchProfiles with length 50 unregisteredUsers cost " + (end - start) / 1000.0);

        assertEquals("Equal is expected.", 50, profiles.length);
    }

    /**
     * Test method <code>ChatUserProfile[] searchProfiles(Map criteria, String[] unregisteredUsers) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchProfiles_2() throws Exception {
        Map criteria = new HashMap();

        String[] unregisteredUsers = new String[50];

        for (int i = 0; i < unregisteredUsers.length; i++) {
            unregisteredUsers[i] = String.valueOf(i + 1);
        }

        long start = System.currentTimeMillis();

        ChatUserProfile[] profiles = null;
        for (int i = 0; i < 20; i++) {
            profiles = persistence.searchProfiles(criteria, unregisteredUsers);
        }
        long end = System.currentTimeMillis();

        System.out.println("call searchProfiles for 20 times cost " + (end - start) / 1000.0);

        assertEquals("Equal is expected.", 50, profiles.length);
    }
}