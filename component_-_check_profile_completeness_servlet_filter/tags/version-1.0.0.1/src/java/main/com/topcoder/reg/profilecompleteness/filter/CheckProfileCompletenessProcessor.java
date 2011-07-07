/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface defines the contract for checking the completeness of the profile. It is inserted
 * by spring to the CheckProfileCompletenessFilter.
 * <p>
 * <b>Thread-safety</b>
 * <p>
 * The implementations are required to be thread-safe.
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public interface CheckProfileCompletenessProcessor {

    /**
     * This method is responsible for processing the profile completeness check.
     * <p>
     * It processes the check only if the input request uri matches the configurable pattern. Then
     * this implementation retrieve the logged user and checks, whether he has provided enough
     * profile fields for performing some action. This check is done by configurable
     * ProfileCompletenessChecker.
     * <p>
     * If these fields are not filled, the user will be redirected to page to complete data and
     * request uri will be stored in the session under the key requestURISessionKey.
     *
     * @param response
     *            http servlet request
     * @param chain
     *            http servlet response
     * @param request
     *            filter chain
     * @throws IllegalArgumentException
     *             if request is null, response is null, chain is null
     * @throws CheckProfileCompletenessProcessorException
     *             if any exception occurred while processing the data
     */
    void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws CheckProfileCompletenessProcessorException;

}
