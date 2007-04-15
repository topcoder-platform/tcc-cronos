/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.topcoder.util.log.Log;

/**
 * <p>
 * This class extends AbstractRequestHandler and is used to test
 * AbstractRequestHandler class.
 * </p>
 *
 * <p>
 * This class is thread safe since it¡¯s immutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractRequestHandlerTester extends AbstractRequestHandler {

    /**
     * Create the instance, empty constructor.
     */
    public AbstractRequestHandlerTester() {
    }

    /**
     * Call the getLog method of the base class.
     *
     * @return the Log instance used for logging
     */
    public Log callGetLog() {
        return getLog();
    }

    /**
     * Empty method.
     *
     * @param xmlRequest
     *            the xml request element
     * @param req
     *            the http request
     * @param res
     *            the http response
     * @throws IllegalArgument
     *             if argument is null
     */
    public void handle(Element xmlRequest, HttpServletRequest req, HttpServletResponse res) {
    }
}
