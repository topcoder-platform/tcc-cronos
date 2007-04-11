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
public class TestInformixProfileKeyManagerStressWithCreate extends TestCase {

    /**
     * Represents the InformixProfileKeyManager instance for testing.
     */
    private InformixProfileKeyManager manager = null;

    /**
     * Represents the ProfileKey instances returned after invoking createProfileKey method.
     */
    private ProfileKey[] ret = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        manager = new InformixProfileKeyManager(DBUtil.getDBConnectionFactory(), "sysuser", DBUtil
                .getIDGenerator("stress"), null);
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test method <code>ProfileKey createProfileKey(ProfileKey key) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testCreateProfileKey() throws Exception {
        Connection connection = DBUtil.getConnection();
        ProfileKey[] keys = new ProfileKey[100];

        ret = new ProfileKey[keys.length];

        for (int i = 0; i < keys.length; i++) {
            keys[i] = new ProfileKey("username" + (i + 1), "Registered");
        }

        try {
            long start = System.currentTimeMillis();

            for (int i = 0; i < keys.length; i++) {
                ret[i] = manager.createProfileKey(keys[i]);
            }

            long end = System.currentTimeMillis();

            System.out.println("Calling createProfileKey for " + keys.length + " times cost " + (end - start)
                    / 1000.0 + " .seconds.");

            for (int i = 0; i < keys.length; i++) {
                assertEquals("Equal is expected.", keys[i].getUsername(), ret[i].getUsername());
                assertEquals("Equal is expected.", keys[i].getType(), ret[i].getType());
            }
        } finally {
            DBUtil.clearTableForProfileKeyManager(connection);
            DBUtil.closeConnection(connection);
        }
    }
}