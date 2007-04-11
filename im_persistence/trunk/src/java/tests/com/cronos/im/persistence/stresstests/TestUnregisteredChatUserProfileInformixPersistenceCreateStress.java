/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.stresstests;

import java.sql.Connection;
import java.util.Iterator;

import com.cronos.im.persistence.UnregisteredChatUserProfileInformixPersistence;
import com.cronos.im.persistence.UserDefinedAttributeNames;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class UnregisteredChatUserProfileInformixPersistence#createChatUserProfile().
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestUnregisteredChatUserProfileInformixPersistenceCreateStress extends TestCase {

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
    }

    /**
     * Clear all the namespace in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        Connection connection = DBUtil.getConnection();

        connection.createStatement().executeUpdate("delete from client");

        DBUtil.closeConnection(connection);

        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test method <code>void createProfile(ChatUserProfile profile) </code>.
     *
     * @throws Exception
     *             to juint.
     */
    public void testCreateProfile() throws Exception {
        ChatUserProfile[] profiles = new ChatUserProfile[100];

        for (int i = 0; i < profiles.length; i++) {
            profiles[i] = new ChatUserProfile(String.valueOf(i + 1), "type");

            profiles[i].setProperty(UserDefinedAttributeNames.FIRST_NAME, "FirstName" + (i + 1));
            profiles[i].setProperty(UserDefinedAttributeNames.LAST_NAME, "LastName" + (i + 1));
            profiles[i].setProperty(UserDefinedAttributeNames.EMAIL, "email" + (i + 1) + "@topcoder.com");
            profiles[i].setProperty(UserDefinedAttributeNames.TITLE, "Titel" + (i + 1));
            profiles[i].setProperty(UserDefinedAttributeNames.COMPANY, "company" + (i + 1));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < profiles.length; i++) {
            persistence.createProfile(profiles[i]);
        }

        long end = System.currentTimeMillis();

        System.out.println("Create " + profiles.length + " ChatUserProfile instances cost " + (end - start) / 1000.0);
    }
}