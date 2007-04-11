/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.stresstests;

import java.sql.Connection;
import java.util.Iterator;

import com.cronos.im.persistence.InformixProfileKeyManager;
import com.topcoder.chat.user.profile.ProfileKey;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixProfileKeyManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestInformixProfileKeyManagerStressWithAllGet extends TestCase {

    /**
     * Represents the times for calling setup.
     */
    private static int countSetup = 0;

    /**
     * Represents the times for calling tearDown.
     */
    private static int countTearDown = 0;

    /**
     * Represents the InformixProfileKeyManager instance for testing.
     */
    private InformixProfileKeyManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        manager = new InformixProfileKeyManager(DBUtil.getDBConnectionFactory(), "sysuser", DBUtil
                .getIDGenerator("stress"), null);
        countSetup++;

        if (countSetup == 1) {
            Connection connection = DBUtil.getConnection();
            DBUtil.clearTableForProfileKeyManager(connection);

            for (int i = 1; i <= 100; i++) {
                DBUtil.insertRecordIntoAll_User(connection, i, "Y");
            }

            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        countTearDown++;

        if (countTearDown == 6) {
            Connection connection = DBUtil.getConnection();
            DBUtil.clearTableForProfileKeyManager(connection);
            DBUtil.closeConnection(connection);
        }

        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test method <code>ProfileKey getProfileKey(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfileKeylong() throws Exception {
        Connection connection = DBUtil.getConnection();
        ProfileKey key = new ProfileKey("username", "Registered");

        try {
            ProfileKey ret = manager.createProfileKey(key);

            long start = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                ProfileKey profileKey = manager.getProfileKey(ret.getId());
            }

            long end = System.currentTimeMillis();

            System.out.println("Calling getProfileKey for 20 times cost " + (end - start) / 1000.0);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Test method <code>ProfileKey[] getProfileKeys(long[] ids) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfileKeyslongArray() throws Exception {
        long[] ids = new long[1000];
        for (int i = 1; i < ids.length; i++) {
            ids[i] = i;
        }

        long start = System.currentTimeMillis();
        ProfileKey[] keys = manager.getProfileKeys(ids);

        long end = System.currentTimeMillis();

        System.out.println("calling getProfileKeys with 1000 size big array cost " + (end - start) / 1000.0);

        assertEquals("Equal is expected.", 1000, keys.length);
    }

    /**
     * Test method <code>ProfileKey getProfileKey(String username, String type) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfileKeyStringString() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            ProfileKey key = manager.getProfileKey("name3", "Registered");

        }

        long end = System.currentTimeMillis();
        System.out
                .println("Calling getProfileKey with username and type for 20 times cost " + (end - start) / 1000.0);
    }

    /**
     * Test method <code>ProfileKey[] getProfileKeys(String[] usernames, String type) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfileKeysStringArrayString() throws Exception {
        String[] usernames = new String[1000];
        for (int i = 0; i < usernames.length; i++) {
            usernames[i] = "name" + (i + 1);
        }

        long start = System.currentTimeMillis();
        ProfileKey[] keys = manager.getProfileKeys(usernames, "Registered");

        long end = System.currentTimeMillis();

        System.out.println("calling getProfileKeys with 1000 length usernames cost " + (end - start) / 1000.0);
        assertEquals("Equal is expected.", 1000, keys.length);
    }

    /**
     * Test method <code>ProfileKey[] getProfileKeys(String type) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfileKeysString() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            ProfileKey[] keys = manager.getProfileKeys("Registered");

        }

        long end = System.currentTimeMillis();

        System.out.println("Calling getProfileKeys with type 20 times cost " + (end - start) / 1000.0);
    }

    /**
     * Test method <code>ProfileKey[][] getProfileKeys(String[] types) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetProfileKeysStringArray() throws Exception {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            ProfileKey[][] keys = manager.getProfileKeys(new String[] { "Registered" });

        }
        long end = System.currentTimeMillis();

        System.out.println("Calling getProfileKeys with types 20 times cost " + (end - start) / 1000.0);
    }

}