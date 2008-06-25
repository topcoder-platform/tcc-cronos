/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Failure tests for all public methods.
     * </p>
     *
     * @return Failure test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(OpenRegistrationValidatorFailureTest.class);
        return suite;
    }
}
