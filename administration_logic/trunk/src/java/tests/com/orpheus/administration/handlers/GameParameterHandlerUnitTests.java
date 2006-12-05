/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.TestHelper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>GameParameterHandler</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class GameParameterHandlerUnitTests extends TestCase {
    /**
     * This holds the name of the request parameter which will contain the ball
     * color id. The value should be able to be parsed into a long value.
     *
     */
    private final String ballColorIdParamName = "ballColorId";

    /**
     * This holds the name of the session attribute which will contain the
     * parsed ball color id. It will be stored as a Long value.
     *
     */
    private final String ballColorIdAttrName = "ballColorId";

    /**
     * This holds the name of the request parameter which will contain the key
     * count. The value should be able to be parsed into an int value.
     *
     */
    private final String keyCountParamName = "keyCount";

    /**
     * This holds the name of the session attribute which will contain the
     * parsed key count. It will be stored as an Integer value.
     *
     */
    private final String keyCountAttrName = "keyCount";

    /**
     * This holds the name of the request parameter which will contain the block
     * count. The value should be able to be parsed into an int value.
     *
     */
    private final String blockCountParamName = "blockCount";

    /**
     * This holds the name of the session attribute which will contain the
     * parsed block count. It will be stored as an Integer value.
     *
     */
    private final String blockCountAttrName = "blockCount";

    /**
     * This holds the name of the request parameter which will contain the game
     * start date. The value should be able to be parsed into a java.util.Date
     * object.
     *
     */
    private final String startDateParamName = "startDate";

    /**
     * This holds the name of the request parameter which will contain the date
     * format to use to parse a string date value into a java.util.Date object.
     *
     */
    private final String dtFormatParamName = "dtFormat";

    /**
     * This holds the name of the session attribute which will contain the
     * parsed start Date. It will be stored as java.util.Date.
     *
     */
    private final String startDateAttrName = "startDate";

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
     * An instance of <code>GameParameterHandler</code> which is tested.
     * </p>
     */
    private GameParameterHandler target = null;

    /**
     * <p>
     * setUp() routine. Creates test GameParameterHandler instance and other
     * instances used in tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        response = new MockHttpResponse();
        request = new MockHttpRequest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        // the xml string used for test
        String xml = "<handler type=\"gameParameter\"><ballcolor-id-request-param>"
                + "ballColorId</ballcolor-id-request-param><ballcolor-id-session-attr>"
                + "ballColorId</ballcolor-id-session-attr><key-count-request-param>"
                + "keyCount</key-count-request-param><key-count-session-attr>"
                + "keyCount</key-count-session-attr><block-count-request-param>"
                + "blockCount</block-count-request-param><block-count-session-attr>"
                + "blockCount</block-count-session-attr><start-date-request-param>"
                + "startDate</start-date-request-param><date-format-request-param>"
                + "dtFormat</date-format-request-param><start-date-session-attr>"
                + "startDate</start-date-session-attr><fail-result>Failed</fail-result>"
                + "<fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = TestHelper.loadXmlString(xml);
        target = new GameParameterHandler(element);
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
     * Accuracy test. Tests the <code>GameParameterHandler(Element)</code>
     * Create for proper behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to initialize GameParameterHandler.", target);
        assertEquals("ballColorIdAttrName", this.ballColorIdAttrName,
                TestHelper.getPrivateField(GameParameterHandler.class, target,
                        "ballColorIdAttrName"));
        assertEquals("failedResult", this.failedResult, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", this.failRequestAttrName,
                TestHelper.getPrivateField(GameParameterHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("ballColorIdParamName", this.ballColorIdParamName,
                TestHelper.getPrivateField(GameParameterHandler.class, target,
                        "ballColorIdParamName"));
        assertEquals("blockCountAttrName", this.blockCountAttrName, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "blockCountAttrName"));
        assertEquals("blockCountParamName", this.blockCountParamName,
                TestHelper.getPrivateField(GameParameterHandler.class, target,
                        "blockCountParamName"));
        assertEquals("dtFormatParamName", this.dtFormatParamName, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "dtFormatParamName"));
        assertEquals("keyCountAttrName", this.keyCountAttrName, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "keyCountAttrName"));
        assertEquals("keyCountParamName", this.keyCountParamName, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "keyCountParamName"));
        assertEquals("startDateAttrName", this.startDateAttrName, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "startDateAttrName"));
        assertEquals("startDateParamName", this.startDateParamName, TestHelper
                .getPrivateField(GameParameterHandler.class, target,
                        "startDateParamName"));
    }

    /**
     * <p>
     * Failure test. Tests the <code>GameParameterHandler(Element)</code> for
     * proper behavior. Verify that IllegalArgumentException is thrown if any
     * argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorElement_1_failure() throws Exception {
        try {
            new GameParameterHandler(null);
            fail("IllegalArgumentException expected if the 1st argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>GameParameterHandler(Element)</code> for
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
            String xml = "<XXX type=\"gameParameter\"><ballcolor-id-request-param>"
                    + "ballColorId</ballcolor-id-request-param><ballcolor-id-session-attr>"
                    + "ballColorId</ballcolor-id-session-attr><key-count-request-param>"
                    + "keyCount</key-count-request-param><key-count-session-attr>"
                    + "keyCount</key-count-session-attr><block-count-request-param>"
                    + "blockCount</block-count-request-param><block-count-session-attr>"
                    + "blockCount</block-count-session-attr><start-date-request-param>"
                    + "startDate</start-date-request-param><date-format-request-param>"
                    + "dateFormat</date-format-request-param><start-date-session-attr>"
                    + "startDate</start-date-session-attr><fail-result>Failed</fail-result>"
                    + "<fail-request-attribute>fail</fail-request-attribute>"
                    + "</XXX>";
            Element element = TestHelper.loadXmlString(xml);
            new GameParameterHandler(element);
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
        request.setParameter("ballColorId", "1001");
        request.setParameter("keyCount", "100");
        request.setParameter("blockCount", "100");
        request.setParameter("dtFormat", "yyyy-MM-dd");
        request.setParameter("startDate", "1984-09-03");
        assertNull("null should be returned if success.", target
                .execute(context));

        MockHttpSession session = (MockHttpSession) request.getSession();
        assertEquals("GameParameterHandler'excute method failed",
                new Long(1001), session.getAttribute(ballColorIdAttrName));
        assertEquals("GameParameterHandler'excute method failed", new Integer(
                100), session.getAttribute(keyCountAttrName));
        assertEquals("GameParameterHandler'excute method failed", new Integer(
                100), session.getAttribute(blockCountAttrName));
        assertEquals("GameParameterHandler'excute method failed",
                new SimpleDateFormat("yyyy-MM-dd").parse("1984-09-03"), session
                        .getAttribute(startDateAttrName));
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
        request.setParameter("ballColorId", "ss123");
        request.setParameter("keyCount", "100");
        request.setParameter("blockCount", "100");
        request.setParameter("dtFormat", "yyyy-MM-dd");
        request.setParameter("startDate", "1984-09-03");
        assertEquals("'failedResult' should be returned if success.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("GameParameterHandler'excute method failed",
                ResultCode.PARAMETER_NOT_LONG, result.getResultCode());
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
    public void testExecute_3_Accuracy() throws Exception {
        request.setParameter("ballColorId", "1001");
        request.setParameter("keyCount", "10dfd");
        request.setParameter("blockCount", "100");
        request.setParameter("dtFormat", "yyyy-MM-dd");
        request.setParameter("startDate", "1984-09-03");
        assertEquals("'failedResult' should be returned if success.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("GameParameterHandler'excute method failed",
                ResultCode.PARAMETER_NOT_INTEGER, result.getResultCode());
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
    public void testExecute_4_Accuracy() throws Exception {
        request.setParameter("ballColorId", "1001");
        request.setParameter("keyCount", "1000");
        request.setParameter("blockCount", "10d0df");
        request.setParameter("dtFormat", "yyyy-MM-dd");
        request.setParameter("startDate", "1984-09-03");
        assertEquals("'failedResult' should be returned if success.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("GameParameterHandler'excute method failed",
                ResultCode.PARAMETER_NOT_INTEGER, result.getResultCode());
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
    public void testExecute_5_Accuracy() throws Exception {
        request.setParameter("ballColorId", "1001");
        request.setParameter("keyCount", "1044");
        request.setParameter("blockCount", "100");
        request.setParameter("dtFormat", "yyyy-MM-dd");
        request.setParameter("startDate", "1984-09-sf");
        assertEquals("'failedResult' should be returned if success.", "Failed",
                target.execute(context));

        // judge the HandlerResult
        HandlerResult result = (HandlerResult) request.getAttribute("fail");
        assertEquals("GameParameterHandler'excute method failed",
                ResultCode.PARAMETER_NOT_DATE, result.getResultCode());
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
