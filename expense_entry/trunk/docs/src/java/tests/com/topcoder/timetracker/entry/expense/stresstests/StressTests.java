/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TopCoder
 * @version 3.2
 */
public class StressTests extends TestCase {
    /**
     * Aggregate all stress test cases.
     *
     * @return all stress test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestInformixExpenseTypeDAOStress.class);
        suite.addTestSuite(TestInformixExpenseStatusDAOStress.class);

        suite.addTestSuite(TestInformixExpenseEntryDAOStress.class);
        return suite;
    }
}