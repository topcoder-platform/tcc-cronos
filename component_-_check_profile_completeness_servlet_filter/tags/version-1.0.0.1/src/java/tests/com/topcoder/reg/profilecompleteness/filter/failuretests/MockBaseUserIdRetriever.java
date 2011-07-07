/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.BaseUserIdRetriever;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Mock implementation of {@link BaseUserIdRetriever} used for testing.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class MockBaseUserIdRetriever extends BaseUserIdRetriever {

    /**
     * <p>Creates new instance of {@link MockBaseUserIdRetriever} class.</p>
     */
    public MockBaseUserIdRetriever() {
        // empty constructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkInitialization() {
        super.checkInitialization();
    }

    /**
     * {@inheritDoc}
     */
    public Long getUserId(HttpServletRequest request) {
        return null;
    }
}
