package com.cronos.onlinereview.phases.accuracytests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author tuenm
 * @version 1.0
 */
public class AccuracyTests {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //look up classes
        suite.addTestSuite(PhaseStatusLookupUtilityAccTest.class);
        suite.addTestSuite(PhaseTypeLookupUtilityAccTest.class);
        suite.addTestSuite(ProjectInfoTypeLookupUtilityAccTest.class);
        suite.addTestSuite(ResourceRoleLookupUtilityAccTest.class);
        suite.addTestSuite(SubmissionStatusLookupUtilityAccTest.class);
        suite.addTestSuite(UploadStatusLookupUtilityAccTest.class);
        suite.addTestSuite(UploadTypeLookupUtilityAccTest.class);
        suite.addTestSuite(ManagerHelperAccuracyTest.class);

        suite.addTestSuite(RegistrationPhaseHandlerAccTest.class);
        suite.addTestSuite(SubmissionPhaseHandlerAccTest.class);
        suite.addTestSuite(ScreeningPhaseHandlerAccTest.class);
        suite.addTestSuite(ReviewPhaseHandlerAccTest.class);
        suite.addTestSuite(AppealsPhaseHandlerAccTest.class);
        suite.addTestSuite(AppealsResponsePhaseHandlerAccTest.class);
        suite.addTestSuite(AggregationPhaseHandlerAccTest.class);
        suite.addTestSuite(AggregationReviewPhaseHandlerAccTest.class);
        suite.addTestSuite(FinalFixPhaseHandlerAccTest.class);
        suite.addTestSuite(FinalReviewPhaseHandlerAccTest.class);
        suite.addTestSuite(ApprovalPhaseHandlerAccTest.class);

        return suite;
    }
}
