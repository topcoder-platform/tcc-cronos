/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import hr.leads.services.JPATestBase;
import hr.leads.services.SystemConfigurationPropertyService;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <p>
 * All tests for <code>TogglePipelineIdentificationCycleDaemon</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleDaemonTest extends JPATestBase {

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        String[] groups = scheduler.getJobGroupNames();
        for (String group : groups) {
            String[] names = scheduler.getJobNames(group);
            for (String name : names) {
                scheduler.deleteJob(name, group);
            }
        }
        openSession();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        closeSession();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.shutdown();
        super.tearDown();
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#loadProperties(String)}.
     * </p>
     *
     * <p>
     * Tests for using the default file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLoadPropertiesDefaultConfigFile() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class.getDeclaredMethod(
                "loadProperties", String.class);
        method.setAccessible(true);
        String file = null;
        Properties properties = (Properties) method.invoke(null, file);
        assertEquals("Should be equals.", "0 0 9 ? * * @@ OPEN", properties.get("execution_time_and_status_1"));
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#loadProperties(String)}.
     * </p>
     *
     * <p>
     * Tests for using the specific file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLoadPropertiesSpecificConfigFile() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class.getDeclaredMethod(
                "loadProperties", String.class);
        method.setAccessible(true);
        String file = "test_files" + File.separator + "TogglePipelineIdentificationCycleDaemon.properties";
        Properties properties = (Properties) method.invoke(null, file);
        assertEquals("Should be equals.", "0 0 9 ? * * @@ OPEN", properties.get("execution_time_and_status_1"));
    }



    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#loadProperties(String)}.
     * </p>
     *
     * <p>
     * Tests for using file not found.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLoadPropertiesNotFoundConfigFile() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class
                .getDeclaredMethod("loadProperties", String.class);
        method.setAccessible(true);
        try {
            String file = "test_files" + File.separator + "NotFound.properties";
            method.invoke(null, file);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#loadProperties(String)}.
     * </p>
     *
     * <p>
     * Tests for using file name invalid.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLoadPropertiesFileNameInvalid() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class
                .getDeclaredMethod("loadProperties", String.class);
        method.setAccessible(true);
        try {
            String file = "   ";
            method.invoke(null, file);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method
     * {@link TogglePipelineIdentificationCycleDaemon
     * #scheduleJob(Scheduler, String, SystemConfigurationPropertyService)}.
     * </p>
     *
     * <p>
     * If the configuration string is null or empty,
     * TogglePipelineIdentificationCycleDaemonException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduleJobNullOrEmptyConfig() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class
                .getDeclaredMethod("scheduleJob", Scheduler.class, String.class, String.class,
                        SystemConfigurationPropertyService.class);

        method.setAccessible(true);
        Scheduler scheduler;
        String name = "someJob";
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to start the scheduler.");
        }
        SystemConfigurationPropertyService systemConfigurationPropertyService
            = (SystemConfigurationPropertyService) lookupForTest(
                    "systemConfigurationPropertyService");
        String config = null;

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "   ";

        try {
            method.invoke(null, scheduler, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "a   @@ ";

        try {
            method.invoke(null, scheduler, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "   @@ b";

        try {
            method.invoke(null, scheduler, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method
     * {@link TogglePipelineIdentificationCycleDaemon
     * #scheduleJob(Scheduler, String, SystemConfigurationPropertyService)}.
     * </p>
     *
     * <p>
     * If the configuration string is invalid,
     * TogglePipelineIdentificationCycleDaemonException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduleJobInvalidConfig() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class
                .getDeclaredMethod("scheduleJob", Scheduler.class, String.class, String.class,
                        SystemConfigurationPropertyService.class);

        method.setAccessible(true);
        Scheduler scheduler;
        String name = "someJob";
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to start the scheduler.");
        }
        SystemConfigurationPropertyService systemConfigurationPropertyService
            = (SystemConfigurationPropertyService) lookupForTest("systemConfigurationPropertyService");
        String config = "execution_time_and_status_1=0 0 9 ? * * @@";

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "  @@   ";

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "  @@";

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "  @@    @@ @@ @@";

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }

        config = "0 0 17 ? * * @@ NOTEXIST";

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
            fail("Exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method
     * {@link TogglePipelineIdentificationCycleDaemon#scheduleJob(
     * Scheduler, String, SystemConfigurationPropertyService)}.
     * </p>
     *
     * <p>
     * If the configuration string is valid, no exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduleJobValidConfig() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class
                .getDeclaredMethod("scheduleJob", Scheduler.class, String.class,
                        String.class, SystemConfigurationPropertyService.class);

        method.setAccessible(true);
        Scheduler scheduler;
        String name = "someJob";
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "Failed to start the scheduler.");
        }
        SystemConfigurationPropertyService systemConfigurationPropertyService
            = (SystemConfigurationPropertyService) lookupForTest("systemConfigurationPropertyService");
        String config = "0 0 9 ? * * @@ OPEN";

        try {
            method.invoke(null, scheduler, name, config, systemConfigurationPropertyService);
        } catch (Exception e) {
            fail("no exception should be thrown.");
        }
    }

    /**
     * <p>
     * Tests method
     * {@link TogglePipelineIdentificationCycleDaemon#scheduleJobs(Properties, SystemConfigurationPropertyService)}.
     * </p>
     *
     * <p>
     * If the configuration string is valid, no exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduleJobs() throws Exception {
        Method method = TogglePipelineIdentificationCycleDaemon.class.getDeclaredMethod(
                "scheduleJobs", Properties.class, SystemConfigurationPropertyService.class);
        method.setAccessible(true);
        Properties properties = new Properties();
        properties.put("execution_time_and_status_1", "0 0 9 ? * * @@ OPEN");
        properties.put("execution_time_and_status_2", "0 0 17 ? * * @@ CLOSED");
        SystemConfigurationPropertyService systemConfigurationPropertyService =
            (SystemConfigurationPropertyService) lookupForTest("systemConfigurationPropertyService");
        method.invoke(null, properties, systemConfigurationPropertyService);
    }


    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemon#lookupService(String, String, String)}.
     * </p>
     *
     * <p>
     * Accuracy tests.
     * </p>
     * @throws Exception to JUnit
     */
    public void testLookupService() throws Exception {

        Method method = TogglePipelineIdentificationCycleDaemon.class.getDeclaredMethod(
                "lookupService", String.class, String.class, String.class);
        method.setAccessible(true);
        String providerUrl = "useless";
        String initialContextFactory = MockInitialContextFactory.class.getName();
        String systemConfigurationPropertyServiceJndiName = "systemConfigurationPropertyService";
        SystemConfigurationPropertyService service =
            (SystemConfigurationPropertyService) method.invoke(null, new Object[] {
                providerUrl, initialContextFactory, systemConfigurationPropertyServiceJndiName});
        assertNotNull(service);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemon#lookupService(String, String, String)}.
     * </p>
     *
     * <p>
     * Failure tests.
     * </p>
     * @throws Exception to JUnit
     */
    public void testLookupServiceClassCast() throws Exception {

        Method method = TogglePipelineIdentificationCycleDaemon.class.getDeclaredMethod(
                "lookupService", String.class, String.class, String.class);
        method.setAccessible(true);
        String providerUrl = "useless";
        String initialContextFactory = MockInitialContextFactory.class.getName();
        String systemConfigurationPropertyServiceJndiName = "reportService";
        try {
            SystemConfigurationPropertyService service = (SystemConfigurationPropertyService) method.invoke(
                null, new Object[] {
                    providerUrl, initialContextFactory, systemConfigurationPropertyServiceJndiName});
            assertNull(service);
            fail("exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemon#lookupService(String, String, String)}.
     * </p>
     *
     * <p>
     * Failure tests.
     * </p>
     * @throws Exception to JUnit
     */
    public void testLookupServiceNamingException() throws Exception {

        Method method = TogglePipelineIdentificationCycleDaemon.class.getDeclaredMethod(
                "lookupService", String.class, String.class, String.class);
        method.setAccessible(true);
        String providerUrl = "useless";
        String initialContextFactory = MockInitialContextFactory.class.getName();
        String systemConfigurationPropertyServiceJndiName = "notExist";
        try {
            SystemConfigurationPropertyService service =
                (SystemConfigurationPropertyService) method.invoke(
                    null, new Object[] {
                        providerUrl, initialContextFactory, systemConfigurationPropertyServiceJndiName});
            assertNull(service);
            fail("exception should be thrown.");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#main(String[])}.
     * </p>
     *
     * <p>
     * If the args is null, using the default property file.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMainNullArgs() throws Exception {
        TogglePipelineIdentificationCycleDaemon.main(null);
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        assertNotNull(scheduler.getJobDetail("TogglePipelineIdentificationCycleJob_1", "DEFAULT"));
        assertNotNull(scheduler.getJobDetail("TogglePipelineIdentificationCycleJob_2", "DEFAULT"));
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#main(String[])}.
     * </p>
     *
     * <p>
     * Invalid args, IllegalArgumentException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMainInvalidArgs() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {"arg1", "arg2"});
            fail("invalid argument exception should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {null});
            fail("invalid argument exception should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {"   "});
            fail("invalid argument exception should thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#main(String[])}.
     * </p>
     *
     * <p>
     * Failed to load properties, TogglePipelineIdentificationCycleDaemonException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMainFailProperties() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {"NotExist"});
            fail("TogglePipelineIdentificationCycleDaemonException should thrown.");
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#main(String[])}.
     * </p>
     *
     * <p>
     * Failed to lookup service, TogglePipelineIdentificationCycleDaemonException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMainFailLookup() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {"test_files/invalid_daemon.properties"});
            fail("TogglePipelineIdentificationCycleDaemonException should thrown.");
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#main(String[])}.
     * </p>
     *
     * <p>
     * Failed to schedule jobs, TogglePipelineIdentificationCycleDaemonException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMainFailScheduleJobs() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(
                    new String[] {"test_files/invalid_daemon_status_config.properties"});
            fail("TogglePipelineIdentificationCycleDaemonException should thrown.");
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleDaemon#main(String[])}.
     * </p>
     *
     * <p>
     * Failed to schedule jobs, TogglePipelineIdentificationCycleDaemonException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMainFailDupicatedJobs() throws Exception {
        TogglePipelineIdentificationCycleDaemon.main(null);
        try {
            // again
            TogglePipelineIdentificationCycleDaemon.main(null);
            fail("TogglePipelineIdentificationCycleDaemonException should thrown.");
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            // success
        }
    }


}
