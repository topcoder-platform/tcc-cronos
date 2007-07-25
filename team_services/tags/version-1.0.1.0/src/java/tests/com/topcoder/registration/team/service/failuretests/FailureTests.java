/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.registration.team.service.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(OperationResultImplFailureTests.class);
		suite.addTestSuite(ResourcePositionImplFailureTests.class);
		suite.addTestSuite(TeamServicesImplFailureTests.class);
        return suite;
    }

}
