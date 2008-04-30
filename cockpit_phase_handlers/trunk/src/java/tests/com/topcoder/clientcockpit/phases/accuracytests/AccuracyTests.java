/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all accuracy test cases.
     * </p>
     *
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DefaultEmailMessageGeneratorAccuracyTest.class);

        // accuracy test for handlers
        suite.addTestSuite(DraftPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(AbandonedPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(ActionRequiredPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(ActivePhaseHandlerAccuracyTest.class);
        suite.addTestSuite(CancelledPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(TerminatedPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(CompletedPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(ExtendedPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(RePostPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(InsufficientSubmissionsPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(InsufficientSubmissionsRerunPossiblePhaseHandlerAccuracyTest.class);
        suite.addTestSuite(NoWinnerChosenPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(ScheduledPhaseHandlerAccuracyTest.class);
        suite.addTestSuite(InDangerPhaseHandlerAccuracyTest.class);

        return suite;
    }
}