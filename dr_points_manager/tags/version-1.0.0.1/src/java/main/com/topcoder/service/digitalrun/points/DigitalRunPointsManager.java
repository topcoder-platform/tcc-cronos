/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;

/**
 * <p>
 * This interface defines the contract for classes that will manage digital run points entities into
 * persistence. The implementing classes will provide the following functionality: <br>
 * create a digital run points/ digital run points type /digital run points status/digital run
 * points operation/digital run points reference type into persistence <br>
 * update a digital run points/digital run points type/digital run points status/digital run points
 * operation/digital run points reference type into persistence <br>
 * remove a digital run points/digital run points type/digital run points status/digital run points
 * operation/digital run points reference type identified by its id from persistence <br>
 * get a digital run points/digital run points type/digital run points status/digital run points
 * operation/digital run points reference type identified by its id from persistence <br>
 * search the digital run points entities in persistence that match a given filter <br>
 * get all digital run points type/digital run points status/digital run points operation/digital
 * run points reference type from persistence <br>
 * </p>
 * <p>
 * Thread Safety: Implementations are required to be thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunPointsManager {
    /**
     * Creates a new DigitalRunPoints entity into persistence. Returns the DigitalRunPoints instance
     * with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param points
     *            the DigitalRunPoints instance to be created into persistence
     * @return the DigitalRunPoints instance with id generated
     */
    public DigitalRunPoints createDigitalRunPoints(DigitalRunPoints points)
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Updates the given DigitalRunPoints instance into persistence.
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws EntityNotFoundException
     *             if a digital run points entity with DigitalRunPoints.id does not exist in the
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param points
     *            the digital run points to be updated into persistence
     */
    public void updateDigitalRunPoints(DigitalRunPoints points) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points entityt with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsId
     *            the digital run points entity id to be removed
     */
    public void removeDigitalRunPoints(long pointsId) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Gets the digital run points entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param id
     *            the id of the entity
     * @return the digital run points entity identified by id
     */
    public DigitalRunPoints getDigitalRunPoints(long id) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Searches the digital run points entities that match the given filter. If there is no such
     * entity that matches the given filter an empty list is returned.
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param filter
     *            the filter used for searching
     * @return a list containing the matching digital run points entities or empty list if no such
     *         entity
     */
    public List<DigitalRunPoints> searchDigitalRunPoints(Filter filter)
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Creates a new DigitalRunPointsType entity into persistence. Returns the DigitalRunPointsType
     * instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsType
     *            the DigitalRunPointsType instance to be created into persistence
     * @return the DigitalRunPointsType instance with id generated
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
     *            the digital run points type entity to be updated into persistence
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
     *            the id of digital run points type entity to be removed
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
     * @return a list containing all types or empty list if no type present in persistence
     */
    public List<DigitalRunPointsType> getAllDigitalRunPointsTypes()
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Creates a new DigitalRunPointsStatus entity into persistence. Returns the
     * DigitalRunPointsStatus instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsStatus
     *            the DigitalRunPointsStatus instance to be created into persistence
     * @return the DigitalRunPointsStatus instance with id generated
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
     *            the digital run points status entity to be updated into persistence
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
     *            the digital run points status id to be removed
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
     * @return a list containing all statuses or empty list if no status
     */
    public List<DigitalRunPointsStatus> getAllDigitalRunPointsStatuses()
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Creates a new DigitalRunPointsOperation entity into persistence. Returns the
     * DigitalRunPointsOperation instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperation
     *            the DigitalRunPointsOperation instance to be created into persistence
     * @return the DigitalRunPointsOperation instance with id generated
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
     *            the digital run points operation entity to be updated into persistence
     */
    public void updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)
        throws EntityNotFoundException, DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points operation entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points operation entityt with the given id in
     *             persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsOperationId
     *            the operation id to be removed
     * @param Return
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
     * @return a list containing all the operations or empty list if no operation
     */
    public List<DigitalRunPointsOperation> getAllDigitalRunPointsOperations()
        throws DigitalRunPointsManagerPersistenceException;

    /**
     * Creates a new DigitalRunPointsReferenceType entity into persistence. Returns the
     * DigitalRunPointsReference instance with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsReferenceType
     *            the DigitalRunPointsReferenceType instance to be created into persistence
     * @return the DigitalRunPointsReferenceType instance with id generated
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
     *            the digital run points reference type to be updated into persistence
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
     *            the reference type id to be removed
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
     * @return a list containing all reference types or empty list if no reference type
     */
    public List<DigitalRunPointsReferenceType> getAllDigitalRunPointsReferenceTypes()
        throws DigitalRunPointsManagerPersistenceException;
}
