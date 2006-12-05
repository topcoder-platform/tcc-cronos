/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.handlers;

import com.orpheus.administration.failuretests.FailureHelper;
import com.orpheus.administration.handlers.ReorderSlotsHandler;
import junit.framework.TestCase;
import org.w3c.dom.Element;


/**
 * <p>
 * Failure test for <code>ReorderSlotsHandler</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class ReorderSlotsHandlerFailureTests extends TestCase {

    /**
     * The Xml string used in test cases.
     */
    private String xml = "<handler type=\"reorderSlots\">"
            + "<game-data-jndi-name>GameDataHome"
            + "</game-data-jndi-name>"
            + "<game-id-request-param>gameId</game-id-request-param>"
            + "<slot-id-request-param>slotId</slot-id-request-param>"
            + "<offset-request-param>offset</offset-request-param><fail-result>"
            + "Failed</fail-result><fail-request-attribute>fail"
            + "</fail-request-attribute></handler>";

    /**
     * The ReorderSlotsHandler instance to test.
     */
    private ReorderSlotsHandler handler = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Element element = FailureHelper.convertXmlString(xml);
        handler = new ReorderSlotsHandler(element);
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
            new ReorderSlotsHandler(null);
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
            new ReorderSlotsHandler(element);

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
            new ReorderSlotsHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'game-id-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingGameIdTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("game-id-request-param", "game-id-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new ReorderSlotsHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'slot-id-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingSlotIdTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("slot-id-request-param", "slot-id-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new ReorderSlotsHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'offset-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingOffsetRequestTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("offset-request-param", "offset-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new ReorderSlotsHandler(element);

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
            new ReorderSlotsHandler(element);

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
            new ReorderSlotsHandler(element);

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
