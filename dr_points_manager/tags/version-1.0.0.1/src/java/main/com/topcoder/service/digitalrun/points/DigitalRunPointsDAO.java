/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;

/**
 * <p>
 * This interface defines the contract for classes that will manage digital run points entities into
 * persistence. The implementing classes will provide the following functionality: <br>
 * create a digital run points entity into persistence <br>
 * update a digital run points entity into persistence <br>
 * remove a digital run points entity identified by its id from persistence <br>
 * get a digital run points entity identified by its id from persistence <br>
 * search the digital run points entities in persistence that match a given filter <br>
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread safe.
 * </p>
 * @author  DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunPointsDAO {
    /**
     * Creates a new DigitalRunPoints entity into persistence. Returns the DigitalRunPoints instance
     * with id generated.
     * @throws IllegalArgumentException
     *             if argument is null or if its id >0
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param points
     *            the entity to be created
     * @return the entity with id set
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
     *            the entity to be updated
     */
    public void updateDigitalRunPoints(DigitalRunPoints points) throws EntityNotFoundException,
            DigitalRunPointsManagerPersistenceException;

    /**
     * Removes the digital run points entity identified by the given id from persistence.
     * @throws IllegalArgumentException
     *             if argument<0
     * @throws EntityNotFoundException
     *             if there is no digital run points entity with the given id in persistence
     * @throws DigitalRunPointsManagerPersistenceException
     *             if any errors occur when accessing the persistent storage
     * @param pointsId
     *            the id
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
     *            the id
     * @return the entity identified by the id
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
     *            the filter
     * @return the list of matching entities or an empty list if no matching entity is found
     */
    public List<DigitalRunPoints> searchDigitalRunPoints(Filter filter)
        throws DigitalRunPointsManagerPersistenceException;
}
