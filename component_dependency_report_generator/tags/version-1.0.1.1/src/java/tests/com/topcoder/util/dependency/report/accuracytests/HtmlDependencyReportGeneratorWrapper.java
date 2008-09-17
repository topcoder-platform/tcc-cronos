/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import java.io.OutputStream;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGenerator;

/**
 * <p>
 * This class is the wrapper class for <code>HtmlDependencyReportGenerator</code> for accuracy test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HtmlDependencyReportGeneratorWrapper extends HtmlDependencyReportGenerator {
    /**
     * <p>
     * Creates an instance of CsvDependencyReportGenerator with the given dependencies and configuration
     * object.
     * </p>
     *
     * @param dependencies the list of input dependency entries for this class (cannot be null or empty;
     *            cannot contain null)
     * @param configuration the configuration object for this class (cannot be null)
     * @throws IllegalArgumentException if dependencies is null, empty or contains null or if configuration is
     *             null
     * @throws DependencyReportConfigurationException if error occurred while reading the configuration (e.g.
     *             required parameter is missing)
     */
    public HtmlDependencyReportGeneratorWrapper(List<DependenciesEntry> dependencies,
        ConfigurationObject configuration) throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * <p>
     * Creates an instance of CsvDependencyReportGenerator from the given dependencies persistence and
     * configuration object.
     * </p>
     *
     * @param configuration the configuration object for this class (cannot be null)
     * @param persistence the component dependencies persistence to be used (cannot be null)
     * @throws IllegalArgumentException if persistence or configuration is null
     * @throws DependencyReportConfigurationException if error occurred while reading the configuration (e.g.
     *             required parameter is missing)
     * @throws DependencyReportGenerationException if error occurred while accessing the dependencies
     *             persistence
     */
    public HtmlDependencyReportGeneratorWrapper(DependenciesEntryPersistence persistence,
        ConfigurationObject configuration) throws DependencyReportGenerationException {
        super(persistence, configuration);
    }

    /**
     * <p>
     * Writes the data from the provided entries to the output stream with use of the CSV format.
     * </p>
     *
     * @param os the output stream for the generated report (cannot be null)
     * @param entries the list of dependency entries that hold data of the generated report (cannot be null;
     *            cannot contain null)
     * @throws IllegalArgumentException if entries is null or contains null or os is null
     * @throws DependencyReportGenerationException if error occurred while writing the report
     */
    public void writeReport(List<DependenciesEntry> entries, OutputStream os)
        throws DependencyReportGenerationException {
        super.writeReport(entries, os);
    }
}
