/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>PendingSponsorHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PendingSponsorHandlerUnitTests extends TestCase {
    /**
     * <p>This holds the name of the namespace to be used
     * when instantiating ConfigManagerSpecificationFactory of the Object Factory component.
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
     * This holds the name of the request attribute to which UserProfile instance representing domain's sponsor
     * should be assigned to, in case of successful execution.
     *
     */
    private final String sponsorDomainAttrName = "SponsorDomains";

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
     * An instance of <code>PendingSponsorHandler</code> which is tested.
     * </p>
     */
    private PendingSponsorHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test PendingSponsorHandler instance and other
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
        String xml = "<handler type=\"pendingSponsorDomain\">"
                + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                + "<game-data-jndi-name>"
                + TestHelper.GAME_DATA_JNDI_NAME
                + "</game-data-jndi-name>"
                + "<sponsor-domain-request-attribute>SponsorDomains</sponsor-domain-request-attribute>"
                + "<fail-result>Failed</fail-result><fail-request-attribute>"
                + "fail</fail-request-attribute></handler>";
        Element element = TestHelper.loadXmlString(xml);
        target = new PendingSponsorHandler(element);
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
     * <code>PendingSponsorHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize PendingSponsorHandler.", target);
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(PendingSponsorHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(PendingSponsorHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(PendingSponsorHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("objFactoryNS", this.objFactoryNS, TestHelper
                .getPrivateField(PendingSponsorHandler.class, target,
                        "objFactoryNS"));
        assertEquals("sponsorDomainRequestAttrName", this.sponsorDomainAttrName,
                TestHelper.getPrivateField(PendingSponsorHandler.class, target,
                        "sponsorDomainRequestAttrName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>PendingSponsorHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new PendingSponsorHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>PendingSponsorHandler(Element)</code> for
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
                + "<sponsor-domain-request-attribute>SponsorDomains</sponsor-domain-request-attribute>"
                + "<fail-result>Failed</fail-result><fail-request-attribute>"
                + "fail</fail-request-attribute></xxx>";
            Element element = TestHelper.loadXmlString(xml);
            new PendingSponsorHandler(element);
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
        assertEquals("null should be returned if success.", null, target
                .execute(context));
        assertNotNull("the attribute was not set.",
                request.getAttribute("SponsorDomains"));
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
