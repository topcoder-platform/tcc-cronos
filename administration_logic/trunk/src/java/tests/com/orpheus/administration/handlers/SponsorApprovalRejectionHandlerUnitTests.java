/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>SponsorApprovalRejectionHandler</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class SponsorApprovalRejectionHandlerUnitTests extends TestCase {
    /**
     * Represents the xml string.
     */
    public static final String XMLSTRING = "<handler type=\"sponsorApproval\">"
                    + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                    + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>fail"
                    + "</fail-request-attribute></handler>";

    /**
     * <p>This holds the name of the namespace to be used when instantiating
     * ConfigManagerSpecificationFactory of the Object Factory component.
     *
     */
    private final String objFactoryNS = "objFactoryNS";

    /**
     * This holds the name of the request parameter which will contain the sponsor id
     * which is to be approved or rejected. The value should be able to be parsed into a long value.
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
     * An instance of <code>SponsorApprovalRejectionHandler</code> which is tested.
     * </p>
     */
    private SponsorApprovalRejectionHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test SponsorApprovalRejectionHandler instance and other
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
        String xml = XMLSTRING;
        Element element = TestHelper.loadXmlString(xml);
        target = new SponsorApprovalHandler(element);
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
     * <code>SponsorApprovalRejectionHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize SponsorApprovalRejectionHandler.",
                target);
        assertEquals("sponsorIdRequestParamName",
                this.sponsorIdRequestParamName, TestHelper.getPrivateField(
                        SponsorApprovalRejectionHandler.class, target,
                        "sponsorIdRequestParamName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(SponsorApprovalRejectionHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(
                        SponsorApprovalRejectionHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("objFactoryNS", this.objFactoryNS, TestHelper
                .getPrivateField(SponsorApprovalRejectionHandler.class, target,
                        "objFactoryNS"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>SponsorApprovalRejectionHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new SponsorApprovalHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>SponsorApprovalRejectionHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<xxx type=\"sponsorApproval\">"
                    + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                    + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
                    + "<fail-result>Failed</fail-result><fail-request-attribute>fail"
                    + "</fail-request-attribute></xxx>";
            Element element = TestHelper.loadXmlString(xml);
            new SponsorApprovalHandler(element);
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
        request.setParameter(sponsorIdRequestParamName, "1");
        assertEquals("null should be returned if success.", null, target
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
