/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.client;

import com.topcoder.jira.webservices.Demo;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.URL;

/**
 * Some tests for JiraManagementServiceClient class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiraManagementServiceClientTest extends TestCase {
    /**
     * Instance to test.
     */
    private JiraManagementServiceClient target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JiraManagementServiceClientTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests first constructor and getJiraManagementServicePort method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testFirstConstructor() throws Exception {
        target = new JiraManagementServiceClient(Demo.WSDL_LOCATION);
        assertEquals("invocation result", "a:b", target.getJiraManagementServicePort().login("a", "b"));
    }

    /**
     * Tests first constructor method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testFirstConstructorForNull() throws Exception {
        try {
            new JiraManagementServiceClient((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests first constructor method for empty string as parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testFirstConstructorForEmpty() throws Exception {
        try {
            new JiraManagementServiceClient(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests first constructor method for malformed url. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testFirstConstructorForBadUrl() throws Exception {
        try {
            new JiraManagementServiceClient("dfgsdfgdsgdd");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests second constructor and getJiraManagementServicePort method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSecondConstructor() throws Exception {
        target = new JiraManagementServiceClient(new URL(Demo.WSDL_LOCATION));
        assertEquals("invocation result", "a:b", target.getJiraManagementServicePort().login("a", "b"));
    }

    /**
     * Tests second constructor method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSecondConstructorForNull() throws Exception {
        try {
            new JiraManagementServiceClient((URL) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests third constructor and getJiraManagementServicePort method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testThirdConstructor() throws Exception {
        target = new JiraManagementServiceClient(new URL(Demo.WSDL_LOCATION),
                JiraManagementServiceClient.DEFAULT_SERVICE_NAME);
        assertEquals("invocation result", "a:b", target.getJiraManagementServicePort().login("a", "b"));
    }

    /**
     * Tests third constructor method for null url parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testThirdConstructorForNullUrl() throws Exception {
        try {
            new JiraManagementServiceClient(null, JiraManagementServiceClient.DEFAULT_SERVICE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests third constructor method for null serviceName parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testThirdConstructorForNullServiceName() throws Exception {
        try {
            new JiraManagementServiceClient(new URL(Demo.WSDL_LOCATION), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}