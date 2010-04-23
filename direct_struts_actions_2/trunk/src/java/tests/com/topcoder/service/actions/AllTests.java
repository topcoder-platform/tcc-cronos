/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.service.actions.accuracytests.AccuracyTests;
import com.topcoder.service.actions.failuretests.FailureTests;
import com.topcoder.service.actions.stresstests.StressTests;
/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        suite.addTest(AccuracyTests.suite());

        suite.addTest(FailureTests.suite());

        suite.addTest(StressTests.suite());

        return suite;
    }

}
