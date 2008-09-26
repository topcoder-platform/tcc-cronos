/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * MockJiveForumServiceTest.java
 */
package com.topcoder.forum.service.ejb.mock;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.UserRole;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * <p>Unit tests for <code>MockJiveForumService</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.1
 */
public class MockJiveForumServiceTest extends TestCase {
    /**
     * <p>Represents for service bean.</p>
     */
    private MockJiveForumService instance;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MockJiveForumServiceTest.class);
    }

    /**
     * <p>Setup the environment.</p>
     */
    protected void setUp() {
        instance = new MockJiveForumService();
    }

    /**
     * <p>Destroy the environment.</p>
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>Accuracy test for constructor <code>MockJiveForumService()</code>.</p>
     *  <p>Verify that the instance can be created successfully.</p>
     */
    public void testEmptyCtorAccuracy() {
        assertNotNull("Unable to create MockJiveForumService instance.", instance);
        assertTrue("Should be instance of MockJiveForumService.", instance instanceof MockJiveForumService);
    }

    /**
     * <p>Accuracy test for method <code>watch(userId,entityId,entityType)</code>.</p>
     *  <p>Verify that can create the watch successfully.</p>
     */
    public void testWatchAccuracy() {
        long userId = 1;
        long entityId = 11;
        EntityType entityType = EntityType.FORUM_THREAD;

        assertFalse("The FORUM_THREAD[id:11] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType));
        instance.watch(userId, entityId, entityType);
        assertTrue("The FORUM_THREAD[id:11] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType));

        long entityId2 = 22;
        assertFalse("The FORUM_THREAD[id:22] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId2, entityType));
        instance.watch(userId, entityId2, entityType);
        assertTrue("The FORUM_THREAD[id:22] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId2, entityType));

        //watch the entity which is already watched
        instance.watch(userId, entityId2, entityType);
        assertTrue("The FORUM_THREAD[id:22] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId2, entityType));
    }

    /**
     * <p>Accuracy test for method <code>watch(userId,entityId,entityType)</code>.</p>
     *  <p>Verify that can create the watch successfully.</p>
     *  <p>Test with all the entity types.</p>
     */
    public void testWatchAccuracy2() {
        long userId = 1;
        long entityId = 11;

        EntityType entityType1 = EntityType.FORUM_THREAD;
        assertFalse("The FORUM_THREAD[id:11] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType1));
        instance.watch(userId, entityId, entityType1);
        assertTrue("The FORUM_THREAD[id:11] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType1));

        EntityType entityType2 = EntityType.FORUM;
        assertFalse("The FORUM[id:11] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType2));
        instance.watch(userId, entityId, entityType2);
        assertTrue("The FORUM[id:11] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType2));

        EntityType entityType3 = EntityType.FORUM_CATEGORY;
        assertFalse("The FORUM_CATEGORY[id:11] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType3));
        instance.watch(userId, entityId, entityType3);
        assertTrue("The FORUM_CATEGORY[id:11] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType3));
    }

    /**
     * <p>Failure test for method <code>watch(userId,entityId,entityType)</code>.</p>
     *  <p>'userId' is not positive, IAE should be thrown.</p>
     */
    public void testWatchFailure1() {
        long invalidUserId = -1;

        try {
            instance.watch(invalidUserId, 11, EntityType.FORUM_THREAD);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>watch(userId,entityId,entityType)</code>.</p>
     *  <p>'entityId' is not positive, IAE should be thrown.</p>
     */
    public void testWatchFailure2() {
        long invalidEntityId = -1;

        try {
            instance.watch(1, invalidEntityId, EntityType.FORUM_THREAD);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>watch(userId,entityId,entityType)</code>.</p>
     *  <p>'entityType' is null, IAE should be thrown.</p>
     */
    public void testWatchFailure3() {
        EntityType invalidEntityType = null;

        try {
            instance.watch(1, 11, invalidEntityType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Accuracy test for method <code>isWatched(userId,entityId,entityType)</code>.</p>
     *  <p>Verify that get watched state successfully.</p>
     */
    public void testIsWatchedAccuracy() {
        long userId = 1;
        long entityId = 11;
        EntityType entityType = EntityType.FORUM_THREAD;

        assertFalse("The FORUM_THREAD[id:11] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType));
        instance.watch(userId, entityId, entityType);
        assertTrue("The FORUM_THREAD[id:11] should be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType));

        EntityType entityType2 = EntityType.FORUM_CATEGORY;
        assertFalse("The FORUM_CATEGORY[id:11] should not be watched by the user[id:1].",
            instance.isWatched(userId, entityId, entityType2));
    }

    /**
     * <p>Failure test for method <code>isWatched(userId,entityId,entityType)</code>.</p>
     *  <p>'userId' is not positive, IAE should be thrown.</p>
     */
    public void testIsWatchedFailure1() {
        long invalidUserId = -1;

        try {
            instance.isWatched(invalidUserId, 11, EntityType.FORUM_THREAD);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>isWatched(userId,entityId,entityType)</code>.</p>
     *  <p>'entityId' is not positive, IAE should be thrown.</p>
     */
    public void testIsWatchedFailure2() {
        long invalidEntityId = -1;

        try {
            instance.isWatched(1, invalidEntityId, EntityType.FORUM_THREAD);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>isWatched(userId,entityId,entityType)</code>.</p>
     *  <p>'entityType' is not valid, IAE should be thrown.</p>
     */
    public void testIsWatchedFailure3() {
        EntityType invalidEntityType = null;

        try {
            instance.isWatched(1, 11, invalidEntityType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Accuracy test for method <code>setUserRole(userId,categoryId,role)</code>.</p>
     *  <p>Verify that can set user role successfully.</p>
     */
    public void testSetUserRoleAccuracy() {
        long userId = 1;
        long categoryId = 111;
        UserRole role = UserRole.MODERATOR;

        assertEquals("User role should be the same.", UserRole.NO_ACCESS, instance.getUserRole(userId, categoryId));
        instance.setUserRole(userId, categoryId, role);
        assertEquals("User role should be the same.", role, instance.getUserRole(userId, categoryId));

        long categoryId2 = 222;
        UserRole role2 = UserRole.CONTRIBUTOR;
        assertEquals("User role should be the same.", UserRole.NO_ACCESS, instance.getUserRole(userId, categoryId2));
        instance.setUserRole(userId, categoryId2, role2);
        assertEquals("User role should be the same.", role2, instance.getUserRole(userId, categoryId2));
    }

    /**
     * <p>Accuracy test for method <code>setUserRole(userId,categoryId,role)</code>.</p>
     *  <p>Verify that can set user role successfully.</p>
     *  <p>Test with all the user roles.</p>
     */
    public void testSetUserRoleAccuracy2() {
        long userId = 1;
        long categoryId = 111;

        UserRole role1 = UserRole.MODERATOR;
        instance.setUserRole(userId, categoryId, role1);
        assertEquals("User role should be the same.", role1, instance.getUserRole(userId, categoryId));

        UserRole role2 = UserRole.NO_ACCESS;
        instance.setUserRole(userId, categoryId, role2);
        assertEquals("User role should be the same.", role2, instance.getUserRole(userId, categoryId));

        UserRole role3 = UserRole.MODERATOR;
        instance.setUserRole(userId, categoryId, role3);
        assertEquals("User role should be the same.", role3, instance.getUserRole(userId, categoryId));
    }

    /**
     * <p>Failure test for method <code>setUserRole(userId,categoryId,role)</code>.</p>
     *  <p>'userId' is not positive, IAE should be thrown.</p>
     */
    public void testSetUserRoleFailure1() {
        long invalidUserId = -1;

        try {
            instance.setUserRole(invalidUserId, 111, UserRole.CONTRIBUTOR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>setUserRole(userId,categoryId,role)</code>.</p>
     *  <p>'categoryId' is not positive, IAE should be thrown.</p>
     */
    public void testSetUserRoleFailure2() {
        long invalidCategoryId = -1;

        try {
            instance.setUserRole(1, invalidCategoryId, UserRole.CONTRIBUTOR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>setUserRole(userId,categoryId,role)</code>.</p>
     *  <p>'role' is not valid, IAE should be thrown.</p>
     */
    public void testSetUserRoleFailure3() {
        UserRole invalidRole = null;

        try {
            instance.setUserRole(1, 111, invalidRole);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Accuracy test for method <code>getUserRole(userId,categoryId)</code>.</p>
     *  <p>Verify that can get user role successfully.</p>
     */
    public void testGetUserRoleAccuracy() {
        long userId = 1;
        long categoryId = 111;
        UserRole role = UserRole.MODERATOR;

        //userRole is null, simply return UserRole.NO_ACCESS
        assertEquals("User role should be the same.", UserRole.NO_ACCESS, instance.getUserRole(userId, categoryId));
        instance.setUserRole(userId, categoryId, role);

        //role is null, simply return UserRole.NO_ACCESS
        long categoryId2 = 222;
        assertEquals("User role should be the same.", UserRole.NO_ACCESS, instance.getUserRole(userId, categoryId2));
        assertEquals("User role should be the same.", role, instance.getUserRole(userId, categoryId));
    }

    /**
     * <p>Failure test for method <code>getUserRole(userId,categoryId,role)</code>.</p>
     *  <p>'userId' is not positive, IAE should be thrown.</p>
     */
    public void testGetUserRoleFailure1() {
        long invalidUserId = -1;

        try {
            instance.getUserRole(invalidUserId, 111);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>getUserRole(userId,categoryId,role)</code>.</p>
     *  <p>'categoryId' is not positive, IAE should be thrown.</p>
     */
    public void testGetUserRoleFailure2() {
        long invalidCategoryId = -1;

        try {
            instance.getUserRole(1, invalidCategoryId);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Accuracy test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>Verify that can create category successfully.</p>
     *  <p>'moderatorUserId' is positive, so the category should be watched and user role should be set.</p>
     */
    public void testCreateCategoryAccuracy1() {
        CategoryConfiguration config = createCategoryConfiguration();
        long userId = 1;
        config.setModeratorUserId(userId);

        long categoryId = instance.createCategory(config);
        assertEquals("categoryId should be the same.", 1, categoryId);
        assertTrue("Category should be watched.", instance.isWatched(userId, categoryId, EntityType.FORUM_CATEGORY));
        assertEquals("User role should be the same.", UserRole.MODERATOR, instance.getUserRole(userId, categoryId));
    }

    /**
     * <p>Accuracy test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>Verify that can create category successfully.</p>
     *  <p>'moderatorUserId' is not positive, so the category should not be watched and user role should not be
     * set.</p>
     */
    public void testCreateCategoryAccuracy2() {
        CategoryConfiguration config = createCategoryConfiguration();
        long userId = -1;
        config.setModeratorUserId(userId);

        long categoryId = instance.createCategory(config);
        assertEquals("categoryId should be the same.", 1, categoryId);
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'categoryConfiguration' is null, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure1() {
        try {
            instance.createCategory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'name' is null, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure2() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setDescription("description");
        config.setVersionText("versionText");
        config.setComponentId(1);
        config.setVersionId(1);
        config.setRootCategoryId(1);

        try {
            instance.createCategory(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'description' is null, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure3() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("name");
        config.setVersionText("versionText");
        config.setComponentId(1);
        config.setVersionId(1);
        config.setRootCategoryId(1);

        try {
            instance.createCategory(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'versionText' is null, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure4() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("name");
        config.setDescription("description");
        config.setComponentId(1);
        config.setVersionId(1);
        config.setRootCategoryId(1);

        try {
            instance.createCategory(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'componentId' is not positive, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure5() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("name");
        config.setDescription("description");
        config.setVersionText("versionText");
        config.setVersionId(1);
        config.setRootCategoryId(1);

        try {
            instance.createCategory(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'versionId' is not positive, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure6() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("name");
        config.setDescription("description");
        config.setVersionText("versionText");
        config.setComponentId(1);
        config.setRootCategoryId(1);

        try {
            instance.createCategory(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Failure test for method <code>createCategory(categoryConfiguration)</code>.</p>
     *  <p>'rootCategoryId' is not positive, IAE should be thrown.</p>
     */
    public void testCreateCategoryFailure7() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("name");
        config.setDescription("description");
        config.setVersionText("versionText");
        config.setComponentId(1);
        config.setVersionId(1);

        try {
            instance.createCategory(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>Tests class level annotations of <code>MockJiveForumService</code>.</p>
     */
    public void testClassLevelAnnotation() {
        Annotation[] annotations = MockJiveForumService.class.getDeclaredAnnotations();
        assertEquals("There should be 1 class level annotations", 1, annotations.length);
        assertEquals("The class level annotation should be Stateful.", "Stateful",
            annotations[0].annotationType().getSimpleName());
    }

    /**
     * <p>Verify that method <code>watch</code> has been annotated correctly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testWatchAnnotation() throws Exception {
        Method method = MockJiveForumService.class.getDeclaredMethod("watch", long.class, long.class, EntityType.class);
        assertEquals("There should be 1 method level annotations", 1, method.getAnnotations().length);

        TransactionAttribute type = method.getAnnotation(TransactionAttribute.class);
        assertEquals("Method annotation name should be TransactionAttribute", "TransactionAttribute",
            type.annotationType().getSimpleName());
        assertEquals("The TransactionAttributeType should be REQUIRED.", 0,
            type.value().compareTo(TransactionAttributeType.REQUIRED));
    }

    /**
     * <p>Verify that method <code>isWatched</code> has been annotated with <code>TransactionAttribute</code>.</p>
     *
     * @throws Exception to JUnit
     */
    public void testIsWatchedAnnotation() throws Exception {
        Method method = MockJiveForumService.class.getDeclaredMethod("isWatched", long.class, long.class,
                EntityType.class);
        assertEquals("There should be 1 method level annotations", 1, method.getAnnotations().length);

        TransactionAttribute type = method.getAnnotation(TransactionAttribute.class);
        assertEquals("Method annotation name should be TransactionAttribute", "TransactionAttribute",
            type.annotationType().getSimpleName());
        assertEquals("The TransactionAttributeType should be REQUIRED.", 0,
            type.value().compareTo(TransactionAttributeType.REQUIRED));
    }

    /**
     * <p>Verify that method <code>setUserRole</code> has been annotated with
     * <code>TransactionAttribute</code>.</p>
     *
     * @throws Exception to JUnit
     */
    public void testSetUserRoleAnnotation() throws Exception {
        Method method = MockJiveForumService.class.getDeclaredMethod("setUserRole", long.class, long.class,
                UserRole.class);
        assertEquals("There should be 1 method level annotations", 1, method.getAnnotations().length);

        TransactionAttribute type = method.getAnnotation(TransactionAttribute.class);
        assertEquals("Method annotation name should be TransactionAttribute", "TransactionAttribute",
            type.annotationType().getSimpleName());
        assertEquals("The TransactionAttributeType should be REQUIRED.", 0,
            type.value().compareTo(TransactionAttributeType.REQUIRED));
    }

    /**
     * <p>Verify that method <code>getUserRole</code> has been annotated with
     * <code>TransactionAttribute</code>.</p>
     *
     * @throws Exception to JUnit
     */
    public void testGetUserRoleAnnotation() throws Exception {
        Method method = MockJiveForumService.class.getDeclaredMethod("getUserRole", long.class, long.class);
        assertEquals("There should be 1 method level annotations", 1, method.getAnnotations().length);

        TransactionAttribute type = method.getAnnotation(TransactionAttribute.class);
        assertEquals("Method annotation name should be TransactionAttribute", "TransactionAttribute",
            type.annotationType().getSimpleName());
        assertEquals("The TransactionAttributeType should be REQUIRED.", 0,
            type.value().compareTo(TransactionAttributeType.REQUIRED));
    }

    /**
     * <p>Verify that method <code>createCategory</code> has been annotated with
     * <code>TransactionAttribute</code>.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCategoryAnnotation() throws Exception {
        Method method = MockJiveForumService.class.getDeclaredMethod("createCategory", CategoryConfiguration.class);
        assertEquals("There should be 1 method level annotations", 1, method.getAnnotations().length);

        TransactionAttribute type = method.getAnnotation(TransactionAttribute.class);
        assertEquals("Method annotation name should be TransactionAttribute", "TransactionAttribute",
            type.annotationType().getSimpleName());
        assertEquals("The TransactionAttributeType should be REQUIRED.", 0,
            type.value().compareTo(TransactionAttributeType.REQUIRED));
    }

    /**
     * <p>Build a category configuration object.</p>
     *
     * @return the configuration object.
     */
    private CategoryConfiguration createCategoryConfiguration() {
        CategoryConfiguration config = new CategoryConfiguration();
        config.setName("name");
        config.setDescription("description");
        config.setVersionText("versionText");
        config.setComponentId(1);
        config.setVersionId(1);
        config.setRootCategoryId(1);

        return config;
    }
}
