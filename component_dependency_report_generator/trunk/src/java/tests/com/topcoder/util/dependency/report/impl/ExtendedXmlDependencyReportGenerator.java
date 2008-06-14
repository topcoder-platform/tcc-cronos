/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.OutputStream;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;

/**
 * <p>
 * This class is used to expose the protected <code>writeReport()</code> method.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ExtendedXmlDependencyReportGenerator extends XmlDependencyReportGenerator {

    /**
     * <p>
     * Creates an instance of <code>ExtendedXmlDependencyReportGenerator</code> with the given dependencies and
     * configuration object.
     * </p>
     *
     * <p>
     * Simply delegates to super.
     * </p>
     *
     * @param dependencies The list of input dependency entries for this class (cannot be null or empty;
     *        cannot contain null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>dependencies</code> is null, empty or contains null element.
     *         Or if <code>configuration</code> is null.
     * @throws DependencyReportConfigurationException If error occurred while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the property is present but has null/empty value; Or the boolean value is neither "true"
     *         nor "false", case sensitive; Or the enum value does not present a valid enum, case insensitive).
     */
    public ExtendedXmlDependencyReportGenerator(List < DependenciesEntry > dependencies,
        ConfigurationObject configuration) throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * <p>
     * Creates an instance of <code>ExtendedXmlDependencyReportGenerator</code> with the given
     * dependencies persistence and configuration object.
     * </p>
     *
     * <p>
     * Simply delegates to super.
     * </p>
     *
     * @param persistence The component dependencies persistence to be used (cannot be null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>persistence</code> is null.
     *         Or if <code>configuration</code> is null.
     * @throws DependencyReportGenerationException If error occurs while accessing the dependencies persistence.
     * @throws DependencyReportConfigurationException If error occurred while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the property is present but has null/empty value; Or the boolean value is neither "true"
     *         nor "false", case sensitive; Or the enum value does not present a valid enum, case insensitive).
     */
    public ExtendedXmlDependencyReportGenerator(DependenciesEntryPersistence persistence,
        ConfigurationObject configuration) throws DependencyReportGenerationException  {
        super(persistence, configuration);
    }

    /**
     * <p>
     * Expose the <code>writeReport()</code> method. Simply delegates to super.
     * </p>
     *
     * @param entries The list of dependency entries that hold data of the generated report
     *        (cannot be null; cannot contain null).
     * @param os The output stream for the generated report (cannot be null).
     *
     * @throws IllegalArgumentException If <code>entries</code> is null or contains null.
     *         Or <code>os</code> is null.
     * @throws DependencyReportGenerationException If error occurred while writing the report.
     */
    @Override
    public void writeReport(List < DependenciesEntry > entries, OutputStream os)
        throws DependencyReportGenerationException {
        super.writeReport(entries, os);
    }
}
