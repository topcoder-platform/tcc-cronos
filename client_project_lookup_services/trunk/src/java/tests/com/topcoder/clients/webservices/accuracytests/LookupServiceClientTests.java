/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.LookupService;
import com.topcoder.clients.webservices.webserviceclients.LookupServiceClient;

import junit.framework.TestCase;

import java.util.List;


/**
 * Test the Lookup service.
 * @author waits
 * @version 1.0
 */
public class LookupServiceClientTests extends TestCase {
    /** LookupService instance to test against. */
    private LookupService service = null;

    /**
     * Create instance.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        String wsdl = ClientStatusServiceClientTests.SERVER_ADDRESS + ClientStatusServiceClientTests.EAR_NAME +
            "-client_project_lookup_services/LookupServiceBean?wsdl";
        service = new LookupServiceClient(wsdl).getLookupServicePort();
    }

    /**
     * Test the services methods.
     *
     * @throws Exception into JUnit
     */
    public void testService() throws Exception {
        //client status
        List<ClientStatus> status = service.getAllClientStatuses();
        assertNotNull("The result is not null.", status);

        ClientStatus clientStatus = new ClientStatus();
        clientStatus.setName("created");

        List<Client> clients = service.getClientsForStatus(clientStatus);
        assertNotNull("The result is not null.", clients);

        //by id
        service.getClientStatus(1L);

        Company company = new Company();
        company.setName("topcoder");
        clients = service.getClientsForCompany(company);
        assertNotNull("The result is not null.", clients);

        List<ProjectStatus> projectStatuses = service.getAllProjectStatuses();
        assertNotNull("The result is not null.", projectStatuses);

        List<Project> projects = service.getProjectsForCompany(company);
        assertNotNull("The result is not null.", projects);

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName("closed");
        projects = service.getProjectsForStatus(projectStatus);
        assertNotNull("The result is not null.", projects);

        service.getProjectStatus(1L);
    }
}
