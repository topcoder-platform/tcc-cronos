/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Stress test suite.
 *
 * @author extra
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * All stress test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AccuracyCalculatorImplStressTests.class);
        suite.addTestSuite(CoverageCalculatorImplStressTests.class);
        suite.addTestSuite(TimelineReliabilityCalculatorImplStressTests.class);

        return suite;

    }
}
