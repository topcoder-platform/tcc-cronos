/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.SponsorImageRejectionHandler;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Accuracy test the <code>SponsorImageRejectionHandler</code> class. Note this
 * class is inherited from SponsorImageApprovalRejectionHandler, so these tests also test
 * the base class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class SponsorImageRejectionHandlerAccuracyTests extends TestCase {
    /**
     * The HttpServletRequest instance used in tests.
     */
    private MockHttpRequest request = new MockHttpRequest();

    /**
     * The HttpServletResponse instance used in tests.
     */
    private MockHttpResponse response = new MockHttpResponse();

    /**
     * The ActionContext used in tests.
     */
    private ActionContext context;

    /**
     * An instance of <code>SponsorImageRejectionHandler</code> which is tested.
     */
    private SponsorImageRejectionHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test SponsorImageRejectionHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        Helper.prepareTest();

        context = new ActionContext(request, response);
        // the xml string used for test
        String xml = "<handler type=\"sponsorImageRejection\">"
            + "<object-factory-ns>objFactoryNS</object-factory-ns>"
            + "<admin-data-jndi-name>"
            + Helper.ADMIN_DATA_JNDI_NAME
            + "</admin-data-jndi-name>"
            + "<game-data-jndi-name>"
            + Helper.GAME_DATA_JNDI_NAME
            + "</game-data-jndi-name>"
            + "<image-id-request-param>imageId</image-id-request-param>"
            + "<domain-id-request-param>domainId</domain-id-request-param>"
            + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>"
            + "fail</fail-request-attribute></handler>";
        Element element = Helper.loadXmlString(xml);
        target = new SponsorImageRejectionHandler(element);
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
     * <code>SponsorApprovalHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize SponsorApprovalHandler.", target);
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
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        request.setParameter("domainId", "1001");
        assertNull("null should be returned if success.",
                target.execute(context));
        assertFalse("SponsorImageApprovalHandler'excute method failed",
                DataProvider.imageApproval);
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
        // request a sponsor that does not exist
        request.setParameter("domainId", "invalid");
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.PARAMETER_NOT_LONG, result.getResultCode());
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
    public void testExecuteAccuracy3() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "invalid");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.PARAMETER_NOT_LONG, result.getResultCode());
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
    public void testExecuteAccuracy4() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "dfsfsdf");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.PARAMETER_NOT_LONG, result.getResultCode());
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
    public void testExecuteAccuracy5() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1001");
        // request.setParameter("imageId", "dfsfsdf");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.MISSING_PARAMETERS, result.getResultCode());
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
    public void testExecuteAccuracy6() throws Exception {
        request.setParameter("domainId", "1001");
        // request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.MISSING_PARAMETERS, result.getResultCode());
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
    public void testExecuteAccuracy7() throws Exception {
        // request a sponsor that does not exist
        // request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.MISSING_PARAMETERS, result.getResultCode());
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
    public void testExecuteAccuracy8() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "100234");
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        // entry not found exception
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.EXCEPTION_OCCURRED, result.getResultCode());
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
    public void testExecuteAccuracy9() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1002");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        // entry not found exception
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.IMAGE_NOT_BELONG_TO_DOMAIN, result.getResultCode());
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
    public void testExecuteAccuracy11() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1002");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        // entry not found exception
        assertEquals("SponsorImageRejectionHandler'excute method failed",
                ResultCode.SPONSOR_NOT_BELONG_TO_DOMAIN, result.getResultCode());
    }
}