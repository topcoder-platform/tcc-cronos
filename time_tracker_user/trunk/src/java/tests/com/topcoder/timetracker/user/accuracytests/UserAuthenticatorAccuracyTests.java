/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.timetracker.user.UserAuthenticator;

/**
 * <p>
 * Accuracy Unit test cases for UserAuthenticator.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class UserAuthenticatorAccuracyTests extends TestCase {
    /**
     * <p>
     * UserAuthenticator instance for testing.
     * </p>
     */
    private UserAuthenticator instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();

        instance = new UserAuthenticator("com.topcoder.timetracker.user.UserAuthenticator");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserAuthenticatorAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create UserAuthenticator instance.", instance);
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoAuthenticate() throws Exception {
        Principal principal = new Principal("topcoder");
        principal.addMapping("username", "admin");
        principal.addMapping("password", "tc_super");
        Response response = instance.authenticate(principal);

        assertTrue("Failed to do authenticate.", response.isSuccessful());
    }

}