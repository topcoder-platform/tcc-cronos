/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.documentcontentservers;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;


/**
 * <p>
 * Unit tests for <code>SocketDocumentContentServer</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SocketDocumentContentServerUnitTest extends TestCase {
    /**
     * <p>The server port to listen to.</p>
     */
    private static int PORT = 31002;

    /**
     * <p>The <code>SocketDocumentContentServer</code> instance for testing.</p>
     */
    private SocketDocumentContentServer server;

    /**
     * <p>Sets up the environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        //FIX: let's increment it instead of waiting
        server = new SocketDocumentContentServer(++PORT, 0);
    }

    /**
     * <p>Destroys the environment.</p>
     * @throws InterruptedException to JUnit.
     */
    protected void tearDown() throws InterruptedException {
        server.stop();
    }

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(SocketDocumentContentServerUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentServer(int, int)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>SocketDocumentContentServer</code> can be created successfully.
     * </p>
     */
    public void testCtor1() {
        server = new SocketDocumentContentServer(0, -1000);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(65535, 1000);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(1000, 0);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(int, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the port is not in range [0, 65535], <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor1_Failure() {
        try {
            new SocketDocumentContentServer(-1, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentServer(String, int, int)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>SocketDocumentContentServer</code> can be created successfully.
     * </p>
     */
    public void testCtor2() {
        server = new SocketDocumentContentServer("127.0.0.1", 0, -1000);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer("127.0.0.1", 65535, 1000);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer("127.0.0.1", 1000, 0);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        // Verify that the address can be null.
        server = new SocketDocumentContentServer(null, 1000, 0);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(String, int, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the port is not in range [0, 65535], <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor2_Failure1() {
        try {
            new SocketDocumentContentServer("127.0.0.1", -1, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(String, int, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the address is empty string, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor2_Failure2() {
        try {
            new SocketDocumentContentServer("    ", 1000, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentServer(int, int, String)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>SocketDocumentContentServer</code> can be created successfully.
     * </p>
     */
    public void testCtor3() {
        server = new SocketDocumentContentServer(0, -1000, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(65535, 1000, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(1000, 0, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        // Verify that the loggerName can be null.
        server = new SocketDocumentContentServer(1000, 0, null);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(int, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the port is not in range [0, 65535], <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor3_Failure1() {
        try {
            new SocketDocumentContentServer(-1, 0, "logger");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(int, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the loggerName is empty string, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor3_Failure2() {
        try {
            new SocketDocumentContentServer(1000, 0, "   ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentServer(String, int, int, String)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>SocketDocumentContentServer</code> can be created successfully.
     * </p>
     */
    public void testCtor4() {
        server = new SocketDocumentContentServer("127.0.0.1", 0, -1000, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer("127.0.0.1", 65535, 1000,
                "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer("127.0.0.1", 1000, 0, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        // Verify that the address can be null.
        server = new SocketDocumentContentServer(null, 1000, 0, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        // Verify that the loggerName can be null.
        server = new SocketDocumentContentServer("127.0.0.1", 1000, 0, null);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(String, int, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the port is not in range [0, 65535], <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor4_Failure1() {
        try {
            new SocketDocumentContentServer("127.0.0.1", -1, 0, "logger");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(String, int, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the loggerName is empty string, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor4_Failure2() {
        try {
            new SocketDocumentContentServer("127.0.0.1", 1000, 0, "   ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(String, int, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the address is empty string, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     */
    public void testCtor4_Failure3() {
        try {
            new SocketDocumentContentServer("   ", 1000, 0, "logger");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentServer(SocketAddress, int)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>SocketDocumentContentServer</code> can be created successfully.
     * </p>
     */
    public void testCtor5() {
        SocketAddress endPoint = new InetSocketAddress("127.0.0.1", 0);

        server = new SocketDocumentContentServer(endPoint, -1000);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(endPoint, 1000);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(endPoint, 0);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(SocketAddress, int)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the endPoint is null, <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtor5_Failure() {
        try {
            new SocketDocumentContentServer(null, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>SocketDocumentContentServer(SocketAddress, int, String)</code>.
     * </p>
     *
     * <p>
     * Verify that the <code>SocketDocumentContentServer</code> can be created successfully.
     * </p>
     */
    public void testCtor6() {
        SocketAddress endPoint = new InetSocketAddress("127.0.0.1", 0);

        server = new SocketDocumentContentServer(endPoint, -1000, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        server = new SocketDocumentContentServer(endPoint, 1000, "logger");
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);

        // Verify that the logger name can be null.
        server = new SocketDocumentContentServer(endPoint, 0, null);
        assertNotNull("Unable to create SocketDocumentConentServer instance.",
            server);
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(SocketAddress, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the endPoint is null, <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtor6_Failure1() {
        try {
            new SocketDocumentContentServer(null, 0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>SocketDocumentContentServer(SocketAddress, int, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the loggerName is empty, <code>IllegalArgumentException</code> is thrown.
     * </p>
     */
    public void testCtor6_Failure2() {
        try {
            new SocketDocumentContentServer(new InetSocketAddress("127.0.0.1", 0),
                0, "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>start()</code>.
     * </p>
     *
     * <p>
     * Verify that the server is started after this method is called.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testStart() throws Exception {
        assertFalse("The server is stopped at first.", server.isRunning());

        server.start();
        assertTrue("The server is running now.", server.isRunning());

        // Verify that start a already started server is ok.
        server.start();
        assertTrue("The server is running now.", server.isRunning());

        server.stop();
        assertFalse("The server should be stopped.", server.isRunning());
    }

    /**
     * <p>
     * Failure test for <code>start()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the port is already taken, <code>IOException</code> will be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testStart_Failure1() throws Exception {
        server.start();

        try {
            SocketDocumentContentServer newServer = new SocketDocumentContentServer(PORT,
                    0);
            newServer.start();

            fail("IOException is expected.");
        } catch (IOException e) {
            // success
        }

        server.stop();
    }

    /**
     * <p>
     * Accuracy test for <code>stop()</code>.
     * </p>
     *
     * <p>
     * Verify that the server is stopped after this method is called.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testStop() throws Exception {
        assertFalse("The server is stopped at first.", server.isRunning());

        // Verify that stop a already stopped server is ok.
        server.stop();

        server.start();
        assertTrue("The server is running now.", server.isRunning());

        server.stop();
        assertFalse("The server should be stopped.", server.isRunning());
    }

    /**
     * <p>
     * Accuracy test for <code>isRunning()</code>.
     * </p>
     *
     * <p>
     * Verify that it will return the right status for the server.
     * </p>
     *
     * @throws Exception to Junit.
     */
    public void testIsRunning() throws Exception {

        boolean running = server.isRunning();

        assertFalse("The server should be not running now.", running);

        server.start();
        running = server.isRunning();
        assertTrue("The server should be running now.", running);

        server.stop();
        running = server.isRunning();
        assertFalse("The server should be not running now.", running);
    }

    /**
     * <p>
     * Accuracy test for <code>getLastException()</code>.
     * </p>
     *
     * <p>
     * Verify that the last exception thrown is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetLastException() throws Exception {
        assertNull("The last exception should be null at first.",
            server.getLastException());

        server.start();

        // Wait for the worker thread to begin.
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x00);
        buffer.flip();

        channel.write(buffer);

        // Wait the worker to stop.
        Thread.sleep(1000);

        Exception e = server.getLastException();
        assertTrue("The last exception should be InvalidRequestException.",
            e instanceof InvalidRequestException);
    }

    /**
     * <p>
     * Failure test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Failure cause: There is not enough 2 bytes for the path's size, the request
     * should fail.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_Failure1() throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a saveDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x01);

        // Only 1 byte.
        buffer.put((byte) 1);

        // Sends out the request.
        buffer.flip();
        channel.write(buffer);

        ByteBuffer readBuf = ByteBuffer.allocate(1);
        int count = channel.read(readBuf);

        assertEquals("There should be only 1 byte returned.", 1, count);
        assertEquals("The request should fail.", 0, readBuf.array()[0]);
    }

    /**
     * <p>
     * Failure test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Failure cause: The path's length mismatch with the length flag, the request
     * should fail.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_Failure2() throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a saveDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x01);

        // Encode the path with utf-8 format.
        String path = new File("test_files\\test1.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) 120);
        // the encode bytes of path.
        buffer.put(pathBuf);

        // Sends out the request.
        buffer.flip();
        channel.write(buffer);

        ByteBuffer readBuf = ByteBuffer.allocate(1);
        int count = channel.read(readBuf);

        assertEquals("There should be only 1 byte returned.", 1, count);
        assertEquals("The request should fail.", 0, readBuf.array()[0]);
    }

    /**
     * <p>
     * Failure test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Failure cause: The request has ending bytes, the request should fail.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_Failure3() throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a saveDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x01);

        // Encode the path with utf-8 format.
        String path = new File("test_files\\test1.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) pathBuf.remaining());
        // the encode bytes of path.
        buffer.put(pathBuf);

        // Sends the document content.
        String content = "A sample text.";
        ByteBuffer contentBuf = encoder.encode(CharBuffer.wrap(
                    content.toCharArray()));
        buffer.putInt(contentBuf.remaining());
        buffer.put(contentBuf);

        // Additional ending bytes.
        buffer.putInt(100);

        // Sends out the request.
        buffer.flip();
        channel.write(buffer);

        ByteBuffer readBuf = ByteBuffer.allocate(1);
        int count = channel.read(readBuf);

        assertEquals("There should be only 1 byte returned.", 1, count);
        assertEquals("The request should fail.", 0, readBuf.array()[0]);
    }

    /**
     * <p>
     * Accuracy test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Verify case: Sends a saveDocumentContent request, then the document content
     * should be saved to the specified path.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_SaveDocumentContent()
        throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a saveDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x01);

        // Encode the path with utf-8 format.
        String path = new File("test_files\\test1.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) pathBuf.remaining());
        // the encode bytes of path.
        buffer.put(pathBuf);

        // Sends the document content.
        String content = "A sample text.";
        ByteBuffer contentBuf = encoder.encode(CharBuffer.wrap(
                    content.toCharArray()));
        buffer.putInt(contentBuf.remaining());
        buffer.put(contentBuf);

        // Sends out the request.
        buffer.flip();
        channel.write(buffer);

        ByteBuffer readBuf = ByteBuffer.allocate(16);
        int count = channel.read(readBuf);

        assertEquals("There should be only 1 byte returned.", 1, count);
        assertEquals("The request should be processed successfully.", 1,
            readBuf.array()[0]);
    }

    /**
     * <p>
     * Failure test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Verify case: Sends a saveDocumentContent request, but there is not 4 bytes to
     * indicates the length of the document content.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_SaveDocumentContent_Failure1()
        throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a saveDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x01);

        // Encode the path with utf-8 format.
        String path = new File("test_files\\test1.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) pathBuf.remaining());
        // the encode bytes of path.
        buffer.put(pathBuf);

        // Only 2 bytes.
        buffer.putShort((short) 2);

        // Sends out the request.
        buffer.flip();
        channel.write(buffer);

        ByteBuffer readBuf = ByteBuffer.allocate(1);
        int count = channel.read(readBuf);

        assertEquals("There should be only 1 byte returned.", 1, count);
        assertEquals("The request should fail.", 0, readBuf.array()[0]);
    }

    /**
     * <p>
     * Failure test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Verify case: Sends a saveDocumentContent request, the length of document
     * content doesn't match the length flag.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_SaveDocumentContent_Failure2()
        throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a saveDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x01);

        // Encode the path with utf-8 format.
        String path = new File("test_files\\test1.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) pathBuf.remaining());
        // the encode bytes of path.
        buffer.put(pathBuf);

        // Sends the document content.
        String content = "A sample text.";
        ByteBuffer contentBuf = encoder.encode(CharBuffer.wrap(
                    content.toCharArray()));
        buffer.putInt(256);
        buffer.put(contentBuf);

        // Sends out the request.
        buffer.flip();
        channel.write(buffer);

        ByteBuffer readBuf = ByteBuffer.allocate(1);
        int count = channel.read(readBuf);

        assertEquals("There should be only 1 byte returned.", 1, count);
        assertEquals("The request should fail.", 0, readBuf.array()[0]);
    }

    /**
     * <p>
     * Accuracy test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Verify case: Sends a getDocumentContent request, the file with specified
     * path exist, the content of the file should be returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_GetDocumentContent() throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a getDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x02);

        // Using utf-8 to encode the path.
        String path = new File("test_files\\test2.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) pathBuf.remaining());
        // the encode bytes of the path.
        buffer.put(pathBuf);

        // Send out the request.
        buffer.flip();
        channel.write(buffer);

        // The first byte indicates whether the request is processed successfully.
        ByteBuffer readBuf = ByteBuffer.allocate(1);
        int count = channel.read(readBuf);

        assertEquals("There should one heading byte.", 1, count);
        assertEquals("The request should be processed successfully.", 1,
            readBuf.array()[0]);

        // Reads the length of the content.
        readBuf = ByteBuffer.allocate(4);
        count = channel.read(readBuf);

        assertEquals("There should 4 heading bytes.", 4, count);

        // Gets the int value of the length.
        readBuf.rewind();

        int size = readBuf.getInt();

        // Reads the content.
        readBuf = ByteBuffer.allocate(size);
        count = channel.read(readBuf);

        assertEquals("The length should match the one in the request.", size,
            count);

        // Decode the content using utf-8.
        readBuf.rewind();

        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer stringBuf = decoder.decode(readBuf);

        assertEquals("The content should match.", "A sample text.",
            stringBuf.toString());
    }

    /**
     * <p>
     * Accuracy test for method <code>SocketDocumentContentWorker.run()</code>.
     * </p>
     *
     * <p>
     * Verify case: Sends a getDocumentContent request, the file with specified
     * path doesn't exist, there is only 1 byte in the response to indicate the
     * request is processed successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWorkerThread_GetDocumentContent_UnExist()
        throws Exception {
        // Start the server and wait the worker thread to begin.
        server.start();
        Thread.sleep(500);

        // Connect to the server.
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress("127.0.0.1", PORT));

        // Sends a getDocumentContent request.
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 0x02);

        // Using utf-8 to encode the path.
        String path = new File("test_files\\unknown.txt").getAbsolutePath();
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer pathBuf = encoder.encode(CharBuffer.wrap(path.toCharArray()));

        // 2 bytes to indicates the path's length.
        buffer.putShort((short) pathBuf.remaining());
        // the encode bytes of the path.
        buffer.put(pathBuf);

        // Send out the request.
        buffer.flip();
        channel.write(buffer);

        // The first byte indicates whether the request is processed successfully.
        ByteBuffer readBuf = ByteBuffer.allocate(16);
        int count = channel.read(readBuf);

        assertEquals("There should only be 1 byte.", 1, count);
        assertEquals("The request should be processed successfully.", 1,
            readBuf.array()[0]);
    }


}
