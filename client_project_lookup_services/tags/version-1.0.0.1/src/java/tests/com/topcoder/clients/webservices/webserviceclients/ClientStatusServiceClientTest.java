/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.URL;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import com.topcoder.clients.webservices.ClientStatusService;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

/**
 * <p>
 * Unit test for ClientStatusServiceClient class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientStatusServiceClientTest extends TestCase {

    /**
     * <p>
     * Tests constructor <code>ClientStatusServiceClient(String)</code>.
     * </p>
     * <p>
     * Given URL is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() throws Exception {
        try {
            new ClientStatusServiceClient((String) null);
            fail("IllegalArgumentException expected if URL is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(String)</code>.
     * </p>
     * <p>
     * Given URL is malformed, <code>ClientStatusServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new ClientStatusServiceClient("MalformedURL");
            fail("ClientStatusServiceClientCreationException expected if URL is malformed.");
        } catch (ClientStatusServiceClientCreationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(URL)</code>.
     * </p>
     * <p>
     * Given URL is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3() throws Exception {
        try {
            new ClientStatusServiceClient((URL) null);
            fail("IllegalArgumentException expected if URL is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(URL, QName)</code>.
     * </p>
     * <p>
     * Given URL is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor4() throws Exception {
        try {
            new ClientStatusServiceClient(null, new QName(ClientStatusService.class.getName(),
                    "ClientStatusService"));
            fail("IllegalArgumentException expected if URL is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(URL, QName)</code>.
     * </p>
     * <p>
     * Given QName is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor5() throws Exception {
        try {
            new ClientStatusServiceClient(new URL(getWsdlAddress()), null);
            fail("IllegalArgumentException expected if QName is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(String)</code>.
     * </p>
     * <p>
     * Given URL points to a correct WSDL location, the client should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_Accuracy() throws Exception {

        ClientStatusServiceClient client = new ClientStatusServiceClient(getWsdlAddress());
        assertNotNull("ClientStatusServiceClient should be created", client);
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(URL)</code>.
     * </p>
     * <p>
     * Given URL points to a correct WSDL location, the client should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_Accuracy() throws Exception {

        ClientStatusServiceClient client = new ClientStatusServiceClient(new URL(getWsdlAddress()));
        assertNotNull("ClientStatusServiceClient should be created", client);
    }

    /**
     * <p>
     * Test constructor <code>ClientStatusServiceClient(URL, QName)</code>.
     * </p>
     * <p>
     * Given URL points to a correct WSDL location, given service name is correct, the client should be
     * created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3_Accuracy() throws Exception {

        ClientStatusServiceClient client = new ClientStatusServiceClient(new URL(getWsdlAddress()),
                new QName(ClientStatusService.class.getName(), "ClientStatusService"));
        assertNotNull("ClientStatusServiceClient should be created", client);
    }

    /**
     * <p>
     * Accuracy test for <code>getClientStatusServicePort()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetClientStatusServicePort() throws Exception {

        ClientStatusServiceClient client = new ClientStatusServiceClient(new URL(getWsdlAddress()));
        assertTrue("ClientStatusService should be returned.",
                client.getClientStatusServicePort() instanceof ClientStatusService);
    }

    /**
     * <p>
     * Gets wsdl URL used in tests from the configuration.
     * </p>
     *
     * @return wsdl URL
     * @throws Exception
     *             to JUnit
     */
    private String getWsdlAddress() throws Exception {
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager("config.properties");
        ConfigurationObject rootConfig = configurationFileManager.getConfiguration("WsdlConfig");
        ConfigurationObject config = rootConfig.getChild("WsdlConfig");

        String earAddress = (String) config.getPropertyValue("EARAddress");
        String result = earAddress + "-" + (String) config.getPropertyValue("ClientStatusServiceBean");

        return result;
    }
}
