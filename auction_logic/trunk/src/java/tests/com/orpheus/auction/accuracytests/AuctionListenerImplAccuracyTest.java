/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.Date;

import org.apache.cactus.ServletTestCase;

import com.orpheus.auction.AuctionListenerImpl;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

/**
 * Accuracy tests for {@link AuctionListenerImpl} class. It tests uses the Cactus framework
 * to run the tests on the server container. 
 * 
 * @author kr00tki
 * @version 1.0
 */
public class AuctionListenerImplAccuracyTest extends ServletTestCase {

    /**
     * The custom GameDataManager used in tests.
     */
    private DummyGameDataManager dataManager = null;
    
    /**
     * The custom AdministrationManager used in tests. 
     */
    private DummyAdministrationManager admManager = null;
    
    /**
     * Sets up the test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
        dataManager = new DummyGameDataManager();
        admManager = new DummyAdministrationManager();
        
        session.getServletContext().setAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY, admManager);
        session.getServletContext().setAttribute(KeyConstants.GAME_DATA_MANAGER_KEY, dataManager);
    }

    /**
     * Clears after the test.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the {@link AuctionListenerImpl#AuctionListenerImpl(javax.servlet.ServletContext)} constructor
     * accuracy. Checks if the internal context is initialized.
     * 
     *  @throws Exception to JUnit.
     */
    public void testAuctionListenerImpl() throws Exception {
        AuctionListenerImpl impl = new AuctionListenerImpl(session.getServletContext());
        assertSame("The servlet context is not set.", session.getServletContext(), 
                TestHelper.getFieldValue(impl, "context"));
    }

    /**
     * Tests the {@link AuctionListenerImpl#auctionComplete(com.topcoder.util.auction.Auction)} method accuracy.
     * Checks if the proper methods are call on the GameDataManager and AdministrationManager.
     * 
     * @throws Exception to JUnit.
     */
    public void testAuctionComplete() {
        CustomBid bid = new CustomBid(1, 1, 1, new Date());
        bid.setId(10);
        Auction auction = TestHelper.createAuction(5, 1, -10, 0, new Bid[] {bid});
        
        AuctionListenerImpl listener = new AuctionListenerImpl(session.getServletContext());
        listener.auctionComplete(auction);
        
        assertEquals("Incorrect block id", 5, dataManager.getBlockId());
        assertEquals("Should have one bid id", 1, dataManager.getBidIds().length);
        assertEquals("Incorrect bid id.", 10, dataManager.getBidIds()[0]);
        
        assertEquals("Incorrect block id.", 5, admManager.getBlockId());
    }
    
    /**
     * Tests the {@link AuctionListenerImpl#auctionComplete(com.topcoder.util.auction.Auction)} method accuracy.
     * Checks if the proper methods are call on the GameDataManager and AdministrationManager.
     * 
     * @throws Exception to JUnit.
     */
    public void testAuctionComplete_NoBids() {
        Auction auction = TestHelper.createAuction(5, 1, -10, 0, new Bid[0]);
        
        AuctionListenerImpl listener = new AuctionListenerImpl(session.getServletContext());
        listener.auctionComplete(auction);
        
        assertEquals("Incorrect block id", 5, dataManager.getBlockId());
        assertEquals("Should have no bid ids", 0, dataManager.getBidIds().length);
        
        assertEquals("Incorrect block id.", 5, admManager.getBlockId());
    }

}
