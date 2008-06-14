/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;
import com.topcoder.util.dependency.report.Helper;


/**
 * <p>
 * This dependency report generator writes reports in CSV format.
 * </p>
 *
 * <p>
 * It extends <code>BaseDependencyReportGenerator</code>, thus supports generating reports to various destination
 * types, processing indirect dependencies, filtering dependencies by type and category, optional including of
 * dependency type, category and path to the report.
 * </p>
 *
 * <p>
 * This class just overrides <code>writeReport()</code> method of <code>BaseDependencyReportGenerator</code>.
 * It doesn't have additional configuration parameters. See the class document of <code>BaseDependencyReportGenerator
 * </code> for details configuration information.
 * </p>
 *
 * <p>
 *     <strong>Sample API Usage:</strong>
 *     <pre>
 *     //Create an instance with persistence and configuration
 *     CsvDependencyReportGenerator generator = new CsvDependencyReportGenerator(persistence, configuration);
 *
 *     //Or create an instance with pre-loaded dependencies and configuration
 *     generator = new CsvDependencyReportGenerator(List&lt;DependencyEntry&gt;, configuration);
 *
 *     //Generate all reports with indirect dependencies included as string:
 *     String report = generator.generateAll(true);
 *
 *     //Generate all reports without indirect dependencies included as string:
 *     report = generator.generateAll(false);
 *
 *     //Generate all reports with indirect dependencies included to file:
 *     generator.generateAll("FileName", true);
 *
 *     //Generate all reports without indirect dependencies included to file:
 *     generator.generateAll("FileName", false);
 *
 *     //Generate all reports with indirect dependencies included to output stream:
 *     generator.generateAll(OutputStream, true);
 *
 *     //Generate all reports without indirect dependencies included to output stream:
 *     generator.generateAll(OutputStream, false);
 *
 *     //Create a list of component IDs:
 *     List&lt;String&gt; componentIds = ......
 *
 *     //Generate certain reports with indirect dependencies included as string:
 *     String report = generator.generate(componentIds, true);
 *
 *     //Generate certain reports without indirect dependencies included as string:
 *     report = generator.generate(componentIds, false);
 *
 *     //Generate certain reports with indirect dependencies included to file:
 *     generator.generate(componentIds, "FileName", true);
 *
 *     //Generate certain reports without indirect dependencies included to file:
 *     generator.generate(componentIds, "FileName", false);
 *
 *     //Generate certain reports with indirect dependencies included to output stream:
 *     generator.generate(componentIds, OutputStream, true);
 *
 *     //Generate certain reports without indirect dependencies included to output stream:
 *     generator.generate(componentIds, OutputStream, false);
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 *
 * @see BaseDependencyReportGenerator
 */
public class CsvDependencyReportGenerator extends BaseDependencyReportGenerator {

    /**
     * <p>
     * Represents the CRLF.
     * </p>
     */
    private static final String CRLF = "\r\n";

    /**
     * <p>
     * Represents the internal type.
     * </p>
     */
    private static final String INTERNAL = "[int]";

    /**
     * <p>
     * Represents the external type.
     * </p>
     */
    private static final String EXTERNAL = "[ext]";

    /**
     * <p>
     * Creates an instance of <code>CsvDependencyReportGenerator</code> with the given dependencies and
     * configuration object.
     * </p>
     *
     * <p>
     * See class doc of <code>BaseDependencyReportGenerator</code> for sample configuration.
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
     *
     * @see BaseDependencyReportGenerator
     */
    public CsvDependencyReportGenerator(List < DependenciesEntry > dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * <p>
     * Creates an instance of <code>CsvDependencyReportGenerator</code> with the given dependencies persistence and
     * configuration object.
     * </p>
     *
     * <p>
     * See class doc of <code>BaseDependencyReportGenerator</code> for sample configuration.
     * </p>
     *
     * @param persistence The component dependencies persistence to be used (cannot be null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>persistence</code> is null. Or if <code>configuration</code> is null.
     * @throws DependencyReportGenerationException If error occurs while accessing the dependencies persistence.
     * @throws DependencyReportConfigurationException If error occurred while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the property is present but has null/empty value; Or the boolean value is neither "true"
     *         nor "false", case sensitive; Or the enum value does not present a valid enum, case insensitive).
     *
     * @see BaseDependencyReportGenerator
     */
    public CsvDependencyReportGenerator(DependenciesEntryPersistence persistence, ConfigurationObject configuration)
        throws DependencyReportGenerationException  {
        super(persistence, configuration);
    }

    /**
     * <p>
     * Writes the data from the provided entries to the output stream with use of the CSV format.
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
    protected void writeReport(List < DependenciesEntry > entries, OutputStream os)
        throws DependencyReportGenerationException {
        Collection < DependenciesEntry > distinctEntries =
            Helper.checkCollection(entries, "The list of dependency entries", true);
        Helper.checkNull(os, "OutputStream should not be null");

        StringBuilder str = new StringBuilder();

        for (DependenciesEntry entry : distinctEntries) {
            //Append component language, name, version
            str.append(entry.getTargetComponent().getLanguage().name().toLowerCase());
            str.append('-');
            str.append(entry.getTargetComponent().getName());
            str.append('-');
            str.append(entry.getTargetComponent().getVersion());

            for (ComponentDependency dependency : entry.getDependencies()) {
                //For each dependency from entry.getDependencies()
                str.append(',');

                //Append dependency type
                if (this.isDependencyTypeIncluded()) {
                    str.append((dependency.getType() == DependencyType.INTERNAL) ? INTERNAL : EXTERNAL);
                }

                //Append dependency category
                if (this.isDependencyCategoryIncluded()) {
                    str.append("[" + dependency.getCategory().name().toLowerCase() + "]");
                }

                //Append dependency name and version
                str.append(dependency.getName());
                str.append('-');
                str.append(dependency.getVersion());

                //Append dependency path
                if (this.isDependencyPathIncluded()) {
                    str.append("[" + dependency.getPath() + "]");
                }
            }

            //Append CRLF
            str.append(CRLF);
        }

        String csvString = str.toString();
        try {
            //Write to output stream
            Writer writer = new OutputStreamWriter(os);
            writer.write(csvString, 0, csvString.length());
            writer.flush();
        } catch (IOException e) {
            throw new DependencyReportGenerationException("Error occurs while writing CSV report to output stream.", e);
        }
    }
}
