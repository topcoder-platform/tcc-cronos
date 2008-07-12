/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>All the stress test cases for this component.</p>
 * @author telly
 * @version 1.0
 */
public class StressTests {
    /**
     * All the Stress test case.
     *
     * @return Test into JUnit
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.topcoder.service.digitalrun.track.stresstests");
        suite.addTest(JPADigitalRunPointsCalculatorDAOTests.suite());        
        suite.addTest(JPADigitalRunTrackStatusDAOTests.suite());
        suite.addTest(JPADigitalRunProjectTypeDAOTests.suite());
        suite.addTest(JPADigitalRunTrackTypeDAOTests.suite());
        suite.addTest(JPADigitalRunTrackDAOTests.suite());

        return suite;
    }
}
