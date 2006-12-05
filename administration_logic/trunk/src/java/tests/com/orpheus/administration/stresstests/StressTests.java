/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AdminSummaryHandlerStressTest.class);
        suite.addTestSuite(CreateGameHandlerStressTest.class);
        suite.addTestSuite(DeleteSlotHandlerStressTest.class);
        suite.addTestSuite(DomainApprovalHandlerStressTest.class);
        suite.addTestSuite(DomainRejectionHandlerStressTest.class);
        suite.addTestSuite(GameParameterHandlerStressTest.class);
        suite.addTestSuite(PendingSponsorDomainHandlerStressTest.class);
        suite.addTestSuite(PendingSponsorHandlerStressTest.class);
        suite.addTestSuite(PendingWinnerApprovalHandlerStressTest.class);
        suite.addTestSuite(PendingWinnerHandlerStressTest.class);
        suite.addTestSuite(PendingWinnerRejectionHandlerStressTest.class);
        suite.addTestSuite(RegenerateBrainteaserHandlerStressTest.class);
        suite.addTestSuite(RegeneratePuzzleHandlerStressTest.class);
        suite.addTestSuite(ReorderSlotsHandlerStressTest.class);
        suite.addTestSuite(SponsorApprovalHandlerStressTest.class);
        suite.addTestSuite(SponsorImageApprovalHandlerStressTest.class);
        suite.addTestSuite(SponsorImageRejectionHandlerStressTest.class);
        suite.addTestSuite(SponsorRejectionHandlerStressTest.class);
        return suite;
    }
}
