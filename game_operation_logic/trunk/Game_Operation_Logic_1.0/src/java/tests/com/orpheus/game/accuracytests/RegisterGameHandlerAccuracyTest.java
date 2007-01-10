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
import com.orpheus.game.RegisterGameHandler;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy test case for RegisterGameHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class RegisterGameHandlerAccuracyTest extends TestCase {
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
	 * Default RegisterGameHandler used in the tests.
	 */
    private RegisterGameHandler handler;
    
	/**
	 * Loads configuration from files. Gets instance of
	 * RegisterGameHandler used in the tests.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
    protected void setUp() throws Exception {
        super.setUp();
		AccuracyTestHelper.clearConfig();
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.add("com.topcoder.naming.jndiutility",
				"com/topcoder/naming/jndiutility/JNDIUtils.properties",
				ConfigManager.CONFIG_PROPERTIES_FORMAT);
		configManager.add("com.topcoder.util.file.DocumentGenerator",
				"com/topcoder/util/file/DocumentManager.xml",
				ConfigManager.CONFIG_XML_FORMAT);
		configManager.add("com.topcoder.user.profile.ConfigProfileType.base",
				"com/topcoder/user/profile/ConfigBaseProfileType.properties",
				ConfigManager.CONFIG_PROPERTIES_FORMAT);
		configManager.add("GameOperationLogicUtilityTest.xml");
        handler = new RegisterGameHandler("game_id");
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
     * Test for {@link RegisterGameHandler#RegisterGameHandler(String)}.
     * No exception should be thrown.
     */
	public void testRegisterGameHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link RegisterGameHandler#RegisterGameHandler(Element)}.
	 * No exception should be thrown.
	 * 
	 * @throws Exception exception thrown to JUnit
	 */
	public void testRegisterGameHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <game_id_param_key>game_id</game_id_param_key>"
				+ "            <templateName>test_files/emailTemplate.txt</templateName>"
				+ "            <templateSource>testTemplate</templateSource>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new RegisterGameHandler(element);
		testRegisterGameHandler_Execute();
	}

	/**
	 * Test for {@link RegisterGameHandler#execute(ActionContext)}.
	 * It should return null.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testRegisterGameHandler_Execute() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		UserProfile user = new UserProfile(new Long(1));
		user.setProperty(BaseProfileType.EMAIL_ADDRESS, "test@topcoder.com");
		session.setAttribute("user_profile", user);
		JNDIHelper.initJNDI();
		
		mockRequest.setParameter("game_id","1");
        
		assertEquals("execute failed.", null, handler.execute(context));
		System.out.println(context.getAttribute("message-to"));
		System.out.println(context.getAttribute("message-subject"));
		System.out.println(context.getAttribute("message-body"));
	}


}
