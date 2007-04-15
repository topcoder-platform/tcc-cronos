/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Element;

/**
 * <p>
 * RequestHandler defines the contract of handling the request based on some logic. Please be noted that, the
 * implementation should not throw any exception. The exception should be caught, and the exception message
 * should be contained as the part of the response.
 * </p>
 *
 * <p>
 * The implementation is required to be thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public interface RequestHandler {
    /**
     * Handle the request and response.
     *
     *
     * @param xmlRequest
     *            the xml request element
     * @param req
     *            the http request
     * @param res
     *            the http response
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws IOException
     *             if I/O error occurs
     */
    public void handle(Element xmlRequest, HttpServletRequest req, HttpServletResponse res) throws IOException;

}
