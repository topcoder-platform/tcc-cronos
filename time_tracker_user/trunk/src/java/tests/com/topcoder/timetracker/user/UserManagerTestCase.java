/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * This abstract base class is used to test the UserManager object, for "normal" cases.  For exception
 * ("failure") tests, see UserManagerExceptionsTest.  Subclasses of this test case must provide the
 * "createUserManager" method, which instantiates a UserManager using one of the constructors.
 * </p>
 *
 * <p>
 * This class extends DbTestCase, which will clean up the database after *each test*.
 * </p>
 *
 * @see UserManager
 * @see UserManagerExceptionsTest
 * @see UserManagerDefaultCtorTest
 * @see UserManager3ArgCtorTest
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class UserManagerTestCase extends DbTestCase {

    /** This is a record in the DefaultUsers table, to be imported. */
    protected static final String IMPORT_USERNAME = "username";

    /** This is the password of the above username. */
    private static final String IMPORT_PASSWORD = "password";

    /** The UserManager that we're testing. */
    private UserManager manager;

    /**
     * Configure the required namespaces and instantiate a UserManager to test, using
     * the createUserManager abstract class.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // set up our namespace; shared between SQLAuthorizationPersistence and Cache
        addNamespace(NAMESPACE, "TimeTrackerUserConfig.xml");
        // This is the namespace that the IDGenerator needs to use. Note that we load the
        // same file, which is perfectly acceptable.
        addNamespace(DB_CONNECTION_FACTORY_NAMESPACE, "TimeTrackerUserConfig.xml");

        // Instantiate a new UserManager
        manager = createUserManager();
    }

    /**
     * Instantiate a UserManager for testing.
     * @return a fully-populated UserManager for testing
     * @throws Exception never under normal conditions.
     */
    protected abstract UserManager createUserManager() throws Exception;


    /**
     * Returns the UserManager configured in setUp().
     * @return the UserManager configured in setUp()
     */
    protected UserManager getUserManager() {
        return manager;
    }


    /**
     * Test the <code>authenticate</code> method, when it succeeds.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticate() throws Exception {

        // indicate we imported into the DB
        inserted();

        assertTrue("Import of new user didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // Should authenticate successfully.
        Response response = manager.authenticate(IMPORT_USERNAME, IMPORT_PASSWORD);
        assertEquals("authenticate() with correct password sets wrong status",
                true, response.isSuccessful());
    }


    /**
     * Test the <code>authenticate</code> method, when the database is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateEmpty() throws Exception {
        UserStoreManager storeManager = manager.getUserStores();
        for (Iterator iter = storeManager.getUserStoreNames().iterator(); iter.hasNext();) {
            storeManager.remove((String) iter.next());
        }

        try {
            manager.authenticate(IMPORT_USERNAME, IMPORT_PASSWORD);
            fail("authenticate() on empty database should throw AuthenticateException");
        } catch (AuthenticateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>authenticate</code> method, when it fails (i.e., wrong password.).
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateFail() throws Exception {

        // indicate we imported into the DB
        inserted();

        assertTrue("Import of new user didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // Should not authenticate; case-sensitive
        Response response = manager.authenticate(IMPORT_USERNAME, IMPORT_PASSWORD.toUpperCase());
        assertEquals("authenticate() with wrong password sets wrong status",
                false, response.isSuccessful());

        // Should not authenticate; wrong password
        response = manager.authenticate(IMPORT_USERNAME, IMPORT_USERNAME);
        assertEquals("authenticate() with wrong password sets wrong status",
                false, response.isSuccessful());

        // Should not authenticate; wrong object type
        try {
            manager.authenticate(IMPORT_USERNAME, IMPORT_USERNAME.toCharArray());
            fail("authenticate() with wrong password type should throw AuthenticateException");
        } catch (AuthenticateException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>getAuthPersistence</code> method.  This method is also exercised
     * in the various test subclasses, making sure that the value set in the constructor
     * is returned by it.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetAuthPersistence() throws Exception {

        // all we know about it is that it should be not null
        assertNotNull("AuthPersistence is null", manager.getAuthPersistence());
    }


    /**
     * Test the <code>getNames</code> method on an empty database; should return an empty collection.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetNamesEmpty() throws Exception {

        Collection actualNames = manager.getNames();
        assertEquals("ctor didn't pre-load correct set of names", 0, actualNames.size());
    }


    /**
     * Test that the <code>getNames</code> method returns the records that were imported.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetNames() throws Exception {
        // indicate we imported into the DB
        inserted();

        assertTrue("Import of new user didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // getNames should return the imported user now
        Collection names = manager.getNames();
        assertEquals("getNames returns wrong # of names", 1, names.size());
        assertEquals("getName didn't return imported name",
                IMPORT_USERNAME, names.iterator().next());

        // this collection should be unmodifiable
        try {
            names.add(names);
            fail("should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test the <code>import</code> method to import a user, and show that it can be retrieved by
     * <code>getUser</code>.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportGetUser() throws Exception {

        // indicate we imported into the DB
        inserted();

        assertTrue("Import of new user didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // validate using getUser
        User user = manager.getUser(IMPORT_USERNAME);
        assertFalse("imported user has bad id", user.getId() < 1);
        assertEquals("imported user has wrong name", IMPORT_USERNAME, user.getName());
        assertEquals("imported user has wrong store", STORE_NAME, user.getUserStoreName());
    }


    /**
     * Test the <code>importUser</code> method returns false if you call it a second time with
     * the same username.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testImportUserTwice() throws Exception {

        // indicate we imported into the DB
        inserted();

        // import it once
        assertTrue("import didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // import it twice
        assertFalse("import the second time didn't return false",
                        manager.importUser(IMPORT_USERNAME, "newstore"));

        // make sure it keeps the first one (we can distinguish by the store name)
        User user = manager.getUser(IMPORT_USERNAME);
        assertEquals("imported user has wrong name", IMPORT_USERNAME, user.getName());
        assertEquals("imported user has wrong store", STORE_NAME, user.getUserStoreName());
    }


    /**
     * Test the <code>removeUser</code> method removes the imported username, verified
     * by the getUser and getNames methods.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUser() throws Exception {

        // indicate we imported into the DB
        inserted();

        // import it once
        assertTrue("import didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // sanity check - make sure it's there to start with
        User user = manager.getUser(IMPORT_USERNAME);
        assertNotNull("getUser returned null", user);

        // remove should return true
        assertTrue("remove of existing user didn't return true", manager.removeUser(IMPORT_USERNAME));

        // sanity check - getUser should not return it.
        user = manager.getUser(IMPORT_USERNAME);
        assertNull("After remove, getUser doesn't return null", user);
        // nor should getNames
        assertEquals("After remove, getUsers returned wrong size",
                0, manager.getNames().size());
    }


    /**
     * Test the <code>removeUser</code> method returns false if you try to remove a user
     * that didn't exist in the first place.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testRemoveUserNotExist() throws Exception {

        // sanity check; make sure it wasn't there
        User user = manager.getUser(IMPORT_USERNAME);
        assertNull("getUser returns not null for nonexistent user", user);

        // should return false
        assertFalse("remove of nonexisting user didn't return false", manager.removeUser(IMPORT_USERNAME));
    }


    /**
     * Test the <code>getUserRole</code> method of a user that has no roles defined.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserRoleEmpty() throws Exception {

        // indicate we imported into the DB
        inserted();

        // import it once
        assertTrue("import didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        SecurityRole role = manager.getUserRole(IMPORT_USERNAME);
        assertNull("Role from empty role table is not null", role);
    }


    /**
     * Test the <code>setUserRole</code> method, using <code>getUserRole</code> to verify.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetGetUserRole() throws Exception {

        // indicate we imported into the DB
        inserted();

        // import it once
        assertTrue("import didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // sanity check
        SecurityRole role = manager.getUserRole(IMPORT_USERNAME);
        assertNull("Role is not null", role);

        // add a role
        manager.setUserRole(IMPORT_USERNAME, UserManager.ACCOUNT_MANAGER);
        // get it again
        role = manager.getUserRole(IMPORT_USERNAME);
        assertEquals("getuserRole doesn't return correct role",
                UserManager.ACCOUNT_MANAGER.getName(),
                role.getName());
    }

    /**
     * Test the <code>setUserRole</code> method twice, making sure it returns
     * only the most recent one.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testSetUserRoleTwice() throws Exception {

        // indicate we imported into the DB
        inserted();

        // import it
        assertTrue("import didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // add a role twice
        manager.setUserRole(IMPORT_USERNAME, UserManager.ACCOUNT_MANAGER);
        manager.setUserRole(IMPORT_USERNAME, UserManager.CONTRACTOR);
        // get the role; it should be the most recently added role
        SecurityRole role = manager.getUserRole(IMPORT_USERNAME);
        assertEquals("after setUserRole twice, getUserRole doesn't return correct role",
                UserManager.CONTRACTOR.getName(),
                role.getName());
    }


    /**
     * Test the <code>getUserStores</code> method.  This method is also exercised
     * in the various test subclasses, making sure that the value set in the constructor
     * is returned by it.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testGetUserStores() throws Exception {

        // all we know about it is that it should be not null
        assertNotNull("UserStores is null", manager.getUserStores());
    }

    /**
     * Test the bug of <code>authenticate</code> method. The method should authenticate
     * the user even if the user was not added to the cache.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testAuthenticateUserCaching() throws Exception {
        insertUsers();
        String username = "username1";
        String password = "password1";

        assertNull("The user should not be in the cache", manager.getUser("username1"));

        Response response = manager.authenticate(username, password);
        assertTrue("The user should be authenticated successfully.", response.isSuccessful());

        SecurityRole role = manager.getUserRole(username);
        assertNull("The user role should be obtained", role);

        manager.setUserRole(username, UserManager.ACCOUNT_MANAGER);

        role = manager.getUserRole(username);
        assertNotNull("The user role should be obtained", role);

        cleanupDatabase();
    }
}
