/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

/**
 * <p>
 * This interface defines a single method for validating the uploaded file. This interface is used in the
 * <code>FileUpload</code> class for validating the files in the <code>ServletRequest</code>.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface should be thread-safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public interface FileValidator {
    /**
     * <p>
     * Validates file based on its MIME-type and filename. Returns true if the file is considered valid and
     * false otherwise.
     * </p>
     *
     * @param fileData
     *            FileData object with the properties of the uploaded file. Should not be null.
     * @return true if the file is considered valid and false otherwise.
     * @throws IllegalArgumentException
     *             if the parameter is null
     */
    public boolean validateFile(FileData fileData);
}
