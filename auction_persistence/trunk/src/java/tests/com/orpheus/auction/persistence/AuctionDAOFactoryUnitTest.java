/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>AuctionDAOFactory</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionDAOFactoryUnitTest extends TestCase {
    /**
     * <p>
     * Sets up the test environment. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * Accuracy test for the method <code>getAuctionDAO()</code> when the namespace is correct. No exception is
     * expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAO_Accuracy1() throws Exception {
        assertNotNull("The AuctionDAO instance should be created.", AuctionDAOFactory.getAuctionDAO());
    }

    /**
     * Accuracy test for the method <code>getAuctionDAO()</code> when the namespace is correct. The AuctionDAO instance
     * retrieved from the second time should be the same as the one of the first time.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetAuctionDAO_Accuracy2() throws Exception {
        assertSame("The AuctionDAO instance should be the same.", AuctionDAOFactory.getAuctionDAO(),
            AuctionDAOFactory.getAuctionDAO());
    }
}
