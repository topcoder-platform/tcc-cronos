/*
 * Copyright (C) 2006, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * <p>
 * This interface defines the contract to parse the uploaded files and parameters from the servlet request.
 * For uploaded file, its form file name, remote file name, content type and file contents will be
 * retrieved. It provides the method to write the file contents to the specified output stream for storage
 * purpose.
 * </p>
 *
 * <p>
 * The usage of this interface is defined as follows:
 * <ol>
 * <li> Initialize the parsing process by parseRequest().</li>
 * <li> Iterate for each uploaded file by hasNextFile(). If it returns true, getFormFileName(),
 * getRemoteFileName(), getContentType() should return appropriate values for the previously parsed file.
 * </li>
 * <li> Call writeNextFile() to write the file contents to the output stream or getUploadedFileInputStream()
 * to get the file as a input stream. </li>
 * <li> When hasNextFile() returns false, no more file is available. getParameters() should then return all
 * the parameters parsed in the process. </li>
 * </ol>
 * Note that IllegalStateException can be thrown if the above usage pattern is not followed.
 * </p>
 *
 * <p>
 * <b>Version 2.2 Changes:</b><br>
 * <ul>
 * <li> Ability to get the uploaded files as an InputStream is provided. getUploadedFileInputStream() method
 * returns the UploadedFileInputStream object (specifically, HttpUploadedFileInputStream instance) that
 * represents the stream. This method should be called after hasNextFile() returns true. Note, that user has
 * to read the stream to the end (i.e. read all the file completely) before call hasNextFile() again.</li>
 * <li> The hasNextFile() method accounts for new getUploadedFileInputStream() method.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The implementation is not required to be thread-safe. In fact it requires a
 * sequence of method calls to work properly. But the <code>FileUpload</code> and
 * <code>StreamFileUpload</code> class will use it in a thread-safe manner.
 * </p>
 *
 * @author colau, PE
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 * @since 2.0
 */
public interface RequestParser {
    /**
     * <p>
     * Parses the given request to initialize the parsing process. This must be the first method to call for
     * parsing.
     * </p>
     *
     * @param request
     *            the servlet request to be parsed.
     *
     * @throws IllegalArgumentException
     *             if request is null.
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints, e.g.
     *             invalid content type.
     */
    void parseRequest(ServletRequest request) throws RequestParsingException;

    /**
     * <p>
     * Returns whether next uploaded file is available in the request. If this method returns true, then
     * form file name, remote file name and content type for this file will be available using respective
     * methods. The writeNextFile() method should then be called to store the file contents or
     * getUploadedFileInputStream() to load the file from a stream.
     * </p>
     * <p>
     * Note that this method can be called only after parseRequest() or writeNextFile() or after the
     * getUploadedFileInputStream() returned a stream an that stream was read to the end.
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b><br>
     * The method accounts for new getUploadedFileInputStream(long fileLimit) method (it can be called prior
     * to this method, see Exceptions handling).
     * </p>
     *
     * @return true if more files are available, false otherwise
     *
     * @throws IllegalStateException
     *             if parseRequest() or writeNextFile() or getUploadedFileInputStream() has not been called
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints
     * @since 2.0
     */
    boolean hasNextFile() throws RequestParsingException;

    /**
     * <p>
     * Gets the form file name of the last parsed file. Can be null if it is not available.
     * </p>
     *
     * @return the form file name of the last parsed file.
     */
    String getFormFileName();

    /**
     * <p>
     * Gets the remote file name of the last parsed file. Can be null if it is not available.
     * </p>
     *
     * @return the remote file name of the last parsed file.
     */
    String getRemoteFileName();

    /**
     * <p>
     * Gets the content type of the last parsed file. Can be null if it is not available.
     * </p>
     *
     * @return the content type of the last parsed file.
     */
    String getContentType();

    /**
     * <p>
     * Writes the next uploaded file contents to the specified output stream. The fileLimit parameter
     * specifies the maximum size of this file, in bytes. A value of -1 indicates no limit. If it is
     * exceeded, a <code>FileSizeLimitExceededException</code> will be thrown.
     * </p>
     *
     * <p>
     * Note that this method can be called only if hasNextFile() has been called and returns true.
     * </p>
     *
     * @param out
     *            the output stream to write file contents to.
     * @param fileLimit
     *            the file size limit in bytes.
     *
     * @throws IllegalArgumentException
     *             if outputStream is null or fileLimit is less than -1.
     * @throws IOException
     *             if any I/O error occurs in writing the file.
     * @throws FileSizeLimitExceededException
     *             if the uploaded file is too large.
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     */
    void writeNextFile(OutputStream out, long fileLimit) throws IOException, FileSizeLimitExceededException;

    /**
     * <p>
     * Gets the parameters collected in the request during the parsing process. The key should be parameter
     * name (String), the value should be a list (List) of parameter values (String).
     * </p>
     *
     * @return a map from parameter names to parameter values.
     */
    Map getParameters();

    /**
     * <p>
     * Returns an input stream of the next file in the ServletRequest. The fileLimit parameter specifies the
     * maximum size of this file, in bytes. A value of -1 indicates no limit.
     * </p>
     * <p>
     * Note that this method can be called only if hasNextFile() has been called and returned true.
     * </p>
     *
     * @param fileLimit
     *            the file size limit in bytes. Should be >= -1
     * @return An instance of UploadedFileInputStream which represents input stream of the uploaded file.
     *         Can not be null.
     * @throws IllegalArgumentException
     *             if fileLimit is less than -1
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     * @throws RequestParsingException
     *             if any error occurs during getting the file input stream
     * @since 2.2
     */
    UploadedFileInputStream getUploadedFileInputStream(long fileLimit) throws RequestParsingException;
}
