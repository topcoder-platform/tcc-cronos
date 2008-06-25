/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Accuracy tests for all public methods.
     * </p>
     *
     * @return Accuracy test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(OpenRegistrationValidatorAccuracyTest.class);
        return suite;
    }
}
