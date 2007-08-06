/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom {@link Handler} implementation which is intended to verify that the current player issuing a request
 * for unlocking the Ball had found all clues for the current domain which hostst the Ball. If not a request is
 * forwarded to <code>Start Game Play</code> page to allow a player to continue hunting for the clues for the requested
 * domain.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerKeySubmissionPreHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PlayerKeySubmissionPreHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;game_param_key&gt;gameId&lt;/game_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public PlayerKeySubmissionPreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, DOMAIN_PARAM_NAME_CONFIG, true);
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

        // Get game ID from request parameter
        HttpServletRequest request = context.getRequest();
        Long gameId = new Long(getLong(GAME_ID_PARAM_NAME_CONFIG, request));
        String domain = request.getParameter(getString(DOMAIN_PARAM_NAME_CONFIG));

        try {
            // Get the slot for the specified domain and game
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot slot = gameDataEJBAdapter.findSlotForDomain(gameId.longValue(), playerId, domain);

            // Get player's game play info collected so far and check if the player has found all clues for the
            // requested domain
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            if (!gamePlayInfo.hasFoundAllHuntTargets(slot)) {
                // Player has to find all clues for the requested domain
                return "mustFindAllClues";
            }
            // Player has found all clues for the requested domain and can unlock the ball
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not verify if the player [" + playerId + "] is authorized to" +
                                                " access a game [" + gameId + "]", e);
        }
    }
}
