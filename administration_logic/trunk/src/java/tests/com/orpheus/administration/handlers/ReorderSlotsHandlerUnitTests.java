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
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>ReorderSlotsHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReorderSlotsHandlerUnitTests extends TestCase {
    /**
     * This holds the JNDI name to use to look up the GameDataHome service.
     *
     */
    private final String gameDataJndiName = TestHelper.GAME_DATA_JNDI_NAME;

    /**
     * This holds the name of the request parameter which will contain the game
     * id associated with pending winner. The value should be able to be parsed
     * into a long value.
     *
     */
    private final String gameIdRequestParamName = "gameId";

    /**
     * This holds the name of the request parameter which will contain the slot
     * id to re-order. The value should be able to be parsed into a long value.
     *
     */
    private final String slotIdRequestParamName = "slotId";

    /**
     * This holds the name of the request parameter which will contain the
     * offset for the slot. The value should be able to be parsed into an int
     * value.
     *
     */
    private final String offsetRequestParamName = "offset";

    /**
     * This holds the name of the result (as configured in Front Controller
     * component) which should execute in case of execution failure.
     *
     */
    private final String failedResult = "Failed";

    /**
     * This holds the name of the request attribute to which HandlerResult
     * instance should be assigned to, in case of execution failure.
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
    private MockHttpResponse response = new MockHttpResponse();

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
        TestHelper.prepareTest();
        request = new MockHttpRequest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        // the xml string used for test
        String xml = "<handler type=\"reorderSlots\">"
                + "<game-data-jndi-name>"
                + this.gameDataJndiName
                + "</game-data-jndi-name>"
                + "<game-id-request-param>gameId</game-id-request-param>"
                + "<slot-id-request-param>slotId</slot-id-request-param>"
                + "<offset-request-param>offset</offset-request-param><fail-result>"
                + "Failed</fail-result><fail-request-attribute>fail"
                + "</fail-request-attribute></handler>";
        Element element = TestHelper.loadXmlString(xml);
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
        TestHelper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ReorderSlotsHandler(Element)</code>
     * Create for proper behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize ReorderSlotsHandler.", target);
        assertEquals("gameDataJndiName", this.gameDataJndiName, TestHelper
                .getPrivateField(ReorderSlotsHandler.class, target,
                        "gameDataJndiName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(ReorderSlotsHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(ReorderSlotsHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("gameIdRequestParamName", this.gameIdRequestParamName,
                TestHelper.getPrivateField(ReorderSlotsHandler.class, target,
                        "gameIdRequestParamName"));
        assertEquals("offsetRequestParamName", this.offsetRequestParamName,
                TestHelper.getPrivateField(ReorderSlotsHandler.class, target,
                        "offsetRequestParamName"));
        assertEquals("slotIdRequestParamName", this.slotIdRequestParamName,
                TestHelper.getPrivateField(ReorderSlotsHandler.class, target,
                        "slotIdRequestParamName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>ReorderSlotsHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new ReorderSlotsHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>ReorderSlotsHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if
     * handlerElement.getTagName() is not 'handler'.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_2_failure() throws Exception {
        try {
            // the xml string used for test
            String xml = "<x type=\"reorderSlots\">"
                    + "<game-data-jndi-name>"
                    + this.gameDataJndiName
                    + "</game-data-jndi-name>"
                    + "<game-id-request-param>gameId</game-id-request-param>"
                    + "<slot-id-request-param>slotId</slot-id-request-param>"
                    + "<offset-request-param>offset</offset-request-param><fail-result>"
                    + "Failed</fail-result><fail-request-attribute>fail"
                    + "</fail-request-attribute></x>";
            Element element = TestHelper.loadXmlString(xml);
            new ReorderSlotsHandler(element);
            fail("IllegalArgumentException expected if handlerElement.getTagName() is not 'handler'.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_1_Accuracy() throws Exception {
        request.setParameter("gameId", "1");
        request.setParameter("slotId", "1");
        request.setParameter("offset", "2");

        assertNull("null should be returned if fail.", target.execute(context));
        // verify the hostingSlots
        HostingSlot[] slots = MockGameDataBean.getSlots();
        assertNotNull("ReorderSlotsHandler'excute method failed", slots);
        assertEquals("ReorderSlotsHandler'excute method failed", 3, slots[0]
                .getSequenceNumber());
        assertEquals("ReorderSlotsHandler'excute method failed", 2, slots[2]
                .getSequenceNumber());
        assertEquals("ReorderSlotsHandler'excute method failed", 1, slots[1]
                .getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for proper
     * behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_2_Accuracy() throws Exception {
        request.setParameter("gameId", "1");
        request.setParameter("slotId", "1");
        request.setParameter("offset", "6");
        target.execute(context);
        // assertEquals("'failedResult' should be returned if success.",
        // "Failed", target
        // .execute(context));
        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("ReorderSlotsHandler'excute method failed",
                ResultCode.CANNOT_MOVE_SLOT_BEYOND_LAST, result.getResultCode());
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
