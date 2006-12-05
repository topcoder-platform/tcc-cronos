/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.MockGameDataBean;
import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>DeleteSlotHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DeleteSlotHandlerUnitTests extends TestCase {
    /**
     * This holds the JNDI name to use to look up the GameDataHome service.
     *
     */
    private final String gameDataJndiName = TestHelper.GAME_DATA_JNDI_NAME;

    /**
     * This holds the name of the request parameter which will contain the game id
     * associated with pending winner.
     *
     */
    private final String gameIdRequestParamName = "gameId";

    /**
     * This holds the name of the request parameter which will contain the slot id
     * to delete.
     *
     */
    private final String slotIdRequestParamName = "slotId";

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
        TestHelper.prepareTest();
        request = new MockHttpRequest();
        response = new MockHttpResponse();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = "<handler type=\"deleteSlot\"><game-data-jndi-name>"
                + gameDataJndiName
                + "</game-data-jndi-name><game-id-request-param>"
                + "gameId</game-id-request-param><slot-id-request-param>slotId"
                + "</slot-id-request-param><fail-result>Failed</fail-result>"
                + "<fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = TestHelper.loadXmlString(xml);
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
        TestHelper.clearTestEnvironment();
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
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize DeleteSlotHandler.", target);
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(DeleteSlotHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(DeleteSlotHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(DeleteSlotHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameIdRequestParamName", this.gameIdRequestParamName,
                TestHelper.getPrivateField(DeleteSlotHandler.class, target,
                        "gameIdRequestParamName"));
        assertEquals("slotIdRequestParamName", this.slotIdRequestParamName,
                TestHelper.getPrivateField(DeleteSlotHandler.class, target,
                        "slotIdRequestParamName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>DeleteSlotHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new DeleteSlotHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdminSummaryHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<xxx type=\"deleteSlot\"><game-data-jndi-name>"
                    + "GameDataHome</game-data-jndi-name><game-id-request-param>"
                    + "gameId</game-id-request-param><slot-id-request-param>slotId"
                    + "</slot-id-request-param><fail-result>Failed</fail-result>"
                    + "<fail-request-attribute>fail</fail-request-attribute>"
                    + "</xxx>";
            Element element = TestHelper.loadXmlString(xml);
            new AdminSummaryHandler(element);
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
        request.setParameter("gameId", "2");
        request.setParameter("slotId", "1");
        assertNull("null should be returned.", target
                .execute(context));
        assertTrue("slot have not been deleted corretly.", MockGameDataBean.isDeleted());
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
        request.setParameter("gameId", "2");
        request.setParameter("slotId", "2");
        assertEquals("'failedResult' should be returned if failed.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute(failRequestAttrName);
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
    public void testExecute_3_Accuracy() throws Exception {
        request.setParameter("gameId", "2");
        request.setParameter("slotId", "3");
        assertEquals("'failedResult' should be returned if failed.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("DeleteSlotHandler'excute method failed",
                ResultCode.SLOT_FINISHED_HOSTING, result.getResultCode());
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
