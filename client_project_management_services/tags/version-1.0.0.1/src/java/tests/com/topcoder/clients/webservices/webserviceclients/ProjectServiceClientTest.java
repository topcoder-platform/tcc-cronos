/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.URL;

import javax.xml.namespace.QName;

import com.topcoder.clients.webservices.ProjectService;
import com.topcoder.clients.webservices.mock.TestHelper;

import junit.framework.TestCase;

/**
 * Unit Test for {@link ProjectServiceClient}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ProjectServiceClientTest extends TestCase {

    /**
     * Represents IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * Represents PSCCE fail message.
     */
    private static final String T_PSCCE = "ProjectServiceClientCreationException is expected to be thrown.";

    /**
     * Represents ProjectServiceClient instance.
     */
    private ProjectServiceClient client;

    /**
     * Represents WSDL location.
     */
    private String wsdlLoc;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        wsdlLoc = TestHelper.getWsdlLocProjectService();
        client = new ProjectServiceClient(wsdlLoc);
    }

    /**
     * Teardown environment.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        wsdlLoc = null;
        client = null;

        super.tearDown();
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(String)}.
     */
    public void testProjectServiceClientString() {
        assertNotNull(client);
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(String)}.
     *
     * Cause by: wsdl location is null.
     * Expected: {@link IllegalArgumentException}.
     */
    public void testProjectServiceClientString_WithNullWsdlLoc() {
        wsdlLoc = null;

        try {
            new ProjectServiceClient(wsdlLoc);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(String)}.
     *
     * Cause by: wsdl location is empty string.
     * Expected: {@link IllegalArgumentException}.
     */
    public void testProjectServiceClientString_WithEmptyWsdlLoc() {
        wsdlLoc = "\n \t";

        try {
            new ProjectServiceClient(wsdlLoc);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(String)}.
     *
     * Cause by: wsdl location is mallformed.
     * Expected: {@link ProjectServiceClientCreationException}
     */
    public void testProjectServiceClientString_WithMallFormedWsdlLoc() {
        wsdlLoc = "url http:///// invalid";

        try {
            new ProjectServiceClient(wsdlLoc);
            fail(T_PSCCE);
        } catch (ProjectServiceClientCreationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(URL)}.
     *
     * @throws Exception into jUnit.
     */
    public void testProjectServiceClientURL() throws Exception {
        URL url = new URL(wsdlLoc);
        client = new ProjectServiceClient(url);

        assertNotNull(client);
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(URL)}.
     *
     * @throws Exception into jUnit.
     */
    public void testProjectServiceClientURL_WithNullUrl() throws Exception {
        URL url = null;

        try {
            new ProjectServiceClient(url);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(URL, QName)}.
     *
     * @throws Exception into JUnit.
     */
    public void testProjectServiceClientURLQName() throws Exception {
        URL url = new URL(wsdlLoc);
        QName qName = new QName(ProjectService.class.getName(), "ProjectService");
        client = new ProjectServiceClient(url, qName);

        assertNotNull(client);
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(URL, QName)}.
     *
     * @throws Exception into JUnit.
     */
    public void testProjectServiceClientURLQName_WithNullURL() throws Exception {
        URL url = null;
        QName qName = new QName(ProjectService.class.getName(), "ProjectService");

        try {
            new ProjectServiceClient(url, qName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceClient#ProjectServiceClient(URL, QName)}.
     *
     * @throws Exception into JUnit.
     */
    public void testProjectServiceClientURLQName_WithNullQName() throws Exception {
        URL url = new URL(wsdlLoc);
        QName qName = null;

        try {
            new ProjectServiceClient(url, qName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceClient#getProjectServicePort()}.
     */
    public void testGetProjectServicePort() {
        assertNotNull("Fail construct ProjectService instance.", client.getProjectServicePort());
    }
}
