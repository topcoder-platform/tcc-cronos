/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;


/**
 * <p>
 * <code>GameDetailHandler</code> class implements the <code>Handler</code> interface from the
 * <code>FrontController</code> component. It looks up a <code>Game</code> by its ID and loads it into a request
 * attribute of configurable name. The game ID will be parsed from a request parameter of configurable name.
 *
 * To follow the <code>Handler</code> implementation's contract in order to make it work properly with
 * <code>FrontController</code> component, two constructors are defined, one takes the configurable parameters, and
 * the other takes an <code>Element</code> object which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author StandLove, mittu
 * @version 1.0
 */
public class GameDetailHandler implements Handler {

    /**
     * <p>
     * Represents the key used to get the game id from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String gameIdParamKey;

    /**
     * <p>
     * Represents the key used to store the Game object into request's attribute Map in execute method. It is
     * initialized in constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String gameDetailKey;

    /**
     * <p>
     * Constructor with configurable arguments. Simply assign the arguments to corresponding variables.
     * </p>
     *
     * @param gameIdParamKey
     *            the key used to get the game id from the request parameter in execute method.
     * @param gameDetailKey
     *            the key used to store the Game object into request's attribute Map in execute method.
     * @throws IllegalArgumentException
     *             if any argument is null or empty string.
     */
    public GameDetailHandler(String gameIdParamKey, String gameDetailKey) {
        ImplementationHelper.checkStringValid(gameIdParamKey, "gameIdParamKey");
        ImplementationHelper.checkStringValid(gameDetailKey, "gameDetailKey");
        this.gameDetailKey = gameDetailKey;
        this.gameIdParamKey = gameIdParamKey;
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *   &lt;handler type=&quot;x&quot;&gt;
     *    &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *    &lt;game_detail_key&gt;game&lt;/game_detail_key&gt;
     *   &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null or any value mentioned is missing or empty string.
     */
    public GameDetailHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        gameDetailKey = ImplementationHelper.getElement(element, "game_detail_key");
        gameIdParamKey = ImplementationHelper.getElement(element, "game_id_param_key");
    }

    /**
     * <p>
     * It looks up a <code>Game</code> by its ID and loads it into a request attribute of configurable name. The game
     * ID will be parsed from a request parameter of configurable name. This method will return null always if the
     * specified game is loaded successfully. Otherwise <code>HandlerExecutionException</code> is thrown to wrap any
     * underlying exceptions.
     * </p>
     *
     * @param context
     *            the ActionContext object.
     * @return <code>null</code> always.
     * @throws IllegalArgumentException
     *             if the given argument is null
     * @throws HandlerExecutionException
     *             if the game id in request parameter is missing or invalid, or it fails to get Game object from
     *             GameDataHelper, or there is no such Game object for the given id.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        try {
            // parses the gameId from the request parameter.
            long gameId = Long.parseLong(context.getRequest().getParameter(gameIdParamKey));
            GameDataHelper helper = GameDataHelper.getInstance();
            // gets the game using helper.
            Game game = helper.getGame(gameId);
            // sets the request attribute with the value as game.
            context.getRequest().setAttribute(gameDetailKey, game);
        } catch (NumberFormatException formatException) {
            throw new HandlerExecutionException("Failed to execute game detail handler.", formatException);
        }
        return null;
    }
}
