/**
 * Copyright ?2003, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.ConfigManagerInterface;
import com.topcoder.util.exec.AsynchronousExecutorHandle;
import com.topcoder.util.exec.Exec;
import com.topcoder.util.exec.ExecutionResult;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * The major class of job scheduler.
 * </p>
 * @author urtks
 * @version 1.0
 */
public class Scheduler implements ConfigManagerInterface {

    // /////////////////////////////////////
    // attributes

    /**
     * <p>
     * Handle to the configuration manager object used to retrieve persisted job
     * information.
     * </p>
     */
    private ConfigManagerInterface cmHandle;

    /**
     * <p>
     * Handle to the LogManager which is used to log the results of job
     * executions.
     * </p>
     */
    private Log logHandle;

    /**
     * <p>
     * Name of the package.
     * </p>
     */
    private final String packageName = "com.topcoder.util.scheduler";

    /**
     * <p>
     * The type of job. An external job is an O/S executable or script.
     * </p>
     */
    public final static int JOB_TYPE_EXTERNAL = 0;

    /**
     * <p>
     * The type of job. A Java Class is type is a java class loaded through
     * reflection and then executed.
     * </p>
     */
    public final static int JOB_TYPE_JAVA_CLASS = 1;

    /**
     * <p>
     * Contains an entry for every job that is to be run. Contains objects of
     * type Job.
     * </p>
     */
    private List schedule;

    /**
     * <p>
     * Contains an entry for every job currently, expected, to run. Stores
     * objects of type ExecutingJob, implemented either through ExternalJob or
     * InternalJob.
     * </p>
     */
    private List execution;

    /**
     * <p>
     * Whether the scheduler is running.
     * </p>
     */
    private boolean isRunning = false;

    /**
     * <p>
     * NameSpace of this scheduler.
     * </p>
     */
    private String namespace;

    /**
     * <p>
     * Timer for the constantly checking.
     * </p>
     */
    private Timer timer;

    /**
     * <p>
     * Interval for the schedule list checking.
     * </p>
     */
    private final int timerInterval = 1000;

    /**
     * <p>
     * Index of schedule list.
     * </p>
     */
    private int scheduleIdx;

    /**
     * <p>
     * Index of execution list.
     * </p>
     */
    private int executionIdx;

    /**
     * <p>
     * Whether the config file needs to be updated.
     * </p>
     */
    private boolean updateConfig = true;

    /**
     * <p>
     * Indicate whether to check the schedule list or execution list for the
     * next cycle.
     * </p>
     */
    private boolean checkSchedule;

    /**
     * <p>
     * String for the Calendar.YEAR.
     * </p>
     */
    private static String YEAR = "YEAR";

    /**
     * <p>
     * String for the Calendar.MONTH.
     * </p>
     */
    private static String MONTH = "MONTH";

    /**
     * <p>
     * String for the Calendar.DATE.
     * </p>
     */
    private static String DATE = "DATE";

    /**
     * <p>
     * String for the Calendar.WEEK_OF_YEAR.
     * </p>
     */
    private static String WEEK_OF_YEAR = "WEEK_OF_YEAR";

    /**
     * <p>
     * String for the Calendar.HOUR.
     * </p>
     */
    private static String HOUR = "HOUR";

    /**
     * <p>
     * String for the Calendar.MINUTE.
     * </p>
     */
    private static String MINUTE = "MINUTE";

    /**
     * <p>
     * String for the Calendar.SECOND.
     * </p>
     */
    private static String SECOND = "SECOND";

    /**
     * <p>
     * String name for the start time.
     * </p>
     */
    private static String START_DTG = "StartDTG";

    /**
     * <p>
     * String name for the end time.
     * </p>
     */
    private static String END_DTG = "EndDTG";

    /**
     * <p>
     * String name for the interval value.
     * </p>
     */
    private static String INTERVAL_VALUE = "IntervalValue";

    /**
     * <p>
     * String name for the interval unit.
     * </p>
     */
    private static String INTERVAL_UNIT = "IntervalUnit";

    /**
     * <p>
     * String name for the job type.
     * </p>
     */
    private static String JOB_TYPE = "JobType";

    /**
     * <p>
     * String name for the job command.
     * </p>
     */
    private static String JOB_CMD = "JobCmd";

    /**
     * <p>
     * String name for the JOB_TYPE_JAVA_CLASS.
     * </p>
     */
    private static String INTERNAL_JOB = "JOB_TYPE_JAVA_CLASS";

    /**
     * <p>
     * String name for the JOB_TYPE_EXTERNAL.
     * </p>
     */
    private static String EXTERNAL_JOB = "JOB_TYPE_EXTERNAL";

    // /////////////////////////////////////
    // operations --- public methods

    /**
     * <p>
     * Constructor for Scheduler object. The configurationNamespace is used to
     * retrieve the configuration file for the scheduler. If the
     * configurationNamespace does not exist, the constructor should build a new
     * one.
     * </p>
     * <p>
     * @param configurationNamespace
     *            The configuration file namespace.
     *            </p>
     */
    public Scheduler(String configurationNamespace) {
        if (configurationNamespace == null) {
            throw new IllegalArgumentException("configurationNamespace should not be null");
        }

        namespace = configurationNamespace;
        schedule = new ArrayList();
        execution = new ArrayList();

        // Load the config file
        cmHandle = ConfigManager.getInstance();
        try {
            ConfigManager cm = (ConfigManager) cmHandle;

            if (!cm.existsNamespace(configurationNamespace)) {
                String filename = packageName.replace('.', File.separatorChar) + File.separator
                    + configurationNamespace + ".xml";

                try {
                    cm.add(configurationNamespace, filename, ConfigManager.CONFIG_XML_FORMAT);
                } catch (ConfigManagerException e) {
                    File f = new File("conf" + File.separator + filename);
                    if (!f.exists()) {
                        createConfigFile(f);
                    }
                    cm.add(configurationNamespace, f.getAbsolutePath(),
                        ConfigManager.CONFIG_XML_FORMAT);
                }
            } else {
                File f = new File(cm.getConfigFilename(configurationNamespace));
                if (!f.exists()) {
                    createConfigFile(f);
                }
                cm.refresh(configurationNamespace);
            }
        } catch (Exception e) {
            dumpError(e);
        }

        // Initialize the variables
        try {
            LogFactory factory = LogFactory.getInstance();
            logHandle = factory.getLog(packageName);
        } catch (LogException e) {
            dumpError(e);
        }
        if (logHandle == null)
            System.out
                .println("The Logger failed to initialize correctly.  The Job Scheduler will execute normally without logging.");
    } // end Scheduler

    /**
     * <p>
     * Starts the actual scheduling process. If the scheduler is already running
     * invoking start() has no effect. Start() will read in the job information
     * from the configuration file each time it is successfully invoked.
     * </p>
     */
    public void start() {
        if (!isRunning) {
            // Set the running flag
            isRunning = true;
            // Load the config file
            try {
                ((ConfigManager) cmHandle).refresh(namespace);
            } catch (ConfigManagerException e) {
                dumpError(e);
            }
            // Add all the jobs to schedule list
            schedule.clear();
            try {
                initSchedule();
            } catch (Exception e) {
                dumpError(e);
            } catch (JobActionException jae) {
            }

            // Initialize variables
            scheduleIdx = 0;
            executionIdx = 0;
            checkSchedule = true;
            // Constantly check the schedule list & execution list
            timer = new Timer();
            timer.schedule(new ScheduleTask(), 0, timerInterval);
        }
    } // end start

    /**
     * <p>
     * Stops the scheduler. The scheduler will attempt to stop any currently
     * active jobs, then it will clear itself of all job information. The
     * scheduler can be started with a call to start(), forcing a new read of
     * the jobs in the configuration file.
     * </p>
     */
    public void stop() {
        if (isRunning) {
            // Modify the variables
            timer.cancel();
            isRunning = false;
            // Stop all the executing jobs
            ListIterator iter = execution.listIterator();
            while (iter.hasNext()) {
                ExecutingJob execJob = (ExecutingJob) iter.next();
                logJob(execJob, null);
                execJob.stop();
            }
            // Clear the schedule and execution lists
            schedule.clear();
            execution.clear();
        }
    } // end stop

    /**
     * <p>
     * Prepares the object for garbage collection. Attempts to shutdown all
     * currently executing jobs, then clears all objects/information in
     * preparation for garbage collection.
     * </p>
     */
    public void shutdown() {
        stop();
    } // end shutdown

    /**
     * <p>
     * Adds the given job to the configuration file, then, if appropriate, loads
     * it into the scheduler.
     * </p>
     * <p>
     * @param job
     *            The job to add.
     *            </p>
     *            <p>
     * @throws JobActionException
     *             when the job is invalid.
     *             </p>
     */
    public void addJob(Job job) throws JobActionException {
        if (job == null || !isValidJob(job)) {
            // Throw exception for invalid job property
            throw new JobActionException("Invalid job.");
        } else {
            // Check whether the same job name exists
            if (schedule.contains(job)) {
                throw new JobActionException("The same job name exists.");
            }

            // Add the job to the schedule list
            if (job.getStop() == null || job.getStop().after(new GregorianCalendar())) {
                computeNextRun(job);
                job.setStatus("Idle");
                schedule.add(new Job(job));
            }

            updateConfigFile(null, job);
        }
    } // end addJob

    /**
     * <p>
     * Replaces the job with new job information.
     * </p>
     * <p>
     * @param oldJob
     *            The job that will be replaced.
     *            </p>
     *            <p>
     * @param newJob
     *            The job that will replace it.
     *            </p>
     * @throws JobActionException
     *             if the new job is valid or has the same name as existing job.
     */
    public void replaceJob(Job oldJob, Job newJob) throws JobActionException {
        if (newJob == null || !isValidJob(newJob)) {
            // Throw exception for invalid job property
            throw new JobActionException("Invalid job.");
        } else {
            // Check whether the old job name exists
            if (!schedule.contains(oldJob)) {
                throw new JobActionException("The old job can not be found.");
            }
            // Check whether the same job name exists
            if (schedule.contains(newJob) && !oldJob.equals(newJob)) {
                throw new JobActionException("The same job name exists.");
            }

            schedule.remove(oldJob);

            // Add the job to the schedule list
            if (newJob.getStop() == null || newJob.getStop().after(new GregorianCalendar())) {
                computeNextRun(newJob);
                schedule.add(new Job(newJob));
            }
            updateConfigFile(oldJob, newJob);
        }
    } // end replaceJob

    /**
     * <p>
     * Deletes the job identified by Job. Does not effect the execution of Job
     * (use stopJob to stop execution).
     * </p>
     * <p>
     * @param job
     *            The job to delete from the configuration file.
     *            </p>
     *            <p>
     * @throws JobActionException
     *             if the job does not exist.
     *             </p>
     */
    public void deleteJob(Job job) throws JobActionException {
        // Check whether the job name exists
        if (schedule.contains(job)) {
            schedule.remove(job);
            updateConfigFile(job, null);
        } else {
            throw new JobActionException("The job can not be found");
        }
    } // end deleteJob

    /**
     * <p>
     * Returns a list of all jobs loaded in the Scheduler, regardless of their
     * current execution status. The Job objects returned are copies of the
     * internal Job object.
     * </p>
     * <p>
     * @return a List containing
     *         </p>
     */
    public List getJobList() {
        return schedule;
    } // end getJobList

    /**
     * <p>
     * Returns a List containing all executing jobs, each stored in a Job
     * object. The Job object returned is a copy of the Job object.
     * </p>
     * <p>
     * @return a List containing all executing jobs, each stored in a Job
     *         object.
     *         </p>
     */
    public List getJobExecutionList() {
        return execution;
    } // end getJobExecutionList

    /**
     * <p>
     * Returns true if the Job Scheduler is running (it has been started through
     * a call to start()), false otherwise.
     * </p>
     * <p>
     * @return a boolean indicating whether scheduler is running or not.
     *         </p>
     */
    public boolean isSchedulerRunning() {
        return isRunning;
    } // end isSchedulerRunning

    /**
     * <p>
     * Attempts to stop the job specified by object Job.
     * </p>
     * <p>
     * @param job
     *            The Job to attempt to stop.
     *            </p>
     */
    public void stopJob(Job job) {
        // Check whether the job exists in execution list
        int idx = execution.indexOf(job);
        if (idx >= 0) {
            ExecutingJob execJob = (ExecutingJob) execution.get(idx);
            int pos = schedule.indexOf(execJob);
            if (pos != -1) {
                ((Job) schedule.get(pos)).setStatus("Idle");
            }
            logJob(execJob, null);
            execJob.stop();
            execution.remove(execJob);
        }
    } // end stopJob

    /**
     * <p>
     * Return the name space of this scheduler.
     * </p>
     * <p>
     * @return a String with the name space of this scheduler.
     *         </p>
     */
    public String getNamespace() {
        return namespace;
    } // end getNameSpace

    /**
     * <p>
     * Return the config property names
     * </p>
     * <p>
     * @return a Enumeration with the config property names
     *         </p>
     */
    public Enumeration getConfigPropNames() {
        Enumeration enum = null;
        try {
            enum = ((ConfigManager) cmHandle).getPropertyNames(namespace);
        } catch (ConfigManagerException e) {
            dumpError(e);
        }

        return enum;
    } // end getConfigPropNames

    // /////////////////////////////////////
    // operations --- private methods

    /**
     * <p>
     * Dump to a local error.txt file the errors received.
     * </p>
     * <p>
     * @param e
     *            The exeception thrown by the ConfigManager or Logger.
     *            </p>
     */
    private void dumpError(Exception e) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("error.txt", true));
            out.println(e.toString());
            out.close();
        } catch (IOException ee) {
            System.out.println("Can't write to error.txt!");
        }
    }

    /**
     * <p>
     * Create the config file.
     * </p>
     * <p>
     * @param f
     *            The config file.
     *            </p>
     */
    private void createConfigFile(File f) {
        try {
            File dir = f.getParentFile();
            if (!dir.exists()) {
                f.getParentFile().mkdirs();
            }

            PrintWriter out = new PrintWriter(new FileWriter(f));

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<CMConfig>");
            out.println("<Property name = \"" + namespace + "\">");
            out.println("</Property>");
            out.println("</CMConfig>");
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * <p>
     * Update the config file.
     * </p>
     * <p>
     * @param oldJob
     *            The old job to be deleted, null if adding a job.
     * @param newJob
     *            The new job to be added, null if deleting a job.
     *            </p>
     */
    private void updateConfigFile(Job oldJob, Job newJob) {
        if (!updateConfig) {
            return;
        }

        try {
            ConfigManager cm = (ConfigManager) cmHandle;
            Enumeration enum = cm.getPropertyNames(namespace);
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM,
                Locale.US);
            cm.createTemporaryProperties(namespace);

            while (enum.hasMoreElements()) {
                String key = (String) enum.nextElement();
                String[] values = cm.getStringArray(namespace, key);

                if (oldJob == null || !oldJob.getName().equals(key)) {
                    cm.setProperty(namespace, key, values);
                } else {
                    cm.setProperty(namespace, key, "");
                }
            }

            if (newJob != null) {
                String key = newJob.getName();
                String jobType = newJob.getJobType() == JOB_TYPE_EXTERNAL ? EXTERNAL_JOB
                    : INTERNAL_JOB;
                String intervalUnit = unitToString(newJob.getIntervalUnit());
                String endDTG = newJob.getStop() == null ? "" : df.format(newJob.getStop()
                    .getTime());
                String[] values = {START_DTG + ":" + df.format(newJob.getStart().getTime()),
                    END_DTG + ":" + endDTG, INTERVAL_VALUE + ":" + newJob.getIntervalValue(),
                    INTERVAL_UNIT + ":" + intervalUnit, JOB_TYPE + ":" + jobType,
                    JOB_CMD + ":" + newJob.getRunCommand()};

                cm.setProperty(namespace, key, values);
            }

            cm.commit(namespace, packageName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * <p>
     * Get the interval unit according to the string.
     * </p>
     * @param s
     *            The input string.
     * @return the unit constant of class Calendar.
     */
    private int stringToUnit(String s) {
        if (s.equalsIgnoreCase(YEAR)) {
            return Calendar.YEAR;
        } else if (s.equalsIgnoreCase(MONTH)) {
            return Calendar.MONTH;
        } else if (s.equalsIgnoreCase(DATE)) {
            return Calendar.DATE;
        } else if (s.equalsIgnoreCase(WEEK_OF_YEAR)) {
            return Calendar.WEEK_OF_YEAR;
        } else if (s.equalsIgnoreCase(HOUR)) {
            return Calendar.HOUR;
        } else if (s.equalsIgnoreCase(MINUTE)) {
            return Calendar.MINUTE;
        } else if (s.equalsIgnoreCase(SECOND)) {
            return Calendar.SECOND;
        }

        return -1;
    }

    /**
     * <p>
     * Get the string according to the interval unit.
     * </p>
     * @param unit
     *            The interval unit.
     * @return the string according to the interval unit.
     */
    private String unitToString(int unit) {
        switch (unit) {
        case Calendar.YEAR:
            return YEAR;
        case Calendar.MONTH:
            return MONTH;
        case Calendar.DATE:
            return DATE;
        case Calendar.WEEK_OF_YEAR:
            return WEEK_OF_YEAR;
        case Calendar.HOUR:
            return HOUR;
        case Calendar.MINUTE:
            return MINUTE;
        case Calendar.SECOND:
            return SECOND;
        default:
            return null;
        }
    }

    /**
     * <p>
     * Compute the nextRun for the given job.
     * </p>
     * <p>
     * @param job
     *            The job to compute the nextRun.
     *            </p>
     */
    private void computeNextRun(Job job) {
        GregorianCalendar rightNow = new GregorianCalendar();
        GregorianCalendar nextRun = null;
        if (job.getNextRun() == null) {
            nextRun = (GregorianCalendar) job.getStart().clone();
            if (nextRun.before(rightNow)
                && (job.getStop() == null || nextRun.before(job.getStop()))) {
                // Compute the nearest run time before now.
                long now = rightNow.getTime().getTime();
                long start = nextRun.getTime().getTime();
                long interval = 0;
                int num = 0;
                nextRun.add(job.getIntervalUnit(), job.getIntervalValue());
                interval = nextRun.getTime().getTime() - start;
                nextRun.add(job.getIntervalUnit(), -job.getIntervalValue());
                num = (int) ((now - start) / interval);
                nextRun.add(job.getIntervalUnit(), num * job.getIntervalValue());
            }
        } else {
            nextRun = (GregorianCalendar) job.getNextRun();
        }

        // Compute the nextRun time
        while (nextRun.before(rightNow) && (job.getStop() == null || nextRun.before(job.getStop()))) {
            nextRun.add(job.getIntervalUnit(), job.getIntervalValue());
        }

        job.setNextRun(nextRun);
    }

    /**
     * <p>
     * Add all the jobs to schedule list.
     * </p>
     * @throws JobActionException
     *             if some jobs are invalid.
     */
    private void initSchedule() throws JobActionException {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM,
            Locale.US);
        Enumeration enum = null;

        updateConfig = false;
        try {
            enum = ((ConfigManager) cmHandle).getPropertyNames(namespace);
        } catch (ConfigManagerException e) {
            dumpError(e);
        }

        // Cycle through all the jobs
        while (enum.hasMoreElements()) {
            String key = (String) enum.nextElement();
            String[] values = null;

            GregorianCalendar startDTG = new GregorianCalendar();
            GregorianCalendar endDTG = new GregorianCalendar();
            int intervalValue = -1;
            int intervalUnit = -1;
            int jobType = -1;
            String jobCmd = null;

            // Get the values of each job
            try {
                values = ((ConfigManager) cmHandle).getStringArray(namespace, key);
            } catch (ConfigManagerException e) {
                dumpError(e);
            }

            // Check the name:value pairs for each job
            if (key != null && !key.equals("")) {
                for (int i = 0; i < values.length; i++) {
                    int p = values[i].indexOf(':');
                    String name = values[i].substring(0, p).trim();
                    String val = values[i].substring(p + 1).trim();

                    try {
                        if (name.equalsIgnoreCase(START_DTG)) {
                            startDTG.setTime(df.parse(val));
                        } else if (name.equalsIgnoreCase(END_DTG)) {
                            if ("".equals(val)) {
                                endDTG = null;
                            } else {
                                endDTG.setTime(df.parse(val));
                            }
                        } else if (name.equalsIgnoreCase(INTERVAL_VALUE)) {
                            intervalValue = Integer.parseInt(val);
                        } else if (name.equalsIgnoreCase(INTERVAL_UNIT)) {
                            intervalUnit = stringToUnit(val);
                        } else if (name.equalsIgnoreCase(JOB_TYPE)) {
                            if (val.equalsIgnoreCase(INTERNAL_JOB)) {
                                jobType = JOB_TYPE_JAVA_CLASS;
                            } else {
                                jobType = JOB_TYPE_EXTERNAL;
                            }
                        } else if (name.equalsIgnoreCase(JOB_CMD)) {
                            jobCmd = val;
                        }
                    } catch (ParseException e) {
                        throw new JobActionException("Invalid job.");
                    }
                }
                // Add the job to schedule list
                addJob(new Job(key, startDTG, endDTG, intervalValue, intervalUnit, jobType, jobCmd));
            }
        }
        updateConfig = true;
    }

    /**
     * <p>
     * Write the job execution information to log file.
     * </p>
     * <p>
     * @param execJob
     *            The job to be logged.
     *            </p>
     *            <p>
     * @param launch
     *            The launch status.
     *            </p>
     */
    private void logJob(ExecutingJob execJob, String launch) {
        // Log the information
        try {
            GregorianCalendar rightNow = new GregorianCalendar();

            if (logHandle != null) {
                if (launch != null) {
                    logHandle.log(Level.INFO, execJob.getName() + ": "
                        + execJob.getLastRun().getTime() + ": " + rightNow.getTime() + ": "
                        + launch + execJob.getStatus());
                } else if (execJob.isDone()) {
                    logHandle.log(Level.INFO, execJob.getName() + ": "
                        + execJob.getLastRun().getTime() + ": " + rightNow.getTime() + ": "
                        + "Terminated normally: " + execJob.getStatus());
                } else {
                    logHandle.log(Level.INFO, execJob.getName() + ": "
                        + execJob.getLastRun().getTime() + ": " + rightNow.getTime() + ": "
                        + "Terminated abnormally: " + execJob.getStatus());
                }
            }
        } catch (LogException e) {
            dumpError(e);
        }
    }

    /**
     * <p>
     * Check whether the given intervalUnit is a valid one.
     * </p>
     * <p>
     * @param intervalUnit
     *            the intervalUnit to be checked.
     *            </p>
     *            <p>
     * @return true if the given intervalUnit is valid, otherwise false.
     *         </p>
     */
    private boolean isValidIntervalUnit(int intervalUnit) {
        return (intervalUnit == Calendar.YEAR) || (intervalUnit == Calendar.MONTH)
            || (intervalUnit == Calendar.DATE) || (intervalUnit == Calendar.WEEK_OF_YEAR)
            || (intervalUnit == Calendar.HOUR) || (intervalUnit == Calendar.MINUTE)
            || (intervalUnit == Calendar.SECOND);
    }

    /**
     * <p>
     * Check whether the given job is a valid job.
     * </p>
     * <p>
     * @param job
     *            the job to be checked.
     *            </p>
     *            <p>
     * @return true if the given job is valid, otherwise false
     *         </p>
     */
    private boolean isValidJob(Job job) {
        return (job.getStart() != null) && (!job.getStart().after(job.getStop()))
            && (job.getName() != null) && (!job.getName().equals(""))
            && (job.getIntervalValue() > 0) && (isValidIntervalUnit(job.getIntervalUnit()))
            && (job.getJobType() == JOB_TYPE_EXTERNAL || job.getJobType() == JOB_TYPE_JAVA_CLASS);
    }

    // /////////////////////////////////////
    // inner classes/interfaces

    /**
     * <p>
     * The class for the external job.
     * </p>
     */
    protected class ExternalJob extends ExecutingJob {

        // /////////////////////////////////////
        // attributes

        /**
         * <p>
         * The handle of the job.
         * </p>
         */
        private AsynchronousExecutorHandle job;

        // /////////////////////////////////////
        // operations

        /**
         * <p>
         * Creates a new <code>ExternalJob</code> instance.
         * </p>
         * <p>
         * @param job
         *            The job information for this external job.
         *            </p>
         */
        protected ExternalJob(Job job) {
            super(job);
            this.job = null;
        } // end ExternalJob

        /**
         * <p>
         * Returns a String status from the executing job. Not all jobs will
         * necessarily return a status. The status string value, if any, is
         * stored on completion in the log file.
         * </p>
         * <p>
         * @return a String with the current or last status of the executing
         *         job.
         *         </p>
         */
        public String getStatus() {
            if (job == null) {
                return null;
            } else {
                if (isDone()) {
                    try {
                        ExecutionResult result = job.getExecutionResult();
                        if (result.getExitStatus() != 0) {
                            return "Complete with error";
                        } else {
                            return "Complete without error";
                        }
                    } catch (IllegalStateException e) {
                        return "Complete with error";
                    }
                } else {
                    return "Not finished";
                }
            }
        } // end getStatus

        /**
         * <p>
         * Returns true if the job has completed normally. This is used to
         * monitor the job while it is executing.
         * </p>
         * <p>
         * @return a boolean with True if job has completed, false otherwise.
         *         </p>
         */
        public boolean isDone() {
            return job.isDone();
        } // end isDone

        /**
         * <p>
         * Stops the execution of the job, even if not completed.
         * </p>
         */
        public void stop() {
            job.halt();
        } // end stop

        /**
         * <p>
         * Starts the actual execution of the job.
         * </p>
         */
        public void start() {
            try {
                job = Exec.executeAsynchronously(new String[] {getRunCommand()});
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } // end start
    } // end Scheduler.ExternalJob

    /**
     * <p>
     * The class for the internal job.
     * </p>
     */
    protected class InternalJob extends ExecutingJob {

        // /////////////////////////////////////
        // attributes

        /**
         * <p>
         * The internal job handle.
         * </p>
         */
        private Object job;

        // /////////////////////////////////////
        // operations

        /**
         * <p>
         * Creates a <code>InternalJob</code> instance.
         * </p>
         * <p>
         * @param job
         *            The job information for this internal job.
         *            </p>
         */
        protected InternalJob(Job job) {
            super(job);
            this.job = null;
        } // end InternalJob

        /**
         * <p>
         * Returns a String status from the executing job. Not all jobs will
         * necessarily return a status. The status string value, if any, is
         * stored on completion in the log file.
         * </p>
         * <p>
         * @return a String with the current or last status of the executing
         *         job.
         *         </p>
         */
        public String getStatus() {
            if (job != null) {
                return ((Schedulable) job).getStatus();
            } else {
                return null;
            }
        } // end getStatus

        /**
         * <p>
         * Returns true if the job has completed normally. This is used to
         * monitor the job while it is executing.
         * </p>
         * <p>
         * @return a boolean with True if job has completed, false otherwise.
         *         </p>
         */
        public boolean isDone() {
            return ((Schedulable) job).isDone();
        } // end isDone

        /**
         * <p>
         * Stops the execution of the job, even if not completed.
         * </p>
         */
        public void stop() {
            ((Schedulable) job).close();
        } // end stop

        /**
         * <p>
         * Starts the actual execution of the job.
         * </p>
         */
        public void start() {
            try {
                Class classDefinition = Class.forName(getRunCommand());
                job = classDefinition.newInstance();
                new Thread((Runnable) job).start();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } // end start

    } // end Scheduler.InternalJob

    /**
     * <p>
     * The base class for the executing job.
     * </p>
     */
    protected abstract class ExecutingJob extends Job {

        // /////////////////////////////////////
        // operations

        /**
         * <p>
         * Returns a String status from the executing job. Not all jobs will
         * necessarily return a status. The status string value, if any, is
         * stored on completion in the log file.
         * </p>
         * <p>
         * @return a String with the current or last status of the executing
         *         job.
         *         </p>
         */
        public abstract String getStatus();

        /**
         * <p>
         * Returns true if the job has completed normally. This is used to
         * monitor the job while it is executing.
         * </p>
         * <p>
         * @return a boolean with True if job has completed, false otherwise.
         *         </p>
         */
        public abstract boolean isDone();

        /**
         * <p>
         * Stops the execution of the job, even if not completed.
         * </p>
         */
        public abstract void stop();

        /**
         * <p>
         * Starts the actual execution of the job.
         * </p>
         */
        public abstract void start();

        /**
         * <p>
         * Creates a <code>ExecutingJob</code> instance.
         * </p>
         * <p>
         * @param job
         *            The job information for this executing job.
         *            </p>
         */
        protected ExecutingJob(Job job) {
            super(job);
        } // end ExecutingJob

    } // end Scheduler.ExecutingJob

    /**
     * <p>
     * This class extends from java.util.Timer in order to constantly check the
     * schedule list & execution list
     * </p>
     */
    private class ScheduleTask extends TimerTask {
        /**
         * <p>
         * Overrides run() method of Runnable interface.
         * </p>
         */
        public void run() {
            if (checkSchedule) {
                // Check the schedule list
                if (scheduleIdx >= schedule.size()) {
                    scheduleIdx = 0;
                    checkSchedule = false;
                } else {
                    GregorianCalendar rightNow = new GregorianCalendar();
                    Job job = (Job) schedule.get(scheduleIdx);
                    ExecutingJob execJob;

                    if ((job.getStop() == null || job.getStop().after(rightNow))
                        && job.getNextRun().before(rightNow)) {
                        // Set the lastRun and compute the nextRun
                        job.setLastRun(rightNow);
                        computeNextRun(job);
                        // Create a new job in execution list
                        if (job.getJobType() == JOB_TYPE_JAVA_CLASS) {
                            execJob = new InternalJob(job);
                        } else {
                            execJob = new ExternalJob(job);
                        }
                        try {
                            execJob.start();
                            execution.add(execJob);
                            job.setStatus("Running");
                        } catch (Exception e) {
                            logJob(execJob, "Launch failed");
                        }
                    }
                    scheduleIdx++;
                }
            } else {
                // Check the execution list
                if (executionIdx >= execution.size()) {
                    executionIdx = 0;
                    checkSchedule = true;
                } else {
                    ExecutingJob execJob = (ExecutingJob) execution.get(executionIdx);
                    if (execJob.isDone()) {
                        int idx = schedule.indexOf(execJob);
                        if (idx != -1) {
                            Job job = (Job) schedule.get(idx);
                            job.setStatus("Idle");
                        }
                        // Remove the job from execution list
                        execution.remove(executionIdx);
                    } else {
                        executionIdx++;
                    }
                }
            }
        }
    } // end Scheduler.ScheduleTask

} // end Scheduler

