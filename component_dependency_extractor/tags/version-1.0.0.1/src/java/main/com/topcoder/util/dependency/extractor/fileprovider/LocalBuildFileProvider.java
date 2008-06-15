/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor.fileprovider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.topcoder.util.dependency.Utils;
import com.topcoder.util.dependency.extractor.BuildFileProvider;
import com.topcoder.util.dependency.extractor.BuildFileProvisionException;

/**
 * <p>
 * This implementation of BuildFileProvider retrieves relative build files from the local file system. This provider can
 * be used when the main build file and its dependency files are stored in usual file system on the local disk.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class LocalBuildFileProvider implements BuildFileProvider {
    /**
     * <p>
     * The name of the main file for this provider. All relative paths are transformed to absolute one with use of this
     * path as the relative point. Is initialized in the constructor and never changed after that. Cannot be null or
     * empty. Is used in getFileStream().
     * </p>
     */
    private final String currentFilePath;

    /**
     * <p>
     * Creates an instance of LocalBuildFileProvider with the given current file path.
     * </p>
     *
     * @param currentFilePath the path of the main build file (cannot be null or empty)
     *
     * @throws IllegalArgumentException if currentFilePath is null or empty
     */
    public LocalBuildFileProvider(String currentFilePath) {
        Utils.checkStringNullOrEmpty(currentFilePath, "currentFilePath");
        this.currentFilePath = currentFilePath;
    }

    /**
     * <p>
     * Retrieves the stream for the file with the given relative path.
     * </p>
     *
     * @param relativeFilePath the relative path of the file to be retrieved (cannot be null; empty if main file must be
     *            retrieved)
     * @return the input stream for the requested file (cannot be null)
     *
     * @throws IllegalArgumentException if relativeFilePath is null
     * @throws BuildFileProvisionException if error occurred while retrieving the file stream (e.g. file doesn't exist)
     */
    public InputStream getFileStream(String relativeFilePath) throws BuildFileProvisionException {
        Utils.checkNull(relativeFilePath, "relativeFilePath");

        String filePath = null;
        if (relativeFilePath.length() == 0) {
            // "" so just uses current file path
            filePath = currentFilePath;
        } else if (relativeFilePath.startsWith("/") || relativeFilePath.startsWith("\\")
            || relativeFilePath.contains(":")) {
            // it is considered as an absolute path
            filePath = relativeFilePath;
        } else {
            // it is different from original implementation note because we assume local file path could be expanded
            // see http://forums.topcoder.com/?module=Thread&threadID=615072&start=0&mc=14#983032
            filePath = getPath(currentFilePath) + relativeFilePath;
        }
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new BuildFileProvisionException("FileNotFoundException occurs when get stream for file<"
                + relativeFilePath + "> :" + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Gets current path from given filePath.
     * </p>
     *
     * @param filePath file path
     * @return current path. It could be empty if no path could be found
     */
    private String getPath(String filePath) {
        int lastSlashIndex = Math.max(filePath.lastIndexOf("/"), filePath.lastIndexOf("\\"));
        if (lastSlashIndex == -1) {
            // if it is just file name, we just return "" which most likely will assume current working directory as
            // default directory
            return "";
        } else {
            return filePath.substring(0, lastSlashIndex) + File.separator;
        }
    }

    /**
     * <p>
     * Closes the current provider.
     * </p>
     */
    public void close() {
        // Do nothing.
    }
}
