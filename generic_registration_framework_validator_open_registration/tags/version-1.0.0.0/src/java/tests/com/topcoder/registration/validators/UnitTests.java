/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Unit tests for all public methods.
     * </p>
     *
     * @return Unit test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(OpenRegistrationValidatorUnitTest.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
