/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import com.orpheus.auction.persistence.UnitTestHelper;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>AuctionDTO</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionDTOUnitTest extends TestCase {
    /** Represents the auction id for testing. */
    private static final Long AUCTION_ID = new Long(1);

    /** Represents the summary for testing. */
    private static final String SUMMARY = "summary";

    /** Represents the description for testing. */
    private static final String DESCRIPTION = "description";

    /** Represents the item count for testing. */
    private static final int ITEM_COUNT = 2;

    /** Represents the minimum BID for testing. */
    private static final int MINIMUN_DID = 3;

    /** Represents the start date for testing. */
    private static final Date START_DATE = new Date();

    /** Represents the end date for testing. */
    private static final Date END_DATE = new Date(START_DATE.getTime() + 100);

    /** Represents the bids for testing. */
    private BidDTO[] bids = null;

    /** Represents the <code>AuctionDTO</code> instance used for testing. */
    private AuctionDTO auctionDTO = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception
     */
    protected void setUp() {
        bids = new BidDTO[2];

        for (int i = 0; i < 2; i++) {
            bids[i] = new BidDTO();
            bids[i].setId(new Long(i + 100));
            bids[i].setTimestamp(new Date());
        }

        auctionDTO = new AuctionDTO();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>AuctionDTO()</code> .
     * </p>
     */
    public void testAuctionDTO_Accuracy() {
        assertNull("The acution id value should be null.",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "id"));
        assertNull("The summary value should be null.",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "summary"));
        assertNull("The description value should be null.",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "description"));
        assertEquals("The itemCount should be default value.", "" + 0,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "itemCount").toString());
        assertEquals("The minimumBid should be default value.", "" + 0,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "minimumBid").toString());
        assertNull("The bids value should be null.",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "bids"));
        assertNull("The start date  value should be null.",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "startDate"));
        assertNull("The end date value should be null.",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "endDate"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>.
     * </p>
     */
    public void testGetID_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "id", AUCTION_ID);
        assertEquals("The bid id should be set properly.", AUCTION_ID, auctionDTO.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setId(Long)</code>.
     * </p>
     */
    public void testSetID_Accuracy() {
        auctionDTO.setId(AUCTION_ID);
        assertEquals("The bid id should be be got properly.", AUCTION_ID,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "id"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getSummary()</code>.
     * </p>
     */
    public void testGetSummary_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "summary", SUMMARY);
        assertEquals("The summary should be set properly.", SUMMARY, auctionDTO.getSummary());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setSummary(String)</code>.
     * </p>
     */
    public void testSetSummary_Accuracy() {
        auctionDTO.setSummary(SUMMARY);
        assertEquals("The summary should be got properly.", SUMMARY,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "summary"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getDescription()</code>.
     * </p>
     */
    public void testGetDescription_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "description", DESCRIPTION);
        assertEquals("The description should be set properly.", DESCRIPTION, auctionDTO.getDescription());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setDescription(String)</code>.
     * </p>
     */
    public void testSetDescriptionAccuracy() {
        auctionDTO.setDescription(DESCRIPTION);
        assertEquals("The description should be got properly.", DESCRIPTION,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "description"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getItemCount()</code>.
     * </p>
     */
    public void testGetItemCount_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "itemCount", new Integer(ITEM_COUNT));
        assertEquals("The item count should be got properly.", ITEM_COUNT, auctionDTO.getItemCount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setItemCount(int)</code>.
     * </p>
     */
    public void testSetItemCount_Accuracy() {
        auctionDTO.setItemCount(ITEM_COUNT);
        assertEquals("The item count should be set properly.", ITEM_COUNT + "",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "itemCount").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getMinimumBid()</code>.
     * </p>
     */
    public void testGetMinimumBid_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "minimumBid", new Integer(MINIMUN_DID));
        assertEquals("The minimum bid should be got properly.", MINIMUN_DID, auctionDTO.getMinimumBid());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setMinimumBid(int)</code>.
     * </p>
     */
    public void testSetMinimumBid_Accuracy() {
        auctionDTO.setMinimumBid(MINIMUN_DID);
        assertEquals("The minimum bid should be set properly.", MINIMUN_DID + "",
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "minimumBid").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getStartDate()</code>.
     * </p>
     */
    public void testGetStartDate_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "startDate", START_DATE);
        assertEquals("The start date value should be got properly.", START_DATE, auctionDTO.getStartDate());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setStartDate(Date)</code>.
     * </p>
     */
    public void testSetStartDate_Accuracy() {
        auctionDTO.setStartDate(START_DATE);
        assertEquals("The start date should be set properly.", START_DATE,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "startDate"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEndDate()</code>.
     * </p>
     */
    public void testGetEndDate_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "endDate", END_DATE);
        assertEquals("The end date value should be got properly.", END_DATE, auctionDTO.getEndDate());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setEndDate(Date)</code>.
     * </p>
     */
    public void testSetEndDate_Accuracy() {
        auctionDTO.setEndDate(END_DATE);
        assertEquals("The end date should be set properly.", END_DATE,
            UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "endDate"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getBids()</code>.
     * </p>
     */
    public void testGetBids_Accuracy() {
        UnitTestHelper.setPrivateField(auctionDTO.getClass(), auctionDTO, "bids", bids);

        BidDTO[] bidDTOs = auctionDTO.getBids();
        assertNotNull("The return should not be null", bidDTOs);
        assertEquals("The length shuold be " + bids.length, bids.length, bidDTOs.length);

        for (int i = 0; i < bidDTOs.length; i++) {
            UnitTestHelper.assertEquals(bids[i], bidDTOs[i], false);
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setBids(BidDTO[])</code>.
     * </p>
     */
    public void testSetBids_Accuracy() {
        auctionDTO.setBids(bids);

        BidDTO[] bidDTOs = (BidDTO[]) UnitTestHelper.getPrivateField(auctionDTO.getClass(), auctionDTO, "bids");

        assertNotNull("The return should not be null", bidDTOs);
        assertEquals("The length shuold be " + bids.length, bids.length, bidDTOs.length);

        for (int i = 0; i < bidDTOs.length; i++) {
            UnitTestHelper.assertEquals(bids[i], bidDTOs[i], false);
        }
    }
}
