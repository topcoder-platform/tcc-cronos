/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin.report;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>A custom {@link Handler} implementation which is used for retrieving the data for <code>Overall Game Report
 * </code>.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OverallGameReportHandler extends AbstractGameServerHandler implements Handler {

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
     * <p>Constructs new <code>OverallGameReportHandler</code> instance initialized based on the configuration
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
    public OverallGameReportHandler(Element element) {
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
            // Get plugin downloading statistics
            Map pluginStats = gameDataEJBAdapter.getPluginDownloadStats();
            int ieDownloadsCount = ((Integer) pluginStats.get("IE")).intValue();
            int ffDownloadsCount = ((Integer) pluginStats.get("FireFox")).intValue();

            Map stats = new LinkedHashMap();
            // Get all users and collect the number of game registrations for all users
            int playersCount = 0;
            int allGameRegistrantsCount = 0;
            
            Map gameRegistrations = new HashMap();
            Game[] games = gameDataEJBAdapter.findGames(null, null);
            // Count the number of games with at least 1 slot
            int gamesCount = 0;
            for (int i = 0; i < games.length; i++) {
                Game game = games[i];
                HostingBlock[] blocks = game.getBlocks();
                for (int j = 0; j < blocks.length; j++) {
                    HostingBlock block = blocks[j];
                    HostingSlot[] slots = block.getSlots();
                    if (slots != null && slots.length > 0) {
                        gamesCount++;
                        break;
                    }
                }
            }
            // Get the user profiles and collects statistics for players
            UserProfile[] userProfiles = this.userProfileManager.getAllUserProfiles();
            for (int uidx = 0; uidx < userProfiles.length; uidx++) {
                UserProfile userProfile = userProfiles[uidx];
                if (userProfile.getProfileType(UserConstants.PLAYER_TYPE_NAME) != null) {
                    playersCount++;
                    long[] gameIds
                        = gameDataEJBAdapter.findGameRegistrations(Long.parseLong(String.valueOf(userProfile.getIdentifier())));
                    for (int i = 0; i < gameIds.length; i++) {
                        Long gameId = new Long(gameIds[i]);
                        if (!gameRegistrations.containsKey(gameId)) {
                            gameRegistrations.put(gameId, new Integer(0));
                        }
                        Integer count = (Integer) gameRegistrations.get(gameId);
                        gameRegistrations.put(gameId, new Integer(count.intValue() + 1));
                    }
                    if (gameIds.length >= gamesCount) {
                        allGameRegistrantsCount++;
                    }
                }
            }
            Set oneKeyFinders = new HashSet();
            // Collect statistics for games
            for (int gidx = 0; gidx < games.length; gidx++) {
                Game game = games[gidx];
                int foundKeysCount = 0;
                List sites = new ArrayList();
                // Count the number of keys found by players
                HostingSlot[] completedSlots = gameDataEJBAdapter.findCompletedSlots(game.getId().longValue());
                for (int sidx = 0; sidx < completedSlots.length; sidx++) {
                    HostingSlot completedSlot = completedSlots[sidx];
                    sites.add(completedSlot.getDomain());
                    SlotCompletion[] slotCompletions
                        = gameDataEJBAdapter.findSlotCompletions(game.getId().longValue(),
                                                                 completedSlot.getId().longValue());
                    for (int i = 0; i < slotCompletions.length; i++) {
                        SlotCompletion slotCompletion = slotCompletions[i];
                        oneKeyFinders.add(new Long(slotCompletion.getPlayerId()));
                    }
                    foundKeysCount += slotCompletions.length;
                }
                stats.put(game, new Object[] {gameRegistrations.get(game.getId()), new Integer(foundKeysCount), sites});
            }
            context.getRequest().setAttribute("stats", stats);
            context.getRequest().setAttribute("total", new Object[] {new Integer(playersCount),
                new Integer(ieDownloadsCount), new Integer(ffDownloadsCount), new Integer(games.length),
                new Integer(0), new Integer(allGameRegistrantsCount), new Integer(oneKeyFinders.size())});
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the data for overall game report", e);
        }
    }
}
