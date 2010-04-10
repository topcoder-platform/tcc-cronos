/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

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

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Accuracy test for <code>CreateProjectAction</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectServiceFacade implements ProjectServiceFacade {
    public ProjectData createProject(TCSubject tcSubject,
        ProjectData projectData) throws PersistenceFault, IllegalArgumentFault {
        return new ProjectData();
    }

    public ProjectData getProject(TCSubject tcSubject, long projectId)
        throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault {
        return new ProjectData();
    }

    public List<ProjectData> getProjectsForUser(TCSubject tcSubject, long userId)
        throws PersistenceFault, UserNotFoundFault, AuthorizationFailedFault {
        return new ArrayList<ProjectData>();
    }

    public List<ProjectData> getAllProjects(TCSubject tcSubject)
        throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault {
        return new ArrayList<ProjectData>();
    }

    public List<Project> getClientProjectsByUser(TCSubject tcSubject)
        throws DAOFault {
        return new ArrayList<Project>();
    }
}
