/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>SponsorImageApprovalRejectionHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SponsorImageApprovalRejectionHandlerUnitTests extends TestCase {

    /** Represents the xml string. */
    public static final String XMLSTRING = "<handler type=\"sponsorImageApproval\">"
            + "<object-factory-ns>objFactoryNS</object-factory-ns>"
            + "<admin-data-jndi-name>"
            + TestHelper.ADMIN_DATA_JNDI_NAME
            + "</admin-data-jndi-name>"
            + "<game-data-jndi-name>"
            + TestHelper.GAME_DATA_JNDI_NAME
            + "</game-data-jndi-name>"
            + "<image-id-request-param>ImageId</image-id-request-param>"
            + "<domain-id-request-param>DomainId</domain-id-request-param>"
            + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>"
            + "fail</fail-request-attribute></handler>";

    /**
     * <p>This holds the name of the namespace to be used when instantiating ConfigManagerSpecificationFactory
     * of the Object Factory component.<br/>
     * The Object Factory will be used to instantiate UserProfileManager to a concrete implementation.
     *
     */
    private final String objFactoryNS = "objFactoryNS";

    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.
     *
     */
    private final String adminDataJndiName = TestHelper.ADMIN_DATA_JNDI_NAME;

    /**
     * This holds the JNDI name to use to look up the GameDataHome service which will be used
     * to search for domain.
     *
     */
    private final String gameDataJndiName = TestHelper.GAME_DATA_JNDI_NAME;

    /**
     * This holds the name of the request parameter which will contain the image id
     * to be approved or rejected. The value should be able to be parsed into a long value.
     *
     */
    private final String imageIdRequestParamName = "ImageId";

    /**
     * This holds the name of the request parameter which will contain the domain id
     * associated with given image id. The value should be able to be parsed into a long value.
     *
     */
    private final String domainIdRequestParamName = "DomainId";

    /**
     * This holds the name of the request parameter which will contain the sponsor id
     * associated with the domain. The value should be able to be parsed into a long value.
     *
     */
    private final String sponsorIdRequestParamName = "sponsorId";

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
    private HttpServletResponse response = new MockHttpResponse();

    /**
     * The ActionContext used in tests.
     */
    private ActionContext context;

    /**
     * <p>
     * An instance of <code>SponsorImageApprovalRejectionHandler</code> which is tested.
     * </p>
     */
    private SponsorImageApprovalRejectionHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test SponsorImageApprovalRejectionHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        request = new MockHttpRequest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = XMLSTRING;
        Element element = TestHelper.loadXmlString(xml);
        target = new SponsorImageApprovalHandler(element);
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
     * <code>SponsorImageApprovalRejectionHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull(
                "Failed to initialize SponsorImageApprovalRejectionHandler.",
                target);
        assertEquals("adminDataJndiName", this.adminDataJndiName, TestHelper
                .getPrivateField(SponsorImageApprovalRejectionHandler.class,
                        target, "adminDataJndiName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(SponsorImageApprovalRejectionHandler.class,
                        target, "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(
                        SponsorImageApprovalRejectionHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(SponsorImageApprovalRejectionHandler.class,
                        target, "gameDataJndiName"));
        assertEquals("sponsorIdRequestParamName",
                this.sponsorIdRequestParamName, TestHelper.getPrivateField(
                        SponsorImageApprovalRejectionHandler.class, target,
                        "sponsorIdRequestParamName"));
        assertEquals("domainIdRequestParamName", this.domainIdRequestParamName,
                TestHelper.getPrivateField(
                        SponsorImageApprovalRejectionHandler.class, target,
                        "domainIdRequestParamName"));
        assertEquals("imageIdRequestParamName", this.imageIdRequestParamName,
                TestHelper.getPrivateField(
                        SponsorImageApprovalRejectionHandler.class, target,
                        "imageIdRequestParamName"));
        assertEquals("objFactoryNS", this.objFactoryNS, TestHelper
                .getPrivateField(SponsorImageApprovalRejectionHandler.class,
                        target, "objFactoryNS"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>SponsorImageApprovalRejectionHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new SponsorImageApprovalHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>SponsorImageApprovalRejectionHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<xxxx type=\"sponsorImageApproval\">"
                    + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                    + "<admin-data-jndi-name>"
                    + TestHelper.ADMIN_DATA_JNDI_NAME
                    + "</admin-data-jndi-name>"
                    + "<game-data-jndi-name>"
                    + TestHelper.GAME_DATA_JNDI_NAME
                    + "</game-data-jndi-name>"
                    + "<image-id-request-param>ImageId</image-id-request-param>"
                    + "<domain-id-request-param>DomainId</domain-id-request-param>"
                    + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>"
                    + "fail</fail-request-attribute></xxxx>";
            Element element = TestHelper.loadXmlString(xml);
            new SponsorImageApprovalHandler(element);
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
    public void testExecute_1_Accuracy() throws Exception {
        request.setParameter(sponsorIdRequestParamName, "1");
        request.setParameter(imageIdRequestParamName, "1");
        request.setParameter(domainIdRequestParamName, "1");
        assertNull("null should be returned if success.",
                target.execute(context));
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
        // request a sponsor that does not exist
        request.setParameter(domainIdRequestParamName, "invalid");
        request.setParameter(sponsorIdRequestParamName, "1");
        request.setParameter(imageIdRequestParamName, "1");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
    public void testExecute_3_Accuracy() throws Exception {
        // request a sponsor that does not exist
        request.setParameter(domainIdRequestParamName, "1");
        request.setParameter(sponsorIdRequestParamName, "invalid");
        request.setParameter(imageIdRequestParamName, "1");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
        // request a sponsor that does not exist
        request.setParameter(domainIdRequestParamName, "1");
        request.setParameter(sponsorIdRequestParamName, "1");
        request.setParameter(imageIdRequestParamName, "dfsfsdf");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
    public void testExecute_5_Accuracy() throws Exception {
        // request a sponsor that does not exist
        request.setParameter("domainId", "1");
        request.setParameter("sponsorId", "1");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
    public void testExecute_6_Accuracy() throws Exception {
        request.setParameter("domainId", "1001");
        // request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
    public void testExecute_7_Accuracy() throws Exception {
        // request a sponsor that does not exist
        // request.setParameter("domainId", "1001");
        request.setParameter("sponsorId", "1001");
        request.setParameter("imageId", "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
    public void testExecute_8_Accuracy() throws Exception {
        // request a sponsor that does not exist
        request.setParameter(domainIdRequestParamName, "2000");
        request.setParameter(sponsorIdRequestParamName, "1001");
        request.setParameter(imageIdRequestParamName, "1001");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        // entry not found exception
        assertEquals("SponsorImageApprovalHandler'excute method failed",
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
    public void testExecute_9_Accuracy() throws Exception {
        // request a sponsor that does not exist
        request.setParameter(domainIdRequestParamName, "1");
        request.setParameter(sponsorIdRequestParamName, "1");
        request.setParameter(imageIdRequestParamName, "2");
        assertEquals("'failedResult' should be returned if success.", "Failed", target
                .execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        // entry not found exception
        assertEquals("SponsorImageApprovalHandler'excute method failed",
                ResultCode.IMAGE_NOT_BELONG_TO_DOMAIN, result.getResultCode());
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
