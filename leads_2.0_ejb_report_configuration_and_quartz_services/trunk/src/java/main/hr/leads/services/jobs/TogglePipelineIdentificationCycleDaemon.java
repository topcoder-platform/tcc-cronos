/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import hr.leads.services.SystemConfigurationPropertyService;
import hr.leads.services.ejb.ReportHelper;
import hr.leads.services.model.PipelineCycleStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <p>
 * This is the class that bootstraps the quartz scheduler from the command line.
 * </p>
 * <p>
 * It reads properties file specified by user in command line, and use it to
 * start scheduler jobs.
 * </p>
 * <p>
 * <b>Usage:</b>
 *
 * <pre>
 * example for using the specific property file:
 * java [-cp libs...]  hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemon c:\scheduler.properties
 * example for using the default property file:
 * java [-cp libs...]  hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemon
 * </pre>
 *
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is immutable and thread safe.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleDaemon {

    /**
     * <p>
     * Represents default config file in classpath to be used if the config file
     * path is not provided via command line.
     * </p>
     * <p>
     * It cannot  be null.
     * </p>
     * <p>
     * Default to "TogglePipelineIdentificationCycleDaemon.properties", cannot
     * be changed.
     * </p>
     * <p>
     * It's used by the loadProperties() method.
     * </p>
     */
    public static final String DEFAULT_CONFIG_FILE =
        "TogglePipelineIdentificationCycleDaemon.properties";

    /**
     * <p>
     * Represents the key used to retrieve provider url from properties file.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Default to "provider_url", cannot be changed.
     * </p>
     * <p>
     * It's used by the main() method.
     * </p>
    */
    public static final String PROVIDER_URL = "provider_url";

    /**
     * <p>
     * Represents the key used to retrieve initial context factory from properties file.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Default to "initial_context_factory", cannot be changed.
     * </p>
     * <p>
     * It's used by the main() method.
     * </p>
    */
    public static final String INITIAL_CONTEXT_FACTORY = "initial_context_factory";

    /**
     * <p>
     * Represents the key used to retrieve jndi name for system configuration
     * property service from properties file.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Default to "system_configuration_property_service_jndi_name", cannot be
     * changed.
     * </p>
     * <p>
     * It's used by the main() method.
     * </p>
     */
    public static final String SYSTEM_CONFIGURATION_PROPERTY_SERVICE_JNDI_NAME =
        "system_configuration_property_service_jndi_name";

    /**
     * <p>
     * Represents the key prefix used to retrieve execution time and status from properties file.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Default to "execution_time_and_status_", cannot be changed.
     * </p>
     * <p>
     * It's used by the main() method.
     * </p>
    */
    public static final String EXECUTION_TIME_AND_STATUS_PREFIX =
        "execution_time_and_status_";

    /**
     * <p>
     * Represents the separator used to separate execution time and status.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Default to "@@", cannot be changed.
     * </p>
     * <p>
     * It's used by the main() method.
     * </p>
    */
    public static final String EXECUTION_TIME_AND_STATUS_SEPARATOR = "@@";

    /**
     * <p>
     * Represents the prefix of each job name.
     * </p>
     */
    private static final String QUARTZ_JOBNAME_PREFIX =
        "TogglePipelineIdentificationCycleJob_";

    /**
     * <p>
     * Represents the prefix of each trigger name.
     * </p>
     */
    private static final String QUARTZ_TRIGGERNAME_PREFIX =
        "TogglePipelineIdentificationCycleTrigger_";

    /**
     * <p>
     * Represents the logger used by this class to write log.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Initialized when TogglePipelineIdentificationCycleDaemon class is loaded
     * by class loader, cannot be changed after that.
     * </p>
     * <p>
     * It's used by the main() method to write log.
     * </p>
     */
    private static final Logger LOGGER =
        Logger.getLogger(TogglePipelineIdentificationCycleDaemon.class.getName());

    /**
     * <p>
     * Creates an instance of TogglePipelineIdentificationCycleDaemon.
     * </p>
    */
    private TogglePipelineIdentificationCycleDaemon() {
        // do nothing
    }

    /**
     * <p>
     * Represents the main method which will be called by user via command line.
     * </p>
     * <p>
     * It will read the properties file specified by user, and then create
     * scheduler jobs to toggle pipeline identification cycle.
     * </p>
     *
     * @param args
     *            the parameters specified by user in command line.
     *
     * @throws IllegalArgumentException
     *             if args != null and args.length > 1, or if args != null and
     *             args.length == 1 and args[0] is null / empty.
     *
     * @throws TogglePipelineIdentificationCycleDaemonException
     *             If any error occurs.
     */
    public static void main(String[] args)
        throws TogglePipelineIdentificationCycleDaemonException {
        // prepare for logging
        String methodName = TogglePipelineIdentificationCycleDaemon.class.getName() + ".main";

        // log the entrance and request parameters
        ReportHelper.logEntrance(LOGGER, methodName);
        ReportHelper.logParameters(LOGGER, new Object[] {args}, new String[] {"args"});

        // validates the argument and gets the property file
        String propertyFile = null;
        if (args != null) {
            if (args.length > 1) {
                ReportHelper.logError(LOGGER, methodName,
                        "The length of the args is invalid.");
                throw new IllegalArgumentException(
                        "The length of the args is invalid.");
            } else if (args.length == 1) {
                ReportHelper.checkNullOrEmpty(LOGGER, methodName, args[0], "args");
                propertyFile = args[0];
            }
        }

        // load the properties
        Properties properties;
        try {
            properties = loadProperties(propertyFile);
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            ReportHelper.logError(LOGGER, methodName, "Failed to load the properties.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to load the properties.", e);
        }

        // lookup the service via jndi
        SystemConfigurationPropertyService service;
        try {
            // retrieves the provider url, initial context factory and the name of the jndi.
            String providerUrl = properties.getProperty(PROVIDER_URL);
            String initialContextFactory = properties.getProperty(INITIAL_CONTEXT_FACTORY);
            String systemConfigurationPropertyServiceJndiName =
                properties.getProperty(SYSTEM_CONFIGURATION_PROPERTY_SERVICE_JNDI_NAME);

            // begin lookup
            service = lookupService(providerUrl, initialContextFactory, systemConfigurationPropertyServiceJndiName);
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            ReportHelper.logError(
                    LOGGER, methodName, "Failed to lookup the jndi.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to lookup the jndi", e);
        }

        // schedule all the jobs from configuration
        try {
            scheduleJobs(properties, service);
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            ReportHelper.logError(
                    LOGGER, methodName, "Failed to schedule the jobs from configuration.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to schedule the jobs from configuration.", e);
        }

        // log exit
        ReportHelper.logExit(LOGGER, methodName);
    }

    /**
     * <p>
     * Starts the scheduler and schedules all the jobs from properties.
     * </p>
     *
     * @param properties
     *            the properties contains info or all the jobs.
     * @param service
     *            the service to bind.
     * @throws TogglePipelineIdentificationCycleDaemonException
     *             if any error occurs.
     */
    private static void scheduleJobs(Properties properties,
            SystemConfigurationPropertyService service)
        throws TogglePipelineIdentificationCycleDaemonException {

        Scheduler scheduler;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            // log the error
            ReportHelper.logError(
                    LOGGER, TogglePipelineIdentificationCycleDaemon.class .getName()
                        + ".scheduleJobs", "Failed to start the scheduler.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to start the scheduler.");
        }

        for (Object key : properties.keySet()) {

            if (key == null || !String.class.isInstance(key)) {
                continue;
            }

            // only the configurations starts with: "execution_time_and_status_"
            // are wanted
            if (!((String) key).startsWith(EXECUTION_TIME_AND_STATUS_PREFIX)) {
                continue;
            }

            // get the name(suffix) of the job
            String name = ((String) key).substring(
                    EXECUTION_TIME_AND_STATUS_PREFIX.length());

            // get the job configuration
            String config = properties.getProperty((String) key);

            scheduleJob(scheduler, name, config, service);
        }
    }

    /**
     * <p>
     * Looks up the SystemConfigurationPropertyService with JNDI.
     * </p>
     *
     * @param providerUrl
     *            the provider url of the JNDI.
     * @param initialContextFactory
     *            the initial context factory class name.
     * @param systemConfigurationPropertyServiceJndiName
     *            the name of the JNDI of the service.
     *
     * @return the SystemConfigurationPropertyService.
     *
     * @throws TogglePipelineIdentificationCycleDaemonException
     *             if there are any errors.
     */
    private static SystemConfigurationPropertyService lookupService(
            String providerUrl, String initialContextFactory,
            String systemConfigurationPropertyServiceJndiName)
        throws TogglePipelineIdentificationCycleDaemonException {

        // validates the parameters.
        checkNullOrEmpty(providerUrl, "providerUrl");
        checkNullOrEmpty(initialContextFactory, "initialContextFactory");
        checkNullOrEmpty(systemConfigurationPropertyServiceJndiName,
                "systemConfigurationPropertyServiceJndiName");

        Properties prop = new Properties();

        prop.setProperty(Context.PROVIDER_URL, providerUrl);
        prop.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                        initialContextFactory);

        InitialContext ctx;
        SystemConfigurationPropertyService service;
        try {
            // look up in jndi
            ctx = new InitialContext(prop);
            service = (SystemConfigurationPropertyService) ctx.lookup(
                    systemConfigurationPropertyServiceJndiName);

        } catch (NamingException e) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName()
                        + ".lookupService", "failed to lookup jndi. "
                            + systemConfigurationPropertyServiceJndiName + e.getMessage());

            // failed to find the jndi source
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "failed to lookup jndi. " + systemConfigurationPropertyServiceJndiName, e);
        } catch (ClassCastException e) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName()
                        + ".lookupService", "invalid class type. "
                            + systemConfigurationPropertyServiceJndiName + " " + e.getMessage());

            // failed to find the jndi source
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "invalid class type.. " + systemConfigurationPropertyServiceJndiName, e);
        }

        return service;
    }

    /**
     * <p>
     * Checks if a String is null or empty.
     * </p>
     *
     * @param param
     *            the parameter to check.
     * @param name
     *            the name of parameter.
     * @throws TogglePipelineIdentificationCycleDaemonException
     *             if there are any errors.
     */
    private static void checkNullOrEmpty(String param, String name)
        throws TogglePipelineIdentificationCycleDaemonException {
        if (param == null) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName()
                        + ".checkNullOrEmpty", "The param " + name + " cannot be null.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "The param " + name + " cannot be null.");
        }

        if (param.trim().length() == 0) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName()
                        + ".checkNullOrEmpty", "The param " + name + " cannot be empty.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "The param " + name + " cannot be empty.");
        }
    }

    /**
     * <p>
     * Loads the configuration file into a Properties object.
     * </p>
     *
     * @param configFilePath
     *            the file path of the configuration file.
     *
     * @return the loaded Properties object.
     *
     * @throws IllegalArgumentException
     *             if configFilePath is empty.
     * @throws TogglePipelineIdentificationCycleDaemonException
     *             if any error occurs during loading the properties.
     */
    private static Properties loadProperties(String configFilePath)
        throws TogglePipelineIdentificationCycleDaemonException {

        // validates the parameter
        if (configFilePath != null && configFilePath.trim().length() == 0) {
            ReportHelper.logError(LOGGER, TogglePipelineIdentificationCycleDaemon.class.getName()
                    + ".loadProperties", "The configFilePath cannot be empty.");
            throw new IllegalArgumentException("The configFilePath cannot be empty.");
        }

        // creates the properties
        Properties prop = new Properties();
        InputStream in = null;
        try {

            if (configFilePath != null) {
                // creates the input stream
                in = new FileInputStream(configFilePath);
            } else {
                // creates the input stream with default name
                in = TogglePipelineIdentificationCycleDaemon.class.getClassLoader().getResourceAsStream(
                                DEFAULT_CONFIG_FILE);
            }
        } catch (FileNotFoundException e) {
            // log the error
            ReportHelper.logError(LOGGER, TogglePipelineIdentificationCycleDaemon.class.getName()
                    + ".loadProperties", "file not found.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "file not found.", e);
        }

        try {
            // load the properties
            prop.load(in);
        } catch (IOException e) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName() + ".loadProperties",
                        "error occurs in loading properties.");
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "error occurs in loading properties.", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // close silently
            }
        }

        return prop;
    }

    /**
     * <p>
     * Schedules a job with a specific service, configuration(providing time and
     * status).
     * </p>
     *
     * @param scheduler
     *            the scheduler.
     * @param name
     *            the name of the job(postfix).
     * @param config
     *            the configuration, providing time and status.
     * @param service
     *            the service to bind.
     * @throws TogglePipelineIdentificationCycleDaemonException
     *             if any error occurs.
     */
    private static void scheduleJob(Scheduler scheduler, String name,
            String config, SystemConfigurationPropertyService service)
        throws TogglePipelineIdentificationCycleDaemonException {

        // validates the parameters
        checkNullOrEmpty(config, "config");
        checkNullOrEmpty(name, "name");

        // parse the configuration
        String[] parts = config.split(EXECUTION_TIME_AND_STATUS_SEPARATOR);

        // validates the configuration
        if (parts.length != 2 || parts[0].trim().length() == 0
                || parts[1].trim().length() == 0) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName() + ".scheduleJob",
                        "the configuration should be 2 parts seperated by " + EXECUTION_TIME_AND_STATUS_SEPARATOR);
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "the configuration should be 2 parts seperated by " + EXECUTION_TIME_AND_STATUS_SEPARATOR);
        }

        // parse PipelineCycleStatus
        PipelineCycleStatus status;
        try {
            status = Enum.valueOf(PipelineCycleStatus.class, parts[1].trim());
        } catch (IllegalArgumentException e) {
            // log the error
            ReportHelper.logError(LOGGER, TogglePipelineIdentificationCycleDaemon.class.getName()
                    + ".scheduleJob", "cannot find the status type " + parts[1].trim());
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "cannot find the status type " + parts[1].trim());
        }

        // creates the job detail
        JobDetail jobDetail = new JobDetail();
        jobDetail.setName(QUARTZ_JOBNAME_PREFIX + name);

        // set the properties for job detail
        jobDetail.setJobClass(TogglePipelineIdentificationCycleJob.class);
        jobDetail.getJobDataMap().put(
                TogglePipelineIdentificationCycleJob.SYSTEM_CONFIGURATION_PROPERTY_SERVICE, service);
        jobDetail.getJobDataMap().put(
                TogglePipelineIdentificationCycleJob.PIPELINE_CYCLE_STATUS, status);

        // creates the trigger
        CronTrigger trigger = new CronTrigger();
        trigger.setName(QUARTZ_TRIGGERNAME_PREFIX + name);

        try {
            // set the cron expression
            trigger.setCronExpression(parts[0].trim());

            // schedule the job
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ParseException e) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName()  + ".scheduleJob",
                        "Failed to parse the schedule config. " + parts[0].trim());
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to parse the schedule config. " + parts[0].trim(), e);
        } catch (SchedulerException e) {
            // log the error
            ReportHelper.logError(LOGGER,
                    TogglePipelineIdentificationCycleDaemon.class.getName()
                    + ".scheduleJob", "Failed to schedule the job. " + e.getMessage());
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to schedule the job. " + e.getMessage(), e);
        }

    }

}
