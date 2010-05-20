/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author yuanyeyuanye, DixonD
 * @version 1.1
 */
public class StressTests extends TestCase {
    /**
     * Returns the test suite of this class.
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(CooGenerateStressTests.suite());
        return suite;
    }
}
