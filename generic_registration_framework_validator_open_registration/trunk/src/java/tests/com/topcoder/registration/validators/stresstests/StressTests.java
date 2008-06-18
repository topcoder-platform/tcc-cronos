/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Stress tests for all public methods.
     * </p>
     *
     * @return Stress test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(OpenRegistrationValidatorStressTest.class);
        return suite;
    }
}
