/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.Date;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.BidPlacementHandler;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionManagerImpl;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy tests for the {@link BidPlacementHandler } class. It uses the Cactus framework to run the tests
 * on the servlet container.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class BidPlacementHandlerAccuracyTest extends ServletTestCase {

    /**
     * The XML configuration of the handler.
     */
    private static final String XML = "<handler type=\"x\"> " +
            "<auction_id_param_key>aid</auction_id_param_key>" +
            "<max_amount_param_key>ma</max_amount_param_key>" +
            "<image_id_param_key>iid</image_id_param_key>" +
            "</handler>";
    
    /**
     * The AuctionManager used in tests.
     */
    private AuctionManager manager = null;
    
    /**
     * The BidPlacementHandler to test on.
     */
    private BidPlacementHandler handler = null;
    
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
        session.getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, manager);
        handler = new BidPlacementHandler("aid", "ma", "iid");
        userProfile = new UserProfile(new Long(123));
        
        session.setAttribute("user_profile", userProfile);
    }

    /**
     * Clears after tests.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the {@link BidPlacementHandler#BidPlacementHandler(String, String, String)} constructor accuracy.
     * Checks if the internal fields are properly initialized.
     * 
     * @throws Exception to JUnit.
     */
    public void testBidPlacementHandlerStringStringString() throws Exception {
        BidPlacementHandler handler = new BidPlacementHandler("aid", "ma", "iid");
        assertEquals("Incorrect auction id key", "aid", TestHelper.getFieldValue(handler, "auctionIdParamKey"));
        assertEquals("Incorrect max amount key", "ma", TestHelper.getFieldValue(handler, "maxAmountParamKey"));
        assertEquals("Incorrect image id key", "iid", TestHelper.getFieldValue(handler, "imageIdParamKey"));
    }

    /**
     * Tests the {@link BidPlacementHandler#BidPlacementHandler(Element)} constructor accuracy.
     * Checks if the internal fields are properly initialized.
     * 
     * @throws Exception to JUnit.
     */
    public void testBidPlacementHandlerElement() throws Exception {
        Element el = TestHelper.createElement(XML);
        BidPlacementHandler handler = new BidPlacementHandler(el);
        assertEquals("Incorrect auction id key", "aid", TestHelper.getFieldValue(handler, "auctionIdParamKey"));
        assertEquals("Incorrect max amount key", "ma", TestHelper.getFieldValue(handler, "maxAmountParamKey"));
        assertEquals("Incorrect image id key", "iid", TestHelper.getFieldValue(handler, "imageIdParamKey"));
    }

    /**
     * The beggining of the execute method test. It sets the request parameters.
     *  
     * @param request the request.
     */
    public void beginExecute(WebRequest request) {
        request.addParameter("aid", "1");
        request.addParameter("ma", "10");
        request.addParameter("iid", "1");
    }
    
    
    /**
     * Tests the {@link BidPlacementHandler#execute(com.topcoder.web.frontcontroller.ActionContext)} method
     * accuracy. It checks if the request is properly interpreted and new bid is added to the correct auction.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute() throws Exception {
        manager.createAuction(TestHelper.createAuction(1, 1, -100, 100, null));
        
        handler.execute(new ActionContext(request, response));
        Auction auction = manager.getAuctionPersistence().getAuction(1);
        Bid[] bids = auction.getBids();
        assertEquals("Should have new bid", 1, bids.length);
        assertEquals("Incorrect bidder id", 123, bids[0].getBidderId());
        assertEquals("Incorrect max amount", 10, bids[0].getMaxAmount());
        assertEquals("Incorrect image id", 1, ((CustomBid) bids[0]).getImageId());
    }
    
    
    /**
     * The beggining of the execute method test. It sets the request parameters.
     *  
     * @param request the request.
     */
    public void beginExecute2(WebRequest request) {
        request.addParameter("aid", "1");
        request.addParameter("ma", "11");
        request.addParameter("iid", "1");
    }
    
    
    /**
     * Tests the {@link BidPlacementHandler#execute(com.topcoder.web.frontcontroller.ActionContext)} method
     * accuracy. It checks if the request is properly interpreted and new bid is added to the correct auction.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute2() throws Exception {
        CustomBid bid = new CustomBid(1, 1, 10, new Date());
        bid.setEffectiveAmount(10);
        manager.createAuction(TestHelper.createAuction(1, 10, -100, 100, 
                new Bid[] {bid}));
        
        handler.execute(new ActionContext(request, response));
        Auction auction = manager.getAuctionPersistence().getAuction(1);
        Bid[] bids = auction.getBids();
        assertEquals("Should have two bids", 2, bids.length);
        
    }

}
