package com.topcoder.service;

import java.util.EventListener;

/**
 * This listener interface is used for receiving service related events.
 * <p>
 * The class that is interested in processing a service event either implements
 * this interface (and all the methods it contains) or extends the abstract
 * ServiceAdapter class (overriding only the methods of interest).
 * </p>
 * <p>
 * The implementations of this interface are required to be thread-safe. Because
 * the event dispather model in ServiceEngine is multi-threads.
 * </p>
 *
 */
public interface ServiceListener extends EventListener {
	/**
	 * Invoked when a service is prepared. (Entering the to-be-serviced state).
	 *
	 *
	 * @param e
	 *            the service prepared event
	 */
	public void servicePrepared(ServiceEvent e);

	/**
	 * Invoked when a service is started. (Exiting the to-be-serviced state,
	 * entering working state).
	 *
	 *
	 * @param e
	 *            the service started event
	 */
	public void serviceStarted(ServiceEvent e);

	/**
	 * Invoked when a service is finished. (Exiting working state).
	 *
	 *
	 * @param e
	 *            the service finished event.
	 */
	public void serviceFinished(ServiceEvent e);
}
