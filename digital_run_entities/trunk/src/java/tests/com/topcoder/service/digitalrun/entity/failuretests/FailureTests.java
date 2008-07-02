/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.service.digitalrun.entity.failuretests;

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

        suite.addTestSuite(TestBaseEntity.class);
        suite.addTestSuite(TestProjectType.class);
        suite.addTestSuite(TestDigitalRunPoints.class);
        suite.addTestSuite(TestTrack.class);
        suite.addTestSuite(TestPointsCalculator.class);
        suite.addTestSuite(TestTrackContest.class);
        suite.addTestSuite(TestTrackContestResultCalculator.class);

        suite.addTestSuite(TestDigitalRunEntityIDGenerator.class);

        return suite;
    }

}
