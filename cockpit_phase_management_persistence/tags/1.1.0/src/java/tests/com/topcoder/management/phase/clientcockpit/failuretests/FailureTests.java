/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * 
 * @author hfx
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * All failure tests for this failure review.
     * 
     * @return the suite aggregated all failure tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CockpitPhaseManagerTests.class);
        
        return suite;
    }

}
