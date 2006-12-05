/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.PendingWinnerApprovalHandler;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the accuracy for <code>PendingWinnerApprovalHandler</code> class.
 * </p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class PendingWinnerApprovalHandlerAccuracyTests extends TestCase {
    /**
     * <p>
     * An instance of <code>PendingWinnerApprovalHandler</code> which is tested.
     * </p>
     */
    private PendingWinnerApprovalHandler target = null;

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
     * setUp() routine. Creates test PendingWinnerApprovalHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        Helper.prepareTest();
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = "<handler type=\"pendingWinnerApproval\">"
        + "<admin-data-jndi-name>" + Helper.ADMIN_DATA_JNDI_NAME
        + "</admin-data-jndi-name>"
        + "<game-id-request-param>gameId</game-id-request-param>"
        + "<user-id-request-param>userId</user-id-request-param>"
        + "<fail-result>Failed</fail-result><fail-request-attribute>"
        + "fail</fail-request-attribute></handler>";
        Element element = Helper.loadXmlString(xml);
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
        Helper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>PendingWinnerApprovalHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize PendingWinnerApprovalHandler.",
                target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteAccuracy1() throws Exception {
        request.setParameter("gameId", "1001");
        request.setParameter("userId", "1001");
        assertNull("null should be returned if success.",
                target.execute(context));
        assertEquals("PendingWinnerApprovalHandler'excute method failed",
                DataProvider.winnerTable.size(), 1);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteAccuracy2() throws Exception {
        request.setParameter("gameId", "1001");
        request.setParameter("userId", "1002");
        assertEquals("'failedresult' should be returned if success.", "Failed",
                target.execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingWinnerApprovalHandler'excute method failed",
                ResultCode.WINNER_NOT_FIRST, result.getResultCode());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteAccuracy3() throws Exception {
        request.setParameter("gameId", "1002");
        request.setParameter("userId", "1002");
        assertEquals("'failedresult' should be returned if success.", "Failed",
                target.execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingWinnerApprovalHandler'excute method failed",
                ResultCode.NO_MATCHING_PENDING_WINNER, result.getResultCode());
    }
}
