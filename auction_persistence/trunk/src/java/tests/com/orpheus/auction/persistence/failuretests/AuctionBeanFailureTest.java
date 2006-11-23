/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import com.orpheus.auction.persistence.ejb.AuctionBean;
import com.orpheus.auction.persistence.ejb.BidDTO;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link com.orpheus.auction.persistence.ejb.AuctionBean}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class AuctionBeanFailureTest extends TestCase {

    /**
     * <p>
     * Represents the <code>{@link com.orpheus.auction.persistence.ejb.AuctionBean}</code> instance used in tests.
     * </p>
     */
    private AuctionBean auctionBean = new AuctionBean();

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.ejb.AuctionBean#createAuction(AuctionDTO)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuction_NullParam() throws Exception {
        try {
            auctionBean.createAuction(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.ejb.AuctionBean#updateAuction(AuctionDTO)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateAuction_NullParam() throws Exception {
        try {
            auctionBean.updateAuction(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.ejb.AuctionBean#updateBids(long, BidDTO[])}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids_NullParam() throws Exception {
        try {
            auctionBean.updateBids(1, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.ejb.AuctionBean#updateBids(long, BidDTO[])}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateBids_ContainsNull() throws Exception {
        try {
            auctionBean.updateBids(1, new BidDTO[] {null});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
