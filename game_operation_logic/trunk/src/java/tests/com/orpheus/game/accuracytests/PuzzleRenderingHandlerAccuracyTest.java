/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.AttributeScope;
import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.PuzzleRenderingHandler;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.puzzle.MockPuzzleTypeSource;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * Accuracy test case for PuzzleRenderingHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class PuzzleRenderingHandlerAccuracyTest extends TestCase {
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
	 * Default PuzzleRenderingHandler used in the tests.
	 */
    private PuzzleRenderingHandler handler;
    
	/**
	 * Loads configuration from files. Gets instance of
	 * PuzzleRenderingHandler used in the tests.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
    protected void setUp() throws Exception {
        super.setUp();
		AccuracyTestHelper.clearConfig();
        
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name","request"),"property1");
		ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        handler = new PuzzleRenderingHandler("puzzle_id","media_type","base_name","puzzle_string");
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
     * Test for {@link PuzzleRenderingHandler#PuzzleRenderingHandler(String, String, String, String)}.
     * No exception should be thrown.
     */
	public void testPuzzleRenderingHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link PuzzleRenderingHandler#PuzzleRenderingHandler(Element)}.
	 * No exception should be thrown.
	 * 
	 * @throws Exception exception thrown to JUnit
	 */
	public void testPuzzleRenderingHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <puzzle_id_request_attribute_key>puzzle_id</puzzle_id_request_attribute_key>"
				+ "            <media_type_request_attribute_key>media_type</media_type_request_attribute_key>"
				+ "            <puzzle_string_request_attribute_key>puzzle_string</puzzle_string_request_attribute_key>"
				+ "            <solutiontester_base_name>base_name</solutiontester_base_name>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new PuzzleRenderingHandler(element);
		testPuzzleRenderingHandler_Execute();
	}

	/**
	 * Test for {@link PuzzleRenderingHandler#execute(ActionContext)}.
	 * It should return null.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testPuzzleRenderingHandler_Execute() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		JNDIHelper.initJNDI();
		
		mockRequest.setAttribute("puzzle_id", new Long(1));
		mockRequest.setAttribute("media_type", "type");
		servletContext.setAttribute("PuzzleTypeSource", new MockPuzzleTypeSource());
		
		assertEquals("execute failed.", null, handler.execute(context));
	}


}
