/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import com.topcoder.web.tc.action.BaseJSONParameterActionFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ActiveContestsManagerActionFailureTests.suite());
        suite.addTest(CategoriesManagerActionFailureTests.suite());
        suite.addTest(BaseJSONParameterActionFailureTests.suite());
        suite.addTest(PastContestsManagerActionFailureTests.suite());
        suite.addTest(ContestStatusManagerActionFailureTests.suite());
        suite.addTest(CategoriesManagerImplFailureTests.suite());
        suite.addTest(PastContestsManagerImplFailureTests.suite());
        suite.addTest(ActiveContestsManagerImplFailureTests.suite());
        suite.addTest(ContestStatusManagerImplFailureTests.suite());

        return suite;
    }

}
