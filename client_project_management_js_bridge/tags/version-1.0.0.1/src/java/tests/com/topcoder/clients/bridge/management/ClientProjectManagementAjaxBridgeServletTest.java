/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.management;

import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for class ClientProjectManagementAjaxBridgeServlet.<br>
 * All the public and protected methods are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientProjectManagementAjaxBridgeServletTest extends TestCase {

    /**
     * A ClientProjectManagementAjaxBridgeServlet instance for testing.
     */
    private ClientProjectManagementAjaxBridgeServlet impl;

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
        return new TestSuite(ClientProjectManagementAjaxBridgeServletTest.class);
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
     * Accuracy test for the <code>doPost()</code> with parameter "createCompany", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_CreateCompany_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("createCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the company must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "updateCompany", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_UpdateCompany_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("updateCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the company must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "deleteCompany", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_DeleteCompany_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("deleteCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the company must be deleted", output.toString().indexOf("json") > 0);
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

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
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
    public void testDoPost_WithNullCompanyMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":\"123\"}";
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn(null);
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
        System.out.println(output);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "createClient", expects no error
     * occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_CreateClient_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("createClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "updateClient", expects no error
     * occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_UpdateClient_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("updateClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "deleteClient", expects no error
     * occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_DeleteClient_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("deleteClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "setClientCodeName", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_SetClientCodeName_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("setClientCodeName");
        expect(request.getParameter("client")).andReturn(json);
        expect(request.getParameter("name")).andReturn("test_name");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client must be setClinetCodeName correctly", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "setClientStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_SetClientStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        String jsonStatus = "{\"description\":\"test\"}";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("setClientStatus");
        expect(request.getParameter("client")).andReturn(json);
        expect(request.getParameter("status")).andReturn(jsonStatus);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the client must be setClientStatus correctly", output.toString().indexOf("json") > 0);
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
    public void testDoPost_WithInvalidClientMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":\"123\"}";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("invalid");
        expect(request.getParameter("client")).andReturn(json);

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
     * Failure test for the <code>doPost()</code> method with invalid method.<br>
     * The return string should be have error message.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_WithNullClientMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":\"123\"}";
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn(null);
        expect(request.getParameter("client")).andReturn(json);

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
     * Accuracy test for the <code>doPost()</code> with parameter "createProject", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_CreateProject_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("createProject");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "updateProject", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_UpdateProject_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("updateProject");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "deleteProject", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_DeleteProject_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("deleteProject");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for the <code>doPost()</code> with parameter "setProjectStatus", expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoPost_SetProjectStatus_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        String jsonStatus = "{\"description\":\"test\"}";
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("setProjectStatus");
        expect(request.getParameter("project")).andReturn(json);
        expect(request.getParameter("status")).andReturn(jsonStatus);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doPost(request, response);

        verify(request);
        verify(response);

        assertTrue("the project must be setProjectStatus correctly", output.toString().indexOf("json") > 0);
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

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
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
    public void testDoPost_WithNullProjectMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{ \"id\":\"123\"}";
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn(null);
        expect(request.getParameter("project")).andReturn(json);

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
        expect(request.getParameter("method")).andReturn("createCompany");

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
    public void testDoPost_WithNullService() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("service")).andReturn(null);
        expect(request.getParameter("method")).andReturn("createCompany");

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
     * Accuracy test for the <code>doGet()</code> method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoGet_Accuracy() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("createCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request);
        replay(response);

        impl.doGet(request, response);

        verify(request);
        verify(response);

        assertTrue("the company must be created", output.toString().indexOf("json") > 0);
    }

}
