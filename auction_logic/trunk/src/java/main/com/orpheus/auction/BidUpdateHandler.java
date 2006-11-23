/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Date;

import javax.servlet.ServletContext;

import org.w3c.dom.Element;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionException;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * This handler allows to update existing bid in an open auction.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe since it does not contain any mutable state.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class BidUpdateHandler implements Handler {
    /**
     * <p>
     * Tag that contains the key used to get the auction id from the request parameters.
     * </p>
     */
    private static final String AUCTION_ID_PARAM_KEY_TAG_NAME = "auction_id_param_key";

    /**
     * <p>
     * Tag that contains the key used to get the maximum amount value from the request parameters.
     * </p>
     */
    private static final String MAX_AMOUNT_PARAM_KEY_TAG_NAME = "max_amount_param_key";

    /**
     * <p>
     * Tag that contains the key used to get the bid id from the request parameters.
     * </p>
     */
    private static final String BID_ID_PARAM_KEY_TAG_NAME = "bid_id_param_key";

    /**
     * <p>
     * The key used to get the auction id from the request parameters. It's set in the constructor,
     * immutable, cannot be null or empty.
     * </p>
     */
    private final String auctionIdParamKey;

    /**
     * <p>
     * The key used to get the bid id from the request parameters. It's set in the constructor,
     * immutable, cannot be null or empty.
     * </p>
     */
    private final String bidIdParamKey;

    /**
     * <p>
     * The key used to get the maximum amount value from the request parameters. It's set in the
     * constructor, immutable, cannot be null or empty.
     * </p>
     */
    private final String maxAmountParamKey;

    /**
     * <p>
     * Creates a new BidUpdateHandler instance with the given attributes.
     * </p>
     * @param auctionIdParamKey
     *            name of the request parameter with auction id.
     * @param bidIdParamKey
     *            name of the request parameter with bid id.
     * @param maxAmountParamKey
     *            name of the request parameter with maximum amount value.
     * @throws IllegalArgumentException
     *             if one of the arguments is null or empty string.
     */
    public BidUpdateHandler(String auctionIdParamKey, String bidIdParamKey, String maxAmountParamKey) {
        Helper.checkString(auctionIdParamKey, "auctionIdParamKey");
        Helper.checkString(bidIdParamKey, "bidIdParamKey");
        Helper.checkString(maxAmountParamKey, "maxAmountParamKey");
        this.auctionIdParamKey = auctionIdParamKey;
        this.bidIdParamKey = bidIdParamKey;
        this.maxAmountParamKey = maxAmountParamKey;
    }

    /**
     * <p>
     * Creates a new BidUpdateHandler instance with parameters taken from the specified element.
     * Parameters:
     * <ul>
     * <li>auction_id_param_key - key used to get the auction id from the request parameters</li>
     * <li>max_amount_param_key - key used to get the maximum amount value from the request
     * parameters</li>
     * <li>bid_id_param_key - key used to get the bid id from the request parameters</li>
     * </ul>
     * </p>
     * <p>
     * The element must follow the given DTD:
     * <pre>
     * &lt;!ELEMENT handler (auction_id_param_key, leading_bids_key)&gt;
     * &lt;!ATTLIST handler type CDATA #REQUIRED&gt;
     * &lt;!ELEMENT auction_id_param_key (#PCDATA)&gt;
     * &lt;!ELEMENT max_amount_param_key (#PCDATA)&gt;
     * &lt;!ELEMENT bid_id_param_key (#PCDATA)&gt;
     * </pre>
     * This method doesn't check that element follows DTD but only extracts necessary data from it.
     * </p>
     * @param element
     *            the Element object to extract properties.
     * @throws IllegalArgumentException
     *             if argument is null, if it's impossible to retrieve the required properties or
     *             value of any property is an empty string.
     */
    public BidUpdateHandler(Element element) {
        String[] values =
                Helper.parseHandlerElement(element, new String[] {AUCTION_ID_PARAM_KEY_TAG_NAME,
                    MAX_AMOUNT_PARAM_KEY_TAG_NAME,
                    BID_ID_PARAM_KEY_TAG_NAME});
        this.auctionIdParamKey = values[0];
        this.maxAmountParamKey = values[1];
        this.bidIdParamKey = values[2];
    }

    /**
     * <p>
     * Updates existing bid in an open auction. First it finds the original bid by auction id and
     * bid id provided as a request parameters then create a new bid. Updated bid has the id of the
     * currently logged-in user as the bidder id and max amount retrieved from the request
     * parameters. All other parameters are copied from the original bid.
     * </p>
     * <p>
     * The names of the request parameters used to get required data are set in the constructor.
     * </p>
     * @param context
     *            the action context.
     * @return null always.
     * @throws IllegalArgumentException
     *             if context is null.
     * @throws HandlerExecutionException
     *             if any error occurs during operation.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        Helper.checkNotNull(context, "context");

        // get auction id from the request
        long auctionId = Helper.parseLongValue(context, auctionIdParamKey);
        // get bid id from the request
        long bidId = Helper.parseLongValue(context, bidIdParamKey);
        // get new max amount
        int maxAmount = Helper.parseIntValue(context, maxAmountParamKey);

        // get servlet context from the request's session
        ServletContext servletContext = Helper.getServletContext(context);
        // get auction manager from the servlet context
        AuctionManager auctionManager =
                (AuctionManager) Helper.getServletContextAttribute(
                        servletContext, KeyConstants.AUCTION_MANAGER_KEY, AuctionManager.class);

        // get auction with the specified id
        Auction auction = Helper.getAuction(auctionManager, auctionId);

        // get auction bids
        Bid[] bids = auction.getBids();

        // retrieve the original bid
        CustomBid bid = null;
        for (int i = 0; i < bids.length; i++) {
            // all bids must be CustomBid instances
            if (!(bids[i] instanceof CustomBid)) {
                throw new HandlerExecutionException("The auction bids should be CustomBid instances");
            }

            CustomBid custom = (CustomBid) bids[i];
            if (custom.getId() != null && custom.getId().longValue() == bidId) {
                bid = custom;
                break;
            }
        }
        // check that bid exists
        if (bid == null) {
            throw new HandlerExecutionException("The bid with the specified id doesn't exist");
        }

        // retrieve id of the currently logged-in user
        long bidderId = Helper.getLoggedUserId(context);

        // create new bid
        // only bidderId and maxAmount should be changed,
        // other parameters are copied from the original bid
        CustomBid newBid = new CustomBid(bidderId, bid.getImageId(), maxAmount, (Date) bid.getTimestamp().clone());
        newBid.setId(bidId);
        if (bid.getEffectiveAmount() != null) {
            newBid.setEffectiveAmount(bid.getEffectiveAmount().intValue());
        }

        // update bid
        try {
            auctionManager.acceptBidUpdate(auctionId, bid, newBid);
        } catch (AuctionException e) {
            throw new HandlerExecutionException("Unable to update bid", e);
        }

        return null;
    }
}
