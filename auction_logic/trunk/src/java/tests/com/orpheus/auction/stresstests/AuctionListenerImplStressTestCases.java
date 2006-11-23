/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import java.util.Date;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.easymock.MockControl;

import com.orpheus.auction.AuctionListenerImpl;
import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;


/**
 * <p>Stress test case for class <code>AuctionListenerImpl</code>.</p>
 * @author waits
 * @version 1.0
 */
public class AuctionListenerImplStressTestCases extends TestCase {
    /** The number of operations in the stress tests. */
    private static final int OPERATIONS = 500;

    /** AuctionListenerImpl instance for testing. */
    private AuctionListenerImpl listener = null;

    /** Represents the Mock ServletContext. */
    private ServletContext context = null;

    /** Represents the MockControl. */
    private MockControl control = null;

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Helper.addConfigFile(Helper.CONFIG_FILE);
    }

    /**
     * test the auctionComplete(aution) method.
     *
     * @throws Exception into JUnit
     */
    public void testAuctionComplete() throws Exception {
        // set the mock objects properly.
        MockGameDataManager gameDataManager = new MockGameDataManager();
        MockAdministrationManager administrationManager = new MockAdministrationManager();

        // prepare bids
        CustomBid[] bids = new CustomBid[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = new CustomBid(i, i + 1, i + 2, new Date());
            bids[i].setId(10 + i);
        }

        MockAuction auction = new MockAuction();

        auction.setId(30);
        auction.setBids(bids);

        long start = System.currentTimeMillis();

        for (int i = 0; i < OPERATIONS; ++i) {
            control = MockControl.createNiceControl(ServletContext.class);
            context = (ServletContext) control.getMock();
            listener = new AuctionListenerImpl(context);

            control.expectAndReturn(context.getAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY),
                administrationManager);
            control.expectAndReturn(context.getAttribute(KeyConstants.GAME_DATA_MANAGER_KEY), gameDataManager);
            control.replay();

            listener.auctionComplete(auction);
            
            assertEquals("Tested method works incorrectly.", 30, administrationManager.getLastBlockId());
            
            
        }

        long end = System.currentTimeMillis();

        System.out.println("StressTests - AuctionListenerImpl.auctionComplete() : " + OPERATIONS + " operations, " +
            "takes " + (end - start) + " ms");
    }

    /**
     * clear the environement.
     */
    protected void tearDown() throws Exception {
        Helper.clearConfigManager();
    }
}
