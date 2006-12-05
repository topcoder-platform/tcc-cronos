/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.DeleteSlotHandler;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the accuracy of <code>DeleteSlotHandler</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class DeleteSlotHandlerAccuracyTests extends TestCase {
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
     * An instance of <code>DeleteSlotHandler</code> which is tested.
     * </p>
     */
    private DeleteSlotHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test DeleteSlotHandler instance and other
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
        String xml = "<handler type=\"deleteSlot\"><game-data-jndi-name>"
                + Helper.GAME_DATA_JNDI_NAME
                + "</game-data-jndi-name><game-id-request-param>"
                + "gameId</game-id-request-param><slot-id-request-param>slotId"
                + "</slot-id-request-param><fail-result>Failed</fail-result>"
                + "<fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = Helper.loadXmlString(xml);
        target = new DeleteSlotHandler(element);
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
     * <code>DeleteSlotHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize DeleteSlotHandler.", target);
        assertEquals("gameDataJndiName", Helper.GAME_DATA_JNDI_NAME, Helper
                .getPrivateField(DeleteSlotHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(DeleteSlotHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(DeleteSlotHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameIdRequestParamName", "gameId",
                Helper.getPrivateField(DeleteSlotHandler.class, target,
                        "gameIdRequestParamName"));
        assertEquals("slotIdRequestParamName", "slotId",
                Helper.getPrivateField(DeleteSlotHandler.class, target,
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
        assertNull("null should be returned.", target
                .execute(context));
        assertTrue("slot have not been deleted corretly.", DataProvider.isDeleted);
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
        request.setParameter("slotId", "1002");
        assertEquals("'failedResult' should be returned if success.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("DeleteSlotHandler'excute method failed",
                ResultCode.SLOT_STARTED_HOSTING, result.getResultCode());
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
        request.setParameter("gameId", "1001");
        request.setParameter("slotId", "1003");
        assertEquals("'failedResult' should be returned if success.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("DeleteSlotHandler'excute method failed",
                ResultCode.SLOT_FINISHED_HOSTING, result.getResultCode());
    }
}
