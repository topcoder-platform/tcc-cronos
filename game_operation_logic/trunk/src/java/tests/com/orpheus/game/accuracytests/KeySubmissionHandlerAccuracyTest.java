/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.KeySubmissionHandler;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * Accuracy test case for KeySubmissionHandler.
 * 
 * @author Zulander
 * @version 1.0
 */
public class KeySubmissionHandlerAccuracyTest extends TestCase {

    /**
     * String "success", success result.
     */
    private static final String SUCCESS_RESULT = "success";

    /**
     * String 'gameId', the game ID.
     */
    private static final String GAME_ID = "gameId";
    
    /**
     * String 'submissions', the submission parameter key.
     */
    private static final String SUBMISSION_PARAM_KEY = "submissions";
    
    /**
     * String 'failureCountNotMetResult', message for failure but user can continue to try.
     */
    private static final String FAILURE_CONTINUE = "failureCountNotMetResult";
    
    /**
     * String 'failureCountExceededResult', message for failure but user can not try again.
     */
    private static final String FAILURE_OVER = null;
    
    /**
     * String 'inactive', message for inactive game.
     */
    private static final String GAME_INACTIVE = "inactive";
    
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
	 * Default KeySubmissionHandler used in the tests.
	 */
    private KeySubmissionHandler handler;
    
	/**
	 * Loads configuration from files. Gets instance of
	 * KeySubmissionHandler used in the tests.
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
        handler = new KeySubmissionHandler(GAME_ID, SUBMISSION_PARAM_KEY, GAME_INACTIVE, SUCCESS_RESULT, FAILURE_CONTINUE, 1);
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
     * Test for {@link KeySubmissionHandler#KeySubmissionHandler(String, String, String, String, String, int)}.
     * No exception should be thrown.
     */
	public void testKeySubmissionHandler_Constructor1() {
		// success
	}

	/**
	 * Test for {@link KeySubmissionHandler#KeySubmissionHandler(Element)}.
	 * No exception should be thrown.
	 * 
	 * @throws Exception exception thrown to JUnit
	 */
	public void testKeySubmissionHandler_Constructor2() throws Exception {
		Element element = AccuracyTestHelper.XML2Element(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valie_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <game_id_param_key>gameId</game_id_param_key>"
				+ "            <submission_param_key>submissions</submission_param_key>"
				+ "            <max_failure_count>10</max_failure_count>"
				+ "            <inactive_game_result>inactive_game_result</inactive_game_result>"
				+ "            <failure_count_not_met_result>count_not_met_result</failure_count_not_met_result>"
				+ "            <success_result>success</success_result>"
				+ "        </handler>"
				+ "    </config>"
				+ "</Root>");
		handler = new KeySubmissionHandler(element);
		testKeySubmissionHandler_Execute_Success();
	}

	/**
	 * Test for {@link KeySubmissionHandler#execute(ActionContext)} with valid value.
	 * It should return null.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testKeySubmissionHandler_Execute_Success() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
		JNDIHelper.initJNDI();
		
		mockRequest.setParameter("gameId","1");
		mockRequest.setParameter("submissions","2");
		mockRequest.setParameter(SUBMISSION_PARAM_KEY, new String[]{"1"});
        
		assertEquals("execute failed.", SUCCESS_RESULT, handler.execute(context));
	}

	/**
	 * Test for {@link KeySubmissionHandler#execute(ActionContext)} with invalid value,
	 * and user can try again with new request.
	 * It should return FAILURE_CONTINUE.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testKeySubmissionHandler_Execute_Fail_Continue() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
		JNDIHelper.initJNDI();
		
		mockRequest.setParameter("gameId","1");
		mockRequest.setParameter("submissions","2");
        
		assertEquals("execute failed.", FAILURE_CONTINUE, handler.execute(context));
	}

	/**
	 * Test for {@link KeySubmissionHandler#execute(ActionContext)} with invalid value,
	 * and user cannot try again.
	 * It should return FAILURE_OVER.
	 * @throws Exception exception thrown to JUnit.
	 */
	public void testKeySubmissionHandler_Execute_Fail_Over() throws Exception {
		servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());

		session = new MockHttpSession(servletContext);
		MockHttpRequest mockRequest = new MockHttpRequest(session);
		request = mockRequest;

		response = new MockHttpResponse();
		context = new ActionContext(request, response);
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
		JNDIHelper.initJNDI();
		
		mockRequest.setParameter("gameId","1");
		mockRequest.setParameter("submissions","2");
        
		handler.execute(context);
		assertEquals("execute failed.", FAILURE_OVER, handler.execute(context));
	}

}
