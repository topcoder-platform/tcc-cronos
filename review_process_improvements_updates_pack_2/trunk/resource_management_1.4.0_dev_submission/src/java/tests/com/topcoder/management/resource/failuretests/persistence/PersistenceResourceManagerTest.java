/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.persistence;

import java.io.File;

import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for <code>PersistenceResourceManager</code> for the added methods.
  *
 * @author morehappiness
 * @version 1.3
 */
public class PersistenceResourceManagerTest extends PersistenceResourceManagerBaseFailureTestCase {
    /**
     * The config file path for the DBConnectionFactory.
     */
    private static final String DB_CONNECTION_CONFIG = "failure" + File.separator + "ConnectionFactory.xml";

    /**
     * The config file path for the SearchBundleManager.
     */
    private static final String SEARCH_BUNDLE_MANAGER_CONFIG = "failure" + File.separator + "SearchBundleManager.xml";

    /**
     * Load configurations used for this test case.
     *
     * @throws Exception to JUnit.
     */
    protected void loadConfigurations() throws Exception {
        clearAllConfigurations();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(DB_CONNECTION_CONFIG);
        cm.add(SEARCH_BUNDLE_MANAGER_CONFIG);
    }
    /**
     * Test <code>getNotificationsForUser(long user, long notificationType)</code> if user is less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testgetNotificationsForUser1() throws Exception {
        try {
            manager.getNotificationsForUser(-1, 2);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>getNotificationsForUser(long user, long notificationType)</code> if notificationType is less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testgetNotificationsForUser2() throws Exception {
        try {
            manager.getNotificationsForUser(2, -1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>removeNotifications(long users, long[] project, long notificationType, String operator)</code> if user is less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveNotifications1() throws Exception {
        try {
            manager.removeNotifications(-1, new long[] {1}, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>removeNotifications(long users, long[] project, long notificationType, String operator)</code> if project is null,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveNotifications2() throws Exception {
        try {
            manager.removeNotifications(1, null, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>removeNotifications(long users, long[] project, long notificationType, String operator)</code> if project is empty,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveNotifications3() throws Exception {
        try {
            manager.removeNotifications(1, new long[] {}, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>removeNotifications(long users, long[] project, long notificationType, String operator)</code> if project contains element less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveNotifications4() throws Exception {
        try {
            manager.removeNotifications(1, new long[] {-1, 1}, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>removeNotifications(long users, long[] project, long notificationType, String operator)</code> if notificationType is less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveNotifications5() throws Exception {
        try {
            manager.removeNotifications(1, new long[] {1}, -1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>removeNotifications(long users, long[] project, long notificationType, String operator)</code> if operator is null,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testremoveNotifications6() throws Exception {
        try {
            manager.removeNotifications(1, new long[] {1}, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>addNotifications(long users, long[] project, long notificationType, String operator)</code> if user is less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testaddNotifications1() throws Exception {
        try {
            manager.addNotifications(-1, new long[] {1}, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>addNotifications(long users, long[] project, long notificationType, String operator)</code> if project is null,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testaddNotifications2() throws Exception {
        try {
            manager.addNotifications(1, null, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>addNotifications(long users, long[] project, long notificationType, String operator)</code> if project is empty,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testaddNotifications3() throws Exception {
        try {
            manager.addNotifications(1, new long[] {}, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>addNotifications(long users, long[] project, long notificationType, String operator)</code> if project contains element less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testaddNotifications4() throws Exception {
        try {
            manager.addNotifications(1, new long[] {-1, 1}, 1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>addNotifications(long users, long[] project, long notificationType, String operator)</code> if notificationType is less than 0,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testaddNotifications5() throws Exception {
        try {
            manager.addNotifications(1, new long[] {1}, -1, "add");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Test <code>addNotifications(long users, long[] project, long notificationType, String operator)</code> if operator is null,
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testaddNotifications6() throws Exception {
        try {
            manager.addNotifications(1, new long[] {1}, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}