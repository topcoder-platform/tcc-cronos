/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.topcoder.util.puzzle.MockSolutionTester;
import com.topcoder.web.frontcontroller.ActionContext;


/**
 * Test case for PuzzleSolutionHandler.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PuzzleSolutionHandlerTest extends TestCase {
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

    /** Represents default PuzzleSolutionHandler used in this test. */
    private PuzzleSolutionHandler handler;

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
     * Test ctor(Element) with valid config, no exception should be thrown.
     *
     * @throws Exception to junit.
     */
    public void testPuzzleSolutionHandlerElement() throws Exception {
        Element element = DocumentHelper.getDocument("/PuzzleSolutionHandler.xml", "config", "valid_config");
        PuzzleSolutionHandler handler = new PuzzleSolutionHandler(element);
        handler.execute(context);
    }

    /**
     * Test for {@link PuzzleSolutionHandler#PuzzleSolutionHandler(Element)}. No exception should be thrown.
     */
    public void testPuzzleSolutionHandlerStringStringStringString() {
        assertNotNull("PuzzleSolutionHandler should be instantiated successfully", handler);
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have game id key.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptyGameIdKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key></game_id_param_key>" + "<slot_id_param_key> some key </slot_id_param_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some name </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have slot id key.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptySlotIdKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key> some key </game_id_param_key>" + "<slot_id_param_key> </slot_id_param_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some name </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have solution result.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptySolutionResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<slot_id_param_key> some key </slot_id_param_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have tester base name.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptyTesterBaseNAme()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<slot_id_param_key> some key </slot_id_param_key>" +
                "<solutiontester_base_name> </solutiontester_base_name>" +
                "<incorrect_solution_result> some name </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have game id key.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoGameIdKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<slot_id_param_key> some key </slot_id_param_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some name </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have slot id key.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoSlotIdKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" +
                "<incorrect_solution_result> some name </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have solution result.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoSolutionResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<slot_id_param_key> some key </slot_id_param_key>" +
                "<solutiontester_base_name> some name </solutiontester_base_name>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * doesn't have tester base name.
     *
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoTesterBaseName()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<slot_id_param_key> some key </slot_id_param_key>" +
                "<incorrect_solution_result> some name </incorrect_solution_result>" + "</handler>";
            new PuzzleSolutionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}. In this case, the element
     * is null.
     */
    public void testPuzzleSolutionHandler_Element_NullElement() {
        try {
            new PuzzleSolutionHandler(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test for {@link PuzzleSolutionHandler#execute(ActionContext)} with incorrect input. It should return
     * INCORRECT.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testPuzzleSolutionHandler_Execute_Incorrect()
        throws Exception {
        servletContext = new MockServletContext();
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getGameManagerKey(),
            new MockGameDataManager());

        session = new MockHttpSession(servletContext);
        session.setAttribute("base_name123", new MockSolutionTester());

        MockHttpRequest mockRequest = new MockHttpRequest(session);
        request = mockRequest;

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        JNDIHelper.initJNDI();

        mockRequest.setParameter("puzzle_id", "1");
        mockRequest.setParameter("Slot_id", "1");
        mockRequest.setParameter("redundant", "-_-b");

        assertEquals("execute failed.", "incorrect_solution_result", handler.execute(context));
    }

    /**
     * Test for {@link PuzzleSolutionHandler#execute(ActionContext)} with valid input. It should return null.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testPuzzleSolutionHandler_Execute_Success()
        throws Exception {
        servletContext = new MockServletContext();
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getGameManagerKey(),
            new MockGameDataManager());

        session = new MockHttpSession(servletContext);
        session.setAttribute("base_name123", new MockSolutionTester());

        MockHttpRequest mockRequest = new MockHttpRequest(session);
        request = mockRequest;

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        JNDIHelper.initJNDI();

        mockRequest.setParameter("puzzle_id", "1");
        mockRequest.setParameter("Slot_id", "1");

        handler.execute(context);
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the param key is empty.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_EmptyGameIdParamKey() {
        try {
            new PuzzleSolutionHandler(" ", "name2", "result1", "result2", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the tester name is empty.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_EmptyIncorrectSolution() {
        try {
            new PuzzleSolutionHandler("name1", "name2", "test", " ", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the param key is empty.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_EmptySlotIdParamKey() {
        try {
            new PuzzleSolutionHandler("name1", " ", "result1", "result2", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the tester name is empty.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_EmptyTesterName() {
        try {
            new PuzzleSolutionHandler("name1", "name2", " ", "result2", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the param key is null.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_NullGameIdParamKey() {
        try {
            new PuzzleSolutionHandler(null, "name2", "result1", "result2", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the tester name is null.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_NullIncorrectSolution() {
        try {
            new PuzzleSolutionHandler("name2", "name2", "test", null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the param key is null.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_NullSlotIdParamKey() {
        try {
            new PuzzleSolutionHandler("name2", null, "result1", "result2", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String). In this case, the tester name is null.
     */
    public void testPuzzleSolutionHandler_StringStringStringString_NullTesterName() {
        try {
            new PuzzleSolutionHandler("name2", "name2", null, "result2", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Sets up test enviroment.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();

        JNDIHelper.initJNDI();

        handler = new PuzzleSolutionHandler("puzzle_id", "Slot_id", "base_name", "incorrect_solution_result",
                "slotCompletion");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("puzzle_id", "1");
        request.setParameter("Slot_id", "1");

        session.setAttribute("base_name123", new MockSolutionTester());
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getGameManagerKey(),
            new MockGameDataManager());
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
