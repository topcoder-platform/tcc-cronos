/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import java.lang.reflect.Field;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.clients.webservices.beans.LookupServiceBean;
import com.topcoder.search.builder.filter.NullFilter;

import junit.framework.TestCase;

/**
 * Failure tests for {@link LookupServiceBean}.
 *
 * @author iRabbit
 * @version 1.0
 */
public class LookupServiceBeanFailureTests extends TestCase {

    /**
     * {@link LookupServiceBean} used in testing.
     */
    private LookupServiceBean instance = new LookupServiceBean();

    /**
     * Failure test for {@link LookupServiceBean#retrieveClient(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveClient_Failure1() throws Exception {
        try {
            instance.retrieveClient(-1);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveClient(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveClient_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveClient(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveClient(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveClient_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.retrieveClient(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveClient(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveClient_Failure4() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveClient(1);
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllClients()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllClients_Failure1() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveAllClients();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllClients()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllClients_Failure2() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.retrieveAllClients();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllClients()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllClients_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveAllClients();
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClientsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClientsByName_Failure1() throws Exception {
        try {
            instance.searchClientsByName(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClientsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClientsByName_Failure2() throws Exception {
        try {
            instance.searchClientsByName(" ");
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClientsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClientsByName_Failure3() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchClientsByName("test");
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClientsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClientsByName_Failure4() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.searchClientsByName("test");
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClientsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClientsByName_Failure5() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchClientsByName("test");
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClients(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClients_Failure1() throws Exception {
        try {
            instance.searchClients(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClients(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClients_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchClients(new NullFilter("test"));
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClients(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClients_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.searchClients(new NullFilter("test"));
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchClients(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchClients_Failure4() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchClients(new NullFilter("test"));
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientStatus_Failure1() throws Exception {
        try {
            instance.getClientStatus(-1);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientStatus_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getClientStatus(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientStatus_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.getClientStatus(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientStatus_Failure4() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getClientStatus(1);
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getAllClientStatuses()}.
     *
     * @throws Exception to junit
     */
    public void testGetAllClientStatuses_Failure1() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getAllClientStatuses();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getAllClientStatuses()}.
     *
     * @throws Exception to junit
     */
    public void testGetAllClientStatuses_Failure2() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.getAllClientStatuses();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getAllClientStatuses()}.
     *
     * @throws Exception to junit
     */
    public void testGetAllClientStatuses_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getAllClientStatuses();
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForStatus_Failure1() throws Exception {
        try {
            instance.getClientsForStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForStatus_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getClientsForStatus(new ClientStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForStatus_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.getClientsForStatus(new ClientStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForStatus_Failure4() throws Exception {
        setField("clientManager", new DummyClientManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getClientsForStatus(new ClientStatus());
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProject(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProject_Failure1() throws Exception {
        try {
            instance.retrieveProject(-1);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProject(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProject_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveProject(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProject(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProject_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.retrieveProject(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProject(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProject_Failure4() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveProject(1);
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProjectsForClient(Client)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClient_Failure1() throws Exception {
        setField("projectManager", new DummyProjectManager());
        try {
            instance.retrieveProjectsForClient(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProjectsForClient(Client)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClient_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveProjectsForClient(new Client());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProjectsForClient(Client)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClient_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.retrieveProjectsForClient(new Client());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveProjectsForClient(Client)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClient_Failure4() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveProjectsForClient(new Client());
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllProjects()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllProjects_Failure1() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveAllProjects();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllProjects()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllProjects_Failure2() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.retrieveAllProjects();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllProjects()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllProjects_Failure3() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveAllProjects();
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjectsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjectsByName_Failure1() throws Exception {
        try {
            instance.searchProjectsByName(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjectsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjectsByName_Failure2() throws Exception {
        try {
            instance.searchProjectsByName(" ");
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjectsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjectsByName_Failure3() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchProjectsByName("test");
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjectsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjectsByName_Failure4() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.searchProjectsByName("test");
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjectsByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjectsByName_Failure5() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchProjectsByName("test");
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjects(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjects_Failure1() throws Exception {
        try {
            instance.searchProjects(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjects(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjects_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchProjects(new NullFilter("test"));
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjects(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjects_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.searchProjects(new NullFilter("test"));
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchProjects(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchProjects_Failure4() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchProjects(new NullFilter("test"));
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectStatus_Failure1() throws Exception {
        try {
            instance.getProjectStatus(-1);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectStatus_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getProjectStatus(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectStatus_Failure3() throws Exception {
        setField("clientManager", new DummyClientManager());
        try {
            instance.getProjectStatus(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectStatus(long)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectStatus_Failure4() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getProjectStatus(1);
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getAllProjectStatuses()}.
     *
     * @throws Exception to junit
     */
    public void testGetAllProjectStatuses_Failure1() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getAllProjectStatuses();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getAllProjectStatuses()}.
     *
     * @throws Exception to junit
     */
    public void testGetAllProjectStatuses_Failure2() throws Exception {
        setField("projectManager", new DummyProjectManager());
        try {
            instance.getAllProjectStatuses();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getAllProjectStatuses()}.
     *
     * @throws Exception to junit
     */
    public void testGetAllProjectStatuses_Failure3() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getAllProjectStatuses();
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForStatus_Failure1() throws Exception {
        try {
            instance.getProjectsForStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForStatus_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getProjectsForStatus(new ProjectStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForStatus_Failure3() throws Exception {
        setField("projectManager", new DummyProjectManager());
        try {
            instance.getProjectsForStatus(new ProjectStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForStatus(ClientStatus)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForStatus_Failure4() throws Exception {
        setField("projectManager", new DummyProjectManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getProjectsForStatus(new ProjectStatus());
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveCompany(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveCompany_Failure1() throws Exception {
        try {
            instance.retrieveCompany(-1);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveCompany(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveCompany_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveCompany(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveCompany(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveCompany_Failure3() throws Exception {
        setField("projectManager", new DummyProjectManager());
        try {
            instance.retrieveCompany(1);
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveCompany(long)}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveCompany_Failure4() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveCompany(1);
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllCompanies()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllCompanies_Failure1() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveAllCompanies();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllCompanies()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllCompanies_Failure2() throws Exception {
        setField("projectManager", new DummyProjectManager());
        try {
            instance.retrieveAllCompanies();
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#retrieveAllCompanies()}.
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllCompanies_Failure3() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.retrieveAllCompanies();
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompaniesByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompaniesByName_Failure1() throws Exception {
        try {
            instance.searchCompaniesByName(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompaniesByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompaniesByName_Failure2() throws Exception {
        try {
            instance.searchCompaniesByName(" ");
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompaniesByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompaniesByName_Failure3() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchCompaniesByName("test");
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompaniesByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompaniesByName_Failure4() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        try {
            instance.searchCompaniesByName("test");
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompaniesByName(String)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompaniesByName_Failure5() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchCompaniesByName("test");
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompanies(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompanies_Failure1() throws Exception {
        try {
            instance.searchCompanies(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompanies(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompanies_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchCompanies(new NullFilter("test"));
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompanies(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompanies_Failure3() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        try {
            instance.searchCompanies(new NullFilter("test"));
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#searchCompanies(Filter)}.
     *
     * @throws Exception to junit
     */
    public void testSearchCompanies_Failure4() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.searchCompanies(new NullFilter("test"));
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForCompany_Failure1() throws Exception {
        try {
            instance.getClientsForCompany(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForCompany_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getClientsForCompany(new Company());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * /** Failure test for {@link LookupServiceBean#getClientsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForCompany_Failure3() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        try {
            instance.getClientsForCompany(new Company());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getClientsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetClientsForCompany_Failure4() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getClientsForCompany(new Company());
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForCompany_Failure1() throws Exception {
        try {
            instance.getProjectsForCompany(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForCompany_Failure2() throws Exception {
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getProjectsForCompany(new Company());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForCompany_Failure3() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        try {
            instance.getProjectsForCompany(new Company());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link LookupServiceBean#getProjectsForCompany(Company)}.
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForCompany_Failure4() throws Exception {
        setField("companyManager", new DummyCompanyManager());
        setField("userMappingRetriever", new DummyUserMappingRetriever());
        try {
            instance.getProjectsForCompany(new Company());
            fail("AuthorizationFailedException is expected");
        } catch (AuthorizationFailedException e) {
            // success
        }
    }

    /**
     * Sets the value into private field.
     *
     * @param fieldName The name of field.
     * @param fieldValue The field value to be set.
     * @throws Exception to JUnit
     */
    private void setField(String fieldName, Object fieldValue) throws Exception {
        Field field = LookupServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, fieldValue);
    }
}
