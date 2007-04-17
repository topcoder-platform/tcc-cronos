/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FailureTests extends TestCase {

    /**
     * Returns the test suite.
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(FailureTestClientUtilityDelegate32.class);
        suite.addTestSuite(FailureTestClientUtilityFactory32.class);
        suite.addTestSuite(FailureTestClientUtilityImpl32.class);
        suite.addTestSuite(FailureTestClientUtilitySessionBean32.class);
        return suite;
    }

}
