/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.service.*;


/**
 * <p>
 * This interface provides a contract for persisting the requester and responder instances. It will
 * store the properties of requester and responder into persistence.
 * </p>
 * <p>
 * It could also generate unique id for different service element. And if some element in
 * persistence containing same properties already exists, the new element will have same id with the
 * one in persistence.
 * </p>
 * <p>
 * <strong>Thread Safety</strong>: Implementations of this interface are required to be thread
 * safe.
 * </p>
 * @author maone, moonli
 * @version 1.0
 */
public class MockServiceElementPersistence implements ServiceElementPersistence {

    /**
     * <p>
     * Creates a service element in persistence.
     * </p>
     * <p>
     * If element with same properties values already exist in persistence, no new entry will be
     * created in persistence and the id of given service element will be updated according to the
     * persistence, otherwise, a unique id will be generated for given service element, and it will
     * also be persisted.
     * </p>
     * @param element
     *            the element to be persisted
     * @throws IllegalArgumentException
     *             if given argument is null
     * @throws PersistenceException
     *             if any error occurs during persistence
     */
    public void createElement(ServiceElement element) throws PersistenceException {
    }

    /**
     * <p>
     * Gets id for the given service element.
     * </p>
     * <p>
     * This method will try to find such element with same property values with given element, and
     * it will return the id of it.
     * </p>
     * @param element
     *            the element for retrieving id
     * @return the id of given element, or -1 if the element is not persisted
     * @throws IllegalArgumentException
     *             if given argument is null
     * @throws PersistenceException
     *             if any other occurs in persistence
     */
    public long getElementId(ServiceElement element) throws PersistenceException {
        return 0;
    }

    /**
     * <p>
     * Deletes element from persistence by id.
     * </p>
     * @param id
     *            id of the element to be deleted
     * @return whether any element is deleted
     * @throws IllegalArgumentException
     *             if id is a negative value
     * @throws PersistenceException
     *             if any error occurs in persistence
     */
    public boolean deleteElement(long id) throws PersistenceException {
        return true;
    }

    /**
     * <p>
     * Deletes element from persistence by element itself.
     * </p>
     * @param element
     *            the element to be deleted
     * @return whether any element is deleted from persistence
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws PersistenceException
     *             if any error occurs in persistence
     */
    public boolean deleteElement(ServiceElement element) throws PersistenceException {
        return true;
    }

    /**
     * <p>
     * Retrieves element for given id.
     * </p>
     * @param id
     *            the id of element
     * @return the element with given id or null if no such element exist
     * @throws IllegalArgumentException
     *             if given id is negative
     * @throws PersistenceException
     *             if any error occurs in persistence
     */
    public ServiceElement retrieveElement(long id) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Retrieves elements for given ids.
     * </p>
     * @param ids
     *            IDs of service elements
     * @return an array of service element with given id, may contain null elements
     * @throws IllegalArgumentException
     *             the array is null, or contains negative value
     * @throws PersistenceException
     *             if any error occurs in persistence
     */
    public ServiceElement[] retrieveElements(long[] ids) throws PersistenceException {
        return new ServiceElement[0];
    }
}
