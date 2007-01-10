/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.PuzzleSolutionHandler;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>PuzzleSolutionHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class PuzzleSolutionHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private PuzzleSolutionHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        handler = new PuzzleSolutionHandler("name1", "name2", "result1", "result2", "test");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the param key is null.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the param key is empty.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the param key is null.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the param key is empty.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the tester name is null.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the tester name is empty.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the tester name is null.
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
     * Test method for PuzzleSolutionHandler(java.lang.String, java.lang.String, java.lang.String, java.lang.String).
     * In this case, the tester name is empty.
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
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element is null.
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
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have game id key.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoGameIdKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
//                + "<game_id_param_key> some key </game_id_param_key>"
                + "<slot_id_param_key> some key </slot_id_param_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have game id key.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptyGameIdKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key></game_id_param_key>"
                + "<slot_id_param_key> some key </slot_id_param_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have slot id key.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoSlotIdKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key> some key </game_id_param_key>"
//                + "<slot_id_param_key> some key </slot_id_param_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have slot id key.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptySlotIdKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<slot_id_param_key> </slot_id_param_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have tester base name.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoTesterBaseName() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<slot_id_param_key> some key </slot_id_param_key>"
//                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have tester base name.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptyTesterBaseNAme() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<slot_id_param_key> some key </slot_id_param_key>"
                + "<solutiontester_base_name> </solutiontester_base_name>"
                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have solution result.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_NoSolutionResult() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<slot_id_param_key> some key </slot_id_param_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
//                + "<incorrect_solution_result> some name </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.orpheus.game.PuzzleSolutionHandler#PuzzleSolutionHandler(org.w3c.dom.Element)}.
     * In this case, the element doesn't have solution result.
     * @throws Exception to JUnit
     */
    public void testPuzzleSolutionHandler_Element_EmptySolutionResult() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"false\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<slot_id_param_key> some key </slot_id_param_key>"
                + "<solutiontester_base_name> some name </solutiontester_base_name>"
                + "<incorrect_solution_result> </incorrect_solution_result>"
                + "</handler>";
            new PuzzleSolutionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected");
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
        	ServletContext servletContext = new MockServletContext();
    		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
    				.getDataStoreKey(), new MockDataStore());

    		HttpSession session = new MockHttpSession(servletContext);
    		MockHttpRequest mockRequest = new MockHttpRequest(session);
    		HttpServletRequest request = mockRequest;

    		HttpServletResponse response = new MockHttpResponse();
            ActionContext ac = new ActionContext(request, response);
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }

}
