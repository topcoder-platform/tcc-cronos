/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.UserStatusManager;
import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactory;

/**
 * Accuracy test cases for class <code>UserStatusManager </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public abstract class AbstractUserStatusManagerAccuracyTests extends TestCase {
    /**
     * <p>
     * UserStatusManager instance for testing.
     * </p>
     */
    private UserStatusManager instance;

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected abstract UserStatusManager getTestInstance() throws Exception;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE_ADD);
        AccuracyTestHelper.setUpDataBase();

        instance = getTestInstance();
    }

    /**
     * <p>
     * Tears down the environment after the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * Test constructor.
     */
    public void testConstructor() {
        assertNotNull(this.instance);
    }

    /**
     * Test {@link UserStatusManager#createUserStatus(UserStatus)}.
     * @throws Exception
     *             to junit.
     */
    public void testCreateUserStatus() throws Exception {
        UserStatus status = AccuracyTestHelper.createTestingUserStatus("Id");
        instance.createUserStatus(status);
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status.getId()), status);
    }

    /**
     * Test {@link UserStatusManager#updateUserStatus(UserStatus)}.
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUserStatus() throws Exception {
        UserStatus status = AccuracyTestHelper.createTestingUserStatus("Id");
        instance.createUserStatus(status);
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status.getId()), status);
        status.setActive(false);
        status.setDescription("new");
        instance.updateUserStatus(status);
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status.getId()), status);
    }

    /**
     * Test {@link UserStatusManager#removeUserStatus(long)}.
     * @throws Exception
     *             to junit.
     */
    public void testRemoveUserStatus() throws Exception {
        instance.removeUserStatus(1);
        UserStatus[] status = instance.getAllUserStatuses();
        for (int i = 0; i < status.length; i++) {
            if (status[i].getId() == 1) {
                fail("removeUserStatus failed");
            }
        }
    }

    /**
     * Test {@link UserStatusManager#getUserStatus(long)}.
     * @throws Exception
     *             to junit.
     */
    public void testGetUserStatus() throws Exception {
        UserStatus user = instance.getUserStatus(1);
        assertEquals("The description are not equals.", "Internal Project", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * Test {@link UserStatusManager#searchUserStatuses(Filter)}.
     * @throws Exception
     *             to junit.
     */
    public void testSearchUserStatuses() throws Exception {
        UserStatus user = instance.searchUserStatuses(UserStatusFilterFactory
                .createDescriptionFilter("Internal Project"))[0];
        assertEquals("The description are not equals.", "Internal Project", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * Test {@link UserStatusManager#addUserStatuses(UserStatus[])}.
     * @throws Exception
     *             to junit.
     */
    public void testAddUserStatuses() throws Exception {
        UserStatus status = AccuracyTestHelper.createTestingUserStatus("Id");
        UserStatus status2 = AccuracyTestHelper.createTestingUserStatus("Id");
        instance.addUserStatuses(new UserStatus[] { status, status2 });
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status.getId()), status);
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status2.getId()), status2);
    }

    /**
     * Test {@link UserStatusManager#updateUserStatuses(UserStatus[])}.
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUserStatuses() throws Exception {
        UserStatus status = AccuracyTestHelper.createTestingUserStatus("Id");
        UserStatus status2 = AccuracyTestHelper.createTestingUserStatus("Id");
        instance.addUserStatuses(new UserStatus[] { status, status2 });
        status.setDescription("new");
        status2.setDescription("status");
        instance.updateUserStatuses(new UserStatus[] { status, status2 });
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status.getId()), status);
        AccuracyTestHelper.assertUserStatusEquals(instance.getUserStatus(status2.getId()), status2);
    }

    /**
     * Test {@link UserStatusManager#removeUserStatuses(long[])}.
     * @throws Exception
     *             to junit.
     */
    public void testRemoveUserStatuses() throws Exception {
        instance.removeUserStatuses(new long[] { 1, 2 });
        UserStatus[] status = instance.getAllUserStatuses();
        for (int i = 0; i < status.length; i++) {
            if (status[i].getId() == 1 || status[i].getId() == 2) {
                fail("removeUserStatuses failed");
            }
        }
    }

    /**
     * Test {@link UserStatusManager#getUserStatuses(long[])}.
     * @throws Exception
     *             to junit.
     */
    public void testGetUserStatuses() throws Exception {
        UserStatus user = instance.getUserStatuses(new long[] { 1 })[0];
        assertEquals("The description are not equals.", "Internal Project", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * Test {@link UserStatusManager#getAllUserStatuses()}.
     * @throws Exception
     *             to junit.
     */
    public void testGetAllUserStatuses() throws Exception {
        assertEquals("getAllUserStatuses failed", 5, instance.getAllUserStatuses().length);
    }
}
