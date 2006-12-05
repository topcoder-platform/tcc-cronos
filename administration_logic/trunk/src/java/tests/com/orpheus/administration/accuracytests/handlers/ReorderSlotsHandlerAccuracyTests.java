/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.ReorderSlotsHandler;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Test the accuracy of <code>ReorderSlotsHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReorderSlotsHandlerAccuracyTests extends TestCase {
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
     * An instance of <code>ReorderSlotsHandler</code> which is tested.
     * </p>
     */
    private ReorderSlotsHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test ReorderSlotsHandler instance and other
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
        String xml = "<handler type=\"reorderSlots\">"
                + "<game-data-jndi-name>"
                + Helper.GAME_DATA_JNDI_NAME
                + "</game-data-jndi-name>"
                + "<game-id-request-param>gameId</game-id-request-param>"
                + "<slot-id-request-param>slotId</slot-id-request-param>"
                + "<offset-request-param>offset</offset-request-param><fail-result>"
                + "Failed</fail-result><fail-request-attribute>fail"
                + "</fail-request-attribute></handler>";
        Element element = Helper.loadXmlString(xml);
        target = new ReorderSlotsHandler(element);
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
     * <code>ReorderSlotsHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize ReorderSlotsHandler.", target);
        assertEquals("gameDataJndiName", Helper.GAME_DATA_JNDI_NAME, Helper
                .getPrivateField(ReorderSlotsHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(ReorderSlotsHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(ReorderSlotsHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameIdRequestParamName", "gameId",
                Helper.getPrivateField(ReorderSlotsHandler.class, target,
                        "gameIdRequestParamName"));
        assertEquals("offsetRequestParamName", "offset",
                Helper.getPrivateField(ReorderSlotsHandler.class, target,
                        "offsetRequestParamName"));
        assertEquals("slotIdRequestParamName", "slotId",
                Helper.getPrivateField(ReorderSlotsHandler.class, target,
                        "slotIdRequestParamName"));
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
        request.setParameter("gameId", "1001");
        request.setParameter("slotId", "1001");
        request.setParameter("offset", "2");

        assertNull("null should be returned if fail.", target
                .execute(context));
        // verify the hostingSlots   
        assertNotNull("ReorderSlotsHandler'excute method failed",
                DataProvider.slots);
        assertTrue("ReorderSlotsHandler'excute method failed",
                DataProvider.slots[0].getSequenceNumber() == 3);
        assertEquals("ReorderSlotsHandler'excute method failed", 2,
                DataProvider.slots[2].getSequenceNumber());
        assertEquals("ReorderSlotsHandler'excute method failed",1, 
                DataProvider.slots[1].getSequenceNumber());
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
        request.setParameter("gameId", "1001");
        request.setParameter("slotId", "1001");
        request.setParameter("offset", "6");
        target.execute(context);
        // assertEquals("'failedResult' should be returned if success.", "Failed", target
                // .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("ReorderSlotsHandler'excute method failed",
                ResultCode.CANNOT_MOVE_SLOT_BEYOND_LAST, result.getResultCode());
    }
}
