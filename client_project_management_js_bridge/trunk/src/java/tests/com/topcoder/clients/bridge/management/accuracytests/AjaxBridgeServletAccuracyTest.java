/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.management.accuracytests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import com.topcoder.clients.bridge.management.ClientProjectManagementAjaxBridgeServlet;

/**
 * <p>
 * Accuracy test case for the Ajax Bridge Servlet.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class AjaxBridgeServletAccuracyTest extends TestCase {

    /**
     * A ClientProjectManagementAjaxBridgeServlet instance for testing.
     */
    private HttpServlet impl;

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
     * Accuracy test for creating company.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateCompany_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("createCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the company must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for creating company.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateCompany_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for deleting company.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDeleteCompany_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("createCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the company must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for deleting company.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDeleteCompany_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for updating company.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateCompany_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("CompanyService");
        expect(request.getParameter("method")).andReturn("createCompany");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the company must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for updating company.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateCompany_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"passcode\":\"666\", \"id\":\"123\", \"createUsername\":\"ivern\","
                + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                + "\"name\":\"world\", \"deleted\":true}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("company")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for creating project.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateProject_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("createProject");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the project must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for creating project.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateProject_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for updating project.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateProject_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("updateProject");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the project must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for updating project.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateProject_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for deleting project.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDeleteProject_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("deleteProject");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the project must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for deleting project.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDeleteProject_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("project")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for setting project status.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetProjectStatus_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        String status = "{\"description\":\"test\"}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ProjectService");
        expect(request.getParameter("method")).andReturn("setProjectStatus");
        expect(request.getParameter("project")).andReturn(json);
        expect(request.getParameter("status")).andReturn(status);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the project status must be set", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for setting project status.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetProjectStatus_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"active\":true,  \"salesTax\":0.17,"
                + " \"poBoxNumber\":\"123\", \"paymentTermsId\":20, \"description\":\"test\","
                + " \"status\":{\"description\":\"test2\"}, \"client\":{\"codeName\":\"uuu\"},"
                + " \"childProjects\":[{\"poBoxNumber\":\"777\", \"parentProjectId\":\"555\"},"
                + " {\"poBoxNumber\":\"888\", \"parentProjectId\":\"666\"}],"
                + " \"parentProjectId\":\"789\", \"id\":\"345\" }";
        String status = "{\"description\":\"test\"}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("project")).andReturn(json);
        expect(request.getParameter("status")).andReturn(status);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for creating client.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateClient_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("createClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the client must be created", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for creating client.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateClient_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for updating project.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateClient_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("updateClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the client must be updated", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for updating client.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateClient_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for deleting client.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDeleteClient_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("deleteClient");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the client must be deleted", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for deleting client.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDeleteClient_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("client")).andReturn(json);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for setting client status.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetClientStatus_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        String status = "{\"description\":\"test\"}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("setClientStatus");
        expect(request.getParameter("client")).andReturn(json);
        expect(request.getParameter("status")).andReturn(status);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the client status must be set", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for setting client status.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetClientStatus_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        String status = "{\"description\":\"test\"}";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("client")).andReturn(json);
        expect(request.getParameter("status")).andReturn(status);

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

    /**
     * <p>
     * Accuracy test for setting client code name.
     * With full parameters set.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetClientCodeName_Accuracy1() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("service")).andReturn("ClientService");
        expect(request.getParameter("method")).andReturn("setClientCodeName");
        expect(request.getParameter("client")).andReturn(json);
        expect(request.getParameter("name")).andReturn("codeName");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("the client code name must be set", output.toString().indexOf("json") > 0);
    }

    /**
     * <p>
     * Accuracy test for setting client code name.
     * With no service set, the expected return json string is {"success" : false, "error" : $error-message }
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSetClientCodeName_Accuracy2() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        String json = "{\"company\":{\"passcode\":\"666\"}, \"paymentTermsId\":20,"
                + " \"status\":{\"description\":\"test\"}, \"salesTax\":0.17, \"startDate\":\"1163531522089\","
                + " \"endDate\":\"1163531522089\", \"codeName\":\"uuu\", \"id\":\"345\" }";
        expect(request.getMethod()).andReturn("POST");
        expect(request.getParameter("client")).andReturn(json);
        expect(request.getParameter("name")).andReturn("codeName");

        expect(response.getWriter()).andReturn(pw);

        response.setContentType("text/json");

        replay(request, response);

        impl.service(request, response);

        verify(request, response);

        assertTrue("error must occur", output.toString().indexOf("error") > 0);
    }

}
