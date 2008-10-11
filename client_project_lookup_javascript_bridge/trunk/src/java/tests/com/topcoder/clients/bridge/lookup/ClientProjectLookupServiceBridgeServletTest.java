/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.lookup;

import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for class ClientProjectLookupServiceBridgeServletTest.<br>
 * All the public and protected methods are tested.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ClientProjectLookupServiceBridgeServletTest extends TestCase {

    /**
     * A ClientProjectLookupServiceBridgeServletTest instance for testing.
     */
    private ClientProjectLookupServiceBridgeServlet impl;

    /**
     * A ServletConfig instance for testing.
     */
    private ServletConfig config;

    /**
     * A StringWriter instance for testing.
     */
    private StringWriter output = new StringWriter();

    /**
     * A PrintWriter instance for testing.
     */
    private PrintWriter pw = new PrintWriter(output, true);

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(ClientProjectLookupServiceBridgeServletTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void setUp() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        impl.init(config);
    }

    /**
     * Clear the test environment.
     */
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ClientProjectManagementAjaxBridgeServlet()</code>,
     * expects the instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the ClientProjectManagementAjaxBridgeServlet instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the <code>init()</code> method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_Accuracy() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        impl.init(config);

        verify(config);
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithNull() throws Exception {
        try {
            impl.init(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with errot object factory namespace.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithErrorObjectFactoryNamespace() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("NotExist");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with not exist object factory namespace.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithNotExistObjectFactoryNamespace() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn(null);
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with ClassCastException occurs.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithClassCastExceptionOccurs() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("invalid1");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with object factory config error.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithErrorObjectFactoryConfig() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("invalid2");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with object factory config error.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithErrorObjectFactoryConfig2() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("invalid3");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with object factory config error.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithErrorObjectFactoryConfig3() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("invalid4");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("NotExist");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with not exist key in object factory.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInit_WithKeyNotExistInObjectFactory() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("NotExist");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        try {
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "createProjectStatus", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_CreateProjectStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("projectStatus");
        expect(request.getParameter("method")).andReturn("createProjectStatus");
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project status must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "updateProjectStatus", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_UpdateProjectStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("projectStatus");
        expect(request.getParameter("method")).andReturn("updateProjectStatus");
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project status must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "deleteProjectStatus", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_DeleteProjectStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("projectStatus");
        expect(request.getParameter("method")).andReturn("deleteProjectStatus");
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project status must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with invalid method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidProjectStatusMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":123}";
        expect(request.getParameter("service")).andReturn("projectStatus");
        expect(request.getParameter("method")).andReturn("invalid");
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with not exist method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithNullProjectStatusMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":123}";
        expect(request.getParameter("service")).andReturn("projectStatus");
        expect(request.getParameter("method")).andReturn(null);
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "createClientStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_CreateClientStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("clientStatus");
        expect(request.getParameter("method")).andReturn("createClientStatus");
        expect(request.getParameter("clientStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client status must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "updateClientStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_UpdateClientStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("clientStatus");
        expect(request.getParameter("method")).andReturn("updateClientStatus");
        expect(request.getParameter("clientStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client status must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "deleteClientStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_DeleteClientStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("clientStatus");
        expect(request.getParameter("method")).andReturn("deleteClientStatus");
        expect(request.getParameter("clientStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client status must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with invalid method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidClientStatusMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":123}";
        expect(request.getParameter("service")).andReturn("clientStatus");
        expect(request.getParameter("method")).andReturn("invalid");
        expect(request.getParameter("clientStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with not exist method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithNullClientStatusMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":123}";
        expect(request.getParameter("service")).andReturn("clientStatus");
        expect(request.getParameter("method")).andReturn(null);
        expect(request.getParameter("clientStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveClient", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveClient_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveClient");
        expect(request.getParameter("id")).andReturn("3");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveAllClients", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveAllClients_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveAllClients");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "searchClientsByName", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_SearchClientsByName_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("searchClientsByName");
        expect(request.getParameter("name")).andReturn("topcoder");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getClientStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetClientStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getClientStatus");
        expect(request.getParameter("id")).andReturn("4");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client status should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getAllClientStatuses", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetAllClientStatuses_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getAllClientStatuses");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client status should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getClientsForStatus", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetClientsForStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getClientsForStatus");
        expect(request.getParameter("clientStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the clients should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveProject", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveProject_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveProject");
        expect(request.getParameter("id")).andReturn("123");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveProjectsForClient",
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveProjectsForClient_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"clientStatus\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":345 }";
        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveProjectsForClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveAllProjects", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveAllProjects_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveAllProjects");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the projects should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "searchProjectsByName", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_SearchProjectsByName_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("searchProjectsByName");
        expect(request.getParameter("name")).andReturn("topcoder");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the projects should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getProjectStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetProjectStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getProjectStatus");
        expect(request.getParameter("id")).andReturn("5");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project status should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getAllProjectStatuses", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetAllProjectStatuses_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getAllProjectStatuses");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project statuses should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getProjectsForStatus", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetProjectsForStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getProjectsForStatus");
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the projects should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveCompany", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveCompany_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveCompany");
        expect(request.getParameter("id")).andReturn("5");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the company should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "retrieveAllCompanies", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_RetrieveAllCompanies_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("retrieveAllCompanies");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the companies should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "searchCompaniesByName", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_SearchCompaniesByName_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("searchCompaniesByName");
        expect(request.getParameter("name")).andReturn("topcoder");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the companies should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getClientsForCompany", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetClientsForCompany_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getClientsForCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the clients should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "getProjectsForCompany", expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_GetProjectsForCompany_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("getProjectsForCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the projects should be retrieved", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with invalid method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidLookupMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn("invalid");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with not exist method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithNullLookupMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("lookup");
        expect(request.getParameter("method")).andReturn(null);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with invalid service.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidService() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("invalid");
        expect(request.getParameter("method")).andReturn("getProjectsForCompany");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with null service.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithNullService() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn(null);
        expect(request.getParameter("method")).andReturn("getProjectsForCompany");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doGet()</code> with parameter "createProjectStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoGet_Accuracy() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn(null);
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("projectStatus");
        expect(request.getParameter("method")).andReturn("createProjectStatus");
        expect(request.getParameter("projectStatus")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(config, request, response);

        impl.init(config);
        impl.doGet(request, response);

        verify(config, request, response);

        assertTrue("the project status must be created", output.toString().indexOf("json") > 0);
    }

}
