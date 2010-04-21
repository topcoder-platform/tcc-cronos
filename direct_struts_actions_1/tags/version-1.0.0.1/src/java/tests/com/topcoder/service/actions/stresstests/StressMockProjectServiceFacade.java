package com.topcoder.service.actions.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.model.Project;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.project.DAOFault;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.UserNotFoundFault;

public class StressMockProjectServiceFacade implements ProjectServiceFacade {

    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault,
            IllegalArgumentFault {
        return projectData;
    }

    public List<ProjectData> getAllProjects(TCSubject tcSubject) throws PersistenceFault,
            AuthorizationFailedFault, UserNotFoundFault {
        List<ProjectData> result = new ArrayList<ProjectData>();
        for (int i = 0; i < 3000; i++) {
            ProjectData prj = new ProjectData();
            result.add(prj);
        }
        return result;
    }

    public List<Project> getClientProjectsByUser(TCSubject tcSubject) throws DAOFault {
        List<Project> result = new ArrayList<Project>();
        for (int i = 0; i < 3000; i++) {
            Project prj = new Project();
            result.add(prj);
        }
        return result;
    }

    public ProjectData getProject(TCSubject tcSubject, long projectId) throws PersistenceFault,
            ProjectNotFoundFault, AuthorizationFailedFault {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ProjectData> getProjectsForUser(TCSubject tcSubject, long userId) throws PersistenceFault,
            UserNotFoundFault, AuthorizationFailedFault {
        // TODO Auto-generated method stub
        return null;
    }

}
