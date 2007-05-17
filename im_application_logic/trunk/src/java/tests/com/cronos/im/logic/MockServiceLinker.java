/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.service.*;

/**
 * <p>
 * This interface is used for linking two service elements, requester and responder. It will be used
 * by <code>ServiceEngine</code> to link available responder and requester. When specific
 * requester and responder are linked, they may become unavailable for its category, so it may be
 * removed the category queue.
 * </p>
 * <p>
 * For example, a typical service linker will remove requester and responder from the requesters or
 * responders queue. And some linkers will add the responder back to the end of responders queue.
 * </p>
 * <p>
 * Implementations of this interface are required to be thread safe. Since it will be used in a
 * multi-threads environment.
 * </p>
 * @author maone, moonli
 * @version 1.0
 */
public class MockServiceLinker implements ServiceLinker {

    /**
     * <p>
     * Links the responder and requester belonging to the given category.
     * </p>
     * <p>
     * During linking, the requester and responder may be marked as busy, and removed from their
     * category queue. The functionality of this method depends on the concrete implementation. For
     * example, in <code>RoundRobinServiceLinker</code>, requester will be removed from its
     * category queue, and responder will be removed from all the category queues.
     * </p>
     * @param serviceEvent
     *            the service event
     * @throws IllegalArgumentException
     *             if any argument is null
     * @throws ServiceLinkException
     *             if any other error occurs
     */
    public void linkService(ServiceEvent serviceEvent) throws ServiceLinkException {
    }
}
