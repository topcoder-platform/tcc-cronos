/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.phases.stresstests;

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

    public static Test suite() {

        final TestSuite suite = new TestSuite();

        suite.addTestSuite(PhaseStatusLookupUtilityTest.class);
        suite.addTestSuite(ReviewPhaseHandlerTest.class);
        suite.addTestSuite(RegistrationPhaseHandlerTest.class);
        suite.addTestSuite(ScreeningPhaseHandlerTest.class);
        suite.addTestSuite(SubmissionPhaseHandlerTest.class);
        suite.addTestSuite(AppealsPhaseHandlerTest.class);
        suite.addTestSuite(ApprovalPhaseHandlerTest.class);
        suite.addTestSuite(FinalFixPhaseHandlerTest.class);
        suite.addTestSuite(FinalReviewPhaseHandlerTest.class);
        //suite.addTestSuite(AggregationPhaseHandlerTest.class);
        
        return suite;
    }
}
