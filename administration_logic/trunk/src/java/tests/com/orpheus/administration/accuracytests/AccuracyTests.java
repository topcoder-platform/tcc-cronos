/*
 * Copyright (C) 2006 TopCoder Inc., All rights reserved.
 */
package com.orpheus.administration.accuracytests;

import com.orpheus.administration.accuracytests.entities.DomainTargetImplAccuracyTests;
import com.orpheus.administration.accuracytests.entities.GameImplAccuracyTests;
import com.orpheus.administration.accuracytests.entities.HandlerResultAccuracyTests;
import com.orpheus.administration.accuracytests.entities.HostingBlockImplAccuracyTests;
import com.orpheus.administration.accuracytests.entities.HostingSlotImplAccuracyTests;
import com.orpheus.administration.accuracytests.entities.PuzzleConfigAccuracyTests;
import com.orpheus.administration.accuracytests.entities.PuzzleTypeEnumAccuracyTests;
import com.orpheus.administration.accuracytests.entities.ResultCodeAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.AdminSummaryHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.CreateGameHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.DeleteSlotHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.DomainApprovalHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.DomainRejectionHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.GameParameterHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.PendingSponsorDomainHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.PendingSponsorHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.PendingWinnerApprovalHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.PendingWinnerHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.PendingWinnerRejectionHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.ReorderSlotsHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.SponsorApprovalHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.SponsorImageApprovalHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.SponsorImageRejectionHandlerAccuracyTests;
import com.orpheus.administration.accuracytests.handlers.SponsorRejectionHandlerAccuracyTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AdministrationManagerAccuracyTests.class);
        suite.addTestSuite(ServiceLocatorAccuracyTests.class);

        suite.addTestSuite(DomainTargetImplAccuracyTests.class);
        suite.addTestSuite(GameImplAccuracyTests.class);
        suite.addTestSuite(HandlerResultAccuracyTests.class);
        suite.addTestSuite(HostingBlockImplAccuracyTests.class);
        suite.addTestSuite(HostingSlotImplAccuracyTests.class);
        suite.addTestSuite(PuzzleConfigAccuracyTests.class);
        suite.addTestSuite(PuzzleTypeEnumAccuracyTests.class);
        suite.addTestSuite(ResultCodeAccuracyTests.class);

        suite.addTestSuite(AdminSummaryHandlerAccuracyTests.class);
        suite.addTestSuite(GameParameterHandlerAccuracyTests.class);
        suite.addTestSuite(DeleteSlotHandlerAccuracyTests.class);
        suite.addTestSuite(PendingSponsorDomainHandlerAccuracyTests.class);
        suite.addTestSuite(PendingWinnerApprovalHandlerAccuracyTests.class);
        suite.addTestSuite(PendingWinnerRejectionHandlerAccuracyTests.class);
        suite.addTestSuite(PendingWinnerHandlerAccuracyTests.class);
        suite.addTestSuite(PendingSponsorHandlerAccuracyTests.class);
        suite.addTestSuite(SponsorImageRejectionHandlerAccuracyTests.class);
        suite.addTestSuite(SponsorImageApprovalHandlerAccuracyTests.class);
        suite.addTestSuite(SponsorApprovalHandlerAccuracyTests.class);
        suite.addTestSuite(SponsorRejectionHandlerAccuracyTests.class);
        suite.addTestSuite(ReorderSlotsHandlerAccuracyTests.class);
        suite.addTestSuite(CreateGameHandlerAccuracyTests.class);
        suite.addTestSuite(DomainApprovalHandlerAccuracyTests.class);
        suite.addTestSuite(DomainRejectionHandlerAccuracyTests.class);

        return suite;
    }

}
