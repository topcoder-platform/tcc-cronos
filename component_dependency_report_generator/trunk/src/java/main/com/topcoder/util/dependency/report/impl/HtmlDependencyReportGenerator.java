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
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;
import com.topcoder.util.dependency.report.Helper;


/**
 * <p>
 * This dependency report generator writes reports in HTML format.
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
 * It doesn't have additional configuration parameters.
 * </p>
 *
 * <p>
 * HTML format (unlike XML and CSV formats) is the most user-oriented report format. Reports in HTML format are
 * more comfortable and easy-to-read. HTML tables are used to combine the dependency information in the most
 * suitable manner.
 * </p>
 *
 * <p>
 *     <strong>Sample API Usage:</strong>
 *     <pre>
 *     //Create an instance with persistence and configuration
 *     HtmlDependencyReportGenerator generator = new HtmlDependencyReportGenerator(persistence, configuration);
 *
 *     //Or create an instance with pre-loaded dependencies and configuration
 *     generator = new HtmlDependencyReportGenerator(List&lt;DependencyEntry&gt;, configuration);
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
public class HtmlDependencyReportGenerator extends BaseDependencyReportGenerator {

    /**
     * <p>
     * Represents the Java language.
     * </p>
     */
    private static final String JAVA = "Java";

    /**
     * <p>
     * Represents the .NET language.
     * </p>
     */
    private static final String NET = ".NET";

    /**
     * <p>
     * Creates an instance of <code>HtmlDependencyReportGenerator</code> with the given dependencies and
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
    public HtmlDependencyReportGenerator(List < DependenciesEntry > dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {
        super(dependencies, configuration);
    }

    /**
     * <p>
     * Creates an instance of <code>HtmlDependencyReportGenerator</code> with the given dependencies persistence and
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
    public HtmlDependencyReportGenerator(DependenciesEntryPersistence persistence, ConfigurationObject configuration)
        throws DependencyReportGenerationException {
        super(persistence, configuration);
    }

    /**
     * <p>
     * Writes the data from the provided entries to the output stream with use of the HTML format.
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

        //Calculate the dependencies columns number
        int dependenciesColsNum = 2;
        if (this.isDependencyTypeIncluded()) {
            dependenciesColsNum++;
        }
        if (this.isDependencyCategoryIncluded()) {
            dependenciesColsNum++;
        }
        if (this.isDependencyPathIncluded()) {
            dependenciesColsNum++;
        }

        //Construct header
        String headerString = this.constructHeader(dependenciesColsNum);

        //Construct body
        String bodyString = this.constructBody(distinctEntries, dependenciesColsNum);

        String footerString = "</table></body></html>";

        try {
            //Write to output stream
            Writer writer = new OutputStreamWriter(os);
            writer.write(headerString, 0, headerString.length());
            writer.write(bodyString, 0, bodyString.length());
            writer.write(footerString, 0, footerString.length());
            writer.flush();
        } catch (IOException e) {
            throw new DependencyReportGenerationException(
                "Error occurs while writing HTML report to output stream.", e);
        }
    }

    /**
     * <p>
     * Construct the body.
     * </p>
     *
     * @param distinctEntries The collection of distinct dependencies entries.
     * @param dependenciesColsNum The columns number of dependencies.
     *
     * @return The body.
     */
    private String constructBody(Collection < DependenciesEntry > distinctEntries, int dependenciesColsNum) {
        StringBuilder str = new StringBuilder();

        for (DependenciesEntry entry : distinctEntries) {
            int dependencyNum = entry.getDependencies().size();
            dependencyNum = Math.max(1, dependencyNum);

            //Append component language, name, version
            str.append("<tr><td rowspan=\"" + dependencyNum + "\">");
            str.append((entry.getTargetComponent().getLanguage() == ComponentLanguage.JAVA) ? JAVA : NET);
            str.append("</td><td rowspan=\"" + dependencyNum + "\">" + entry.getTargetComponent().getName()
                + "</td><td rowspan=\"" + dependencyNum + "\">" + entry.getTargetComponent().getVersion() + "</td>");

            int index = 0;
            for (ComponentDependency dependency : entry.getDependencies()) {
                //For 1-N dependencies, start a new row
                if (index > 0) {
                    str.append("<tr>");
                }

                //Append dependency name and version
                str.append("<td>" + dependency.getName() + "</td>"
                        + "<td>" + dependency.getVersion() + "</td>");

                //Append dependency type
                if (this.isDependencyTypeIncluded()) {
                    str.append("<td>" + dependency.getType().name().toLowerCase() + "</td>");
                }

                //Append dependency category
                if (this.isDependencyCategoryIncluded()) {
                    str.append("<td>" + dependency.getCategory().name().toLowerCase() + "</td>");
                }

                //Append dependency path
                if (this.isDependencyPathIncluded()) {
                    str.append("<td>" + dependency.getPath() + "</td>");
                }

                str.append("</tr>");

                index++;
            }

            if (index == 0) {
                //If component has no dependencies
                str.append("<td colspan=\"" + dependenciesColsNum + "\">&nbsp;</td></tr>");
            }
        }

        return str.toString();
    }

    /**
     * <p>
     * Construct the header.
     * </p>
     *
     * @param dependenciesColsNum The columns number of dependencies.
     *
     * @return The header.
     */
    private String constructHeader(int dependenciesColsNum) {
        StringBuilder str = new StringBuilder();
        str.append("<html><head><title>Component Dependency Report</title></head><body><table border=\"1\">"
            + "<tr align=\"center\"><td colspan=\"3\">Component</td><td colspan=\"");
        str.append(dependenciesColsNum);
        str.append("\">Dependencies</td></tr><tr align=\"center\"><td>Language</td><td>Name</td><td>Version</td>"
            + "<td>Name</td><td>Version</td>");

        //Append type header
        if (this.isDependencyTypeIncluded()) {
            str.append("<td>Type</td>");
        }

        //Append category header
        if (this.isDependencyCategoryIncluded()) {
            str.append("<td>Category</td>");
        }

        //Append path header
        if (this.isDependencyPathIncluded()) {
            str.append("<td>Path</td>");
        }

        str.append("</tr>");

        return str.toString();
    }
}
