/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.server.util.dropdown.DropDownDataImpl;
import com.orpheus.game.server.util.dropdown.DropDownItemImpl;
import com.orpheus.auction.KeyConstants;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>A custom {@link Handler} implementation to be used for prepearing the data necessary to display the page for
 * placing new or updating existing bid. The purpose of this handler is to obtain the details for hosting block, auction
 * and sponsor domains and images and put them to request. Currently there is no such handler provided by any of
 * existing components.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorPlaceBidPreHandler extends AbstractAuctionHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>SponsorPlaceBidPreHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
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
    public SponsorPlaceBidPreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, AUCTION_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, HOSTING_BLOCK_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, DOMAIN_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, IMAGE_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, HOSTING_BLOCK_ATTR_NAME_CONFIG, true);
        readAsString(element, AUCTION_ATTR_NAME_CONFIG, true);
        readAsString(element, DOMAINS_ATTR_NAME_CONFIG, true);
        readAsString(element, IMAGES_ATTR_NAME_CONFIG, true);
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
     * <p>Executes this handler when servicing the specified request. Obtains the auctions from the request and locates
     * the list of games which correspond to provided auctions. The list of such games is bound to request for further
     * use.</p>
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

            // Convert domains and images data to a format suitable to Drop Down Manager
            long currentDomainId = -1;
            String parameter = request.getParameter(getString(DOMAIN_ID_PARAM_NAME_CONFIG));
            if ((parameter != null) && (parameter.trim().length() > 0)) {
                currentDomainId = Long.parseLong(parameter);
            }
            long currentImageId = -1;
            parameter = request.getParameter(getString(IMAGE_ID_PARAM_NAME_CONFIG));
            if ((parameter != null) && (parameter.trim().length() > 0)) {
                currentImageId = Long.parseLong(parameter);
            }
            DropDownDataImpl domainsDropDownData = buildDomainsDropDownData(domains, currentDomainId);
            DropDownDataImpl imagesDropDownData = buildImagesDropDownData(domains, domainsDropDownData, currentImageId);

            // Get details for requested auction and hosting block
            AuctionManager auctionManager
                = (AuctionManager) session.getServletContext().getAttribute(KeyConstants.AUCTION_MANAGER_KEY);
            Auction auction
                = auctionManager.getAuctionPersistence().getAuction(getLong(AUCTION_ID_PARAM_NAME_CONFIG, request));

            // Put all data to request
            request.setAttribute(getString(AUCTION_ATTR_NAME_CONFIG), auction);
            request.setAttribute(getString(HOSTING_BLOCK_ATTR_NAME_CONFIG), block);
            request.setAttribute(getString(DOMAINS_ATTR_NAME_CONFIG), domainsDropDownData);
            request.setAttribute(getString(IMAGES_ATTR_NAME_CONFIG), imagesDropDownData);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for placing a bid", e);
        }
    }

    /**
     * <p>Builds the drop-down data list from the specified list of domains.</p>
     *
     * @param domains a <code>Domain</code> array providing the details for domain.
     * @param currentDomainId a <code>long</code> providing the optional ID of a domain which might have been selected
     *        by the user.
     * @return a <code>DropDownData</code> to be used for presenting the domains as drop-down list.
     */
    private static DropDownDataImpl buildDomainsDropDownData(Domain[] domains, long currentDomainId) {
        DropDownItemImpl domain;
        DropDownDataImpl data = new DropDownDataImpl();
        for (int i = 0; i < domains.length; i++) {
            domain = new DropDownItemImpl(String.valueOf(domains[i].getId()), domains[i].getDomainName());
            if (domains[i].getId().longValue() == currentDomainId) {
                domain.setSelected(true);
            }
            data.addDropDownItem(domain);
        }
        return data;
    }

    /**
     * <p>Builds the drop-down data list for images associated with domains from the specified list of domains.</p>
     *
     * @param domains a <code>Domain</code> array providing the details for domain.
     * @param domainsDropDown a <code>DropDownData</code> providing the details for domains drop-down list.
     * @param currentImageId a <code>long</code> providing the optional ID of an image which might have been selected
     *        by the user.
     * @return a <code>DropDownData</code> to be used for presenting the domains as drop-down list.
     */
    private static DropDownDataImpl buildImagesDropDownData(Domain[] domains, DropDownDataImpl domainsDropDown,
                                                            long currentImageId) {
        DropDownItemImpl image;
        ImageInfo[] images;
        DropDownDataImpl data = new DropDownDataImpl();
        for (int i = 0; i < domains.length; i++) {
            images = domains[i].getImages();
            for (int j = 0; j < images.length; j++) {
                image = new DropDownItemImpl(String.valueOf(images[j].getId()), images[j].getDescription());
                image.addParent(domainsDropDown.getDropDownItem(String.valueOf(domains[i].getId())));
                if (images[j].getId().longValue() == currentImageId) {
                    image.setSelected(true);
                }
                data.addDropDownItem(image);
            }
        }
        return data;
    }
}
