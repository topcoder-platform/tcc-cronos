/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * A Handler that processes key submissions. It will accept a game ID via a request parameter of configurable name, and
 * several key strings as multiple values of another request parameter, also of configurable name. It will determine
 * whether the submitted keys match those recorded for the currently logged-in player for the specified game for the
 * most recently k hosting slots traversed by the Ball, where k is the key count of the specified game. The
 * handler will maintain a count, in the player's session, of the number of failures to submit matching keys for the
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
 * If the keys do not match then the player's failure count for the game and slot is incremented. The handler will
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

    /** The result code returned when key submission is successful */
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
     * The handler's type attribute is required by Front Controller component, it won't be used in this design. <br>
     * The game_id_param_key node's value represents the http request parameter name to get the game id<br>
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

        // gameId from request parameter:
        long gameId = RequestHelper.getLongParameter(request, gameIdParamKey);

        try {
            // obtains GameData
            GameData gameData = null;
            GameDataLocal gameDataLocal = null;
            GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

            if (golu.isUseLocalInterface()) {
                gameDataLocal = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }

            try {
                Game game = golu.isUseLocalInterface()
                        ? gameDataLocal.getGame(gameId)
                        : gameData.getGame(gameId);

//                if ((game.getEndDate() != null) || game.getStartDate().after(new Date())) {
                if ((game.getEndDate() != null) && (game.getEndDate().compareTo(new Date()) <= 0)
                    || game.getStartDate().after(new Date())) {
                    //specified game is not active
                    return this.inactiveGameResult;
                }

		String attributeName = getFailureCountName(game);
                Integer failureAttribute
		       	= (Integer) request.getSession().getAttribute(attributeName);
                int failureCount = ((failureAttribute != null) ? failureAttribute.intValue() : 0);

		// Don't allow extra attempts if the user has already exhausted their chances
                if (failureCount > maxFailureCount) {
                    // Cheater!  Maybe we should log this in a future version.
                    return null;
                }

                long[] slotIds = getPastHostingSlotIds(game);

                /*
                 * Get the player's keys for the most recent slots traversed.
                 * The result should not be null, but may contain null elements
                 */
                String[] keys = golu.isUseLocalInterface()
                        ? gameDataLocal.getKeysForPlayer(userId, slotIds)
                        : gameData.getKeysForPlayer(userId, slotIds);

                /*
                 * Get the keys submitted by the player.  The result may be null
                 * (meaning that the expected request parameter is not present,
                 * probably because the client has manipulated the request
                 * parameters by hand), but if non-null it can be relied upon to
                 * not contain null elements.
                 */
                String[] submittedKeys
                        = request.getParameterValues(this.submissionParamKey);

                if (submittedKeys == null) {
                    throw new HandlerExecutionException(submissionParamKey
                            + " is not among the request parameters");
                }

                Arrays.sort(submittedKeys);

                try {
                    Arrays.sort(keys);
                } catch (NullPointerException npe) {
                    /*
                     * one of the keys is null, indicating that the player has
                     * not completed the corresponding slot.  This is a
                     * non-exceptional failure result.  Moreover, no special
                     * handling is required because the upcoming Arrays.equals()
                     * test can be relied upon to fail, producing the
                     * appropriate behavior.
                     */
                }

                if (Arrays.equals(submittedKeys, keys)) {
                    // the submitted keys match the required ones
                    return successResult;
                } else {

                    // The keys don't match

                    failureCount++;

                    request.getSession().setAttribute(attributeName, new Integer(failureCount));

                    if (failureCount > maxFailureCount) {
                        return null;
                    } else {
                        return this.failureCountNotMetResult;
                    }
                }
            } finally {
                if (gameDataLocal != null) {
                    gameDataLocal.remove();
                } else if (gameData != null) {
                    gameData.remove();
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

    /*
     * <p>
     * Retrieves the name of the failure count attribute to use with the current game;
     * the value may depend on the game state.
     * </p><p>
     * Implementation note: this version returns the string
     * <code>"current_failure_count_"</code> followed by the ID of the current slot in the
     * specified game, or <code>null</code> if there is no current slot.  This relies on
     * the fact that slot IDs are unique across all games, and it prevents key submission
     * failures for one slot from being counted against a subsequent slot.
     * </p>
     *
     * @param  game the <code>Game</code> to which the attribute will apply
     *
     * @return the attribute name as a <code>String</code>
     */
    private String getFailureCountName(Game game) {
	// find the ID of the current slot
	HostingBlock[] blocks = game.getBlocks();

	for (int blockIndex = 0; blockIndex < blocks.length; blockIndex++) {
	    HostingSlot[] slots = blocks[blockIndex].getSlots();

	    for (int slotIndex = 0; slotIndex < slots.length; slotIndex++) {
		HostingSlot slot = slots[slotIndex];

		if ((slot.getHostingEnd() == null) && (slot.getHostingStart() != null)) {
		    return "current_failure_count_" + ((Long) slot.getId()).longValue();
		}
	    }
	}

	return null;
    }

    /**
     * <p>
     * Returns an array of the ids of the last <code>game.getKeyCount()</code>
     * slots in the specified game that have finished hosting (i.e. the IDs of
     * the recent Ball hosts for which the player must provide keys to achieve
     * a success result).
     * </p><p>
     * <strong>NOTE:</strong> This method assumes that the slot currently
     * hosting the Ball itself is not marked as having stopped hosting.
     * </p>
     *
     * @param  game the <code>Game</code> to consider
     *
     * @return a <code>long[]</code> containing the IDs
     *
     * @throws HandlerExecutionException if there are fewer past slots in the
     *         game than the number of keys required
     */
    private long[] getPastHostingSlotIds(Game game) throws HandlerExecutionException {
        long[] slotIds = new long[game.getKeyCount()];
        List slotIdList = new ArrayList();
        HostingBlock[] blocks = game.getBlocks();

        // Find those slots that have finished hosting.  Be thorough, not clever
        for (int blockNumber = 0; blockNumber < blocks.length; blockNumber++) {
            HostingSlot[] slots = blocks[blockNumber].getSlots();

            for (int slotNumber = 0; slotNumber < slots.length; slotNumber++) {
                if (slots[slotNumber].getHostingEnd() != null) {
                    // A slot traversed by the Ball
                    slotIdList.add(slots[slotNumber].getId());
                }
            }
        }

        try {

            /*
             * Take the last game.getKeyCount() slots.  This assumes that the blocks
             * and slots are ordered according to their sequence numbers
             */
            int offset = slotIdList.size() - slotIds.length;

            for (int idIndex = 0; idIndex < slotIds.length; idIndex++) {
                slotIds[idIndex] = ((Long) slotIdList.get(idIndex + offset)).longValue();
            }
        } catch (IndexOutOfBoundsException ioobe) {

            /*
             * The Ball has not traversed enough sites yet; the system should
             * not have permitted this handler to be invoked
             */
            throw new HandlerExecutionException(
                "Too few slots traversed for key submission in game "
                + game.getId());
        }

        // all done
        return slotIds;
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
