/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.stresstests;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;


/**
 * <p>
 * Stress test cases for <code>SocketDocumentContentManager</code> class.
 * </p>
 *
 * @author woodatxc
 * @version 1.0
 */
public class SocketDocumentContentManagerStressTests extends BaseStressTests {
    /**
     * <p>The server port to connect to.</p>
     */
    private static int PORT = 20001;

    /**
     * <p>
     * The instance of SocketDocumentContentManager for testing.
     * </p>
     */
    private SocketDocumentContentManager manager;

    /**
     * <p>The <code>SocketDocumentContentServer</code> instance for testing.</p>
     */
    private SocketDocumentContentServer server;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // Wait the previous worker thread to stop.
        Thread.sleep(500);
        server = new SocketDocumentContentServer(++PORT, 0);

        server.start();

        // Wait the worker thread to begin.
        Thread.sleep(500);
        manager = new SocketDocumentContentManager("127.0.0.1", PORT);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        server.stop();
        manager = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SocketDocumentContentManagerStressTests.class);
    }

    /**
     * <p>
     * Tests <code>saveDocumentContent(String, byte[])</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSaveDocumentContent() throws Exception {
        this.beginTest();

        String path = "a.txt";
        byte[] contents = new byte[] {'a', 'b', 'c' };
        for (int i = 0; i < 1; i++) {
            manager.saveDocumentContent(path, contents);
        }

        this.endTest("SocketDocumentContentManager's saveDocumentContent(String, byte[])", 10000);
    }

    /**
     * <p>
     * Tests <code>getDocumentContent(String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDocumentContent() throws Exception {
        this.beginTest();

        String path = "a.txt";
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.getDocumentContent(path);
        }

        this.endTest("SocketDocumentContentManager's getDocumentContent(String)", 10000);
    }

    /**
     * <p>
     * Tests <code>existDocumentContent(String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExistDocumentContent() throws Exception {
        this.beginTest();

        String path = "a.txt";
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.existDocumentContent(path);
        }

        this.endTest("SocketDocumentContentManager's existDocumentContent(String)", 10000);
    }
}
