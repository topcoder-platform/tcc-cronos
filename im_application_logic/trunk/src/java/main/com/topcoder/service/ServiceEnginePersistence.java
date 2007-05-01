package com.topcoder.service;

/**
 * This persistence only persists the relationshiops between
 * category/responder/requester. In other words, it persists the STATE of
 * service engine.
 * <p>
 * Category object value is loaded from configuration. Requester/Respnder are
 * stored in ServiceElementPersistence.
 * </p>
 * The implementations of this interface are required to be thread-safe
 *
 */
public interface ServiceEnginePersistence {
	/**
	 * Add requester to the specific category queue. This requester will be
	 * added as the last element.
	 * <p>
	 * Same requester can be added to category for many times.
	 * </p>
	 *
	 *
	 * @param requester
	 *            id of requester
	 * @param category
	 *            id of category.
	 * @throws IllegalException
	 *             if any arg is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public void addRequester(long requester, long category);

	/**
	 * Add requester to the category queue in specified index position.
	 * <p>
	 * Same requester can be added mutiple times.
	 * </p>
	 *
	 *
	 * @param requester
	 *            id of requester
	 * @param category
	 *            id of category
	 * @param index
	 *            the position to add the requester
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws IndexOutOfBoundsException
	 *             if index if < 0 OR > size
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public void addRequester(long requester, long category, int index);

	/**
	 * Remove requester from specified position from given category.
	 *
	 *
	 * @param index
	 *            indicating the position
	 * @param category
	 *            id of category
	 * @return whether any requester is removed
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws IndexOutOfRangeException
	 *             if index is out of range.
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public boolean removeRequester(int index, long category);

	/**
	 * Remove specified requester from category.
	 * <p>
	 * Note, all the occurrences of this requester will be removed.
	 * </p>
	 *
	 *
	 * @param requester
	 *            id of requester
	 * @param category
	 *            id of category
	 * @return whether any requester is removed
	 * @throws IllegalArgumentException
	 *             if any id is null.
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public boolean removeRequester(long requester, long category);

	/**
	 * Get requester id from given category at given position.
	 *
	 *
	 * @param index
	 *            indicating the position
	 * @param category
	 *            id of category
	 * @return id of requester at given place
	 */
	public long getRequester(int index, long category);

	/**
	 * Get the position for given requester from given category.<br />
	 * <p>
	 * If there are multiple occurrences, the first occurrence will be returned.<br />
	 * If there is no occurence of requester, -1 will be returned.
	 * </p>
	 *
	 *
	 * @param requester
	 *            id of requester
	 * @param category
	 *            id of category
	 * @return position of requester in the category queue.
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws PeristenceException
	 *             if any persistence error occurs.
	 */
	public int indexOfRequester(long requester, long category);

	/**
	 * Count the number of requester for the given category.
	 *
	 *
	 * @param category
	 *            id of category
	 * @return the number of requesters belonging to the category
	 * @throws IllegalArgumentException
	 *             if any id is negative.
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public int countRequesters(long category);

	/**
	 * Remove all the requesters belonging to given category.
	 *
	 *
	 * @param category
	 *            id of category
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public void removeRequesters(long category);

	/**
	 * Remove specified requester from all categories.
	 *
	 *
	 * @param requester
	 *            id of requester
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public void removeRequester(long requester);

	/**
	 * Get all the categories, which contain the specified requester.
	 *
	 *
	 * @param requester
	 *            id of requester
	 * @return an array of id of categories, which contains requester
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public long[] getCategoriesForRequester(long requester);

	/**
	 * Get all the requesters belonging to specified category.
	 *
	 *
	 * @param category
	 *            the id of category
	 * @return all the waiting requester in the category
	 * @throws IllegalArgumentException
	 *             if any id is null.
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public long[] getRequesters(long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param responder
	 * @param category
	 */
	public void addResponder(long responder, long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param responder
	 * @param category
	 * @param index
	 */
	public void addResponder(long responder, long category, int index);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param index
	 * @param category
	 * @return
	 */
	public boolean removeResponder(int index, long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param responder
	 * @param category
	 * @return
	 */
	public boolean removeResponder(long responder, long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param index
	 * @param category
	 * @return
	 */
	public long getResponder(int index, long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param requester
	 * @param category
	 * @return
	 */
	public int indexOfResponder(long requester, long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param category
	 * @return
	 */
	public int countResponders(long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param category
	 */
	public void removeResponders(long category);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param responder
	 */
	public void removeResponder(long responder);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param responder
	 * @return
	 */
	public long[] getCategoriesForResponder(long responder);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param category
	 * @return
	 */
	public long[] getResponders(long category);

	/**
	 * Lock specified category with given requester and responder.
	 * <p>
	 * If the category is already locked, a PersistenceException will be raised.
	 * </p>
	 *
	 *
	 * @param category
	 *            id of category
	 * @param requester
	 *            id of requester
	 * @param responder
	 *            id of responder
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public void lockCategory(long category, long requester, long responder);

	/**
	 * Please see namesake method for requester. <br />
	 * They are provide same operations to requester and responder separately.
	 *
	 *
	 * @param category
	 *            id of category
	 * @throws IllegalArgumentException
	 *             if id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public void unlockCategory(long category);

	/**
	 * Determine whether given category is locked.
	 *
	 *
	 * @param category
	 *            id of category
	 * @return whether the given category is locked
	 * @throws IllegalArgumentException
	 *             if id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public boolean isCategoryLocked(long category);

	/**
	 * get the locked requester for specified category. Or -1 if the category is
	 * not locked.
	 *
	 *
	 * @param category
	 *            id of category
	 * @return id of requester or -1 if category is not lockecd.
	 * @throws IllegalArgumentException
	 *             if any id is negative
	 * @throws PersistenceException
	 *             if any persistence error occurs.
	 */
	public long getLockedRequester(long category);

	/**
	 * get the locked responder for specified category. Or -1 if the category is
	 * not locked.
	 *
	 *
	 * @param category
	 *            id of category
	 * @return the locked category, OR -1 if category is not locked.
	 */
	public long getLockedResponder(long category);
}
