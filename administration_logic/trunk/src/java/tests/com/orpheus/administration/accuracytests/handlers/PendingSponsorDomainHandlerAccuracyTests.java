/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.PendingSponsorDomainHandler;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Test the accuracy of <code>PendingSponsorDomainHandler</code> class.
 * </p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class PendingSponsorDomainHandlerAccuracyTests extends TestCase {
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
     * An instance of <code>PendingSponsorDomainHandler</code> which is tested.
     * </p>
     */
    private PendingSponsorDomainHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test PendingSponsorDomainHandler instance and other
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
        String xml = "<handler type=\"pendingSponsorDomain\">"
                + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                + "<game-data-jndi-name>"
                + Helper.GAME_DATA_JNDI_NAME
                + "</game-data-jndi-name>"
                + "<domain-id-request-param>DomainId</domain-id-request-param>"
                + "<domain-request-attribute>Domain</domain-request-attribute>"
                + "<sponsor-request-attribute>Sponsor</sponsor-request-attribute>"
                + "<fail-result>Failed</fail-result><fail-request-attribute>"
                + "fail</fail-request-attribute></handler>";
        Element element = Helper.loadXmlString(xml);
        target = new PendingSponsorDomainHandler(element);
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
     * <code>PendingSponsorDomainHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize PendingSponsorDomainHandler.",
                target);
        assertEquals("domainIdRequestParamName", "DomainId",
                Helper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "domainIdRequestParamName"));
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(PendingSponsorDomainHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "failRequestAttrName"));
        assertEquals("domainRequestAttrName", "Domain",
                Helper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "domainRequestAttrName"));
        assertEquals("gameDataJndiName", Helper.GAME_DATA_JNDI_NAME, Helper
                .getPrivateField(PendingSponsorDomainHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("objFactoryNS", "objFactoryNS", Helper
                .getPrivateField(PendingSponsorDomainHandler.class, target,
                        "objFactoryNS"));
        assertEquals("sponsorRequestAttrName", "Sponsor",
                Helper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "sponsorRequestAttrName"));
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
        request.setParameter("DomainId", "1001");
        assertNull("null should be returned if success.", target
                .execute(context));
        // verify the request attributes
        assertNotNull("PendingSponsorDomainHandler excute failed",
                request.getAttribute("Domain"));
        assertNotNull("PendingSponsorDomainHandler excute failed",
                request.getAttribute("Sponsor"));
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
        request.setParameter("DomainId", "1002");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingSponsorDomainHandler'excute method failed",
                ResultCode.EXCEPTION_OCCURRED, result.getResultCode());
        assertTrue("PendingSponsorDomainHandler'excute method failed",
                result.getCause() instanceof EntryNotFoundException);
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
        request.setParameter("DomainId", "asdf");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingSponsorDomainHandler'excute method failed",
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
        // request.setParameter("DomainId", "asdf");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingSponsorDomainHandler'excute method failed",
                ResultCode.MISSING_PARAMETERS, result.getResultCode());
    }
}
