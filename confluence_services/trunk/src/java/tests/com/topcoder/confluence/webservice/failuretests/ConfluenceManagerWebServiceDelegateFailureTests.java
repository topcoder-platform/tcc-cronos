/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.failuretests;

import junit.framework.TestCase;

import com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegate;
import com.topcoder.util.log.LogManager;

/**
 * Failure tests for class ConfluenceManagerWebServiceDelegate.
 *
 * @author extra
 * @version 1.0
 */
public class ConfluenceManagerWebServiceDelegateFailureTests extends TestCase {

    /**
     * Failure test for method ConfluenceManagerWebServiceDelegate(String, String). If filename is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1NullFilename() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(null, ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagerWebServiceDelegate(String, String). If filename is empty string,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1EmptyFilename() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(" ", ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagerWebServiceDelegate(String, String). If namespace is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1NullNamespace() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagerWebServiceDelegate(String, String). If namespace is empty string,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1EmptyNamespace() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method ConfluenceManagerWebServiceDelegate(ConfigurationObject). If configurationObject is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2NullConfigurationObject() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for method ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient, Log).If
     * serviceClient is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3NullServiceClient() throws Exception {
        try {
            new ConfluenceManagerWebServiceDelegate(null, LogManager.getLog());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}