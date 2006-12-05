/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.orpheus.administration.handlers.DeleteSlotHandler;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link DeleteSlotHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class DeleteSlotHandlerStressTest extends AbstractHandlerStressTest {

    /**
     * <p>
     * Set up the testing environment.
     * </p>
     */
    protected void setUp() throws Exception {
        super.setUp();

        ((MockHttpServletRequest) request).setupAddParameter("gameId", "1");
        ((MockHttpServletRequest) request).setupAddParameter("slotId", "1");
    }

    /**
     * <p>
     * assert for the result after execute.
     * </p>
     */
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
        // the xml string used for test
        String xml = "<handler type=\"deleteSlot\"><game-data-jndi-name>" + StressTestHelper.GAME_DATA_JNDI_NAME
            + "</game-data-jndi-name><game-id-request-param>"
            + "gameId</game-id-request-param><slot-id-request-param>slotId"
            + "</slot-id-request-param><fail-result>Failed</fail-result>"
            + "<fail-request-attribute>fail</fail-request-attribute>" + "</handler>";
        return new DeleteSlotHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "DeleteSlotHandler";
    }

}
