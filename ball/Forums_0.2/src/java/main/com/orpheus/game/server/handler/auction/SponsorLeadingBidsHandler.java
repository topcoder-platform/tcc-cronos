/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.orpheus.auction.KeyConstants;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionPersistenceException;
import com.topcoder.util.auction.AuctionStrategy;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorLeadingBidsHandler extends AbstractAuctionHandler implements Handler {

    /**
     * <p>
     * Creates a new SponsorLeadingBidsHandler instance with parameters taken from the specified element.
     * Parameters:
     * <ul>
     * <li>games_key - key used to get the list of auction games request attribues</li>
     * <li>leading_bids_key - key used to store the leading bids for games in the request attribute</li>
     * </ul>
     * </p>
     * <p>
     * The element must follow the given DTD:
     *
     * <pre>
     * &lt;!ELEMENT handler (games_key, leading_bids_key)&gt;
     * &lt;!ATTLIST handler type CDATA #REQUIRED&gt;
     * &lt;!ELEMENT games_key (#PCDATA)&gt;
     * &lt;!ELEMENT leading_bids_key (#PCDATA)&gt;
     * </pre>
     *
     * This method doesn't check that element follows DTD but only extracts necessary data from it.
     * </p>
     * @param element the Element object to extract properties.
     * @throws IllegalArgumentException if argument is null, if it's impossible to retrieve the
     *         required properties or value of any property is an empty string.
     */
    public SponsorLeadingBidsHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAMES_ATTR_NAME_CONFIG, true);
        readAsString(element, LEADING_BIDS_ATTR_NAME_CONFIG, true);
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
     * @param context the action context.
     * @return null always.
     * @throws IllegalArgumentException if context is null.
     * @throws HandlerExecutionException if any error occurs during operation.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        // get servlet context from the request's session
        HttpSession session = context.getRequest().getSession(false);
        if (session == null) {
            throw new HandlerExecutionException("Session is not associated with the context's request");
        }
        ServletContext servletContext = session.getServletContext();
        HttpServletRequest request = context.getRequest();

        // get auction manager from the servlet context
        AuctionManager auctionManager = (AuctionManager) servletContext.getAttribute(KeyConstants.AUCTION_MANAGER_KEY);
        AuctionStrategy auctionStrategy = auctionManager.getAuctionStrategy();
        if (auctionStrategy == null) {
            throw new HandlerExecutionException("Auction strategy couldn't be null");
        }

        // Get the list of games associated with auctions
        List games = (List) request.getAttribute(getString(GAMES_ATTR_NAME_CONFIG));

        // Build the mapping from auction id to array of leading bids, Auctions and hosting blocks share same IDs
        Map leadingBids = new HashMap();
        Game game;
        for (Iterator iterator = games.iterator(); iterator.hasNext();) {
            game = (Game) iterator.next();
            HostingBlock[] blocks = game.getBlocks();
            for (int i = 0; i < blocks.length; i++) {
                Auction auction = getAuction(auctionManager, blocks[i].getId().longValue());
                leadingBids.put(auction.getId(), auctionStrategy.selectLeadingBids(auction));
            }
        }

        // Put a mapping to request
        request.setAttribute(getString(LEADING_BIDS_ATTR_NAME_CONFIG), leadingBids);
        return null;
    }


    /**
     * <p>Gets the details for the auction identified by specified ID using the specified auction manager.</p>
     *
     * @param auctionManager an <code>AuctionManager</code> providing the services for managing the auctions.
     * @param id a <code>long</code> providing the ID of requested auction to get details for.
     * @return an <code>Auction</code> providing details for requested auction. 
     * @throws HandlerExecutionException if an unrecoverable error occurs or auction is not found.
     */
    private static Auction getAuction(AuctionManager auctionManager, long id) throws HandlerExecutionException {
        AuctionPersistence auctionPersistence = auctionManager.getAuctionPersistence();
        try {
            return auctionPersistence.getAuction(id);
        } catch (AuctionPersistenceException e) {
            throw new HandlerExecutionException("Could not retrieve details for auction [" + id + "] from data store",
                                                e);
        }
    }
}
