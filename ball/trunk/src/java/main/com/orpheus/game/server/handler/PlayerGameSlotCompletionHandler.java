/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A custom {@link Handler} implementation which is responsible for locating the details for last unlocked domains
 * for all games which the current player is registered to.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerGameSlotCompletionHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PlayerGameSlotCompletionHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;player_completed_slots_key&gt;playerCompletedSlots&lt;/player_completed_slots_key&gt;
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
    public PlayerGameSlotCompletionHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAMES_ATTR_NAME_CONFIG, true);
        readAsString(element, PLAYER_COMPLETED_SLOTS_ATTR_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Executes this handler when servicing the specified request. Iterates over the list of the games which the
     * current player is registered to and for each game locates the last domain unlocked by the player in the course of
     * the game. The map mapping the game IDs to respective hosting slots is then bound to request for further use by
     * subsequent handlers or front-end pages.</p>
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

        // Get the player profile from the session
        UserProfile player = LoginHandler.getAuthenticatedUser(session);
        if (player == null) {
            throw new HandlerExecutionException("The player is not logged to application");
        }
        long playerId = ((Long) player.getIdentifier()).longValue();

        // Get the list of games which the player is currently registered to from the request
        Game[] games = (Game[]) request.getAttribute(getString(GAMES_ATTR_NAME_CONFIG));

        try {
            // Load details for sponsor domains and associated images and the hosting block
            HostingSlot[] completedSlots;
            SlotCompletion[] slotCompletions;
            Map playerCompletedSlots = new HashMap();
            GameDataEJBAdapter gameDateEjbAdapter = getGameDataEJBAdapter(this.jndiContext);
            for (int i = 0; i < games.length; i++) {
                completedSlots = gameDateEjbAdapter.findCompletedSlots(games[i].getId().longValue());
                for (int j = 0; j < completedSlots.length; j++) {
                    slotCompletions = gameDateEjbAdapter.findSlotCompletions(games[i].getId().longValue(),
                                                                             completedSlots[j].getId().longValue());
                    collectCompletedSlots(slotCompletions, completedSlots, games[i], playerId, playerCompletedSlots);
                }
            }
            // Put all data to request
            request.setAttribute(getString(PLAYER_COMPLETED_SLOTS_ATTR_NAME_CONFIG), playerCompletedSlots);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for slot completions by player [" + playerId + "]",
                                                e);
        }
    }

    /**
     * <p>Populates the specified map with mapping from the ID of specified game to a <code>HostingSlot</code> instance
     * providing details for the latest domain unlocked by the specified player in the course of specified game.</p>
     *
     * @param slotCompletions a <code>SlotCompletion</code> array providing the details for slot completions for
     *        specified game.
     * @param completedSlots a <code>HostingSlot</code> array providing the list of slots which have been completed in
     *        the course of specified game.
     * @param game a <code>Game</code> providing the details for the current game.
     * @param playerId a <code>long</code> providing the ID of a player.
     * @param playerCompletedSlots a <code>Map</code> collecting the game IDs mapped to <code>HostingSlot</code>
     *        instances providing the latest slots completed by the specified player in the course of specified game.
     */
    private void collectCompletedSlots(SlotCompletion[] slotCompletions, HostingSlot[] completedSlots, Game game,
                                       long playerId, Map playerCompletedSlots) {
        for (int i = 0; i < completedSlots.length; i++) {
            // Locate the respective hosting slot which has been completed by the player and put it to map
            // Since the completed slots are provided in ascending order based on their complettion type after the
            // cycles finish the latest slot completed by player will be mapped to specified game
            for (int j = 0; j < slotCompletions.length; j++) {
                if (slotCompletions[j].getPlayerId() == playerId) {
                    if (completedSlots[i].getId().longValue() == slotCompletions[j].getSlotId()) {
                        playerCompletedSlots.put(game.getId(), completedSlots[i]);
                    }
                }
            }
        }
    }
}
