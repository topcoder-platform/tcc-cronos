/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.cronos.timetracker.entry.expense.persistence.V1Dot1ExpenseEntryDbPersistenceUnitTest;
import com.cronos.timetracker.entry.expense.search.V1Dot1CompositeCriteriaUnitTest;
import com.cronos.timetracker.entry.expense.search.V1Dot1FieldBetweenCriteriaUnitTest;
import com.cronos.timetracker.entry.expense.search.V1Dot1FieldLikeCriteriaUnitTest;
import com.cronos.timetracker.entry.expense.search.V1Dot1FieldMatchCriteriaUnitTest;
import com.cronos.timetracker.entry.expense.search.V1Dot1NotCriteriaUnitTest;
import com.cronos.timetracker.entry.expense.search.V1Dot1RejectReasonCriteriaUnitTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
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

        // unit test cases for v1.1
        suite.addTestSuite(V1Dot1BasicInfoUnitTest.class);
        suite.addTestSuite(V1Dot1CommonInfoUnitTest.class);
        suite.addTestSuite(V1Dot1ExpenseEntryManagerUnitTest.class);
        suite.addTestSuite(V1Dot1ExpenseEntryRejectReasonUnitTest.class);
        suite.addTestSuite(V1Dot1ExpenseEntryStatusUnitTest.class);
        suite.addTestSuite(V1Dot1ExpenseEntryTypeUnitTest.class);
        suite.addTestSuite(V1Dot1ExpenseEntryUnitTest.class);

        suite.addTestSuite(V1Dot1ExpenseEntryDbPersistenceUnitTest.class);

        suite.addTestSuite(V1Dot1CompositeCriteriaUnitTest.class);
        suite.addTestSuite(V1Dot1FieldBetweenCriteriaUnitTest.class);
        suite.addTestSuite(V1Dot1NotCriteriaUnitTest.class);
        suite.addTestSuite(V1Dot1FieldLikeCriteriaUnitTest.class);
        suite.addTestSuite(V1Dot1FieldMatchCriteriaUnitTest.class);
        suite.addTestSuite(V1Dot1RejectReasonCriteriaUnitTest.class);

        // demo for V1.1
        suite.addTestSuite(V1Dot1DemoTest.class);

        // demo for v2.0
        suite.addTestSuite(V2DotDemoTest.class);

        // unit test cases for v1.0
        suite.addTest(DemoTestCase.suite());
        suite.addTest(ExceptionTestCase.suite());
        suite.addTest(ExpenseEntryManagerTestCase.suite());
        suite.addTest(ExpenseEntryStatusManagerTestCase.suite());
        suite.addTest(ExpenseEntryStatusTestCase.suite());
        suite.addTest(ExpenseEntryTestCase.suite());
        suite.addTest(ExpenseEntryTypeManagerTestCase.suite());

        return suite;
    }
}
