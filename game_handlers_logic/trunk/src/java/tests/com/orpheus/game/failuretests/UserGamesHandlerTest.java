/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.MockControl;

import com.orpheus.game.UserGamesHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>UserGamesHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class UserGamesHandlerTest extends TestCase {

    /**
     * Represents the handler;
     */
    private UserGamesHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        handler = new UserGamesHandler("id1", "code");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for UserGamesHandler(java.lang.String, java.lang.String).
     * In this case, the gamesKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUserGamesHandler_StringString_NullGamesKey() {
        try {
            new UserGamesHandler(null, "code");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(java.lang.String, java.lang.String).
     * In this case, the gamesKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUserGamesHandler_StringString_EmptyGamesKey() {
        try {
            new UserGamesHandler(" ", "code");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(java.lang.String, java.lang.String).
     * In this case, the resultCode is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUserGamesHandler_StringString_NullResultCode() {
        try {
            new UserGamesHandler("id1", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(java.lang.String, java.lang.String).
     * In this case, the resultCode is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUserGamesHandler_StringString_EmptyResultCode() {
        try {
            new UserGamesHandler("id1", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUserGamesHandler_Element_NullElement() {
        try {
            new UserGamesHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(org.w3c.dom.Element).
     * In this case, the gamesKey is missing.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testUserGamesHandler_Element_MissingGamesKey() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<games_key_1>key</games_key_1>"
                + "<not_logged_in_result_code>code</not_logged_in_result_code>"
                + "</handler>";
            new UserGamesHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(org.w3c.dom.Element).
     * In this case, the gamesKey is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testUserGamesHandler_Element_EmptyGamesKey() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<games_key> </games_key>"
                + "<not_logged_in_result_code>code</not_logged_in_result_code>"
                + "</handler>";
            new UserGamesHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(org.w3c.dom.Element).
     * In this case, the result code is missing.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testUserGamesHandler_Element_MissingResultCode() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<games_key>key</games_key>"
                + "<not_logged_in_result_code_1>code</not_logged_in_result_code_1>"
                + "</handler>";
            new UserGamesHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UserGamesHandler(org.w3c.dom.Element).
     * In this case, the result code is empty.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testUserGamesHandler_Element_EmptyResultCode() throws Exception, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<games_key>key</games_key>"
                + "<not_logged_in_result_code> </not_logged_in_result_code>"
                + "</handler>";
            new UserGamesHandler(FailureTestHelper.parseElement(xml));
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
            fail("IllegalArgumentException expected.");
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
            MyHttpServletRequest wrapper = new MyHttpServletRequest((HttpServletRequest) reqControl.getMock());
            wrapper.setNullSesssion(false);
            ActionContext ac = new ActionContext(
                    wrapper,
                    (HttpServletResponse) resControl.getMock());
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }
}
