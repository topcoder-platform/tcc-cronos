/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.ejb.user.UserBean;
import com.topcoder.web.reg.Constants;

/**
 * This class contains Accuracy tests for UserBean.
 * @author sokol
 * @version 1.0
 */
public class UserBeanAccuracyTest {

    /**
     * <p>
     * Represents UserBean instance for testing.
     * </p>
     */
    private UserBean userBean;

    /**
     * <p>
     * Represents User instance for testing.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Represents User handle for testing.
     * </p>
     */
    private String handle;

    /**
     * <p>
     * Creates TestSuite that aggregates all tests for class under test.
     * </p>
     * @return TestSuite that aggregates all tests for class under test.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserBeanAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        handle = AccuracyHelper.HANDLE + "UserBeanTest";
        userBean = new UserBean();
        user = AccuracyHelper.createUserInPersistence(handle, AccuracyHelper.EMAIL_ADDRESS, true);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        try {
            AccuracyHelper.clearDatabase();
        } finally {
            HibernateUtils.close();
        }
        userBean = null;
    }

    /**
     * <p>
     * Tests UserBean constructor.
     * </p>
     * <p>
     * UserBean instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("UserBean instance should be created successfully.", userBean);
    }

    /**
     * <p>
     * Tests {@link UserBean#createNewUser(String, char, String, String)} with valid arguments passed.
     * </p>
     * <p>
     * User instance should be created successfully.
     * </p>
     */
    @Test
    public void testCreateNewUser() {
        long id = userBean.createNewUser(AccuracyHelper.HANDLE, AccuracyHelper.ACTIVE_STATUS,
                AccuracyHelper.ACCURACY_DATASOURCE, AccuracyHelper.ACCURACY_DATASOURCE);
        User user = AccuracyHelper.getUser(AccuracyHelper.HANDLE);
        assertEquals("Handle should be equal.", AccuracyHelper.HANDLE, user.getHandle());
        assertEquals("Status should be equal.", AccuracyHelper.ACTIVE_STATUS, user.getStatus());
        assertEquals("id should be equal.", new Long(id), user.getId());
    }

    /**
     * <p>
     * Tests {@link UserBean#createUser(long, String, char, String)} with valid arguments passed.
     * </p>
     * <p>
     * User instance should be created successfully.
     * </p>
     */
    @Test
    public void testCreateUser() {
        long id = 2;
        userBean
                .createUser(id, AccuracyHelper.HANDLE, AccuracyHelper.ACTIVE_STATUS, AccuracyHelper.ACCURACY_DATASOURCE);
        User user = AccuracyHelper.getUser(AccuracyHelper.HANDLE);
        assertEquals("Handle should be equal.", AccuracyHelper.HANDLE, user.getHandle());
        assertEquals("Status should be equal.", AccuracyHelper.ACTIVE_STATUS, user.getStatus());
        assertEquals("id should be equal.", new Long(id), user.getId());
    }

    /**
     * <p>
     * Tests {@link User#setFirstName(String)} with valid arguments passed.
     * </p>
     * <p>
     * First name should be changed.
     * </p>
     */
    @Test
    public void testSetFirstName() {
        String newFirstName = "newName";
        long id = user.getId();
        userBean.setFirstName(id, newFirstName, AccuracyHelper.ACCURACY_DATASOURCE);
        UserProfile actualUser = AccuracyHelper.getUserProfile(id);
        assertEquals("Name should be changed", newFirstName, actualUser.getFirstName());
    }

    /**
     * <p>
     * Tests {@link UserBean#setMiddleName(long, String, String)} with valid arguments passed.
     * </p>
     * <p>
     * Middle name should be changed.
     * </p>
     */
    @Test
    public void testSetMiddleName() {
        String newMiddleName = "newName";
        long id = user.getId();
        userBean.setMiddleName(id, newMiddleName, AccuracyHelper.ACCURACY_DATASOURCE);
        UserProfile actualUser = AccuracyHelper.getUserProfile(id);
        assertEquals("Name should be changed", newMiddleName, actualUser.getMiddleName());
    }

    /**
     * <p>
     * Tests {@link UserBean#setLastName(long, String, String)} with valid arguments passed.
     * </p>
     * <p>
     * Last name should be changed.
     * </p>
     */
    @Test
    public void testSetLastName() {
        String newLastName = "newName";
        long id = user.getId();
        userBean.setLastName(id, newLastName, AccuracyHelper.ACCURACY_DATASOURCE);
        UserProfile actualUser = AccuracyHelper.getUserProfile(id);
        assertEquals("Name should be changed", newLastName, actualUser.getLastName());
    }

    /**
     * <p>
     * Tests {@link UserBean#setStatus(long, char, String)} with valid arguments passed.
     * </p>
     * <p>
     * Status should be changed.
     * </p>
     */
    @Test
    public void testSetStatus() {
        Character newStatus = Constants.INACTIVE_STATI[0];
        long id = user.getId();
        userBean.setStatus(id, newStatus, AccuracyHelper.ACCURACY_DATASOURCE);
        User actualUser = AccuracyHelper.getUser(handle);
        assertEquals("Status should be changed", newStatus, actualUser.getStatus());
    }

    /**
     * <p>
     * Tests {@link UserBean#setActivationCode(long, String, String)} with valid arguments passed.
     * </p>
     * <p>
     * Activation code should be changed.
     * </p>
     */
    @Test
    public void testSetActivationCode() {
        String newActivationCode = "new code";
        long id = user.getId();
        userBean.setActivationCode(id, newActivationCode, AccuracyHelper.ACCURACY_DATASOURCE);
        User actualUser = AccuracyHelper.getUser(handle);
        assertEquals("Acivation code should be changed", newActivationCode, actualUser.getActivationCode());
    }

    /**
     * <p>
     * Tests {@link UserBean#setPassword(long, String, String)} with valid arguments passed.
     * </p>
     * <p>
     * Password should be changed.
     * </p>
     */
    @Test
    public void testSetPassword() {
        String newPassword = "new password";
        long id = user.getId();
        userBean.setPassword(id, newPassword, AccuracyHelper.ACCURACY_DATASOURCE);
        String actualPassword = (String) AccuracyHelper.getUserProperty(id, "password");
        assertEquals("Password should be changed", newPassword, actualPassword);
    }

    /**
     * <p>
     * Tests {@link UserBean#setHandle(long, String, String)} with valid arguments passed.
     * </p>
     * <p>
     * Handle should be changed.
     * </p>
     */
    @Test
    public void testSetHandle() {
        String newHandle = AccuracyHelper.HANDLE + "someNewHandle";
        long id = user.getId();
        userBean.setHandle(id, newHandle, AccuracyHelper.ACCURACY_DATASOURCE);
        String actualHandle = (String) AccuracyHelper.getUserProperty(id, "handle");
        assertEquals("Handle should be changed", newHandle, actualHandle);
    }

    /**
     * <p>
     * Tests {@link UserBean#getHandle(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetHandle() {
        long id = user.getId();
        String expectedValue = user.getHandle();
        String actualValue = userBean.getHandle(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#getFirstName(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetFirstName() {
        long id = user.getId();
        String expectedValue = user.getFirstName();
        String actualValue = userBean.getFirstName(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#getMiddleName(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetMiddleName() {
        long id = user.getId();
        String expectedValue = user.getMiddleName();
        String actualValue = userBean.getMiddleName(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#getLastName(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetLastName() {
        long id = user.getId();
        String expectedValue = user.getLastName();
        String actualValue = userBean.getLastName(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#getStatus(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetStatus() {
        long id = user.getId();
        Character expectedValue = user.getStatus();
        Character actualValue = userBean.getStatus(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#getActivationCode(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetActivationCode() {
        long id = user.getId();
        String expectedValue = user.getActivationCode();
        String actualValue = userBean.getActivationCode(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#getPassword(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * Property should be retrieved successfully.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testGetPassword() throws Exception {
        long id = user.getId();
        String expectedValue = (String) AccuracyHelper.getUserProperty(id, "password");
        String actualValue = userBean.getPassword(id, AccuracyHelper.ACCURACY_DATASOURCE);
        assertEquals("Values should be equal.", expectedValue, actualValue);
    }

    /**
     * <p>
     * Tests {@link UserBean#userExists(String, String)} with valid arguments passed.
     * </p>
     * <p>
     * User should exists.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testUserExists_Handle1() throws Exception {
        assertTrue("User should exists.", userBean.userExists(user.getHandle(), AccuracyHelper.ACCURACY_DATASOURCE));
    }

    /**
     * <p>
     * Tests {@link UserBean#userExists(String, String)} with valid arguments passed.
     * </p>
     * <p>
     * User should not exists.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testUserExists_Handle2() throws Exception {
        assertFalse("User should not exists.", userBean.userExists("Unknown", AccuracyHelper.ACCURACY_DATASOURCE));
    }

    /**
     * <p>
     * Tests {@link UserBean#userExists(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * User should exists.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testUserExists_Id1() throws Exception {
        assertTrue("User should exists.", userBean.userExists(user.getId(), AccuracyHelper.ACCURACY_DATASOURCE));
    }

    /**
     * <p>
     * Tests {@link UserBean#userExists(long, String)} with valid arguments passed.
     * </p>
     * <p>
     * User should not exists.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    @Test
    public void testUserExists_Id2() throws Exception {
        assertFalse("User should not exists.", userBean.userExists(Long.MAX_VALUE, AccuracyHelper.ACCURACY_DATASOURCE));
    }
}
