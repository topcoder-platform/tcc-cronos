/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.handlers.PendingSponsorHandler;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * Accuracy test for PendingSponsorHandler class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PendingSponsorHandlerAccuracyTests extends TestCase {
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
        Helper.prepareTest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        // the xml string used for test
        String xml = "<handler type=\"pendingSponsorDomain\">"
                + "<object-factory-ns>objFactoryNS</object-factory-ns>"
                + "<game-data-jndi-name>"
                + "GameDataHome"
                + "</game-data-jndi-name>"
                + "<sponsor-domain-request-attribute>SponsorDomains</sponsor-domain-request-attribute>"
                + "<fail-result>Failed</fail-result><fail-request-attribute>"
                + "fail</fail-request-attribute></handler>";
        Element element = Helper.loadXmlString(xml);
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
        Helper.clearTestEnvironment();
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
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize PendingSponsorHandler.", target);
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(PendingSponsorHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(PendingSponsorHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameDataJndiName", "GameDataHome", Helper
                .getPrivateField(PendingSponsorHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("objFactoryNS", "objFactoryNS", Helper
                .getPrivateField(PendingSponsorHandler.class, target,
                        "objFactoryNS"));
        assertEquals("sponsorDomainRequestAttrName", "SponsorDomains",
                Helper.getPrivateField(PendingSponsorHandler.class, target,
                        "sponsorDomainRequestAttrName"));
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
        assertEquals("null should be returned if success.", null, target
                .execute(context));
        assertNotNull("the attribute was not set.",
                request.getAttribute("SponsorDomains"));
    }
}
