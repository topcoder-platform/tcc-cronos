/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.failuretests;

import com.topcoder.jira.webservices.client.JiraManagementServiceClient;

import junit.framework.TestCase;

import java.net.URL;

/**
 * Failure test for <code>JiraManagementServiceClient</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiraManagementServiceClientFailureTest extends TestCase {
    /**
     * Failure test first constructor method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor1WithNull() throws Exception {
        try {
            new JiraManagementServiceClient((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test first constructor method for empty string as parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor1WithEmpty() throws Exception {
        try {
            new JiraManagementServiceClient(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test first constructor method for malformed url. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor1WithBadUrl() throws Exception {
        try {
            new JiraManagementServiceClient("dfgsdfgdsgdd");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test second constructor method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor2WithNull() throws Exception {
        try {
            new JiraManagementServiceClient((URL) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test third constructor method for null url parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor3WithNullUrl() throws Exception {
        try {
            new JiraManagementServiceClient(null, JiraManagementServiceClient.DEFAULT_SERVICE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test third constructor method for null serviceName parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor3WithNullServiceName() throws Exception {
        try {
            new JiraManagementServiceClient(new URL(TestHelper.WSDL_LOCATION), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}