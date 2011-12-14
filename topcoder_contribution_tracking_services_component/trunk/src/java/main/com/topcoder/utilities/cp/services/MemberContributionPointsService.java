/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services;

import java.util.List;

import com.topcoder.utilities.cp.entities.MemberContributionPoints;

/**
 * <p>
 * This interface represents a member contribution points DAO. It provides CRUD methods for manipulating member
 * contribution points.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public interface MemberContributionPointsService {
    /**
     * Gets the MemberContributionPoints entities by user and contest.
     *
     * @param contestId
     *            the ID of the contest.
     * @param userId
     *            the ID of the user.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if userId or contestId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public List<MemberContributionPoints> getByUserAndContest(long userId, long contestId)
        throws ContributionServiceException;

    /**
     * Gets the MemberContributionPoints entities by user.
     *
     * @param userId
     *            the ID of the user.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public List<MemberContributionPoints> getByUserId(long userId) throws ContributionServiceException;

    /**
     * Gets the MemberContributionPoints entities by project.
     *
     * @param directProjectId
     *            the ID of the direct project.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public List<MemberContributionPoints> getByProjectId(long directProjectId) throws ContributionServiceException;

    /**
     * Gets the MemberContributionPoints entities by contest.
     *
     * @param contestId
     *            the ID of the contest.
     *
     * @return the MemberContributionPoints entities or empty if no entity found.
     *
     * @throws IllegalArgumentException
     *             if contestId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public List<MemberContributionPoints> getByContestId(long contestId) throws ContributionServiceException;

    /**
     * Creates the MemberContributionPoints entity.
     *
     * @param memberContributionPoints
     *            the MemberContributionPoints entity.
     *
     * @return the ID of the created MemberContributionPoints entity.
     *
     * @throws IllegalArgumentException
     *             if memberContributionPoints is null or memberContributionPoints#id is positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    public long create(MemberContributionPoints memberContributionPoints) throws ContributionServiceException;

    /**
     * Updates the MemberContributionPoints entity.
     *
     * @param memberContributionPoints
     *            the MemberContributionPoints entity.
     *
     * @throws IllegalArgumentException
     *             if memberContributionPoints is null or memberContributionPoints#id is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public void update(MemberContributionPoints memberContributionPoints) throws ContributionServiceException;

    /**
     * Deletes the MemberContributionPoints entity.
     *
     * @param id
     *            the ID of the MemberContributionPoints entity.
     *
     * @throws IllegalArgumentException
     *             if id is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    public void delete(long id) throws ContributionServiceException;
}
