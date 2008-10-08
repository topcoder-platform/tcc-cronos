/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;

import junit.framework.TestCase;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.accuracytests.AccuracyTestHelper;
import com.topcoder.clients.webservices.accuracytests.mock.MockPrincipal;
import com.topcoder.clients.webservices.accuracytests.mock.MockSessionContext;
import com.topcoder.clients.webservices.accuracytests.mock.MockUserMappingRetriever;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class contains unit tests for <code>ClientServiceBean</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestClientServiceBean extends TestCase {
    /**
     * <p>
     * Represents the instance of ClientServiceBean used for test.
     * </p>
     */
    private ClientServiceBean bean = null;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new ClientServiceBean();

        AccuracyTestHelper.setField(bean, "clientManagerFile", new File(
            "test_files/accuracy/configuration.properties").getAbsolutePath());
        AccuracyTestHelper.setField(bean, "clientManagerNamespace", "ClientServiceBean");
        AccuracyTestHelper.setField(bean, "userMappingRetrieverFile", new File(
            "test_files/accuracy/configuration.properties").getAbsolutePath());
        AccuracyTestHelper.setField(bean, "userMappingRetrieverNamespace", "UserMappingRetriever");
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);
        AccuracyTestHelper.setField(bean, "clientAndProjectUserRole", Roles.USER);

        // Initialize SessionContext
        MockSessionContext context = new MockSessionContext();
        Principal principal = new MockPrincipal();
        MockSessionContext.setPrincipal(principal);
        AccuracyTestHelper.setField(bean, "sessionContext", context);
        MockSessionContext.setRoles(new String[] { Roles.ADMIN, Roles.USER });

        bean.setVerboseLogging(true);

        bean.initialize();
    }

    /**
     * Clean up the test environment after testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
        MockSessionContext.setRoles(null);
        MockSessionContext.setPrincipal(null);
        MockUserMappingRetriever.setClients(new ArrayList<Client>());
        MockUserMappingRetriever.setProjects(new ArrayList<Project>());
        MockUserMappingRetriever.setFail(false);
    }

    /**
     * Function test : Tests <code>ClientServiceBean()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testClientServiceBeanAccuracy() throws Exception {
        assertNotNull("Should not be null.", bean);
    }

    /**
     * Function test : Tests <code>createClient(Client client)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCreateClientAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        Client client = new Client();
        client.setName("nameaa");
        Client result = bean.createClient(client);

        assertNotNull("Should not be null.", result);
        assertEquals("should be name.", "AAA", result.getName());
    }

    /**
     * Function test : Tests <code>updateClient(Client client)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateClientAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        Client client = new Client();
        client.setName("name2");
        Client result = bean.updateClient(client);

        assertNotNull("Should not be null..", result);
        assertEquals("should be name.", "CCC", result.getName());
    }

    /**
     * Function test : Tests <code>deleteClient(Client client)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testDeleteClientAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        Client client = new Client();
        client.setName("name");
        Client result = bean.deleteClient(client);

        assertNotNull("Should not be null.", result);
        assertEquals("should be name.", "BBB", result.getName());
    }

    /**
     * Function test : Tests <code>setClientCodeName(Client client, String name)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetClientCodeNameAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        Client client = new Client();
        client.setName("name");
        String name = "name2";
        Client result = bean.setClientCodeName(client, name);
        assertNotNull("Should not be null..", result);
        assertEquals("should be name.", "EEE", result.getName());
    }

    /**
     * Function test : Tests <code>setClientStatus(Client client, ClientStatus status)</code>
     * method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetClientStatusAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        Client client = new Client();
        client.setName("name");
        ClientStatus status = new ClientStatus();
        Client result = bean.setClientStatus(client, status);
        assertNotNull("Should not be null..", result);
        assertEquals("should be name.", "DDD", result.getName());
    }

    /**
     * Function test : Tests <code>isVerboseLogging()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testIsVerboseLoggingAccuracy() throws Exception {
        assertTrue("verboseLogging should be true", bean.isVerboseLogging());
    }

    /**
     * Function test : Tests <code>setVerboseLogging(boolean verboseLogging)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetVerboseLoggingAccuracy() throws Exception {
        bean.setVerboseLogging(false);
        assertFalse("verboseLogging should be false", bean.isVerboseLogging());
    }

    /**
     * Function test : Tests <code>getLog()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetLogAccuracy() throws Exception {
        assertNotNull("should not be null.", bean.getLog());
    }

    /**
     * Function test : Tests <code>setLog(Log log)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetLogAccuracy() throws Exception {
        Log log = LogManager.getLog("name");
        bean.setLog(log);
        assertEquals("should equal.", log, bean.getLog());
    }
}