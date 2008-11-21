/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.management;

import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.clients.bridge.management.ClientProjectManagementAjaxBridgeServlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Failure test cases for class ClientProjectManagementAjaxBridgeServlet.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientProjectManagementAjaxBridgeServletFailureTest extends TestCase {

    /**
     * A ClientProjectManagementAjaxBridgeServlet instance for testing.
     */
    private ClientProjectManagementAjaxBridgeServlet impl;

    /**
     * A ServletConfig instance for testing.
     */
    private ServletConfig config;

    /**
     * A PrintWriter instance for testing.
     */
    private PrintWriter pw = new PrintWriter(System.out, true);

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(ClientProjectManagementAjaxBridgeServletFailureTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void setUp() throws Exception {
        impl = new ClientProjectManagementAjaxBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("factoryNamespace");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("projectServiceClientKey")).andReturn("projectServiceClientOBKey");
        expect(config.getInitParameter("clientServiceClientKey")).andReturn("clientServiceClientOBKey");
        expect(config.getInitParameter("companyServiceClientKey")).andReturn("companyServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        impl.init(config);
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
     * Failure test for the <code>doPost()</code> method with invalid method.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidCompanyMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":\"123\"}";
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("invalid");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with invalid method.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidProjectMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":\"123\"}";
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("invalid");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);
    }

    /**
     * <p>
     * Failure test for the <code>doPost()</code> method with invalid service.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithInvalidService() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn("invalid");
        expect(request.getParameter("method")).andReturn("createCompany");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);
    }
}
