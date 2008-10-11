/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.lookup.stresstests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import com.topcoder.clients.bridge.lookup.ClientProjectLookupServiceBridgeServlet;

/**
 * <p>
 * Stress Tests for Client Project Lookup JavaScript Bridge.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class ClientProjectLookupServiceBridgeServletStressTest extends TestCase {

    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long TOTAL_RUNS = 1000;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * A HttpServlet instance for testing.
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
     * <p>The stress test for creating client status.</p>
     * @throws Exception to JUnit
     */
    public void testDoPost() throws Exception {
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
            HttpServletRequest request = createMock(HttpServletRequest.class);
            HttpServletResponse response = createMock(HttpServletResponse.class);
            String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                    + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                    + "\"name\":\"world\", \"deleted\":true}";
            expect(request.getMethod()).andReturn("POST");
            expect(request.getParameter("service")).andReturn("clientStatus");
            expect(request.getParameter("method")).andReturn("createClientStatus");
            expect(request.getParameter("clientStatus")).andReturn(json);

            expect(response.getWriter()).andReturn(pw);

            response.setContentType("text/json");

            replay(request);
            replay(response);
            impl.service(request, response);
        }

        this.printResult("creating the client status via HTTP Post", TOTAL_RUNS);
    }

    /**
     * <p>The stress test for updating project status.</p>
     * @throws Exception to JUnit
     */
    public void testDoGet() throws Exception {
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
            HttpServletRequest request = createMock(HttpServletRequest.class);
            HttpServletResponse response = createMock(HttpServletResponse.class);

            String json = "{\"description\":\"test\", \"id\":123, \"createUsername\":\"ivern\","
                    + "\"createDate\":\"1222483449970\", \"modifyUsername\":\"ketty\",\"modifyDate\":\"1222483449970\","
                    + "\"name\":\"world\", \"deleted\":true}";
            expect(request.getMethod()).andReturn("GET");
            expect(request.getParameter("service")).andReturn("projectStatus");
            expect(request.getParameter("method")).andReturn("updateProjectStatus");
            expect(request.getParameter("projectStatus")).andReturn(json);

            expect(response.getWriter()).andReturn(pw);

            response.setContentType("text/json");

            replay(request);
            replay(response);
            impl.service(request, response);
        }

        this.printResult("updating the project status via HTTP Post", TOTAL_RUNS);
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }

}
