/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


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
 * <p>
 * Contingencies and results:
 * <ol>
 * <li>
 * If the specified game is not active then the handler returns a configurable result code characteristic of the
 * problem, regardless of the values of the keys.
 * </li>
 * <li>
 * If the keys do not match then the player¡¯s failure count for the game and slot is incremented. The handler will
 * return null if user exhausts his available attempts, otherwise a configurable result string.
 * </li>
 * <li>
 * If the keys do match then the handler records a game completion for the player in the specified game (via the game
 * data persistence component), then returns successResult("success" by default)
 * </li>
 * </ol>
 * This class is thread safe since it does not contain any mutable state.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class KeySubmissionHandler implements Handler {
    private static final String DEFAULT_SUCCESS_RESULT = "success";

    /** Represent the result name this handler will return if the max failure is not met. */
    private final String failureCountNotMetResult;

    /** A configurable param name to retrieve the game id from the request parameters. */
    private final String gameIdParamKey;

    /** Represent the result name this handler will return if the game is inactive. */
    private final String inactiveGameResult;
    
    /** Represent the result name this handler will return if user submit the correct keys. */
    private final String successResult;

    /**
     * A configurable param name to retrieve the key submissions from the request parameters(The value retrieved is a
     * string array.).
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
     * @param successResult the returned result when use submits the correct keys, "success" will be used if it's 
     *  set to be null 
     * @param failureCountNotMetResult the failure count not met result
     * @param maxFailureCount the max failure count
     *
     * @throws IllegalArgumentException if either of arguments is null or empty string
     */
    public KeySubmissionHandler(String gameIdParamKey, String submissionParamKey, String inactiveGameResult,
        String successResult, String failureCountNotMetResult, int maxFailureCount) {
        ParameterCheck.checkNullEmpty("gameIdParamKey", gameIdParamKey);
        ParameterCheck.checkNullEmpty("submissionParamKey", submissionParamKey);
        ParameterCheck.checkNullEmpty("inactiveGameResult", inactiveGameResult);
        ParameterCheck.checkNullEmpty("failureCountNotMetResult", failureCountNotMetResult);
        ParameterCheck.checkNotPositive("maxFailureCount", maxFailureCount);

        this.gameIdParamKey = gameIdParamKey;
        this.submissionParamKey = submissionParamKey;
        this.inactiveGameResult = inactiveGameResult;
        this.failureCountNotMetResult = failureCountNotMetResult;
        this.maxFailureCount = maxFailureCount;
        this.successResult = initSuccessResult(successResult);
    }
    
    /**
     * Create the instance from element. The structure of the Element will be like this:
     * <pre>&lt;handler type=&quot;x&quot;&gt;
     *  &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *  &lt;submission_param_key&gt;submissions&lt;/submission_param_key&gt;
     *  &lt;max_failure_count&gt;10&lt;/max_failure_count&gt;
     *  &lt;inactive_game_result&gt;inactive_game_result&lt;/inactive_game_result&gt;
     *  &lt;failure_count_not_met_result&gt;count_not_met_result&lt;/failure_count_not_met_result&gt;
     *  &lt;success_result&gt;success&lt;/success_result&gt;
     *  &lt;/handler&gt;</pre>
     * <p>
     * <br> Following is simple explanation of the above XML structure.
     * </p>
     * <p>
     * The handler¡¯s type attribute is required by Front Controller component, it won¡¯t be used in this design. <br>
     * The game_id_param_key node¡¯s value represents the http request parameter name to get the game id<br>
     * The submission_param_key node value represents the http request parameter name to get the key submissions<br>
     * The max_failure_count node represents the max failure count of the comparision of the submitted submissions
     * with these retrieved from EJB<br>
     * The failure_count_not_met_result node represents the result name this handler will return if the max count is
     * not met<br>
     * The success_result node represents the result name this handler will return if user submit the correct keys.
     * It's an optional property, "success" will be set if it's missing.<br>
     * </p>
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
        this.failureCountNotMetResult = XMLHelper.getNodeValue(element, "failure_count_not_met_result", true);
        
        String successResult = XMLHelper.getNodeValue(element, "success_result", false);
        this.successResult = initSuccessResult(successResult);

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
     * Processes user's  key submissions.
     *
     * @param context the action context
     *
     * @return null if execution successfully otherwise a configurable forward name
     *
     * @throws HandlerExecutionException if any error occurred while executing this handler
     * @throws IllegalArgumentException if the context is null
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
        GameDataLocal gameDataLocal = null;
        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

        try {
            if (golu.isUseLocalInterface()) {
                gameDataLocal = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }

            Game game = golu.isUseLocalInterface()?gameDataLocal.getGame(gameId):gameData.getGame(gameId);
            
            Date currentDate = new Date();

            if (game.getStartDate().after(currentDate) || (game.getEndDate() != null)) {
                //specified game is not active
                return this.inactiveGameResult;
            }

            HostingSlot[] slot = golu.isUseLocalInterface()?gameDataLocal.findCompletedSlots(gameId):gameData.findCompletedSlots(gameId);
            long[] slotIds = new long[slot.length];

            for (int i = 0; i < slot.length; i++) {
                slotIds[i] = slot[i].getId().longValue();
            }

            String[] keys = golu.isUseLocalInterface()?gameDataLocal.getKeysForPlayer(userId, slotIds):gameData.getKeysForPlayer(userId, slotIds);

            String[] submittedKeys = request.getParameterValues(this.submissionParamKey);

            if (submittedKeys == null) {
                throw new HandlerExecutionException(submissionParamKey + " does not exist in request parameter");
            }

            Arrays.sort(keys);
            Arrays.sort(submittedKeys);

            if (Arrays.equals(submittedKeys, keys)) {
                // if the submitted keys equal the keys in game data, return the result string
                return successResult;
            } else {
                Integer failureCount = (Integer) request.getSession().getAttribute("current_faliure_count_" + gameId);
                int count = ((failureCount != null) ? (failureCount.intValue() + 1) : 1);

                request.getSession().setAttribute("current_faliure_count_" + gameId, new Integer(count));

                if (count > maxFailureCount) {
                    return null;
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
        } catch (Exception e) {
             throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        }
    }
    
    /**
     * Set the success result string to the given one. If the argument is null, the default value will be used.
     *@param result success result string
     *@return the result string to be set
     *@throws IllegalArgumentException if the given result is empty string
     */
    private String initSuccessResult(String result){
        if (result != null){
            if (result.trim().length() == 0){
                throw new IllegalArgumentException("successResult should not be empty");
            }
            return result;
        }else{
            return DEFAULT_SUCCESS_RESULT;
        }
    }
}
