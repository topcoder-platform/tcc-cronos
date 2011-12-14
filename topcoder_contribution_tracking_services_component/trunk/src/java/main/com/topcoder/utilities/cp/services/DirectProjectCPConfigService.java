/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services;

import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;

/**
 * <p>
 * This interface represents the DAO of the contribution points associated with the provided contribution project. It
 * provides CRUD methods for manipulating contribution points that are associated with the provided contribution
 * project.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public interface DirectProjectCPConfigService {
    /**
     * Creates the DirectProjectCPConfig entity.
     *
     * @param config
     *            the DirectProjectCPConfig entity.
     *
     * @return the ID of the created DirectProjectCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public long create(DirectProjectCPConfig config) throws ContributionServiceException;

    /**
     * Updates the DirectProjectCPConfig entity.
     *
     * @param config
     *            the DirectProjectCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#directProjectId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public void update(DirectProjectCPConfig config) throws ContributionServiceException;

    /**
     * Deletes the DirectProjectCPConfig entity.
     *
     * @param directProjectId
     *            the ID of the direct project.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public void delete(long directProjectId) throws ContributionServiceException;

    /**
     * Gets the DirectProjectCPConfig entity by id.
     *
     * @param directProjectId
     *            the ID of the direct project.
     *
     * @return the DirectProjectCPConfig entity or <code>null</code> if the entity doesn't exist.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public DirectProjectCPConfig get(long directProjectId) throws ContributionServiceException;

    /**
     * Gets the available initial payments by id.
     *
     * @param directProjetId
     *            the ID of the direct project or <code>0</code> if the entity doesn't exist.
     *
     * @return the available initial payments.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public double getAvailableInitialPayments(long directProjetId) throws ContributionServiceException;
}
