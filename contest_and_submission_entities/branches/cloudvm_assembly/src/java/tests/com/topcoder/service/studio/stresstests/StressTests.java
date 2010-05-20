/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * All stress test cases.
     * </p>
     *
     * @return stress tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(ContestGeneralInfoStressTest.class));
        suite.addTest(new TestSuite(ContestMultiRoundInformationStressTest.class));
        suite.addTest(new TestSuite(ContestResourceStressTest.class));
        suite.addTest(new TestSuite(ContestSpecificationsStressTest.class));
        suite.addTest(new TestSuite(MilestonePrizeStressTest.class));

        return suite;
    }
}
