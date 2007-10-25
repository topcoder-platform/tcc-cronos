package com.topcoder.registration.team.service.failuretests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.search.builder.filter.Filter;

public class MockProjectServices implements ProjectServices {

    public MockProjectServices() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FullProjectData[] findActiveProjects() {
        return new FullProjectData[0];
    }

    public Project[] findActiveProjectsHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

    public FullProjectData[] findFullProjects(Filter arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] findProjectsHeaders(Filter arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public FullProjectData getFullProjectData(long arg0) {
        if (arg0 == 1003) {
            return null;
        }
        if (arg0 == 1004) {
            throw new ProjectServicesException("");
        }
        return new FullProjectData(new Date(), new DefaultWorkdays());
    }

}
