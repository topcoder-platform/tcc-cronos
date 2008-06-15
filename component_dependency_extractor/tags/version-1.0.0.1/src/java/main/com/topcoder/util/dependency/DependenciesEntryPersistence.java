/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.util.List;

/**
 * <p>
 * This interface must be implemented by all classes that provide DependenciesEntry persistence functionality. It
 * provides two methods that can be used to save the list of dependency entries to persistence and load the list of
 * entries from it. Implementations of this interface must provide a constructor that accepts ConfigurationObject as the
 * only argument to be compatible with ComponentDependencyExtractorUtility and Component Dependency Report Generator
 * component. This constructor can throw ComponentDependencyConfigurationException.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface don't have to be thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface DependenciesEntryPersistence {

    /**
     * <p>
     * Loads the list of dependency entries from persistence.
     * </p>
     *
     * @return the loaded list of entries (cannot be null; cannot contain null)
     *
     * @throws DependenciesEntryPersistenceException if error occurred while accessing the persistence
     */
    public List<DependenciesEntry> load() throws DependenciesEntryPersistenceException;

    /**
     * <p>
     * Saves the provided list of dependency entries to persistence.
     * </p>
     *
     * @param entries the list of entries to be saved (cannot be null; cannot contain null)
     *
     * @throws IllegalArgumentException if entries is null or contains null
     * @throws DependenciesEntryPersistenceException if error occurred while accessing the persistence
     */
    public void save(List<DependenciesEntry> entries) throws DependenciesEntryPersistenceException;
}
