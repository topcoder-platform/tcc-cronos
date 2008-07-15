/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.TrackContest;

import java.util.List;


/**
 * <p>
 * Mock implementation of <code>TrackContestDAO</code>.
 * All the methods throw <code>PersistenceException</code> always.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTrackContestDAO implements TrackContestDAO {

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContest Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     * @throws EntityExistsException never.
     */
    public TrackContest createTrackContest(TrackContest trackContest)
        throws PersistenceException, EntityExistsException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestId Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     */
    public TrackContest getTrackContest(long trackContestId)
        throws PersistenceException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestId Useless parameter.
     *
     * @throws PersistenceException always.
     * @throws EntityNotFoundException never.
     */
    public void removeTrackContest(long trackContestId)
        throws PersistenceException, EntityNotFoundException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContest Useless parameter.
     *
     * @throws PersistenceException always.
     * @throws EntityNotFoundException never.
     */
    public void updateTrackContest(TrackContest trackContest)
        throws PersistenceException, EntityNotFoundException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param filter Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     */
    public List < TrackContest > searchTrackContests(Filter filter)
        throws PersistenceException {
        throw new PersistenceException("mock exception");
    }
}
