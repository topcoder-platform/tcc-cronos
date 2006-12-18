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

import com.orpheus.game.PluginDownloadHandler;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy test case for PluginDownloadHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class PluginDownloadHandlerAccuracyTest extends TestCase {
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
	 * Default PluginDownloadHandler used in the tests.
	 */
    private PluginDownloadHandler handler;
    
	/**
	 * Loads configuration from files. Gets instance of
	 * PluginDownloadHandler used in the tests.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
    protected void setUp() throws Exception {
        super.setUp();
		AccuracyTestHelper.clearConfig();
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
		configManager.add("GameOperationLogicUtilityTest.xml");
        handler = new PluginDownloadHandler("plugin");
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
     * Test for {@link PluginDownloadHandler#PluginDownloadHandler(String)}.
     * No exception should be thrown.
     */
	public void testPluginDownloadHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link PluginDownloadHandler#PluginDownloadHandler(Element)}.
	 * No exception should be thrown.
	 * 
	 * @throws Exception exception thrown to JUnit
	 */
	public void testPluginDownloadHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <plugin_name_param_key>plugin</plugin_name_param_key>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new PluginDownloadHandler(element);
		testPluginDownloadHandler_Execute();
	}

	/**
	 * Test for {@link PluginDownloadHandler#execute(ActionContext)} with valid value.
	 * It should return null.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testPluginDownloadHandler_Execute() throws Exception {
		servletContext = new MockServletContext();
		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;
		mockRequest.setParameter("plugin","some_plugin");
		response = new MockHttpResponse();
        context = new ActionContext(request, response);
        
        JNDIHelper.initJNDI();
        
		assertEquals("execute failed.", null, handler.execute(context));
	}

}
