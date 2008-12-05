/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceManagementServiceClient</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceClientTests extends TestCase {

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor1_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(
            "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/"
                + "ConfluenceManagementServiceBean?wsdl"));
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(String).argument
     * is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor1_Failure1() throws Exception {
        try {
            new ConfluenceManagementServiceClient((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(String).argument
     * is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor1_Failure2() throws Exception {
        try {
            new ConfluenceManagementServiceClient(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(String).argument
     * is malformed URL,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor1_Failure3() throws Exception {
        try {
            new ConfluenceManagementServiceClient("sfs/");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertTrue("should be MalformedURLException.", e.getCause() instanceof MalformedURLException);
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for
     * {@link ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(java.net.URL)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(new URL(
            "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/"
                + "ConfluenceManagementServiceBean?wsdl")));
    }

    /**
     * <p>
     * Failure test case for {@link ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(URL)}.argument
     * is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor2_Failure1() throws Exception {
        try {
            new ConfluenceManagementServiceClient((URL) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for
     * {@link ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(URL, QName)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor3_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(new URL(
            "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/"
                + "ConfluenceManagementServiceBean?wsdl"), ConfluenceManagementServiceClient.DEFAULT_SERVICE_NAME));
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(URL, QName)}.wsdlDocumentLocation
     * is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor3_Failure1() throws Exception {
        try {
            new ConfluenceManagementServiceClient(null, ConfluenceManagementServiceClient.DEFAULT_SERVICE_NAME);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluenceManagementServiceClient#ConfluenceManagementServiceClient(URL, QName)}.Qname is null,so
     * throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_ctor3_Failure2() throws Exception {
        try {
            new ConfluenceManagementServiceClient(new URL(
                "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/"
                    + "ConfluenceManagementServiceBean?wsdl"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceManagementServiceClient#getConfluenceManagementServicePort()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getConfluenceManagementServicePort_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(
            "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/"
                + "ConfluenceManagementServiceBean?wsdl").getConfluenceManagementServicePort());
    }
}