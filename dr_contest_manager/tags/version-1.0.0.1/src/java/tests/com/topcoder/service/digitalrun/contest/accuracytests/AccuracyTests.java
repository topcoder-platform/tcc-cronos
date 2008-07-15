/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.accuracytests;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>The accuracy test cases.</p>
 * @author waits
 * @version 1.0
 */
public class AccuracyTests {
    /**
     * <p>The accuracy test case.</p>
     *
     * @return Test into Junit
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.topcoder.service.digitalrun.contest.accuracytests");
        suite.addTestSuite(DigitalRunContestManagerBeanAccuracyTests.class);

        return suite;
    }
}
