/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.handlers;

import com.orpheus.administration.failuretests.FailureHelper;
import com.orpheus.administration.handlers.SponsorImageRejectionHandler;
import junit.framework.TestCase;
import org.w3c.dom.Element;


/**
 * <p>
 * Failure test for <code>SponsorImageRejectionHandler</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class SponsorImageRejectionHandlerFailureTests extends TestCase {

    /**
     * The Xml string used in test cases.
     */
    private String xml = "<handler type=\"sponsorImageApproval\">"
            + "<object-factory-ns>objFactoryNS</object-factory-ns>"
            + "<admin-data-jndi-name>AdminDataHome"
            + "</admin-data-jndi-name>"
            + "<game-data-jndi-name>GameDataHome"
            + "</game-data-jndi-name>"
            + "<image-id-request-param>ImageId</image-id-request-param>"
            + "<domain-id-request-param>DomainId</domain-id-request-param>"
            + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>"
            + "fail</fail-request-attribute></handler>";

    /**
     * The SponsorImageRejectionHandler instance to test.
     */
    private SponsorImageRejectionHandler handler = null;

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
        handler = new SponsorImageRejectionHandler(element);
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
            new SponsorImageRejectionHandler(null);
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
            new SponsorImageRejectionHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'object-factory-ns' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingObjectFactoryTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("object-factory-ns", "object-factory-ns1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new SponsorImageRejectionHandler(element);

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
            new SponsorImageRejectionHandler(element);

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
            new SponsorImageRejectionHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'image-id-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingImageIdTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("image-id-request-param", "image-id-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new SponsorImageRejectionHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'domain-id-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingDomainIdTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("domain-id-request-param", "domain-id-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new SponsorImageRejectionHandler(element);

            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'sponsor-id-request-param' tag is missing. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorMissingSponsorIdTag() throws Exception {
        try {
            String xmlFail = xml.replaceAll("sponsor-id-request-param", "sponsor-id-request-param1");
            Element element = FailureHelper.convertXmlString(xmlFail);
            new SponsorImageRejectionHandler(element);

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
            new SponsorImageRejectionHandler(element);

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
            new SponsorImageRejectionHandler(element);

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
