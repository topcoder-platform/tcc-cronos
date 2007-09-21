/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.cronos.im.ajax.RequestHandler;

/**
 * <p>
 * AbstractRequestHandler provides an additional method to return the log for all the subclass that need to
 * log something.
 * </p>
 *
 * <p>
 * This class is thread safe since it's immutable.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractRequestHandler implements RequestHandler {

    /**
     * Empty constructor.
     *
     */
    protected AbstractRequestHandler() {
    }

    /**
     * Get the log for the subclass to use.
     *
     *
     * @return the Log instance used for logging
     */
    protected Log getLog() {
        return LogFactory.getLog(this.getClass().getName());
    }

}
