/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor.utility;

import static java.lang.System.out;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.InvalidConfigurationException;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.dependency.BaseDependenciesEntryExtractor;
import com.topcoder.util.dependency.ComponentDependencyConfigurationException;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.DependenciesEntryExtractor;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.Utils;
import com.topcoder.util.dependency.extractor.MultipleFormatDependenciesEntryExtractor;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the main class of standalone application that can be used for extracting component dependencies with use of
 * command line. It reads all configuration data from the configuration file. Some of this data can be overridden by the
 * user with use of command line switches and arguments. By default, this utility uses
 * MultipleFormatDependenciesEntryExtractor to extract dependencies from the specified files and
 * DefaultXmlDependenciesEntryPersistence to save the extracted dependencies in the file. But the user can specify
 * another DependenciesEntryExtractor and DependenciesEntryPersistence implementations to be used and name of the file
 * where dependencies must be written to. The user can specify the list of input files for the extractor with use of
 * file path masks. These masks can contain wildcard characters in folder and file names . Please see section 3.2.3 of
 * CS for the full list of configuration parameters and command line switches supported by this component.
 * </p>
 * <p>
 * <b>Sample usage</b>
 *
 * <pre>
 *     To use the custom configuration file the user can use &quot;-c&quot; switch:
 *     java ComponentDependencyExtractorUtility -c custom_config.xml
 *
 *     The user can specify the input file mask and output file names:
 *     java ComponentDependencyExtractorUtility -i build.xml -o dependencies.xml
 *
 *     The user can specify the custom persistence options:
 *     java ComponentDependencyExtractorUtility -pclass myPackage.CustomDependenciesEntryPersistence -o custom.dat
 *
 *     more details see CS 4.3.4
 * </pre>
 *
 * <b>Sample Configuration</b>
 *
 * <pre>
 *     &lt;?xml version=&quot;1.0&quot;?&gt;
 *     &lt;CMConfig&gt;
 *     &lt;Config name=&quot;com.topcoder.util.dependency.extractor.utility.ComponentDependencyExtractorUtility&quot;&gt;
 *     &lt;property name=&quot;object_factory_config_name&quot;&gt;
 *     &lt;value&gt;object_factory_config&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;persistence_class&quot;&gt;
 *     &lt;value&gt;com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;log_factory_key&quot;&gt;
 *     &lt;value&gt;lf&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;logger_name&quot;&gt;
 *     &lt;value&gt;logger&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;extractor_class&quot;&gt;
 *     &lt;value&gt;com.topcoder.util.dependency.extractor.MultipleFormatDependenciesEntryExtractor&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;dependency_types&quot;&gt;
 *     &lt;value&gt;internal;external&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;dependency_categories&quot;&gt;
 *     &lt;value&gt;compile;test&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;file_masks&quot;&gt;
 *     &lt;value&gt;test_files/scripts/*&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;object_factory_config&quot;&gt;
 *     &lt;property name=&quot;lf&quot;&gt;
 *     &lt;property name=&quot;type&quot;&gt;
 *     &lt;value&gt;com.topcoder.util.log.basic.BasicLogFactory&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;/property&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;persistence_config&quot;&gt;
 *     &lt;property name=&quot;file_name&quot;&gt;
 *     &lt;value&gt;test_files/report1.xml&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;/property&gt;
 *     &lt;/Config&gt;
 *     &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because it uses implementations of DependenciesEntryExtractor and
 * DependenciesEntryPersistence that are not thread safe. But the user should not worry about thread safety because this
 * class is used as a standalone utility and execution is made from a single thread. The only problem can appear if two
 * instances of this application use the same persistence simultaneously.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDependencyExtractorUtility {
    /**
     * <p>
     * Configuration name space.
     * </p>
     */
    private static final String CONFIG_NAME_SPACE = ComponentDependencyExtractorUtility.class.getName();

    /**
     * <p>
     * Switch constant.Optional. Provides the name of the configuration file for this utility. This file is read with
     * use of Configuration Persistence component. The structure of this file is described in the section 3.2.2. Default
     * is "config.xml".
     *
     * </p>
     */
    private static final String SWITCH_CONFIGURATION_FILE = "c";

    /**
     * <p>
     * Switch constant.Optional. The full class name of the DependenciesEntryPersistence implementation to be used. This
     * switch specifies or overrides the parameter "persistence_class" from the section 3.2.2. This switch must not be
     * specified together with "-f" switch. DefaultXmlDependenciesEntryPersistence is a default persistence
     * implementation.
     *
     * </p>
     */
    private static final String SWITCH_PERSISTENCE_CLASS = "pclass";

    /**
     * <p>
     * Switch constant.Optional. The output file name for the list of extracted dependency entries. Can override the
     * parameter "file_name" mentioned in the section 3.2.1. In this case persistence configuration must be specified in
     * section "persistence_config" mentioned in the section 3.2.2.
     * </p>
     */
    private static final String SWITCH_OUTPUT_FILE_NAME = "o";

    /**
     * <p>
     * Switch constant.Optional. The name of the output file format to be used. Currently the following values are
     * supported: "xml" - represents DefaultXmlDependenciesEntryPersistence and "binary" - represents
     * BinaryFileDependenciesEntryPersistence. This switch specifies or overrides the parameter "persistence_class" from
     * the section 3.2.2. This switch must not be specified together with "-pclass" switch. It duplicates functionality
     * of "-pclass", but is much more easy-to-use. Default format is "xml".
     *
     * </p>
     */
    private static final String SWITCH_FORMAT_NAME = "f";

    /**
     * <p>
     * Switch constant.Optional. The full class name of the DependencyEntryExtractor implementation to be used. This
     * switch specifies or overrides the parameter "extractor_class" from the section 3.2.2.
     * MultipleFormatDependenciesEntryExtractor is a default extractor implementation.
     *
     * </p>
     */
    private static final String SWITCH_EXTRACTOR_CLASS = "eclass";

    /**
     * <p>
     * Switch constant.Optional. The list of dependency types those must be extracted from build/distribution files. The
     * comma-separated list of case-insensitive DependencyType values. This switch can override the "dependency_types"
     * parameter mentioned in the section 3.2.2. Default is "internal,external".
     *
     * </p>
     */
    private static final String SWITCH_DEPENDENCY_TYPE = "dtype";

    /**
     * <p>
     * Switch constant.Optional. The list of dependency categories those must be extracted from build/distribution
     * files. The comma-separated list of case-insensitive DependencyCategory values. This switch can override the
     * "dependency_categories" parameter mentioned in the section 3.2.2. Default is "compile,test".
     *
     * </p>
     */
    private static final String SWITCH_DEPENDENCY_CATEGORY = "dcat";

    /**
     * <p>
     * Switch constant.Optional. The semicolon-delimited list of file path masks. These masks are used to locate
     * build/distribution files. This parameter accepts wildcard characters in folder and file names. Wildcard character
     * ('*') matches any file path substring that doesn't contain path separators. This switch can override the
     * "input_path" parameter mentioned in the section 3.2.2.
     * </p>
     */
    private static final String SWITCH_FILE_MASKS = "i";

    /**
     * <p>
     * Switch constant.Indicates that logging should not be used. If this switch is not provided, logging is used only
     * if logger name is specified in the configuration file (see the section 3.2.2).
     * </p>
     */
    private static final String SWITCH_NO_LOG = "nolog";

    /**
     * <p>
     * Switch constant.
     * </p>
     */
    private static final String SWITCH_IGNORE_ERRORS = "ignore_errors";

    /**
     * <p>
     * Switch constant. When this is provided, the application prints out the usage string to the standard output and
     * terminates immediately.
     * </p>
     */
    private static final String SWITCH_HELP1 = "help";

    /**
     * <p>
     * Switch constant.When this is provided, the application prints out the usage string to the standard output and
     * terminates immediately.
     * </p>
     */
    private static final String SWITCH_HELP2 = "?";

    /**
     * <p>
     * Switch constant.When this is provided, the application prints out the usage string to the standard output and
     * terminates immediately.
     * </p>
     */
    private static final String SWITCH_HELP3 = "h";

    /**
     * <p>
     * The name of the section that holds Object Factory configuration. Default is "object_factory_config".String. Not
     * empty. Optional.
     * </p>
     */
    private static final String KEY_OBJECT_FACTORY_CONFIG_NAME = "object_factory_config_name";

    /**
     * <p>
     * Default value for object factory config name.
     * </p>
     */
    private static final String DEFAULT_OBJECT_FACTORY_CONFIG_NAME = "object_factory_config";

    /**
     * <p>
     * The full class name of the DependenciesEntryPersistence implementation that is used by the utility when saving
     * the extracted dependencies list. Can be specified or overridden in the command line.String. Not empty. Optional.
     * </p>
     */
    private static final String KEY_PERSISTENCE_CLASS = "persistence_class";

    /**
     * <p>
     * Default value for persistence class.
     * </p>
     */
    private static final String DEFAULT_PERSISTENCE_CLASS = DefaultXmlDependenciesEntryPersistence.class.getName();

    /**
     * <p>
     * The configuration for the used DependenciesEntryPersistence implementation. Some attributes of this object can be
     * specified or overridden in the command line. ConfigurationObject. Required.
     * </p>
     */
    private static final String KEY_PERSISTENCE_CONFIG = "persistence_config";

    /**
     * <p>
     * The Object Factory key that is used to create LogFactory instance. If not specified default log factory from
     * LogManager is used.String. Not empty. Optional.
     * </p>
     */
    private static final String KEY_LOG_FACTORY_KEY = "log_factory_key";

    /**
     * <p>
     * The name that is passed to LogManager to create a Log instance.String. Not empty. Required.
     * </p>
     */
    private static final String KEY_LOGGER_NAME = "logger_name";

    /**
     * <p>
     * The full class name of the DependenciesEntryExtractor implementation that is used by the utility when saving the
     * extracted dependencies list. Can be specified or overridden in the command line. Default is
     * "com.topcoder.util.dependency.extractor.MultipleFormatDependenciesEntryExtractor".String. Not empty. Optional.
     * </p>
     */
    private static final String KEY_EXTRACTOR_CLASS = "extractor_class";

    /**
     * <p>
     * Default value for extractor class.
     * </p>
     */
    private static final String DEFAULT_EXTRACTOR_CLASS = MultipleFormatDependenciesEntryExtractor.class.getName();

    /**
     * <p>
     * The list of dependency types those must be extracted from build/distribution files. The comma-separated list of
     * case-insensitive DependencyType values. This parameter is used only if extractor is of
     * BaseDependenciesEntryExtractor type. Default is "internal;external".String. Not empty. Optional.
     * </p>
     */
    private static final String KEY_DEPENDENCY_TYPES = "dependency_types";

    /**
     * <p>
     * The default dependency types string.
     * </p>
     */
    private static final String DEFAULT_DEPENDENCY_TYPES = "internal;external";

    /**
     * <p>
     * The list of dependency categories those must be extracted from build/distribution files. The comma-separated list
     * of case-insensitive DependencyCategory values. This parameter is used only if extractor is of
     * BaseDependenciesEntryExtractor type. Default is "compile;test".String. Not empty. Optional.
     * </p>
     */
    private static final String KEY_DEPENDENCY_CATEGORIES = "dependency_categories";

    /**
     * <p>
     * Default value for dependency categories.
     * </p>
     */
    private static final String DEFAULT_DEPENDENCY_CATEGORIES = "compile;test";

    /**
     * <p>
     * The list of file path masks. These masks are used to locate build/distribution files. Then component dependencies
     * are extracted from the located files. This parameter accepts wildcard characters in folder and file names.
     * Wildcard character ('*') matches any file path substring that doesn't contain path separators.Multiple strings.
     * Cannot contain empty. Optional.
     * </p>
     *
     */
    private static final String KEY_FILE_MASKS = "file_masks";

    /**
     * <p>
     * Constant for type value: internal.
     * </p>
     */
    private static final String TYPE_INTERNAL = "internal";

    /**
     * <p>
     * Constant for type value: external.
     * </p>
     */
    private static final String TYPE_EXTERNAL = "external";

    /**
     * <p>
     * Constant for category value: compile.
     * </p>
     */
    private static final String CATEGORY_COMPILE = "compile";

    /**
     * <p>
     * Constant for category value: test.
     * </p>
     */
    private static final String CATEGORY_TEST = "test";

    /**
     * <p>
     * Constant for semi-colon separator. It will be used for every situation where values needs to be seperated in one
     * string
     * </p>
     */
    private static final String LIST_SEPARATEOR = ";";

    /**
     * <p>
     * Default configuration file name. It will be used if it is not specified in -c switch.
     * </p>
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "config.xml";

    /**
     * <p>
     * Empty private constructor.
     * </p>
     */
    private ComponentDependencyExtractorUtility() {
        // Do nothing.
    }

    /**
     * <p>
     * The main method of the standalone application. It reads parameters from the configuration files, uses Command
     * Line Utility to parse command line arguments and then can print out usage string or extract dependencies from the
     * specified files and write them to the configured persistence.
     * </p>
     * <p>
     * This method MUST NOT throw any exception. Instead it must print out the detailed error explanation to the
     * standard output and terminate.
     * </p>
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // some details see section 1.3.5 of CS.
        // see sections 3.2.2 and 3.2.3 of CS for details about configuration parameters, supported command line
        // switches and arguments.
        Log logger = null;
        try {
            // initialize command line utility
            CommandLineUtility commandLineUtility = initCommandLineUtility(args);
            // parse
            commandLineUtility.parse(args);

            // help is requested?
            if (isHelpRequested(commandLineUtility)) {
                out.println(commandLineUtility.getUsageString());
                return;
            }

            // load configuration
            ConfigurationFileManager manager = new ConfigurationFileManager();
            String configFileName = commandLineUtility.getSwitch(SWITCH_CONFIGURATION_FILE).getValue();
            if (configFileName == null) {
                configFileName = DEFAULT_CONFIGURATION_FILE;
            }
            manager.loadFile(CONFIG_NAME_SPACE, configFileName);
            // fix for original step. new configuration manager will load all in xml
            ConfigurationObject config = manager.getConfiguration(CONFIG_NAME_SPACE).getChild(CONFIG_NAME_SPACE);
            if (config == null) {
                logError(logger, "configuration is null under namespace<" + CONFIG_NAME_SPACE + "> in file<"
                    + configFileName + ">");
                return;
            }

            // persistence class name/config
            String persistenceClassName = getPersistenceClassName(commandLineUtility, config);
            ConfigurationObject persistenceConfig = getPersistenceConfig(commandLineUtility, config);

            // log factory/logger
            String logFactoryKey = Utils.getOptionalStringValue(config, KEY_LOG_FACTORY_KEY);
            if (logFactoryKey != null) {
                String objectFactoryConfigName = Utils.getOptionalStringValue(config, KEY_OBJECT_FACTORY_CONFIG_NAME);
                if (objectFactoryConfigName == null) {
                    objectFactoryConfigName = DEFAULT_OBJECT_FACTORY_CONFIG_NAME;
                }
                LogFactory logFactory = Utils.createObject(LogFactory.class, config, objectFactoryConfigName,
                    KEY_LOG_FACTORY_KEY);
                LogManager.setLogFactory(logFactory);
            }
            String loggerName = Utils.getRequiredStringValue(config, KEY_LOGGER_NAME);
            logger = isSwtichSpecified(commandLineUtility, SWITCH_NO_LOG) ? null : LogManager.getLog(loggerName);

            // extractor
            String extractorClassName = getExtractorClassName(commandLineUtility, config);
            DependenciesEntryExtractor extractor = createExtractor(extractorClassName, logger, getDependencyTypes(
                commandLineUtility, config), getDependencyCategories(commandLineUtility, config));
            DependenciesEntryPersistence persistence = createPersistence(persistenceClassName, persistenceConfig);

            Set<String> fileMasks = getFileMasks(commandLineUtility, config, logger);
            boolean ignoreErrors = isSwtichSpecified(commandLineUtility, SWITCH_IGNORE_ERRORS);

            // handle all files
            List<DependenciesEntry> resultDependencies = new ArrayList<DependenciesEntry>();
            for (String fileMask : fileMasks) {
                List<String> filesToProcess = findFiles(fileMask);
                logInfo(logger, "files to be processed: " + filesToProcess);
                for (String filePath : filesToProcess) {
                    if (extractor.isSupportedFileType(filePath)) {
                        try {
                            resultDependencies.add(extractor.extract(filePath));
                        } catch (DependenciesEntryExtractionException e) {
                            if (ignoreErrors) {
                                // log it and ignore it and continue to process
                                logWarn(logger, "DependenciesEntryExtractionException occurs when handling file<"
                                    + filePath + ">.", e);
                            } else {
                                // log and return
                                logException(logger, e);
                                return;
                            }
                        }
                    }
                }
            } // for fileMasks

            persistence.save(resultDependencies);
            logInfo(logger, "It extracts and saves dependencies information for  " + resultDependencies.size()
                + " components successfully.");
        } catch (Exception e) {
            // PLEASE refer to http://forums.topcoder.com/?module=Thread&threadID=615246&start=0
            logException(logger, e);
        }
    }

    /**
     * <p>
     * Initiate a new command line utility.
     * </p>
     *
     * @param args arguments
     * @return a new command line utility
     *
     * @throws IllegalSwitchException if any switch is invalid. it should not occur.
     */
    private static CommandLineUtility initCommandLineUtility(String[] args) throws IllegalSwitchException {
        CommandLineUtility commandLineUtility = new CommandLineUtility();

        commandLineUtility
            .addSwitch(new Switch(SWITCH_CONFIGURATION_FILE, false, 1, 1, null, "-c <configuration file>"));
        commandLineUtility.addSwitch(new Switch(SWITCH_PERSISTENCE_CLASS, false, 1, 1, null,
            "-pclass <persistence class name>"));
        commandLineUtility.addSwitch(new Switch(SWITCH_OUTPUT_FILE_NAME, false, 1, 1, null, "-o <output file name>"));
        commandLineUtility.addSwitch(new Switch(SWITCH_FORMAT_NAME, false, 1, 1, null,
            "-f <format_name> format_name is a semi-colon separated string and value is xml or binary"));
        commandLineUtility.addSwitch(new Switch(SWITCH_EXTRACTOR_CLASS, false, 0, 1, null,
            "-eclass <extractor class name>"));
        commandLineUtility.addSwitch(new Switch(SWITCH_DEPENDENCY_TYPE, false, 0, 1, null,
            "-dtype <types_list> list is semi-colon separated string. value is internal or external"));
        commandLineUtility.addSwitch(new Switch(SWITCH_DEPENDENCY_CATEGORY, false, 0, 1, null,
            "-dcate <categories_list> list is semi-colon separated string .value is compile or test"));
        commandLineUtility.addSwitch(new Switch(SWITCH_FILE_MASKS, false, 0, -1, null,
            "-i <file masks> semi-colon separated list. example: */*/*.jar;*.xml"));
        commandLineUtility.addSwitch(new Switch(SWITCH_NO_LOG, false, 0, 0, null, "no logging if specified"));
        commandLineUtility.addSwitch(new Switch(SWITCH_IGNORE_ERRORS, false, 0, 0, null, "ignore errors if specified"));
        commandLineUtility.addSwitch(new Switch(SWITCH_HELP1, false, 0, 0, null, "show help"));
        commandLineUtility.addSwitch(new Switch(SWITCH_HELP2, false, 0, 0, null, "show help"));
        commandLineUtility.addSwitch(new Switch(SWITCH_HELP3, false, 0, 0, null, "show help"));

        return commandLineUtility;
    }

    /**
     * <p>
     * Gets persistence class name through switches and configuration.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param config configuration object
     * @return persistence class name
     *
     * @throws ComponentDependencyConfigurationException if switch contains empty value or -f and -pclass both are set
     *             or error while getting value from configuration
     */
    private static String getPersistenceClassName(CommandLineUtility commandLineUtility, ConfigurationObject config)
        throws ComponentDependencyConfigurationException {
        // see http://forums.topcoder.com/?module=Thread&threadID=615245&start=0&mc=8#983770
        // if both -f and -pclass is specified, we need to terminate it
        String fSwitchValue = getFSwitchValue(commandLineUtility);
        String pClassSwitchValue = getSwitchValue(commandLineUtility, SWITCH_PERSISTENCE_CLASS);
        if (fSwitchValue != null && pClassSwitchValue != null) {
            throw new ComponentDependencyConfigurationException("-f and -pclass should not be set in the same time.");
        }

        String persistenceClassName = fSwitchValue == null ? pClassSwitchValue : fSwitchValue;
        return persistenceClassName == null ? getOptionalStringValue(config, KEY_PERSISTENCE_CLASS,
            DEFAULT_PERSISTENCE_CLASS) : persistenceClassName;
    }

    /**
     * <p>
     * Gets and converts -f switch value.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @return persistence class name the switch value corresponds to if any. It could be null if the switch is
     *         specified
     *
     * @throws ComponentDependencyConfigurationException if value is empty or invalid value is provided other than xml
     *             or binary
     */
    private static String getFSwitchValue(CommandLineUtility commandLineUtility)
        throws ComponentDependencyConfigurationException {
        // check -f switch
        String fSwitchValue = getSwitchValue(commandLineUtility, SWITCH_FORMAT_NAME);
        if (fSwitchValue == null) {
            return null;
        }
        // if the value is provided, let's convert to real class name
        if ("xml".equals(fSwitchValue)) {
            return DefaultXmlDependenciesEntryPersistence.class.getName();
        } else if ("binary".equals(fSwitchValue)) {
            return BinaryFileDependenciesEntryPersistence.class.getName();
        } else {
            throw new ComponentDependencyConfigurationException("-f switch should only contains xml or binary.");
        }
    }

    /**
     * <p>
     * Gets persistence configuration object.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param config configuration object
     * @return persistence configuration object
     *
     * @throws ComponentDependencyConfigurationException if switch has empty value or error getting object from
     *             configuration
     * @throws InvalidConfigurationException if any error while writing to configuration object
     * @throws ConfigurationAccessException if any error while writing to configuration object
     */
    private static ConfigurationObject getPersistenceConfig(CommandLineUtility commandLineUtility,
        ConfigurationObject config) throws ComponentDependencyConfigurationException, InvalidConfigurationException,
        ConfigurationAccessException {
        ConfigurationObject persistenceConfig = Utils.getRequiredConfigurationObject(config, KEY_PERSISTENCE_CONFIG);

        // check -o switch
        String oSwitchValue = getSwitchValue(commandLineUtility, SWITCH_OUTPUT_FILE_NAME);
        if (oSwitchValue != null) {
            persistenceConfig.setPropertyValue("file_name", oSwitchValue);
        }
        return persistenceConfig;
    }

    /**
     * <p>
     * Gets extractor class name.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param config configuration object
     * @return extractor class name
     *
     * @throws ComponentDependencyConfigurationException if switch value is empty or any other error when getting value
     *             from configuration
     */
    private static String getExtractorClassName(CommandLineUtility commandLineUtility, ConfigurationObject config)
        throws ComponentDependencyConfigurationException {
        return getParameterStringValue(commandLineUtility, SWITCH_EXTRACTOR_CLASS, config, KEY_EXTRACTOR_CLASS,
            DEFAULT_EXTRACTOR_CLASS);
    }

    /**
     * <p>
     * Returns switch value.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param switchName switch name
     * @return switch value, it could be null if it is not specified
     *
     * @throws ComponentDependencyConfigurationException if the value is empty
     */
    private static String getSwitchValue(CommandLineUtility commandLineUtility, String switchName)
        throws ComponentDependencyConfigurationException {
        String switchValue = commandLineUtility.getSwitch(switchName).getValue();
        // see http://forums.topcoder.com/?module=Thread&threadID=615245&start=0&mc=8#983772
        // if it is an empty value, throws exception so we could terminate it
        if (switchValue != null && switchValue.trim().length() == 0) {
            throw new ComponentDependencyConfigurationException("value for switch<" + switchName
                + "> should not be empty value.");
        }
        return switchValue;
    }

    /**
     * <p>
     * Gets a set of dependency types.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param config configuration object
     * @return return a set of dependency types
     *
     * @throws ComponentDependencyConfigurationException if switch value is empty if provided or error when getting
     *             value from configuration or values contain value other than internal or external
     */
    private static Set<DependencyType> getDependencyTypes(CommandLineUtility commandLineUtility,
        ConfigurationObject config) throws ComponentDependencyConfigurationException {
        String dependencyTypesStr = getParameterStringValue(commandLineUtility, SWITCH_DEPENDENCY_TYPE, config,
            KEY_DEPENDENCY_TYPES, DEFAULT_DEPENDENCY_TYPES);

        Set<DependencyType> types = new HashSet<DependencyType>();
        for (String type : dependencyTypesStr.split(LIST_SEPARATEOR)) {
            if (TYPE_INTERNAL.equals(type)) {
                types.add(DependencyType.INTERNAL);
            } else if (TYPE_EXTERNAL.equals(type)) {
                types.add(DependencyType.EXTERNAL);
            } else {
                throw new ComponentDependencyConfigurationException(
                    "dependency type should be either internal or external.");
            }
        }

        return types;
    }

    /**
     * <p>
     * Gets a set of dependency categories.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param config configuration object
     * @return return a set of dependency categories
     *
     * @throws ComponentDependencyConfigurationException if switch value is empty if provided or error when getting
     *             value from configuration or values contain value other than compile or test
     */
    private static Set<DependencyCategory> getDependencyCategories(CommandLineUtility commandLineUtility,
        ConfigurationObject config) throws ComponentDependencyConfigurationException {
        String dependencyCategoriesStr = getParameterStringValue(commandLineUtility, SWITCH_DEPENDENCY_CATEGORY,
            config, KEY_DEPENDENCY_CATEGORIES, DEFAULT_DEPENDENCY_CATEGORIES);

        // parse
        Set<DependencyCategory> categories = new HashSet<DependencyCategory>();
        for (String category : dependencyCategoriesStr.split(LIST_SEPARATEOR)) {
            if (CATEGORY_COMPILE.equals(category)) {
                categories.add(DependencyCategory.COMPILE);
            } else if (CATEGORY_TEST.equals(category)) {
                categories.add(DependencyCategory.TEST);
            } else {
                throw new ComponentDependencyConfigurationException(
                    "dependency category should be either compile or test.");
            }
        }

        return categories;
    }

    /**
     * <p>
     * Get a set of file masks.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param config configuration object
     * @param logger logs error
     * @return return a set of file masks
     *
     * @throws ComponentDependencyConfigurationException if switch value is empty if provided or error when getting
     *             value from configuration or no fileMaks is defined or any fileMask is empty
     */
    private static Set<String> getFileMasks(CommandLineUtility commandLineUtility, ConfigurationObject config,
        Log logger) throws ComponentDependencyConfigurationException {
        // check -i switch first then configuration if it is null
        String fileMasksStr = getParameterStringValue(commandLineUtility, SWITCH_FILE_MASKS, config, KEY_FILE_MASKS,
            null);

        if (fileMasksStr == null) {
            throw new ComponentDependencyConfigurationException("no fileMasks value has been specified.");
        }

        // parse
        Set<String> fileMasks = new HashSet<String>();
        for (String fileMask : fileMasksStr.split(LIST_SEPARATEOR)) {
            // see http://forums.topcoder.com/?module=Thread&threadID=615328&start=0
            // just give warning
            if (Utils.isStringNullOrEmpty(fileMask)) {
                logWarn(logger, "empty file path is specified.", null);
            }
            fileMasks.add(fileMask);
        }

        return fileMasks;
    }

    /**
     * <p>
     * Creates the extractor instance.
     * </p>
     *
     * @param extractorClassName extractor class name
     * @param logger logger to be used in construction
     * @param types dependency types
     * @param categories dependency categories
     * @return created extractor instance
     *
     * @throws ComponentDependencyConfigurationException if extractor class name is not of type
     *             DependenciesEntryExtractor
     * @throws ClassNotFoundException the extractor class can not be found
     * @throws SecurityException any security exception
     * @throws NoSuchMethodException if ctor with <code>Log</code> as parameter doesn't exist
     * @throws InvocationTargetException if invocation target error while construction
     * @throws InstantiationException if any instantiation error while construction
     * @throws IllegalAccessException if any illegal access error while construction
     */
    private static DependenciesEntryExtractor createExtractor(String extractorClassName, Log logger,
        Set<DependencyType> types, Set<DependencyCategory> categories)
        throws ComponentDependencyConfigurationException, ClassNotFoundException, NoSuchMethodException,
        InvocationTargetException, InstantiationException, IllegalAccessException {
        Class extractorClass = Class.forName(extractorClassName);
        if (!DependenciesEntryExtractor.class.isAssignableFrom(extractorClass)) {
            throw new ComponentDependencyConfigurationException("extractorClassName<" + extractorClassName
                + "> is a invalid extractor class type.");
        }
        Constructor ctor = extractorClass.getConstructor(new Class[] {Log.class});
        DependenciesEntryExtractor extractor = (DependenciesEntryExtractor) ctor.newInstance(new Object[] {logger});
        if (extractor instanceof BaseDependenciesEntryExtractor) {
            ((BaseDependenciesEntryExtractor) extractor).setExtractedTypes(types);
            ((BaseDependenciesEntryExtractor) extractor).setExtractedCategories(categories);
        }
        return extractor;
    }

    /**
     * <p>
     * Creates the persistence instance.
     * </p>
     *
     * @param persistenceClassName persistence class name
     * @param persistenceConfig persistence configuration to be used in construction
     * @return created persistence instance
     *
     * @throws ComponentDependencyConfigurationException if persistence class name is not of type
     *             DependenciesEntryPersistence
     * @throws ClassNotFoundException the persistence class can not be found
     * @throws SecurityException any security exception
     * @throws NoSuchMethodException if ctor with <code>Log</code> as parameter doesn't exist
     * @throws InvocationTargetException if invocation target error while construction
     * @throws InstantiationException if any instantiation error while construction
     * @throws IllegalAccessException if any illegal access error while construction
     */
    private static DependenciesEntryPersistence createPersistence(String persistenceClassName,
        ConfigurationObject persistenceConfig) throws ComponentDependencyConfigurationException,
        ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
        InvocationTargetException {
        Class persistenceClass = Class.forName(persistenceClassName);
        if (!DependenciesEntryPersistence.class.isAssignableFrom(persistenceClass)) {
            throw new ComponentDependencyConfigurationException("persistenceClassName<" + persistenceClassName
                + "> is  a invalid persistence class type.");
        }
        Constructor ctor = persistenceClass.getConstructor(new Class[] {ConfigurationObject.class});
        DependenciesEntryPersistence persistence = (DependenciesEntryPersistence) ctor
            .newInstance(new Object[] {persistenceConfig});

        return persistence;
    }

    /**
     * <p>
     * Gets string value for given parameter such as extractor class name etc. The sequence should be:
     * <ol>
     * <li> checks switch value. it should not be empty value if it is not null. </li>
     * <li> if it is null, use value from configuration.</li>
     * <li> if it is still null, use default value.</li>
     * </ol>
     * </p>
     *
     * @param commandLineUtility command line utility
     * @param switchName switch name
     * @param config configuration object
     * @param propertyKey property key in configuration
     * @param defaultValue default value to be used
     * @return parameter string value
     *
     * @throws ComponentDependencyConfigurationException if switch value is empty or any other error when getting value
     *             from configuration
     */
    private static String getParameterStringValue(CommandLineUtility commandLineUtility, String switchName,
        ConfigurationObject config, String propertyKey, String defaultValue)
        throws ComponentDependencyConfigurationException {
        // see http://forums.topcoder.com/?module=Thread&threadID=615245&start=0&mc=8#983887
        String parameterValue = getSwitchValue(commandLineUtility, switchName);
        return parameterValue == null ? getOptionalStringValue(config, propertyKey, defaultValue) : parameterValue;
    }

    /**
     * <p>
     * Gets optional string value from configuration object. The default value will be returned if the optional value is
     * not provided i.e return null.
     * </p>
     *
     * @param config configuration object
     * @param key property key
     * @param defaultValue default value. It should not be null.
     * @return resolved value. It could be default value
     *
     * @throws ComponentDependencyConfigurationException if any error while retrieving value from configuration object
     */
    private static String getOptionalStringValue(ConfigurationObject config, String key, String defaultValue)
        throws ComponentDependencyConfigurationException {
        String value = Utils.getOptionalStringValue(config, key);
        return value == null ? defaultValue : value;
    }

    /**
     * <p>
     * Checks to see if help is requested.
     * </p>
     *
     * @param commandLineUtility command line utility
     * @return true if -help,-? or -h has been issued.
     */
    private static boolean isHelpRequested(CommandLineUtility commandLineUtility) {
        for (Object swObject : commandLineUtility.getValidSwitches()) {
            Switch sw = (Switch) swObject;
            if (SWITCH_HELP1.equals(sw.getName()) || SWITCH_HELP2.equals(sw.getName())
                || SWITCH_HELP3.equals(sw.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks to see if switch is specified. It is for checking switch as -nolog,-ignore_errors etc
     * </p>
     *
     * @param switchName switch name. Must be one of existing ones
     * @param commandLineUtility command line utility
     * @return true if this switch is specified, false if it is not
     */
    private static boolean isSwtichSpecified(CommandLineUtility commandLineUtility, String switchName) {
        for (Object swObject : commandLineUtility.getValidSwitches()) {
            Switch sw = (Switch) swObject;
            if (switchName.equals(sw.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Finds all files on the local disk that matches the given mask. This mask can contains wildcard characters ('*').
     * Each wildcard character can match any part of folder name or file name, i.e. cannot match path delimiters ('\'
     * and '/').
     * </p>
     *
     * @param filePathMask the file path mask (cannot be null or empty)
     * @return the list of found files (cannot be null, cannot contain null or empty)
     */
    private static List<String> findFiles(String filePathMask) {
        Utils.checkStringNullOrEmpty(filePathMask, "filePathMask");
        // some detail is in 1.3.6 of CS.
        // see http://forums.topcoder.com/?module=Thread&threadID=615211&start=0
        // normalize it. convert all "\" to "/" and replace multiple "/"s to just into one "/"
        filePathMask = filePathMask.replace("\\", "/").replaceAll("/{2,}", "/");

        List<String> result = new ArrayList<String>();
        if (!filePathMask.contains("*")) {
            result.add(filePathMask);
            return result;
        }

        // get all parts
        String parts[] = filePathMask.split("/");
        if (Utils.isStringNullOrEmpty(parts[parts.length - 1])) {
            // if last part is empty, just return
            return result;
        }

        // get files
        List<String> pathsToCheck = new ArrayList<String>();
        if (filePathMask.startsWith("/")) {
            pathsToCheck.add("/");
        } else {
            pathsToCheck.add(".");
        }

        for (int i = 0; i < parts.length; i++) {
            List<String> newPaths = new ArrayList<String>();

            String curPart = parts[i];
            if (!curPart.contains("*")) {
                // no "*" in the part string
                for (String pathToCheck : pathsToCheck) {
                    String newPath = pathToCheck + "/" + curPart;
                    if (new File(newPath).exists()) {
                        newPaths.add(newPath);
                    }
                }
            } else {
                // there are "*"(s)
                Pattern pattern = Pattern.compile(getPatternString(curPart));
                for (String pathToCheck : pathsToCheck) {
                    File file = new File(pathToCheck);
                    if (file.isDirectory()) {
                        for (String subfile : file.list()) {
                            Matcher matcher = pattern.matcher(subfile);
                            // matching "*" here :):)
                            if (matcher.matches()) {
                                newPaths.add(pathToCheck + "/" + subfile);
                            }
                        }
                    }
                }
            } // if/else

            // refresh the result
            pathsToCheck = newPaths;
        }

        // pick out all legitimate files
        for (String pathToCheck : pathsToCheck) {
            if (new File(pathToCheck).isFile()) {
                result.add(pathToCheck);
            }
        }

        return result;
    }

    /**
     * <p>
     * Gets pattern string for specific part with * in it. "*" will be converted into ".*" and any other character will
     * be quoted using '\Q' and '\E'.
     * </p>
     *
     * @param part string
     * @return pattern string from part string
     */
    private static String getPatternString(String part) {
        StringBuilder sb = new StringBuilder();
        // combine continuous * into just one
        part = part.replaceAll("\\*{2,}", "*");
        String[] groups = part.split("\\*", -1);
        for (int i = 0; i < groups.length; i++) {
            String group = groups[i];
            // not "" and not last group
            if (group.length() != 0) {
                sb.append("\\Q").append(group).append("\\E");
            }
            if (i != groups.length - 1) {
                sb.append(".*");
            }
        }
        return sb.toString();
    }

    /**
     * <p>
     * Logs information.
     * </p>
     *
     * @param logger logs info message
     * @param message info message
     */
    private static void logInfo(Log logger, String message) {
        if (logger != null) {
            Utils.logInfo(logger, message);
        } else {
            out.println("INFO: " + message);
        }
    }

    /**
     * <p>
     * Logs warning message.
     * </p>
     *
     * @param logger logs the warning message
     * @param message warning message
     * @param e exception. possibly null
     */
    private static void logWarn(Log logger, String message, Exception e) {
        if (logger != null) {
            Utils.logWarningException(logger, message, e);
        } else {
            out.println("WARN: " + message);
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Logs error message.
     * </p>
     *
     * @param logger logs the error message
     * @param errorMessage error message
     */
    private static void logError(Log logger, String errorMessage) {
        if (logger != null && logger.isEnabled(Level.ERROR)) {
            logger.log(Level.ERROR, errorMessage);
        } else {
            out.println("ERROR: " + errorMessage);
        }
    }

    /**
     * <p>
     * Logs exception/error.
     * </p>
     *
     * @param logger to log exception. It is possibly null
     * @param e exception to be logged
     */
    private static void logException(Log logger, Exception e) {
        // see http://forums.topcoder.com/?module=Thread&threadID=615233&start=0&mc=11#983734
        if (logger != null) {
            Utils.logException(logger, e);
        } else {
            e.printStackTrace();
        }
    }
}
