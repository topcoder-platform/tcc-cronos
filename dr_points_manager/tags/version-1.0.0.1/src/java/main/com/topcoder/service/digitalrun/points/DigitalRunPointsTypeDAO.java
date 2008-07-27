/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.List;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;

/**
 * <p>
 * This interface defines the contract for classes that will manage digital run points type entities
 * into persistence. The implementing classes will provide the following functionality: <br>
 * create a digital run points type entity into persistence <br>
 * update a digital run points type entity into persistence <br>
 * remove a digital run points type entity identified by its id from persistence <br>
 * get a digital run points type entity identified by its id from persistence <br>
 * get all digital run points type entities from persistence
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunPointsTypeDAO {
    /**
     * Creates a new DigitalRunPointsType entity into persistence. Returns the DigitalRunPointsType
     * instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsType
     *            the type to be created
     * @return the type with id set
     */
    public DigitalRunPointsType createDigitalRunPointsType(DigitalRunPointsType pointsType)
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Updates the given DigitalRunPointsType instance into persistence.
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points type entity with DigitalRunPoints.id does not exist in
     *             the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsType
     *            the type to be updated
     */
    public void updateDigitalRunPointsType(DigitalRunPointsType pointsType) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points type entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points type entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsTypeId
     *            the id
     */
    public void removeDigitalRunPointsType(long pointsTypeId) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets the digital run points type entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points type entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the type identified by id
     */
    public DigitalRunPointsType getDigitalRunPointsType(long id) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets all the digital run points types from persistence. If there is no digital run points
     * type in persistence an empty list is returned.
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return a list containing all types or empty list if no type
     */
    public List<DigitalRunPointsType> getAllDigitalRunPointsTypes()
        throws DigitalRunPointsManagerPersistenceException;
}
