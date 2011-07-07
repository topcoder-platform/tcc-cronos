/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface defines the contract for retrieving the user id from the request. The concrete
 * details of data retrieval are up to the implementation.
 * <p>
 * <b>Thread-safety</b> The implementations are required to be thread-safe.
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public interface UserIdRetriever {

    /**
     * This method retrieves the user id from the request.
     *
     * @param request
     *            http servlet request
     * @return The user id, stored in the request, null if the user is not logged or any exception
     *         occurred.
     * @throws IllegalArgumentException
     *             if request is null
     */
    Long getUserId(HttpServletRequest request);

}
