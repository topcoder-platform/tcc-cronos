/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import static com.topcoder.util.dependency.report.Helper.SEPARATOR;
import static com.topcoder.util.dependency.report.Helper.checkCollection;
import static com.topcoder.util.dependency.report.Helper.checkNull;
import static com.topcoder.util.dependency.report.Helper.checkNullOrEmpty;
import static com.topcoder.util.dependency.report.Helper.getBooleanProperty;
import static com.topcoder.util.dependency.report.Helper.getStringProperty;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;


/**
 * <p>
 * This is a base implementation of <code>DependencyReportGenerator</code>.
 * It provides the basic logic of each generator.
 * </p>
 *
 * <p>
 * This class provides template method overloads <code>generate()</code> and
 * <code>generateAll()</code> that use abstract method <code>writeReport()</code>
 * which should be implemented by subclasses. Thus subclasses should not worry
 * about writing report data to various destination types and should only be able
 * to write report to any <code>OutputStream</code>.
 * </p>
 *
 * <p>
 * Also this class extracts indirect dependencies from the given dependency entry
 * list and filters dependencies by type and category. Thus <code>writeReport()</code>
 * method in subclasses will accept all dependencies that must be included in the report.
 * </p>
 *
 * <p>
 *     <strong>Control the dependencies included in report by type and category:</strong>
 *     This class is configured with Configuration API object that can hold information about
 *     what dependency types and categories must be included in the report. If no dependency
 *     type configured, the default included types are <code>INTERNAL</code> and <code>EXTERNAL</code>
 *     If no dependency category configured, the default included categories are <code>COMPILE</code>
 *     and <code>TEST</code>. You can override them programmatically by <code>setAllowedDependencyTypes
 *     ()</code> and <code>setAllowedDependencyCategories()</code>.
 * </p>
 *
 * <p>
 *     <strong>Control the dependencies fields/attributes included in report:</strong>
 *     This class is configured with information about what optional fields(type, category, path) must
 *     be included in the report. The dependency type and category are included in report by default.
 *     The dependency path is <b>NOT</b> included in report by default. Subclasses can access this information
 *     with use of methods:
 *     <ul>
 *         <li><code>isDependencyTypeIncluded()</code></li>
 *         <li><code>isDependencyCategoryIncluded()</code></li>
 *         <li><code>isDependencyPathIncluded()</code></li>
 *     </ul>
 * </p>
 *
 * <p>
 *     <strong>Control the indirect dependencies included in report:</strong>
 *     All the <code>generate()</code> and <code>generateAll()</code> methods take an argument which
 *     indicates that whether indirect dependencies should be included in report.
 * </p>
 *
 * <p>
 *     <strong>Circular Dependency:</strong>
 *     This class checks for circular dependencies when searching indirect dependencies of component.
 * </p>
 *
 * <p>
 *     <strong>Cache:</strong>
 *     Results of direct/indirect dependencies searches are cached to increase the performance.
 * </p>
 *
 * <p>
 *     <strong>To clear cache:</strong>
 *     When circular dependency detected, the cache is auto cleared. There is no explicit
 *     methods provided to clear cache, but you can call either <code>setAllowedDependencyTypes
 *     ()</code> or <code>setAllowedDependencyCategories()</code> to clear cache.
 * </p>
 *
 * <p>
 *     <strong>Sample Configuration(As mentioned in CS3.2.1):</strong>
 *     <pre>
 *        &lt;!-- Optional. Not empty. Semicolon separated. Case Insensitive. Default is "internal;external". --&gt;
 *        &lt;Property name="dependency_types"&gt;
 *            &lt;Value&gt;internal;external&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;!-- Optional. Not empty. Semicolon separated. Case Insensitive. Default is "compile;test". --&gt;
 *        &lt;Property name="dependency_categories"&gt;
 *            &lt;Value&gt;compile;test&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;!-- Optional. Either "true" or "false". Case Sensitive. Default is "true". --&gt;
 *        &lt;Property name="include_dependency_type"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;!-- Optional. Either "true" or "false". Case Sensitive. Default is "true". --&gt;
 *        &lt;Property name="include_dependency_category"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *        &lt;!-- Optional. Either "true" or "false". Case Sensitive. Default is "false". --&gt;
 *        &lt;Property name="include_dependency_path"&gt;
 *            &lt;Value&gt;true&lt;/Value&gt;
 *        &lt;/Property&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe. It has mutable collections. The user must synchronize all
 *     calls to <code>generate()</code> and <code>generateAll()</code> methods to use this class
 *     in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseDependencyReportGenerator implements DependencyReportGenerator {

    /**
     * <p>
     * The pattern of component ID.
     * </p>
     */
    private static final String COMPONENT_ID_PATTERN = "{0}-{1}-{2}";

    /**
     * <p>
     * Represents the "dependency_types" property.
     * </p>
     */
    private static final String DEPENDENCY_TYPES_PROPERTY = "dependency_types";

    /**
     * <p>
     * Represents the "dependency_categories" property.
     * </p>
     */
    private static final String DEPENDENCY_CATEGORIES_PROPERTY = "dependency_categories";

    /**
     * <p>
     * Represents the "include_dependency_type" property.
     * </p>
     */
    private static final String INCLUDE_DEPENDENCY_TYPE_PROPERTY = "include_dependency_type";

    /**
     * <p>
     * Represents the "include_dependency_category" property.
     * </p>
     */
    private static final String INCLUDE_DEPENDENCY_CATEGORY_PROPERTY = "include_dependency_category";

    /**
     * <p>
     * Represents the "include_dependency_path" property.
     * </p>
     */
    private static final String INCLUDE_DEPENDENCY_PATH_PROPERTY = "include_dependency_path";

    /**
     * <p>
     * The default allowed dependency types: "internal;external".
     * </p>
     */
    private static final String DEFAULT_DEPENDENCY_TYPES = DependencyType.INTERNAL + SEPARATOR
        + DependencyType.EXTERNAL;

    /**
     * <p>
     * The default allowed dependency categories: "compile;test".
     * </p>
     */
    private static final String DEFAULT_DEPENDENCY_CATEGORIES = DependencyCategory.COMPILE + SEPARATOR
        + DependencyCategory.TEST;

    /**
     * <p>
     * The map of the input dependencies entries.
     * </p>
     *
     * <p>
     * Keys are component IDs (please see section 1.3.6 for information about their format).
     * Values are their corresponding dependencies entry instances.
     * </p>
     *
     * <p>
     * Collection instance and its content is initialized in the constructor and never changed after that.
     * </p>
     *
     * <p>
     * Cannot be null or empty. Keys cannot be null or empty. Values cannot be null.
     * </p>
     *
     * <p>
     * It is used in all the generate methods.
     * </p>
     */
    private final Map < String, DependenciesEntry > dependencies;

    /**
     * <p>
     * This map holds the cached dependency entries for components with filtered indirect dependencies.
     * </p>
     *
     * <p>
     * Keys are component IDs (please see section 1.3.6 for information about their format).
     * Values are the sum of direct dependency entries and indirect dependencies.
     * Also dependencies are filtered with use of <code>allowedDependencyTypes</code> and
     * <code>allowedDependencyCategories</code>.
     * <p>
     *
     * <p>
     * This map allows to significantly increase performance of this class when report is generated for multiple
     * components.
     * </p>
     *
     * <p>
     * Collection instance is initialized in the constructor and never changed after that.
     * </p>
     *
     * <p>
     * Keys cannot be null or empty. Values cannot be null.
     * </p>
     *
     * <p>
     * Content of this collection is modified in <code>processIndirectDependencies()</code>,
     * cleared in <code>setAllowedDependencyTypes()</code> and <code>setAllowedDependencyCategories()</code>.
     * </p>
     *
     * <p>
     * It is used in all the generate methods.
     * </p>
     */
    private final Map < String, DependenciesEntry > cachedResults;

    /**
     * <p>
     * This map holds the cached dependency entries for components with filtered direct dependencies.
     * </p>
     *
     * <p>
     * Keys are component IDs (please see section 1.3.6 for information about their format).
     * Values are the direct dependency entries.
     * Also dependencies are filtered with use of <code>allowedDependencyTypes</code> and
     * <code>allowedDependencyCategories</code>.
     * <p>
     *
     * <p>
     * This map allows to significantly increase performance of this class when report is generated for multiple
     * components.
     * </p>
     *
     * <p>
     * Collection instance is initialized in the constructor and never changed after that.
     * </p>
     *
     * <p>
     * Keys cannot be null or empty. Values cannot be null.
     * </p>
     *
     * <p>
     * Content of this collection is modified in <code>processDirectDependencies()</code>,
     * cleared in <code>setAllowedDependencyTypes()</code> and <code>setAllowedDependencyCategories()</code>.
     * </p>
     *
     * <p>
     * It is used in all the generate methods.
     * </p>
     */
    private final Map < String, DependenciesEntry > cachedDirectResults;

    /**
     * <p>
     * The set of dependency types that must be included in the generated report.
     * </p>
     *
     * <p>
     * Collection instance is initialized in the constructor and never changed after that.
     * Content is initialized from the provided configuration object.
     * </p>
     *
     * <p>
     * Cannot be null or empty. Cannot contain null.
     * </p>
     *
     * <p>
     * Content of this collection is modified in <code>setAllowedDependencyTypes()</code>.
     * </p>
     *
     * <p>
     * It is used in <code>isAllowedDependency()</code>.
     * </p>
     */
    private final Set < DependencyType > allowedDependencyTypes;

    /**
     * <p>
     * The set of dependency categories that must be included in the generated report.
     * </p>
     *
     * <p>
     * Collection instance is initialized in the constructor and never changed after that.
     * Content is initialized from the provided configuration object.
     * </p>
     *
     * <p>
     * Cannot be null or empty. Cannot contain null.
     * </p>
     *
     * <p>
     * Content of this collection is modified in <code>setAllowedDependencyCategories()</code>.
     * </p>
     *
     * <p>
     * It is used in <code>isAllowedDependency()</code>.
     * </p>
     */
    private final Set < DependencyCategory > allowedDependencyCategories;

    /**
     * <p>
     * This collection holds the set of component IDs (please see section 1.3.6 for information about their format)
     * currently being processed while retrieving all indirect dependencies for some component.
     * </p>
     *
     * <p>
     * It helps to detect circular component dependency errors.
     * </p>
     *
     * <p>
     * Collection instance is initialized in the constructor and never changed after that.
     * </p>
     *
     * <p>
     * Cannot contain null or empty.
     * </p>
     *
     * <p>
     * Content is used and modified in <code>processIndirectDependencies()</code>.
     * </p>
     */
    private final Set < String > progressComponents;

    /**
     * <p>
     * Indicates whether dependency type field must be included in the generated report.
     * </p>
     *
     * <p>
     * It is initialized in the constructor and never changed after that.
     * The value of this attribute can be specified in the constructor with use of the provided configuration object.
     * </p>
     *
     * <p>
     * Subclasses of <code>BaseDependencyReportGenerator</code> can use this attribute in their <code>writeReport()
     * </code> methods.
     * </p>
     */
    private final boolean dependencyTypeIncluded;

    /**
     * <p>
     * Indicates whether dependency category field must be included in the generated report.
     * </p>
     *
     * <p>
     * It is initialized in the constructor and never changed after that.
     * The value of this attribute can be specified in the constructor with use of the provided configuration object.
     * </p>
     *
     * <p>
     * Subclasses of <code>BaseDependencyReportGenerator</code> can use this attribute in their <code>writeReport()
     * </code> methods.
     * </p>
     */
    private final boolean dependencyCategoryIncluded;

    /**
     * <p>
     * Indicates whether dependency component path field must be included in the generated report.
     * </p>
     *
     * <p>
     * It is initialized in the constructor and never changed after that.
     * The value of this attribute can be specified in the constructor with use of the provided configuration object.
     * </p>
     *
     * <p>
     * Subclasses of <code>BaseDependencyReportGenerator</code> can use this attribute in their <code>writeReport()
     * </code> methods.
     * </p>
     */
    private final boolean dependencyPathIncluded;

    /**
     * <p>
     * Creates an instance of <code>BaseDependencyReportGenerator</code> with the given dependencies and
     * configuration object.
     * </p>
     *
     * <p>
     * See class doc for sample configuration.
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
    protected BaseDependencyReportGenerator(List < DependenciesEntry > dependencies, ConfigurationObject configuration)
        throws DependencyReportConfigurationException {

        //The given list of DependenciesEntry should not be null or empty, should not contain null
        Collection < DependenciesEntry > distinctEntries =
            checkCollection(dependencies, "The list of input dependency entries", false);

        //The given ConfigurationObject should not be null
        checkNull(configuration, "ConfigurationObject should not be null.");

        //Instantiate the dependencies map
        //Use LinkedHashMap to preserve order. So that when generateAll(), the order will be same as the given list.
        this.dependencies = new LinkedHashMap<String, DependenciesEntry>();

        for (DependenciesEntry dependency : distinctEntries) {
            //Map from <ID of dependency.getTargetComponent()> to dependency
            this.dependencies.put(buildComponentID(dependency.getTargetComponent()), dependency);
        }

        //Instantiate the cachedResults map
        cachedResults = new HashMap<String, DependenciesEntry>();

        //Instantiate the cachedDirectResults map
        cachedDirectResults = new HashMap<String, DependenciesEntry>();

        //Get the comma separated dependence types, default to "internal;external"
        String dependencyTypes = getStringProperty(configuration, DEPENDENCY_TYPES_PROPERTY,
            DEFAULT_DEPENDENCY_TYPES);

        //Instantiate the allowedDependencyTypes set
        allowedDependencyTypes = new HashSet<DependencyType>();

        for (String dependencyType : dependencyTypes.split(SEPARATOR)) {
            //Get the DependencyType enum and add it to allowedDependencyTypes set
            allowedDependencyTypes.add(parseEnum(DependencyType.class, dependencyType.trim().toUpperCase()));
        }

        //The allowedDependencyTypes must not be empty.
        if (allowedDependencyTypes.size() == 0) {
            throw new DependencyReportConfigurationException(
                "The 'dependency_types' property must have at least one type specified, but is: " + dependencyTypes);
        }

        //Get the comma separated dependence categories, default to "compile;test"
        String dependencyCategories = getStringProperty(configuration, DEPENDENCY_CATEGORIES_PROPERTY,
            DEFAULT_DEPENDENCY_CATEGORIES);

        //Instantiate the allowedDependencyCategories set
        allowedDependencyCategories = new HashSet<DependencyCategory>();

        for (String dependencyCategory : dependencyCategories.split(SEPARATOR)) {
            //Get the DependencyCategory enum and add it to allowedDependencyCategories set
            allowedDependencyCategories.add(
                parseEnum(DependencyCategory.class, dependencyCategory.trim().toUpperCase()));
        }

        //The allowedDependencyCategories must not be empty.
        if (allowedDependencyCategories.size() == 0) {
            throw new DependencyReportConfigurationException(
                "The 'dependency_categories' property must have at least one category specified, but is: "
                + dependencyCategories);
        }

        //Instantiate the progressComponents set
        progressComponents = new HashSet<String>();

        //Get the boolean value of dependencyTypeIncluded, default to true
        dependencyTypeIncluded = getBooleanProperty(configuration, INCLUDE_DEPENDENCY_TYPE_PROPERTY, true);

        //Get the boolean value of dependencyCategoryIncluded, default to true
        dependencyCategoryIncluded = getBooleanProperty(configuration, INCLUDE_DEPENDENCY_CATEGORY_PROPERTY, true);

        //Get the boolean value of dependencyPathIncluded, default to false
        dependencyPathIncluded = getBooleanProperty(configuration, INCLUDE_DEPENDENCY_PATH_PROPERTY, false);
    }

    /**
     * <p>
     * Creates an instance of <code>BaseDependencyReportGenerator</code> with the given dependencies persistence and
     * configuration object.
     * </p>
     *
     * <p>
     * See class doc for sample configuration.
     * </p>
     *
     * @param persistence The component dependencies persistence to be used (cannot be null).
     * @param configuration The configuration object for this class (cannot be null).
     *
     * @throws IllegalArgumentException If <code>persistence</code> is null. Or if <code>configuration</code> is null.
     * @throws DependencyReportConfigurationException If error occurred while reading the configuration
     *         (e.g. Some property has multiple values; Or the value is not type of <code>String</code>;
     *         Or the property is present but has null/empty value; Or the boolean value is neither "true"
     *         nor "false", case sensitive; Or the enum value does not present a valid enum, case insensitive).
     * @throws DependencyReportGenerationException If error occurs while accessing the dependencies persistence.
     */
    protected BaseDependencyReportGenerator(DependenciesEntryPersistence persistence, ConfigurationObject configuration)
        throws DependencyReportGenerationException {
        this(loadFromPersistence(persistence, configuration), configuration);
    }

    /**
     * <p>
     * Load the list of <code>DependenciesEntry</code> from <code>DependenciesEntryPersistence</code>.
     * </p>
     *
     * @param persistence The <code>DependenciesEntryPersistence</code> to load component dependencies entries.
     * @param configuration The configuration.
     *
     * @return The list of <code>DependenciesEntry</code> loaded.
     *
     * @throws IllegalArgumentException If <code>persistence</code> is null or <code>configuration</code> is null.
     * @throws DependencyReportGenerationException If error occurs while accessing the dependencies persistence.
     */
    private static List < DependenciesEntry > loadFromPersistence(DependenciesEntryPersistence persistence,
        ConfigurationObject configuration) throws DependencyReportGenerationException {

        checkNull(persistence, "DependenciesEntryPersistence should not be null.");
        checkNull(configuration, "ConfigurationObject should not be null.");

        try {
            return persistence.load();
        } catch (DependenciesEntryPersistenceException e) {
            throw new DependencyReportGenerationException(
                "Error occurs while loading the list of dependency entries from persistence", e);
        }
    }

    /**
     * <p>
     * Parse <code>Enum</code> of the specified enum type with the specified name.
     * The name must match exactly an identifier used to declare an enum constant in this type.
     * </p>
     *
     * @param <T> Generic type.
     * @param enumType The <code>Class</code> object of the enum type.
     * @param name The name of the constant enum.
     *
     * @return The enum constant of the specified enum type with the specified name.
     *
     * @throws DependencyReportConfigurationException If the specified enum type has no constant
     *         with the specified name, or the specified class object does not represent an enum type.
     */
    private static < T extends Enum < T > > T parseEnum(Class < T > enumType, String name)
        throws DependencyReportConfigurationException {

        try {
            return Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException e) {
            throw new DependencyReportConfigurationException(
                MessageFormat.format("There is no such {0} enum with name: {1}", enumType.getSimpleName(), name), e);
        }
    }

    /**
     * <p>
     * Build the component ID. See section 1.3.6 for information about the format of component ID.
     * </p>
     *
     * @param component The component to get its ID.
     *
     * @return The ID of given component.
     */
    private static String buildComponentID(Component component) {
        return MessageFormat.format(COMPONENT_ID_PATTERN,
            component.getLanguage().name().toLowerCase(), component.getName(), component.getVersion());
    }

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
     * <p>
     * This method constructs a list of <code>DependenciesEntry</code> with only filtered direct and filtered
     * indirect dependencies(if requested) included, and then calls abstract <code>writeReport()</code> method
     * that takes care about writing the report to output stream in the desired format.
     * </p>
     *
     * <p>
     *     <strong>About filtering:</strong>
     *     By "<b>filtering</b>", we mean that only a certain set of dependencies entries will be included in
     *     report. The "<b>dependency_types</b>" and "<b>dependency_categories</b>" configuration parameters are
     *     used to filter the dependencies entries by types and categories.
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
     *         given <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    public void generate(List < String > componentIds, OutputStream os, boolean indirect)
        throws DependencyReportGenerationException {

        doSafeGenerate(
            checkCollection(componentIds, "The list of component IDs", false),
            checkNull(os, "The OutputStream to generate report"),
            indirect);
    }

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
     * <p>
     * This method constructs a list of <code>DependenciesEntry</code> with only filtered direct and filtered
     * indirect dependencies(if requested) included, and then calls abstract <code>writeReport()</code> method
     * that takes care about writing the report to output stream in the desired format.
     * </p>
     *
     * <p>
     *     <strong>About filtering:</strong>
     *     By "<b>filtering</b>", we mean that only a certain set of dependencies entries will be included in
     *     report. The "<b>dependency_types</b>" and "<b>dependency_categories</b>" configuration parameters are
     *     used to filter the dependencies entries by types and categories.
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
    public void generate(List < String > componentIds, String fileName, boolean indirect)
        throws DependencyReportGenerationException {

        doSafeGenerateToFile(
            checkCollection(componentIds, "The list of component IDs", false),
            checkNullOrEmpty(fileName, "The file name of generated report"),
            indirect);
    }

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
     * <p>
     * This method constructs a list of <code>DependenciesEntry</code> with only filtered direct and filtered
     * indirect dependencies(if requested) included, and then calls abstract <code>writeReport()</code> method
     * that takes care about writing the report to output stream in the desired format.
     * </p>
     *
     * <p>
     *     <strong>About filtering:</strong>
     *     By "<b>filtering</b>", we mean that only a certain set of dependencies entries will be included in
     *     report. The "<b>dependency_types</b>" and "<b>dependency_categories</b>" configuration parameters are
     *     used to filter the dependencies entries by types and categories.
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
    public String generate(List < String > componentIds, boolean indirect) throws DependencyReportGenerationException {

        return doSafeGenerateAsString(
               checkCollection(componentIds, "The list of component IDs", false),
               indirect);
    }

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
     * <p>
     * This method constructs a list of <code>DependenciesEntry</code> with only filtered direct and filtered
     * indirect dependencies(if requested) included, and then calls abstract <code>writeReport()</code> method
     * that takes care about writing the report to output stream in the desired format.
     * </p>
     *
     * <p>
     *     <strong>About filtering:</strong>
     *     By "<b>filtering</b>", we mean that only a certain set of dependencies entries will be included in
     *     report. The "<b>dependency_types</b>" and "<b>dependency_categories</b>" configuration parameters are
     *     used to filter the dependencies entries by types and categories.
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
    public void generateAll(OutputStream os, boolean indirect) throws DependencyReportGenerationException {

        doSafeGenerate(
            dependencies.keySet(),
            checkNull(os, "The OutputStream to generate report"),
            indirect);
    }

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
     * <p>
     * This method constructs a list of <code>DependenciesEntry</code> with only filtered direct and filtered
     * indirect dependencies(if requested) included, and then calls abstract <code>writeReport()</code> method
     * that takes care about writing the report to output stream in the desired format.
     * </p>
     *
     * <p>
     *     <strong>About filtering:</strong>
     *     By "<b>filtering</b>", we mean that only a certain set of dependencies entries will be included in
     *     report. The "<b>dependency_types</b>" and "<b>dependency_categories</b>" configuration parameters are
     *     used to filter the dependencies entries by types and categories.
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
    public void generateAll(String fileName, boolean indirect) throws DependencyReportGenerationException {

        doSafeGenerateToFile(
            dependencies.keySet(),
            checkNullOrEmpty(fileName, "The file name of generated report"),
            indirect);
    }

    /**
     * <p>
     * Generates a component dependency report for all available components and returns it as a <code>String</code>.
     * </p>
     *
     * <p>
     * Indirect dependencies will be included in the report if requested.
     * </p>
     *
     * <p>
     * This method constructs a list of <code>DependenciesEntry</code> with only filtered direct and filtered
     * indirect dependencies(if requested) included, and then calls abstract <code>writeReport()</code> method
     * that takes care about writing the report to output stream in the desired format.
     * </p>
     *
     * <p>
     *     <strong>About filtering:</strong>
     *     By "<b>filtering</b>", we mean that only a certain set of dependencies entries will be included in
     *     report. The "<b>dependency_types</b>" and "<b>dependency_categories</b>" configuration parameters are
     *     used to filter the dependencies entries by types and categories.
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
    public String generateAll(boolean indirect) throws DependencyReportGenerationException {
        return doSafeGenerateAsString(dependencies.keySet(), indirect);
    }

    /**
     * <p>
     * This method is called by {@link #generate(List, String, boolean)} and {@link #generateAll(String, boolean)}
     * methods after they have the arguments validated. So this method presume the passed in arguments are valid
     * and so it is safe to operate on them.
     * </p>
     *
     * @param componentIds The IDs of components that must be included in the generated report
     *                     (cannot be null or empty, cannot contain null or empty).
     * @param fileName The file name of the generated report (cannot be null or empty).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws ComponentIdNotFoundException If dependencies entry was not provided for some component from
     *         <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    private void doSafeGenerateToFile(Collection < String > componentIds, String fileName, boolean indirect)
        throws DependencyReportGenerationException {

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileName);
            doSafeGenerate(componentIds, fileOutputStream, indirect);
        } catch (IOException e) {
            throw new DependencyReportGenerationException("I/O error occurred while generating report.", e);
        } finally {
            //Finally close file output stream
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    //Silently Ignore
                }
            }
        }
    }

    /**
     * <p>
     * This method is called by {@link #generate(List, boolean)} and {@link #generateAll(boolean)} methods
     * after they have the arguments validated. So this method presume the passed in arguments are valid
     * and so it is safe to operate on them.
     * </p>
     *
     * @param componentIds The IDs of components that must be included in the generated report
     *                     (cannot be null or empty, cannot contain null or empty).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @return The generated report (cannot be null).
     *
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws ComponentIdNotFoundException If dependencies entry was not provided for some component from
     *         given <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    private String doSafeGenerateAsString(Collection < String > componentIds, boolean indirect)
        throws DependencyReportGenerationException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        doSafeGenerate(componentIds, stream, indirect);

        //No need close ByteArrayOutputStream. The ByteArrayOutputStream#close() is an empty method.
        return stream.toString();
    }

    /**
     * <p>
     * This method is called by the other generate methods after they have the arguments validated.
     * So this method presume the passed in arguments are valid and so it is safe to operate on them.
     * </p>
     *
     * @param componentIds The IDs of components that must be included in the generated report
     *                     (cannot be null or empty, cannot contain null or empty).
     * @param os The output stream for the generated report (cannot be null).
     * @param indirect True if indirect dependencies must be included in the report, false otherwise.
     *
     * @throws CircularComponentDependencyException If <code>indirect</code> is true and circular component
     *         dependency was found.
     * @throws ComponentIdNotFoundException If dependencies entry was not provided for some component from
     *         given <code>componentIds</code>.
     * @throws DependencyReportGenerationException If I/O or other error occurred while generating or writing
     *         the report.
     */
    private void doSafeGenerate(Collection < String > componentIds, OutputStream os, boolean indirect)
        throws DependencyReportGenerationException {

        List < DependenciesEntry > resEntryList = new ArrayList<DependenciesEntry>();

        for (String componentId : componentIds) {

            //Fail fast if any given componentId is not found
            if (!dependencies.containsKey(componentId)) {
                throw new ComponentIdNotFoundException("Component is not found with ID: " + componentId);
            }

            //For each componentId from given componentIds
            DependenciesEntry resEntry = indirect
                ? processIndirectDependencies(componentId) : processDirectDependencies(componentId);

            //Note, we have failed fast, so at this point, the resEntry will never be null
            resEntryList.add(resEntry);
        }

        //Write report
        writeReport(resEntryList, os);
    }

    /**
     * <p>
     * Processes the direct dependencies of component with the specified ID.
     * </p>
     *
     * <p>
     * This methods is a revision of original design with regard to performance consideration.
     * It use the <code>cachedDirectResults</code> map to cache the filtered direct dependencies.
     * </p>
     *
     * <p>
     * So, this method will:
     * <ul>
     *     <li>Filter the direct component dependencies which can be added to the report;</li>
     *     <li>Cache the filtered direct component dependencies.</li>
     * </ul>
     * </p>
     *
     * <p>
     * This method is called by <code>doSafeGenerate()</code> and <code>processIndirectDependencies()</code>.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     The caller method should ensure the passed in component ID DOES exist. This method will assume
     *     the given component ID exists and will NOT do additional/duplicate check.
     * </p>
     *
     * @param componentId The ID of the component to be processed.
     *
     * @return The <code>DependenciesEntry</code> contains the filtered direct dependencies. Will never be
     *         null.
     */
    private DependenciesEntry processDirectDependencies(String componentId) {

        //First find from cache
        DependenciesEntry result = cachedDirectResults.get(componentId);

        //If not hit within cache, filter the direct dependencies, put to cache
        if (result == null) {

            DependenciesEntry entry = dependencies.get(componentId);

            //Filter the dependencies which can be added to the report
            List < ComponentDependency > filteredDependenciesList = new ArrayList<ComponentDependency>();
            for (ComponentDependency dependency : entry.getDependencies()) {
                if (isAllowedDependency(dependency)) {
                    filteredDependenciesList.add(dependency);
                }
            }

            result = new DependenciesEntry(entry.getTargetComponent(), filteredDependenciesList);

            //Put result entry to cache
            cachedDirectResults.put(componentId, result);
        }

        return result;
    }

    /**
     * <p>
     * Checks whether the given component dependency can be included into the generated report.
     * </p>
     *
     * <p>
     * This method is used in <code>processDirectDependencies()</code> to filter the component
     * dependencies which can be added to the report.
     * </p>
     *
     * @param dependency The component dependency to be checked.
     *
     * @return True if the given dependency can be included into the generated report, false otherwise.
     */
    private boolean isAllowedDependency(ComponentDependency dependency) {

        return allowedDependencyTypes.contains(dependency.getType())
            && allowedDependencyCategories.contains(dependency.getCategory());
    }

    /**
     * <p>
     * Processes the list of indirect dependencies for the component with the specified ID.
     * </p>
     *
     * <p>
     * Returns immediately if this list has been retrieved before.
     * Else result is calculated and put to <code>cachedResults</code> collection.
     * </p>
     *
     * <p>
     * This method uses a recursive algorithm to retrieve indirect dependencies.
     * Collection <code>progressComponents</code> is used for detecting circular component dependencies.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     If given component ID is not found, we do not throw <code>ComponentIdNotFoundException</code>
     *     from this method. Because this method is self-recursively called, and no exception should be
     *     thrown if componentId for n-depth indirect component is not found.
     * </p>
     *
     * @param componentId The ID of the component to be processed (cannot be null or empty).
     *
     * @throws CircularComponentDependencyException If circular component dependency was found.
     *
     * @return The <code>DependenciesEntry</code> contains the filtered direct and indirect dependencies.
     *         <code>Null</code> is returned if given <code>componentId</code> is not found.
     */
    private DependenciesEntry processIndirectDependencies(String componentId)
        throws CircularComponentDependencyException {

        //Detect cycle dependency
        if (progressComponents.contains(componentId)) {
            //Before throwing an exception from this method progressComponents collection must be cleared.
            progressComponents.clear();

            //Throw CircularComponentDependencyException
            throw new CircularComponentDependencyException("Circular component dependency detected: " + componentId);
        }

        //First find from cache
        DependenciesEntry result = cachedResults.get(componentId);

        //If not hit within cache, filter the direct dependencies, merge the indirect dependencies, put to cache
        if (result == null) {

            //If componentId not found, simply return null.
            //Note, we do not throw ComponentIdNotFoundException here.
            //ComponentIdNotFoundException is ONLY thrown if some componentId which is passed as argument
            //into generate() method is not found.
            //This method is self-recursively called, so if componentId for n-depth indirect component is
            //not found, no ComponentIdNotFoundException thrown.
            if (!dependencies.containsKey(componentId)) {
                return null;
            }

            //At first, process the direct dependencies
            //Get the DependenciesEntry which contains the direct dependencies
            DependenciesEntry directDependenciesEntry = processDirectDependencies(componentId);

            //Add component ID to the set of components being processed
            progressComponents.add(componentId);

            //Now, we create a shallow copy of the direct dependencies list
            //(Note the DependenciesEntry.getDependencies() returns a shallow copy).
            //Next, we will merge the indirect dependencies into the shallow copy list.
            List < ComponentDependency > directDependenciesShallowCopy = directDependenciesEntry.getDependencies();

            //Get the number of direct dependencies
            int directDepNum = directDependenciesShallowCopy.size();

            for (int i = 0; i < directDepNum; i++) {

                ComponentDependency directDependency = directDependenciesShallowCopy.get(i);

                //The directDependency itself is a dependency, so the dependencies of directDependency
                //are considered to be "indirect". This is exactly what the term "indirect" means here.

                //For each directDependency, process the indirect dependencies of it recursively.
                //Get the DependenciesEntry which contains the indirect dependencies
                DependenciesEntry indirectDependenciesEntry =
                    processIndirectDependencies(buildComponentID(directDependency));

                //Here the indirectDependenciesEntry may be null, see Line 1151
                if (indirectDependenciesEntry != null) {
                    for (ComponentDependency indirectDependency : indirectDependenciesEntry.getDependencies()) {
                        //For each indirect dependency, Check whether it is duplicate
                        if (!isInDependencyList(directDependenciesShallowCopy, indirectDependency)) {
                            //If not duplicate, then MERGE it into the directDependenciesShallowCopy
                            directDependenciesShallowCopy.add(indirectDependency);
                        }
                    }
                }

                //Note, we do not throw ComponentIdNotFoundException here.
                //ComponentIdNotFoundException is ONLY thrown if some componentId which is passed as argument
                //into generate() method is not found.
                //This method is self-recursively called, so if componentId for n-depth indirect component is
                //not found, no ComponentIdNotFoundException thrown.
            }

            result = new DependenciesEntry(directDependenciesEntry.getTargetComponent(), directDependenciesShallowCopy);

            //Put result entry to cache
            cachedResults.put(componentId, result);

            //Remove component ID from the set of components being processed
            progressComponents.remove(componentId);
        }

        return result;
    }

    /**
     * <p>
     * Checks whether the given component dependency is present in the specified dependency list.
     * </p>
     *
     * <p>
     * This method is used in <code>processIndirectDependencies()</code>.
     * </p>
     *
     * @param list The list of component dependencies (cannot be null, cannot contain null).
     * @param dependency The dependency to be checked (cannot be null).
     *
     * @return True if dependency is already in the list, false otherwise.
     */
    private boolean isInDependencyList(List < ComponentDependency > list, ComponentDependency dependency) {
        for (ComponentDependency listDependency : list) {
            if (dependency.getPath().equalsIgnoreCase(listDependency.getPath())
                && (!dependencyCategoryIncluded || dependency.getCategory() == listDependency.getCategory())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Clears the caches.
     * </p>
     */
    private void clearCaches() {
        cachedResults.clear();
        cachedDirectResults.clear();
    }

    /**
     * <p>
     * Sets the dependency types that must be included in the generated report.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     The cache will be cleared.
     * </p>
     *
     * @param types The dependency types that must be included in the generated report
     *       (cannot be null or empty, cannot contain null).
     * @throws IllegalArgumentException If <code>types</code> is null or empty, or contains null.
     */
    public void setAllowedDependencyTypes(Set < DependencyType > types) {
        checkCollection(types, "The set of allowed dependency types", false);

        allowedDependencyTypes.clear();
        allowedDependencyTypes.addAll(types);

        //Clear caches
        clearCaches();
    }

    /**
     * <p>
     * Sets the dependency categories that must be included in the generated report.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     The cache will be cleared.
     * </p>
     *
     * @param categories The dependency categories that must be included in the generated report
     *        (cannot be null or empty, cannot contain null).
     * @throws IllegalArgumentException If <code>categories</code> is null or empty, or contains null.
     */
    public void setAllowedDependencyCategories(Set < DependencyCategory > categories) {
        checkCollection(categories, "The set of allowed dependency categories", false);

        allowedDependencyCategories.clear();
        allowedDependencyCategories.addAll(categories);

        //Clear caches
        clearCaches();
    }

    /**
     * <p>
     * Gets the value that indicates whether dependency type field must be included in the generated report.
     * </p>
     *
     * @return The value that indicates whether dependency type field must be included in the generated report.
     */
    public boolean isDependencyTypeIncluded() {
        return dependencyTypeIncluded;
    }

    /**
     * <p>
     * Gets the value that indicates whether dependency category field must be included in the generated report.
     * </p>
     *
     * @return The value that indicates whether dependency category field must be included in the generated report.
     */
    public boolean isDependencyCategoryIncluded() {
        return dependencyCategoryIncluded;
    }

    /**
     * <p>
     * Gets the value that indicates whether dependency path field must be included in the generated report.
     * </p>
     *
     * @return The value that indicates whether dependency path field must be included in the generated report.
     */
    public boolean isDependencyPathIncluded() {
        return dependencyPathIncluded;
    }

    /**
     * <p>
     * Writes the data from the provided entries to the output stream with use of the custom format.
     * </p>
     *
     * <p>
     * This abstract method will be used by those <code>generate()</code> and <code>generateAll()</code>
     * methods to write report.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     The implementations are responsible to flush the output stream but <b>NOT</b> close them.
     * </p>
     *
     * @param entries The list of dependency entries that hold data of the generated report (cannot be null;
     *        cannot contain null).
     * @param os The output stream for the generated report (cannot be null).
     *
     * @throws IllegalArgumentException If <code>entries</code> is null or contains null.
     *         Or <code>os</code> is null.
     * @throws DependencyReportGenerationException If error occurred while writing the report.
     */
    protected abstract void writeReport(List < DependenciesEntry > entries, OutputStream os)
        throws DependencyReportGenerationException;
}
