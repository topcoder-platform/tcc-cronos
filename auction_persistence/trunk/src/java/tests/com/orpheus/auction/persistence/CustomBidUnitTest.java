/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>CustomBid</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CustomBidUnitTest extends TestCase {
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

    /** Represents the <code>CustomBid</code> instance used for testing. */
    private CustomBid customBid = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        customBid = new CustomBid(BIDDER_ID, IMAGE_ID, MAXIMUN_AMOUNT, TIME_STAMP);
    }

    /**
     * Test the constructor <code>CustomBid(long, long, int, Date)</code> when the given timestamp is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCustomBid_NullTimestamp() throws Exception {
        try {
            new CustomBid(BIDDER_ID, IMAGE_ID, MAXIMUN_AMOUNT, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>CustomBid(long, long, int, Date)</code>.
     * </p>
     */
    public void testCustomBid_Accuracy() {
        assertNull("The bid id value should be null.",
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "id"));
        assertEquals("The image id should be set.", IMAGE_ID + "",
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "imageId").toString());
        assertEquals("The bidder id should be set.", BIDDER_ID + "",
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "bidderId").toString());
        assertNull("The effective amount value should be null.",
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "effectiveAmount"));
        assertEquals("The maximum amount should be default value.", "" + MAXIMUN_AMOUNT,
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "maxAmount").toString());
        assertEquals("The date and time value should be set.", TIME_STAMP,
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "timestamp"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>when the bid id is not set.
     * </p>
     */
    public void testGetID_Accuracy1() {
        assertNull("The bid id value should be got properly.", customBid.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>.
     * </p>
     */
    public void testGetID_Accuracy2() {
        UnitTestHelper.setPrivateField(customBid.getClass(), customBid, "id", BID_ID);
        assertEquals("The bid id value should be got properly.", BID_ID, customBid.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getImageId()</code>.
     * </p>
     */
    public void testGetImageId_Accuracy() {
        assertEquals("The image id should be got properly.", IMAGE_ID, customBid.getImageId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setId(Long)</code>.
     * </p>
     */
    public void testSetID_Accuracy() {
        customBid.setId(BID_ID.longValue());
        assertEquals("The bid id should be set properly.", BID_ID,
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "id"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getBidderId()</code>.
     * </p>
     */
    public void testGetBidderId_Accuracy() {
        assertEquals("The bidder id should be got properly.", BIDDER_ID, customBid.getBidderId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEffectiveAmount()</code>when the effectiveAmount id is not set.
     * </p>
     */
    public void testGetEffectiveAmount_Accuracy1() {
        assertNull("The effective amount should be got properly.", customBid.getEffectiveAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEffectiveAmount()</code>.
     * </p>
     */
    public void testGetEffectiveAmount_Accuracy2() {
        UnitTestHelper.setPrivateField(customBid.getClass(), customBid, "effectiveAmount", EFFECTIVE_AMOUNT);
        assertEquals("The effective amount should be got properly.", EFFECTIVE_AMOUNT, customBid.getEffectiveAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setEffectiveAmount(Integer)</code>.
     * </p>
     */
    public void testSetEffectiveAmount_Accuracy() {
        customBid.setEffectiveAmount(EFFECTIVE_AMOUNT.intValue());
        assertEquals("The effective amount should be set properly.", EFFECTIVE_AMOUNT,
            UnitTestHelper.getPrivateField(customBid.getClass(), customBid, "effectiveAmount"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getMaxAmount()</code>.
     * </p>
     */
    public void testGetMaxAmount_Accuracy() {
        assertEquals("The max amount should be got properly.", MAXIMUN_AMOUNT, customBid.getMaxAmount());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getTimestamp()</code>.
     * </p>
     */
    public void testGetTimestamp_Accuracy() {
        assertEquals("The date value should be got properly.", TIME_STAMP, customBid.getTimestamp());
    }
}
