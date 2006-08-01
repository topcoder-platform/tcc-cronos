/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ScorecardAssignment;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {


    public void createProject(Project project, String operator) {
    }

    public void updateProject(Project project, String operator) {
    }

    public Project getProject(long id) {
        if (id == -1) {
            return null;
        } else {
            return new MockProject();
        }
        
    }

    public Project[] searchProjects(Filter filter) {
        return null;
    }

    public Project[] getUserProjects(long user) {
        return null;
    }

    public ProjectCategory[] getAllProjectCategories() {
        return null;
    }

    public ProjectStatus[] getAllProjectStatuses() {
        return null;
    }

    public ScorecardAssignment[] getAllScorecardAssignments() {
        return null;
    }
}
