/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>ProjectManagerException</code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestProjectManagerException extends TestCase {

    /**
     * Test method for 'ProjectManagerException(Project, ProjectStatus)'.
     */
    public void testProjectManagerExceptionProjectProjectStatus() {
        Project project = new Project();
        ProjectStatus status = new ProjectStatus();

        ProjectManagerException e = new ProjectManagerException(project, status);

        assertEquals("Equal to project.", project, e.getProject());

        assertEquals("Equal to ProjectStatus.", status, e.getProjectStatus());

    }

    /**
     * Test method for 'ProjectManagerException(String, Project, ProjectStatus)'.
     */
    public void testProjectManagerExceptionStringProjectProjectStatus() {

        Project project = new Project();
        ProjectStatus status = new ProjectStatus();

        ProjectManagerException e = new ProjectManagerException("error", project, status);

        assertEquals("Equal to 'error'", "error", e.getMessage());
        assertEquals("Equal to project.", project, e.getProject());

        assertEquals("Equal to ProjectStatus.", status, e.getProjectStatus());

    }

    /**
     * Test method for 'ProjectManagerException(String, Throwable, Project, ProjectStatus)'.
     */
    public void testProjectManagerExceptionStringThrowableProjectProjectStatus() {

        Project project = new Project();
        ProjectStatus status = new ProjectStatus();

        Exception cause = new NullPointerException("NPE");

        ProjectManagerException e = new ProjectManagerException("error", cause, project, status);

        assertEquals("Equal to 'error'", "error", e.getMessage());
        assertEquals("Equal to project.", project, e.getProject());

        assertEquals("Equal to ProjectStatus.", status, e.getProjectStatus());

        assertEquals("The cause should be npe.", cause, e.getCause());
    }

    /**
     * Test method for 'ProjectManagerException(String, Throwable, ExceptionData, Project, ProjectStatus)'.
     */
    public void testProjectManagerExceptionStringThrowableExceptionDataProjectProjectStatus() {

        Project project = new Project();
        ProjectStatus status = new ProjectStatus();

        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        ProjectManagerException e = new ProjectManagerException("error", cause, data, project, status);

        assertEquals("Equal to 'error'", "error", e.getMessage());
        assertEquals("Equal to project.", project, e.getProject());

        assertEquals("Equal to ProjectStatus.", status, e.getProjectStatus());

        assertEquals("The cause should be npe.", cause, e.getCause());

        assertEquals("Equal to 'code'", "code", e.getApplicationCode());
    }

    /**
     * Test method for 'ProjectManagerException.getProject()'.
     */
    public void testGetProject() {
        Project project = new Project();
        ProjectStatus status = new ProjectStatus();

        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        ProjectManagerException e = new ProjectManagerException("error", cause, data, project, status);

        assertEquals("Equal to project.", project, e.getProject());

    }

    /**
     * Test method for 'getProjectStatus()'.
     */
    public void testGetProjectStatus() {
        Project project = new Project();
        ProjectStatus status = new ProjectStatus();

        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        ProjectManagerException e = new ProjectManagerException("error", cause, data, project, status);
        assertEquals("Equal to ProjectStatus.", status, e.getProjectStatus());

    }

}
