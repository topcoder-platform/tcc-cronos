/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.OrpheusFunctions;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.GameDataManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.naming.Context;
import java.util.Date;

/**
 * <p>A custom implementation of {@link Handler} interface which is intended </p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerPuzzleSolutionPreHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PlayerPuzzleSolutionPreHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;solutiontester_base_name&gt;solutionTester&lt;/solutiontester_base_name&gt;
     *      &lt;puzzle_id_request_attribute_key&gt;puzzleId&lt;/puzzle_id_request_attribute_key&gt;
     *      &lt;media_type_request_attribute_key&gt;mediaType&lt;/media_type_request_attribute_key&gt;
     *      &lt;media_type&gt;DHTML&lt;/media_type&gt;
     *      &lt;game_param_key&gt;gameId&lt;/game_param_key&gt;
     *      &lt;domain_param_key&gt;domain&lt;/domain_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public PlayerPuzzleSolutionPreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, SLOT_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Processes the incoming request. If a current session is associated with authenticated player then the hosting
     * slot is located based on the current game and domain (identified by request paramaters) and the brainteaser ID
     * and a media type are put to request to be used later by <code>PuzzleRenderingHandler</code>.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        // Check if player is authenticated and get the player ID from profile
        long playerId = getUserId(context);

        try {
            HttpServletRequest request = context.getRequest();

            // Record the current time
            final Date currentTime = new Date();

            // Get game ID, domain and sequence number from request parameters
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            long slotId = getLong(SLOT_ID_PARAM_NAME_CONFIG, request);

            // Get player's game play info collected so far and record the fact on resolution of hunt target
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            Date puzzleStartTime = gamePlayInfo.getWinGamePuzzleStartTime(gameId, slotId);
            if ((puzzleStartTime == null)
                || (currentTime.getTime() - puzzleStartTime.getTime()
                    > OrpheusFunctions.getSolveWinGamePuzzlePeriod() * 1000L)) {
                System.out.println("ISV : PlayerPuzzleSolutionPreHandler : the player [" + playerId + "] did not solve "
                                   + "the puzzle for game [" + gameId + "] and slot [" + slotId + "] in time");
                // Get the adapter for Game Data EJB based on configuration parameters
                GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);

                // Advance game to next slot and generate a key for player
                SlotCompletion completion
                    = gameDataEJBAdapter.recordSlotCompletion(playerId, slotId, currentTime);
                System.out.println("ISV : PlayerPuzzleSolutionPreHandler : recorded slot completion for player ["
                                   + playerId + "]" + ", game [" + gameId + "] and slot [" + slotId + "]");
                // Check if the slot hasn't already finished hosting and advance the game to next slot only if it is so;
                // otherwise do not advance the game since the game has already advanced to next slot
                HostingSlot slot = gameDataEJBAdapter.getSlot(slotId);
                if (slot.getHostingEnd() == null) {
                    System.out.println("ISV : PlayerPuzzleSolutionPreHandler : advancing game [" + gameId + "] from slot"
                                       + "[" + slotId + "] on behalf of player [" + playerId + "] ...");
                    GameDataManager gameManager = getGameDataManager(context);
                    gameManager.advanceHostingSlot(gameId);
                    System.out.println("ISV : PlayerPuzzleSolutionPreHandler : advanced game [" + gameId + "] from slot"
                                       + "[" + slotId + "] on behalf of player [" + playerId + "]");
                }
                request.setAttribute("slotCompletion", completion);
                request.setAttribute("upcomingDomain", getUpcomingDomain(gameId, playerId, gameDataEJBAdapter, slot));
                return "puzzleSolutionPeriodExpiredResult";
            }
            // The puzzle has been solved within the configured period
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not pre handle Test Puzzle Solution request due to unexpected "
                                                + "error", e);
        }
    }

    
}
