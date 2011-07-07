/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessor;
import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessorException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>This is mock implementation of {@link CheckProfileCompletenessProcessor} class, used for testing.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class MockCheckProfileCompletenessProcessor implements CheckProfileCompletenessProcessor {

    /**
     * {@inheritDoc}
     */
    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws CheckProfileCompletenessProcessorException {

        throw new CheckProfileCompletenessProcessorException("Exception throw by mock.");
    }
}
