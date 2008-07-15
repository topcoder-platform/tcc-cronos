/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.List;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;

/**
 * <p>
 * This interface defines the contract for classes that will manage digital run points reference
 * type entities into persistence. The implementing classes will provide the following
 * functionality: <br>
 * create a digital run points reference type entity into persistence <br>
 * update a digital run points reference type entity into persistence <br>
 * remove a digital run points reference type entity identified by its id from persistence <br>
 * get a digital run points reference type entity identified by its id from persistence <br>
 * get all digital run points reference type entities from persistence
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunPointsReferenceTypeDAO {
    /**
     * Creates a new DigitalRunPointsReferenceType entity into persistence. Returns the
     * DigitalRunPointsReference instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceType
     *            the reference type to be created
     * @return the reference type with id set
     */
    public DigitalRunPointsReferenceType createDigitalRunPointsReferenceType(
            DigitalRunPointsReferenceType pointsReferenceType)
            throws DigitalRunPointsManagerPersistenceException;

    /**
     * Updates the given DigitalRunPointsReference instance into persistence.
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points reference type entity with DigitalRunPoints.id does not
     *             exist in the persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceType
     *            the reference type to be updated
     */
    public void updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points reference type entity identified by the given id from
     * persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points reference type entityt with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceTypeId
     *            the id of reference type to be removed
     */
    public void removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException;

    /**
     * Gets the digital run points reference type entity identified by the given id from
     * persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points reference type entity with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id
     * @return the reference type identified by id
     */
    public DigitalRunPointsReferenceType getDigitalRunPointsReferenceType(long id)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException;

    /**
     * Gets all the digital run points reference types from persistence. If there is no digital run
     * points reference type in persistence an empty list is returned.
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @return all reference types or empty list if no reference type
     */
    public List<DigitalRunPointsReferenceType> getAllDigitalRunPointsReferenceTypes()
        throws DigitalRunPointsManagerPersistenceException;
}
