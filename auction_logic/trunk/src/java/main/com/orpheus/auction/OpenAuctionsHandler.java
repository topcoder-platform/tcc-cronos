/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.w3c.dom.Element;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionPersistenceException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * This class allows to retrieve the currently open auctions (those that have started but not yet
 * ended) and assign them (in the form of an Auction[]) as request attribute for use in generating a
 * view.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe since it does not contain any mutable state.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class OpenAuctionsHandler implements Handler {

    /**
     * <p>
     * Tag that contains the key of the request attribute to store retrieved auctions.
     * </p>
     */
    private static final String OPEN_AUCTION_KEY_TAG_NAME = "open_auction_key";

    /**
     * <p>
     * The key used to store the open auctions in the request attribute. It's set in the
     * constructor, immutable, cannot be null or empty.
     * </p>
     */
    private final String openAuctionKey;

    /**
     * <p>
     * Creates a new OpenAuctionsHandler instance with the given request attribute key.
     * </p>
     * @param openAuctionKey name of the request attribute to store retrieved auctions.
     * @throws IllegalArgumentException if argument is null or empty string.
     */
    public OpenAuctionsHandler(String openAuctionKey) {
        Helper.checkString(openAuctionKey, "openAuctionKey");
        this.openAuctionKey = openAuctionKey;
    }

    /**
     * <p>
     * Creates a new OpenAuctionsHandler instance with parameter taken from the specified element.
     * <ul>
     * <li>open_auction_key - key used to store the open auctions as the request attribute</li>
     * </ul>
     * </p>
     * <p>
     * The element must follow the given DTD:
     *
     * <pre>
     * &lt;!ELEMENT handler (open_auction_key)&gt;
     * &lt;!ATTLIST handler type CDATA #REQUIRED&gt;
     * &lt;!ELEMENT open_auction_key (#PCDATA)&gt;
     * </pre>
     *
     * </p>
     * <p>
     * Constructor doesn't check that element follows DTD but only extracts necessary data from it.
     * </p>
     * @param element the Element object to extract properties.
     * @throws IllegalArgumentException if argument is null, it's impossible to retrieve the
     *         required property or property value is an empty string.
     */
    public OpenAuctionsHandler(Element element) {
        String[] values = Helper.parseHandlerElement(element,
                new String[] {OPEN_AUCTION_KEY_TAG_NAME});
        this.openAuctionKey = values[0];
    }

    /**
     * <p>
     * Retrieves the currently open auctions (those that have started but not yet ended) and assign
     * them (in the form of an Auction[]) as request attribute.
     * </p>
     * @param context the action context that holds the .
     * @return null always.
     * @throws IllegalArgumentException if context is null.
     * @throws HandlerExecutionException if any error occurs during operation.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        Helper.checkNotNull(context, "context");

        // get servlet context from the request's session
        ServletContext servletContext = Helper.getServletContext(context);
        // get auction manager from the servlet context
        AuctionManager auctionManager = (AuctionManager) Helper.getServletContextAttribute(
                servletContext, KeyConstants.AUCTION_MANAGER_KEY, AuctionManager.class);
        // get auction persistence from the auction manager
        AuctionPersistence auctionPersistence = auctionManager.getAuctionPersistence();
        if (auctionPersistence == null) {
            throw new HandlerExecutionException(
                    "The auction persistence must present within auction manager");
        }

        // retrieve all auctions
        Auction[] auctions;
        try {
            auctions = auctionPersistence.findAuctionsByDate(null, null);
        } catch (AuctionPersistenceException e) {
            throw new HandlerExecutionException("Unable to retrieve auctions from persistence", e);
        }

        // stores open auctions to the list
        List res = new ArrayList();
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < auctions.length; i++) {
            if (auctions[i].getStartDate().getTime() <= currentTime
                    && currentTime <= auctions[i].getEndDate().getTime()) {
                res.add(auctions[i]);
            }
        }

        // stores result as request attribute
        context.getRequest().setAttribute(openAuctionKey, res.toArray(new Auction[res.size()]));

        return null;
    }
}
