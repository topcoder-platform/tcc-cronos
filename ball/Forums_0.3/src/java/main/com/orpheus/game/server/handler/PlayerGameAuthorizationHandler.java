/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.persistence.PersistenceException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

/**
 * <p>A custom {@link Handler} implementation which is intended to verify that the current player issuing a request
 * specific to a game play (like, viewing brainteaser, solving puzzle, testing target object, etc) is registered to
 * requested game. If not a request is forwarded to <code>Game Rules Agreement</code> page to allow a player to register
 * to a game.</p>
 *
 * <p>The handler also verifies if a player has not already completed the game. If that's the case then a request is
 * forwarded to a page notifying on that.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerGameAuthorizationHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of result corresponding to <<code>Game Rules Agreement</code> screen.</p>
     */
    public static final String GAME_RULES_RESULT_NAME_CONFIG = "game_rules_result";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of result corresponding to <<code>Error</code> screen.</p>
     */
    public static final String ERRORS_RESULT_NAME_CONFIG = "error_result";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request/session attribute to bind the game registration post URL.</p>
     */
    public static final String POST_URL_ATTR_CONFIG = "game_registration_post_url_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * flag indicating whether the handler must forward request to an error page in case the player is not registered to
     * a game or not.</p>
     */
    public static final String RAISE_ERROR_CONFIG = "raise_error";

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PlayerGameAuthorizationHandler</code> instance initialized based on the configuration
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
    public PlayerGameAuthorizationHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, POST_URL_ATTR_CONFIG, false);
        readAsBoolean(element, RAISE_ERROR_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        if (getBoolean(RAISE_ERROR_CONFIG).booleanValue()) {
            readAsString(element, ERRORS_RESULT_NAME_CONFIG, true);
        } else {
            readAsString(element, GAME_RULES_RESULT_NAME_CONFIG, true);
        }
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

        try {
            // Get player's game play info collected so far and check if the player is already registered to requested
            // game. If not then refresh the list of registered games from DB and check again
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            if (!gamePlayInfo.isRegisteredToGame(gameId)) {
                GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
                long[] registeredGameIds = gameDataEJBAdapter.findGameRegistrations(playerId);
                gamePlayInfo.setRegisteredGames(registeredGameIds);
            }
            if (!gamePlayInfo.isRegisteredToGame(gameId)) {
                // Player is not registered to requested game - forward to game rules agreement screen or error screen
                if (getBoolean(RAISE_ERROR_CONFIG).booleanValue()) {
                    return getString(ERRORS_RESULT_NAME_CONFIG);
                } else {
                    // Forward to Game Rules Agreement screen
                    // If necessary save the orignal URL to request attribute so the user could be forwarded to
                    // appropriate page after registering to a game - the URL is saved to session
                    String postUrlAttrName = getString(POST_URL_ATTR_CONFIG);
                    if (postUrlAttrName != null) {
                        HttpSession session = request.getSession(false);
                        session.setAttribute(postUrlAttrName, buildUrl(request));
                    }
                    return getString(GAME_RULES_RESULT_NAME_CONFIG);
                }
            } else {
                // Player is already registered to requested game
                // Check if player has not already completed a game. If so forward to a page notifying that the player
                // has already completed the game
                GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
                if (isGameCompleted(gameId.longValue(), playerId, context, gameDataEJBAdapter)) {
                    return "gameCompleted-error";
                }
                return null;
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not verify if the player [" + playerId + "] is authorized to" +
                                                " access a game [" + gameId + "]", e);
        }
    }

    /**
     * <p>Records the fact of completion of specified game by specified player in servlet's context.</p>
     *
     * @param gameId a <code>long</code> providing the ID of a game.
     * @param playerId a <code>long</code> providing the ID of a current player.
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param gameDataEJBAdapter a <code>GameDataEJBAdapter</code> to be used to access <code>Game Data EJB</code>.
     * @return <code>true</code> if specified game is already completed by the specified player; <code>false</code>
     *         otherwise.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException if there is any problem in the persistence layer.
     */
    protected boolean isGameCompleted(long gameId, long playerId, ActionContext context,
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
            return set.contains(new Long(gameId));
        }
    }

    /**
     * <p>Builds the relative URL string which the request must be forwarded to once the player successfully registers
     * to the game.</p>
     *
     * @param request an <code>HttpServletRequest</code> representing the incoming request.
     * @return a <code>String</code> providing the URL to forward the request to once the player successfully registers
     *         to game
     * @throws UnsupportedEncodingException if an UTF-8 encoding is not supported.
     */
    private String buildUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        StringBuffer buf = new StringBuffer();
        buf.append(request.getRequestURI().substring(request.getContextPath().length()));
        buf.append("?");
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                buf.append("&");
                buf.append(paramName);
                buf.append("=");
                buf.append(URLEncoder.encode(paramValues[i], "UTF-8"));
            }
        }
        return buf.toString();
    }
}
