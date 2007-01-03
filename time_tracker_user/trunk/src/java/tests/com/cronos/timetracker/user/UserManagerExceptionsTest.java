/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;

/**
 * <p>
 * Tests that the various methods of the UserManager class throw exceptions when appropriate.
 * </p>
 *
 * @see UserManager
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserManagerExceptionsTest extends ConfigTestCase {


    /** The UserManager that we're testing. */
    private UserManager manager;

    /**
     * The authPersistence used throughout this test, in both the setUp method, and
     * other methods. Constructed in setUp; usually points to a DummyAuthorizationPersistend object,
     * but can be changed (e.g., to a ThrowingAuthPersistence object.)
     */
    private AuthorizationPersistence authPersistence;

    /**
     * A security role that we use throughout this test. It is merely a shortcut to the
     * predefined role in UserManager.
     */
    private SecurityRole securityRole = UserManager.ACCOUNT_MANAGER;

    /**
     * The persistence layer used in tihs test; instantiated in the setUp method.
     * Usually points to a DummyUserPersistence object, but can be changed (e.g., to
     * a ThrowingUserPersistence object.)
     */
    private UserPersistence userPersistence;

    /**
     * The user store manager used throughout this test, in both the setUp method, and
     * other methods. Constructed in setUp; usually points to a DummyUserStoreManager object.
     */
    private UserStoreManager storeManager;

    /**
     * Instantiate a UserManager, and all its supporting objects (UserStore, AuthorizationPersistence,
     * and UserPersistence).  Uses the three-arg constructor of UserManager.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        userPersistence = new DummyUserPersistence();
        storeManager = new DummyUserStoreManager();

        // Simulate an import. Note that we leave this as a DummyUserStore, so we can
        // access the 'add' method, which is not in the UserStore interface definition.
        DummyUserStore userStore = new DummyUserStore();
        userStore.add(USERNAME);
        storeManager.add(STORE_NAME, userStore);

        // instead of the SQL authorization persistence, we use this dummy one.
        authPersistence = new DummyAuthorizationPersistence();

        // Instantiate a new good UserManager
        addNamespace(NAMESPACE, "TimeTrackerUserConfig.xml");
        // This is the namespace that the IDGenerator needs to use. Note that we load the
        // same file, which is perfectly acceptable.
        addNamespace(DB_CONNECTION_FACTORY_NAMESPACE, "TimeTrackerUserConfig.xml");

        manager = new UserManager(userPersistence, authPersistence, storeManager);
    }


    /**
     * Test that the default constructor throws ConfigurationException if there are no
     * properties defined.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorThrowsConfigurationExceptionNoProperties() throws Exception {

        removeNamespace(NAMESPACE);
        // with nothing configured, it should throw an exception.
        try {
            new UserManager();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the default constructor throws ConfigurationException if the configuration
     * has the wrong database driver class name.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorThrowsConfigurationExceptionBadClass() throws Exception {

        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "TimeTrackerUserBadClass.xml");

        try {
            new UserManager();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the default constructor throws ConfigurationException if the configuration
     * has a bad default connection name (this causes a problem with the IDGenerator).
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorThrowsConfigurationExceptionBadDefaultConn() throws Exception {

        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "TimeTrackerUserBadDefaultConnection.xml");

        try {
            new UserManager();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the default constructor throws ConfigurationException if the configuration
     * mentions a UserStore that doesn't exist.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorThrowsConfigurationExceptionBadUserStore() throws Exception {

        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "TimeTrackerUserBadUserStore.xml");
        try {
            new UserManager();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the default constructor throws ConfigurationException if the configuration
     * has a bad "ConnectionName" for the UserPersistenceImpl.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorThrowsConfigurationExceptionBadConnString() throws Exception {

        removeNamespace(NAMESPACE);
        addNamespace(NAMESPACE, "TimeTrackerUserBadConnString.xml");
        try {
            new UserManager();
            fail("default ctor didn't throw ConfigurationException");
        } catch (ConfigurationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 3-arg constructor throws NullPointerException if userPersistence,
     * authPersistence or storeManager is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test3ArgCtorThrowsNPE() throws Exception {

        try {
            new UserManager(null, null, null);
            fail("3-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new UserManager(userPersistence, null, null);
            fail("3-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new UserManager(null, authPersistence, null);
            fail("3-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new UserManager(null, null, storeManager);
            fail("3-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 3-arg constructor throws PersistenceException if any error occurs during
     * initialization.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test3ArgCtorThrowsPersistenceException() throws Exception {

        try {
            // force an exception; the constructor pre-loads the users, so it will throw
            new UserManager(new ThrowingUserPersistence(), authPersistence, storeManager);
            fail("3-arg ctor didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws NullPointerException if name or password is
     * null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsNPE() throws Exception {

        try {
            manager.authenticate(null, null);
            fail("authenticate didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.authenticate(USERNAME, null);
            fail("authenticate didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.authenticate(null, new Object());
            fail("authenticate didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws IllegalArgumentException if name is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.authenticate("", new Object());
            fail("authenticate didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws AuthenticateException if any error occurs during
     * authentication.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsAuthenticateException() throws Exception {

        try {
            // Force an AuthenticateException; the user was never imported!
            manager.authenticate(USERNAME, new Object());
            fail("authenticate didn't throw AuthenticateException");
        } catch (AuthenticateException expected) {
            assertNotNull(expected);
        }

        assertTrue("import didn't return true", manager.importUser(USERNAME, STORE_NAME));
        // use a throwing store.
        storeManager.remove(STORE_NAME);
        storeManager.add(STORE_NAME, new ThrowingUserStore());
        try {
            // Force an AuthenticateException; The store always throws.
            manager.authenticate(USERNAME, new Object());
            fail("authenticate didn't throw AuthenticateException");
        } catch (AuthenticateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>authenticate</code> throws UnknownUserStoreException if the given user contains
     * a non-existent user store name according to the UserStoreManager contained by the UserManager
     * under test.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateThrowsUnknownUserStoreException() throws Exception {

        // this store doesn't exist any more.
        manager.importUser(USERNAME, STORE_NAME);
        storeManager.remove(STORE_NAME);
        try {
            // Force an UnknownUserStoreException
            manager.authenticate(USERNAME, new Object());
            fail("authenticate didn't throw UnknownUserStoreException");
        } catch (UnknownUserStoreException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>importUser</code> throws PersistenceException if any error occurs during
     * user persistence.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportUserThrowsPersistenceException() throws Exception {

        // replace the userStore with one that always throws an exception.
        storeManager.remove(STORE_NAME);
        storeManager.add(STORE_NAME, new ThrowingUserStore());

        try {
            // Force a PersistenceException
            manager.importUser(USERNAME, STORE_NAME);
            fail("importUser didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>importUser</code> throws UnknownUserStoreException if userstore is not
     * present wthin userStoreManager.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportUserThrowsUnknownUserStoreException() throws Exception {

        try {
            // This store couldnt' possibly exist.
            manager.importUser(USERNAME, STORE_NAME + System.currentTimeMillis());
            fail("importUser didn't throw UnknownUserStoreException");
        } catch (UnknownUserStoreException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>importUser</code> throws UnknownUserException if given user is not present
     * at the userstore.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportUserThrowsUnknownUserException() throws Exception {

        try {
            // This store couldnt' possibly exist.
            manager.importUser(USERNAME + System.currentTimeMillis(), STORE_NAME);
            fail("importUser didn't throw UnknownUserException");
        } catch (UnknownUserException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>importUser</code> throws NullPointerException if name or userStore is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportUserThrowsNPE() throws Exception {

        try {
            manager.importUser(null, null);
            fail("importUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.importUser(USERNAME, null);
            fail("importUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.importUser(null, STORE_NAME);
            fail("importUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>importUser</code> throws IllegalArgumentException if name or userStore is
     * empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportUserThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.importUser("", "");
            fail("importUser didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }

        try {
            // Force an IllegalArgumentException
            manager.importUser(USERNAME, "");
            fail("importUser didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }

        try {
            // Force an IllegalArgumentException
            manager.importUser("", STORE_NAME);
            fail("importUser didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUser</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserThrowsNPE() throws Exception {

        try {
            manager.getUser(null);
            fail("getUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUser</code> throws IllegalArgumentException if name is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.getUser("");
            fail("getUser didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>removeUser</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserThrowsNPE() throws Exception {

        try {
            manager.removeUser(null);
            fail("removeUser didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>removeUser</code> throws IllegalArgumentException if name is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.removeUser("");
            fail("removeUser didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>removeUser</code> throws PersistenceException if any error occurs during
     * removing a user.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserThrowsPersistenceException() throws Exception {

        // replace the userStore with one that always throws an exception.
        ThrowingUserPersistence throwingUserPersistence = new ThrowingUserPersistence();
        // only throw on a remove
        throwingUserPersistence.setThrowMask(2);
        manager = new UserManager(throwingUserPersistence, authPersistence, storeManager);
        manager.importUser(USERNAME, STORE_NAME);
        try {
            // Force a PersistenceException
            manager.removeUser(USERNAME);
            fail("removeUser didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUserRole</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserRoleThrowsNPE() throws Exception {

        try {
            manager.getUserRole(null);
            fail("getUserRole didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUserRole</code> throws IllegalArgumentException if name is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserRoleThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.getUserRole("");
            fail("getUserRole didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUserRole</code> throws PersistenceException if any error occurs during
     * gettting the role.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserRoleThrowsPersistenceException() throws Exception {

        authPersistence = new ThrowingAuthPersistence();
        manager = new UserManager(userPersistence, authPersistence, storeManager);

        assertTrue("Import didn't return true", manager.importUser(USERNAME, STORE_NAME));
        try {
            // Force a PersistenceException
            manager.getUserRole(USERNAME);
            fail("getUserRole didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>getUserRole</code> throws UnknownUserException if such user doesn't exist
     * in the user list.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserRoleThrowsUnknownUserException() throws Exception {

        try {
            // This user could not possibly exist
            manager.getUserRole(USERNAME + System.currentTimeMillis());
            fail("getUserRole didn't throw UnknownUserException");
        } catch (UnknownUserException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>setUserRole</code> throws NullPointerException if name is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetUserRoleThrowsNPE() throws Exception {

        try {
            manager.setUserRole(null, securityRole);
            fail("setUserRole didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.setUserRole(USERNAME, null);
            fail("setUserRole didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            manager.setUserRole(null, null);
            fail("setUserRole didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>setUserRole</code> throws IllegalArgumentException if name is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetUserRoleThrowsIAE() throws Exception {

        try {
            // Force an IllegalArgumentException
            manager.setUserRole("", securityRole);
            fail("setUserRole didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>setUserRole</code> throws PersistenceException if any error occurs during
     * gettting the role.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetUserRoleThrowsPersistenceException() throws Exception {

        authPersistence = new ThrowingAuthPersistence();
        manager = new UserManager(userPersistence, authPersistence, storeManager);

        // have to import it first, or it won't be settable
        assertTrue("Import didn't return true", manager.importUser(USERNAME, STORE_NAME));
        try {
            // Force a PersistenceException
            manager.setUserRole(USERNAME, securityRole);
            fail("setUserRole didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that <code>setUserRole</code> throws UnknownUserException if the given user was
     * not imported first.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetUserRoleThrowsUnknownUserException() throws Exception {

        try {
            // This user could not have been added
            manager.setUserRole(USERNAME + System.currentTimeMillis(), securityRole);
            fail("setUserRole didn't throw UnknownUserException");
        } catch (UnknownUserException expected) {
            assertNotNull(expected);
        }
    }
}
