/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ProjectStatusService;
import com.topcoder.clients.webservices.webserviceclients.ProjectStatusServiceClient;

import junit.framework.TestCase;


/**
 * <p>
 * Test the ProjectStatusService web service.
 * </p>
 */
public class ProjectStatusServiceClientTests extends TestCase {
    /** ProjectStatusService instance to test against. */
    private ProjectStatusService service = null;

    /**
     * Create instance.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        String wsdl = ClientStatusServiceClientTests.SERVER_ADDRESS + ClientStatusServiceClientTests.EAR_NAME +
            "-client_project_lookup_services/ProjectStatusServiceBean?wsdl";
        service = new ProjectStatusServiceClient(wsdl).getProjectStatusServicePort();
    }

    /**
     * <p>
     * Test the service method of the project status web service.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testService() throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setDescription("client service.");
        status.setName("client sservice.");
        status.setDeleted(false);
        //create it
        status = service.createProjectStatus(status);
        assertNotNull("The service is not null.", status);
        //update it
        status.setDescription("dec");
        status = service.updateProjectStatus(status);
        assertNotNull("The service is not null.", status);

        //delete it
        status.setDeleted(true);
        status = service.deleteProjectStatus(status);
        assertNotNull("The service is not null.", status);
    }
}
