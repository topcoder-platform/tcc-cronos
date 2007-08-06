/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom {@link Handler} implementation to perform a post processing for <code>Delete Slot</code> requests.
 * Currently such post-processing includes generation of a notification message to be sent to players to notify on a
 * slot deleted from a game play chain.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminDeleteSlotPostHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>AdminDeleteSlotPostHandler</code> instance initialized based on the configuration
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
    public AdminDeleteSlotPostHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
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
        HttpServletRequest request = context.getRequest();

        try {
            // Read game details from DB
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            Game game = gameDataEJBAdapter.getGame(gameId);

            // Get deleted slot from request attribute. There may be no such attribute if the deleted slot hasn't
            // started hosting yet - in such a case there is no need to notify players on it's deletion
            HostingSlot slot = (HostingSlot) request.getAttribute("deletedSlot");
            if (slot != null) {
                // Notify all players on a site removed from game play chain
                String s = "Hello, it's your friendly game administrators. We're upping the challenge factor for"
                           + " everyone in the game by randomly removing one site " + slot.getDomain().getDomainName()
                           + " from the hunting list for Game " + game.getName() + ". And to boot, if you've got a key "
                           + "for that site already, we'll be taking it away. But don't worry, that just leaves more "
                           + "Happy Hunting Fun for you to enjoy!";
                broadcastGameMessage(game, s);
            }
        } catch (Exception e) {
            // Log exception and ignore the error so the current player would not see any error screen since that
            // error does not affect the fact of game completion by player
            e.printStackTrace();
        }

        // return null for successful execution.
        return null;
    }

}
