/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.util.commandline.ArgumentValidationException;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.commandline.UsageException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This is the main class of the standalone command line application that calculates and updates users' reliability
 * ratings for specific project categories. Actually it just uses multiple pluggable ReliabilityCalculator instances
 * to perform this task. This utility reads a configuration from a file using Configuration Persistence and
 * Configuration API components. ReliabilityCalculationUtility performs the logging of errors and debug information
 * using Logging Wrapper.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 *
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.reliability.ReliabilityCalculationUtility"&gt;
 *     &lt;Property name="loggerName"&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="objectFactoryConfig"&gt;
 *       &lt;!-- Put Object Factory configuration here --&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="reliabilityCalculator1"&gt;
 *       &lt;Property name="projectCategoryIds"&gt;
 *         &lt;Value&gt;1&lt;/Value&gt;
 *         &lt;Value&gt;2&lt;/Value&gt;
 *         &lt;Value&gt;6&lt;/Value&gt;
 *         &lt;Value&gt;7&lt;/Value&gt;
 *         &lt;Value&gt;13&lt;/Value&gt;
 *         &lt;Value&gt;14&lt;/Value&gt;
 *         &lt;Value&gt;19&lt;/Value&gt;
 *         &lt;Value&gt;23&lt;/Value&gt;
 *         &lt;Value&gt;24&lt;/Value&gt;
 *         &lt;Value&gt;26&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="key"&gt;
 *         &lt;Value&gt;ReliabilityCalculatorImpl&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name="config"&gt;
 *         &lt;Property name="loggerName"&gt;
 *           &lt;Value&gt;myLogger&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="objectFactoryConfig"&gt;
 *           &lt;Property name="DatabaseReliabilityDataPersistence"&gt;
 *             &lt;Property name="type"&gt;
 *               &lt;Value&gt;com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistence&lt;/Value&gt;
 *               &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="UserProjectParticipationDataResolutionDateBasedComparator"&gt;
 *             &lt;Property name="type"&gt;
 *               &lt;Value&gt;
 *                 com.topcoder.reliability.impl.comparators.UserProjectParticipationDataResolutionDateBasedComparator
 *               &lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="UniformUserReliabilityCalculator"&gt;
 *             &lt;Property name="type"&gt;
 *               &lt;Value&gt;com.topcoder.reliability.impl.calculators.UniformUserReliabilityCalculator&lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="WeightedUserReliabilityCalculator"&gt;
 *             &lt;Property name="type"&gt;
 *               &lt;Value&gt;com.topcoder.reliability.impl.calculators.WeightedUserReliabilityCalculator&lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="PhaseEndTimeBasedResolutionDateDetector"&gt;
 *             &lt;Property name="type"&gt;
 *               &lt;Value&gt;
 *                 com.topcoder.reliability.impl.detectors.PhaseEndTimeBasedResolutionDateDetector&lt
 *               ;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="reliabilityDataPersistenceKey"&gt;
 *           &lt;Value&gt;DatabaseReliabilityDataPersistence&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="reliabilityDataPersistenceConfig"&gt;
 *           &lt;Property name="loggerName"&gt;
 *             &lt;Value&gt;myLogger&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="dbConnectionFactoryConfig"&gt;
 *             &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *               &lt;Property name="connections"&gt;
 *                 &lt;Property name="default"&gt;
 *                   &lt;Value&gt;myConnection&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="myConnection"&gt;
 *                   &lt;Property name="producer"&gt;
 *                       &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="parameters"&gt;
 *                       &lt;Property name="jdbc_driver"&gt;
 *                           &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                       &lt;/Property&gt;
 *                       &lt;Property name="jdbc_url"&gt;
 *                           &lt;Value&gt;
 *                             jdbc:informix-sqli://localhost:1526/demo:informixserver=ol_topcoder
 *                           &lt;/Value&gt;
 *                       &lt;/Property&gt;
 *                       &lt;Property name="SelectMethod"&gt;
 *                           &lt;Value&gt;cursor&lt;/Value&gt;
 *                       &lt;/Property&gt;
 *                       &lt;Property name="user"&gt;
 *                           &lt;Value&gt;informix&lt;/Value&gt;
 *                       &lt;/Property&gt;
 *                       &lt;Property name="password"&gt;
 *                           &lt;Value&gt;123456&lt;/Value&gt;
 *                       &lt;/Property&gt;
 *                     &lt;/Property&gt;
 *                 &lt;/Property&gt;
 *               &lt;/Property&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="connectionName"&gt;
 *             &lt;Value&gt;myConnection&lt;/Value&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="participationDataComparatorKey"&gt;
 *           &lt;Value&gt;UserProjectParticipationDataResolutionDateBasedComparator&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="projectCategoryConfig1"&gt;
 *           &lt;Property name="projectCategoryIds"&gt;
 *             &lt;Value&gt;1&lt;/Value&gt;
 *             &lt;Value&gt;2&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="reliabilityStartDate"&gt;
 *             &lt;Value&gt;2005-10-05 09:00&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="userReliabilityCalculatorKey"&gt;
 *             &lt;Value&gt;UniformUserReliabilityCalculator&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="userReliabilityCalculatorConfig"&gt;
 *             &lt;Property name="loggerName"&gt;
 *               &lt;Value&gt;myLogger&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name="historyLength"&gt;
 *               &lt;Value&gt;15&lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="resolutionDateDetectorKey"&gt;
 *             &lt;Value&gt;PhaseEndTimeBasedResolutionDateDetector&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="resolutionDateDetectorConfig"&gt;
 *             &lt;Property name="loggerName"&gt;
 *               &lt;Value&gt;myLogger&lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="projectCategoryConfig2"&gt;
 *           &lt;Property name="projectCategoryIds"&gt;
 *             &lt;Value&gt;6&lt;/Value&gt;
 *             &lt;Value&gt;7&lt;/Value&gt;
 *             &lt;Value&gt;13&lt;/Value&gt;
 *             &lt;Value&gt;14&lt;/Value&gt;
 *             &lt;Value&gt;19&lt;/Value&gt;
 *             &lt;Value&gt;23&lt;/Value&gt;
 *             &lt;Value&gt;24&lt;/Value&gt;
 *             &lt;Value&gt;26&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="reliabilityStartDate"&gt;
 *             &lt;Value&gt;2009-03-23 00:00&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="userReliabilityCalculatorKey"&gt;
 *             &lt;Value&gt;WeightedUserReliabilityCalculator&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="userReliabilityCalculatorConfig"&gt;
 *             &lt;Property name="loggerName"&gt;
 *               &lt;Value&gt;myLogger&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name="weights"&gt;
 *               &lt;Value&gt;0.82&lt;/Value&gt;
 *               &lt;Value&gt;0.84&lt;/Value&gt;
 *               &lt;Value&gt;0.86&lt;/Value&gt;
 *               &lt;Value&gt;0.88&lt;/Value&gt;
 *               &lt;Value&gt;0.9&lt;/Value&gt;
 *               &lt;Value&gt;0.92&lt;/Value&gt;
 *               &lt;Value&gt;0.94&lt;/Value&gt;
 *               &lt;Value&gt;0.96&lt;/Value&gt;
 *               &lt;Value&gt;0.98&lt;/Value&gt;
 *               &lt;Value&gt;1.00&lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="resolutionDateDetectorKey"&gt;
 *             &lt;Value&gt;PhaseEndTimeBasedResolutionDateDetector&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="resolutionDateDetectorConfig"&gt;
 *             &lt;Property name="loggerName"&gt;
 *               &lt;Value&gt;myLogger&lt;/Value&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Usage:</em><br>
 *
 * This command line can be used to print out the usage string:
 *
 * <pre>
 * java com.topcoder.reliability.ReliabilityCalculationUtility -help
 * </pre>
 *
 * If configuration for the utility is stored in the default namespace of the default configuration file, then the
 * application can be executed without additional arguments:
 *
 * <pre>
 * java com.topcoder.reliability.ReliabilityCalculationUtility
 * </pre>
 *
 * To use the custom configuration file the user can provide "-c" switch:
 *
 * <pre>
 * java com.topcoder.reliability.ReliabilityCalculationUtility -c custom_config.properties
 * </pre>
 *
 * The user can specify custom import files utility configuration file name and namespace:
 *
 * <pre>
 * java com.topcoder.reliability.ReliabilityCalculationUtility -c cusom_config.properties -ns custom_namespace
 * </pre>
 *
 * The following command line allows to calculate reliability for project categories with ID=1,2 and to update the
 * current reliability ratings of users for these project categories:
 *
 * <pre>
 * java com.topcoder.reliability.ReliabilityCalculationUtility -pc 1,2 -u yes
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe. But it's not safe to execute multiple
 * instances of ReliabilityCalculationUtility command line application (configured to use the same persistence) at a
 * time.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class ReliabilityCalculationUtility {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ReliabilityCalculationUtility.class.getName();

    /**
     * <p>
     * Represents the default configuration file.
     * </p>
     */
    private static final String DEFAULT_CONFIG_FILE =
        "com/topcoder/reliability/ReliabilityCalculationUtility.properties";

    /**
     * <p>
     * Represents the default namespace.
     * </p>
     */
    private static final String DEFAULT_NS = "com.topcoder.reliability.ReliabilityCalculationUtility";

    /**
     * <p>
     * Represents the default update flag.
     * </p>
     */
    private static final String DEFAULT_U = "no";

    /**
     * <p>
     * Represents the switch '-c'.
     * </p>
     */
    private static final String SWITCH_C = "c";

    /**
     * <p>
     * Represents the switch '-ns'.
     * </p>
     */
    private static final String SWITCH_NS = "ns";

    /**
     * <p>
     * Represents the switch '-pc'.
     * </p>
     */
    private static final String SWITCH_PC = "pc";

    /**
     * <p>
     * Represents the switch '-u'.
     * </p>
     */
    private static final String SWITCH_U = "u";

    /**
     * <p>
     * Represents the switch to print usage message.
     * </p>
     */
    private static final List<String> SWITCH_HELP = Arrays.asList("help", "h", "?");

    /**
     * <p>
     * Represents the boolean values.
     * </p>
     */
    private static final List<String> BOOLEAN_VALUES = Arrays.asList("yes", "no");

    /**
     * <p>
     * Represents the prefix of child key 'reliabilityCalculatorXXX'.
     * </p>
     */
    private static final String KEY_CALCULATOR_CONFIG_PREFIX = "reliabilityCalculator";

    /**
     * <p>
     * Represents the property key 'key'.
     * </p>
     */
    private static final String KEY_CALCULATOR_KEY = "key";

    /**
     * <p>
     * Represents the child key 'config'.
     * </p>
     */
    private static final String KEY_CALCULATOR_CONFIG = "config";

    /**
     * <p>
     * Represents the property key 'projectCategoryIds'.
     * </p>
     */
    private static final String KEY_PC_IDS = "projectCategoryIds";

    /**
     * <p>
     * Represents the line separator.
     * </p>
     */
    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * <p>
     * Represents the usage text.
     * </p>
     */
    private static final String USAGE_TEXT =
        "Usage: com.topcoder.reliability.ReliabilityCalculationUtility"
        + " [-c <file_name>] [-ns <namespace>] [-pc <project_category_ids>] [-u [yes|no]] [-?|-h|-help]" + LINE_SEP
        + "           <file_name> Optional. Provides the name of the configuration file for this command line"
        + " application. Default is 'com/topcoder/reliability/ReliabilityCalculationUtility.properties'." + LINE_SEP
        + "           <namespace> Optional. The namespace in the specified configuration file that contains"
        + " configuration for this command line application. Default is"
        + " 'com.topcoder.reliability.ReliabilityCalculationUtility'." + LINE_SEP
        + "<project_category_ids> Optional. The comma separated IDs of project categories for which reliability"
        + " calculation must be performed.  If not specified, reliability is calculated for all project categories"
        + " mentioned in the configuration" + LINE_SEP
        + "              [yes|no] Optional. The value indicating whether current reliability ratings must be updated"
        + " for all users. The only correct arguments are 'yes' and 'no' (case insensitive)."
        + " Default is 'no'." + LINE_SEP
        + "           -?|-h|-help Optional. Prints out this usage text.";

    /**
     * <p>
     * Empty private constructor.
     * </p>
     */
    private ReliabilityCalculationUtility() {
        // Empty
    }

    /**
     * <p>
     * This is the main method of the command line application that recalculates reliability ratings for all
     * configured project categories (or optionally project categories specified in the command line). It uses
     * CommandLineUtility class to parse command line arguments.
     * </p>
     *
     * <p>
     * <em>NOTE: </em>
     * <ul>
     * <li>It's assumed that this method will never be used programmatically, thus argument checking is not required.
     *     </li>
     * <li>This method will NOT throw any exception. Instead it will print out the detailed error explanation to the
     * standard error output and terminate.</li>
     * </ul>
     * </p>
     *
     * @param args
     *            the command line arguments.
     */
    public static void main(String[] args) {
        Date enterTimestamp = new Date();
        String signature = getSignature("main(String[] args)");

        LogManager.setLogFactory(new Log4jLogFactory());

        // Create a command line utility:
        CommandLineUtility commandLineUtility = new CommandLineUtility();

        // Parse arguments
        parseArgs(commandLineUtility, args);

        for (String s : SWITCH_HELP) {
            if (commandLineUtility.getValidSwitches().contains(commandLineUtility.getSwitch(s))) {
                System.out.println(USAGE_TEXT);

                return;
            }
        }

        try {
            String configFileName = getSwitchValue(commandLineUtility, SWITCH_C, DEFAULT_CONFIG_FILE);
            Switch switchC = commandLineUtility.getSwitch(SWITCH_C);

            ConfigurationFileManager cfm = null;

            try {
                cfm = new ConfigurationFileManager(configFileName);
            } catch (IOException e) {
                if (!commandLineUtility.getValidSwitches().contains(switchC)) {
                    // The default configuration file does not exist
                    System.out.println(USAGE_TEXT);

                    return;
                }
                System.err.println("The configuration file '" + configFileName + "' does not exist.");
                e.printStackTrace();

                System.exit(1);
            }

            // Get namespace from command line (use default if not specified)
            String namespace = getSwitchValue(commandLineUtility, SWITCH_NS, DEFAULT_NS);
            // Get flag indicating whether current reliability ratings must be updated in persistence,
            // convert it to boolean (use default if not specified)
            String updateCurrentReliabilityStr =
                getSwitchValue(commandLineUtility, SWITCH_U, DEFAULT_U).toLowerCase();
            int bIndex = BOOLEAN_VALUES.indexOf(updateCurrentReliabilityStr);
            if (bIndex < 0) {
                System.err.println("The value for '-u' is incorrect.");

                System.exit(1);
            }
            boolean updateCurrentReliability = (bIndex == 0);

            // Get projectCategoryIds
            List<Long> projectCategoryIds = parseProjectCategoryIds(commandLineUtility);

            // Get configuration object from the manager
            ConfigurationObject config = cfm.getConfiguration(namespace);

            process(namespace, projectCategoryIds, updateCurrentReliability, config, signature,
                enterTimestamp);
        } catch (ConfigurationPersistenceException e) {
            e.printStackTrace();

            System.exit(1);
        }
    }

    /**
     * <p>
     * Parses the project category ids.
     * </p>
     *
     * @param commandLineUtility
     *            the command line utility.
     *
     * @return the parsed project category ids
     */
    @SuppressWarnings("unchecked")
    private static List<Long> parseProjectCategoryIds(CommandLineUtility commandLineUtility) {
        Switch s = commandLineUtility.getSwitch(SWITCH_PC);

        // Convert projectCategoryIdsStr to projectCategoryIds:long[] (use null if not provided).
        List<Long> projectCategoryIds = null;
        if (commandLineUtility.getValidSwitches().contains(s)) {
            projectCategoryIds = new ArrayList<Long>();

            for (String value : (List<String>) s.getValues()) {
                try {
                    projectCategoryIds.add(Long.parseLong(value));
                } catch (NumberFormatException e) {
                    e.printStackTrace();

                    System.exit(1);
                }
            }
        }

        return projectCategoryIds;
    }

    /**
     * <p>
     * The main process.
     * </p>
     *
     * @param namespace
     *            the namespace.
     * @param projectCategoryIds
     *            the project category ids.
     * @param updateCurrentReliability
     *            the update flag.
     * @param config
     *            the configuration object.
     * @param signature
     *            the method signature.
     * @param enterTimestamp
     *            the timestamp while entering the method.
     */
    private static void process(String namespace, List<Long> projectCategoryIds, boolean updateCurrentReliability,
        ConfigurationObject config, String signature, Date enterTimestamp) {
        // Get Log instance
        Log log = null;

        try {
            config = config.getChild(namespace);

            log = Helper.getLog(config);

            // Create object factory
            ObjectFactory objectFactory = Helper.getObjectFactory(config);

            // Create mapping from project category ID to reliability calculator
            Map<Long, ReliabilityCalculator> calculatorByProjectCategoryId =
                new LinkedHashMap<Long, ReliabilityCalculator>();

            for (String childName : config.getAllChildrenNames()) {
                if (childName.startsWith(KEY_CALCULATOR_CONFIG_PREFIX)) {
                    ConfigurationObject childConfig = Helper.getChildConfig(config, childName);
                    // Create reliability calculator
                    ReliabilityCalculator reliabilityCalculator = Helper.createObject(ReliabilityCalculator.class,
                        objectFactory, childConfig, KEY_CALCULATOR_KEY, KEY_CALCULATOR_CONFIG);

                    // Get project category IDs for which this reliability calculator must be used
                    long[] curProjectCategoryIds = Helper.getPropertyPositiveValues(childConfig, KEY_PC_IDS);
                    for (long curProjectCategoryId : curProjectCategoryIds) {
                        if ((projectCategoryIds == null) || projectCategoryIds.contains(curProjectCategoryId)) {
                            // Put category ID and reliability calculator to the map
                            calculatorByProjectCategoryId.put(curProjectCategoryId, reliabilityCalculator);
                        }
                    }
                }
            }
            // Log start processing
            Date start = new Date();
            log.log(Level.INFO, "Reliability calculation started");
            log.log(Level.INFO, "Project catagories to process : " + calculatorByProjectCategoryId.keySet());
            log.log(Level.INFO, "Update current reliability flag : " + updateCurrentReliability);


            for (Entry<Long, ReliabilityCalculator> entry : calculatorByProjectCategoryId.entrySet()) {
                long projectCategoryId = entry.getKey();
                ReliabilityCalculator reliabilityCalculator = entry.getValue();
                // Calculate the reliability for the next project category
                try {
                    reliabilityCalculator.calculate(projectCategoryId, updateCurrentReliability);
                } catch (ReliabilityCalculationException e) {
                    // Print out the exception to System.err and ignore
                    e.printStackTrace();
                }
            }

            Date end = new Date();
            log.log(Level.INFO, "Reliability calculation ended");
            log.log(Level.INFO, "Reliability calculation took " +  (end.getTime() - start.getTime()) + " ms");

            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (ConfigurationAccessException e) {
            e.printStackTrace();
            Helper.logException(log, signature, e, e.getMessage());

            System.exit(1);
        } catch (ReliabilityCalculatorConfigurationException e) {
            e.printStackTrace();
            Helper.logException(log, signature, e, e.getMessage());

            System.exit(1);
        }
    }

    /**
     * <p>
     * Gets the switch value.
     * </p>
     *
     * @param commandLineUtility
     *            the command line utility.
     * @param name
     *            the switch name.
     * @param defaultValue
     *            the default switch value.
     *
     * @return the switch value.
     */
    private static String getSwitchValue(CommandLineUtility commandLineUtility, String name, String defaultValue) {
        Switch s = commandLineUtility.getSwitch(name);

        if (!commandLineUtility.getValidSwitches().contains(s)) {
            return defaultValue;
        }

        return s.getValue();
    }

    /**
     * <p>
     * Parse the arguments.
     * </p>
     *
     * @param commandLineUtility
     *            the command line utility.
     * @param args
     *            the arguments.
     */
    private static void parseArgs(CommandLineUtility commandLineUtility, String[] args) {
        try {
            // Add switch to the command line utility
            for (String s : new String[] {SWITCH_C, SWITCH_NS, SWITCH_U}) {
                commandLineUtility.addSwitch(new Switch(s, false, 0, 1, null));
            }
            commandLineUtility.addSwitch(new Switch(SWITCH_PC, false, 0, -1, null));
            for (String s : SWITCH_HELP) {
                commandLineUtility.addSwitch(new Switch(s, false, 0, -1, null));
            }

            // Parse command line arguments
            commandLineUtility.parse(args);
        } catch (IllegalSwitchException e) {
            // Will not happen
            e.printStackTrace();

            System.exit(1);
        } catch (ArgumentValidationException e) {
            e.printStackTrace();

            System.exit(1);
        } catch (UsageException e) {
            e.printStackTrace();

            System.exit(1);
        }
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     *
     * @param method
     *            the method name.
     *
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return CLASS_NAME + Helper.DOT + method;
    }
}

