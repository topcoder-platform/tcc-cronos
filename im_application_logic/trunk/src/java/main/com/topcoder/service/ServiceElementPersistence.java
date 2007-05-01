package com.topcoder.service;

/**
 * It persists the requester/respnder instances. It will store the properties of
 * requester/responder.
 * <p>
 * It could also generate unique id for different service element. And if some
 * element in persistence containing same properties already exists, the new
 * element will have same id with the one in persistence.
 * </p>
 * <b>Thread Safety</b>: Implementations of this interface are required to be
 * thread safe.
 *
 */
public interface ServiceElementPersistence {
	/**
	 * Create a service element in persistence.
	 * <p>
	 * If element with same properties values already exist in persistence, no
	 * new entry will be created in persistence and the id of given service
	 * element will be updated according to the persistence
	 * </p>
	 * <p>
	 * Or else, a unique id will be generated for given service element, and it
	 * will also be persisted.
	 * </p>
	 *
	 *
	 * @param element
	 *            the element to be persisted.
	 * @throws IllegalArgumentException
	 *             if given arg is null
	 * @throws PersistenceException
	 *             if any error occurs during persistence.
	 */
	public void createElement(com.topcoder.service.ServiceElement element);

	/**
	 * Get id for the given service element.
	 * <p>
	 * This method will try to find such element with same property values with
	 * given element, and it will return the id of it.<br />
	 * If no such element exists in persistence, -1 should be returned.
	 * </p>
	 * Note, id of given element will not be updated.
	 *
	 *
	 * @param element
	 *            the element to retrieve id for
	 * @return the id of given element, or -1 if the element is not persisted
	 * @throws IllegalArgumentException
	 *             if given arg is null.
	 * @throws PersistenceException
	 *             if any other occurs in persistence.
	 */
	public long getElementId(com.topcoder.service.ServiceElement element);

	/**
	 * Delete element from persistence.<br/> The element is given as ID.
	 *
	 *
	 * @param id
	 *            id of the element to be deleted
	 * @return whether any element is deleted.
	 * @throws IllegalArgumentException
	 *             if id is a negative value
	 * @throws PersistenceException
	 *             if any error occurs in persistence.
	 */
	public boolean deleteElement(long id);

	/**
	 * Delete element from persistence.<br/> The element is given as a service
	 * element.
	 * <p>
	 * NOTE, the element is to be matched with all of its property values, Id
	 * will be ignored in this method.
	 * </p>
	 *
	 *
	 * @param element
	 *            the element to be deleted.
	 * @return whether any element is deleted from persistence.
	 * @throws IllegalArgumentException
	 *             if any arg is null
	 * @throws PersistenceException
	 *             if any error occurs in persistence.
	 */
	public boolean deleteElement(com.topcoder.service.ServiceElement element);

	/**
	 * Retrieve element for given id.<br/> Or null will be returned if no such
	 * element with given id.
	 *
	 *
	 * @param id
	 *            the id of element
	 * @return the element with given id.
	 * @throws IllegalArgumentException
	 *             if given id is negative.
	 * @throws PersistenceException
	 *             if any error occurs in persistence.
	 */
	public com.topcoder.service.ServiceElement retrieveElement(long id);

	/**
	 * Batch version of retrieveElement(id:long) method.
	 * <p>
	 * An array of service element will be returned. But the returned array may
	 * contain null element, which means there is no element for the
	 * corresponding id in ids array.
	 * </p>
	 *
	 *
	 * @param ids
	 *            IDs of service element
	 * @return an array of service element with given id.
	 * @throws if
	 *             the array is null, or contains negative value.
	 * @throws PersistenceException
	 *             if any error occurs in persistence.
	 */
	public com.topcoder.service.ServiceElement[] retrieveElements(long[] ids);
}
