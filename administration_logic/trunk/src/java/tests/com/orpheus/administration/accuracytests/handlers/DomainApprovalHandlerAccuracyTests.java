/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.DomainApprovalHandler;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Test the accuracy of <code>DomainApprovalHandler</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class DomainApprovalHandlerAccuracyTests extends TestCase {
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
     * An instance of <code>DomainApprovalHandler</code> which is tested.
     * </p>
     */
    private DomainApprovalHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test DomainApprovalHandler instance and other
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
        String xml = "<handler type=\"domainApproval\"><object-factory-ns>"
            + "objFactoryNS</object-factory-ns><admin-data-jndi-name>"
            + Helper.ADMIN_DATA_JNDI_NAME
            + "</admin-data-jndi-name><game-data-jndi-name>"
            + Helper.GAME_DATA_JNDI_NAME
            + "</game-data-jndi-name><domain-id-request-param>"
            + "domainId</domain-id-request-param><sponsor-id-request-param>"
            + "sponsorId</sponsor-id-request-param><fail-result>Failed"
            + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
            + "</handler>";
        Element element = Helper.loadXmlString(xml);
        target = new DomainApprovalHandler(element);
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
     * <code>DomainApprovalRejectionHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize DomainApprovalRejectionHandler.",
                target);
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
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1001");
        assertEquals("null should be returned if success.", null, target
                .execute(context));
        assertTrue("domain approval should be set correctly.",
                DataProvider.domainApproval);
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
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1002");
        assertEquals("'failedReslut' should be returned if success.", "Failed", target
                .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("DomainApprovalHandler'excute method failed",
                ResultCode.SPONSOR_NOT_BELONG_TO_DOMAIN, result.getResultCode());
    }
}
