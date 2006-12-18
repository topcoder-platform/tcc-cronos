/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

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

import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * Accuracy test case for KeySubmissionHandler.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class KeySubmissionHandlerTest extends TestCase {

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
    private static final String FAILURE_OVER = "failureCountExceededResult";
    
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
    
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        
        handler = new KeySubmissionHandler(GAME_ID, SUBMISSION_PARAM_KEY, GAME_INACTIVE, FAILURE_OVER, FAILURE_CONTINUE, 1);
    }
    
    /**
     * Clears test environment.
     *
     * @throws Exception  to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
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
		Element element = TestHelper.parseElement(
				"<?xml version=\"1.0\" ?>"
				+ "<Root>"
				+ "    <config name=\"valid_config\">"
				+ "        <handler type=\"x\" >"
				+ "            <game_id_param_key>gameId</game_id_param_key>"
				+ "            <submission_param_key>submissions</submission_param_key>"
				+ "            <max_failure_count>10</max_failure_count>"
				+ "            <inactive_game_result>inactive_game_result</inactive_game_result>"
				+ "            <failure_count_not_met_result>count_not_met_result</failure_count_not_met_result>"
				+ "            <failure_count_exceeded_result>count_exceeded_result</failure_count_exceeded_result>"
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
		JNDIHelper.initJNDI();
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
		
		mockRequest.setParameter("gameId","1");
		mockRequest.setParameter("submissions","2");
		mockRequest.setParameter(SUBMISSION_PARAM_KEY, new String[]{"1"});
        
		assertEquals("execute failed.", null, handler.execute(context));
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
		JNDIHelper.initJNDI();
		session.setAttribute("user_profile", new UserProfile(new Long(1)));

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
		JNDIHelper.initJNDI();
		session.setAttribute("user_profile", new UserProfile(new Long(1)));

		mockRequest.setParameter("gameId","1");
		mockRequest.setParameter("submissions","2");
        
		handler.execute(context);
		assertEquals("execute failed.", FAILURE_OVER, handler.execute(context));
	}
    
     /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has empty
     * failureCountExceededResult.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptyfailureCountExceededResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key> inactiveGameResult </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> failureCountExceededResult</inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has empty
     * failureCountNotMetResult.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptyfailureCountNotMetResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key> inactiveGameResult </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> failureCountExceededResult</inactive_game_result>" +
                "<failure_count_not_met_result> </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> failureCountNotMetResult </failure_count_exceeded_result>" +
                "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has empty gameIdParamKey.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptygameIdParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key>  </game_id_param_key>" +
                "<submission_param_key> some key </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> some result name </inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has empty
     * inactiveGameResult.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptyinactiveGameResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key> inactiveGameResult </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" + "<inactive_game_result> </inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has empty
     * submissionParamKey.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptysubmissionParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key>  </submission_param_key>" + "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> some result name </inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has no maxFailureCount.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NoMaxFailureCount()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key> inactiveGameResult </submission_param_key>"//                + "<max_failure_count> 10 </max_failure_count>"
                 + "<inactive_game_result> failureCountExceededResult</inactive_game_result>" +
                "<failure_count_not_met_result> </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> failureCountNotMetResult </failure_count_exceeded_result>" +
                "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has no
     * failureCountExceededResult.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NofailureCountExceededResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<submission_param_key> some key </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> some result name </inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>"//                + "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>"
                 + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has no
     * failureCountNotMetResult.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NofailureCountNotMetResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<submission_param_key> some key </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> some result name </inactive_game_result>"//                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                 + "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has no gameIdParamKey.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NogameIdParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"//                + "<game_id_param_key> some key </game_id_param_key>"
                 + "<submission_param_key> some key </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> some result name </inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has no inactiveGameResult.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NoinactiveGameResult()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> some key </game_id_param_key>" +
                "<submission_param_key> some key </submission_param_key>" +
                "<max_failure_count> 10 </max_failure_count>"//                + "<inactive_game_result> some result name </inactive_game_result>"
                 + "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has no submissionParamKey.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NosubmissionParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> some key </game_id_param_key>"//                + "<submission_param_key> some key </submission_param_key>"
                 + "<max_failure_count> 10 </max_failure_count>" +
                "<inactive_game_result> some result name </inactive_game_result>" +
                "<failure_count_not_met_result> some result name </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> some result name </failure_count_exceeded_result>" + "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element is null.
     */
    public void testKeySubmissionHandler_Element_NullElement() {
        try {
            new KeySubmissionHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has empty maxFailureCount.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_emptyMaxFailureCount()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key> inactiveGameResult </submission_param_key>" +
                "<max_failure_count> </max_failure_count>" +
                "<inactive_game_result> failureCountExceededResult</inactive_game_result>" +
                "<failure_count_not_met_result> </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> failureCountNotMetResult </failure_count_exceeded_result>" +
                "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element). In this case, the element has invalid
     * maxFailureCount.
     *
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_invalidMaxFailureCount()
        throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">" +
                "<game_id_param_key> submissionParamKey </game_id_param_key>" +
                "<submission_param_key> inactiveGameResult </submission_param_key>" +
                "<max_failure_count> test </max_failure_count>" +
                "<inactive_game_result> failureCountExceededResult</inactive_game_result>" +
                "<failure_count_not_met_result> </failure_count_not_met_result>" +
                "<failure_count_exceeded_result> failureCountNotMetResult </failure_count_exceeded_result>" +
                "</handler> ";
            new KeySubmissionHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the GameIdParamKey is empty.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_EmptyGameIdParamKey() {
        try {
            new KeySubmissionHandler(" ", "test", "test", "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the failureCountExceededResult is empty.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_EmptyfailureCountExceededResult() {
        try {
            new KeySubmissionHandler("test", "test", "test", " ", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the failureCountExceededResult is empty.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_EmptyfailureCountNotMetResult() {
        try {
            new KeySubmissionHandler("test", "test", "test", "test", " ", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the inactiveGameResult is empty.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_EmptyinactiveGameResult() {
        try {
            new KeySubmissionHandler("test", "test", " ", "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the submissionParamKey is empty.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_EmptysubmissionParamKey() {
        try {
            new KeySubmissionHandler("test", " ", "test", "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the count is negative.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NegativeMaxCount() {
        try {
            new KeySubmissionHandler("test", "test", "test", "test", " ", -1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the GameIdParamKey is null.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NullGameIdParamKey() {
        try {
            new KeySubmissionHandler(null, "test", "test", "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the failureCountExceededResult is null.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NullfailureCountExceededResult() {
        try {
            new KeySubmissionHandler("test", "test", "test", null, "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the failureCountExceededResult is null.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NullfailureCountNotMetResult() {
        try {
            new KeySubmissionHandler("test", "test", "test", "test", null, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the inactiveGameResult is null.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NullinactiveGameResult() {
        try {
            new KeySubmissionHandler("test", "test", null, "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler( java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, int)}. In this case, the submissionParamKey is null.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NullsubmissionParamKey() {
        try {
            new KeySubmissionHandler("test", null, "test", "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }


}
