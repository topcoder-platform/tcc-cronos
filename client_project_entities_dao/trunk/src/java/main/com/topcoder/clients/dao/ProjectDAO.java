/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import com.topcoder.clients.model.Project;

/**
 * <p>
 * This interface represents the ProjectDAO business interface.
 * </p>
 * <p>
 * This interface defines the specific methods available for the ProjectDAO
 * business interface: retrieve project by id and retrieve all projects.
 * </p>
 * <p>
 * See base interface for other available operations.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface ProjectDAO extends GenericDAO<Project, Long> {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the
     * ProjectDAO business interface. Represents the EJB session bean name.
     * </p>
     * <p>
     * It is initialized to a default value: "ProjectDAOBean" String during
     * runtime.
     * </p>
     */
    public static final String BEAN_NAME = "ProjectDAOBean";

    /**
     * <p>
     * Defines the operation that performs the retrieval of a project using the
     * given id from the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned.
     * </p>
     *
     * @param id
     *                the identifier of the Project that should be retrieved.
     *                Should be positive and not null.
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the Project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundException
     *                 if id is not found in the persistence.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public Project retrieveById(Long id, boolean includeChildren)
        throws EntityNotFoundException, DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from
     * the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned. If nothing is found, return an empty list.
     * </p>
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Project> retrieveAll(boolean includeChildren)
        throws DAOException;
}
