/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.PuzzleRenderingHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>PuzzleRenderingHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class PuzzleRenderingHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private PuzzleRenderingHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        handler = new PuzzleRenderingHandler("test", "test", "test", "test");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the puzzle id attribute key is null.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_NullPuzzleIdAttrKey() {
        try {
            new PuzzleRenderingHandler(null, "test", "test", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the puzzle id attribute key is empty.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_EmptyPuzzleIdAttrKey() {
        try {
            new PuzzleRenderingHandler(" ", "test", "test", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the media type attribute key is null.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_NullMediaTypeAttrKey() {
        try {
            new PuzzleRenderingHandler("test", null, "test", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the media type attribute key is empty.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_EmptyMediaTypeAttrKey() {
        try {
            new PuzzleRenderingHandler("test", " ", "test", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the media type attribute key is null.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_NullTesterBaseNAme() {
        try {
            new PuzzleRenderingHandler("test", "test", null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the tester base name is empty.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_EmptyTesterBaseNAme() {
        try {
            new PuzzleRenderingHandler("test", "tste", " ", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the media type attribute key is null.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_NullStringAttrKey() {
        try {
            new PuzzleRenderingHandler("test", "test", "test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the tester base name is empty.
     */
    public void testPuzzleRenderingHandler_StringStringStringString_EmptyStringAttrKey() {
        try {
            new PuzzleRenderingHandler("test", "tste", "test", " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     */
    public void testPuzzleRenderingHandler_Element_NullElement() {
        try {
            new PuzzleRenderingHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no puzzleIdRequestAttributeKey.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NoPuzzleIdParamKey() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
//                + "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no puzzleIdRequestAttributeKey.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptyPuzzleIdParamKey() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key>  </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no mediaTypeRequestAttributeKey.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NomediaTypeRequestAttributeKey() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>"
//                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no mediaTypeRequestAttributeKey.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptymediaTypeRequestAttributeKey() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key>  some key </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no solutionTesterBaseName.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NosolutionTesterBaseName() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
//                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no solutionTesterBaseName.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptysolutionTesterBaseName() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key>  some key </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no puzzleStringRequestAttributeKey.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NopuzzleStringRequestAttributeKey() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
//                + "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element).
     * In this case, the element has no puzzleStringRequestAttributeKey.
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptypuzzleStringRequestAttributeKey() throws Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<puzzle_id_request_attribute_key>  some key </puzzle_id_request_attribute_key>"
                + "<media_type_request_attribute_key> some key </media_type_request_attribute_key>"
                + "<puzzle_string_request_attribute_key> </puzzle_string_request_attribute_key>"
                + "<solutiontester_base_name> haha </solutiontester_base_name>"
                + "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(FailureTestHelper.parseElement(xml));
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
            reqControl.replay();
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
