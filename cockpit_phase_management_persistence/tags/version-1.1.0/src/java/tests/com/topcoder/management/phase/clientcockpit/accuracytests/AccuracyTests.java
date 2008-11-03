/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author LostHunter
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>Returns all the unit tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CockpitPhaseManagerTests.class);
        return suite;
    }
}
