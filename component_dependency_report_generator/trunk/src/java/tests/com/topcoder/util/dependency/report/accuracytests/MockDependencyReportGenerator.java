package com.topcoder.util.dependency.report.accuracytests;

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
 * <p>
 * This class is the mock dependency report generator class for accuracy test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDependencyReportGenerator extends BaseDependencyReportGenerator {
    /**
     * Represents the <code>isException</code> instance for accuracy test.
     */
    boolean isException = false;

    /**
     * Represents the <code>resultEntries</code> instance for accuracy test.
     */
    List<DependenciesEntry> resultEntries;

    /**
     * Constructs an instance of <code>MockDependencyReportGenerator</code> to take a list of dependencies
     * and configuration.
     *
     * @param dependencies a list of dependencies
     * @param configuration object
     * @throws DependencyReportConfigurationException any error in construction
     */
    protected MockDependencyReportGenerator(List<DependenciesEntry> dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * Constructs an instance of <code>MockDependencyReportGenerator</code> to take a persistence and
     * configuration.
     *
     * @param persistence instance
     * @param configuration object
     * @throws DependencyReportGenerationException any error in construction
     */
    protected MockDependencyReportGenerator(DependenciesEntryPersistence persistence,
        ConfigurationObject configuration) throws DependencyReportGenerationException {
        super(persistence, configuration);
    }

    /**
     * Mock implementation.
     *
     * @param entries a list of entries
     * @param os output stream
     * @throws DependencyReportGenerationException if any error during report generation
     */
    @Override
    protected void writeReport(List<DependenciesEntry> entries, OutputStream os)
        throws DependencyReportGenerationException {
        resultEntries = entries;

        if (isException) {
            throw new DependencyReportGenerationException("mock error");
        }

        try {
            os.write((entries.size() + " entries").getBytes());
            os.flush();
        } catch (IOException e) {
            throw new DependencyReportGenerationException("mock error : " + e.getMessage());
        }
    }

}