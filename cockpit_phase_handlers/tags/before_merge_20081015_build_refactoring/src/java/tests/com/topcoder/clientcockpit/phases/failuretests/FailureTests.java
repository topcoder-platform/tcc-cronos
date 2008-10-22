/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Aggregates all failure test cases.
     * </p>
     *
     * @return the test case aggregates all failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(InsufficientSubmissionsRerunPossiblePhaseHandlerFailureTests.suite());
        suite.addTest(AbstractPhaseHandlerFailureTests.suite());
        suite.addTest(TerminatedPhaseHandlerFailureTests.suite());
        suite.addTest(CompletedPhaseHandlerFailureTests.suite());
        suite.addTest(InDangerPhaseHandlerFailureTests.suite());
        suite.addTest(ActionRequiredPhaseHandlerFailureTests.suite());
        suite.addTest(RePostPhaseHandlerFailureTests.suite());
        suite.addTest(NoWinnerChosenPhaseHandlerFailureTests.suite());
        suite.addTest(CancelledPhaseHandlerFailureTests.suite());
        suite.addTest(ScheduledPhaseHandlerFailureTests.suite());
        suite.addTest(InsufficientSubmissionsPhaseHandlerFailureTests.suite());
        suite.addTest(AbandonedPhaseHandlerFailureTests.suite());
        suite.addTest(ActivePhaseHandlerFailureTests.suite());
        suite.addTest(DraftPhaseHandlerFailureTests.suite());
        suite.addTest(ExtendedPhaseHandlerFailureTests.suite());
        suite.addTest(DefaultEmailMessageGeneratorFailureTests.suite());

        return suite;
    }

}
