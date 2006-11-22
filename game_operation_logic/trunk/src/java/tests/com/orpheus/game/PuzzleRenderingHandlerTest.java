/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.puzzle.MockPuzzleTypeSource;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Test case for PuzzleRenderingHandler.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PuzzleRenderingHandlerTest extends TestCase {
    /** Represents default ActionContext used in this test. */
    private ActionContext context;

    /** Represents default HttpRequest used in this test. */
    private MockHttpRequest request;

    /** Represents default HttpResponse used in this test. */
    private MockHttpResponse response;

    /** Represents default HttpSession used in this test. */
    private MockHttpSession session;

    /** Represents default ServletContext used in this test. */
    private MockServletContext servletContext;

    /** Represents default PuzzleRenderingHandler used in this test. */
    private PuzzleRenderingHandler handler;

    /**
     * Test for {@link PuzzleRenderingHandler#execute(ActionContext)}. It should return null.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testExecute() throws Exception {
        assertEquals("execute failed.", null, handler.execute(context));
    }

    /**
     * Test method for execute(ActionContext). In this case, the context is null. Expected : {@link
     * IllegalArgumentException}.
     *
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
     * Test for {@link PuzzleRenderingHandler#PuzzleRenderingHandler(Element)}. No exception should be thrown.
     *
     * @throws Exception exception thrown to JUnit
     */
    public void testPuzzleRenderingHandlerElement() throws Exception {
        Element element = DocumentHelper.getDocument("/PuzzleRenderingHandler.xml", "config", "valid_config");
        PuzzleRenderingHandler handler = new PuzzleRenderingHandler(element);
        handler.execute(context);
    }

    /**
     * Test ctor(puzzleIdRequestAttributeKey, mediaTypeRequestAttributeKey, solutionTesterBaseName,
     * puzzleStringRequestAttributeKey) with valid config.
     */
    public void testPuzzleRenderingHandlerStringStringStringString() {
        assertNotNull("PuzzleRenderingHandler should be instantiated successfully", handler);
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * puzzleIdRequestAttributeKey.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptyPuzzleIdParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key>  </puzzle_id_request_attribute_key>" +
                "<media_type_request_attribute_key> some key </media_type_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * mediaTypeRequestAttributeKey.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptymediaTypeRequestAttributeKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key>  some key </puzzle_id_request_attribute_key>" +
                "<media_type_request_attribute_key> </media_type_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * puzzleStringRequestAttributeKey.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptypuzzleStringRequestAttributeKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key>  some key </puzzle_id_request_attribute_key>" +
                "<media_type_request_attribute_key> some key </media_type_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> </puzzle_string_request_attribute_key>" +
                "<solutiontester_base_name> haha </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * solutionTesterBaseName.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_EmptysolutionTesterBaseName()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key>  some key </puzzle_id_request_attribute_key>" +
                "<media_type_request_attribute_key> some key </media_type_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>" +
                "<solutiontester_base_name> </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * puzzleIdRequestAttributeKey.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NoPuzzleIdParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<media_type_request_attribute_key> some key </media_type_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * mediaTypeRequestAttributeKey.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NomediaTypeRequestAttributeKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * puzzleStringRequestAttributeKey.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NopuzzleStringRequestAttributeKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>" +
                "<media_type_request_attribute_key> some key </media_type_request_attribute_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element has no
     * solutionTesterBaseName.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleRenderingHandler_Element_NosolutionTesterBaseName()
        throws Exception {
        try {
            String xml = "<handler type=\"x\">" +
                "<puzzle_id_request_attribute_key> some key </puzzle_id_request_attribute_key>" +
                "<media_type_request_attribute_key> some key </media_type_request_attribute_key>" +
                "<puzzle_string_request_attribute_key> some key </puzzle_string_request_attribute_key>" +
                "<incorrect_solution_result> some result name </incorrect_solution_result> </handler>";
            new PuzzleRenderingHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleRenderingHandler(org.w3c.dom.Element). In this case, the element is null.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the media type attribute key is empty.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the puzzle id attribute key is empty.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the tester base name is empty.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the tester base name is empty.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the media type attribute key is null.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the puzzle id attribute key is null.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the media type attribute key is null.
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
     * Test method for PuzzleRenderingHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the media type attribute key is null.
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
     * Sets up test environment.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();

        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");
        handler = new PuzzleRenderingHandler("puzzle_id", "media_type", "base_name", "puzzle_string");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setAttribute("puzzle_id", new Long(1));
        request.setAttribute("media_type", "type");
        servletContext.setAttribute("PuzzleTypeSource", new MockPuzzleTypeSource());
        JNDIHelper.initJNDI();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }
}
