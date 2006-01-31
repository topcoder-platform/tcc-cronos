/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(CommonInfoTestCase.suite());
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
