/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.io.OutputStream;

import java.util.List;


/**
 * <p>
 * This interface must be implemented by classes that can generate component dependency reports.
 * </p>
 *
 * <p>
 * It provides methods for generating reports for the specified components or all components from
 * the given dependency list.
 * </p>
 *
 * <p>
 * To be compatible with <code>DependencyReportGeneratorUtility</code>, implementations of this
 * interface must have constructors that accepts two parameters:
 * <pre>
 * (<code>dependencies:List&lt;DependencyEntry&gt;</code> and <code>configuration:ConfigurationObject</code>).
 * </pre>
 * and
 * <pre>
 * (<code>persistence:DependenciesEntryPersistence</code> and <code>configuration:ConfigurationObject</code>).
 * </pre>
 * </p>
 *
 * <p>
 * This interface provides methods that allow to generate reports to various destination types:
 * <code>OutputStream</code>, <code>File</code> and <code>String</code>.
 * </p>
 *
 * <p>
 * Also implementations must support retrieving of indirect dependencies (dependencies of dependencies)
 * for each component and including them in the report.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     Implementations of this interface can be not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface DependencyReportGenerator {

    /**
     * <p>
     * Generates a component dependency report for the given components and writes it to the
     * provided <code>OutputStream</code>.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * <p>
     * The identifier of component should have following format:
     *     <ul>
     *         <li>language-component_name-component_version</li>
     *     </ul>
     * E.g.<ul>
     *         <li>java-logging_wrapper-2.0.0</li>
     *         <li>dot_net-id_generator-1.1.1</li>
     *     </ul>
     * </p>
     *
     * @param componentIds The IDs of components that must be included in the generated report
     *                     (cannot be null or empty, cannot contain null or empty).
     * @param os The output stream for the generated report (cannot be null).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @throws IllegalArgumentException If <code>componentIds</code> is null or empty, contains null or empty.
     *         Or if <code>os</code> is null.
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws ComponentIdNotFoundException If dependencies entry was not provided for some component from
     *         <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    void generate(List < String > componentIds, OutputStream os, boolean indirect)
        throws DependencyReportGenerationException;

    /**
     * <p>
     * Generates a component dependency report for the given components and writes it to the
     * specified file.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * <p>
     * The identifier of component should have following format:
     *     <ul>
     *         <li>language-component_name-component_version</li>
     *     </ul>
     * E.g.<ul>
     *         <li>java-logging_wrapper-2.0.0</li>
     *         <li>dot_net-id_generator-1.1.1</li>
     *     </ul>
     * </p>
     *
     * @param componentIds The IDs of components that must be included in the generated report
     *                     (cannot be null or empty, cannot contain null or empty).
     * @param fileName The file name of the generated report (cannot be null or empty).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @throws IllegalArgumentException If <code>componentIds</code> is null or empty, contains null or empty.
     *         Or if <code>fileName</code> is null or empty.
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws ComponentIdNotFoundException If dependencies entry was not provided for some component from
     *         <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    void generate(List < String > componentIds, String fileName, boolean indirect)
        throws DependencyReportGenerationException;

    /**
     * <p>
     * Generates a component dependency report for the given components and returns it as a <code>String</code>.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * <p>
     * The identifier of component should have following format:
     *     <ul>
     *         <li>language-component_name-component_version</li>
     *     </ul>
     * E.g.<ul>
     *         <li>java-logging_wrapper-2.0.0</li>
     *         <li>dot_net-id_generator-1.1.1</li>
     *     </ul>
     * </p>
     *
     * @param componentIds The IDs of components that must be included in the generated report
     *                     (cannot be null or empty, cannot contain null or empty).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @return The generated report (cannot be null).
     *
     * @throws IllegalArgumentException If <code>componentIds</code> is null or empty, contains null or empty.
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws ComponentIdNotFoundException If dependencies entry was not provided for some component from
     *         <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    String generate(List < String > componentIds, boolean indirect) throws DependencyReportGenerationException;

    /**
     * <p>
     * Generates a component dependency report for all available components and writes it to the
     * provided <code>OutputStream</code>.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * @param os The output stream for the generated report (cannot be null).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @throws IllegalArgumentException If <code>os</code> is null.
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    void generateAll(OutputStream os, boolean indirect) throws DependencyReportGenerationException;

    /**
     * <p>
     * Generates a component dependency report for all available components and writes it to the
     * specified file.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * @param fileName The file name of the generated report (cannot be null or empty).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @throws IllegalArgumentException If <code>fileName</code> is null or empty.
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    void generateAll(String fileName, boolean indirect) throws DependencyReportGenerationException;

    /**
     * <p>
     * Generates a component dependency report for all available components and returns it as a <code>String</code>.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @return The generated report (cannot be null).
     *
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    String generateAll(boolean indirect) throws DependencyReportGenerationException;
}
