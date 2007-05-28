package com.topcoder.registration.team.service.failuretests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.search.builder.filter.Filter;

public class MockProjectManager implements ProjectManager {

    public MockProjectManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void createProject(Project arg0, String arg1) throws PersistenceException, ValidationException {
        // TODO Auto-generated method stub
        
    }

    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException, ValidationException {
        // TODO Auto-generated method stub
        
    }

    public Project getProject(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getProjects(long[] arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getUserProjects(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

}
