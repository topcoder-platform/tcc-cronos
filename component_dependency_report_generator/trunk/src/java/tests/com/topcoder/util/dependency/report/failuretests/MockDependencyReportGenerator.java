/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.failuretests;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;

/**
 * Mock class extends BaseDependencyReportGenerator for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockDependencyReportGenerator extends BaseDependencyReportGenerator {

    /**
     * The constructor With the given dependencies and configuration object.
     *
     * @param dependencies
     *            The list of input dependency entries
     * @param configuration
     *            The configuration object
     * @throws IllegalArgumentException
     *             If dependencies is null, empty or contains null element, or configuration is null.
     * @throws DependencyReportConfigurationException
     *             If error occurred while reading the configuration.
     */
    public MockDependencyReportGenerator(List<DependenciesEntry> dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * The constructor.
     *
     * @param persistence
     *            the component dependencies persistence
     * @param configuration
     *            the configuration object
     * @throws IllegalArgumentException
     *             If <code>persistence</code> is null. Or if <code>configuration</code> is null.
     * @throws DependencyReportConfigurationException
     *             If error occurred while reading the configuration.
     * @throws DependencyReportGenerationException
     *             If error occurs while accessing the dependencies persistence.
     */
    public MockDependencyReportGenerator(DependenciesEntryPersistence persistence, ConfigurationObject configuration)
        throws DependencyReportGenerationException {
        super(persistence, configuration);
    }

    /**
     * Writes the data from the provided entries to the output stream with use of the custom format.
     *
     * @param entries
     *            The list of dependency entries that hold data of the generated report
     * @param os
     *            The output stream for the generated report
     * @throws IllegalArgumentException
     *             If entries is null or contains null, or os is null.
     * @throws DependencyReportGenerationException
     *             If error occurred while writing the report.
     */
    protected void writeReport(List<DependenciesEntry> entries, OutputStream os)
        throws DependencyReportGenerationException {
        if (entries == null || entries.contains(null)) {
            throw new IllegalArgumentException("the entries should not be null or contain null element.");
        }
        if (os == null) {
            throw new IllegalArgumentException("the os should not be null.");
        }
        try {
            os.write(entries.size());
            os.flush();
        } catch (IOException e) {
            throw new DependencyReportGenerationException("Error occurs while writing the report.", e);
        }
    }

}
