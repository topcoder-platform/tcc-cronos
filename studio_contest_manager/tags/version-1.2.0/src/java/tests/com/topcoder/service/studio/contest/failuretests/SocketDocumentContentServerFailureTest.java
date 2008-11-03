/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import java.io.IOException;
import java.net.InetSocketAddress;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;

/**
 * <p>
 * Failure tests for SocketDocumentContentServer class.
 * </p>
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class SocketDocumentContentServerFailureTest extends TestCase {
    /**
     * <p>
     * The server port to listen to.
     * </p>
     */
    private static final int PORT = 30002;

    /**
     * <p>
     * The SocketDocumentContentServer instance for testing.
     * </p>
     */
    private SocketDocumentContentServer server;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        server = new SocketDocumentContentServer(PORT, 0);
    }

    /**
     * <p>
     * Destroys the environment.
     * </p>
     * 
     * @throws InterruptedException to JUnit.
     */
    protected void tearDown() throws InterruptedException {
        server.stop();
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * 
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(SocketDocumentContentServerFailureTest.class);
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(int, int).
     * </p>
     * 
     * <p>
     * Failure cause: If the port is not in range [0, 65535], IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor1_Failure() {
        try {
            new SocketDocumentContentServer(-1, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(String, int, int).
     * </p>
     * 
     * <p>
     * Failure cause: If the port is not in range [0, 65535], IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor2_Failure1() {
        try {
            new SocketDocumentContentServer("127.0.0.1", -1, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(String, int, int).
     * </p>
     * 
     * <p>
     * Failure cause: If the address is empty string, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor2_Failure2() {
        try {
            new SocketDocumentContentServer("    ", 1000, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(int, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the port is not in range [0, 65535], IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor3_Failure1() {
        try {
            new SocketDocumentContentServer(-1, 0, "logger");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(int, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the loggerName is empty string, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor3_Failure2() {
        try {
            new SocketDocumentContentServer(1000, 0, "   ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(String, int, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the port is not in range [0, 65535], IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor4_Failure1() {
        try {
            new SocketDocumentContentServer("127.0.0.1", -1, 0, "logger");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(String, int, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the loggerName is empty string, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor4_Failure2() {
        try {
            new SocketDocumentContentServer("127.0.0.1", 1000, 0, "   ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(String, int, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the address is empty string, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor4_Failure3() {
        try {
            new SocketDocumentContentServer("   ", 1000, 0, "logger");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(SocketAddress, int).
     * </p>
     * 
     * <p>
     * Failure cause: If the endPoint is null, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor5_Failure() {
        try {
            new SocketDocumentContentServer(null, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(SocketAddress, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the endPoint is null, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor6_Failure1() {
        try {
            new SocketDocumentContentServer(null, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor SocketDocumentContentServer(SocketAddress, int, String).
     * </p>
     * 
     * <p>
     * Failure cause: If the loggerName is empty, IllegalArgumentException is thrown.
     * </p>
     */
    public void test_Ctor6_Failure2() {
        try {
            new SocketDocumentContentServer(new InetSocketAddress("127.0.0.1", 0), 0, "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for start().
     * </p>
     * 
     * <p>
     * Failure cause: If the port is already taken, IOException will be thrown.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    public void test_Start_Failure1() throws Exception {
        // Wait for previous worker thread to stop.
        Thread.sleep(1000);

        server.start();

        try {
            SocketDocumentContentServer newServer = new SocketDocumentContentServer(PORT, 0);
            newServer.start();

            fail("IOException is expected.");
        } catch (IOException e) {
            // success
        }

        server.stop();
    }
}
