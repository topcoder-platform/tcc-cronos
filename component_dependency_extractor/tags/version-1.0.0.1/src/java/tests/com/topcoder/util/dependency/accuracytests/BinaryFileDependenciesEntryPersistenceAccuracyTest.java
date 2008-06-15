/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;

/**
 * <p>
 * Accuracy tests for class BinaryFileDependenciesEntryPersistence.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
public class BinaryFileDependenciesEntryPersistenceAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents instance for test.
     * </p>
     */
    private BinaryFileDependenciesEntryPersistence persistence;

    /**
     * <p>
     * The configuration object.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * The dependencies entries.
     * </p>
     */
    private List<DependenciesEntry> entries;

    /**
     * <p>
     * The file path.
     * </p>
     */
    private String filePath;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to test case.
     */
    protected void setUp() throws Exception {
        filePath = "test_files/accuracytests/BinaryFileDependenciesEntryPersistence.data";
        configuration = new DefaultConfigurationObject("default");
        configuration.setPropertyValue("file_name", filePath);

        entries = AccuracyTestHelper.getEntries();

        super.setUp();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to test case.
     */
    protected void tearDown() throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            file.getCanonicalFile().delete();
        }
        super.tearDown();
    }

    /**
     * <p>
     * Test method save().
     * </p>
     * <p>
     * Should be saved to the filePath.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSave() throws Exception {
        persistence = new BinaryFileDependenciesEntryPersistence(filePath);

        persistence.save(entries);

        List<DependenciesEntry> result = persistence.load();

        AccuracyTestHelper.validDependenciesEntry(entries, result);
    }

    /**
     * <p>
     * Test method save().
     * </p>
     * <p>
     * Empty entries, should be saved to the filePath.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveEmpty1() throws Exception {
        persistence = new BinaryFileDependenciesEntryPersistence(filePath);

        entries.clear();
        persistence.save(entries);

        List<DependenciesEntry> result = persistence.load();

        AccuracyTestHelper.validDependenciesEntry(entries, result);
    }

    /**
     * <p>
     * Test method save().
     * </p>
     * <p>
     * Empty dependencies, should be saved to the filePath.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveEmpty2() throws Exception {
        persistence = new BinaryFileDependenciesEntryPersistence(filePath);

        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setDependencies(new ArrayList<ComponentDependency>());
        }
        persistence.save(entries);

        List<DependenciesEntry> result = persistence.load();

        AccuracyTestHelper.validDependenciesEntry(entries, result);
    }

}
