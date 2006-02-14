/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.stresstests;

import com.topcoder.timetracker.entry.time.stresstests.TaskTypeDAOStressTest;
import com.topcoder.timetracker.entry.time.stresstests.TimeEntryDAOStressTest;
import com.topcoder.timetracker.entry.time.stresstests.TimeStatusDAOStressTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TimeStatusDAOStressTest.class);
        suite.addTestSuite(TimeEntryDAOStressTest.class);
        suite.addTestSuite(TaskTypeDAOStressTest.class);
        return suite;
    }
}
