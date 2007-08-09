/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A custom {@link Handler} implementation used to obtain the details for the pending winners for the requested game
 * from the persistent data store and bind them to request.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PendingWinnerProfilesHandler extends AbstractGameServerHandler implements Handler {

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for domain sponsor.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null.<br/>
     */
    private final UserProfileManager userProfileManager;

    /**
     * <p>Constructs new <code>PendingWinnerProfilesHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Here is an example of the xml element:</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public PendingWinnerProfilesHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, PROFILES_ATTR_NAME_CONFIG, true);
        readAsString(element, PENDING_WINNERS_ATTR_NAME_CONFIG, true);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return null or a non-empty result code string.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            HttpServletRequest request = context.getRequest();
            PendingWinner[] pendingWinners
                = (PendingWinner[]) request.getAttribute(getString(PENDING_WINNERS_ATTR_NAME_CONFIG));
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            List winnerProfiles = new ArrayList();
            if (pendingWinners.length > 0) {
                for (int i = 0; i < pendingWinners.length; i++) {
                    // Load profile details for each winner for desired game
                    if (pendingWinners[i].getGameId() == gameId) {
                        winnerProfiles.add(this.userProfileManager.getUserProfile(pendingWinners[i].getPlayerId()));
                    }
                }
            }
            request.setAttribute(getString(PROFILES_ATTR_NAME_CONFIG),
                                 winnerProfiles.toArray(new UserProfile[winnerProfiles.size()]));
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get all winners.", e);
        }
    }
}
