/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.HostingSlot;

import com.topcoder.user.profile.UserProfile;

import com.topcoder.util.config.ConfigManagerException;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;

import org.w3c.dom.Element;

import java.rmi.RemoteException;

import java.util.Arrays;
import java.util.Date;

import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * A Handler that processes key submissions. It will accept a game ID via a request parameter of configurable name, and
 * several key strings as multiple values of another request parameter, also of configurable name. It will determine
 * whether the submitted keys match those recorded for the currently logged-in player for the specified game for the
 * most recently completed (by any player) k hosting slots, where k is the key count of the specified game. The
 * handler will maintain a count, in the player¡¯s session, of the number of failures to submit matching keys for the
 * specified game since the last time its hosting slot changed. (The ID of the most recently-completed hosting slot in
 * that game is one possible measure of whether the hosting slot has changed.)
 * </p>
 * 
 * <p>
 * Contingencies and results:
 * 
 * <ol>
 * <li>
 * If the specified game is not active then the handler returns a configurable result code characteristic of the
 * problem, regardless of the values of the keys.
 * </li>
 * <li>
 * If the keys do not match then the player¡¯s failure count for the game and slot is incremented. The handler then
 * returns a configurable result string characteristic of whether or not the failure count meets or exceeds a
 * configurable  threshold value.
 * </li>
 * <li>
 * If the keys do match then the handler records a game completion for the player in the specified game (via the game
 * data persistence component), then returns null
 * </li>
 * </ol>
 * 
 * This class is thread safe since it does not contain any mutable state.
 * </p>
 */
public class KeySubmissionHandler implements Handler {
    /** Represent the result name this handler will return if the max failure is met or exceeded. */
    private final String failureCountExceededResult;

    /** Represent the result name this handler will return if the max failure is not met. */
    private final String failureCountNotMetResult;

    /** A configurable param name to retrieve the game id from the request parameters. */
    private final String gameIdParamKey;

    /** Represent the result name this handler will return if the game is inactive. */
    private final String inactiveGameResult;

    /**
     * A configurable param name to retrieve the key submissions from the request parameters.(The value retrieved is a
     * string array.)
     */
    private final String submissionParamKey;

    /** A integer represents the max failure match count. */
    private final int maxFailureCount;

    /**
     * Create the instance with given arguments. Simply assign them to variables
     *
     * @param gameIdParamKey the game id param key
     * @param submissionParamKey the submission param key
     * @param inactiveGameResult the inactive game result
     * @param failureCountExceededResult the failure coun exceeded result
     * @param failureCountNotMetResult the failure count not met result
     * @param maxFailureCount the max failure count
     *
     * @throws IllegalArgumentException if either of arguments is null or empty string
     */
    public KeySubmissionHandler(String gameIdParamKey, String submissionParamKey, String inactiveGameResult,
        String failureCountExceededResult, String failureCountNotMetResult, int maxFailureCount) {
        ParameterCheck.checkNullEmpty("gameIdParamKey", gameIdParamKey);
        ParameterCheck.checkNullEmpty("submissionParamKey", submissionParamKey);
        ParameterCheck.checkNullEmpty("inactiveGameResult", inactiveGameResult);
        ParameterCheck.checkNullEmpty("failureCountExceededResult", failureCountExceededResult);
        ParameterCheck.checkNullEmpty("failureCountNotMetResult", failureCountNotMetResult);
        ParameterCheck.checkNotPositive("maxFailureCount", maxFailureCount);

        this.gameIdParamKey = gameIdParamKey;
        this.submissionParamKey = submissionParamKey;
        this.inactiveGameResult = inactiveGameResult;
        this.failureCountExceededResult = failureCountExceededResult;
        this.failureCountNotMetResult = failureCountNotMetResult;
        this.maxFailureCount = maxFailureCount;
    }

    /**
     * Create the instance from element. The structure of the Element is described in CS.
     *
     * @param element the xml element to create the handler instance.
     *
     * @throws IllegalArgumentException if argument is null or invalid.
     */
    public KeySubmissionHandler(Element element) {
        ParameterCheck.checkNull("element", element);

        this.gameIdParamKey = XMLHelper.getNodeValue(element, "game_id_param_key", true);
        this.submissionParamKey = XMLHelper.getNodeValue(element, "submission_param_key", true);
        this.inactiveGameResult = XMLHelper.getNodeValue(element, "inactive_game_result", true);
        this.failureCountExceededResult = XMLHelper.getNodeValue(element, "failure_count_exceeded_result", true);
        this.failureCountNotMetResult = XMLHelper.getNodeValue(element, "failure_count_not_met_result", true);

        String failureCountStr = XMLHelper.getNodeValue(element, "max_failure_count", true);

        try {
            this.maxFailureCount = Integer.parseInt(failureCountStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("max_failure_count is not type of integer:" + failureCountStr);
        }

        if (this.maxFailureCount <= 0) {
            throw new IllegalArgumentException("max_failure_count must be positive, but is:" + failureCountStr);
        }
    }

    /**
     * Executes this handler.
     *
     * @param context the action context
     *
     * @return null always
     *
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

        long gameId = RequestHelper.getLongParameter(request, gameIdParamKey); // gameId from request parameter

        // obtains GameData
        GameData gameData = null;
        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

        try {
            if (golu.isUseLocalInterface()) {
                gameData = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }

            Game game = gameData.getGame(gameId);
            Date currentDate = new Date();

            if (game.getStartDate().before(currentDate) && (game.getEndDate() == null)) {
                //specified game is not active
                return this.inactiveGameResult;
            }

            HostingSlot[] slot = gameData.findCompletedSlots(gameId);
            long[] slotIds = new long[slot.length];

            for (int i = 0; i < slot.length; i++) {
                slotIds[i] = slot[i].getId().longValue();
            }

            String[] keys = gameData.getKeysForPlayer(userId, slotIds);

            String[] submitedKeys = request.getParameterValues(this.submissionParamKey);

            if (submitedKeys == null) {
                throw new HandlerExecutionException(submissionParamKey + " does not exist in request parameter");
            }

            if (Arrays.equals(submitedKeys, keys)) {
                // if the submitted keys equal the keys in game data, return null
                return null;
            } else {
                int count = 0;
                Integer failureCount = (Integer) request.getSession().getAttribute("current_faliure_count_" + gameId);

                if (failureCount != null) {
                    count = failureCount.intValue();
                }

                count++;
                request.getSession().setAttribute("current_faliure_count_" + gameId, new Integer(count));

                if (count > maxFailureCount) {
                    return this.failureCountExceededResult;
                } else {
                    return this.failureCountNotMetResult;
                }
            }
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (NamingException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (GameDataException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        } catch (RemoteException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        }
    }
}
