/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.UserTypeManager;
import com.topcoder.timetracker.user.filterfactory.UserTypeFilterFactory;

/**
 * Accuracy test cases for class <code>UserTypeManager </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public abstract class AbstractUserTypeManagerAccuracyTests extends TestCase {
    /**
     * <p>
     * UserTypeManager instance for testing.
     * </p>
     */
    private UserTypeManager instance;

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected abstract UserTypeManager getTestInstance() throws Exception;

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
     * <p>
     * Accuracy test for{@link UserTypeManager#addUserTypes(UserType[])} method.
     * </p>
     * <p>
     * Checks whether multiple users added properly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testAddUserTypes() throws Exception {
        UserType type = AccuracyTestHelper.createTestingUserType("Id");
        UserType type2 = AccuracyTestHelper.createTestingUserType("Id");
        instance.addUserTypes(new UserType[] { type, type2 });
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type.getId()), type);
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type2.getId()), type2);
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#createUserType(UserType)} method.
     * </p>
     * <p>
     * Creates a new user type and verifies the creation.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateUserType() throws Exception {
        UserType type = AccuracyTestHelper.createTestingUserType("Id");
        instance.createUserType(type);
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type.getId()), type);
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#updateUserType(UserType)} method.
     * </p>
     * <p>
     * Creates an UserType, verifies the creation. Updates the UserType and then verifies the update.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateUserType() throws Exception {
        UserType type = AccuracyTestHelper.createTestingUserType("Id");
        instance.createUserType(type);
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type.getId()), type);
        type.setActive(false);
        type.setDescription("new");
        instance.updateUserType(type);
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type.getId()), type);
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#removeUserType(long)} method.
     * </p>
     * <p>
     * Creates an UserType, verifies the creation, Removes the UserType and verifies the removal.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveUserType() throws Exception {
        instance.removeUserType(1);
        UserType[] type = instance.getAllUserTypes();
        for (int i = 0; i < type.length; i++) {
            if (type[i].getId() == 1) {
                fail("removeUserType failed");
            }
        }
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#getUserType(long)} method.
     * </p>
     * <p>
     * Checks whether the data is retrieved correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetUserType() throws Exception {
        UserType user = instance.getUserType(1);
        assertEquals("The description are not equals.", "Project Manager", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#searchUserTypes(Filter)} method.
     * </p>
     * <p>
     * Checks whether the result is correct.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSearchUserTypes() throws Exception {
        UserType user = instance.searchUserTypes(UserTypeFilterFactory.createDescriptionFilter("Project Manager"))[0];
        assertEquals("The description are not equals.", "Project Manager", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#updateUserTypes(UserType[])} method.
     * </p>
     * <p>
     * Updates and verifies.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateUserTypes() throws Exception {
        UserType type = AccuracyTestHelper.createTestingUserType("Id");
        UserType type2 = AccuracyTestHelper.createTestingUserType("Id");
        instance.addUserTypes(new UserType[] { type, type2 });
        type.setDescription("new");
        type2.setDescription("type");
        instance.updateUserTypes(new UserType[] { type, type2 });
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type.getId()), type);
        AccuracyTestHelper.assertUserTypeEquals(instance.getUserType(type2.getId()), type2);
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#removeUserTypes(long[])} method.
     * </p>
     * <p>
     * Checks whether all the UserType are removed.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testRemoveUserTypes() throws Exception {
        instance.removeUserTypes(new long[] { 1, 2 });
        UserType[] type = instance.getAllUserTypes();
        for (int i = 0; i < type.length; i++) {
            if (type[i].getId() == 1 || type[i].getId() == 2) {
                fail("removeUserTypes failed");
            }
        }
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#getUserTypes(long[])} method.
     * </p>
     * <p>
     * Checks whether the values are retrieved properly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetUserTypes() throws Exception {
        UserType user = instance.getUserTypes(new long[] { 1 })[0];
        assertEquals("The description are not equals.", "Project Manager", user.getDescription());
        assertEquals("The active is not equal.", true, user.isActive());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The creation users are not equals.", "System", user.getCreationUser());
        assertEquals("The modification users are not equals.", "System", user.getModificationUser());
        assertNotNull("The creation date is not set.", user.getCreationDate());
        assertNotNull("The modification date is not set.", user.getModificationDate());
    }

    /**
     * <p>
     * Accuracy test for{@link UserTypeManager#getAllUserTypes()} method.
     * </p>
     * <p>
     * Checks whether the value is retrieved properly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllUserTypes() throws Exception {
        assertEquals("getAllUserTypes failed", 9, instance.getAllUserTypes().length);
    }
}
