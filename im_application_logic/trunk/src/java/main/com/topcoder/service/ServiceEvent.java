package com.topcoder.service;

import java.util.EventObject;

/**
 * This class represents a service related event occurred in a ServiceEngine. It
 * contains the service requester, service responder, service category, and
 * service status. There are three kinds of service statuses defined in this
 * class: PREPARED, STARTED, and FINISHED.
 * <p>
 * This class is immutalbe and thread-safe.
 * </p>
 *
 */
public class ServiceEvent extends EventObject {

	/**
	 * Represents the requester in this service event.
	 * <p>
	 * Initialized in ctor, and never changed later. Not null.
	 * </p>
	 *
	 */
	private final com.topcoder.service.ServiceElement requester;

	/**
	 * Represents the responder in this service event.
	 * <p>
	 * Initialized in ctor, and never changed later. Not null.
	 * </p>
	 *
	 */
	private final com.topcoder.service.ServiceElement responder;

	/**
	 * Represents the category of this service event.
	 * <p>
	 * Initialized in ctor, and never changed later. Not null.
	 * </p>
	 *
	 */
	private final com.topcoder.service.Category category;

	/**
	 * Represents the status in this service event.
	 * <p>
	 * Initialized in ctor, and never changed later. The value should be one of
	 * PREPARED, STARTED, and FINISHED.
	 * </p>
	 *
	 */
	private final com.topcoder.service.ServiceStatus status;

	/**
	 * Constructor. Simply assign the args to corresponding fields.
	 *
	 *
	 * @param source
	 *            a ServiceEngine triggering this event
	 * @param requester
	 *            the service requester
	 * @param responder
	 *            the service responder
	 * @param category
	 *            service category
	 * @param status
	 *            service status
	 * @throws IllegalArgumentException
	 *             if any arg is null, or status value is not one of
	 *             PREPARED/STARTED/FINISHED.
	 */
	public ServiceEvent(com.topcoder.service.ServiceEngine source,
			com.topcoder.service.ServiceElement requester,
			com.topcoder.service.ServiceElement responder,
			com.topcoder.service.Category category,
			com.topcoder.service.ServiceStatus status) {
		super(source);

		this.requester = requester;
		this.responder = responder;
		this.category = category;
		this.status = status;
	}

	/**
	 * Getter fo category field.
	 *
	 *
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Getter of requester field.
	 *
	 *
	 * @return the requester
	 */
	public com.topcoder.service.ServiceElement getRequester() {
		return requester;
	}

	/**
	 * Getter fo responder field.
	 *
	 *
	 * @return the responder
	 */
	public com.topcoder.service.ServiceElement getResponder() {
		return responder;
	}

	/**
	 * Getter of status field.
	 *
	 *
	 * @return the status
	 */
	public com.topcoder.service.ServiceStatus getStatus() {
		return status;
	}
}
