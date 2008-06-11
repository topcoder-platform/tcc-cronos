/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.io.InputStream;

/**
 * This interface must be implemented by classes that can provide build files to DotNetDependenciesEntryExtractor and
 * JavaDependenciesEntryExtractor and other possible implementations of DependenciesEntryExtractor from various source.
 * Each provider must hold the virtual path of the main build file and should be able to retrieve this file or other
 * file by its relative path. This interface allows implementations of DependenciesEntryExtractor to provide the same
 * algorithm for extracting dependencies from both build files and distribution archive files.
 *
 * Thread Safety: Implementations of this interface don't have to be thread safe.
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface BuildFileProvider {

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
    public InputStream getFileStream(String relativeFilePath) throws BuildFileProvisionException;

    /**
     * <p>
     * Closes the current provider.
     * </p>
     */
    public void close();
}
