/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.handlers;

import com.orpheus.administration.failuretests.FailureHelper;
import com.orpheus.administration.handlers.CreateGameHandler;
import junit.framework.TestCase;
import org.w3c.dom.Element;


/**
 * <p>
 * Failure test for <code>CreateGameHandler</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class CreateGameHandlerFailureTests extends TestCase {

    /**
     * The Xml string used in test cases.
     */
    private String xml = "<handler type=\"createGame\"><game-data-jndi-name>GameDataHome"
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

    /**
     * The CreateGameHandler instance to test.
     */
    private CreateGameHandler handler = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Element element = FailureHelper.convertXmlString(xml);
        handler = new CreateGameHandler(element);
    }


    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'handlerElement' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorNullElement() throws Exception {
        try {
            new CreateGameHandler(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'handler' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingHandlerTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("handler", "handler1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'game-data-jndi-name' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingGameTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("game-data-jndi-name", "game-data-jndi-name1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'block-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingBlockTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("block-request-param", "block-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'max-slot-time-prop' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingMaxSlotTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("max-slot-time-prop", "max-slot-time-prop1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'slot-count-prop' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingSlotCountTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("slot-count-prop", "slot-count-prop1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'auction-start-time-prop' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingAuctionStartTimeTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("auction-start-time-prop", "auction-start-time-prop1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'auction-end-time-prop' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingAuctionEndTimeTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("auction-end-time-prop", "auction-end-time-prop1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'ballcolor-id-session-attr' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingBallColorTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("ballcolor-id-session-attr", "ballcolor-id-session-attr1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'key-count-session-attr' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingKeyCountTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("key-count-session-attr", "key-count-session-attr1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'block-count-session-attr' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingBlockCountTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("block-count-session-attr", "block-count-session-attr1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'date-format-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingDateFormatTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("date-format-request-param", "date-format-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'start-date-session-attr' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingStartDateTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("start-date-session-attr", "start-date-session-attr1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'auction-mgr-app-attr' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingAuctionMgrTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("auction-mgr-app-attr", "auction-mgr-app-attr1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'minimum-auction-bid' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingMinimumAuctionTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("minimum-auction-bid", "minimum-auction-bid1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'fail-result' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingFailResultTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("fail-result", "fail-result1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'fail-request-attribute' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingFailRequestTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("fail-request-attribute", "fail-request-attribute1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new CreateGameHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the <code>execute()</code> method.
     * In this test case the 'actionContext' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExecuteNull() throws Exception {
        try {
            handler.execute(null);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }
}
