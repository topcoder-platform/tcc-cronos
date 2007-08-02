/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.game.persistence.DomainTarget;
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
 * <p>A custom {@link Handler} implementation to be used for regenerating the cluen image for the desired target from
 * selected slot.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminRegenerateClueImageHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>AdminRegenerateClueImageHandler</code> instance initialized based on the configuration
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
     *      &lt;target_param_key&gt;target&lt;/target_param_key&gt;
     *      &lt;random_string_image_ns&gt;/var/opt/random.xml&lt;/random_string_image_ns&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public AdminRegenerateClueImageHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, DOMAIN_TARGET_PARAM_NAME_CONFIG, true);
        readAsString(element, SLOT_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, RANDOM_STRING_IMAGE_NS_VALUE_CONFIG, true);
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
            // Get the details for slot from persistent data store
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot slot = gameDataEJBAdapter.getSlot(slotId);
            // Locate the domain target to re-generate clue image for
            DomainTargetImpl updatedTarget = null;
            DomainTarget[] targets = slot.getDomainTargets();
            for (int i = 0; i < targets.length; i++) {
                if (targets[i].getSequenceNumber() == seqNumber) {
                    long imageId = generateClueImage(targets[i], gameDataEJBAdapter);
                    request.setAttribute("imageId", new Long(imageId));
                    updatedTarget = new DomainTargetImpl();
                    updatedTarget.setClueImageId(imageId);
                    updatedTarget.setId(targets[i].getId());
                    updatedTarget.setIdentifierHash(targets[i].getIdentifierHash());
                    updatedTarget.setIdentifierText(targets[i].getIdentifierText());
                    updatedTarget.setSequenceNumber(targets[i].getSequenceNumber());
                    updatedTarget.setUriPath(targets[i].getUriPath());
                    targets[i] = updatedTarget;
                }
            }
            // If a requested target was found then update slot, broadcast message and optionally re-generate the
            // brainteaser
            if (updatedTarget != null) {
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
                updatedSlot.setDomainTargets(targets);
                gameDataEJBAdapter.updateSlots(new HostingSlot[] {updatedSlot});
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the puzzle data for rendering", e);
        }
    }
}
