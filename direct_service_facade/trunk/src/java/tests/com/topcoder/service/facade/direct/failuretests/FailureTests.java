/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.failuretests;

import com.topcoder.service.facade.direct.ejb.DirectServiceFacadeBeanInitializeFailureTests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 * @author vilain
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Returns the test suite.
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new JUnit4TestAdapter(DirectServiceFacadeBeanInitializeFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(DirectServiceFacadeBeanFailureTests.class));
        return suite;
    }
}