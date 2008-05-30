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
     * The server port.
     */
    private static final int PORT = 2100;

    /**
     * The test file.
     */
    private static final String APATH = "test_files/stresstests/a.txt";

    /**
     * The test file.
     */
    private static final String BPATH = "test_files/stresstests/b.txt";

    /**
     * The test file.
     */
    private static final String CPATH = "test_files/stresstests/c.txt";

    /**
     * <p>
     * The instance of SocketDocumentContentManager for testing.
     * </p>
     */
    private SocketDocumentContentManager manager;

    /**
     * Represents the file content.
     */
    private byte[] content;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        manager = new SocketDocumentContentManager("127.0.0.1", PORT);
        content = new byte[1024];
        for (int i = 0; i < content.length; i++) {
            content[i] = 'a';
        }
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        manager = null;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     * @throws Exception
     *             to JUnit
     */
    public void testSaveDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(PORT, 0);
        server.start();
        Thread.sleep(2000);

        try {
            this.beginTest();

            manager.saveDocumentContent(APATH, content);

            this.endTest("SocketDocumentContentManager's saveDocumentContent(String, byte[])", 10000);
        } finally {
            server.stop();
            Thread.sleep(2000);
        }
    }

    /**
     * <p>
     * Tests <code>getDocumentContent(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(PORT, 0);
        server.start();
        Thread.sleep(2000);

        try {
            this.beginTest();

            byte[] result = manager.getDocumentContent(BPATH);

            assertEquals("the file content size.", 1024, result.length);
            this.endTest("SocketDocumentContentManager's getDocumentContent(String)", 10000);
        } finally {
            server.stop();
            Thread.sleep(2000);
        }
    }

    /**
     * <p>
     * Tests <code>existDocumentContent(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExistDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(PORT, 0);
        server.start();
        Thread.sleep(1000);

        try {
            this.beginTest();

            manager.existDocumentContent(CPATH);
            this.endTest("SocketDocumentContentManager's existDocumentContent(String)", 10000);
        } finally {
            server.stop();
            Thread.sleep(2000);
        }
    }
}
