/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is used to indicate any error that occurs in ProjectManager's business methods, for example, an error
 * thrown by underlying ProjectDAO implementation will be caught and wrapped in this exception and re-thrown.
 * It extends from ManagerException.
 *
 * It has a field that can be used to carry a Project instance when the exception is constructed, user can retrieve the
 * project by related getter, it helps user diagnose the exception.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class ProjectManagerException extends ManagerException {
    /**
     * Represents a Project instance. Typically, when an operation fails, the project entity it operates on will be
     * stored in this field to let the user diagnose the error.
     *
     * It's set in the constructor, can be null and has a getter.
     */
    private final Project project;

    /**
     * Represents a ProjectStatus instance. Typically, when an operation fails, the project status entity it operates
     * on will be stored in this field to let the user diagnose the error.
     *
     * It's set in the constructor, can be null and has a getter.
     */
    private final ProjectStatus projectStatus;

    /**
     * Constructs an instance of this exception with specified Project object, and ProjectStatus object.
     *
     *
     * @param project
     *            the Project instance associated with this error, can be null
     * @param projectStatus
     *            the ProjectStatus instance associated with this error, can be null
     */
    public ProjectManagerException(Project project, ProjectStatus projectStatus) {
        this.project = project;
        this.projectStatus = projectStatus;
    }

    /**
     * Constructs an instance of this exception with given error message and Project object and ProjectStatus object.
     *
     *
     * @param message
     *            the error message
     * @param project
     *            the Project instance associated with this error, can be null
     * @param projectStatus
     *            the ProjectStatus instance associated with this error, can be null
     */
    public ProjectManagerException(String message, Project project, ProjectStatus projectStatus) {
        super(message);
        this.project = project;
        this.projectStatus = projectStatus;
    }

    /**
     * Constructs an instance of this exception with given error message, inner cause , Project object
     * and ProjectStatus object.
     *
     * @param message
     *            the error message
     * @param project
     *            the Project instance associated with this error, can be null
     * @param cause
     *            the inner error
     * @param projectStatus
     *            the ProjectStatus instance associated with this error, can be null
     */
    public ProjectManagerException(String message, Throwable cause, Project project, ProjectStatus projectStatus) {
        super(message, cause);

        this.project = project;
        this.projectStatus = projectStatus;
    }

    /**
     * Constructs an instance of this exception with given error message, inner cause, exception data, Project
     * object and ProjectStatus.
     *
     * @param message
     *            the error message
     * @param project
     *            the Project instance associated with this error, can be null
     * @param cause
     *            the inner error
     * @param projectStatus
     *            the ProjectStatus instance associated with this error, can be null
     * @param exceptionData
     *            an object carrying the details of this exception
     */
    public ProjectManagerException(String message, Throwable cause, ExceptionData exceptionData, Project project,
            ProjectStatus projectStatus) {
        super(message, cause, exceptionData);

        this.project = project;
        this.projectStatus = projectStatus;
    }

    /**
     * Gets the Project instance related to this exception.
     *
     * @return a Project instance, can be null
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the ProjectStatus instance related to this exception.
     *
     * @return a ProjectStatus instance, can be null
     */
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }
}
