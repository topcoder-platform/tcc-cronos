/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.accuracytests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import junit.framework.TestCase;

import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.actions.AbstractAction;
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.actions.ajax.CustomFormatAJAXResult;
import com.topcoder.service.actions.ajax.serializers.JSONDataSerializer;

/**
 * <p>
 * Accuracy cases of the <code>CustomFormatAJAXResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CustomFormatAJAXResultAccuracyTests extends TestCase {
    /**
     * <p>
     * The key under which the data to be serialized will be stored.
     * </p>
     */
    private static final String RESULT_DATA_KEY = "return";
    /**
     * <p>
     * The CustomFormatAJAXResult for test.
     * </p>
     */
    private CustomFormatAJAXResult result;

    /**
     * <p>
     * The MockHttpServletRequest for test.
     * </p>
     */
    private MockHttpServletRequest request;

    /**
     * <p>
     * The MockHttpServletResponse for test.
     * </p>
     */
    private MockHttpServletResponse response;

    /**
     * <p>
     * The MockActionInvocation for test.
     * </p>
     */
    private MockActionInvocation invocation;

    /**
     * <p>
     * The result template used for test.
     * </p>
     */
    private String resultTemplate = "name = ${action} , result = ${result}";

    /**
     * <p>
     * The error template used for test.
     * </p>
     */
    private String errorTemplate = "name = ${action} , errorMessage = ${error}";

    /**
     * <p>
     * The left bracket around a token.
     * </p>
     */
    private String leftBracket = "{";

    /**
     * <p>
     * The right bracket around a token.
     * </p>
     */
    private String rightBracket = "}";

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        result = new CustomFormatAJAXResult();
        JSONDataSerializer serializer = new JSONDataSerializer();
        if (serializer.getJsonErrorTemplate().indexOf("[") > 0) {
            leftBracket = "[";
            rightBracket = "]";
            resultTemplate = resultTemplate.replace("{", "[");
            resultTemplate = resultTemplate.replace("}", "]");
            errorTemplate = errorTemplate.replace("{", "[");
            errorTemplate = errorTemplate.replace("}", "]");
        }
        serializer.setJsonErrorTemplate(errorTemplate);
        serializer.setJsonResultTemplate(resultTemplate);
        // set the serializer.
        result.setDataSerializer(serializer);
        // set ActionContext
        ActionContext.setContext(new ActionContext(new HashMap()));
        response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        invocation = new MockActionInvocation();
    }

    /**
     * <p>
     * The accuracy test for <code>execute</code>.
     * </p>
     * <p>
     * Test case : using default CustomFormatAJAXResult set.
     * </p>
     *
     * @throws Exception
     */
    public void testExecute_Acc_1() throws Exception {
        AbstractActionAccMock action = new AbstractActionAccMock();
        AggregateDataModel model = new AggregateDataModel();
        model.setAction("actionName");
        model.setData(RESULT_DATA_KEY, new Object());
        action.setModel(model);
        invocation.setAction(action);
        result.execute(invocation);
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("The result is wrong.", "name = actionName , result = {}", response.getContentAsString());
    }

    /**
     * <p>
     * The accuracy test for <code>execute</code>.
     * </p>
     * <p>
     * Test case : the data is a string
     * </p>
     *
     * @throws Exception
     */
    public void testExecute_Acc_2() throws Exception {
        AbstractActionAccMock action = new AbstractActionAccMock();
        AggregateDataModel model = new AggregateDataModel();
        model.setAction("actionName");
        model.setData(RESULT_DATA_KEY, "{\"key\" : \"value\"}");
        action.setModel(model);
        invocation.setAction(action);
        result.execute(invocation);
        assertNull(response.getHeader("Cache-Control"));
        assertEquals("The result is wrong.", "name = actionName , result = {\"key\":\"value\"}", response
                .getContentAsString());
    }

    /**
     * <p>
     * The accuracy test for <code>execute</code>.
     * </p>
     * <p>
     * Test case : gzip is used.
     * </p>
     *
     * @throws Exception
     */
    public void testExecute_Acc_3() throws Exception {
        AbstractActionAccMock action = new AbstractActionAccMock();
        AggregateDataModel model = new AggregateDataModel();
        model.setAction("actionName");
        model.setData(RESULT_DATA_KEY, "{\"key\" : \"value\"}");
        action.setModel(model);
        invocation.setAction(action);
        request.addHeader("Accept-Encoding", "gzip");
        result.setEnabledGzip(true);
        result.execute(invocation);
        assertNull(response.getHeader("Cache-Control"));
        GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(response.getContentAsByteArray()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        String uncompressedResult = out.toString();
        assertEquals("The result is wrong.", "name = actionName , result = {\"key\":\"value\"}",
                uncompressedResult);
        assertEquals("The Content-Encoding head should be set.", "gzip", response
                .getHeader("Content-Encoding"));
        in.close();
        out.close();
    }

    /**
     * <p>
     * The accuracy test for <code>execute</code>.
     * </p>
     * <p>
     * Test case : if noCache is set, the related head should be set.
     * </p>
     *
     * @throws Exception
     */
    public void testExecute_Acc_4() throws Exception {
        AbstractActionAccMock action = new AbstractActionAccMock();
        AggregateDataModel model = new AggregateDataModel();
        model.setAction("actionName");
        model.setData(RESULT_DATA_KEY, new Object());
        action.setModel(model);
        invocation.setAction(action);
        result.setNoCache(true);
        result.execute(invocation);
        assertEquals("no-cache", response.getHeader("Cache-Control"));
        assertEquals("0", response.getHeader("Expires"));
        assertEquals("no-cache", response.getHeader("Pragma"));
    }

    /**
     * <p>
     * Tests accuracy of <code>execute</code>
     * </p>
     * <p>
     * Case: action is not AbstractAction type, do nothing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Acc_5() throws Exception {
        invocation.setAction(new ActionAccMock());
        result.execute(invocation);
        assertEquals("", response.getContentAsString());
    }

    /**
     * <p>
     * Mock class for the Struts Action, extends AbstractAction.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class AbstractActionAccMock extends AbstractAction {
        /**
         * <p>
         * Represents the serial id.
         * </p>
         */
        private static final long serialVersionUID = 5946034242786345717L;
    }

    /**
     * <p>
     * Mock class for the Struts Action, extends ActionSupport.
     * </p>
     */
    private class ActionAccMock extends ActionSupport {

    }

}
