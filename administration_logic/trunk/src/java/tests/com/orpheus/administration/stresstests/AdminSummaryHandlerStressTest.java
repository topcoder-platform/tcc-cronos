/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.orpheus.administration.handlers.AdminSummaryHandler;
import com.orpheus.administration.persistence.AdminSummary;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.AdminSummaryHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class AdminSummaryHandlerStressTest extends AbstractHandlerStressTest {

    /**
     * <p>
     * Get the handler instance to be tested.
     * </p>
     * @return the handler to be test.
     * @throws Exception
     *             If fail to get.
     */
    protected Handler getHandler() throws Exception {
        // the xml string used for test
        String xml = "<handler type=\"adminSummary\"><admin-data-jndi-name>"
            + "AdminDataHome</admin-data-jndi-name><summary-request-attribute>"
            + "AdminSummary</summary-request-attribute><fail-result>Failed"
            + "</fail-result><fail-request-attribute>fail</fail-request-attribute>" + "</handler>";
        return new AdminSummaryHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "AdminSummaryHandler";
    }

    /**
     * <p>
     * assert for the result after execute.
     * </p>
     */
    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertTrue("summary request attribute should be set.",
            request.getAttribute("AdminSummary") instanceof AdminSummary);
    }
}
