/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.user.UserManager;
import com.topcoder.timetracker.user.UserPersistence;
import com.topcoder.timetracker.user.UserPersistenceImpl;
import com.topcoder.timetracker.user.UserStoreManager;
import com.topcoder.timetracker.user.UserStoreManagerImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy test for class <code>UserManager</code>.
 * @author fuyun
 * @version 1.0
 */
public class UserManagerAccuracyTest extends TestCase {

    /**
     * Represent the <code>UserManager</code> instance for tests.
     */
    private UserManager manager = null;

    /**
     * Represent the <code>UserPersistence</code> instance for tests.
     */
    private UserPersistence userPersistence = null;

    /**
     * Represent the <code>UserStoreManager</code> instance for tests.
     */
    private UserStoreManager storeManager = null;

    /**
     * Represent the <code>AuthorizationPersistence</code> instance for tests.
     */
    private AuthorizationPersistence authPersistence = null;

    /**
     * <p>
     * Represents the namespace used for create <code>UserStore</code> object.
     * </p>
     */
    private final String namespace = "com.topcoder.timetracker.user";

    /**
     * <p>
     * Represents the DB Connection factory's config namespace.
     * </p>
     */
    private final String dbFactoryNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the Logging Wrapper's config namespace.
     * </p>
     */
    private final String logNamespace = "com.topcoder.util.log";

    /**
     * <p>
     * The setUp method used for initializing testing environment.
     * </p>
     * <p>
     * The environment includes the namespace configuration, tested instance creating and database
     * data preparing.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void setUp() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        // remove the namespace if it exists.
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }

        // add the configuration file with namespace
        configManager.add("Accuracy/SampleConfig.xml");
        configManager.add("Accuracy/DBFactory.xml");
        configManager.add(logNamespace, "Accuracy/Logging.xml", ConfigManager.CONFIG_XML_FORMAT);

        // create all the helper objects
        userPersistence = new UserPersistenceImpl(UserManager.CONFIG_NAMESPACE);
        authPersistence = new SQLAuthorizationPersistence(UserManager.CONFIG_NAMESPACE);
        storeManager = new UserStoreManagerImpl(UserManager.CONFIG_NAMESPACE);

        // clean tables
        AccuracyTestHelper.cleanUsersTable();
        AccuracyTestHelper.cleanAuthenticationTables();
        AccuracyTestHelper.cleanDefaultUsersTable();
        // create user manager instance
        AccuracyTestHelper.prepareUsersTable();
        AccuracyTestHelper.prepareAuthenticationTables();
        AccuracyTestHelper.prepareDefaultUsersTable();
        manager = new UserManager(userPersistence, authPersistence, storeManager);

    }

    /**
     * <p>
     * The tearDown method used for cleaning the testing environment.
     * </p>
     * <p>
     * The task includes cleaning the namespace, set tested instance <code>null</code> and clean
     * database data.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void tearDown() throws Exception {

        manager = null;
        userPersistence = null;
        authPersistence = null;
        storeManager = null;
        // clean tables
        AccuracyTestHelper.cleanUsersTable();
        AccuracyTestHelper.cleanAuthenticationTables();
        AccuracyTestHelper.cleanDefaultUsersTable();
        ConfigManager configManager = ConfigManager.getInstance();
        // remove the namespace if it exists.
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
            configManager.removeNamespace(dbFactoryNamespace);
            configManager.removeNamespace(logNamespace);
        }

    }

    /**
     * <p>
     * Test constructor UserManager().
     * </p>
     * <p>
     * Verify that the <code>UserManager</code> instance can be created and all fields are
     * initialized properly.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testUserManager() throws Exception {
        // get the private field using reflection
        Field idGeneratorField = UserManager.class.getDeclaredField("idGenerator");
        Field authPersistenceField = UserManager.class.getDeclaredField("authPersistence");
        Field storeManagerField = UserManager.class.getDeclaredField("storeManager");
        Field userPersistenceField = UserManager.class.getDeclaredField("userPersistence");
        Field usersField = UserManager.class.getDeclaredField("users");
        idGeneratorField.setAccessible(true);
        authPersistenceField.setAccessible(true);
        storeManagerField.setAccessible(true);
        userPersistenceField.setAccessible(true);
        usersField.setAccessible(true);
        // create new instance using the constructor with no arg
        UserManager userManager = new UserManager();

        // asssert all the fields are set properly
        assertNotNull("fail to create UserManager instance.", userManager);
        assertNotNull("the field idGenerator is not properly set", idGeneratorField
                .get(userManager));
        assertNotNull("the field authPersistenceField is not properly set", authPersistenceField
                .get(userManager));
        assertNotNull("the field storeManagerField is not properly set", storeManagerField
                .get(userManager));
        assertNotNull("the field userPersistenceField is not properly set", userPersistenceField
                .get(userManager));
        Map users = (Map) usersField.get(userManager);
        assertEquals("there should be two users now.", 2, users.size());

    }

    /**
     * <p>
     * Test constructor UserManager(UserPersistence userPersistence, AuthorizationPersistence
     * authPersistence, UserStoreManager storeManager).
     * </p>
     * <p>
     * Verify that the <code>UserManager</code> instance can be created and all fields are
     * initialized properly.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testUserManagerUserPersistenceAuthorizationPersistenceUserStoreManager()
        throws Exception {
        assertNotNull("fail to create UserManager instance.", manager);

        // get the private fields
        Field idGeneratorField = UserManager.class.getDeclaredField("idGenerator");
        Field authPersistenceField = UserManager.class.getDeclaredField("authPersistence");
        Field storeManagerField = UserManager.class.getDeclaredField("storeManager");
        Field userPersistenceField = UserManager.class.getDeclaredField("userPersistence");
        Field usersField = UserManager.class.getDeclaredField("users");
        idGeneratorField.setAccessible(true);
        authPersistenceField.setAccessible(true);
        storeManagerField.setAccessible(true);
        userPersistenceField.setAccessible(true);
        usersField.setAccessible(true);

        // verify that all fields are properly set
        assertNotNull("the field idGenerator is not properly set", idGeneratorField.get(manager));
        assertNotNull("the field authPersistenceField is not properly set", authPersistenceField
                .get(manager));
        assertNotNull("the field storeManagerField is not properly set", storeManagerField
                .get(manager));
        assertNotNull("the field userPersistenceField is not properly set", userPersistenceField
                .get(manager));
        Map users = (Map) usersField.get(manager);
        assertEquals("there should be two users now.", 2, users.size());

    }

    /**
     * <p>
     * Test method getAuthPersistence().
     * </p>
     * <p>
     * The authPersistence set in constructor should be retrieved.
     * </p>
     */
    public void testGetAuthPersistence() {
        assertEquals("authPersistence is not retrieved properly.", authPersistence, manager
                .getAuthPersistence());
    }

    /**
     * <p>
     * Test method getUserStores().
     * </p>
     * <p>
     * The userStoreManager set in constructor should be retrieved.
     * </p>
     */
    public void testGetUserStores() {
        assertEquals("userStores is not retrieved properly.", storeManager, manager.getUserStores());
    }

    /**
     * <p>
     * Test method importUser(String name, String userStore).
     * </p>
     * <p>
     * Verify that the users can be imported successfully.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testImportUser1() throws Exception {
        assertTrue("the user should be imported", manager.importUser("user3", "DbUserStore1"));
        assertEquals("there should be 3 names", 3, manager.getNames().size());
    }

    /**
     * <p>
     * Test method importUser(String name, String userStore).
     * </p>
     * <p>
     * the <code>user</code> is already imported, so false should be returned.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testImportUser2() throws Exception {
        assertFalse("User is already imported.", manager.importUser("user1", "DbUserStore1"));
    }

    /**
     * Test method getNames().
     */
    public void testGetNames() {
        Collection names = manager.getNames();
        assertEquals("there should be 2 names", 2, names.size());
    }

    /**
     * Test method getUser(String name).
     */
    public void testGetUser1() {
        assertEquals("fail to get user", "user1", manager.getUser("user1").getName());
    }

    /**
     * Test method getUser(String name).
     */
    public void testGetUser2() {
        assertNull("no user should be returned.", manager.getUser("user3"));
    }

    /**
     * <p>
     * Test method removeUser(String name).
     * </p>
     * <p>
     * the user specified by the name does not exists, so false should be returned.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testRemoveUser1() throws Exception {
        assertFalse("No user should be removed.", manager.removeUser("user3"));
    }

    /**
     * <p>
     * Test method removeUser(String name).
     * </p>
     * <p>
     * Verify that all user specified by the name can be removed successfully.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testRemoveUser2() throws Exception {
        assertTrue("The user should be removed.", manager.removeUser("user1"));
        // verify remvoe from internal map
        assertEquals("Size of internal map should be decreased.", 1, manager.getNames().size());

        // verify remove from userPersistence
        assertEquals("Size of user persistence should be descreased.", 1, userPersistence
                .getUsers().size());

        // verify remove from authPersistence
        assertEquals("Size of authPersistence should be decreased.", 1, authPersistence
                .getPrincipals().size());
    }

    /**
     * <p>
     * Test method getUserRole(String name).
     * </p>
     * <p>
     * Vefify the roles can be retrived successfully.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetUserRole1() throws Exception {
        assertEquals("fail to get the user's role", "Super Administrator", manager.getUserRole(
                "user1").getName());
    }

    /**
     * <p>
     * Test method getUserRole(String name).
     * </p>
     * <p>
     * The user specified by the name does not have role, so <code>null</code> should be returned.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testGetUserRole2() throws Exception {
        assertNull("No role should be retrived", manager.getUserRole("user2"));
    }

    /**
     * <p>
     * Test method setUserRole(String name, SecurityRole role).
     * </p>
     * <p>
     * Verify that the role can be set successfully.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testSetUserRole1() throws Exception {
        manager.setUserRole("user2", UserManager.ACCOUNT_MANAGER);
        assertEquals("fail to set the user's role", UserManager.ACCOUNT_MANAGER.getName(), manager
                .getUserRole("user2").getName());
    }

    /**
     * <p>
     * Test method setUserRole(String name, SecurityRole role).
     * </p>
     * <p>
     * The user has already set a different role. verify that the role can be updated successfully.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testSetUserRole2() throws Exception {
        manager.setUserRole("user1", UserManager.ACCOUNT_MANAGER);
        assertEquals("fail to update the user's role", UserManager.ACCOUNT_MANAGER.getName(),
                manager.getUserRole("user1").getName());
    }

    /**
     * <p>
     * Test method authenticate(String name, Object password).
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testAuthenticate1() throws Exception {
        manager.importUser("user3", "DbUserStore1");
        // the user3 should have the password user3
        assertTrue("fail to authenticate the user", manager.authenticate("user3", "user3")
                .isSuccessful());
    }

    /**
     * <p>
     * Test method authenticate(String name, Object password).
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testAuthenticate2() throws Exception {
        manager.importUser("user3", "DbUserStore1");
        // the user3 should have the password user3, so false should be returned.
        assertFalse("fail to authenticate the user", manager.authenticate("user3", "user")
                .isSuccessful());
    }
}
