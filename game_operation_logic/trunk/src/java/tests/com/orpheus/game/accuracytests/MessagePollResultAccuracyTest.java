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
import com.orpheus.game.result.MessagePollResult;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy test case for MessagePollResult.
 * 
 * @author Zulander
 * @version 1.0
 */
public class MessagePollResultAccuracyTest extends TestCase {

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
	 * Default MessagePollResult used in the tests.
	 */
	private MessagePollResult result;

	/**
	 * Set up. Creates instance of AttributeScope used in the tests.
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
		result = new MessagePollResult("date", new String[] { "gameA", "gameB",
				"gameC" });
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
	 * Test for {@link MessagePollResult#MessagePollResult(String, String[])}.
	 * No exception should be thrown.
	 */
	public void testMessagePollResult_Constructor1() {
		// success
	}

	/**
	 * Test for {@link MessagePollResult#MessagePollResult(Element)}. No
	 * exception should be thrown.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
	public void testMessagePollResult_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <date_param_key>date</date_param_key>"
				+ "            <catetory_names>"
				+ "                <value>cat1</value>"
				+ "                <value>cat2</value>"
				+ "            </catetory_names>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		result = new MessagePollResult(element);
		testMessagePollResult_Execute();
	}

	/**
	 * Test {@link MessagePollResult#execute(ActionContext)}. No exception
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
	public void testMessagePollResult_Execute() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		JNDIHelper.initJNDI();
		
		mockRequest.setParameter("date", "2006-10-10T12:33:32,000-05:00");

		result.execute(context);
		System.out.println(((MockHttpResponse)response).getOut());
	}

}
