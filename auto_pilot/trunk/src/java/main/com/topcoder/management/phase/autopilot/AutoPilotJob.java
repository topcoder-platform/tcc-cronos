/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.autopilot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.topcoder.util.commandline.ArgumentValidationException;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.IntegerValidator;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.commandline.UsageException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.util.scheduler.Job;
import com.topcoder.util.scheduler.JobActionException;
import com.topcoder.util.scheduler.Schedulable;
import com.topcoder.util.scheduler.Scheduler;

/**
 * <p>
 * Represents an auto pilot job that is to be executed using Job Scheduling component. A new
 * instance of this class will be created and executed (in a separate thread) by the Scheduler at a
 * certain interval. This class also provides the command line entry. See {@link #main(String[])}
 * for more detailed doc. This class simply encapsulates AutoPilot instance.
 * </p>
 * <p>
 * This class may not be thread-safe, the variable done is mutable by the 'run' method. However this
 * class will be run in its own thread by the Scheduler, and only a single thread will execute the
 * run method, so in the context of the scheduler, it's thread safe. The internal scheduler is
 * instantiated in schedule() in a synchronized block. It's also thread safe in the context of the
 * command-line because only one thread is active.
 * </p>
 * @author sindu, abelli
 * @version 1.0
 */
public class AutoPilotJob implements Runnable, Schedulable {

    /**
     * <p>
     * Defines the property key in the config manager that can optionally contains the operator name
     * used to do auditing.
     * </p>
     */
    public static final String CONFIG_OPERATOR_KEY = "Operator";

    /**
     * <p>
     * Represents the default operator name that is used to do auditing if none is specified in the
     * configuration files.
     * </p>
     */
    public static final String DEFAULT_OPERATOR = "AutoPilotJob";

    /**
     * <p>
     * Represents the status to return when a job is running.
     * </p>
     */
    public static final String STATUS_RUNNING = "RUNNING";

    /**
     * <p>
     * Represents the status to return when the job is completed.
     * </p>
     */
    public static final String STATUS_DONE = "DONE";

    /**
     * <p>
     * The specified namespace plus this postfix is used as namespace to initialize the object factory.
     * </p>
     */
    public static final String OBJECT_FACTORY_POSTFIX = ".factory";

    /**
     * <p>
     * A static scheduler instance used to schedule AutoPilotJob. It's lazily initialized in
     * schedule and immutable afterwards. It can be retrieved with the getter.
     * </p>
     */
    private static Scheduler scheduler;

    /**
     * <p>
     * Represents the AutoPilot instance that is used to do the job. This variable is initially
     * null, initialized in constructor using object factory and immutable afterwards. It can be
     * retrieved with the getter.
     * </p>
     */
    private final AutoPilot autoPilot;

    /**
     * <p>
     * Represents the operator name that is used to do auditing. This variable is initially null,
     * initialized in constructor using values from configuration and immutable afterwards. It can
     * be retrieved with the getter.
     * </p>
     */
    private final String operator;

    /**
     * <p>
     * Represents a flag that indicates whether the job is completed. This variable is initially
     * false, after run() is complete, it'll be set to true. It is referenced by the isDone() method
     * to inform the scheduler that the job is completed.
     * </p>
     */
    private boolean done = false;

    /**
     * <p>
     * Constructs a new instance of AutoPilotJob class. This will initialize the AutoPilot instance
     * using object factory. The object factory is initialized with AutoPilotJob full name as its
     * configuration namespace. Inside this namespace, a property with the key of AutoPilot's full
     * name is used to retrieve the AutoPilot instance. The namespace can optionally contains a key
     * of "Operator" which defines the operator name that will be used to do auditing. If it's not
     * defined, DEFAULT_OPERATOR is used.
     * </p>
     * @throws ConfigurationException if any error occurs instantiating the object factory or the
     *             auto pilot instance
     */
    public AutoPilotJob() throws ConfigurationException {
        this(AutoPilotJob.class.getName(), AutoPilot.class.getName());
    }

    /**
     * <p>
     * Constructs a new instance of AutoPilotJob class using the given namespace/autoPilotKey. This
     * will initialize the AutoPilot instance using object factory. The object factory is
     * initialized with namespace + '.factory' as its configuration namespace. Inside the object
     * factory, a property with the key of autoPilotKey is used to retrieve the AutoPilot instance.<br>
     * <br>
     * The namespace (no '.factory') can optionally contain a key of "Operator" which defines the
     * operator name that will be used to do auditing. If it's not defined, DEFAULT_OPERATOR is
     * used.
     * </p>
     * @param namespace the namespace + '.factory' to initialize object factory, and the namespace
     *            (no '.factory') to retrieve other configuration
     * @param autoPilotKey the key defining the AutoPilot instance to use
     * @throws IllegalArgumentException if any of the argument is null or empty (trimmed) string
     * @throws ConfigurationException if any error occurs instantiating the object factory or the
     *             auto pilot instance
     */
    public AutoPilotJob(String namespace, String autoPilotKey) throws ConfigurationException {
        // Check arguments.
        if (null == namespace) {
            throw new IllegalArgumentException("namespace cannot be null");
        }
        if (namespace.trim().length() < 1) {
            throw new IllegalArgumentException("namespace cannot be empty");
        }
        if (null == autoPilotKey) {
            throw new IllegalArgumentException("autoPilotKey cannot be null");
        }
        if (autoPilotKey.trim().length() < 1) {
            throw new IllegalArgumentException("autoPilotKey cannot be empty");
        }

        // Create object factory.
        ObjectFactory of;
        Object objAutoPilot;
        try {
            of = new ObjectFactory(new ConfigManagerSpecificationFactory(namespace + OBJECT_FACTORY_POSTFIX));

            // Create autoPilot from object factory.
            objAutoPilot = of.createObject(autoPilotKey);
            if (!AutoPilot.class.isInstance(objAutoPilot)) {
                throw new ConfigurationException("fail to create AutoPilot object cause of bad type:" + objAutoPilot);
            }
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException(
                "fail to create auto pilot cause of invalid class specification exception", e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException(
                "fail to create object factory instance cause of specification configuration exception",
                e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException(
                "fail to create object factory instance cause of illegal reference exception", e);
        }

        // Get operator with Operator key.
        String oper;
        try {
            oper = ConfigManager.getInstance().getString(namespace, CONFIG_OPERATOR_KEY);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("fail to get operator cause of unknown namespace '"
                + namespace + "'", e);
        }

        this.autoPilot = (AutoPilot) objAutoPilot;

        // Use default operator name if not specified.
        if (oper != null && oper.trim().length() > 0) {
            this.operator = oper;
        } else {
            this.operator = DEFAULT_OPERATOR;
        }
    }

    /**
     * <p>
     * Constructs a new instance of AutoPilotJob class using the given AutoPilot instance and
     * operator name.
     * </p>
     * @param autoPilot the AutoPilot instance to use
     * @param operator the operator name for auditing
     * @throws IllegalArgumentException if any of the argument is null, or operator is empty string
     *             (trimmed).
     */
    public AutoPilotJob(AutoPilot autoPilot, String operator) {
        // Check arguments.
        if (null == autoPilot) {
            throw new IllegalArgumentException("autoPilot cannot be null");
        }
        if (null == operator) {
            throw new IllegalArgumentException("operator cannot be null");
        }
        if (operator.trim().length() < 1) {
            throw new IllegalArgumentException("operator cannot be empty");
        }

        this.autoPilot = autoPilot;
        this.operator = operator;
    }

    /**
     * <p>
     * Return the auto pilot instance used by this class.
     * </p>
     * @return the auto pilot instance used by this class
     */
    public AutoPilot getAutoPilot() {
        return this.autoPilot;
    }

    /**
     * <p>
     * Return the operator name used to do auditing.
     * </p>
     * @return the operator name used to do auditing
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * <p>
     * This method implements 'run' in the Runnable interface. It's invoked to start the job.
     * </p>
     * @throws RuntimeException - if fail to execute the auto pilot job. AutoPilotSourceException
     *             and PhaseOperationException will be wrapped in RuntimeException.
     */
    public void run() {
        // to prevent the potential if the same job instance will be executed more than once.
        done = false;

        try {
            execute();
        } catch (AutoPilotSourceException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("fail to advance projects with " + getOperator()
                + " cause of auto pilot source exception", e);
        } catch (PhaseOperationException e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("fail to advance project " + e.getProjectId() + " phase "
                + e.getPhase() + " with " + getOperator() + " cause of phase operation exception",
                e);
        }

        done = true;
    }

    /**
     * <p>
     * This method is invoked by command line interface to process a given list of project ids.
     * </p>
     * @return an array of AutoPilotResult representing result of auto-pilot (never null, but can be
     *         empty)
     * @throws AutoPilotSourceException if any error occurs retrieving project ids from
     *             AutoPilotSource
     * @throws PhaseOperationException if any error occurs while ending/starting a phase
     */
    private AutoPilotResult[] execute() throws AutoPilotSourceException, PhaseOperationException {
        return autoPilot.advanceProjects(getOperator());
    }

    /**
     * <p>
     * This method is invoked by command line interface to process a given list of project ids.
     * </p>
     * @param projectId a list of project id to process
     * @return an array of AutoPilotResult representing result of auto-pilot (never null, but can be
     *         empty)
     * @throws IllegalArgumentException if projectId is null
     * @throws AutoPilotSourceException if any error occurs retrieving project ids from
     *             AutoPilotSource
     * @throws PhaseOperationException if any error occurs while ending/starting a phase
     */
    public AutoPilotResult[] run(long[] projectId) throws AutoPilotSourceException,
        PhaseOperationException {
        return autoPilot.advanceProjects(projectId, getOperator());
    }

    /**
     * <p>
     * This method implements 'isDone' in the Schedulable interface. It's invoked by scheduler to
     * check whether the job is completed.
     * </p>
     * @return true if job is completed, false otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * <p>
     * This is invoked by the Scheduler when the Scheduler is stopped. We simply do nothing here. If
     * the job is still running, we'll let it run until it's finished. Since it's running in its own
     * thread, we can just do nothing here so that we don't block the scheduler's thread.
     * </p>
     */
    public void close() {
        // your code here
    }

    /**
     * <p>
     * This should return the job status. Application can poll this status using Scheduler's Job.
     * </p>
     * @return STATUS_DONE or STATUS_RUNNING depending whether the job has completed
     */
    public String getStatus() {
        return isDone() ? STATUS_DONE : STATUS_RUNNING;
    }

    /**
     * <p>
     * A main static method to provide command line functionality to the component. Basically, the
     * command line provides 2 functionalities:<br>
     * <br> - run once (given a list of project ids or retrieve ids from AutoPilotSource)<br>
     * <br> - poll mode (scheduled in background to run Auto Pilot every some intervals)<br>
     * <br>
     * The command line syntax is:<br>
     * java AutoPilotJob [-config configFile] [-namespace ns [-autopilot apKey]] (-poll [interval]
     * [-jobname jobname] | -project [Id[, ...]])<br>
     * <br> - configFile specifies the path to config files to be loaded into configuration manager.
     * if not specified it's assumed the config file is preloaded<br>
     * <br> - ns and apKey are optional, it's used to instantiate AutoPilotJob for project mode. ns
     * is also used to instantiate the Scheduler. The default values are AutoPilotJob's full name &
     * AutoPilot's full name respectively.<br>
     * <br> - poll or project These next options are mutually exclusive (to indicate two kinds of
     * run-mode): Both poll/projects are specified, or none are specified is not allowed.<br>
     * <br>
     * A) Poll-mode<br>
     * <br> - poll is used to define the interval in minutes, if interval is not specified, a
     * default of 5 minutes is used. The autopilot job will be executed every this interval starting
     * from midnight.<br>
     * <br> - jobname is the job name, Job Scheduling will use this job name. The default value is
     * 'AutoPilotJob'. It is optional and can only be specified if poll is specified.<br>
     * <br> - ns is used to instantiate the Scheduler. Optional. The default value is AutoPilotJob's full
     * name.<br>
     * <br> - apKey is ignored.<br>
     * <br>
     * <br>
     * B) Project mode<br>
     * <br> - project can be specified to process projects with the given ids. The project ids will
     * be processed once and the application terminates, it doesn't go into poll mode. If no ids are
     * given, AutoPilotSource is used instead.<br>
     * <br> - ns and apKey, must be specified or not at the same time, default to AutoPilotJob's
     * full name and AutoPilot's full name respectively.<br>
     * <br>
     * </p>
     * @param args the command line arguments
     * @throws IllegalArgumentException if argument is invalid, i.e.
     *             <ul>
     *             <li>specifying namespace without apKey (and vice versa) for project mode,</li>
     *             <li>specifying both poll/project, or no poll/project is given,</li>
     *             <li>specifying jobname without poll,</li>
     *             <li>poll interval cannot be converted to long</li>
     *             <li>poll interval &lt;= 0,</li>
     *             <li>project id cannot be converted to long</li>
     *             <li><code>IllegalSwitchException</code> occurs</li>
     *             <li><code>ArgumentValidationException</code> occurs</li>
     *             <li><code>UsageException</code> occurs</li>
     *             </ul>
     * @throws ConfigurationException if any error occurs loading config file or instantiating the
     *             AutoPilotJob or while configuring the job scheduler
     * @throws AutoPilotSourceException if any error occurs retrieving project ids from
     *             AutoPilotSource
     * @throws PhaseOperationException if any error occurs while ending/starting a phase
     * @throws RuntimeException if runtime exceptions occurs while executing the command line.
     */
    public static void main(String[] args) throws ConfigurationException, AutoPilotSourceException,
        PhaseOperationException {
        if (null == args) {
            throw new IllegalArgumentException("args cannot be null");
        }

        // No args, just print usage.
        if (args.length < 1) {
            showUsage();
            return;
        }

        // Build command line parser.
        CommandLineUtility clu = new CommandLineUtility();
        try {
            Switch pollSwitch = new Switch("poll", false, 0, 1, new IntegerValidator(),
                "Poll interval in mins");
            Switch projectSwitch = new Switch("project", false, 0, -1, new IntegerValidator(),
                "Project id");

            clu.addSwitch(new Switch("config", false, 1, 1, null, "The configuration file to load"));
            clu.addSwitch(new Switch("namespace", false, 1, 1, null, "Namespace configuration"));
            clu.addSwitch(new Switch("autopilot", false, 1, 1, null, "AutoPilot key"));
            clu.addSwitch(pollSwitch);
            clu.addSwitch(new Switch("jobname", false, 1, 1, null, "Job name"));
            clu.addSwitch(projectSwitch);

            clu.parse(args);

            // Get config file. Assume pre-loaded if not specified.
            String configFile = clu.getSwitch("config").getValue();
            if (configFile != null) {
                ConfigManager cfg = ConfigManager.getInstance();
                cfg.add(configFile);
            }

            String namespace = clu.getSwitch("namespace").getValue();
            List validSwitches = clu.getValidSwitches();

            // One of project and poll can exist.
            if (validSwitches.contains(pollSwitch)
                && validSwitches.contains(projectSwitch)) {
                // Both poll and project exists.
                showUsage();
                throw new IllegalArgumentException("either project or poll can exist");

            } else if (!validSwitches.contains(pollSwitch)
                && !validSwitches.contains(projectSwitch)) {
                // Neither poll nor project exists.
                showUsage();
                throw new IllegalArgumentException("either project or poll must exist");

            } else if (validSwitches.contains(projectSwitch)) {
                // Deal with project mode.
                dealProject(clu, namespace);

            } else if (validSwitches.contains(pollSwitch)) {
                // Deal with poll mode.
                dealPoll(clu, namespace);
            }

        } catch (IllegalSwitchException e) {
            throw createIAE("fail to build command line cause of illegal switch:" + e, e);
        } catch (ArgumentValidationException e) {
            throw createIAE("fail to parse command line cause of argument validation:" + e, e);
        } catch (UsageException e) {
            throw createIAE("fail to parse command line cause of usage exception:" + e, e);
        } catch (ConfigManagerException e) {
            showUsage();
            throw new ConfigurationException(
                "fail to load namespace cause of config manager exception", e);
        } catch (RuntimeException t) {
            showUsage();
            throw t;
        }
    }

    /**
     * <p>
     * Create IAE with the message, and init the cause.
     * </p>
     * @param message the message.
     * @param t the cause.
     * @return the IllegalArgumentException.
     */
    private static IllegalArgumentException createIAE(String message, Throwable t) {
        showUsage();

        IllegalArgumentException ex = new IllegalArgumentException(message);
        ex.initCause(t);
        return ex;
    }

    /**
     * <p>
     * Deal with poll mode.
     * </p>
     * @param clu the parsed command line utility.
     * @param namespace the namespace value.
     * @throws ConfigurationException - if there is configuration exceptions.
     */
    private static void dealPoll(CommandLineUtility clu, String namespace)
        throws ConfigurationException {
        // Use 5 mins if interval not specified.
        int interval = 5;
        String poll = clu.getSwitch("poll").getValue();
        if (null != poll) {
            interval = Integer.parseInt(poll);
        }

        // Get job name and schedule.
        String jobName = clu.getSwitch("jobname").getValue();
        AutoPilotJob.schedule((null == namespace) ? AutoPilotJob.class.getName() : namespace,
            (null == jobName) ? "AutoPilotJob" : jobName, interval);
        scheduler.start();
    }

    /**
     * <p>
     * Deal with project mode.
     * </p>
     * @param clu the parsed command line utility.
     * @param namespace the namespace value
     * @throws IllegalArgumentException - if jobname is specified, or namespace specified without
     *             apKey (and vice versa)
     * @throws ConfigurationException - if there is configuration exceptions.
     * @throws AutoPilotSourceException - if there is auto pilot source exceptions.
     * @throws PhaseOperationException - if there is phase operation exceptions.
     */
    private static void dealProject(CommandLineUtility clu, String namespace)
        throws ConfigurationException, AutoPilotSourceException, PhaseOperationException {
        if (null != clu.getSwitch("jobname").getValue()) {
            throw new IllegalArgumentException("jobname cannot be specified for project mode");
        }

        String autoPilot = clu.getSwitch("autopilot").getValue();

        // namespace and autopilot can exist both or neither.
        if ((null == namespace && null != autoPilot)
            || (null != namespace && null == autoPilot)) {
            throw new IllegalArgumentException("ns and apKey cannot be specified only one");
        }

        // Parse project Ids.
        List ids = clu.getSwitch("project").getValues();
        long[] projectId = null;
        if (null != ids && !ids.isEmpty()) {
            projectId = new long[ids.size()];
            int i = -1;
            for (Iterator it = ids.iterator(); it.hasNext();) {
                projectId[++i] = Long.parseLong((String) it.next());
            }
        }

        // Create AutoPilotJob instance.
        AutoPilotJob autoPilotJob = (null == namespace && null == autoPilot) ? new AutoPilotJob()
            : new AutoPilotJob(namespace, autoPilot);

        // run and print the result.
        AutoPilotResult[] result;
        result = (null == projectId) ? autoPilotJob.execute() : autoPilotJob.run(projectId);
        printResult(result);
    }

    /**
     * <p>
     * Show usage of this command line tool.
     * </p>
     */
    private static void showUsage() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                AutoPilotJob.class.getResourceAsStream("usage")));
            String ln = br.readLine();
            while (ln != null) {
                System.err.println(ln);
                ln = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Cannot find usage file!");
        }
    }

    /**
     * <p>
     * Print the auto pilot result.
     * </p>
     * @param result result array.
     */
    private static void printResult(AutoPilotResult[] result) {
        System.out.println("|      ProjectId      |    Ended    |    Started    |");
        final char[] space1 = "                                                    ".toCharArray();
        for (int i = 0; i < result.length; i++) {
            StringBuffer buf = new StringBuffer();

            // Print project Id.
            System.out.print('|');
            String strId = String.valueOf(result[i].getProjectId());
            buf.append(space1, 0, (21 - strId.length()) / 2);
            buf.append(strId);
            buf.append(space1, buf.length(), 21 - buf.length());
            System.out.print(buf);

            // Print ended.
            System.out.print('|');
            buf.delete(0, buf.length());
            String strEnd = String.valueOf(result[i].getPhaseEndedCount());
            buf.append(space1, 0, (13 - strEnd.length()) / 2);
            buf.append(strEnd);
            buf.append(space1, buf.length(), 13 - buf.length());
            System.out.print(buf);

            // Print started.
            System.out.print('|');
            buf.delete(0, buf.length());
            String strStart = String.valueOf(result[i].getPhaseStartedCount());
            buf.append(space1, 0, (15 - strEnd.length()) / 2);
            buf.append(strStart);
            buf.append(space1, buf.length(), 15 - buf.length());
            System.out.print(buf);

            System.out.println('|');
        }
    }

    /**
     * <p>
     * Returns an AutoPilotJob job to be scheduled using Job Scheduling component. The job is
     * created with the given name, starting at midnight and executing every interval minutes until
     * forever.
     * </p>
     * @param jobName the job name
     * @param interval the interval (in minutes)
     * @return a Job to run AutoPilotJob starting at midnight today at every interval minutes
     * @throws IllegalArgumentException if jobName is null or an empty (trimmed) string or interval <=
     *             0
     */
    public static Job createJob(String jobName, int interval) {
        // Check arguments.
        if (null == jobName) {
            throw new IllegalArgumentException("jobName cannot be null");
        }
        if (jobName.trim().length() < 1) {
            throw new IllegalArgumentException("jobName cannot be empty");
        }
        if (interval < 1) {
            throw new IllegalArgumentException("interval cannot be 0 or negative:" + interval);
        }

        GregorianCalendar start = new GregorianCalendar();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return new Job(jobName, start, null, interval, Calendar.MINUTE,
            Scheduler.JOB_TYPE_JAVA_CLASS, AutoPilotJob.class.getName());
    }

    /**
     * <p>
     * Schedules a job using the static scheduler. The scheduler's Timer will prevent application
     * from exiting until it's closed. This will lazily instantiate the Scheduler (if one hasn't
     * been instantiated), then it'll add the AutoPilotJob to the scheduler and start it. The
     * scheduler instantiation is synchronized so that multiple thread trying to schedule jobs will
     * only instantiate one scheduler instance.<br>
     * Cause job name is unique in the scheduler, the job with the same name will be replaced with
     * auto pilot job if there exists the job with the same name. Otherwise the auto pilot job will
     * be added.
     * </p>
     * @param namespace the namespace to configure scheduler with
     * @param jobName the job name
     * @param interval the interval in minutes
     * @throws IllegalArgumentException if string parameters are null or empty (trimmed) string, or
     *             interval is non-positive.
     * @throws ConfigurationException if an error occurs configuring the Scheduler
     */
    public static void schedule(String namespace, String jobName, int interval)
        throws ConfigurationException {
        // Check arguments.
        if (null == namespace) {
            throw new IllegalArgumentException("namespace cannot be null");
        }
        if (namespace.trim().length() < 1) {
            throw new IllegalArgumentException("namespace cannot be empty");
        }
        if (null == jobName) {
            throw new IllegalArgumentException("jobName cannot be null");
        }
        if (jobName.trim().length() < 1) {
            throw new IllegalArgumentException("jobName cannot be empty");
        }
        if (interval < 1) {
            throw new IllegalArgumentException("interval cannot be 0 or negative:" + interval);
        }

        synchronized (AutoPilotJob.class) {
            if (scheduler == null) {
                scheduler = new Scheduler(namespace);
            }
        }

        // Add or replace the job with jobName.
        synchronized (scheduler) {
            try {
                // Find old job with same name.
                Job oldJob = findJob(jobName);

                // Create new job if no old job with same name. Replace with new job otherwise.
                Job newJob = createJob(jobName, interval);
                if (null == oldJob) {
                    scheduler.addJob(newJob);
                } else {
                    scheduler.replaceJob(oldJob, newJob);
                }
            } catch (JobActionException e) {
                throw new ConfigurationException("fail to add job '" + jobName
                    + "' cause of job action exception", e);
            } catch (RuntimeException e) {
                throw new ConfigurationException("fail to add job '" + jobName
                    + "' cause of run time exception", e);
            }

            if (!scheduler.isSchedulerRunning()) {
                scheduler.start();
            }
        }
    }

    /**
     * <p>
     * Find job with the specified name.
     * </p>
     * @param jobName the job name.
     * @return the job with the specified name.
     */
    private static Job findJob(String jobName) {
        Job oldJob = null;
        for (Iterator it = scheduler.getJobList().iterator(); it.hasNext();) {
            Job job = (Job) it.next();
            if (job.getName().equals(jobName)) {
                oldJob = job;
                break;
            }
        }
        return oldJob;
    }

    /**
     * <p>
     * Returns the internal scheduler instance (could be null).
     * </p>
     * @return the internal scheduler instance (could be null if schedule hasn't been called yet)
     */
    public static Scheduler getScheduler() {
        return scheduler;
    }
}
