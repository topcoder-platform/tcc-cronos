/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

/**
 * <p>
 * This interface must be implemented by all classes that can extract DependenciesEntry instance with information about
 * the target component and all its dependencies from any source file. This interface also provides a method
 * isSupportedFileType() that can be used for checking whether some extractor can process some file.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface don't have to be thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface DependenciesEntryExtractor {

    /**
     * <p>
     * Extracts the component dependencies entry from the given file.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return the extracted dependencies entry (cannot be null)
     *
     * @throws IllegalArgumentException if filePath is null or empty
     * @throws DependenciesEntryExtractionException if file with the given file doesn't exist, its type is not supported
     *             by the current extractor or a fatal error occurred during the extraction
     */
    public DependenciesEntry extract(String filePath) throws DependenciesEntryExtractionException;

    /**
     * <p>
     * Checks whether the given file can be processed by this extractor.
     * </p>
     * <p>
     * Note: Implementations don't have to provide a check of file content. They can just check file name and extension.
     * Also implementations are not required to check whether file exists.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return true if file format is supported by this extractor, false otherwise
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public boolean isSupportedFileType(String filePath);
}
