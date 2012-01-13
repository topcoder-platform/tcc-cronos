/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import java.util.List;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;

/**
 * <p>
 * This interface is the service for managing the direct project metadata. It is used to create, update, delete and
 * search the metadata based on the input parameters. Before storing in the database, project metadata is validated
 * for the proper conditions. Audit is performed when creating, updating or deleting records.
 * </p>
 *
 * <p>
 *     Version 1.1 changes:
 *     - Add method to batch saving a list of project metadata.
 *     - Add method to get project metadata by project id and metadata key id.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax, GreatKevin
 * @version 1.1
 */
public interface DirectProjectMetadataService {
    /**
     * Creates project metadata and returns the generated id for the entity.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create.
     * @return the generated id for created entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long createProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException;

    /**
     * Updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to update.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException;

    /**
     * Creates or updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create or update.
     *
     * @return the id of the entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException;

    /**
     * Batch save a list of project metadata.
     *
     * @param projectMetadataList a list of project metadata.
     * @param userId the id of the user.
     * @return a list of updated or created project metadata IDs.
     * @throws ValidationException if entities fail the validation.
     * @throws PersistenceException if  any problem with persistence occurs.
     * @since 1.1
     */
    public long[] saveProjectMetadata(List<DirectProjectMetadata> projectMetadataList, long userId) throws ValidationException,
                PersistenceException;

    /**
     * Deletes project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataId
     *            the project metadata id to delete.
     *
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void deleteProjectMetadata(long projectMetadataId, long userId) throws EntityNotFoundException,
        PersistenceException;

    /**
     * Gets project metadata.
     *
     * @param projectMetadataId
     *            the project metadata id to get.
     *
     * @return the ProjectMetadata for the id or null if the entity is not found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public DirectProjectMetadata getProjectMetadata(long projectMetadataId) throws PersistenceException;

    /**
     * Gets project list of metadata by project id.
     *
     * @param tcDirectProjectId
     *            the topcoder direct project id to get project list of metadata.
     *
     * @return the List of ProjectMetadata entities for the id or empty list if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public List<DirectProjectMetadata> getProjectMetadataByProject(long tcDirectProjectId)
        throws PersistenceException;

    /**
     * Gets the project metadata by project id and project metadata key id.
     *
     * @param tcDirectProjectId the id of the direct project.
     * @param projectMetadataKey the id of the project metadata key.
     * @return a list of project metadata retrieved.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.1
     */
    public List<DirectProjectMetadata> getProjectMetadataByProjectAndKey(long tcDirectProjectId, long projectMetadataKey)
                throws PersistenceException;

    /**
     * Adds list of project metadata to the given tc project.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataList
     *            the list of project metadata to add.
     * @param tcDirectProjectId
     *            the topcoder direct project id to add list of project metadata.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataList is null or contains null elements.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long tcDirectProjectId, List<DirectProjectMetadataDTO> projectMetadataList,
        long userId) throws ValidationException, PersistenceException;

    /**
     * Adds project metadata to the given list of tc projects.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param tcDirectProjectIds
     *            the topcoder direct project ids to add project metadata.
     * @param projectMetadata
     *            the project metadata to add to projects.
     *
     * @throws IllegalArgumentException
     *             if tcDirectProjectIds or projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)
        throws ValidationException, PersistenceException;

    /**
     * Searches the projects by the given search filter.
     *
     * @param filter
     *            the direct project filter to search projects.
     *
     * @return the List of projects for the filter or empty list of no entity was found.
     *
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public List<TcDirectProject> searchProjects(DirectProjectFilter filter) throws PersistenceException;
}
