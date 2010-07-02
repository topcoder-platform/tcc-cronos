/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.documentcontentmanagers;

import com.topcoder.service.studio.contest.DocumentContentManagementException;
import com.topcoder.service.studio.contest.DocumentContentManager;
import com.topcoder.service.studio.contest.bean.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.ByteBuffer;

import java.util.Map;

/**
 * <p>
 * This class implements the <code>DocumentContentManager</code> interface,
 * and it acts as a socket client to save document content to local file system
 * or get document content information from local file system.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is immutable and thread-safe.
 * </p>
 *
 * @author superZZ
 * @version 1.0
 */
public class FileSystemDocumentContentManager implements DocumentContentManager {
    /**
     * Represents file path prefix. Will be used if specified.
     */
    private final String path;

    /**
     * Empty constructor.
     */
    public FileSystemDocumentContentManager() {
        path = null;
    }

    /**
     * Constructor with file path prefix.
     *
     * @param path
     *            File path prefix
     * @throws IllegalArgumentException
     *             if the string is null or empty
     */
    public FileSystemDocumentContentManager(String path) {
        Helper.checkString(path, "path");
        if (!path.endsWith(File.separator)) {
            this.path = path + File.separator;
        } else {
            this.path = path;
        }
    }

    /**
     * Constructor with an attributes set. There is only one path attribute
     * expected.
     *
     * @param attributes the attributes.
     * @throws IllegalArgumentException
     *             if the attributes is null or does not contain path attribute.
     */
    public FileSystemDocumentContentManager(Map<String, Object> attributes) {
        Helper.checkNull(attributes, "attributes");

        // There should and only be 1 pairs in the map.
        if (attributes.size() != 1) {
            throw new IllegalArgumentException("There should only be 1 pairs.");
        }
        String filePath = (String) attributes.get("path");
        if (filePath != null && !filePath.endsWith(File.separator)) {
            this.path = filePath + File.separator;
        } else {
            this.path = filePath;
        }
    }

    /**
     * <p>
     * Checks whether the document content identified by the path exists.
     * </p>
     *
     * @param path
     *            the document content path
     * @return true if the document content identified by the path exists,
     *         return false otherwise
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty string.
     * @throws IOException
     *             if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content.
     */
    public synchronized boolean existDocumentContent(String path)
        throws IOException, DocumentContentManagementException {
        Helper.checkString(path, "path");

        File file = new File(path);
        return file.exists() && file.isFile();
    }

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
    public synchronized byte[] getDocumentContent(String path)
            throws IOException, DocumentContentManagementException {
        Helper.checkString(path, "path");

        File file = new File(path);

        if (file.exists()) {
            int length = (int) file.length();

            byte[] buffer = new byte[length];
            FileInputStream stream = new FileInputStream(
                    this.path == null ? path : this.path + path);

            try {
                stream.read(buffer);
            } finally {
                stream.close();
            }

            return buffer;
        } else {
            return null;
        }
    }

    /**
     * <p>
     * Saves the document content into local file system.
     * </p>
     *
     * @param path
     *            the path of the document content
     * @param documentContent
     *            the document content
     *
     * @throws IllegalArgumentException
     *             if any argument is null, or path argument is empty string, or
     *             documentContent arg is empty array.
     * @throws IOException
     *             if any i/o error occurs when saving the document content.
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content.
     */
    public synchronized void saveDocumentContent(String path,
            byte[] documentContent) throws IOException,
            DocumentContentManagementException {
        Helper.checkString(path, "path");
        ByteBuffer contentBuf = ByteBuffer.wrap(documentContent);

        String filePath = this.path == null ? path : this.path + path;
        if (new File(filePath).isDirectory()) {
            throw new DocumentContentManagementException(filePath
                    + " is a directory.");
        }

        // Create folder if unexist.
        File folder = new File(new File(filePath).getParent());
        if (!folder.exists()) {
            folder.mkdir();
        }

        FileOutputStream stream = new FileOutputStream(filePath);

        try {
            stream.write(contentBuf.array());
        } finally {
            stream.close();
        }
    }

    /**
     * <p>
     * Moves uploaded document files to contest folder. This method should be
     * called after contest is created. [BUG TCCC-134]
     * </p>
     * <p>
     * For example: move from /studiofiles/documents to
     * /studiofiles/documents/2011/ if the file is original located under
     * /studiofiles/documents and contestId is 2011.
     * </p>
     *
     * @param filePath
     *            the document path
     * @param contestId contest id
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty string. If contestId is
     *             negative.
     * @throws IOException
     *             if any i/o error occurs when getting document content.
     * @throws DocumentContentManagementException
     *             if any other error occurs when managing the document content.
     */
    public void moveDocumentToContestFolder(String filePath, long contestId)
            throws IOException, DocumentContentManagementException {
        Helper.checkString(filePath, "filePath");
        if (contestId < 0) {
            throw new IllegalArgumentException(
                    "ContestId cannot be negative. It is " + contestId);
        }

        File file = new File(filePath);
        File newFolder = new File(file.getParent() + File.separator + contestId);
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }
        file.renameTo(new File(file.getParent() + File.separator + contestId
                + File.separator + file.getName()));
    }
}
