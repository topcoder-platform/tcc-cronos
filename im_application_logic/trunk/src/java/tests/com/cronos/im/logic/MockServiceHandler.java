/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.service.*;

/**
 * <p>
 * This interface is used for the actual service logic. It is plugged in <code>ServiceEngine</code>
 * to serve all categories of services.
 * </p>
 * <p>
 * There are two phases of service logic should be provided by implementations. <b>to-be-serviced</b>
 * logic happens when both requester and responder is available for specific category. And <b>serviced</b>
 * logic deals with the actual service logic.
 * </p>
 * <p>
 * Implementations of this interface are required to be thread safe. Since it will be used in a
 * multi-threads environment.
 * </p>
 * @author maone, moonli
 * @version 1.0
 */
public class MockServiceHandler implements ServiceHandler {

    /**
     * <p>
     * This method is used when both requester and responder becomes available for the category. It
     * implements the logic in <b>to-be-serviced</b> state. Logic contained here may be some
     * initialization process, or handshaking process, or etc.
     * </p>
     * @param serviceEvent
     *            the service event
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws ServiceHandleException
     *             if any other service related error occurs
     */
    public void onToBeServiced(ServiceEvent serviceEvent) throws ServiceHandleException {
    }

    /**
     * <p>
     * This method is used when the service is started. It implements the logic in <b>serviced</b>
     * state.
     * </p>
     * @param serviceEvent
     *            the service event
     * @throws IllegalArgumentException
     *             if any argument is null.
     * @throws ServiceHandleException
     *             if any service related error occurs
     */
    public void onServiced(ServiceEvent serviceEvent) throws ServiceHandleException {
    }
}
