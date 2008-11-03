/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * This test case aggregates all stress test cases.
     * </p>
     *
     * @return a test suite containing all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Adds XdriveObjectStressTest.
        suite.addTestSuite(AbandonedPhaseHandlerStressTest.class);

        // Adds ActionRequiredPhaseHandlerStressTest.
        suite.addTestSuite(ActionRequiredPhaseHandlerStressTest.class);

        // Adds ActivePhaseHandlerStressTest.
        suite.addTestSuite(ActivePhaseHandlerStressTest.class);

        // Adds CancelledPhaseHandlerStressTest.
        suite.addTestSuite(CancelledPhaseHandlerStressTest.class);

        // Adds CompletedPhaseHandlerStressTest.
        suite.addTestSuite(CompletedPhaseHandlerStressTest.class);

        // Adds DraftPhaseHandlerStressTest.
        suite.addTestSuite(DraftPhaseHandlerStressTest.class);

        // Adds ExtendedPhaseHandlerStressTest.
        suite.addTestSuite(ExtendedPhaseHandlerStressTest.class);

        // Adds InDangerPhaseHandlerStressTest.
        suite.addTestSuite(InDangerPhaseHandlerStressTest.class);

        // Adds InsufficientSubmissionsPhaseHandlerStressTest.
        suite.addTestSuite(InsufficientSubmissionsPhaseHandlerStressTest.class);

        // Adds InsufficientSubmissionsRerunPossiblePhaseHandlerStressTest.
        suite.addTestSuite(InsufficientSubmissionsRerunPossiblePhaseHandlerStressTest.class);

        // Adds NoWinnerChosenPhaseHandlerStressTest.
        suite.addTestSuite(NoWinnerChosenPhaseHandlerStressTest.class);

        // Adds RePostPhaseHandlerStressTest.
        suite.addTestSuite(RePostPhaseHandlerStressTest.class);

        // Adds ScheduledPhaseHandlerStressTest.
        suite.addTestSuite(ScheduledPhaseHandlerStressTest.class);

        // Adds TerminatedPhaseHandlerStressTest.
        suite.addTestSuite(TerminatedPhaseHandlerStressTest.class);

        // Adds DefaultEmailMessageGeneratorStressTest.
        suite.addTestSuite(DefaultEmailMessageGeneratorStressTest.class);

        return suite;
    }
}
