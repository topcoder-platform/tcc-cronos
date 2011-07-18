/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * All Stress test suite.
     * </p>
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ActivateAccountActionStressTest.class);
        suite.addTestSuite(CreateAccountActionStressTest.class);
        suite.addTestSuite(PreCreateAccountActionStressTest.class);
        suite.addTestSuite(ResendAccountActivationEmailActionStressTest.class);
        suite.addTestSuite(SelectRegistrationPreferenceActionStressTest.class);
        suite.addTestSuite(ViewRegistrationPreferenceActionStressTest.class);
        return suite;
    }
}
