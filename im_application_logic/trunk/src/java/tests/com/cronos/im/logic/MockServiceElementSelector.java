/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.service.*;


/**
 * <p>
 * This interface is used for selecting next service elements, requester and responder. It will be
 * used by <code>ServiceEngine</code> to select available responder and requester from queues.
 * </p>
 * <p>
 * For example, a typical service element selector will select the requester/responder which comes
 * first.
 * </p>
 * <p>
 * Implementations of this interface are required to be thread safe. Since it will be used in a
 * multi-threads environment.
 * </p>
 * @author maone, moonli
 * @version 1.0
 */
public class MockServiceElementSelector implements ServiceElementSelector {

    /**
     * <p>
     * Selects next available requester to be served in specific category.
     * </p>
     * @param serviceEngine
     *            the service engine to select element for
     * @param category
     *            the service category
     * @return the next available requester, or null if none exists
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws ElementSelectException
     *             if any other error occurs
     */
    public ServiceElement nextRequester(ServiceEngine serviceEngine, Category category)
        throws ElementSelectException {
        return null;
    }

    /**
     * <p>
     * Selects next available responder to serve in specific category.
     * </p>
     * @param serviceEngine
     *            the service engine to select element for
     * @param category
     *            the service category
     * @return the next available responder, or null if none exist
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws ElementSelectException
     *             if any other error occurs
     */
    public ServiceElement nextResponder(ServiceEngine serviceEngine, Category category)
        throws ElementSelectException {
        return null;
    }
}
