/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionManager;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.auction.KeyConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.rmi.RemoteException;

/**
 * <p>An abstract base class for custom {@link Handler} implementations provided by <code>Orpheus Game Server Auction
 * </code> module. Provides the useful functionality for parsing the configuration parameters from provided <code>XML
 * </code> element and the candidate names for various configuration parameters.</p>
 *
 * @author isv
 * @version 1.0
 */
public abstract class AbstractAuctionHandler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the auction ID from.</p>
     */
    protected static final String AUCTION_ID_PARAM_NAME_CONFIG = "auction_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the hosting block ID from.</p>
     */
    protected static final String HOSTING_BLOCK_ID_PARAM_NAME_CONFIG = "block_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain ID from.</p>
     */
    protected static final String DOMAIN_ID_PARAM_NAME_CONFIG = "domain_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the image ID from.</p>
     */
    protected static final String IMAGE_ID_PARAM_NAME_CONFIG = "image_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the bid ID from.</p>
     */
    protected static final String BID_ID_PARAM_NAME_CONFIG = "bid_id_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of games.</p>
     */
    protected static final String GAMES_ATTR_NAME_CONFIG = "games_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of auctions.</p>
     */
    protected static final String AUCTIONS_ATTR_NAME_CONFIG = "auctions_request_attr_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * <code>JNDI</code> name to be used for locating the <code>Home</code> interface for <code>GameData</code> EJB.</p>
     */
    protected static final String GAME_EJB_JNDI_NAME_CONFIG = "game_data_jndi_name";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name for <code>JNDI</code> context to be used for obtaining the <code>JNDI</code> context from <code>JNDI Utility
     * </code>.</p>
     */
    protected static final String JNDI_CONTEXT_NAME_CONFIG = "jndi_context_name";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * flag indicating whether a remote interface should be used for accessing the EJBs or not.</p>
     */
    protected static final String USER_REMOTE_INTERFACE_CONFIG = "use_remote_interface";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * flag indicating whether a bid is going to be updated or not (e.q. created).</p>
     */
    protected static final String UPDATE_BID_CONFIG = "update_bid";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the list of leading bids for auctions.</p>
     */
    protected static final String LEADING_BIDS_ATTR_NAME_CONFIG = "leading_bids_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for a single auction.</p>
     */
    protected static final String AUCTION_ATTR_NAME_CONFIG = "auction_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for a single hosting block.</p>
     */
    protected static final String HOSTING_BLOCK_ATTR_NAME_CONFIG = "block_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for selected domains.</p>
     */
    protected static final String DOMAINS_ATTR_NAME_CONFIG = "domains_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for selected domain.</p>
     */
    protected static final String DOMAIN_ATTR_NAME_CONFIG = "domain_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for selected images.</p>
     */
    protected static final String IMAGES_ATTR_NAME_CONFIG = "images_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for selected image.</p>
     */
    protected static final String IMAGE_ATTR_NAME_CONFIG = "image_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request attribute to bind the details for selected bid.</p>
     */
    protected static final String BID_ATTR_NAME_CONFIG = "bid_key";

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> name of a configuration parameter to respective <code>
     * Object</code> providing the value for configuration parameter.</p>
     */
    private final Map config;

    /**
     * <p>Constructs new <code>AbstractAuctionHandler</code> instance. The list of cofniguration parameters is initially
     * empty and may be populated by calling to various <code>readAs</code> methods provided by this class.</p>
     */
    protected AbstractAuctionHandler() {
        this.config = new HashMap();
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>String</code> and saves it for further use.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsString(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        this.config.put(parameterName, value);
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>Long</code> and saves it for further user.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsLong(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        this.config.put(parameterName, new Long(value));
    }

    /**
     * <p>Reads the value of the specified configuration parameter as <code>Boolean</code> and saves it for further use.
     * </p>
     *
     * @param element an <code>Element</code> providing the configuration parameters for this handler.
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @param required <code>true</code> if specified parameter is required to be provided; <code>false</code>
     *        otherwise.
     */
    protected final void readAsBoolean(Element element, String parameterName, boolean required) {
        String value = getElement(element, parameterName, required);
        this.config.put(parameterName, Boolean.valueOf(value));
    }

    /**
     * <p>Gets the value of specified configuration parameter as string.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>String</code> providing the value of specified configuration parameter or <code>null</code> if
     *         such parameter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>String
     *         </code> type.
     */
    protected final String getString(String parameterName) {
        return (String) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified configuration parameter as <code>Long</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>Long</code> providing the value of specified configuration parameter or <code>null</code> if such
     *         parammeter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>Long</code>
     *         type.
     */
    protected final Long getLong(String parameterName) {
        return (Long) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified configuration parameter as <code>Boolean</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested configuration parameter.
     * @return a <code>Boolean</code> providing the value of specified configuration parameter or <code>null</code> if
     *         such parammeter does not exist.
     * @throws ClassCastException if value of specified configuration parameter is not compatible with <code>Boolean
     *         </code> type.
     */
    protected final Boolean getBoolean(String parameterName) {
        return (Boolean) this.config.get(parameterName);
    }

    /**
     * <p>Gets the value of specified request parameter as <code>Long</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the requested request parameter.
     * @return a <code>lLong</code> providing the value of specified request parameter.
     * @throws HandlerExecutionException if value of specified request parameter is not compatible with <code>Long
     *         </code> type.
     */
    protected final long getLong(String parameterName, HttpServletRequest request) throws HandlerExecutionException {
        String value = request.getParameter(getString(parameterName));
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Parameter [" + parameterName + "] does not provide a long value ["
                                                + value + "]", e);
        }
    }

    /**
     * <p>Gets the text value of the specified node (key) from the given element.</p>
     *
     * @param element an <code>Element</code> providing the element to be used.
     * @param key a <code>String</code> providing the tag name for element to be extracted.
     * @param required <code>true</code> if specified configuration parameter is required; <code>false</code> otherwise.
     * @return a <code>String</code> providing the text value of the given tag.
     * @throws IllegalArgumentException if the given <code>key</code> is not present or has empty value.
     */
    protected static String getElement(Element element, String key, boolean required) {
        NodeList nodeList;
        nodeList = element.getElementsByTagName(key);
        if (required && (nodeList.getLength() == 0)) {
            throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
        }
        if (nodeList.getLength() != 1) {
            throw new IllegalArgumentException("Key '" + key + "' is occurring more than once in the element");
        }
        Node node = nodeList.item(0).getFirstChild();
        if (required && (node == null)) {
            throw new IllegalArgumentException("The [" + key + "] element is not found");
        }
        String value = node.getNodeValue();
        if (required && ((value == null) || (value.trim().length() == 0))) {
            throw new IllegalArgumentException("The [" + key + "] element is empty");
        }
        return value;
    }

    /**
     *
     * @param jndiContext
     * @return
     * @throws RemoteException
     * @throws CreateException
     * @throws NamingException
     */
    protected final Object getGameDataEJB(Context jndiContext) throws RemoteException, CreateException,
                                                                      NamingException {
        String gameDataJndiName = getString(GAME_EJB_JNDI_NAME_CONFIG);
        if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
            GameDataHome gameDataHome = (GameDataHome) JNDIUtils.getObject(jndiContext, gameDataJndiName,
                                                                           GameDataHome.class);
            return gameDataHome.create();
        } else {
            GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) JNDIUtils.getObject(jndiContext,
                                                                                          gameDataJndiName,
                                                                                          GameDataLocalHome.class);
            return gameDataLocalHome.create();
        }
    }

    /**
     * <p>Gets the auction persistence to be used for accessing the persistent data store maintaining auction data.</p>
     *
     * @param context a <code>ActionContext</code> representing a context surrounding the incoming request.
     * @return an <code>AuctionPersistence</code> to be used for accessing the persistent data store. 
     */
    protected final AuctionPersistence getAuctionPersistence(ActionContext context) {
        AuctionManager auctionManager = getAuctionManager(context);
        return auctionManager.getAuctionPersistence();
    }

    /**
     * <p>Gets the auction manager which could be used for accessing the functionality provided by <code>Auction</code>
     * framework.</p>
     *
     * @param context a <code>ActionContext</code> representing a context surrounding the incoming request. 
     * @return an <code>AuctionManager</code> to be used for accessing the auction functionality. 
     */
    protected final AuctionManager getAuctionManager(ActionContext context) {
        HttpSession session = context.getRequest().getSession(false);
        return (AuctionManager) session.getServletContext().getAttribute(KeyConstants.AUCTION_MANAGER_KEY);
    }

    /**
     * <p>Gets the value of specified request parameter as <code>int</code>.</p>
     *
     * @param parameterName a <code>String</code> providing the name of the configuration property referencing the name
     *        of requested request parameter.
     * @param request a <code>HttpServletRequest</code> representing the incoming request from the client.
     * @return an <code>int</code> providing the value of specified request parameter.
     * @throws HandlerExecutionException if value of specified request parameter is not compatible with <code>Integer
     *         </code> type.
     */
    protected final int getInt(String parameterName, HttpServletRequest request) throws HandlerExecutionException {
        String value = request.getParameter(getString(parameterName));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Parameter [" + parameterName + "] does not provide an int value ["
                                                + value + "]", e);
        }
    }
}
