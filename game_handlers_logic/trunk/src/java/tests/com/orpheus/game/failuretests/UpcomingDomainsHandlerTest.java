/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.MockControl;

import com.orpheus.game.GameDetailHandler;
import com.orpheus.game.UnlockedDomainsHandler;
import com.orpheus.game.UpcomingDomainsHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>UpcomingDomainsHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class UpcomingDomainsHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private UpcomingDomainsHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        handler = new UpcomingDomainsHandler("id1", "id2");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for UpcomingDomainsHandler(java.lang.String, java.lang.String).
     * In this case, gameIdParamKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUpcomingDomainsHandler_StringString_NullgameIdParamKey() {
        try {
            new UpcomingDomainsHandler(null, "id2");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(java.lang.String, java.lang.String).
     * In this case, gameIdParamKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUpcomingDomainsHandler_StringString_EmptygameIdParamKey() {
        try {
            new UpcomingDomainsHandler(" ", "id2");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(java.lang.String, java.lang.String).
     * In this case, domainsKey is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUpcomingDomainsHandler_StringString_NulldomainsKey() {
        try {
            new UpcomingDomainsHandler("id1", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(java.lang.String, java.lang.String).
     * In this case, domainsKey is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testUpcomingDomainsHandler_StringString_EmptydomainsKey() {
        try {
            new UpcomingDomainsHandler("id2", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testUpcomingDomainsHandler_Element_NullElement() {
        try {
            new UpcomingDomainsHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(org.w3c.dom.Element).
     * In this case, the gameIdParamKey is missing.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpcomingDomainsHandler_Element_MissingGameIdParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key_1>id</game_id_param_key_1>"
                + "<domains_key>idd</domains_key>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(org.w3c.dom.Element).
     * In this case, the gameIdParamKey is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpcomingDomainsHandler_Element_EmptyGameIdParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key> </game_id_param_key>"
                + "<domains_key>idd</domains_key>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(org.w3c.dom.Element).
     * In this case, the domainsKey is missing.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpcomingDomainsHandler_Element_MissingDomainsKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<domains_key_1>idd</domains_key_1>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for UpcomingDomainsHandler(org.w3c.dom.Element).
     * In this case, the domainsKey is empty.
     * Expected : {@link IllegalArgumentException}
     * @throws Exception to JUnit
     */
    public void testUpcomingDomainsHandler_Element_EmptyDomainsKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key> </game_id_param_key>"
                + "<domains_key> </domains_key>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
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
