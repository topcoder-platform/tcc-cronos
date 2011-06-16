/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.Constants;

/**
 * <p>
 * This class contains Accuracy tests for UserDAOHibernate.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class UserDAOHibernateAccuracyTest {

    /**
     * <p>
     * Represents UserDAOHibernate instance for testing.
     * </p>
     */
    private UserDAOHibernate userDAOHibernate;

    /**
     * Represents User instance for testing.
     */
    private User user;

    /**
     * <p>
     * Creates TestSuite that aggregates all tests for class under test.
     * </p>
     * @return TestSuite that aggregates all tests for class under test.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserDAOHibernateAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        userDAOHibernate = new UserDAOHibernate();
        user = AccuracyHelper.createUserInPersistence(AccuracyHelper.HANDLE, AccuracyHelper.EMAIL_ADDRESS, true);
        HibernateUtils.begin();
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
        userDAOHibernate = null;
        user = null;
    }

    /**
     * <p>
     * Tests UserDAOHibernate default constructor.
     * </p>
     * <p>
     * UserDAOHibernate should be created successfully.
     * </p>
     */
    @Test
    public void testCtor1() {
        assertNotNull("UserDAOHibernate should be created successfully.", userDAOHibernate);
    }

    /**
     * <p>
     * Tests UserDAOHibernate constructor with passed Session.
     * </p>
     * <p>
     * UserDAOHibernate should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        userDAOHibernate = new UserDAOHibernate(HibernateUtils.getSession());
        assertNotNull("UserDAOHibernate should be created successfully.", userDAOHibernate);
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(Long)} with valid argument passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Id1() {
        Long id = user.getId();
        User actualUser = userDAOHibernate.find(id);
        assertEquals("User should be retrieved successfully.", user.getId(), actualUser.getId());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(Long)} with valid argument passed.
     * </p>
     * <p>
     * null should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Id2() {
        Long id = -1L;
        User actualUser = userDAOHibernate.find(id);
        assertNull("null should be retrieved successfully.", actualUser);
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, boolean)} with valid argument passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Name1() {
        String userName = AccuracyHelper.HANDLE.toLowerCase();
        User actualUser = userDAOHibernate.find(userName, true);
        assertEquals("User should be retrieved successfully.", user.getId(), actualUser.getId());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, boolean)} with valid argument passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Name2() {
        String userName = AccuracyHelper.HANDLE.toLowerCase();
        User actualUser = userDAOHibernate.find(userName, false);
        assertNull("null should be retrieved successfully.", actualUser);
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, boolean, boolean)} with valid argument passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Name3() {
        String userName = AccuracyHelper.HANDLE;
        User actualUser = userDAOHibernate.find(userName, true, true);
        assertEquals("User should be retrieved successfully.", user, actualUser);
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, boolean, boolean)} with valid argument passed.
     * </p>
     * <p>
     * null should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Name4() {
        String userName = AccuracyHelper.HANDLE;
        // user is inactive
        user.setStatus(Constants.INACTIVE_STATI[1]);
        HibernateUtils.getSession().persist(user);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        User actualUser = userDAOHibernate.find(userName, true, true);
        assertNull("null should be retrieved successfully.", actualUser);
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, String, String, String)} with valid argument passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_All1() {
        List < User > actualUsers = userDAOHibernate.find(AccuracyHelper.HANDLE, "accuracyFirst", "accuracyLast",
                AccuracyHelper.EMAIL_ADDRESS);
        assertEquals("User should be retrieved successfully.", 1, actualUsers.size());
        assertEquals("User should be retrieved successfully.", user.getId(), actualUsers.get(0).getId());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, String, String, String)} with valid argument passed.
     * </p>
     * <p>
     * Users should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_All2() {
        AccuracyHelper.createUserInPersistence(AccuracyHelper.HANDLE + "2", AccuracyHelper.EMAIL_ADDRESS + "2", true);
        HibernateUtils.begin();
        List < User > actualUsers = userDAOHibernate.find(null, "accuracyFirst", null, null);
        assertEquals("Users should be retrieved successfully.", 2, actualUsers.size());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#saveOrUpdate(User)} with valid argument passed.
     * </p>
     * <p>
     * User should be updated successfully.
     * </p>
     */
    @Test
    public void testSaveOrUpdate1() {
        String newFirstName = "new first name";
        user.setFirstName(newFirstName);
        // update user first name
        userDAOHibernate.saveOrUpdate(user);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        User actualUser = userDAOHibernate.find(user.getId());
        assertEquals("User should be updated successfully.", newFirstName, actualUser.getFirstName());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#saveOrUpdate(User)} with valid argument passed.
     * </p>
     * <p>
     * User should be saved successfully.
     * </p>
     */
    @Test
    public void testSaveOrUpdate2() {
        User user1 = AccuracyHelper.createUserInPersistence(AccuracyHelper.HANDLE + "2", AccuracyHelper.EMAIL_ADDRESS
                + "2", false);
        // save user
        userDAOHibernate.saveOrUpdate(user1);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        User actualUser = userDAOHibernate.find(user1.getHandle(), true);
        assertEquals("User should be saved successfully.", user1.getFirstName(), actualUser.getFirstName());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#saveOrUpdate(User)} with valid argument passed.
     * </p>
     * <p>
     * User should be saved successfully with demographic responses.
     * </p>
     */
    @Test
    public void testSaveOrUpdate3() {
        String text = "some text";
        User user1 = AccuracyHelper.createUserInPersistence(AccuracyHelper.HANDLE + "2", AccuracyHelper.EMAIL_ADDRESS
                + "2", false);
        AccuracyHelper.createDemographicResponse(user1, text);
        // save user
        userDAOHibernate.saveOrUpdate(user1);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        User actualUser = userDAOHibernate.find(user1.getHandle(), true);
        assertEquals("User should be updated successfully.", text, actualUser.getDemographicResponses().iterator()
                .next().getResponse());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#saveOrUpdate(User)} with valid argument passed.
     * </p>
     * <p>
     * User should be updated successfully with demographic responses.
     * </p>
     */
    @Test
    public void testSaveOrUpdate4() {
        String text = "some text";
        User user1 = AccuracyHelper.createUserInPersistence(AccuracyHelper.HANDLE + "2", AccuracyHelper.EMAIL_ADDRESS
                + "2", false);
        AccuracyHelper.createDemographicResponse(user1, text);
        // save user
        userDAOHibernate.saveOrUpdate(user1);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        // retrieve user with some demographic responses
        User actualUser = userDAOHibernate.find(user1.getHandle(), true);
        // create new response
        String newText = "new response text";
        AccuracyHelper.createDemographicResponse(actualUser, newText);
        userDAOHibernate.saveOrUpdate(actualUser);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        actualUser = userDAOHibernate.find(user1.getHandle(), true);
        assertEquals("User should be updated successfully.", newText, actualUser.getDemographicResponses().iterator()
                .next().getResponse());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#find(String, String, String, String)} with valid argument passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     */
    @Test
    public void testFind_Email() {
        User actualUser = userDAOHibernate.find(AccuracyHelper.EMAIL_ADDRESS);
        assertEquals("User should be retrieved successfully.", user.getId(), actualUser.getId());
    }

    /**
     * <p>
     * Tests {@link UserDAOHibernate#canChangeHandle(String)} with valid argument passed.
     * </p>
     * <p>
     * User cannot change handle
     * </p>
     */
    @Test
    public void testCanChangeHandle() {
        assertFalse("User cannot change handle.", userDAOHibernate.canChangeHandle(user.getHandle()));
    }
}
