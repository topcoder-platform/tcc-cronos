/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.failuretests;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.mock.MockJiveForumService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Failure tests for <code>MockJiveForumService</code> class.
 * </p>
 *
 * @author zhengjuyu
 * @version 1.1
 * @since 1.1
 */
public class MockJiveForumServiceFailure extends TestCase {
    /**
     * <p>The <code>MockJiveForumService</code> instance for testing.</p>
     */
    private MockJiveForumService service;

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MockJiveForumServiceFailure.class);
    }

    /**
     * <p>Sets up the environment.</p>
     */
    protected void setUp() {
        service = new MockJiveForumService();
    }

    /**
     * <p>
     * Failure test for method <code>watch(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is a negative number, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testWatch_Failure1() {
        try {
            service.watch(-1, 1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>watch(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is 0, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testWatch_Failure2() {
        try {
            service.watch(0, 1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>watch(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the entityId is negative, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testWatch_Failure3() {
        try {
            service.watch(1, -1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>watch(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the entityId is 0, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testWatch_Failure4() {
        try {
            service.watch(1, 0, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>watch(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the entityType is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testWatch_Failure5() {
        try {
            service.watch(1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>isWatched(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is a negative number, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testIsWatched_Failure1() {
        try {
            service.isWatched(-1, 1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>isWatched(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is 0, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testIsWatched_Failure2() {
        try {
            service.isWatched(0, 1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>isWatched(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the entityId is negative, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testIsWatched_Failure3() {
        try {
            service.isWatched(1, -1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>isWatched(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the entityId is 0, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testIsWatched_Failure4() {
        try {
            service.isWatched(1, 0, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>isWatched(long, long, EntityType)</code>.
     * </p>
     * <p>
     * Failure cause: If the entityType is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testIsWatched_Failure5() {
        try {
            service.isWatched(1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is is negative, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testSetUserRole_Failure1() {
        try {
            service.setUserRole(-1, 1, UserRole.CONTRIBUTOR);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is is 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testSetUserRole_Failure2() {
        try {
            service.setUserRole(0, 1, UserRole.CONTRIBUTOR);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the categoryId is is negative, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testSetUserRole_Failure3() {
        try {
            service.setUserRole(1, -1, UserRole.CONTRIBUTOR);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the categoryId is is 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testSetUserRole_Failure4() {
        try {
            service.setUserRole(1, 0, UserRole.CONTRIBUTOR);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the user role is null, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testSetUserRole_Failure5() {
        try {
            service.setUserRole(1, 1, null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is is negative, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testGetUserRole_Failure1() {
        try {
            service.getUserRole(-1, 1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the userId is is 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testGetUserRole_Failure2() {
        try {
            service.getUserRole(0, 1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the categoryId is is negative, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testGetUserRole_Failure3() {
        try {
            service.getUserRole(1, -1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>getUserRole(long, long, UserRole)</code>.
     * </p>
     * <p>
     * Failure cause: If the categoryId is is 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testGetUserRole_Failure4() {
        try {
            service.getUserRole(1, 0);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the config is null, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure1() {
        try {
            service.createCategory(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the componentId isn't bigger than 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure2() {
        try {
            CategoryConfiguration config = new CategoryConfiguration();
            //config.setComponentId(-1);
            config.setVersionId(1);
            config.setRootCategoryId(1);
            config.setName("name");
            config.setDescription("description");
            config.setVersionText("1.0");

            service.createCategory(config);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the versionId isn't bigger than 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure3() {
        try {
            CategoryConfiguration config = new CategoryConfiguration();
            config.setComponentId(1);
            //config.setVersionId(-1);
            config.setRootCategoryId(1);
            config.setName("name");
            config.setDescription("description");
            config.setVersionText("1.0");

            service.createCategory(config);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the rootCategoryId isn't bigger than 0, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure4() {
        try {
            CategoryConfiguration config = new CategoryConfiguration();
            config.setComponentId(1);
            config.setVersionId(1);
            //config.setRootCategoryId(-1);
            config.setName("name");
            config.setDescription("description");
            config.setVersionText("1.0");

            service.createCategory(config);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the name is null, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure5() {
        try {
            CategoryConfiguration config = new CategoryConfiguration();
            config.setComponentId(1);
            config.setVersionId(1);
            config.setRootCategoryId(1);
            //config.setName(null);
            config.setDescription("description");
            config.setVersionText("1.0");

            service.createCategory(config);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the description is null, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure6() {
        try {
            CategoryConfiguration config = new CategoryConfiguration();
            config.setComponentId(1);
            config.setVersionId(1);
            config.setRootCategoryId(1);
            config.setName("name");
            //config.setDescription(null);
            config.setVersionText("1.0");

            service.createCategory(config);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     * <p>
     * Failure cause: If the versionText is null, <code>IllegalArgumentException</code>
     * should be thrown.
     * </p>
     */
    public void testCreateCategory_Failure7() {
        try {
            CategoryConfiguration config = new CategoryConfiguration();
            config.setComponentId(1);
            config.setVersionId(1);
            config.setRootCategoryId(1);
            config.setName("name");
            config.setDescription("description");
            //config.setVersionText(null);
            service.createCategory(config);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
