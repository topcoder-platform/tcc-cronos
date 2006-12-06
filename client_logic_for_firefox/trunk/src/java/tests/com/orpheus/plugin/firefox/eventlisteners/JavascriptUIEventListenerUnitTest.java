/*
 * Copyright (C) 0006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.eventlisteners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.orpheus.plugin.firefox.MockJSObject;
import com.orpheus.plugin.firefox.UIEventType;
import com.orpheus.plugin.firefox.UnitTestHelper;


/**
 * <p>
 * Tests functionality and error cases of {@link JavascriptUIEventListener} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JavascriptUIEventListenerUnitTest extends TestCase {
    /** Represents the JSObject for testing. */
    private MockJSObject jsObject = null;

    /** Represents the <code>JavascriptUIEventListener</code> instance used for testing. */
    private JavascriptUIEventListener listener = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        jsObject = new MockJSObject();
        listener = UnitTestHelper.BuildListener();
        listener.setClientWindow(jsObject);
    }

    /**
     * <p>
     * Tests the accuracy of constructor {@link JavascriptUIEventListener#JavascriptUIEventListener()}.
     * </p>
     */
    public void testJavascriptUIEventListener_Accuracy() {
        listener = new JavascriptUIEventListener();
        assertEquals("The clientWindow value should be default value.", null,
            UnitTestHelper.getPrivateField(listener.getClass(), listener, "clientWindow"));

        assertEquals("The listenerFunctions value should be an empty map.", new HashMap(),
            UnitTestHelper.getPrivateField(listener.getClass(), listener, "listenerFunctions"));
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#logInClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLogInClick_Accuracy() throws Exception {
        listener.logInClick();
        assertEquals("The functions should called", UnitTestHelper.LOGIN_CLICK_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#successfulLogIn()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSuccessfulLogIn_Accuracy() throws Exception {
        listener.successfulLogIn();
        assertEquals("The functions should called", UnitTestHelper.SUCCESSFUL_LOGIN_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#logOutClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLogOutClick_Accuracy() throws Exception {
        listener.logOutClick();
        assertEquals("The functions should called", UnitTestHelper.LOGOUT_CLICK_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#successfulLogOut()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSuccessfulLogOut_Accuracy() throws Exception {
        listener.successfulLogOut();
        assertEquals("The functions should called", UnitTestHelper.SUCCESSFUL_LOGOUT_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#showActiveGamesClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowActiveGamesClick_Accuracy() throws Exception {
        listener.showActiveGamesClick();
        assertEquals("The functions should called", UnitTestHelper.SHOW_ACTIVE_GAMES_CLICK_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#showMyGamesClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowMyGamesClick_Accuracy() throws Exception {
        listener.showMyGamesClick();
        assertEquals("The functions should called",
                UnitTestHelper.SHOW_MY_GAMES_CLICK_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#showUnlockedDomainsClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowUnlockedDomainsClick_Accuracy() throws Exception {
        listener.showUnlockedDomainsClick();
        assertEquals("The functions should called", UnitTestHelper.SHOW_UNLOCKED_DOMAINS_CLICK_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#showUpcomingGamesClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowUpcomingGamesClick_Accuracy() throws Exception {
        listener.showUpcomingGamesClick();
        assertEquals("The functions should called", UnitTestHelper.SHOW_UPCOMING_GAMES_CLICK_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#showLeadersClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowLeadersClick_Accuracy() throws Exception {
        listener.showLeadersClick();
        assertEquals("The functions should called", UnitTestHelper.SHOW_LEADERS_CLICK_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#showLatestClueClick()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowLatestClueClick_Accuracy() throws Exception {
        listener.showLatestClueClick();
        assertEquals("The functions should called", UnitTestHelper.SHOW_LATEST_CLUE_CLICK_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#pageChanged(String)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPageChanged_Accuracy() throws Exception {
        listener.pageChanged("http://www.topcoder.com/tc");
        assertEquals("The functions should called", UnitTestHelper.PAGE_CHANGED_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#workingGameUpdate(long)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testWorkingGameUpdate_Accuracy() throws Exception {
        listener.workingGameUpdate(100);
        assertEquals("The functions should called", UnitTestHelper.WORKING_GAME_ID_UPDATE_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#addFunctionName(UIEventType, String)} when the given
     * UIEventType is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddFunctionName_NullUIEventType() throws Exception {
        try {
            listener.addFunctionName(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#addFunctionName(UIEventType, String)} when the given
     * functionName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddFunctionName_NullFunctionName() throws Exception {
        try {
            listener.addFunctionName(UIEventType.LOGIN_CLICK, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#addFunctionName(UIEventType, String)} when the given
     * functionName is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddFunctionName_EmptyFunctionName() throws Exception {
        try {
            listener.addFunctionName(UIEventType.LOGIN_CLICK, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#addFunctionName(UIEventType, String)} when the given type and
     * functionName exists, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddFunctionName_ExistFunctionName() throws Exception {
        listener.addFunctionName(UIEventType.LOGIN_CLICK, "name");

        try {
            listener.addFunctionName(UIEventType.LOGIN_CLICK, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#addFunctionName(UIEventType, String)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddFunctionName_Accuracy() throws Exception {
        listener.clearFunctionNames();
        listener.addFunctionName(UIEventType.LOGIN_CLICK, "name");

        Map functions = (Map) UnitTestHelper.getPrivateField(listener.getClass(), listener, "listenerFunctions");
        assertNotNull("The function should not be null.", functions);
        assertEquals("The size of the functions should be 1.", 1, functions.size());

        List functionNames = (List) functions.get(UIEventType.LOGIN_CLICK);

        assertNotNull("The function name list should not be null.", functionNames);
        assertEquals("The size of the function name list should be 1.", 1, functionNames.size());
        assertEquals("The function name should be add properly.", "name", functionNames.get(0));
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#removeFunctionName(UIEventType, String)} when the given
     * UIEventType is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveFunctionName_NullUIEventType() throws Exception {
        try {
            listener.removeFunctionName(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#removeFunctionName(UIEventType, String)} when the given
     * functionName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveFunctionName_NullFunctionName() throws Exception {
        try {
            listener.removeFunctionName(UIEventType.LOGIN_CLICK, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#removeFunctionName(UIEventType, String)} when the given
     * functionName is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveFunctionName_EmptyFunctionName() throws Exception {
        try {
            listener.removeFunctionName(UIEventType.LOGIN_CLICK, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#removeFunctionName(UIEventType, String)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveFunctionName_Accuracy() throws Exception {
        listener.clearFunctionNames();
        listener.addFunctionName(UIEventType.LOGIN_CLICK, "name1");
        listener.addFunctionName(UIEventType.LOGIN_CLICK, "name2");

        listener.removeFunctionName(UIEventType.LOGIN_CLICK, "name1");

        Map functions = (Map) UnitTestHelper.getPrivateField(listener.getClass(), listener, "listenerFunctions");
        assertNotNull("The function should not be null.", functions);
        assertEquals("The size of the functions should be 1.", 1, functions.size());

        List functionNames = (List) functions.get(UIEventType.LOGIN_CLICK);

        assertNotNull("The function name list should not be null.", functionNames);
        assertEquals("The size of the function name list should be 1.", 1, functionNames.size());
        assertEquals("The function name should be add properly.", "name2", functionNames.get(0));
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#clearFunctionNames()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testClearFunctionNames_Accuracy() throws Exception {
        Map functions = (Map) UnitTestHelper.getPrivateField(listener.getClass(), listener, "listenerFunctions");
        assertNotNull("The function should not be null.", functions);
        assertNotSame("The size of the functions should not be 0.", new Integer(0), new Integer(functions.size()));

        listener.clearFunctionNames();

        functions = (Map) UnitTestHelper.getPrivateField(listener.getClass(), listener, "listenerFunctions");
        assertNotNull("The function should not be null.", functions);
        assertEquals("The size of the functions should be 0.", 0, functions.size());
    }

    /**
     * <p>
     * Tests the method {@link JavascriptUIEventListener#setClientWindow(JSObject)} when the given clientWindow is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetClientWindow_NullClientWindow() throws Exception {
        try {
            listener.setClientWindow(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link JavascriptUIEventListener#setClientWindow(JSObject)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetClientWindow_Accuracy() throws Exception {
        listener.setClientWindow(jsObject);
        assertEquals("The clientWindow value should be set properly.", this.jsObject,
            UnitTestHelper.getPrivateField(listener.getClass(), listener, "clientWindow"));
    }
}
