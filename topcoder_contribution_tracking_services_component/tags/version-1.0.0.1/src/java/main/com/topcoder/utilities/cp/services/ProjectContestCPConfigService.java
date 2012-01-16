/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services;

import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;

/**
 * <p>
 * This interface represents the DAO of the contribution points associated with the provided contest. It provides CRUD
 * methods for manipulating contribution points that are associated with the provided contest.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public interface ProjectContestCPConfigService {
    /**
     * Creates the ProjectContestCPConfig entity.
     *
     * @param config
     *            the ProjectContestCPConfig entity.
     *
     * @return the ID of the created ProjectContestCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#contestId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public long create(ProjectContestCPConfig config) throws ContributionServiceException;

    /**
     * Updates the ProjectContestCPConfig entity.
     *
     * @param config
     *            the ProjectContestCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#contestId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public void update(ProjectContestCPConfig config) throws ContributionServiceException;

    /**
     * Deletes the ProjectContestCPConfig entity.
     *
     * @param contestId
     *            the ID of the contest.
     *
     * @throws IllegalArgumentException
     *             if contestId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public void delete(long contestId) throws ContributionServiceException;

    /**
     * Gets the ProjectContestCPConfig entity by id.
     *
     * @param contestId
     *            the ID of the contest.
     *
     * @return the ProjectContestCPConfig entity or <code>null</code> if the entity doesn't exist.
     *
     * @throws IllegalArgumentException
     *             if contestId is not positive.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public ProjectContestCPConfig get(long contestId) throws ContributionServiceException;
}
