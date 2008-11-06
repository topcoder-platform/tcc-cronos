/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.jira.failuretests.FailureTests;
import com.topcoder.jira.accuracytests.AccuracyTests;
import com.topcoder.jira.stresstests.StressTests;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
		
		suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
		suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
