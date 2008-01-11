/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.jivesoftware.forum.mock.MockForumCategory;
import com.jivesoftware.forum.mock.MockForumFactory;
import com.jivesoftware.forum.mock.MockGroup;
import com.jivesoftware.forum.mock.MockGroupManager;
import com.jivesoftware.forum.mock.MockUser;
import com.jivesoftware.forum.mock.MockWatchManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Unit tests for <code>JiveForumManager</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiveForumManagerUnitTest extends TestCase {
    /**
     * <p>The user id used to create the <code>JiveForumManager</code>.</p>
     */
    private long userId = 1;

    /**
     * <p>The template category ids used to create the <code>JiveForumManager</code>.</p>
     */
    private Map<CategoryType, Long> categoryTemplateIds = new HashMap<CategoryType, Long>();

    /**
     * <p>The <code>JiveForumManager</code> instance for testing.</p>
     */
    private JiveForumManager manager;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JiveForumManagerUnitTest.class);
    }

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        MockForumFactory.setException(false);

        categoryTemplateIds.put(CategoryType.APPLICATION, new Long(1));

        manager = new JiveForumManager(userId, categoryTemplateIds);
    }

    /**
     * <p>
     * Destorys the environment.
     * </p>
     */
    protected void tearDown() {
        MockForumCategory.setException(false);
        MockGroupManager.setException1(false);
        MockGroupManager.setException2(false);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>JiveForumManager</code> can be created successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor() throws Exception {
        assertNotNull("Unable to create JiveForumManager instance.", manager);
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure1() throws Exception {
        try {
            new JiveForumManager(0, categoryTemplateIds);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryTemplateIds is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure2() throws Exception {
        try {
            new JiveForumManager(userId, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryTemplateIds is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure3() throws Exception {
        try {
            new JiveForumManager(userId, new HashMap<CategoryType, Long>());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryTemplateIds contains null key,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure4() throws Exception {
        try {
            categoryTemplateIds.put(null, new Long(1));
            new JiveForumManager(userId, categoryTemplateIds);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryTemplateIds contains null value,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure5() throws Exception {
        try {
            categoryTemplateIds.put(CategoryType.APPLICATION, null);
            new JiveForumManager(userId, categoryTemplateIds);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryTemplateIds contains long value isn't above 0,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure6() throws Exception {
        try {
            categoryTemplateIds.put(CategoryType.APPLICATION, new Long(0));
            new JiveForumManager(userId, categoryTemplateIds);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>JiveForumManager(long, Map)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If fails to obtain the <code>ForumFactory</code>, the
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor_Failure7() throws Exception {
        try {
            //Use my mock ForumFactory to set the exception flag
            MockForumFactory.setException(true);

            categoryTemplateIds.put(CategoryType.APPLICATION, new Long(1));
            new JiveForumManager(userId, categoryTemplateIds);

            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Verify that if a forum isn't been watched, it would be watched.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_FORUM() throws Exception {
        assertFalse("The forum isn't been watched, before the calling.",
            MockWatchManager.isForumWatched());

        manager.watch(1, 1, EntityType.FORUM);
        assertTrue("The forum should be watched.",
            MockWatchManager.isForumWatched());

        //Watch an already watched forum, it should process successfully.
        manager.watch(1, 1, EntityType.FORUM);
        assertTrue("The forum should be watched.",
            MockWatchManager.isForumWatched());

        MockWatchManager.clearForumWatched();
    }

    /**
     * <p>
     * Accuracy test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Verify that if a forum category isn't been watched, it would be watched.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_FORUM_CATEGORY() throws Exception {
        assertFalse("The forum category isn't been watched, before the calling.",
            MockWatchManager.isForumCategoryWatched());

        manager.watch(1, 1, EntityType.FORUM_CATEGORY);
        assertTrue("The forum category should be watched.",
            MockWatchManager.isForumCategoryWatched());

        //Watch an already watched forum category, it should process successfully.
        manager.watch(1, 1, EntityType.FORUM_CATEGORY);
        assertTrue("The forum category should be watched.",
            MockWatchManager.isForumCategoryWatched());

        MockWatchManager.clearForumCategoryWatched();
    }

    /**
     * <p>
     * Accuracy test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Verify that if a forum thread isn't been watched, it should be watched.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_FORUM_THREAD() throws Exception {
        assertFalse("The forum thread isn't been watched, before the calling.",
            MockWatchManager.isForumThreadWatched());

        manager.watch(1, 1, EntityType.FORUM_THREAD);
        assertTrue("The forum thread should be watched.",
            MockWatchManager.isForumThreadWatched());

        //Watch an already watched forum thread, it should process successfully.
        manager.watch(1, 1, EntityType.FORUM_THREAD);
        assertTrue("The forum thread should be watched.",
            MockWatchManager.isForumThreadWatched());

        MockWatchManager.clearForumThreadWatched();
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure1() throws Exception {
        try {
            manager.watch(0, 1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the entityId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure2() throws Exception {
        try {
            manager.watch(1, 0, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the entityType is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure3() throws Exception {
        try {
            manager.watch(1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the application doesn't have the permission to do the operation,
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure4() throws Exception {
        try {
            //Simulate the exception.
            MockForumFactory.setException(true);

            manager.watch(1, 1, EntityType.FORUM);
            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the user with specified userId doesn't exist,
     * <code>UserNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure5() throws Exception {
        try {
            //In my mock implementation, there is only one user, which id is 1.
            //Any other userId doesn't exist.
            manager.watch(1000, 1, EntityType.FORUM);
            fail("UserNotFoundException is expected.");
        } catch (UserNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the forum with specifed entityId doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure6() throws Exception {
        try {
            //In my mock implementation, there is only one forum, which id is 1.
            //Any other Id doesn't exist.
            manager.watch(1, 1000, EntityType.FORUM);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the forum category with specifed entityId doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure7() throws Exception {
        try {
            //In my mock implementation, there is only one forum category, which id is 1.
            //Any other Id doesn't exist.
            manager.watch(1, 2, EntityType.FORUM_CATEGORY);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the forum thread with specifed entityId doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch_Failure8() throws Exception {
        try {
            //In my mock implementation, there is only one forum thread, which id is 1.
            //Any other Id doesn't exist.
            manager.watch(1, 2, EntityType.FORUM_THREAD);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Verify that is can return the correct status of the watch info on forum.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_FORUM() throws Exception {
        assertFalse("The forum should not be watched at first.",
            manager.isWatched(1, 1, EntityType.FORUM));

        manager.watch(1, 1, EntityType.FORUM);
        assertTrue("The forum should be watched now.",
            manager.isWatched(1, 1, EntityType.FORUM));

        //clear the watch info
        MockWatchManager.clearForumWatched();
    }

    /**
     * <p>
     * Accuracy test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Verify that is can return the correct status of the watch info on forum category.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_FORUM_CATEGORY() throws Exception {
        assertFalse("The forum category should not be watched at first.",
            manager.isWatched(1, 1, EntityType.FORUM_CATEGORY));

        manager.watch(1, 1, EntityType.FORUM_CATEGORY);
        assertTrue("The forum category should be watched now.",
            manager.isWatched(1, 1, EntityType.FORUM_CATEGORY));

        //clear the watch info
        MockWatchManager.clearForumCategoryWatched();
    }

    /**
     * <p>
     * Accuracy test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Verify that is can return the correct status of the watch info on forum thread.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_FORUM_THREAD() throws Exception {
        assertFalse("The forum thread should not be watched at first.",
            manager.isWatched(1, 1, EntityType.FORUM_THREAD));

        manager.watch(1, 1, EntityType.FORUM_THREAD);
        assertTrue("The forum thread should be watched now.",
            manager.isWatched(1, 1, EntityType.FORUM_THREAD));

        //clear the watch info
        MockWatchManager.clearForumThreadWatched();
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure1() throws Exception {
        try {
            manager.isWatched(0, 1, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the entityId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure2() throws Exception {
        try {
            manager.isWatched(1, 0, EntityType.FORUM);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the entityType is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure3() throws Exception {
        try {
            manager.isWatched(1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the application doesn't have permission to do the operation,
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure4() throws Exception {
        try {
            //Simulate the no permission situation.
            MockForumFactory.setException(true);

            manager.isWatched(1, 1, EntityType.FORUM);
            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the user with specifed id doesn't exist,
     * <code>UserNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure5() throws Exception {
        try {
            //The user widh id 2, doesn't exist.
            manager.isWatched(2, 1, EntityType.FORUM);
            fail("UserNotFoundException is expected.");
        } catch (UserNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the forum with specifed id doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure6() throws Exception {
        try {
            //The forum widh id 2, doesn't exist.
            manager.isWatched(1, 2, EntityType.FORUM);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the forum category with specifed id doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure7() throws Exception {
        try {
            //The forum category widh id 2, doesn't exist.
            manager.isWatched(1, 2, EntityType.FORUM_CATEGORY);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>isWatched(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the forum thread with specifed id doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched_Failure8() throws Exception {
        try {
            //The forum thread widh id 2, doesn't exist.
            manager.isWatched(1, 2, EntityType.FORUM_THREAD);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Verify that the user is assigned to the specified role group.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole() throws Exception {
        manager.setUserRole(1, 1, UserRole.NO_ACCESS);

        //Verify
        MockForumFactory forumFactory = MockForumFactory.getInstance();
        MockUser user = (MockUser) forumFactory.getUserManager().getUser(1);

        assertNull("The user doesn't belong to any group.", user.getGroup());
    }

    /**
     * <p>
     * Accuracy test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Verify that the user is assigned to the moderator role group.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Moderator() throws Exception {
        manager.setUserRole(1, 1, UserRole.MODERATOR);

        //Verify
        MockForumFactory forumFactory = MockForumFactory.getInstance();
        MockUser user = (MockUser) forumFactory.getUserManager().getUser(1);

        assertEquals("The user belong to the moderator group.", 1,
            user.getGroup().getID());
    }

    /**
     * <p>
     * Accuracy test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Verify that the user is assigned to the contributor role group.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Contributor() throws Exception {
        manager.setUserRole(1, 1, UserRole.CONTRIBUTOR);

        //Verify
        MockForumFactory forumFactory = MockForumFactory.getInstance();
        MockUser user = (MockUser) forumFactory.getUserManager().getUser(1);

        assertEquals("The user belong to the moderator group.", 2,
            user.getGroup().getID());
    }

    /**
     * <p>
     * Failure test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId isn't above 0, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Failure1() throws Exception {
        try {
            manager.setUserRole(0, 1, UserRole.CONTRIBUTOR);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryId isn't above 0, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Failure2() throws Exception {
        try {
            manager.setUserRole(1, 0, UserRole.CONTRIBUTOR);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userRole is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Failure3() throws Exception {
        try {
            manager.setUserRole(1, 1, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the user with specified id doesn't exist, <code>UserNotFoundException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Failure4() throws Exception {
        try {
            manager.setUserRole(2, 1, UserRole.CONTRIBUTOR);
            fail("UserNotFoundException is expected.");
        } catch (UserNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the category doesn't exist, <code>ForumEntityNotFoundException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Failure5() throws Exception {
        try {
            manager.setUserRole(1, 100, UserRole.CONTRIBUTOR);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the application doesn't have permission to do the operation,
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_Failure6() throws Exception {
        try {
            MockGroup.setException(true);
            manager.setUserRole(1, 1, UserRole.CONTRIBUTOR);

            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            MockGroup.setException(false);

            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Verify that the specified user doesn't belong to any group.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_NO_ACCESS() throws Exception {
        MockForumFactory forumFactory = MockForumFactory.getInstance();
        MockUser user = (MockUser) forumFactory.getUserManager().getUser(1);
        user.setGroup(null);

        assertEquals("The user role should be NO_ACCESS.", UserRole.NO_ACCESS,
            manager.getUserRole(1, 1));
    }

    /**
     * <p>
     * Accuracy test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Verify that the specified user is in moderator group.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_MODERATOR() throws Exception {
        manager.setUserRole(1, 1, UserRole.MODERATOR);

        assertEquals("The user role should be MODERATOR.", UserRole.MODERATOR,
            manager.getUserRole(1, 1));
    }

    /**
     * <p>
     * Accuracy test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Verify that the specified user is in contributor group.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_CONTRIBUTOR() throws Exception {
        manager.setUserRole(1, 1, UserRole.CONTRIBUTOR);

        assertEquals("The user role should be CONTRIBUTOR.",
            UserRole.CONTRIBUTOR, manager.getUserRole(1, 1));
    }

    /**
     * <p>
     * Failure test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_Failure1() throws Exception {
        try {
            manager.getUserRole(0, 1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryId isn't above 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_Failure2() throws Exception {
        try {
            manager.getUserRole(1, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the user doesn't exist, <code>UserNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_Failure3() throws Exception {
        try {
            manager.getUserRole(2, 1);
            fail("UserNotFoundException is expected.");
        } catch (UserNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserRole(long, long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the category doesn't exist, <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_Failure4() throws Exception {
        try {
            manager.getUserRole(1, 100);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Verify that a new category is created with the new configuration.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory() throws Exception {
        CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
        categoryConfiguration.setName("category");
        categoryConfiguration.setDescription("a new category");
        categoryConfiguration.setVersionText("1.0");
        categoryConfiguration.setComponentId(1);
        categoryConfiguration.setVersionId(1);
        categoryConfiguration.setRootCategoryId(10);
        categoryConfiguration.setPublic(false);
        categoryConfiguration.setModeratorUserId(1);
        categoryConfiguration.setTemplateCategoryId(1);

        manager.createCategory(categoryConfiguration);
    }

    /**
     * <p>
     * Accuracy test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Verify that a new category is created with the new configuration.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory1() throws Exception {
        CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
        categoryConfiguration.setName("category");
        categoryConfiguration.setDescription("a new category");
        categoryConfiguration.setVersionText("1.0");
        categoryConfiguration.setComponentId(1);
        categoryConfiguration.setVersionId(1);
        categoryConfiguration.setRootCategoryId(10);
        categoryConfiguration.setPublic(false);
        categoryConfiguration.setModeratorUserId(1);
        categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

        manager.createCategory(categoryConfiguration);
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the categoryConfig is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure1() throws Exception {
        try {
            manager.createCategory(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the name is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure2() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            //categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the description is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure3() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            //categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the versionText is null, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure4() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            //categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the componentId is -1, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure5() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            //categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the versionId is -1, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure6() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            //categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the rootCategoryId is -1, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure7() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            //categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the application doesn't have permission to create the category,
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure8() throws Exception {
        try {
            MockForumCategory.setException(true);

            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the root category specified by the rootCategoryId doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure9() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(1000);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the moderator group already exist,
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure10() throws Exception {
        try {
            MockGroupManager.setException1(true);

            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the admin group doesn't exist,
     * <code>ForumEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure11() throws Exception {
        try {
            MockGroupManager.setException2(true);

            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.APPLICATION);

            manager.createCategory(categoryConfiguration);
            fail("ForumEntityNotFoundException is expected.");
        } catch (ForumEntityNotFoundException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If there is no corresponding map for the category type,
     * <code>JiveForumManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory_Failure12() throws Exception {
        try {
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
            categoryConfiguration.setName("category");
            categoryConfiguration.setDescription("a new category");
            categoryConfiguration.setVersionText("1.0");
            categoryConfiguration.setComponentId(1);
            categoryConfiguration.setVersionId(1);
            categoryConfiguration.setRootCategoryId(10);
            categoryConfiguration.setPublic(false);
            categoryConfiguration.setModeratorUserId(1);
            categoryConfiguration.setTemplateCategoryType(CategoryType.ASSEMBLY_COMPETITION);

            manager.createCategory(categoryConfiguration);
            fail("JiveForumManagementException is expected.");
        } catch (JiveForumManagementException e) {
            //success
        }
    }
}
