/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.jira.webservices.client.JiraManagementServiceClient;

/**
 * This version of JiraManagementServiceClient used to simplify testing. It will allow to preset client with any
 * JiraManagementService implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockJiraManagementServiceClient extends JiraManagementServiceClient {
    /**
     * Service to return on getJiraManagementServicePort invocation.
     */
    private static JiraManagementService service;

    /**
     * Simple constructor.
     */
    public MockJiraManagementServiceClient() {
        super(Demo.WSDL_LOCATION);
        service = null;
    }

    /**
     * We need to override parent's method and return preset service.
     *
     * @return management service
     */
    public JiraManagementService getJiraManagementServicePort() {
        return service;
    }

    /**
     * Sets service which will be returned by getJiraManagementServicePort call.
     *
     * @param service service to set
     */
    public static void setService(JiraManagementService service) {
        MockJiraManagementServiceClient.service = service;
    }
}
