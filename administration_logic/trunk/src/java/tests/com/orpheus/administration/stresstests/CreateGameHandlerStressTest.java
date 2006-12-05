/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.orpheus.administration.handlers.CreateGameHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.CreateGameHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CreateGameHandlerStressTest extends AbstractHandlerStressTest {

    /**
     * This holds the name of the session attribute which will contain the parsed ball color id. It will be stored as a
     * Long value.
     */
    private final String ballColorIdAttrName = "ballColorId";

    /**
     * This holds the name of the session attribute which will contain the parsed key count. It will be stored as an
     * Integer value.
     */
    private final String keyCountAttrName = "keyCount";

    /**
     * This holds the name of the session attribute which will contain the parsed block count. It will be stored as an
     * Integer value.
     */
    private final String blockCountAttrName = "blockCount";

    /**
     * This holds the name of the session attribute which will contain the parsed game start Date. It will be stored as
     * java.util.Date.
     */
    private final String startDateAttrName = "startDate";

    /**
     * This holds the name of the request parameter which will contain block information such as max time per slot, slot
     * count, auction start and end times.
     */
    private final String blockInfoParamName = "ballColorId";

    protected void setUp() throws Exception {
        super.setUp();
        request = new MockHttpRequest();
        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        HttpSession session = request.getSession(false);
        session.setAttribute(ballColorIdAttrName, new Long(1));
        session.setAttribute(keyCountAttrName, new Integer(1));
        session.setAttribute(startDateAttrName, new Date());
        session.setAttribute(blockCountAttrName, new Integer(1));
        ((MockHttpRequest) request)
            .setParameterValues(blockInfoParamName, new String[] {"\"maxSlotTime\":5", "\"slotCount\":12",
                "\"auctionStartTime\":\"2006-12-21 09:00\"", "\"auctionEndTime\":\"2006-12-30 09:00\""});
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
        String xml = "<handler type=\"createGame\"><game-data-jndi-name>GameDataHome"
            + "</game-data-jndi-name><block-request-param>ballColorId"
            + "</block-request-param><max-slot-time-prop>maxSlotTime"
            + "</max-slot-time-prop><slot-count-prop>slotCount</slot-count-prop>"
            + "<auction-start-time-prop>auctionStartTime</auction-start-time-prop>"
            + "<auction-end-time-prop>auctionEndTime</auction-end-time-prop>"
            + "<ballcolor-id-session-attr>ballColorId</ballcolor-id-session-attr>"
            + "<key-count-session-attr>keyCount</key-count-session-attr>"
            + "<block-count-session-attr>blockCount</block-count-session-attr>"
            + "<date-format-request-param>dtFormat</date-format-request-param>"
            + "<start-date-session-attr>startDate</start-date-session-attr>"
            + "<auction-mgr-app-attr>auctionMgr</auction-mgr-app-attr>"
            + "<minimum-auction-bid>1</minimum-auction-bid><fail-result>Failed"
            + "</fail-result><fail-request-attribute>failed</fail-request-attribute>" + "</handler>";
        return new CreateGameHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "CreateGameHandler";
    }

}
