/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>PendingSponsorDomainHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PendingSponsorDomainHandlerUnitTests extends TestCase {

    /**
     * <p>This holds the name of the namespace to be used when instantiating
     * ConfigManagerSpecificationFactory of the Object Factory component.
     *
     */
    private final String objFactoryNS = "objFactoryNS";

    /**
     * This holds the JNDI name to use to look up the GameDataHome service which will be used
     * to search for domain.
     *
     */
    private final String gameDataJndiName = TestHelper.GAME_DATA_JNDI_NAME;

    /**
     * This holds the name of the request parameter which will contain the domain id
     * for which details are to be loaded.
     *
     */
    private final String domainIdRequestParamName = "DomainId";

    /**
     * This holds the name of the request attribute to which Domain instance representing pending sponsor
     * should be assigned to, in case of successful execution.
     *
     */
    private final String domainRequestAttrName = "Domain";

    /**
     * This holds the name of the request attribute to which UserProfile instance representing domain's sponsor
     * should be assigned to, in case of successful execution.
     *
     */
    private final String sponsorRequestAttrName = "Sponsor";

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
        TestHelper.prepareTest();
        request = new MockHttpRequest();
        response = new MockHttpResponse();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = "<handler type=\"pendingSponsorDomain\">"
                + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                + "<game-data-jndi-name>"
                + TestHelper.GAME_DATA_JNDI_NAME
                + "</game-data-jndi-name>"
                + "<domain-id-request-param>DomainId</domain-id-request-param>"
                + "<domain-request-attribute>Domain</domain-request-attribute>"
                + "<sponsor-request-attribute>Sponsor</sponsor-request-attribute>"
                + "<fail-result>Failed</fail-result><fail-request-attribute>"
                + "fail</fail-request-attribute></handler>";
        Element element = TestHelper.loadXmlString(xml);
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
        TestHelper.clearTestEnvironment();
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
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize PendingSponsorDomainHandler.",
                target);
        assertEquals("domainIdRequestParamName", this.domainIdRequestParamName,
                TestHelper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "domainIdRequestParamName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(PendingSponsorDomainHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "failRequestAttrName"));
        assertEquals("domainRequestAttrName", this.domainRequestAttrName,
                TestHelper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "domainRequestAttrName"));
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(PendingSponsorDomainHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("objFactoryNS", this.objFactoryNS, TestHelper
                .getPrivateField(PendingSponsorDomainHandler.class, target,
                        "objFactoryNS"));
        assertEquals("sponsorRequestAttrName", this.sponsorRequestAttrName,
                TestHelper.getPrivateField(PendingSponsorDomainHandler.class,
                        target, "sponsorRequestAttrName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>PendingSponsorDomainHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new PendingSponsorDomainHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>PendingSponsorDomainHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<xxx type=\"pendingSponsorDomain\">"
                    + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                    + "<game-data-jndi-name>"
                    + TestHelper.GAME_DATA_JNDI_NAME
                    + "</game-data-jndi-name>"
                    + "<domain-id-request-param>DomainId</domain-id-request-param>"
                    + "<domain-request-attribute>Domain</domain-request-attribute>"
                    + "<sponsor-request-attribute>Sponsor</sponsor-request-attribute>"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>"
                    + "fail</fail-request-attribute></xxx>";
            Element element = TestHelper.loadXmlString(xml);
            new PendingSponsorDomainHandler(element);
            fail("IllegalArgumentException expected if handlerElement.getTagName() is not 'handler'.");
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
        assertEquals("'failedResult' should be returned if success.", failedResult, target
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

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_Accuracy() throws Exception {
        request.setParameter("DomainId", "1");
        assertNull("null should be returned if success.", target
                .execute(context));
        // verify the request attributes
        assertNotNull("PendingSponsorDomainHandler excute failed",
                request.getAttribute(domainRequestAttrName));
        assertNotNull("PendingSponsorDomainHandler excute failed",
                request.getAttribute(sponsorRequestAttrName));
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
    public void testExecute_2_Accuracy() throws Exception {
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
    public void testExecute_3_Accuracy() throws Exception {
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
    public void testExecute_4_Accuracy() throws Exception {
        // request.setParameter("DomainId", "asdf");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("PendingSponsorDomainHandler'excute method failed",
                ResultCode.MISSING_PARAMETERS, result.getResultCode());
    }
}
