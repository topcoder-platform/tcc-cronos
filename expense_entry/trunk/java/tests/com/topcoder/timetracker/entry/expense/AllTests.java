/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.expense.accuracytests.AccuracyTests;
import com.topcoder.timetracker.entry.expense.failuretests.FailureTests;
import com.topcoder.timetracker.entry.expense.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author Topcoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());
        suite.addTest(AccuracyTests.suite());

        return suite;
    }

}
