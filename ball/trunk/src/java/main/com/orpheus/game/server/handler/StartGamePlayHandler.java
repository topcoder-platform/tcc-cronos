/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation which is responsible for restoring the game play context based on the game
 * and domain selected by the current player.</p>
 *
 * @author isv
 * @version 1.0
 */
public class StartGamePlayHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>StartGamePlayHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
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
    public StartGamePlayHandler(Element element) {
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, DOMAIN_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        readAsBoolean(element, STRICT_VALUE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }


    /**
     * <p>Processes the incoming request. Locates the appropriate hosting slot for the game and domain selected by the
     * player and determines the next page to be displayed to player in order to start game play for the specified game
     * and domain. If player hadn't found any targets for the slot yet then request is forwarded to a <code>Brainteaser
     * </code> page; otherwise request is forwarded to a page showing a clue image for the next target for hunting on a
     * selected domain.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        try {
            HttpServletRequest request = context.getRequest();
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);

            // Get ID of a game selected by player and the domain from the request parameters
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            String domain = request.getParameter(getString(DOMAIN_PARAM_NAME_CONFIG));
            long playerId = getUserId(context);

            // Get the current game play track and determine which target must the player to hunt for
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            HostingSlot slot = getMatchingSlot(gameId, domain, playerId, gameDataEJBAdapter);
            if (slot == null) {
                // In fact, during the normal game play this should never happen
//                throw new HandlerExecutionException("Could not restore game play context for game [" + gameId + "], "
//                                                    + "domain [" + domain + "] and player [" + playerId + "] due to "
//                                                    + "missing hosting slot");
                request.setAttribute("game", gameDataEJBAdapter.getGame(gameId));
                return "notHostingSlot-error";
            }
            synchronized (gamePlayInfo) {
                if (gamePlayInfo.hasFoundAllHuntTargets(slot)) {
                    // Player already has found all targets for the slot so he should be directly forwarded to Found
                    // Ball or Found Key result in such a case
                    if (slot.getHostingEnd() == null) {
                        // Slot is still hosting the Ball - the player is allowed to unlock the ball
                        request.setAttribute("game", gameDataEJBAdapter.getGame(gameId));
                        return "foundBallResult";
                    } else {
                        // Slot is already unlocked by someone else so current player is considered found a Key
                        // Record slot completion by player and generate the key for player
                        // This may occur in following scenario - the player originally has found a Ball, then he went
                        // out to different domain and then came back while, in the meantime, someone else has already
                        // unlocked the domain
                        final Date currentTime = new Date();
                        SlotCompletion completion = gameDataEJBAdapter.recordSlotCompletion(playerId,
                                                                                            slot.getId().longValue(),
                                                                                            currentTime);
                        request.setAttribute("upcomingDomain", getUpcomingDomain(gameId, playerId, gameDataEJBAdapter,
                                                                                 slot));
                        request.setAttribute("slotCompletion", completion);
                        return "foundKeyResult";
                    }
                } else {
                    // Player hasn't found all targets and has to find the next one available
                    DomainTarget domainTarget = gamePlayInfo.getNextHuntTarget(slot);
                    if (domainTarget == null) {
                        // In fact, this should never happen too
                        throw new HandlerExecutionException("Could not restore game play context for game [" + gameId
                                                            + "], domain [" + domain + "] and player [" + playerId
                                                            + "] due to no more hunt targets available");
                    }
                    if (domainTarget.getSequenceNumber() == slot.getDomainTargets()[0].getSequenceNumber()) {
                        // If no targets have been found by player so far then present player with brainteaser page
                        return "brainTeaserResult";
                    } else {
                        // Otherwise present player with a clue for the next target to hunt
                        String url
                            = normalizeURL(domainTarget.getUriPath(), getBoolean(STRICT_VALUE_CONFIG).booleanValue());
                        request.setAttribute("nextHuntTarget", domainTarget);
                        request.setAttribute("nextHuntUrl", getHash(url));
                        return "nextClueResult";
                    }
                }
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not restore game play context due to unexpected error", e);
        }
    }

    /**
     * <p>Gets the hosting slot corresponding to specified domain which has started hosting and has not been yet
     * completed by the specified player in the course of the specified game.</p>
     *
     * @param gameId a <code>long</code> providing the ID of a game selected by player.
     * @param domain a <code>String</code> providing the domain selected by player.
     * @param playerId a <code>long</code> providing the ID of a current player.
     * @param gameDataEJBAdapter a <code>GameDataEJBAdapter</code> to be used for accessing the <code>Game Data EJB
     *        </code>.  
     * @return a <code>HostingSlot</code> providing the details for a slot which the specified game and domain are
     *         matching to in context of a game play for specified player or <code>null</code> if no slot currently
     *         corresponds to specified domain and game. 
     * @throws RemoteException if a remote error is encountered while communicating to <code>Game Data EJB</code>.
     * @throws PersistenceException if an error is encountered while acceessing the persistent data store.
     */
    private HostingSlot getMatchingSlot(long gameId, String domain, long playerId,
                                        GameDataEJBAdapter gameDataEJBAdapter)
        throws RemoteException, PersistenceException {
        HostingSlot slot = gameDataEJBAdapter.findSlotForDomain(gameId, playerId, domain);
        if ((slot == null) || (slot.getHostingStart() == null)) {
            return null;
        } else {
            return slot;
        }
    }
}
