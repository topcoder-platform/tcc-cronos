/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;
import com.topcoder.timetracker.user.*;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure test cases for UserManager.
 *
 * @author WishingBone
 * @version 1.0
 */
public class UserManagerFailureTests extends TestCase {

    /**
     * Load config file.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failuretests/ConnectionFactory.xml");
    }

    /**
     * Unload config file.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
    }

    /**
     * Create with invalid config.
     */
    public void testCreate_InvalidConfig() {
        try {
            new UserManager();
            fail("Should have thrown ConfigurationException.");
        } catch (ConfigurationException ce) {
        }
    }

    /**
     * Create with null user persistence.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate_NullUserPersistence() throws Exception {
        try {
            new UserManager(
                    null,
                    TestHelper.getAuthorizationPersistence(),
                    TestHelper.getUserStoreManager());
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with null authorization persistence.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate_NullAuthorizationPersistence() throws Exception {
        try {
            new UserManager(
                    TestHelper.getUserPersistence(),
                    null,
                    TestHelper.getUserStoreManager());
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with null user store manager.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate_NullUserStoreManager() throws Exception {
        try {
            new UserManager(
                    TestHelper.getUserPersistence(),
                    TestHelper.getAuthorizationPersistence(),
                    null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * importUser() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testImportUser_NullName() throws Exception {
        try {
            TestHelper.getUserManager().importUser(null, "store");
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * importUser() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testImportUser_EmptyName() throws Exception {
        try {
            TestHelper.getUserManager().importUser("", "store");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * importUser() with null store name.
     *
     * @throws Exception to JUnit.
     */
    public void testImportUser_NullStoreName() throws Exception {
        try {
            TestHelper.getUserManager().importUser("name", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * importUser() with empty store name.
     *
     * @throws Exception to JUnit.
     */
    public void testImportUser_EmptyStoreName() throws Exception {
        try {
            TestHelper.getUserManager().importUser("name", "");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * importUser() with invalid store name.
     *
     * @throws Exception to JUnit.
     */
    public void testImportUser_InvalidStoreName() throws Exception {
        try {
            TestHelper.getUserManager().importUser("name", "invalid");
            fail("Should have thrown UnknownUserStoreException.");
        } catch (UnknownUserStoreException uuse) {
        }
    }

    /**
     * getUser() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUser_NullName() throws Exception {
        try {
            TestHelper.getUserManager().getUser(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * getUser() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUser() throws Exception {
        try {
            TestHelper.getUserManager().getUser("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * removeUser() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveUser_NullName() throws Exception {
        try {
            TestHelper.getUserManager().removeUser(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * removeUser() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveUser_EmptyName() throws Exception {
        try {
            TestHelper.getUserManager().removeUser("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * getUserRole() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_NullName() throws Exception {
        try {
            TestHelper.getUserManager().getUserRole(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * getUserRole() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_EmptyName() throws Exception {
        try {
            TestHelper.getUserManager().getUserRole("");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * getUserRole() with invalid name.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole_InvalidName() throws Exception {
        try {
            TestHelper.getUserManager().getUserRole("invalid");
            fail("Should have thrown UnknownUserException.");
        } catch (UnknownUserException uue) {
        }
    }

    /**
     * setUserRole() with null name.
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_NullName() throws Exception {
        try {
            TestHelper.getUserManager().setUserRole(null, UserManager.SUPER_ADMIN);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * setUserRole() with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_EmptyName() throws Exception {
        try {
            TestHelper.getUserManager().setUserRole("", UserManager.SUPER_ADMIN);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setUserRole() with invalid name.
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_InvalidName() throws Exception {
        try {
            TestHelper.getUserManager().setUserRole("invalid", UserManager.SUPER_ADMIN);
            fail("Should have thrown UnknownUserException.");
        } catch (UnknownUserException uue) {
        }
    }

    /**
     * setUserRole() with null role.
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole_NullRole() throws Exception {
        try {
            TestHelper.getUserManager().setUserRole("name", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * authenticate() with null username.
     *
     * @throws Exception to JUnit.
     */
    public void testAuthenticate_NullUsername() throws Exception {
        try {
            TestHelper.getUserManager().authenticate(null, "password");
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * authenticate() with null password.
     *
     * @throws Exception to JUnit.
     */
    public void testAuthenticate_NullPassword() throws Exception {
        try {
            TestHelper.getUserManager().authenticate("username", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * authenticate() with empty username.
     *
     * @throws Exception to JUnit.
     */
    public void testAuthenticate_EmptyUsername() throws Exception {
        try {
            TestHelper.getUserManager().authenticate("", "password");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

}
