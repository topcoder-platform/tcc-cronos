/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.ejb.BaseReportConfigurationServiceBean;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceBean;
import hr.leads.services.jobs.TogglePipelineIdentificationCycleJob;
import hr.leads.services.model.PipelineCycleStatus;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <p>
 * This class is the accuracy tests for class <code>TogglePipelineIdentificationCycleJob</code>
 * .
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJobAccuracyTests extends AccuracyBaseTest {

    /**
     * Represents the logger instance for accuracy test.
     */
    private static final Logger LOGGER = Logger.getLogger("name");

    /**
     * <p>
     * Represents the SystemConfigurationPropertyServiceBean instance for accuracy test.
     * </p>
     */
    private SystemConfigurationPropertyServiceBean bean;

    /**
     * <p>
     * Represents the scheduler for accuracy test.
     * </p>
     */
    private Scheduler scheduler;

    /**
     * <p>
     * Represents the entity manager for accuracy test.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        clearDB();

        entityManager = createEntityManager();
        setupTest(entityManager);

        bean = createServiceBean();
        new TogglePipelineIdentificationCycleJob();
        scheduler = new StdSchedulerFactory().getScheduler();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        bean = null;
        scheduler = null;
        clearDB();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>TogglePipelineIdentificationCycleJob()</code>.
     * </p>
     */
    public void testCtor() {
        TogglePipelineIdentificationCycleJob local = new TogglePipelineIdentificationCycleJob();

        assertNotNull(local);
        assertTrue(local instanceof Job);
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        entityManager.getTransaction().begin();
        createJob(bean, PipelineCycleStatus.OPEN);
        entityManager.getTransaction().commit();

        PipelineCycleStatus status = bean.getPipelineCycleStatus();
        assertNotNull(status);
        assertEquals(PipelineCycleStatus.OPEN, status);
    }

    /**
     * <p>
     * Creates a job for schedule.
     * </p>
     *
     * @param systemConfigurationPropertyService the SystemConfigurationPropertyService job.
     * @param pipelineCycleStatus the pipeline cycle status.
     *
     * @throws Exception to JUnit.
     */
    private void createJob(Object systemConfigurationPropertyService, Object pipelineCycleStatus)
        throws Exception {
        JobDetail jobDetail = new JobDetail("Job", null, TogglePipelineIdentificationCycleJob.class);
        jobDetail.getJobDataMap().put(
            TogglePipelineIdentificationCycleJob.SYSTEM_CONFIGURATION_PROPERTY_SERVICE,
            systemConfigurationPropertyService);
        jobDetail.getJobDataMap().put(TogglePipelineIdentificationCycleJob.PIPELINE_CYCLE_STATUS,
            pipelineCycleStatus);

        SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger", "triggerGroup-s1");
        simpleTrigger.setStartTime(new Date(System.currentTimeMillis()));
        simpleTrigger.setEndTime(new Date(System.currentTimeMillis() + 100000));

        scheduler.start();
        scheduler.scheduleJob(jobDetail, simpleTrigger);

        Thread.sleep(3 * 1000);
    }

    /**
     * <p>
     * Creates the bean bean for accuracy test.
     * </p>
     *
     * @return the created system bean
     * @throws Exception if any error occurs
     */
    private SystemConfigurationPropertyServiceBean createServiceBean() throws Exception {
        SystemConfigurationPropertyServiceBean bean = new SystemConfigurationPropertyServiceBean();

        setField(SystemConfigurationPropertyServiceBean.class, bean, "entityManager", entityManager);
        setField(SystemConfigurationPropertyServiceBean.class, bean, "pipelineCycleStatusPropertyName",
            "pipelineCycleStatus");
        setField(BaseReportConfigurationServiceBean.class, bean, "logger", LOGGER);

        return bean;
    }
}
