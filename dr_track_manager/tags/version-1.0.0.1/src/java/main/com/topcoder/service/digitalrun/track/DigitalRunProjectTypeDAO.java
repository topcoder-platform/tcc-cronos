/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.ProjectType;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for classes that will manage <code>ProjectType</code> entities into persistence.
 * </p>
 *
 * <p>
 * The  implementing classes will provide the following functionality:
 *
 * <ul>
 * <li>
 * -get a digital run ProjectType entity identified by its id from persistence
 * </li>
 * <li>
 * -get all digital run ProjectType entities from persistence.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Implementations are not required to be thread safe.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public interface DigitalRunProjectTypeDAO {
    /**
     * <p>
     * Gets the ProjectType entity identified by the given id from the persistence.
     * </p>
     *
     * @param projectTypeId the id
     *
     * @return the ProjectType identified by id
     *
     * @throws IllegalArgumentException id argument less than 0
     * @throws EntityNotFoundException if there is no ProjectType with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public ProjectType getProjectType(long projectTypeId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Gets all ProjectTypes entities from persistence. If there is no ProjectType in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all project types or empty list if no project types
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < ProjectType > getAllProjectTypes() throws DigitalRunTrackManagerPersistenceException;
}
