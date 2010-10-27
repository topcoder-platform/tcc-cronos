/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests.mock;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;


/**
 * <p>
 * Mock implementation for testing server.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockServer {
    /**
     * <p>Mock variable.</p>
     */
    private int backlog;

    /**
     * <p>Mock variable.</p>
     */
    private byte[] msg;

    /**
     * <p>Mock variable.</p>
     */
    private ServerSocketChannel server;

    /**
     * <p>Mock variable.</p>
     */
    private SocketAddress endPoint;

    /**
     * <p>Mock variable.</p>
     */
    private Selector selector;

    /**
     * <p>Mock variable.</p>
     */
    private boolean isRunning = false;

    /**
     * <p>Mock constructor.</p>
     * @param port the port
     * @param backlog the backlog
     */
    public MockServer(int port, int backlog) {
        this.backlog = backlog;

        endPoint = new InetSocketAddress(port);
    }

    /**
     * <p>
     * Sets the message to return.
     * </p>
     * @param msg the message to return.
     */
    public void setMsg(byte[] msg) {
        this.msg = msg;
    }

    /**
     * <p>Starts the server.</p>
     * @throws IOException to JUnit.
     */
    public void start() throws IOException {
        server = ServerSocketChannel.open();
        server.configureBlocking(false);

        // Bind to the specified address and use the specified backlog.
        server.socket().bind(endPoint, backlog);

        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        isRunning = true;

        // Start the worker thread.
        new Thread(new MockServerWorker()).start();
    }

    /**
     * <p>Stops the server.</p>
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * <p>
     * Mock worker.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class MockServerWorker implements Runnable {
        /**
             * <p>Mock run. Returns the message directly.</p>
             */
        public void run() {
            try {
                while (isRunning) {
                    SocketChannel client = null;

                    // waiting for events with timeout value
                    if (selector.select(1000) <= 0) {
                        continue;
                    }

                    // get selected keys
                    Iterator it = selector.selectedKeys().iterator();

                    // process every selectionKey
                    while (it.hasNext()) {
                        SelectionKey key = (SelectionKey) it.next();

                        // a client required a connection
                        if (key.isAcceptable()) {
                            it.remove();

                            // get client socket channel
                            client = server.accept();

                            // Set Non-Blocking mode for select operation
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            // The server is ready to read now.
                            it.remove();

                            client = (SocketChannel) key.channel();

                            client.write(ByteBuffer.wrap(msg));
                        }
                    }
                }
            } catch (IOException e) {
                // Ignore
            } finally {
                try {
                    selector.close();
                } catch (IOException e) {
                    // Ignore
                }

                try {
                    server.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}
