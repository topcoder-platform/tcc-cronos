/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests.mock;

import com.topcoder.service.studio.contest.DocumentContentManagementException;
import com.topcoder.service.studio.contest.DocumentContentManager;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import java.util.Map;

/**
 * <p>
 * Wrong impl of DocumentContentManager which do not have a constructor for Map&ltString, Object&gt.
 * </p>
 * 
 * <p>
 * <strong>Thread safety:</strong> This class is immutable and thread-safe.
 * </p>
 * 
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class MockDocumentContentManagerCtorWrong implements DocumentContentManager {
    /**
     * <p>
     * The key for server address in the map attributes.
     * </p>
     */
    private static final String SERVERADDRESS = "serverAddress";

    /**
     * <p>
     * The key for server port in the map attributes.
     * </p>
     */
    private static final String SERVERPORT = "serverPort";

    /**
     * <p>
     * The count of bytes of a int value.
     * </p>
     */
    private static final int INT_LENGTH = 4;

    /**
     * <p>
     * The first byte value of existDocumentContent request.
     * </p>
     */
    private static final byte COMMAND_EXIST = 3;

    /**
     * <p>
     * This field represents the server ip address. It's initialized in the constructor, and never changed afterwards.
     * It must be non-null, non-empty string.
     * </p>
     */
    private final String serverAddress;

    /**
     * <p>
     * This field represents the server port. It's initialized in the constructor, and never changed afterwards. It must
     * be int value in range [0, 65535].
     * </p>
     */
    private final int serverPort;

    /**
     * <p>
     * Creates a <code>SocketDocumentContentManager</code> instance. Initializes the server address and port from the
     * passed-in map. The argument <code>attributes</code> should contain two key-value pairs.<br/> 1)
     * "serverAddress" -- the value must be a non-null, non-empty string, it's used as the server address for the
     * manager.<br/> 2) "serverPort" -- the value must be non-null java.lang.Integer object, whose value should be in
     * range [0, 65535], it's used as the server port for the manager.
     * </p>
     * 
     * @param attributes a map of attributes to initialize fields
     * @param i not used
     * 
     * @throws IllegalArgumentException if the argument is null, or if it doesn't contain any attribute mentioned above,
     *         or the attribute value is invalid, or it containes additional attributes.
     */
    public MockDocumentContentManagerCtorWrong(Map<String, Object> attributes, int i) {

        // There should and only be 2 pairs in the map.
        if (attributes.size() != 2) {
            throw new IllegalArgumentException("There should only be 2 pairs.");
        }

        // Get the server address object.
        Object obj = attributes.get(SERVERADDRESS);

        if (!(obj instanceof String)) {
            throw new IllegalArgumentException("The value for 'serverAddress' isn't instance of String.");
        }

        String address = (String) obj;
        this.serverAddress = address;

        obj = attributes.get(SERVERPORT);

        if (!(obj instanceof Integer)) {
            throw new IllegalArgumentException("The value for 'serverPort' isn't instance of Integer.");
        }

        Integer port = (Integer) obj;
        this.serverPort = port.intValue();
    }

    /**
     * <p>
     * Creates a <code>SocketDocumentContentManager</code> instance with specified address and port.
     * </p>
     * 
     * @param serverAddress the server ip address
     * @param serverPort the server port
     * 
     * @throws IllegalArgumentException if serverAddress is null or empty string, or serverPort is not in range [0,
     *         65535]
     */
    public MockDocumentContentManagerCtorWrong(String serverAddress, int serverPort) {

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * <p>
     * Saves the document content into persistence.
     * </p>
     * 
     * @param path the path of the document content
     * @param documentContent the document content
     * 
     * @throws IllegalArgumentException if any argument is null, or path argument is empty string, or documentContent
     *         arg is empty array.
     * @throws IOException if any i/o error occurs when saving the document content.
     * @throws DocumentContentManagementException if any other error occurs when managing the document content. (e.g. if
     *         the received response is invalid, or failure response is received. )
     */
    public void saveDocumentContent(String path, byte[] documentContent) throws IOException,
        DocumentContentManagementException {

        if (documentContent.length == 0) {
            throw new IllegalArgumentException("The document content should not be empty.");
        }

        // Connect to the server.
        SocketChannel channel = null;

        try {
            channel = openChannel();

            // Encode the path using utf-8 format.
            ByteBuffer pathBuf = encodePath(path);

            // Form the saveDocumentContent request.
            ByteBuffer buffer = ByteBuffer.allocate(1 + 2 + pathBuf.remaining() + INT_LENGTH + documentContent.length);

            buffer.put((byte) 0x01);
            buffer.putShort((short) pathBuf.remaining());
            buffer.put(pathBuf);
            buffer.putInt(documentContent.length);
            buffer.put(documentContent);
            buffer.flip();

            channel.write(buffer);

            // Allocate enough memory for the first byte, and the next two bytes.
            buffer = ByteBuffer.allocate(1 + 2);

            int count = channel.read(buffer);

            // As the read mode is block, if channel can't read anything from the server, it will
            // throw exception. So the count is at least 1.
            byte[] array = buffer.array();

            if (array[0] == 0) {
                commonErrorHandling(count, buffer, channel);
            } else if (array[0] == 1) {
                // Success response.
                if (count > 1) {
                    throw new DocumentContentManagementException(
                        "There should be no additional bytes in success response.");
                }
            } else {
                throw new DocumentContentManagementException("The first byte should be either 0 or 1.");
            }
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    /**
     * <p>
     * Gets the content of document given by path. Returns null if the document content doesn't exist. It can also
     * return empty array if the document content is empty.
     * </p>
     * 
     * @param path the document content path
     * @return the document content
     * 
     * @throws IllegalArgumentException if the argument is null or empty string.
     * @throws IOException if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException if any other error occurs when managing the document content. (e.g. if
     *         the received response is invalid. or failure response is received. )
     */
    public byte[] getDocumentContent(String path) throws IOException, DocumentContentManagementException {

        // Connect to the server.
        SocketChannel channel = null;

        try {
            channel = openChannel();
            doCommand((byte) 0x02, path, channel);

            // Allocate enough memory for the first byte, and the next 4 bytes.
            ByteBuffer buffer = ByteBuffer.allocate(1 + INT_LENGTH);

            int count = channel.read(buffer);

            // As the read mode is block, if channel can't read anything from the server, it will
            // throw exception. So the count is at least 1.
            byte[] array = buffer.array();

            if (array[0] == 0) {
                specialErrorHandling(count, buffer, channel);

                return null;
            } else if (array[0] == 1) {
                if (count == 1) {
                    // The response only has 1 byte, indicates the document content doesn't exist.
                    return null;
                }

                if (count != (1 + INT_LENGTH)) {
                    throw new DocumentContentManagementException(
                        "There are no enough byte to indicates the content length.");
                }

                buffer.rewind();
                buffer.get();

                int length = buffer.getInt();

                if (length == 0) {
                    return new byte[0];
                }

                buffer = ByteBuffer.allocate(length + 1);
                count = channel.read(buffer);

                if (count < length) {
                    throw new DocumentContentManagementException(
                        "The content doesn't have enough bytes as heading bytes indicates");
                } else if (count > length) {
                    throw new DocumentContentManagementException("The content has ending additional bytes.");
                }

                buffer.rewind();

                // exclude the ending byte.
                return subArray(buffer.array(), count);
            } else {
                throw new DocumentContentManagementException("The first byte should be either 0 or 1.");
            }
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    /**
     * <p>
     * Returns the sub array with size of count.
     * </p>
     * 
     * @param array the byte array
     * @param count the size of sub array
     * @return the sub array with size of count
     */
    private byte[] subArray(byte[] array, int count) {
        byte[] content = new byte[count];

        for (int i = 0; i < count; i++) {
            content[i] = array[i];
        }

        return content;
    }

    /**
     * <p>
     * Checks whether the document content identified by the path exists.
     * </p>
     * 
     * @param path the document content path
     * @return true if the document content identified by the path exists, return false otherwise
     * 
     * @throws IllegalArgumentException if the argument is null or empty string.
     * @throws IOException if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException if any other error occurs when managing the document content. (e.g. if
     *         the received response is invalid. or failure response is received. )
     */
    public boolean existDocumentContent(String path) throws IOException, DocumentContentManagementException {

        SocketChannel channel = null;

        try {
            // Connect to the server.
            channel = openChannel();

            doCommand(COMMAND_EXIST, path, channel);

            // Allocate enough memory for the first byte, and the next 2 bytes.
            ByteBuffer buffer = ByteBuffer.allocate(1 + 2);

            int count = channel.read(buffer);

            // As the read mode is block, if channel can't read anything from the server, it will
            // throw exception. So the count is at least 1.
            byte[] array = buffer.array();

            if (array[0] == 0) {
                commonErrorHandling(count, buffer, channel);

                return false;
            } else if (array[0] == 1) {
                if (count != 2) {
                    throw new DocumentContentManagementException(
                        "Success response for existDocumentContent should only have 2 bytes.");
                }

                if (array[1] == 1) {
                    return true;
                } else if (array[2] == 0) {
                    return false;
                }

                throw new DocumentContentManagementException("Only 0 and 1 is valid for exist flag.");
            } else {
                throw new DocumentContentManagementException("The first byte should be either 0 or 1.");
            }
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }
    }

    /**
     * <p>
     * Connects to the server.
     * </p>
     * 
     * @return the socket channel
     * @throws IOException if any error occurs
     */
    private SocketChannel openChannel() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress(serverAddress, serverPort));

        return channel;
    }

    /**
     * <p>
     * Encodes the file path using utf-8 format.
     * </p>
     * 
     * @param path the file path
     * @return the byte buffer contains encoded file path
     * @throws IOException if any error occurs
     */
    private ByteBuffer encodePath(String path) throws IOException {
        // Encode the path using utf-8 format.
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();

        return encoder.encode(CharBuffer.wrap(path.toCharArray()));
    }

    /**
     * <p>
     * Decode the ByteBuffer to String using utf-8 format.
     * </p>
     * 
     * @param buffer the byte buffer
     * @return the decoded string
     * @throws IOException if any error occurs
     */
    private String decodeString(ByteBuffer buffer) throws IOException {
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder newDecoder = charset.newDecoder();

        return newDecoder.decode(buffer).toString();
    }

    /**
     * <p>
     * Construct a request and send it to the server.
     * </p>
     * 
     * @param command the specified request
     * @param path the file path
     * @param channel the socket channel
     * @throws IOException if any error occurs
     */
    private void doCommand(byte command, String path, SocketChannel channel) throws IOException {
        // Encode the path using utf-8 format.
        ByteBuffer pathBuf = encodePath(path);

        ByteBuffer buffer = ByteBuffer.allocate(1 + 2 + pathBuf.remaining());

        buffer.put(command);
        buffer.putShort((short) pathBuf.remaining());
        buffer.put(pathBuf);
        buffer.flip();

        channel.write(buffer);
    }

    /**
     * <p>
     * Handles the error messages from server.
     * </p>
     * 
     * @param count the count of read bytes
     * @param buffer the byte buffer
     * @param channel the socket channel
     * @throws IOException if any io error occurs
     * @throws DocumentContentManagementException if any other error occurs
     */
    private void commonErrorHandling(int count, ByteBuffer buffer, SocketChannel channel) throws IOException,
        DocumentContentManagementException {
        // Failure response.
        if (count != (1 + 2)) {
            throw new DocumentContentManagementException(
                "There should be 2 bytes to indicates the length of error message.");
        }

        buffer.rewind();
        buffer.get();

        // Gets the length of the error message.
        short size = buffer.getShort();

        buffer = ByteBuffer.allocate(size + 1);
        count = channel.read(buffer);

        if (count < size) {
            throw new DocumentContentManagementException("The error message content doesn't have enough bytes.");
        } else if (count > size) {
            throw new DocumentContentManagementException("The error message content has ending additional bytes.");
        }

        // Exclude the last byte.
        buffer.rewind();
        buffer = buffer.slice();
        buffer.limit(count);

        throw new DocumentContentManagementException(decodeString(buffer));
    }

    /**
     * <p>
     * Handles the error messages from server.
     * </p>
     * 
     * @param count the count of read bytes
     * @param buffer the byte buffers
     * @param channel the socket channel
     * @throws DocumentContentManagementException if any other error occurs
     * @throws IOException if any io error occurs
     */
    private void specialErrorHandling(int count, ByteBuffer buffer, SocketChannel channel)
        throws DocumentContentManagementException, IOException {
        // Failure response.
        if (count < (1 + 2)) {
            throw new DocumentContentManagementException(
                "There should be 2 bytes to indicates the length of error message.");
        }

        buffer.rewind();
        buffer.get();

        // Gets the length of the error message.
        short size = buffer.getShort();

        if (size <= 2) {
            if (count != (1 + 2 + size)) {
                throw new DocumentContentManagementException("The length mismatch.");
            }

            buffer.rewind();
            buffer = buffer.slice();
            buffer.limit(count);
            buffer.position(1 + 2);

            throw new DocumentContentManagementException(decodeString(buffer));
        }

        short preRead = buffer.getShort();

        // 2 bytes are already read
        buffer = ByteBuffer.allocate((size + 1) - 2);
        count = channel.read(buffer);

        if (count < (size - 2)) {
            throw new DocumentContentManagementException("The error message content doesn't have enough bytes.");
        } else if (count > (size - 2)) {
            throw new DocumentContentManagementException("The error message content has ending additional bytes.");
        }

        // Exclude the last byte.
        buffer.rewind();
        buffer = buffer.slice();
        buffer.limit(count);

        ByteBuffer msgBuf = ByteBuffer.allocate(size);
        msgBuf.putShort(preRead);
        msgBuf.put(buffer);
        msgBuf.rewind();

        throw new DocumentContentManagementException(decodeString(msgBuf));
    }

    /**
     * Dummy implementation.
     *
     * @param filename file name
     * @param contestId contest id
     * @throws IOException never
     * @throws DocumentContentManagementException never
     */
    public void moveDocumentToContestFolder(String filename, long contestId)
        throws IOException, DocumentContentManagementException {
        // empty
    }
}
