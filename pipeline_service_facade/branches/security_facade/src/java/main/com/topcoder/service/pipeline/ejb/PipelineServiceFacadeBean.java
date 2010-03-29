/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.ws.annotation.EndpointConfig;

import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.pipeline.ContestPipelineService;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.ContestPipelineServiceException;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;
import com.topcoder.service.pipeline.CommonPipelineData;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is an implementation of <code>ContestPipelineService</code> web service in form of stateless session EJB.
 * </p>
 * 
 * <p>
 * Updated for Pipeline Conversion Cockpit Integration Assembly 1 v1.0 - Added method for getContestsByDate - Added
 * method to retrieve change histories for array of contest ids and their types.
 * </p>
 * 
 * <p>
 * Version 1.0.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve CommonPipelineData for given date range.
 * </p>
 * <p>
 * Version 1.1 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 * - added service that retrieves a list of dates that have full capacity starting from tomorrow for a given contest
 *   type (for software or studio contests)
 * </p>
 * 
 * <p>
 * Thread-safty: This is an CMT bean, so it transaction is managed by the container.
 * </p>
 *
 * @author snow01, pulky
 * @version 1.1
 * @since Pipeline Conversion Cockpit Integration Assembly 2 v1.0
 */
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PipelineServiceFacadeBean implements PipelineServiceFacadeRemote, PipelineServiceFacadeLocal {
    /**
     * <p>
     * A <code>ContestPipelineService</code> providing access to available <code>Contest Pipeline Service EJB</code>.
     * This bean is delegated to process the calls to the methods inherited from <code>Contest Pipeline Service</code>
     * component.
     * </p>
     */
    @EJB(name = "ejb/ContestPipelineService")
    private ContestPipelineService pipelineService = null;

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * A default empty constructor.
     */
    public PipelineServiceFacadeBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    protected void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        // first record in logger
        logExit("init");
    }

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestDateChangeHistories(long[] contestIds, CompetitionType[] competitionTypes, CompetitionChangeType changeType) method");
        logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");

        try {
            return this.pipelineService.getContestDateChangeHistories(contestIds, competitionTypes);
        } finally {
            this.logExit("getContestDateChangeHistories");
        }
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestPrizeChangeHistories() method");

        try {
            return this.pipelineService.getContestPrizeChangeHistories(contestIds, competitionTypes);
        } finally {
            this.logExit("getContestPrizeChangeHistories");
        }
    }

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId, CompetitionType competitionType)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG,
                "Enter getContestDateChangeHistory(long contestId, CompetitionType competitionType) method");
        logger.log(Level.DEBUG, "The parameters[contestId =" + contestId + ", competitionType = " + competitionType
                + "].");

        try {
            return this.pipelineService.getContestDateChangeHistory(contestId, competitionType);
        } finally {
            this.logExit("getContestDateChangeHistory");
        }
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId, CompetitionType competitionType)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG,
                "Enter getContestPrizeChangeHistory(long contestId, CompetitionType competitionType) method");
        logger.log(Level.DEBUG, "The parameters[contestId =" + contestId + ", competitionType = " + competitionType
                + "].");

        try {
            return this.pipelineService.getContestPrizeChangeHistory(contestId, competitionType);
        } finally {
            this.logExit("getContestPrizeChangeHistory");
        }
    }

    /**
     * Gets the list of competition for the specified date search criteria.
     * 
     * @param searchCriteria
     *            the date search criteria
     * 
     * @return the list of competition for the specified search criteria.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getContestsByDate(ContestsSearchCriteria criteria) method.");
        logger.log(Level.DEBUG, "with parameter criteria:" + criteria);
        return this.pipelineService.getContestsByDate(criteria);
    }

    /**
     * Gets the list of competition for the specified search criteria.
     * 
     * @param searchCriteria
     *            the search criteria
     * 
     * @return the list of competition for the specified search criteria.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Competition> getContests(ContestsSearchCriteria criteria) throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getContests(ContestsSearchCriteria criteria) method.");
        logger.log(Level.DEBUG, "with parameter criteria:" + criteria);
        ExceptionUtils.checkNull(criteria, null, null, "The criteria is null.");

        try {
            return this.pipelineService.getContests(criteria);
        } finally {
            logExit("getContests");
        }
    }

    /**
     * Gets the list of common pipeline data within between specified start and end date.
     * 
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException
     *             if error during retrieval from database.
     * @since 1.0.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonPipelineData> getCommonPipelineData(Date startDate, Date endDate, boolean overdueContests)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getCommonPipelineData(Date startDate, Date endDate, boolean overdueContests method.");
        logger.log(Level.DEBUG, "with parameter startDate:" + startDate + ", endDate: " + endDate + ", overdueContests: " + overdueContests);
        ExceptionUtils.checkNull(startDate, null, null, "The startDate is null.");
        ExceptionUtils.checkNull(endDate, null, null, "The endDate is null.");

        try {
            return this.pipelineService.getCommonPipelineData(startDate, endDate, overdueContests);
        } finally {
            logExit("getCommonPipelineData");
        }
    }

     /**
     * Gets the list of dates that have full capacity starting from tomorrow for the given contest type (for software 
     * or studio contests)
     * This method delegates to Pipeline Service layer.
     *
     * @param contestType the contest type
     * @param isStudio true of it is a studio competition, false otherwise
     *
     * @return the list of dates that have full capacity.
     *
     * @throws ContestPipelineServiceException if any error occurs during retrieval of information.
     *
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CapacityData> getCapacityFullDates(int contestType, boolean isStudio) 
        throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getCapacityFullDates(int contestType, boolean isStudio) method.");
        logger.log(Level.DEBUG, "with parameter contestType: " + contestType);
        logger.log(Level.DEBUG, "with parameter isStudio: " + isStudio);
        try {
            return this.pipelineService.getCapacityFullDates(contestType, isStudio);
        } finally {
            logExit("getCapacityFullDates");
        }
    }

    /**
     * This method is used to log the debug message.
     * 
     * @param msg
     *            the message string.
     */
    private void logDebug(String msg) {
        if (logger != null) {
            logger.log(Level.DEBUG, msg);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     * 
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     * 
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }
}
