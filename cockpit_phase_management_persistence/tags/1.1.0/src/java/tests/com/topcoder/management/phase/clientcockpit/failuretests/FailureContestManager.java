/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import java.util.List;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestStatus;

/**
 * Mock of contestManager for failure tests.
 * 
 * @author hfx
 * @version 1.0
 */
public class FailureContestManager extends MockContestManager {
    /**
     * Add a contest status to this manager.
     * 
     * @param contestStatus
     *            the contest status to add.
     * @return the newly added contest status.
     * @throws ContestManagementException
     *             if any error occurs while adding contest status.
     */

    public ContestStatus addContestStatus(ContestStatus contestStatus)
            throws ContestManagementException {
        throw new ContestManagementException("for failure test.");
    }

    /**
     * Creates the contest in this manager.
     * 
     * @param contest
     *            contest.
     * @return created contest.
     * @throws ContestManagementException
     *             if any error occurs while creating the contest.
     */

    public Contest createContest(Contest contest)
            throws ContestManagementException {
        throw new ContestManagementException("for failure test.");
    }

    /**
     * Gets all contest statuses.
     * 
     * @return all contest statuses.
     * @throws ContestManagementException
     *             if any error occurs while retrieving all contest statuses.
     */

    public List<ContestStatus> getAllContestStatuses()
            throws ContestManagementException {
        throw new ContestManagementException("for failure test.");
    }

    /**
     * Get the contest with specified id.
     * 
     * @param contestId
     *            the contest id.
     * @return a Contest instance.
     * @throws ContestManagementException
     *             never thrown.
     */

    public Contest getContest(long contestId) throws ContestManagementException {
        throw new ContestManagementException("for failure test.");
    }

    /**
     * Gets the contest status with specified id.
     * 
     * @param contestStatusId
     *            contest status id.
     * @return ContestStatus instance.
     * @throws ContestManagementException
     *             never thrown.
     */

    public ContestStatus getContestStatus(long contestStatusId)
            throws ContestManagementException {
        throw new ContestManagementException("for failure test.");
    }

    /**
     * Updates a contest status.
     * 
     * @param contestId
     *            contest id
     * @param newStatusId
     *            new status id.
     * @throws ContestManagementException
     *             if any error occurs while updating contest status.
     */

    public void updateContestStatus(long contestId, long newStatusId)
            throws ContestManagementException {
        throw new ContestManagementException("for failure test.");
    }

}
