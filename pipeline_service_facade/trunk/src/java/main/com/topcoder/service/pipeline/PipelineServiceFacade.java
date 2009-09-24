/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;
import com.topcoder.service.studio.contest.ContestManagementException;

import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;

/**
 * <p>
 * An interface for the web service for contest pipeline services. It contains methods to find the contests pipeline
 * info and methods to search the competition change history info for both date and prize type.
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
 * 
 * @author snow01
 * @since Pipeline Conversion Service Layer Assembly 2 v1.0
 * @version 1.0.1
 */
@WebService(name = "PipelineServiceFacade")
public interface PipelineServiceFacade {
    /**
     * <p>
     * Search the contests by the given criteria.
     * </p>
     * 
     * @param criteria
     *            the search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException
     *             fail to do the search
     */
    List<Competition> getContests(ContestsSearchCriteria criteria) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the contests by the given date criteria.
     * </p>
     * 
     * @param criteria
     *            the date search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException
     *             fail to do the search
     */
    List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId, CompetitionType competitionType)
            throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId, CompetitionType competitionType)
            throws ContestPipelineServiceException;

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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException;

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
            throws ContestPipelineServiceException;
    
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
    public List<CommonPipelineData> getCommonPipelineData(Date startDate, Date endDate, boolean overdueContests)
            throws ContestPipelineServiceException;
}
