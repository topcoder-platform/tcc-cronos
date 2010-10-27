/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.documentcontentservers;

import com.topcoder.service.studio.contest.bean.Helper;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import java.util.Date;
import java.util.Iterator;


/**
 * <p>
 * This class acts as the file system server for the <code>SocketDocumentContentManager</code> class.
 * It will respond to the requests sent from the <code>SocketDocumentContentManager</code> to save
 * document content to file system, send back document content retrieved from file system, and verify
 * document existence in file system.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * This class has mutable variable(s), but all its methods are synchronized, and its mutable variables
 * are all synchronized properly, so it's thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class SocketDocumentContentServer {
    /**
     * <p>
     * This field represents the server endpoint. It's initialized in the constructor, and never
     * changed afterwards. It must be non-null.
     * </p>
     */
    private final SocketAddress endPoint;

    /**
     * <p>
     * This field represents the backlog used for <code>ServerSocket</code>. It can be any value.
     * It's initialized in the constructor, and never changed afterwards.
     * </p>
     */
    private final int backlog;

    /**
     * <p>
     * This field represents the server is stopped or not. It's set to true initially (stopped),
     * it will be changed to false if start() method is called and executed successfully. And it
     * will be changed to true if stop() method is called and server is stopped successfully.
     * </p>
     */
    private boolean stopped = true;

    /**
     * <p>
     * This field represents the last exception thrown during the execution. It is null initially.
     * It can be set in <code>SocketDocumentContentWorker.run</code> method if any error occurs.
     * </p>
     */
    private Exception lastException;

    /**
     * <p>
     * This field represents the server socket channel to accept clients' requrests. It's null initially.
     * It's initialized in the <code>start</code> method, and it can be reset to null if the server is stopped.
     * </p>
     */
    private ServerSocketChannel server;

    /**
     * <p>
     * This field represents the <code>Selector</code> for the socket channel. It's null initially.
     * It's initialized in the <code>start</code> method, and it can be reset to null if the server
     * is stopped.
     * </p>
     */
    private Selector selector;

    /**
     * <p>
     * This field represents the logger to log invocation information and exception. It is initialized
     * in the constructor, and never changed afterwards. It can be null, and if so, logging is disabled.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Creates <code>SocketDocumentContentServer</code> instance with specified server
     * port and backlog.
     * </p>
     *
     * @param port the server port to listen to
     * @param backlog the backlog used for the ServerSocket
     * @throws IllegalArgumentException if port is not in range [0, 65535]
     */
    public SocketDocumentContentServer(int port, int backlog) {
        this(port, backlog, SocketDocumentContentServer.class.getName());
    }

    /**
     * <p>
     * Creates <code>SocketDocumentContentServer</code> instance with specified address,
     * server port and backlog.
     * </p>
     *
     * @param address the server ip address
     * @param port the server port to listen to
     * @param backlog the backlog used for the ServerSocket
     * @throws IllegalArgumentException
     *         if address is empty string, or if the port is not in range [0, 65535].
     */
    public SocketDocumentContentServer(String address, int port, int backlog) {
        this(address, port, backlog, SocketDocumentContentServer.class.getName());
    }

    /**
     * <p>
     * Creates <code>SocketDocumentContentServer</code> instance with specified server port,
     * backlog and logger name.
     * </p>
     *
     * @param port the server port to listen to
     * @param backlog the backlog used for the ServerSocket
     * @param loggerName the logger name
     * @throws IllegalArgumentException
     *         if the loggerName is empty string, or if the port is not in range [0, 65535].
     */
    public SocketDocumentContentServer(int port, int backlog, String loggerName) {
        this(null, port, backlog, loggerName);
    }

    /**
     * <p>
     * Creates <code>SocketDocumentContentServer</code> instance with specified address,
     * port, backlog and logger name. If the address is null, use localhost instead. If
     * the loggerName isn't assigned, the logger won't be used.
     * </p>
     *
     * @param address the server ip address. It can be null, but cannot be empty string.
     * @param port the server port to listen to
     * @param backlog the backlog used for the ServerSocket
     * @param loggerName the logger name. It can be null, but cannot be empty string.
     *
     * @throws IllegalArgumentException
     *         if loggerName or address is empty string, or port is not in range [0, 65535]
     */
    public SocketDocumentContentServer(String address, int port, int backlog,
        String loggerName) {
        Helper.checkPort(port);
        Helper.checkEmpty(address, "address");
        Helper.checkEmpty(loggerName, "loggerName");

        // Creates the endpoint from address:port.
        if (address != null) {
            endPoint = new InetSocketAddress(address, port);
        } else {
            endPoint = new InetSocketAddress(port);
        }

        if (loggerName != null) {
            logger = LogManager.getLog(loggerName);
        } else {
            logger = null;
        }

        this.backlog = backlog;
    }

    /**
     * <p>
     * Creates <code>SocketDocumentContentServer</code> instance with specified endpoint
     * and backlog.
     * </p>
     *
     * @param endPoint the server endpoint
     * @param backlog the backlog used for the ServerSocket
     * @throws IllegalArgumentException if endPoint is null
     */
    public SocketDocumentContentServer(SocketAddress endPoint, int backlog) {
        this(endPoint, backlog, SocketDocumentContentServer.class.getName());
    }

    /**
     * <p>
     * Creates <code>SocketDocumentContentServer</code> instance with specified endpoint,
     * backlog and logger name. If the logger name isn't assigned, the logger won't be used.
     * </p>
     *
     * @param endPoint the server endpoint. It can't be null.
     * @param backlog the backlog used for the ServerSocket
     * @param loggerName the logger name. It can be null, but cannot be empty string.
     *
     * @throws IllegalArgumentException
     *         if loggerName is empty string, or endPoint is null.
     */
    public SocketDocumentContentServer(SocketAddress endPoint, int backlog,
        String loggerName) {
        Helper.checkEmpty(loggerName, "loggerName");
        Helper.checkNull(endPoint, "endPoint");

        if (loggerName != null) {
            logger = LogManager.getLog(loggerName);
        } else {
            logger = null;
        }

        this.endPoint = endPoint;
        this.backlog = backlog;
    }

    /**
     * <p>
     * Starts the server.
     * </p>
     *
     * @throws IOException if any error occurs
     */
    public synchronized void start() throws IOException {
        try {
            logEnter("start()");

            if (!stopped) {
                // The server is already started.
                return;
            }

            server = ServerSocketChannel.open();
            server.configureBlocking(false);

            // Bind to the specified address and use the specified backlog.
            server.socket().bind(endPoint, backlog);

            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            stopped = false;

            // Start the worker thread.
            new Thread(new SocketDocumentContentWorker()).start();
        } catch (IOException e) {
            logException(e, "There are errors in the start()");
            throw e;
        } finally {
            logExit("Start()");
        }
    }

    /**
     * <p>Stops the server.</p>
     */
    public synchronized void stop() {
        try {
            logEnter("stop()");

            if (stopped) {
                // The server is already stopped.
                return;
            }

            stopped = true;
        } finally {
            logExit("stop()");
        }
    }

    /**
     * <p>
     * Log the enter action with timestamp.
     * </p>
     *
     * @param methodName the method name
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "[{0}: Enter method {1}]", new Date(),
                methodName);
        }
    }

    /**
     * <p>
     * Log the exist action with timestamp.
     * </p>
     *
     * @param methodName the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "[{0}: Exit method {1}]", new Date(),
                methodName);
        }
    }

    /**
     * <p>
     * Log the exception and the message, the exception's message and stack trace will
     * be logged too.
     * </p>
     *
     * @param e the exception to log
     * @param message the message to log
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);
        }
    }

    /**
     * <p>
     * Checks the server is running or not. Returns true if it's running, return false otherwise.
     * </p>
     *
     * @return true if the server is running, return false otherwise
     */
    public synchronized boolean isRunning() {
        return !stopped;
    }

    /**
     * <p>
     * Gets the last exception.
     * </p>
     *
     * @return the last exception thrown during the execution.
     */
    public synchronized Exception getLastException() {
        return lastException;
    }

    /**
     * <p>
     * This class implements the <code>Runnable</code> interface, and it is used to process
     * clients' requests, and send back proper responses to client.
     * </p>
     *
     * <p>
     * <strong>Thread safety:</strong>
     * It's used thread-safely by <code>SocketDocumentContentServer</code> as all methods in
     * <code>SocketDocumentContentServer</code> are synchronized.
     * </p>
     *
     * @author Standlove, TCSDEVELOPER
     * @version 1.0
     */
    private class SocketDocumentContentWorker implements Runnable {
        /**
             * <p>This value indicates a saveDocumentContent request.</p>
             */
        private static final byte SAVE_DOCUMENT_CONTENT = 1;

        /**
         * <p>This value indicates a getDocumentContent request.</p>
         */
        private static final byte GET_DOCUMENT_CONTENT = 2;

        /**
         * <p>This value indicates a existDocumentContent request.</p>
         */
        private static final byte EXIST_DOCUMENT_CONTENT = 3;

        /**
         * <p>The count of int value's bytes.</p>
         */
        private static final int INT_LENGTH = 4;

        /**
         * <p>The default timeout value.</p>
         */
        private static final int TIMEOUT = 1000;

        /**
         * <p>Default empty constructor.</p>
         */
        public SocketDocumentContentWorker() {
        }

        /**
         * <p>
         * Process the clients' requests, and write proper responses to client.
         * </p>
         */
        public void run() {
            try {
                while (!stopped) {
                    SocketChannel client = null;

                    try {
                        // waiting for events with timeout value
                        if (selector.select(TIMEOUT) <= 0) {
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
                                key.cancel();

                                client = (SocketChannel) key.channel();

                                processData(client);
                            }
                        }
                    } catch (IOException e) {
                        processException(e, client);
                    } catch (InvalidRequestException e) {
                        processException(e, client);
                    }
                }
            } finally {
                try {
                    synchronized (SocketDocumentContentServer.this) {
                        selector.close();
                    }
                } catch (IOException e) {
                    logException(e,
                        "There are errors while closing the selector.");
                }

                try {
                    synchronized (SocketDocumentContentServer.this) {
                        server.close();
                    }
                } catch (IOException e) {
                    logException(e, "There are errors while closing the server.");
                }
            }
        }

        /**
         * <p>
         * Process the exception, and then send the failure response to the client.
         * </p>
         *
         * @param e the exception
         * @param client the client socket channel
         */
        private void processException(Exception e, SocketChannel client) {
            logException(e, "There are errors in run().");

            // log the exception
            lastException = e;

            try {
                Charset charset = Charset.forName("utf-8");
                CharsetEncoder encoder = charset.newEncoder();

                ByteBuffer buf = encoder.encode(CharBuffer.wrap(
                            e.getMessage().toCharArray()));

                ByteBuffer response = ByteBuffer.allocate(1 + 2 + buf.remaining());
                response.put((byte) 0x0);
                response.putShort((short) buf.remaining());
                response.put(buf);
                response.flip();

                client.write(response);
            } catch (CharacterCodingException e2) {
                logException(e2,
                    "There are errors while encoding the error message.");
            } catch (IOException e2) {
                logException(e2,
                    "There are io errors while written the error message.");
            }
        }

        /**
         * <p>
         * Reads data from the client, and processes the request according to the specified
         * request.
         * </p>
         *
         * @param client the client socket channel
         * @throws IOException if any i/o error occurs
         * @throws InvalidRequestException if the request is invalid
         */
        private void processData(SocketChannel client)
            throws IOException, InvalidRequestException {
            // The first byte is used to indicate the request type.
            ByteBuffer requestTypeBuf = ByteBuffer.allocate(1);
            client.read(requestTypeBuf);

            requestTypeBuf.rewind();

            byte requestType = requestTypeBuf.get();

            // Only 0x01, 0x02, 0x03 is valid request type.
            if ((requestType == SAVE_DOCUMENT_CONTENT) || (requestType == GET_DOCUMENT_CONTENT)
                || (requestType == EXIST_DOCUMENT_CONTENT)) {
                // There is 2 bytes to indicate the path's length.
                ByteBuffer pathSizeBuf = ByteBuffer.allocate(2);

                int count = client.read(pathSizeBuf);

                if (count != 2) {
                    // There is less than 2 bytes in the buffer.
                    throw new InvalidRequestException(
                        "There should be 2 bytes to indicates the path's length in bytes.");
                }

                pathSizeBuf.rewind();

                int pathSize = pathSizeBuf.getShort();
                ByteBuffer pathBuf = ByteBuffer.allocate(pathSize);

                count = client.read(pathBuf);

                if (count != pathSize) {
                    // The path buffer's size is less than the previous indicated size.
                    throw new InvalidRequestException(
                        "The path buffer length mismatch with the length flag.");
                }

                pathBuf.rewind();

                // Decode the path buffer with utf-8 format.
                Charset charset = Charset.forName("utf-8");
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer pathStringBuf = decoder.decode(pathBuf);
                String path = pathStringBuf.toString();

                if (requestType == SAVE_DOCUMENT_CONTENT) {
                    saveDocumentContent(path, client);
                } else if (requestType == GET_DOCUMENT_CONTENT) {
                    getDocumentContent(path, client);
                } else {
                    existDocumentContent(path, client);
                }
            } else {
                throw new InvalidRequestException("Unrecognized request type: " + requestType);
            }
        }

        /**
         * <p>
         * Processes the <code>saveDocumentContent</code> request.
         * </p>
         *
         * @param path the document path
         * @param client the client socket channel
         * @throws IOException if any i/o error occurs
         * @throws InvalidRequestException if the request is invalid
         */
        private void saveDocumentContent(String path, SocketChannel client)
            throws IOException, InvalidRequestException {
            // There is 4 bytes to indicates the length of content.
            ByteBuffer sizeBuf = ByteBuffer.allocate(INT_LENGTH);

            int count = client.read(sizeBuf);

            if (count != INT_LENGTH) {
                // There is no enough 4 bytes.
                throw new InvalidRequestException(
                    "There is no enough 4 bytes for indicating the content length.");
            }

            sizeBuf.rewind();

            // The length of the content.
            int size = sizeBuf.getInt();

            ByteBuffer contentBuf = ByteBuffer.allocate(size);
            count = client.read(contentBuf);

            if (count != size) {
                // There is less than 'size' bytes.
                throw new InvalidRequestException(
                    "The content has less bytes than the length flag.");
            }

            // Checks whether there ared additional ending bytes.
            checkEndingBytes(client);

            FileOutputStream stream = new FileOutputStream(path);

            try {
                stream.write(contentBuf.array());
            } finally {
                stream.close();
            }

            byte[] buffer = new byte[1];
            buffer[0] = 0x01;

            client.write(ByteBuffer.wrap(buffer));
        }

        /**
         * <p>
         * Processes the <code>getDocumentContent</code> request.
         * </p>
         *
         * @param path the document path
         * @param client the client socket channel
         * @throws IOException if any error occurs
         * @throws InvalidRequestException if there are additional ending bytes
         */
        private void getDocumentContent(String path, SocketChannel client)
            throws IOException, InvalidRequestException {
            // Checks whether there ared additional ending bytes.
            checkEndingBytes(client);

            File file = new File(path);

            if (file.exists()) {
                // As the file is written by this SocketDocumentContentServer,
                // and it used "int" as the length in the saveDocumentContent request,
                // so we can assume its biggest length won't exceed "int" range.
                int length = (int) file.length();

                byte[] buffer = new byte[length];
                FileInputStream stream = new FileInputStream(path);

                try {
                    stream.read(buffer);
                } finally {
                    stream.close();
                }

                ByteBuffer contentBuf = ByteBuffer.allocate(length + 1 + INT_LENGTH);
                contentBuf.put((byte) 0x01);
                contentBuf.putInt(length);
                contentBuf.put(buffer);
                contentBuf.flip();

                client.write(contentBuf);
            } else {
                byte[] buffer = new byte[1];
                buffer[0] = 0x01;

                client.write(ByteBuffer.wrap(buffer));
            }
        }

        /**
         * <p>
         * Processes the <code>existDocumentContent</code> request.
         * </p>
         *
         * @param path the document path
         * @param client the client socket channel
         * @throws IOException if any error occurs
         * @throws InvalidRequestException if there are additional ending bytes
         */
        private void existDocumentContent(String path, SocketChannel client)
            throws IOException, InvalidRequestException {
            // Checks whether there ared additional ending bytes.
            checkEndingBytes(client);

            File file = new File(path);

            byte[] buffer = new byte[2];

            // Indicates the request is processed successfully.
            buffer[0] = 0x01;

            if (file.exists() && file.isFile()) {
                // The document should be normal file.
                // Indicates the file with specified path exists.
                buffer[1] = 0x01;
            } else {
                // Indicates the file with specified path doesn't exist.
                buffer[1] = 0x0;
            }

            client.write(ByteBuffer.wrap(buffer));
        }

        /**
         * <p>
         * Checks whether there are additional bytes in the client socket. If it is,
         * InvalidRequestException is thrown.
         * </p>
         *
         * @param client the client socket channel
         * @throws InvalidRequestException if thread are additional bytes
         * @throws IOException if any io error occurs
         */
        private void checkEndingBytes(SocketChannel client)
            throws InvalidRequestException, IOException {
            ByteBuffer endBuf = ByteBuffer.allocate(1);
            int count = client.read(endBuf);

            if (count == 1) {
                throw new InvalidRequestException(
                    "There are additional bytes in the end.");
            }
        }
    }
}
