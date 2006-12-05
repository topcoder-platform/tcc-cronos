/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.handlers.PendingWinnerHandler;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Test the accuracy of <code>PendingWinnerHandler</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class PendingWinnerHandlerAccuracyTests extends TestCase {
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
     * An instance of <code>PendingWinnerHandler</code> which is tested.
     * </p>
     */
    private PendingWinnerHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test PendingWinnerHandler instance and other
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
        String xml = "<handler type=\"pendingWinner\"><admin-data-jndi-name>"
                + Helper.ADMIN_DATA_JNDI_NAME
                + "</admin-data-jndi-name><pending-winner-request-attribute>"
                + "PendingWinner</pending-winner-request-attribute><fail-result>Failed"
                + "</fail-result><fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = Helper.loadXmlString(xml);
        target = new PendingWinnerHandler(element);
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
     * <code>PendingWinnerHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize PendingWinnerHandler.", target);
        assertEquals("adminDataJndiName", Helper.ADMIN_DATA_JNDI_NAME, Helper
                .getPrivateField(PendingWinnerHandler.class, target,
                        "adminDataJndiName"));
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(PendingWinnerHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(PendingWinnerHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("pendingWinnerRequestAttrName",
                "PendingWinner", Helper.getPrivateField(
                        PendingWinnerHandler.class, target,
                        "pendingWinnerRequestAttrName"));
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
    public void testExecuteAccuracy() throws Exception {
        assertEquals("null should be returned if success.", null, target.execute(context));
        assertNotNull("attribute was not set correctly",
                request.getAttribute("PendingWinner"));
    }
}
