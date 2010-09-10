/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.direct.services.copilot.model.CopilotProject;

import java.util.List;

/**
 * <p>This interface represents a copilot project DAO. It extends GenericDAO&lt;CopilotProject&gt; and provides an
 * additional method for retrieving a list of copilot projects for the specified copilot.</p>
 *
 * <p><strong>Thread safety:</strong> Implementations of this interface must be thread safe when entities passed to them
 * are used by the caller in thread safe manner.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CopilotProjectDAO extends GenericDAO<CopilotProject> {

    /**
     * <p>Retrieves the copilot projects for the copilot with the specified profile ID. Returns an empty list if the
     * copilot has no associated projects.</p>
     *
     * @param copilotProfileId the ID of the copilot profile
     *
     * @return the copilot projects for the specified copilot (not null, doesn't contain null)
     *
     * @throws IllegalArgumentException if copilotProfileId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    List<CopilotProject> getCopilotProjects(long copilotProfileId) throws CopilotDAOException;
}

