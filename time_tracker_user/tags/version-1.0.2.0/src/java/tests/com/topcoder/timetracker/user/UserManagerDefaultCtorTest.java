/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>
 * Tests the UserManager class operates normally, when the class is instantiated via its default
 * constructor. There are only two actual tests in this class; this class is mostly responsible for
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
public class UserManagerDefaultCtorTest extends UserManagerTestCase {


    /**
     * Instantiate a UserManager to test, using the default constructor. Note this requires
     * the proper ConfigManager namespaces to be defined.
     * @return a UserManager to test, instantiated using the default constructor.
     * @throws Exception never under normal conditions.
     */
    protected UserManager createUserManager() throws Exception {
        // these are the importable users.
        insertDefaultUsers();

        // Instantiate a new UserManager with the default settings.
        return new UserManager();
    }


    /**
     * Tests that the default constructor sets initial values correctly.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testDefaultCtorSetsInitialValues() throws Exception {

        UserManager manager = getUserManager();
        // Make sure the parameters are set in the ctor.
        assertNotNull("AuthPersistence is not defaulted by ctor!", manager.getAuthPersistence());
        assertNotNull("UserStores are not read by ctor!", manager.getUserStores());

        Collection names = manager.getNames();
        assertNotNull("Names is not defaulted by ctor!", names);
        assertEquals("Initial collectoin of names has wrong size", 0, names.size());
    }


    /**
     * Test that the constructor populates the names (verified by the <code>getNames</code>
     * method) *after importing a user*.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testConstructorPopulatesNames() throws Exception {

        UserManager manager = getUserManager();

        // import a user; by returning true we know that it really inserted it.
        assertTrue("import didn't return true", manager.importUser(IMPORT_USERNAME, STORE_NAME));

        // make a new manager; it should import the name
        manager = new UserManager();
        Set actualNames = new HashSet(manager.getNames());
        assertEquals("ctor didn't pre-load correct set of names",
                Collections.singleton(IMPORT_USERNAME), actualNames);
    }
}
