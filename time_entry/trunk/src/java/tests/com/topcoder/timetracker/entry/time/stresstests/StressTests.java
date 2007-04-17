/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregates all Stress test case.
     * 
     * @return all Stress test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(TaskTypeManagerImplStressTests.suite());
        suite.addTest(TimeEntryManagerImplStressTests.suite());
        suite.addTest(TimeStatusManagerImplStressTests.suite());

        return suite;
    }
}
