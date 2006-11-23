/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AuctionDAOFactoryFailureTest.class);
        suite.addTestSuite(CustomAuctionPersistenceFailureTest.class);
        suite.addTestSuite(CustomBidFailureTest.class);
        suite.addTestSuite(LocalCustomAuctionPersistenceFailureTest.class);
        suite.addTestSuite(RemoteCustomAuctionPersistenceFailureTest.class);
        suite.addTestSuite(AuctionBeanFailureTest.class);
        suite.addTestSuite(DefaultAuctionTranslatorFailureTest.class);
        suite.addTestSuite(SQLServerAuctionDAOFailureTest.class);
        return suite;
    }

}
