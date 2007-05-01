package com.topcoder.service;

/**
 * This interface is used for link two service elements, requester and
 * responder. It will be used by ServiceEngine to link available responder and
 * requester. When specific requester and responder are linked, they may become
 * unavailable for its category, so it may be removed the category queue.
 * <p>
 * For example, a typical service linker will remove requester and responder
 * from the requesters or responders queue. And some linkes will add the
 * reponder back to the end of responders queue.
 * </p>
 * <p>
 * Implementations of this interface are required to be thread safe. Since it
 * will be used in a multi-threads environment.
 * </p>
 *
 */
public interface ServiceLinker {
	/**
	 * Link two reponder/requester belonging to the given category.
	 * <p>
	 * During linking, the requester and responder may be marked as busy, and
	 * removed from their category queue. The functionality of this method
	 * depends on the concrete implementation. For example, in
	 * RoundRobinServiceLinker, requester will be removed from its category
	 * queue, and responder will be removed from all the category queues.
	 * </p>
	 *
	 *
	 * @param serviceEvent
	 * @throws IllegalArgumentException
	 *             if any arg is null.
	 * @throws ServiceLinkException
	 *             if any other error occurs.
	 */
	public void linkService(com.topcoder.service.ServiceEvent serviceEvent);
}
