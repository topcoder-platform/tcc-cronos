/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGeneratorTestAcc;
import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGeneratorTestExp;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Get all Unit test cases.
     * </p>
     *
     * @return all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //Exception classes
        suite.addTestSuite(EmailSendingExceptionTest.class);
        suite.addTestSuite(EmailMessageGenerationExceptionTest.class);
        suite.addTestSuite(PhaseHandlerConfigurationExceptionTest.class);

        //Message generator
        suite.addTestSuite(DefaultEmailMessageGeneratorTestAcc.class);
        suite.addTestSuite(DefaultEmailMessageGeneratorTestExp.class);

        //Handlers
        addHandlerTestCases(suite);

        //Demo
        suite.addTestSuite(Demo.class);

        return suite;
    }

    /**
     * <p>
     * Add all phase handler tests cases.
     * </p>
     *
     * @param suite <code>TestSuite</code> to add test cases into.
     */
    private static void addHandlerTestCases(TestSuite suite) {

        suite.addTestSuite(DraftPhaseHandlerTestAcc.class);
        suite.addTestSuite(DraftPhaseHandlerTestExp.class);

        suite.addTestSuite(ScheduledPhaseHandlerTestAcc.class);
        suite.addTestSuite(ScheduledPhaseHandlerTestExp.class);

        suite.addTestSuite(ActivePhaseHandlerTestAcc.class);
        suite.addTestSuite(ActivePhaseHandlerTestExp.class);

        suite.addTestSuite(ActionRequiredPhaseHandlerTestAcc.class);
        suite.addTestSuite(ActionRequiredPhaseHandlerTestExp.class);

        suite.addTestSuite(InDangerPhaseHandlerTestAcc.class);
        suite.addTestSuite(InDangerPhaseHandlerTestExp.class);

        suite.addTestSuite(InsufficientSubmissionsRerunPossiblePhaseHandlerTestAcc.class);
        suite.addTestSuite(InsufficientSubmissionsRerunPossiblePhaseHandlerTestExp.class);

        suite.addTestSuite(ExtendedPhaseHandlerTestAcc.class);
        suite.addTestSuite(ExtendedPhaseHandlerTestExp.class);

        suite.addTestSuite(InsufficientSubmissionsPhaseHandlerTestAcc.class);
        suite.addTestSuite(InsufficientSubmissionsPhaseHandlerTestExp.class);

        suite.addTestSuite(AbandonedPhaseHandlerTestAcc.class);
        suite.addTestSuite(AbandonedPhaseHandlerTestExp.class);

        suite.addTestSuite(NoWinnerChosenPhaseHandlerTestAcc.class);
        suite.addTestSuite(NoWinnerChosenPhaseHandlerTestExp.class);

        suite.addTestSuite(CompletedPhaseHandlerTestAcc.class);
        suite.addTestSuite(CompletedPhaseHandlerTestExp.class);

        suite.addTestSuite(RePostPhaseHandlerTestAcc.class);
        suite.addTestSuite(RePostPhaseHandlerTestExp.class);

        suite.addTestSuite(CancelledPhaseHandlerTestAcc.class);
        suite.addTestSuite(CancelledPhaseHandlerTestExp.class);

        suite.addTestSuite(TerminatedPhaseHandlerTestAcc.class);
        suite.addTestSuite(TerminatedPhaseHandlerTestExp.class);

    }
}
