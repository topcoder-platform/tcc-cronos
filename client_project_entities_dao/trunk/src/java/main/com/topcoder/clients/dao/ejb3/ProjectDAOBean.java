/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ProjectDAO business
 * interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ProjectDAO business
 * interface: retrieve project by id and retrieve all projects.
 * </p>
 * <p>
 * See base class for other available operations.
 * </p>
 * <p>
 * It uses the EntityManager configured in the base class to perform the needed
 * operations, retrieve the EntityManager using it's corresponding getter.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class is technically mutable since the
 * inherited configuration properties (with {@link PersistenceContext}) are set
 * after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread
 * safety in this case.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@Local(ProjectDAOLocal.class)
@Remote(ProjectDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectDAOBean extends GenericEJB3DAO<Project, Long> implements
        ProjectDAO, ProjectDAOLocal, ProjectDAORemote {
    /**
     * Default no-arg constructor. Constructs a new 'ProjectDAOBean' instance.
     */
    public ProjectDAOBean() {
    }

    /**
     * Performs the retrieval of a project using the given id from the
     * persistence. If include children is true return the Project.childProjects
     * list too, otherwise the list should not be returned.
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
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public Project retrieveById(Long id, boolean includeChildren)
        throws DAOException {
        Project project = retrieveById(id);

        // if includeChildren is true we return the childProjects list too
        if (includeChildren) {
            return project;
        } else {
            project.setChildProjects(null);
            return project;
        }
    }

    /**
     * Performs the retrieval of all projects from the persistence. If include
     * children is true return the Project.childProjects list too; otherwise the
     * list should not be returned. If nothing is found, return an empty list.
     * Return only the entities that are not marked as deleted.
     *
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Project> retrieveAll(boolean includeChildren)
        throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            String queryString = "select p from Project p"
                    + " where p.deleted = false";
            Query query = entityManager.createQuery(queryString);

            // Involves an unchecked conversion:
            List<Project> projects = query.getResultList();

            // if includeChildren is true we return the childProjects list too
            if (includeChildren) {
                return projects;
            } else {
                for (Project proj : projects) {
                    proj.setChildProjects(null);
                }
                return projects;
            }
        } catch (Exception e) {
            throw new DAOException("Failed to retrieve all project.", e);
        }
    }
}
