package com.topcoder.clients.webservices.failuretests;

import java.util.List;

import com.topcoder.clients.manager.ProjectEntityNotFoundException;
import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.manager.ProjectManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.Filter;

public class DummyProjectManager implements ProjectManager {

    public Project createProject(Project project, Client client) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project deleteProject(Project project) throws ProjectManagerException, ProjectEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectStatus deleteProjectStatus(ProjectStatus status) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ProjectStatus> getAllProjectStatuses() throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectStatus getProjectStatus(long id) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> getProjectsForStatus(ProjectStatus status) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> retrieveAllProjects() throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project retrieveProject(long id) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> retrieveProjectsForClient(Client client) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> searchProjects(Filter filter) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> searchProjectsByName(String name) throws ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project setProjectStatus(Project project, ProjectStatus status) throws ProjectEntityNotFoundException,
            ProjectManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project updateProject(Project project) throws ProjectManagerException, ProjectEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectStatus updateProjectStatus(ProjectStatus status) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
