/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;
import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.KeySubmissionHandler;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Test case for <code>KeySubmissionHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class KeySubmissionHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private KeySubmissionHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        handler = new KeySubmissionHandler("test", "test", "test", "test", "test", 1);
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the GameIdParamKey is null.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the GameIdParamKey is empty.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the submissionParamKey is null.
     */
    public void testKeySubmissionHandler_StringStringStringStringStringInt_NullsubmissionParamKey() {
        try {
            new KeySubmissionHandler("test", null, "test", "test", "test", 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the submissionParamKey is empty.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the inactiveGameResult is null.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the inactiveGameResult is empty.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the successResult is empty.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the failureCountExceededResult is empty.
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
     * Test method for KeySubmissionHandler(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
     * In this case, the count is negative.
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
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element is null.
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
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has no gameIdParamKey.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NogameIdParamKey() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
//                + "<game_id_param_key> some key </game_id_param_key>"
                + "<submission_param_key> some key </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> some result name </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has empty gameIdParamKey.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptygameIdParamKey() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key>  </game_id_param_key>"
                + "<submission_param_key> some key </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> some result name </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has no submissionParamKey.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NosubmissionParamKey() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> some key </game_id_param_key>"
//                + "<submission_param_key> some key </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> some result name </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has empty submissionParamKey.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptysubmissionParamKey() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key>  </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> some result name </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has no inactiveGameResult.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NoinactiveGameResult() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<submission_param_key> some key </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
//                + "<inactive_game_result> some result name </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has empty inactiveGameResult.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptyinactiveGameResult() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key> inactiveGameResult </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has no success result.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NofailureCountExceededResult() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<submission_param_key> some key </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> some result name </inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
//                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has empty success result.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptyfailureCountExceededResult() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key> inactiveGameResult </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> failureCountExceededResult</inactive_game_result>"
                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has no failureCountNotMetResult.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NofailureCountNotMetResult() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> some key </game_id_param_key>"
                + "<submission_param_key> some key </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> some result name </inactive_game_result>"
//                + "<failure_count_not_met_result> some result name </failure_count_not_met_result>"
                + "<success_result> some result name </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has empty failureCountNotMetResult.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_EmptyfailureCountNotMetResult() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key> inactiveGameResult </submission_param_key>"
                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> failureCountExceededResult</inactive_game_result>"
                + "<failure_count_not_met_result> </failure_count_not_met_result>"
                + "<success_result> failureCountNotMetResult </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has no maxFailureCount.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_NoMaxFailureCount() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key> inactiveGameResult </submission_param_key>"
//                + "<max_failure_count> 10 </max_failure_count>"
                + "<inactive_game_result> failureCountExceededResult</inactive_game_result>"
                + "<failure_count_not_met_result> </failure_count_not_met_result>"
                + "<success_result> failureCountNotMetResult </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has empty maxFailureCount.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_emptyMaxFailureCount() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key> inactiveGameResult </submission_param_key>"
                + "<max_failure_count> </max_failure_count>"
                + "<inactive_game_result> failureCountExceededResult</inactive_game_result>"
                + "<failure_count_not_met_result> </failure_count_not_met_result>"
                + "<success_result> failureCountNotMetResult </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for KeySubmissionHandler(org.w3c.dom.Element).
     * In this case, the element has invalid maxFailureCount.
     * @throws Exception to JUnit
     */
    public void testKeySubmissionHandler_Element_invalidMaxFailureCount() throws Exception {
        try {
            String xml = "<handler type=\"x\" useLocalInterface=\"true\">"
                + "<game_id_param_key> submissionParamKey </game_id_param_key>"
                + "<submission_param_key> inactiveGameResult </submission_param_key>"
                + "<max_failure_count> test </max_failure_count>"
                + "<inactive_game_result> failureCountExceededResult</inactive_game_result>"
                + "<failure_count_not_met_result> </failure_count_not_met_result>"
                + "<success_result> failureCountNotMetResult </success_result>"
                + "</handler> ";
            new KeySubmissionHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
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
