/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import java.net.URLEncoder;
import java.util.Calendar;

import com.orpheus.plugin.firefox.FirefoxExtensionHelperAccuracyMock;
import com.orpheus.plugin.firefox.OrpheusServer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the functionality of <code>OrpheusServer</code> class. Some methods should not be tested, such as
 * <code>run()</code>. A mock of <code>FirefoxExtensionHelper</code> is used in the tests here.
 * 
 * @author visualage
 * @version 1.0
 */
public class OrpheusServerTestCase extends TestCase {
    /**
     * Represents the <code>FirefoxExtensionHelper</code> instance used in tests.
     */
    private FirefoxExtensionHelperAccuracyMock helper;

    /**
     * Represents the <code>OrpheusServer</code> instance used in tests.
     */
    private OrpheusServer server;

    /**
     * Represents the simple HTTP server used in tests.
     */
    private SimpleHttpServer httpServer;

    /**
     * Aggragates all tests in this class.
     * 
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(OrpheusServerTestCase.class);
    }

    /**
     * Sets up the test environment. The test instances are created.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    protected void setUp() throws Exception {
        helper = new FirefoxExtensionHelperAccuracyMock();
        httpServer = new SimpleHttpServer(8080);
        server = new OrpheusServer("http://localhost:8080/server", "time", null, 1, helper);
    }

    /**
     * Cleans up the test environment. The test instances are disposed.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    protected void tearDown() throws Exception {
        server.stopThread();
        httpServer.reset();
        server = null;
        helper = null;
        httpServer = null;
    }

    /**
     * Tests the accuracy of <code>OrpheusServer(String, String, Calendar, int, FirefoxExtensionHelper)</code> when
     * the time stamp is <code>null</code>. All arguments should be set to the private fields.
     */
    public void testOrpheusServerStringStringCalendarIntFirefoxExtensionHelperNoTimestampAccuracy() {
        server = new OrpheusServer("http://localhost:8080/server", "time", null, 1, helper);

        // Verify
        assertEquals("The URL should be set in.", "http://localhost:8080/server", TestHelper.getPrivateField(
            OrpheusServer.class, server, "url"));
        assertEquals("The time parameter should be set as 'time'.", "time", TestHelper.getPrivateField(
            OrpheusServer.class, server, "timestampParameter"));
        assertNull("The time stamp should be set as null.", TestHelper.getPrivateField(OrpheusServer.class, server,
            "timestamp"));
        assertEquals("The poll time should be set as 1.", new Integer(1), TestHelper.getPrivateField(
            OrpheusServer.class, server, "pollTime"));
        assertSame("The helper instance should be set in.", helper, TestHelper.getPrivateField(OrpheusServer.class,
            server, "listener"));
    }

    /**
     * Tests the accuracy of <code>OrpheusServer(String, String, Calendar, int, FirefoxExtensionHelper)</code> when
     * the time stamp is specified. All arguments should be set to the private fields.
     */
    public void testOrpheusServerStringStringCalendarIntFirefoxExtensionHelperTimestampAccuracy() {
        Calendar calendar = Calendar.getInstance();

        server = new OrpheusServer("http://localhost:8080/server", "time", calendar, 1, helper);

        // Verify
        assertEquals("The URL should be set in.", "http://localhost:8080/server", TestHelper.getPrivateField(
            OrpheusServer.class, server, "url"));
        assertEquals("The time parameter should be set as 'time'.", "time", TestHelper.getPrivateField(
            OrpheusServer.class, server, "timestampParameter"));
        assertEquals("The time stamp should be set in.", calendar, TestHelper.getPrivateField(OrpheusServer.class,
            server, "timestamp"));
        assertEquals("The poll time should be set as 1.", new Integer(1), TestHelper.getPrivateField(
            OrpheusServer.class, server, "pollTime"));
        assertSame("The helper instance should be set in.", helper, TestHelper.getPrivateField(OrpheusServer.class,
            server, "listener"));
    }

    /**
     * Tests the accuracy of <code>pollServerNow()</code> when there is no time stamp parameter. The GET method should
     * be used. The URL should contain no time parameter. The new time stamp should be set. This is verified by the
     * simple HTTP server. The feed should be processed by the helper.
     */
    public void testPollServerNowNoTimestampAccuracy() {
        // Start the http server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\nSome response\n");
        httpServer.start();

        // Poll the server without timestamp
        server.pollServerNow();

        // Stop the server
        httpServer.stopServer();

        // Verify
        assertEquals("There should be only one request.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The request should contain the correct URL.", request.indexOf("/server") >= 0);
        assertTrue("The request should use GET.", request.indexOf("GET ") >= 0);
        assertTrue("The request should contain no time parameter.", request.indexOf("time=") < 0);
        assertNotNull("The time stamp should be set.", TestHelper.getPrivateField(OrpheusServer.class, server,
            "timestamp"));
        assertNotNull("The feed should be processed by the helper.", helper.getFeed());
    }

    /**
     * Tests the accuracy of <code>pollServerNow()</code> when there is time stamp parameter and the time parameter
     * name does not have to be escaped. The GET method should be used. The URL should contain the time parameter. The
     * new time stamp should be set. This is verified by the simple HTTP server. The feed should be processed by the
     * helper.
     */
    public void testPollServerNowTimestampUnescapeAccuracy() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2003, 1, 1);
        server = new OrpheusServer("http://localhost:8080/server", "time", calendar, 1, helper);
        calendar = (Calendar) calendar.clone();

        // Start the http server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\nSome response\n");
        httpServer.start();

        // Poll the server with timestamp
        server.pollServerNow();

        // Stop the server
        httpServer.stopServer();

        // Verify
        assertEquals("There should be only one request.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The request should contain the correct URL.", request.indexOf("/server?") >= 0);
        assertTrue("The request should use GET.", request.indexOf("GET ") >= 0);
        assertTrue("The request should contain the time parameter.", request.indexOf("time=") >= 0);
        assertFalse("The time stamp should be set to the new value.", TestHelper.getPrivateField(OrpheusServer.class,
            server, "timestamp").equals(calendar));
        assertNotNull("The feed should be processed by the helper.", helper.getFeed());
    }

    /**
     * Tests the accuracy of <code>pollServerNow()</code> when there is time stamp parameter and the time parameter
     * name has to be escaped. The GET method should be used. The URL should contain the time parameter. The new time
     * stamp should be set. This is verified by the simple HTTP server. The feed should be processed by the helper.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testPollServerNowTimestampEscapeAccuracy() throws Exception {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2003, 1, 1);
        server = new OrpheusServer("http://localhost:8080/server", ";=\u1234", calendar, 1, helper);
        calendar = (Calendar) calendar.clone();

        // Start the http server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\nSome response\n");
        httpServer.start();

        // Poll the server with timestamp
        server.pollServerNow();

        // Stop the server
        httpServer.stopServer();

        // Verify
        assertEquals("There should be only one request.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The request should contain the correct URL.", request.indexOf("/server?") >= 0);
        assertTrue("The request should use GET.", request.indexOf("GET ") >= 0);
        assertTrue("The request should contain the time parameter.", request.indexOf(URLEncoder.encode(";=\u1234",
            "UTF-8")
            + "=") >= 0);
        assertFalse("The time stamp should be set to the new value.", TestHelper.getPrivateField(OrpheusServer.class,
            server, "timestamp").equals(calendar));
        assertNotNull("The feed should be processed by the helper.", helper.getFeed());
    }

    /**
     * Tests the accuracy of <code>setPollTime(int)</code>. The poll time should be set. This is verified by the
     * property getter. It is also an accuracy test for the property getter.
     */
    public void testSetPollTimeIntAccuracy() {
        server.setPollTime(2);

        // Verify
        assertEquals("The poll time should be set.", 2, server.getPollTime());
    }

    /**
     * Tests the accuracy of <code>queryDomain(String, String)</code> when the domain and its parameter do not have to
     * be escaped. The request should be GET request. The URL should contain the domain and its parameter. This is
     * verified by the simple HTTP server.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testQueryDomainStringStringUnescapeAccuracy() throws Exception {
        // Start the http server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\n12.345");
        httpServer.start();

        // Query
        double value = server.queryDomain("domain", "www.topcoder.com");

        // Stop the server
        httpServer.stopServer();

        // Verify
        assertEquals("There should be only one request.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The request should contain the correct URL.", request.indexOf("/server?") >= 0);
        assertTrue("The request should use GET.", request.indexOf("GET ") >= 0);
        assertTrue("The request should contain the domain parameter and value.", request
            .indexOf("domain=www.topcoder.com") >= 0);
        assertEquals("The returned value should be as expected.", 12.345, value, 1e-9);
    }

    /**
     * Tests the accuracy of <code>queryDomain(String, String)</code> when the domain and its parameter do not have to
     * be escaped. The request should be GET request. The URL should contain the domain and its parameter. This is
     * verified by the simple HTTP server.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testQueryDomainStringStringEscapeAccuracy() throws Exception {
        // Start the http server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\n12.345");
        httpServer.start();

        // Query
        double value = server.queryDomain(";=\u1234", "\u1234;=");

        // Stop the server
        httpServer.stopServer();

        // Verify
        assertEquals("There should be only one request.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The request should contain the correct URL.", request.indexOf("/server?") >= 0);
        assertTrue("The request should use GET.", request.indexOf("GET ") >= 0);
        assertTrue("The request should contain the domain parameter and value.", request.indexOf(URLEncoder.encode(
            ";=\u1234", "UTF-8")
            + "=" + URLEncoder.encode("\u1234;=", "UTF-8")) >= 0);
        assertEquals("The returned value should be as expected.", 12.345, value, 1e-9);
    }

    /**
     * Tests the accuracy of <code>stopThread()</code>. The stop polling flag should be set as <code>true</code>.
     * This is verified via reflection.
     */
    public void testStopThreadAccuracy() {
        server.stopThread();

        // Verify
        assertEquals("The stop polling flag should be set.", Boolean.TRUE, TestHelper.getPrivateField(
            OrpheusServer.class, server, "stopPolling"));
    }

    /**
     * Tests the accuracy when the server is stopped and then started. The server should be started successfully, and
     * the stop polling flag should be cleared. This is verified via reflection.
     */
    public void testStartServerAfterStopAccuracy() {
        server.stopThread();

        // Start the thread
        server.start();

        try {
            // wait a while for the server to be processed.
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }

        // Verify
        assertEquals("The stop polling flag should be cleared.", Boolean.FALSE, TestHelper.getPrivateField(
            OrpheusServer.class, server, "stopPolling"));
    }
}
