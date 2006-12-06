/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import netscape.javascript.JSObject;

import com.orpheus.plugin.firefox.UIEventListener;

/**
 * Defines a mocked implementation of <code>UIEventListener</code> class. The last method call is recorded.
 * 
 * @author visualage
 * @version 1.0
 */
public class MockUIEventListener implements UIEventListener {
    /**
     * Represents the last called method name.
     */
    private String method;

    /**
     * Represents the client window set by <code>setClientWindow(JSObject)</code>.
     */
    private JSObject window;

    /**
     * Gets the client window.
     * 
     * @return the client window.
     */
    public JSObject getWindow() {
        return window;
    }

    /**
     * Defines a mocked version of <code>logInClick()</code>.
     */
    public void logInClick() {
        method = "logInClick()";
    }

    /**
     * Defines a mocked version of <code>logOutClick()</code>.
     */
    public void logOutClick() {
        method = "logOutClick()";
    }

    /**
     * Defines a mocked version of <code>pageChanged(String)</code>.
     */
    public void pageChanged(String newPage) {
        method = "pageChanged(\"" + newPage + "\")";
    }

    /**
     * Defines a mocked version of <code>setClientWindow(JSObject)</code>.
     */
    public void setClientWindow(JSObject clientWindow) {
        window = clientWindow;
    }

    /**
     * Defines a mocked version of <code>showActiveGamesClick()</code>.
     */
    public void showActiveGamesClick() {
        method = "showActiveGamesClick()";
    }

    /**
     * Defines a mocked version of <code>showLatestClueClick()</code>.
     */
    public void showLatestClueClick() {
        method = "showLatestClueClick()";
    }

    /**
     * Defines a mocked version of <code>showLeadersClick()</code>.
     */
    public void showLeadersClick() {
        method = "showLeadersClick()";
    }

    /**
     * Defines a mocked version of <code>showMyGamesClick()</code>.
     */
    public void showMyGamesClick() {
        method = "showMyGamesClick()";
    }

    /**
     * Defines a mocked version of <code>showUnlockedDomainsClick()</code>.
     */
    public void showUnlockedDomainsClick() {
        method = "showUnlockedDomainsClick()";
    }

    /**
     * Defines a mocked version of <code>showUpcomingGamesClick()</code>.
     */
    public void showUpcomingGamesClick() {
        method = "showUpcomingGamesClick()";
    }

    /**
     * Defines a mocked version of <code>successfulLogIn()</code>.
     */
    public void successfulLogIn() {
        method = "successfulLogIn()";
    }

    /**
     * Defines a mocked version of <code>successfulLogOut()</code>.
     */
    public void successfulLogOut() {
        method = "successfulLogOut()";
    }

    /**
     * Defines a mocked version of <code>workingGameUpdate(long)</code>.
     */
    public void workingGameUpdate(long newID) {
        method = "workingGameUpdate(" + newID + ")";
    }

    /**
     * Gets the last called method in this instance.
     * 
     * @return the last called method in this instance.
     */
    public String getCalledMethod() {
        return method;
    }
}
