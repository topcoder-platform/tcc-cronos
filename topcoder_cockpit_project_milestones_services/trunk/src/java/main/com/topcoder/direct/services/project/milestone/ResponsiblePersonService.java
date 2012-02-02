/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import java.util.List;

import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * <p>
 * This interface defines the service contract for the retrieval of responsible people in a given direct project.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations are expected to be effectively thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ResponsiblePersonService {
    /**
     * This method gets all responsible people for the given project ID. If none found, returns an empty list.
     *
     * @param projectId
     *            the ID of the direct project
     * @return the responsible people
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public List<ResponsiblePerson> getAllResponsiblePeople(long projectId)
        throws ProjectMilestoneManagementException;
}
