/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import javax.servlet.ServletContext;

import org.w3c.dom.Element;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * This class allows to determine the current leading bids for the specified auction and assign
 * them (in the form of a Bid[]) as request attribute for use in generating a view.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe since it does not contain any mutable state.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class LeadingBidsHandler implements Handler {
    /**
     * <p>
     * Tag that contains the key used to get the auction id from the request parameters.
     * </p>
     */
    private static final String AUCTION_ID_PARAM_KEY_TAG_NAME = "auction_id_param_key";

    /**
     * <p>
     * Tag that contains the key of the request attribute to store leading bids.
     * </p>
     */
    private static final String LEADING_BIDS_KEY_TAG_NAME = "leading_bids_key";

    /**
     * <p>
     * The key used to get the auction id from the request parameters. It's set in the constructor,
     * immutable, cannot be null or empty.
     * </p>
     */
    private final String auctionIdParamKey;

    /**
     * <p>
     * The key used to store the leading bids in the request attribute. It's set in the
     * constructor, immutable, cannot be null or empty.
     * </p>
     */
    private final String leadingBidsKey;

    /**
     * <p>
     * Creates a new LeadingBidsHandler instance with the given attributes.
     * </p>
     * @param auctionIdParamKey
     *            name of the request parameter with auction id.
     * @param leadingBidsKey
     *            name of the request attribute to store leading bids.
     * @throws IllegalArgumentException
     *             if one of the arguments is null or empty string.
     */
    public LeadingBidsHandler(String auctionIdParamKey, String leadingBidsKey) {
        Helper.checkString(auctionIdParamKey, "auctionIdParamKey");
        Helper.checkString(leadingBidsKey, "leadingBidsKey");
        this.auctionIdParamKey = auctionIdParamKey;
        this.leadingBidsKey = leadingBidsKey;
    }

    /**
     * <p>
     * Creates a new LeadingBidsHandler instance with parameters taken from the specified element.
     * Parameters:
     * <ul>
     * <li>auction_id_param_key - key used to get the auction id from the request parameters</li>
     * <li>leading_bids_key - key used to store the leading bids in the request attribute</li>
     * </ul>
     * </p>
     * <p>
     * The element must follow the given DTD:
     * <pre>
     * &lt;!ELEMENT handler (auction_id_param_key, leading_bids_key)&gt;
     * &lt;!ATTLIST handler type CDATA #REQUIRED&gt;
     * &lt;!ELEMENT auction_id_param_key (#PCDATA)&gt;
     * &lt;!ELEMENT leading_bids_key (#PCDATA)&gt;
     * </pre>
     * This method doesn't check that element follows DTD but only extracts necessary data from it.
     * </p>
     * @param element
     *            the Element object to extract properties.
     * @throws IllegalArgumentException
     *             if argument is null, if it's impossible to retrieve the required properties or
     *             value of any property is an empty string.
     */
    public LeadingBidsHandler(Element element) {
        String[] values =
                Helper.parseHandlerElement(element, new String[] {AUCTION_ID_PARAM_KEY_TAG_NAME,
                    LEADING_BIDS_KEY_TAG_NAME});
        this.auctionIdParamKey = values[0];
        this.leadingBidsKey = values[1];
    }

    /**
     * <p>
     * Determines the current leading bids for the specified auction and assigns them (in the form
     * of a Bid[]) as request attribute.
     * </p>
     * <p>
     * The names of the request parameters and attributes used to get required data and also to
     * store the result are set in the constructor.
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

        // get servlet context from the request's session
        ServletContext servletContext = Helper.getServletContext(context);
        // get auction manager from the servlet context
        AuctionManager auctionManager =
                (AuctionManager) Helper.getServletContextAttribute(
                        servletContext, KeyConstants.AUCTION_MANAGER_KEY, AuctionManager.class);
        // get auction
        Auction auction = Helper.getAuction(auctionManager, Helper.parseLongValue(context, auctionIdParamKey));

        // if auction strategy is null then throw Exception
        if (auctionManager.getAuctionStrategy() == null) {
            throw new HandlerExecutionException("Auction strategy couldn't be null");
        }

        // store auction's leading bids as request attribute
        context.getRequest().setAttribute(
                leadingBidsKey, auctionManager.getAuctionStrategy().selectLeadingBids(auction));

        return null;
    }
}
