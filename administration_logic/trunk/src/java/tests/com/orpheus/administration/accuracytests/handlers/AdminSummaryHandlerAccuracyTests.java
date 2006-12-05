/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.handlers.AdminSummaryHandler;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * Accuracy test for AdminSummaryHandler.
 *
 * @author KKD
 * @version 1.0
 */
public class AdminSummaryHandlerAccuracyTests extends TestCase {
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
        Helper.prepareTest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = "<handler type=\"adminSummary\"><admin-data-jndi-name>"
                + "AdminDataHome</admin-data-jndi-name><summary-request-attribute>"
                + "AdminSummary</summary-request-attribute><fail-result>Failed"
                + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = Helper.loadXmlString(xml);
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
        Helper.clearTestEnvironment();
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
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize AdminSummaryHandler.", target);
        assertEquals("adminDataJndiName", "AdminDataHome", Helper
                .getPrivateField(AdminSummaryHandler.class, target,
                        "adminDataJndiName"));
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(AdminSummaryHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(AdminSummaryHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("summaryRequestAttrName", "AdminSummary",
                Helper.getPrivateField(AdminSummaryHandler.class, target,
                        "summaryRequestAttrName"));
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
    public void testExecuteAccuracy1() throws Exception {
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
    public void testExecuteAccuracy2() throws Exception {
        //      the xml string used for test
        String xml = "<handler type=\"adminSummary\"><admin-data-jndi-name>"
                + "AdminDataHomexx</admin-data-jndi-name><summary-request-attribute>"
                + "AdminSummary</summary-request-attribute><fail-result>Failed"
                + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = Helper.loadXmlString(xml);
        target = new AdminSummaryHandler(element);
        assertEquals("failedResult should be returned if failed.", "Failed", target
                .execute(context));
    }
}
