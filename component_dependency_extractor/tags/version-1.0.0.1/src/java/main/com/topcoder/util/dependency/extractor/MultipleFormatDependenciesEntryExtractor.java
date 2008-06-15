/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.util.dependency.BaseDependenciesEntryExtractor;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.Utils;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This implementation of DependenciesEntryExtractor supports multiple source file formats while extracting dependencies
 * from them. This class just holds the list of other BaseDependenciesEntryExtractor subclasses. When the user calls
 * extract(), it finds an extractor that can process the given file (with use of isSupportedFileType() method) and
 * passes user's call to it. Logging is used in this class if the user provides a Log instance in the constructor.
 * Actually, logging is not done explicitly in this class; all logging functionality is provided by dependent
 * BaseDependenciesEntryExtractor subclasses. By default, this class holds DotNetDependenciesEntryExtractor and
 * JavaDependenciesEntryExtractor instances, i.e. can process both Java and .NET component dependencies. Thus, by
 * default, it supports build.xml, default.build, *.jar and *???? files as input.
 * </p>
 * <p>
 * <b>Sample Usage</b>
 *
 * <pre>
 * // Extract Java and .NET dependencies with a single multi-format extractor
 * extractor = new MultipleFormatDependenciesEntryExtractor(null);
 * // Get source file name
 * String fileName = DEMO_DIR + File.separator + &quot;build.xml&quot;;
 * // Check whether file format is supported
 * boolean isSupported = extractor.isSupportedFileType(fileName);
 * // Extract dependencies
 * if (isSupported) {
 *     entry = extractor.extract(fileName);
 * }
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe. Also it uses subclasses of
 * BaseDependenciesEntryExtractor that are not thread safe. The user must synchronize calls to all its methods to use it
 * in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class MultipleFormatDependenciesEntryExtractor extends BaseDependenciesEntryExtractor {

    /**
     * <p>
     * The list of extractors that are used to extract dependencies from files. Collection instance is initialized in
     * the constructor and never changed after that. Cannot be null or empty, cannot contain null. Is used in extract()
     * and isSupportedFileType().
     * </p>
     */
    private final List<BaseDependenciesEntryExtractor> extractors = new ArrayList<BaseDependenciesEntryExtractor>();

    /**
     * <p>
     * The logger to be used by this class. Is initialized in the constructor and and can be modified in setLogger().
     * Can be null if logging is not required. Is used in all methods of this class.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Creates an instance of MultipleFormatDependenciesEntryExtractor with the given logger and default extractors.
     * </p>
     *
     * @param logger the logger to be used by this class (can be null if logging is not required)
     */
    public MultipleFormatDependenciesEntryExtractor(Log logger) {
        super();
        this.logger = logger;
        extractors.add(new JavaDependenciesEntryExtractor(logger));
        extractors.add(new DotNetDependenciesEntryExtractor(logger));
    }

    /**
     * <p>
     * Creates an instance of MultipleFormatDependenciesEntryExtractor with the given logger and extractors.
     * </p>
     *
     * @param extractors the list of extractors to be used by this class (cannot be null or empty, cannot contain null)
     * @param logger the logger to be used by this class (can be null if logging is not required)
     *
     * @throws IllegalArgumentException if extractors is null or empty or contains null
     */
    public MultipleFormatDependenciesEntryExtractor(Log logger, List<BaseDependenciesEntryExtractor> extractors) {
        super();
        Utils.checkCollection(extractors, "extractors", false);
        this.extractors.addAll(extractors);
        setLogger(logger);
    }

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
    public DependenciesEntry extract(String filePath) throws DependenciesEntryExtractionException {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        for (BaseDependenciesEntryExtractor extractor : extractors) {
            if (extractor.isSupportedFileType(filePath)) {
                extractor.setExtractedTypes(extractedTypes);
                extractor.setExtractedCategories(extractedCategories);
                return extractor.extract(filePath);
            }
        }

        throw new DependenciesEntryExtractionException("file<" + filePath + "> is not supported.");
    }

    /**
     * <p>
     * Checks whether the given file can be processed by this extractor.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return true if file format is supported by this extractor, false otherwise
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public boolean isSupportedFileType(String filePath) {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        for (BaseDependenciesEntryExtractor extractor : extractors) {
            if (extractor.isSupportedFileType(filePath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Sets the logger for this extractor.
     * </p>
     *
     * @param logger the logger to be used (null if logging is not required)
     */
    public void setLogger(Log logger) {
        this.logger = logger;
        for (BaseDependenciesEntryExtractor extractor : extractors) {
            if (extractor instanceof DotNetDependenciesEntryExtractor) {
                ((DotNetDependenciesEntryExtractor) extractor).setLogger(logger);
            } else if (extractor instanceof JavaDependenciesEntryExtractor) {
                ((JavaDependenciesEntryExtractor) extractor).setLogger(logger);
            }
        }
    }
}
