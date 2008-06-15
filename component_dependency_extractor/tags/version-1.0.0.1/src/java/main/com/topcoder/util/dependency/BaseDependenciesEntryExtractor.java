/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This abstract class implements DependenciesEntryExtractor interface. This class must be extended by extractors that
 * can extract dependencies of specific type and category. BaseDependenciesEntryExtractor holds sets of dependency types
 * and categories that must be extracted. It also provided setters and getters for them. Getters return shallow copies
 * of sets, thus subclasses should better access them as protected attributes.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because it has mutable collections. The user must synchronize access to
 * setters and getters to use it in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseDependenciesEntryExtractor implements DependenciesEntryExtractor {

    /**
     * <p>
     * The set of dependency types that must be extracted. Collection instance is initialized in the constructor and
     * never changed after that. Cannot be null or empty. Cannot contain null. Is initialized to the full set of
     * DependencyType enum values. Has corresponding setter and getter. Can be directly used by subclasses to increase
     * the performance.
     * </p>
     */
    protected final Set<DependencyType> extractedTypes;

    /**
     * <p>
     * The set of dependency categories that must be extracted. Collection instance is initialized in the constructor
     * and never changed after that. Cannot be null or empty. Cannot contain null. Is initialized to the full set of
     * DependencyCatgory enum values. Has corresponding setter and getter. Can be directly used by subclasses to
     * increase the performance.
     * </p>
     */
    protected final Set<DependencyCategory> extractedCategories;

    /**
     * <p>
     * Creates an instance of BaseDependenciesEntryExtractor.
     * </p>
     */
    protected BaseDependenciesEntryExtractor() {
        extractedTypes = new HashSet<DependencyType>();
        extractedTypes.add(DependencyType.INTERNAL);
        extractedTypes.add(DependencyType.EXTERNAL);
        extractedCategories = new HashSet<DependencyCategory>();
        extractedCategories.add(DependencyCategory.COMPILE);
        extractedCategories.add(DependencyCategory.TEST);
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
     * @throws DependenciesEntryExtractionException if file with the given path doesn't exist, its type is not supported
     *             by the current extractor or a fatal error occurred during the extraction
     */
    public abstract DependenciesEntry extract(String filePath) throws DependenciesEntryExtractionException;

    /**
     * <p>
     * Checks whether the given file can be processed by this extractor.
     * </p>
     * <p>
     * Implementations don't have to provide a check of file content. They can just check file name and extension. Also
     * implementations are not required to check whether file exists.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return true if file format is supported by this extractor, false otherwise
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public abstract boolean isSupportedFileType(String filePath);

    /**
     * <p>
     * Sets the dependency types that must be extracted.
     * </p>
     *
     * @param types the set of dependency types that must be extracted (cannot be null or empty, cannot contain null)
     *
     * @throws IllegalArgumentException if types is null or empty or contains null
     */
    public void setExtractedTypes(Set<DependencyType> types) {
        Utils.checkCollection(types, "types", false);
        this.extractedTypes.clear();
        this.extractedTypes.addAll(types);
    }

    /**
     * <p>
     * Sets the dependency categories that must be extracted.
     * </p>
     *
     * @param categories the set of dependency categories that must be extracted (cannot be null or empty, cannot
     *            contain null)
     *
     * @throws IllegalArgumentException if categories is null or empty or contains null
     */
    public void setExtractedCategories(Set<DependencyCategory> categories) {
        Utils.checkCollection(categories, "categories", false);
        this.extractedCategories.clear();
        this.extractedCategories.addAll(categories);
    }

    /**
     * <p>
     * Retrieves the dependency types that must be extracted.
     * </p>
     *
     * @return the set of dependency types that must be extracted (cannot be null or empty, cannot contain null)
     */
    public Set<DependencyType> getExtractedTypes() {
        return new HashSet<DependencyType>(this.extractedTypes);
    }

    /**
     * <p>
     * Retrieves the set of dependency categories that must be extracted.
     * </p>
     *
     * @return the set of dependency categories that must be extracted (cannot be null or empty, cannot contain null)
     */
    public Set<DependencyCategory> getExtractedCategories() {
        return new HashSet<DependencyCategory>(this.extractedCategories);
    }
}
