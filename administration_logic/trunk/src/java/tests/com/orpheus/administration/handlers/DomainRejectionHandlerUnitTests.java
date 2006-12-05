/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.MockAdminDataBean;
import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>DomainRejectionHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DomainRejectionHandlerUnitTests extends TestCase {
    /**
     * The HttpServletRequest instance used in tests.
     */
    private MockHttpRequest request;

    /**
     * The HttpServletResponse instance used in tests.
     */
    private MockHttpResponse response;

    /**
     * The ActionContext used in tests.
     */
    private ActionContext context;

    /**
     * <p>
     * An instance of <code>DomainRejectionHandler</code> which is tested.
     * </p>
     */
    private DomainRejectionHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test DomainRejectionHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        request = new MockHttpRequest();
        response = new MockHttpResponse();
        TestHelper.prepareTest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        // the xml string used for test
        String xml = DomainApprovalRejectionHandlerUnitTests.XMLSTRING;
        Element element = TestHelper.loadXmlString(xml);
        target = new DomainRejectionHandler(element);
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
     * Accuracy test. Tests the <code>DomainRejectionHandler(Element)</code>
     * Create for proper behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize DomainRejectionHandler.", target);
    }

    /**
     * <p>
     * Failure test. Tests the <code>DomainRejectionHandler(Element)</code>
     * for proper behavior. Verify that IllegalArgumentException is thrown if
     * any argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new DomainRejectionHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_Accuracy() throws Exception {
        request.setParameter("DomainId", "1");
        request.setParameter("sponsorId", "1");
        assertEquals("null should be returned if success.", null, target
                .execute(context));
        assertFalse("domain approval should be set correctly.",
                MockAdminDataBean.isDomainApproval());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_2_Accuracy() throws Exception {
        request.setParameter("DomainId", "1");
        request.setParameter("sponsorId", "1000");
        assertEquals("'failedReslut' should be returned if success.", "Failed",
                target.execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("DomainApprovalHandler'excute method failed",
                ResultCode.SPONSOR_NOT_BELONG_TO_DOMAIN, result.getResultCode());
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

    /**
     * <p>
     * Accuracy test. Tests the <code>getApprovedFlag()</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetApprovedFlag_1_accuracy() throws Exception {
        assertEquals("false should be returned.", false, target
                .getApprovedFlag());
    }
}
