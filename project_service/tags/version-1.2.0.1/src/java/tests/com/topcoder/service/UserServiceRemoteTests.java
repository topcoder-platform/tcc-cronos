/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.topcoder.service.user.Address;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.service.user.UserServiceRemote;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.Test;

/**
 * <p>
 * Unit test for the <code>UserServiceBean</code> methods that were added in
 * the 1.1 version.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class UserServiceRemoteTests extends BaseUnitTestCase {

    /**
     * group id that does not exist.
     */
    private static final long TEST_NON_EXISTING_GROUP_ID = 823747;

    /**
     * group id 1 used in tests.
     */
    private static final long TEST_GROUP_ID1 = 888801;

    /**
     * group id 2 used in tests.
     */
    private static final long TEST_GROUP_ID2 = 888802;

    /**
     * group id 3 used in tests.
     */
    private static final long TEST_GROUP_ID3 = 888803;

    /**
     * terms of use id used in tests.
     */
    private static final long TEST_TERMS_ID = 666601;

    /**
     * group id that does not exist.
     */
    private static final long TEST_NON_EXISTING_TERMS_ID = 792831;

    /**
     * <p>
     * Aggregates all Unit tests in this class.
     * </p>
     *
     * @return Test suite aggregating all Unit tests in this class
     */
    public static Test suite() {
        return suite(UserServiceRemoteTests.class);
    }

    /**
     * Tests the <code>registerUser</code> method registering a new user and then
     * retrieving the user info verifying that they match.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRegisterUser() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo5");

        long userId = userService.registerUser(user);
        user.setUserId(userId);

        UserInfo retrievedUser = userService.getUserInfo("foo5");

        assertUserInfoEquals(user, retrievedUser);

        EntityManager em = getEntityManager();
        EntityTransaction et = getEntityTransaction();

        if (!et.isActive()) {
            et.begin();
        }

        try {
            // check that the association was created
            Query query = em.createNativeQuery(
                    "select login_id from security_user where user_id = :handle");
            query.setParameter("handle", "foo5");

            assertFalse("security_user entry missing", query.getResultList().isEmpty());
        } finally {
            et.commit();
            em.clear();
        }
    }

    /**
     * Tests the <code>registerUser</code> method registering a new user with groups
     * associated and then retrieving the user info verifying that they match.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRegisterUserWithGroups() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo2");
        user.setGroupIds(new long[] {TEST_GROUP_ID1, TEST_GROUP_ID2, TEST_GROUP_ID3});

        long userId = userService.registerUser(user);
        user.setUserId(userId);

        UserInfo retrievedUser = userService.getUserInfo("foo2");

        assertUserInfoEquals(user, retrievedUser);
    }

    /**
     * Tests the <code>registerUser</code> method with illegal parameters.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRegisterUserIllegalArguments() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // null user
        try {
            userService.registerUser(null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        boolean emptyValue = false;
        for (int i = 1; i <= 14;) {
            User user = createIllegalUser(i, emptyValue);
            // null/empty field
            try {
                userService.registerUser(user);
                fail("IllegalArgumentException expected");
            } catch (EJBException e) {
                if (!(e.getCause() instanceof IllegalArgumentException)) {
                    throw e;
                }
            }
            if (emptyValue) {
                i++;
            } else {
                emptyValue = true;
            }
        }
    }

    /**
     * Creates a user with a required field null or empty. The field is determined by the index parameter.
     *
     * @param index
     *            used to specify which field is to be set with an empty or null value (valid values are from 1 to 14).
     * @param emptyValue
     *            indicates whether the specified field should be empty (true) or null (false).
     * @return a new <code>User</code> instance with all the fields set with default values
     * @throws IllegalArgumentException
     *             if the index is not associated with a field
     */
    public User createIllegalUser(int index, boolean emptyValue) {

        User user = createDefaultUser("foo");

        String value;
        if (emptyValue) {
            value = "";
        } else {
            value = null;
        }

        if (index == 1) {
            user.setAddress(null);
        } else if (index == 2) {
            user.getAddress().setAddress1(value);
        } else if (index == 3) {
            user.getAddress().setAddress1(value);
        } else if (index == 4) {
            user.getAddress().setCity(value);
        } else if (index == 5) {
            user.getAddress().setCountryCode(value);
        } else if (index == 6) {
            user.getAddress().setZip(value);
        } else if (index == 7) {
            user.getAddress().setProvince(value);
        } else if (index == 8) {
            user.setAddress(null);
        } else if (index == 9) {
            user.setEmailAddress(value);
        } else if (index == 10) {
            user.setFirstName(value);
        } else if (index == 11) {
            user.setLastName(value);
        } else if (index == 12) {
            user.setPassword(value);
        } else if (index == 13) {
            user.setPhone(value);
        } else if (index == 14) {
            user.setHandle(value);
        } else {
            throw new IllegalArgumentException("Wrong index");
        }
        return user;
    }

    /**
     * Tests the <code>getUserInfo</code> method with illegal parameters.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testGetUserInfoIllegalArguments() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // null handle
        try {
            userService.getUserInfo(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // empty handle
        try {
            userService.getUserInfo(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests the <code>getUserInfo</code> method with a non-existent user handle.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testGetUserInfoUserNotFound() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.getUserInfo("abcdfooxyz912");
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>addUserToGroups</code> method registering a new user with one group
     * and then adding another, and then tries to add a non existing group.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testAddUserToGroups() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User expectedUser = createDefaultUser("foo3");
        expectedUser.setGroupIds(new long[] {TEST_GROUP_ID1});

        // register the expected user without groups
        long userId = userService.registerUser(expectedUser);

        // complete the expected user
        expectedUser.setUserId(userId);
        expectedUser.setGroupIds(new long[] {TEST_GROUP_ID1, TEST_GROUP_ID2});

        // add the groups using the addUserToGroups method
        // note that the user is already associated to TEST_GROUP_ID1
        userService.addUserToGroups("foo3", new long[] {TEST_GROUP_ID1, TEST_GROUP_ID2});

        UserInfo retrievedUser = userService.getUserInfo("foo3");

        // assert that both are the same
        assertUserInfoEquals(expectedUser, retrievedUser);
    }

    /**
     * Tests the <code>addUserToGroups</code> method with illegal parameters.
     *
     * @throws Exception pass any unhandled exception to JUnit.
     */
    public void testAddUserToGroupsIllegalArguments() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // null handle
        try {
            long[] groupIds = new long[] {1, 2};
            userService.addUserToGroups(null, groupIds);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // empty handle
        try {
            long[] groupIds = new long[] {1, 2};
            userService.addUserToGroups(" ", groupIds);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // null groupIds
        try {
            userService.addUserToGroups("abc", null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // empty groupIds
        try {
            long[] groupIds = new long[] {};
            userService.addUserToGroups("abc", groupIds);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // groupIds contains non-positive values
        try {
            long[] groupIds = new long[] {1, 0};
            userService.addUserToGroups("abc", groupIds);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // groupIds contains non-positive values
        try {
            long[] groupIds = new long[] {1, 2, -1};
            userService.addUserToGroups("abc", groupIds);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }
    }

    /**
     * Tests the <code>addUserToGroups</code> method with an unexisting user handle.
     *
     * @throws Exception pass any unhandled exception to JUnit.
     */
    public void testAddUserToGroupsUserNotFound() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            long[] groupIds = new long[] {TEST_GROUP_ID1, TEST_GROUP_ID2};
            userService.addUserToGroups("abcdfooxyz912", groupIds);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
             // expected
        }

    }

    /**
     * Tests the <code>addUserToGroups</code> method with an unexisting group.
     *
     * @throws Exception pass any unhandled exception to JUnit.
     */
    public void testAddUserToGroupsGroupNotFound() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.addUserToGroups("foo", new long[] {TEST_NON_EXISTING_GROUP_ID});
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>removeUserFromGroups</code> method registering a new user with 3 groups
     * and then removing one.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserFromGroups() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User expectedUser = createDefaultUser("foo4");
        expectedUser.setGroupIds(new long[] {TEST_GROUP_ID1, TEST_GROUP_ID2, TEST_GROUP_ID3});

        // register the expected user with 3 groups
        long userId = userService.registerUser(expectedUser);

        // remove groups using the removeUserFromGroups method
        userService.removeUserFromGroups("foo4", new long[] {TEST_GROUP_ID2});

        // complete the expected user
        expectedUser.setUserId(userId);
        expectedUser.setGroupIds(new long[] {TEST_GROUP_ID1, TEST_GROUP_ID3});

        UserInfo retrievedUser = userService.getUserInfo("foo4");

        // assert that both are the same
        assertUserInfoEquals(expectedUser, retrievedUser);
    }

    /**
     * Tests the <code>removeUserFromGroups</code> method with illegal parameters.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserFromGroupsIllegalArguments() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // null handle
        try {
            userService.removeUserFromGroups(null, new long[] {TEST_GROUP_ID2});
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // empty handle
        try {
            userService.removeUserFromGroups(" ", new long[] {TEST_GROUP_ID2});
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // null groupIds
        try {
            userService.removeUserFromGroups("abc", null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // empty groupIds
        try {
            userService.removeUserFromGroups("abc", new long[0]);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // groupIds with non-positive values
        try {
            userService.removeUserFromGroups("abc", new long[] {1, 0});
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // groupIds with non-positive values
        try {
            userService.removeUserFromGroups("abc", new long[] {2, -1});
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }
    }

    /**
     * Tests the <code>removeUserFromGroups</code> method with a non-existing group.
     *
     * @throws Exception pass any unhandled exception to JUnit.
     */
    public void testRemoveUserFromGroupsUserNotFound() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.removeUserFromGroups("abcdfooxyz912", new long[] {TEST_GROUP_ID1});
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>removeUserFromGroups</code> method with a non-existing group.
     *
     * @throws Exception pass any unhandled exception to JUnit.
     */
    public void testRemoveUserFromGroupsNonExistingGroup() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.removeUserFromGroups("foo", new long[] {TEST_NON_EXISTING_GROUP_ID});
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>removeUserFromGroups</code> method with a group not associated to the user.
     *
     * @throws Exception pass any unhandled exception to JUnit.
     */
    public void testRemoveUserFromGroupsGroupNotAssociated() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo10");
        userService.registerUser(user);

        try {
            userService.removeUserFromGroups("foo10", new long[] {TEST_GROUP_ID1});
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>addUserTerm</code> method.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testAddUserTerm() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo1");

        long userId = userService.registerUser(user);

        Date nowDate = new Date();

        // simply add a term
        userService.addUserTerm("foo1", TEST_TERMS_ID, nowDate);

        EntityManager em = getEntityManager();
        EntityTransaction et = getEntityTransaction();

        if (!et.isActive()) {
            et.begin();
        }

        try {
            // check that the association was created
            Query query = em.createNativeQuery(
                    "select terms_of_use_id from user_terms_of_use_xref where "
                    + "user_id = :userId and terms_of_use_id = :termsId");
            query.setParameter("userId", userId);
            query.setParameter("termsId", TEST_TERMS_ID);

            assertFalse("The association was not made", query.getResultList().isEmpty());
        } finally {
            et.commit();
            em.clear();
        }
    }

    /**
     * Tests the <code>addUserTerm</code> method with illegal parameters.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testAddUserTermIllegalArguments() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // null handle
        try {
            userService.addUserTerm(null, 1, null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // empty handle
        try {
            userService.addUserTerm(" ", 1, null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // non-positive term id
        try {
            userService.addUserTerm("abc", 0, null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // non-positive term id
        try {
            userService.addUserTerm("abc", -1, null);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }
    }

    /**
     * Tests the <code>addUserTerm</code> method with a non existing user handle.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testAddUserTermUserNotFound() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.addUserTerm("abcdfooxyz912", TEST_TERMS_ID, null);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>addUserTerm</code> method with a non existing term id.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testAddUserTermNonexistingTerms() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.addUserTerm("foo", TEST_NON_EXISTING_TERMS_ID, null);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>addUserTerm</code> method with an already associated term id.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testAddUserTermAlreadyAsociated() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo11");

        userService.registerUser(user);

        userService.addUserTerm("foo11", TEST_TERMS_ID, null);

        try {
            userService.addUserTerm("foo11", TEST_TERMS_ID, null);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>removeUserTerm</code> method.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserTerm() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo6");

        long userId = userService.registerUser(user);

        Date nowDate = new Date();

        // add a term and then remove it
        userService.addUserTerm("foo6", TEST_TERMS_ID, nowDate);
        userService.removeUserTerm("foo6", TEST_TERMS_ID);

        EntityManager em = getEntityManager();
        EntityTransaction et = getEntityTransaction();

        if (!et.isActive()) {
            et.begin();
        }

        try {
            // now lets check that the association does not exist
            Query query = em.createNativeQuery(
                    "select terms_of_use_id from user_terms_of_use_xref where "
                    + "user_id = :userId and terms_of_use_id = :termsId");
            query.setParameter("userId", userId);
            query.setParameter("termsId", TEST_TERMS_ID);

            assertTrue("The association was not removed", query.getResultList().isEmpty());
        } finally {
            et.commit();
            em.clear();
        }
    }

    /**
     * Tests the <code>removeUserTerm</code> method with illegal parameters.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserTermIllegalArguments() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // null handle
        try {
            userService.removeUserTerm(null, 1);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // empty handle
        try {
            userService.removeUserTerm(" ", 1);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // non-positive term id
        try {
            userService.removeUserTerm("abc", 0);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }

        // non-positive term id
        try {
            userService.removeUserTerm("abc", -1);
            fail("IllegalArgumentException expected");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw e;
            }
        }
    }

    /**
     * Tests the <code>removeUserTerm</code> method with a non existing user handle.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserTermUserNotFound() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.removeUserTerm("abcdfooxyz912", TEST_TERMS_ID);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>removeUserTerm</code> method with terms not associated.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserTermTermNotAssociated() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        User user = createDefaultUser("foo13");
        userService.registerUser(user);

        try {
            userService.removeUserTerm("foo13", TEST_TERMS_ID);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Tests the <code>removeUserTerm</code> method with a non existing term id.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testRemoveUserTermNonexistingTerms() throws Exception {

        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        try {
            userService.removeUserTerm("foo", TEST_NON_EXISTING_TERMS_ID);
            fail("UserServiceException expected");
        } catch (UserServiceException e) {
            // expected
        }
    }

    /**
     * Asserts that two given <code>UserInfo</code> instances match.
     * @param expected the expected user info
     * @param actual the actual user info
     */
    public void assertUserInfoEquals(UserInfo expected, UserInfo actual) {
        assertEquals("users don't match", expected.getHandle(), actual.getHandle());
        assertEquals("users don't match", expected.getEmailAddress(), actual.getEmailAddress());
        assertEquals("users don't match", expected.getLastName(), actual.getLastName());
        assertEquals("users don't match", expected.getFirstName(), actual.getFirstName());
        long[] expectedGroupIds = expected.getGroupIds();
        long[] actualGroupIds = actual.getGroupIds();
        Arrays.sort(expectedGroupIds);
        Arrays.sort(actualGroupIds);
        assertTrue("users don't match", Arrays.equals(expectedGroupIds, actualGroupIds));
    }

    /**
     * Creates a user with all the fields set with default values.
     * @param handle the user handle
     * @return a new <code>User</code> instance with all the fields set with default values
     */
    public User createDefaultUser(String handle) {

        Address address = new Address();
        address.setAddress1("foo1");
        address.setAddress2("foo2");
        address.setAddress3("foo3");
        address.setCity("nycity");
        address.setCountryCode("us");
        address.setZip("07870");
        address.setProvince("foo");
        address.setStateCode("ny");

        User user = new User();
        user.setAddress(address);
        user.setEmailAddress("foo@foo.com");
        user.setFirstName("Foo");
        user.setLastName("Foo");
        user.setPassword("foo");
        user.setPhone("55738293");
        user.setHandle(handle);
        user.setGroupIds(new long[0]);

        return user;
    }
}
