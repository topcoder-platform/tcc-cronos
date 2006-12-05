/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.administration.failuretests;

import com.orpheus.administration.failuretests.entities.HandlerResultFailureTests;
import com.orpheus.administration.failuretests.entities.PuzzleConfigFailureTests;
import com.orpheus.administration.failuretests.handlers.AdminSummaryHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.CreateGameHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.DeleteSlotHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.DomainApprovalHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.DomainRejectionHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.GameParameterHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.PendingSponsorDomainHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.PendingSponsorHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.PendingWinnerApprovalHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.PendingWinnerHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.PendingWinnerRejectionHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.RegenerateBrainteaserHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.RegeneratePuzzleHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.ReorderSlotsHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.SponsorApprovalHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.SponsorImageApprovalHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.SponsorImageRejectionHandlerFailureTests;
import com.orpheus.administration.failuretests.handlers.SponsorRejectionHandlerFailureTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AdministrationManagerFailureTests.class);
        suite.addTestSuite(ServiceLocatorFailureTests.class);

        suite.addTestSuite(HandlerResultFailureTests.class);
        suite.addTestSuite(PuzzleConfigFailureTests.class);

        suite.addTestSuite(AdminSummaryHandlerFailureTests.class);
        suite.addTestSuite(CreateGameHandlerFailureTests.class);
        suite.addTestSuite(GameParameterHandlerFailureTests.class);
        suite.addTestSuite(DeleteSlotHandlerFailureTests.class);
        suite.addTestSuite(DomainApprovalHandlerFailureTests.class);

        suite.addTestSuite(DomainRejectionHandlerFailureTests.class);
        suite.addTestSuite(PendingSponsorDomainHandlerFailureTests.class);
        suite.addTestSuite(PendingSponsorHandlerFailureTests.class);
        suite.addTestSuite(PendingWinnerApprovalHandlerFailureTests.class);
        suite.addTestSuite(PendingWinnerHandlerFailureTests.class);

        suite.addTestSuite(PendingWinnerRejectionHandlerFailureTests.class);
        suite.addTestSuite(RegenerateBrainteaserHandlerFailureTests.class);
        suite.addTestSuite(RegeneratePuzzleHandlerFailureTests.class);
        suite.addTestSuite(ReorderSlotsHandlerFailureTests.class);
        suite.addTestSuite(SponsorApprovalHandlerFailureTests.class);

        suite.addTestSuite(SponsorImageApprovalHandlerFailureTests.class);
        suite.addTestSuite(SponsorImageRejectionHandlerFailureTests.class);
        suite.addTestSuite(SponsorRejectionHandlerFailureTests.class);


        return suite;
    }

}
