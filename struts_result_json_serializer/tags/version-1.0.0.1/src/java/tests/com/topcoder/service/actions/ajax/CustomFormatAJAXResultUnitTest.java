/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.actions.ajax.processors.DefaultAJAXResultPreProcessor;
import com.topcoder.service.actions.ajax.serializers.JSONDataSerializer;



/**
 * <p>
 * Unit tests for <code>CustomFormatAJAXResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CustomFormatAJAXResultUnitTest extends TestCase {

    /**
     * <p>
     * The CustomFormatAJAXResult instance used for test.
     * </p>
     */
    private CustomFormatAJAXResult instance;

    /**
     * <p>
     * Set up the environments.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new CustomFormatAJAXResult();
        ActionContext.setContext(new ActionContext(new HashMap()));
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(CustomFormatAJAXResultUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor of CustomFormatAJAXResult, make sure it could create the
     * CustomFormatAJAXResult instance successfully, and the default field set with correct values.
     * </p>
     */
    public void testConstructor_Accuracy_1() {
        assertNotNull("The CustomFormatAJAXResult instance should be created.", instance);
        assertTrue("The dataSerializer should be JSONDataSerializer type",
                instance.getDataSerializer() instanceof JSONDataSerializer);
        assertTrue("The dataPreProcessor should be DefaultAJAXResultPreProcessor type", instance
                .getDataPreprocessor() instanceof DefaultAJAXResultPreProcessor);
        assertNull("The dataPostProcessor should be null.", instance.getDataPostProcessor());
    }

    /**
     * <p>
     * Accuracy test for the constructor of CustomFormatAJAXResult, make sure it could create the
     * CustomFormatAJAXResult instance successfully, and the default field set with correct values.
     * </p>
     */
    public void testConstructor_Accuracy_2() {
        assertNotNull("The CustomFormatAJAXResult instance should be created.", instance);
        assertEquals("The charset should match", "UTF-8", instance.getCharset());
        assertEquals("The noCache should match", false, instance.isNoCache());
        assertEquals("The contentType should match", "application/json", instance.getContentType());
        assertEquals("The enabledGzip should match", false, instance.isEnabledGzip());
    }

    /**
     * <p>
     * Failure test for <code>execute</code>, if invocation is null, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Fialure_1() throws Exception {
        try {
            instance.execute(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>execute</code>, if the serialization failed, AJAXDataSerializationException
     * should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Fialure_2() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();
        AggregateDataModel dataModel = new AggregateDataModel();
        dataModel.setData("return", "error string");
        action.setModel(dataModel);
        invocation.setAction(action);

        try {
            instance.execute(invocation);
            fail("AJAXDataSerializationException should be thrown.");
        } catch (AJAXDataSerializationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>execute</code>, if the post-processing failed, AJAXDataPostProcessingException
     * should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Fialure_3() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();
        AggregateDataModel dataModel = new AggregateDataModel();
        action.setModel(dataModel);
        invocation.setAction(action);
        MockAJAXResultPostProcessor postProcessor = new MockAJAXResultPostProcessor();
        postProcessor.setException(true);
        instance.setDataPostProcessor(postProcessor);
        try {
            instance.execute(invocation);
            fail("AJAXDataPostProcessingException should be thrown.");
        } catch (AJAXDataPostProcessingException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>execute</code>, if the io error occurs, IOException should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Fialure_4() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ((MockServletOutputStream) response.outputStream).exception = true;
        ServletActionContext.setResponse(response);

        try {
            instance.execute(invocation);
            fail("IOException should be thrown.");
        } catch (IOException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure it could process the data correctly with the correct
     * result.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_1() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

        ((JSONDataSerializer) instance.getDataSerializer()).setJsonResultTemplate("$[action]:$[result]");
        instance.execute(invocation);

        assertEquals("The value should match", "name:{\"key1\":\"value1\"}",
                ((MockServletOutputStream) response.outputStream).buf.toString());
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure it could process the data correctly, the response
     * should be set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_2() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

        ((JSONDataSerializer) instance.getDataSerializer()).setJsonResultTemplate("${action}:${result}");
        instance.setNoCache(true);
        instance.execute(invocation);

        assertEquals("The content type should match.", "application/json;charset=UTF-8", response
                .getContentType());
        assertEquals("The header of Cache-Control should match.", "no-cache", response.heads
                .get("Cache-Control"));
        assertEquals("The header of Expires should match.", "0", response.heads.get("Expires"));
        assertEquals("The header of Pragma should match.", "no-cache", response.heads.get("Pragma"));
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure it could process the data correctly, if the
     * enabledGzip is true, but the HTTP servlet request is null, the Content-Encoding should not be set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_3() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

        ((JSONDataSerializer) instance.getDataSerializer()).setJsonResultTemplate("${action}:${result}");
        instance.setEnabledGzip(true);
        instance.execute(invocation);
        assertEquals("The header of Cache-Control should match.", null, response.heads
                .get("Content-Encoding"));
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure it could process the data correctly, if the
     * enabledGzip is true, but the HTTP servlet request is not null, but request's Accept-Encoding head is
     * null, the Content-Encoding should not be set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_4() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);

        instance.setEnabledGzip(true);
        instance.execute(invocation);
        assertEquals("The header of Cache-Control should match.", null, response.heads
                .get("Content-Encoding"));
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure it could process the data correctly, if the
     * enabledGzip is true, but the HTTP servlet request is not null, but request's "Accept-Encoding" head is
     * not "gzip", the Content-Encoding should not be set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_5() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.heads.put("Accept-Encoding", "no");
        ServletActionContext.setRequest(request);

        instance.setEnabledGzip(true);
        instance.execute(invocation);
        assertEquals("The header of Cache-Control should match.", null, response.heads
                .get("Content-Encoding"));
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure it could process the data correctly, if the
     * enabledGzip is true, but the HTTP servlet request is not null,and request's "Accept-Encoding" head
     * contains "gzip", the Content-Encoding should not be set.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_6() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.heads.put("Accept-Encoding", "gzip");
        ServletActionContext.setRequest(request);

        instance.setEnabledGzip(true);
        instance.execute(invocation);
        assertEquals("The header of Cache-Control should match.", "gzip", response.heads
                .get("Content-Encoding"));
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, if the action is not AbstractAction type, do nothing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_7() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        invocation.setAction(null);
        instance.execute(invocation);
        assertEquals("The action should be null.", null, invocation.getAction());
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, if the dataModel is null, actionName and data should using
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_8() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        ((JSONDataSerializer) instance.getDataSerializer()).setJsonResultTemplate("$[action]:$[result]");
        instance.execute(invocation);
        assertEquals("The value should match", ":\"\"", ((MockServletOutputStream) response.outputStream).buf
                .toString());
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure the content length is set if gzip compress is not
     * used.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_9() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        ((JSONDataSerializer) instance.getDataSerializer()).setJsonResultTemplate("$[action]:$[result]");
        instance.execute(invocation);
        assertEquals("The length should match", 3, response.length);
    }

    /**
     * <p>
     * Accuracy test for <code>execute</code>, make sure the content length is set if gzip compress is not
     * used.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExecute_Accuracy_10() throws Exception {
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        dataModel.setData("return", map);
        dataModel.setAction("name");
        action.setModel(dataModel);

        invocation.setAction(action);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
        ((JSONDataSerializer) instance.getDataSerializer()).setJsonResultTemplate("${action}:${result}");
        instance.setDataPreProcessor(null);
        instance.execute(invocation);
        assertEquals("The length should match", 19, response.length);
    }

    /**
     * <p>
     * Failure test for <code>setDataSerializer</code>, if the input argument is null,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetDataSerializer_Failure_1() {
        try {
            instance.setDataSerializer(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setDataSerializer</code>, make sure it could set the dataSerializer correctly.
     * </p>
     */
    public void testSetDataSerializer_Accuracy_1() {
        AJAXDataSerializer dataSerializer = new JSONDataSerializer();
        instance.setDataSerializer(dataSerializer);
        assertTrue("The dataSerializer should match", dataSerializer == instance.getDataSerializer());
    }

    /**
     * <p>
     * Accuracy test for <code>getDataSerializer</code>, make sure it could get the dataSerializer correctly.
     * </p>
     */
    public void testGetDataSerializer_Accuracy_1() {
        AJAXDataSerializer dataSerializer = new JSONDataSerializer();
        assertTrue("The dataSerializer should NOT match", dataSerializer != instance.getDataSerializer());
        instance.setDataSerializer(dataSerializer);
        assertTrue("The dataSerializer should match", dataSerializer == instance.getDataSerializer());
    }

    /**
     * <p>
     * Accuracy test for <code>setDataPreprocessor</code>, make sure it could set the dataPreProcessor
     * correctly.
     * </p>
     */
    public void testSetDataPreprocessor_Accuracy_1() {
        AJAXResultPreProcessor dataPreProcessor = new DefaultAJAXResultPreProcessor();
        instance.setDataPreProcessor(dataPreProcessor);
        assertTrue("The dataPreProcessor should match", dataPreProcessor == instance.getDataPreprocessor());
        instance.setDataPreProcessor(null);
        assertNull("The dataPreProcessor should be null", instance.getDataPreprocessor());
    }

    /**
     * <p>
     * Accuracy test for <code>getDataPreprocessor</code>, make sure it could get the dataPreProcessor
     * correctly.
     * </p>
     */
    public void testGetDataPreprocessor_Accuracy_1() {
        AJAXResultPreProcessor dataPreProcessor = new DefaultAJAXResultPreProcessor();
        assertTrue("The dataSerializer should NOT match", dataPreProcessor != instance.getDataPreprocessor());
        instance.setDataPreProcessor(dataPreProcessor);
        assertTrue("The dataPreProcessor should match", dataPreProcessor == instance.getDataPreprocessor());
        instance.setDataPreProcessor(null);
        assertNull("The dataPreProcessor should be null", instance.getDataPreprocessor());
    }

    /**
     * <p>
     * Accuracy test for <code>setDataPostProcessor</code>, make sure it could set the dataPostProcessor
     * correctly.
     * </p>
     */
    public void testSetDataPostProcessor_Accuracy_1() {
        AJAXResultPostProcessor dataPostProcessor = new MockAJAXResultPostProcessor();
        instance.setDataPostProcessor(dataPostProcessor);
        assertTrue("The dataPostProcessor should match", dataPostProcessor == instance.getDataPostProcessor());
        instance.setDataPostProcessor(null);
        assertNull("The dataPostProcessor should be null", instance.getDataPostProcessor());
    }

    /**
     * <p>
     * Accuracy test for <code>getDataPostProcessor</code>, make sure it could get the dataPostProcessor
     * correctly.
     * </p>
     */
    public void testGetDataPostProcessor_Accuracy_1() {
        assertNull("The dataPostProcessor should be null", instance.getDataPostProcessor());
        AJAXResultPostProcessor dataPostProcessor = new MockAJAXResultPostProcessor();
        instance.setDataPostProcessor(dataPostProcessor);
        assertTrue("The dataPostProcessor should match", dataPostProcessor == instance.getDataPostProcessor());
        instance.setDataPostProcessor(null);
        assertNull("The dataPostProcessor should be null", instance.getDataPostProcessor());
    }

    /**
     * <p>
     * Failure test for <code>setContentType</code>, if the input argument is null, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testSetContentType_Failure_1() {
        try {
            instance.setContentType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setContentType</code>, if the input argument is empty, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testSetContentType_Failure_2() {
        try {
            instance.setContentType("\n\t   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setContentType</code>, make sure it could set the contentType correctly.
     * </p>
     */
    public void testSetContentType_Accuracy_1() {
        instance.setContentType("content type");
        assertEquals("The content type should match", "content type", instance.getContentType());
    }

    /**
     * <p>
     * Accuracy test for <code>getContentType</code>, make sure it could set the contentType correctly.
     * </p>
     */
    public void testGetContentType_Accuracy_1() {
        assertEquals("The content type should match", "application/json", instance.getContentType());
        instance.setContentType("content type");
        assertEquals("The content type should match", "content type", instance.getContentType());
    }

    /**
     * <p>
     * Accuracy test for <code>setNoCache</code>, make sure it could set the noCache correctly.
     * </p>
     */
    public void testSetNoCache_Accuracy_1() {
        instance.setNoCache(true);
        assertEquals("The noCache should match", true, instance.isNoCache());
        instance.setNoCache(false);
        assertEquals("The noCache should match", false, instance.isNoCache());
    }

    /**
     * <p>
     * Accuracy test for <code>isNoCache</code>, make sure it could get the noCache correctly.
     * </p>
     */
    public void testIsNoCache_Accuracy_1() {
        instance.setNoCache(true);
        assertEquals("The noCache should match", true, instance.isNoCache());
        instance.setNoCache(false);
        assertEquals("The noCache should match", false, instance.isNoCache());
    }

    /**
     * <p>
     * Accuracy test for <code>setEnabledGzip</code>, make sure it could set the enabledGzip correctly.
     * </p>
     */
    public void testSetEnabledGzip_Accuracy_1() {
        instance.setEnabledGzip(true);
        assertEquals("The enabledGzip should match", true, instance.isEnabledGzip());
        instance.setEnabledGzip(false);
        assertEquals("The enabledGzip should match", false, instance.isEnabledGzip());
    }

    /**
     * <p>
     * Accuracy test for <code>isEnabledGzip</code>, make sure it could get the enabledGzip correctly.
     * </p>
     */
    public void testIsEnabledGzip_Accuracy_1() {
        instance.setEnabledGzip(true);
        assertEquals("The enabledGzip should match", true, instance.isEnabledGzip());
        instance.setEnabledGzip(false);
        assertEquals("The enabledGzip should match", false, instance.isEnabledGzip());
    }

    /**
     * <p>
     * Failure test for <code>setCharset</code>, if the charset is null, IllegalArgumentException should be
     * thrown.
     * </p>
     */
    public void testSetCharset_Failure_1() {
        try {
            instance.setCharset(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setCharset</code>, if the charset is invalid, IllegalArgumentException should be
     * thrown.
     * </p>
     */
    public void testSetCharset_Failure_2() {
        try {
            instance.setCharset("8a123214  12312...****183241");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for <code>setCharset</code>, if the charset is not supported, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testSetCharset_Failure_3() {
        try {
            instance.setCharset("UTF-123");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setCharset</code>, if the charset is supported, it should set correctly.
     * </p>
     */
    public void testSetCharset_Accuracy_1() {
        instance.setCharset("UTF-16");
        assertEquals("The char set should match.", "UTF-16", instance.getCharset());
    }

    /**
     * <p>
     * Accuracy test for <code>getCharset</code>, make sure it could get the correct charset.
     * </p>
     */
    public void testGetCharset_Accuracy_1() {
        assertEquals("The char set should match.", "UTF-8", instance.getCharset());
        instance.setCharset("UTF-16");
        assertEquals("The char set should match.", "UTF-16", instance.getCharset());
    }

    /**
     * <p>
     * The mock class for test.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class MockAJAXResultPostProcessor implements AJAXResultPostProcessor {
        /**
         * <p>
         * A flag indicate if throw an exception.
         * </p>
         */
        private boolean exception = false;

        /**
         * <p>
         * Post-process the given object. Add "post processed" at the end of the former string.
         * </p>
         *
         * @param data
         *            the data to be post-processed
         * @return The post-processed string.
         *
         * @throws IllegalArgumentException
         *             If given data is null.
         * @throws AJAXDataPostProcessingException
         *             if there were issues with post-processing
         */
        public String postProcessData(String data) throws AJAXDataPostProcessingException {
            if (this.exception) {
                throw new AJAXDataPostProcessingException("error");
            }
            return data + "post processed";
        }

        /**
         * <p>
         * Set the exception flag.
         * </p>
         *
         * @param exception
         *            the value to set.
         */
        public void setException(boolean exception) {
            this.exception = exception;
        }

    }
}
