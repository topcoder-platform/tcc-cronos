/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.io.IOException;

import javax.servlet.ServletInputStream;

/**
 * <p>
 * This class represents the input stream of the uploaded file from the servlet request using the HTTP 1.1
 * standard. For details of the protocol please refer to RFC 1867. This class extends the abstract
 * <code>UploadedFileInputStream</code> class.
 * </p>
 *
 * <p>
 * This class keeps an instance of <code>ServletInputStream</code> which is used as an underlying
 * <code>InputStream</code>. It is expected that no one else reads from this ServletInputStream until the
 * file is completely read (using this <code>HttpUploadFileInputStream</code>).
 * </p>
 *
 * <p>
 * The stream reading is done in the following manner:
 * <ol>
 * <li>The data from the ServlerInputStream is read line-by-line. Line data is kept in the buffer field
 * array.</li>
 * <li>When new line is read it is compared with the boundary string (boundary filed) to check if the end
 * of file is reached in the ServletRequest.</li>
 * <li>read() method of the class returns the next byte in the buffer line (and, if necessary, reads the
 * new line from the ServletInputStream).</li>
 * <li>Once end of the file (indicated by the boundary string) or the end of the whole ServletInputStream
 * is reached read() method returns -1 indicating no more bytes in the stream.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable but its only read() method is synchronized, and the super
 * class is also thread-safe, so this class is thread-safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public class HttpUploadedFileInputStream extends UploadedFileInputStream {
    /**
     * <p>
     * Represents the size of buffer initialized, it is 8 Kb.
     * </p>
     */
    private static final int INITIAL_BUFFER_SIZE = 8 * 1024;

    /**
     * <p>
     * This is a buffer array that contains a current line read from the <code>ServletInputStream</code>.
     * Initialized once in the constructor and then never changed. Can not be null. Can not be empty.
     * </p>
     */
    private final byte[] buffer;

    /**
     * <p>
     * Represents the input stream pointing to data submitted in the request. Can not be null. It is set in
     * the constructor and then never changed. It is accessed in the read() method.
     * </p>
     */
    private final ServletInputStream input;

    /**
     * <p>
     * Represents the boundary which is a unique string that does not occur in the data (according to HTTP
     * 1.1). Can not be null or empty string. It is set in the constructor and then never changed. It is
     * accessed in the read() method. The size of this string is always less than 8 Kb (the size of buffer
     * initialized in the constructor).
     * </p>
     */
    private final String boundary;

    /**
     * <p>
     * The number of the bytes read from the <code>ServletInputStream</code> to the buffer field array.
     * This is actually length of the meaningful part of the buffer. Can not be less than 0.
     * </p>
     */
    private int bufferSize;

    /**
     * <p>
     * Indicates the current position in the buffer. This is the index of the next byte to be returned by
     * the read() method. Can not be less than 0.
     * </p>
     */
    private int bufferPosition = 0;

    /**
     * <p>
     * Indicate whether the end of the file is reached. Initially set to false. Once set to true (in read()
     * method) will never again be false. If this field is true the read() method will always return -1.
     * </p>
     */
    private boolean hasReachedEnd = false;

    /**
     * <p>
     * Package-private constructor of the class that calls the constructor of the superclass and initializes
     * corresponding fields. After that the first buffer line is read from the
     * <code>ServletInputStream</code>.
     * </p>
     *
     * @param fileLimit
     *            The maximum allowed file size limit in bytes. Value -1 means no restriction on the file
     *            size. Should be >= -1.
     * @param input
     *            Represents the input stream pointing to data submitted in the request. Can not be null.
     * @param boundary
     *            Represents the boundary which is a unique string that does not occur in the data
     *            (according to HTTP 1.1). Can not be null or empty string.
     * @throws IllegalArgumentException
     *             if fileLimit is less than -1, if in parameter is null, or if boundary is null or empty
     *             string.
     * @throws IOException
     *             if any I/O error occurs in reading from the ServletInputStream
     */
    HttpUploadedFileInputStream(long fileLimit, ServletInputStream input, String boundary) throws IOException {
        super(fileLimit);

        FileUploadHelper.validateNotNull(input, "input");
        FileUploadHelper.validateString(boundary, "boundary");

        this.input = input;
        this.boundary = boundary;

        // initialize buffer field (of size 8 * 1024).
        buffer = new byte[INITIAL_BUFFER_SIZE];
        bufferSize = input.readLine(buffer, 0, buffer.length);
        if (bufferSize == -1) {
            hasReachedEnd = true;
        }
    }

    /**
     * <p>
     * Reads the next byte of data from the input stream. The value byte is returned as an int in the range
     * 0 to 255. If no byte is available because the end of the file in the ServlerInputStream stream or the
     * end of the stream itself has been reached, the value -1 is returned. This method blocks until input
     * data is unavailable, the end of the stream is detected, or an exception is thrown.
     * </p>
     *
     * <p>
     * Note that the access to this method should be synchronized.
     * </p>
     *
     * @return the next byte of data, or -1 if the end of the stream is reached.
     * @throws IOException
     *             if any I/O error occurs in reading from the ServletInputStream
     * @throws InputSizeLimitExceededException
     *             if the file size in the stream exceeds the predefined limit.
     */
    public synchronized int read() throws IOException {
        // if has reached the end, return -1
        if (hasReachedEnd) {
            return -1;
        }

        // calls readByte() method of the superclass to indicate that the next byte has been read.
        readByte();

        // reads the next byte from the buffer field array. If the buffer has ended new
        // buffer line is read from the ServletInputStream.
        if (bufferPosition < bufferSize) {
            return buffer[bufferPosition++];
        } else {
            // read new line
            bufferSize = input.readLine(buffer, 0, buffer.length);
        }

        // Check whether it has reached boundary
        boolean reachedBoundary = true;

        for (int i = 0; i < boundary.length(); i++) {
            if (buffer[i + bufferPosition] != boundary.charAt(i)) {
                reachedBoundary = false;
                break;
            }
        }

        // If no byte is available because the end of the file in the ServlerInputStream stream
        // or the end of the stream itself has been reached, the value -1 is returned.
        if (bufferSize == -1 || reachedBoundary) {
            hasReachedEnd = true;
            return -1;
        }

        bufferPosition = 0;
        return buffer[bufferPosition++];
    }
}
