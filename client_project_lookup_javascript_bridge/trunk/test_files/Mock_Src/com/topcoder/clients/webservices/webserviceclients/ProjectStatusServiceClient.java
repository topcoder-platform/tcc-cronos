/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import com.topcoder.clients.webservices.ProjectStatusService;

;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectStatusServiceClient {
    /**
     * <p>
     * Simply return the ProjectStatusService web service endpoint interface.
     * </p>
     *
     * @return the ProjectStatusService web service
     * @throws WebServiceException
     *             if appears any problem during retrieving the endpoint interface
     */
    public ProjectStatusService getProjectStatusServicePort() {
        return new ProjectStatusService();
    }

}
