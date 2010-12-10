/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import java.util.Date;
import java.util.List;

import com.topcoder.reliability.Configurable;

/**
 * <p>
 * This interface represents a reliability data persistence. Before and after accessing the persistence open() and
 * close() methods of this interface must be called respectively. This interface defines methods for retrieving IDs of
 * users that have reliability, retrieving participation data, saving reliability history data and updating current
 * reliability of specific user. This interface extends Configurable interface to support configuration via
 * Configuration API component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface ReliabilityDataPersistence extends Configurable {
    /**
     * <p>
     * Opens a connection to persistence.
     * </p>
     *
     * @throws IllegalStateException
     *             if connection is already opened; if this persistence was not properly configured (is not thrown by
     *             implementation that don't require any configuration parameters).
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    public void open() throws ReliabilityDataPersistenceException;

    /**
     * <p>
     * Retrieves the IDs of users who have reliability rating in the specified project category.
     * </p>
     *
     * @param startDate
     *            the start date when the reliability started counting in the specified project category (corresponds
     *            to submission phase start date).
     * @param projectCategoryId
     *            the ID of the project category.
     *
     * @return the retrieved IDs of users with reliability (not <code>null</code>, doesn't contain <code>null</code>).
     *
     * @throws IllegalArgumentException
     *             if projectCategoryId is not positive or startDate is <code>null</code>.
     * @throws IllegalStateException
     *             if persistence connection is not opened; if this persistence was not properly configured (is not
     *             thrown by implementation that don't require any configuration parameters).
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    public List<Long> getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)
        throws ReliabilityDataPersistenceException;

    /**
     * <p>
     * Retrieves information about all projects that can count toward reliability for some specific user. Elements in
     * the result list must be sorted by the resolution date chronologically.
     * </p>
     *
     * @param projectCategoryId
     *            the ID of the project category.
     * @param userId
     *            the ID of the user.
     * @param startDate
     *            the start date when the reliability started counting in the specified project category (corresponds
     *            to submission phase start date).
     *
     * @return the retrieved user participation data (not <code>null</code>, doesn't contain <code>null</code>).
     *
     * @throws IllegalArgumentException
     *             if userId or projectCategoryId is not positive, or startDate is <code>null</code>.
     * @throws IllegalStateException
     *             if persistence connection is not opened; if this persistence was not properly configured (is not
     *             thrown by implementation that don't require any configuration parameters).
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    public List<UserProjectParticipationData> getUserParticipationData(long userId, long projectCategoryId,
        Date startDate) throws ReliabilityDataPersistenceException;

    /**
     * <p>
     * Saves the provided user reliability data in persistence. The provided list must contain information about all
     * projects that affected user's reliability.
     * </p>
     *
     * @param projects
     *            the list with reliability details for each project that affected user's reliability.
     *
     *@throws IllegalArgumentException
     *             if projects is <code>null</code>/empty or contains <code>null</code>, or for any element of
     *             projects: (resolutionDate is <code>null</code>) or any 2 elements in the list have different userId
     *             properties.
     * @throws IllegalStateException
     *             if persistence connection is not opened; if this persistence was not properly configured (is not
     *             thrown by implementation that don't require any configuration parameters).
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    public void saveUserReliabilityData(List<UserProjectReliabilityData> projects)
        throws ReliabilityDataPersistenceException;

    /**
     * <p>
     * Updates the current user reliability for the specified project category.
     * </p>
     *
     * @param projectCategoryId
     *            the ID of the project category.
     * @param userId
     *            the ID of the user.
     * @param reliability
     *            the new reliability.
     *
     * @throws IllegalArgumentException
     *             if userId or projectCategoryId is not positive, or reliability is not in the range [0, 1].
     * @throws IllegalStateException
     *             if persistence connection is not opened; if this persistence was not properly configured (is not
     *             thrown by implementation that don't require any configuration parameters).
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    public void updateCurrentUserReliability(long userId, long projectCategoryId, double reliability)
        throws ReliabilityDataPersistenceException;

    /**
     * <p>
     * Closes the connection to persistence.
     * </p>
     *
     * @throws IllegalStateException
     *             if connection is already closed; if this persistence was not properly configured (is not thrown by
     *             implementation that don't require any configuration parameters).
     */
    public void close();
}
