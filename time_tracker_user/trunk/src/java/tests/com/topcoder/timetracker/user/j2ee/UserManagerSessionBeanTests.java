/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.EjbBeanAccess;
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
 * Unit test cases for UserManagerSessionBean.
 * </p>
 *
 * <p>
 * In this test case, MockEJB is used to deploy the UserManagerSessionBean and UserManagerDelegate is used to visit
 * the UserManagerSessionBean.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserManagerSessionBeanTests extends TestCase {

    /**
     * <p>
     * The UserManagerSessionBean instance for testing.
     * </p>
     */
    private UserManagerSessionBean bean;

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

        bean = new UserManagerSessionBean();

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor =
            new SessionBeanDescriptor("UserManagerLocalHome", UserManagerLocalHome.class,
                UserManagerLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        UserManagerLocalHome home = (UserManagerLocalHome) context.lookup("UserManagerLocalHome");

        UserManagerLocal service = home.create();

        bean = (UserManagerSessionBean) ((EjbBeanAccess) service).getBean();

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
        bean = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserManagerSessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor UserManagerSessionBean#UserManagerSessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created UserManagerDelegate instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new UserManagerSessionBean instance.", bean);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#createUser(User,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#createUser(User,boolean) is correct.
     * </p>
     *
     * @throws DataAccessException
     *             to JUnit
     */
    public void testCreateUser() throws DataAccessException {
        User testingUser = TestHelper.createTestingUser(null);

        bean.createUser(testingUser, true);

        User actualUser = bean.getUser(testingUser.getId());

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#createUser(User,boolean) for failure.
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
            bean.createUser(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#createUser(User,boolean) for failure.
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
            bean.createUser(TestHelper.createTestingUser(null), true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#addUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);

        bean.addUsers(new User[] {testingUser}, true);

        User actualUser = bean.getUsers(new long[] {testingUser.getId()})[0];

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addUsers(User[],boolean) for failure.
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
            bean.addUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addUsers(User[],boolean) for failure.
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
            bean.addUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addUsers(User[],boolean) for failure.
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
            bean.addUsers(new User[] {TestHelper.createTestingUser(null)}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUser(User,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#updateUser(User,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        bean.createUser(testingUser, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        bean.updateUser(testingUser, true);

        User actualUser = bean.getUser(testingUser.getId());
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUser(User,boolean) for failure.
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
            bean.updateUser(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUser(User,boolean) for failure.
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
            bean.updateUser(user, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUser(User,boolean) for failure.
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
        User user = bean.getUser(1);
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            bean.updateUser(user, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#updateUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        bean.addUsers(new User[] {testingUser}, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        bean.updateUsers(new User[] {testingUser}, true);

        User actualUser = bean.getUsers(new long[] {testingUser.getId()})[0];
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUsers(User[],boolean) for failure.
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
            bean.updateUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUsers(User[],boolean) for failure.
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
            bean.updateUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUsers(User[],boolean) for failure.
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
            bean.updateUsers(new User[] {testingUser}, true);
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
     * Tests UserManagerSessionBean#removeUser(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#removeUser(long,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser() throws Exception {
        bean.removeUser(1, true);

        User[] users = bean.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id [1].");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUser(long,boolean) for failure.
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
            bean.removeUser(-5, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUser(long,boolean) for failure.
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
            bean.removeUser(4000, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUser(long,boolean) for failure.
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
            bean.removeUser(1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUsers([J,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#removeUsers([J,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        bean.addUsers(new User[] {testingUser}, true);

        bean.removeUsers(new long[] {1, testingUser.getId()}, true);

        User[] users = bean.getAllUsers();
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
     * Tests UserManagerSessionBean#removeUsers(long[],boolean) for failure.
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
            bean.removeUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUsers(long[],boolean) for failure.
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
            bean.removeUsers(new long[] {1, -6}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUsers(long[],boolean) for failure.
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
            bean.removeUsers(new long[] {1, 4342424}, true);
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
     * Tests UserManagerSessionBean#removeUsers(long[],boolean) for failure.
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
            bean.removeUsers(new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUser(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#getUser(long) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser() throws Exception {
        User user = bean.getUser(1);

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
     * Tests UserManagerSessionBean#getUser(long) for failure.
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
            bean.getUser(1);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUser(long) for failure.
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
            bean.getUser(40000);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUsers(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#getUsers(long[]) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers() throws Exception {
        User user = bean.getUsers(new long[] {1})[0];

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
     * Tests UserManagerSessionBean#getUsers(long[]) for failure.
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
            bean.getUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUsers(long[]) for failure.
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
            bean.getUsers(new long[] {1});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUsers(long[]) for failure.
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
            bean.getUsers(new long[] {1, -8});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUsers(long[]) for failure.
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
            bean.getUsers(new long[] {1, 4342424});
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
     * Tests UserManagerSessionBean#searchUsers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#searchUsers(Filter) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers() throws Exception {
        Filter userNameFilter =
            bean.getUserFilterFactory().createUsernameFilter(StringMatchType.EXACT_MATCH, "admin");
        User[] users = bean.searchUsers(userNameFilter);

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
     * Tests UserManagerSessionBean#searchUsers(Filter) for failure.
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
            bean.searchUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#searchUsers(Filter) for failure.
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
            bean.searchUsers(new EqualToFilter("no_column", "value"));
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#addRoleToUser(User,SecurityRole) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = bean.getUser(1);

        bean.addRoleToUser(user, role);

        // verify the result
        SecurityRole[] roles = bean.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for failure.
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
            bean.addRoleToUser(null, role);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);
        SecurityRole role = new GeneralSecurityRole("NewRole");

        try {
            bean.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);

        try {
            bean.addRoleToUser(user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for failure.
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
            bean.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);

        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            bean.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser() throws Exception {
        User user = bean.getUser(1);
        SecurityRole[] roles = bean.retrieveRolesForUser(user);

        for (int i = 0; i < roles.length; i++) {
            bean.removeRoleFromUser(user, roles[i]);
        }

        assertEquals("Failed to remove the roles for the user.", 0, bean.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);
        SecurityRole role = bean.retrieveRolesForUser(user)[0];

        try {
            bean.removeRoleFromUser(null, role);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);

        try {
            bean.removeRoleFromUser(user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);
        SecurityRole newRole = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(newRole);

        try {
            bean.removeRoleFromUser(user, newRole);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for failure.
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
        User user = bean.getUser(1);
        SecurityRole newRole = new GeneralSecurityRole("NewRole");

        try {
            bean.removeRoleFromUser(user, newRole);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for failure.
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
            bean.removeRoleFromUser(TestHelper.createTestingUser(null), newRole);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#retrieveRolesForUser(User) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#retrieveRolesForUser(User) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser() throws Exception {
        User user = bean.getUser(1);
        SecurityRole[] roles = bean.retrieveRolesForUser(user);

        assertEquals("Failed to retrieve all the security roles for the admin user.", 1, roles.length);
        assertEquals("Failed to retrieve all the security roles for the admin user.", "Administrator",
            roles[0].getName());
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#retrieveRolesForUser(User) for failure.
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
            bean.retrieveRolesForUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#retrieveRolesForUser(User) for failure.
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
            bean.retrieveRolesForUser(TestHelper.createTestingUser(null));
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#retrieveRolesForUser(User) for failure.
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
        User user = bean.getUser(1);

        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            bean.retrieveRolesForUser(user);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#clearRolesFromUser(User) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#clearRolesFromUser(User) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser() throws Exception {
        User user = bean.getUser(1);
        bean.clearRolesFromUser(user);

        assertEquals("Failed to clear the roles.", 0, bean.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#clearRolesFromUser(User) for failure.
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
            bean.clearRolesFromUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#clearRolesFromUser(User) for failure.
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
            bean.clearRolesFromUser(TestHelper.createTestingUser(null));
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#clearRolesFromUser(User) for failure.
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
        User user = bean.getUser(1);

        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");

        try {
            bean.clearRolesFromUser(user);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#authenticateUser(String,String) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser() throws Exception {
        assertTrue("Failed to authentication the user.", bean.authenticateUser("admin", "tc_super"));
        assertFalse("Should fail authentication because of unknown user name.", bean.authenticateUser(
            "new_comer", "tc_super"));
        assertFalse("Should fail authentication because of unknown user name.", bean.authenticateUser(
            "admin", "try_it_out"));
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for failure.
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
            bean.authenticateUser(null, "tc_super");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for failure.
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
            bean.authenticateUser("  ", "tc_super");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for failure.
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
            bean.authenticateUser("admin", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for failure.
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
            bean.authenticateUser("admin", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for failure.
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
            bean.authenticateUser("admin", "tc_super");
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getAllUsers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#getAllUsers() is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers() throws Exception {
        User[] users = bean.getAllUsers();

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
     * Tests UserManagerSessionBean#getAllUsers() for failure.
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
            bean.getAllUsers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUserFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#getUserFilterFactory() is correct.
     * </p>
     */
    public void testGetUserFilterFactory() {
        UserFilterFactory factory = bean.getUserFilterFactory();

        assertEquals("Failed to get the user filter factory.", MappedUserFilterFactory.class, factory
            .getClass());
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUserFilterFactory() for failure.
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

        assertNull("Expects null when fails to get user manager implementation.", bean.getUserFilterFactory());
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#ejbCreate() is correct.
     * </p>
     */
    public void testEjbCreate() {
        bean.ejbCreate();

        // no assertion here because it is empty
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();

        // no assertion here because it is empty
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();

        // no assertion here because it is empty
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();

        // no assertion here because it is empty
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);

        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerSessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        assertNotNull("Failed to get the session context.", bean.getSessionContext());
    }
}