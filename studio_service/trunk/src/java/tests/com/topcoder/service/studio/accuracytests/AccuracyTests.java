/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Gathers all accuracy tests together and return.
     *
     * @return all tests in one suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(StudioServiceBeanAccTests.class);
        suite.addTestSuite(StudioServiceBeanTest.class);
        return suite;
    }
}
