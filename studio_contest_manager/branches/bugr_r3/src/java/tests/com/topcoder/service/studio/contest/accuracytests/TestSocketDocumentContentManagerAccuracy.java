/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;


/**
 * Accuracy test cases for class <code>SocketDocumentContentManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestSocketDocumentContentManagerAccuracy extends TestCase {
    /** Represents the SocketDocumentContentManager instance for testing. */
    private SocketDocumentContentManager manager = null;

    private int port = 50000;
    /**
     * set up.
     */
    public void setUp() {
        manager = new SocketDocumentContentManager("127.0.0.1", ++port);
    }

    /**
     * Test ctor.
     */
    public void testSocketDocumentContentManagerStringInt() {
        assertNotNull(manager);
    }

    /**
     * Test method <code>void saveDocumentContent(String path, byte[] documentContent) </code>.
     *
     * @throws Exception to junit
     */
    public void testSaveDocumentContent_1() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(port, 0);
        server.start();

        try {
            Thread.sleep(500);

            manager.saveDocumentContent("test_files/acc_files/temp/a.txt", "abc".getBytes());

            // valide the result.
            File file = new File("test_files/acc_files/temp/a.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            StringBuilder builder = new StringBuilder();

            String str = "";

            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }

            assertEquals("Equal to 'abc'", "abc", builder.toString());
        } finally {
            server.stop();

        }
    }

    /**
     * Test method <code>void saveDocumentContent(String path, byte[] documentContent) </code>.
     *
     * @throws Exception to junit
     */
    public void testSaveDocumentContent_2() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(port, 0);
        server.start();

        try {
            Thread.sleep(500);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            for (int i = 0; i < 1000; i++) {
                output.write("abc".getBytes());
            }

            manager.saveDocumentContent("test_files/acc_files/temp/b.txt", output.toByteArray());
            manager.existDocumentContent("test_files/acc_files/temp/b.txt");
        } finally {
            server.stop();

        }
    }

    /**
     * Test method <code> byte[] getDocumentContent(String path) </code>.
     *
     * @throws Exception to junit
     */
    public void testGetDocumentContent_1() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(port, 0);
        server.start();

        try {
            Thread.sleep(500);

            byte[] ret = manager.getDocumentContent("test_files/acc_files/temp/b.txt");

            assertEquals("The size must be equal", 1000 * ("abc".getBytes().length), ret.length);
        } finally {
            server.stop();

        }
    }

    /**
     * Test method <code> byte[] getDocumentContent(String path) </code>.
     *
     * @throws Exception to junit
     */
    public void testGetDocumentContent_2() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(port, 0);
        server.start();

        try {
            Thread.sleep(500);

            byte[] ret = manager.getDocumentContent("test_files/acc_files/temp/a.txt");

            assertEquals("The size must be equal", "abc".getBytes().length, ret.length);
        } finally {
            server.stop();

        }
    }

    /**
     * Test method <code>boolean existDocumentContent(String path) </code>.
     *
     * @throws Exception to junit
     */
    public void testExistDocumentContent() throws Exception {
        SocketDocumentContentServer server = new SocketDocumentContentServer(port, 0);
        server.start();

        try {
            Thread.sleep(500);

            boolean ret = manager.existDocumentContent("test_files/acc_files/temp/a.txt");

            assertTrue(ret);
        } finally {
            server.stop();

        }
    }
}
