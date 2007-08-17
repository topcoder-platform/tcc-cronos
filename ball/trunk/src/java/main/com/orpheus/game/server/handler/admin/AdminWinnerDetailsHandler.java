/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.server.util.AdminDataEJBAdapter;
import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom implementation of {@link Handler} interface  which is intended to get the details for pending winner and
 * respective game from the persistent data store and then put them to request to be used by subsequent handlers in
 * chain.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminWinnerDetailsHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for domain sponsor.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null.<br/>
     */
    private final UserProfileManager userProfileManager;

    /**
     * <p>Constructs new <code>AdminWinnerDetailsHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Here is an example of the xml element:</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_detail_key&gt;game&lt;/game_detail_key&gt;
     *      &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *      &lt;profile_key&gt;winner&lt;/profile_key&gt;
     *      &lt;profile_id_param_key&gt;playerId&lt;/profile_id_param_key&gt;
     *      &lt;object_factory_ns&gt;com.orpheus.game.server.ObjectFactory&lt;/object_factory_ns&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public AdminWinnerDetailsHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_DETIALS_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, PROFILE_ATTR_NAME_CONFIG, true);
        readAsString(element, PROFILE_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsString(element, PENDING_WINNER_ATTR_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
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
            
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            long profileId = getLong(PROFILE_ID_PARAM_NAME_CONFIG, request);

            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            AdminDataEJBAdapter adminDataEJBAdapter = getAdminDataEJBAdapter(this.jndiContext);
            PendingWinner[] pendingWinners = adminDataEJBAdapter.getPendingWinners();
            PendingWinner pendingWinner = null;
            for (int i = 0; (pendingWinner == null) && (i < pendingWinners.length); i++) {
                if (pendingWinners[i].getPlayerId() == profileId) {
                    pendingWinner = pendingWinners[i];
                }
            }
            request.setAttribute(getString(PROFILE_ATTR_NAME_CONFIG),
                                 this.userProfileManager.getUserProfile(profileId));
            request.setAttribute(getString(GAME_DETIALS_ATTR_NAME_CONFIG), gameDataEJBAdapter.getGame(gameId));
            request.setAttribute(getString(PENDING_WINNER_ATTR_NAME_CONFIG), pendingWinner);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get all winners.", e);
        }
    }
}
