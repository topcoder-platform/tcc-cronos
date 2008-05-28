/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.ProjectNotFoundFault;
import com.topcoder.util.errorhandling.ExceptionData;

import javax.xml.ws.WebFault;

/**
 * <p> This exception is thrown automatically by the web service interface when a project is not found. It is related
 * with "project_not_found_faultMsg" fault in WSDL, but it contains only annotations about "project_not_found_fault"
 * because the messages are automatically constructed. It was generated by JBoss tools and I changed the name, the
 * inheritance and other changes.</p><p> The constructors with ProjectNotFoundFault and the getter are necessary for the
 * correct webservices serialization/deserialization. The other constructors are added for the future implementations of
 * the service interface.</p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "project_not_found_fault", faultBean = "com.topcoder.service.studio.ejb.ProjectNotFoundFault")
public class ProjectNotFoundException extends StudioServiceException {
    /**
     * <p> Represents the faultInfo, Java type that goes as soapenv:Fault detail element.</p>
     */
    private final ProjectNotFoundFault faultInfo;

    /**
     * <p> Represents the projectNotFound. I updated the WSDL, now it's an long. It is retrieved from
     * ProjectNotFoundFault.</p>
     */
    private final long projectIdNotFound;

    /**
     * <p> Construct the exception. Call the super constructor,set the fault info and set the projectIdNotFound from the
     * faultInfo.</p>
     *
     * @param message   the message
     * @param faultInfo the fault info which contains the projectIdNotFound
     */
    public ProjectNotFoundException(String message, ProjectNotFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.projectIdNotFound = (faultInfo == null) ? -1 : faultInfo.getProjectIdNotFound();
    }

    /**
     * <p> Construct the exception. Call the super constructor,set the fault info and set the projectIdNotFound from the
     * faultInfo.</p>
     *
     * @param message   the message
     * @param faultInfo the fault info which contains the projectIdNotFound
     * @param cause     the cause
     */
    public ProjectNotFoundException(String message, ProjectNotFoundFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.projectIdNotFound = (faultInfo == null) ? -1 : faultInfo.getProjectIdNotFound();
    }

    /**
     * <p>Constructor with error message.</p>
     *
     * @param message           the error message
     * @param projectIdNotFound the project's id not found
     */
    public ProjectNotFoundException(String message, long projectIdNotFound) {
        super(message);
        this.faultInfo = null;
        this.projectIdNotFound = projectIdNotFound;
    }

    /**
     * <p>Constructor with error message and inner cause.</p>
     *
     * @param message           the error message
     * @param cause             the cause of this exception
     * @param projectIdNotFound the project's id not found
     */
    public ProjectNotFoundException(String message, Throwable cause, long projectIdNotFound) {
        super(message, cause);
        this.faultInfo = null;
        this.projectIdNotFound = projectIdNotFound;
    }

    /**
     * <p>Constructor with error message and exception data</p>
     *
     * @param message           the error message
     * @param projectIdNotFound the project's id not found
     * @param data              the exception data
     */
    public ProjectNotFoundException(String message, ExceptionData data, long projectIdNotFound) {
        super(message, data);
        this.faultInfo = null;
        this.projectIdNotFound = projectIdNotFound;
    }

    /**
     * <p>Constructor with error message and inner cause and exception data</p>
     *
     * @param message           the error message
     * @param cause             the cause of this exception
     * @param data              the exception data
     * @param projectIdNotFound the project's id not found
     */
    public ProjectNotFoundException(String message, Throwable cause, ExceptionData data, long projectIdNotFound) {
        super(message, cause, data);
        this.faultInfo = null;
        this.projectIdNotFound = projectIdNotFound;
    }

    /**
     * <p> Return the projectId NotFound</p>
     *
     * @return the projectId NotFound
     */
    public long getProjectIdNotFound() {
        return projectIdNotFound;
    }

    /**
     * <p> Return the fault bean. This method is necessary for the serialization/deserialization. it returns null if the
     * constructors without fault bean are used.</p>
     *
     * @return returns fault bean
     */
    public ProjectNotFoundFault getFaultInfo() {
        return faultInfo;
    }
}

