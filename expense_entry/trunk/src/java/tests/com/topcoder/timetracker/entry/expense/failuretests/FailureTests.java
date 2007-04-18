/*
 * Copyright (C) 2007 TopCoder Inc., All rights reserved.
 */
 package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CompositeCriteriaFailureTests.class);
        suite.addTestSuite(ExpenseEntryFailureTests.class);
        suite.addTestSuite(ExpenseEntryManagerLocalDelegateFailureTests.class);
        suite.addTestSuite(ExpenseStatusFailureTests.class);
        suite.addTestSuite(ExpenseStatusManagerLocalDelegateFailureTests.class);
        suite.addTestSuite(ExpenseTypeFailureTests.class);
        suite.addTestSuite(ExpenseTypeManagerLocalDelegateFailureTests.class);
        suite.addTestSuite(FieldBetweenCriteriaFailureTests.class);
        suite.addTestSuite(FieldLikeCriteriaFailureTests.class);
        suite.addTestSuite(FieldMatchCriteriaFailureTests.class);
        suite.addTestSuite(InformixExpenseEntryDAOFailureTests.class);
        suite.addTestSuite(InformixExpenseStatusDAOFailureTests.class);
        suite.addTestSuite(InformixExpenseTypeDAOFailureTests.class);
        suite.addTestSuite(NotCriteriaFailureTests.class);
        return suite;
    }

}
