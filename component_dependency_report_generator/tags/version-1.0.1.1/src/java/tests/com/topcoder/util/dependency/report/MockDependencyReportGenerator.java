/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

/**
 * <p>
 * The mock implementation of <code>BaseDependencyReportGenerator</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDependencyReportGenerator extends BaseDependencyReportGenerator {

    /**
     * <p>
     * The list of <code>DependenciesEntry</code> to write.
     * </p>
     */
    private List < DependenciesEntry > entriesToWrite;

    /**
     * <p>
     * Creates an instance of <code>MockDependencyReportGenerator</code> with the given dependencies and
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
     *         Or the value is empty; Or the boolean value is neither "true" nor "false", case sensitive;
     *         Or the enum value does not present a valid enum, case insensitive).
     */
    public MockDependencyReportGenerator(List < DependenciesEntry > dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * <p>
     * Creates an instance of <code>MockDependencyReportGenerator</code> with the given dependencies persistence and
     * configuration object.
     * </p>
     *
     * <p>
     * Simply delegates to super.
     * </p>
     *
     * @param persistence The component dependencies persistence to be used (cannot be null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>persistence</code> is null. Or if <code>configuration</code> is null.
     * @throws DependencyReportGenerationException If error occurs while accessing the dependencies persistence.
     * @throws DependencyReportConfigurationException If error occurs while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the value is empty; Or the boolean value is neither "true" nor "false", case sensitive;
     *         Or the enum value does not present a valid enum, case insensitive).
     */
    public MockDependencyReportGenerator(DependenciesEntryPersistence persistence, ConfigurationObject configuration)
        throws DependencyReportGenerationException {
        super(persistence, configuration);
    }

    /**
     * <p>
     * Mock implementation of <code>writeReport()</code>.
     * </p>
     *
     * @param entries The list of dependency entries that hold data of the generated report
     *        (cannot be null; cannot contain null).
     * @param os The output stream for the generated report (cannot be null).
     *
     * @throws IllegalArgumentException If <code>entries</code> is null or contains null.
     *         Or <code>os</code> is null.
     * @throws DependencyReportGenerationException Never.
     */
    @Override
    protected void writeReport(List < DependenciesEntry > entries, OutputStream os)
        throws DependencyReportGenerationException {
        Helper.checkCollection(entries, "The list of dependency entries", true);
        Helper.checkNull(os, "OutputStream should not be null");

        entriesToWrite = entries;
    }

    /**
     * <p>
     * Gets the list of <code>DependenciesEntry</code> to write.
     * </p>
     *
     * @return The list of <code>DependenciesEntry</code> to write.
     */
    public List < DependenciesEntry > getEntriesToWrite() {
        return entriesToWrite;
    }

    /**
     * <p>
     * Gets the map of dependencies.
     * </p>
     *
     * @return The map of dependencies.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public Map < String, DependenciesEntry > getDependencies() throws Exception {
        return (Map) this.getField("dependencies");
    }

    /**
     * <p>
     * Gets the map of cached results.
     * </p>
     *
     * @return The map of cached results.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public Map < String, DependenciesEntry > getCachedResults() throws Exception {
        return (Map) this.getField("cachedResults");
    }

    /**
     * <p>
     * Gets the set of allowed dependency types.
     * </p>
     *
     * @return The set of allowed dependency types.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public Set < DependencyType > getAllowedDependencyTypes() throws Exception {
        return (Set) this.getField("allowedDependencyTypes");
    }

    /**
     * <p>
     * Gets the set of allowed dependency categories.
     * </p>
     *
     * @return The set of allowed dependency categories.
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public Set < DependencyCategory > getAllowedDependencyCategories() throws Exception {
        return (Set) this.getField("allowedDependencyCategories");
    }

    /**
     * <p>
     * Get the value from private field.
     * </p>
     *
     * @param fieldName The name of field.
     * @return the value of the field.
     *
     * @throws Exception to JUnit.
     */
    private Object getField(String fieldName) throws Exception {
        Field field = BaseDependencyReportGenerator.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this);
    }
}
