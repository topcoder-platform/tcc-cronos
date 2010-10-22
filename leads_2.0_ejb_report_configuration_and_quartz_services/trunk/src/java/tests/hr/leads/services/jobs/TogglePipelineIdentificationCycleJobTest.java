/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import hr.leads.services.JPATestBase;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceBean;
import hr.leads.services.model.PipelineCycleStatus;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
/**
 * <p>
 * All tests for <code>TogglePipelineIdentificationCycleJob</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJobTest extends JPATestBase {

    /**
     * <p>
     * Represents the instance of TogglePipelineIdentificationCycleJob for unit tests.
     * </p>
     */
    private TogglePipelineIdentificationCycleJob togglePipelineIdentificationCycleJob;

    /**
     * <p>
     * Sets up the test environments.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        togglePipelineIdentificationCycleJob = new TogglePipelineIdentificationCycleJob();
    }

    /**
     * <p>
     * Tears down the test environments.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Tests constructor: {@link TogglePipelineIdentificationCycleJob#TogglePipelineIdentificationCycleJob()}.
     * </p>
     *
     * <p>
     * Checks if the instance can be successfully created.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJob() {
        assertNotNull("The instance cannot be null.", togglePipelineIdentificationCycleJob);
        assertTrue("should be instance of TogglePipelineIdentificationCycleJob",
                togglePipelineIdentificationCycleJob instanceof TogglePipelineIdentificationCycleJob);
        assertTrue("should be instance of Job",
                togglePipelineIdentificationCycleJob instanceof Job);
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleJob#execute(JobExecutionContext)}.
     * </p>
     *
     * <p>
     * If the context is null, TogglePipelineIdentificationCycleJobExecutionException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExecuteNullContext() throws Exception {
     // init the context
        openSession();
        try {

            // execute the job
            beginTransaction();
            togglePipelineIdentificationCycleJob.execute(null);
            commitTransaction();

            fail("TogglePipelineIdentificationCycleJobExecutionException should be thrown.");

        } catch (Exception e) {
            rollbackTransaction();
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleJob#execute(JobExecutionContext)}.
     * </p>
     *
     * <p>
     * If the service is null, TogglePipelineIdentificationCycleJobExecutionException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExecuteNullService() throws Exception {
     // init the context
        openSession();
        try {

            JobDetail job = new JobDetail();

            job.getJobDataMap().put(
                            TogglePipelineIdentificationCycleJob.SYSTEM_CONFIGURATION_PROPERTY_SERVICE, null);
            job.getJobDataMap().put(
                    TogglePipelineIdentificationCycleJob.PIPELINE_CYCLE_STATUS, PipelineCycleStatus.OPEN);

            Trigger trigger = new CronTrigger();
            TriggerFiredBundle bundle = new TriggerFiredBundle(job, trigger, null,
                    false, null, null, null, null);

            JobExecutionContext context = new JobExecutionContext(null, bundle,
                    null);

            // execute the job
            beginTransaction();
            togglePipelineIdentificationCycleJob.execute(context);
            commitTransaction();

            fail("TogglePipelineIdentificationCycleJobExecutionException should be thrown.");

        } catch (Exception e) {
            rollbackTransaction();
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleJob#execute(JobExecutionContext)}.
     * </p>
     *
     * <p>
     * If the status is null, TogglePipelineIdentificationCycleJobExecutionException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExecuteNullStatus() throws Exception {
     // init the context
        openSession();
        try {
            SystemConfigurationPropertyServiceBean service =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");

            JobDetail job = new JobDetail();

            job.getJobDataMap().put(
                            TogglePipelineIdentificationCycleJob.SYSTEM_CONFIGURATION_PROPERTY_SERVICE, service);
            job.getJobDataMap().put(
                    TogglePipelineIdentificationCycleJob.PIPELINE_CYCLE_STATUS, null);

            Trigger trigger = new CronTrigger();
            TriggerFiredBundle bundle = new TriggerFiredBundle(job, trigger, null,
                    false, null, null, null, null);

            JobExecutionContext context = new JobExecutionContext(null, bundle,
                    null);

            // execute the job
            beginTransaction();
            togglePipelineIdentificationCycleJob.execute(context);
            commitTransaction();

            fail("TogglePipelineIdentificationCycleJobExecutionException should be thrown.");

        } catch (Exception e) {
            rollbackTransaction();
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleJob#execute(JobExecutionContext)}.
     * </p>
     *
     * <p>
     * If failed to update the status,
     * TogglePipelineIdentificationCycleJobExecutionException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExecuteFailToUpdate() throws Exception {
     // init the context
        openSession();
        try {
            SystemConfigurationPropertyServiceBean service =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");

            JobDetail job = new JobDetail();

            job.getJobDataMap().put(
                            TogglePipelineIdentificationCycleJob.SYSTEM_CONFIGURATION_PROPERTY_SERVICE, service);
            job.getJobDataMap().put(
                    TogglePipelineIdentificationCycleJob.PIPELINE_CYCLE_STATUS, PipelineCycleStatus.OPEN);

            Trigger trigger = new CronTrigger();
            TriggerFiredBundle bundle = new TriggerFiredBundle(job, trigger, null,
                    false, null, null, null, null);

            JobExecutionContext context = new JobExecutionContext(null, bundle,
                    null);



            // execute the job
            closeSession();

            togglePipelineIdentificationCycleJob.execute(context);


            fail("TogglePipelineIdentificationCycleJobExecutionException should be thrown.");

        } catch (TogglePipelineIdentificationCycleJobExecutionException e) {
            // success
        } finally {
            // closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link TogglePipelineIdentificationCycleJob#execute(JobExecutionContext)}.
     * </p>
     *
     * <p>
     * Accuracy test to check if the status successfully updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        // init the context
        openSession();
        try {
            SystemConfigurationPropertyServiceBean service =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");

            JobDetail job = new JobDetail();

            job.getJobDataMap().put(
                            TogglePipelineIdentificationCycleJob.SYSTEM_CONFIGURATION_PROPERTY_SERVICE, service);
            job.getJobDataMap().put(
                    TogglePipelineIdentificationCycleJob.PIPELINE_CYCLE_STATUS, PipelineCycleStatus.OPEN);

            Trigger trigger = new CronTrigger();
            TriggerFiredBundle bundle = new TriggerFiredBundle(job, trigger, null,
                    false, null, null, null, null);

            JobExecutionContext context = new JobExecutionContext(null, bundle,
                    null);

            // execute the job
            beginTransaction();
            togglePipelineIdentificationCycleJob.execute(context);
            commitTransaction();

            // test if it is updated
            PipelineCycleStatus status = service.getPipelineCycleStatus();
            assertNotNull("status cannot be null.", status);
            assertEquals("status not equal.", PipelineCycleStatus.OPEN, status);

        } finally {
            closeSession();
        }
    }
}
