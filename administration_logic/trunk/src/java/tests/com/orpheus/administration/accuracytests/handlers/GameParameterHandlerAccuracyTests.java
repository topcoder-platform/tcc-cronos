/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import com.orpheus.administration.accuracytests.Helper;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.handlers.GameParameterHandler;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Test the <code>GameParameterHandler</code> class.
 * </p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class GameParameterHandlerAccuracyTests extends TestCase {
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
        Helper.prepareTest();
        // initialize instances used in tests
        context = new ActionContext(request, response);
        //      the xml string used for test
        String xml = "<handler type=\"gameParameter\"><ballcolor-id-request-param>"
                + "ballColorId</ballcolor-id-request-param><ballcolor-id-session-attr>"
                + "ballColorIdSession</ballcolor-id-session-attr><key-count-request-param>"
                + "keyCount</key-count-request-param><key-count-session-attr>"
                + "keyCountSession</key-count-session-attr><block-count-request-param>"
                + "blockCount</block-count-request-param><block-count-session-attr>"
                + "blockCountSession</block-count-session-attr><start-date-request-param>"
                + "startDate</start-date-request-param><date-format-request-param>"
                + "dtFormat</date-format-request-param><start-date-session-attr>"
                + "startDateSession</start-date-session-attr><fail-result>Failed</fail-result>"
                + "<fail-request-attribute>fail</fail-request-attribute>"
                + "</handler>";
        Element element = Helper.loadXmlString(xml);
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
        Helper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>GameParameterHandler(Element)</code> Create for proper
     * behavior. Verify that all fields are set correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to initialize GameParameterHandler.", target);
        assertEquals("ballColorIdAttrName", "ballColorIdSession", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "ballColorIdAttrName"));
        assertEquals("failedResult", "Failed", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "failedResult"));
        assertEquals("failRequestAttrName", "fail",
                Helper.getPrivateField(GameParameterHandler.class, target,
                        "failRequestAttrName"));
        assertEquals("ballColorIdParamName", "ballColorId",
                Helper.getPrivateField(GameParameterHandler.class, target,
                        "ballColorIdParamName"));
        assertEquals("blockCountAttrName", "blockCountSession", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "blockCountAttrName"));
        assertEquals("blockCountParamName", "blockCount",
                Helper.getPrivateField(GameParameterHandler.class, target,
                        "blockCountParamName"));
        assertEquals("dtFormatParamName", "dtFormat", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "dtFormatParamName"));
        assertEquals("keyCountAttrName", "keyCountSession", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "keyCountAttrName"));
        assertEquals("keyCountParamName", "keyCount", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "keyCountParamName"));
        assertEquals("startDateAttrName", "startDateSession", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "startDateAttrName"));
        assertEquals("startDateParamName", "startDate", Helper
                .getPrivateField(GameParameterHandler.class, target,
                        "startDateParamName"));
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
        request.setParameter("ballColorId", "1001");
        request.setParameter("keyCount", "100");
        request.setParameter("blockCount", "100");
        request.setParameter("dtFormat", "yyyy-MM-dd");
        request.setParameter("startDate", "1984-09-03");
        assertNull("null should be returned if success.", target.execute(context));

        MockHttpSession session = (MockHttpSession) request.getSession();
        assertEquals("GameParameterHandler'excute method failed",
                new Long(1001), session.getAttribute("ballColorIdSession"));
        assertEquals("GameParameterHandler'excute method failed",
                new Integer(100), session.getAttribute("keyCountSession"));
        assertEquals("GameParameterHandler'excute method failed",
                new Integer(100), session.getAttribute("blockCountSession"));
        assertEquals("GameParameterHandler'excute method failed",
                new SimpleDateFormat("yyyy-MM-dd").parse("1984-09-03"),
                session.getAttribute("startDateSession"));
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
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteAccuracy3() throws Exception {
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
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteAccuracy4() throws Exception {
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
     * Accuracy test. Tests the <code>execute(ActionContext)</code> for
     * proper behavior. Verifies if the return value is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecuteAccuracy5() throws Exception {
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
}
