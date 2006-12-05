/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.MockAdminDataBean;
import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>PendingWinnerApprovalRejectionHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PendingWinnerApprovalRejectionHandlerUnitTests extends TestCase {
    /**
     * Represents the xml string.
     */
    public static final String XMLSTRING = "<handler type=\"pendingWinnerApproval\">"
            + "<admin-data-jndi-name>"
            + TestHelper.ADMIN_DATA_JNDI_NAME
            + "</admin-data-jndi-name>"
            + "<game-id-request-param>gameId</game-id-request-param>"
            + "<user-id-request-param>userId</user-id-request-param>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>"
            + "fail</fail-request-attribute></handler>";

    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.
     *
     */
    private final String adminDataJndiName = TestHelper.ADMIN_DATA_JNDI_NAME;

    /**
     * This holds the name of the request parameter which will contain the game
     * id associated with pending winner. The value should be able to be parsed
     * into a long value. It will never be null or empty.<br/>
     *
     */
    private final String gameIdRequestParamName = "gameId";

    /**
     * This holds the name of the request parameter which will contain the user
     * id associated with pending winner. The value should be able to be parsed
     * into a long value. It will never be null or empty.<br/>
     *
     */
    private final String userIdRequestParamName = "userId";

    /**
     * This holds the name of the result (as configured in Front Controller
     * component) which should execute in case of execution failure.
     *
     */
    private final String failedResult = "Failed";

    /**
     * This holds the name of the request attribute to which HandlerResult
     * instance should be assigned to, in case of execution failure.
     *
     */
    private final String failRequestAttrName = "fail";

    /**
     * The HttpServletRequest instance used in tests.
     */
    private MockHttpRequest request = new MockHttpRequest();

    /**
     * The HttpServletResponse instance used in tests.
     */
    private HttpServletResponse response = new MockHttpResponse();

    /**
     * The ActionContext used in tests.
     */
    private ActionContext context;

    /**
     * <p>
     * An instance of <code>PendingWinnerApprovalRejectionHandler</code> which
     * is tested.
     * </p>
     */
    private PendingWinnerApprovalRejectionHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test PendingWinnerApprovalRejectionHandler
     * instance and other instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        request = new MockHttpRequest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        // the xml string used for test
        String xml = XMLSTRING;
        Element element = TestHelper.loadXmlString(xml);
        target = new PendingWinnerApprovalHandler(element);
    }

    /**
     * <p>
     * Clean up all namespace of ConfigManager.
     * </p>
     *
     * @throws Exception
     *             the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>PendingWinnerApprovalRejectionHandler(Element)</code> Create for
     * proper behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull(
                "Failed to initialize PendingWinnerApprovalRejectionHandler.",
                target);
        assertEquals("adminDataJndiName", this.adminDataJndiName, TestHelper
                .getPrivateField(PendingWinnerApprovalRejectionHandler.class,
                        target, "adminDataJndiName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(PendingWinnerApprovalRejectionHandler.class,
                        target, "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(
                        PendingWinnerApprovalRejectionHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameIdRequestParamName", this.gameIdRequestParamName,
                TestHelper.getPrivateField(
                        PendingWinnerApprovalRejectionHandler.class, target,
                        "gameIdRequestParamName"));
        assertEquals("userIdRequestParamName", this.userIdRequestParamName,
                TestHelper.getPrivateField(
                        PendingWinnerApprovalRejectionHandler.class, target,
                        "userIdRequestParamName"));
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>PendingWinnerApprovalRejectionHandler(Element)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown if any argument
     * is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new PendingWinnerApprovalHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>PendingWinnerApprovalRejectionHandler(Element)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown if
     * handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<xxx type=\"pendingWinnerApproval\">"
                    + "<admin-data-jndi-name>"
                    + TestHelper.ADMIN_DATA_JNDI_NAME
                    + "</admin-data-jndi-name>"
                    + "<game-id-request-param>gameId</game-id-request-param>"
                    + "<user-id-request-param>userId</user-id-request-param>"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>"
                    + "Failed</fail-request-attribute></xxx>";
            Element element = TestHelper.loadXmlString(xml);
            new PendingWinnerApprovalHandler(element);
            fail("IllegalArgumentException expected if handlerElement.getTagName() is not 'handler'.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_Accuracy() throws Exception {
        request.setParameter("gameId", "1");
        request.setParameter("userId", "1");
        assertNull("null should be returned if success.", target
                .execute(context));
        assertTrue("PendingWinnerApprovalHandler'excute method failed",
                MockAdminDataBean.IsApproveWinner());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_2_Accuracy() throws Exception {
        request.setParameter("gameId", "1");
        request.setParameter("userId", "100");
        assertEquals("'failedresult' should be returned if success.", "Failed",
                target.execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingWinnerApprovalHandler'excute method failed",
                ResultCode.WINNER_NOT_FIRST, result.getResultCode());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_3_Accuracy() throws Exception {
        request.setParameter("gameId", "1002");
        request.setParameter("userId", "1002");
        assertEquals("'failedresult' should be returned if success.", "Failed",
                target.execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingWinnerApprovalHandler'excute method failed",
                ResultCode.NO_MATCHING_PENDING_WINNER, result.getResultCode());
    }

    /**
     * <p>
     * Failure test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown if any argument
     * is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_failure() throws Exception {
        try {
            target.execute(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
}
