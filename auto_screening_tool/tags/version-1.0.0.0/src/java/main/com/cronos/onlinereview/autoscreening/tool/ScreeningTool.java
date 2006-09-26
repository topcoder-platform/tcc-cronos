/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.IntegerValidator;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.scheduler.Job;
import com.topcoder.util.scheduler.Schedulable;
import com.topcoder.util.scheduler.Scheduler;

/**
 * <p>
 * This class provides the command-line interface for the component. It is
 * responsible for parsing the configurable command-line parameters and
 * initializing the component to perform screening using the switches that were
 * provided. It utilizes the TC Job Scheduler to make sure that the screening
 * process runs at regular intervals.
 * </p>
 * <p>
 * Thread Safety: This class does not need to be thread safe since it is run
 * from the command line, and so there will only be one instance running on a
 * single thread.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class ScreeningTool {

    /**
     * <p>
     * This is an inner class that is used to adapt the Screener class and allow
     * it to be run by the Job scheduler. The run method of this class simply
     * delegates to the screen method of the screener. It will always return
     * "screening" as status.
     * </p>
     * <p>
     * Thread Safety: This will only be invoked by a single thread (by the Job
     * Scheduler). However the Job Scheduler just starts a new thread to call
     * the run method of this class every certain period. So there is a chance
     * that the previous screening job has not finished when another screening
     * job is started. As a result, the run method has be to synchronized on the
     * static screener field.
     * </p>
     * @author ShindouHikaru, urtks
     * @version 1.0
     */
    public static class ScreeningJob implements Runnable, Schedulable {

        /**
         * Indicates whether the job is done.
         */
        private boolean finished;

        /**
         * <p>
         * Default constructor.
         * </p>
         */
        public ScreeningJob() {
        }

        /**
         * <p>
         * Run method that performs the actual work of this component. This
         * method is synchronized on the screener field.
         * </p>
         * @throws ScreeningException
         *             if the screener instance for this screening job has not
         *             been created.
         */
        public synchronized void run() {
            if (screener == null) {
                throw new ScreeningException(
                    "The screener instance for this screening job has not been created.");
            }

            synchronized (screener) {
                finished = false;

                // do the screening.
                screener.screen();

                finished = true;
            }
        }

        /**
         * <p>
         * This method is used to stop the job. This method will not do
         * anything. The Screener will be allowed to complete one iteration of
         * screening.
         * </p>
         */
        public void close() {
        }

        /**
         * Return whether the job is done.
         * @return whether the job is done.
         */
        public boolean isDone() {
            return finished;
        }

        /**
         * <p>
         * Retrieves the screener status. This should always return the String
         * "Screening".
         * </p>
         * @return The screening status.
         */
        public String getStatus() {
            return "Screening";
        }
    }

    /**
     * Represents the name of the screening job.
     */
    private static final String SCREENING_JOB_NAME = "screening";

    /**
     * Represents the namespace of the job scheduler.
     */
    private static final String JOB_SCHEDULER_NAMESPACE = ScreeningTool.class.getName();

    /**
     * Represents the usage documentation for the interval switch.
     */
    private static final String INTERVAL_SWITCH_USAGE = "Specify the number of milliseconds "
        + "after which the screener will run again.";

    /**
     * Represents the usage documentation for the id switch.
     */
    private static final String ID_SWITCH_USAGE = "Specify the id of the screener.";

    /**
     * Represents the usage documentation for the namespace switch.
     */
    private static final String NAMESPACE_SWITCH_USAGE = "Specify the configuration namespace "
        + "that will be used by the Screener instance.";

    /**
     * <p>
     * This is a Command Line switch that is used to retrieve the information
     * for the interval duration on which to run the screening process. Note
     * that the value is expected to be provided in milliseconds.
     * </p>
     * <p>
     * Initialization Notes. name: interval; minArgs: 1; maxArgs: 1; isRequired:
     * true; validator: integer validator; validator usage:
     * INTERVAL_SWITCH_USAGE. Note that the value is expected to be provided in
     * milliseconds.
     * </p>
     */
    private static final Switch INTERVAL_SWITCH;

    /**
     * <p>
     * This is a Command Line switch that is used to retrieve the information
     * for the id that will be used by the screener.
     * </p>
     * <p>
     * Initialization Notes. name: screenerId; minArgs: 1; maxArgs: 1;
     * isRequired: true; validator: integer validator; usage: ID_SWITCH_USAGE.
     * </p>
     */
    private static final Switch ID_SWITCH;

    /**
     * <p>
     * This is a Command Line switch that is used to retrieve the configuration
     * value for the namespace.
     * </p>
     * <p>
     * Initialization Notes. name: configNamespace; minArgs: 1; maxArgs: 1;
     * isRequired: true; validator: none; usage: NAMESPACE_SWITCH_USAGE.
     * </p>
     */
    private static final Switch NAMESPACE_SWITCH;

    /**
     * This is the command line utility that has the three switches:
     * INTERVAL_SWITCH, ID_SWITCH, NAMESPACE_SWITCH.
     */
    private static final CommandLineUtility COMMAND_LINE_UTILITY;

    /**
     * Represents the number of milliseconds per second.
     */
    private static final int MILLISECONDS_PER_SECOND = 1000;

    /**
     * Static constructor
     */
    static {

        // create interval switch
        try {
            INTERVAL_SWITCH = new Switch("interval", true, 1, 1, new IntegerValidator(),
                INTERVAL_SWITCH_USAGE);
        } catch (IllegalSwitchException e) {
            throw new ScreeningException("Unable to create interval switch.", e);
        }

        // create id switch
        try {
            ID_SWITCH = new Switch("screenerId", true, 1, 1, new IntegerValidator(),
                ID_SWITCH_USAGE);
        } catch (IllegalSwitchException e) {
            throw new ScreeningException("Unable to create screenerId switch.", e);
        }

        // create namespace switch
        try {
            NAMESPACE_SWITCH = new Switch("configNamespace", true, 1, 1, null,
                NAMESPACE_SWITCH_USAGE);
        } catch (IllegalSwitchException e) {
            throw new ScreeningException("Unable to create configNamespace switch.", e);
        }

        // create command line utility
        try {
            COMMAND_LINE_UTILITY = new CommandLineUtility();
            COMMAND_LINE_UTILITY.addSwitch(INTERVAL_SWITCH);
            COMMAND_LINE_UTILITY.addSwitch(ID_SWITCH);
            COMMAND_LINE_UTILITY.addSwitch(NAMESPACE_SWITCH);
        } catch (IllegalSwitchException e) {
            throw new ScreeningException("Unable to create the CommandLineUtility instance.", e);
        }
    }

    /**
     * <p>
     * This is the screener that is used to perform actual screening process.
     * Each run method (of the inner class ScreeningJob) will delegate to this.
     * </p>
     */
    private static Screener screener;

    /**
     * <p>
     * Default constructor that is private; This class is meant to be run from
     * the command line.
     * </p>
     */
    private ScreeningTool() {
        // empty
    }

    /**
     * <p>
     * This performs the main logic for the screening tool. It will parse the
     * necessary command line parameters, then initialize the necessary classes
     * and proceed to schedule the screening process.
     * </p>
     * <p>
     * Generaly speaking, this method does the following things:
     * <ol>
     * <li>Parse the args using the Command Line Utility (CLU).</li>
     * <li>If a Usage or Validation exception occurs, print out the usage
     * string of the Command Line Utitlity and quit the program.</li>
     * <li>Otherwise, retrieve the interval, id and namespace values from the
     * CLU.</li>
     * <li>Use these id and namespace to initialize the screener.</li>
     * <li>Run "initialize" using the screener.</li>
     * <li>Create a Job named "screening", start date as the present date, null
     * end date, specified interval value, unit as SECONDS, a Java job-type, and
     * the ScreeningJob class.</li>
     * <li>Create a Job Scheduler.</li>
     * <li>Add the created Job to the Scheduler.</li>
     * <li>Run the scheudler.</li>
     * <li>If any exception occurs, print it to the main error stream and exit
     * gracefully.</li>
     * </ol>
     * </p>
     * @param args
     *            The command line arguments to use.
     */
    public static void main(String[] args) {

        // try to parse the command line
        try {
            COMMAND_LINE_UTILITY.parse(args);
        } catch (Throwable e) {
            System.out.println(COMMAND_LINE_UTILITY.getUsageString());
            return;
        }

        try {
            // get the screener id
            long id = Long.parseLong(ID_SWITCH.getValue());

            // get the interval
            int interval = Integer.parseInt(INTERVAL_SWITCH.getValue()) / MILLISECONDS_PER_SECOND;
            if (interval <= 0) {
                throw new ScreeningException("interval should be at least 1000 (1 second).");
            }

            // get the screener configuration namespace
            String namespace = NAMESPACE_SWITCH.getValue();

            // initialize the screener.
            screener = new Screener(id, namespace);
            screener.initialize();

            // create the Job instance
            Job job = new Job(SCREENING_JOB_NAME, new GregorianCalendar(), null, interval,
                Calendar.SECOND, Scheduler.JOB_TYPE_JAVA_CLASS, ScreeningJob.class.getName());

            // create the job scheduler instance, add the job and start it
            Scheduler scheduler = new Scheduler(JOB_SCHEDULER_NAMESPACE);
            scheduler.addJob(job);
            scheduler.start();

            synchronized (scheduler) {
                scheduler.wait();
            }
        } catch (Throwable e) {
            // print any exceptions to err
            e.printStackTrace(System.err);
        }
    }
}
