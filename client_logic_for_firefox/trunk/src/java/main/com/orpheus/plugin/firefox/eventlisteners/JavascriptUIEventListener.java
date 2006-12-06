/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.eventlisteners;

import com.orpheus.plugin.firefox.ClientLogicForFirefoxHelper;
import com.orpheus.plugin.firefox.UIEventListener;
import com.orpheus.plugin.firefox.UIEventType;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * The default implementation of the {@link UIEventListener} interface of this component to call registered JavaScript
 * functions in the client browser in response to events raised through the <code>FirefoxExtensionHelper</code>. The
 * flow is as follows: The browser calls the registered JavaScript function for a click event. That function calls the
 * Java <code>FirefoxExtensionHelper</code> method for that click event and the <code>FirefoxExtensionHelper</code>
 * calls this class in response to the click event. Then, this class calls back out to a different JavaScript
 * function.
 * </p>
 *
 * <p>
 * Basically, this class allows us to have both JavaScript and Java event handler functions for a click event raised by
 * the user.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable as its registered listener functions and clientWindow can change after
 * instantiation.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class JavascriptUIEventListener implements UIEventListener {
    /**
     * <p>
     * Represents a reference to the window the JavaScript code is running in. We use this value to call JavaScript
     * functions in response to events raised.
     * </p>
     *
     * <p>
     * This value is initially null and is set through the {@link UIEventListener#setClientWindow(JSObject)} method.
     * This value cannot be set to null.
     * </p>
     */
    private JSObject clientWindow = null;

    /**
     * <p>
     * Represents the mapping from {@link UIEventType} values to Lists of Strings. Each String in the List value is a
     * JavaScript function name that is called in response to the event that matches the {@link UIEventType}. Each
     * JavaScript function name should map to a parameterless JavaScript function that has no return value.
     * </p>
     *
     * <p>
     * The values in this object are changed via the add / remove / clear Functionname methods in this class. Each key
     * and value can not be null or empty string. Duplicated {@link UIEventType} and function name is not allowed.
     * </p>
     */
    private Map listenerFunctions = new HashMap();

    /**
     * <p>
     * This empty constructor does nothing. Method of {@link UIEventListener#setClientWindow(JSObject)} should be
     * called before any other methods to ensure the class has something to work with the JavaScript functions.
     * </p>
     */
    public JavascriptUIEventListener() {
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Log In" button was clicked.
     * </p>
     */
    public void logInClick() {
        callAllFunctions(UIEventType.LOGIN_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the login was successful.
     * </p>
     */
    public void successfulLogIn() {
        callAllFunctions(UIEventType.SUCCESSFUL_LOGIN);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Log Out" button was clicked.
     * </p>
     */
    public void logOutClick() {
        callAllFunctions(UIEventType.LOGOUT_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the logout was successful.
     * </p>
     */
    public void successfulLogOut() {
        callAllFunctions(UIEventType.SUCCESSFUL_LOGOUT);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Show Active Games" button was clicked.
     * </p>
     */
    public void showActiveGamesClick() {
        callAllFunctions(UIEventType.SHOW_ACTIVE_GAMES_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Show My Games" button was clicked.
     * </p>
     */
    public void showMyGamesClick() {
        callAllFunctions(UIEventType.SHOW_MY_GAMES_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Show Unlocked Domains" button was clicked.
     * </p>
     */
    public void showUnlockedDomainsClick() {
        callAllFunctions(UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Show Upcoming Games" button was clicked.
     * </p>
     */
    public void showUpcomingGamesClick() {
        callAllFunctions(UIEventType.SHOW_UPCOMING_GAMES_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Show Leaders" button was clicked.
     * </p>
     */
    public void showLeadersClick() {
        callAllFunctions(UIEventType.SHOW_LEADERS_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the "Show Latest Clue" button was clicked.
     * </p>
     */
    public void showLatestClueClick() {
        callAllFunctions(UIEventType.SHOW_LATEST_CLUE_CLICK);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the current page has changed.
     * </p>
     *
     * @param newPage The new page changed to.
     */
    public void pageChanged(String newPage) {
        callAllFunctions(UIEventType.PAGE_CHANGED);
    }

    /**
     * <p>
     * This method is called from <code>FirefoxExtensionHelper</code> in response to a JavaScript event that indicates
     * that the current working game has changed.
     * </p>
     *
     * @param newID the new game id.
     */
    public void workingGameUpdate(long newID) {
        callAllFunctions(UIEventType.WORKING_GAME_ID_UPDATE);
    }

    /**
     * <p>
     * This method adds the given JavaScript function name as an event handler for the {@link UIEventType} value given.
     * </p>
     *
     * @param eventType The UIEventType value to associate the function name with.
     * @param functionName The name of the JavaScript function to call in response to the event being raised.
     *
     * @throws IllegalArgumentException if either parameter is null, or if the functionName is an empty string, or if
     *         duplicated pair of (eventType, functionName) exists.
     */
    public void addFunctionName(UIEventType eventType, String functionName) {
        ClientLogicForFirefoxHelper.validateNotNull(eventType, "eventType");
        ClientLogicForFirefoxHelper.validateString(functionName, "functionName");

        List functions = (List) listenerFunctions.get(eventType);

        if (functions == null) {
            functions = new ArrayList();
            listenerFunctions.put(eventType, functions);
        }

        if (functions.contains(functionName)) {
            throw new IllegalArgumentException("Duplicated functionName: " + functionName
                + " under the same UIEventType: " + eventType + ".");
        }

        functions.add(functionName);
    }

    /**
     * <p>
     * This method removes the given JavaScript function name as an event handler for the {@link UIEventType} value
     * given.
     * </p>
     *
     * @param eventType The UIEventType to remove the function name from.
     * @param functionName The name of the function to remove as an event listener.
     *
     * @throws IllegalArgumentException if either parameter is null, or if the functionName is an empty string.
     */
    public void removeFunctionName(UIEventType eventType, String functionName) {
        ClientLogicForFirefoxHelper.validateNotNull(eventType, "eventType");
        ClientLogicForFirefoxHelper.validateString(functionName, "functionName");

        List functions = (List) listenerFunctions.get(eventType);

        if (functions != null) {
            functions.remove(functionName);
        }
    }

    /**
     * <p>
     * This method clears all JavaScript function names as handlers for the UI events.
     * </p>
     */
    public void clearFunctionNames() {
        listenerFunctions.clear();
    }

    /**
     * <p>
     * Gets the List value from the listenerFunctions Map that corresponds to the given UIEventType value. For each
     * string value in the List retrieved, that JavaScript function should be called.
     * </p>
     *
     * @param functionType the given UIEventType value.
     */
    private void callAllFunctions(UIEventType functionType) {
        if (clientWindow == null) {
            return;
        }

        List functions = (List) listenerFunctions.get(functionType);

        if (functions != null) {
            for (Iterator iter = functions.iterator(); iter.hasNext();) {
                try {
                    clientWindow.eval((String) iter.next() + "();");
                } catch (JSException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * <p>
     * This method sets the JSObject used to reference the page's JavaScript "window" value. The implementations can
     * use this value to call JavaScript functions on the page loaded. This can be used to do things like call
     * JavaScript functions in the client browser instance.
     * </p>
     *
     * <p>
     * This method is called from the <code>FirefoxExtensionHelper</code>, right after it is added by
     * "addEventListener(UIEventListener)" method.
     * </p>
     *
     * @param clientWindow The JSObject that points to the current JavaScript "window" object.
     *
     * @throws IllegalArgumentException if the given value is null.
     */
    public void setClientWindow(JSObject clientWindow) {
        ClientLogicForFirefoxHelper.validateNotNull(clientWindow, "clientWindow");
        this.clientWindow = clientWindow;
    }
}
