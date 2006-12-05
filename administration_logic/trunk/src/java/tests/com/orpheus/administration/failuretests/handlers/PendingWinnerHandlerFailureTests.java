/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.handlers;

import com.orpheus.administration.failuretests.FailureHelper;
import com.orpheus.administration.handlers.PendingWinnerHandler;
import junit.framework.TestCase;
import org.w3c.dom.Element;


/**
 * <p>
 * Failure test for <code>PendingWinnerHandler</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class PendingWinnerHandlerFailureTests extends TestCase {

    /**
     * The Xml string used in test cases.
     */
    private String xml = "<handler type=\"pendingWinner\"><admin-data-jndi-name>"
            + "AdminDataHome</admin-data-jndi-name><pending-winner-request-attribute>"
            + "PendingWinner</pending-winner-request-attribute><fail-result>Failed"
            + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
            + "</handler>";

    /**
     * The PendingWinnerHandler instance to test.
     */
    private PendingWinnerHandler handler = null;

    /**
     * <p>
     * Setup for each test cases.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureHelper.loadConfiguration();

        Element element = FailureHelper.convertXmlString(xml);
        handler = new PendingWinnerHandler(element);
    }

    /**
     * <p>
     * Clean up for each test cases.
     * </p>
     *
     * @throws Exception the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureHelper.clearAllConfigurationNS();
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
            new PendingWinnerHandler(null);
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
            new PendingWinnerHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'admin-data-jndi-name' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingAdminTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("admin-data-jndi-name", "admin-data-jndi-name1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new PendingWinnerHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'pending-winner-request-attribute' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingPendingWinnerTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("pending-winner-request-attribute", "pending-winner-request-attribute1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new PendingWinnerHandler(element);

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
            new PendingWinnerHandler(element);

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
            new PendingWinnerHandler(element);

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
