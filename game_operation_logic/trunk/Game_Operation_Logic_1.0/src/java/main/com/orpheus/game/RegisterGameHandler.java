/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.GameData;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * Registers the current (logged-in) user for a specified game. The game to register for will be identified by its
 * unique ID, parsed from a request parameter of configurable name. This class is immutable and thread safe.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class RegisterGameHandler implements Handler {
    /** A configurable param name to retrieve the game id from the request parameters. */
    private final String gameIdParamKey;

    /**
     * Create the instance with given gameIdParamKey.
     *
     * @param gameIdParamKey the game id param key
     *
     * @throws IllegalArgumentException if string argument is null or empty
     */
    public RegisterGameHandler(String gameIdParamKey) {
        ParameterCheck.checkNullEmpty("gameIdParamKey", gameIdParamKey);
        this.gameIdParamKey = gameIdParamKey;
    }

    /**
     * Create the instance from element. The structure of the element can be found in CS.
     *
     * @param element the xml element to create the handler instance.
     *
     * @throws IllegalArgumentException if element is null or failed to extract what we want
     */
    public RegisterGameHandler(Element element) {
        ParameterCheck.checkNull("element", element);
        gameIdParamKey = XMLHelper.getNodeValue(element, "game_id_param_key", true);
    }

    /**
     * Registers the current (logged-in) user for a specified game.
     *
     * @param context the action context
     *
     * @return null always
     *
     * @throws IllegalArgumentException if context is null
     * @throws HandlerExecutionException if any error occurred while executing this handler
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        HttpServletRequest request = context.getRequest();

        UserProfile user = LoginHandler.getAuthenticatedUser(request.getSession());

        if (user == null) {
            throw new HandlerExecutionException("user does not login yet");
        }

        long userId = ((Long) user.getIdentifier()).longValue();

        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

        long gameId = RequestHelper.getLongParameter(request, gameIdParamKey); //gameId from request parameter

        try {
            GameData gameData = null;

            if (golu.isUseLocalInterface()) {
                gameData = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }

            gameData.recordRegistration(userId, gameId);
        } catch (Exception e) {
            throw new HandlerExecutionException("error occurs while recording user registation", e);
        }

        return null;
    }
}
