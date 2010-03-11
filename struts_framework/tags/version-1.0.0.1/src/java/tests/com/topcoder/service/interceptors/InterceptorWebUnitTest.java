/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.interceptors;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.TestHelper;

/**
 * <p>
 * Tests the interceptor classes in a true GUI environment using JWebUnit.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InterceptorWebUnitTest extends WebTestCase {

    /**
     * The WebTester instance used for running web tests.
     */
    private WebTester tester;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        tester = new WebTester();
        tester.setBaseUrl(TestHelper.getBaseURL());
        tester.beginAt("/login");
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    protected void tearDown() throws Exception {
        tester = null;
    }

    /**
     * Verifies that when trying to access a page but not logged in, we
     * are directed to login page.
     */
    @Test
    public void testNotLoggedIn() {
        // try going to the employees page, but since
        // we haven't logged in we will be forced to the login
        // page
        tester.gotoPage("/employees");

        // make sure we ended up at the login page
        tester.assertTextPresent("Login Name");
    }

    /**
     * Verifies that after logging in successfully we are directed to the
     * expected page.
     */
    @Test
    public void testSuccessfulLogin() {
        tester.gotoPage("/login");

        // make sure we ended up at the login page
        tester.assertTextPresent("Login Name");

        // perform the login
        tester.setTextField("loginName", "topcoder");
        tester.setTextField("password", "password");
        tester.submit();

        // now we should be at the employees page
        tester.assertTextInTable("empTable", "John");
        tester.assertTextInTable("empTable", "Jane");
        tester.assertTextInTable("empTable", "Doe");

        // now that we've been authenticated, we should
        // be able to go directly to the employees
        // page without having to login again
        tester.gotoPage("/employees");
        tester.assertTextInTable("empTable", "John");
        tester.assertTextInTable("empTable", "Jane");
        tester.assertTextInTable("empTable", "Doe");
    }

    /**
     * Verifies that after a failed login we remain on the
     * login page.
     */
    @Test
    public void testFailedLogin() {
        tester.gotoPage("/login");

        // make sure we ended up at the login page
        tester.assertTextPresent("Login Name");

        // perform the login with invalid password
        tester.setTextField("loginName", "topcoder");
        tester.setTextField("password", "fail");
        tester.submit();

        // make sure we stayed at login page
        tester.assertTextPresent("Login Name");
        tester.assertTextPresent("Your login credentials were incorrect, try again.");
    }
}
