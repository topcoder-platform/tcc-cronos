package com.topcoder.service;

/**
 * This interface is used for selecting next service elements, requester and
 * responder. It will be used by ServiceEngine to select available responder and
 * requester from queue.
 * <p>
 * For example, a typical service element selector will select the
 * requester/responder which comes first.
 * </p>
 * <p>
 * Implementations of this interface are required to be thread safe. Since it
 * will be used in a multi-threads environment.
 * </p>
 *
 */
public interface ServiceElementSelector {
	/**
	 * Select next availabe requester to be served in specific category.
	 *
	 *
	 * @param serviceEngine
	 *            the service engine to select element for
	 * @param category
	 *            the service category
	 * @return the next avaiable requester, or null if none.
	 * @throws IllegalArgumentException
	 *             if any arg is null
	 * @throws ElementSelectException
	 *             if any other error occurs
	 */
	public com.topcoder.service.ServiceElement nextRequester(
			com.topcoder.service.ServiceEngine serviceEngine,
			com.topcoder.service.Category category);

	/**
	 * Select next availabe responder to serve in specific category.
	 *
	 *
	 * @param serviceEngine
	 *            the service engine to select element for
	 * @param category
	 *            the service category
	 * @return the next avaiable responder, or null if none.
	 * @throws IllegalArgumentException
	 *             if any arg is null
	 * @throws ElementSelectException
	 *             if any other error occurs
	 */
	public com.topcoder.service.ServiceElement nextResponder(
			com.topcoder.service.ServiceEngine serviceEngine,
			com.topcoder.service.Category category);
}
