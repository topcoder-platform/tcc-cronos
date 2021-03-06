/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Suite.
     *
     * @return the test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AggregationPhaseHandlerTest.class);
        suite.addTestSuite(AggregationReviewPhaseHandlerTest.class);
        suite.addTestSuite(AppealsPhaseHandlerTest.class);
        suite.addTestSuite(AppealsResponsePhaseHandlerTest.class);
        suite.addTestSuite(ApprovalPhaseHandlerTest.class);
        suite.addTestSuite(FinalFixPhaseHandlerTest.class);
        suite.addTestSuite(FinalReviewPhaseHandlerTest.class);
        suite.addTestSuite(PostMortemPhaseHandlerTest.class);
        suite.addTestSuite(RegistrationPhaseHandlerTest.class);
        suite.addTestSuite(ReviewPhaseHandlerTest.class);
        suite.addTestSuite(ScreeningPhaseHandlerTest.class);
        suite.addTestSuite(SubmissionPhaseHandlerTest.class);
        suite.addTestSuite(ShowStressResult.class);
        return suite;
    }
}
