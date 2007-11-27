/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;


import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserDAO;
import com.topcoder.timetracker.user.UserManagerImpl;

/**
 * <p>
 * Failure test for <code>{@link UserManagerImpl}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserManagerImplFailureTests extends MockObjectTestCase {
    /**
     * <p>
     * Represents the UserManagerImpl instance used for testing.
     * </p>
     */
    private UserManagerImpl userManagerImpl;

    /**
     * <p>
     * the UserDAO interface.
     * </p>
     */
    private UserDAO userDAO;

    /**
     * <p>
     * Mock of the UserDAO interface.
     * </p>
     */
    private Mock mockAuthorizationPersistence;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        mockAuthorizationPersistence = mock(AuthorizationPersistence.class);

        userDAO = new MockUserDAO();
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.FAILURE_CONFIG_ROOT + "config.xml");
        FailureTestHelper.setUpDataBase();

        userManagerImpl = new UserManagerImpl(userDAO, (AuthorizationPersistence) mockAuthorizationPersistence.proxy(),
            "Default_TT_UserAuthenticator");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#UserManagerImpl(UserDAO, AuthorizationPersistence, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerImpl_NullUserDAO() throws Exception {
        try {
            new UserManagerImpl(null, (AuthorizationPersistence) mockAuthorizationPersistence.proxy(),
                "AuthenticatorName");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#UserManagerImpl(UserDAO, AuthorizationPersistence, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUserManagerImpl_NullAuthorizationPersistence() throws Exception {
        try {
            new UserManagerImpl(userDAO, null, "AuthenticatorName");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#UserManagerImpl(UserDAO, AuthorizationPersistence, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_userManagerImpl_NullAuthenticatorName() throws Exception {
        try {
            new UserManagerImpl(userDAO, (AuthorizationPersistence) mockAuthorizationPersistence.proxy(), null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#UserManagerImpl(UserDAO, AuthorizationPersistence, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_userManagerImpl_EmptyAuthenticatorName() throws Exception {
        try {
            new UserManagerImpl(userDAO, (AuthorizationPersistence) mockAuthorizationPersistence.proxy(), "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#UserManagerImpl(UserDAO, AuthorizationPersistence, String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_userManagerImpl_TrimmedEmptyAuthenticatorName() throws Exception {
        try {
            new UserManagerImpl(userDAO, (AuthorizationPersistence) mockAuthorizationPersistence.proxy(), " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#UserManagerImpl(UserDAO, AuthorizationPersistence, String)}</code>
     * constructor.
     * </p>
     */
    public void testUserManagerImpl_ConfigurationException() {
        try {
            new UserManagerImpl(userDAO, (AuthorizationPersistence) mockAuthorizationPersistence.proxy(),
                "AuthenticatorName");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#createUser(User, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateUser_NullUser() throws Exception {
        try {
            userManagerImpl.createUser(null, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#createUser(User, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateUser_DataAccessException() throws Exception {
        User user = new User();

        try {
            userManagerImpl.createUser(user, false);
            fail("expect throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#updateUser(User, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUser_NullUser() throws Exception {
        try {
            userManagerImpl.updateUser(null, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#updateUser(User, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUser_DataAccessException() throws Exception {
        User user = new User();

        try {
            userManagerImpl.updateUser(user, false);
            fail("expect throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#removeUser(long, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUser_DataAccessException() throws Exception {
        try {
            userManagerImpl.removeUser(1, false);
            fail("expect throw DataAccessException.");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#getUser(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUser_DataAccessException() throws Exception {
        try {
            userManagerImpl.getUser(1);
            fail("expect throw DataAccessException.");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#searchUsers(Filter)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchUsers() {
        try {
            userManagerImpl.searchUsers((Filter) mock(Filter.class).proxy());
            fail("expect throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#addRoleToUser(User, SecurityRole)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddRoleToUser_NullUser() throws Exception {
        SecurityRole role = (SecurityRole) mock(SecurityRole.class).proxy();

        try {
            userManagerImpl.addRoleToUser(null, role);

            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#addRoleToUser(User, SecurityRole)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddRoleToUser_NullRole() throws Exception {
        try {
            userManagerImpl.addRoleToUser(new User(), null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#removeRoleFromUser(User, SecurityRole)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveRoleFromUser_NullUser() throws Exception {
        SecurityRole role = (SecurityRole) mock(SecurityRole.class).proxy();

        try {
            userManagerImpl.removeRoleFromUser(null, role);

            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#removeRoleFromUser(User, SecurityRole)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveRoleFromUser_NullRole() throws Exception {
        try {
            userManagerImpl.removeRoleFromUser(new User(), null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#retrieveRolesForUser(User)}</code> method.
     * </p>
     * @throws Exception
     */
    public void testRetrieveRolesForUser_NullUser() throws Exception {
        try {
            userManagerImpl.retrieveRolesForUser(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#clearRolesFromUser(User)}</code> method.
     * </p>
     * @throws Exception
     */
    public void testClearRolesFromUser_NullUser() throws Exception {
        try {
            userManagerImpl.clearRolesFromUser(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#authenticateUser(String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuthenticateUser_NullUserName() throws Exception {
        try {
            userManagerImpl.authenticateUser(null, "password");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#authenticateUser(String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuthenticateUser_EmptyUserName() throws Exception {
        try {
            userManagerImpl.authenticateUser("", "password");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#authenticateUser(String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuthenticateUser_TrimmedUserName() throws Exception {
        try {
            userManagerImpl.authenticateUser(" ", "password");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#authenticateUser(String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuthenticateUser_NullPassword() throws Exception {
        try {
            userManagerImpl.authenticateUser("username", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#authenticateUser(String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuthenticateUser_EmptyPassword() throws Exception {
        try {
            userManagerImpl.authenticateUser("username", "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserManagerImpl#authenticateUser(String, String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuthenticateUser_TrimmedPassword() throws Exception {
        try {
            userManagerImpl.authenticateUser("username", " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
