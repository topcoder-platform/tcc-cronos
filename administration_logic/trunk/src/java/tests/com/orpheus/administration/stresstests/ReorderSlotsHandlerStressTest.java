/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.orpheus.administration.handlers.ReorderSlotsHandler;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link ReorderSlotsHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class ReorderSlotsHandlerStressTest extends AbstractHandlerStressTest {

    /**
     * <p>
     * Get the handler instance to be tested.
     * </p>
     * @return the handler to be test.
     * @throws Exception
     *             If fail to get.
     */
    protected Handler getHandler() throws Exception {
        String xml = "<handler type=\"reorderSlots\">" + "<game-data-jndi-name>" + StressTestHelper.GAME_DATA_JNDI_NAME
            + "</game-data-jndi-name>" + "<game-id-request-param>gameId</game-id-request-param>"
            + "<slot-id-request-param>slotId</slot-id-request-param>"
            + "<offset-request-param>offset</offset-request-param><fail-result>"
            + "Failed</fail-result><fail-request-attribute>fail" + "</fail-request-attribute></handler>";
        return new ReorderSlotsHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "ReorderSlotsHandler";
    }

}
