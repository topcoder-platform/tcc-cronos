/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Test;

import com.topcoder.management.contest.coo.impl.BaseDBConnector;

/**
 * <p>
 * Set of failure tests for BaseDBConnector,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class BaseDBConnectorFailureTests {

    /**
     * Method under test BaseDBConnector.BaseDBConnector(ConfigurationObject).
     * Failure testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testBaseDBConnectorFailureNull() throws Exception {
        try {
            new BaseDBConnector(null) {
            };
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}