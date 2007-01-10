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

import com.orpheus.game.WinnerDataHandler;
import com.topcoder.user.profile.manager.MockUserProfileManager;
import com.topcoder.user.profile.manager.ProfileTypeFactory;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * Accuracy test case for WinnerDataHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class WinnerDataHandlerAccuracyTest extends TestCase {
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
	 * Default WinnerDataHandler used in the tests.
	 */
	private WinnerDataHandler handler;

	/**
	 * Loads configuration from files. Gets instance of
	 * WinnerDataHandler used in the tests.
	 * 
	 * @throws Exception
	 *             to Junit
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.clearConfig();

		String[] profileTypeNames = new String[] { "typeA", "typeB" };
		Map profilesMap = new HashMap();
		profilesMap.put("first_name", "firstName");
		profilesMap.put("email_address", "email");
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
		configManager.add("UserProfileManager.xml");
		configManager.add("DBConnectionFactory.xml");
		configManager.add("ObjectFactory.xml");
		configManager.add("FrontControllerConfig.xml");
		configManager.add("com.topcoder.user.profile.ConfigProfileType.base","com/topcoder/user/profile/ConfigProfileType.base.properties",ConfigManager.CONFIG_PROPERTIES_FORMAT);
		handler = new WinnerDataHandler(new MockUserProfileManager(),
				profileTypeNames, profilesMap, ProfileTypeFactory
						.getInstance("com.topcoder.user.profile.manager"));
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
	 * Test for
	 * {@link WinnerDataHandler#WinnerDataHandler(com.topcoder.user.profile.manager.UserProfileManager, String[], Map, ProfileTypeFactory)}.
	 * No exception should be thrown.
	 */
	public void testWinnerDataHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link WinnerDataHandler#WinnerDataHandler(Element)}. No
	 * exception should be thrown.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit
	 */
	public void testWinnerDataHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler>"
				+ "        	   <object_factory>"
				+ "                <namespace>com.orpheus.game</namespace>"
				+ "                <user_profile_manager_key>UserProfileManager</user_profile_manager_key>"
				+ "                <profile_type_factory_key>ProfileTypeFactory</profile_type_factory_key>"
				+ "        	   </object_factory>"
				+ "            <profile_type_names>"
				+ "                <value>typeA</value>"
				+ "                <value>typeB</value>"
				+ "            </profile_type_names>"
				+ "            <profile_property_param_names>"
				+ "                <value>firstName</value>"
				+ "                <value>email</value>"
				+ "            </profile_property_param_names>"
				+ "            <profile_property_names>"
				+ "                <value>first_name</value>"
				+ "                <value>email_address</value>"
				+ "            </profile_property_names>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new WinnerDataHandler(element);
		testWinnerDataHandler_Execute();
	}

	/**
	 * Test for {@link WinnerDataHandler#execute(ActionContext)}. It should
	 * return null.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
	public void testWinnerDataHandler_Execute() throws Exception {
		servletContext = new MockServletContext();

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		JNDIHelper.initJNDI();

		mockRequest.setParameter("firstName", "tom");
		mockRequest.setParameter("email", "tom@email.com");

		assertEquals("execute failed.", null, handler.execute(context));
	}

}
