/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.MockControl;

import com.orpheus.game.SlotValidationHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>SlotValidationHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class SlotValidationHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private SlotValidationHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        handler = new SlotValidationHandler("id1", "id2", "id3");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for SlotValidationHandler(String, String, String).
     * In this case, the game id param key is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSlotValidationHandler_StringStringString_NullGameIdParamKey() {
        try {
            new SlotValidationHandler(null, "id2", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(String, String, String).
     * In this case, the game id param key is empty.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSlotValidationHandler_StringStringString_EmptyGameIdParamKey() {
        try {
            new SlotValidationHandler(" ", "id2", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(String, String, String).
     * In this case, the slot id param key is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSlotValidationHandler_StringStringString_SlotGameIdParamKey() {
        try {
            new SlotValidationHandler("id1", " ", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(String, String, String).
     * In this case, the slot id param key is empty.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSlotValidationHandler_StringStringString_EmptySlotIdParamKey() {
        try {
            new SlotValidationHandler("id1", " ", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(String, String, String).
     * In this case, the result code is null.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSlotValidationHandler_StringStringString_ResultCode() {
        try {
            new SlotValidationHandler("id1", "id2", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(String, String, String).
     * In this case, the result code is empty.
     * Expected : {@link IllegalArgumentException}
     */
    public void testSlotValidationHandler_StringStringString_EmptyResultCode() {
        try {
            new SlotValidationHandler("id1", "id2", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSlotValidationHandler_Element_NullElement() {
        try {
            new SlotValidationHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element has no game id param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testSlotValidationHandler_Element_MissingGameIdParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key_1>id</game_id_param_key_1>"
                + "<slot_id_param_key>id</slot_id_param_key>"
                + "<validation_failed_result_code>code</validation_failed_result_code>"
                + "</handler>";
            new SlotValidationHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element has empty game id param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testSlotValidationHandler_Element_EmptyGameIdParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key> </game_id_param_key>"
                + "<slot_id_param_key>id</slot_id_param_key>"
                + "<validation_failed_result_code>code</validation_failed_result_code>"
                + "</handler>";
            new SlotValidationHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element has no slot id param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testSlotValidationHandler_Element_MissingSlotIdParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<slot_id_param_key_1>id</slot_id_param_key_1>"
                + "<validation_failed_result_code>code</validation_failed_result_code>"
                + "</handler>";
            new SlotValidationHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element has empty slot id param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testSlotValidationHandler_Element_EmptySlotIdParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<slot_id_param_key> </slot_id_param_key>"
                + "<validation_failed_result_code>code</validation_failed_result_code>"
                + "</handler>";
            new SlotValidationHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element has no result code.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testSlotValidationHandler_Element_MissingResultCode() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<slot_id_param_key>id</slot_id_param_key>"
                + "<validation_failed_result_code_1>code</validation_failed_result_code_1>"
                + "</handler>";
            new SlotValidationHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for SlotValidationHandler(org.w3c.dom.Element).
     * In this case, the element has empty result code.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testSlotValidationHandler_Element_EmptyResultCode() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<game_id_param_key>id</game_id_param_key>"
                + "<slot_id_param_key>id</slot_id_param_key>"
                + "<validation_failed_result_code> </validation_failed_result_code>"
                + "</handler>";
            new SlotValidationHandler(FailureTestHelper.parseElement(xml));
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
