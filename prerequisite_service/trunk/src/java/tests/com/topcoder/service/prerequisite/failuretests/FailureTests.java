/**
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.prerequisite.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * The failure tests suite
     *
     * @return failure tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(PrerequisiteServiceBeanFailureTests.class);

        return suite;
    }
}
