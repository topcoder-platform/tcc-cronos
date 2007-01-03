/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import java.io.File;

import com.cronos.timetracker.common.RejectEmail;
import com.cronos.timetracker.company.DbCompanyDAO;
import com.cronos.timetracker.user.DbUserAuthenticator;
import com.cronos.timetracker.user.DbUserDAO;
import com.cronos.timetracker.user.User;
import com.cronos.timetracker.user.UserDAO;
import com.cronos.timetracker.user.UserManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authenticationfactory.AbstractAuthenticator;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Failure unit test cases for the UserManager class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class UserManagerFailureTests extends TestCase {
    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String CONFIG_XML = "failuretests" + File.separatorChar + "auth.xml";

    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String DB_CONFIG_XML = "failuretests" + File.separatorChar + "dbconfig.xml";

    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String AUTH_CONFIG_XML = "failuretests" + File.separatorChar + "authorization.xml";

    /**
     * <p>
     * Connection name.
     * </p>
     */
    private static final String CONN_NAME = "informix";

    /**
     * <p>
     * Id generator name.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "ttu2";

    /**
     * <p>
     * Algorithm name.
     * </p>
     */
    private static final String ALGORITHM_NAME = "ttu2";

    /**
     * <p>
     * DBConnection Factory namespace.
     * </p>
     */
    private static final String DB_CONN_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * <p>
     * DBConnectionFactory instance used for testing
     * </p>
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * <p>
     * The UserDAO instance used for testing.
     * </p>
     */
    private UserDAO userDAO = null;

    /**
     * <p>
     * The AbstractAuthenticator instance used for testing.
     * </p>
     */
    private AbstractAuthenticator authenticator = null;

    /**
     * <p>
     * The AuthorizationPersistence instance used for testing.
     * </p>
     */
    private AuthorizationPersistence persistence = null;

    /**
     * <p>
     * The UserManager instance used for testing.
     * </p>
     */
    private UserManager userManager = null;

    /**
     * <p>
     * Creates DbCompanyDAO instance.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        FailureTestsHelper.loadConfig(CONFIG_XML);
        FailureTestsHelper.addConfig(DB_CONFIG_XML);
        FailureTestsHelper.addConfig(AUTH_CONFIG_XML);

        connectionFactory = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);
        userDAO = new DbUserDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, ID_GENERATOR_NAME);
        authenticator = new DbUserAuthenticator("auth");
        persistence = new SQLAuthorizationPersistence("SQLAuthorizationPersistence");
        userManager = new UserManager(userDAO, authenticator, persistence);
    }

    /**
     * <p>
     * Removes the configuration if it exists.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestsHelper.cleanConfig();
    }

    /**
     * <p>
     * Tests UserManager(UserDAO, AbstractAuthenticator, AuthorizationPersistence) for failure. Passes null as
     * first argument, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new UserManager(null, authenticator, persistence);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests UserManager(UserDAO, AbstractAuthenticator, AuthorizationPersistence) for failure. Passes null as
     * second argument, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor2() {
        try {
            new UserManager(userDAO, null, persistence);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests UserManager(UserDAO, AbstractAuthenticator, AuthorizationPersistence) for failure. Passes null as
     * third argument, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor3() {
        try {
            new UserManager(userDAO, authenticator, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests addRoleForUser(User, SecurityRole) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddRoleForUser1() throws Exception {
        try {
            userManager.addRoleForUser(null, new GeneralSecurityRole("aa"));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests addRoleForUser(User, SecurityRole) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddRoleForUser2() throws Exception {
        try {
            userManager.addRoleForUser(new User(), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests addRolesForUsers(User[], SecurityRole[]) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddRolesForUsers1() throws Exception {
        try {
            userManager.addRolesForUsers(null, new SecurityRole[] {new GeneralSecurityRole("aa")});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests addRolesForUsers(User[], SecurityRole[]) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddRolesForUsers2() throws Exception {
        try {
            userManager.addRolesForUsers(new User[] {new User()}, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests addRolesForUsers(User[], SecurityRole[]) for failure. Passes null user in array,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddRolesForUsers3() throws Exception {
        try {
            userManager.addRolesForUsers(new User[] {new User(), null},
                    new SecurityRole[] {new GeneralSecurityRole("aa")});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests authenticateUser(String, String) for failure. Passes empty string as first argument,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAuthenticateUser1() throws Exception {
        try {
            userManager.authenticateUser(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests authenticateUser(String, String) for failure. Passes empty string as second argument,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAuthenticateUser2() throws Exception {
        try {
            userManager.authenticateUser("A", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests clearRolesForUser(User) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testClearRolesForUser1() throws Exception {
        try {
            userManager.clearRolesForUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests clearRolesForUsers(User[]) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @thorws Exception to JUnit.
     */
    public void testClearRolesForUsers1() throws Exception {
        try {
            userManager.clearRolesForUsers(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests clearRolesForUsers(User[]) for failure. Passes null in array, IllegalArgumentException is
     * expected.
     * </p>
     * @thorws Exception to JUnit.
     */
    public void testClearRolesForUsers2() throws Exception {
        try {
            userManager.clearRolesForUsers(new User[] {new User(), null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests convertPrincipalToUser(Principal) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @thorws Exception to JUnit.
     */
    public void testConvertPrincipalToUser1() throws Exception {
        try {
            userManager.convertPrincipalToUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests convertUserToPrincipal(User) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testConvertUserToPrincipal1() {
        try {
            userManager.convertUserToPrincipal(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUser(User, String) for failure. Passes null user, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUser1() throws Exception {
        try {
            userManager.createUser(null, "a");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUsers(User[], String, boolean) for failure. Passes null array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUsers1() throws Exception {
        try {
            userManager.createUsers(null, "a", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUsers(User[], String, boolean) for failure. Passes empty string, IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUsers2() throws Exception {
        try {
            userManager.createUsers(new User[] {new User()}, " ", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUsers(User[], String, boolean) for failure. Passes null in array, IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUsers3() throws Exception {
        try {
            userManager.createUsers(new User[] {new User(), null}, "a", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteUser(User) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteUser1() throws Exception {
        try {
            userManager.deleteUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteUsers(User[], boolean) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteUsers1() throws Exception {
        try {
            userManager.deleteUsers(null, false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteUsers(User[], boolean) for failure. Passes null in array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteUsers2() throws Exception {
        try {
            userManager.deleteUsers(new User[] {new User(), null}, false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests listRolesForUser(User) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testListRolesForUser1() throws Exception {
        try {
            userManager.listRolesForUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests removeRoleForUser(User, SecurityRole) for failure. IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRemoveRoleForUser1() throws Exception {
        try {
            userManager.removeRoleForUser(null, new GeneralSecurityRole("aa"));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests removeRoleForUser(User, SecurityRole) for failure. IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRemoveRoleForUser2() throws Exception {
        try {
            userManager.removeRoleForUser(new User(), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests removeRolesForUsers(User[], SecurityRole[]) for failure. IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRemoveRolesForUsers1() throws Exception {
        try {
            userManager.removeRolesForUsers(null, new SecurityRole[] {new GeneralSecurityRole("aa")});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests removeRolesForUsers(User[], SecurityRole[]) for failure. IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRemoveRolesForUsers2() throws Exception {
        try {
            userManager.removeRolesForUsers(new User[] {new User()}, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests removeRolesForUsers(User[], SecurityRole[]) for failure. IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRemoveRolesForUsers3() throws Exception {
        try {
            userManager.removeRolesForUsers(new User[] {new User(), null},
                    new SecurityRole[] {new GeneralSecurityRole("aa")});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveUser(long) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveUser1() throws Exception {
        try {
            userManager.retrieveUser(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveUsers(long[]) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveUsers1() throws Exception {
        try {
            userManager.retrieveUsers(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests searchUsers(Filter) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSearchUsers1() throws Exception {
        try {
            userManager.searchUsers(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * <p>
     * Tests updateUser(User, String) for failure. Passes null user, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUser1() throws Exception {
        try {
            userManager.updateUser(null, "a");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUser(User, String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUser2() throws Exception {
        try {
            userManager.updateUser(new User(), " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUsers(User[], String, boolean) for failure. Passes null array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUsers1() throws Exception {
        try {
            userManager.updateUsers(null, "a", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUsers(User[], String, boolean) for failure. Passes empty string, IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUsers2() throws Exception {
        try {
            userManager.updateUsers(new User[] {new User()}, " ", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUsers(User[], String, boolean) for failure. Passes null in array, IllegalArgumentException
     * is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUsers3() throws Exception {
        try {
            userManager.updateUsers(new User[] {new User(), null}, "a", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
