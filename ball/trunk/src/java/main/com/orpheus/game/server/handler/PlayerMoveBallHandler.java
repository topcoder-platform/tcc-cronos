/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.GameDataManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>A custom handler implementation of {@link Handler} interface which is responsible for advancing the game to next
 * slot in case a player attempting to unlock the ball had failed to submit valid keys for three times.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerMoveBallHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PlayerTestTargetObjectPostHandler</code> instance initialized based on the configuration
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
    public PlayerMoveBallHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, DOMAIN_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
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
            String domain = request.getParameter(getString(DOMAIN_PARAM_NAME_CONFIG));

            // Get the adapter for Game Data EJB based on configuration parameters
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);

            // Find a hosting slot for specified game and domain which is not completed by the player yet
            HostingSlot hostingSlot = gameDataEJBAdapter.findSlotForDomain(gameId, playerId, domain);
            if (hostingSlot == null) {
//                throw new HandlerExecutionException("No hosting slot for game [" + gameId + "] and domain [" + domain
//                                                    + "] is found for player");
                request.setAttribute("game", gameDataEJBAdapter.getGame(gameId));
                return "notHostingSlot-error";
            }
            
            // Check if the slot has started hosting the Ball. If not - raise an error
            if (hostingSlot.getHostingStart() == null) {
                throw new HandlerExecutionException("The slot [" + hostingSlot.getId() + "] for domain [" + domain
                                                    + "] and game [" + gameId + "] hasn't started hosting yet");
            }

            // Record slot completion for player, generate key for player and advance game to next slot
            SlotCompletion completion
                = gameDataEJBAdapter.recordSlotCompletion(playerId, hostingSlot.getId().longValue(),
                                                          currentTime);
            // Check if the slot hasn't already finished hosting and advance the game to next slot only if it is so;
            // otherwise do not advance the game since the game has already advanced to next slot
            if (hostingSlot.getHostingEnd() == null) {
                GameDataManager gameManager = getGameDataManager(context);
                gameManager.advanceHostingSlot(gameId);
            }
            request.setAttribute("slotCompletion", completion);
            request.setAttribute("upcomingDomain", getUpcomingDomain(gameId, playerId, gameDataEJBAdapter,
                                                                     hostingSlot));
            return "ballMovedResult";
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not post handle Test Target Object request due to unexpected "
                                                + "error", e);
        }
    }
}
