/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author -oo-, kr00tki, brain_cn
 * @version 3.2
 * @since 1.1
 */
public class AccuracyTests extends TestCase {
    /**
     * Accuracy test cases suite.
     *
     * @return the suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(CriteriaAccuracyTests.class);
        suite.addTestSuite(InformixExpenseEntryDAOAccuracyTests.class);
        suite.addTestSuite(ExpenseEntryManagerLocalDelegateAccuracyTests.class);
        suite.addTestSuite(InformixExpenseTypeDAOAccuracyTests.class);
        suite.addTestSuite(InformixExpenseStatusDAOAccuracyTest.class);

        suite.addTestSuite(BasicExpenseEntryBeanAccuracyTests.class);
        suite.addTestSuite(ExpenseEntryBeanAccuracyTests.class);
        suite.addTestSuite(ExpenseStatusBeanAccuracyTests.class);
        suite.addTestSuite(ExpenseStatusManagerLocalDelegateAccuracyTests.class);
        suite.addTestSuite(ExpenseTypeBeanAccuracyTests.class);
        suite.addTestSuite(ExpenseTypeManagerLocalDelegateAccuracyTests.class);

        return suite;
    }
}
