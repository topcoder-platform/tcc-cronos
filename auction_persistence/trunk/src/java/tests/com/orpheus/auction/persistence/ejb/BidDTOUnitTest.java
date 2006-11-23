/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import com.orpheus.auction.persistence.UnitTestHelper;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>BidDTO</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BidDTOUnitTest extends TestCase {
    /** Represents the bid id for testing. */
    private static final Long BID_ID = new Long(1);

    /** Represents the image id for testing. */
    private static final long IMAGE_ID = 2;

    /** Represents the bidder id for testing. */
    private static final long BIDDER_ID = 3;

    /** Represents the effective amount for testing. */
    private static final Integer EFFECTIVE_AMOUNT = new Integer(4);

    /** Represents the maximum amount for testing. */
    private static final int MAXIMUN_AMOUNT = 5;

    /** Represents the date and time for testing. */
    private static final Date TIME_STAMP = new Date();

    /** Represents the <code>BidDTO</code> instance used for testing. */
    private BidDTO bidDTO = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        bidDTO = new BidDTO();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>BidDTO()</code> .
     * </p>
     */
    public void testBidDTO_Accuracy() {
        assertNull("The bid id value should be null.", UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "id"));
        assertEquals("The image id should be default value.", "" + 0,
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "imageId").toString());
        assertEquals("The bidder id should be default value.", "" + 0,
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "bidderId").toString());
        assertNull("The effective amount value should be null.",
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "effectiveAmount"));
        assertEquals("The maximum amount should be default value.", "" + 0,
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "maxAmount").toString());
        assertNull("The date and time value should be null.",
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "timestamp"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> when the bid id is not set.
     * </p>
     */
    public void testGetID_Accuracy1() {
        assertNull("The bid id value should be got properly.", bidDTO.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>.
     * </p>
     */
    public void testGetID_Accuracy2() {
        UnitTestHelper.setPrivateField(bidDTO.getClass(), bidDTO, "id", BID_ID);
        assertEquals("The bid id value should be got properly.", BID_ID, bidDTO.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setId(Long)</code>.
     * </p>
     */
    public void testSetID_Accuracy() {
        bidDTO.setId(BID_ID);
        assertEquals("The bid id should be set properly.", BID_ID,
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "id"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getImageId()</code>when the image id is not set.
     * </p>
     */
    public void testGetImageId_Accuracy1() {
        assertEquals("The image id should be got properly.", 0, bidDTO.getImageId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getImageId()</code>.
     * </p>
     */
    public void testGetImageId_Accuracy2() {
        UnitTestHelper.setPrivateField(bidDTO.getClass(), bidDTO, "imageId", new Long(IMAGE_ID));
        assertEquals("The image id should be got properly.", IMAGE_ID, bidDTO.getImageId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setImageId(long)</code>.
     * </p>
     */
    public void testSetImageId_Accuracy() {
        bidDTO.setImageId(IMAGE_ID);
        assertEquals("The image id should be set properly.", IMAGE_ID + "",
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "imageId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getBidderId()</code>when the bidder id is not set.
     * </p>
     */
    public void testGetBidderId_Accuracy1() {
        assertEquals("The bidder id should be got properly.", 0, bidDTO.getBidderId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getBidderId()</code>.
     * </p>
     */
    public void testGetBidderId_Accuracy2() {
        UnitTestHelper.setPrivateField(bidDTO.getClass(), bidDTO, "bidderId", new Long(BIDDER_ID));
        assertEquals("The bidder id should be got properly.", BIDDER_ID, bidDTO.getBidderId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setBidderId(long)</code>.
     * </p>
     */
    public void testSetBidderId_Accuracy() {
        bidDTO.setBidderId(BIDDER_ID);
        assertEquals("The bidder id should be set properly.", BIDDER_ID + "",
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "bidderId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEffectiveAmount()</code>when the effectiveAmount id is not set.
     * </p>
     */
    public void testGetEffectiveAmount_Accuracy1() {
        assertNull("The effective amount should be got properly.", bidDTO.getEffectiveAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEffectiveAmount()</code>.
     * </p>
     */
    public void testGetEffectiveAmount_Accuracy2() {
        UnitTestHelper.setPrivateField(bidDTO.getClass(), bidDTO, "effectiveAmount", EFFECTIVE_AMOUNT);
        assertEquals("The effective amount should be got properly.", EFFECTIVE_AMOUNT, bidDTO.getEffectiveAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setEffectiveAmount(Integer)</code>.
     * </p>
     */
    public void testSetEffectiveAmount_Accuracy() {
        bidDTO.setEffectiveAmount(EFFECTIVE_AMOUNT);
        assertEquals("The effective amount should be set properly.", EFFECTIVE_AMOUNT,
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "effectiveAmount"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getMaxAmount()</code>when the max amount is not set.
     * </p>
     */
    public void testGetMaxAmount_Accuracy1() {
        assertEquals("The max amount should be got properly.", 0, bidDTO.getMaxAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getMaxAmount()</code>.
     * </p>
     */
    public void testGetMaxAmount_Accuracy2() {
        UnitTestHelper.setPrivateField(bidDTO.getClass(), bidDTO, "maxAmount", new Integer(MAXIMUN_AMOUNT));
        assertEquals("The max amount should be got properly.", MAXIMUN_AMOUNT, bidDTO.getMaxAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setMaxAmount(int)</code>.
     * </p>
     */
    public void testSetMaxAmount_Accuracy() {
        bidDTO.setMaxAmount(MAXIMUN_AMOUNT);
        assertEquals("The max amount should be set properly.", MAXIMUN_AMOUNT + "",
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "maxAmount").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getTimestamp()</code>when the date is not set.
     * </p>
     */
    public void testGetTimestamp_Accuracy1() {
        assertNull("The date value should be got properly.", bidDTO.getTimestamp());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getTimestamp()</code>.
     * </p>
     */
    public void testGetTimestamp_Accuracy2() {
        UnitTestHelper.setPrivateField(bidDTO.getClass(), bidDTO, "timestamp", TIME_STAMP);
        assertEquals("The date value should be got properly.", TIME_STAMP, bidDTO.getTimestamp());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setTimestamp(Date)</code>.
     * </p>
     */
    public void testSetTimestamp_Accuracy() {
        bidDTO.setTimestamp(TIME_STAMP);
        assertEquals("The date should be set properly.", TIME_STAMP,
            UnitTestHelper.getPrivateField(bidDTO.getClass(), bidDTO, "timestamp"));
    }
}
