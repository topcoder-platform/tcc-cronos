/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.orpheus.administration.handlers.PendingWinnerApprovalHandler;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.PendingWinnerApprovalHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class PendingWinnerApprovalHandlerStressTest extends AbstractHandlerStressTest {

    protected void setUp() throws Exception {
        super.setUp();

        ((MockHttpServletRequest) request).setupAddParameter("gameId", "1");
        ((MockHttpServletRequest) request).setupAddParameter("userId", "1");
    }

    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertNull("should return null", result);
    }

    /**
     * <p>
     * Get the handler instance to be tested.
     * </p>
     * @return the handler to be test.
     * @throws Exception
     *             If fail to get.
     */
    protected Handler getHandler() throws Exception {
        String xml = "<handler type=\"pendingWinnerApproval\">" + "<admin-data-jndi-name>"
            + StressTestHelper.ADMIN_DATA_JNDI_NAME + "</admin-data-jndi-name>"
            + "<game-id-request-param>gameId</game-id-request-param>"
            + "<user-id-request-param>userId</user-id-request-param>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>" + "fail</fail-request-attribute></handler>";

        return new PendingWinnerApprovalHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "PendingWinnerApprovalHandler";
    }

}
