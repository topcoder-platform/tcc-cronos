/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.Date;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.BidUpdateHandler;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionManagerImpl;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy tests for the {@link BidUpdateHandler} class. It uses the Cactus framework to run the tests on the
 * servlet container.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class BidUpdateHandlerAccuracyTest extends ServletTestCase {

    /**
     * The XML configuration of the handler.
     */
    private static final String XML = "<handler type=\"x\"> " +
            "<auction_id_param_key>aid</auction_id_param_key>" +
            "<max_amount_param_key>ma</max_amount_param_key>" +
            "<bid_id_param_key>bid</bid_id_param_key>" +
            "</handler>";
    
    /**
     * The AuctionManager used in tests.
     */
    private AuctionManager manager = null;
    
    /**
     * The BidUpdateHandler to test on.
     */
    private BidUpdateHandler handler = null;
    
    /**
     * The UserProfile used in tests.
     */
    private UserProfile userProfile = null;
    
    /**
     * Sets up the test environment, creates required objects.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
        manager = new AuctionManagerImpl();
        handler = new BidUpdateHandler("aid", "bid", "ma");
        userProfile = new UserProfile(new Long(123));
        
        session.getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, manager);
        session.setAttribute("user_profile", userProfile);
    }

    /**
     * Clears after tests.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the {@link BidUpdateHandler#BidUpdateHandler(java.lang.String, java.lang.String, java.lang.String)}
     * constructor accuracy. It checks if internal fields are assigned.
     * 
     * @throws Exception to JUnit.
     */
    public void testBidUpdateHandlerStringStringString() throws Exception {
        BidUpdateHandler handler = new BidUpdateHandler("aid", "bid", "ma");
        assertEquals("Incorrect auction id key", "aid", TestHelper.getFieldValue(handler, "auctionIdParamKey"));
        assertEquals("Incorrect max amount key", "ma", TestHelper.getFieldValue(handler, "maxAmountParamKey"));
        assertEquals("Incorrect image id key", "bid", TestHelper.getFieldValue(handler, "bidIdParamKey"));
    }

    /**
     * Tests the {@link BidUpdateHandler#BidUpdateHandler(org.w3c.dom.Element)} constructor accuracy.
     * It checks if internal fields are assigned.
     * 
     * @throws Exception to JUnit.
     */
    public void testBidUpdateHandlerElement() throws Exception {
        Element el = TestHelper.createElement(XML);
        BidUpdateHandler handler = new BidUpdateHandler(el);
        assertEquals("Incorrect auction id key", "aid", TestHelper.getFieldValue(handler, "auctionIdParamKey"));
        assertEquals("Incorrect max amount key", "ma", TestHelper.getFieldValue(handler, "maxAmountParamKey"));
        assertEquals("Incorrect image id key", "bid", TestHelper.getFieldValue(handler, "bidIdParamKey"));
    }

    /**
     * Sets up the request parameters for the Excute method test.
     * 
     * @param request the web request.
     */
    public void beginExecute(WebRequest request) {
        request.addParameter("aid", "1");
        request.addParameter("bid", "2");
        request.addParameter("ma", "4");
    }
    
    /**
     * Tests the {@link BidUpdateHandler#execute(com.topcoder.web.frontcontroller.ActionContext)} method accuracy.
     * It checks if the request parameters are properly interpreted and the correct bid is updated.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute() throws Exception {
        CustomBid bid = new CustomBid(123, 1, 2, new Date());
        bid.setId(2);
        Auction auction = TestHelper.createAuction(1, 1, -100, 100, new CustomBid[]{});
        manager.createAuction(auction);
        manager.acceptNewBid(1, bid);
        
        handler.execute(new ActionContext(request, response));
        
        auction = manager.getAuctionPersistence().getAuction(1);
        assertEquals("Should have one updated bid", 1, auction.getBids().length);
        Bid newBid = auction.getBids()[0];
        assertEquals("Incorrect new max amount", 4, newBid.getMaxAmount());
        assertEquals("Incorrect bidder id", 123, newBid.getBidderId());
        
    }
    
    
    /**
     * Sets up the request parameters for the Excute method test.
     * 
     * @param request the web request.
     */
    public void beginExecute2(WebRequest request) {
        request.addParameter("aid", "1");
        request.addParameter("bid", "2");
        request.addParameter("ma", "40");
    }
    
    /**
     * Tests the {@link BidUpdateHandler#execute(com.topcoder.web.frontcontroller.ActionContext)} method accuracy.
     * It checks if the request parameters are properly interpreted and the correct bid is updated.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute2() throws Exception {
        CustomBid bid1 = new CustomBid(123, 1, 2, new Date());
        bid1.setId(1);
        
        CustomBid bid2 = new CustomBid(123, 10, 2, new Date());
        bid2.setId(2);
        Auction auction = TestHelper.createAuction(1, 1, -100, 100, new CustomBid[]{});
        manager.createAuction(auction);
        manager.createAuction(TestHelper.createAuction(2, 10, 1, 2, null));
        manager.acceptNewBid(1, bid1);
        manager.acceptNewBid(1, bid2);
        
        handler.execute(new ActionContext(request, response));
        
        auction = manager.getAuctionPersistence().getAuction(1);
        assertEquals("Should have two bids", 2, auction.getBids().length);
        Bid newBid = auction.getBids()[0];
        assertEquals("Incorrect new max amount", 40, newBid.getMaxAmount());
        assertEquals("Incorrect bidder id", 123, newBid.getBidderId());
        
    }

}
