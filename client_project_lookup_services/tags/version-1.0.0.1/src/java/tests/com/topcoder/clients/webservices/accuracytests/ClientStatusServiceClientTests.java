/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.ClientStatusService;
import com.topcoder.clients.webservices.webserviceclients.ClientStatusServiceClient;

import junit.framework.TestCase;


/**
 * <p>
 * Test the Client status web service.
 * </p>
 * @author waits
 * @version 1.0
 */
public class ClientStatusServiceClientTests extends TestCase {
    /** The server base address. */
    public static final String SERVER_ADDRESS = "http://127.0.0.1:8080/";

    /**
     * <p>
     * The ear name.
     * </p>
     */
    public static final String EAR_NAME = "client_project_lookup_services";

    /** ClientStatusService instance to test against. */
    private ClientStatusService service = null;

    /**
     * Create instance.
     */
    protected void setUp() throws Exception {
        String wsdl = SERVER_ADDRESS + EAR_NAME + "-client_project_lookup_services/ClientStatusServiceBean?wsdl";
        service = new ClientStatusServiceClient(wsdl).getClientStatusServicePort();
    }

    /**
     * <p>
     * Test the createClientStatus method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateClientStatus() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setDescription("setup");
        status.setName("topcoder");
        status.setDeleted(false);

        //create it
        status = service.createClientStatus(status);
        assertNotNull("The result is invalid.", status);

        //update it
        status.setDescription("update it");
        status = service.updateClientStatus(status);
        assertNotNull("The result is invalid.", status);

        //delete it
        status.setDeleted(true);
        status = service.updateClientStatus(status);
    }
}
