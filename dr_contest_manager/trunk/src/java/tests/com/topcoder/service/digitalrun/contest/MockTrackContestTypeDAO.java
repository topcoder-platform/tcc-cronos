/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.List;

import com.topcoder.service.digitalrun.entity.TrackContestType;

/**
 * <p>
 * Mock implementation of <code>TrackContestTypeTypeDAO</code>.
 * All the methods throw <code>PersistenceException</code> always.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTrackContestTypeDAO implements TrackContestTypeDAO {


    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @return never.
     *
     * @throws PersistenceException always.
     */
    public List < TrackContestType > getAllTrackContestTypes()
        throws PersistenceException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestType Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     * @throws EntityExistsException never.
     */
    public TrackContestType createTrackContestType(TrackContestType trackContestType)
        throws PersistenceException, EntityExistsException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestTypeId Useless parameter.
     * @return never.
     *
     * @throws PersistenceException always.
     */
    public TrackContestType getTrackContestType(long trackContestTypeId)
        throws PersistenceException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestTypeId Useless parameter.
     *
     * @throws PersistenceException always.
     * @throws EntityNotFoundException never.
     */
    public void removeTrackContestType(long trackContestTypeId)
        throws PersistenceException, EntityNotFoundException {
        throw new PersistenceException("mock exception");
    }

    /**
     * <p>
     * Throws <code>PersistenceException</code> always.
     * </p>
     *
     * @param trackContestType Useless parameter.
     *
     * @throws PersistenceException always.
     * @throws EntityNotFoundException never.
     */
    public void updateTrackContestType(TrackContestType trackContestType)
        throws PersistenceException, EntityNotFoundException {
        throw new PersistenceException("mock exception");
    }
}
