/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.URL;

import javax.xml.namespace.QName;

import com.topcoder.clients.webservices.ClientService;
import com.topcoder.clients.webservices.mock.TestHelper;

import junit.framework.TestCase;

/**
 * Unit test for {@link ClientServiceClient}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ClientServiceClientTest extends TestCase {

    /**
     * Represents IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * Represents CSCCE fail message.
     */
    private static final String T_CSCCE = "ClientServiceClientCreationException is expected to be thrown.";

    /**
     * Represents ClientServiceClient instance.
     */
    private ClientServiceClient client;

    /**
     * Represents wsdl location.
     */
    private String wsdlLoc;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        wsdlLoc = TestHelper.getWsdlLocClientService();
        client = new ClientServiceClient(wsdlLoc);
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        client = null;
        wsdlLoc = null;

        super.tearDown();
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(String)}.
     */
    public void testClientServiceClientString() {
        assertNotNull("Fail create new instance", client);
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(String)}.
     *
     * Cause by: Null wsdl location
     * Expected: IllegalArgumentException
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientString_WithNullParam() throws Exception {
        try {
            wsdlLoc = null;
            new ClientServiceClient(wsdlLoc);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(String)}.
     *
     * Cause by: Empty string wsdl location
     * Expected: IllegalArgumentException
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientString_WithEmptyParam() throws Exception {
        try {
            wsdlLoc = "\n \t";
            new ClientServiceClient(wsdlLoc);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(String)}.
     *
     * Cause by: wsdl location is mallformed
     * Expected: {@link ClientServiceClientCreationException}
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientString_WithInvalidFormatParam() throws Exception {
        try {
            wsdlLoc = "htp:////error.c";
            new ClientServiceClient(wsdlLoc);
            fail(T_CSCCE);
        } catch (ClientServiceClientCreationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(URL)}.
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientURL() throws Exception {
        URL url = new URL(wsdlLoc);
        client = new ClientServiceClient(url);

        assertNotNull("Fail create new instance.", client);
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(URL)}.
     *
     * Cause by: Null url of wsdl.
     * Expected: {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientURL_WithNullParam() throws Exception {
        URL url = null;
        try {
            client = new ClientServiceClient(url);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(URL, QName)}.
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientURLQName() throws Exception {
        URL url = new URL(wsdlLoc);
        QName qName = new QName(ClientService.class.getName(), "ClientService");

        client = new ClientServiceClient(url, qName);
        assertNotNull("Fail create new instance.", client);
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(URL, QName)}.
     *
     * Cause by: Null url of wsdl.
     * Expected: {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientURLQName_WithNullURL() throws Exception {
        URL url = null;
        QName qName = new QName(ClientService.class.getName(), "ClientService");

        try {
            new ClientServiceClient(url, qName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceClient#ClientServiceClient(URL, QName)}.
     *
     * Cause by: Null QName of service name.
     * Expected: {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testClientServiceClientURLQName_WithNullQName() throws Exception {
        URL url = new URL(wsdlLoc);
        QName qName = null;

        try {
            new ClientServiceClient(url, qName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientServiceClient#getClientServicePort()}.
     */
    public void testGetClientServicePort() {
        assertNotNull("Fail construct ClientService instance.", client.getClientServicePort());
    }
}
