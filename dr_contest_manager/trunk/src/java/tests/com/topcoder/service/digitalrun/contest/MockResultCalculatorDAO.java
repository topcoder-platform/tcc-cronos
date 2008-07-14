/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.List;

import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;

/**
 * <p>
 * Mock implementation of <code>TrackContestResultCalculatorDAO</code>.
 * All the methods throw <code>PersistenceException</code> always.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockResultCalculatorDAO implements TrackContestResultCalculatorDAO {


    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @return never.
     *
     * @throws PersistenceException always.
     */
    public List < TrackContestResultCalculator > getAllTrackContestResultCalculators()
        throws PersistenceException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestResultCalculator Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     * @throws EntityExistsException never.
     */
    public TrackContestResultCalculator createTrackContestResultCalculator(
        TrackContestResultCalculator trackContestResultCalculator)
        throws PersistenceException, EntityExistsException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestResultCalculatorId Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     */
    public TrackContestResultCalculator getTrackContestResultCalculator(long trackContestResultCalculatorId)
        throws PersistenceException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestResultCalculatorId Useless parameter.
     *
     * @throws PersistenceException always.
     * @throws EntityNotFoundException never.
     */
    public void removeTrackContestResultCalculator(long trackContestResultCalculatorId)
        throws PersistenceException, EntityNotFoundException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestResultCalculator Useless parameter.
     *
     * @throws PersistenceException always.
     * @throws EntityNotFoundException never.
     */
    public void updateTrackContestResultCalculator(TrackContestResultCalculator trackContestResultCalculator)
        throws PersistenceException, EntityNotFoundException {
        throw new PersistenceException("mock exception");
    }

}
