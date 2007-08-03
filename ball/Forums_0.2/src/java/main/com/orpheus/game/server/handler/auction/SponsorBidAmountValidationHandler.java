/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.formvalidator.validator.Message;
import com.orpheus.game.server.auction.OrpheusAuctionFunctions;
import com.orpheus.auction.persistence.CustomBid;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>A custom implementation of {@link Handler} interface to be used for validating the bid amount submitted by
 * sponsor. The handler validates that the bid amount is not less than the maximum allowed bid for auction in case a new
 * bid is placed or if a bid amount is greater than current effective bid amount in case the existing bid is updated.
 * </p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorBidAmountValidationHandler extends AbstractAuctionHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the bid amount from.</p>
     */
    public static final String BID_AMOUNT_PARAM_NAME_CONFIG = "bid_amount_param_key";

    /**
     * <p>A <code>String</code> providing the name of the request attribute in which a <code>Map</code> of validation
     * errors is recorded.</p>
     */
    public final static String VALIDATION_ERRORS_ATTRIBUTE = "validationErrors";

    /**
     * <p>A <code>String</code> providing the name of the result to forward the request to in case the new bid amount
     * fails to pass the validation.</p>
     */
    public final static String VALIDATION_ERROR_RESULT_NAME = "validation-error";

    /**
     * <p>Constructs new <code>SponsorBidAmountValidationHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;auctions_request_attr_key&gt;openAuctions&lt;/auctions_request_attr_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public SponsorBidAmountValidationHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsBoolean(element, UPDATE_BID_CONFIG, true);
        readAsString(element, AUCTION_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, BID_AMOUNT_PARAM_NAME_CONFIG, true);
        if (getBoolean(UPDATE_BID_CONFIG).booleanValue()) {
            readAsString(element, BID_ID_PARAM_NAME_CONFIG, true);
        }
    }


    /**
     * <p> Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers. </p>
     *
     * @param context the ActionContext object.
     * @return null or a non-empty resultCode string.
     * @throws IllegalArgumentException if the context is null.
     * @throws HandlerExecutionException if fail to execute this handler.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            HttpServletRequest request = context.getRequest();
            boolean updateBidFlag = getBoolean(UPDATE_BID_CONFIG).booleanValue();

            AuctionManager auctionManager = getAuctionManager(context);
            AuctionPersistence auctionPersistence = getAuctionPersistence(context);
            long auctionId = getLong(AUCTION_ID_PARAM_NAME_CONFIG, request);
            Auction auction = auctionPersistence.getAuction(auctionId);

            int newBidAmount = getInt(BID_AMOUNT_PARAM_NAME_CONFIG, request);

            if (updateBidFlag) {
                // Existing bid is updated
                long bidId = getLong(BID_ID_PARAM_NAME_CONFIG, request);
                Bid[] bids = auction.getBids();
                for (int i = 0; i < bids.length; i++) {
                    CustomBid bid = (CustomBid) bids[i];
                    if (bid.getId().longValue() == bidId) {
                        if (bid.getMaxAmount() >= newBidAmount) {
                            bindError(getString(BID_AMOUNT_PARAM_NAME_CONFIG), "Less than current maximum bid amount",
                                      request);
                            return VALIDATION_ERROR_RESULT_NAME;
                        }
                    }
                }
            } else {
                // New bid is going to be placed - validate the bid amount against minimum allowed bid amount for
                // auction 
                Bid[] leadingBids = auctionManager.getAuctionStrategy().selectLeadingBids(auction);
                int minimumAllowedBid = OrpheusAuctionFunctions.getMinimuAllowedBid(auction, leadingBids);
                if (minimumAllowedBid > newBidAmount) {
                    bindError(getString(BID_AMOUNT_PARAM_NAME_CONFIG), "Less than minimum allowed bid amount", request);
                    return VALIDATION_ERROR_RESULT_NAME;
                }
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not validate the new bid amount", e);
        }
        return null;
    }

    /**
     * <p>Binds the specified error message for a validation error related to specified request parameter.</p>
     *
     * @param paramName a <code>String</code> providing the name of request parameter which had failed the validation.
     * @param errorMessage a <code>String</code> providing the error message to be displayed to user.
     * @param request a <code>HttpServletRequest</code> representing the incoming request. 
     */
    private void bindError(String paramName, String errorMessage, HttpServletRequest request) {
        Map errorMap = (Map) request.getAttribute(VALIDATION_ERRORS_ATTRIBUTE);
        if (errorMap == null) {
            errorMap = new HashMap();
            request.setAttribute(VALIDATION_ERRORS_ATTRIBUTE, errorMap);
        }
        errorMap.put(paramName, new Message(errorMessage, false, Message.ELEMENT_LEVEL_VALIDATION));
    }
}
