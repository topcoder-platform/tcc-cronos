/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AuctionListenerImplTests.class);
        suite.addTestSuite(BidPlacementHandlerTests.class);
        suite.addTestSuite(BidUpdateHandlerTests.class);
        suite.addTestSuite(BidValidatorImplTests.class);
        suite.addTestSuite(LeadingBidsHandlerTests.class);
        suite.addTestSuite(OpenAuctionsHandlerTests.class);

        return suite;
    }
}
