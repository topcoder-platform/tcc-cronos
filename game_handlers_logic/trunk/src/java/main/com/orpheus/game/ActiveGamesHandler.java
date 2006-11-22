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
 * ActiveGamesHandler class implements the <code>Handler</code> interface from the <code>FrontController</code>
 * component. It determines which are the currently active games and loads an array containing them (as
 * <code>Game</code> instances) into a request attribute of configurable name. To follow the <code>Handler</code>
 * implementation's contract in order to make it work properly with <code>FrontController</code> component, two
 * constructors are defined, one takes the configurable parameters, and the other takes an <code>Element</code> object
 * which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class ActiveGamesHandler implements Handler {

    /**
     * <p>
     * Represents the attribute key used to store a <code>Game</code> array into the request's attribute Map in
     * execute method. It is initialized in constructor and never changed afterwards. It must be non-null, non-empty
     * string.
     * </p>
     */
    private final String gamesKey;

    /**
     * <p>
     * Constructor with the gamesKey argument. Simply assign the argument to this.gamesKey variable.
     * </p>
     *
     * @param gamesKey
     *            attribute key used to store a Game array into the request's attribute Map in execute method.
     * @throws IllegalArgumentException
     *             if the argument is null or empty string
     */
    public ActiveGamesHandler(String gamesKey) {
        ImplementationHelper.checkStringValid(gamesKey, "gamesKey");
        this.gamesKey = gamesKey;
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null or the values configured in xml element are missing or invalid. (games_key
     *             node is required, and its node value must be non-null, non-empty string.)
     */
    public ActiveGamesHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        gamesKey = ImplementationHelper.getElement(element, "games_key");
    }

    /**
     * <p>
     * It determines which are the currently active games and loads an array containing them (as <code>Game</code>
     * instances) into a request attribute of configurable name. This method will return <code>null</code> always if
     * the active games are loaded successfully (it's ok if loaded games array is of zero length). Otherwise
     * <code>HandlerExecutionException</code> is thrown to wrap any underlying exceptions.
     * </p>
     *
     * @param context
     *            the ActionContext object.
     * @return <code>null</code> always.
     * @throws IllegalArgumentException
     *             if the given argument is null.
     * @throws HandlerExecutionException
     *             if failed to load active games.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        GameDataHelper helper = GameDataHelper.getInstance();
        // get the active games using helper and set it to the request.
        Game[] games = helper.getActiveGames();
        context.getRequest().setAttribute(gamesKey, games);
        return null;
    }
}
