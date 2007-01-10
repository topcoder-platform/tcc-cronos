/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.timetracker.entry.expense.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(TestCompositeCriteria.class);
        suite.addTestSuite(TestExpenseEntry.class);
        suite.addTestSuite(TestFieldBetweenCriteria.class);
        suite.addTestSuite(TestFieldLikeCriteria.class);
        suite.addTestSuite(TestFieldMatchCriteria.class);
        suite.addTestSuite(TestNotCriteria.class);
        suite.addTestSuite(TestExpenseEntryManager.class);
        suite.addTestSuite(TestExpenseEntryDbPersistence.class);
        return suite;
    }

}
