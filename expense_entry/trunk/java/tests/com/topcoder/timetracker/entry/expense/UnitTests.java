/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.expense.criteria.CompositeCriteriaUnitTest;
import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteriaUnitTest;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteriaUnitTest;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteriaUnitTest;
import com.topcoder.timetracker.entry.expense.criteria.NotCriteriaUnitTest;
import com.topcoder.timetracker.entry.expense.criteria.RejectReasonCriteriaUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.BasicExpenseEntryBeanUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryBeanUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryManagerLocalDelegateUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusBeanUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusManagerLocalDelegateUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeBeanUnitTest;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeManagerLocalDelegateUnitTest;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseEntryDAOUnitTest;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAOUnitTest;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAOUnitTest;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceExceptionUnitTest;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ConfigurationExceptionUnitTest.class);
        suite.addTestSuite(ExpenseEntryUnitTest.class);
        suite.addTestSuite(ExpenseEntryHelperUnitTest.class);
        suite.addTestSuite(ExpenseStatusUnitTest.class);
        suite.addTestSuite(ExpenseTypeUnitTest.class);
        suite.addTestSuite(InsufficientDataExceptionUnitTest.class);

        suite.addTestSuite(CompositeCriteriaUnitTest.class);
        suite.addTestSuite(FieldBetweenCriteriaUnitTest.class);
        suite.addTestSuite(FieldLikeCriteriaUnitTest.class);
        suite.addTestSuite(FieldMatchCriteriaUnitTest.class);
        suite.addTestSuite(NotCriteriaUnitTest.class);
        suite.addTestSuite(RejectReasonCriteriaUnitTest.class);

        suite.addTestSuite(PersistenceExceptionUnitTest.class);
        suite.addTestSuite(InformixExpenseTypeDAOUnitTest.class);
        suite.addTestSuite(InformixExpenseStatusDAOUnitTest.class);
        suite.addTestSuite(InformixExpenseEntryDAOUnitTest.class);

        suite.addTestSuite(ExpenseTypeBeanUnitTest.class);
        suite.addTestSuite(ExpenseStatusBeanUnitTest.class);
        suite.addTestSuite(ExpenseTypeManagerLocalDelegateUnitTest.class);
        suite.addTestSuite(ExpenseStatusManagerLocalDelegateUnitTest.class);
        suite.addTestSuite(BasicExpenseEntryBeanUnitTest.class);
        suite.addTestSuite(ExpenseEntryBeanUnitTest.class);
        suite.addTestSuite(ExpenseEntryManagerLocalDelegateUnitTest.class);

        suite.addTestSuite(DemoTestCase.class);
        return suite;
    }
}
