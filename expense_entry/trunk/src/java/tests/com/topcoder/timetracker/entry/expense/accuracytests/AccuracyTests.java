/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ExpenseEntryStatusManagerTest.suite());
        suite.addTest(CommonInfoTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(ExpenseEntryDbPersistenceTest.suite());
        suite.addTest(ExpenseEntryManagerTest.suite());
        suite.addTest(ExpenseEntryStatusDbPersistenceTest.suite());
        suite.addTest(ExpenseEntryTest.suite());
        suite.addTest(ExpenseEntryTypeDbPersistenceTest.suite());
        suite.addTest(ExpenseEntryTypeManagerTest.suite());
        suite.addTest(InsufficientDataExceptionTest.suite());
        return suite;
    }

}
