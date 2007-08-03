/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.auction.Auction;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation to be used for retrieving the list of all non-completed games (not
 * started, in-progress) which currently exist. Such a handler is provided as there is currently no such handler
 * provided by any of existing components. This handler is used for retrieving the games which could be displayed for
 * purpose of displaying open auctions to sponsor.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorAuctionsHandler extends AbstractAuctionHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>ShowBrainteaserHandler</code> instance initialized based on the configuration parameters
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
    public SponsorAuctionsHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        // Parse configuration parameters
        readAsString(element, GAMES_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsString(element, AUCTIONS_ATTR_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
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
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            // check whether to use remote look up and obtain the list of non-completed games using appropriate EJB
            // interface
            String gameDataJndiName = getString(GAME_EJB_JNDI_NAME_CONFIG);
            if (getBoolean(USER_REMOTE_INTERFACE_CONFIG).booleanValue()) {
                GameDataHome gameDataHome = (GameDataHome) JNDIUtils.getObject(this.jndiContext,gameDataJndiName,
                                                                               GameDataHome.class);
                gameData = gameDataHome.create();
                games = gameData.findGames(null, Boolean.FALSE);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) JNDIUtils.getObject(this.jndiContext,
                                                                                              gameDataJndiName,
                                                                                              GameDataLocalHome.class);
                gameDataLocal = gameDataLocalHome.create();
                games = gameDataLocal.findGames(null, Boolean.FALSE);
            }
            // Get the open auctions
            Auction[] auctions = (Auction[]) context.getRequest().getAttribute(getString(AUCTIONS_ATTR_NAME_CONFIG));
            context.getRequest().setAttribute(getString(GAMES_ATTR_NAME_CONFIG), filterGames(games, auctions));
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get all games.", e);
        }
    }

    /**
     * <p>Filters the specified list of games based on the specified auctions. Only games which have hosting blocks
     * associated with any of specified auctions remain.</p>
     *
     * @param games a <code>Game</code> array providing the list of games to be filtered.
     * @param auctions a <code>Auction</code> array providing the list of auctions.
     * @return a <code>List</code> of <code>Game</code> instances.
     */
    private List filterGames(Game[] games, Auction[] auctions) {
        final Date currentTime = new Date();
        HostingBlock[] blocks;
        boolean gameAdded;
        Date auctionStart, auctionEnd;
        List result = new ArrayList();
        for (int i = 0; i < games.length; i++) {
            gameAdded = false;
            blocks = games[i].getBlocks();
            for (int j = 0; (!gameAdded) && (j < blocks.length); j++) {
                for (int k = 0; k < auctions.length; k++) {
                    if (blocks[j].getId().equals(auctions[k].getId())) {
                        auctionStart = auctions[k].getStartDate();
                        auctionEnd = auctions[k].getEndDate();
                        if ((currentTime.compareTo(auctionStart) >= 0) || (currentTime.compareTo(auctionEnd) < 0)) {
                            gameAdded = true;
                            result.add(games[i]);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
}
