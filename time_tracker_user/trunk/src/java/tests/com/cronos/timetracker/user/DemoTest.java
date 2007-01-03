/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.security.authorization.Action;
import com.topcoder.security.authorization.ActionContext;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralAction;
import com.topcoder.security.authorization.persistence.GeneralActionContext;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.util.config.ConfigManager;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * This test case is a demonstration of the major pieces of functionality of the Time
 * Tracker User component.  The configuration is defined in the setUp method, and the
 * testDemo method runs through the functionality of the UserManager class, as supported
 * by the UserStoreManager, UserStore, UserPersistence, and AuthorizationPersistence classes.
 * </p>
 *
 * <p>
 * The code in this class is also shown in the Component Specification, however, in the
 * component specification it does not have the 'assert' statements, for clarity.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends DbTestCase {

    /**
     * Configure the two required namespaces: UserManager.CONFIG_NAMESPACE for the
     * UserManager and its supporting classes; and the DBConnectionFactoryImpl namespace,
     * required for the IDGenerator 3.0 component.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // set up our namespace; shared between SQLAuthorizationPersistence and Cache
        ConfigManager.getInstance().add(UserManager.CONFIG_NAMESPACE,
                                      "TimeTrackerUserDemoConfig.xml",
                                      ConfigManager.CONFIG_XML_FORMAT);

        // This is the namespace that the IDGenerator needs to use. Note that we load the
        // same file, which is perfectly acceptable.
        // (Resolves to "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
        ConfigManager.getInstance().add(DB_CONNECTION_FACTORY_NAMESPACE,
                                        "TimeTrackerUserDemoConfig.xml",
                                        ConfigManager.CONFIG_XML_FORMAT);
    }


    /**
     * Demonstrates construction of a UserManager class using the default constructor,
     * which uses default implementations of the UserPersistence, AuthorizationPersistence
     * and UserStoreManager classes.  Then, this UserManager instance is demonstrated using
     * the (private) demoUserManager method.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultConstructor() throws Exception {
        // construct a userManager solely from the definitions in the
        // default UserManager namespace
        UserManager userManager = new UserManager();

        // test it.
        demoUserManager(userManager);
    }


    /**
     * Demonstrates construction of a UserManager class using the 3-arg constructor,
     * which uses given objects of the UserPersistence, AuthorizationPersistence
     * and UserStoreManager types.  The UserPersistence and UserStoreManager objects are
     * demonstrated in this method, then, the UserManager instance is demonstrated using
     * the (private) demoUserManager method.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test3ArgConstructor() throws Exception {

        // this will predefine 3 users as if they were already imported.
        insertUsers();

        // construct the userPersistence, which can read/write Users.  These
        // represent the users that are already in the database.
        UserPersistence userPersistence = new UserPersistenceImpl(UserManager.CONFIG_NAMESPACE);
        Collection users = userPersistence.getUsers();
        // there should be 3 initially, which were inserted in the set Up method.
        System.out.println("users = " + users);
        assertEquals("wrong # of users", 3, users.size());

        // now, clean up the database so we don't interfere with the demo
        cleanupDatabase();


        // This is currently the only concrete persistence class for Authorization
        AuthorizationPersistence authPersistence = new SQLAuthorizationPersistence(UserManager.CONFIG_NAMESPACE);

        // This will pre-load any UserStores defined in the configuration file
        UserStoreManager storeManager = new UserStoreManagerImpl(UserManager.CONFIG_NAMESPACE);
        Collection stores = storeManager.getUserStoreNames();
        System.out.println("stores are " + stores);
        assertEquals("wrong # of stores", 1, stores.size());

        // construct the user manager; this is equivalent to the default
        // constructor since we used the same namespace for all three classes.
        UserManager userManager = new UserManager(userPersistence, authPersistence, storeManager);

        // test it.
        demoUserManager(userManager);
    }


    /**
     * Demonstrates the functionality of the UserManager class, including managing the
     * user stores, importing, retrieving and removing users, and manipulating the Authorization
     * roles of those users.
     *
     * @param userManager A UserManager that was configured elsewhere.
     * @throws Exception never under normal conditions.
     */
    private void demoUserManager(UserManager userManager) throws Exception {

        // create some records to import
        insertDefaultUsers();

        // we can look at users directly within the UserStore if we want to
        UserStoreManager userStoreManager = userManager.getUserStores();

        // Define some UserStore, in addition to the one that was loaded from the config file.
        DBConnectionFactory factory = new DBConnectionFactoryImpl(UserManager.CONFIG_NAMESPACE);
        userStoreManager.add("db1", new DbUserStore("InformixConnection", factory));
        userStoreManager.add("db2", new DbUserStore("InformixConnection", factory));

        // get db2 user store and search records
        Collection users = userStoreManager.getUserStore("db2").search("username%");
        System.out.println("all users starting with 'username' are " + users);
        assertEquals("Wrong # of pre-imported users", 4, users.size());

        // import each one
        Iterator usersIter = users.iterator();
        while (usersIter.hasNext()) {
            userManager.importUser(usersIter.next().toString(), "db2");
        }

        // get all imported user names
        Collection usernames = userManager.getNames();
        System.out.println("Imported users = " + usernames);
        assertEquals("Wrong # of imported users", 4, usernames.size());

        // remove a user
        boolean removed = userManager.removeUser("username3");
        System.out.println("removed = " + removed);
        assertTrue("remove didn't return true", removed);

        // should return false, username4 didn't exist
        removed = userManager.removeUser("username4");
        assertFalse("remove didn't return false", removed);

        demoAuthorization(userManager);

        // clean up
        userManager.removeUser("username1");
        userManager.removeUser("username2");
    }


    /**
     * Demonstrate that we can manipulate the Authorization framework either directly through
     * the UserManager, or via the AuthorizationPersistence object directly.
     *
     * @param userManager the UserManager instance to exercise.
     * @throws Exception Never under normal conditions.
     */
    private void demoAuthorization(UserManager userManager) throws Exception {

        // Perform some authorization management:
        // assign SUPER ADMIN role for username1
        userManager.setUserRole("username1", UserManager.SUPER_ADMIN);

        // add new role to the database
        SecurityRole customRole = new GeneralSecurityRole(7, "CustomRole");

        // We have access to the authorization persistence layer for more
        // advanced authorization manipulation functionality.
        AuthorizationPersistence authPersistence = userManager.getAuthPersistence();
        // add a custom role
        authPersistence.addRole(customRole);

        // set the new role for username1
        userManager.setUserRole("username1", customRole);

        // it keeps the most recent role
        SecurityRole role = userManager.getUserRole("username1");
        System.out.println("role is " + role);
        assertEquals("wrong role id", 7, role.getId());

        // get all roles in the database (incl. the predefined ones)
        Collection roles = authPersistence.getSecurityRoles();
        System.out.println("all roles are " + roles);
        assertEquals("wrong # of roles", 7, roles.size());

        // authenticate "username1"
        Response response = userManager.authenticate("username1", "password1");
        System.out.println("Result : " + response.isSuccessful());
        assertTrue("Didn't authenticate", response.isSuccessful());

        // get user instance (which is a Principal in the Authorization framework)
        User user = userManager.getUser("username1");

        // remove the role we defined using the userManager.setUserRole method
        authPersistence.removeRoleForPrincipal(user, role);
        authPersistence.removeRole(customRole);

        // use authorization component against this user (future usage of this component)
        Action action = new GeneralAction(1, "base action");
        ActionContext actionContext = new GeneralActionContext(1, "base action context", null);
        authPersistence.addAction(action);
        authPersistence.addActionContext(actionContext);

        // check permissions
        int result = authPersistence.checkPermissionForPrincipal(user, action, actionContext);
        System.out.println("Permission is " + result);

        // remove the action
        authPersistence.removeAction(action);
        authPersistence.removeActionContext(actionContext);
    }
}