/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor.fileprovider;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.topcoder.util.dependency.Utils;
import com.topcoder.util.dependency.extractor.BuildFileProvider;
import com.topcoder.util.dependency.extractor.BuildFileProvisionException;

/**
 * <p>
 * This implementation of BuildFileProvider retrieves relative build files from ZIP-archive. It is used by
 * DotNetDependenciesEntryExtractor and JavaDependenciesEntryExtractor when dependencies are extracted from a ZIP/JAR
 * component distribution file.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ZipBuildFileProvider implements BuildFileProvider {
    /**
     * <p>
     * Allowed current path shortcut.
     * </p>
     */
    private static final String CURRENT_PATH = "./";

    /**
     * <p>
     * Length of current path shortcut.
     * </p>
     */
    private static final int CURRENT_PATH_LENGTH = CURRENT_PATH.length();

    /**
     * <p>
     * Allowed parent path shortcut.
     * </p>
     */
    private static final String PARENT_PATH = "../";

    /**
     * <p>
     * Length of parent path shortcut.
     * </p>
     */
    private static final int PARENT_PATH_LENGTH = PARENT_PATH.length();

    /**
     * <p>
     * The path of ZIP entry for the main build file. All relative paths are transformed to absolute one with use of
     * this path as the relative point. Is initialized in the constructor and never changed after that. Cannot be null
     * or empty. Is used in getFileStream().
     * </p>
     */
    private final String currentZipEntryPath;

    /**
     * <p>
     * The ZIP file that holds encoded build files. Is initialized in the constructor and never changed after that.
     * Cannot be null. Is used in getFileStream() and close().
     * </p>
     */
    private final ZipFile zipFile;

    /**
     * <p>
     * Creates an instance of ZipBuildFileProvider with the given ZIP-file path and ZIP-entry path of the main build
     * file.
     * </p>
     *
     * @param currentZipEntryPath the ZIP-entry path of the main build file (cannot be null or empty)
     * @param zipFilePath the path of ZIP-file (cannot be null or empty)
     *
     * @throws IllegalArgumentException if zipFilePath or currentZipEntryPath is null or empty
     * @throws BuildFileProvisionException if error occurred while accessing the ZIP-file (e.g. file doesn't exists or
     *             has invalid format)
     */
    public ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath) throws BuildFileProvisionException {
        Utils.checkStringNullOrEmpty(zipFilePath, "zip fileName");
        Utils.checkStringNullOrEmpty(currentZipEntryPath, "current zip entry path");

        this.currentZipEntryPath = currentZipEntryPath;
        try {
            zipFile = new ZipFile(zipFilePath);
        } catch (IOException e) {
            throw new BuildFileProvisionException("IOException occurs when trying to construct ZipFile with path<"
                + zipFilePath + "> :" + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Retrieves the stream for the file with the given relative path. All paths are within zip/jar file virtual file
     * directory.
     * </p>
     * <p>
     * Note that each ZIP-file can have its own path separator. Thus when retrieving zip entry, we must first try
     * forward slash ('\') as a path separator, and then a backslash ('/').
     * </p>
     *
     * @param relativeFilePath the relative path of the file to be retrieved (cannot be null; empty if main file must be
     *            retrieved)
     * @return the input stream for the requested file (cannot be null)
     *
     * @throws IllegalArgumentException if relativeFilePath is null
     * @throws BuildFileProvisionException if error occurred while retrieving the file stream (e.g. file doesn't exist
     *             or ZIP file is already closed)
     */
    public InputStream getFileStream(String relativeFilePath) throws BuildFileProvisionException {
        Utils.checkNull(relativeFilePath, "relativeFilePath");
        String savedRelativeFilePath = relativeFilePath;
        String currentFilePath = currentZipEntryPath.replace('\\', '/');
        relativeFilePath = relativeFilePath.replace('\\', '/');

        String filePath = null;
        if (relativeFilePath.length() == 0) {
            // "" so just uses current file path
            filePath = currentFilePath;
        } else if (relativeFilePath.startsWith("/")) {
            // consider as absolute path in zip virtual folder
            filePath = relativeFilePath;
        } else {
            // see http://forums.topcoder.com/?module=Thread&threadID=615109&start=0
            String currentPath = getParentPath(currentFilePath);
            relativeFilePath = removeCurrentPathShortCuts(relativeFilePath);
            while (relativeFilePath.startsWith(PARENT_PATH)) {
                if (currentPath.length() == 0) {
                    throw new BuildFileProvisionException("Path<" + savedRelativeFilePath + "> is invalid.");
                }
                currentPath = getParentPath(currentPath);
                relativeFilePath = relativeFilePath.substring(PARENT_PATH_LENGTH);
                relativeFilePath = removeCurrentPathShortCuts(relativeFilePath);
            } // while
            filePath = currentPath + "/" + relativeFilePath;
        }

        // IMPORTANT: in zip file, the top folder can not start with "/" so we need to remove it
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }

        ZipEntry entry = zipFile.getEntry(filePath);
        if (entry == null) {
            filePath = filePath.replace('/', '\\');
            entry = zipFile.getEntry(filePath);
        }
        if (entry == null) {
            throw new BuildFileProvisionException("It can not retrieve file entry for relativeFilePath<"
                + savedRelativeFilePath + "> when converted to <" + filePath + ">.");
        }
        try {
            return zipFile.getInputStream(entry);
        } catch (IOException e) {
            throw new BuildFileProvisionException("IOException occurs when get stream for file entry<" + filePath
                + "> :" + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Gets current path from given filePath.
     * </p>
     *
     * @param filePath file path. It is not null and only contains "/".
     * @return current path. It could be empty if no path could be found
     */
    private String getParentPath(String filePath) {
        int lastSlashIndex = filePath.lastIndexOf("/");
        if (lastSlashIndex == -1) {
            return "";
        } else {
            return filePath.substring(0, lastSlashIndex);
        }
    }

    /**
     * <p>
     * Removes all current path shortcuts currently exposed in the beginning.
     * </p>
     *
     * @param filePath file path to be handled
     * @return file path without any current path short cut
     */
    private String removeCurrentPathShortCuts(String filePath) {
        while (filePath.startsWith(CURRENT_PATH)) {
            filePath = filePath.substring(CURRENT_PATH_LENGTH);
        }

        return filePath;
    }

    /**
     * <p>
     * Closes the current provider.
     * </p>
     */
    public void close() {
        try {
            zipFile.close();
        } catch (IOException e) {
            // silently ignore
        }
    }
}
