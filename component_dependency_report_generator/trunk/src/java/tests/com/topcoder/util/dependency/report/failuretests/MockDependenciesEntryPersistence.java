/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.failuretests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;

/**
 * Mock class implements DependenciesEntryPersistence for test.
 * @author extra
 * @version 1.0
 */
public class MockDependenciesEntryPersistence implements DependenciesEntryPersistence {

    /**
     * Whether error would occur.
     */
    private boolean error = false;

    /**
     * All the entries.
     */
    private List<DependenciesEntry> entries;

    /**
     * Default constructor.
     */
    public MockDependenciesEntryPersistence() {
        entries = new ArrayList<DependenciesEntry>();
    }

    /**
     * Load the entries.
     *
     * @return the entries.
     * @throws DependenciesEntryPersistenceException
     *             if error occurs.
     */
    public List<DependenciesEntry> load() throws DependenciesEntryPersistenceException {
        if (error) {
            throw new DependenciesEntryPersistenceException("Error occurs.");
        }
        return this.entries;
    }

    /**
     * Save the entries.
     *
     * @param entries
     *            the entries
     * @throws DependenciesEntryPersistenceException
     *             if error occurs
     * @throws IllegalArgumentException
     *             if argument not valid
     */
    public void save(List<DependenciesEntry> entries) throws DependenciesEntryPersistenceException {
        if (entries == null || entries.contains(null)) {
            throw new IllegalArgumentException("entries should not be null or contain null.");
        }
        if (error) {
            throw new DependenciesEntryPersistenceException("Error occurs.");
        }
        this.entries.clear();
        this.entries.addAll(entries);
    }

    /**
     * Setter for error.
     *
     * @param error
     *            the error
     */
    public void setError(boolean error) {
        this.error = error;
    }
}
