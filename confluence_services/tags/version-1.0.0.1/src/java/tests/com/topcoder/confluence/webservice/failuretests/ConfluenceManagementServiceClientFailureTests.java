/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.failuretests;

import java.net.URL;

import junit.framework.TestCase;

import com.topcoder.confluence.webservice.client.ConfluenceManagementServiceClient;

/**
 * Failure tests for class ConfluenceManagementServiceClient.
 *
 * @author extra
 * @version 1.0
 */
public class ConfluenceManagementServiceClientFailureTests extends TestCase {

    /**
     * Failure test for method ConfluenceManagementServiceClient(String). If wsdlDocumentLocation is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1NullStringWsdlDocumentLocation() throws Exception {
        try {
            new ConfluenceManagementServiceClient((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagementServiceClient(String). If wsdlDocumentLocation is empty string,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1EmptyStringWsdlDocumentLocation() throws Exception {
        try {
            new ConfluenceManagementServiceClient(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagementServiceClient(URL). If wsdlDocumentLocation is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2NullURLWsdlDocumentLocation() throws Exception {
        try {
            new ConfluenceManagementServiceClient((URL) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagementServiceClient(URL, QName). If wsdlDocumentLocation is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3NullURLWsdlDocumentLocation() throws Exception {
        try {
            new ConfluenceManagementServiceClient((URL) null, ConfluenceManagementServiceClient.DEFAULT_SERVICE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagementServiceClient(URL, QName). If serviceName is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3NullServiceName() throws Exception {
        try {
            new ConfluenceManagementServiceClient(new URL("http://localhost:8080/"), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
