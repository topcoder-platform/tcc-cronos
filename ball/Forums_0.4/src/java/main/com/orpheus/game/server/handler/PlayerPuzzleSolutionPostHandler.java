/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.server.OrpheusFunctions;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>A custom {@link Handler} implementation which is responsible for creating a message notifying the players on game
 * completion by some player.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerPuzzleSolutionPostHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PlayerPuzzleSolutionPostHandler</code> instance initialized based on the configuration
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
    public PlayerPuzzleSolutionPostHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        readAsString(element, EMAIL_SUBJECT_CONFIG, true);
        readAsString(element, EMAIL_RECIPIENTS_CONFIG, true);
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

            // Save the fact of game completion to session history
            recordGameCompletion(gameId, getUserId(context), context, gameDataEJBAdapter);

            // Get details for current player from session and read player's handle from it
            UserProfile player = LoginHandler.getAuthenticatedUser(request.getSession(false));
            String handle = OrpheusFunctions.getHandle(player);

            // Notify all other players on game completion by a player
            String s = "Ball Alert: " + handle + " has found the ball and solved the puzzle. But don't give up playing "
                       + "game " + game.getName() + " just yet, " + handle + " still has to be approved and there are "
                       + "more prizes to be won!";
            broadcastGameMessage(game, s);
            // Send email to admins to notify on game completion by player
            String adminMessage = "Hi: " + handle + " has just completed game " + game.getName() + ".";
            String subject = getString(EMAIL_SUBJECT_CONFIG);
            String[] recipients = getString(EMAIL_RECIPIENTS_CONFIG).split(",");
            for (int i = 0; i < recipients.length; i++) {
                sendEmail(context, recipients[i], adminMessage, subject);
            }
        } catch (Exception e) {
            // Log exception and ignore the error so the current player would not see any error screen since that
            // error does not affect the fact of game completion by player
            e.printStackTrace();
        }

        // return null for successful execution.
        return null;
    }

    /**
     * <p>Records the fact of completion of specified game by specified player in servlet's context.</p>
     *
     * @param gameId a <code>long</code> providing the ID of a game.
     * @param playerId a <code>long</code> providing the ID of a current player.
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param gameDataEJBAdapter a <code>GameDataEJBAdapter</code> to be used to access <code>Game Data EJB</code>.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException if there is any problem in the persistence layer.
     */
    protected void recordGameCompletion(long gameId, long playerId, ActionContext context,
                                        GameDataEJBAdapter gameDataEJBAdapter)
        throws RemoteException, PersistenceException {
        Long playeIdObject = new Long(playerId);
        ServletContext servletContext = getServletContext(context);
        Map playerCompletedGames = (Map) servletContext.getAttribute("CompletedGames");
        // TODO : The 'playerCompletedGames' should better map Game IDs to sets of Player IDs who have completed the
        // game to minimize the memory consumption
        synchronized (playerCompletedGames) {
            Set set;
            if (!playerCompletedGames.containsKey(playeIdObject)) {
                long[] completedGameIds = gameDataEJBAdapter.findCompletedGameIds(playerId);
                set = new HashSet();
                for (int i = 0; i < completedGameIds.length; i++) {
                    set.add(new Long(completedGameIds[i]));
                }
                playerCompletedGames.put(playeIdObject, set);
            } else {
                set = (Set) playerCompletedGames.get(playeIdObject);
            }
            set.add(new Long(gameId));
        }
    }
}
