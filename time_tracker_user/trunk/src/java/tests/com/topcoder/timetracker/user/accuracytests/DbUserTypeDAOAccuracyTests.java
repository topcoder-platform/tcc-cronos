/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;


import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.db.DbUserTypeDAO;
import com.topcoder.timetracker.user.filterfactory.UserTypeFilterFactory;

/**
 * Accuracy test cases for class <code>DbUserTypeDAO </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class DbUserTypeDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * DbUserStatusDAO instance for testing.
     * </p>
     */
    private DbUserTypeDAO instance;

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

        instance = (DbUserTypeDAO) AccuracyTestHelper.getUserTypeDAO(dbFactory);
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
     * Test {@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}.
     */
    public void testConstructor() {
        assertNotNull(this.instance);
    }

    /**
     * Test {@link DbUserTypeDAO#addUserTypes(UserType[])}.
     * @throws Exception
     *             to junit.
     */
    public void testAddUserTypes() throws Exception {
        UserType actualUser = AccuracyTestHelper.createTestingUserType("Id");
        this.instance.addUserTypes(new UserType[] { actualUser });
        UserType expectedUser = this.instance.getUserTypes(new long[] { actualUser.getId() })[0];
        AccuracyTestHelper.assertUserTypeEquals(expectedUser, actualUser);
    }

    /**
     * Test {@link DbUserTypeDAO#addUserTypes(UserType[])}.
     * @throws Exception
     *             to junit.
     */
    public void testAddUserTypes_2() throws Exception {
        UserType type = new UserType();
        type.setCompanyId(1);
        type.setActive(false);
        type.setChanged(false);
        type.setCreationDate(new Date());
        type.setCreationUser("me");
        type.setModificationDate(new Date());
        type.setModificationUser("user");
        type.setDescription("ds");

        this.instance.addUserTypes(new UserType[] { type});
        UserType[] ret = instance.getUserTypes(new long[] {type.getId()});
        assertEquals("Equal to 1.", 1, ret.length);
        assertEquals("Equal is expected.", "ds", ret[0].getDescription());
    }

    /**
     * Test {@link DbUserTypeDAO#removeUserTypes(long[])}.
     * @throws Exception
     *             to junit.
     */
    public void testRemoveUserTypes() throws Exception {
        UserType actualUser = AccuracyTestHelper.createTestingUserType("Id");
        this.instance.addUserTypes(new UserType[] { actualUser });
        this.instance.removeUserTypes(new long[] { 1, actualUser.getId() });
        UserType[] users = this.instance.getAllUserTypes();
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
     * Test {@link DbUserTypeDAO#updateUserTypes(UserType[])}.
     * @throws Exception
     *             to junit.
     */
    public void testUpdateUserTypes() throws Exception {
        UserType actualUser = AccuracyTestHelper.createTestingUserType("Id");
        this.instance.addUserTypes(new UserType[] { actualUser });
        actualUser.setDescription("new");
        actualUser.setActive(true);
        this.instance.updateUserTypes(new UserType[] { actualUser });
        UserType expectedUser = this.instance.getUserTypes(new long[] { actualUser.getId() })[0];
        AccuracyTestHelper.assertUserTypeEquals(expectedUser, actualUser);
    }

    /**
     * Test {@link DbUserTypeDAO#searchUserTypes(Filter)}.
     * @throws Exception
     *             to junit.
     */
    public void testSearchUserTypes() throws Exception {
        UserType type = new UserType();
        type.setCompanyId(1);
        type.setActive(false);
        type.setChanged(false);
        type.setCreationDate(new Date());
        type.setCreationUser("me");
        type.setModificationDate(new Date());
        type.setModificationUser("user");
        type.setDescription("ds");

        this.instance.addUserTypes(new UserType[] { type});
        UserType[] users = this.instance.searchUserTypes(UserTypeFilterFactory.createCompanyIdFilter(1));
        assertEquals("Equal to 10.", 10, users.length);
    }

    /**
     * Test {@link DbUserTypeDAO#searchUserTypes(Filter)}.
     * @throws Exception
     *             to junit.
     */
    public void testSearchUserTypes_2() throws Exception {
        UserType user = this.instance.searchUserTypes(UserTypeFilterFactory
                .createDescriptionFilter("Project Manager"))[0];
        assertEquals("The description are not equals.", "Project Manager", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * Test {@link DbUserTypeDAO#searchUserTypes(Filter)}.
     * @throws Exception
     *             to junit.
     */
    public void testSearchUserTypes_3() throws Exception {
        UserType type = new UserType();
        type.setCompanyId(1);
        type.setActive(false);
        type.setChanged(false);
        type.setCreationDate(new Date());
        type.setCreationUser("me");
        type.setModificationDate(new Date());
        type.setModificationUser("user");
        type.setDescription("ds");

        this.instance.addUserTypes(new UserType[] { type});
        UserType[] users = this.instance.searchUserTypes(UserTypeFilterFactory.createModificationUserFilter("user"));
        assertEquals("Equal to 1.", 1, users.length);
    }

    /**
     * Test {@link DbUserTypeDAO#getUserTypes(long[])}.
     * @throws Exception
     *             to junit.
     */
    public void testGetUserType() throws Exception {
        UserType user = this.instance.getUserTypes(new long[] { 1 })[0];
        assertEquals("The description are not equals.", "Project Manager", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * Test {@link DbUserTypeDAO#getAllUserTypes()}.
     * @throws Exception
     *             to junit.
     */
    public void testGetAllUserTypes() throws Exception {
        assertEquals("getAllUserTypes failed", 9, this.instance.getAllUserTypes().length);
    }
}
