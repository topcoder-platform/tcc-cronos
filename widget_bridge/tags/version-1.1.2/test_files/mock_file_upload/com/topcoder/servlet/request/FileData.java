/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

/**
 * <p>
 * This is a simple bean class that contains remote file name and content type information of the uploaded
 * file.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> The class is immutable and therefore thread-safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public class FileData {
    /**
     * <p>
     * Name of the file. Can be null and empty string. Set in the constructor and the never changed.
     * </p>
     */
    private final String fileName;

    /**
     * <p>
     * MIME-type of the file. Can be null and empty string. Set in the constructor and the never changed.
     * </p>
     */
    private final String contentType;

    /**
     * <p>
     * Package-private constructor. Simply initializes corresponding fields with the parameters provided.
     * </p>
     *
     * @param fileName
     *            Name of the file. Can be null and empty string.
     * @param contentType
     *            MIME-type of the file. Can be null and empty string.
     */
    FileData(String fileName, String contentType) {
        this.fileName = fileName;
        this.contentType = contentType;
    }

    /**
     * <p>
     * Getter for fileName field.
     * </p>
     *
     * @return Name of the file. Can be null and empty string.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * <p>
     * Getter for contentType field.
     * </p>
     *
     * @return MIME-type of the file. Can be null and empty string.
     */
    public String getContentType() {
        return this.contentType;
    }
}
