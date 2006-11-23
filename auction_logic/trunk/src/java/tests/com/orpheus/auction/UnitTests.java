/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.auction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AuctionListenerImplExceptionUnitTests.class);
        suite.addTestSuite(AuctionListenerImplUnitTests.class);
        suite.addTestSuite(AuctionLogicConfigExceptionUnitTests.class);
        suite.addTestSuite(BidPlacementHandlerUnitTests.class);
        suite.addTestSuite(BidUpdateHandlerUnitTests.class);
        suite.addTestSuite(BidValidatorImplUnitTests.class);
        suite.addTestSuite(KeyConstantsUnitTests.class);
        suite.addTestSuite(LeadingBidsHandlerUnitTests.class);
        suite.addTestSuite(OpenAuctionsHandlerUnitTests.class);

        suite.addTestSuite(HelperUnitTests.class);
        
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
