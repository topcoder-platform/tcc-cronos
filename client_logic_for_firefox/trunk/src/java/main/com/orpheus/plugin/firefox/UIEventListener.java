/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import netscape.javascript.JSObject;


/**
 * <p>
 * This interface defines methods used to notify event listeners of the various events that can be raised on a page and
 * then passed into this Java component via Javascript.
 * </p>
 *
 * <p>
 * Custom implementations can be used to perform various operations depending on the various method implementations.
 * Note that all implementations will run client-side, and are called AFTER the {@link FirefoxExtensionHelper} has
 * popped up a new window and made the request in response to each click event.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations of this interface do not need to be thread safe.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public interface UIEventListener {
    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Log In" button was clicked.
     * </p>
     */
    void logInClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the login was successful.
     * </p>
     */
    void successfulLogIn();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Log Out" button was clicked.
     * </p>
     */
    void logOutClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the logout was successful.
     * </p>
     */
    void successfulLogOut();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Show Active Games" button was clicked.
     * </p>
     */
    void showActiveGamesClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Show My Games" button was clicked.
     * </p>
     */
    void showMyGamesClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Show Unlocked Domains" button was clicked.
     * </p>
     */
    void showUnlockedDomainsClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Show Upcoming Games" button was clicked.
     * </p>
     */
    void showUpcomingGamesClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Show Leaders" button was clicked.
     * </p>
     */
    void showLeadersClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the "Show Latest Clue" button was clicked.
     * </p>
     */
    void showLatestClueClick();

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the current page has changed.
     * </p>
     *
     * @param newPage The new page changed to.
     */
    void pageChanged(String newPage);

    /**
     * <p>
     * This method is called from {@link FirefoxExtensionHelper} in response to a Javascript event that indicates that
     * the current working game has changed.
     * </p>
     *
     * @param newID the new game id.
     */
    void workingGameUpdate(long newID);

    /**
     * <p>
     * This method sets the JSObject used to reference the page's Javascript "window" value. The implementations can
     * use this value to call Javascript functions on the page loaded. This can be used to do things like call
     * javascript functions in the client browser instance.
     * </p>
     *
     * <p>
     * This method is called from the {@link FirefoxExtensionHelper}, right after it is added by {@link
     * FirefoxExtensionHelper#addEventListener(UIEventListener)}.
     * </p>
     *
     * @param clientWindow The JSObject that points to the current Javascript "window" object.
     *
     * @throws IllegalArgumentException if the given value is null.
     */
    void setClientWindow(JSObject clientWindow);
}
