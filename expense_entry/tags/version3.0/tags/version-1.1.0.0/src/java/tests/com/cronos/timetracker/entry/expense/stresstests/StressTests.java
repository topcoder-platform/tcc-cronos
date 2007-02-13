/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.entry.expense.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.1
 */
public class StressTests extends TestCase {
    /**
     * Aggregate all stress test cases.
     *
     * @return all stress test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ExpenseEntryDbPersistenceStressTest.suite());
        suite.addTest(ExpenseEntryBulkOperationStressTest.suite());
        return suite;
    }
}
