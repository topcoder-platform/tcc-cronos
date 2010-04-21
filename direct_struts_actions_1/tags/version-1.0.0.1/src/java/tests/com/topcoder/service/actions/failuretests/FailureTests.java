/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.actions.failuretests;

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
        suite.addTestSuite(ContestActionTests.class);
        suite.addTestSuite(GetCapacityFullDatesActionTests.class);
        suite.addTestSuite(ProjectActionTests.class);
        suite.addTestSuite(SaveDraftContestActionTests.class);
        suite.addTestSuite(ValidationErrorsInterceptorTests.class);
        return suite;
    }

}
