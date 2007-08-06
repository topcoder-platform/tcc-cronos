/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.naming.Context;
import java.util.Date;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class GetPuzzleTimeLeftServerHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    public GetPuzzleTimeLeftServerHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, SLOT_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }


    /**
     * <p> Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers. </p>
     *
     * @param context the ActionContext object.
     * @return null or a non-empty resultCode string.
     * @throws IllegalArgumentException if the context is null.
     * @throws HandlerExecutionException if fail to execute this handler.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            HttpServletRequest request = context.getRequest();

            // Record the current time
            final Date currentTime = new Date();

            // Get game ID, domain and sequence number from request parameters
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            long slotId = getLong(SLOT_ID_PARAM_NAME_CONFIG, request);

            // Get player's game play info collected so far and record the fact on resolution of hunt target
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            Date puzzleStartTime = gamePlayInfo.getWinGamePuzzleStartTime(gameId, slotId);
            // The puzzle has been solved within the configured period
            request.setAttribute("timeLeft",
                                 new Long(OrpheusFunctions.getSolveWinGamePuzzlePeriod() - (currentTime.getTime()
                                                                                            - puzzleStartTime.getTime())
                                                                                           / 1000));
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not pre handle Test Puzzle Solution request due to unexpected "
                                                + "error", e);
        }
    }
}
