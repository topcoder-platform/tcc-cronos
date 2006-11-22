/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(HandlersStressTest.class);
        return suite;
    }
}
