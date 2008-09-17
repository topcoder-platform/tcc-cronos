/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.commandline.ArgumentValidationException;
import com.topcoder.util.commandline.ArgumentValidator;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerator;
import com.topcoder.util.dependency.report.Helper;
import com.topcoder.util.dependency.report.impl.CsvDependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGenerator;
import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This is the main class of standalone application that can be used for generating component dependency reports
 * from the command line. It reads all configuration data from the configuration file. Some of this data can be
 * overridden by the user with use of command line switches and arguments.
 * </p>
 *
 * <p>
 * By default, this utility uses <code>DefaultXmlDependenciesEntryPersistence</code> to get the list of direct
 * component dependencies and <code>XmlDependencyReportGenerator</code> to write the report to the standard output.
 * </p>
 *
 * <p>
 * The user can specify another <code>DependenciesEntryPersistence</code> and <code>DependencyReportGenerator</code>
 * implementation to be used and specify some file where report must be written to.
 * </p>
 *
 * <p>
 *     <strong>Sample Configuration(As mentioned in CS3.2.1 and CS3.2.2):</strong>
 *     <pre>
 *  &lt;!-- The "config.xml" is the default configuration file --&gt;
 *  &lt;Config name="com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtility"&gt;
 *    &lt;!-- All properties/children are optional...... --&gt;
 *
 *    &lt;!-- Optional. Not empty.
 *         Default is "com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence". --&gt;
 *    &lt;Property name="persistence_class"&gt;
 *        &lt;Value&gt;com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence&lt;/Value&gt;
 *    &lt;/Property&gt;
 *
 *    &lt;!-- Optional child. Default is an empty child. --&gt;
 *    &lt;Property name="persistence_config"&gt;
 *        &lt;Property name="file_name"&gt;
 *            &lt;Value&gt;/dependencies.xml&lt;/Value&gt;
 *        &lt;/Property&gt;
 *    &lt;/Property&gt;
 *
 *    &lt;!-- Optional. Not empty.
 *         Default is "com.topcoder.util.dependency.report.impl.XmlDependencyReportGenerator". --&gt;
 *    &lt;Property name="generator_class"&gt;
 *        &lt;Value&gt;com.topcoder.util.dependency.report.impl.HtmlDependencyReportGenerator&lt;/Value&gt;
 *    &lt;/Property&gt;
 *
 *    &lt;!-- Optional child. Default is an empty child. --&gt;
 *    &lt;Property name="generator_config"&gt;
 *        &lt;!-- Optional. Not empty. Semicolon separated. Case Insensitive. Default is "internal;external". --&gt;
 *        &lt;Property name="dependency_types"&gt;
 *            &lt;Value&gt;internal;external&lt;/Value&gt;
 *       &lt;/Property&gt;
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
 *    &lt;/Property&gt;
 *
 *    &lt;!-- Optional. Either "true" or "false". Case Sensitive. Default is "true". --&gt;
 *    &lt;Property name="stdout"&gt;
 *        &lt;Value&gt;true&lt;/Value&gt;
 *    &lt;/Property&gt;
 *
 *    &lt;!-- Optional. Not empty. Default is null. --&gt;
 *    &lt;Property name="report_file_name"&gt;
 *        &lt;Value&gt;report.html&lt;/Value&gt;
 *    &lt;/Property&gt;
 *
 *    &lt;!-- Optional. Either "true" or "false". Case Sensitive. Default is "true". --&gt;
 *    &lt;Property name="indirect"&gt;
 *        &lt;Value&gt;false&lt;/Value&gt;
 *    &lt;/Property&gt;
 *  &lt;/Config&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Available Switches(As mentioned in CS3.2.3):</strong>
 *     <pre>
 *    Usage:
 *    -o    &lt;file_name&gt; Optional.
 *         The report output file name.
 *         Can override the parameter 'report_file_name' mentioned in the CS 3.2.2.
 *    -noindirect    Optional.
 *         Indicates that indirect dependencies should be included into the generated report.
 *         This switch must not be specified together with '-indirect' switch.
 *         This switch specifies or overrides the parameter 'indirect' from the CS 3.2.2.
 *    -dcat    &lt;categories_list&gt; Optional.
 *         The list of dependency categories those must be included into the generated report.
 *         The semicolon-separated list of case-insensitive DependencyCategory values.
 *         This switch can override the 'dependency_categories' parameter mentioned in the CS 3.2.1.
 *         In this case the configuration of BaseDependencyReportGenerator is provided with 'generator_config'
 *         parameter from the CS 3.2.2.
 *         Default is 'compile;test'.
 *    -c    &lt;file_name&gt; Optional.
 *         The name of the configuration file for this utility.
 *         This file is read with use of Configuration Persistence component.
 *         The structure of this file is described in the CS 3.2.2.
 *         Default is 'config.xml'.
 *         Note that configuration file is optional and the utility can work when it doesn't exist.
 *    -h    Prints out the usage message.
 *    -?    Prints out the usage message.
 *    -id    &lt;component_ids&gt; Optional.
 *         The semicolon-separated list of component IDs (see CS 1.3.6 for format).
 *         E.G. 'java-logging_wrapper-2.0.0; object_factory-2.1.0,base_exception-1.0'.
 *         If this switch is provided, then report is generated for specified components only.
 *         If this switch is not specified, report is generated for all components that have a dependency
 *         entry provided.
 *    -pclass    &lt;class_name&gt; Optional.
 *         The full class name of the DependenciesEntryPersistence implementation to be used.
 *         This switch specifies or overrides the parameter 'persistence_class' from the CS 3.2.2.
 *         DefaultXmlDependenciesEntryPersistence is a default persistence implementation.
 *    -help    Prints out the usage message.
 *    -i    &lt;file_name&gt; Optional.
 *         The name of the input file that is used by DependenciesEntryPersistence implementation to load the list of
 *         dependency entries.
 *         This switch is not ignored only if DefaultXmlDependenciesEntryPersistence or
 *         BinaryFileDependenciesEntryPersistence is used as persistence implementation.
 *         This switch overrides the file name specified in 'persistence_config' mentioned in the CS 3.2.2.
 *    -gclass    &lt;class_name&gt; Optional.
 *         The full class name of the DependencyReportGenerator implementation to be used.
 *         This switch specifies or overrides the parameter 'generator_class' from the CS 3.2.2.
 *         This switch must not be specified together with '-f' switch.
 *         XmlDependencyReportGenerator is a default generator implementation.
 *    -f    &lt;format_name&gt; Optional.
 *         The name of the report format to be used.
 *         Currently the following values are supported(case sensitive):
 *            'xml' - represents XmlDependencyReportGenerator
 *            'csv' - represents CsvDependencyReportGenerator
 *            'html' - represents HtmlDependencyReportGenerator.
 *         This switch specifies or overrides the parameter 'generator_class' from the CS 3.2.2.
 *         This switch must not be specified together with '-gclass' switch.
 *         It duplicates functionality of '-gclass', but is much more easy-to-use.
 *         Default format is 'xml'.
 *    -dtype    &lt;types_list&gt; Optional.
 *         The list of dependency types those must be included into the generated report.
 *         The semicolon-separated list of case-insensitive DependencyType values.
 *         This switch can override the 'dependency_types' parameter mentioned in the CS 3.2.1.
 *         In this case the configuration of BaseDependencyReportGenerator is provided with 'generator_config' parameter
 *         from the CS 3.2.2.
 *         Default is 'internal;external'.
 *    -indirect    Optional.
 *         Indicates that indirect dependencies should not be included into the generated report.
 *         This switch must not be specified together with '-noindirect' switch.
 *         This switch specifies or overrides the parameter 'indirect' from the CS 3.2.2.
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Sample CommandLine Usage:</strong>
 *     <pre>
 *     This command line can be used to print out the usage string:
 *     <b>java DependencyReportGeneratorUtility -help</b>
 *
 *     If all required configuration for the utility is stored in file config.xml,
 *     then the application can be executed without additional arguments:
 *     <b>java DependencyReportGeneratorUtility</b>
 *
 *     To use the custom configuration file the user can use "-c" switch:
 *     <b>java DependencyReportGeneratorUtility -c custom_config.xml</b>
 *
 *     The user can specify the input and output file names:
 *     <b>java DependencyReportGeneratorUtility -i dependencies.xml -o report.xml</b>
 *
 *     The user can specify the custom persistence options:
 *     <b>java DependencyReportGeneratorUtility -pclass myPackage.CustomDependenciesEntryPersistence -i custom.dat</b>
 *
 *     Generator implementation class can be specified as command line argument:
 *     <b>java DependencyReportGeneratorUtility -gclass myPackage.CustomDependencyReportGenerator</b>
 *
 *     For easiness "-f" switch can be used to specify one of XML, CSV and HTML output report formats:
 *     <b>java DependencyReportGeneratorUtility -f html -o report.html</b>
 *
 *     To specify that only external component dependencies must be included in the report the user can use
 *     "-dtype" switch:
 *     <b>java DependencyReportGeneratorUtility -dtype external</b>
 *
 *     To specify that only test dependencies must be included in the report the user can use "-dcat" switch:
 *     <b>java DependencyReportGeneratorUtility -dcat test</b>
 *
 *     To include in the report direct dependencies only this argument can be used:
 *     <b>java DependencyReportGeneratorUtility -noindirect</b>
 *
 *     To generate report for Java Logging Wrapper 2.0.0 and Java Object Factory 2.1.0 only the user can use
 *     "-id" switch in the following way:
 *     <b>java DependencyReportGeneratorUtility -id java-logging_wrapper-2.0.0;java-object_factory-2.1.0</b>
 *
 *     Usually the command line will look like the following one:
 *     <b>java DependencyReportGeneratorUtility -c custom_config.xml -f csv -i dependencies.xml -o report.txt
 *     -id java-logging_wrapper-2.0.0</b>
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe because it uses implementations of <code>DependencyReportGenerator</code> and
 *     <code>DependenciesEntryPersistence</code> that are not thread safe. But the user should not worry about thread
 *     safety because this class is used as a standalone utility and execution is made from a single thread. The only
 *     problem can appear if two instances of this application use the same persistence or destination report file
 *     simultaneously.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class DependencyReportGeneratorUtility {

    /**
     * <p>
     * The default config file.
     * </p>
     */
    private static final String DEFAULT_CONFIG_FILE = "config.xml";

    /**
     * <p>
     * Represents the "persistence_class" property.
     * </p>
     */
    private static final String PERSISTENCE_CLASS_PROPERTY = "persistence_class";

    /**
     * <p>
     * Represents the "persistence_config" child.
     * </p>
     */
    private static final String PERSISTENCE_CONFIG_CHILD = "persistence_config";

    /**
     * <p>
     * Represents the "generator_class" property.
     * </p>
     */
    private static final String GENERATOR_CLASS_PROPERTY = "generator_class";

    /**
     * <p>
     * Represents the "generator_config" child.
     * </p>
     */
    private static final String GENERATOR_CONFIG_CHILD = "generator_config";

    /**
     * <p>
     * Represents the "stdout" property.
     * </p>
     */
    private static final String STD_OUT_PROPERTY = "stdout";

    /**
     * <p>
     * Represents the "report_file_name" property.
     * </p>
     */
    private static final String REPORT_FILE_NAME_PROPERTY = "report_file_name";

    /**
     * <p>
     * Represents the "indirect" property.
     * </p>
     */
    private static final String INDIRECT_PROPERTY = "indirect";

    /**
     * <p>
     * Represents the "dependency_types" property of "generator_config" child.
     * </p>
     */
    private static final String DEPENDENCY_TYPES_PROPERTY = "dependency_types";

    /**
     * <p>
     * Represents the "dependency_categories" property of "generator_config" child.
     * </p>
     */
    private static final String DEPENDENCY_CATEGORIES_PROPERTY = "dependency_categories";
    
    /**
     * <p>
     * Represents the "circularwarning_color" property .
     * </p>
     */
    private static final String CIRCULAR_WARN_PROPERTY = "circularwarning_color";
    
    /**
     * <p>
     * Represents the "pathwarning_color" property .
     * </p>
     */
    private static final String PATH_WARN_PROPERTY = "pathwarning_color";
    
    /**
     * <p>
     * Represents the "version_conflict_color" property .
     * </p>
     */
    private static final String VERSION_WARN_PROPERTY = "version_conflict_color";

    /**
     * <p>
     * Represents the "file_name" property of "persistence_config" child.
     * </p>
     */
    private static final String FILE_NAME_PROPERTY = "file_name";

    /**
     * <p>
     * Represents the "c" switch.
     * </p>
     */
    private static final String CONFIG_FILE_SWITCH = "c";

    /**
     * <p>
     * Represents the "pclass" switch.
     * </p>
     */
    private static final String PERSISTENCE_CLASS_SWITCH = "pclass";

    /**
     * <p>
     * Represents the "i" switch.
     * </p>
     */
    private static final String INPUT_FILE_SWITCH = "i";

    /**
     * <p>
     * Represents the "o" switch.
     * </p>
     */
    private static final String OUTPUT_FILE_SWITCH = "o";

    /**
     * <p>
     * Represents the "f" switch.
     * </p>
     */
    private static final String FORMAT_SWITCH = "f";

    /**
     * <p>
     * Represents the "gclass" switch.
     * </p>
     */
    private static final String GENERATOR_CLASS_SWITCH = "gclass";

    /**
     * <p>
     * Represents the "dtype" switch.
     * </p>
     */
    private static final String DEPENDENCY_TYPE_SWITCH = "dtype";

    /**
     * <p>
     * Represents the "dcat" switch.
     * </p>
     */
    private static final String DEPENDENCY_CATEGORY_SWITCH = "dcat";

    /**
     * <p>
     * Represents the "id" switch.
     * </p>
     */
    private static final String COMPONENT_ID_SWITCH = "id";

    /**
     * <p>
     * Represents the "indirect" switch.
     * </p>
     */
    private static final String INDIRECT_SWITCH = "indirect";

    /**
     * <p>
     * Represents the "noindirect" switch.
     * </p>
     */
    private static final String NO_INDIRECT_SWITCH = "noindirect";

    /**
     * <p>
     * Represents the "h" switch.
     * </p>
     */
    private static final String HELP_SWITCH_1 = "h";

    /**
     * <p>
     * Represents the "help" switch.
     * </p>
     */
    private static final String HELP_SWITCH_2 = "help";

    /**
     * <p>
     * Represents the "?" switch.
     * </p>
     */
    private static final String HELP_SWITCH_3 = "?";

    /**
     * <p>
     * Represents the "xml" report format.
     * </p>
     */
    private static final String XML_FORMAT = "xml";

    /**
     * <p>
     * Represents the "csv" report format.
     * </p>
     */
    private static final String CSV_FORMAT = "csv";

    /**
     * <p>
     * Represents the "html" report format.
     * </p>
     */
    private static final String HTML_FORMAT = "html";

    /**
     * <p>
     * The instance of <code>ArgumentValidator</code> to ensure the switch value is non-null
     * and non-empty(trimmed). This instance is thread safe since it has no state.
     * </p>
     */
    private static final ArgumentValidator VALIDATOR = new ArgumentValidator() {

        /**
         * <p>
         * Takes a string from a command line switch and validates it.
         * </p>
         *
         * @param argument The string to validate.
         *
         * @throws ArgumentValidationException If given string is null or empty(trimmed).
         */
        public void validate(String argument) throws ArgumentValidationException {
            if (argument == null || argument.trim().length() == 0) {
                throw new ArgumentValidationException(argument, "Switch value must not be null or empty(trimmed).");
            }
        }
    };

    /**
     * <p>
     * Empty private constructor.
     * </p>
     */
    private DependencyReportGeneratorUtility() {
        //empty
    }

    /**
     * <p>
     * Add switches to <code>CommandLineUtility</code>.
     * </p>
     *
     * @param utility The <code>CommandLineUtility</code> to add switches.
     *
     * @throws IllegalSwitchException If any error occurs while adding switches.
     */
    private static void addSwitches(CommandLineUtility utility) throws IllegalSwitchException {
        utility.addSwitch(new Switch(CONFIG_FILE_SWITCH, false, 1, 1, VALIDATOR,
            "<file_name> Optional.\n\t\t"
            + " The name of the configuration file for this utility.\n\t\t"
            + " This file is read with use of Configuration Persistence component.\n\t\t"
            + " The structure of this file is described in the CS 3.2.2.\n\t\t"
            + " Default is 'config.xml'.\n\t\t"
            + " Note that configuration file is optional and the utility can work when it doesn't exist."));

        utility.addSwitch(new Switch(PERSISTENCE_CLASS_SWITCH, false, 1, 1, VALIDATOR,
            "<class_name> Optional.\n\t\t"
            + " The full class name of the DependenciesEntryPersistence implementation to be used.\n\t\t"
            + " This switch specifies or overrides the parameter 'persistence_class' from the CS 3.2.2.\n\t\t"
            + " DefaultXmlDependenciesEntryPersistence is a default persistence implementation."));

        utility.addSwitch(new Switch(INPUT_FILE_SWITCH, false, 1, 1, VALIDATOR,
            "<file_name> Optional.\n\t\t"
            + " The name of the input file that is used by DependenciesEntryPersistence implementation"
            + " to load the list of dependency entries.\n\t\t"
            + " This switch is not ignored only if DefaultXmlDependenciesEntryPersistence or"
            + " BinaryFileDependenciesEntryPersistence is used as persistence implementation.\n\t\t"
            + " This switch overrides the file name specified in 'persistence_config' mentioned in the CS 3.2.2."));

        utility.addSwitch(new Switch(FORMAT_SWITCH, false, 1, 1, VALIDATOR,
                "<format_name> Optional.\n\t\t"
                + " The name of the report format to be used.\n\t\t"
                + " Currently the following values are supported(case sensitive):\n\t\t"
                + "    'xml' - represents XmlDependencyReportGenerator\n\t\t"
                + "    'csv' - represents CsvDependencyReportGenerator\n\t\t"
                + "    'html' - represents HtmlDependencyReportGenerator.\n\t\t"
                + " This switch specifies or overrides the parameter 'generator_class' from the CS 3.2.2.\n\t\t"
                + " This switch must not be specified together with '-gclass' switch.\n\t\t"
                + " It duplicates functionality of '-gclass', but is much more easy-to-use.\n\t\t"
                + " Default format is 'xml'."));

        utility.addSwitch(new Switch(GENERATOR_CLASS_SWITCH, false, 1, 1, VALIDATOR,
            "<class_name> Optional.\n\t\t"
            + " The full class name of the DependencyReportGenerator implementation to be used.\n\t\t"
            + " This switch specifies or overrides the parameter 'generator_class' from the CS 3.2.2.\n\t\t"
            + " This switch must not be specified together with '-f' switch.\n\t\t"
            + " XmlDependencyReportGenerator is a default generator implementation."));

        utility.addSwitch(new Switch(DEPENDENCY_TYPE_SWITCH, false, 1, 1, VALIDATOR,
                "<types_list> Optional.\n\t\t"
                + " The list of dependency types those must be included into the generated report.\n\t\t"
                + " The semicolon-separated list of case-insensitive DependencyType values.\n\t\t"
                + " This switch can override the 'dependency_types' parameter mentioned in the CS 3.2.1.\n\t\t"
                + " In this case the configuration of BaseDependencyReportGenerator is provided with 'generator_config'"
                + " parameter from the CS 3.2.2.\n\t\t"
                + " Default is 'internal;external'."));

        utility.addSwitch(new Switch(DEPENDENCY_CATEGORY_SWITCH, false, 1, 1, VALIDATOR,
                "<categories_list> Optional.\n\t\t"
                + " The list of dependency categories those must be included into the generated report.\n\t\t"
                + " The semicolon-separated list of case-insensitive DependencyCategory values.\n\t\t"
                + " This switch can override the 'dependency_categories' parameter mentioned in the CS 3.2.1.\n\t\t"
                + " In this case the configuration of BaseDependencyReportGenerator is provided with 'generator_config'"
                + " parameter from the CS 3.2.2.\n\t\t"
                + " Default is 'compile;test'."));

        utility.addSwitch(new Switch(OUTPUT_FILE_SWITCH, false, 1, 1, VALIDATOR,
                "<file_name> Optional.\n\t\t"
                + " The report output file name.\n\t\t"
                + " Can override the parameter 'report_file_name' mentioned in the CS 3.2.2."));

        utility.addSwitch(new Switch(COMPONENT_ID_SWITCH, false, 1, 1, VALIDATOR,
                "<component_ids> Optional.\n\t\t"
                + " The semicolon-separated list of component IDs (see CS 1.3.6 for format).\n\t\t"
                + " E.G. 'java-logging_wrapper-2.0.0; object_factory-2.1.0,base_exception-1.0'.\n\t\t"
                + " If this switch is provided, then report is generated for specified components only.\n\t\t"
                + " If this switch is not specified, report is generated for all components that have a dependency"
                + " entry provided."));

        /*************    Following switches do NOT expect values, so no VALIDATOR          ***********/

        utility.addSwitch(new Switch(INDIRECT_SWITCH, false, 0, 0, null,
                "Optional.\n\t\t"
                + " Indicates that indirect dependencies should not be included into the generated report.\n\t\t"
                + " This switch must not be specified together with '-noindirect' switch.\n\t\t"
                + " This switch specifies or overrides the parameter 'indirect' from the CS 3.2.2."));

        utility.addSwitch(new Switch(NO_INDIRECT_SWITCH, false, 0, 0, null,
                "Optional.\n\t\t"
                + " Indicates that indirect dependencies should be included into the generated report.\n\t\t"
                + " This switch must not be specified together with '-indirect' switch.\n\t\t"
                + " This switch specifies or overrides the parameter 'indirect' from the CS 3.2.2."));

        utility.addSwitch(new Switch(HELP_SWITCH_1, false, 0, 0, null, "Prints out the usage message."));
        utility.addSwitch(new Switch(HELP_SWITCH_2, false, 0, 0, null, "Prints out the usage message."));
        utility.addSwitch(new Switch(HELP_SWITCH_3, false, 0, 0, null, "Prints out the usage message."));
    }

    /**
     * <p>
     * The main method of the standalone application.
     * </p>
     *
     * <p>
     * It reads parameters from the configuration files, uses Command Line Utility to parse command line arguments
     * and then can print out usage string or write the dependency report to the file or the standard output.
     * </p>
     *
     * <p>
     * Please see CS 3.2.2 and 3.2.3 for details about configuration parameters, supported command line
     * switches and arguments.
     * </p>
     *
     * <p>
     * This method will NOT throw any exception. Instead it will print out the detailed error explanation to the
     * standard output and terminate.
     * </p>
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        //The big try {} catch{} block, it catches all Exception since this method will NOT throw any exception
        try {

            // Create command line utility.
            CommandLineUtility utility = new CommandLineUtility(false, 0, 0);

            // Add all switches.
            addSwitches(utility);

            // Parse params.
            utility.parse(args);

            // Map of valid switches name->value.
            // Since switch values have validated using VALIDATOR, so it is safe to use this map in below code.
            Map < String, String > switchesMap = getValidSwitches(utility);

            // If user requests the help (with -h, -? or -help)
            if (switchesMap.containsKey(HELP_SWITCH_1) || switchesMap.containsKey(HELP_SWITCH_2)
                || switchesMap.containsKey(HELP_SWITCH_3)) {
                System.out.println(utility.getUsageString());
                return;
            }

            // The "-f" and "-gclass" must not be used together
            checkExclude(new String[]{FORMAT_SWITCH, GENERATOR_CLASS_SWITCH}, switchesMap);

            // The "-indirect" and "-noindirect" must not be used together
            checkExclude(new String[]{INDIRECT_SWITCH, NO_INDIRECT_SWITCH}, switchesMap);

            // Get the config file name
            String fileName = getConfigFileName(switchesMap);

            // If no config file available, use a default empty ConfigurationObject
            ConfigurationObject config = fileName == null ? new DefaultConfigurationObject("default") : null;

            if (fileName != null) {
                // Load ConfigurationObject from config file
                String namespace = DependencyReportGeneratorUtility.class.getName();
                ConfigurationFileManager manager = new ConfigurationFileManager();
                manager.loadFile(namespace, fileName);
                // ConfigurationFileManager#getConfiguration(String namespace) will never return null
                // UnrecognizedNamespaceException is thrown if no such namespace
                config = manager.getConfiguration(namespace).getChild(namespace);
                if (config == null) {
                    throw new BaseException("There is no '" + namespace + "' namespace within file: " + fileName);
                }
            }

            // Now we have both the configuration and switches in hand, generate report
            generateReport(config, switchesMap);

        } catch (Exception e) {
            // Print error message, and terminate
            printError(e);
        }
    }
    
    /**
     * <p>
     * Generate report.
     * </p>
     *
     * @param config The configuration.
     * @param switchesMap The parsed switches map.
     *
     * @throws Exception If any error occurs while generating report.
     */
    private static void generateReport(ConfigurationObject config
            , Map < String, String > switchesMap) throws Exception {

        // Get DependenciesEntryPersistence and DependencyReportGenerator
        DependencyReportGenerator generator = getDependencyReportGenerator(config, switchesMap,
            getDependenciesEntryPersistence(config, switchesMap));
        
        String color=Helper.getStringProperty(config, CIRCULAR_WARN_PROPERTY, "0000FF");
        String pathcolor=Helper.getStringProperty(config, PATH_WARN_PROPERTY, "00FF00");
        String versioncolor=Helper.getStringProperty(config, VERSION_WARN_PROPERTY, "FF0000");
        
        if(generator instanceof HtmlDependencyReportGenerator){
        	((HtmlDependencyReportGenerator)generator).setCircularColor(color);
        	((HtmlDependencyReportGenerator)generator).setPath_warn_color(pathcolor);
        	((HtmlDependencyReportGenerator)generator).setVersion_warn_color(versioncolor);
        }

        // Get output file name of generated report
        String outputFileName = getParameter(switchesMap, OUTPUT_FILE_SWITCH, config, REPORT_FILE_NAME_PROPERTY, null);

        // Get the list of componentIds
        List < String > componentIds = getComponentIds(switchesMap);

        // Get the indirect value
        boolean indirect = getIndirect(config, switchesMap);

        if (outputFileName == null) {
            // Print to standard out
            boolean stdout = Helper.getBooleanProperty(config, STD_OUT_PROPERTY, Boolean.TRUE);
            if (!stdout) {
                throw new BaseException("There is no output report file name specified/configured,"
                    + " and stdout is disallowed.");
            }
            System.out.println(componentIds != null ? generator.generate(componentIds, indirect)
                : generator.generateAll(indirect));
        } else {
            // Generate to file
            if (componentIds != null) {
                generator.generate(componentIds, outputFileName, indirect);
            } else {
                generator.generateAll(outputFileName, indirect);
            }
        }
    }

    /**
     * <p>
     * Gets the valid switch names and values.
     * </p>
     *
     * @param utility The <code>CommandLineUtility</code> to parse switches.
     *
     * @return The map from valid switch name to value.
     */
    private static Map < String, String > getValidSwitches(CommandLineUtility utility) {
        // Get valid switches.
        List list = utility.getValidSwitches();

        // Map of valid switches name->value.
        Map < String, String > map = new HashMap<String, String>();

        // Fill map of valid switches.
        for (int i = 0; i < list.size(); i++) {
            Switch aSwitch = (Switch) list.get(i);
            map.put(aSwitch.getName(), aSwitch.getValue());
        }
        return map;
    }

    /**
     * <p>
     * Checks that only one switch from <code>unique</code> array exist in
     * <code>switchesMap</code>.
     * </p>
     *
     * @param unique The array of names of switches those must not be present together.
     * @param switchesMap The parsed switches map.
     *
     * @throws BaseException If these switches are used together.
     */
    private static void checkExclude(String[] unique, Map < String, String > switchesMap) throws BaseException {
        // Count of parsed switches from unique array.
        int count = 0;

        // Calculate count.
        for (int i = 0; i < unique.length; i++) {
            if (switchesMap.containsKey(unique[i])) {
                count++;
            }
        }

        if (count > 1) {
            // If these switches are used together, then throw error.
            StringBuffer sb = new StringBuffer();
            sb.append("The switches [");
            for (int i = 0; i < unique.length; i++) {
                sb.append("-" + unique[i]);
                if (i != unique.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("] can not be used together.");
            throw new BaseException(sb.toString());
        }
    }

    /**
     * <p>
     * Get config file name.
     * </p>
     *
     * @param switchesMap The parsed switches map.
     *
     * @return The config file name if exist. May be null if file does not exist.
     *
     * @throws IOException If I/O error occurs.
     */
    private static String getConfigFileName(Map < String, String > switchesMap) throws IOException {

        String configFileName = null;

        boolean configFileExist = false;

        // Get the config file name specified by command line
        if (switchesMap.containsKey(CONFIG_FILE_SWITCH)) {
            configFileName = switchesMap.get(CONFIG_FILE_SWITCH);
            configFileExist = checkFileExist(configFileName);
            if (!configFileExist) {
                System.out.println("Warning: The config file '" + configFileName + "' does not exist."
                        + " Default config file 'config.xml' will be used.");
            }
        }

        //If the config file is not specified by command line, or if it does not exist, use default config.xml
        if (!configFileExist) {
            configFileName = DEFAULT_CONFIG_FILE;
            configFileExist = checkFileExist(configFileName);
        }

        //If the config.xml also does not exist, use default configuration values mentioned in CS3.2.2
        if (!configFileExist) {
            System.out.println("Warning: The default config file 'config.xml' does not exist."
                    + " Default configuration values mentioned in CS3.2.2 will be used.");
            configFileName = null;
        }

        return configFileName;
    }

    /**
     * <p>
     * Check whether the file exists.
     * </p>
     *
     * <p>
     * This is a scratch from <code>ConfigurationFileManager.getFile()</code>.
     * </p>
     *
     * @param fileName The file name to check.
     *
     * @return True if file exists; False otherwise.
     *
     * @throws IOException If any error occurs while checking.
     */
    private static boolean checkFileExist(String fileName) throws IOException {
        File file = null;

        URL url = DependencyReportGeneratorUtility.class.getClassLoader().getResource(fileName);

        //The ConfigurationFileManager.getFile() searches the class-path first, then file system
        //Here we duplicate the same functionality to check whether file exists

        file = url != null ? new File(URLDecoder.decode(url.getFile(), "utf-8")) : new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            return true;
        }

        return false;
    }

    /**
     * <p>
     * Gets the list of component IDs by parsing the switch value.
     * </p>
     *
     * @param switchesMap The parsed switches map.
     *
     * @return The list of component IDs parsed from switch value. May be null if not specified.
     *
     * @throws BaseException If any component ID is empty(trimmed). Or if "-id" switch
     *         is present but there is no component ID specified.
     */
    private static List < String > getComponentIds(Map < String, String > switchesMap) throws BaseException {
        if (switchesMap.containsKey(COMPONENT_ID_SWITCH)) {
            List < String > componentIds = new ArrayList<String>();

            for (String componentId : switchesMap.get(COMPONENT_ID_SWITCH).split(Helper.SEPARATOR)) {
                //Trim the component id
                componentId = componentId.trim();

                //Component id should not be empty
                if (componentId.length() == 0) {
                    throw new BaseException("Component ID must not be empty(trimmed).");
                }
                componentIds.add(componentId);
            }

            //If no component id parsed, raise error
            if (componentIds.isEmpty()) {
                throw new BaseException("'-id' switch is present, but there is no component ID specified.");
            }

            return componentIds;
        }
        return null;
    }

    /**
     * <p>
     * Gets the indirect value.
     * </p>
     *
     * @param config The configuration.
     * @param switchesMap The parsed switches map.
     *
     * @return The indirect value.
     *
     * @throws DependencyReportConfigurationException If any error occurs while loading configuration.
     */
    private static boolean getIndirect(ConfigurationObject config
            , Map < String, String > switchesMap) throws DependencyReportConfigurationException {
        if (switchesMap.containsKey(INDIRECT_SWITCH)) {
            return true;
        } else if (switchesMap.containsKey(NO_INDIRECT_SWITCH)) {
            return false;
        } else {
            return Helper.getBooleanProperty(config, INDIRECT_PROPERTY, Boolean.TRUE);
        }
    }

    /**
     * <p>
     * Gets an instance of <code>DependencyReportGenerator</code>.
     * </p>
     *
     * @param config The configuration.
     * @param switchesMap The parsed switches map.
     * @param persistence The <code>DependenciesEntryPersistence</code>.
     *
     * @return The <code>DependencyReportGenerator</code> created.
     *
     * @throws Exception If any error occurs while creating <code>DependencyReportGenerator</code>.
     */
    private static DependencyReportGenerator getDependencyReportGenerator(ConfigurationObject config
            , Map < String, String > switchesMap, DependenciesEntryPersistence persistence) throws Exception {
        String generatorClassName = null;

        if (switchesMap.containsKey(FORMAT_SWITCH)) {
            // If format is specified
            String format = switchesMap.get(FORMAT_SWITCH).trim();

            // Format is case sensitive
            if (XML_FORMAT.equals(format)) {
                generatorClassName = XmlDependencyReportGenerator.class.getName();
            } else if (CSV_FORMAT.equals(format)) {
                generatorClassName = CsvDependencyReportGenerator.class.getName();
            } else if (HTML_FORMAT.equals(format)) {
                generatorClassName = HtmlDependencyReportGenerator.class.getName();
            } else {
                // Format not recognized
                throw new BaseException("The format must be either 'xml', 'csv' or 'html' (case sensitive).");
            }

        } else {
            // Get the class name
            generatorClassName = getParameter(switchesMap, GENERATOR_CLASS_SWITCH, config, GENERATOR_CLASS_PROPERTY,
                XmlDependencyReportGenerator.class.getName());
        }

        ConfigurationObject generatorConfig = getChild(config, GENERATOR_CONFIG_CHILD);

        if (switchesMap.containsKey(DEPENDENCY_TYPE_SWITCH)) {
            // Override the "dependency_types" configuration if specified in command line
            String allowedDependencyTypes = switchesMap.get(DEPENDENCY_TYPE_SWITCH);
            generatorConfig.setPropertyValue(DEPENDENCY_TYPES_PROPERTY, allowedDependencyTypes);
        }

        if (switchesMap.containsKey(DEPENDENCY_CATEGORY_SWITCH)) {
            // Override the "dependency_categories" configuration if specified in command line
            String allowedDependencyCategories = switchesMap.get(DEPENDENCY_CATEGORY_SWITCH);
            generatorConfig.setPropertyValue(DEPENDENCY_CATEGORIES_PROPERTY, allowedDependencyCategories);
        }

        // Create DependencyReportGenerator by reflection
        return (DependencyReportGenerator) Class.forName(generatorClassName)
            .getConstructor(new Class[]{DependenciesEntryPersistence.class, ConfigurationObject.class})
            .newInstance(new Object[]{persistence, generatorConfig});
    }

    /**
     * <p>
     * Gets an instance of <code>DependenciesEntryPersistence</code>.
     * </p>
     *
     * @param config The configuration.
     * @param switchesMap The parsed switches map.
     *
     * @return The <code>DependenciesEntryPersistence</code> created.
     *
     * @throws Exception If any error occurs while creating <code>DependenciesEntryPersistence</code>.
     */
    private static DependenciesEntryPersistence getDependenciesEntryPersistence(ConfigurationObject config
        , Map < String, String > switchesMap) throws Exception {

        // Get the class name
        String persistenceClassName =
            getParameter(switchesMap, PERSISTENCE_CLASS_SWITCH,
                         config, PERSISTENCE_CLASS_PROPERTY,
                         DefaultXmlDependenciesEntryPersistence.class.getName());

        ConfigurationObject persistenceConfig = getChild(config, PERSISTENCE_CONFIG_CHILD);

        if (DefaultXmlDependenciesEntryPersistence.class.getName().equalsIgnoreCase(persistenceClassName)
            || BinaryFileDependenciesEntryPersistence.class.getName().equalsIgnoreCase(persistenceClassName)) {
            //The "-i" switch is accepted only if DefaultXmlDependenciesEntryPersistence
            //or BinaryFileDependenciesEntryPersistence is used as persistence implementation.
            if (switchesMap.containsKey(INPUT_FILE_SWITCH)) {
                // Override the "file_name" configuration if specified in command line
                String inputFileName = switchesMap.get(INPUT_FILE_SWITCH);
                persistenceConfig.setPropertyValue(FILE_NAME_PROPERTY, inputFileName);
            }
        }

        // Create DependenciesEntryPersistence by reflection
        return (DependenciesEntryPersistence) Class.forName(persistenceClassName)
            .getConstructor(new Class[]{ConfigurationObject.class})
            .newInstance(new Object[]{persistenceConfig});
    }

    /**
     * <p>
     * Get the parameter value.
     * </p>
     *
     * <p>
     * If the parameter is specified in switches, then the switch value will be used.
     * Else if the parameter is specified in configuration, then the configuration property value will be used.
     * Otherwise the default value will be used.
     * </p>
     *
     * @param switchesMap The parsed switches map.
     * @param switchName The name of switch.
     * @param config The configuration.
     * @param propertyName The property name in configuration.
     * @param defaultValue The default value.
     *
     * @return The parameter value.
     *
     * @throws DependencyReportConfigurationException If any error occurs while retrieving value.
     */
    private static String getParameter(Map < String, String > switchesMap, String switchName,
        ConfigurationObject config, String propertyName, String defaultValue)
        throws DependencyReportConfigurationException {
        if (switchesMap.containsKey(switchName)) {
            // The argument provided by command line has higher priority
            return switchesMap.get(switchName);
        } else {
            // If not provided by command line, load from ConfigurationObject
            // If still missing in ConfigurationObject, use defaultValue
            return Helper.getStringProperty(config, propertyName, defaultValue);
        }
    }

    /**
     * <p>
     * Gets child from configuration.
     * </p>
     *
     * <p>
     * If there is no such child, an empty child will be returned.
     * </p>
     *
     * @param config The configuration.
     * @param childName The name of child.
     *
     * @return The child. Will never be null.
     *
     * @throws ConfigurationAccessException If any error occurs while retrieving child.
     */
    private static ConfigurationObject getChild(ConfigurationObject config, String childName)
        throws ConfigurationAccessException {
        ConfigurationObject child = config.getChild(childName);

        //If no such child, return an empty one
        if (child == null) {
            child = new DefaultConfigurationObject(childName);
        }

        return child;
    }

    /**
     * <p>
     * Print error.
     * </p>
     *
     * @param e The exception occurred.
     */
    private static void printError(Exception e) {
        System.out.println("Failed to generate report:");
        if (e instanceof BaseException) {
            //For TCS specific exception, print out the error message
            System.out.println(e.getMessage());
        } else {
            //For any other exception, print out the stack trace
            e.printStackTrace(System.out);
        }
        System.out.println("Use -h, -help or -? to show help message.");
    }
}
