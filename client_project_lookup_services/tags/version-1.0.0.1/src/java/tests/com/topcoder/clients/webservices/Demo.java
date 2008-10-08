/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.ClientStatusServiceBean;
import com.topcoder.clients.webservices.beans.LookupServiceBean;
import com.topcoder.clients.webservices.beans.ProjectStatusServiceBean;
import com.topcoder.clients.webservices.beans.Roles;
import com.topcoder.clients.webservices.beans.SessionContextMock;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;

/**
 * <p>
 * Demo test for the component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Demonstrates ProjectStatusService functionality.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testProjectStatusService() throws Exception {

        // Get the service
        ProjectStatusService projectStatusService = getProjectStatusService();

        ProjectStatus projectStatus;
        projectStatus = new ProjectStatus();
        projectStatus.setName("project status name");
        projectStatus.setDescription("project status description");

        // create a projectStatus:
        projectStatus = projectStatusService.createProjectStatus(projectStatus);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // update a projectStatus:
        projectStatus.setDescription("project status another description");
        projectStatus.setName("project status another name ");
        projectStatusService.updateProjectStatus(projectStatus);

        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // delete a projectStatus:
        projectStatusService.deleteProjectStatus(projectStatus);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.
    }

    /**
     * <p>
     * Demonstrates ClientStatusService functionality.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testClientStatusService() throws Exception {

        // Get the service
        ClientStatusService clientStatusService = getClientStatusService();

        ClientStatus clientStatus;
        clientStatus = new ClientStatus();
        clientStatus.setName("client status name");
        clientStatus.setDescription("client status description");

        // create a clientStatus:
        clientStatus = clientStatusService.createClientStatus(clientStatus);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // update a clientStatus:
        clientStatus.setName("client status another name ");
        clientStatus.setDescription("client status another description");
        clientStatus = clientStatusService.updateClientStatus(clientStatus);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // delete a clientStatus:
        clientStatusService.deleteClientStatus(clientStatus);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.
    }

    /**
     * <p>
     * Demonstrates LookupService functionality.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testLookupService() throws Exception {
        // Get the service
        LookupService lookupService = getLookupService();

        // we assume that there is already a filter provided by the application:
        Filter filter = new NullFilter("test");

        Client client;
        List < Client > clients;

        ClientStatus clientStatus;
        clientStatus = new ClientStatus();
        clientStatus.setName("client status name");
        clientStatus.setDescription("client status description");

        // retrieve client with the id 111222333:
        client = lookupService.retrieveClient(111222333);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // retrieve all clients:
        clients = lookupService.retrieveAllClients();
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        assertTrue("Client should be in the list of all clients.", clients.contains(client));

        // search clients with the name 'TopCoder':
        clients = lookupService.searchClientsByName("TopCoder");
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // search clients with the given filter:
        clients = lookupService.searchClients(filter);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.

        // get clients with the given client status:
        clients = lookupService.getClientsForStatus(clientStatus);
        // if the user is in "User" or "Admin" roles then the operation has been performed otherwise an
        // AuthorizationFailedException has been thrown.
    }

    /**
     * <p>
     * Creates properly configured ProjectStatusService instance.
     * </p>
     *
     * @return ProjectStatusService instance
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    private ProjectStatusService getProjectStatusService() throws Exception {
        ProjectStatusServiceBean bean = new ProjectStatusServiceBean();
        bean.setVerboseLogging(false);

        // Initialize required resources
        this.setField(bean, "projectManagerFile", "config.properties");
        this.setField(bean, "projectManagerNamespace", "ProjectStatusServiceBean");
        Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        this.setField(bean, "sessionContext", context);

        return bean;
    }

    /**
     * <p>
     * Creates properly configured ClientStatusService instance.
     * </p>
     *
     * @return ClientStatusService instance
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    private ClientStatusService getClientStatusService() throws Exception {
        ClientStatusServiceBean bean = new ClientStatusServiceBean();
        bean.setVerboseLogging(false);

        // Initialize required resources
        this.setField(bean, "clientManagerFile", "config.properties");
        this.setField(bean, "clientManagerNamespace", "ClientStatusServiceBean");
        Method method = ClientStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        this.setField(bean, "sessionContext", context);

        return bean;
    }

    /**
     * <p>
     * Creates properly configured LookupService instance.
     * </p>
     *
     * @return LookupService instance
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    private LookupService getLookupService() throws Exception {
        LookupServiceBean bean = new LookupServiceBean();
        bean.setVerboseLogging(false);

        // Initialize required resources
        this.setField(bean, "clientManagerFile", "config.properties");
        this.setField(bean, "clientManagerNamespace", "LookupServiceBean");
        this.setField(bean, "companyManagerFile", "config.properties");
        this.setField(bean, "companyManagerNamespace", "LookupServiceBean");
        this.setField(bean, "projectManagerFile", "config.properties");
        this.setField(bean, "projectManagerNamespace", "LookupServiceBean");
        this.setField(bean, "userMappingRetrieverFile", "config.properties");
        this.setField(bean, "userMappingRetrieverNamespace", "LookupServiceBean");
        this.setField(bean, "clientAndProjectUserRole", Roles.USER);
        Method method = LookupServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        SessionContextMock.setRoles(new String[]{Roles.USER });
        this.setField(bean, "sessionContext", context);

        return bean;
    }

    /**
     * <p>
     * Set the value into private field of passed bean.
     * </p>
     *
     * @param bean
     *            The bean in which value will be set.
     * @param fieldName
     *            The name of field.
     * @param fieldValue
     *            The field value to be set.
     * @throws Exception
     *             to JUnit
     */
    private void setField(Object bean, String fieldName, Object fieldValue) throws Exception {
        Field field = bean.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(bean, fieldValue);
    }

}
