package com.topcoder.clients.webservices.webserviceclients;

import com.topcoder.clients.webservices.ProjectService;

public class ProjectServiceClient {

    public ProjectService getProjectServicePort() {
        return new ProjectService();
    }

}
