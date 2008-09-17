/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * It extends <code>BaseDependencyReportGenerator</code>, thus supports
 * generating reports to various destination types, processing indirect
 * dependencies, filtering dependencies by type and category, optional including
 * of dependency type, category and path to the report.
 * </p>
 * 
 * <p>
 * This class just overrides <code>writeReport()</code> method of
 * <code>BaseDependencyReportGenerator</code>. It doesn't have additional
 * configuration parameters.
 * </p>
 * 
 * <p>
 * HTML format (unlike XML and CSV formats) is the most user-oriented report
 * format. Reports in HTML format are more comfortable and easy-to-read. HTML
 * tables are used to combine the dependency information in the most suitable
 * manner.
 * </p>
 * 
 * <p>
 * <strong>Sample API Usage:</strong>
 * 
 * <pre>
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
 *     generator.generateAll(&quot;FileName&quot;, true);
 * 
 *     //Generate all reports without indirect dependencies included to file:
 *     generator.generateAll(&quot;FileName&quot;, false);
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
 *     generator.generate(componentIds, &quot;FileName&quot;, true);
 * 
 *     //Generate certain reports without indirect dependencies included to file:
 *     generator.generate(componentIds, &quot;FileName&quot;, false);
 * 
 *     //Generate certain reports with indirect dependencies included to output stream:
 *     generator.generate(componentIds, OutputStream, true);
 * 
 *     //Generate certain reports without indirect dependencies included to output stream:
 *     generator.generate(componentIds, OutputStream, false);
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread safe because its
 * base class is not thread safe.
 * </p>
 * 
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 * 
 * @see BaseDependencyReportGenerator
 */
public class HtmlDependencyReportGenerator extends
		BaseDependencyReportGenerator {

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
	 * Represents the font color to indicate the component has the circular
	 * dependency.
	 * </p>
	 */
	private String circular_color = "000000";

	/**
	 * <p>
	 * Represents the font color to indicate the dependency path is invalid,a
	 * third party library is NOT under ${ext_libdir} and any time a TC library
	 * is not under ${tcs_libdir} .
	 * </p>
	 */
	private String path_warn_color = "000000";

	/**
	 * <p>
	 * Represents the font color to indicate the component has the conflict
	 * dependency, which are the same dependency have different name.
	 * </p>
	 */
	private String version_warn_color = "000000";

	/**
	 * <p>
	 * Get the Circular color.
	 * </p>
	 */
	public String getCircularColor() {
		return this.circular_color;
	}

	/**
	 * <p>
	 * Set the Circular color.
	 * </p>
	 * 
	 * @param the
	 *            circular color string
	 */
	public void setCircularColor(String color) {
		this.circular_color = color;
	}

	/**
	 * <p>
	 * Creates an instance of <code>HtmlDependencyReportGenerator</code> with
	 * the given dependencies and configuration object.
	 * </p>
	 * 
	 * <p>
	 * See class doc of <code>BaseDependencyReportGenerator</code> for sample
	 * configuration.
	 * </p>
	 * 
	 * @param dependencies
	 *            The list of input dependency entries for this class (cannot be
	 *            null or empty; cannot contain null).
	 * @param configuration
	 *            The configuration object for this class (cannot be null).
	 * 
	 * @throws IllegalArgumentException
	 *             If <code>dependencies</code> is null, empty or contains
	 *             null element. Or if <code>configuration</code> is null.
	 * @throws DependencyReportConfigurationException
	 *             If error occurred while reading the configuration (e.g. Some
	 *             property has multiple values; Or the value is not type of
	 *             <code>String</code>; Or the property is present but has
	 *             null/empty value; Or the boolean value is neither "true" nor
	 *             "false", case sensitive; Or the enum value does not present a
	 *             valid enum, case insensitive).
	 * 
	 * @see BaseDependencyReportGenerator
	 */
	public HtmlDependencyReportGenerator(List<DependenciesEntry> dependencies,
			ConfigurationObject configuration)
			throws DependencyReportConfigurationException {
		super(dependencies, configuration);
	}

	/**
	 * <p>
	 * Creates an instance of <code>HtmlDependencyReportGenerator</code> with
	 * the given dependencies persistence and configuration object.
	 * </p>
	 * 
	 * <p>
	 * See class doc of <code>BaseDependencyReportGenerator</code> for sample
	 * configuration.
	 * </p>
	 * 
	 * @param persistence
	 *            The component dependencies persistence to be used (cannot be
	 *            null).
	 * @param configuration
	 *            The configuration object for this class (cannot be null).
	 * 
	 * @throws IllegalArgumentException
	 *             If <code>persistence</code> is null. Or if
	 *             <code>configuration</code> is null.
	 * @throws DependencyReportGenerationException
	 *             If error occurs while accessing the dependencies persistence.
	 * @throws DependencyReportConfigurationException
	 *             If error occurred while reading the configuration (e.g. Some
	 *             property has multiple values; Or the value is not type of
	 *             <code>String</code>; Or the property is present but has
	 *             null/empty value; Or the boolean value is neither "true" nor
	 *             "false", case sensitive; Or the enum value does not present a
	 *             valid enum, case insensitive).
	 * 
	 * @see BaseDependencyReportGenerator
	 */
	public HtmlDependencyReportGenerator(
			DependenciesEntryPersistence persistence,
			ConfigurationObject configuration)
			throws DependencyReportGenerationException {
		super(persistence, configuration);
	}

	/**
	 * <p>
	 * Writes the data from the provided entries to the output stream with use
	 * of the HTML format.
	 * </p>
	 * 
	 * @param entries
	 *            The list of dependency entries that hold data of the generated
	 *            report (cannot be null; cannot contain null).
	 * @param os
	 *            The output stream for the generated report (cannot be null).
	 * 
	 * @throws IllegalArgumentException
	 *             If <code>entries</code> is null or contains null. Or
	 *             <code>os</code> is null.
	 * @throws DependencyReportGenerationException
	 *             If error occurred while writing the report.
	 */
	@Override
	protected void writeReport(List<DependenciesEntry> entries, OutputStream os)
			throws DependencyReportGenerationException {

		Collection<DependenciesEntry> distinctEntries = Helper.checkCollection(
				entries, "The list of dependency entries", true);
		Helper.checkNull(os, "OutputStream should not be null");

		// Calculate the dependencies columns number
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

		// Construct header
		String headerString = this.constructHeader(dependenciesColsNum);

		// Construct body
		String bodyString = this.constructBody(distinctEntries,
				dependenciesColsNum);

		String footerString = "</table></body></html>";

		try {
			// Write to output stream
			Writer writer = new OutputStreamWriter(os);
			writer.write(headerString, 0, headerString.length());
			writer.write(bodyString, 0, bodyString.length());
			writer.write(footerString, 0, footerString.length());
			writer.flush();
		} catch (IOException e) {
			throw new DependencyReportGenerationException(
					"Error occurs while writing HTML report to output stream.",
					e);
		}
	}

	/**
	 * <p>
	 * Construct the body.
	 * </p>
	 * 
	 * @param distinctEntries
	 *            The collection of distinct dependencies entries.
	 * @param dependenciesColsNum
	 *            The columns number of dependencies.
	 * 
	 * @return The body.
	 */
	private String constructBody(Collection<DependenciesEntry> distinctEntries,
			int dependenciesColsNum) {
		StringBuilder str = new StringBuilder();

		for (DependenciesEntry entry : distinctEntries) {
			int dependencyNum = entry.getDependencies().size();
			dependencyNum = Math.max(1, dependencyNum);

			Map<String, Integer> names = new HashMap<String, Integer>();

			for (ComponentDependency dependency : entry.getDependencies()) {
				int num = 1;
				if (names.containsKey(dependency.getName())) {
					num = names.get(dependency.getName()).intValue() + 1;

				}
				names.put(dependency.getName(), Integer.valueOf(num));
			}

			// Append component language, name, version
			str.append("<tr><td rowspan=\"" + dependencyNum + "\">");
			str
					.append((entry.getTargetComponent().getLanguage() == ComponentLanguage.JAVA) ? JAVA
							: NET);
			String name = entry.getTargetComponent().getName();

			str.append("</td><td rowspan=\"" + dependencyNum + "\">" + name
					+ "</td><td rowspan=\"" + dependencyNum + "\">"
					+ entry.getTargetComponent().getVersion() + "</td>");
			Set<String> dset = directedDependencies.get(entry
					.getTargetComponent().getName()
					+ entry.getTargetComponent().getVersion());
			int index = 0;

			Map<String, Set<String>> componentParents = new HashMap<String, Set<String>>();

			for (ComponentDependency dependency : entry.getDependencies()) {
				String cid=buildComponentID(dependency);
				if(!dependencies.containsKey(cid)){
					exDependencies.put(cid, new DependenciesEntry(dependency,new ArrayList<ComponentDependency>()));
				}
				DependenciesEntry tempEntry = processDirectDependencies(cid);
				String parentName = tempEntry.getTargetComponent().getName()
						+ " " + tempEntry.getTargetComponent().getVersion();
				for (ComponentDependency temp : tempEntry.getDependencies()) {
					String tempname = temp.getName() + " " + temp.getVersion();
					if (!componentParents.containsKey(tempname)) {
						componentParents.put(tempname, new HashSet<String>());
					}
					Set<String> parents = componentParents.get(tempname);
					parents.add(parentName);
				}
			}
			for (ComponentDependency dependency : entry.getDependencies()) {
				// For 1-N dependencies, start a new row
				if (index > 0) {
					str.append("<tr>");
				}

				// Append dependency name and version
				String dname = dependency.getName();
				String version = dependency.getVersion();
				String fullname = dname + version;
				String parentName = "";
				Set<String> parents = componentParents.get(dname + " "
						+ version);
				if (parents != null) {
					for (String parent : parents) {
						if (parentName.length() == 0) {
							parentName = parent;
						} else {
							parentName += "," + parent;
						}
					}
					if (dset == null || !dset.contains(fullname)) {
						dname += "(" + parentName + ")";

					}
				}

				int num = names.get(dependency.getName()).intValue();
				if (circularDependencies.contains(fullname)) {
					String color = circular_color;
					if (num > 1) {
						int c1 = Integer.parseInt(circular_color, 16);
						int c2 = Integer.parseInt(version_warn_color, 16);
						color = Integer.toHexString(c1 | c2);
					}
					dname = "<font color=#" + color + ">" + dname + "</font>";
					version = "<font color=#" + color + ">" + version
							+ "</font>";
				} else if (num > 1) {
					dname = "<font color=#" + version_warn_color + ">" + dname
							+ "</font>";
					version = "<font color=#" + version_warn_color + ">"
							+ version + "</font>";
				}
				if (dset != null && dset.contains(fullname)) {
					dname = "<b>" + dname + "</b>";
					version = "<b>" + version + "</b>";
				}
				str.append("<td>" + dname + "</td>" + "<td>" + version
						+ "</td>");

				// Append dependency type
				if (isDependencyTypeIncluded()) {
					str.append("<td>"
							+ dependency.getType().name().toLowerCase()
							+ "</td>");
				}

				// Append dependency category
				if (this.isDependencyCategoryIncluded()) {
					str.append("<td>"
							+ dependency.getCategory().name().toLowerCase()
							+ "</td>");
				}

				// Append dependency path
				String path = dependency.getPath();
				if (entry.getTargetComponent().getLanguage() == ComponentLanguage.JAVA
						&& path.indexOf("lib/tcs/") < 0
						&& path.indexOf("lib/third_party/") < 0) {
					path = "<font color=#" + path_warn_color + ">" + path
							+ "</font>";
				}
				if (entry.getTargetComponent().getLanguage() == ComponentLanguage.DOT_NET
						&& path.indexOf("tcs\\bin") < 0
						&& path.indexOf("third_party") < 0) {
					path = "<font color=#" + path_warn_color + ">" + path
							+ "</font>";
				}
				if (this.isDependencyPathIncluded()) {
					str.append("<td>" + path + "</td>");
				}

				str.append("</tr>");

				index++;
			}

			if (index == 0) {
				// If component has no dependencies
				str.append("<td colspan=\"" + dependenciesColsNum
						+ "\">&nbsp;</td></tr>");
			}
		}

		return str.toString();
	}

	/**
	 * <p>
	 * Construct the header.
	 * </p>
	 * 
	 * @param dependenciesColsNum
	 *            The columns number of dependencies.
	 * 
	 * @return The header.
	 */
	private String constructHeader(int dependenciesColsNum) {
		StringBuilder str = new StringBuilder();
		str
				.append("<html><head><title>Component Dependency Report</title></head><body><table border=\"1\">"
						+ "<tr align=\"center\"><td colspan=\"3\">Component</td><td colspan=\"");
		str.append(dependenciesColsNum);
		str
				.append("\">Dependencies</td></tr><tr align=\"center\"><td>Language</td><td>Name</td><td>Version</td>"
						+ "<td>Name</td><td>Version</td>");

		// Append type header
		if (this.isDependencyTypeIncluded()) {
			str.append("<td>Type</td>");
		}

		// Append category header
		if (this.isDependencyCategoryIncluded()) {
			str.append("<td>Category</td>");
		}

		// Append path header
		if (this.isDependencyPathIncluded()) {
			str.append("<td>Path</td>");
		}

		str.append("</tr>");

		return str.toString();
	}

	/**
	 * <p>
	 * Get the path color.
	 * </p>
	 */
	public String getPath_warn_color() {
		return path_warn_color;
	}

	/**
	 * <p>
	 * set the path color.
	 * </p>
	 */
	public void setPath_warn_color(String path_warn_color) {
		this.path_warn_color = path_warn_color;
	}

	/**
	 * <p>
	 * Get the version_warn_color.
	 * </p>
	 */
	public String getVersion_warn_color() {
		return version_warn_color;
	}

	/**
	 * <p>
	 * Set the version_warn_color.
	 * </p>
	 */
	public void setVersion_warn_color(String version_warn_color) {
		this.version_warn_color = version_warn_color;
	}
}
