/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminGameSlotSponsorsHandler extends AbstractGameServerHandler implements Handler {

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for domain sponsor.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null.<br/>
     */
    private final UserProfileManager userProfileManager;

    /**
     * <p>Constructs new <code>AdminGameSlotSponsorsHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_detail_key&gt;games&lt;/game_detail_key&gt;
     *      &lt;slot_sponsors_key&gt;slotSponsors&lt;/slot_sponsors_key&gt;
     *      &lt;object_factory_ns&gt;com.orpheus.game.server.ObjectFactory&lt;/object_factory_ns&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public AdminGameSlotSponsorsHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_DETIALS_ATTR_NAME_CONFIG, true);
        readAsString(element, SLOT_SPONSORS_ATTR_NAME_CONFIG, true);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Executes this handler when servicing the specified request. Obtains the auctions from the request and locates
     * the list of games which correspond to provided auctions. The list of such games is bound to request for further
     * use.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if a handler execution succeeds; otherwise an exception will be thrown.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();

        // Get the current game
        Game game = (Game) request.getAttribute(getString(GAME_DETIALS_ATTR_NAME_CONFIG));

        try {
            // Map mapping sponsor ID to sponsor profile to cache the profiles which have been already retrieved
            Map sponsors = new HashMap();

            // Resulting map mapping the slot ID to sponsor profile
            Map slotSponsors = new HashMap();

            // Build the mapping from slot ID to sponsor
            HostingBlock[] blocks = game.getBlocks();
            for (int i = 0; i < blocks.length; i++) {
                HostingSlot[] slots = blocks[i].getSlots();
                for (int j = 0; j < slots.length; j++) {
                    Long sponsorId = slots[j].getDomain().getSponsorId();
                    if (sponsorId != null) {
                        if (!sponsors.containsKey(sponsorId)) {
                            sponsors.put(sponsorId, this.userProfileManager.getUserProfile(sponsorId.longValue()));
                        }
                        slotSponsors.put(slots[j].getId(), sponsors.get(sponsorId));
                    }
                }
            }

            // Put necessary data to request
            request.setAttribute(getString(SLOT_SPONSORS_ATTR_NAME_CONFIG), slotSponsors);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for slot completions ]", e);
        }
    }
}
