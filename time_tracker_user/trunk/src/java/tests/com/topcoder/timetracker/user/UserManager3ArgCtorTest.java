/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.security.authorization.AuthorizationPersistence;

/**
 * <p>
 * Tests that the UserManager class operates normally, when the class is instantiated via its 3-argument
 * constructor. There is only one actual test in this class; this class is only responsible for
 * creating the object, which is exercised in the superclass. For the rest of the tests to be
 * performed (not including exception tests) on the constructed UserManager, please see
 * UserManagerTestCase. For exception tests please see UserManagerExceptionsTest.
 * </p>
 *
 * @see UserManager
 * @see UserManagerTestCase
 * @see UserManagerExceptionsTest
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserManager3ArgCtorTest extends UserManagerTestCase {


    /**
     * The authPersistence used throughout this test, in both the setUp method, and
     * other methods. Constructed in setUp; usually points to a DummyAuthorizationPersistence object,
     * but can be changed (e.g., to a ThrowingAuthPersistence object.)
     */
    private AuthorizationPersistence authPersistence;

    /**
     * The persistence layer used in this test; instantiated in the setUp method.
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
     * Provides a new instance of a UserManager object. In this test class, the 3-argument
     * constructor is used to instantiate the object.
     * @return a new instance of the UserManager class
     * @throws Exception never under normal conditions.
     */

    protected UserManager createUserManager() throws Exception {

        userPersistence = new DummyUserPersistence();
        storeManager = new DummyUserStoreManager();

        // Simulate a user we can import. Note we leave this as a DummyUserStore,
        // so we can call the add method, which is not in the UserStore interface.
        DummyUserStore userStore = new DummyUserStore();
        userStore.add(IMPORT_USERNAME);
        for (int i = 1; i <= 3; i++) {
            userStore.add(IMPORT_USERNAME + i);
        }
        storeManager.add(STORE_NAME, userStore);

        authPersistence = new DummyAuthorizationPersistence();

        // Instantiate a new UserManager
        return new UserManager(userPersistence, authPersistence, storeManager);
    }


    /**
     * Tests that the 3-arg constructor sets initial values correctly, including pre-loading
     * the existing names (of which there are none yet).
     *
     * @throws Exception Never under normal conditions.
     */
    public void test3ArgCtorSetsInitialValues() throws Exception {
        UserManager manager = getUserManager();

        // Make sure the parameters are set in the ctor.
        assertEquals("AuthPersistence is not set by ctor!", authPersistence,
                        manager.getAuthPersistence());
        assertEquals("UserStores is not set by ctor!", storeManager, manager.getUserStores());

        // create the set of expected and actual names pre-loaded
        Set actualNames = new HashSet(manager.getNames());
        assertEquals("3-arg ctor didn't pre-load correct set of names",
                Collections.EMPTY_SET, actualNames);
    }
}
