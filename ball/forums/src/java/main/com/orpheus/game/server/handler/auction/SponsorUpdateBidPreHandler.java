/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.server.auction.OrpheusAuctionFunctions;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * <p>A custom {@link Handler} implementation to be used for prepearing the data necessary to display the page for
 * updating existing bid. The purpose of this handler is to obtain the details for hosting block, auction, bid
 * and sponsor domain and image and put them to request.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorUpdateBidPreHandler extends AbstractAuctionHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>SponsorUpdateBidPreHandler</code> instance initialized based on the configuration
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
    public SponsorUpdateBidPreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, AUCTION_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, HOSTING_BLOCK_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, BID_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, BID_ATTR_NAME_CONFIG, true);
        readAsString(element, HOSTING_BLOCK_ATTR_NAME_CONFIG, true);
        readAsString(element, AUCTION_ATTR_NAME_CONFIG, true);
        readAsString(element, DOMAIN_ATTR_NAME_CONFIG, true);
        readAsString(element, IMAGE_ATTR_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);

        // Instantiate JNDI context
        String jndiContextName = getElement(element, JNDI_CONTEXT_NAME_CONFIG, true);
        try {
            this.jndiContext = JNDIUtils.getContext(jndiContextName);
        } catch (NamingException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        } catch (ConfigManagerException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        }
    }

    /**
     * <p>Executes this handler when servicing the specified request. Obtains the details for auction and updated bid
     * as well as the lists of domains and associated images for current sponsor and puts them to request.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if a handler execution succeeds; otherwise an exception will be thrown.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);

        // Get the sponsor profile from the session
        UserProfile sponsor = LoginHandler.getAuthenticatedUser(session);
        if (sponsor == null) {
            throw new HandlerExecutionException("The sponsor is not logged to application");
        }
        long sponsorId = ((Long) sponsor.getIdentifier()).longValue();

        try {
            // Load details for sponsor domains and associated images and the hosting block
            Domain[] domains;
            HostingBlock block;
            if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
                GameData gameData = (GameData) getGameDataEJB(this.jndiContext);
                domains = gameData.findDomainsForSponsor(sponsorId);
                block = gameData.getBlock(getLong(HOSTING_BLOCK_ID_PARAM_NAME_CONFIG, request));
            } else {
                GameDataLocal gameData = (GameDataLocal) getGameDataEJB(this.jndiContext);
                domains = gameData.findDomainsForSponsor(sponsorId);
                block = gameData.getBlock(getLong(HOSTING_BLOCK_ID_PARAM_NAME_CONFIG, request));
            }

            // Get details for requested auction and locate the updated bid
            AuctionManager auctionManager
                = (AuctionManager) session.getServletContext().getAttribute(KeyConstants.AUCTION_MANAGER_KEY);
            Auction auction
                = auctionManager.getAuctionPersistence().getAuction(getLong(AUCTION_ID_PARAM_NAME_CONFIG, request));
            long bidId = getLong(BID_ID_PARAM_NAME_CONFIG, request);
            List sponsorBids = OrpheusAuctionFunctions.getSponsorBids(sponsor, auction);
            CustomBid bid;
            CustomBid updatedBid = null;
            for (Iterator iterator = sponsorBids.iterator(); (updatedBid == null) && iterator.hasNext();) {
                bid = (CustomBid) iterator.next();
                if (bid.getId().longValue() == bidId) {
                    updatedBid = bid;
                }
            }

            // Get the image and domain details based on updated bid
            long imageId = updatedBid.getImageId();
            Domain domain = null;
            ImageInfo image = null;
            for (int i = 0; (domain == null) && (i < domains.length); i++) {
                ImageInfo[] images = domains[i].getImages();
                for (int j = 0; (image == null) && (j < images.length); j++) {
                    if (images[j].getId().longValue() == imageId) {
                        image = images[j];
                        domain = domains[i];
                    }
                }
            }

            // Put all data to request
            request.setAttribute(getString(AUCTION_ATTR_NAME_CONFIG), auction);
            request.setAttribute(getString(HOSTING_BLOCK_ATTR_NAME_CONFIG), block);
            request.setAttribute(getString(DOMAIN_ATTR_NAME_CONFIG), domain);
            request.setAttribute(getString(IMAGE_ATTR_NAME_CONFIG), image);
            request.setAttribute(getString(BID_ATTR_NAME_CONFIG), updatedBid);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for placing a bid", e);
        }
    }
}
