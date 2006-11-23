/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import org.apache.cactus.ServletTestCase;
import org.w3c.dom.Element;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.OpenAuctionsHandler;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.impl.AuctionManagerImpl;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Unit tests for the {@link OpenAuctionsHandler} class. It uses the Cactus framework and need to by run on
 * servlet container.
 * @author kr00tki
 * @version 1.0
 */
public class OpenAuctionsHandlerAccuracyTest extends ServletTestCase {
    
    /**
     * The sample XML.
     */
    private static final String XML = "<handler type=\"x\"> " +
            "<open_auction_key>test</open_auction_key > " +
            "</handler>";

    
    /**
     * The AuctionManager instance used in tests.
     */
    private AuctionManager manager = null;
    
    /**
     * Loads the required configuration and creates new AuctionManager instance.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
        manager = new AuctionManagerImpl();
    }

    /**
     * Clears the loaded configuration.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the {@link OpenAuctionsHandler#OpenAuctionsHandler(String)} constructor accuracy.
     * Checks if the field is initialized.
     * 
     * @throws Exception to JUnit.
     */
    public void testOpenAuctionsHandlerString() throws Exception {
        OpenAuctionsHandler handler = new OpenAuctionsHandler("test");
        assertEquals("Internal key not initialized.", "test", TestHelper.getFieldValue(handler, "openAuctionKey"));
    }

    /**
     * Tests the {@link OpenAuctionsHandler#OpenAuctionsHandler(Element)} constructor.
     * Checks if the paramter name is correctly initialized.
     * 
     * @throws Exception to Junit.
     */
    public void testOpenAuctionsHandlerElement() throws Exception {
        Element el = TestHelper.createElement(XML);
        OpenAuctionsHandler handler = new OpenAuctionsHandler(el);
        assertEquals("Internal key not initialized.", "test", TestHelper.getFieldValue(handler, "openAuctionKey"));
    }

    /**
     * Tests the {@link OpenAuctionsHandler#execute(ActionContext)} method.
     * Checks if the correct Auctions are loaded from the persistence and save in the request attribute.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute() throws Exception {
        AuctionManager manager = new AuctionManagerImpl();
        Auction auction = TestHelper.createAuction(1, 1, -10, 10, null);
        manager.createAuction(auction);
        session.getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, manager);
        ActionContext ctx = new ActionContext(request, response);
        
        OpenAuctionsHandler handler = new OpenAuctionsHandler("test");
        handler.execute(ctx);
        Auction[] auctions = (Auction[]) request.getAttribute("test");
        assertEquals("Should have one auction", 1, auctions.length);
        assertSame("Incorrect auction", auction, auctions[0]);
    }
    
    /**
     * Tests the {@link OpenAuctionsHandler#execute(ActionContext)} method.
     * Checks if the empty Auctions array is saved in the request attribute when no auctions exists.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute_NoAuctions() throws Exception {
        session.getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, manager);
        ActionContext ctx = new ActionContext(request, response);
        
        OpenAuctionsHandler handler = new OpenAuctionsHandler("test");
        handler.execute(ctx);
        Auction[] auctions = (Auction[]) request.getAttribute("test");
        assertEquals("Should be empty", 0, auctions.length);
    }

    
    /**
     * Tests the {@link OpenAuctionsHandler#execute(ActionContext)} method.
     * Checks if the correct Auctions are loaded from the persistence and save in the request attribute.
     * 
     * @throws Exception to JUnit.
     */
    public void testExecute2() throws Exception {
        Auction auction = TestHelper.createAuction(1, 1, -10, 10, null);
        manager.createAuction(auction);
        manager.createAuction(TestHelper.createAuction(2, 10, -100, -10, null));
        manager.createAuction(TestHelper.createAuction(3, 10, 10, 1000, null));
        session.getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY, manager);
        ActionContext ctx = new ActionContext(request, response);
        
        OpenAuctionsHandler handler = new OpenAuctionsHandler("test");
        handler.execute(ctx);
        Auction[] auctions = (Auction[]) request.getAttribute("test");
        assertEquals("Should have one auction", 1, auctions.length);
        assertSame("Incorrect auction", auction, auctions[0]);
    }
}
