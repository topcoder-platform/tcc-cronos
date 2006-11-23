/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.Date;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.LeadingBidsHandler;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionManagerImpl;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy tests for the {@link LeadingBidsHandler} class. It uses the Cactus framework to run the test on the
 * servlet container.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class LeadingBidsHandlerAccuracyTest extends ServletTestCase {
   
    /**
     * The XML used to configure the handler.
     */
    private static final String XML = "<handler type=\"x\"> " +
            "<auction_id_param_key>aid</auction_id_param_key>" +
            "<leading_bids_key>bk</leading_bids_key>" +
            "</handler>";

    /**
     * The auction manager used in tests.
     */
    private AuctionManager manager = null;
    
    /**
     * The LeadingBidsHandler handler used in tests.
     */
    private LeadingBidsHandler handler = null;

    /**
     * Sets up the test environment.
     * 
     *  @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
        manager = new AuctionManagerImpl();
        session.getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, manager);
        handler = new LeadingBidsHandler("aid", "key");
    }

    /**
     * Clears the configuration.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the {@link LeadingBidsHandler#LeadingBidsHandler(String, String)} constructor accuracy.
     * Checks if the internal fields are properly initialized.
     * 
     * @throws Exception to JUnit.
     */
    public void testLeadingBidsHandlerStringString() throws Exception {
        LeadingBidsHandler handler = new LeadingBidsHandler("aid", "key");
        assertEquals("Incorrect auction id key", "aid", TestHelper.getFieldValue(handler, "auctionIdParamKey"));
        assertEquals("Incorrect bids key", "key", TestHelper.getFieldValue(handler, "leadingBidsKey"));
    }

    /**
     * Tests the {@link LeadingBidsHandler#LeadingBidsHandler(org.w3c.dom.Element)} constructor accuracy.
     * Checks if the internal fields are properly initialized.
     * 
     * @throws Exception to JUnit.
     */
    public void testLeadingBidsHandlerElement() throws Exception {
        Element el = TestHelper.createElement(XML);
        LeadingBidsHandler handler = new LeadingBidsHandler(el);
        assertEquals("Incorrect auction id key", "aid", TestHelper.getFieldValue(handler, "auctionIdParamKey"));
        assertEquals("Incorrect bids key", "bk", TestHelper.getFieldValue(handler, "leadingBidsKey"));
    }

    
    /**
     * Sets up the request for the execute method test.
     * 
     * @param request the request object.
     * @throws Exception to JUnit.
     */
    public void beginExecute(WebRequest request) throws Exception {
        request.addParameter("aid", "123");
    }
    
    /**
     * Tests the {@link LeadingBidsHandler#execute(ActionContext)} method.
     * Checks if the request paramter is parsed and correct bid is returned for it.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute() throws Exception {
        CustomBid bid1 = new CustomBid(1, 1, 10, new Date());
        bid1.setEffectiveAmount(2);
        bid1.setId(1);
        Bid bid2 = new CustomBid(2, 2, 20, new Date(System.currentTimeMillis() + 1000));
        bid1.setEffectiveAmount(3);
        bid1.setId(2);
        
        CustomBid bid3 = new CustomBid(3, 3, 30, new Date(System.currentTimeMillis() + 2000));
        bid3.setEffectiveAmount(10);
        bid3.setId(3);
        
        Auction auction = TestHelper.createAuction(123, 1, 10, 11, new Bid[] {bid1, bid2, bid3});
        manager.createAuction(auction);
        manager.createAuction(TestHelper.createAuction(1, 1, 10, 11, new Bid[] {bid1}));
        
        assertNull("Should return null.", handler.execute(new ActionContext(request, response)));
        
        Bid[] bids = (Bid[]) request.getAttribute("key");
        assertNotNull("The leading bids attribute should not be null.", bids);
        assertEquals("Should have one bid", 1, bids.length);
        assertEquals("Incorrect bid returned.", 3, ((CustomBid) bids[0]).getId().longValue());
        assertEquals("Incorrect bidder returned.", 3, bids[0].getBidderId());
    }
    
    /**
     * Sets up the request for the execute method test.
     * 
     * @param request the request object.
     * @throws Exception to JUnit.
     */
    public void beginExecute_NoBids(WebRequest request) throws Exception {
        request.addParameter("aid", "123");
    }
    
    /**
     * Tests the {@link LeadingBidsHandler#execute(ActionContext)} method.
     * Checks if the request paramter is parsed and if the empty array is returned in request attribute.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute_NoBids() throws Exception {
        Auction auction = TestHelper.createAuction(123, 1, 10, 11, null);
        manager.createAuction(auction);
        
        assertNull("Should return null.", handler.execute(new ActionContext(request, response)));
        
        Bid[] bids = (Bid[]) request.getAttribute("key");
        assertNotNull("The leading bids attribute should not be null.", bids);
        assertEquals("Should have no bids", 0, bids.length);
    }

}
