/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import java.net.URL;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import com.topcoder.clients.webservices.ProjectStatusService;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

/**
 * <p>
 * Unit test for ProjectStatusServiceClient class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectStatusServiceClientTest extends TestCase {

    /**
     * <p>
     * Tests constructor <code>ProjectStatusServiceClient(String)</code>.
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
            new ProjectStatusServiceClient((String) null);
            fail("IllegalArgumentException expected if URL is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(String)</code>.
     * </p>
     * <p>
     * Given URL is malformed, <code>ProjectStatusServiceClientCreationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new ProjectStatusServiceClient("MalformedURL");
            fail("ProjectStatusServiceClientCreationException expected if URL is malformed.");
        } catch (ProjectStatusServiceClientCreationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(URL)</code>.
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
            new ProjectStatusServiceClient((URL) null);
            fail("IllegalArgumentException expected if URL is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(URL, QName)</code>.
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
            new ProjectStatusServiceClient(null, new QName(ProjectStatusService.class.getName(),
                    "ProjectStatusService"));
            fail("IllegalArgumentException expected if URL is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(URL, QName)</code>.
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
            new ProjectStatusServiceClient(new URL(getWsdlAddress()), null);
            fail("IllegalArgumentException expected if QName is null.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(String)</code>.
     * </p>
     * <p>
     * Given URL points to a correct WSDL location, the client should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_Accuracy() throws Exception {

        ProjectStatusServiceClient client = new ProjectStatusServiceClient(getWsdlAddress());
        assertNotNull("ProjectStatusServiceClient should be created", client);
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(URL)</code>.
     * </p>
     * <p>
     * Given URL points to a correct WSDL location, the client should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_Accuracy() throws Exception {

        ProjectStatusServiceClient client = new ProjectStatusServiceClient(new URL(getWsdlAddress()));
        assertNotNull("ProjectStatusServiceClient should be created", client);
    }

    /**
     * <p>
     * Test constructor <code>ProjectStatusServiceClient(URL, QName)</code>.
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

        ProjectStatusServiceClient client = new ProjectStatusServiceClient(new URL(getWsdlAddress()),
                new QName(ProjectStatusService.class.getName(), "ProjectStatusService"));
        assertNotNull("ProjectStatusServiceClient should be created", client);
    }

    /**
     * <p>
     * Accuracy test for <code>getProjectStatusServicePort()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectStatusServicePort() throws Exception {

        ProjectStatusServiceClient client = new ProjectStatusServiceClient(new URL(getWsdlAddress()));
        assertTrue("ProjectStatusService should be returned.",
                client.getProjectStatusServicePort() instanceof ProjectStatusService);
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
        String result = earAddress + "-" + (String) config.getPropertyValue("ProjectStatusServiceBean");

        return result;
    }

}
