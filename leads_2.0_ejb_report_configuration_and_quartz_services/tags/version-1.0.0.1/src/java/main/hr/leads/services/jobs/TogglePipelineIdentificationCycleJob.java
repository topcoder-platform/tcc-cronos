/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import hr.leads.services.LeadsServiceException;
import hr.leads.services.SystemConfigurationPropertyService;
import hr.leads.services.ejb.ReportHelper;
import hr.leads.services.model.PipelineCycleStatus;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * <p>
 * This class will toggle pipeline identification cycle by updating the pipeline
 * cycle status.
 * </p>
 * <p>
 * It implements Job interface so that it can be invoked by quartz as a
 * scheduler job.
 * </p>
 * <p>
 * It will retrieve system configuration property service and pipeline cycle
 * status from given execution context, and use them to update status.
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is immutable, it's thread safe assuming
 * that the execute() method's context parameter do not change during the
 * execution of job.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJob implements Job {

    /**
     * <p>
     * Represents the key used to retrieve SystemConfigurationPropertyService
     * instance from job execution context.
     * </p>
     * <p>
     * It cannot be null.
     * </p>
     * <p>
     * Default to "systemConfigurationPropertyService", cannot be changed.
     * </p>
     * <p>
     * It's used by the execute() method to retrieve
     * SystemConfigurationPropertyService instance, also used by
     * TogglePipelineIdentificationCycleDaemon#main() method to store
     * SystemConfigurationPropertyService instance.
     * </p>
     */
    public static final String SYSTEM_CONFIGURATION_PROPERTY_SERVICE =
        "systemConfigurationPropertySerivce";

    /**
     * <p>
     * Represents the key used to retrieve PipelineCycleStatus instance from job
     * execution context.
     * </p>
     * <p>
     * It cannot be null.
     * </p>
     * <p>
     * Default to "pipelineCycleStatus", cannot be changed.
     * </p>
     * <p>
     * It's used by the execute() method to retrieve PipelineCycleStatus
     * instance, also used by TogglePipelineIdentificationCycleDaemon#main()
     * method to store PipelineCycleStatus instance.
     * </p>
     */
    public static final String PIPELINE_CYCLE_STATUS = "pipelineCycleStatus";

    /**
     * <p>
     * Represents the logger used by this class to write log.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Initialized when a instance is created and before the constructor is called,
     * cannot be changed after that.
     * </p>
     * <p>
     * It's used by the execute() method to write log.
     * </p>
    */
    private final Logger logger = Logger
            .getLogger(TogglePipelineIdentificationCycleJob.class.getName());

    /**
     * <p>
     * Creates an instance of TogglePipelineIdentificationCycleJob.
     * </p>
     *
     * <p>
     * This is the default constructor.
     * </p>
    */
    public TogglePipelineIdentificationCycleJob() {
        // do nothing
    }

    /**
     * <p>
     * Calls by the Scheduler when a Trigger fires that is associated with the Job.
     * </p>
     *
     * @param context the JobExecutionContext instance represents the execution context of current job.
     *
     * @throws IllegalArgumentException if context is null.
     * @throws TogglePipelineIdentificationCycleJobExecutionException - if any error occurs.
    */
    @Override
    public void execute(JobExecutionContext context)
        throws TogglePipelineIdentificationCycleJobExecutionException {

        // prepare for logging
        String methodName = TogglePipelineIdentificationCycleJob.class.getName() + "execute";

        // log the entrance and the request parameters.
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[]{context}, new String[]{"context"});

        // validate the context
        if (context == null) {
            ReportHelper.logError(logger, methodName, "The parameter context is null.");
            throw new IllegalArgumentException("The parameter context cannot be null.");
        }

        // get SystemConfigurationPropertyService
        SystemConfigurationPropertyService service;
        try {
            service = (SystemConfigurationPropertyService) context.getJobDetail().getJobDataMap().get(
                        SYSTEM_CONFIGURATION_PROPERTY_SERVICE);
        } catch (ClassCastException e) {
            ReportHelper.logError(logger, methodName,
                    "The service is not the type of SystemConfigurationPropertyService");
            throw new TogglePipelineIdentificationCycleJobExecutionException(
                "The service is not the type of SystemConfigurationPropertyService");
        }

        if (service == null) {
            ReportHelper.logError(logger, methodName, "The service is null.");
            throw new TogglePipelineIdentificationCycleJobExecutionException(
                    "failed go get the service by " + SYSTEM_CONFIGURATION_PROPERTY_SERVICE);
        }

        // get PipelineCycleStatus
        PipelineCycleStatus status;
        try {
            status = (PipelineCycleStatus) context.getJobDetail().getJobDataMap().get(PIPELINE_CYCLE_STATUS);
        } catch (ClassCastException e) {
            throw new TogglePipelineIdentificationCycleJobExecutionException(
                "The status is not the type of PipelineCycleStatus");
        }

        if (status == null) {
            ReportHelper.logError(logger, methodName, "The status is null.");
            throw new TogglePipelineIdentificationCycleJobExecutionException(
                    "failed to get the PipelineCycleStatus via " + PIPELINE_CYCLE_STATUS);
        }

        // update status using service
        try {
            service.updatePipelineCycleStatus(status);
        } catch (LeadsServiceException e) {
            ReportHelper.logError(logger, methodName, "failed to update the pipeline cycle status.");
            throw new TogglePipelineIdentificationCycleJobExecutionException(
                    "failed to update the pipeline cycle status.", e);
        }

        // log method exit
        ReportHelper.logExit(logger, methodName);
    }

}
