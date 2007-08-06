/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin.report;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>A custom {@link Handler} implementation which is used for retrieving the data for <code>Overall Player Report
 * </code>.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OverallPlayerReportHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>UserProfileManager</code> instance to be used for locating and getting details for
     */
    private final UserProfileManager userProfileManager;

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>OverallPlayerReportHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public OverallPlayerReportHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Processes the incoming request.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            Map stats = new LinkedHashMap();
            Map games = new HashMap();
            Map completedSlots = new HashMap();
            Map slotCompletions = new HashMap();

            // Get the user profiles and collects statistics for players
            UserProfile[] userProfiles = this.userProfileManager.getAllUserProfiles();
            for (int uidx = 0; uidx < userProfiles.length; uidx++) {
                UserProfile userProfile = userProfiles[uidx];
                if (userProfile.getProfileType(UserConstants.PLAYER_TYPE_NAME) != null) {
                    long playerId = Long.parseLong(String.valueOf(userProfile.getIdentifier()));
                    Map gameStats = new LinkedHashMap();
                    long[] completedGameIds = gameDataEJBAdapter.findCompletedGameIds(playerId);
                    long[] registeredGames = gameDataEJBAdapter.findGameRegistrations(playerId);
                    for (int i = 0; i < registeredGames.length; i++) {
                        Long gameId = new Long(registeredGames[i]);
                        if (!games.containsKey(gameId)) {
                            games.put(gameId, gameDataEJBAdapter.getGame(gameId.longValue()));
                        }
                        Game game = (Game) games.get(gameId);
                        if (!completedSlots.containsKey(gameId)) {
                            completedSlots.put(gameId, gameDataEJBAdapter.findCompletedSlots(gameId.longValue()));
                        }
                        List playerCompletedSites = new ArrayList();
                        HostingSlot[] slots = (HostingSlot[]) completedSlots.get(gameId);
                        for (int j = 0; j < slots.length; j++) {
                            if (!slotCompletions.containsKey(gameId + "," + slots[j].getId())) {
                                slotCompletions.put(gameId + "," + slots[j].getId(),
                                                    gameDataEJBAdapter.findSlotCompletions(gameId.longValue(),
                                                                                           slots[j].getId().longValue()));
                            }
                            SlotCompletion[] sc
                                = (SlotCompletion[]) slotCompletions.get(gameId + "," + slots[j].getId());
                            for (int k = 0; k < sc.length; k++) {
                                SlotCompletion slotCompletion = sc[k];
                                if (slotCompletion.getPlayerId() == playerId) {
                                    playerCompletedSites.add(slots[j]);
                                }
                            }
                        }
                        String status = "";
                        for (int j = 0; j < completedGameIds.length; j++) {
                            if (completedGameIds[j] == gameId.longValue()) {
                                status = "Completed";
                            }
                        }
                        gameStats.put(game, new Object[] {new Integer(playerCompletedSites.size()),
                                                          playerCompletedSites, status});
                    }
                    stats.put(userProfiles[uidx], new Object[] {gameStats, new Integer(gameStats.size())});
                }
            }
            context.getRequest().setAttribute("stats", stats);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the data for overall player report", e);
        }
    }
}
