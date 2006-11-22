/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * <p>
 * <code>UserGamesHandler</code> class implements the <code>Handler</code> interface from the
 * <code>FrontController</code> component. It determines which games the current (logged-in) user is registered for
 * and loads an array containing them (as <code>Game</code> instances) into a request attribute of configurable name.
 * The <code>LoginHandler</code> will be used to get logged-in user's details, and a proper resultCode is returned in
 * execute method if user is not logged in.
 *
 * To follow the <code>Handler</code> implementation's contract in order to make it work properly with
 * <code>FrontController</code> component, two constructors are defined, one takes the configurable parameters, and
 * the other takes an <code>Element</code> object which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class UserGamesHandler implements Handler {

    /**
     * <p>
     * Represents the attribute key used to store a Game array into the request's attribute Map in execute method. It is
     * initialized in constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String gamesKey;

    /**
     * <p>
     * Represents the result code to return in the execute method if the user is not logged-in. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String notLoggedInResultCode;

    /**
     * <p>
     * Constructor with the gamesKey and notLoggedInResultCode arguments. Simply assign the arguments to corresponding
     * variables.
     * </p>
     *
     * @param gamesKey
     *            the attribute key used to store a Game array into the request's attribute Map in execute method.
     * @param notLoggedInResultCode
     *            the result code to return in the execute method if the user is not logged-in.
     * @throws IllegalArgumentException
     *             if any argument is null or empty string.
     */
    public UserGamesHandler(String gamesKey, String notLoggedInResultCode) {
        ImplementationHelper.checkStringValid(gamesKey, "gamesKey");
        ImplementationHelper.checkStringValid(notLoggedInResultCode, "notLoggedInResultCode");
        this.gamesKey = gamesKey;
        this.notLoggedInResultCode = notLoggedInResultCode;
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *  &lt;handler type=&quot;x&quot;&gt;
     *   &lt;games_key&gt;games&lt;/games_key&gt;
     *   &lt;not_logged_in_result_code&gt;not_logged_in&lt;/not_logged_in_result_code&gt;
     *  &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null or any value mentioned in implementation note is missing or empty string.
     */
    public UserGamesHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        gamesKey = ImplementationHelper.getElement(element, "games_key");
        notLoggedInResultCode = ImplementationHelper.getElement(element, "not_logged_in_result_code");
    }

    /**
     * <p>
     * It determines which games the current (logged-in) user is registered for and loads an array containing them (as
     * Game instances) into a request attribute of configurable name. The <code>LoginHandler</code> will be used to
     * get logged-in user's details, and a proper resultCode is returned if user is not logged in, null is returned if
     * user is logged-in, and the games for this user are loaded successfully (it is ok if the games array is of zero
     * length). Otherwise, <code>HandlerExecutionException</code> is thrown.
     * </p>
     *
     * @param context
     *            the ActionContext object.
     * @return null if this operation succeeds or proper result code if user is not logged in.
     * @throws IllegalArgumentException
     *             if the given argument is null.
     * @throws HandlerExecutionException
     *             if fail to load user's games, or the UserProfile's identifier is not Long object.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        GameDataHelper helper = GameDataHelper.getInstance();
        try {
            // gets the user profile from the LoginHandler.
            UserProfile userProfile = LoginHandler.getAuthenticatedUser(context.getRequest().getSession(true));
            // if profile is missing, return result code.
            if (userProfile == null) {
                return notLoggedInResultCode;
            }
            long profileId = ((Long) userProfile.getIdentifier()).longValue();
            // gets the registered games using the helper.
            Game[] games = helper.getRegisteredGames(profileId);
            // saves the games to the request attribute.
            context.getRequest().setAttribute(gamesKey, games);
        } catch (ClassCastException classCastException) {
            throw new HandlerExecutionException("Failed to execute user games handler.", classCastException);
        } catch (IllegalArgumentException exception) {
            // session is null.
            return notLoggedInResultCode;
        }
        return null;
    }
}
