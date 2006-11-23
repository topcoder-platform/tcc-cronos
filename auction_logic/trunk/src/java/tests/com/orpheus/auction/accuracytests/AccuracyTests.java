/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.auction.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(AuctionListenerImplAccuracyTest.class);
        suite.addTestSuite(BidPlacementHandlerAccuracyTest.class);
        suite.addTestSuite(BidUpdateHandlerAccuracyTest.class);
        suite.addTestSuite(BidValidatorImplAccuracyTest.class);
        suite.addTestSuite(KeyConstantsAccuracyTest.class);
        suite.addTestSuite(LeadingBidsHandlerAccuracyTest.class);
        suite.addTestSuite(OpenAuctionsHandlerAccuracyTest.class);
        return suite;
    }

}
