/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.administration.entities.DomainTargetImplUnitTests;
import com.orpheus.administration.entities.GameImplUnitTests;
import com.orpheus.administration.entities.HandlerResultUnitTests;
import com.orpheus.administration.entities.HostingBlockImplUnitTests;
import com.orpheus.administration.entities.HostingSlotImplUnitTests;
import com.orpheus.administration.entities.PuzzleConfigUnitTests;
import com.orpheus.administration.entities.PuzzleTypeEnumUnitTests;
import com.orpheus.administration.entities.ResultCodeUnitTests;
import com.orpheus.administration.entities.SponsorDomainUnitTests;
import com.orpheus.administration.handlers.AdminSummaryHandlerUnitTests;
import com.orpheus.administration.handlers.CreateGameHandlerUnitTests;
import com.orpheus.administration.handlers.DeleteSlotHandlerUnitTests;
import com.orpheus.administration.handlers.DomainApprovalHandlerUnitTests;
import com.orpheus.administration.handlers.DomainApprovalRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.DomainRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.GameParameterHandlerUnitTests;
import com.orpheus.administration.handlers.PendingSponsorDomainHandlerUnitTests;
import com.orpheus.administration.handlers.PendingSponsorHandlerUnitTests;
import com.orpheus.administration.handlers.PendingWinnerApprovalHandlerUnitTests;
import com.orpheus.administration.handlers.PendingWinnerApprovalRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.PendingWinnerHandlerUnitTests;
import com.orpheus.administration.handlers.PendingWinnerRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.RegenerateBrainteaserHandlerUnitTests;
import com.orpheus.administration.handlers.RegenerateBrainteaserOrPuzzleHandlerUnitTests;
import com.orpheus.administration.handlers.RegeneratePuzzleHandlerUnitTests;
import com.orpheus.administration.handlers.ReorderSlotsHandlerUnitTests;
import com.orpheus.administration.handlers.SponsorApprovalHandlerUnitTests;
import com.orpheus.administration.handlers.SponsorApprovalRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.SponsorImageApprovalHandlerUnitTests;
import com.orpheus.administration.handlers.SponsorImageApprovalRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.SponsorImageRejectionHandlerUnitTests;
import com.orpheus.administration.handlers.SponsorRejectionHandlerUnitTests;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Aggregates testcases.
     *
     * @return a test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // main package
        suite.addTestSuite(AdministrationExceptionUnitTests.class);
        suite.addTestSuite(AdministrationManagerUnitTests.class);
        suite.addTestSuite(ConfigurationExceptionUnitTests.class);
        suite.addTestSuite(ServiceLocatorExceptionUnitTests.class);
        suite.addTestSuite(ServiceLocatorUnitTests.class);

        // handler tests
        suite.addTestSuite(CreateGameHandlerUnitTests.class);
        suite.addTestSuite(AdminSummaryHandlerUnitTests.class);
        suite.addTestSuite(DeleteSlotHandlerUnitTests.class);
        suite.addTestSuite(DomainApprovalHandlerUnitTests.class);
        suite.addTestSuite(DomainApprovalRejectionHandlerUnitTests.class);
        suite.addTestSuite(DomainRejectionHandlerUnitTests.class);
        suite.addTestSuite(GameParameterHandlerUnitTests.class);
        suite.addTestSuite(PendingSponsorDomainHandlerUnitTests.class);
        suite.addTestSuite(PendingSponsorHandlerUnitTests.class);
        suite.addTestSuite(PendingWinnerApprovalHandlerUnitTests.class);
        suite
                .addTestSuite(PendingWinnerApprovalRejectionHandlerUnitTests.class);
        suite.addTestSuite(PendingWinnerHandlerUnitTests.class);
        suite.addTestSuite(PendingWinnerRejectionHandlerUnitTests.class);
        suite.addTestSuite(RegenerateBrainteaserOrPuzzleHandlerUnitTests.class);
        suite.addTestSuite(RegenerateBrainteaserHandlerUnitTests.class);
        suite.addTestSuite(RegeneratePuzzleHandlerUnitTests.class);
        suite.addTestSuite(ReorderSlotsHandlerUnitTests.class);
        suite.addTestSuite(SponsorApprovalRejectionHandlerUnitTests.class);
        suite.addTestSuite(SponsorApprovalHandlerUnitTests.class);
        suite.addTestSuite(SponsorImageApprovalRejectionHandlerUnitTests.class);
        suite.addTestSuite(SponsorImageApprovalHandlerUnitTests.class);
        suite.addTestSuite(SponsorImageRejectionHandlerUnitTests.class);
        suite.addTestSuite(SponsorRejectionHandlerUnitTests.class);

        // entities
        suite.addTestSuite(DomainTargetImplUnitTests.class);
        suite.addTestSuite(GameImplUnitTests.class);
        suite.addTestSuite(HandlerResultUnitTests.class);
        suite.addTestSuite(HostingBlockImplUnitTests.class);
        suite.addTestSuite(HostingSlotImplUnitTests.class);
        suite.addTestSuite(PuzzleConfigUnitTests.class);
        suite.addTestSuite(PuzzleTypeEnumUnitTests.class);
        suite.addTestSuite(ResultCodeUnitTests.class);
        suite.addTestSuite(SponsorDomainUnitTests.class);

        // demo
        suite.addTestSuite(Demo.class);

        return suite;
    }

}
