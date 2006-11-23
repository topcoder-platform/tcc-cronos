/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.auction.persistence.ejb.AuctionBeanUnitTest;
import com.orpheus.auction.persistence.ejb.AuctionDTOUnitTest;
import com.orpheus.auction.persistence.ejb.BidDTOUnitTest;
import com.orpheus.auction.persistence.impl.DefaultAuctionTranslatorUnitTest;
import com.orpheus.auction.persistence.impl.SQLServerAuctionDAOUnitTest;


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
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // package com.orpheus.auction.persistence
        suite.addTestSuite(PersistenceExceptionUnitTest.class);
        suite.addTestSuite(DuplicateEntryExceptionUnitTest.class);
        suite.addTestSuite(InvalidEntryExceptionUnitTest.class);
        suite.addTestSuite(EntryNotFoundExceptionUnitTest.class);
        suite.addTestSuite(TranslationExceptionUnitTest.class);
        suite.addTestSuite(ObjectInstantiationExceptionUnitTest.class);
        suite.addTestSuite(AuctionDAOFactoryUnitTest.class);
        suite.addTestSuite(CustomBidUnitTest.class);
        suite.addTestSuite(LocalCustomAuctionPersistenceUnitTest.class);
        suite.addTestSuite(RemoteCustomAuctionPersistenceUnitTest.class);
        suite.addTestSuite(AuctionPersistenceHelperUnitTest.class);

        // package com.orpheus.auction.persistence.ejb
        suite.addTestSuite(AuctionDTOUnitTest.class);
        suite.addTestSuite(BidDTOUnitTest.class);
        suite.addTestSuite(AuctionBeanUnitTest.class);

        // package com.orpheus.auction.persistence.impl
        suite.addTestSuite(SQLServerAuctionDAOUnitTest.class);
        suite.addTestSuite(DefaultAuctionTranslatorUnitTest.class);

        // demo test
        suite.addTestSuite(DemoTest.class);
        return suite;
    }
}
