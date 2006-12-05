/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.orpheus.administration.handlers.GameParameterHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.GameParameterHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class GameParameterHandlerStressTest extends AbstractHandlerStressTest {

    protected void setUp() throws Exception {
        super.setUp();
        request = new MockHttpRequest();
        ((MockHttpRequest) request).setParameter("ballColorId", "1");
        ((MockHttpRequest) request).setParameter("keyCount", "2");
        ((MockHttpRequest) request).setParameter("blockCount", "3");
        ((MockHttpRequest) request).setParameter("startDate", "2006-11-16");
        ((MockHttpRequest) request).setParameter("dtFormat", "yyyy-MM-dd");
        response = new MockHttpResponse();
        context = new ActionContext(request, response);
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
        // the xml string used for test
        String xml = "<handler type=\"gameParameter\"><ballcolor-id-request-param>"
            + "ballColorId</ballcolor-id-request-param><ballcolor-id-session-attr>"
            + "ballColorId</ballcolor-id-session-attr><key-count-request-param>"
            + "keyCount</key-count-request-param><key-count-session-attr>"
            + "keyCount</key-count-session-attr><block-count-request-param>"
            + "blockCount</block-count-request-param><block-count-session-attr>"
            + "blockCount</block-count-session-attr><start-date-request-param>"
            + "startDate</start-date-request-param><date-format-request-param>"
            + "dtFormat</date-format-request-param><start-date-session-attr>"
            + "startDate</start-date-session-attr><fail-result>Failed</fail-result>"
            + "<fail-request-attribute>fail</fail-request-attribute>" + "</handler>";
        return new GameParameterHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "GameParameterHandler";
    }

}
