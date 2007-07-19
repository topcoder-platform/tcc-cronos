/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.GameDataManager;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
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
 * <p>A custom {@link Handler} implementation to be used to post-process the <code>Test Target Object</code> request
 * from the plugin and determine the next page which the player must be routed to based on current game play statistics
 * for the player, the current state of the game, etc.</p>
 *
 * <p>This handler is expected to be used in conjunction with <code>TestTargetObjectHandler</code> and should be put to
 * use only if the former handler indicates on successful passing of a test for target object.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PlayerTestTargetObjectPostHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * value of <code>Bloom Filter</code> category.</p>
     */
    protected static final String BLOOM_FILTER_CATEGORY_CONFIG = "bloom_filter_category";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * value of <code>Bloom Filter</code> capacity.</p>
     */
    protected static final String BLOOM_FILTER_CAPACITY_CONFIG = "bloom_filter_capacity";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * value of <code>Bloom Filter</code> error rate.</p>
     */
    protected static final String BLOOM_FILTER_ERROR_RATE_CONFIG = "bloom_filter_error_rate";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * configuration namespace for messenger plugin to be used to store RSS feed in persistent data store.</p>
     */
    protected static final String MESSENGER_NAMESPACE_CONFIG = "messenger_namespace";

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
    public PlayerTestTargetObjectPostHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, DOMAIN_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, SEQ_NUMBER_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
/*
        readAsString(element, BLOOM_FILTER_CATEGORY_CONFIG, true);
        readAsInteger(element, BLOOM_FILTER_CAPACITY_CONFIG, true);
        readAsFloat(element, BLOOM_FILTER_ERROR_RATE_CONFIG, true);
        readAsString(element, MESSENGER_NAMESPACE_CONFIG, true);
*/
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
            long sequenceNumber = getLong(SEQ_NUMBER_PARAM_NAME_CONFIG, request);

            // Get the adapter for Game Data EJB based on configuration parameters
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);

            // Find a hosting slot for specified game and domain which is not completed by the player yet
            HostingSlot hostingSlot = gameDataEJBAdapter.findSlotForDomain(gameId, playerId, domain);
            if (hostingSlot == null) {
                throw new HandlerExecutionException("No hosting slot for game [" + gameId + "] and domain [" + domain
                                                    + "] is found for player");
            }

            // Check if the slot has started hosting the Ball. If not - raise an error
            if (hostingSlot.getHostingStart() == null) {
//                throw new HandlerExecutionException("The slot [" + hostingSlot.getId() + "] for domain [" + domain
//                                                    + "] and game [" + gameId + "] hasn't started hosting yet");
                request.setAttribute("game", gameDataEJBAdapter.getGame(gameId));
                return "notHostingSlot-error";
            }

            // Get player's game play info collected so far and record the fact on resolution of hunt target
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            gamePlayInfo.recordHuntTargetResolution(hostingSlot, sequenceNumber);

            // Determine whether the player must find a next hunt target or all hunt targets have been found. In later
            // case determine whether a key or a ball have been found.
            if (gamePlayInfo.hasFoundAllHuntTargets(hostingSlot)) {
                // Get the game details 
                Game game = gameDataEJBAdapter.getGame(gameId);
                if (hostingSlot.getHostingEnd() == null) {
                    // The player has found a ball - determine if the player must be allowed
                    // to enter the keys and unlock ball or the game should advance to next slot and a key should have
                    // been generated for player
                    // Count the number of slots completed in game so far and find the hosting block which the
                    // current hosting slot belongs to
                    HostingBlock hostingBlock = null;
                    int completedSlotsCount = 0;
                    HostingBlock[] blocks = game.getBlocks();
                    for (int i = 0; i < blocks.length; i++) {
                        HostingSlot[] slots = blocks[i].getSlots();
                        for (int j = 0; j < slots.length; j++) {
                            if (slots[j].getHostingEnd() != null) {
                                completedSlotsCount++;
                            }
                            if (slots[j].getId().equals(hostingSlot.getId())) {
                                hostingBlock = blocks[i]; 
                            }
                        }
                    }

                    // Determine if the game must advance to next slot - the number of completed slots is less than the
                    // number of keys required by the game or current slot is hosting the ball for a period which
                    // exceeds the maximum allowed time per slot 
                    boolean mustAdvanceSlot = false;
                    if (completedSlotsCount < game.getKeyCount()) {
                        mustAdvanceSlot = true;
                    } else if ((currentTime.getTime() - hostingSlot.getHostingStart().getTime())
                               > (hostingBlock.getMaxHostingTimePerSlot() * 60 * 1000L)) {
                        mustAdvanceSlot = true;
                    }

                    // If game must advance to next slot then do so, record slot completion by player and generate the
                    // key for the player for recording
                    if (mustAdvanceSlot) {
                        SlotCompletion completion
                            = gameDataEJBAdapter.recordSlotCompletion(playerId, hostingSlot.getId().longValue(),
                                                                      currentTime);
                        // Check if the slot hasn't already finished hosting and advance the game to next slot only if
                        // it is so; otherwise do not advance the game since the game has already advanced to next slot
                        // (Re-read the slot details from DB) 
                        HostingSlot slot = gameDataEJBAdapter.getSlot(hostingSlot.getId().longValue());
                        if (slot.getHostingEnd() == null) {
                            GameDataManager gameManager = getGameDataManager(context);
                            gameManager.advanceHostingSlot(gameId);
                        }
                        request.setAttribute("upcomingDomain", getUpcomingDomain(gameId, playerId, gameDataEJBAdapter,
                                                                                 slot));
                        request.setAttribute("slotCompletion", completion);
                        request.setAttribute("preceedingDomain", getPreceedingDomain(game, hostingSlot));
                        return "foundKeyResult";
                    } else {
                        // The player is allowed to unlock the ball
                        request.setAttribute("game", game);
                        return "foundBallResult";
                    }
                } else {
                    // The player has found a key - record slot completion by player and generate the key for player
                    SlotCompletion completion = gameDataEJBAdapter.recordSlotCompletion(playerId,
                                                                                        hostingSlot.getId().longValue(),
                                                                                        currentTime);
                    request.setAttribute("upcomingDomain", getUpcomingDomain(gameId, playerId, gameDataEJBAdapter,
                                                                             hostingSlot));
                    request.setAttribute("slotCompletion", completion);
                    request.setAttribute("preceedingDomain", getPreceedingDomain(game, hostingSlot));
                    return "foundKeyResult";
                }
            } else {
                // The player should find next hunt target for the slot/domain
                DomainTarget nextHuntTarget = gamePlayInfo.getNextHuntTarget(hostingSlot);
                request.setAttribute("nextHuntTarget", nextHuntTarget);
                request.setAttribute("nextHuntUrl", getHash(nextHuntTarget.getUriPath()));
                return "foundObjectResult";
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not post handle Test Target Object request due to unexpected "
                                                + "error", e);
        }
    }
}
