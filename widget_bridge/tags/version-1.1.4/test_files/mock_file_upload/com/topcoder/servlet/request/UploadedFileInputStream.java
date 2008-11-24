/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.io.InputStream;

/**
 * <p>
 * This is the base class for input stream of the uploaded files. The class extends standard abstract
 * <code>InputStream</code> class. This class manages the maximum allowed size of the file to be loaded
 * and keeps track of how many bytes have already been read.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable but synchronize the methods. Therefore the class is thread
 * safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public abstract class UploadedFileInputStream extends InputStream {
    /**
     * <p>
     * The maximum allowed file size limit in bytes. Value -1 means no restriction on the file size.
     * Initialized in the constructor but can be changed later through the setter. Access to this field is
     * synchronized. Should always be >= -1.
     * </p>
     */
    private long fileLimit;

    /**
     * <p>
     * Number of bytes already read from the stream. Initially set to 0. Incremented by 1 each time the byte
     * is read in the readByte() method. There's a getter for this field. Access to this field is
     * synchronized. Can not be less than 0.
     * </p>
     */
    private long numberOfBytesRead = 0;

    /**
     * <p>
     * Constructor that initializes the fileLimit field with the parameter value.
     * </p>
     *
     * @param fileLimit
     *            the maximum allowed file size limit in bytes. Value -1 means no restriction on the file
     *            size. Should be >= -1.
     * @throws IllegalArgumentException
     *             if fileLimit is less than -1.
     */
    protected UploadedFileInputStream(long fileLimit) {
        FileUploadHelper.validateFileLimit(fileLimit, "fileLimit");
        this.fileLimit = fileLimit;
    }

    /**
     * <p>
     * Getter for numberOfBytesRead field. Make sure to synchronize access to this method.
     * </p>
     *
     * @return Number of bytes already read from the stream. Can not be less than 0.
     */
    public synchronized long getNumberOfBytesRead() {
        return numberOfBytesRead;
    }

    /**
     * <p>
     * Getter for fileLimit field. Make sure to synchronize access to this method.
     * </p>
     *
     * @return The maximum allowed file size limit in bytes. Value -1 means no restriction on the file size.
     *         Should be >= -1.
     */
    public synchronized long getFileLimit() {
        return fileLimit;
    }

    /**
     * <p>
     * Setter for fileLimit field. If after setting the limit the number of bytes already read exceeds the
     * fileLimit (if not -1 which means no size restriction), InputSizeLimitExceededException is thrown.
     * Make sure to synchronize access to this method.
     * </p>
     *
     * @param fileLimit
     *            The maximum allowed file size limit in bytes. Value -1 means no restriction on the file
     *            size. Should be >= -1.
     * @throws IllegalArgumentException
     *             if fileLimit is less than -1
     * @throws InputSizeLimitExceededException
     *             if numberOfBytesRead exceeds the new fileLimit.
     */
    public synchronized void setFileLimit(long fileLimit) throws InputSizeLimitExceededException {
        FileUploadHelper.validateFileLimit(fileLimit, "fileLimit");
        if (fileLimit != -1 && numberOfBytesRead > fileLimit) {
            throw new InputSizeLimitExceededException("The numberOfBytesRead exceeds the new fileLimit.");
        }

        this.fileLimit = fileLimit;
    }

    /**
     * <p>
     * This is a simple protected method that increments the numberOfBytesRead field by 1. This method
     * should be called from the read() method implemented in the subclasses. If the numberOfBytesRead value
     * after incrementing exceeds the fileLimit the FileSizeLimitExceededException is thrown (if only
     * fileLimit is not -1 which means no size restriction).
     * </p>
     *
     * @throws InputSizeLimitExceededException
     *             if numberOfBytesRead exceeds the fileLimit (after incrementing)
     */
    protected synchronized void readByte() throws InputSizeLimitExceededException {
        if (fileLimit != -1 && numberOfBytesRead + 1 > fileLimit) {
            throw new InputSizeLimitExceededException("The numberOfBytesRead exceeds the fileLimit.");
        }

        numberOfBytesRead++;
    }
}
