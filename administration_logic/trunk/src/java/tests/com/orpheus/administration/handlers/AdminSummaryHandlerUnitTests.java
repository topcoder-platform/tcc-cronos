/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>AdminSummaryHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AdminSummaryHandlerUnitTests extends TestCase {
    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.
     *
     */
    private final String adminDataJndiName = TestHelper.ADMIN_DATA_JNDI_NAME;

    /**
     * This holds the name of the request attribute to which AdminSummary instance should be
     * assigned to, in case of successful execution.
     *
     */
    private final String summaryRequestAttrName = "AdminSummary";

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
    private final String failRequestAttrName = "fail";

    /**
     * The HttpServletRequest instance used in tests.
     */
    private HttpServletRequest request = new MockHttpRequest();

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
     * An instance of <code>AdminSummaryHandler</code> which is tested.
     * </p>
     */
    private AdminSummaryHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test AdminSummaryHandler instance and other
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
        String xml = "<handler type=\"adminSummary\"><admin-data-jndi-name>"
                + "AdminDataHome</admin-data-jndi-name><summary-request-attribute>"
                + "AdminSummary</summary-request-attribute><fail-result>Failed"
                + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = TestHelper.loadXmlString(xml);
        target = new AdminSummaryHandler(element);
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
     * <code>AdminSummaryHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize AdminSummaryHandler.", target);
        assertEquals("adminDataJndiName", this.adminDataJndiName, TestHelper
                .getPrivateField(AdminSummaryHandler.class, target,
                        "adminDataJndiName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(AdminSummaryHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(AdminSummaryHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("summaryRequestAttrName", this.summaryRequestAttrName,
                TestHelper.getPrivateField(AdminSummaryHandler.class, target,
                        "summaryRequestAttrName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdminSummaryHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new AdminSummaryHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdminSummaryHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<xxx type=\"adminSummary\"><admin-data-jndi-name>"
                    + "AdminDataHome</admin-data-jndi-name><summary-request-attribute>"
                    + "AdminSummary</summary-request-attribute><fail-result>Failed"
                    + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                    + "</xxx>";
            Element element = TestHelper.loadXmlString(xml);
            new AdminSummaryHandler(element);
            fail("IllegalArgumentException expected if handlerElement.getTagName() is not 'handler'.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdminSummaryHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if element or value is missing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_3_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<handler type=\"adminSummary\"><summary-request-attribute>"
                    + "AdminSummary</summary-request-attribute><fail-result>Failed"
                    + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                    + "</handler>";
            Element element = TestHelper.loadXmlString(xml);
            new AdminSummaryHandler(element);
            fail("IllegalArgumentException expected if element or value is missing.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdminSummaryHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if element or value is missing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_4_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<handler type=\"adminSummary\"><admin-data-jndi-name>"
                    + "  </admin-data-jndi-name><summary-request-attribute>"
                    + "AdminSummary</summary-request-attribute><fail-result>Failed"
                    + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                    + "</handler>";
            Element element = TestHelper.loadXmlString(xml);
            new AdminSummaryHandler(element);
            fail("IllegalArgumentException expected if element or value is missing.");
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
        assertEquals("null should be returned if success.", null, target.execute(context));
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
        //      the xml string used for test
        String xml = "<handler type=\"adminSummary\"><admin-data-jndi-name>"
                + "AdminDataHomexx</admin-data-jndi-name><summary-request-attribute>"
                + "AdminSummary</summary-request-attribute><fail-result>Failed"
                + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = TestHelper.loadXmlString(xml);
        target = new AdminSummaryHandler(element);
        assertEquals("failedResult should be returned if failed.", failedResult, target
                .execute(context));
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
