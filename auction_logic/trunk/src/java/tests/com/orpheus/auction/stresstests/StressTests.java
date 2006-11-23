/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author waits
 * @version 1.0
 */
public class StressTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
       suite.addTestSuite(AuctionListenerImplStressTestCases.class);
       suite.addTestSuite(BidPlacementHandlerStressTestCase.class);
       suite.addTestSuite(BidUpdateHandlerStressTestCase.class);
       suite.addTestSuite(LeadingBidsHandlerStressTestCase.class);
       suite.addTestSuite(OpenAuctionsHandlerStressTestCase.class);
       
        return suite;
    }
}
