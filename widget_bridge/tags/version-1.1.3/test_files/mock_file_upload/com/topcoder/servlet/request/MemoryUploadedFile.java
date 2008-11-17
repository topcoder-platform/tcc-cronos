/*
 * Copyright (C) 2006, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * This class represents an uploaded file stored in the memory. Some file information and the file contents
 * (byte data) can be retrieved from the memory. No file id is associated with this instance.
 * </p>
 *
 * <p>
 * Instances of this class are created by the concrete <code>MemoryFileUpload</code> class.
 * </p>
 *
 * <p>
 * The class extends <code>UploadedFile</code> so it implements Serializable indirectly to meet the
 * requirements. The internal byte array is also Serializable.
 * </p>
 *
 * <p>
 * <b>Version 2.2 Changes:</b> The file id is now associated to each {@link FileUpload#getUniqueFileName()}
 * method of the base class is used to generate unique file ids.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread-safe by being immutable.
 * </p>
 *
 * @author colau, PE
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 * @since 2.0
 */
public class MemoryUploadedFile extends UploadedFile {
    /**
     * <p>
     * Represents the remote file name of the uploaded file. It cannot be null or empty string.
     * </p>
     */
    private final String remoteFileName;

    /**
     * <p>
     * Represents the byte contents of the file. It cannot be null.
     * </p>
     */
    private final byte[] data;

    /**
     * <p>
     * Creates a new instance of <code>MemoryUploadedFile</code> class. Package-private constructor to
     * prevent instantiation outside the package.
     * </p>
     *
     * <p>
     * <b>Version 2.2 Changes:</b> The file id is now set for each {@link FileUpload#getUniqueFileName()}
     * method of the base class is used to generate unique file ids.
     * </p>
     *
     * @param data
     *            the byte contents of the file. Can not be null.
     * @param remoteFileName
     *            the remote file name of the uploaded file. It cannot be null or empty string.
     * @param contentType
     *            the content type of the file. Can be null or empty string.
     * @param fileId
     *            the file id
     * @throws IllegalArgumentException
     *             if data is null, or remoteFileName is null or empty string, or fileId is null or empty.
     */
    MemoryUploadedFile(byte[] data, String remoteFileName, String contentType, String fileId) {
        super(fileId, contentType);

        FileUploadHelper.validateNotNull(data, "data");
        FileUploadHelper.validateString(remoteFileName, "remoteFileName");
        FileUploadHelper.validateString(fileId, "fileId");

        // No need to make a shallow copy of the array because of performance issues,
        // and this constructor is called by the MemoryFileUpload class only.
        this.data = data;
        this.remoteFileName = remoteFileName;
    }

    /**
     * <p>
     * Gets the remote file name of the uploaded file.
     * </p>
     *
     * @return the remote file name of the uploaded file.
     */
    public String getRemoteFileName() {
        return this.remoteFileName;
    }

    /**
     * <p>
     * Gets the size of the file, in bytes.
     * </p>
     *
     * @return the size of the file, in bytes.
     */
    public long getSize() {
        return data.length;
    }

    /**
     * <p>
     * Gets the input stream pointing to the start of file contents. User is required to close the stream
     * after use.
     * </p>
     *
     * @return the underlying input stream.
     */
    public InputStream getInputStream() {
        return new ByteArrayInputStream(data);
    }
}
