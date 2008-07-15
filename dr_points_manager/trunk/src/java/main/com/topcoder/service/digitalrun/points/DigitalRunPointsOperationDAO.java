/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.List;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;

/**
 * <p>
 * This interface defines the contract for classes that will manage digital run points operation
 * entities into persistence. The implementing classes will provide the following functionality:
 * <br>
 * create a digital run points operation entity into persistence <br>
 * update a digital run points operation entity into persistence <br>
 * remove a digital run points operation entity identified by its id from persistence <br>
 * get a digital run points operation entity identified by its id from persistence <br>
 * get all digital run points operation entities from persistence
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunPointsOperationDAO {
    /**
     * Creates a new DigitalRunPointsOperation entity into persistence. Returns the
     * DigitalRunPointsOperation instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperation
     *            the operation to be created
     * @return the operation with id set
     */
    public DigitalRunPointsOperation createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Updates the given DigitalRunPointsOperation instance into persistence.
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points operation entity with DigitalRunPoints.id does not exist
     *             in the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperation
     *            the operation to be updated
     */
    public void updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points operation entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points operation entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperationId
     *            the id
     */
    public void removeDigitalRunPointsOperation(long pointsOperationId) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets the digital run points operation entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points operation entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the operation identified by id
     */
    public DigitalRunPointsOperation getDigitalRunPointsOperation(long id) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets all the digital run points operations from persistence. If there is no digital run
     * points operation in persistence an empty list is returned.
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list containing all operations or empty list if no operation
     */
    public List<DigitalRunPointsOperation> getAllDigitalRunPointsOperations()
        throws DigitalRunPointsManagerPersistenceException;
}
