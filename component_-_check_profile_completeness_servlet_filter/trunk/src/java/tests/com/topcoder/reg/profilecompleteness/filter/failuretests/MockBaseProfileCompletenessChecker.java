/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.BaseProfileCompletenessChecker;

/**
 * <p>Creates new instance of {@link MockBaseProfileCompletenessChecker} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class MockBaseProfileCompletenessChecker extends BaseProfileCompletenessChecker {

    /**
     * <p>Creates new instance of {@link MockBaseProfileCompletenessChecker} class.</p>
     */
    public MockBaseProfileCompletenessChecker() {
        // empty constructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkInitialization() {
        super.checkInitialization();
    }
}
