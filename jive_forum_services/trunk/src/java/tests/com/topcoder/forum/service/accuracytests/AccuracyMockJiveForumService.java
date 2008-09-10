/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.mock.MockJiveForumService;

/**
 * <p>
 * Accuracy tests for <code>MockJiveForumService</code> class.
 * </p>
 * @author peony
 * @version 1.1
 * @since 1.1
 */
public class AccuracyMockJiveForumService extends TestCase {
    /**
     * <p>
     * Represents for service bean.
     * </p>
     */
    private MockJiveForumService instance;

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(AccuracyMockJiveForumService.class);
    }

    /**
     * <p>
     * Setup the environment.
     * </p>
     */
    protected void setUp() {
        instance = new MockJiveForumService();
    }

    /**
     * <p>
     * Accuracy test for method <code>watch(long userId, long entityId, EntityType entityType)</code> and
     * <code>isWatched(long userId, long entityId, EntityType entityType)</code>.
     * </p>
     * <p>
     * Verify that can create the watch successfully.
     * </p>
     */
    public void testWatchAccuracy() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (EntityType type : EntityType.values()) {
                    assertFalse("watch/isWatch incorrect", instance.isWatched(i, j, type));
                    instance.watch(i, j, type);
                    assertTrue("watch/isWatch incorrect", instance.isWatched(i, j, type));
                }
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                for (EntityType type : EntityType.values()) {
                    assertTrue("watch/isWatch incorrect", instance.isWatched(i, j, type));
                    instance.watch(i, j, type);
                    assertTrue("watch/isWatch incorrect", instance.isWatched(i, j, type));
                }
            }
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setUserRole(long userId, long categoryId, UserRole role)</code> and
     * <code>getUserRole(long userId, long categoryId)</code>.
     * </p>
     * <p>
     * Verify that can set user role successfully.
     * </p>
     */
    public void testUserRoleAccuracy() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                UserRole last = UserRole.NO_ACCESS;
                UserRole current = UserRole.values()[(i * 10 + j - 1) % UserRole.values().length];
                assertEquals("setUserRole/getUserRole incorrect", last, instance.getUserRole(i, j));
                instance.setUserRole(i, j, current);
                assertEquals("setUserRole/getUserRole incorrect", current, instance.getUserRole(i, j));
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                UserRole last = UserRole.values()[(i * 10 + j - 1) % UserRole.values().length];
                UserRole current = UserRole.values()[(i * 10 + j) % UserRole.values().length];
                assertEquals("setUserRole/getUserRole incorrect", last, instance.getUserRole(i, j));
                instance.setUserRole(i, j, current);
                assertEquals("setUserRole/getUserRole incorrect", current, instance.getUserRole(i, j));
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                UserRole last = UserRole.values()[(i * 10 + j) % UserRole.values().length];
                UserRole current = last;
                assertEquals("setUserRole/getUserRole incorrect", last, instance.getUserRole(i, j));
                instance.setUserRole(i, j, current);
                assertEquals("setUserRole/getUserRole incorrect", current, instance.getUserRole(i, j));
            }
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>createCategory(categoryConfiguration)</code>.
     * </p>
     * <p>
     * Verify that can create category successfully.
     * </p>
     */
    public void testCreateCategoryAccuracy() {
        CategoryConfiguration config = createCategoryConfiguration();

        for (int i = 1; i <= 10; i++) {
            config.setModeratorUserId(i);
            long categoryId = instance.createCategory(config);
            assertEquals("createCategory incorrect", i, categoryId);
            assertTrue("createCategory incorrect", instance.isWatched(i, i, EntityType.FORUM_CATEGORY));
            assertEquals("User role should be the same.", UserRole.MODERATOR, instance.getUserRole(i, i));
        }
        for (int i = 1; i <= 10; i++) {
            config.setModeratorUserId(i);
            long categoryId = instance.createCategory(config);
            assertEquals("createCategory incorrect", 10 + i, categoryId);
            assertTrue("createCategory incorrect", instance.isWatched(i, 10 + i, EntityType.FORUM_CATEGORY));
            assertEquals("User role should be the same.", UserRole.MODERATOR, instance.getUserRole(i, 10 + i));
        }
    }

    /**
     * <p>
     * Create a default configuration.
     * </p>
     * @return the configuration.
     */
    private CategoryConfiguration createCategoryConfiguration() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("1");
        config.setDescription("2");
        config.setVersionText("3");
        config.setComponentId(1);
        config.setVersionId(10);
        config.setRootCategoryId(100);
        return config;
    }
}
