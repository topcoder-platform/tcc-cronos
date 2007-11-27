/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.TestHelper;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for UserManagerDelegate.
 * </p>
 *
 * <p>
 * In this test case, MockEJB is used to deploy the UserManagerSessionBean and UserManagerDelegate is used to visit
 * the UserManagerSessionBean.
 * </p>
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserManagerDelegateTests extends TestCase {
    /**
     * <p>
     * UserManagerDelegate instance used to test the functionality of UserManagerSessionBean.
     * </p>
     */
    private UserManagerDelegate delegate;

    /**
     * <p>
     * The AuthorizationPersistence instance for testing.
     * </p>
     */
    private AuthorizationPersistence authPersistence;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);
        TestHelper.setUpDataBase();

        authPersistence =
            new SQLAuthorizationPersistence("com.topcoder.timetracker.application.authorization");

        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system
         * properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor =
            new SessionBeanDescriptor("UserManagerLocalHome", UserManagerLocalHome.class,
                UserManagerLocal.class, new UserManagerSessionBean());

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new UserManagerDelegate("com.topcoder.timetracker.user.j2ee.UserManagerDelegate");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
        authPersistence = null;
        delegate = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserManagerDelegateTests.class);
    }

    /**
     * <p>
     * Tests ctor UserManagerDelegate#UserManagerDelegate(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created UserManagerDelegate instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new UserManagerDelegate instance.", delegate);
    }

    /**
     * <p>
     * Tests ctor UserManagerDelegate#UserManagerDelegate(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullNamespace() throws Exception {
        try {
            new UserManagerDelegate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserManagerDelegate#UserManagerDelegate(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_EmptyNamespace() throws Exception {
        try {
            new UserManagerDelegate(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserManagerDelegate#UserManagerDelegate(String) for failure.
     * </p>
     *
     * <p>
     * Expects for ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_ConfigurationException() throws Exception {
        TestHelper.clearConfigFile("com.topcoder.timetracker.user.j2ee.UserManagerDelegate");
        try {
            new UserManagerDelegate("com.topcoder.timetracker.user.j2ee.UserManagerDelegate");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#createUser(User,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#createUser(User,boolean) is correct.
     * </p>
     *
     * @throws DataAccessException
     *             to JUnit
     */
    public void testCreateUser() throws DataAccessException {
        User testingUser = TestHelper.createTestingUser(null);

        delegate.createUser(testingUser, true);

        User actualUser = delegate.getUser(testingUser.getId());

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerDelegate#createUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws DataAccessException
     *             to JUnit
     */
    public void testCreateUser_NullUser() throws DataAccessException {
        try {
            delegate.createUser(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#createUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUser_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.createUser(TestHelper.createTestingUser(null), true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#addUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);

        delegate.addUsers(new User[] {testingUser}, true);

        User actualUser = delegate.getUsers(new long[] {testingUser.getId()})[0];

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_NullUsers() throws Exception {
        try {
            delegate.addUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users contain null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_NullInUsers() throws Exception {
        try {
            delegate.addUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.addUsers(new User[] {TestHelper.createTestingUser(null)}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUser(User,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#updateUser(User,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        delegate.createUser(testingUser, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        delegate.updateUser(testingUser, true);

        User actualUser = delegate.getUser(testingUser.getId());
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser_NullUser() throws Exception {
        try {
            delegate.updateUser(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is unknown and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser_UnknownUser() throws Exception {
        User user = TestHelper.createTestingUser(null);
        user.setId(1356);
        try {
            delegate.updateUser(user, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser_DataAccessException() throws Exception {
        User user = delegate.getUser(1);
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.updateUser(user, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#updateUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        delegate.addUsers(new User[] {testingUser}, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        delegate.updateUsers(new User[] {testingUser}, true);

        User actualUser = delegate.getUsers(new long[] {testingUser.getId()})[0];
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_NullUsers() throws Exception {
        try {
            delegate.updateUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_NullInUsers() throws Exception {
        try {
            delegate.updateUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It test the case when some user id is unknown and expects BatchOperationException. And the cause of the
     * BatchOperationException should be UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_BatchOperationException() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        testingUser.setId(4000);
        try {
            delegate.updateUsers(new User[] {testingUser}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            Throwable[] causes = e.getCauses();
            assertEquals("Should be only one cause.", 1, causes.length);
            assertEquals("The cause should be UnrecognizedEntityException.",
                UnrecognizedEntityException.class, causes[0].getClass());
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUser(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#removeUser(long,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser() throws Exception {
        delegate.removeUser(1, true);

        User[] users = delegate.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id [1].");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUser(long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when userId <= 0 and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser_NegativeId() throws Exception {
        try {
            delegate.removeUser(-5, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUser(long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when id was not found in the data store and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser_UnrecognizedEntityException() throws Exception {
        try {
            delegate.removeUser(4000, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUser(long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.removeUser(1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUsers([J,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#removeUsers([J,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        delegate.addUsers(new User[] {testingUser}, true);

        delegate.removeUsers(new long[] {1, testingUser.getId()}, true);

        User[] users = delegate.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id [1].");
            }

            if (users[i].getId() == testingUser.getId()) {
                fail("Failed to remove the user with id [" + testingUser.getId() + "].");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_NullUserIds() throws Exception {
        try {
            delegate.removeUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds contains negative element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_NegativeUserIds() throws Exception {
        try {
            delegate.removeUsers(new long[] {1, -6}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It test the case when some user id is unknown and expects BatchOperationException. And the cause of the
     * BatchOperationException should be UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_BatchOperationException() throws Exception {
        try {
            delegate.removeUsers(new long[] {1, 4342424}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            Throwable[] causes = e.getCauses();
            assertEquals("Should be two causes.", 2, causes.length);
            assertNull("The first user should can be removed.", causes[0]);
            assertEquals("The second user should not be removed because of UnrecognizedEntityException.",
                UnrecognizedEntityException.class, causes[1].getClass());
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.removeUsers(new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUser(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#getUser(long) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser() throws Exception {
        User user = delegate.getUser(1);

        assertEquals("The user names are not equals.", "admin", user.getUsername());
        assertEquals("The passwords are not equals.", "tc_super", user.getPassword());
        assertEquals("The statuses are not equals.", 1, user.getStatus().getId());
        assertEquals("The addresses are not equals.", 1, user.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The contacts are not equals.", 1, user.getContact().getId());
        assertEquals("The creation users are not equals.", "", user.getCreationUser());
        assertEquals("The modification users are not equals.", "", user.getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUser(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.getUser(1);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUser(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when id was not found in the data store and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser_UnrecognizedEntityException() throws Exception {
        try {
            delegate.getUser(40000);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUsers(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#getUsers(long[]) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers() throws Exception {
        User user = delegate.getUsers(new long[] {1})[0];

        assertEquals("The user names are not equals.", "admin", user.getUsername());
        assertEquals("The passwords are not equals.", "tc_super", user.getPassword());
        assertEquals("The statuses are not equals.", 1, user.getStatus().getId());
        assertEquals("The addresses are not equals.", 1, user.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The contacts are not equals.", 1, user.getContact().getId());
        assertEquals("The creation users are not equals.", "", user.getCreationUser());
        assertEquals("The modification users are not equals.", "", user.getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_NullUserIds() throws Exception {
        try {
            delegate.getUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.getUsers(new long[] {1});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_NegativeUserId() throws Exception {
        try {
            delegate.getUsers(new long[] {1, -8});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It test the case when some user id is unknown and expects BatchOperationException. And the cause of the
     * BatchOperationException should be UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_BatchOperationException() throws Exception {
        try {
            delegate.getUsers(new long[] {1, 4342424});
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            Throwable[] causes = e.getCauses();
            assertEquals("Should be two causes.", 2, causes.length);
            assertNull("The first user should can be removed.", causes[0]);
            assertEquals("The second user should not be removed because of UnrecognizedEntityException.",
                UnrecognizedEntityException.class, causes[1].getClass());
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#searchUsers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#searchUsers(Filter) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers() throws Exception {
        Filter userNameFilter =
            delegate.getUserFilterFactory().createUsernameFilter(StringMatchType.EXACT_MATCH, "admin");
        User[] users = delegate.searchUsers(userNameFilter);

        assertEquals("Only one user should be in the database.", 1, users.length);

        assertEquals("The user names are not equals.", "admin", users[0].getUsername());
        assertEquals("The passwords are not equals.", "tc_super", users[0].getPassword());
        assertEquals("The statuses are not equals.", 1, users[0].getStatus().getId());
        assertEquals("The addresses are not equals.", 1, users[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, users[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, users[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", users[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", users[0].getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerDelegate#searchUsers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers_NullFilter() throws Exception {
        try {
            delegate.searchUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#searchUsers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers_InvalidFilter() throws Exception {
        try {
            delegate.searchUsers(new EqualToFilter("no_column", "value"));
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addRoleToUser(User,SecurityRole) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#addRoleToUser(User,SecurityRole) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = delegate.getUser(1);

        delegate.addRoleToUser(user, role);

        // verify the result
        SecurityRole[] roles = delegate.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_NullUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);

        try {
            delegate.addRoleToUser(null, role);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role hasn't been persisted and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_NotPersistedRole() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole role = new GeneralSecurityRole("NewRole");

        try {
            delegate.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_NullRole() throws Exception {
        User user = delegate.getUser(1);

        try {
            delegate.addRoleToUser(user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the user hasn't been persisted and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_UnrecognizedEntityException() throws Exception {
        User user = TestHelper.createTestingUser(null);
        SecurityRole role = new GeneralSecurityRole("NewRole");

        try {
            delegate.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_DataAccessException() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);

        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeRoleFromUser(User,SecurityRole) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#removeRoleFromUser(User,SecurityRole) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole[] roles = delegate.retrieveRolesForUser(user);

        for (int i = 0; i < roles.length; i++) {
            delegate.removeRoleFromUser(user, roles[i]);
        }

        assertEquals("Failed to remove the roles for the user.", 0,
            delegate.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NullUser() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole role = delegate.retrieveRolesForUser(user)[0];

        try {
            delegate.removeRoleFromUser(null, role);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NullRole() throws Exception {
        User user = delegate.getUser(1);

        try {
            delegate.removeRoleFromUser(user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role does not belongs to the user and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_UnknownRole() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole newRole = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(newRole);

        try {
            delegate.removeRoleFromUser(user, newRole);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role hasn't been persisted and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NotPersistedRole() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole newRole = new GeneralSecurityRole("NewRole");

        try {
            delegate.removeRoleFromUser(user, newRole);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user hasn't been persisted and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NotPersistedUser() throws Exception {
        SecurityRole newRole = new GeneralSecurityRole("NewRole");

        try {
            delegate.removeRoleFromUser(TestHelper.createTestingUser(null), newRole);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#retrieveRolesForUser(User) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#retrieveRolesForUser(User) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole[] roles = delegate.retrieveRolesForUser(user);

        assertEquals("Failed to retrieve all the security roles for the admin user.", 1, roles.length);
        assertEquals("Failed to retrieve all the security roles for the admin user.", "Administrator",
            roles[0].getName());
    }

    /**
     * <p>
     * Tests UserManagerDelegate#retrieveRolesForUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser_NullUser() throws Exception {
        try {
            delegate.retrieveRolesForUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#retrieveRolesForUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is not persisted and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser_NotPersistedUser() throws Exception {
        try {
            delegate.retrieveRolesForUser(TestHelper.createTestingUser(null));
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#retrieveRolesForUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser_DataAccessException() throws Exception {
        User user = delegate.getUser(1);

        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.retrieveRolesForUser(user);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#clearRolesFromUser(User) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#clearRolesFromUser(User) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser() throws Exception {
        User user = delegate.getUser(1);
        delegate.clearRolesFromUser(user);

        assertEquals("Failed to clear the roles.", 0, delegate.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerDelegate#clearRolesFromUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser_NullUser() throws Exception {
        try {
            delegate.clearRolesFromUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#clearRolesFromUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is not persisted and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser_NotPersistedUser() throws Exception {
        try {
            delegate.clearRolesFromUser(TestHelper.createTestingUser(null));
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#clearRolesFromUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser_DataAccessException() throws Exception {
        User user = delegate.getUser(1);

        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.clearRolesFromUser(user);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#authenticateUser(String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#authenticateUser(String,String) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser() throws Exception {
        assertTrue("Failed to authentication the user.", delegate.authenticateUser("admin", "tc_super"));
        assertFalse("Should fail authentication because of unknown user name.", delegate.authenticateUser(
            "new_comer", "tc_super"));
        assertFalse("Should fail authentication because of unknown user name.", delegate.authenticateUser(
            "admin", "try_it_out"));
    }

    /**
     * <p>
     * Tests UserManagerDelegate#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_NullUsername() throws Exception {
        try {
            delegate.authenticateUser(null, "tc_super");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_EmptyUsername() throws Exception {
        try {
            delegate.authenticateUser("  ", "tc_super");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_NullPassword() throws Exception {
        try {
            delegate.authenticateUser("admin", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_EmptyPassword() throws Exception {
        try {
            delegate.authenticateUser("admin", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.authenticateUser("admin", "tc_super");
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getAllUsers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#getAllUsers() is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers() throws Exception {
        User[] users = delegate.getAllUsers();

        assertEquals("Only one user should be in the database.", 1, users.length);

        assertEquals("The user names are not equals.", "admin", users[0].getUsername());
        assertEquals("The passwords are not equals.", "tc_super", users[0].getPassword());
        assertEquals("The statuses are not equals.", 1, users[0].getStatus().getId());
        assertEquals("The addresses are not equals.", 1, users[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, users[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, users[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", users[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", users[0].getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getAllUsers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            delegate.getAllUsers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUserFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerDelegate#getUserFilterFactory() is correct.
     * </p>
     */
    public void testGetUserFilterFactory() {
        UserFilterFactory factory = delegate.getUserFilterFactory();

        assertEquals("Failed to get the user filter factory.", MappedUserFilterFactory.class, factory
            .getClass());
    }

    /**
     * <p>
     * Tests UserManagerDelegate#getUserFilterFactory() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fails to get user manager from factory and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserFilterFactory_DataAccessException() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        assertNull("Expects null when fails to get user manager implementation.", delegate
            .getUserFilterFactory());
    }
}