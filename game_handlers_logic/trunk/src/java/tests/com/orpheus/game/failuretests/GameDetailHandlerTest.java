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
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>GameDetailHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class GameDetailHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private GameDetailHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        handler = new GameDetailHandler("id1", "id2");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for GameDetailHandler(java.lang.String, java.lang.String).
     * In this case, the param key is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testGameDetailHandler_StringString_NullParamKey() {
        try {
            new GameDetailHandler(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(java.lang.String, java.lang.String).
     * In this case, the param key is empty.
     * Expected : {@link IllegalArgumentException}
     */
    public void testGameDetailHandler_StringString_EmptyParamKey() {
        try {
            new GameDetailHandler(" ", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(java.lang.String, java.lang.String).
     * In this case, the detail key is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testGameDetailHandler_StringString_NullDetailKey() {
        try {
            new GameDetailHandler("test", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(java.lang.String, java.lang.String).
     * In this case, the detail key is empty.
     * Expected : {@link IllegalArgumentException}
     */
    public void testGameDetailHandler_StringString_EmptyDetailKey() {
        try {
            new GameDetailHandler("test", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testGameDetailHandler_Element_NullElement() {
        try {
            new GameDetailHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(org.w3c.dom.Element).
     * In this case, the element misses param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testGameDetailHandler_Element_MissingParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key_1>id</game_id_param_key_1><game_detail_key>idd</game_detail_key>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(org.w3c.dom.Element).
     * In this case, the element misses param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testGameDetailHandler_Element_EmptyParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key> </game_id_param_key><game_detail_key>idd</game_detail_key>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(org.w3c.dom.Element).
     * In this case, the element misses detail key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testGameDetailHandler_Element_MissingDetailKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key><game_detail_key_1>idd</game_detail_key_1>"
                + "</handler>";
            new GameDetailHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for GameDetailHandler(org.w3c.dom.Element).
     * In this case, the element has an empty detail key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testGameDetailHandler_Element_EmptyDetailKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml =
                "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key><game_detail_key> </game_detail_key>"
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
