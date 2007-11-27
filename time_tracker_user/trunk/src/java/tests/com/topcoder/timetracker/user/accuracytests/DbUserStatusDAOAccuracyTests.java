/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.db.DbUserStatusDAO;
import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactory;

/**
 * Accuracy test cases for class <code>DbUserStatusDAO </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class DbUserStatusDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * DbUserStatusDAO instance for testing.
     * </p>
     */
    private DbUserStatusDAO instance;

    /**
     * Test instance.
     */
    private DBConnectionFactory dbFactory;

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

        dbFactory = AccuracyTestHelper.getDBConnectionFactory();

        instance = (DbUserStatusDAO) AccuracyTestHelper.getUserStatusDAO(dbFactory);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        instance = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * Test {@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}.
     */
    public void testConstructor() {
        assertNotNull(this.instance);
    }

    /**
     * Test {@link DbUserStatusDAO#addUserStatuses(UserStatus[])}.
     * @throws Exception
     *             to junit.
     */
    public void testAddUserStatuses() throws Exception {
        UserStatus actualUser = AccuracyTestHelper.createTestingUserStatus("Id");
        this.instance.addUserStatuses(new UserStatus[] { actualUser });
        UserStatus expectedUser = this.instance.getUserStatuses(new long[] { actualUser.getId() })[0];
        AccuracyTestHelper.assertUserStatusEquals(expectedUser, actualUser);
    }

    /**
     * Test {@link DbUserStatusDAO#getUserStatuses(long[])}.
     * @throws Exception
     *             to junit.
     */
    public void testGetUserStatuses() throws Exception {
        UserStatus actualUser = AccuracyTestHelper.createTestingUserStatus("Id");
        this.instance.addUserStatuses(new UserStatus[] { actualUser });
        UserStatus expectedUser = this.instance.getUserStatuses(new long[] { actualUser.getId() })[0];
        AccuracyTestHelper.assertUserStatusEquals(expectedUser, actualUser);
    }

    /**
     * Test {@link DbUserStatusDAO#searchUserStatuses(Filter)}.
     * @throws Exception
     *             to junit.
     */
    public void testSearchUserStatuses() throws Exception {
        UserStatus user = this.instance.searchUserStatuses(UserStatusFilterFactory
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
     * Test {@link DbUserStatusDAO#removeUserStatuses(long[])}.
     * @throws Exception
     *             to junit.
     */
    public void testRemoveUserStatuses() throws Exception {
        UserStatus actualUser = AccuracyTestHelper.createTestingUserStatus("Id");
        this.instance.addUserStatuses(new UserStatus[] { actualUser });
        this.instance.removeUserStatuses(new long[] { 1, actualUser.getId() });
        UserStatus[] users = this.instance.getAllUserStatuses();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id [1].");
            }

            if (users[i].getId() == actualUser.getId()) {
                fail("Failed to remove the user with id [" + actualUser.getId() + "].");
            }
        }
    }

    /**
     * Test {@link DbUserStatusDAO#updateUserStatuses(UserStatus[])}.
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUserStatuses() throws Exception {
        UserStatus actualUser = AccuracyTestHelper.createTestingUserStatus("Id");
        this.instance.addUserStatuses(new UserStatus[] { actualUser });
        actualUser.setDescription("new");
        actualUser.setActive(true);
        this.instance.updateUserStatuses(new UserStatus[] { actualUser });
        UserStatus expectedUser = this.instance.getUserStatuses(new long[] { actualUser.getId() })[0];
        AccuracyTestHelper.assertUserStatusEquals(expectedUser, actualUser);
    }

    /**
     * Test {@link DbUserStatusDAO#getAllUserStatuses()}.
     * @throws Exception
     *             to junit.
     */
    public void testGetAllUserStatuses() throws Exception {
        assertEquals("getAllUserStatuses failed", 5, this.instance.getAllUserStatuses().length);
    }
}
