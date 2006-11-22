/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.w3c.dom.Element;

import com.orpheus.game.ActiveGamesHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Test case for <code>ActiveGamesHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class ActiveGamesHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private ActiveGamesHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        handler = new ActiveGamesHandler("test");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for ActiveGamesHandler(java.lang.String).
     * In this case, the input is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testActiveGamesHandler_String_Null() {
        try {
            new ActiveGamesHandler((String)null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ActiveGamesHandler(java.lang.String).
     * In this case, the input is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testActiveGamesHandler_String_Empty() {
        try {
            new ActiveGamesHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ActiveGamesHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testActiveGamesHandlerElement_Null() {
        try {
            new ActiveGamesHandler((Element) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ActiveGamesHandler(org.w3c.dom.Element).
     * In this case, the games_key node is missing.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testActiveGamesHandlerElement_MissGamesKey() throws Exception {
        try {
            String missGamesKey = "<handler type=\"x\"><haha>haha</haha></handler>";
            new ActiveGamesHandler(FailureTestHelper.parseElement(missGamesKey));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ActiveGamesHandler(org.w3c.dom.Element).
     * In this case, the games_key value is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testActiveGamesHandlerElement_EmptyGamesKey() throws Exception {
        try {
            String emptyGamesKey = "<handler type=\"x\"><games_key> </games_key></handler>";
            new ActiveGamesHandler(FailureTestHelper.parseElement(emptyGamesKey));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link HandlerExecutionException}.
     * @throws Exception to JUnit
     */
    public void testExecute_FailedToLoad() throws Exception {
        try {
            MockControl reqControl = MockControl.createNiceControl(HttpServletRequest.class);
            MockControl resControl = MockControl.createControl(HttpServletResponse.class);
            ActionContext ac = new ActionContext(
                    (HttpServletRequest) reqControl.getMock(),
                    (HttpServletResponse) resControl.getMock());
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }

}
