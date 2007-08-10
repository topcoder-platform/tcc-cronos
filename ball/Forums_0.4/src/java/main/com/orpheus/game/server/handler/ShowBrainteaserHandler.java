/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with <code>PuzzleRenderingHandler</code> for
 * rendering a brainteaser to be presented to player for solving. This handler prepares the data necessary for <code>
 * PuzzleRenderingHandler</code> to locate and render the desired brainteaser as currently there is no such handler
 * provided by any of existing components.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ShowBrainteaserHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>ShowBrainteaserHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
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
    public ShowBrainteaserHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, PUZZLE_ID_ATTR_NAME_CONFIG, true);
        readAsString(element, MEDIA_TYPE_ATTR_NAME_CONFIG, true);
        readAsString(element, MEDIA_TYPE_VALUE_CONFIG, true);
        readAsString(element, SOLUTION_TESTER_BASE_NAME_VALUE_CONFIG, true);
        readAsString(element, DOMAIN_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
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

            // Get game ID and domain from request parameters
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            String domain = request.getParameter(getString(DOMAIN_PARAM_NAME_CONFIG));

            // Find a hosting slot for specified game and domain which is not completed by the player yet
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot hostingSlot = gameDataEJBAdapter.findSlotForDomain(gameId, playerId, domain);

            // Check if the slot has started hosting the Ball. If not - raise an error
            if (hostingSlot.getHostingStart() == null) {
//                throw new HandlerExecutionException("Could not prepare the brainteaser data for rendering. The slot ["
//                                                    + hostingSlot.getId() + "] for domain [" + domain + "] and game ["
//                                                    + gameId + "] hasn't started hosting yet");
                request.setAttribute("game", gameDataEJBAdapter.getGame(gameId));
                return "notHostingSlot-error";
            }

            // Get player's game play info collected so far and the list of brainteaser IDs available for selected slot
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            long[] brainTeaserIds = hostingSlot.getBrainTeaserIds();

            // Get the index of a brainteaser to be displayed to player based on current player's game play info
            // collected so far. If a player was never presented with a brainteaser for selected slot then a first
            // brainteaser from a serie is selected for display; otherwise a next brainteaser in a serie is selected.
            // If no more brainteasers are available from a serie then the last one is displayed. If a player issues a
            // request for brainteaser update at a rate faster then specified by existing configuration then the last
            // brainteaser which has been already displayed to user is displayed again
            final Date currentTime = new Date();
            final int brainteaserIndex;
            final Date brainteaserUpdateDate;
            synchronized (gamePlayInfo) {
                if (gamePlayInfo.isFirstBrainteaserRequest(hostingSlot)) {
                    brainteaserIndex = 0;
                    brainteaserUpdateDate = currentTime;
                } else {
                    int lastBrainteaserIndex = gamePlayInfo.getLastBrainteaserIndex(hostingSlot);
                    Date lastBrainteaserUpdate = gamePlayInfo.getLastBrainteaserUpdateTime(hostingSlot);
                    if (currentTime.getTime() - lastBrainteaserUpdate.getTime() < getBrainteaserUpdateInterval()) {
                        brainteaserIndex = lastBrainteaserIndex;
                        brainteaserUpdateDate = lastBrainteaserUpdate;
                    } else {
                        brainteaserIndex = Math.min(lastBrainteaserIndex + 1, brainTeaserIds.length - 1);
                        brainteaserUpdateDate = currentTime;
                    }
                }

                // Record the index of latest brainteaser displayed to user and the time of displaying in player's game
                // play history
                gamePlayInfo.setLastDisplayedBrainteaser(hostingSlot, brainteaserIndex, brainteaserUpdateDate);
            }

            // Put the ID of a brainteaser and media type to request to be used by subsequent PuzzleRenderingHandler
            request.setAttribute(getString(PUZZLE_ID_ATTR_NAME_CONFIG), new Long(brainTeaserIds[brainteaserIndex]));
            request.setAttribute(getString(MEDIA_TYPE_ATTR_NAME_CONFIG), getString(MEDIA_TYPE_VALUE_CONFIG));

            // Put the hash and sequence number for the first hunt target which is associated with brainuteaser
            DomainTarget domainTarget = hostingSlot.getDomainTargets()[0];
            request.setAttribute("nextHuntTarget", domainTarget);
            request.setAttribute("nextHuntUrl", getHash(domainTarget.getUriPath()));

            // If there are next brainteasers available for displaying then put the request attributes indicating on
            // that and providing the 
            if (brainteaserIndex < brainTeaserIds.length - 1) {
                request.setAttribute("nextBrainteaserAvailable", Boolean.TRUE);
                request.setAttribute("brainteaserUpdatePeriod", new Long(getBrainteaserUpdateInterval()));
            }

            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the brainteaser data for rendering", e);
        }
    }
}
