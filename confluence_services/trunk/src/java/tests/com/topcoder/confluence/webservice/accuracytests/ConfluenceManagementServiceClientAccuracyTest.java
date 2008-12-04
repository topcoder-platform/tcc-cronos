/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.accuracytests;

import java.net.URL;

import com.topcoder.confluence.webservice.client.ConfluenceManagementServiceClient;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>ConfluenceManagementServiceClient</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceClientAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the wsdl document location.
     * </p>
     */
    private final static String WSDL_DOCUMENT_LOCATION = "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/ConfluenceManagementServiceBean?wsdl";

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagementServiceClient(String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(
                WSDL_DOCUMENT_LOCATION));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagementServiceClient(URL)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(new URL(
                WSDL_DOCUMENT_LOCATION)));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagementServiceClient(URL, QName)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(new URL(
                WSDL_DOCUMENT_LOCATION), ConfluenceManagementServiceClient.DEFAULT_SERVICE_NAME));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>getConfluenceManagementServicePort()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetConfluenceManagementServicePortAccuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagementServiceClient(
                WSDL_DOCUMENT_LOCATION).getConfluenceManagementServicePort());
    }
}