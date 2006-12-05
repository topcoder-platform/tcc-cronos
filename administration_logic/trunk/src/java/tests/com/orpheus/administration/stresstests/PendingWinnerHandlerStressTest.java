/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.orpheus.administration.handlers.PendingWinnerHandler;
import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.PendingWinnerHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class PendingWinnerHandlerStressTest extends AbstractHandlerStressTest {

    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertNull("should return null.", result);
        assertTrue("attribute should exist.", request.getAttribute("PendingWinner") instanceof PendingWinner[]);
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
        // the xml string used for test
        String xml = "<handler type=\"pendingWinner\"><admin-data-jndi-name>" + StressTestHelper.ADMIN_DATA_JNDI_NAME
            + "</admin-data-jndi-name><pending-winner-request-attribute>"
            + "PendingWinner</pending-winner-request-attribute><fail-result>Failed"
            + "</fail-result><fail-request-attribute>fail</fail-request-attribute>" + "</handler>";
        return new PendingWinnerHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "PendingWinnerHandler";
    }

}
