/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.user.profile.UserProfile;
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
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A custom {@link Handler} implementation which is responsible for obtaining the details for sites unlocked by the
 * current player (such as unlocking time and key for the slot) and binding them to request to be rendered by the
 * <code>Unlocked Sites</code> front-end page.</p>
 *
 * @author isv
 * @version 1.0
 */
public class UnlockedDomainDetailsHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>UnlockedDomainDetailsHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_detail_key&gt;games&lt;/game_detail_key&gt;
     *      &lt;unlocked_domains_details_key&gt;unlockedDomainsDetails&lt;/unlocked_domains_details_key&gt;
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
    public UnlockedDomainDetailsHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_DETIALS_ATTR_NAME_CONFIG, true);
        readAsString(element, UNLOCKED_DOMAINS_DETAILS_ATTR_NAME_CONFIG, true);
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
     * <p>Executes this handler when servicing the specified request.</p>
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

        // Get the current game
        Game game = (Game) request.getAttribute(getString(GAME_DETIALS_ATTR_NAME_CONFIG));

        try {
            // Load details for all completed slots and slots completed by player
            SlotCompletion[] slotCompletions;
            Map playerCompletedSlots = new HashMap();
            GameDataEJBAdapter gameDataEjbAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot[] completedSlots = gameDataEjbAdapter.findCompletedSlots(game.getId().longValue());
            for (int j = 0; j < completedSlots.length; j++) {
                slotCompletions = gameDataEjbAdapter.findSlotCompletions(game.getId().longValue(),
                                                                         completedSlots[j].getId().longValue());
                collectCompletedSlots(slotCompletions, completedSlots, playerId, playerCompletedSlots);
            }
            // Put all data to request
            request.setAttribute(getString(UNLOCKED_DOMAINS_DETAILS_ATTR_NAME_CONFIG), playerCompletedSlots);
            request.setAttribute("completedSlots", completedSlots);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for slot completions by player [" + playerId + "]",
                                                e);
        }
    }

    /**
     * <p>Gathers the statistics for the slots completed by the specified player.</p>
     *
     * @param slotCompletions a <code>SlotCompletion</code> array providing the details for slot completions for
     *        specified game.
     * @param completedSlots a <code>HostingSlot</code> array providing the list of slots which have been completed in
     *        the course of specified game.
     * @param playerId a <code>long</code> providing the ID of a player.
     * @param playerCompletedDomains a <code>Map</code> collecting the domain IDs mapped to <code>Map</code> mapping the
     *        slot IDs to <code>SlotCompletion</code> instances providing the details for latest slots completed by the
     *        specified player in the course of specified game for domain.
     */
    private void collectCompletedSlots(SlotCompletion[] slotCompletions, HostingSlot[] completedSlots, long playerId,
                                       Map playerCompletedDomains) {
        for (int i = 0; i < completedSlots.length; i++) {
            // Locate the respective hosting slot which has been completed by the player and put it to map
            // Since the completed slots are provided in ascending order based on their complettion type after the
            // cycles finish the latest slot completed by player will be mapped to specified game
            for (int j = 0; j < slotCompletions.length; j++) {
                if (slotCompletions[j].getPlayerId() == playerId) {
                    if (completedSlots[i].getId().longValue() == slotCompletions[j].getSlotId()) {
                        Map slots = (Map) playerCompletedDomains.get(completedSlots[i].getDomain().getId());
                        if (slots == null) {
                            slots = new HashMap();
                            playerCompletedDomains.put(completedSlots[i].getDomain().getId(), slots);
                        }
                        slots.put(completedSlots[i].getId(), slotCompletions[j]);
                    }
                }
            }
        }
    }
}
