/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.URL;

import javax.xml.namespace.QName;

import com.topcoder.clients.webservices.CompanyService;
import com.topcoder.clients.webservices.mock.TestHelper;

import junit.framework.TestCase;

/**
 * Unit test for {@link CompanyServiceClient}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class CompanyServiceClientTest extends TestCase {

    /**
     * Represents IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * Represents T_CSCCE fail message.
     */
    private static final String T_CSCCE = "CompanyServiceClientCreationException is expected to be thrown";

    /**
     * Represents CompanyServiceClient instance.
     */
    private CompanyServiceClient client;

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

        wsdlLoc = TestHelper.getWsdlLocCompanyService();
        client = new CompanyServiceClient(wsdlLoc);
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
     * Test for {@link CompanyServiceClient#CompanyServiceClient(String)}.
     */
    public void testCompanyServiceClientString() {
        assertNotNull(client);
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(String)}.
     *
     * Cause by; Null wsdl location.
     * Expected: {@link IllegalArgumentException}.
     */
    public void testCompanyServiceClientString_WithNullParam() {
        wsdlLoc = null;
        try {
            new CompanyServiceClient(wsdlLoc);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(String)}.
     *
     * Cause by: wsdl location is empty string.
     * Expected: {@link IllegalArgumentException}.
     */
    public void testCompanyServiceClientString_WithEmptyParam() {
        wsdlLoc = " \t \n";
        try {
            new CompanyServiceClient(wsdlLoc);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(String)}.
     *
     * Cause by: WSDL location is mallformed.
     * Expected: {@link CompanyServiceClientCreationException}
     */
    public void testCompanyServiceClientString_WithMallFormedParam() {
        wsdlLoc = "x http:\\\\ccc.c";
        try {
            new CompanyServiceClient(wsdlLoc);
            fail(T_CSCCE);
        } catch (CompanyServiceClientCreationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(URL)}.
     *
     * @throws Exception into JUnit.
     */
    public void testCompanyServiceClientURL() throws Exception {
        URL url = new URL(wsdlLoc);
        client = new CompanyServiceClient(url);

        assertNotNull(client);
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(URL)}.
     *
     * Cause by: URL of wsdl is null.
     * Expected: {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCompanyServiceClientURL_WithNullURL() throws Exception {
        URL url = null;
        try {
            new CompanyServiceClient(url);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(URL, QName)}.
     *
     * @throws Exception into JUnit.
     */
    public void testCompanyServiceClientURLQName() throws Exception {
        URL url = new URL(wsdlLoc);
        QName qName = new QName(CompanyService.class.getName(), "CompanyService");
        client = new CompanyServiceClient(url, qName);

        assertNotNull(client);
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(URL, QName)}.
     *
     * Cause by: URL of wsdl is null.
     * Expected: {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCompanyServiceClientURLQName_WithNullURL() throws Exception {
        URL url = null;
        QName qName = new QName(CompanyService.class.getName(), "CompanyService");

        try {
            new CompanyServiceClient(url, qName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceClient#CompanyServiceClient(URL, QName)}.
     *
     * Cause by: URL of wsdl is null.
     * Expected: {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCompanyServiceClientURLQName_WithNullQName() throws Exception {
        URL url = new URL(wsdlLoc);
        QName qName = null;

        try {
            new CompanyServiceClient(url, qName);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link CompanyServiceClient#getCompanyServicePort()}.
     */
    public void testGetCompanyServicePort() {
        assertNotNull("Fail construct CompanyService instance.", client.getCompanyServicePort());
    }
}
