/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.orpheus.plugin.firefox.eventlisteners.JavascriptUIEventListener;

import junit.framework.TestCase;

import netscape.javascript.JSObject;


/**
 * <p>
 * Demonstrates the usage of this component. Since the JavaScript will delegate all the functions to {@link
 * FirefoxExtensionHelper} class, so we show the usage of {@link FirefoxExtensionHelper} class which will also show
 * what the JavaScript can do.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /** Represents the {@link FirefoxExtensionHelper} instance. */
    private FirefoxExtensionHelper helper;

    /** Represents the {@link UIEventListener} instance. */
    private JavascriptUIEventListener listener;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();

        // create the FirefoxExtensionHelper instance and initialize it
        helper = new FirefoxExtensionHelper();

        JSObject jsObject = new MockJSObject();
        UnitTestHelper.setPrivateField(helper.getClass(), helper, "clientWindow", jsObject);
        helper.initialize();

        // create the JavascriptUIEventListener instance
        listener = new JavascriptUIEventListener();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * <p>
     * This demo will demonstrate typical usage of JavaScript calls.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testTypicalJavaScriptCalls_Usage() throws Exception {
        helper.successfulLogIn();

        // set the poll time to 10 minutes
        helper.setPollTime(10);

        // set the working game ID
        helper.setWorkingGameID(12);

        // get the working game ID, event listeners will also be notified
        long id = helper.getWorkingGameID();
        System.out.println(id);

        // set the current target ID
        helper.setCurrentTargetID("lksjlksjeoijlsiejf", 0);

        // create an element string to test against the current target
        String target = "<element> tagert </element>";

        // this could cause a popup to occur if the object given matches the current target.
        helper.currentTargetTest(target);

        // popup a window
        helper.popupWindow("http://www.google.com", true, false, true, false, false, false, false, 480, 640);

        // force a poll to the server, this could cause a popup if the server returns a message
        helper.pollServerNow();

        // the user changed a page, if somegame.com is a valid game domain, a popup will appear
        helper.pageChanged("http://www.somegame.com");

        // function customLogIn() {
        //   alert("Log In was clicked");
        // }
        // add a custom JavaScript event handler
        listener.addFunctionName(UIEventType.LOGIN_CLICK, "customLogIn");

        // remove the custom event handler
        listener.removeFunctionName(UIEventType.LOGIN_CLICK, "customLogIn");

        helper.successfulLogOut();
    }
}
