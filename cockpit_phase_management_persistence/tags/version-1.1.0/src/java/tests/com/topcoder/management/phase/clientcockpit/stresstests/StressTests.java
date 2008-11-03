/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * This test case aggregates all stress test cases.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregates all stress test cases and returns the test suite for them.
     *
     * @return the test suite of all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Aggregates all stress test cases
        suite.addTestSuite(CockpitPhaseManagerStressTest.class);
        return suite;
    }
}
