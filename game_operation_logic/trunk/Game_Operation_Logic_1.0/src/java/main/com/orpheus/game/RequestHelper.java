/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.web.frontcontroller.HandlerExecutionException;

import javax.servlet.http.HttpServletRequest;


/**
 * Helper class for HttpServletRequest, providing methods to obtain object from request, and validate the object's
 * type.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class RequestHelper {
    /**
     * Private ctor preventing this class from being instantiated.
     */
    private RequestHelper() {
        //does nothing
    }

    /**
     * Obtains a parameter from request, assuming it as string of Long representation.
     *
     * @param request HttpServletRequest from where parameter is obtained
     * @param name name of the parameter
     *
     * @return long value of the parameter
     *
     * @throws HandlerExecutionException if the parameter does not exist or is not of long type.
     */
    public static long getLongParameter(HttpServletRequest request, String name)
        throws HandlerExecutionException {
        String longVal = request.getParameter(name);

        if (longVal == null) {
            throw new HandlerExecutionException("parameter:" + name + " does not exist in request");
        }

        long result = 0;

        try {
            result = Long.parseLong(longVal);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("parameter:" + name + " is expected to be Long, but is:" + longVal);
        }

        return result;
    }
}
