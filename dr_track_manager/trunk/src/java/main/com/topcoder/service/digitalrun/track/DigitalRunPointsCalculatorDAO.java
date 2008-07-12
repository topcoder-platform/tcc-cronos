/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.PointsCalculator;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for classes that will manage digital run <code> PointsCalculator</code> entities
 * into persistence.
 * </p>
 *
 * <p>
 * The implementing classes will provide the following functionality:
 *
 * <ul>
 * <li>
 * -create a digital run PointsCalculator entity into persistence
 * </li>
 * <li>
 * -update a digital run PointsCalculator entity into persistence
 * </li>
 * <li>
 * -remove a digital run PointsCalculator entity identified by its id from persistence
 * </li>
 * <li>
 * -get a digital run PointsCalculator entity identified by its id from persistence
 * </li>
 * <li>
 * -get all digital run PointsCalculator entities from persistence
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Implementations are not required to be thread safe.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public interface DigitalRunPointsCalculatorDAO {
    /**
     * <p>
     * Creates a new PointsCalculator entity into persistence. Returns the PointsCalculator instance with id generated.
     * </p>
     *
     * @param pointsCalculator the PointsCalculator to be created
     *
     * @return the PointsCalculator with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id large than 0
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public PointsCalculator createPointsCalculator(PointsCalculator pointsCalculator)
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>Updates the given PointsCalculator instance into persistence.</p>
     *
     * @param pointsCalculator the PointsCalculator to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a PointsCalculator entity with DigitalRunPoints.id does not exist in the
     *         persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updatePointsCalculator(PointsCalculator pointsCalculator)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Removes the PointsCalculator type entity identified by the given id from persistence.
     * </p>
     *
     * @param pointsCalculatorId the id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no PointsCalculator entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removePointsCalculator(long pointsCalculatorId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Gets all the PointsCalculator entities from persistence. If there is no PointsCalculator in persistence an empty
     * list is returned.
     * </p>
     *
     * @return a list containing all operations or an empty list if there is no such entity
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < PointsCalculator > getAllPointsCalculators()
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Gets the PointsCalculator entity identified by the given id from persistence.
     * </p>
     *
     * @param pointsCalculatorId the id
     *
     * @return the PointsCalculator identified by id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no PointsCalculator entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public PointsCalculator getPointsCalculator(long pointsCalculatorId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException;
}
