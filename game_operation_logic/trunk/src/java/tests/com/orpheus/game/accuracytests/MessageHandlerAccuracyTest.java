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
import com.orpheus.game.MessageHandler;
import com.topcoder.message.messenger.Messenger;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * Accuracy test case for MessageHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class MessageHandlerAccuracyTest extends TestCase {
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
	 * Default MessageHandler used in the tests.
	 */
    private MessageHandler handler;
    
	/**
	 * Loads configuration from files. Gets instance of
	 * MessageHandler used in the tests.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
    protected void setUp() throws Exception {
        super.setUp();
		AccuracyTestHelper.clearConfig();
        
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");
		ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        handler = new MessageHandler("some_name",map);
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
     * Test for {@link MessageHandler#MessageHandler(String, Map)}.
     * No exception should be thrown.
     */
	public void testMessageHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link MessageHandler#MessageHandler(Element)}.
	 * No exception should be thrown.
	 * 
	 * @throws Exception exception thrown to JUnit
	 */
	public void testMessageHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <plugin_name>some_name</plugin_name>"
				+ "            <attribute_names>"
				+ "                <value scope=\"request\">request_property_name</value>"
				+ "            </attribute_names>"
				+ "            <message_property_names>"
				+ "                <value>property1</value>"
				+ "            </message_property_names>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new MessageHandler(element);
		testMessageHandler_Execute();
	}

	/**
	 * Test for {@link MessageHandler#execute(ActionContext)}.
	 * It should return null.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testMessageHandler_Execute() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		request = new MockHttpRequest(session);

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		JNDIHelper.initJNDI();
		
		request.setAttribute("request_property_name","request_property_value");
		
        Messenger messenger = Messenger.createInstance();
        messenger.registerPlugin("some_name","com.topcoder.message.messenger.MockMessengerPlugin");
        
		assertEquals("execute failed.", null, handler.execute(context));
	}


}
