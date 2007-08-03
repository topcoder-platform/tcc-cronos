/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.GameDataManager;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.administration.entities.DomainTargetImpl;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

/**
 * <p>A custom {@link Handler} implementation to be used for deleting the desired target from selected slot.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminDeleteTargetHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the template for the message notifying players on deleted target.</p>
     */
    private static final String TARGET_DELETED_MSG_CONTENT_PATTERN
        = "Hi - its your friendly Game Administrators here. To make the game more fun we "
          + "have deleted target {2} in Game {0} for site {1}. If you already have a key "
          + "for that site, you don't need to do anything. If you are on site and looking for the old target, "
          + "don't forget to refresh the clue page. Happy Hunting!";

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>AdminDeleteTargetHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;slot_id_param_key&gt;slotId&lt;/slot_id_param_key&gt;
     *      &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *      &lt;target_param_key&gt;target&lt;/target_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public AdminDeleteTargetHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, DOMAIN_TARGET_PARAM_NAME_CONFIG, true);
        readAsString(element, SLOT_ID_PARAM_NAME_CONFIG, true);
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
        try {
            HttpServletRequest request = context.getRequest();
            // Get slot ID, game ID and target from request parameters
            long slotId = getLong(SLOT_ID_PARAM_NAME_CONFIG, request);
            long seqNumber = getLong(DOMAIN_TARGET_PARAM_NAME_CONFIG, request);
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            // Get the details for slot and game from persistent data store
            GameDataManager gameManager = getGameDataManager(context);
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot slot = gameDataEJBAdapter.getSlot(slotId);
            Game game = gameDataEJBAdapter.getGame(gameId);
            // Locate the desired target for deletion and build the list of remaining targets
            boolean firstTargetDeleted = false;
            DomainTarget deletedTarget = null;
            List leftTargets = new ArrayList();
            int leftTargetsCnt = 0;
            DomainTarget[] targets = slot.getDomainTargets();
            for (int i = 0; i < targets.length; i++) {
                if (targets[i].getSequenceNumber() == seqNumber) {
                    if (i == 0) {
                        firstTargetDeleted = true;
                    }
                    deletedTarget = targets[i];
                } else {
                    // Each remaining target is re-ordered
                    DomainTargetImpl leftTarget = new DomainTargetImpl();
                    leftTarget.setClueImageId(targets[i].getClueImageId());
                    leftTarget.setId(targets[i].getId());
                    leftTarget.setIdentifierHash(targets[i].getIdentifierHash());
                    leftTarget.setIdentifierText(targets[i].getIdentifierText());
                    leftTarget.setSequenceNumber(leftTargetsCnt++);
                    leftTarget.setUriPath(targets[i].getUriPath());
                    leftTargets.add(leftTarget);
                }
            }
            // If a requested target was found then update slot, broadcast message and optionally re-generate the
            // brainteaser
            if (deletedTarget != null) {
                // Update slot in persistent data store
                final HostingSlotImpl updatedSlot = new HostingSlotImpl();
                updatedSlot.setId(slot.getId());
                updatedSlot.setDomain(slot.getDomain());
                updatedSlot.setImageId(slot.getImageId());
                updatedSlot.setBrainTeaserIds(slot.getBrainTeaserIds());
                updatedSlot.setPuzzleId(slot.getPuzzleId());
                updatedSlot.setSequenceNumber(slot.getSequenceNumber());
                updatedSlot.setWinningBid(slot.getWinningBid());
                updatedSlot.setHostingStart(slot.getHostingStart());
                updatedSlot.setHostingEnd(slot.getHostingEnd());
                updatedSlot.setDomainTargets((DomainTarget[]) leftTargets.toArray(new DomainTarget[leftTargets.size()]));
                gameDataEJBAdapter.updateSlots(new HostingSlot[] {updatedSlot});
                if (slot.getHostingStart() != null) {
                    // Broadcast a message notifying players on deleted target
                    String message = MessageFormat.format(TARGET_DELETED_MSG_CONTENT_PATTERN,
                                                          new Object[] {game.getName(), slot.getDomain().getDomainName(),
                                                                        deletedTarget.getIdentifierText()});
                    broadcastGameMessage(game, message);
                }
                // If first target deleted and there are other targets left then re-generate a brainteaser for the slot
                if (firstTargetDeleted && (targets.length > 1)) {
                    gameManager.regenerateBrainTeaser(slotId);
                }
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the puzzle data for rendering", e);
        }
    }
}
