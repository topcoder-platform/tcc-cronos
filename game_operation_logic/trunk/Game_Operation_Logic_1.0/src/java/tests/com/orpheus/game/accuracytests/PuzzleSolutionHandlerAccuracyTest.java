/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.MockGameDataManager;
import com.orpheus.game.PuzzleSolutionHandler;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.puzzle.MockSolutionTester;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy test case for PuzzleSolutionHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class PuzzleSolutionHandlerAccuracyTest extends TestCase {
	
	/**
	 * String 'incorrect_solution_result', message for incorrect solution.
	 */
	private static final String INCORRECT = "incorrect_solution_result";
	
	/**
	 * Default ActionContext used in the tests.
	 */
	private ActionContext context;

	/**
	 * Default HttpServletRequest used in the tests.
	 */
	private HttpServletRequest request;

	/**
	 * Default HttpSession used in the tests.
	 */
	private HttpSession session;

	/**
	 * Default ServletContext used in the tests.
	 */
	private ServletContext servletContext;

	/**
	 * Default HttpServletResponse used in the tests.
	 */
	private HttpServletResponse response;

	/**
	 * Default PuzzleSolutionHandler used in the tests.
	 */
    private PuzzleSolutionHandler handler;
    
	/**
	 * Set up. Creates instance of PuzzleSolutionHandler used in the tests.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
    protected void setUp() throws Exception {
        super.setUp();
		AccuracyTestHelper.clearConfig();
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
		configManager.add("GameOperationLogicUtilityTest.xml");
        handler = new PuzzleSolutionHandler("puzzle_id", "Slot_id", "base_name", INCORRECT, "slotCompletion");
    }
    
	/**
	 * Clears test environment.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
	protected void tearDown() throws Exception {
		super.tearDown();

		AccuracyTestHelper.clearConfig();
	}

    /**
     * Test for {@link PuzzleSolutionHandler#PuzzleSolutionHandler(String, String, String, String, String)}.
     * No exception should be thrown.
     */
	public void testPuzzleSolutionHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link PuzzleSolutionHandler#PuzzleSolutionHandler(Element)}.
	 * No exception should be thrown.
	 * 
	 * @throws Exception exception thrown to JUnit
	 */
	public void testPuzzleSolutionHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <game_id_param_key>puzzle_id</game_id_param_key>"
				+ "            <slot_id_param_key>Slot_id</slot_id_param_key>"
				+ "            <solutiontester_base_name>base_name</solutiontester_base_name>"
				+ "            <slot_completion_request_attribute_key>slotCompletion</slot_completion_request_attribute_key>"
				+ "            <incorrect_solution_result>incorrect_solution_result</incorrect_solution_result>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new PuzzleSolutionHandler(element);
		testPuzzleSolutionHandler_Execute_Incorrect();
	}

	/**
	 * Test for {@link PuzzleSolutionHandler#execute(ActionContext)} with valid input.
	 * It should return null.
	 * @throws Exception exception thrown to JUnit.
	 */
// It depends on mock method, so it's eliminated.
//	public void testPuzzleSolutionHandler_Execute_Success() throws Exception {
//		servletContext = new MockServletContext();
//		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
//				.getGameManagerKey(), new MockGameDataManager());

//		session = new MockHttpSession(servletContext);
//        session.setAttribute("base_name123", new MockSolutionTester());
//		MockHttpRequest mockRequest = new MockHttpRequest(session);
//		request = mockRequest;

//		response = new MockHttpResponse();
//		context = new ActionContext(request, response);
//		JNDIHelper.initJNDI();
		
//		mockRequest.setParameter("puzzle_id","1");
//		mockRequest.setParameter("Slot_id","1");
        
//		assertEquals("execute failed.", null, handler.execute(context));
//	}

	/**
	 * Test for {@link PuzzleSolutionHandler#execute(ActionContext)} with incorrect input.
	 * It should return INCORRECT.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testPuzzleSolutionHandler_Execute_Incorrect() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getGameManagerKey(), new MockGameDataManager());

		session = new MockHttpSession(servletContext);
        session.setAttribute("base_name19", new MockSolutionTester());
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		JNDIHelper.initJNDI();
		
		mockRequest.setParameter("puzzle_id","19");
		mockRequest.setParameter("Slot_id","1");
		mockRequest.setParameter("redundant", "-_-b");
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
        
		assertEquals("execute failed.", INCORRECT, handler.execute(context));
	}


}
