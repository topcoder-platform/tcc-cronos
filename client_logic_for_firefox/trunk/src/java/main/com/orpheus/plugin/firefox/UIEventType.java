/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This enumeration holds values that indicate the type of an event. These enumeration values are used in both
 * <code>JavascriptUIEventListener</code> and {@link FirefoxExtensionHelper} to relate items to specific events. In
 * {@link FirefoxExtensionHelper}, the event types are associated in a Map to the page to load in response to the
 * event, and in <code>JavascriptUIEventListener</code>, the class maps UIEventType values to a list of Javascript
 * functions to call in the client browser in response to the event types.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is immutable and thread safe.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class UIEventType extends Enum {
    /** This value indicates the event where the "Log In" button has been clicked. */
    public static final UIEventType LOGIN_CLICK = new UIEventType("login");

    /** This value indicates the successful login event type. */
    public static final UIEventType SUCCESSFUL_LOGIN = new UIEventType("successful login");

    /** This value indicates the event where the "Log Out" button has been clicked. */
    public static final UIEventType LOGOUT_CLICK = new UIEventType("logout");

    /** This value indicates an event of a successful logout. */
    public static final UIEventType SUCCESSFUL_LOGOUT = new UIEventType("successful logout");

    /** This value indicates the event where the "Show Active Games" button has been clicked. */
    public static final UIEventType SHOW_ACTIVE_GAMES_CLICK = new UIEventType("show active games");

    /** This value indicates the event where the "Show My Games" button has been clicked. */
    public static final UIEventType SHOW_MY_GAMES_CLICK = new UIEventType("show my games");

    /** This value indicates the event where the "Show Unlocked Domains" button has been clicked. */
    public static final UIEventType SHOW_UNLOCKED_DOMAINS_CLICK = new UIEventType("show unlocked domains");

    /** This value indicates the event where the "Show Upcoming Games" button has been clicked. */
    public static final UIEventType SHOW_UPCOMING_GAMES_CLICK = new UIEventType("show upcoming games");

    /** This value indicates the event where the "Show Leaders" button has been clicked. */
    public static final UIEventType SHOW_LEADERS_CLICK = new UIEventType("show leaders");

    /** This value indicates the event where the "Show Latest Clue" button has been clicked. */
    public static final UIEventType SHOW_LATEST_CLUE_CLICK = new UIEventType("show latest clue");

    /** This value indicates the event where the user has changed what page is displayed. */
    public static final UIEventType PAGE_CHANGED = new UIEventType("page changed");

    /** This value indicates the event where the user has changed what game they are working on. */
    public static final UIEventType WORKING_GAME_ID_UPDATE = new UIEventType("working game id update");

    /**
     * <p>
     * This member variable holds the name of the event type, set in the constructor.
     * </p>
     *
     * <p>
     * This value cannot be null or an empty string, and it can be accessed through both the getName() and toString()
     * method.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * This constructor sets the name member variable value to the value of the parameter given.
     * </p>
     *
     * @param name The name of the event type.
     *
     * @throws IllegalArgumentException if the name is null or an empty string.
     */
    private UIEventType(String name) {
        ClientLogicForFirefoxHelper.validateString(name, "name");
        this.name = name;
    }

    /**
     * <p>
     * This method allows the user to return the string name of the event type.
     * </p>
     *
     * @return The value of the name member variable.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * This method allows the user to return the string representation of the event type.
     * </p>
     *
     * @return The value of the name member variable
     */
    public String toString() {
        return name;
    }
}
