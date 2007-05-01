package com.topcoder.service;

/**
 * This is an enumeration of service statuses. It extends from type safe enum
 * class. All kinds of service statuses are pre-defined, and the constructor is
 * made private. There are three kinds of service statuses, PREPARED, STARTED,
 * and FINISHED.
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 */
public class ServiceStatus extends
		com.topcoder.util.collection.typesafeenum.Enum {

	/**
	 * Repsents the service prepared event status. This event after service
	 * enters into to-be-service state.
	 *
	 */
	public static final com.topcoder.service.ServiceStatus PREPARED = new ServiceStatus(
			"PREPARED");

	/**
	 * Repsents the service started event status. This event after service exits
	 * from to-be-service state, and service is started.
	 *
	 */
	public static final com.topcoder.service.ServiceStatus STARTED = new ServiceStatus(
			"STARTED");

	/**
	 * Repsents the service finished event status. This event after service is
	 * finished.
	 *
	 */
	public static final com.topcoder.service.ServiceStatus FINISHED = new ServiceStatus(
			"FINISHED");

	/**
	 * Represents name of service status.
	 * <p>
	 * It will be initialized in ctor, and never changed later. It shouldn't be
	 * null or empty. It can be accessed by getter.
	 * </p>
	 *
	 */
	private final String name;

	/**
	 * Private constructor to only allow pre-defined statuses.
	 * <p>
	 * this.name = name
	 * </p>
	 *
	 *
	 * @param name
	 *            the name of this status
	 * @throws IllegalArgumentException
	 *             if name is null or empty
	 */
	private ServiceStatus(String name) {
		this.name = name;
	}

	/**
	 * Get the name of this status
	 *
	 *
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Override the toString() method to return the name of status.
	 *
	 *
	 * @return this.name
	 */
	public String toString() {
		return this.name;
	}
}
