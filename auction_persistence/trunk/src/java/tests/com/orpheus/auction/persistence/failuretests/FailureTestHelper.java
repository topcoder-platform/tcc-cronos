/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class for Failure test.
 * </p>
 * @author FireIce
 * @version 1.0
 */
class FailureTestHelper {

    /**
     * <p>
     * private constructor preventing instantiation.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * clear up the temporary loaded testing configuration.
     * </p>
     * @throws Exception
     *             If any exception occurs.
     */
    static void releaseNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * load the testing configuration from file.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    static void loadNamespaceFromFile(String file) throws Exception {
        releaseNamespace();

        // load namespace
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(file);
    }

    /**
     * <p>
     * Get the field value of an object.
     * </p>
     * @param obj
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @return the field value
     * @throws Exception
     *             any exception occurs.
     */
    static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * Build an auctionDTO for testing.
     * @param auctionId
     *            the auction id.
     * @param bidNum
     *            the number of the auction
     * @return an AuctionDTO instance
     */
    static AuctionDTO createAuctionDTO(long auctionId, int bidNum) {
        AuctionDTO auction = new AuctionDTO();
        auction.setBids(createBidDTOs(bidNum));
        auction.setDescription("description" + bidNum);
        auction.setSummary("summary" + bidNum);
        auction.setStartDate(new Date(auctionId * 10000));
        auction.setEndDate(new Date(auctionId * 10000 + 5000));
        auction.setId(new Long(auctionId));
        auction.setItemCount(bidNum);
        auction.setMinimumBid(bidNum);

        return auction;
    }

    /**
     * Build an Auction for testing.
     * @param auctionId
     *            the auction id.
     * @param bidNum
     *            the number of the auction.
     * @return an Auction instance.
     */
    public static Auction createAuction(long auctionId, int bidNum) {
        AuctionDTO auctionDTO = createAuctionDTO(auctionId, bidNum);

        return new MockAuction(auctionDTO.getId(), auctionDTO.getSummary(), auctionDTO.getDescription(), auctionDTO
            .getItemCount(), auctionDTO.getMinimumBid(), auctionDTO.getStartDate(), auctionDTO.getEndDate(),
            createBids(bidNum));
    }

    /**
     * Build an array of Bid with special length.
     * @param bidNum
     *            the number of the bidDTO to build.
     * @return array of Bid instance, empty array when bidNum is zero.
     */
    public static Bid[] createBids(int bidNum) {
        BidDTO[] bidDTOs = createBidDTOs(bidNum);
        Bid[] bids = new Bid[bidDTOs.length];

        for (int i = 0; i < bidDTOs.length; i++) {
            CustomBid bid = new CustomBid(bidDTOs[i].getBidderId(), bidDTOs[i].getImageId(), bidDTOs[i].getMaxAmount(),
                bidDTOs[i].getTimestamp());

            if (bidDTOs[i].getEffectiveAmount() != null) {
                bid.setEffectiveAmount(bidDTOs[i].getEffectiveAmount().intValue());
            }

            if (bidDTOs[i].getId() != null) {
                bid.setId(bidDTOs[i].getId().longValue());
            }

            bids[i] = bid;
        }

        return bids;
    }

    /**
     * Build an array of BidDTO with special length.
     * @param bidNum
     *            the number of the bidDTO to build.
     * @return array of BidDTO instance, empty array when bidNum is zero.
     */
    public static BidDTO[] createBidDTOs(int bidNum) {
        Calendar calendar = Calendar.getInstance();

        BidDTO[] bids = new BidDTO[bidNum];

        for (int i = 0; i < bids.length; i++) {
            bids[i] = new BidDTO();
            bids[i].setBidderId(1);

            if (((i + bidNum) % 2) == 0) {
                bids[i].setEffectiveAmount(new Integer(i));
            }

            bids[i].setImageId(3);
            bids[i].setMaxAmount(bidNum + i);

            calendar.add(Calendar.DAY_OF_YEAR, bidNum);
            bids[i].setTimestamp(calendar.getTime());
        }

        return bids;
    }
}
