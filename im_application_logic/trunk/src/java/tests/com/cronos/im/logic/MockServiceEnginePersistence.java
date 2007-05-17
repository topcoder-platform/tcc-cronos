/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.service.*;

/**
 * <p>
 * This persistence only persists the relationships among category, responder and requester. In
 * other words, it persists the STATE of service engine.
 * </p>
 * <p>
 * Category object value is loaded from configuration. Requester and Responder are stored in
 * <code>ServiceElementPersistence</code>.
 * </p>
 * <p>
 * The implementations of this interface are required to be thread-safe.
 * </p>
 * @author maone, moonli
 * @version 1.0
 */
public class MockServiceEnginePersistence implements ServiceEnginePersistence {

    /**
     * <p>
     * Adds requester to the specific category queue.
     * </p>
     * @param requester
     *            id of requester
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if any argument is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void addRequester(long requester, long category) throws PersistenceException {
    }

    /**
     * <p>
     * Adds requester to the category queue in specified index position.
     * </p>
     * <p>
     * Same requester can be added multiple times.
     * </p>
     * @param requester
     *            id of requester
     * @param category
     *            id of category
     * @param index
     *            the position to add the requester
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws IndexOutOfBoundsException
     *             if index is less than zero or greater than size of requesters
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void addRequester(long requester, long category, int index) throws PersistenceException {
    }

    /**
     * <p>
     * Removes requester from specified position from given category.
     * </p>
     * @param index
     *            indicating the position
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws IndexOutOfBoundsException
     *             if index is out of range
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void removeRequester(int index, long category) throws PersistenceException {
    }

    /**
     * <p>
     * Removes specified requester from category.
     * </p>
     * <p>
     * <strong>Note:</strong> all the occurrences of this requester will be removed.
     * </p>
     * @param requester
     *            id of requester
     * @param category
     *            id of category
     * @return whether any requester is removed
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public boolean removeRequester(long requester, long category) throws PersistenceException {
        return true;
    }

    /**
     * <p>
     * Gets requester id from given category at given position.
     * </p>
     * @param index
     *            position of the requester
     * @param category
     *            id of category
     * @return id of requester at given place
     * @throws IndexOutOfBoundsException
     *             if index is out of range
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long getRequester(int index, long category) throws PersistenceException {
        return 1;
    }

    /**
     * <p>
     * Gets the position for given requester from given category.
     * </p>
     * @param requester
     *            id of requester
     * @param category
     *            id of category
     * @return position of requester in the category queue
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public int indexOfRequester(long requester, long category) throws PersistenceException {
        return 1;
    }

    /**
     * <p>
     * Counts the number of requesters for the given category.
     * </p>
     * @param category
     *            id of category
     * @return the number of requesters belonging to the category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public int countRequesters(long category) throws PersistenceException {
        return 1;
    }

    /**
     * <p>
     * Removes all the requesters belonging to given category.
     * </p>
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void removeRequesters(long category) throws PersistenceException {
    }

    /**
     * <p>
     * Removes specified requester from all categories.
     * </p>
     * @param requester
     *            id of requester
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void removeRequester(long requester) throws PersistenceException {
    }

    /**
     * <p>
     * Gets all the categories, which contain the specified requester.
     * </p>
     * @param requester
     *            id of requester
     * @return an array of id of categories, which contains specified requester
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long[] getCategoriesForRequester(long requester) throws PersistenceException {
        return new long[0];
    }

    /**
     * <p>
     * Gets all the requesters belonging to specified category.
     * </p>
     * @param category
     *            the id of category
     * @return all the waiting requesters in the category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long[] getRequesters(long category) throws PersistenceException {
        return new long[0];
    }

    /**
     * <p>
     * Adds responder to the specific category queue.
     * </p>
     * @param responder
     *            id of responder
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if any argument is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void addResponder(long responder, long category) throws PersistenceException {
    }

    /**
     * <p>
     * Adds responder to the category queue in specified index position.
     * </p>
     * <p>
     * Same responder can be added multiple times.
     * </p>
     * @param responder
     *            id of responder
     * @param category
     *            id of category
     * @param index
     *            the position to add the responder
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws IndexOutOfBoundsException
     *             if index is less than zero or greater than size of responders
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void addResponder(long responder, long category, int index) throws PersistenceException {
    }

    /**
     * <p>
     * Removes responder from specified position from given category.
     * </p>
     * @param index
     *            indicating the position
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws IndexOutOfBoundsException
     *             if index is out of range
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void removeResponder(int index, long category) throws PersistenceException {
    }

    /**
     * <p>
     * Removes specified responder from category.
     * </p>
     * <p>
     * <strong>Note:</strong> all the occurrences of this responder will be removed.
     * </p>
     * @param responder
     *            id of responder
     * @param category
     *            id of category
     * @return whether any responder is removed
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public boolean removeResponder(long responder, long category) throws PersistenceException {
        return true;
    }

    /**
     * <p>
     * Gets responder id from given category at given position.
     * </p>
     * @param index
     *            position of the responder
     * @param category
     *            id of category
     * @return id of responder at given place
     * @throws IndexOutOfBoundsException
     *             if index is out of range
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long getResponder(int index, long category) throws PersistenceException {
        return 1;
    }

    /**
     * <p>
     * Gets the position for given responder from given category.
     * </p>
     * @param responder
     *            id of responder
     * @param category
     *            id of category
     * @return position of responder in the category queue
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public int indexOfResponder(long responder, long category) throws PersistenceException {
        return 1;
    }

    /**
     * <p>
     * Counts the number of responders for the given category.
     * </p>
     * @param category
     *            id of category
     * @return the number of responders belonging to the category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public int countResponders(long category) throws PersistenceException {
        return 0;
    }

    /**
     * <p>
     * Removes all the responders belonging to given category.
     * </p>
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void removeResponders(long category) throws PersistenceException {
    }

    /**
     * <p>
     * Removes specified responder from all categories.
     * </p>
     * @param responder
     *            id of responder
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void removeResponder(long responder) throws PersistenceException {
    }

    /**
     * <p>
     * Gets all the categories, which contain the specified responder.
     * </p>
     * @param responder
     *            id of responder
     * @return an array of id of categories, which contains specified responder
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long[] getCategoriesForResponder(long responder) throws PersistenceException {
        return new long[0];
    }

    /**
     * <p>
     * Gets all the responders belonging to specified category.
     * </p>
     * @param category
     *            the id of category
     * @return all the waiting responders in the category
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long[] getResponders(long category) throws PersistenceException {
        return new long[0];
    }

    /**
     * <p>
     * Locks specified category with given requester and responder.
     * </p>
     * <p>
     * If the category is already locked, a <code>PersistenceException</code> will be raised.
     * </p>
     * @param category
     *            id of category
     * @param requester
     *            id of requester
     * @param responder
     *            id of responder
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public void lockCategory(long category, long requester, long responder)
        throws PersistenceException {
        }

    /**
     * <p>
     * Unlocks category by id.
     * </p>
     * @param category
     *            id of category
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws PersistenceException
     *             if any persistence error occurs.
     */
    public void unlockCategory(long category) throws PersistenceException {
    }

    /**
     * <p>
     * Determines whether given category is locked.
     * </p>
     * @param category
     *            id of category
     * @return whether the given category is locked
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public boolean isCategoryLocked(long category) throws PersistenceException {
        return true;
    }

    /**
     * <p>
     * Gets the locked requester for specified category. Or -1 if the category is not locked.
     * </p>
     * @param category
     *            id of category
     * @return id of requester or -1 if category is not locked
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long getLockedRequester(long category) throws PersistenceException {
        return 0;
    }

    /**
     * <p>
     * Gets the locked responder for specified category.
     * </p>
     * @param category
     *            id of category
     * @return the locked responder or -1 if category is not locked
     * @throws IllegalArgumentException
     *             if any id is negative
     * @throws PersistenceException
     *             if any persistence error occurs
     */
    public long getLockedResponder(long category) throws PersistenceException {
        return 1;
    }
}
