/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests;

import com.topcoder.registration.service.failuretests.impl.RegistrationInfoImplFailureTest;
import com.topcoder.registration.service.failuretests.impl.RegistrationPositionImplFailureTest;
import com.topcoder.registration.service.failuretests.impl.RegistrationResultImplFailureTest;
import com.topcoder.registration.service.failuretests.impl.RegistrationServicesImplFailureTest;
import com.topcoder.registration.service.failuretests.impl.RemovalResultImplFailureTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * This test case aggregates all Stress test cases.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add stress tests
        suite.addTest(RegistrationInfoImplFailureTest.suite());
        suite.addTest(RegistrationPositionImplFailureTest.suite());
        suite.addTest(RegistrationResultImplFailureTest.suite());
        suite.addTest(RegistrationServicesImplFailureTest.suite());
        suite.addTest(RemovalResultImplFailureTest.suite());

        return suite;
    }
}
