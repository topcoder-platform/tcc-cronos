/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Date;

import javax.servlet.ServletContext;

import org.w3c.dom.Element;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.AuctionException;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * This handler allows to place new bids in an open auction.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe since it does not contain any mutable state.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class BidPlacementHandler implements Handler {

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
     * Tag that contains the key used to get the image id from the request parameters.
     * </p>
     */
    private static final String IMAGE_ID_PARAM_KEY_TAG_NAME = "image_id_param_key";

    /**
     * <p>
     * The key used to get the auction id from the request parameters. It's set in the constructor,
     * immutable, cannot be null or empty.
     * </p>
     */
    private final String auctionIdParamKey;

    /**
     * <p>
     * The key used to get the maximum amount value from the request parameters. It's set in the
     * constructor, immutable, cannot be null or empty.
     * </p>
     */
    private final String maxAmountParamKey;

    /**
     * <p>
     * The key used to get the image id from the request parameters. It's set in the constructor,
     * immutable, cannot be null or empty.
     * </p>
     */
    private final String imageIdParamKey;

    /**
     * <p>
     * Creates a new BidPlacementHandler instance with the given attributes.
     * </p>
     * @param auctionIdParamKey
     *            name of the request parameter with auction id.
     * @param maxAmountParamKey
     *            name of the request parameter with maximum amount value.
     * @param imageIdParamKey
     *            name of the request parameter with image id.
     * @throws IllegalArgumentException
     *             if one of the arguments is null or empty string.
     */
    public BidPlacementHandler(String auctionIdParamKey, String maxAmountParamKey, String imageIdParamKey) {
        Helper.checkString(auctionIdParamKey, "auctionIdParamKey");
        Helper.checkString(maxAmountParamKey, "maxAmountParamKey");
        Helper.checkString(imageIdParamKey, "imageIdParamKey");
        this.auctionIdParamKey = auctionIdParamKey;
        this.maxAmountParamKey = maxAmountParamKey;
        this.imageIdParamKey = imageIdParamKey;
    }

    /**
     * <p>
     * Creates a new BidUpdateHandler instance with parameters taken from the specified element.
     * Parameters:
     * <ul>
     * <li>auction_id_param_key - key used to get the auction id from the request parameters</li>
     * <li>max_amount_param_key - key used to get the maximum amount value from the request
     * parameters</li>
     * <li>image_id_param_key - key used to get the image id from the request parameters</li>
     * </ul>
     * </p>
     * <p>
     * The element must follow the given DTD:
     * <pre>
     * &lt;!ELEMENT handler (auction_id_param_key, leading_bids_key)&gt;
     * &lt;!ATTLIST handler type CDATA #REQUIRED&gt;
     * &lt;!ELEMENT auction_id_param_key (#PCDATA)&gt;
     * &lt;!ELEMENT max_amount_param_key (#PCDATA)&gt;
     * &lt;!ELEMENT image_id_param_key (#PCDATA)&gt;
     * </pre>
     * This method doesn't check that element follows DTD but only extracts necessary data from it.
     * </p>
     * @param element
     *            the Element object to extract properties.
     * @throws IllegalArgumentException
     *             if argument is null, if it's impossible to retrieve the required properties or
     *             value of any property is an empty string.
     */
    public BidPlacementHandler(Element element) {
        String[] values =
                Helper.parseHandlerElement(element, new String[] {AUCTION_ID_PARAM_KEY_TAG_NAME,
                    MAX_AMOUNT_PARAM_KEY_TAG_NAME,
                    IMAGE_ID_PARAM_KEY_TAG_NAME});
        this.auctionIdParamKey = values[0];
        this.maxAmountParamKey = values[1];
        this.imageIdParamKey = values[2];
    }

    /**
     * <p>
     * Places new bid in an open auction. New bid has the auction id, image id and max amount value
     * retrieved from the request parameters while the bidder id is the id of currently logged
     * user.
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
        // get new max amount
        int maxAmount = Helper.parseIntValue(context, maxAmountParamKey);
        // get image id from the request
        long imageId = Helper.parseLongValue(context, imageIdParamKey);

        // get servlet context from the request's session
        ServletContext servletContext = Helper.getServletContext(context);
        // get auction manager from the servlet context
        AuctionManager auctionManager =
                (AuctionManager) Helper.getServletContextAttribute(
                        servletContext, KeyConstants.AUCTION_MANAGER_KEY, AuctionManager.class);

        // retrieve id of the currently logged-in user
        long bidderId = Helper.getLoggedUserId(context);

        // create new bid
        CustomBid bid = new CustomBid(bidderId, imageId, maxAmount, new Date());

        // place bid
        try {
            auctionManager.acceptNewBid(auctionId, bid);
        } catch (AuctionException e) {
            throw new HandlerExecutionException("Unable to place new bid", e);
        }

        return null;
    }
}
