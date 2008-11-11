/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.topcoder.configuration.ConfigurationObject;

/**
 * <p>
 * A class abstracting the parsing of <code>ServletRequest</code> to produce the uploaded files as an
 * InputStream. The information parsed in the ServletRequest includes uploaded files (as an input streams)
 * and form parameters.
 * </p>
 *
 * <p>
 * This class should be initialized from a configuration namespace which should be preloaded, the same
 * properties are also supported through initialization through the ConfigurationObject input in the 2nd
 * constructor. It will look for the following properties:
 * <ol>
 * <li> single_file_limit (optional) - the single file size limit for each uploaded file in the request, in
 * bytes. A value of -1 indicates no limit. Default value is -1. </li>
 * <li> total_file_limit (optional) - the total file size limit for all uploaded files in the request, in
 * bytes. A value of -1 indicates no limit. Default value is -1.</li>
 * </ol>
 * </p>
 *
 * <p>
 * The class can be configured through <tt>ConfigManager</tt>, <tt>Configuration API</tt> or
 * programmatically through setters.
 * </p>
 *
 * <p>
 * The only valid sequence of the calls (considering only parseRequest, getUploadedFileInputStream and
 * hasNextFile) is the following:
 * parseRequest,hasNextFile,getUploadedFileInputStream,hasNextFile,getUploadedFileInputStream, ... ,
 * hasNextFile.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is not thread-safe as it is mutable and is not synchronized.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public class StreamFileUpload {
    /**
     * <p>
     * The property key specifying the single file size limit for each uploaded file in the request. The
     * property value should be a long integer. This property is optional. Can not be null or empty string.
     * Set once during construction and never changed later.
     * </p>
     */
    public static final String SINGLE_FILE_LIMIT_PROPERTY = "single_file_limit";

    /**
     * <p>
     * The property key specifying the total file size limit for all uploaded files in the request. The
     * property value should be a long integer. This property is optional. Can not be null or empty string.
     * Set once during construction and never changed later.
     * </p>
     */
    public static final String TOTAL_FILE_LIMIT_PROPERTY = "total_file_limit";

    /**
     * <p>
     * Represents the parseRequest method has been called.
     * </p>
     */
    private static final int PARSER_EQUEST_CALLED = 0;

    /**
     * <p>
     * Represents the getUploadedFileInputStream method has been called.
     * </p>
     */
    private static final int GET_UPLOADEDFILEINPUTSTREAM_CALLED = 1;

    /**
     * <p>
     * Represents the hasNextFile() method has been called and the return value is true.
     * </p>
     */
    private static final int HAS_NEXT_FILE_CALLED_AND_RETURN_TRUE = 2;

    /**
     * <p>
     * Represents the single file size limit for each uploaded file in the request, in bytes. The value
     * range should be -1 or greater. A value of -1 indicates no limit. It is initialized in the constructor
     * but can be changed subsequently through a setter.
     * </p>
     */
    private long singleFileLimit = -1;

    /**
     * <p>
     * Represents the total file size limit for all uploaded files in the request, in bytes. The value range
     * should be -1 or greater. A value of -1 indicates no limit. It is initialized in the constructor but
     * can be changed subsequently through a setter.
     * </p>
     */
    private long totalFileLimit = -1;

    /**
     * <p>
     * An instance of <code>RequestParser</code> that is used to parse the <code>ServletRequest</code>
     * and produce <code>UploadedFileInputStream</code> objects. Initially null. Initialized in
     * {@link #parseRequest()} method.
     * </p>
     */
    private RequestParser parser = null;

    /**
     * <p>
     * Last <code>UploadedFileInputStream</code> object produced by the <code>RequestParser</code>.
     * This input stream is generally used by the application directly, but this class needs to keep it to
     * read the stream to the end before calling {@link RequestParser#hasNextFile()} method (in case if the
     * user does not read the whole stream to the end). Initially null. Initialized in
     * {@link #getUploadedFileInputStream()} method.
     * </p>
     */
    private UploadedFileInputStream uploadedFileInputStream = null;

    /**
     * <p>
     * This is the number of bytes read so far from all the files in the ServletRequest. This value is
     * updated each time {@link #hasNextFile()} method is called. This values is needed to keep track of the
     * overall number of bytes read from all the files in order not to exceed totalFileLimit. Initially set
     * to 0.
     * </p>
     */
    private long numberOfBytesRead = 0;

    /**
     * <p>
     * Represents the flag which indicates the last operation. It is initialized into -1, and 0 means the
     * parseRequest method has been called; 1 means the hasNextFile() method has been called and the return
     * value is true.
     * </p>
     */
    private int lastOperation = -1;

    /**
     * <p>
     * Constructor that loads configuration from the specified namespace. The namespace should be preloaded.
     * </p>
     *
     * @param namespace
     *            The configuration namespace to use. Should not be null or empty string.
     * @throws IllegalArgumentException
     *             if namespace is null or empty string
     * @throws ConfigurationException
     *             if any configuration error occurs
     */
    public StreamFileUpload(String namespace) throws ConfigurationException {
        FileUploadHelper.validateString(namespace, "namespace");

        // get the property values
        this.singleFileLimit = FileUploadHelper.getLongPropertyValue(namespace, SINGLE_FILE_LIMIT_PROPERTY);
        this.totalFileLimit = FileUploadHelper.getLongPropertyValue(namespace, TOTAL_FILE_LIMIT_PROPERTY);

        // validate the property values
        this.validateFileLimit(singleFileLimit, "singleFileLimit");
        this.validateFileLimit(totalFileLimit, "totalFileLimit");
    }

    /**
     * <p>
     * Constructor that loads configuration from the specified ConfigurationObject.
     * </p>
     *
     * @param configObject
     *            Configuration object with initialized properties. Should not be null.
     * @throws ConfigurationException
     *             if any configuration error occurs
     * @throws IllegalArgumentException
     *             if configObject is null.
     */
    public StreamFileUpload(ConfigurationObject configObject) throws ConfigurationException {
        FileUploadHelper.validateNotNull(configObject, "Configuration object");

        // get the property values
        this.singleFileLimit = FileUploadHelper.getLongPropertyValue(configObject, SINGLE_FILE_LIMIT_PROPERTY);
        this.totalFileLimit = FileUploadHelper.getLongPropertyValue(configObject, TOTAL_FILE_LIMIT_PROPERTY);

        // validate the property values
        this.validateFileLimit(singleFileLimit, "singleFileLimit");
        this.validateFileLimit(totalFileLimit, "totalFileLimit");
    }

    /**
     * <p>
     * Returns the single file size limit for each uploaded file in the request, in bytes. A value of -1
     * indicates no limit.
     * </p>
     *
     * @return the single file size limit. Should be equal or greater than -1.
     */
    public long getSingleFileLimit() {
        return singleFileLimit;
    }

    /**
     * <p>
     * Sets the singleFileLimit field with the corresponding input value. Single file size limit for each
     * uploaded file in the request, in bytes. A value of ¨C1 indicates no limit.
     * </p>
     *
     * @param singleFileLimit
     *            Single file size limit for each uploaded file in the request, in bytes. Should be equal or
     *            greater than -1.
     * @throws IllegalArgumentException
     *             if the singleFileLimit is less than -1
     */
    public void setSingleFileLimit(long singleFileLimit) {
        FileUploadHelper.validateFileLimit(singleFileLimit, "singleFileLimit");
        this.singleFileLimit = singleFileLimit;
    }

    /**
     * <p>
     * Returns the total file size limit for all uploaded files in the request, in bytes. A value of -1
     * indicates no limit.
     * </p>
     *
     * @return the total file size limit. Should be equal or greater than -1.
     */
    public long getTotalFileLimit() {
        return totalFileLimit;
    }

    /**
     * <p>
     * Sets the totalFileLimit field with the corresponding input value. Total file size limit for all
     * uploaded files in the request, in bytes. A value of ¨C1 indicates no limit.
     * </p>
     *
     * @param totalFileLimit
     *            Total file size limit for all uploaded files in the request, in bytes. Should be equal or
     *            greater than -1.
     * @throws IllegalArgumentException
     *             if the totalFileLimit is less than -1 or totalFileLimit less than numberOfBytesRead
     */
    public void setTotalFileLimit(long totalFileLimit) {
        FileUploadHelper.validateFileLimit(totalFileLimit, "totalFileLimit");
        if (totalFileLimit < numberOfBytesRead) {
            throw new IllegalArgumentException("The totalFileLimit should not less than numberOfBytesRead");
        }

        this.totalFileLimit = totalFileLimit;
    }

    /**
     * <p>
     * Parses <code>ServletRequest</code> using the <code>HttpRequestParser</code>. This method should
     * be called before {@link #hasNextFile()} and {@link #getUploadedFileInputStream()} method.
     * </p>
     *
     * @param request
     *            the servlet request to be parsed. Should not be null.
     * @throws IllegalArgumentException
     *             if request is null
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints, e.g.
     *             invalid content type
     */
    public void parseRequest(ServletRequest request) throws RequestParsingException {
        parseRequest(request, new HttpRequestParser());
    }

    /**
     * <p>
     * Parses <code>ServletRequest</code> using the specified <code>RequestParser</code>. This method
     * should be called before {@link #hasNextFile()} and {@link #getUploadedFileInputStream()} method. This
     * method also sets <code>uploadedFileInpustStream</code> field to null.
     * </p>
     *
     * <p>
     * Note that the parser should not be used by other threads.
     * </p>
     *
     * @param request
     *            the servlet request to be parsed. Should not be null.
     * @param parser
     *            Request parser instance. Should not be null.
     * @throws IllegalArgumentException
     *             if any of the arguments is null
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints, e.g.
     *             invalid content type
     */
    public void parseRequest(ServletRequest request, RequestParser parser) throws RequestParsingException {
        FileUploadHelper.validateNotNull(request, "request");
        FileUploadHelper.validateNotNull(parser, "parser");
        parser.parseRequest(request);

        this.parser = parser;
        this.uploadedFileInputStream = null;
        this.lastOperation = PARSER_EQUEST_CALLED;
    }

    /**
     * <p>
     * Returns whether next uploaded file is available in the request. If this method returns true, then
     * form file name, remote file name and content type for this file will be available using respective
     * methods. The {@link #getUploadedFileInputStream()} method should then be called to get the file
     * contents as an InputStream.
     * </p>
     *
     * <p>
     * Note that this method can be called only after {@link #parseRequest()} or
     * {@link #getUploadedFileInputStream()} method.
     * </p>
     *
     * @return true if more files are available, false otherwise
     * @throws RequestParsingException
     *             if any I/O error occurs during parsing, or the parsing violates the constraints
     * @throws IllegalStateException
     *             if parseRequest() or getUploadedFileInputStream() has not been called.
     * @throws InputSizeLimitExceededException
     *             if the total size of the files in the ServletRequest exceeded the totalFileLimit value.
     */
    public boolean hasNextFile() throws RequestParsingException, InputSizeLimitExceededException {
        if (this.lastOperation != PARSER_EQUEST_CALLED && this.lastOperation != GET_UPLOADEDFILEINPUTSTREAM_CALLED) {
            throw new IllegalStateException("The parseRequest() has not been called.");
        }

        // If the uploadedFileInpustStream field is not null (which means that this is not the first
        // hasNextFile() call after the parseRequest() ), then the stream is first read to the end.
        // Please note that it is needed to first completely read the stream to the end before call
        // RequestParser#hasNextFile() method according to the RequestParser's contract.
        if (uploadedFileInputStream != null) {
            // Before reading the stream to the end its file size limit is increased to the
            // total number of bytes left for the entire ServletRequest. In other words the
            // single file limit is not applied for the stream anymore.
            uploadedFileInputStream.setFileLimit(getTotalFileLimit() - numberOfBytesRead
                    - uploadedFileInputStream.getNumberOfBytesRead());

            // simply read all the bytes
            try {
                BufferedInputStream stream = new BufferedInputStream(uploadedFileInputStream);
                while (stream.read(new byte[8 * 1024]) != -1)
                    ;
            } catch (IOException e) {
                throw new RequestParsingException(
                        "IOException occurs while reading bytes from uploadedFileInputStream.");
            }

            // After the stream reading the numberOfBytesRead field is also increased by
            // the number of bytes read from the stream
            numberOfBytesRead += uploadedFileInputStream.getNumberOfBytesRead();
        }

        boolean hasNextFile = parser.hasNextFile();
        if (hasNextFile) {
            this.lastOperation = HAS_NEXT_FILE_CALLED_AND_RETURN_TRUE;
        }

        return hasNextFile;
    }

    /**
     * <p>
     * Returns an input stream of the next file in the <code>ServletRequest</code>. Note that this method
     * can be called only if {@link #hasNextFile()} has been called and returned true.
     * </p>
     *
     * @return An instance of UploadedFileInputStream which represent input stream of the uploaded file. Can
     *         not be null.
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     * @throws RequestParsingException
     *             if any error occurs during getting the file input stream.
     */
    public UploadedFileInputStream getUploadedFileInputStream() throws RequestParsingException {
        this.checkHasNextFile();
        uploadedFileInputStream = parser.getUploadedFileInputStream(Math.min(getSingleFileLimit(),
                getTotalFileLimit() - numberOfBytesRead));
        this.lastOperation = GET_UPLOADEDFILEINPUTSTREAM_CALLED;
        return uploadedFileInputStream;
    }

    /**
     * <p>
     * Returns the form file name of the last parsed file. Can be null if it is not available.
     * </p>
     *
     * @return the form file name of the last parsed file. Can be null if it is not available, but not an
     *         empty string.
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     */
    public String getFormFileName() {
        this.checkHasNextFile();
        return parser.getFormFileName();
    }

    /**
     * <p>
     * Returns the remote file name of the last parsed file. Can be null if it is not available.
     * </p>
     *
     * @return the remote file name of the last parsed file. Can be null if it is not available, but not an
     *         empty string.
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     */
    public String getRemoteFileName() {
        this.checkHasNextFile();
        return parser.getRemoteFileName();
    }

    /**
     * <p>
     * Returns the content type of the last parsed file. Can be null if it is not available.
     * </p>
     *
     * @return the content type of the last parsed file. Can be null if it is not available, but not an
     *         empty string.
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     */
    public String getContentType() {
        this.checkHasNextFile();
        return parser.getContentType();
    }

    /**
     * <p>
     * Returns the parameters collected in the request during the parsing process. The key should be
     * parameter name (String), the value should be a list (List) of parameter values (String).
     * </p>
     *
     * <p>
     * Please note that returning map contains only parameters collected so far during the request parsing.
     * All parameters will be available only after all the files from the ServletRequest are parsed.
     * </p>
     *
     * @return A map from parameter names to parameter values. Can not be null. Can be empty. Keys can not
     *         be null or empty strings. Values can not be null but can be empty lists. Values of these
     *         lists can not be null or empty strings.
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     */
    public Map getParameters() {
        this.checkHasNextFile();
        return parser.getParameters();
    }

    /**
     * <p>
     * Checks the {@link #hasNextFile()} method has been called and returns true.
     * </p>
     *
     * @throws IllegalStateException
     *             if hasNextFile() has not been called or hasNextFile() returns false.
     */
    private void checkHasNextFile() {
        if (this.lastOperation != HAS_NEXT_FILE_CALLED_AND_RETURN_TRUE) {
            throw new IllegalStateException("The hasNextFile() method has not been called or it returns false.");
        }
    }

    /**
     * <p>
     * Validates the fileLimit >= -1.
     * </p>
     *
     * @param fileLimit
     *            the file size limit in bytes. Should be >= -1.
     * @param name
     *            name of the fileLimit.
     * @throws ConfigurationException
     *             if fileLimit is less than -1.
     */
    void validateFileLimit(long fileLimit, String name) throws ConfigurationException {
        if (fileLimit < -1) {
            throw new ConfigurationException(name + " cannot be less than -1.");
        }
    }
}
