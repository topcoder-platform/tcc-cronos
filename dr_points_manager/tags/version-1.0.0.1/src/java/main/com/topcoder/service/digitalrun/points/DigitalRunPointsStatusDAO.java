/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.List;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;

/**
 * <p>
 * This interface defines the contract for classes that will manage digital run points status
 * entities into persistence. The implementing classes will provide the following functionality:
 * <br>
 * create a digital run points status entity into persistence <br>
 * update a digital run points status entity into persistence <br>
 * remove a digital run points status entity identified by its id from persistence <br>
 * get a digital run points status entity identified by its id from persistence <br>
 * get all digital run points status entities from persistence
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunPointsStatusDAO {
    /**
     * Creates a new DigitalRunPointsStatus entity into persistence. Returns the
     * DigitalRunPointsStatus instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatus
     *            the status to be created
     * @return the status with id set
     */
    public DigitalRunPointsStatus createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Updates the given DigitalRunPointsStatus instance into persistence.
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points status entity with DigitalRunPoints.id does not exist in
     *             the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatus
     *            the status to be updated
     */
    public void updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points status entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points status entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatusId
     *            the id
     */
    public void removeDigitalRunPointsStatus(long pointsStatusId) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets the digital run points status entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points status entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the status identified by id
     */
    public DigitalRunPointsStatus getDigitalRunPointsStatus(long id) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets all the digital run points statuses from persistence. If there is no digital run points
     * status in persistence an empty list is returned.
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list contained all statuses or empty list if no status
     */
    public List<DigitalRunPointsStatus> getAllDigitalRunPointsStatuses()
        throws DigitalRunPointsManagerPersistenceException;
}
