/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.IOException;

/**
 * <p>
 * This interface will be used in <code>ContestManager</code> implementation
 * to provide operations to save document content, get document content content
 * and check document content existence. The document content will be identified
 * by a path.
 * </p>
 *
 * <p>
 * NOTE: Implementations must have a ctor like: ctor-name(Map&lt;String,
 * Object&gt; attributes) in order to be used by <code>ContestManagerBean</code>
 * class.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> Implementations need to be thread-safe.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public interface DocumentContentManager {
    /**
     * <p>
     * Saves the document content into persistence.
     * </p>
     *
     * @param path
     *            the path of the document content
     * @param documentContent
     *            the document content
     *
     * @throws IllegalArgumentException
     *             if any argument is null, or path argument is empty string, or
     *             documentContent is empty array
     * @throws IOException
     *             if any i/o error occurs when saving the document content
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content
     */
    public void saveDocumentContent(String path, byte[] documentContent)
        throws IOException, DocumentContentManagementException;

    /**
     * <p>
     * Gets the content of document given by path. Returns null if the document
     * content doesn't exist. It can also return empty array if the document
     * content is empty.
     * </p>
     *
     * @param path
     *            the document content path
     * @return the document content
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty string.
     * @throws IOException
     *             if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content.
     */
    public byte[] getDocumentContent(String path) throws IOException,
            DocumentContentManagementException;

    /**
     * <p>
     * Checks whether the document content identified by the path exists.
     * </p>
     *
     * @param path
     *            the document path
     * @return true if the document content identified by the path exists,
     *         returns false otherwise
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty string.
     * @throws IOException
     *             if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content.
     */
    public boolean existDocumentContent(String path) throws IOException,
            DocumentContentManagementException;

    /**
     * <p>
     * Moves uploaded document files to contest folder. This method should be
     * called after contest is created. [BUG TCCC-134]
     * </p>
     *
     * @param filename
     *            the file name
     * @param contestId contes id 
     * @return true if the document content identified by the path exists,
     *         returns false otherwise
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty string. If contestId is negative.
     * @throws IOException
     *             if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content.
     */
    public void moveDocumentToContestFolder(String filename, long contestId)
            throws IOException, DocumentContentManagementException;
}
