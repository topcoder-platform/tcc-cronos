/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.utility;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;

import com.topcoder.management.deliverable.latetracker.Helper;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTrackerConfigurationException;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTrackingJobRunner;

import com.topcoder.util.commandline.ArgumentValidationException;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.IntegerValidator;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.commandline.UsageException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.scheduler.processor.JobProcessor;
import com.topcoder.util.scheduler.scheduling.ConfigurationException;
import com.topcoder.util.scheduler.scheduling.Job;
import com.topcoder.util.scheduler.scheduling.Scheduler;
import com.topcoder.util.scheduler.scheduling.SchedulingException;
import com.topcoder.util.scheduler.scheduling.Second;
import com.topcoder.util.scheduler.scheduling.persistence.ConfigurationObjectScheduler;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * This is the main class of the standalone command line application that performs
 * periodical late deliverables tracking. It uses
 * <code>LateDeliverablesTrackingJobRunner</code> and schedules its repetitive execution
 * with use of Job Scheduling and Job Processor components. This utility reads a
 * configuration from a file using Configuration Persistence and Configuration API
 * components. <code>LateDeliverablesTrackingUtility</code> performs the logging of
 * errors and debug information using Logging Wrapper.
 * </p>
 * <p>
 * Sample command line utility:
 *
 * <pre>
 *  // This command line can be used to print out the usage string
 *  java com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility -help
 *  // If configuration for the utility is stored in the default namespace of the default configuration file,
 *  // then the application can be executed without additional arguments
 *  java com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility
 *  // To use the custom configuration file the user can provide &quot;-c&quot; switch
 *  java com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility
 *    -c custom_config.properties
 *  // The user can specify custom import files utility configuration file name and namespace
 *  java com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility
 *    -c cusom_config.properties -ns custom_namespace
 *  // The user can specify the interval between late deliverable checks in the command
 *  // line (in this example deliverables will be checked every 5 minutes)
 *  java com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility
 *    -inverval 300
 * </pre>
 *
 * Sample config: please refer CS 4.3.3.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe. But it's not safe to execute
 * multiple instances of LateDeliverablesTrackingUtility command line application
 * (configured to use the same persistence) at a time.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class LateDeliverablesTrackingUtility {
    /**
     * <p>
     * Represents the name of this class used for logging.
     * </p>
     */
    private static final String CLASS_NAME = LateDeliverablesTrackingUtility.class.getName();

    /**
     * <p>
     * Represents the default configuration file path.
     * </p>
     */
    private static final String DEFAULT_CONFIG_FILE = "com/topcoder/management/deliverable"
        + "/latetracker/utility/LateDeliverablesTrackingUtility.properties";

    /**
     * <p>
     * Represents the default configuration namespace.
     * </p>
     */
    private static final String DEFAULT_NAME_SPACE = "com.topcoder.management.deliverable.latetracker"
        + ".utility.LateDeliverablesTrackingUtility";

    /**
     * <p>
     * Represents switch name &quot;c&quot;.
     * </p>
     */
    private static final String CONFIG = "c";

    /**
     * <p>
     * Represents switch name &quot;ns&quot;.
     * </p>
     */
    private static final String NAMESPACE = "ns";

    /**
     * <p>
     * Represents switch name &quot;interval&quot;.
     * </p>
     */
    private static final String INTERVAL = "interval";

    /**
     * <p>
     * Represents the usage of help.
     * </p>
     */
    private static final String HELP_USAGE = "        -? -h -help      print this help message";

    /**
     * <p>
     * Represents &quot;-?&quot; argument name.
     * </p>
     */
    private static final String HELP_ONE = "-?";

    /**
     * <p>
     * Represents &quot;-h&quot; argument name.
     * </p>
     */
    private static final String HELP_TWO = "-h";

    /**
     * <p>
     * Represents &quot;-help&quot; argument name.
     * </p>
     */
    private static final String HELP_THREE = "-help";

    /**
     * <p>
     * Represents the usage of config switch.
     * </p>
     */
    private static final String CONFIG_SWITCH_USAGE = "<file_name> Optional. Provides the"
        + " name of the configuration file for this command line application. This file is read with"
        + " use of Configuration Persistence component. Default is \"com/topcoder/management/"
        + "deliverable/latetracker/utility/LateDeliverablesTrackingUtility.properties\".";

    /**
     * <p>
     * Represents the usage of namespace switch.
     * </p>
     */
    private static final String NAMESPACE_SWITCH_USAGE = "<namespace> Optional. The"
        + " namespace in the specified configuration file that contains configuration for this"
        + " command line application. Default is \"com.topcoder.management.deliverable.latetracker"
        + ".utility. LateDeliverablesTrackingUtility\".";

    /**
     * <p>
     * Represents the usage of interval switch.
     * </p>
     */
    private static final String INTERVAL_SWITCH_USAGE = "<interval_in_sec> Optional. The"
        + " interval in seconds between checks of projects for late deliverables. If not specified,"
        + " the value from the scheduler configuration is used.";

    /**
     * <p>
     * Represents the milliseconds of one day, 24x3600x1000.
     * </p>
     */
    private static final int ONE_DAY = 86400000;

    /**
     * <p>
     * This is a Command Line switch that is used to retrieve the configuration file.
     * </p>
     */
    private static Switch configSwitch;

    /**
     * <p>
     * This is a Command Line switch that is used to retrieve the namespace.
     * </p>
     */
    private static Switch namespaceSwitch;

    /**
     * <p>
     * This is a Command Line switch that is used to retrieve the interval time.
     * </p>
     */
    private static Switch intervalSwitch;

    /**
     * This is the command line utility which holds all switches.
     */
    private static CommandLineUtility commandLineUtility;

    /**
     * Static initialization.
     */
    static {
        try {
            // create switches
            configSwitch = new Switch(CONFIG, false, 1, 1, null, CONFIG_SWITCH_USAGE);
            namespaceSwitch = new Switch(NAMESPACE, false, 1, 1, null, NAMESPACE_SWITCH_USAGE);
            intervalSwitch = new Switch(INTERVAL, false, 1, 1, new IntegerValidator(1, null), INTERVAL_SWITCH_USAGE);
            // create command line utility
            commandLineUtility = new CommandLineUtility();
            commandLineUtility.addSwitch(configSwitch);
            commandLineUtility.addSwitch(intervalSwitch);
            commandLineUtility.addSwitch(namespaceSwitch);
        } catch (IllegalSwitchException e) {
            // never happens
        }
    }

    /**
     * <p>
     * Represents &quot;jobConfig&quot; child configuration key in configuration.
     * </p>
     */
    private static final String JOB_CONFIG = "jobConfig";

    /**
     * <p>
     * Represents &quot;schedulerConfig&quot; child configuration key in configuration.
     * </p>
     */
    private static final String SCHEDULER_CONFIG = "schedulerConfig";

    /**
     * <p>
     * Represents &quot;jobName&quot; property key in configuration.
     * </p>
     */
    private static final String JOB_NAME_KEY = "jobName";

    /**
     * Empty private constructor.
     */
    private LateDeliverablesTrackingUtility() {
    }

    /**
     * This is the main method of the command line application that periodically performs
     * a late deliverables tracking.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        final String signature = CLASS_NAME + "main(String[] args)";

        // user request help
        for (String arg : args) {
            if (arg.equals(HELP_ONE) || arg.equals(HELP_TWO) || arg.equals(HELP_THREE)) {
                System.out.print(commandLineUtility.getUsageString());
                System.out.println(HELP_USAGE);

                return;
            }
        }

        try {
            commandLineUtility.parse(args);
        } catch (ArgumentValidationException e) {
            System.out.println("Fails to validate the value of interval argument.");

            return;
        } catch (UsageException e) {
            System.out.println("Fails to parse the arguments.");

            return;
        }

        // get three values
        String configFileName = configSwitch.getValue();

        // configuration switch does not have value and default
        // configuration file does not exist
        if ((configFileName == null) && !doesDefaultConfigExist()) {
            System.out.println(commandLineUtility.getUsageString());
            System.out.println(HELP_USAGE);

            return;
        }

        if (configFileName == null) {
            configFileName = DEFAULT_CONFIG_FILE;
        }

        String namespace = namespaceSwitch.getValue();

        if (namespace == null) {
            namespace = DEFAULT_NAME_SPACE;
        }

        String intervalStr = intervalSwitch.getValue();
        Integer interval = (intervalStr == null) ? null : Integer.parseInt(intervalStr);

        Log log = null;
        doTrack(configFileName, namespace, interval, log, signature);
        Helper.logExit(log, signature, null, start);
    }

    /**
     * Creates job processor and start the tracking job.
     *
     * @param configFileName the configuration file name.
     * @param namespace the namespace.
     * @param interval the interval.
     * @param log the log to use.
     * @param signature the method name.
     */
    private static void doTrack(String configFileName, String namespace, Integer interval, Log log,
        String signature) {
        try {
            ConfigurationObject config = loadConfiguration(configFileName, namespace);

            // Get logger name from the configuration
            String loggerName = Helper.getPropertyValue(config, Helper.LOGGER_NAME_KEY, false, false);

            if (loggerName != null) {
                log = LogFactory.getLog(loggerName);
            } else {
                log = LogFactory.getLog();
            }

            ConfigurationObject schedulerConfig = Helper.getChildConfig(config, SCHEDULER_CONFIG);
            String jobName = Helper.getPropertyValue(config, JOB_NAME_KEY, true, false);
            ConfigurationObject jobConfig = Helper.getChildConfig(config, JOB_CONFIG);

            Scheduler scheduler = new ConfigurationObjectScheduler(schedulerConfig);
            Job job = scheduler.getJob(jobName);

            if (job == null) {
                printAndExit(log, null, "Job with name[" + jobName + "] is not under config.", signature);
            }

            LateDeliverablesTrackingJobRunner.setConfig(jobConfig);

            if (interval != null) {
                job.setIntervalUnit(new Second());
                job.setIntervalValue(interval);
            }

            JobProcessor jobProcessor = new JobProcessor(scheduler, ONE_DAY, log);
            jobProcessor.start();

            System.out.println("Press Enter to terminate the late deliverables tracker...");
            // Wait until the user presses Enter
            System.in.read();
            // Shutdown the job processor
            jobProcessor.shutdown();
        } catch (LateDeliverablesTrackerConfigurationException e) {
            printAndExit(log, e, "Configuration error occurred, error details : " + e.getMessage(),
                signature);
        } catch (IOException e) {
            printAndExit(log, e, "IO error occurred, error details : " + e.getMessage(), signature);
        } catch (ConfigurationException e) {
            printAndExit(log, e, "Configuration error occurred, error details : " + e.getMessage(),
                signature);
        } catch (SchedulingException e) {
            printAndExit(log, e, "Scheduling error occurred, error details : " + e.getMessage(),
                signature);
        } catch (NullPointerException e) {
            printAndExit(log, e, "Required configuration for job is missing.",
                signature);
        }
    }

    /**
     * Logs the exception and prints out the message exit JVM.
     *
     * @param log
     *            the log to use.
     * @param exception
     *            the exception to log.
     * @param message
     *            the message to print.
     * @param signature
     *            the method name.
     */
    private static void printAndExit(Log log, Exception exception, String message, String signature) {
        if (exception != null) {
            Helper.logException(log, signature, exception);
        }

        System.err.println(message);
        System.exit(1);
    }

    /**
     * Checks whether the default configuration file exists or not.
     *
     * @return true if default configuration file exists, false otherwise.
     */
    private static boolean doesDefaultConfigExist() {
        if (LateDeliverablesTrackingUtility.class.getClassLoader().getResource(DEFAULT_CONFIG_FILE) != null) {
            return true;
        }

        if (new File(DEFAULT_CONFIG_FILE).exists()) {
            return true;
        }

        return false;
    }

    /**
     * Loads configuration object from given file and namespace.
     *
     * @param configFileName
     *            the configuration file to load from.
     * @param namespace
     *            the namespace of the configuration object.
     * @return the configuration object load from file.
     * @throws LateDeliverablesTrackerConfigurationException
     *             if any error occurs.
     */
    private static ConfigurationObject loadConfiguration(String configFileName, String namespace) {
        try {
            ConfigurationFileManager manager = new ConfigurationFileManager(configFileName);
            ConfigurationObject config = manager.getConfiguration(namespace);

            return Helper.getChildConfig(config, namespace);
        } catch (ConfigurationParserException e) {
            throw new LateDeliverablesTrackerConfigurationException(
                "Fails to parse the configuration file.", e);
        } catch (NamespaceConflictException e) {
            throw new LateDeliverablesTrackerConfigurationException("Namespace conflict.", e);
        } catch (UnrecognizedFileTypeException e) {
            throw new LateDeliverablesTrackerConfigurationException(
                "Unrecognized file type of the config file.", e);
        } catch (IOException e) {
            throw new LateDeliverablesTrackerConfigurationException(
                "IO error when reading config file.", e);
        } catch (UnrecognizedNamespaceException e) {
            throw new LateDeliverablesTrackerConfigurationException("Unrecognized namespace : "
                + namespace, e);
        }
    }
}