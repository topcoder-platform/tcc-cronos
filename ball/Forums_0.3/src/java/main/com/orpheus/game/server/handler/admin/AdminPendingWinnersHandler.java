/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminPendingWinnersHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for domain sponsor.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null.<br/>
     */
    private final UserProfileManager userProfileManager;

    /**
     * <p> Constructor with an xml Element object. The constructor will extract necessary configurable values from this
     * xml element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public AdminPendingWinnersHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAMES_ATTR_NAME_CONFIG, true);
        readAsString(element, PROFILES_ATTR_NAME_CONFIG, true);
        readAsString(element, PENDING_WINNERS_ATTR_NAME_CONFIG, true);
        readAsString(element, SLOT_COMPLETIONS_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return null or a non-empty result code string.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            HttpServletRequest request = context.getRequest();

            PendingWinner[] pendingWinners
                = (PendingWinner[]) request.getAttribute(getString(PENDING_WINNERS_ATTR_NAME_CONFIG));
            Game[] winnerGames = new Game[pendingWinners.length];
            UserProfile[] winnerProfiles = new UserProfile[pendingWinners.length];

            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            
            if (pendingWinners.length > 0) {
                Game[] games = gameDataEJBAdapter.findGames(null, null);
                for (int i = 0; i < pendingWinners.length; i++) {
                    // Find the game matching the winner
                    for (int j = 0; j < games.length; j++) {
                        if (games[j].getId().longValue() == pendingWinners[i].getGameId()) {
                            winnerGames[i] = games[j];
                        }
                    }
                    // Load profile details for each winner
                    winnerProfiles[i] = this.userProfileManager.getUserProfile(pendingWinners[i].getPlayerId());
                }
            }
            Map winnerGamesMap = new HashMap();
            for (int i = 0; i < winnerGames.length; i++) {
                Game winnerGame = winnerGames[i];
                winnerGamesMap.put(winnerGame.getId(), winnerGame);
            }
            Map winnerProfilesMap = new HashMap();
            for (int i = 0; i < winnerProfiles.length; i++) {
                UserProfile winnerProfile = winnerProfiles[i];
                winnerProfilesMap.put(winnerProfile.getIdentifier(), winnerProfile);
            }
            request.setAttribute(getString(GAMES_ATTR_NAME_CONFIG), winnerGamesMap);
            request.setAttribute(getString(PROFILES_ATTR_NAME_CONFIG), winnerProfilesMap);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get all winners.", e);
        }
    }
}
