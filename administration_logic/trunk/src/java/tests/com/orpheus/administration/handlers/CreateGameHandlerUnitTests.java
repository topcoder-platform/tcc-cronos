/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>CreateGameHandler</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class CreateGameHandlerUnitTests extends TestCase {
    /**
     * This holds the JNDI name to use to look up the GameDataHome service.
     *
     */
    private final String gameDataJndiName = "GameDataHome";

    /**
     * This holds the name of the request parameter which will contain block information
     * such as max time per slot, slot count, auction start and end times.
     *
     */
    private final String blockInfoParamName = "ballColorId";

    /**
     * This holds the name of the JSON property which will contain the max time per slot.
     *
     */
    private final String maxSlotTimePropName = "maxSlotTime";

    /**
     * This holds the name of the JSON property which will contain the slot Count.
     *
     */
    private final String slotCountPropName = "slotCount";

    /**
     * This holds the name of the JSON property which will contain the auction Start Time.
     * The value should be able to be parsed into a java.util.Date object. It should
     * have day,month,year and time of day data.
     * The format of the date string will also be taken as request parameter.
     *
     */
    private final String auctionStartTimePropName = "auctionStartTime";

    /**
     * This holds the name of the JSON property which will contain the auction End Time.
     * The value should be able to be parsed into a java.util.Date object. It should
     * have day,month,year and time of day data.
     * The format of the date string will also be taken as request parameter.
     *
     */
    private final String auctionEndTimePropName = "auctionEndTime";

    /**
     * This holds the name of the session attribute which will contain the parsed ball color id.
     * It will be stored as a Long value.
     *
     */
    private final String ballColorIdAttrName = "ballColorId";

    /**
     * This holds the name of the session attribute which will contain the parsed key count.
     * It will be stored as an Integer value.
     *
     */
    private final String keyCountAttrName = "keyCount";

    /**
     * This holds the name of the session attribute which will contain the parsed block count.
     * It will be stored as an Integer value.
     *
     */
    private final String blockCountAttrName = "blockCount";

    /**
     * This holds the name of the request parameter which will contain the date format
     * to use to parse a string date value into a java.util.Date object.
     *
     */
    private final String dtFormatParamName = "dtFormat";

    /**
     * This holds the name of the session attribute which will contain the parsed game start Date.
     * It will be stored as java.util.Date.
     *
     */
    private final String startDateAttrName = "startDate";

    /**
     * This holds the name of the application attribute which will contain the AuctionManager instance.
     *
     */
    private final String auctionMgrAttrName = "auctionMgr";

    /**
     * This is the minimum bid for any auction when created. Right now it is a fixed value read from
     * configuration. In the future this will probably be added to the UI and handled via a request parameter
     *
     */
    private final int minimumBid = 1;

    /**
     * This holds the name of the result (as configured in Front Controller component) which
     * should execute in case of execution failure.
     *
     */
    private final String failedResult = "Failed";

    /**
     * This holds the name of the request attribute to which HandlerResult instance should be
     * assigned to, in case of execution failure.
     *
     */
    private final String failRequestAttrName = "failed";

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
     * An instance of <code>CreateGameHandler</code> which is tested.
     * </p>
     */
    private CreateGameHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test CreateGameHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
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
                + "</fail-result><fail-request-attribute>failed</fail-request-attribute>"
                + "</handler>";
        Element element = TestHelper.loadXmlString(xml);
        target = new CreateGameHandler(element);
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
     * <code>CreateGameHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize CreateGameHandler.", target);
        assertEquals("auctionEndTimePropName", this.auctionEndTimePropName,
                TestHelper.getPrivateField(CreateGameHandler.class, target,
                        "auctionEndTimePropName"));
        assertEquals("auctionMgrAttrName", this.auctionMgrAttrName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "auctionMgrAttrName"));
        assertEquals("auctionStartTimePropName", this.auctionStartTimePropName,
                TestHelper.getPrivateField(CreateGameHandler.class, target,
                        "auctionStartTimePropName"));
        assertEquals("ballColorIdAttrName", this.ballColorIdAttrName,
                TestHelper.getPrivateField(CreateGameHandler.class, target,
                        "ballColorIdAttrName"));
        assertEquals("blockCountAttrName", this.blockCountAttrName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "blockCountAttrName"));
        assertEquals("blockInfoParamName", this.blockInfoParamName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "blockInfoParamName"));
        assertEquals("dtFormatParamName", this.dtFormatParamName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "dtFormatParamName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(CreateGameHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("keyCountAttrName", this.keyCountAttrName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "keyCountAttrName"));
        assertEquals("maxSlotTimePropName", this.maxSlotTimePropName,
                TestHelper.getPrivateField(CreateGameHandler.class, target,
                        "maxSlotTimePropName"));
        assertEquals("minimumBid", new Integer(this.minimumBid), TestHelper
                .getPrivateField(CreateGameHandler.class, target, "minimumBid"));
        assertEquals("slotCountPropName", this.slotCountPropName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "slotCountPropName"));
        assertEquals("startDateAttrName", this.startDateAttrName, TestHelper
                .getPrivateField(CreateGameHandler.class, target,
                        "startDateAttrName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>CreateGameHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new CreateGameHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>CreateGameHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if xml is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            String xml = "<handler type=\"createGame\"><game-data-jndi-name>GameDataHome"
                    + "</game-data-jndi-name><block-request-param>ballColorId"
                    + "</block-request-param><max-slot-time-prop>maxSlotTime"
                    + "</max-slot-time-prop><slot-count-prop>slotCount</slot-count-prop>"
                    + "<auction-start-time-prop>auctionStartTime</auction-start-time-prop>"
                    + "<auction-end-time-prop>auctionEndTime</auction-end-time-prop>"
                    + "<ballcolor-id-session-attr>ballColorId</ballcolor-id-session-attr>"
                    + "<key-count-session-attr>keyCount</key-count-session-attr>"
                    + "<block-count-session-attr>blockCount</block-count-session-attr>"
                    + "<date-format-request-param>yyyy-MM-dd hh:mm</date-format-request-param>"
                    + "<start-date-session-attr>startDate</start-date-session-attr>"
                    + "<auction-mgr-app-attr>auctionMgr</auction-mgr-app-attr>"
                    + "<minimum-auction-bid>-1</minimum-auction-bid><fail-result>Failed"
                    + "</fail-result><fail-request-attribute>failed</fail-request-attribute>"
                    + "</handler>";
            Element element = TestHelper.loadXmlString(xml);
            new CreateGameHandler(element);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
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
    public void testExecute_1_accuracy() throws Exception {
        HttpSession session = request.getSession(false);
        session.setAttribute(ballColorIdAttrName, new Long(1));
        session.setAttribute(keyCountAttrName, new Integer(1));
        session.setAttribute(startDateAttrName, new Date());
        session.setAttribute(blockCountAttrName, new Integer(1));
        request.setParameterValues(blockInfoParamName, new String[] {
            "\"maxSlotTime\":5", "\"slotCount\":12",
            "\"auctionStartTime\":\"2006-12-21 09:00\"",
            "\"auctionEndTime\":\"2006-12-30 09:00\""});
        assertEquals("'fail' should be returned if success.", failedResult,
                target.execute(context));
        assertEquals("BLOCK_INFO_LENGTH_NOT_MATCH should be returned.",
                ResultCode.BLOCK_INFO_LENGTH_NOT_MATCH,
                ((HandlerResult) request.getAttribute(failRequestAttrName))
                        .getResultCode());
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
    public void testExecute_2_accuracy() throws Exception {
        HttpSession session = request.getSession(false);
        session.setAttribute(ballColorIdAttrName, new Long(1));
        session.setAttribute(keyCountAttrName, new Integer(1));
        session.setAttribute(startDateAttrName, new Date());
        session.setAttribute(blockCountAttrName, new Integer(4));
        request.setParameterValues(blockInfoParamName, new String[] {
            "\"maxSlotTime\":5", "\"slotCount\":12",
            "\"auctionStartTime\":\"2006-12-21 09:00\"",
            "\"auctionEndTime\":\"2006-12-30 09:00\""});
        assertEquals("'fail' should be returned if success.", failedResult,
                target.execute(context));
        assertEquals("BLOCK_INFO_LENGTH_NOT_MATCH should be returned.",
                ResultCode.EXCEPTION_OCCURRED, ((HandlerResult) request
                        .getAttribute(failRequestAttrName)).getResultCode());
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
