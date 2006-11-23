/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;


/**
 * <p>
 * Tests functionality and error cases of <code>AuctionPersistenceHelper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionPersistenceHelperUnitTest extends TestCase {
    /**
     * <p>
     * Sets up the test environment. The test instance is created. The configuration namespace is loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateNotNull_NullObject() {
        try {
            AuctionPersistenceHelper.validateNotNull(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is not null.
     */
    public void testValidateNotNull_Accuracy() {
        AuctionPersistenceHelper.validateNotNull(new Object(), "name");
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_NullString() {
        try {
            AuctionPersistenceHelper.validateString(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is empty,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_EmptyString() {
        try {
            AuctionPersistenceHelper.validateString(" ", "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given string is correct.
     */
    public void testValidateString_Accuracy() {
        AuctionPersistenceHelper.validateString("string", "name");
    }

    /**
     * Accuracy test for the method <code>validateNotNegative(double, String)</code> when the given double is negative,
     * IllegalArgumentException is expected.
     */
    public void testValidateNotNegative_Negative() {
        try {
            AuctionPersistenceHelper.validateNotNegative(-1, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateNotNegative(double, String)</code> when the given double is not
     * negative.
     */
    public void testValidateNotNegative_Zero() {
        AuctionPersistenceHelper.validateNotNegative(0, "name");
    }

    /**
     * Accuracy test for the method <code>validateNotNegative(double, String)</code> when the given double is not
     * negative.
     */
    public void testValidateNotNegative_Accuracy() {
        AuctionPersistenceHelper.validateNotNegative(10, "name");
    }

    /**
     * Accuracy test for the method <code>validateCollection(Object[], String)</code> when the given object array is
     * null, IllegalArgumentException is expected.
     */
    public void testValidateCollection_NullArray() {
        try {
            AuctionPersistenceHelper.validateCollection(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateCollection(Object[], String)</code> when the given object array
     * contains null element, IllegalArgumentException is expected.
     */
    public void testValidateCollection_NullElement() {
        try {
            Object[] objects = {null};
            AuctionPersistenceHelper.validateCollection(objects, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateCollection(Object[], String)</code> when the given array is correct.
     */
    public void testValidateCollection_Accuracy() {
        Object[] objects = {"tcs1", "tcs2"};
        AuctionPersistenceHelper.validateCollection(objects, "correct");
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the given auction is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NullAuction() {
        try {
            AuctionPersistenceHelper.validateAuctionDTO(null, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the minimumBid of given auction is
     * negative, IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NegativeMinimumBid() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setMinimumBid(-1);

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the itemCount of given auction is
     * negative, IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NegativeItemCount() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setItemCount(-1);

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when bids is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NullBids() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setBids(null);

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the given auction has null Bid
     * elements, IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NullBidElements() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        auction.setBids(new BidDTO[] {null});

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the imageId of one bid is negative,
     * IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NegativeImageId() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setImageId(-1);

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the maxAmount of one bid is
     * negative, IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NegativeMaxAmount() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setMaxAmount(-1);

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateAuctionDTO(AuctionDTO, String)</code> when the effectiveAmount of one bid is
     * negative, IllegalArgumentException is expected.
     */
    public void testValidateAuctionDTO_NegativEffectiveAmount() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        (auction.getBids())[0].setEffectiveAmount(new Integer(-1));

        try {
            AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateAuctionDTO(AuctionDTO, String)</code> when the given auction is
     * correct.
     */
    public void testValidateAuctionDTO_Accuracy() {
        AuctionDTO auction = UnitTestHelper.buildAuctionDTO(1, 5);
        AuctionPersistenceHelper.validateAuctionDTO(auction, "auction");
    }

    /**
     * Test the method of <code>validateBidDTOCollection(BidDTO[], String)</code> when the given bids is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateBidDTOCollection_NullBids() {
        try {
            AuctionPersistenceHelper.validateBidDTOCollection(null, "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTOCollection(BidDTO[], String)</code> when the given bids contain null
     * element, IllegalArgumentException is expected.
     */
    public void testValidateBidDTOCollection_ContainNullBid() {
        BidDTO[] bids = new BidDTO[] {null};

        try {
            AuctionPersistenceHelper.validateBidDTOCollection(bids, "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTOCollection(BidDTO[], String)</code> when the imageId of one bid is
     * negative, IllegalArgumentException is expected.
     */
    public void testValidateBidDTOCollection_NegativeImageId() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setImageId(-1);

        try {
            AuctionPersistenceHelper.validateBidDTOCollection(bids, "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTOCollection(BidDTO[], String)</code> when the maxAmount of one bid is
     * negative, IllegalArgumentException is expected.
     */
    public void testValidateBidDTOCollection_NegativeMaxAmount() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setMaxAmount(-1);

        try {
            AuctionPersistenceHelper.validateBidDTOCollection(bids, "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTOCollection(BidDTO[], String)</code> when the effectiveAmount of one bid
     * is negative, IllegalArgumentException is expected.
     */
    public void testValidateBidDTOCollection_NegativEffectiveAmount() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setEffectiveAmount(new Integer(-1));

        try {
            AuctionPersistenceHelper.validateBidDTOCollection(bids, "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateBidDTOCollection(BidDTO[], String)</code> when the given collection
     * is correct.
     */
    public void testValidateBidDTOCollection_Accuracy() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        AuctionPersistenceHelper.validateBidDTOCollection(bids, "bids");
    }

    /**
     * Test the method of <code>validateBidDTO(BidDTO, String)</code> when the given bid is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateBidDTO_NullBid() {
        try {
            AuctionPersistenceHelper.validateBidDTO(null, "bid");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTO(BidDTO, String)</code> when the imageId of bid is negative,
     * IllegalArgumentException is expected.
     */
    public void testValidateBidDTO_NegativeImageId() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setImageId(-1);

        try {
            AuctionPersistenceHelper.validateBidDTO(bids[0], "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTO(BidDTO, String)</code> when the maxAmount of bid is negative,
     * IllegalArgumentException is expected.
     */
    public void testValidateBidDTO_NegativeMaxAmount() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setMaxAmount(-1);

        try {
            AuctionPersistenceHelper.validateBidDTO(bids[0], "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>validateBidDTO(BidDTO, String)</code> when the effectiveAmount of bid is negative,
     * IllegalArgumentException is expected.
     */
    public void testValidateBidDTO_NegativEffectiveAmount() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        bids[0].setEffectiveAmount(new Integer(-1));

        try {
            AuctionPersistenceHelper.validateBidDTO(bids[0], "bids");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateBidDTO(BidDTO, String)</code> when the given bid is correct.
     */
    public void testValidateBidDTO_Accuracy() {
        BidDTO[] bids = UnitTestHelper.buildBidDTOs(1);
        AuctionPersistenceHelper.validateBidDTO(bids[0], "bids");
    }

    /**
     * Test the method of <code>validateTwoDate(Date, Date)</code> when the from date is after end date.
     * IllegalArgumentException is expected.
     */
    public void testValidateTwoDate_BebinAfterEnd() {
        Calendar calendar = Calendar.getInstance();
        Date begin = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        Date end = calendar.getTime();

        try {
            AuctionPersistenceHelper.validateTwoDate(begin, end);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateTwoDate(BidDTO, String)</code> when the given dates are correct.
     */
    public void testValidateTwoDate_Accuracy() {
        Calendar calendar = Calendar.getInstance();
        Date begin = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        Date end = calendar.getTime();

        AuctionPersistenceHelper.validateTwoDate(begin, end);
    }

    /**
     * Test the method of <code>createObjectFactory(String</code> when the given namespace is incorrect,
     * ObjectInstantiationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObjectFactory_NotCorrect() throws Exception {
        try {
            AuctionPersistenceHelper.createObjectFactory("incorrect");
            fail("ObjectInstantiationException should be thrown.");
        } catch (ObjectInstantiationException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>createObjectFactory(String)</code> when the given namespace is correct.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObjectFactory_Accuracy() throws Exception {
        assertNotNull("The object factory should be created properly.",
            AuctionPersistenceHelper.createObjectFactory("com.orpheus.auction.persistence.objectfactory"));
    }
}
