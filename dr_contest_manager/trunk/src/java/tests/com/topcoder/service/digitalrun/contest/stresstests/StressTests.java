/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Stress tests.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class StressTests extends TestCase {


    /**
     * <p>
     * Runs all stress tests.
     * </p>
     *
     * @return all stress tests.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(DigitalRunContestManagerBeanStressTests.class);
        return suite;
    }
}
