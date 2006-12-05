/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.handlers;

import com.orpheus.administration.failuretests.FailureHelper;
import com.orpheus.administration.handlers.GameParameterHandler;
import junit.framework.TestCase;
import org.w3c.dom.Element;


/**
 * <p>
 * Failure test for <code>GameParameterHandler</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class GameParameterHandlerFailureTests extends TestCase {

    /**
     * The Xml string used in test cases.
     */
    private String xml = "<handler type=\"gameParameter\"><ballcolor-id-request-param>"
            + "ballColorId</ballcolor-id-request-param><ballcolor-id-session-attr>"
            + "ballColorId</ballcolor-id-session-attr><key-count-request-param>"
            + "keyCount</key-count-request-param><key-count-session-attr>"
            + "keyCount</key-count-session-attr><block-count-request-param>"
            + "blockCount</block-count-request-param><block-count-session-attr>"
            + "blockCount</block-count-session-attr><start-date-request-param>"
            + "startDate</start-date-request-param><date-format-request-param>"
            + "dtFormat</date-format-request-param><start-date-session-attr>"
            + "startDate</start-date-session-attr><fail-result>Failed</fail-result>"
            + "<fail-request-attribute>fail</fail-request-attribute>"
            + "</handler>";

    /**
     * The GameParameterHandler instance to test.
     */
    private GameParameterHandler handler = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Element element = FailureHelper.convertXmlString(xml);
        handler = new GameParameterHandler(element);
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
            new GameParameterHandler(null);
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
            new GameParameterHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'ballcolor-id-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingBallColorIdTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("ballcolor-id-request-param", "ballcolor-id-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new GameParameterHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'key-count-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingKeyCountRequestTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("key-count-request-param", "key-count-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new GameParameterHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the '<block-count-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingBlockCountRequestTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("block-count-request-param", "block-count-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new GameParameterHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'start-date-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingStartDateRequestTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("start-date-request-param", "start-date-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new GameParameterHandler(element);

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
            new GameParameterHandler(element);

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
            new GameParameterHandler(element);

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
            new GameParameterHandler(element);

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
            new GameParameterHandler(element);

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
            new GameParameterHandler(element);

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
            new GameParameterHandler(element);

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
